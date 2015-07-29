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

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleEventListenerRegistryUtil;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleListener;

import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {"destination.name=liferay/export_import_lifecycle_event_async"},
	service = MessageListener.class
)
public class AsyncExportImportLifecycleMessageListener
	extends BaseExportImportLifecycleMessageListener {

	protected Set<ExportImportLifecycleListener>
		getExportImportLifecycleListeners(Message message) {

		return ExportImportLifecycleEventListenerRegistryUtil.
			getAsyncExportImportLifecycleListeners();
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

}