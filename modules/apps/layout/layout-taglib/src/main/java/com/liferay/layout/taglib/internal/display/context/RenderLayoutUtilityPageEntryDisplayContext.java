/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.internal.display.context;

import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;

import java.util.Date;

/**
 * @author Jürgen Kappler
 */
public class RenderLayoutUtilityPageEntryDisplayContext {

	public RenderLayoutUtilityPageEntryDisplayContext(
		LayoutUtilityPageEntry layoutUtilityPageEntry) {

		_layoutUtilityPageEntry = layoutUtilityPageEntry;
	}

	public String getHref() {
		if (_layoutUtilityPageEntry == null) {
			return StringPool.BLANK;
		}

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			_layoutUtilityPageEntry.getPlid());

		if (layout == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(PortalUtil.getPathContext());
		sb.append("/o/layout-common-styles/main.css?plid=");
		sb.append(_layoutUtilityPageEntry.getPlid());
		sb.append("&segmentsExperienceId=");
		sb.append(
			SegmentsExperienceLocalServiceUtil.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));
		sb.append("&t=");
		sb.append(_getModifiedDate(layout));

		Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
			layout.getMasterLayoutPlid());

		if (masterLayout != null) {
			sb.append(_getModifiedDate(masterLayout));
		}

		return sb.toString();
	}

	private String _getModifiedDate(Layout layout) {
		Date modifiedDate = layout.getModifiedDate();

		if (modifiedDate != null) {
			return String.valueOf(modifiedDate.getTime());
		}

		return String.valueOf(System.currentTimeMillis());
	}

	private final LayoutUtilityPageEntry _layoutUtilityPageEntry;

}