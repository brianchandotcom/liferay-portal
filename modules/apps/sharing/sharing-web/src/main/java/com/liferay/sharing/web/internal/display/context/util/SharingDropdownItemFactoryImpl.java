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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.sharing.display.context.util.SharingDropdownItemFactory;
import com.liferay.sharing.display.context.util.SharingJavaScriptFactory;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(service = SharingDropdownItemFactory.class)
public class SharingDropdownItemFactoryImpl
	implements SharingDropdownItemFactory {

	@Override
	public DropdownItem createManageCollaboratorsDropdownItem(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return DropdownItemBuilder.setHref(
			() -> {
				String manageCollaboratorsOnClickMethod =
					_sharingJavaScriptFactory.
						createManageCollaboratorsOnClickMethod(
							className, classPK, httpServletRequest);

				return "javascript:" + manageCollaboratorsOnClickMethod;
			}
		).setLabel(
			SharingItemFactoryUtil.getManageCollaboratorsLabel(
				httpServletRequest)
		).build();
	}

	@Override
	public DropdownItem createShareDropdownItem(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return DropdownItemBuilder.setHref(
			() -> {
				String sharingOnClickMethod =
					_sharingJavaScriptFactory.createSharingOnClickMethod(
						className, classPK, httpServletRequest);

				return "javascript:" + sharingOnClickMethod;
			}
		).setIcon(
			"share"
		).setLabel(
			SharingItemFactoryUtil.getSharingLabel(httpServletRequest)
		).build();
	}

	@Reference
	private SharingJavaScriptFactory _sharingJavaScriptFactory;

}