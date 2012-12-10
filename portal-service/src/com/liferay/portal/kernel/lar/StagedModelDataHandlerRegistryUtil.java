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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;

/**
 * Provides a utility facade to the staged model data handler registry
 * framework.
 *
 * @author Mate Thurzo
 * @author Brian Wing Shun Chan
 * @since  6.2
 */
public class StagedModelDataHandlerRegistryUtil {

	/**
	 * Returns the staged model data handler for a given class name from the
	 * registry.
	 *
	 * @param  className the name of the staged model class
	 * @return the staged model data handler for the staged model class if it
	 *         has been registered, <code>null</code> otherwise
	 */
	public static StagedModelDataHandler<?> getStagedModelDataHandler(
		String className) {

		return getStagedModelDataHandlerRegistry().getStagedModelDataHandler(
			className);
	}

	public static StagedModelDataHandlerRegistry
		getStagedModelDataHandlerRegistry() {

		PortalRuntimePermission.checkGetBeanProperty(
			StagedModelDataHandlerRegistryUtil.class);

		return _stagedModelDataHandlerRegistry;
	}

	/**
	 * Returns the list of staged model data handlers registered in the
	 * registry.
	 *
	 * @return a list of staged model data handlers registered in the registry
	 */
	public static List<StagedModelDataHandler<?>> getStagedModelDataHandlers() {
		return getStagedModelDataHandlerRegistry().getStagedModelDataHandlers();
	}

	/**
	 * Registers a staged model data handler.
	 *
	 * @param stagedModelDataHandler the staged model data handler to register,
	 *        a passed <code>null</code> value won't be registered
	 */
	public static void register(
		StagedModelDataHandler<?> stagedModelDataHandler) {

		getStagedModelDataHandlerRegistry().register(stagedModelDataHandler);
	}

	/**
	 * Unregister a list of staged model data handlers.
	 *
	 * @param stagedModelDataHandlers the list of staged model data handlers to
	 *        unregister
	 */
	public static void unregister(
		List<StagedModelDataHandler<?>> stagedModelDataHandlers) {

		for (StagedModelDataHandler<?> stagedModelDataHandler :
				stagedModelDataHandlers) {

			unregister(stagedModelDataHandler);
		}
	}

	/**
	 * Unregister a staged model data handler.
	 *
	 * @param stagedModelDataHandler the staged model data handler to unregister
	 */
	public static void unregister(
		StagedModelDataHandler<?> stagedModelDataHandler) {

		getStagedModelDataHandlerRegistry().unregister(stagedModelDataHandler);
	}

	public void setStagedModelDataHandlerRegistry(
		StagedModelDataHandlerRegistry stagedModelDataHandlerRegistry) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_stagedModelDataHandlerRegistry = stagedModelDataHandlerRegistry;
	}

	private static StagedModelDataHandlerRegistry
		_stagedModelDataHandlerRegistry;

}