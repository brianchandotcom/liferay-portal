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

package com.liferay.portal.kernel.notifications;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof NotificationEvent)) {
			return false;
		}

		NotificationEvent notificationEvent = (NotificationEvent)obj;

		if (Validator.equals(getUuid(), notificationEvent.getUuid())) {
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
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(_KEY_ARCHIVED, _archived);
		jsonObject.put(_KEY_DELIVERY_BY, _deliverBy);
		jsonObject.put(_KEY_DELIVERY_REQUIRED, _deliveryRequired);
		jsonObject.put(_KEY_DELIVERY_TYPE, _deliveryType);
		jsonObject.put(_KEY_PAYLOAD, _payloadJSONObject);
		jsonObject.put(_KEY_TIMESTAMP, _timestamp);
		jsonObject.put(_KEY_TYPE, _type);
		jsonObject.put(_KEY_UUID, _uuid);

		return jsonObject;
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