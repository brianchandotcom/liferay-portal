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

package com.liferay.trash.internal.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.helper.TrashHelper;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = TrashHelper.class)
public class TrashHelperImpl implements TrashHelper {

	@Override
	public String getOriginalTitle(String title) {
		return _trashHelper.getOriginalTitle(title);
	}

	@Override
	public String getOriginalTitle(String title, String paramName) {
		return _trashHelper.getOriginalTitle(title, paramName);
	}

	@Override
	public String getTrashTitle(long entryId) {
		return _trashHelper.getTrashTitle(entryId);
	}

	@Override
	public PortletURL getViewContentURL(
			HttpServletRequest httpServletRequest, String className,
			long classPK)
		throws PortalException {

		return _trashHelper.getViewContentURL(
			httpServletRequest, className, classPK);
	}

	@Reference
	private com.liferay.trash.TrashHelper _trashHelper;

}