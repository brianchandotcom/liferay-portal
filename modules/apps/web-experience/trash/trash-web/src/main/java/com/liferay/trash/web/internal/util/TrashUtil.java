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

package com.liferay.trash.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = TrashUtil.class)
public class TrashUtil {

	public void addBaseModelBreadcrumbEntries(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classPK, PortletURL containerModelURL)
		throws PortalException, PortletException {

		addBreadcrumbEntries(
			request, liferayPortletResponse, className, classPK, "classPK",
			containerModelURL, true);
	}

	public void addContainerModelBreadcrumbEntries(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classPK, PortletURL containerModelURL)
		throws PortalException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		String rootContainerModelTitle = LanguageUtil.get(
			themeDisplay.getLocale(), trashHandler.getRootContainerModelName());

		if (classPK == 0) {
			_portal.addPortletBreadcrumbEntry(
				request, rootContainerModelTitle, null);

			return;
		}

		containerModelURL.setParameter("containerModelId", "0");

		_portal.addPortletBreadcrumbEntry(
			request, rootContainerModelTitle, containerModelURL.toString());

		addBreadcrumbEntries(
			request, liferayPortletResponse, className, classPK,
			"containerModelId", containerModelURL, false);
	}

	protected void addBreadcrumbEntries(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classPK, String paramName, PortletURL containerModelURL,
			boolean checkInTrashContainers)
		throws PortalException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLUtil.clone(
			containerModelURL, liferayPortletResponse);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		List<ContainerModel> containerModels =
			trashHandler.getParentContainerModels(classPK);

		Collections.reverse(containerModels);

		for (ContainerModel containerModel : containerModels) {
			TrashHandler containerModelTrashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					containerModel.getModelClassName());

			if (checkInTrashContainers &&
				!containerModelTrashHandler.isInTrash(
					containerModel.getContainerModelId())) {

				continue;
			}

			portletURL.setParameter(
				paramName,
				String.valueOf(containerModel.getContainerModelId()));

			String name = containerModel.getContainerModelName();

			if (containerModelTrashHandler.isInTrash(
					containerModel.getContainerModelId())) {

				name = _trashHelper.getOriginalTitle(name);
			}

			_portal.addPortletBreadcrumbEntry(
				request, name, portletURL.toString());
		}

		TrashRenderer trashRenderer = trashHandler.getTrashRenderer(classPK);

		_portal.addPortletBreadcrumbEntry(
			request, trashRenderer.getTitle(themeDisplay.getLocale()), null);
	}

	@Reference
	private Portal _portal;

	@Reference
	private TrashHelper _trashHelper;

}