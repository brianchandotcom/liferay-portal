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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.cluster.BaseClusterReceiver;
import com.liferay.portal.cluster.JGroupsReceiver;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterReceiver;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CachePeer;
import net.sf.ehcache.distribution.jgroups.JGroupEventMessage;

import org.jgroups.JChannel;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-11061.
 * </p>
 *
 * @author Tina Tian
 */
public class JGroupsManager implements CacheManagerPeerProvider, CachePeer {

	public JGroupsManager(
		CacheManager cacheManager, String clusterName,
		String channelProperties) {

		_cacheManager = cacheManager;
		_executorService = PortalExecutorManagerUtil.getPortalExecutor(
			JGroupsManager.class.getName());

		_clusterReceiver = new EhcacheJGroupsReceiver(_executorService);

		JChannel jChannel = null;

		try {
			jChannel = new JChannel(channelProperties);

			jChannel.setReceiver(new JGroupsReceiver(_clusterReceiver));

			jChannel.connect(clusterName);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Create a new channel with properties " +
						jChannel.getProperties());
			}
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to initialize channels", e);
			}
		}

		_jChannel = jChannel;

		_clusterReceiver.openLatch();
	}

	@Override
	public void dispose() throws CacheException {
		if (_jChannel != null) {
			_jChannel.setReceiver(null);

			_jChannel.close();
		}

		_executorService.shutdownNow();
	}

	public org.jgroups.Address getBusLocalAddress() {
		return _jChannel.getAddress();
	}

	public List<org.jgroups.Address> getBusMembership() {
		List<org.jgroups.Address> view = new ArrayList<>();

		for (Address address : _clusterReceiver.getAddresses()) {
			view.add((org.jgroups.Address)address.getRealAddress());
		}

		return view;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getElements(List list) {
		return null;
	}

	@Override
	public String getGuid() {
		return null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getKeys() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Element getQuiet(Serializable serializable) {
		return null;
	}

	@Override
	public String getScheme() {
		return _SCHEME;
	}

	@Override
	public long getTimeForClusterToForm() {
		return 0;
	}

	@Override
	public String getUrl() {
		return null;
	}

	@Override
	public String getUrlBase() {
		return null;
	}

	public void handleNotification(Serializable serializable) {
		if (serializable instanceof JGroupEventMessage) {
			handleJGroupsNotification((JGroupEventMessage)serializable);
		}
		else if (serializable instanceof List<?>) {
			List<?> valueList = (List<?>)serializable;

			for (Object object : valueList) {
				if (object instanceof JGroupEventMessage) {
					handleJGroupsNotification((JGroupEventMessage)object);
				}
			}
		}
	}

	@Override
	public void init() {
	}

	@Override
	public List<JGroupsManager> listRemoteCachePeers(Ehcache ehcache) {
		List<JGroupsManager> cachePeers = new ArrayList<>();

		cachePeers.add(this);

		return cachePeers;
	}

	@Override
	public void put(Element element) {
	}

	@Override
	public void registerPeer(String string) {
	}

	@Override
	public boolean remove(Serializable serializable) {
		return false;
	}

	@Override
	public void removeAll() {
	}

	@SuppressWarnings("rawtypes")
	public void send(Address address, List eventMessages)
		throws RemoteException {

		ArrayList<JGroupEventMessage> jGroupEventMessages = new ArrayList<>();

		for (Object eventMessage : eventMessages) {
			if (eventMessage instanceof JGroupEventMessage) {
				JGroupEventMessage jGroupEventMessage =
					(JGroupEventMessage)eventMessage;

				jGroupEventMessages.add(jGroupEventMessage);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						eventMessage + "is not a JGroupEventMessage type");
				}
			}
		}

		try {
			_jChannel.send(null, jGroupEventMessages);
		}
		catch (Throwable t) {
			throw new RemoteException(t.getMessage());
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void send(List eventMessages) throws RemoteException {
		send(null, eventMessages);
	}

	@Override
	public void unregisterPeer(String string) {
	}

	protected void handleJGroupsNotification(
		JGroupEventMessage jGroupEventMessage) {

		Cache cache = _cacheManager.getCache(jGroupEventMessage.getCacheName());

		if (cache == null) {
			return;
		}

		int event = jGroupEventMessage.getEvent();

		if (event == JGroupEventMessage.REMOVE_ALL) {
			cache.removeAll(true);

			return;
		}

		Serializable key = jGroupEventMessage.getSerializableKey();

		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if ((event == JGroupEventMessage.REMOVE) &&
			(cache.getQuiet(key) != null)) {

			cache.remove(key, true);
		}
		else if (event == JGroupEventMessage.PUT) {
			Element element = jGroupEventMessage.getElement();

			Object value = element.getObjectValue();

			if (value == null) {
				cache.remove(key, true);
			}
			else {
				cache.put(new Element(key, value), true);
			}
		}
	}

	private static final String _SCHEME = "JGroups";

	private static final Log _log = LogFactoryUtil.getLog(JGroupsManager.class);

	private final CacheManager _cacheManager;
	private final ClusterReceiver _clusterReceiver;
	private final ExecutorService _executorService;
	private final JChannel _jChannel;

	private class EhcacheJGroupsReceiver extends BaseClusterReceiver {

		@Override
		protected void doReceive(
			Object messagePayload, Address srcAddress, Address destAddress) {

			if (messagePayload instanceof Serializable) {
				handleNotification((Serializable)messagePayload);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to process message content of type " +
							messagePayload.getClass().getName());
				}
			}
		}

		private EhcacheJGroupsReceiver(ExecutorService executorService) {
			super(executorService);
		}

	}

}