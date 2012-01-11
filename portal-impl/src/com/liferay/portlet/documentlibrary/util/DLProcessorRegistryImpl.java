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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Mika Koivisto
 */
public class DLProcessorRegistryImpl implements DLProcessorRegistry {

	public void cleanUp(FileEntry fileEntry) {
		if (!DLProcessorThreadLocal.isEnabled()) {
			return;
		}

		for (String dlProcessorClassName : _DL_FILE_ENTRY_PROCESSORS) {
			DLProcessor dlProcessor = (DLProcessor)InstancePool.get(
				dlProcessorClassName);

			dlProcessor.cleanUp(fileEntry);
		}

		for (DLProcessor dlProcessor : _dlProcessors) {
			dlProcessor.cleanUp(fileEntry);
		}
	}

	public void cleanUp(FileVersion fileVersion) {
		if (!DLProcessorThreadLocal.isEnabled()) {
			return;
		}

		for (String dlProcessorClassName : _DL_FILE_ENTRY_PROCESSORS) {
			DLProcessor dlProcessor = (DLProcessor)InstancePool.get(
				dlProcessorClassName);

			dlProcessor.cleanUp(fileVersion);
		}

		for (DLProcessor dlProcessor : _dlProcessors) {
			dlProcessor.cleanUp(fileVersion);
		}
	}

	public void register(DLProcessor dlProcessor) {
		_dlProcessors.add(dlProcessor);
	}

	public void trigger(FileEntry fileEntry) {
		if (!DLProcessorThreadLocal.isEnabled()) {
			return;
		}

		if ((fileEntry == null) || (fileEntry.getSize() == 0)) {
			return;
		}

		FileVersion latestFileVersion = null;

		try {
			if (fileEntry.getModel() instanceof DLFileEntry) {
				DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

				latestFileVersion = new LiferayFileVersion(
					dlFileEntry.getLatestFileVersion(false));
			}
			else {
				latestFileVersion = fileEntry.getLatestFileVersion();
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			return;
		}

		for (String dlProcessorClassName : _DL_FILE_ENTRY_PROCESSORS) {
			DLProcessor dlProcessor = (DLProcessor)InstancePool.get(
				dlProcessorClassName);

			if (dlProcessor.isSupported(latestFileVersion)) {
				dlProcessor.trigger(latestFileVersion);
			}
		}

		for (DLProcessor dlProcessor : _dlProcessors) {
			if (dlProcessor.isSupported(latestFileVersion)) {
				dlProcessor.trigger(latestFileVersion);
			}
		}
	}

	public void unregister(DLProcessor dlProcessor) {
		_dlProcessors.remove(dlProcessor);
	}

	private static final String[] _DL_FILE_ENTRY_PROCESSORS =
		PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PROCESSORS);

	private static Log _log = LogFactoryUtil.getLog(
		DLProcessorRegistryImpl.class);

	private List<DLProcessor> _dlProcessors =
		new CopyOnWriteArrayList<DLProcessor>();

}