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

import com.liferay.commerce.product.model.CPFriendlyURLEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPFriendlyURLEntry in entity cache.
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntry
 * @generated
 */
@ProviderType
public class CPFriendlyURLEntryCacheModel implements CacheModel<CPFriendlyURLEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPFriendlyURLEntryCacheModel)) {
			return false;
		}

		CPFriendlyURLEntryCacheModel cpFriendlyURLEntryCacheModel = (CPFriendlyURLEntryCacheModel)obj;

		if (CPFriendlyURLEntryId == cpFriendlyURLEntryCacheModel.CPFriendlyURLEntryId) {
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
		StringBundler sb = new StringBundler(27);

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
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", urlTitle=");
		sb.append(urlTitle);
		sb.append(", main=");
		sb.append(main);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPFriendlyURLEntry toEntityModel() {
		CPFriendlyURLEntryImpl cpFriendlyURLEntryImpl = new CPFriendlyURLEntryImpl();

		if (uuid == null) {
			cpFriendlyURLEntryImpl.setUuid("");
		}
		else {
			cpFriendlyURLEntryImpl.setUuid(uuid);
		}

		cpFriendlyURLEntryImpl.setCPFriendlyURLEntryId(CPFriendlyURLEntryId);
		cpFriendlyURLEntryImpl.setGroupId(groupId);
		cpFriendlyURLEntryImpl.setCompanyId(companyId);
		cpFriendlyURLEntryImpl.setUserId(userId);

		if (userName == null) {
			cpFriendlyURLEntryImpl.setUserName("");
		}
		else {
			cpFriendlyURLEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpFriendlyURLEntryImpl.setCreateDate(null);
		}
		else {
			cpFriendlyURLEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpFriendlyURLEntryImpl.setModifiedDate(null);
		}
		else {
			cpFriendlyURLEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpFriendlyURLEntryImpl.setClassNameId(classNameId);
		cpFriendlyURLEntryImpl.setClassPK(classPK);

		if (languageId == null) {
			cpFriendlyURLEntryImpl.setLanguageId("");
		}
		else {
			cpFriendlyURLEntryImpl.setLanguageId(languageId);
		}

		if (urlTitle == null) {
			cpFriendlyURLEntryImpl.setUrlTitle("");
		}
		else {
			cpFriendlyURLEntryImpl.setUrlTitle(urlTitle);
		}

		cpFriendlyURLEntryImpl.setMain(main);

		cpFriendlyURLEntryImpl.resetOriginalValues();

		return cpFriendlyURLEntryImpl;
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
		languageId = objectInput.readUTF();
		urlTitle = objectInput.readUTF();

		main = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPFriendlyURLEntryId);

		objectOutput.writeLong(groupId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (urlTitle == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(urlTitle);
		}

		objectOutput.writeBoolean(main);
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
	public String languageId;
	public String urlTitle;
	public boolean main;
}