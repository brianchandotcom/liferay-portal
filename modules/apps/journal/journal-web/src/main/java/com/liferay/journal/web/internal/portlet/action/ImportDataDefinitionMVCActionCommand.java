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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.upload.UploadPortletRequestImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/import_data_definition"
	},
	service = MVCActionCommand.class
)
public class ImportDataDefinitionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			UploadPortletRequest uploadPortletRequest =
				_getUploadPortletRequest(actionRequest);

			DataDefinition dataDefinition = DataDefinition.toDTO(
				FileUtil.read(uploadPortletRequest.getFile("jsonFile")));

			dataDefinition.setName(
				HashMapBuilder.<String, Object>put(
					String.valueOf(themeDisplay.getSiteDefaultLocale()),
					ParamUtil.getString(actionRequest, "name")
				).build());

			DataDefinitionResource.Builder dataDefinitionResourcedBuilder =
				_dataDefinitionResourceFactory.create();

			DataDefinitionResource dataDefinitionResource =
				dataDefinitionResourcedBuilder.user(
					themeDisplay.getUser()
				).build();

			_uniquifyDataDefinitionFields(dataDefinition);

			dataDefinitionResource.postSiteDataDefinitionByContentType(
				themeDisplay.getScopeGroupId(), "journal", dataDefinition);

			SessionMessages.add(
				actionRequest, "importDataDefinitionSuccessMessage");

			hideDefaultSuccessMessage(actionRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			SessionErrors.add(
				actionRequest, "importDataDefinitionErrorMessage");

			hideDefaultErrorMessage(actionRequest);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	private String _generateFieldName(String fieldName) {
		String newFieldName = null;

		int index = fieldName.length() - 8;

		if ((index >= 0) && Validator.isNumber(fieldName.substring(index))) {
			newFieldName = DDMFormFieldUtil.getDDMFormFieldName(
				fieldName.substring(0, index));
		}
		else {
			newFieldName = DDMFormFieldUtil.getDDMFormFieldName(fieldName);
		}

		while (_fieldNames.contains(newFieldName)) {
			_generateFieldName(fieldName);
		}

		return newFieldName;
	}

	private UploadPortletRequest _getUploadPortletRequest(
		ActionRequest actionRequest) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(actionRequest);

		return new UploadPortletRequestImpl(
			_portal.getUploadServletRequest(
				liferayPortletRequest.getHttpServletRequest()),
			liferayPortletRequest,
			_portal.getPortletNamespace(
				liferayPortletRequest.getPortletName()));
	}

	private void _populateFieldNameBlacklist(DataDefinition dataDefinition) {
		for (DataDefinitionField dataDefinitionField :
				dataDefinition.getDataDefinitionFields()) {

			_fieldNames.add(dataDefinitionField.getName());
		}
	}

	private void _populateFieldNameBlacklist(
		DataDefinitionField dataDefinitionField) {

		for (DataDefinitionField nestedDataDefinitionField :
				dataDefinitionField.getNestedDataDefinitionFields()) {

			_fieldNames.add(nestedDataDefinitionField.getName());
		}
	}

	private void _uniquifyDataDefinitionFields(DataDefinition dataDefinition) {
		_populateFieldNameBlacklist(dataDefinition);

		for (DataDefinitionField dataDefinitionField :
				dataDefinition.getDataDefinitionFields()) {

			String oldFieldName = dataDefinitionField.getName();

			String newFieldName = _generateFieldName(oldFieldName);

			if (Objects.equals(
					dataDefinitionField.getFieldType(), "fieldset")) {

				_uniquifyDataDefinitionFieldset(dataDefinitionField);
			}

			dataDefinitionField.setName((String)newFieldName);

			_fieldNames.add(newFieldName);

			DataLayout dataLayout = dataDefinition.getDefaultDataLayout();

			_updateDataLayout(dataLayout, newFieldName, oldFieldName);
		}
	}

	private void _uniquifyDataDefinitionFieldset(
		DataDefinitionField dataDefinitionField) {

		_populateFieldNameBlacklist(dataDefinitionField);

		for (DataDefinitionField nestedDataDefinitionField :
				dataDefinitionField.getNestedDataDefinitionFields()) {

			if (Objects.equals(
					nestedDataDefinitionField.getFieldType(), "fieldset")) {

				_uniquifyDataDefinitionFieldset(nestedDataDefinitionField);
			}

			String oldFieldName = nestedDataDefinitionField.getName();

			String newFieldName = _generateFieldName(oldFieldName);

			nestedDataDefinitionField.setName((String)newFieldName);

			_fieldNames.add(newFieldName);

			_updateDataLayoutFieldset(
				dataDefinitionField, newFieldName, oldFieldName);
		}
	}

	private void _updateDataLayout(
		DataLayout dataLayout, String newFieldName, String oldFieldName) {

		for (DataLayoutPage dataLayoutPage : dataLayout.getDataLayoutPages()) {
			for (DataLayoutRow dataLayoutRow :
					dataLayoutPage.getDataLayoutRows()) {

				for (DataLayoutColumn dataLayoutColumn :
						dataLayoutRow.getDataLayoutColumns()) {

					String[] dataLayoutColumnFieldNames =
						dataLayoutColumn.getFieldNames();

					for (int i = 0; i < dataLayoutColumnFieldNames.length;
						 i++) {

						if (dataLayoutColumnFieldNames[i].equals(
								oldFieldName)) {

							dataLayoutColumnFieldNames[i] = newFieldName;

							return;
						}
					}
				}
			}
		}
	}

	private void _updateDataLayoutFieldset(
		DataDefinitionField dataDefinitionField, String newFieldName,
		String oldFieldName) {

		Map<String, Object> customProperties =
			dataDefinitionField.getCustomProperties();

		Object[] rows = (Object[])customProperties.get("rows");

		for (Object row : rows) {
			Map<String, Object> rowMap = (Map<String, Object>)row;

			Object[] columns = (Object[])rowMap.get("columns");

			for (Object column : columns) {
				Map<String, Object> columnMap = (Map<String, Object>)column;

				Object[] fields = (Object[])columnMap.get("fields");

				for (int i = 0; i < fields.length; i++) {
					if (Objects.equals(fields[i], oldFieldName)) {
						fields[i] = newFieldName;

						return;
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImportDataDefinitionMVCActionCommand.class);

	@Reference
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	private final Set<String> _fieldNames = new HashSet<>();

	@Reference
	private Portal _portal;

}