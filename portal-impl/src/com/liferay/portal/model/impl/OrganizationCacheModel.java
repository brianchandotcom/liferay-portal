/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Organization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Organization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Organization
 * @generated
 */
public class OrganizationCacheModel implements CacheModel<Organization>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", organizationId=");
		sb.append(organizationId);
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
		sb.append(", parentOrganizationId=");
		sb.append(parentOrganizationId);
		sb.append(", treePath=");
		sb.append(treePath);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append(", recursable=");
		sb.append(recursable);
		sb.append(", regionId=");
		sb.append(regionId);
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", statusId=");
		sb.append(statusId);
		sb.append(", comments=");
		sb.append(comments);
		sb.append(", logoId=");
		sb.append(logoId);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Organization toEntityModel() {
		OrganizationImpl organizationImpl = new OrganizationImpl();

		if (uuid == null) {
			organizationImpl.setUuid(StringPool.BLANK);
		}
		else {
			organizationImpl.setUuid(uuid);
		}

		organizationImpl.setOrganizationId(organizationId);
		organizationImpl.setCompanyId(companyId);
		organizationImpl.setUserId(userId);

		if (userName == null) {
			organizationImpl.setUserName(StringPool.BLANK);
		}
		else {
			organizationImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			organizationImpl.setCreateDate(null);
		}
		else {
			organizationImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			organizationImpl.setModifiedDate(null);
		}
		else {
			organizationImpl.setModifiedDate(new Date(modifiedDate));
		}

		organizationImpl.setParentOrganizationId(parentOrganizationId);

		if (treePath == null) {
			organizationImpl.setTreePath(StringPool.BLANK);
		}
		else {
			organizationImpl.setTreePath(treePath);
		}

		if (name == null) {
			organizationImpl.setName(StringPool.BLANK);
		}
		else {
			organizationImpl.setName(name);
		}

		if (type == null) {
			organizationImpl.setType(StringPool.BLANK);
		}
		else {
			organizationImpl.setType(type);
		}

		organizationImpl.setRecursable(recursable);
		organizationImpl.setRegionId(regionId);
		organizationImpl.setCountryId(countryId);
		organizationImpl.setStatusId(statusId);

		if (comments == null) {
			organizationImpl.setComments(StringPool.BLANK);
		}
		else {
			organizationImpl.setComments(comments);
		}

		organizationImpl.setLogoId(logoId);
		organizationImpl.setMvccVersion(mvccVersion);

		organizationImpl.resetOriginalValues();

		return organizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		organizationId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		parentOrganizationId = objectInput.readLong();
		treePath = objectInput.readUTF();
		name = objectInput.readUTF();
		type = objectInput.readUTF();
		recursable = objectInput.readBoolean();
		regionId = objectInput.readLong();
		countryId = objectInput.readLong();
		statusId = objectInput.readInt();
		comments = objectInput.readUTF();
		logoId = objectInput.readLong();
		mvccVersion = objectInput.readLong();
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

		objectOutput.writeLong(organizationId);
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
		objectOutput.writeLong(parentOrganizationId);

		if (treePath == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(treePath);
		}

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (type == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeBoolean(recursable);
		objectOutput.writeLong(regionId);
		objectOutput.writeLong(countryId);
		objectOutput.writeInt(statusId);

		if (comments == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(comments);
		}

		objectOutput.writeLong(logoId);
		objectOutput.writeLong(mvccVersion);
	}

	public String uuid;
	public long organizationId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentOrganizationId;
	public String treePath;
	public String name;
	public String type;
	public boolean recursable;
	public long regionId;
	public long countryId;
	public int statusId;
	public String comments;
	public long logoId;
	public long mvccVersion;
}