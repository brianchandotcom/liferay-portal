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

package com.liferay.commerce.discount.internal.rule.type;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.discount.constants.CommerceDiscountRuleConstants;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"commerce.discount.rule.type.key=" + CommerceDiscountRuleConstants.TYPE_ADDED_ALL,
		"commerce.discount.rule.type.order:Integer=50"
	},
	service = CommerceDiscountRuleType.class
)
public class AddedAllCommerceDiscountRuleTypeImpl
	implements CommerceDiscountRuleType {

	@Override
	public boolean evaluate(
			CommerceDiscountRule commerceDiscountRule,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

		if (commerceOrder == null) {
			return false;
		}

		long[] cpDefinitionIds = StringUtil.split(
			commerceDiscountRule.getSettingsProperty(
				commerceDiscountRule.getType()),
			0L);

		return ArrayUtil.containsAll(
			TransformUtil.transformToLongArray(
				commerceOrder.getCommerceOrderItems(),
				CommerceOrderItem::getCPDefinitionId),
			cpDefinitionIds);
	}

	@Override
	public String getKey() {
		return CommerceDiscountRuleConstants.TYPE_ADDED_ALL;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(resourceBundle, "has-all-of-these-products");
	}

	@Reference
	private Language _language;

}