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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Mika Koivisto
 */
public class DLProcessorRegistryUtil {

	public static void cleanup(FileEntry fileEntry) {
		getDLProcessorRegistry().cleanup(fileEntry);
	}

	public static void cleanup(FileVersion fileVersion) {
		getDLProcessorRegistry().cleanup(fileVersion);
	}

	public static DLProcessorRegistry getDLProcessorRegistry() {
		return _dlProcessorRegistry;
	}

	public static void register(DLProcessor dlProcessor) {
		getDLProcessorRegistry().register(dlProcessor);
	}

	public static void trigger(FileEntry fileEntry) {
		getDLProcessorRegistry().trigger(fileEntry);
	}

	public static void unregister(DLProcessor dlProcessor) {
		getDLProcessorRegistry().unregister(dlProcessor);
	}

	public void setDLProcessorRegistry(
		DLProcessorRegistry dlProcessorRegistry) {

		_dlProcessorRegistry = dlProcessorRegistry;
	}

	private static DLProcessorRegistry _dlProcessorRegistry;

}