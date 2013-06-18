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

import com.liferay.portal.WebsiteURLException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.WebsiteLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WebsiteLocalServiceImpl extends WebsiteLocalServiceBaseImpl {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addWebsite(long, String,
	 *             long, String, int, boolean, ServiceContext)}
	 */
	@Override
	public Website addWebsite(
			long userId, String className, long classPK, String url, int typeId,
			boolean primary)
		throws PortalException, SystemException {

		return addWebsite(
			userId, className, classPK, url, typeId, primary,
			new ServiceContext());
	}

	@Override
	public Website addWebsite(
			long userId, String className, long classPK, String url, int typeId,
			boolean primary, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		validate(
			0, user.getCompanyId(), classNameId, classPK, url, typeId, primary);

		long websiteId = counterLocalService.increment();

		Website website = websitePersistence.create(websiteId);

		website.setUuid(serviceContext.getUuid());
		website.setCompanyId(user.getCompanyId());
		website.setUserId(user.getUserId());
		website.setUserName(user.getFullName());
		website.setCreateDate(now);
		website.setCreateDate(serviceContext.getCreateDate(now));
		website.setModifiedDate(serviceContext.getModifiedDate(now));
		website.setClassNameId(classNameId);
		website.setClassPK(classPK);
		website.setUrl(url);
		website.setTypeId(typeId);
		website.setPrimary(primary);

		websitePersistence.update(website);

		return website;
	}

	@Override
	public void deleteWebsites(long companyId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		List<Website> websites = websitePersistence.findByC_C_C(
			companyId, classNameId, classPK);

		for (Website website : websites) {
			deleteWebsite(website);
		}
	}

	@Override
	public List<Website> getWebsites() throws SystemException {
		return websitePersistence.findAll();
	}

	@Override
	public List<Website> getWebsites(
			long companyId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return websitePersistence.findByC_C_C(companyId, classNameId, classPK);
	}

	@Override
	public Website updateWebsite(
			long websiteId, String url, int typeId, boolean primary)
		throws PortalException, SystemException {

		validate(websiteId, 0, 0, 0, url, typeId, primary);

		Website website = websitePersistence.findByPrimaryKey(websiteId);

		website.setModifiedDate(new Date());
		website.setUrl(url);
		website.setTypeId(typeId);
		website.setPrimary(primary);

		websitePersistence.update(website);

		return website;
	}

	protected void validate(
			long websiteId, long companyId, long classNameId, long classPK,
			boolean primary)
		throws SystemException {

		// Check to make sure there isn't another website with the same company
		// id, class name, and class pk that also has primary set to true

		if (primary) {
			List<Website> websites = websitePersistence.findByC_C_C_P(
				companyId, classNameId, classPK, primary);

			for (Website website : websites) {
				if ((websiteId <= 0) || (website.getWebsiteId() != websiteId)) {
					website.setPrimary(false);

					websitePersistence.update(website);
				}
			}
		}
	}

	protected void validate(
			long websiteId, long companyId, long classNameId, long classPK,
			String url, int typeId, boolean primary)
		throws PortalException, SystemException {

		if (!Validator.isUrl(url)) {
			throw new WebsiteURLException();
		}

		if (websiteId > 0) {
			Website website = websitePersistence.findByPrimaryKey(websiteId);

			companyId = website.getCompanyId();
			classNameId = website.getClassNameId();
			classPK = website.getClassPK();
		}

		listTypeService.validate(
			typeId, classNameId, ListTypeConstants.WEBSITE);

		validate(websiteId, companyId, classNameId, classPK, primary);
	}

}