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

package com.liferay.headless.foundation.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import java.util.Date;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface UserAccount {

	public String getAdditionalName();

	public void setAdditionalName(
			String additionalName);

	public void setAdditionalName(
			UnsafeSupplier<String, Throwable>
				additionalNameUnsafeSupplier);
	public String getAlternateName();

	public void setAlternateName(
			String alternateName);

	public void setAlternateName(
			UnsafeSupplier<String, Throwable>
				alternateNameUnsafeSupplier);
	public Date getBirthDate();

	public void setBirthDate(
			Date birthDate);

	public void setBirthDate(
			UnsafeSupplier<Date, Throwable>
				birthDateUnsafeSupplier);
	public ContactInformation getContactInformation();

	public void setContactInformation(
			ContactInformation contactInformation);

	public void setContactInformation(
			UnsafeSupplier<ContactInformation, Throwable>
				contactInformationUnsafeSupplier);
	public String getDashboardURL();

	public void setDashboardURL(
			String dashboardURL);

	public void setDashboardURL(
			UnsafeSupplier<String, Throwable>
				dashboardURLUnsafeSupplier);
	public String getEmail();

	public void setEmail(
			String email);

	public void setEmail(
			UnsafeSupplier<String, Throwable>
				emailUnsafeSupplier);
	public String getFamilyName();

	public void setFamilyName(
			String familyName);

	public void setFamilyName(
			UnsafeSupplier<String, Throwable>
				familyNameUnsafeSupplier);
	public String getGivenName();

	public void setGivenName(
			String givenName);

	public void setGivenName(
			UnsafeSupplier<String, Throwable>
				givenNameUnsafeSupplier);
	public String getHonorificPrefix();

	public void setHonorificPrefix(
			String honorificPrefix);

	public void setHonorificPrefix(
			UnsafeSupplier<String, Throwable>
				honorificPrefixUnsafeSupplier);
	public String getHonorificSuffix();

	public void setHonorificSuffix(
			String honorificSuffix);

	public void setHonorificSuffix(
			UnsafeSupplier<String, Throwable>
				honorificSuffixUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public String getImage();

	public void setImage(
			String image);

	public void setImage(
			UnsafeSupplier<String, Throwable>
				imageUnsafeSupplier);
	public String getJobTitle();

	public void setJobTitle(
			String jobTitle);

	public void setJobTitle(
			UnsafeSupplier<String, Throwable>
				jobTitleUnsafeSupplier);
	public Organization[] getMyOrganizations();

	public void setMyOrganizations(
			Organization[] myOrganizations);

	public void setMyOrganizations(
			UnsafeSupplier<Organization[], Throwable>
				myOrganizationsUnsafeSupplier);
	public Long[] getMyOrganizationsIds();

	public void setMyOrganizationsIds(
			Long[] myOrganizationsIds);

	public void setMyOrganizationsIds(
			UnsafeSupplier<Long[], Throwable>
				myOrganizationsIdsUnsafeSupplier);
	public String getName();

	public void setName(
			String name);

	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier);
	public String getProfileURL();

	public void setProfileURL(
			String profileURL);

	public void setProfileURL(
			UnsafeSupplier<String, Throwable>
				profileURLUnsafeSupplier);
	public Role[] getRoles();

	public void setRoles(
			Role[] roles);

	public void setRoles(
			UnsafeSupplier<Role[], Throwable>
				rolesUnsafeSupplier);
	public Long[] getRolesIds();

	public void setRolesIds(
			Long[] rolesIds);

	public void setRolesIds(
			UnsafeSupplier<Long[], Throwable>
				rolesIdsUnsafeSupplier);
	public String[] getTasksAssignedToMe();

	public void setTasksAssignedToMe(
			String[] tasksAssignedToMe);

	public void setTasksAssignedToMe(
			UnsafeSupplier<String[], Throwable>
				tasksAssignedToMeUnsafeSupplier);
	public String[] getTasksAssignedToMyRoles();

	public void setTasksAssignedToMyRoles(
			String[] tasksAssignedToMyRoles);

	public void setTasksAssignedToMyRoles(
			UnsafeSupplier<String[], Throwable>
				tasksAssignedToMyRolesUnsafeSupplier);

}