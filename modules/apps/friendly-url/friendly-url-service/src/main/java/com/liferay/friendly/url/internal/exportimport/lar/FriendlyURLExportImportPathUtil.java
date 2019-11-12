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

package com.liferay.friendly.url.internal.exportimport.lar;

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.friendly.url.model.FriendlyURLEntry;

/**
 * Provides utility methods for generating paths for friendlyURLs serialized
 * with the portal's export/import framework.
 *
 * @author Jorge García Jiménez
 * @review
 */
public class FriendlyURLExportImportPathUtil {

	/**
	 * Returns a model path based on the portlet data context and friendly url
	 * entry.
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @param  friendlyURLEntry the friendly url entry the path is needed for
	 * @return a model path for the friendly url entry
	 * @review
	 */
	public static String getModelPath(
		PortletDataContext portletDataContext,
		FriendlyURLEntry friendlyURLEntry) {

		FriendlyURLEntry sourceFriendlyURLEntry = _getSourceFriendlyURLEntry(
			portletDataContext, friendlyURLEntry);

		return ExportImportPathUtil.getModelPath(
			sourceFriendlyURLEntry, sourceFriendlyURLEntry.getUuid());
	}

	private static FriendlyURLEntry _getSourceFriendlyURLEntry(
		PortletDataContext portletDataContext,
		FriendlyURLEntry friendlyURLEntry) {

		if (friendlyURLEntry.getCompanyId() ==
				portletDataContext.getSourceCompanyId()) {

			return friendlyURLEntry;
		}

		FriendlyURLEntry sourceFriendlyURLEntry =
			(FriendlyURLEntry)friendlyURLEntry.clone();

		sourceFriendlyURLEntry.setCompanyId(
			portletDataContext.getSourceCompanyId());

		return sourceFriendlyURLEntry;
	}

}