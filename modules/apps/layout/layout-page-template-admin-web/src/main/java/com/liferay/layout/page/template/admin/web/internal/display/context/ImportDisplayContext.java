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

package com.liferay.layout.page.template.admin.web.internal.display.context;

import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporterResultEntry;
import com.liferay.portal.kernel.servlet.SessionMessages;

import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Jürgen Kappler
 */
public class ImportDisplayContext {

	public ImportDisplayContext(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public List<LayoutPageTemplatesImporterResultEntry>
		getNotImportedLayoutPageTemplatesImporterResultEntries() {

		return (List<LayoutPageTemplatesImporterResultEntry>)
			SessionMessages.get(
				_renderRequest,
				"notImportedLayoutPageTemplatesImporterResultEntries");
	}

	private final RenderRequest _renderRequest;

}