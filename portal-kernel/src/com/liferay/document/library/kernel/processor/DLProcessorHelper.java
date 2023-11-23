/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Mika Koivisto
 */
public interface DLProcessorHelper {

	public void cleanUp(FileEntry fileEntry);

	public void cleanUp(FileVersion fileVersion);

	public void exportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception;

	public DLProcessor getDLProcessor(String dlProcessorType);

	public long getPreviewableProcessorMaxSize(long groupId);

	public void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception;

	public boolean isPreviewableSize(FileVersion fileVersion);

	public void trigger(FileEntry fileEntry, FileVersion fileVersion);

	public void trigger(
		FileEntry fileEntry, FileVersion fileVersion, boolean trusted);

}