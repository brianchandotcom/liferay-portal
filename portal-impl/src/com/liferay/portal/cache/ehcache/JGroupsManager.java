/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
 * See http://issues.liferay.com/browse/LPS-11061.
 * </p>
 *
 * @author Tina Tian
 */
public class JGroupsManager implements CacheManagerPeerProvider, CachePeer {

	public JGroupsManager(
		CacheManager cacheManager, String clusterName,
		String channelProperties) {

		try {
			_jChannel = new JChannel(channelProperties);

			_jChannel.setReceiver(new EhcacheJGroupsReceiver());

			_jChannel.connect(clusterName);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Create a new channel with properties " +
						_jChannel.getProperties());
			}
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to initialize channels", e);
			}
		}

		_cacheManager = cacheManager;
	}

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

	@SuppressWarnings("rawtypes")
	public List getElements(List list) {
		return null;
	}

	public String getGuid() {
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List getKeys() {
		return null;
	}

	public String getName() {
		return null;
	}

	public Element getQuiet(Serializable serializable) {
		return null;
	}

	public String getScheme() {
		return _SCHEME;
	}

	public long getTimeForClusterToForm() {
		return 0;
	}

	public String getUrl() {
		return null;
	}

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

	public void init() {
	}

	public List<JGroupsManager> listRemoteCachePeers(Ehcache ehcache) {
		List<JGroupsManager> cachePeers = new ArrayList<JGroupsManager>();

		cachePeers.add(this);

		return cachePeers;
	}

	public void put(Element element) {
	}

	public void registerPeer(String string) {
	}

	public boolean remove(Serializable serializable) {
		return false;
	}

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

	@SuppressWarnings("rawtypes")
	public void send(List eventMessages) throws RemoteException {
		send(null, eventMessages);
	}

	public void unregisterPeer(String string) {
	}

	protected void handleJGroupsNotification(
		JGroupEventMessage jGroupEventMessage) {

		Cache cache = _cacheManager.getCache(jGroupEventMessage.getCacheName());

		if (cache == null) {
			return;
		}

		int event = jGroupEventMessage.getEvent();
		Serializable key = jGroupEventMessage.getSerializableKey();

		if ((event == JGroupEventMessage.REMOVE) &&
			(cache.getQuiet(key) != null)) {

			cache.remove(key, true);
		}
		else if (event == JGroupEventMessage.REMOVE_ALL) {
			cache.removeAll(true);
		}
		else if (event == JGroupEventMessage.PUT) {
			Element element = jGroupEventMessage.getElement();

			cache.put(new Element(key, element.getValue()), true);
		}
	}

	private static final String _SCHEME = "JGroups";

	private static Log _log = LogFactoryUtil.getLog(JGroupsManager.class);

	private CacheManager _cacheManager;
	private JChannel _jChannel;

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