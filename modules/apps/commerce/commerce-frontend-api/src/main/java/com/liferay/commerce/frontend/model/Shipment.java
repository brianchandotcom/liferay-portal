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

package com.liferay.commerce.frontend.model;

/**
 * @author Alessio Antonio Rendina
 * @author Alec Sloan
 */
public class Shipment {

	public Shipment(
		String accountName, String address, String channelName,
		String createDate, String expectedDeliveryDate, String expectedShipDate,
		long shipmentId, LabelField status, String tracking) {

		_accountName = accountName;
		_address = address;
		_channelName = channelName;
		_createDate = createDate;
		_expectedDeliveryDate = expectedDeliveryDate;
		_expectedShipDate = expectedShipDate;
		_shipmentId = shipmentId;
		_status = status;
		_tracking = tracking;
	}

	public String getAccountName() {
		return _accountName;
	}

	public String getAddress() {
		return _address;
	}

	public String getChannelName() {
		return _channelName;
	}

	public String getCreateDate() {
		return _createDate;
	}

	public String getExpectedDeliveryDate() {
		return _expectedDeliveryDate;
	}

	public String getExpectedShipDate() {
		return _expectedShipDate;
	}

	public long getShipmentId() {
		return _shipmentId;
	}

	public LabelField getStatus() {
		return _status;
	}

	public String getTracking() {
		return _tracking;
	}

	private final String _accountName;
	private final String _address;
	private final String _channelName;
	private final String _createDate;
	private final String _expectedDeliveryDate;
	private final String _expectedShipDate;
	private final long _shipmentId;
	private final LabelField _status;
	private final String _tracking;

}