/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.subscription.web.internal.model;

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class Payment {

	public Payment(
		LabelField type, String date, long transactionId, String amount) {

		_type = type;
		_date = date;
		_transactionId = transactionId;
		_amount = amount;
	}

	public String getAmount() {
		return _amount;
	}

	public String getDate() {
		return _date;
	}

	public long getTransactionId() {
		return _transactionId;
	}

	public LabelField getType() {
		return _type;
	}

	private final String _amount;
	private final String _date;
	private final long _transactionId;
	private final LabelField _type;

}