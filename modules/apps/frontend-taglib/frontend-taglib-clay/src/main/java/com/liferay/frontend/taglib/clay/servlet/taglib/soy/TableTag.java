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

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.TableDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.table.Schema;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.table.Size;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Iván Zaera Avellón
 */
public class TableTag extends BaseClayTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayTable");
		setHydrate(true);
		setModuleBaseName("table");

		if (_tableDisplayContext != null) {
			_populateContext(_tableDisplayContext);
		}

		return super.doStartTag();
	}

	public TableDisplayContext getDisplayContext() {
		return _tableDisplayContext;
	}

	public void setActionsMenuVariant(String actionsMenuVariant) {
		putValue("actionsMenuVariant", actionsMenuVariant);
	}

	public void setDisplayContext(
		TableDisplayContext tableDisplayContext) {

		_tableDisplayContext = tableDisplayContext;
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

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_tableDisplayContext = null;
	}

	private void _populateContext(TableDisplayContext tableDisplayContext) {
		Map<String, Object> context = getContext();

		if (context.get("actionsMenuVariant") == null) {
			setActionsMenuVariant(tableDisplayContext.getActionsMenuVariant());
		}

		if (context.get("dependencies") == null) {
			Collection<String> dependencies =
				tableDisplayContext.getDependencies();

			if (dependencies != null) {
				setDependencies(new HashSet<>(dependencies));
			}
		}

		if (context.get("elementClasses") == null) {
			setElementClasses(tableDisplayContext.getElementClasses());
		}

		if (context.get("id") == null) {
			setId(tableDisplayContext.getId());
		}

		if (context.get("schema") == null) {
			setSchema(tableDisplayContext.getSchema());
		}

		if (context.get("selectable") == null) {
			setSelectable(tableDisplayContext.isSelectable());
		}

		if (context.get("showActionsMenu") == null) {
			setShowActionsMenu(tableDisplayContext.isShowActionsMenu());
		}

		if (context.get("items") == null) {
			setItems(tableDisplayContext.getItems());
		}

		if (context.get("size") == null) {
			setSize(tableDisplayContext.getSize());
		}

		if (context.get("spritemap") == null) {
			setSpritemap(tableDisplayContext.getSpritemap());
		}

		if (context.get("tableClasses") == null) {
			setTableClasses(tableDisplayContext.getTableClasses());
		}

		if (context.get("useDefaultClasses") == null) {
			setUseDefaultClasses(tableDisplayContext.isUseDefaultClasses());
		}

		if (context.get("wrapTable") == null) {
			setWrapTable(tableDisplayContext.isWrapTable());
		}
	}

	private TableDisplayContext _tableDisplayContext;

}