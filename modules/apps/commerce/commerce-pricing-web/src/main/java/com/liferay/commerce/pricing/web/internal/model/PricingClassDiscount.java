/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.model;

import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Riccardo Alberti
 */
public class PricingClassDiscount {

	public PricingClassDiscount(
		long commerceDiscountId, String name, String target, String type,
		LabelField status) {

		_commerceDiscountId = commerceDiscountId;
		_name = name;
		_target = target;
		_type = type;
		_status = status;
	}

	public long getCommerceDiscountId() {
		return _commerceDiscountId;
	}

	public String getName() {
		return _name;
	}

	public LabelField getStatus() {
		return _status;
	}

	public String getTarget() {
		return _target;
	}

	public String getType() {
		return _type;
	}

	private final long _commerceDiscountId;
	private final String _name;
	private final LabelField _status;
	private final String _target;
	private final String _type;

}