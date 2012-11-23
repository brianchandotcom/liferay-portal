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

package com.liferay.portal.kernel.lar;

import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class StagedModelDataHandlerRegistryUtil {

	public static StagedModelDataHandler getStagedModelDataHandler(
		String className) {

		return getStagedModelDataHandlerRegistry().getStagedModelDataHandler(
			className);
	}

	public static List<StagedModelDataHandler> getStagedModelDataHandlerList() {
		return getStagedModelDataHandlerRegistry().
			getStagedModelDataHandlerList();
	}

	public static StagedModelDataHandlerRegistry
		getStagedModelDataHandlerRegistry() {

		return _stagedModelDataHandlerRegistry;
	}

	public static Map<String, StagedModelDataHandler>
		getStagedModelDataHandlers() {

		return getStagedModelDataHandlerRegistry().getStagedModelDataHandlers();
	}

	public static void register(StagedModelDataHandler stagedModelDataHandler) {
		getStagedModelDataHandlerRegistry().register(stagedModelDataHandler);
	}

	public static void unregister(
		StagedModelDataHandler stagedModelDataHandler) {

		getStagedModelDataHandlerRegistry().unregister(stagedModelDataHandler);
	}

	public void setStagedModelDataHandlerRegistry(
		StagedModelDataHandlerRegistry stagedModelDataHandlerRegistry) {

		_stagedModelDataHandlerRegistry = stagedModelDataHandlerRegistry;
	}

	private static StagedModelDataHandlerRegistry
		_stagedModelDataHandlerRegistry;

}