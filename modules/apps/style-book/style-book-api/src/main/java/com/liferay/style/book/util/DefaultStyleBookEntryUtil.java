/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.util;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;

/**
 * @author Víctor Galán
 */
public class DefaultStyleBookEntryUtil {

	public static StyleBookEntry getDefaultMasterStyleBookEntry(Layout layout) {
		StyleBookEntry styleBookEntry = null;

		if (layout.getMasterLayoutPlid() > 0) {
			Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
				layout.getMasterLayoutPlid());

			if (masterLayout != null) {
				styleBookEntry =
					StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
						masterLayout.getStyleBookEntryId());
			}
		}

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		try {
			Theme theme = layout.getTheme();

			styleBookEntry =
				StyleBookEntryLocalServiceUtil.fetchDefaultStyleBookEntry(
					StagingUtil.getLiveGroupId(layout.getGroupId()),
					theme.getThemeId());
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get layout's default style book", portalException);
		}

		return styleBookEntry;
	}

	public static StyleBookEntry getDefaultStyleBookEntry(Layout layout) {
		StyleBookEntry styleBookEntry = null;

		if (layout.getStyleBookEntryId() > 0) {
			styleBookEntry = StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
				layout.getStyleBookEntryId());
		}

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		return getDefaultMasterStyleBookEntry(layout);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultStyleBookEntryUtil.class);

}