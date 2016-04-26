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

package com.liferay.lcs.rest;

/**
 * @author Ivica Cardic
 */
public interface LCSProject {

	public long getAccountEntryId();

	public long getAddressId();

	public boolean getArchived();

	public String getContactEmailAddress();

	public long getCorpProjectId();

	public long getCreateTime();

	public long getLcsProjectId();

	public long getModifiedTime();

	public String getName();

	public long getOrganizationId();

	public String getPhoneNumber();

	public String getSourceSystemName();

	public boolean isArchived();

	public void setAccountEntryId(long accountEntryId);

	public void setAddressId(long addressId);

	public void setArchived(boolean archived);

	public void setContactEmailAddress(String contactEmailAddress);

	public void setCorpProjectId(long corpProjectId);

	public void setCreateTime(long createTime);

	public void setLcsProjectId(long lcsProjectId);

	public void setModifiedTime(long modifiedTime);

	public void setName(String name);

	public void setOrganizationId(long organizationId);

	public void setPhoneNumber(String phoneNumber);

	public void setSourceSystemName(String sourceSystemName);

}