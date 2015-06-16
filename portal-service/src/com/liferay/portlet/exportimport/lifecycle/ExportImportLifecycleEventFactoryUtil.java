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

package com.liferay.portlet.exportimport.lifecycle;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.io.Serializable;

/**
 * @author Daniel Kocsis
 */
public class ExportImportLifecycleEventFactoryUtil {

	public static ExportImportLifecycleEvent create(
		int code, int processFlag, Serializable... attributes) {

		return getExportImportLifecycleEventFactory().create(
			code, processFlag, attributes);
	}

	public static ExportImportLifecycleEventFactory
		getExportImportLifecycleEventFactory() {

		PortalRuntimePermission.checkGetBeanProperty(
			ExportImportLifecycleEventFactoryUtil.class);

		return _exportImportLifecycleEventFactory;
	}

	public void setExportImportLifecycleEventFactory(
		ExportImportLifecycleEventFactory exportImportLifecycleEventFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_exportImportLifecycleEventFactory = exportImportLifecycleEventFactory;
	}

	private static ExportImportLifecycleEventFactory
		_exportImportLifecycleEventFactory;

}