/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.web.internal.portlet.action;

import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordService;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	property = {
		"javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS,
		"javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS_DISPLAY,
		"mvc.command.name=/dynamic_data_lists/update_record"
	},
	service = MVCResourceCommand.class
)
public class UpdateRecordMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		_ddlRecordService.updateRecord(
			ParamUtil.getLong(resourceRequest, "recordId"),
			ParamUtil.getBoolean(resourceRequest, "majorVersion"),
			ParamUtil.getInteger(resourceRequest, "displayIndex"),
			_updateDDMFormValues(
				_getDDMFormValues(
					ParamUtil.getLong(resourceRequest, "recordId")),
				_jsonFactory.createJSONObject(
					ParamUtil.getString(resourceRequest, "ddmFormValues"))),
			_getServiceContext(resourceRequest));
	}

	private DDMFormValues _getDDMFormValues(long recordId) throws Exception {
		DDLRecord ddlRecord = _ddlRecordService.getRecord(recordId);

		return ddlRecord.getDDMFormValues();
	}

	private ServiceContext _getServiceContext(ResourceRequest resourceRequest)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			resourceRequest);

		serviceContext.setAttribute(
			"workflowAction", WorkflowConstants.ACTION_PUBLISH);

		return serviceContext;
	}

	private Map<String, List<DDMFormFieldValue>> _toDDMFormFieldValues(
		JSONArray jsonArray) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValues =
			new HashMap<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			ddmFormFieldValues.putIfAbsent(
				jsonObject.getString("name"), new ArrayList<>());

			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setInstanceId(jsonObject.getString("instanceId"));
			ddmFormFieldValue.setName(jsonObject.getString("name"));

			JSONObject valueJSONObject = jsonObject.getJSONObject("value");

			if (valueJSONObject != null) {
				LocalizedValue localizedValue = new LocalizedValue();

				Iterator<String> iterator = valueJSONObject.keys();

				while (iterator.hasNext()) {
					String languageId = iterator.next();

					localizedValue.addString(
						LocaleUtil.fromLanguageId(languageId),
						valueJSONObject.getString(languageId));
				}

				ddmFormFieldValue.setValue(localizedValue);
			}
			else {
				ddmFormFieldValue.setValue(
					new UnlocalizedValue(jsonObject.getString("value")));
			}

			List<DDMFormFieldValue> ddmFormFieldValueList =
				ddmFormFieldValues.get(jsonObject.getString("name"));

			ddmFormFieldValueList.add(ddmFormFieldValue);
		}

		return ddmFormFieldValues;
	}

	private void _updateDDMFormFieldValue(
		List<DDMFormFieldValue> ddmFormFieldValues,
		DDMFormFieldValue updatedDDMFormFieldValue) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			if (Objects.equals(
					ddmFormFieldValue.getName(),
					updatedDDMFormFieldValue.getName())) {

				ddmFormFieldValue.setValue(updatedDDMFormFieldValue.getValue());
			}

			if (ListUtil.isNotEmpty(
					ddmFormFieldValue.getNestedDDMFormFieldValues())) {

				_updateDDMFormFieldValue(
					ddmFormFieldValue.getNestedDDMFormFieldValues(),
					updatedDDMFormFieldValue);
			}
		}
	}

	private DDMFormValues _updateDDMFormValues(
		DDMFormValues ddmFormValues, JSONObject jsonObject) {

		Set<Locale> locales = new HashSet<>();

		for (String languageId :
				JSONUtil.toStringArray(
					jsonObject.getJSONArray("availableLanguageIds"))) {

			locales.add(LocaleUtil.fromLanguageId(languageId));
		}

		ddmFormValues.setAvailableLocales(locales);
		ddmFormValues.setDefaultLocale(
			LocaleUtil.fromLanguageId(
				jsonObject.getString("defaultLanguageId")));

		Map<String, List<DDMFormFieldValue>> updatedDDMFormFieldValues =
			_toDDMFormFieldValues(jsonObject.getJSONArray("fieldValues"));

		for (Map.Entry<String, List<DDMFormFieldValue>> entry :
				updatedDDMFormFieldValues.entrySet()) {

			List<DDMFormFieldValue> ddmFormFieldValues = entry.getValue();

			_updateDDMFormFieldValue(
				ddmFormValues.getDDMFormFieldValues(),
				ddmFormFieldValues.get(0));
		}

		return ddmFormValues;
	}

	@Reference
	private DDLRecordService _ddlRecordService;

	@Reference
	private JSONFactory _jsonFactory;

}