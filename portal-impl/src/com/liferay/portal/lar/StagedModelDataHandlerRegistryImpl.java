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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistry;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class StagedModelDataHandlerRegistryImpl
	implements StagedModelDataHandlerRegistry {

	public StagedModelDataHandler<?> getStagedModelDataHandler(
		String className) {

		return _stagedModelDataHandlers.get(className);
	}

	public List<StagedModelDataHandler<?>> getStagedModelDataHandlers() {
		return ListUtil.fromMapValues(_stagedModelDataHandlers);
	}

	public void register(StagedModelDataHandler<?> stagedModelDataHandler) {
		_stagedModelDataHandlers.put(
			stagedModelDataHandler.getClassName(), stagedModelDataHandler);
	}

	public void unregister(StagedModelDataHandler<?> stagedModelDataHandler) {
		_stagedModelDataHandlers.remove(stagedModelDataHandler.getClassName());
	}

	private Map<String, StagedModelDataHandler<?>> _stagedModelDataHandlers =
		new HashMap<String, StagedModelDataHandler<?>>();

}