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

package com.liferay.portal.wab.extender.internal;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.wab.extender.internal.configuration.WabExtenderConfiguration;
import com.liferay.portal.wab.extender.internal.event.EventUtil;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.utils.extender.AbstractExtender;
import org.apache.felix.utils.extender.Extension;
import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.wab.extender.internal.configuration.WabExtenderConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true
)
public class WabFactory extends AbstractExtender {

	@Activate
	public void activate(ComponentContext componentContext) {
		setSynchronous(true);

		_bundleContext = componentContext.getBundleContext();
		_eventUtil = new EventUtil(_bundleContext);
		_logger = new Logger(_bundleContext);

		_saxParserFactory.setNamespaceAware(false);
		_saxParserFactory.setValidating(false);
		_saxParserFactory.setXIncludeAware(false);

		try {
			_saxParserFactory.setFeature(_FEATURES_DISALLOW_DOCTYPE_DECL, true);
			_saxParserFactory.setFeature(
				_FEATURES_EXTERNAL_GENERAL_ENTITIES, false);
			_saxParserFactory.setFeature(
				_FEATURES_EXTERNAL_PARAMETER_ENTITIES, false);
			_saxParserFactory.setFeature(_FEATURES_LOAD_EXTERNAL_DTD, false);
		}
		catch (ParserConfigurationException | SAXNotRecognizedException |
			   SAXNotSupportedException e) {

			ReflectionUtil.throwException(e);
		}

		Dictionary<String, Object> properties =
			componentContext.getProperties();

		_wabExtenderConfiguration = Configurable.createConfigurable(
			WabExtenderConfiguration.class, properties);

		try {
			_webBundleDeployer = new WebBundleDeployer(
				_bundleContext, properties, _saxParserFactory, _eventUtil,
				_logger);

			super.start(_bundleContext);
		}
		catch (Exception e) {
			_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
		}
	}

	@Deactivate
	public void deactivate() throws Exception {
		super.stop(_bundleContext);

		_bundleContext = null;

		_eventUtil.close();

		_eventUtil = null;

		_logger = null;

		_webBundleDeployer.close();

		_webBundleDeployer = null;
	}

	@Override
	protected void debug(Bundle bundle, String message) {
		_logger.log(Logger.LOG_DEBUG, "[" + bundle + "] " + message);
	}

	@Override
	protected Extension doCreateExtension(Bundle bundle) throws Exception {
		return new WABExtension(bundle);
	}

	@Override
	protected void error(String message, Throwable t) {
		_logger.log(Logger.LOG_ERROR, message, t);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSAXParserFactory(SAXParserFactory saxParserFactory) {
		_saxParserFactory = saxParserFactory;
	}

	@Override
	protected void warn(Bundle bundle, String message, Throwable t) {
		_logger.log(Logger.LOG_WARNING, "[" + bundle + "] " + message, t);
	}

	private static final String _FEATURES_DISALLOW_DOCTYPE_DECL =
		"http://apache.org/xml/features/disallow-doctype-decl";

	private static final String _FEATURES_EXTERNAL_GENERAL_ENTITIES =
		"http://xml.org/sax/features/external-general-entities";

	private static final String _FEATURES_EXTERNAL_PARAMETER_ENTITIES =
		"http://xml.org/sax/features/external-parameter-entities";

	private static final String _FEATURES_LOAD_EXTERNAL_DTD =
		"http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private BundleContext _bundleContext;
	private EventUtil _eventUtil;
	private Logger _logger;
	private volatile SAXParserFactory _saxParserFactory;
	private WabExtenderConfiguration _wabExtenderConfiguration;
	private WebBundleDeployer _webBundleDeployer;

	private class WABExtension implements Extension {

		public WABExtension(Bundle bundle) {
			_bundle = bundle;
		}

		@Override
		public void destroy() throws Exception {
			try {
				_started.await(
					_wabExtenderConfiguration.stopTimeout(),
					TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException ie) {
				_logger.log(
					Logger.LOG_ERROR,
					String.format(
						"The wait for bundle {0}/{1} to start before " +
							"destroying was interrupted",
						_bundle.getSymbolicName(), _bundle.getBundleId()),
					ie);
			}

			_webBundleDeployer.doStop(_bundle);
		}

		@Override
		public void start() throws Exception {
			try {
				_webBundleDeployer.doStart(_bundle);
			}
			finally {
				_started.countDown();
			}
		}

		private final Bundle _bundle;
		private final CountDownLatch _started = new CountDownLatch(1);

	}

}