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
				_classTypeId, formStyledLayoutStructureItem._classTypeId)) {

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

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = super.getItemConfigJSONObject();

		return jsonObject.put(
			"classNameId", _classNameId
		).put(
			"classTypeId", _classTypeId
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

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setClassTypeId(long classTypeId) {
		_classTypeId = classTypeId;
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
	}

	private long _classNameId;
	private long _classTypeId;

}