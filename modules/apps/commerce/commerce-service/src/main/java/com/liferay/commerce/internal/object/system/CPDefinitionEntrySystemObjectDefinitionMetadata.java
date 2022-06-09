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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author José Abelenda
 */
@Component(enabled = true, service = SystemObjectDefinitionMetadata.class)
public class CPDefinitionEntrySystemObjectDefinitionMetadata
	extends BaseSystemObjectDefinitionMetadata {

	@Override
	public Map<Locale, String> getLabelMap() {
		return createLabelMap("cp-definition");
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinition.class;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return Arrays.asList(
			createObjectField(
				"Text", "String", "description", "description", false),
			createObjectField(
				"Text", "String", "external-reference-code",
				"externalReferenceCode", false),
			createObjectField("Text", "String", "name", "name", false),
			createObjectField(
				"Text", "CPDefinitionId", "String", "product-id", "productId",
				false),
			createObjectField(
				"Text", "String", "short-description", "shortDescription",
				false),
			createObjectField("Text", "String", "sku", "skuFormatted", false),
			createObjectField(
				"Text", "String", "thumbnail", "thumbnail", false),
			createObjectField("Text", "String", "uuid", "uuid", false));
	}

	@Override
	public Map<Locale, String> getPluralLabelMap() {
		return createLabelMap("cp-definitions");
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return CPDefinitionTable.INSTANCE.CPDefinitionId;
	}

	@Override
	public String getRESTContextPath() {
		return "headless-commerce-admin-catalog/v1.0/products";
	}

	@Override
	public String getScope() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public Table getTable() {
		return CPDefinitionTable.INSTANCE;
	}

	@Override
	public int getVersion() {
		return 1;
	}

}