/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.language.Language;
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
			String label = fdsTableSchemaField.getLabel();

			if (fdsTableSchemaField.isLocalizeLabel()) {
				label = _language.get(
					resourceBundle, fdsTableSchemaField.getLabel());
			}

			if (Validator.isNull(label)) {
				label = StringPool.BLANK;
			}

			JSONObject fdsTableSchemaFieldJSONObject =
				fdsTableSchemaField.toJSONObject();

			fieldsJSONArray.put(
				fdsTableSchemaFieldJSONObject.put("label", label));
		}

		return HashMapBuilder.<String, Object>put(
			"quickActionsEnabled", baseTableFDSView.isQuickActionsEnabled()
		).put(
			"schema", JSONUtil.put("fields", fieldsJSONArray)
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}