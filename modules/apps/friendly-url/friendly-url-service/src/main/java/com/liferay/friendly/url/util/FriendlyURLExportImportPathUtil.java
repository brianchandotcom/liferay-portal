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

package com.liferay.friendly.url.util;

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.friendly.url.model.FriendlyURLEntry;

/**
 * Provides utility methods for retrieving path for friendlyURL being imported
 *  with the portal's export/import framework.
 *
 * @author Jorge García Jiménez
 */
public class FriendlyURLExportImportPathUtil {

	/**
	 * Returns a model path based on portletDataContext companyId
	 * and friendlyURLEntry StagedModel
	 *
	 * It fixes a corner case not covered by ExportImportPathUtil.getModelPath
	 * when the source and target companyId is not same for friendlyURLEntry
	 *
	 * @param portletDataContext the context of the current export/import
	 * 		process
	 * @param friendlyURLEntry the staged model the path is needed for
	 * @return a model path based on the parameters
	 */
	public static String getModelPath(
		PortletDataContext portletDataContext,
		FriendlyURLEntry friendlyURLEntry) {

		String modelPath;

		if (friendlyURLEntry.getCompanyId() !=
				portletDataContext.getSourceCompanyId()) {

			FriendlyURLEntry sourceFriendlyURLEntry =
				(FriendlyURLEntry)friendlyURLEntry.clone();

			sourceFriendlyURLEntry.setCompanyId(
				portletDataContext.getSourceCompanyId());

			modelPath = ExportImportPathUtil.getModelPath(
				sourceFriendlyURLEntry, sourceFriendlyURLEntry.getUuid());
		}
		else {
			modelPath = ExportImportPathUtil.getModelPath(
				friendlyURLEntry, friendlyURLEntry.getUuid());
		}

		return modelPath;
	}

}