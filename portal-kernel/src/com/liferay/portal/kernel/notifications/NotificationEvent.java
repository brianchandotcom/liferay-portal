/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.notifications;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Edward Han
 */
public class NotificationEvent implements Serializable {

	public NotificationEvent(
		long timestamp, String type, JSONObject payloadJSONObject) {

		_timestamp = timestamp;
		_type = type;
		_payloadJSONObject = payloadJSONObject;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationEvent)) {
			return false;
		}

		NotificationEvent notificationEvent = (NotificationEvent)object;

		if (Objects.equals(getUuid(), notificationEvent.getUuid())) {
			return true;
		}

		return false;
	}

	public long getDeliverBy() {
		return _deliverBy;
	}

	public int getDeliveryType() {
		return _deliveryType;
	}

	public JSONObject getPayload() {
		return _payloadJSONObject;
	}

	public long getTimestamp() {
		return _timestamp;
	}

	public String getType() {
		return _type;
	}

	public String getUuid() {
		if (_uuid == null) {
			_uuid = PortalUUIDUtil.generate();
		}

		return _uuid;
	}

	@Override
	public int hashCode() {
		String uuid = getUuid();

		return uuid.hashCode();
	}

	public boolean isArchived() {
		return _archived;
	}

	public boolean isDeliveryRequired() {
		return _deliveryRequired;
	}

	public void setArchived(boolean archived) {
		_archived = archived;
	}

	public void setDeliverBy(long deliverBy) throws IllegalArgumentException {
		if ((deliverBy < 0) && _deliveryRequired) {
			throw new IllegalArgumentException(
				"Deliver by must be greater than or equal to 0 if delivery " +
					"is required");
		}

		_deliverBy = deliverBy;
	}

	public void setDeliveryRequired(long deliverBy)
		throws IllegalArgumentException {

		if (deliverBy < 0) {
			throw new IllegalArgumentException(
				"Deliver by must be greater than or equal to 0 if delivery " +
					"is required");
		}

		_deliverBy = deliverBy;
		_deliveryRequired = true;
	}

	public void setDeliveryType(int deliveryType) {
		_deliveryType = deliveryType;
	}

	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			_KEY_ARCHIVED, _archived
		).put(
			_KEY_DELIVERY_BY, _deliverBy
		).put(
			_KEY_DELIVERY_REQUIRED, _deliveryRequired
		).put(
			_KEY_DELIVERY_TYPE, _deliveryType
		).put(
			_KEY_PAYLOAD, _payloadJSONObject
		).put(
			_KEY_TIMESTAMP, _timestamp
		).put(
			_KEY_TYPE, _type
		).put(
			_KEY_UUID, _uuid
		);
	}

	private static final String _KEY_ARCHIVED = "archived";

	private static final String _KEY_DELIVERY_BY = "deliveryBy";

	private static final String _KEY_DELIVERY_REQUIRED = "deliveryRequired";

	private static final String _KEY_DELIVERY_TYPE = "deliveryType";

	private static final String _KEY_PAYLOAD = "payload";

	private static final String _KEY_TIMESTAMP = "timestamp";

	private static final String _KEY_TYPE = "type";

	private static final String _KEY_UUID = "uuid";

	private boolean _archived;
	private long _deliverBy;
	private boolean _deliveryRequired;
	private int _deliveryType;
	private final JSONObject _payloadJSONObject;
	private long _timestamp;
	private final String _type;
	private String _uuid;

}