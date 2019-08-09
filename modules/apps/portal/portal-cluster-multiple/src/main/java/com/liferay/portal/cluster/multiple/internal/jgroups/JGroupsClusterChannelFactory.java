/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.cluster.multiple.internal.jgroups;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.cluster.multiple.configuration.ClusterExecutorConfiguration;
import com.liferay.portal.cluster.multiple.internal.ClusterChannel;
import com.liferay.portal.cluster.multiple.internal.ClusterChannelFactory;
import com.liferay.portal.cluster.multiple.internal.ClusterReceiver;
import com.liferay.portal.cluster.multiple.internal.io.ClusterClassLoaderPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.net.InetAddress;
import java.net.NetworkInterface;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

import org.jgroups.conf.ConfiguratorFactory;
import org.jgroups.conf.ProtocolStackConfigurator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.cluster.multiple.configuration.ClusterExecutorConfiguration",
	enabled = false, immediate = true, service = ClusterChannelFactory.class
)
public class JGroupsClusterChannelFactory implements ClusterChannelFactory {

	@Override
	public ClusterChannel createClusterChannel(
		ExecutorService executorService, String channleLogicName,
		String channelPropertiesLocation, String clusterName,
		ClusterReceiver clusterReceiver) {

		try {
			return new JGroupsClusterChannel(
				executorService, channleLogicName,
				_parseChannelProperties(channelPropertiesLocation), clusterName,
				clusterReceiver, _bindInetAddress,
				_clusterExecutorConfiguration, _classLoaders);
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to create JGroupsClusterChannel", e);
		}
	}

	@Override
	public InetAddress getBindInetAddress() {
		return _bindInetAddress;
	}

	@Override
	public NetworkInterface getBindNetworkInterface() {
		return _bindNetworkInterface;
	}

	@Activate
	@Modified
	protected synchronized void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_clusterExecutorConfiguration = ConfigurableUtil.createConfigurable(
			ClusterExecutorConfiguration.class, properties);

		initSystemProperties(
			_props.getArray(PropsKeys.CLUSTER_LINK_CHANNEL_SYSTEM_PROPERTIES));

		initBindAddress(
			GetterUtil.getString(
				_props.get(PropsKeys.CLUSTER_LINK_AUTODETECT_ADDRESS)));

		_bundleTracker = new BundleTracker<ClassLoader>(
			bundleContext, Bundle.ACTIVE, null) {

			@Override
			public ClassLoader addingBundle(Bundle bundle, BundleEvent event) {
				BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

				ClassLoader classLoader = bundleWiring.getClassLoader();

				ClusterClassLoaderPool.registerFallback(
					bundle.getSymbolicName(), bundle.getVersion(), classLoader);

				return classLoader;
			}

			@Override
			public void removedBundle(
				Bundle bundle, BundleEvent event, ClassLoader classLoader) {

				ClusterClassLoaderPool.unregisterFallback(
					bundle.getSymbolicName(), bundle.getVersion());
			}

		};

		_bundleTracker.open();
	}

	@Deactivate
	protected synchronized void deactive() {
		if (_bundleTracker != null) {
			_bundleTracker.close();
		}

		_classLoaders.clear();
	}

	protected void initBindAddress(String autodetectAddress) {
		if (Validator.isNull(autodetectAddress)) {
			return;
		}

		String host = autodetectAddress;
		int port = 80;

		int index = autodetectAddress.indexOf(CharPool.COLON);

		if (index != -1) {
			host = autodetectAddress.substring(0, index);
			port = GetterUtil.getInteger(
				autodetectAddress.substring(index + 1), port);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Autodetecting JGroups outgoing IP address and interface ",
					"for ", host, ":", port));
		}

		try {
			SocketUtil.BindInfo bindInfo = SocketUtil.getBindInfo(host, port);

			_bindInetAddress = bindInfo.getInetAddress();

			_bindNetworkInterface = bindInfo.getNetworkInterface();
		}
		catch (IOException e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to detect bind address for JGroups, using " +
						"loopback");

				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}

			_bindInetAddress = InetAddress.getLoopbackAddress();

			try {
				_bindNetworkInterface = NetworkInterface.getByInetAddress(
					_bindInetAddress);
			}
			catch (IOException ie) {
				_log.error("Unable to bind to lopoback interface", ie);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Setting JGroups outgoing IP address to ",
					_bindInetAddress.getHostAddress(), " and interface to ",
					_bindNetworkInterface.getName()));
		}
	}

	protected void initSystemProperties(String[] channelSystemPropertiesArray) {
		for (String channelSystemProperty : channelSystemPropertiesArray) {
			int index = channelSystemProperty.indexOf(CharPool.COLON);

			if (index == -1) {
				continue;
			}

			String key = channelSystemProperty.substring(0, index);
			String value = channelSystemProperty.substring(index + 1);

			System.setProperty(key, value);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Setting system property {key=", key, ", value=", value,
						"}"));
			}
		}
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	private InputStream _getInputStream(String channelPropertiesLocation)
		throws IOException {

		InputStream inputStream = ConfiguratorFactory.getConfigStream(
			channelPropertiesLocation);

		if (inputStream == null) {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			inputStream = classLoader.getResourceAsStream(
				channelPropertiesLocation);
		}

		if (inputStream == null) {
			throw new FileNotFoundException(
				"Unable to load channel properties from " +
					channelPropertiesLocation);
		}

		return inputStream;
	}

	private ProtocolStackConfigurator _parseChannelProperties(
			String channelPropertiesLocation)
		throws Exception {

		try (InputStream inputStream = _getInputStream(
				channelPropertiesLocation)) {

			String configXML = StreamUtil.toString(inputStream);

			int index = 0;

			StringBundler sb = new StringBundler();

			while (index < configXML.length()) {
				int startIndex = configXML.indexOf(
					StringPool.DOLLAR_AND_OPEN_CURLY_BRACE, index);

				if (startIndex < 0) {
					break;
				}

				int endIndex = configXML.indexOf(
					StringPool.CLOSE_CURLY_BRACE, startIndex);

				if (endIndex < 0) {
					break;
				}

				String propertyKey = configXML.substring(
					startIndex + 2, endIndex);

				Object value = _props.get(
					StringUtil.replace(
						propertyKey, _ENCODED_CHARACTERS,
						_ORIGINAL_CHARACTERS));

				if (value instanceof String) {
					sb.append(configXML.substring(index, startIndex));
					sb.append(
						StringUtil.replace(
							(String)value, _ORIGINAL_CHARACTERS,
							_ENCODED_CHARACTERS));
				}
				else {
					sb.append(configXML.substring(index, endIndex + 1));
				}

				index = endIndex + 1;
			}

			if (sb.length() > 0) {
				if (index < configXML.length()) {
					sb.append(configXML.substring(index));
				}

				configXML = sb.toString();
			}

			return ConfiguratorFactory.getStackConfigurator(
				new UnsyncByteArrayInputStream(
					configXML.getBytes(StringPool.UTF8)));
		}
	}

	private static final String[] _ENCODED_CHARACTERS = {
		StringPool.AMPERSAND_ENCODED, StringPool.QUOTE_ENCODED,
		StringPool.APOSTROPHE_ENCODED, "&gt;", "&lt;"
	};

	private static final String[] _ORIGINAL_CHARACTERS = {
		StringPool.AMPERSAND, StringPool.QUOTE, StringPool.APOSTROPHE,
		StringPool.GREATER_THAN, StringPool.LESS_THAN
	};

	private static final Log _log = LogFactoryUtil.getLog(
		JGroupsClusterChannelFactory.class);

	private InetAddress _bindInetAddress;
	private NetworkInterface _bindNetworkInterface;
	private BundleTracker<ClassLoader> _bundleTracker;
	private final ConcurrentMap<ClassLoader, ClassLoader> _classLoaders =
		new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	private volatile ClusterExecutorConfiguration _clusterExecutorConfiguration;
	private Props _props;

}