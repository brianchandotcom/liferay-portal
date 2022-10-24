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

package com.liferay.layout.content.page.editor.web.internal.info.display.url.provider;

import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.info.display.url.provider.InfoEditURLProviderTracker;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = {})
public class InfoEditURLProviderUtil {

	public static String getURLEdit(
			String className, Object object,
			HttpServletRequest httpServletRequest)
		throws Exception {

		InfoEditURLProvider<Object> infoEditURLProvider =
			(InfoEditURLProvider<Object>)
				_infoEditURLProviderTracker.getInfoEditURLProvider(className);

		if (infoEditURLProvider == null) {
			return null;
		}

		return infoEditURLProvider.getURL(object, httpServletRequest);
	}

	@Reference(unbind = "-")
	protected void setInfoEditURLProviderTracker(
		InfoEditURLProviderTracker infoEditURLProviderTracker) {

		_infoEditURLProviderTracker = infoEditURLProviderTracker;
	}

	private static InfoEditURLProviderTracker _infoEditURLProviderTracker;

}