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

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.CommercePricingClassTable;
import com.liferay.commerce.pricing.service.CommercePricingClassLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gleice Lisbino
 */
@Component(enabled = true, service = SystemObjectDefinitionMetadata.class)
public class CommercePricingClassSystemObjectDefinitionMetadata
	extends BaseSystemObjectDefinitionMetadata {

	@Override
	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException {

		return _commercePricingClassLocalService.deleteCommercePricingClass(
			(CommercePricingClass)baseModel);
	}

	@Override
	public String getJaxRsApplicationName() {
		return "Liferay.Headless.Commerce.Admin.Catalog";
	}

	@Override
	public Map<Locale, String> getLabelMap() {
		return createLabelMap("commerce-product-group");
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePricingClass.class;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return Arrays.asList(
			createObjectField(
				"Text", "String", "description", "description", false, true),
			createObjectField(
				"Integer", "Integer", "number-of-products", "productsCount",
				false, true),
			createObjectField("Text", "String", "title", "title", false, true));
	}

	@Override
	public Map<Locale, String> getPluralLabelMap() {
		return createLabelMap("commerce-product-groups");
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return CommercePricingClassTable.INSTANCE.commercePricingClassId;
	}

	@Override
	public String getRESTContextPath() {
		return "headless-commerce-admin-catalog/v1.0/product-groups";
	}

	@Override
	public String getScope() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public Table getTable() {
		return CommercePricingClassTable.INSTANCE;
	}

	@Override
	public int getVersion() {
		return 1;
	}

	@Reference
	private CommercePricingClassLocalService _commercePricingClassLocalService;

}