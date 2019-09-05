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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.Organization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Organization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OrganizationCacheModel
	implements CacheModel<Organization>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OrganizationCacheModel)) {
			return false;
		}

		OrganizationCacheModel organizationCacheModel =
			(OrganizationCacheModel)obj;

		if ((organizationId == organizationCacheModel.organizationId) &&
			(mvccVersion == organizationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, organizationId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(39);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Organization toEntityModel() {
		OrganizationImpl organizationImpl = new OrganizationImpl();

		organizationImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			organizationImpl.setUuid("");
		}
		else {
			organizationImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			organizationImpl.setExternalReferenceCode("");
		}
		else {
			organizationImpl.setExternalReferenceCode(externalReferenceCode);
		}

		organizationImpl.setOrganizationId(organizationId);
		organizationImpl.setCompanyId(companyId);
		organizationImpl.setUserId(userId);

		if (userName == null) {
			organizationImpl.setUserName("");
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
			organizationImpl.setTreePath("");
		}
		else {
			organizationImpl.setTreePath(treePath);
		}

		if (name == null) {
			organizationImpl.setName("");
		}
		else {
			organizationImpl.setName(name);
		}

		if (type == null) {
			organizationImpl.setType("");
		}
		else {
			organizationImpl.setType(type);
		}

		organizationImpl.setRecursable(recursable);
		organizationImpl.setRegionId(regionId);
		organizationImpl.setCountryId(countryId);
		organizationImpl.setStatusId(statusId);

		if (comments == null) {
			organizationImpl.setComments("");
		}
		else {
			organizationImpl.setComments(comments);
		}

		organizationImpl.setLogoId(logoId);

		organizationImpl.resetOriginalValues();

		return organizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

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

		statusId = objectInput.readLong();
		comments = objectInput.readUTF();

		logoId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(organizationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(parentOrganizationId);

		if (treePath == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(treePath);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeBoolean(recursable);

		objectOutput.writeLong(regionId);

		objectOutput.writeLong(countryId);

		objectOutput.writeLong(statusId);

		if (comments == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(comments);
		}

		objectOutput.writeLong(logoId);
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
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
	public long statusId;
	public String comments;
	public long logoId;

}