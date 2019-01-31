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

package com.liferay.sharing.taglib.internal.servlet;

import com.liferay.sharing.display.context.util.SharingJavaScriptFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class SharingJavaScriptFactoryUtil {

	public static final SharingJavaScriptFactory getSharingJavaScriptFactory() {
		return _sharingJavaScriptFactory;
	}

	@Reference(unbind = "-")
	protected void setSharingOnclickMethodFactory(
		SharingJavaScriptFactory sharingJavaScriptFactory) {

		_sharingJavaScriptFactory = sharingJavaScriptFactory;
	}

	private static SharingJavaScriptFactory _sharingJavaScriptFactory;

}