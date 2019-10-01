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

package com.liferay.content.repository.web.internal.display.context;

import com.liferay.content.repository.web.internal.constants.ContentRepositoryAdminWebKeys;
import com.liferay.content.repository.web.internal.util.ContentRepositoryAdminGroupSearchProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portlet.usersadmin.search.GroupSearch;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class ContentRepositoryAdminDisplayContext {

	public ContentRepositoryAdminDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_contentRepositoryAdminGroupSearchProvider =
			(ContentRepositoryAdminGroupSearchProvider)
				httpServletRequest.getAttribute(
					ContentRepositoryAdminWebKeys.
						CONTENT_REPOSITORY_ADMIN_GROUP_SEARCH_PROVIDER);
	}

	public String getDisplayStyle() {
		return "icon";
	}

	public GroupSearch getSearchContainer() {
		GroupSearch groupSearch =
			_contentRepositoryAdminGroupSearchProvider.getGroupSearch(
				_liferayPortletRequest,
				_liferayPortletResponse.createRenderURL());

		groupSearch.setId("content-repositories");

		return groupSearch;
	}

	private final ContentRepositoryAdminGroupSearchProvider
		_contentRepositoryAdminGroupSearchProvider;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}