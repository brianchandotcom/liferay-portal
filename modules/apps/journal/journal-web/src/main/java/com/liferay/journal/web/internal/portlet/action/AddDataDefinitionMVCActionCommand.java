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

import com.liferay.data.engine.rest.client.serdes.v2_0.DataDefinitionSerDes;
import com.liferay.data.engine.rest.client.serdes.v2_0.DataLayoutSerDes;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.data.engine.rest.dto.v2_0.DataRule;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/add_data_definition"
	},
	service = MVCActionCommand.class
)
public class AddDataDefinitionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		String dataDefinition = ParamUtil.getString(
			actionRequest, "dataDefinition");
		String dataLayout = ParamUtil.getString(actionRequest, "dataLayout");

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).user(
				themeDisplay.getUser()
			).build();

		dataDefinitionResource.postSiteDataDefinitionByContentType(
			groupId, "journal",
			_toDataDefinition(
				DataDefinitionSerDes.toDTO(dataDefinition),
				DataLayoutSerDes.toDTO(dataLayout)));
	}

	private DataLayoutRow _dataLayoutRow(
		com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutRow
			dataLayoutRow) {

		return new DataLayoutRow() {
			{
				dataLayoutColumns = _toDataLayoutColumns(
					dataLayoutRow.getDataLayoutColumns());
			}
		};
	}

	private DataDefinition _toDataDefinition(
		com.liferay.data.engine.rest.client.dto.v2_0.DataDefinition
			dataDefinition,
		com.liferay.data.engine.rest.client.dto.v2_0.DataLayout dataLayout) {

		return new DataDefinition() {
			{
				availableLanguageIds = dataDefinition.getAvailableLanguageIds();
				dataDefinitionFields = _toDataDefinitionFields(
					dataDefinition.getDataDefinitionFields());
				dataDefinitionKey = dataDefinition.getDataDefinitionKey();
				dateCreated = dataDefinition.getDateCreated();
				dateModified = dataDefinition.getDateModified();
				defaultDataLayout = _toDataLayout(dataLayout);
				defaultLanguageId = dataDefinition.getDefaultLanguageId();
				description = dataDefinition.getDescription();
				id = dataDefinition.getId();
				name = dataDefinition.getName();
				siteId = dataDefinition.getSiteId();
				storageType = dataDefinition.getStorageType();
				userId = dataDefinition.getUserId();
			}
		};
	}

	private DataDefinitionField _toDataDefinitionField(
		com.liferay.data.engine.rest.client.dto.v2_0.DataDefinitionField
			dataDefinitionField) {

		return new DataDefinitionField() {
			{
				customProperties = dataDefinitionField.getCustomProperties();
				defaultValue = dataDefinitionField.getDefaultValue();
				fieldType = dataDefinitionField.getFieldType();
				indexable = dataDefinitionField.getIndexable();
				indexType = _toIndexType(dataDefinitionField.getIndexType());
				label = dataDefinitionField.getLabel();
				localizable = dataDefinitionField.getLocalizable();
				name = dataDefinitionField.getName();
				nestedDataDefinitionFields = _toDataDefinitionFields(
					dataDefinitionField.getNestedDataDefinitionFields());
				readOnly = dataDefinitionField.getReadOnly();
				repeatable = dataDefinitionField.getRepeatable();
				required = dataDefinitionField.getRequired();
				showLabel = dataDefinitionField.getShowLabel();
				tip = dataDefinitionField.getTip();
			}
		};
	}

	private DataDefinitionField[] _toDataDefinitionFields(
		com.liferay.data.engine.rest.client.dto.v2_0.DataDefinitionField[]
			dataDefinitionFields) {

		DataDefinitionField[] newDataDefinitionFields =
			new DataDefinitionField[dataDefinitionFields.length];

		for (int i = 0; i < dataDefinitionFields.length; i++) {
			newDataDefinitionFields[i] = _toDataDefinitionField(
				dataDefinitionFields[i]);
		}

		return newDataDefinitionFields;
	}

	private DataLayout _toDataLayout(
		com.liferay.data.engine.rest.client.dto.v2_0.DataLayout dataLayout) {

		return new DataLayout() {
			{
				dataLayoutPages = _toDataLayoutPages(
					dataLayout.getDataLayoutPages());
				dataRules = _toDataRules(dataLayout.getDataRules());
				paginationMode = dataLayout.getPaginationMode();
			}
		};
	}

	private DataLayoutColumn _toDataLayoutColumn(
		com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutColumn
			dataLayoutColumn) {

		return new DataLayoutColumn() {
			{
				columnSize = dataLayoutColumn.getColumnSize();
				fieldNames = dataLayoutColumn.getFieldNames();
			}
		};
	}

	private DataLayoutColumn[] _toDataLayoutColumns(
		com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutColumn[]
			dataLayoutColumns) {

		DataLayoutColumn[] newDataLayoutColumn =
			new DataLayoutColumn[dataLayoutColumns.length];

		for (int i = 0; i < dataLayoutColumns.length; i++) {
			newDataLayoutColumn[i] = _toDataLayoutColumn(dataLayoutColumns[i]);
		}

		return newDataLayoutColumn;
	}

	private DataLayoutPage _toDataLayoutPage(
		com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutPage
			dataLayoutPage) {

		return new DataLayoutPage() {
			{
				dataLayoutRows = _toDataLayoutRows(
					dataLayoutPage.getDataLayoutRows());
				description = dataLayoutPage.getDescription();
				title = dataLayoutPage.getTitle();
			}
		};
	}

	private DataLayoutPage[] _toDataLayoutPages(
		com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutPage[]
			dataLayoutPages) {

		DataLayoutPage[] newDataLayoutPages =
			new DataLayoutPage[dataLayoutPages.length];

		for (int i = 0; i < dataLayoutPages.length; i++) {
			newDataLayoutPages[i] = _toDataLayoutPage(dataLayoutPages[i]);
		}

		return newDataLayoutPages;
	}

	private DataLayoutRow[] _toDataLayoutRows(
		com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutRow[]
			dataLayoutRows) {

		DataLayoutRow[] newDataLayoutRow =
			new DataLayoutRow[dataLayoutRows.length];

		for (int i = 0; i < dataLayoutRows.length; i++) {
			newDataLayoutRow[i] = _dataLayoutRow(dataLayoutRows[i]);
		}

		return newDataLayoutRow;
	}

	private DataRule _toDataRule(
		com.liferay.data.engine.rest.client.dto.v2_0.DataRule dataRule) {

		return new DataRule() {
			{
				actions = dataRule.getActions();
				conditions = dataRule.getConditions();
				logicalOperator = dataRule.getLogicalOperator();
			}
		};
	}

	private DataRule[] _toDataRules(
		com.liferay.data.engine.rest.client.dto.v2_0.DataRule[] dataRules) {

		if (ArrayUtil.isEmpty(dataRules)) {
			return new DataRule[0];
		}

		DataRule[] newDataRules = new DataRule[dataRules.length];

		for (int i = 0; i < dataRules.length; i++) {
			newDataRules[i] = _toDataRule(dataRules[i]);
		}

		return newDataRules;
	}

	private DataDefinitionField.IndexType _toIndexType(
		com.liferay.data.engine.rest.client.dto.v2_0.DataDefinitionField.
			IndexType indexType) {

		return DataDefinitionField.IndexType.create(indexType.getValue());
	}

	@Reference
	private Portal _portal;

}