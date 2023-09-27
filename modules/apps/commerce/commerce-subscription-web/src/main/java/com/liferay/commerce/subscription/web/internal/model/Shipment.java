/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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