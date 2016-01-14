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

package com.liferay.document.library.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Repository;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.taglib.security.PermissionsURLTag;

import javax.portlet.PortletRequest;

/**
 * @author Roberto Díaz
 */
public class RepositoryPermissionPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public RepositoryPermissionPortletConfigurationIcon(
		PortletRequest portletRequest, Repository repository) {

		super(portletRequest);

		_repository = repository;
	}

	@Override
	public String getMessage() {
		return "permissions";
	}

	@Override
	public String getURL() {
		String url = StringPool.BLANK;

		try {
			url = PermissionsURLTag.doTag(
				null, DLFolderConstants.getClassName(),
				HtmlUtil.unescape(_repository.getName()), null,
				String.valueOf(_repository.getDlFolderId()),
				LiferayWindowState.POP_UP.toString(), null,
				themeDisplay.getRequest());
		}
		catch (Exception e) {
		}

		return url;
	}

	@Override
	public boolean isShow() {
		if (_repository != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

	private final Repository _repository;

}