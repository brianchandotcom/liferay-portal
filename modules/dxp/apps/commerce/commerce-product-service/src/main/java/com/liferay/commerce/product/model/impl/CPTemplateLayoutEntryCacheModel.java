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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPTemplateLayoutEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPTemplateLayoutEntry in entity cache.
 *
 * @author Marco Leo
 * @see CPTemplateLayoutEntry
 * @generated
 */
@ProviderType
public class CPTemplateLayoutEntryCacheModel implements CacheModel<CPTemplateLayoutEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPTemplateLayoutEntryCacheModel)) {
			return false;
		}

		CPTemplateLayoutEntryCacheModel cpTemplateLayoutEntryCacheModel = (CPTemplateLayoutEntryCacheModel)obj;

		if (CPFriendlyURLEntryId == cpTemplateLayoutEntryCacheModel.CPFriendlyURLEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPFriendlyURLEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPFriendlyURLEntryId=");
		sb.append(CPFriendlyURLEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", layoutUuid=");
		sb.append(layoutUuid);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPTemplateLayoutEntry toEntityModel() {
		CPTemplateLayoutEntryImpl cpTemplateLayoutEntryImpl = new CPTemplateLayoutEntryImpl();

		if (uuid == null) {
			cpTemplateLayoutEntryImpl.setUuid(StringPool.BLANK);
		}
		else {
			cpTemplateLayoutEntryImpl.setUuid(uuid);
		}

		cpTemplateLayoutEntryImpl.setCPFriendlyURLEntryId(CPFriendlyURLEntryId);
		cpTemplateLayoutEntryImpl.setGroupId(groupId);
		cpTemplateLayoutEntryImpl.setCompanyId(companyId);
		cpTemplateLayoutEntryImpl.setUserId(userId);

		if (userName == null) {
			cpTemplateLayoutEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			cpTemplateLayoutEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpTemplateLayoutEntryImpl.setCreateDate(null);
		}
		else {
			cpTemplateLayoutEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpTemplateLayoutEntryImpl.setModifiedDate(null);
		}
		else {
			cpTemplateLayoutEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpTemplateLayoutEntryImpl.setClassNameId(classNameId);
		cpTemplateLayoutEntryImpl.setClassPK(classPK);

		if (layoutUuid == null) {
			cpTemplateLayoutEntryImpl.setLayoutUuid(StringPool.BLANK);
		}
		else {
			cpTemplateLayoutEntryImpl.setLayoutUuid(layoutUuid);
		}

		cpTemplateLayoutEntryImpl.resetOriginalValues();

		return cpTemplateLayoutEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPFriendlyURLEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		layoutUuid = objectInput.readUTF();
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

		objectOutput.writeLong(CPFriendlyURLEntryId);

		objectOutput.writeLong(groupId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (layoutUuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(layoutUuid);
		}
	}

	public String uuid;
	public long CPFriendlyURLEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String layoutUuid;
}