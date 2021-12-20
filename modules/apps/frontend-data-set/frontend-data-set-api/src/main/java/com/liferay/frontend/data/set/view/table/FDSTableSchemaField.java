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

package com.liferay.frontend.data.set.view.table;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Marco Leo
 */
public class FDSTableSchemaField {

	public String getActionId() {
		return _actionId;
	}

	public String getContentRenderer() {
		return _contentRenderer;
	}

	public String getContentRendererModuleURL() {
		return _contentRendererModuleURL;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public String getLabel() {
		return _label;
	}

	public SortingOrder getSortingOrder() {
		return _sortingOrder;
	}

	public boolean isExpand() {
		return _expand;
	}

	public boolean isSortable() {
		return _sortable;
	}

	public void setActionId(String actionId) {
		_actionId = actionId;
	}

	public void setContentRenderer(String contentRenderer) {
		_contentRenderer = contentRenderer;
	}

	public void setContentRendererModuleURL(String contentRendererModuleURL) {
		_contentRendererModuleURL = contentRendererModuleURL;
	}

	public void setExpand(boolean expand) {
		_expand = expand;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setSortable(boolean sortable) {
		_sortable = sortable;
	}

	public void setSortingOrder(SortingOrder sortingOrder) {
		_sortingOrder = sortingOrder;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"actionId", getActionId()
		).put(
			"contentRenderer", getContentRenderer()
		).put(
			"contentRendererModuleURL", getContentRendererModuleURL()
		).put(
			"expand", isExpand()
		).put(
			"fieldName",
			() -> {
				String fieldName = getFieldName();

				if (fieldName.contains(StringPool.PERIOD)) {
					return StringUtil.split(fieldName, StringPool.PERIOD);
				}

				return fieldName;
			}
		).put(
			"sortable", isSortable()
		).put(
			"sortingOrder",
			() -> {
				FDSTableSchemaField.SortingOrder sortingOrder =
					getSortingOrder();

				if (sortingOrder != null) {
					return StringUtil.toLowerCase(sortingOrder.toString());
				}

				return null;
			}
		);
	}

	public enum SortingOrder {

		ASC, DESC

	}

	private String _actionId;
	private String _contentRenderer;
	private String _contentRendererModuleURL;
	private boolean _expand;
	private String _fieldName;
	private String _label;
	private boolean _sortable;
	private SortingOrder _sortingOrder;

}