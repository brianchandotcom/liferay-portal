/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPConfigurationListRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPConfigurationListRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPConfigurationListRelCacheModel
	implements CacheModel<CPConfigurationListRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPConfigurationListRelCacheModel)) {
			return false;
		}

		CPConfigurationListRelCacheModel cpConfigurationListRelCacheModel =
			(CPConfigurationListRelCacheModel)object;

		if ((CPConfigurationListRelId ==
				cpConfigurationListRelCacheModel.CPConfigurationListRelId) &&
			(mvccVersion == cpConfigurationListRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPConfigurationListRelId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", CPConfigurationListRelId=");
		sb.append(CPConfigurationListRelId);
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
		sb.append(", CPConfigurationListId=");
		sb.append(CPConfigurationListId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPConfigurationListRel toEntityModel() {
		CPConfigurationListRelImpl cpConfigurationListRelImpl =
			new CPConfigurationListRelImpl();

		cpConfigurationListRelImpl.setMvccVersion(mvccVersion);
		cpConfigurationListRelImpl.setCtCollectionId(ctCollectionId);
		cpConfigurationListRelImpl.setCPConfigurationListRelId(
			CPConfigurationListRelId);
		cpConfigurationListRelImpl.setCompanyId(companyId);
		cpConfigurationListRelImpl.setUserId(userId);

		if (userName == null) {
			cpConfigurationListRelImpl.setUserName("");
		}
		else {
			cpConfigurationListRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpConfigurationListRelImpl.setCreateDate(null);
		}
		else {
			cpConfigurationListRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpConfigurationListRelImpl.setModifiedDate(null);
		}
		else {
			cpConfigurationListRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpConfigurationListRelImpl.setClassNameId(classNameId);
		cpConfigurationListRelImpl.setClassPK(classPK);
		cpConfigurationListRelImpl.setCPConfigurationListId(
			CPConfigurationListId);

		cpConfigurationListRelImpl.resetOriginalValues();

		return cpConfigurationListRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		CPConfigurationListRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		CPConfigurationListId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(CPConfigurationListRelId);

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

		objectOutput.writeLong(CPConfigurationListId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long CPConfigurationListRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long CPConfigurationListId;

}
// LIFERAY-SERVICE-BUILDER-HASH:-17497803