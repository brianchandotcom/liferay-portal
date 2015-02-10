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

package com.liferay.portal.lar.lifecycle;

import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleConstants;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleEvent;
import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleEventFactory;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class ExportImportLifecycleEventFactoryImpl
	implements ExportImportLifecycleEventFactory {

	@Override
	public ExportImportLifecycleEvent create(
		int code, Serializable... attributes) {

		ExportImportLifecycleEvent exportImportLifecycleEvent =
			new ExportImportLifecycleEventImpl();

		exportImportLifecycleEvent.setAttributes(attributes);
		exportImportLifecycleEvent.setCode(code);

		Map<Integer, Boolean> processFlags = new HashMap<>();

		processFlags.put(
			ExportImportLifecycleConstants.PROCESS_FLAG_EXPORT_IN_PROCESS,
			ExportImportThreadLocal.isExportInProcess());
		processFlags.put(
			ExportImportLifecycleConstants.PROCESS_FLAG_IMPORT_IN_PROCESS,
			ExportImportThreadLocal.isImportInProcess());
		processFlags.put(
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_EXPORT_IN_PROCESS,
			ExportImportThreadLocal.isLayoutExportInProcess());
		processFlags.put(
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS,
			ExportImportThreadLocal.isLayoutImportInProcess());
		processFlags.put(
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS,
			ExportImportThreadLocal.isLayoutStagingInProcess());
		processFlags.put(
			ExportImportLifecycleConstants.
				PROCESS_FLAG_LAYOUT_VALIDATION_IN_PROCESS,
			ExportImportThreadLocal.isLayoutValidationInProcess());
		processFlags.put(
			ExportImportLifecycleConstants.
				PROCESS_FLAG_PORTLET_EXPORT_IN_PROCESS,
			ExportImportThreadLocal.isPortletExportInProcess());
		processFlags.put(
			ExportImportLifecycleConstants.
				PROCESS_FLAG_PORTLET_IMPORT_IN_PROCESS,
			ExportImportThreadLocal.isPortletImportInProcess());
		processFlags.put(
			ExportImportLifecycleConstants.
				PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS,
			ExportImportThreadLocal.isPortletStagingInProcess());
		processFlags.put(
			ExportImportLifecycleConstants.
				PROCESS_FLAG_PORTLET_VALIDATION_IN_PROCESS,
			ExportImportThreadLocal.isPortletValidationInProcess());

		exportImportLifecycleEvent.setProcessFlags(processFlags);

		return exportImportLifecycleEvent;
	}

}