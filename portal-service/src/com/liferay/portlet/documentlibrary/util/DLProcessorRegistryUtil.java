/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Mika Koivisto
 */
public class DLProcessorRegistryUtil {

	public static void cleanUp(FileEntry fileEntry) {
		getDLProcessorRegistry().cleanUp(fileEntry);
	}

	public static void cleanUp(FileVersion fileVersion) {
		getDLProcessorRegistry().cleanUp(fileVersion);
	}

	public static void exportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		getDLProcessorRegistry().exportGeneratedFiles(
			portletDataContext, fileEntry, fileEntryElement);
	}

	public static DLProcessor getDLProcessor(String dlProcessorType) {
		return getDLProcessorRegistry().getDLProcessor(dlProcessorType);
	}

	public static DLProcessorRegistry getDLProcessorRegistry() {
		PortalRuntimePermission.checkGetBeanProperty(
			DLProcessorRegistryUtil.class);

		return _dlProcessorRegistry;
	}

	public static void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		getDLProcessorRegistry().importGeneratedFiles(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement);
	}

	public static void register(DLProcessor dlProcessor) {
		getDLProcessorRegistry().register(dlProcessor);
	}

	public static void trigger(FileEntry fileEntry, FileVersion fileVersion) {
		getDLProcessorRegistry().trigger(fileEntry, fileVersion);
	}

	public static void trigger(
		FileEntry fileEntry, FileVersion fileVersion, boolean trusted) {

		getDLProcessorRegistry().trigger(fileEntry, fileVersion, trusted);
	}

	public static void unregister(DLProcessor dlProcessor) {
		getDLProcessorRegistry().unregister(dlProcessor);
	}

	public void setDLProcessorRegistry(
		DLProcessorRegistry dlProcessorRegistry) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_dlProcessorRegistry = dlProcessorRegistry;
	}

	private static DLProcessorRegistry _dlProcessorRegistry;

}