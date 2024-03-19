/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal.cluster.link;

import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEvent;
import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEventType;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;

import java.io.Serializable;

import java.nio.ByteBuffer;

/**
 * @author Tina Tian
 */
public class ClusterLinkMessageUtil {

	public static long getCompanyId(Message message) {
		return message.getLong(_COMPANY_ID);
	}

	public static Serializable getKey(Message message) {
		return _deserialize(message.get(_KEY));
	}

	public static PortalCacheClusterEventType getPortalCacheClusterEventType(
		Message message) {

		return PortalCacheClusterEventType.valueOf(
			message.getString(_EVENT_TYPE));
	}

	public static String getPortalCacheManagerName(Message message) {
		return message.getString(_CACHE_MANAGER_NAME);
	}

	public static String getPortalCacheName(Message message) {
		return message.getString(_CACHE_NAME);
	}

	public static int getTimeToLive(Message message) {
		return message.getInteger(_TIME_TO_LIVE);
	}

	public static Serializable getValue(Message message) {
		return _deserialize(message.get(_VALUE));
	}

	public static void populateMessageFromPortalCacheClusterEvent(
		Message message, PortalCacheClusterEvent portalCacheClusterEvent) {

		message.put(
			_CACHE_MANAGER_NAME,
			portalCacheClusterEvent.getPortalCacheManagerName());
		message.put(_CACHE_NAME, portalCacheClusterEvent.getPortalCacheName());
		message.put(_COMPANY_ID, portalCacheClusterEvent.getCompanyId());
		message.put(
			_EVENT_TYPE,
			String.valueOf(portalCacheClusterEvent.getEventType()));
		message.put(_KEY, _serialize(portalCacheClusterEvent.getElementKey()));
		message.put(_TIME_TO_LIVE, portalCacheClusterEvent.getTimeToLive());
		message.put(
			_VALUE, _serialize(portalCacheClusterEvent.getElementValue()));
	}

	private static Serializable _deserialize(Object object) {
		if (object instanceof byte[]) {
			byte[] bytes = (byte[])object;

			Deserializer deserializer = new Deserializer(
				ByteBuffer.wrap(bytes));

			try {
				return deserializer.readObject();
			}
			catch (ClassNotFoundException classNotFoundException) {
				_log.error(
					"Unable to deserialize object", classNotFoundException);

				return null;
			}
		}

		return (Serializable)object;
	}

	private static Serializable _serialize(Serializable serializable) {
		if ((serializable == null) || (serializable instanceof String) ||
			(serializable instanceof Number) ||
			(serializable instanceof Boolean)) {

			return serializable;
		}

		Serializer serializer = new Serializer();

		serializer.writeObject(serializable);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		return byteBuffer.array();
	}

	private static final String _CACHE_MANAGER_NAME = "cache.manager.name";

	private static final String _CACHE_NAME = "cache.name";

	private static final String _COMPANY_ID = "company.id";

	private static final String _EVENT_TYPE = "event.type";

	private static final String _KEY = "key";

	private static final String _TIME_TO_LIVE = "time.to.live";

	private static final String _VALUE = "value";

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterLinkMessageUtil.class);

}