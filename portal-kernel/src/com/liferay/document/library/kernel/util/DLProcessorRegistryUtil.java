/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Mika Koivisto
 */
public class DLProcessorRegistryUtil {

	public static void cleanUp(FileEntry fileEntry) {
		DLProcessorRegistry dlProcessorRegistry =
			_dlProcessorRegistrySnapshot.get();

		dlProcessorRegistry.cleanUp(fileEntry);
	}

	public static void cleanUp(FileVersion fileVersion) {
		DLProcessorRegistry dlProcessorRegistry =
			_dlProcessorRegistrySnapshot.get();

		dlProcessorRegistry.cleanUp(fileVersion);
	}

	public static void exportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		DLProcessorRegistry dlProcessorRegistry =
			_dlProcessorRegistrySnapshot.get();

		dlProcessorRegistry.exportGeneratedFiles(
			portletDataContext, fileEntry, fileEntryElement);
	}

	public static DLProcessor getDLProcessor(String dlProcessorType) {
		DLProcessorRegistry dlProcessorRegistry =
			_dlProcessorRegistrySnapshot.get();

		return dlProcessorRegistry.getDLProcessor(dlProcessorType);
	}

	public static long getPreviewableProcessorMaxSize(long groupId) {
		DLProcessorRegistry dlProcessorRegistry =
			_dlProcessorRegistrySnapshot.get();

		return dlProcessorRegistry.getPreviewableProcessorMaxSize(groupId);
	}

	public static void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		DLProcessorRegistry dlProcessorRegistry =
			_dlProcessorRegistrySnapshot.get();

		dlProcessorRegistry.importGeneratedFiles(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement);
	}

	public static boolean isPreviewableSize(FileVersion fileVersion) {
		DLProcessorRegistry dlProcessorRegistry =
			_dlProcessorRegistrySnapshot.get();

		return dlProcessorRegistry.isPreviewableSize(fileVersion);
	}

	public static void trigger(FileEntry fileEntry, FileVersion fileVersion) {
		DLProcessorRegistry dlProcessorRegistry =
			_dlProcessorRegistrySnapshot.get();

		dlProcessorRegistry.trigger(fileEntry, fileVersion);
	}

	public static void trigger(
		FileEntry fileEntry, FileVersion fileVersion, boolean trusted) {

		DLProcessorRegistry dlProcessorRegistry =
			_dlProcessorRegistrySnapshot.get();

		dlProcessorRegistry.trigger(fileEntry, fileVersion, trusted);
	}

	private static final Snapshot<DLProcessorRegistry>
		_dlProcessorRegistrySnapshot = new Snapshot<>(
			DLProcessorRegistryUtil.class, DLProcessorRegistry.class);

}