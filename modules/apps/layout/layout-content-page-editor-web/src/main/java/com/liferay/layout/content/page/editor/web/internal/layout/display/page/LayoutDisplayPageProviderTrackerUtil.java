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

package com.liferay.layout.content.page.editor.web.internal.layout.display.page;

import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = {})
public class LayoutDisplayPageProviderTrackerUtil {

	public static LayoutDisplayPageProvider<?> getLayoutDisplayPageProvider(
		String className) {

		return _layoutDisplayPageProviderTracker.
			getLayoutDisplayPageProviderByClassName(className);
	}

	@Reference(unbind = "-")
	protected void setLayoutDisplayPageProviderTracker(
		LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker) {

		_layoutDisplayPageProviderTracker = layoutDisplayPageProviderTracker;
	}

	private static LayoutDisplayPageProviderTracker
		_layoutDisplayPageProviderTracker;

}