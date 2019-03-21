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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.internal.ClayTableTagSchemaContributorsProvider;
import com.liferay.frontend.taglib.clay.internal.ClayTagDataSourceProvider;
import com.liferay.frontend.taglib.clay.internal.servlet.taglib.display.context.TableDefaults;
import com.liferay.frontend.taglib.clay.servlet.taglib.contributor.ClayTableTagSchemaContributor;
import com.liferay.frontend.taglib.clay.servlet.taglib.data.ClayTagDataSource;
import com.liferay.frontend.taglib.clay.servlet.taglib.model.table.Schema;
import com.liferay.frontend.taglib.clay.servlet.taglib.model.table.Size;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Iván Zaera Avellón
 */
public class TableTag<T> extends BaseClayTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayTable");
		setHydrate(true);
		setModuleBaseName("table");

		int returnValue = super.doStartTag();

		ClayTagDataSource<T> clayTagDataSource = getClayTagDataSource();

		if (clayTagDataSource != null) {
			_populateContext(clayTagDataSource);
		}

		List<ClayTableTagSchemaContributor> clayTableTagSchemaContributors =
			getTableTagSchemaContributors();

		if (clayTableTagSchemaContributors != null) {
			_populateSchema(clayTableTagSchemaContributors);
		}

		putValue("schema", _schema.toMap());

		Map<String, Object> context = getContext();

		boolean selectable = GetterUtil.getBoolean(context.get("selectable"));

		boolean showCheckbox = GetterUtil.getBoolean(
			context.get("showCheckbox"),
			TableDefaults.isShowCheckbox(selectable));

		setShowCheckbox(showCheckbox);

		return returnValue;
	}

	public void setActionsMenuVariant(String actionsMenuVariant) {
		putValue("actionsMenuVariant", actionsMenuVariant);
	}

	public void setDataSourceKey(String dataSourceKey) {
		putValue("dataSourceKey", dataSourceKey);
	}

	public void setItems(Collection<?> items) {
		putValue("items", items);
	}

	public void setSchema(Schema schema) {
		_schema = schema;
	}

	public void setSelectable(Boolean selectable) {
		putValue("selectable", selectable);
	}

	public void setShowActionsMenu(Boolean showActionsMenu) {
		putValue("showActionsMenu", showActionsMenu);
	}

	public void setShowCheckbox(Boolean showCheckbox) {
		putValue("showCheckbox", showCheckbox);
	}

	public void setSize(Size size) {
		String sizeValue = null;

		if (size != null) {
			sizeValue = size.getValue();
		}

		putValue("size", sizeValue);
	}

	public void setTableClasses(String tableClasses) {
		putValue("tableClasses", tableClasses);
	}

	public void setTableSchemaContributorKey(String tableSchemaContributorKey) {
		putValue("tableSchemaContributorKey", tableSchemaContributorKey);
	}

	public void setUseDefaultClasses(Boolean useDefaultClasses) {
		putValue("useDefaultClasses", useDefaultClasses);
	}

	public void setWrapTable(Boolean wrapTable) {
		putValue("wrapTable", wrapTable);
	}

	protected ClayTagDataSource<T> getClayTagDataSource() {
		Map<String, Object> context = getContext();

		String dataSourceKey = (String)context.get("dataSourceKey");

		if (Validator.isNull(dataSourceKey)) {
			return null;
		}

		return (ClayTagDataSource<T>)
			ClayTagDataSourceProvider.getClayTagDataSource(dataSourceKey);
	}

	protected List<ClayTableTagSchemaContributor>
		getTableTagSchemaContributors() {

		Map<String, Object> context = getContext();

		String tableSchemaContributorKey = GetterUtil.getString(
			context.get("tableSchemaContributorKey"));

		if (Validator.isNull(tableSchemaContributorKey)) {
			return null;
		}

		return ClayTableTagSchemaContributorsProvider.
			getClayTableTagSchemaContributors(tableSchemaContributorKey);
	}

	private void _populateContext(ClayTagDataSource<T> clayTagDataSource) {
		Map<String, Object> context = getContext();

		if (context.get("items") == null) {
			setItems(clayTagDataSource.getItems(request));
		}
	}

	private void _populateSchema(
		List<ClayTableTagSchemaContributor> clayTableTagSchemaContributors) {

		for (ClayTableTagSchemaContributor clayTableTagSchemaContributor :
				clayTableTagSchemaContributors) {

			clayTableTagSchemaContributor.populate(_schema);
		}
	}

	private Schema _schema = new Schema();

}