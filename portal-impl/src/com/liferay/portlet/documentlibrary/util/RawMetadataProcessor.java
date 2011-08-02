/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.metadata.RawMetadataProcessorUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 * @author Mika Koivisto
 * @author Miguel Pastor
 */
public class RawMetadataProcessor implements DLProcessor {

	public static void generateMetadata(FileEntry fileEntry)
		throws PortalException, SystemException {

		long fileEntryMetadataCount =
			DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadataCount(
				fileEntry.getFileEntryId(),
				fileEntry.getLatestFileVersion().getFileVersionId());

		if (fileEntryMetadataCount == 0) {
			_instance.trigger(fileEntry);
		}
	}

	public static void saveMetadata(FileVersion fileVersion)
		throws PortalException, SystemException {

		InputStream inputStream = fileVersion.getContentStream(false);

		Map<String, Fields> rawMetadataMap =
			RawMetadataProcessorUtil.getRawMetadataMap(inputStream);

		List<DDMStructure> ddmStructures =
			DDMStructureLocalServiceUtil.getClassStructures(
				PortalUtil.getClassNameId(DLFileEntry.class),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(fileVersion.getGroupId());
		serviceContext.setUserId(fileVersion.getUserId());

		DLFileEntryMetadataLocalServiceUtil.updateFileEntryMetadata(
			fileVersion.getCompanyId(), ddmStructures, 0L,
			fileVersion.getFileEntryId(), fileVersion.getFileVersionId(),
			rawMetadataMap, serviceContext);
	}

	public void trigger(FileEntry fileEntry) {
		try {
			MessageBusUtil.sendMessage(
				DestinationNames.DOCUMENT_LIBRARY_RAW_METADATA_PROCESSOR,
				fileEntry.getLatestFileVersion());
		}
		catch (Exception e) {
			_log.error(e,e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		RawMetadataProcessor.class);

	private static RawMetadataProcessor _instance = new RawMetadataProcessor();

}