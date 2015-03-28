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

package com.liferay.portal.ac.profile.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.ac.profile.model.ServiceACProfile;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ServiceACProfile in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceACProfile
 * @generated
 */
@ProviderType
public class ServiceACProfileCacheModel implements CacheModel<ServiceACProfile>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ServiceACProfileCacheModel)) {
			return false;
		}

		ServiceACProfileCacheModel serviceACProfileCacheModel = (ServiceACProfileCacheModel)obj;

		if (serviceACProfileId == serviceACProfileCacheModel.serviceACProfileId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, serviceACProfileId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", serviceACProfileId=");
		sb.append(serviceACProfileId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", allowedServices=");
		sb.append(allowedServices);
		sb.append(", name=");
		sb.append(name);
		sb.append(", title=");
		sb.append(title);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ServiceACProfile toEntityModel() {
		ServiceACProfileImpl serviceACProfileImpl = new ServiceACProfileImpl();

		if (uuid == null) {
			serviceACProfileImpl.setUuid(StringPool.BLANK);
		}
		else {
			serviceACProfileImpl.setUuid(uuid);
		}

		serviceACProfileImpl.setServiceACProfileId(serviceACProfileId);
		serviceACProfileImpl.setCompanyId(companyId);
		serviceACProfileImpl.setUserId(userId);

		if (userName == null) {
			serviceACProfileImpl.setUserName(StringPool.BLANK);
		}
		else {
			serviceACProfileImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			serviceACProfileImpl.setCreateDate(null);
		}
		else {
			serviceACProfileImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			serviceACProfileImpl.setModifiedDate(null);
		}
		else {
			serviceACProfileImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (allowedServices == null) {
			serviceACProfileImpl.setAllowedServices(StringPool.BLANK);
		}
		else {
			serviceACProfileImpl.setAllowedServices(allowedServices);
		}

		if (name == null) {
			serviceACProfileImpl.setName(StringPool.BLANK);
		}
		else {
			serviceACProfileImpl.setName(name);
		}

		if (title == null) {
			serviceACProfileImpl.setTitle(StringPool.BLANK);
		}
		else {
			serviceACProfileImpl.setTitle(title);
		}

		serviceACProfileImpl.resetOriginalValues();

		return serviceACProfileImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		serviceACProfileId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		allowedServices = objectInput.readUTF();
		name = objectInput.readUTF();
		title = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(serviceACProfileId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (allowedServices == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(allowedServices);
		}

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}
	}

	public String uuid;
	public long serviceACProfileId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String allowedServices;
	public String name;
	public String title;
}