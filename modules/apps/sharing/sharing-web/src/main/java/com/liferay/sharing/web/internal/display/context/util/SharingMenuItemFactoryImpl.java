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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.sharing.display.context.util.SharingDropdownItemFactory;
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
	service = {
		SharingDropdownItemFactory.class, SharingMenuItemFactory.class,
		SharingToolbarItemFactory.class
	}
)
public class SharingMenuItemFactoryImpl
	implements SharingDropdownItemFactory, SharingMenuItemFactory,
			   SharingToolbarItemFactory {

	@Override
	public DropdownItem createManageCollaboratorsDropdownItem(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		DropdownItem dropdownItem = new DropdownItem();

		dropdownItem.setHref(
			"javascript:" +
				_sharingJavaScriptFactory.
					createManageCollaboratorsOnClickMethod(
						className, classPK, httpServletRequest));

		dropdownItem.setLabel(
			LanguageUtil.get(httpServletRequest, "manage-collaborators"));

		return dropdownItem;
	}

	@Override
	public MenuItem createManageCollaboratorsMenuItem(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		JavaScriptMenuItem javaScriptMenuItem = new JavaScriptMenuItem();

		javaScriptMenuItem.setKey("#manage-collaborators");
		javaScriptMenuItem.setLabel(
			LanguageUtil.get(httpServletRequest, "manage-collaborators"));
		javaScriptMenuItem.setOnClick(
			_sharingJavaScriptFactory.createManageCollaboratorsOnClickMethod(
				className, classPK, httpServletRequest));

		return javaScriptMenuItem;
	}

	@Override
	public ToolbarItem createManageCollaboratorsToolbarItem(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		JavaScriptToolbarItem javaScriptToolbarItem =
			new JavaScriptToolbarItem();

		javaScriptToolbarItem.setKey("#manage-collaborators");
		javaScriptToolbarItem.setLabel(
			LanguageUtil.get(httpServletRequest, "manage-collaborators"));
		javaScriptToolbarItem.setOnClick(
			_sharingJavaScriptFactory.createManageCollaboratorsOnClickMethod(
				className, classPK, httpServletRequest));

		return javaScriptToolbarItem;
	}

	@Override
	public DropdownItem createShareDropdownItem(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		DropdownItem dropdownItem = new DropdownItem();

		dropdownItem.setHref(
			"javascript:" +
				_sharingJavaScriptFactory.createSharingOnClickMethod(
					className, classPK, httpServletRequest));

		dropdownItem.setLabel(LanguageUtil.get(httpServletRequest, "share"));

		return dropdownItem;
	}

	@Override
	public MenuItem createShareMenuItem(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		JavaScriptMenuItem javaScriptMenuItem = new JavaScriptMenuItem();

		javaScriptMenuItem.setKey("#share");
		javaScriptMenuItem.setLabel(
			LanguageUtil.get(httpServletRequest, "share"));
		javaScriptMenuItem.setOnClick(
			_sharingJavaScriptFactory.createSharingOnClickMethod(
				className, classPK, httpServletRequest));

		return javaScriptMenuItem;
	}

	@Override
	public ToolbarItem createShareToolbarItem(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		JavaScriptToolbarItem javaScriptToolbarItem =
			new JavaScriptToolbarItem();

		javaScriptToolbarItem.setKey("#share");
		javaScriptToolbarItem.setLabel(
			LanguageUtil.get(httpServletRequest, "share"));
		javaScriptToolbarItem.setOnClick(
			_sharingJavaScriptFactory.createSharingOnClickMethod(
				className, classPK, httpServletRequest));

		return javaScriptToolbarItem;
	}

	@Reference
	private SharingJavaScriptFactory _sharingJavaScriptFactory;

}