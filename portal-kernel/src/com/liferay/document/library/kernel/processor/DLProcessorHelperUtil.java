/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Mika Koivisto
 */
public class DLProcessorHelperUtil {

	public static void cleanUp(FileEntry fileEntry) {
		DLProcessorHelper dlProcessorHelper = _dlProcessorHelperSnapshot.get();

		dlProcessorHelper.cleanUp(fileEntry);
	}

	public static void cleanUp(FileVersion fileVersion) {
		DLProcessorHelper dlProcessorHelper = _dlProcessorHelperSnapshot.get();

		dlProcessorHelper.cleanUp(fileVersion);
	}

	public static void exportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		DLProcessorHelper dlProcessorHelper = _dlProcessorHelperSnapshot.get();

		dlProcessorHelper.exportGeneratedFiles(
			portletDataContext, fileEntry, fileEntryElement);
	}

	public static DLProcessor getDLProcessor(String dlProcessorType) {
		DLProcessorHelper dlProcessorHelper = _dlProcessorHelperSnapshot.get();

		return dlProcessorHelper.getDLProcessor(dlProcessorType);
	}

	public static long getPreviewableProcessorMaxSize(long groupId) {
		DLProcessorHelper dlProcessorHelper = _dlProcessorHelperSnapshot.get();

		return dlProcessorHelper.getPreviewableProcessorMaxSize(groupId);
	}

	public static void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		DLProcessorHelper dlProcessorHelper = _dlProcessorHelperSnapshot.get();

		dlProcessorHelper.importGeneratedFiles(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement);
	}

	public static boolean isPreviewableSize(FileVersion fileVersion) {
		DLProcessorHelper dlProcessorHelper = _dlProcessorHelperSnapshot.get();

		return dlProcessorHelper.isPreviewableSize(fileVersion);
	}

	public static void trigger(FileEntry fileEntry, FileVersion fileVersion) {
		DLProcessorHelper dlProcessorHelper = _dlProcessorHelperSnapshot.get();

		dlProcessorHelper.trigger(fileEntry, fileVersion);
	}

	public static void trigger(
		FileEntry fileEntry, FileVersion fileVersion, boolean trusted) {

		DLProcessorHelper dlProcessorHelper = _dlProcessorHelperSnapshot.get();

		dlProcessorHelper.trigger(fileEntry, fileVersion, trusted);
	}

	private static final Snapshot<DLProcessorHelper>
		_dlProcessorHelperSnapshot = new Snapshot<>(
			DLProcessorHelperUtil.class, DLProcessorHelper.class);

}