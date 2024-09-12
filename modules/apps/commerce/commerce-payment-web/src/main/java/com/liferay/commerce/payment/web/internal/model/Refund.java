/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.web.internal.model;

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Crescenzo Rega
 */
public class Refund {

	public Refund(
		String amount, String createDateString, String externalReferenceCode,
		long id, long relatedItemId, String relatedItemName,
		LabelField status) {

		_amount = amount;
		_createDateString = createDateString;
		_externalReferenceCode = externalReferenceCode;
		_id = id;
		_relatedItemId = relatedItemId;
		_relatedItemName = relatedItemName;
		_status = status;
	}

	public String getAmount() {
		return _amount;
	}

	public String getCreateDateString() {
		return _createDateString;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public long getId() {
		return _id;
	}

	public long getRelatedItemId() {
		return _relatedItemId;
	}

	public String getRelatedItemName() {
		return _relatedItemName;
	}

	public LabelField getStatus() {
		return _status;
	}

	private final String _amount;
	private final String _createDateString;
	private final String _externalReferenceCode;
	private final long _id;
	private final long _relatedItemId;
	private final String _relatedItemName;
	private final LabelField _status;

}