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

package com.liferay.commerce.subscription.web.internal.model;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class Shipment {

	public Shipment(
		String createDateString, Link shipmentId, Label status, Link orderId,
		String receiver, Link tracking) {

		_createDateString = createDateString;
		_shipmentId = shipmentId;
		_status = status;
		_orderId = orderId;
		_receiver = receiver;
		_tracking = tracking;
	}

	public String getCreateDateString() {
		return _createDateString;
	}

	public Link getOrderId() {
		return _orderId;
	}

	public String getReceiver() {
		return _receiver;
	}

	public Link getShipmentId() {
		return _shipmentId;
	}

	public Label getStatus() {
		return _status;
	}

	public Link getTracking() {
		return _tracking;
	}

	private final String _createDateString;
	private final Link _orderId;
	private final String _receiver;
	private final Link _shipmentId;
	private final Label _status;
	private final Link _tracking;

}