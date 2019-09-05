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

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.kernel.lar.ExportImportProcessCallbackRegistry;

import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true, service = ExportImportProcessCallbackRegistry.class
)
public class ExportImportProcessCallbackRegistryImpl
	implements ExportImportProcessCallbackRegistry {

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	@Override
	public void registerCallback(Callable<?> callable) {
	}

	@Override
	public void registerCallback(String processId, Callable<?> callable) {
		ExportImportProcessCallbackUtil.registerCallback(processId, callable);
	}

}