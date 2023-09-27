/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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