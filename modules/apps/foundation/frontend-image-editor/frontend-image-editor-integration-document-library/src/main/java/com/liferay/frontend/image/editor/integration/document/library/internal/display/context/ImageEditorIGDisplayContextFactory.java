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

package com.liferay.frontend.image.editor.integration.document.library.internal.display.context;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.image.gallery.display.kernel.display.context.IGDisplayContextFactory;
import com.liferay.image.gallery.display.kernel.display.context.IGViewFileVersionDisplayContext;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrin Chaudhary
 */
@Component(immediate = true, service = IGDisplayContextFactory.class)
public class ImageEditorIGDisplayContextFactory
	implements IGDisplayContextFactory {

	public IGViewFileVersionDisplayContext getIGViewFileVersionDisplayContext(
		IGViewFileVersionDisplayContext parentIGViewFileVersionDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileShortcut fileShortcut) {

		return parentIGViewFileVersionDisplayContext;
	}

	public IGViewFileVersionDisplayContext getIGViewFileVersionDisplayContext(
		IGViewFileVersionDisplayContext parentIGViewFileVersionDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion) {

		Object model = fileVersion.getModel();

		if (model instanceof DLFileVersion) {
			return new ImageEditorIGViewFileVersionDisplayContext(
				parentIGViewFileVersionDisplayContext, request, response,
				fileVersion, _resourceBundleLoader);
		}

		return parentIGViewFileVersionDisplayContext;
	}

	@Reference(unbind = "-")
	public void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.frontend.image.editor.integration.document.library)",
		unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = resourceBundleLoader;
	}

	private DLAppService _dlAppService;
	private ResourceBundleLoader _resourceBundleLoader;

}