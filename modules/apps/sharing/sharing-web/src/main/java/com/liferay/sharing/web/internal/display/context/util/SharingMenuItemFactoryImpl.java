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

package com.liferay.sharing.web.internal.display.context.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.sharing.display.context.util.SharingJavaScriptFactory;
import com.liferay.sharing.display.context.util.SharingMenuItemFactory;
import com.liferay.sharing.display.context.util.SharingToolbarItemFactory;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	service = {SharingMenuItemFactory.class, SharingToolbarItemFactory.class}
)
public class SharingMenuItemFactoryImpl
	implements SharingMenuItemFactory, SharingToolbarItemFactory {

	@Override
	public MenuItem createShareMenuItem(
			String className, long classPK, HttpServletRequest request)
		throws PortalException {

		JavaScriptMenuItem javaScriptMenuItem = new JavaScriptMenuItem();

		javaScriptMenuItem.setJavaScript(
			_sharingJavaScriptFactory.createSharingJavaScript(request));
		javaScriptMenuItem.setKey("#share");
		javaScriptMenuItem.setLabel(_language.get(request, "share"));
		javaScriptMenuItem.setOnClick(
			_sharingJavaScriptFactory.createSharingOnclickMethod(
				className, classPK, request));

		return javaScriptMenuItem;
	}

	@Override
	public ToolbarItem createShareToolbarItem(
			String className, long classPK, HttpServletRequest request)
		throws PortalException {

		JavaScriptToolbarItem javaScriptToolbarItem =
			new JavaScriptToolbarItem();

		javaScriptToolbarItem.setJavaScript(
			_sharingJavaScriptFactory.createSharingJavaScript(request));
		javaScriptToolbarItem.setKey("#share");
		javaScriptToolbarItem.setLabel(_language.get(request, "share"));
		javaScriptToolbarItem.setOnClick(
			_sharingJavaScriptFactory.createSharingOnclickMethod(
				className, classPK, request));

		return javaScriptToolbarItem;
	}

	@Reference
	private Language _language;

	@Reference
	private SharingJavaScriptFactory _sharingJavaScriptFactory;

}