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

	public void setComment(
			String comment);

	public void setComment(
			UnsafeSupplier<String, Throwable>
				commentUnsafeSupplier);
	public ContactInformation getContactInformation();

	public void setContactInformation(
			ContactInformation contactInformation);

	public void setContactInformation(
			UnsafeSupplier<ContactInformation, Throwable>
				contactInformationUnsafeSupplier);
	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public Location getLocation();

	public void setLocation(
			Location location);

	public void setLocation(
			UnsafeSupplier<Location, Throwable>
				locationUnsafeSupplier);
	public String getLogo();

	public void setLogo(
			String logo);

	public void setLogo(
			UnsafeSupplier<String, Throwable>
				logoUnsafeSupplier);
	public UserAccount[] getMembers();

	public void setMembers(
			UserAccount[] members);

	public void setMembers(
			UnsafeSupplier<UserAccount[], Throwable>
				membersUnsafeSupplier);
	public Long[] getMembersIds();

	public void setMembersIds(
			Long[] membersIds);

	public void setMembersIds(
			UnsafeSupplier<Long[], Throwable>
				membersIdsUnsafeSupplier);
	public String getName();

	public void setName(
			String name);

	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier);
	public Organization getParentOrganization();

	public void setParentOrganization(
			Organization parentOrganization);

	public void setParentOrganization(
			UnsafeSupplier<Organization, Throwable>
				parentOrganizationUnsafeSupplier);
	public Long getParentOrganizationId();

	public void setParentOrganizationId(
			Long parentOrganizationId);

	public void setParentOrganizationId(
			UnsafeSupplier<Long, Throwable>
				parentOrganizationIdUnsafeSupplier);
	public Services[] getServices();

	public void setServices(
			Services[] services);

	public void setServices(
			UnsafeSupplier<Services[], Throwable>
				servicesUnsafeSupplier);
	public Organization[] getSubOrganization();

	public void setSubOrganization(
			Organization[] subOrganization);

	public void setSubOrganization(
			UnsafeSupplier<Organization[], Throwable>
				subOrganizationUnsafeSupplier);
	public Long[] getSubOrganizationIds();

	public void setSubOrganizationIds(
			Long[] subOrganizationIds);

	public void setSubOrganizationIds(
			UnsafeSupplier<Long[], Throwable>
				subOrganizationIdsUnsafeSupplier);

}