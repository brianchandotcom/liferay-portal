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

package com.liferay.layout.util.structure;

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class FormStyledLayoutStructureItem extends StyledLayoutStructureItem {

	public static final int DISPLAY_PAGE_ITEM_TYPE = 1;

	public static final int OTHER_ITEM_TYPE = 2;

	public FormStyledLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormStyledLayoutStructureItem)) {
			return false;
		}

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)object;

		if (!Objects.equals(
				_classNameId, formStyledLayoutStructureItem._classNameId) ||
			!Objects.equals(
				_classTypeId, formStyledLayoutStructureItem._classTypeId) ||
			!Objects.equals(
				_formConfig, formStyledLayoutStructureItem._formConfig)) {

			return false;
		}

		return super.equals(object);
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public long getClassTypeId() {
		return _classTypeId;
	}

	public int getFormConfig() {
		return _formConfig;
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = super.getItemConfigJSONObject();

		return jsonObject.put(
			"classNameId", _classNameId
		).put(
			"classTypeId", _classTypeId
		).put(
			"formConfig", _formConfig
		).put(
			"indexed", _indexed
		);
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_FORM;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public boolean isIndexed() {
		return _indexed;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setClassTypeId(long classTypeId) {
		_classTypeId = classTypeId;
	}

	public void setFormConfig(int formConfig) {
		_formConfig = formConfig;
	}

	public void setIndexed(boolean indexed) {
		_indexed = indexed;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		super.updateItemConfig(itemConfigJSONObject);

		if (itemConfigJSONObject.has("classNameId")) {
			setClassNameId(itemConfigJSONObject.getLong("classNameId"));
		}

		if (itemConfigJSONObject.has("classTypeId")) {
			setClassTypeId(itemConfigJSONObject.getLong("classTypeId"));
		}

		if (itemConfigJSONObject.has("formConfig")) {
			setFormConfig(itemConfigJSONObject.getInt("formConfig"));
		}

		if (itemConfigJSONObject.has("indexed")) {
			setIndexed(itemConfigJSONObject.getBoolean("indexed"));
		}
	}

	private long _classNameId;
	private long _classTypeId;
	private int _formConfig = DISPLAY_PAGE_ITEM_TYPE;
	private boolean _indexed = true;

}