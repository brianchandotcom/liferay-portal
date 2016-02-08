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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.CommonPermissionUtil;
import com.liferay.portal.service.base.WebsiteServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WebsiteServiceImpl extends WebsiteServiceBaseImpl {

	@Override
	public Website addWebsite(
			String className, long classPK, String url, long typeId,
			boolean primary, ServiceContext serviceContext)
		throws PortalException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return websiteLocalService.addWebsite(
			getUserId(), className, classPK, url, typeId, primary,
			serviceContext);
	}

	@Override
	public void deleteWebsite(long websiteId) throws PortalException {
		Website website = websitePersistence.findByPrimaryKey(websiteId);

		CommonPermissionUtil.check(
			getPermissionChecker(), website.getClassNameId(),
			website.getClassPK(), ActionKeys.UPDATE);

		websiteLocalService.deleteWebsite(website);
	}

	@Override
	public Website getWebsite(long websiteId) throws PortalException {
		Website website = websitePersistence.findByPrimaryKey(websiteId);

		CommonPermissionUtil.check(
			getPermissionChecker(), website.getClassNameId(),
			website.getClassPK(), ActionKeys.VIEW);

		return website;
	}

	@Override
	public List<Website> getWebsites(String className, long classPK)
		throws PortalException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.VIEW);

		User user = getUser();

		return websiteLocalService.getWebsites(
			user.getCompanyId(), className, classPK);
	}

	@Override
	public Website updateWebsite(
			long websiteId, String url, long typeId, boolean primary)
		throws PortalException {

		Website website = websitePersistence.findByPrimaryKey(websiteId);

		CommonPermissionUtil.check(
			getPermissionChecker(), website.getClassNameId(),
			website.getClassPK(), ActionKeys.UPDATE);

		return websiteLocalService.updateWebsite(
			websiteId, url, typeId, primary);
	}

}