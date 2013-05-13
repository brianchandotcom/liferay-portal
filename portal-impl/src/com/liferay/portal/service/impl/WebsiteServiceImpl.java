/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.WebsiteServiceBaseImpl;
import com.liferay.portal.service.permission.CommonPermissionUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WebsiteServiceImpl extends WebsiteServiceBaseImpl {

	@Override
	public Website addWebsite(
			String className, long classPK, String url, int typeId,
			boolean primary)
		throws PortalException, SystemException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return websiteLocalService.addWebsite(
			getUserId(), className, classPK, url, typeId, primary);
	}

	@Override
	public void deleteWebsite(long websiteId)
		throws PortalException, SystemException {

		Website website = websitePersistence.findByPrimaryKey(websiteId);

		CommonPermissionUtil.check(
			getPermissionChecker(), website.getClassNameId(),
			website.getClassPK(), ActionKeys.UPDATE);

		websiteLocalService.deleteWebsite(websiteId);
	}

	@Override
	public Website getWebsite(long websiteId)
		throws PortalException, SystemException {

		Website website = websitePersistence.findByPrimaryKey(websiteId);

		CommonPermissionUtil.check(
			getPermissionChecker(), website.getClassNameId(),
			website.getClassPK(), ActionKeys.VIEW);

		return website;
	}

	@Override
	public List<Website> getWebsites(String className, long classPK)
		throws PortalException, SystemException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.VIEW);

		User user = getUser();

		return websiteLocalService.getWebsites(
			user.getCompanyId(), className, classPK);
	}

	@Override
	public Website updateWebsite(
			long websiteId, String url, int typeId, boolean primary)
		throws PortalException, SystemException {

		Website website = websitePersistence.findByPrimaryKey(websiteId);

		CommonPermissionUtil.check(
			getPermissionChecker(), website.getClassNameId(),
			website.getClassPK(), ActionKeys.UPDATE);

		return websiteLocalService.updateWebsite(
			websiteId, url, typeId, primary);
	}

}