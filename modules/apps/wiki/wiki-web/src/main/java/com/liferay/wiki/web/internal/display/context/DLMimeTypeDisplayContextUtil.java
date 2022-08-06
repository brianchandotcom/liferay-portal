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

package com.liferay.wiki.web.internal.display.context;

import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.portal.kernel.repository.model.FileVersion;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = {})
public class DLMimeTypeDisplayContextUtil {

	public static String getCssClassFileMimeType(FileVersion fileVersion) {
		return _dlMimeTypeDisplayContextUtil._dlMimeTypeDisplayContext.
			getCssClassFileMimeType(fileVersion.getMimeType());
	}

	public static String getIconFileMimeType(FileVersion fileVersion) {
		return _dlMimeTypeDisplayContextUtil._dlMimeTypeDisplayContext.
			getIconFileMimeType(fileVersion.getMimeType());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlMimeTypeDisplayContextUtil = this;
	}

	@Deactivate
	protected void deactivate() {
		_dlMimeTypeDisplayContextUtil = null;
	}

	private static DLMimeTypeDisplayContextUtil _dlMimeTypeDisplayContextUtil;

	@Reference
	private DLMimeTypeDisplayContext _dlMimeTypeDisplayContext;

}