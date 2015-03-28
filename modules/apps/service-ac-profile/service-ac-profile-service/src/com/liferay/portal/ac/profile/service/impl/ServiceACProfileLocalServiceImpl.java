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

package com.liferay.portal.ac.profile.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.ac.profile.exception.DuplicateNameException;
import com.liferay.portal.ac.profile.model.ServiceACProfile;
import com.liferay.portal.ac.profile.service.base.ServiceACProfileLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The implementation of the service a c profile local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.ac.profile.service.ServiceACProfileLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.ac.profile.service.base.ServiceACProfileLocalServiceBaseImpl
 * @see com.liferay.portal.ac.profile.service.ServiceACProfileLocalServiceUtil
 */
@ProviderType
public class ServiceACProfileLocalServiceImpl
	extends ServiceACProfileLocalServiceBaseImpl {

	public ServiceACProfile addServiceACProfile(
			long companyId, long userId, String allowedServices, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		if (getServiceACProfile(companyId, name) != null) {
			throw new DuplicateNameException();
		}

		User user = userLocalService.getUserById(userId);

		Date now = new Date();

		long serviceACProfileId = counterLocalService.increment();

		ServiceACProfile serviceACProfile = serviceACProfilePersistence.create(
			serviceACProfileId);

		serviceACProfile.setAllowedServices(allowedServices);
		serviceACProfile.setCompanyId(companyId);
		serviceACProfile.setCreateDate(now);
		serviceACProfile.setModifiedDate(now);
		serviceACProfile.setName(name);
		serviceACProfile.setTitleMap(titleMap);
		serviceACProfile.setUserId(userId);
		serviceACProfile.setUserName(user.getFullName());
		serviceACProfile.setUserUuid(user.getUuid());

		serviceACProfile = serviceACProfilePersistence.update(
			serviceACProfile, serviceContext);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addServiceACProfileResources(
				serviceACProfile, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addServiceACProfileResources(
				serviceACProfile, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return serviceACProfile;
	}

	public void addServiceACProfileResources(
			ServiceACProfile serviceACProfile, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			serviceACProfile.getCompanyId(), 0, serviceACProfile.getUserId(),
			ServiceACProfile.class.getName(),
			serviceACProfile.getServiceACProfileId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	public void addServiceACProfileResources(
			ServiceACProfile serviceACProfile, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			serviceACProfile.getCompanyId(), 0, serviceACProfile.getUserId(),
			ServiceACProfile.class.getName(),
			serviceACProfile.getServiceACProfileId(), groupPermissions,
			guestPermissions);
	}

	public ServiceACProfile deleteServiceACProfile(long serviceACProfileId)
		throws PortalException {

		ServiceACProfile serviceACProfile =
			serviceACProfilePersistence.findByPrimaryKey(serviceACProfileId);

		return deleteServiceACProfile(serviceACProfile);
	}

	public ServiceACProfile deleteServiceACProfile(
			ServiceACProfile serviceACProfile)
		throws PortalException {

		serviceACProfilePersistence.remove(serviceACProfile);

		resourceLocalService.deleteResource(
			serviceACProfile.getCompanyId(), ServiceACProfile.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			serviceACProfile.getServiceACProfileId());

		return serviceACProfile;
	}

	public List<ServiceACProfile> getCompanyServiceACProfiles(
		long companyId, int start, int end) {

		return serviceACProfilePersistence.findByCompanyId(
			companyId, start, end);
	}

	public List<ServiceACProfile> getCompanyServiceACProfiles(
		long companyId, int start, int end,
		OrderByComparator<ServiceACProfile> obc) {

		return serviceACProfilePersistence.findByCompanyId(
			companyId, start, end, obc);
	}

	public int getCompanyServiceACProfilesCount(long companyId) {
		return serviceACProfilePersistence.countByCompanyId(companyId);
	}

	public ServiceACProfile getServiceACProfile(long companyId, String name)
		throws PortalException {

		return serviceACProfilePersistence.findByC_N(companyId, name);
	}

	public ServiceACProfile updateServiceACProfile(
			long serviceACProfileId, String allowedServices, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		ServiceACProfile serviceACProfile =
			serviceACProfilePersistence.findByPrimaryKey(serviceACProfileId);

		serviceACProfile.setAllowedServices(allowedServices);
		serviceACProfile.setName(name);
		serviceACProfile.setTitleMap(titleMap);

		serviceACProfile = serviceACProfilePersistence.update(
			serviceACProfile, serviceContext);

		// Resources

		if ((serviceContext.getGroupPermissions() != null) ||
			(serviceContext.getGuestPermissions() != null)) {

			updateServiceACProfileResources(
				serviceACProfile, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return serviceACProfile;
	}

	public void updateServiceACProfileResources(
			ServiceACProfile serviceACProfile, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.updateResources(
			serviceACProfile.getCompanyId(), 0,
			ServiceACProfile.class.getName(),
			serviceACProfile.getServiceACProfileId(), groupPermissions,
			guestPermissions);
	}

}