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

import com.liferay.frontend.taglib.clay.internal.ClayTagDataSourceProvider;
import com.liferay.frontend.taglib.clay.internal.servlet.taglib.display.context.TableDefaults;
import com.liferay.frontend.taglib.clay.servlet.taglib.data.ClayTagDataSource;
import com.liferay.frontend.taglib.clay.servlet.taglib.model.table.Schema;
import com.liferay.frontend.taglib.clay.servlet.taglib.model.table.Size;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
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
		Map<String, ?> schemaMap = null;

		if (schema != null) {
			schemaMap = schema.toMap();
		}

		putValue("schema", schemaMap);
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

	private void _populateContext(ClayTagDataSource<T> clayTagDataSource) {
		Map<String, Object> context = getContext();

		if (context.get("items") == null) {
			setItems(clayTagDataSource.getItems(request));
		}
	}

}