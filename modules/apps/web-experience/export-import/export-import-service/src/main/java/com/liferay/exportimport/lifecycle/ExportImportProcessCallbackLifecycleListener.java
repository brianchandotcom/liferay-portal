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

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lifecycle.BaseProcessExportImportLifecycleListener;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.lar.ExportImportProcessCallbackUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import java.util.List;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = ExportImportLifecycleListener.class)
@ProviderType
public class ExportImportProcessCallbackLifecycleListener
	extends BaseProcessExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	protected void onProcessFailed(List<Serializable> attributes)
		throws Exception {

		ExportImportProcessCallbackUtil.popCallbackList();
	}

	@Override
	protected void onProcessStarted(List<Serializable> attributes)
		throws Exception {

		ExportImportProcessCallbackUtil.pushCallbackList();
	}

	@Override
	protected void onProcessSucceeded(List<Serializable> attributes)
		throws Exception {

		List<Callable<?>> callables =
			ExportImportProcessCallbackUtil.popCallbackList();

		for (Callable<?> callable : callables) {
			try {
				callable.call();
			}
			catch (Exception e) {
				_log.error(
					"Unable to execute export import process callback", e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportProcessCallbackLifecycleListener.class);

};