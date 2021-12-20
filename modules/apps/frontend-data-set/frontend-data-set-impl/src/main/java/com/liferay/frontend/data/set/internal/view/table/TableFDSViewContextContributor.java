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

package com.liferay.frontend.data.set.internal.view.table;

import com.liferay.frontend.data.set.constants.FDSConstants;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.FDSViewContextContributor;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "frontend.data.set.view.name=" + FDSConstants.TABLE,
	service = FDSViewContextContributor.class
)
public class TableFDSViewContextContributor
	implements FDSViewContextContributor {

	@Override
	public Map<String, Object> getFDSViewContext(
		FDSView fdsView, Locale locale) {

		if (fdsView instanceof BaseTableFDSView) {
			return _serialize((BaseTableFDSView)fdsView, locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseTableFDSView baseTableFDSView, Locale locale) {

		JSONArray fieldsJSONArray = _jsonFactory.createJSONArray();

		FDSTableSchema fdsTableSchema = baseTableFDSView.getFDSTableSchema(
			locale);

		Map<String, FDSTableSchemaField> fieldsMap =
			fdsTableSchema.getFDSTableSchemaFieldsMap();

		ResourceBundle resourceBundle = baseTableFDSView.getResourceBundle(
			locale);

		for (FDSTableSchemaField fdsTableSchemaField : fieldsMap.values()) {
			String label = LanguageUtil.get(
				resourceBundle, fdsTableSchemaField.getLabel());

			if (Validator.isNull(label)) {
				label = StringPool.BLANK;
			}

			JSONObject fdsTableSchemaFieldJSONObject =
				fdsTableSchemaField.toJSONObject();

			fieldsJSONArray.put(
				fdsTableSchemaFieldJSONObject.put("label", label));
		}

		return HashMapBuilder.<String, Object>put(
			"schema", JSONUtil.put("fields", fieldsJSONArray)
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}