/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class FragmentStyledLayoutStructureItem
	extends StyledLayoutStructureItem {

	public FragmentStyledLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentStyledLayoutStructureItem)) {
			return false;
		}

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)object;

		if (!Objects.equals(
				_fragmentEntryLinkId,
				fragmentStyledLayoutStructureItem._fragmentEntryLinkId)) {

			return false;
		}

		return super.equals(object);
	}

	public String getFragmentEntryLinkCssClass(
		FragmentEntryLink fragmentEntryLink) {

		return _normalizeCssClass(
			LAYOUT_STRUCTURE_ITEM_CSS_CLASS_PREFIX +
				_getFragmentEntryLinkIdentifier(fragmentEntryLink));
	}

	public long getFragmentEntryLinkId() {
		return _fragmentEntryLinkId;
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = super.getItemConfigJSONObject();

		JSONObject stylesJSONObject = jsonObject.getJSONObject("styles");

		if (stylesJSONObject == null) {
			stylesJSONObject = JSONFactoryUtil.createJSONObject();
		}

		return jsonObject.put(
			"fragmentEntryLinkId", String.valueOf(_fragmentEntryLinkId)
		).put(
			"indexed",
			() -> {
				if (_indexed) {
					return null;
				}

				return false;
			}
		).put(
			"styles", stylesJSONObject
		);
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_FRAGMENT;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public boolean isIndexed() {
		return _indexed;
	}

	public void setFragmentEntryLinkId(long fragmentEntryLinkId) {
		_fragmentEntryLinkId = fragmentEntryLinkId;
	}

	public void setIndexed(boolean indexed) {
		_indexed = indexed;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		super.updateItemConfig(itemConfigJSONObject);

		if (itemConfigJSONObject.has("indexed")) {
			setIndexed(itemConfigJSONObject.getBoolean("indexed"));
		}

		if (itemConfigJSONObject.has("fragmentEntryLinkId")) {
			setFragmentEntryLinkId(
				itemConfigJSONObject.getLong("fragmentEntryLinkId"));
		}
	}

	private JSONObject _createJSONObject(String value) {
		try {
			return JSONFactoryUtil.createJSONObject(value);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	private String _getFragmentEntryLinkIdentifier(
		FragmentEntryLink fragmentEntryLink) {

		String rendererKey = fragmentEntryLink.getRendererKey();

		if (Validator.isNotNull(rendererKey)) {
			return rendererKey;
		}

		JSONObject jsonObject = _createJSONObject(
			fragmentEntryLink.getEditableValues());

		String portletId = jsonObject.getString("portletId");

		if (Validator.isNotNull(portletId)) {
			return PortletIdCodec.decodePortletName(portletId);
		}

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());

		if (fragmentEntry != null) {
			return fragmentEntry.getFragmentEntryKey();
		}

		return StringPool.BLANK;
	}

	private String _normalizeCssClass(String cssClass) {
		cssClass = StringUtil.toLowerCase(cssClass);

		return cssClass.replaceAll("[^A-Za-z0-9-]", StringPool.DASH);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentStyledLayoutStructureItem.class);

	private long _fragmentEntryLinkId;
	private boolean _indexed = true;

}