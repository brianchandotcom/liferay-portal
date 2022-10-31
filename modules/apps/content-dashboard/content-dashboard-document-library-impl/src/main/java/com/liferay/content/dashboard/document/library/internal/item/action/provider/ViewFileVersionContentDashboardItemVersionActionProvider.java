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

package com.liferay.content.dashboard.document.library.internal.item.action.provider;

import com.liferay.content.dashboard.document.library.internal.item.action.ViewFileVersionContentDashboardItemVersionAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemVersionActionProvider;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "service.ranking:Integer=200",
	service = ContentDashboardItemVersionActionProvider.class
)
public class ViewFileVersionContentDashboardItemVersionActionProvider
	implements ContentDashboardItemVersionActionProvider<FileVersion> {

	@Override
	public ContentDashboardItemVersionAction
		getContentDashboardItemVersionAction(
			FileVersion fileVersion, HttpServletRequest httpServletRequest) {

		if (!isShow(fileVersion, httpServletRequest)) {
			return null;
		}

		return new ViewFileVersionContentDashboardItemVersionAction(
			fileVersion, httpServletRequest, _language, _portal,
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest));
	}

	@Override
	public boolean isShow(
		FileVersion fileVersion, HttpServletRequest httpServletRequest) {

		return true;
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}