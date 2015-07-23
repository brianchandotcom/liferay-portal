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

package com.liferay.service.access.policy.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.service.access.policy.model.ServiceAccessPolicy;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ServiceAccessPolicy in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceAccessPolicy
 * @generated
 */
@ProviderType
public class ServiceAccessPolicyCacheModel implements CacheModel<ServiceAccessPolicy>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ServiceAccessPolicyCacheModel)) {
			return false;
		}

		ServiceAccessPolicyCacheModel serviceAccessPolicyCacheModel = (ServiceAccessPolicyCacheModel)obj;

		if (serviceAccessPolicyId == serviceAccessPolicyCacheModel.serviceAccessPolicyId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, serviceAccessPolicyId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", serviceAccessPolicyId=");
		sb.append(serviceAccessPolicyId);
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
		sb.append(", allowedServiceSignatures=");
		sb.append(allowedServiceSignatures);
		sb.append(", defaultServiceAccessPolicy=");
		sb.append(defaultServiceAccessPolicy);
		sb.append(", name=");
		sb.append(name);
		sb.append(", title=");
		sb.append(title);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ServiceAccessPolicy toEntityModel() {
		ServiceAccessPolicyImpl serviceAccessPolicyImpl = new ServiceAccessPolicyImpl();

		if (uuid == null) {
			serviceAccessPolicyImpl.setUuid(StringPool.BLANK);
		}
		else {
			serviceAccessPolicyImpl.setUuid(uuid);
		}

		serviceAccessPolicyImpl.setServiceAccessPolicyId(serviceAccessPolicyId);
		serviceAccessPolicyImpl.setCompanyId(companyId);
		serviceAccessPolicyImpl.setUserId(userId);

		if (userName == null) {
			serviceAccessPolicyImpl.setUserName(StringPool.BLANK);
		}
		else {
			serviceAccessPolicyImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			serviceAccessPolicyImpl.setCreateDate(null);
		}
		else {
			serviceAccessPolicyImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			serviceAccessPolicyImpl.setModifiedDate(null);
		}
		else {
			serviceAccessPolicyImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (allowedServiceSignatures == null) {
			serviceAccessPolicyImpl.setAllowedServiceSignatures(StringPool.BLANK);
		}
		else {
			serviceAccessPolicyImpl.setAllowedServiceSignatures(allowedServiceSignatures);
		}

		serviceAccessPolicyImpl.setDefaultServiceAccessPolicy(defaultServiceAccessPolicy);

		if (name == null) {
			serviceAccessPolicyImpl.setName(StringPool.BLANK);
		}
		else {
			serviceAccessPolicyImpl.setName(name);
		}

		if (title == null) {
			serviceAccessPolicyImpl.setTitle(StringPool.BLANK);
		}
		else {
			serviceAccessPolicyImpl.setTitle(title);
		}

		serviceAccessPolicyImpl.resetOriginalValues();

		return serviceAccessPolicyImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		serviceAccessPolicyId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		allowedServiceSignatures = objectInput.readUTF();
		defaultServiceAccessPolicy = objectInput.readBoolean();
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

		objectOutput.writeLong(serviceAccessPolicyId);
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

		if (allowedServiceSignatures == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(allowedServiceSignatures);
		}

		objectOutput.writeBoolean(defaultServiceAccessPolicy);

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
	public long serviceAccessPolicyId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String allowedServiceSignatures;
	public boolean defaultServiceAccessPolicy;
	public String name;
	public String title;
}