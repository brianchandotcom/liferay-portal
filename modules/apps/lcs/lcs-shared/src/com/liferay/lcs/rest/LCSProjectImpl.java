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
public class LCSProjectImpl implements LCSProject {

	@Override
	public long getAccountEntryId() {
		return _accountEntryId;
	}

	@Override
	public long getAddressId() {
		return _addressId;
	}

	@Override
	public boolean getArchived() {
		return _archived;
	}

	@Override
	public String getContactEmailAddress() {
		return _contactEmailAddress;
	}

	@Override
	public long getCorpProjectId() {
		return _corpProjectId;
	}

	@Override
	public long getCreateTime() {
		return _createTime;
	}

	@Override
	public long getLcsProjectId() {
		return _lcsProjectId;
	}

	@Override
	public long getModifiedTime() {
		return _modifiedTime;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public long getOrganizationId() {
		return _organizationId;
	}

	@Override
	public String getPhoneNumber() {
		return _phoneNumber;
	}

	@Override
	public String getSourceSystemName() {
		return _sourceSystemName;
	}

	@Override
	public boolean isArchived() {
		return _archived;
	}

	@Override
	public void setAccountEntryId(long accountEntryId) {
		_accountEntryId = accountEntryId;
	}

	@Override
	public void setAddressId(long addressId) {
		_addressId = addressId;
	}

	@Override
	public void setArchived(boolean archived) {
		_archived = archived;
	}

	@Override
	public void setContactEmailAddress(String contactEmailAddress) {
		_contactEmailAddress = contactEmailAddress;
	}

	@Override
	public void setCorpProjectId(long corpProjectId) {
		_corpProjectId = corpProjectId;
	}

	@Override
	public void setCreateTime(long createTime) {
		_createTime = createTime;
	}

	@Override
	public void setLcsProjectId(long lcsProjectId) {
		_lcsProjectId = lcsProjectId;
	}

	@Override
	public void setModifiedTime(long modifiedTime) {
		_modifiedTime = modifiedTime;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setOrganizationId(long organizationId) {
		_organizationId = organizationId;
	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		_phoneNumber = phoneNumber;
	}

	@Override
	public void setSourceSystemName(String sourceSystemName) {
		_sourceSystemName = sourceSystemName;
	}

	private long _accountEntryId;
	private long _addressId;
	private boolean _archived;
	private String _contactEmailAddress;
	private long _corpProjectId;
	private long _createTime;
	private long _lcsProjectId;
	private long _modifiedTime;
	private String _name;
	private long _organizationId;
	private String _phoneNumber;
	private String _sourceSystemName;

}