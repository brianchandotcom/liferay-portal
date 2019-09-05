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

package com.liferay.exportimport.lifecycle;

import com.liferay.exportimport.kernel.lifecycle.BaseProcessExportImportLifecycleListener;

import java.io.Serializable;

import java.util.List;

/**
 * @author     Daniel Kocsis
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
public class ExportImportProcessCallbackLifecycleListener
	extends BaseProcessExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	protected void onProcessFailed(List<Serializable> attributes)
		throws Exception {
	}

	@Override
	protected void onProcessStarted(List<Serializable> attributes)
		throws Exception {
	}

	@Override
	protected void onProcessSucceeded(List<Serializable> attributes)
		throws Exception {
	}

}