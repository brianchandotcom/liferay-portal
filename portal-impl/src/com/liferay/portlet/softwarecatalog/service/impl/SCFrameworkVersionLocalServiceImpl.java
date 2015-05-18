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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.softwarecatalog.FrameworkVersionNameException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.service.base.SCFrameworkVersionLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SCFrameworkVersionLocalServiceImpl
	extends SCFrameworkVersionLocalServiceBaseImpl {

	@Override
	public SCFrameworkVersion addFrameworkVersion(
			long userId, String name, String url, boolean active, int priority,
			ServiceContext serviceContext)
		throws PortalException {

		// Framework version

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		validate(name);

		long frameworkVersionId = counterLocalService.increment();

		SCFrameworkVersion frameworkVersion =
			scFrameworkVersionPersistence.create(frameworkVersionId);

		frameworkVersion.setGroupId(groupId);
		frameworkVersion.setCompanyId(user.getCompanyId());
		frameworkVersion.setUserId(user.getUserId());
		frameworkVersion.setUserName(user.getFullName());
		frameworkVersion.setName(name);
		frameworkVersion.setUrl(url);
		frameworkVersion.setActive(active);
		frameworkVersion.setPriority(priority);

		scFrameworkVersionPersistence.update(frameworkVersion);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addFrameworkVersionResources(
				frameworkVersion, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addFrameworkVersionResources(
				frameworkVersion, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return frameworkVersion;
	}

	@Override
	public void addFrameworkVersionResources(
			long frameworkVersionId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		SCFrameworkVersion frameworkVersion =
			scFrameworkVersionPersistence.findByPrimaryKey(frameworkVersionId);

		addFrameworkVersionResources(
			frameworkVersion, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFrameworkVersionResources(
			long frameworkVersionId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		SCFrameworkVersion frameworkVersion =
			scFrameworkVersionPersistence.findByPrimaryKey(frameworkVersionId);

		addFrameworkVersionResources(
			frameworkVersion, groupPermissions, guestPermissions);
	}

	@Override
	public void addFrameworkVersionResources(
			SCFrameworkVersion frameworkVersion, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			frameworkVersion.getCompanyId(), frameworkVersion.getGroupId(),
			frameworkVersion.getUserId(), SCFrameworkVersion.class.getName(),
			frameworkVersion.getFrameworkVersionId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFrameworkVersionResources(
			SCFrameworkVersion frameworkVersion, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			frameworkVersion.getCompanyId(), frameworkVersion.getGroupId(),
			frameworkVersion.getUserId(), SCFrameworkVersion.class.getName(),
			frameworkVersion.getFrameworkVersionId(), groupPermissions,
			guestPermissions);
	}

	@Override
	public void deleteFrameworkVersion(long frameworkVersionId)
		throws PortalException {

		SCFrameworkVersion frameworkVersion =
			scFrameworkVersionPersistence.findByPrimaryKey(frameworkVersionId);

		deleteFrameworkVersion(frameworkVersion);
	}

	@Override
	public void deleteFrameworkVersion(SCFrameworkVersion frameworkVersion) {
		scFrameworkVersionPersistence.remove(frameworkVersion);
	}

	@Override
	public void deleteFrameworkVersions(long groupId) {
		List<SCFrameworkVersion> frameworkVersions =
			scFrameworkVersionPersistence.findByGroupId(groupId);

		for (SCFrameworkVersion frameworkVersion : frameworkVersions) {
			deleteFrameworkVersion(frameworkVersion);
		}
	}

	@Override
	public SCFrameworkVersion getFrameworkVersion(long frameworkVersionId)
		throws PortalException {

		return scFrameworkVersionPersistence.findByPrimaryKey(
			frameworkVersionId);
	}

	@Override
	public List<SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active) {

		return scFrameworkVersionPersistence.findByG_A(groupId, active);
	}

	@Override
	public List<SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end) {

		return scFrameworkVersionPersistence.findByG_A(
			groupId, active, start, end);
	}

	@Override
	public List<SCFrameworkVersion> getFrameworkVersions(
		long groupId, int start, int end) {

		return scFrameworkVersionPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public int getFrameworkVersionsCount(long groupId) {
		return scFrameworkVersionPersistence.countByGroupId(groupId);
	}

	@Override
	public int getFrameworkVersionsCount(long groupId, boolean active) {
		return scFrameworkVersionPersistence.countByG_A(groupId, active);
	}

	@Override
	public List<SCFrameworkVersion> getProductVersionFrameworkVersions(
		long productVersionId) {

		return scProductVersionPersistence.getSCFrameworkVersions(
			productVersionId);
	}

	@Override
	public SCFrameworkVersion updateFrameworkVersion(
			long frameworkVersionId, String name, String url, boolean active,
			int priority)
		throws PortalException {

		validate(name);

		SCFrameworkVersion frameworkVersion =
			scFrameworkVersionPersistence.findByPrimaryKey(frameworkVersionId);

		frameworkVersion.setName(name);
		frameworkVersion.setUrl(url);
		frameworkVersion.setActive(active);
		frameworkVersion.setPriority(priority);

		scFrameworkVersionPersistence.update(frameworkVersion);

		return frameworkVersion;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new FrameworkVersionNameException();
		}
	}

}