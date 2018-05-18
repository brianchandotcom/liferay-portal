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

package com.liferay.commerce.discount.rule.added.all.internal;

import com.liferay.commerce.discount.model.CommerceDiscountRuleConstants;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.discount.rule.type.key=" + CommerceDiscountRuleConstants.TYPE_ADDED_ALL,
		"commerce.discount.rule.type.order:Integer=50"
	},
	service = CommerceDiscountRuleType.class
)
public class AddedAllCommerceDiscountRuleTypeImpl
	implements CommerceDiscountRuleType {

	@Override
	public boolean evaluate(CommerceOrder commerceOrder) {
		return false;
	}

	@Override
	public String getKey() {
		return CommerceDiscountRuleConstants.TYPE_ADDED_ALL;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "has-all-of-these-products");
	}

}