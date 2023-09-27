/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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