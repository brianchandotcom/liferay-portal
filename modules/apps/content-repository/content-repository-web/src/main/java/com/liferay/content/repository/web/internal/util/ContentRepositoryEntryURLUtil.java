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

package com.liferay.content.repository.web.internal.util;

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.RenderURL;

/**
 * @author Alejandro Tardín
 */
public class ContentRepositoryEntryURLUtil {

	public static ActionURL getAddContentRepositoryEntryActionURL(
		String redirect, LiferayPortletResponse liferayPortletResponse) {

		ActionURL addContentRepositoryURL =
			liferayPortletResponse.createActionURL();

		addContentRepositoryURL.setParameter(
			ActionRequest.ACTION_NAME, "/content_repository_entry/add");

		addContentRepositoryURL.setParameter("redirect", redirect);

		return addContentRepositoryURL;
	}

	public static ActionURL getEditContentRepositoryEntryActionURL(
		LiferayPortletResponse liferayPortletResponse) {

		ActionURL editContentRepositoryEntryActionURL =
			liferayPortletResponse.createActionURL();

		editContentRepositoryEntryActionURL.setParameter(
			ActionRequest.ACTION_NAME, "/content_repository_entry/edit");

		return editContentRepositoryEntryActionURL;
	}

	public static RenderURL getEditContentRepositoryEntryRenderURL(
		long contentRepositoryEntryId, String redirect,
		LiferayPortletResponse liferayPortletResponse) {

		RenderURL editContentRepositoryEntryURL =
			liferayPortletResponse.createRenderURL();

		editContentRepositoryEntryURL.setParameter(
			"contentRepositoryEntryId",
			String.valueOf(contentRepositoryEntryId));

		editContentRepositoryEntryURL.setParameter(
			"mvcRenderCommandName", "/content_repository_entry/edit");

		editContentRepositoryEntryURL.setParameter("redirect", redirect);

		return editContentRepositoryEntryURL;
	}

}