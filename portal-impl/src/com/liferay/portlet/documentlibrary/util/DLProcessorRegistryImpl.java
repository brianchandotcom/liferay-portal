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
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Mika Koivisto
 */
public class DLProcessorRegistryImpl implements DLProcessorRegistry {

	public void register(DLProcessor dlProcessor) {
		_dlProcessors.add(dlProcessor);
	}

	public void trigger(FileEntry fileEntry) {
		if (fileEntry == null) {
			return;
		}

		for (String processorClassName : _DL_FILE_ENTRY_PROCESSORS) {
			DLProcessor dlProcessor =
				(DLProcessor)InstancePool.get(processorClassName);

			dlProcessor.trigger(fileEntry);
		}

		for (DLProcessor dlProcessor : _dlProcessors) {
			dlProcessor.trigger(fileEntry);
		}
	}

	public void unregister(DLProcessor dlProcessor) {
		_dlProcessors.remove(dlProcessor);
	}

	private static final String[] _DL_FILE_ENTRY_PROCESSORS =
		PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PROCESSORS);

	private CopyOnWriteArraySet<DLProcessor> _dlProcessors =
		new CopyOnWriteArraySet<DLProcessor>();

}