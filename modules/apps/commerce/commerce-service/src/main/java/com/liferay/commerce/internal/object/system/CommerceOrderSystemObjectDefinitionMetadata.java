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

package com.liferay.commerce.internal.object.system;

import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false, immediate = true,
	service = SystemObjectDefinitionMetadata.class
)
public class CommerceOrderSystemObjectDefinitionMetadata
	extends BaseSystemObjectDefinitionMetadata {

	@Override
	public Map<Locale, String> getLabelMap() {
		return createLabelMap("commerce-order");
	}

	@Override
	public String getName() {
		return "CommerceOrder";
	}

	@Override
	public String getScope() {
		return "company";
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return Arrays.asList(
			createObjectField("order-status", "orderStatus", true, "Integer"),
			createObjectField(
				"shipping-amount", "shippingAmount", true, "BigDecimal"));
	}

	@Override
	public Map<Locale, String> getPluralLabelMap() {
		return createLabelMap("commerce-orders");
	}

	@Override
	public int getVersion() {
		return 1;
	}

}