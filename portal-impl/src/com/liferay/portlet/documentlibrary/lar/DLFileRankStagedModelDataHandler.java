/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class DLFileRankStagedModelDataHandler
	extends BaseStagedModelDataHandler<DLFileRank> {

	public static final String[] CLASS_NAMES = {DLFileRank.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DLFileRank fileRank)
		throws Exception {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			fileRank.getFileEntryId());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, fileEntry);

		Element fileRankElement =
			portletDataContext.getExportDataStagedModelElement(fileRank);

		portletDataContext.addClassedModel(
			fileRankElement, ExportImportPathUtil.getModelPath(fileRank),
			fileRank, DLPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DLFileRank fileRank)
		throws Exception {

		long userId = portletDataContext.getUserId(fileRank.getUserUuid());

		String fileEntryPath = ExportImportPathUtil.getModelPath(
			portletDataContext, FileEntry.class.getName(),
			fileRank.getFileEntryId());

		FileEntry fileEntry = (FileEntry)portletDataContext.getZipEntryAsObject(
			fileEntryPath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, fileEntry);

		Map<Long, Long> fileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		long fileEntryId = MapUtil.getLong(
			fileEntryIds, fileRank.getFileEntryId(), fileRank.getFileEntryId());

		if (DLFileEntryLocalServiceUtil.fetchDLFileEntry(fileEntryId) == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to retrieve file to import file rank");
			}

			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(fileRank.getCreateDate());

		DLAppLocalServiceUtil.updateFileRank(
			portletDataContext.getScopeGroupId(),
			portletDataContext.getCompanyId(), userId, fileEntryId,
			serviceContext);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFileRankStagedModelDataHandler.class);

}