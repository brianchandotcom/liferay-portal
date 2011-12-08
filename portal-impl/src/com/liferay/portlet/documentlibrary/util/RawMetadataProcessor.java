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
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.metadata.RawMetadataProcessorUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * Document library processor responsible for the generation of raw metadata
 * associated with all of the the files stored in the document library.
 *
 * <p>
 * This processor automatically and assynchronously extracts the metadata from
 * all of the files stored in the document library. The metadata extraction is
 * done with the help of {@link
 * com.liferay.portal.metadata.TikaRawMetadataProcessor}
 * </p>
 *
 * @author Alexander Chow
 * @author Mika Koivisto
 * @author Miguel Pastor
 */
public class RawMetadataProcessor implements DLProcessor {

	public void cleanUp(FileEntry fileEntry) {
	}

	public void cleanUp(FileVersion fileVersion) {
	}

	/**
	 * Generates the raw metadata associated with the file entry.
	 *
	 * @param  fileVersion the file version from which the raw metatada is to be
	 *         generated
	 * @throws PortalException if an error occurred in the metadata extraction
	 * @throws SystemException if a system exception occurred
	 */
	public static void generateMetadata(FileVersion fileVersion)
		throws PortalException, SystemException {

		long fileEntryMetadataCount =
			DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadataCount(
				fileVersion.getFileEntryId(),
				fileVersion.getFileVersionId());

		if (fileEntryMetadataCount == 0) {
			_instance.trigger(fileVersion);
		}
	}

	public boolean isSupported(FileVersion fileVersion) {
		return true;
	}

	public boolean isSupported(String mimeType) {
		return true;
	}

	/**
	 * Saves the raw metadata present in the file version.
	 *
	 * <p>
	 * The raw metadata present in the file version is extracted and persisted
	 * using {@link com.liferay.portal.metadata.TikaRawMetadataProcessor}.
	 * </p>
	 *
	 * @param  fileVersion the file version from which the raw metatada is to be
	 *         extracted and persisted
	 * @throws PortalException if an error occurred in the metadata extraction
	 * @throws SystemException if a system exception occurred
	 */
	public static void saveMetadata(FileVersion fileVersion)
		throws PortalException, SystemException {

		Map<String, Fields> rawMetadataMap = null;

		if (fileVersion instanceof LiferayFileVersion) {
			try {
				LiferayFileVersion liferayFileVersion =
					(LiferayFileVersion)fileVersion;

				File file = liferayFileVersion.getFile(false);

				rawMetadataMap = RawMetadataProcessorUtil.getRawMetadataMap(
					fileVersion.getExtension(), fileVersion.getMimeType(),
					file);
			}
			catch (UnsupportedOperationException uoe) {
			}
		}

		if (rawMetadataMap == null) {
			InputStream inputStream = fileVersion.getContentStream(false);

			rawMetadataMap = RawMetadataProcessorUtil.getRawMetadataMap(
				fileVersion.getExtension(), fileVersion.getMimeType(),
				inputStream);
		}

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

	/**
	 * Launches extraction of raw metadata from the file version.
	 *
	 * <p>
	 * The raw metadata extraction is done asynchronously.
	 * </p>
	 *
	 * @param fileVersion the latest file version from which the raw metadata is
	 *        to be generated
	 */
	public void trigger(FileVersion fileVersion) {
		if (PropsValues.DL_FILE_ENTRY_PROCESSORS_PROCESS_SYNCHRONOUSLY) {
			try {
				MessageBusUtil.sendSynchronousMessage(
					DestinationNames.DOCUMENT_LIBRARY_RAW_METADATA_PROCESSOR,
					fileVersion);
			}
			catch (MessageBusException mbe) {
				if (_log.isWarnEnabled()) {
					_log.warn(mbe, mbe);
				}
			}
		}
		else {
			MessageBusUtil.sendMessage(
				DestinationNames.DOCUMENT_LIBRARY_RAW_METADATA_PROCESSOR,
				fileVersion);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RawMetadataProcessor.class);

	private static RawMetadataProcessor _instance = new RawMetadataProcessor();

}