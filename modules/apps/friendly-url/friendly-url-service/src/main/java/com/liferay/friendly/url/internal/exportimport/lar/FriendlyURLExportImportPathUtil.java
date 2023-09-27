/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.exportimport.lar;

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.friendly.url.model.FriendlyURLEntry;

/**
 * Generates friendly URL paths serialized with the export/import framework.
 *
 * @author Jorge García Jiménez
 */
public class FriendlyURLExportImportPathUtil {

	/**
	 * Returns a model path based on the portlet data context and friendly URL
	 * entry.
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @param  friendlyURLEntry the friendly URL entry the path is needed for
	 * @return a model path for the friendly URL entry
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