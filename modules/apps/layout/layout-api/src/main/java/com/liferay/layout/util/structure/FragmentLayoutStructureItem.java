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

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class FragmentLayoutStructureItem extends StyledLayoutStructureItem {

	public FragmentLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentLayoutStructureItem)) {
			return false;
		}

		FragmentLayoutStructureItem fragmentLayoutStructureItem =
			(FragmentLayoutStructureItem)object;

		if (!Objects.equals(
				_fragmentEntryLinkId,
				fragmentLayoutStructureItem._fragmentEntryLinkId)) {

			return false;
		}

		return super.equals(object);
	}

	public long getFragmentEntryLinkId() {
		return _fragmentEntryLinkId;
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = super.getItemConfigJSONObject();

		if (_fragmentConfigurationJSONObject != null) {
			for (String key : _fragmentConfigurationJSONObject.keySet()) {
				jsonObject.put(key, _fragmentConfigurationJSONObject.get(key));
			}
		}

		return jsonObject.put(
			"fragmentEntryLinkId", String.valueOf(_fragmentEntryLinkId));
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_FRAGMENT;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public void setFragmentEntryLinkId(long fragmentEntryLinkId) {
		_fragmentEntryLinkId = fragmentEntryLinkId;

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				fragmentEntryLinkId);

		if (fragmentEntryLink != null) {
			try {
				JSONObject editablesJSONObject =
					JSONFactoryUtil.createJSONObject(
						fragmentEntryLink.getEditableValues());

				_fragmentConfigurationJSONObject =
					editablesJSONObject.getJSONObject(
						_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);
			}
			catch (Exception exception) {
				_log.error("Unable to parse editable values", exception);
			}
		}
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		super.updateItemConfig(itemConfigJSONObject);

		if (itemConfigJSONObject.has("fragmentEntryLinkId")) {
			setFragmentEntryLinkId(
				itemConfigJSONObject.getLong("fragmentEntryLinkId"));
		}
	}

	private static final String _KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.freemarker." +
			"FreeMarkerFragmentEntryProcessor";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentLayoutStructureItem.class);

	private JSONObject _fragmentConfigurationJSONObject;
	private long _fragmentEntryLinkId;

}