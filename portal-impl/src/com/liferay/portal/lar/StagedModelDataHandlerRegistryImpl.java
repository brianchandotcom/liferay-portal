/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of the staged model data handler registry framework.
 *
 * @author Mate Thurzo
 * @see    com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil
 * @since  6.2
 */
@DoPrivileged
public class StagedModelDataHandlerRegistryImpl
	implements StagedModelDataHandlerRegistry {

	@Override
	public StagedModelDataHandler<?> getStagedModelDataHandler(
		String className) {

		return _stagedModelDataHandlers.get(className);
	}

	@Override
	public List<StagedModelDataHandler<?>> getStagedModelDataHandlers() {
		return ListUtil.fromMapValues(_stagedModelDataHandlers);
	}

	@Override
	public void register(StagedModelDataHandler<?> stagedModelDataHandler) {
		for (String className : stagedModelDataHandler.getClassNames()) {
			if (_stagedModelDataHandlers.containsKey(className)) {
				if (_log.isWarnEnabled()) {
					_log.warn("Duplicate class " + className);
				}

				continue;
			}

			_stagedModelDataHandlers.put(className, stagedModelDataHandler);
		}
	}

	@Override
	public void unregister(StagedModelDataHandler<?> stagedModelDataHandler) {
		for (String className : stagedModelDataHandler.getClassNames()) {
			_stagedModelDataHandlers.remove(className);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		StagedModelDataHandlerRegistryImpl.class);

	private Map<String, StagedModelDataHandler<?>> _stagedModelDataHandlers =
		new HashMap<String, StagedModelDataHandler<?>>();

}