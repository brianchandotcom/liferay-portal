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

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Víctor Galán
 */
public class LayoutStructureItemCSSUtil {

	public static String getFragmentEntryLinkCssClass(
		FragmentEntryLink fragmentEntryLink) {

		return _normalizeCssClass(
			_LAYOUT_STRUCTURE_ITEM_CSS_CLASS_PREFIX +
				_getFragmentEntryLinkIdentifier(fragmentEntryLink));
	}

	public static String getLayoutStructureItemCssClass(
		LayoutStructureItem layoutStructureItem) {

		return _LAYOUT_STRUCTURE_ITEM_CSS_CLASS_PREFIX +
			layoutStructureItem.getItemType();
	}

	public static String getLayoutStructureItemUniqueCssClass(
		LayoutStructureItem layoutStructureItem) {

		return _LAYOUT_STRUCTURE_ITEM_CSS_CLASS_PREFIX +
			layoutStructureItem.getItemId();
	}

	public static String getStyledLayoutStructureItemCssClasses(
		StyledLayoutStructureItem styledLayoutStructureItem) {

		return StringUtil.merge(
			styledLayoutStructureItem.getCssClasses(), StringPool.SPACE);
	}

	private static JSONObject _createJSONObject(String value) {
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

	private static String _getFragmentEntryLinkIdentifier(
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

	private static String _normalizeCssClass(String cssClass) {
		cssClass = StringUtil.toLowerCase(cssClass);

		return cssClass.replaceAll("[^A-Za-z0-9-]", StringPool.DASH);
	}

	private static final String _LAYOUT_STRUCTURE_ITEM_CSS_CLASS_PREFIX =
		"lfr-layout-structure-item-";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStructureItemCSSUtil.class);

}