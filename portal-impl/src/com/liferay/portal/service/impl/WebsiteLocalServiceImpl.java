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

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.WebsiteURLException;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.service.base.WebsiteLocalServiceBaseImpl;

import java.util.List;

import org.apache.commons.validator.routines.UrlValidator;

/**
 * @author Brian Wing Shun Chan
 */
public class WebsiteLocalServiceImpl extends WebsiteLocalServiceBaseImpl {

	@Override
	public Website addWebsite(
			long userId, String className, long classPK, String url,
			long typeId, boolean primary, ServiceContext serviceContext)
		throws PortalException {

		User user = _userPersistence.findByPrimaryKey(userId);
		long classNameId = _classNameLocalService.getClassNameId(className);

		validate(
			0, user.getCompanyId(), classNameId, classPK, url, typeId, primary);

		long websiteId = counterLocalService.increment();

		Website website = websitePersistence.create(websiteId);

		website.setUuid(serviceContext.getUuid());
		website.setCompanyId(user.getCompanyId());
		website.setUserId(user.getUserId());
		website.setUserName(user.getFullName());
		website.setClassNameId(classNameId);
		website.setClassPK(classPK);
		website.setUrl(url);
		website.setTypeId(typeId);
		website.setPrimary(primary);

		return websitePersistence.update(website);
	}

	@Override
	public Website deleteWebsite(long websiteId) throws PortalException {
		Website website = websitePersistence.findByPrimaryKey(websiteId);

		return websiteLocalService.deleteWebsite(website);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public Website deleteWebsite(Website website) {
		websitePersistence.remove(website);

		return website;
	}

	@Override
	public void deleteWebsites(long companyId, String className, long classPK) {
		List<Website> websites = websitePersistence.findByC_C_C(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK);

		for (Website website : websites) {
			websiteLocalService.deleteWebsite(website);
		}
	}

	@Override
	public List<Website> getWebsites() {
		return websitePersistence.findAll();
	}

	@Override
	public List<Website> getWebsites(
		long companyId, String className, long classPK) {

		return websitePersistence.findByC_C_C(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK);
	}

	@Override
	public Website updateWebsite(
			long websiteId, String url, long typeId, boolean primary)
		throws PortalException {

		validate(websiteId, 0, 0, 0, url, typeId, primary);

		Website website = websitePersistence.findByPrimaryKey(websiteId);

		website.setUrl(url);
		website.setTypeId(typeId);
		website.setPrimary(primary);

		return websitePersistence.update(website);
	}

	protected void validate(
		long websiteId, long companyId, long classNameId, long classPK,
		boolean primary) {

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
			String url, long typeId, boolean primary)
		throws PortalException {

		UrlValidator urlValidator = new UrlValidator();

		if (!urlValidator.isValid(url)) {
			throw new WebsiteURLException(url);
		}

		if (websiteId > 0) {
			Website website = websitePersistence.findByPrimaryKey(websiteId);

			companyId = website.getCompanyId();
			classNameId = website.getClassNameId();
			classPK = website.getClassPK();
		}

		_listTypeLocalService.validate(
			typeId, classNameId, ListTypeConstants.WEBSITE);

		validate(websiteId, companyId, classNameId, classPK, primary);
	}

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

	@BeanReference(type = ListTypeLocalService.class)
	private ListTypeLocalService _listTypeLocalService;

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}