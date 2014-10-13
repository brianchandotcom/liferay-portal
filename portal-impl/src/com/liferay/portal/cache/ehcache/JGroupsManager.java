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

import com.liferay.portal.cluster.BaseReceiver;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CachePeer;
import net.sf.ehcache.distribution.jgroups.JGroupEventMessage;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.View;

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

		JChannel jChannel = null;

		try {
			jChannel = new JChannel(channelProperties);

			jChannel.setReceiver(new EhcacheJGroupsReceiver());

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
	}

	@Override
	public void dispose() throws CacheException {
		if (_jChannel != null) {
			_jChannel.close();
		}
	}

	public Address getBusLocalAddress() {
		return _jChannel.getAddress();
	}

	public List<Address> getBusMembership() {
		BaseReceiver baseReceiver = (BaseReceiver)_jChannel.getReceiver();

		View view = baseReceiver.getView();

		return view.getMembers();
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
		List<JGroupsManager> cachePeers = new ArrayList<JGroupsManager>();

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

		ArrayList<JGroupEventMessage> jGroupEventMessages =
			new ArrayList<JGroupEventMessage>();

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
			_jChannel.send(address, jGroupEventMessages);
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
	private final JChannel _jChannel;

	private class EhcacheJGroupsReceiver extends BaseReceiver {

		@Override
		public void receive(Message message) {
			Object object = message.getObject();

			if (object == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Message content is null");
				}

				return;
			}

			if (object instanceof Serializable) {
				handleNotification((Serializable)object);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to process message content of type " +
							object.getClass().getName());
				}
			}
		}

	}

}