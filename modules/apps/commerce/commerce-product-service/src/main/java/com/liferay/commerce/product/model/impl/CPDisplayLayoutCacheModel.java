/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPDisplayLayout;
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
 * The cache model class for representing CPDisplayLayout in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPDisplayLayoutCacheModel
	implements CacheModel<CPDisplayLayout>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDisplayLayoutCacheModel)) {
			return false;
		}

		CPDisplayLayoutCacheModel cpDisplayLayoutCacheModel =
			(CPDisplayLayoutCacheModel)object;

		if ((CPDisplayLayoutId ==
				cpDisplayLayoutCacheModel.CPDisplayLayoutId) &&
			(mvccVersion == cpDisplayLayoutCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPDisplayLayoutId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", CPDisplayLayoutId=");
		sb.append(CPDisplayLayoutId);
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
		sb.append(", layoutPageTemplateEntryUuid=");
		sb.append(layoutPageTemplateEntryUuid);
		sb.append(", layoutUuid=");
		sb.append(layoutUuid);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDisplayLayout toEntityModel() {
		CPDisplayLayoutImpl cpDisplayLayoutImpl = new CPDisplayLayoutImpl();

		cpDisplayLayoutImpl.setMvccVersion(mvccVersion);
		cpDisplayLayoutImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			cpDisplayLayoutImpl.setUuid("");
		}
		else {
			cpDisplayLayoutImpl.setUuid(uuid);
		}

		cpDisplayLayoutImpl.setCPDisplayLayoutId(CPDisplayLayoutId);
		cpDisplayLayoutImpl.setGroupId(groupId);
		cpDisplayLayoutImpl.setCompanyId(companyId);
		cpDisplayLayoutImpl.setUserId(userId);

		if (userName == null) {
			cpDisplayLayoutImpl.setUserName("");
		}
		else {
			cpDisplayLayoutImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDisplayLayoutImpl.setCreateDate(null);
		}
		else {
			cpDisplayLayoutImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDisplayLayoutImpl.setModifiedDate(null);
		}
		else {
			cpDisplayLayoutImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpDisplayLayoutImpl.setClassNameId(classNameId);
		cpDisplayLayoutImpl.setClassPK(classPK);

		if (layoutPageTemplateEntryUuid == null) {
			cpDisplayLayoutImpl.setLayoutPageTemplateEntryUuid("");
		}
		else {
			cpDisplayLayoutImpl.setLayoutPageTemplateEntryUuid(
				layoutPageTemplateEntryUuid);
		}

		if (layoutUuid == null) {
			cpDisplayLayoutImpl.setLayoutUuid("");
		}
		else {
			cpDisplayLayoutImpl.setLayoutUuid(layoutUuid);
		}

		cpDisplayLayoutImpl.resetOriginalValues();

		return cpDisplayLayoutImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		CPDisplayLayoutId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		layoutPageTemplateEntryUuid = objectInput.readUTF();
		layoutUuid = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPDisplayLayoutId);

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

		if (layoutPageTemplateEntryUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(layoutPageTemplateEntryUuid);
		}

		if (layoutUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(layoutUuid);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long CPDisplayLayoutId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String layoutPageTemplateEntryUuid;
	public String layoutUuid;

}