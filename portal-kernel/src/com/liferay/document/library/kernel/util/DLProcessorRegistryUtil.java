/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
public class DLProcessorRegistryUtil {

	public static void cleanUp(FileEntry fileEntry) {
		_dlProcessorRegistry.cleanUp(fileEntry);
	}

	public static void cleanUp(FileVersion fileVersion) {
		_dlProcessorRegistry.cleanUp(fileVersion);
	}

	public static void exportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		_dlProcessorRegistry.exportGeneratedFiles(
			portletDataContext, fileEntry, fileEntryElement);
	}

	public static void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		_dlProcessorRegistry.importGeneratedFiles(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement);
	}

	public static boolean isPreviewableSize(FileVersion fileVersion) {
		return _dlProcessorRegistry.isPreviewableSize(fileVersion);
	}

	public static void trigger(FileEntry fileEntry, FileVersion fileVersion) {
		_dlProcessorRegistry.trigger(fileEntry, fileVersion);
	}

	public static void trigger(
		FileEntry fileEntry, FileVersion fileVersion, boolean trusted) {

		_dlProcessorRegistry.trigger(fileEntry, fileVersion, trusted);
	}

	private static volatile DLProcessorRegistry _dlProcessorRegistry =
		ServiceProxyFactory.newServiceTrackedInstance(
			DLProcessorRegistry.class, DLProcessorRegistryUtil.class,
			"_dlProcessorRegistry", false);

}