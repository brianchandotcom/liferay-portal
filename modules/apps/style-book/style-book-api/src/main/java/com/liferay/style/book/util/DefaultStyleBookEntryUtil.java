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

package com.liferay.style.book.util;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.model.Layout;
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

			styleBookEntry = StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
				masterLayout.getStyleBookEntryId());
		}

		if (styleBookEntry == null) {
			styleBookEntry =
				StyleBookEntryLocalServiceUtil.fetchDefaultStyleBookEntry(
					StagingUtil.getLiveGroupId(layout.getGroupId()));
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

}