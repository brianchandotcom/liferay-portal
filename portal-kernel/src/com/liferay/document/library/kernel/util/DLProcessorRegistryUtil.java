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

	public static DLProcessor getDLProcessor(String dlProcessorType) {
		return _dlProcessorRegistry.getDLProcessor(dlProcessorType);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static DLProcessorRegistry getDLProcessorRegistry() {
		return _dlProcessorRegistry;
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

	public static void register(DLProcessor dlProcessor) {
		_dlProcessorRegistry.register(dlProcessor);
	}

	public static void trigger(FileEntry fileEntry, FileVersion fileVersion) {
		_dlProcessorRegistry.trigger(fileEntry, fileVersion);
	}

	public static void trigger(
		FileEntry fileEntry, FileVersion fileVersion, boolean trusted) {

		_dlProcessorRegistry.trigger(fileEntry, fileVersion, trusted);
	}

	public static void unregister(DLProcessor dlProcessor) {
		_dlProcessorRegistry.unregister(dlProcessor);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setDLProcessorRegistry(
		DLProcessorRegistry dlProcessorRegistry) {
	}

	private static volatile DLProcessorRegistry _dlProcessorRegistry =
		ServiceProxyFactory.newServiceTrackedInstance(
			DLProcessorRegistry.class, DLProcessorRegistryUtil.class,
			"_dlProcessorRegistry", false);

}