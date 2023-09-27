/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.fieldset;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Lancha
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.FIELDSET,
	service = {
		DDMFormFieldTemplateContextContributor.class,
		FieldSetDDMFormFieldTemplateContextContributor.class
	}
)
public class FieldSetDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		List<Object> nestedFields =
			(List<Object>)ddmFormFieldRenderingContext.getProperty(
				"nestedFields");

		if (nestedFields == null) {
			nestedFields = new ArrayList<>();
		}

		JSONArray rowsJSONArray = getJSONArray(
			GetterUtil.getString(ddmFormField.getProperty("rows")));

		if (_needsLoadLayout(ddmFormField)) {
			rowsJSONArray = getRowsJSONArray(
				_getDDMStructureLayoutDefinition(
					GetterUtil.getLong(
						ddmFormField.getProperty("ddmStructureLayoutId"))));
		}
		else if (Validator.isNotNull(
					GetterUtil.getString(
						ddmFormField.getProperty("nestedFieldNames")))) {

			rowsJSONArray = getRowsJSONArray(nestedFields);
		}

		return HashMapBuilder.<String, Object>put(
			"collapsible", ddmFormField.getProperty("collapsible")
		).put(
			"dataDefinitionId",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormFieldRenderingContext, "dataDefinitionId")
		).put(
			"ddmStructureId", ddmFormField.getProperty("ddmStructureId")
		).put(
			"ddmStructureLayoutId",
			ddmFormField.getProperty("ddmStructureLayoutId")
		).put(
			"nestedFields", nestedFields
		).put(
			"rows", rowsJSONArray
		).put(
			"upgradedStructure",
			GetterUtil.getBoolean(ddmFormField.getProperty("upgradedStructure"))
		).put(
			"visible",
			ListUtil.isNotEmpty(_getVisibleNestedFields(nestedFields))
		).build();
	}

	protected JSONArray getJSONArray(String rows) {
		try {
			return jsonFactory.createJSONArray(rows);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return jsonFactory.createJSONArray();
	}

	protected JSONArray getRowsJSONArray(List<Object> nestedFields) {
		JSONArray rowsJSONArray = jsonFactory.createJSONArray();

		List<Object> visibleNestedFields = _getVisibleNestedFields(
			nestedFields);

		if (!visibleNestedFields.isEmpty()) {
			rowsJSONArray.put(_createRowJSONObject(visibleNestedFields));
		}

		List<Object> invisibleNestedFields = ListUtil.filter(
			nestedFields, nestedField -> !_isNestedFieldVisible(nestedField));

		if (!invisibleNestedFields.isEmpty()) {
			rowsJSONArray.put(_createRowJSONObject(invisibleNestedFields));
		}

		return rowsJSONArray;
	}

	protected JSONArray getRowsJSONArray(String definition) {
		try {
			JSONObject jsonObject = jsonFactory.createJSONObject(
				StringUtil.replace(definition, "fieldNames", "fields"));

			JSONArray pagesJSONArray = jsonObject.getJSONArray("pages");

			JSONObject pageJSONObject = pagesJSONArray.getJSONObject(0);

			return pageJSONObject.getJSONArray("rows");
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return jsonFactory.createJSONArray();
	}

	@Reference
	protected DDMStructureLayoutLocalService ddmStructureLayoutLocalService;

	@Reference
	protected JSONFactory jsonFactory;

	private JSONObject _createRowJSONObject(List<Object> nestedFields) {
		JSONArray columnsJSONArray = jsonFactory.createJSONArray();

		for (Object nestedField : nestedFields) {
			columnsJSONArray.put(
				JSONUtil.put(
					"fields",
					JSONUtil.put(
						MapUtil.getString(
							(Map<String, ?>)nestedField, "fieldName"))
				).put(
					"size", 12 / nestedFields.size()
				));
		}

		return JSONUtil.put("columns", columnsJSONArray);
	}

	private String _getDDMStructureLayoutDefinition(long structureLayoutId) {
		try {
			DDMStructureLayout ddmStructureLayout =
				ddmStructureLayoutLocalService.getStructureLayout(
					structureLayoutId);

			return ddmStructureLayout.getDefinition();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return StringPool.BLANK;
	}

	private List<Object> _getVisibleNestedFields(List<Object> nestedFields) {
		return ListUtil.filter(nestedFields, this::_isNestedFieldVisible);
	}

	private boolean _isNestedFieldVisible(Object nestedField) {
		return MapUtil.getBoolean(
			(Map<String, Object>)nestedField, "visible", true);
	}

	private boolean _needsLoadLayout(DDMFormField ddmFormField) {
		if (Validator.isNotNull(ddmFormField.getProperty("ddmStructureId")) &&
			Validator.isNotNull(
				ddmFormField.getProperty("ddmStructureLayoutId"))) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FieldSetDDMFormFieldTemplateContextContributor.class);

}