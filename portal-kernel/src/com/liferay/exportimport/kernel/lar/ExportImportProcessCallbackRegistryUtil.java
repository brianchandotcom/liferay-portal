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

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.concurrent.Callable;

/**
 * @author Daniel Kocsis
 */
public class ExportImportProcessCallbackRegistryUtil {

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	public static void registerCallback(Callable<?> callable) {
		_exportImportProcessCommitCallbackRegistry.registerCallback(callable);
	}

	public static void registerCallback(
		String processId, Callable<?> callable) {

		_exportImportProcessCommitCallbackRegistry.registerCallback(
			processId, callable);
	}

	private static volatile ExportImportProcessCallbackRegistry
		_exportImportProcessCommitCallbackRegistry =
			ServiceProxyFactory.newServiceTrackedInstance(
				ExportImportProcessCallbackRegistry.class,
				ExportImportProcessCallbackRegistryUtil.class,
				"_exportImportProcessCommitCallbackRegistry", false);

}