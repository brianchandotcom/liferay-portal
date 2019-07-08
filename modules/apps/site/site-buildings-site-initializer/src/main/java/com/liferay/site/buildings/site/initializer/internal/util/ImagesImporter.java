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

package com.liferay.site.buildings.site.initializer.internal.util;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = {})
public class ImagesImporter {

	public List<FileEntry> importFile(long userId, long groupId, File file)
		throws Exception {

		List<FileEntry> fileEntries = new ArrayList<>();

		ZipFile zipFile = new ZipFile(file);

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String fileName = zipEntry.getName();

			byte[] bytes = null;

			try (InputStream is = zipFile.getInputStream(zipEntry)) {
				bytes = FileUtil.getBytes(is);
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			FileEntry fileEntry = _dlAppLocalService.addFileEntry(
				userId, groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				fileName, MimeTypesUtil.getContentType(fileName), bytes,
				serviceContext);

			fileEntries.add(fileEntry);
		}

		return fileEntries;
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

}