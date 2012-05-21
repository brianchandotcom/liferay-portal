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

package com.liferay.portal.kernel.trash;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class TrashHandlerRegistryUtil {

	public static TrashHandler getTrashHandler(String className) {
		return getTrashHandlerRegistry().getTrashHandler(className);
	}

	public static TrashHandlerRegistry getTrashHandlerRegistry() {
		PortalRuntimePermission.checkGetBeanProperty(
			TrashHandlerRegistryUtil.class);

		return _trashHandlerRegistry;
	}

	public static List<TrashHandler> getTrashHandlers() {
		return getTrashHandlerRegistry().getTrashHandlers();
	}

	public static void register(List<TrashHandler> trashHandlers) {
		for (TrashHandler trashHandler : trashHandlers) {
			register(trashHandler);
		}
	}

	public static void register(TrashHandler trashHandler) {
		getTrashHandlerRegistry().register(trashHandler);
	}

	public static void unregister(List<TrashHandler> trashHandlers) {
		for (TrashHandler trashHandler : trashHandlers) {
			unregister(trashHandler);
		}
	}

	public static void unregister(TrashHandler trashHandler) {
		getTrashHandlerRegistry().unregister(trashHandler);
	}

	public void setTrashHandlerRegistry(
		TrashHandlerRegistry trashHandlerRegistry) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_trashHandlerRegistry = trashHandlerRegistry;
	}

	private static TrashHandlerRegistry _trashHandlerRegistry;

}