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
 */
public class SubscriptionEntry {

	public SubscriptionEntry(
		long subscriptionId, Link orderId, Link accountEntryId,
		Label subscriptionStatus, String accountEntryName) {

		_subscriptionId = subscriptionId;
		_orderId = orderId;
		_accountEntryId = accountEntryId;
		_subscriptionStatus = subscriptionStatus;
		_accountEntryName = accountEntryName;
	}

	public Link getAccountEntryId() {
		return _accountEntryId;
	}

	public String getAccountEntryName() {
		return _accountEntryName;
	}

	public Link getOrderId() {
		return _orderId;
	}

	public long getSubscriptionId() {
		return _subscriptionId;
	}

	public Label getSubscriptionStatus() {
		return _subscriptionStatus;
	}

	private final Link _accountEntryId;
	private final String _accountEntryName;
	private final Link _orderId;
	private final long _subscriptionId;
	private final Label _subscriptionStatus;

}