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

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface Organization {

	public String getComment();

	public ContactInformation getContactInformation();

	public Long getId();

	public Location getLocation();

	public String getLogo();

	public UserAccount[] getMembers();

	public Long[] getMembersIds();

	public String getName();

	public Organization getParentOrganization();

	public Long getParentOrganizationId();

	public Services[] getServices();

	public Organization[] getSubOrganization();

	public Long[] getSubOrganizationIds();

	public void setComment(String comment);

	public void setComment(
		UnsafeSupplier<String, Throwable> commentUnsafeSupplier);

	public void setContactInformation(ContactInformation contactInformation);

	public void setContactInformation(
		UnsafeSupplier<ContactInformation, Throwable>
			contactInformationUnsafeSupplier);

	public void setId(Long id);

	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier);

	public void setLocation(Location location);

	public void setLocation(
		UnsafeSupplier<Location, Throwable> locationUnsafeSupplier);

	public void setLogo(String logo);

	public void setLogo(UnsafeSupplier<String, Throwable> logoUnsafeSupplier);

	public void setMembers(
		UnsafeSupplier<UserAccount[], Throwable> membersUnsafeSupplier);

	public void setMembers(UserAccount[] members);

	public void setMembersIds(Long[] membersIds);

	public void setMembersIds(
		UnsafeSupplier<Long[], Throwable> membersIdsUnsafeSupplier);

	public void setName(String name);

	public void setName(UnsafeSupplier<String, Throwable> nameUnsafeSupplier);

	public void setParentOrganization(Organization parentOrganization);

	public void setParentOrganization(
		UnsafeSupplier<Organization, Throwable>
			parentOrganizationUnsafeSupplier);

	public void setParentOrganizationId(Long parentOrganizationId);

	public void setParentOrganizationId(
		UnsafeSupplier<Long, Throwable> parentOrganizationIdUnsafeSupplier);

	public void setServices(Services[] services);

	public void setServices(
		UnsafeSupplier<Services[], Throwable> servicesUnsafeSupplier);

	public void setSubOrganization(Organization[] subOrganization);

	public void setSubOrganization(
		UnsafeSupplier<Organization[], Throwable>
			subOrganizationUnsafeSupplier);

	public void setSubOrganizationIds(Long[] subOrganizationIds);

	public void setSubOrganizationIds(
		UnsafeSupplier<Long[], Throwable> subOrganizationIdsUnsafeSupplier);

}