/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Mika Koivisto
 */
public class DLProcessorHelperUtil {

	public static void cleanUp(FileEntry fileEntry) {
		_dlProcessorHelper.cleanUp(fileEntry);
	}

	public static void cleanUp(FileVersion fileVersion) {
		_dlProcessorHelper.cleanUp(fileVersion);
	}

	public static void exportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		_dlProcessorHelper.exportGeneratedFiles(
			portletDataContext, fileEntry, fileEntryElement);
	}

	public static void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		_dlProcessorHelper.importGeneratedFiles(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement);
	}

	public static boolean isPreviewableSize(FileVersion fileVersion) {
		return _dlProcessorHelper.isPreviewableSize(fileVersion);
	}

	public static void trigger(FileEntry fileEntry, FileVersion fileVersion) {
		_dlProcessorHelper.trigger(fileEntry, fileVersion);
	}

	public static void trigger(
		FileEntry fileEntry, FileVersion fileVersion, boolean trusted) {

		_dlProcessorHelper.trigger(fileEntry, fileVersion, trusted);
	}

	private static volatile DLProcessorHelper _dlProcessorHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			DLProcessorHelper.class, DLProcessorHelperUtil.class,
			"_dlProcessorHelper", false);

}