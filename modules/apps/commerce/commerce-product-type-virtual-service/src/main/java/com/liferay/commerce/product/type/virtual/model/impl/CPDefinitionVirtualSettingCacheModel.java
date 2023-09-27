/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.model.impl;

import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
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
 * The cache model class for representing CPDefinitionVirtualSetting in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPDefinitionVirtualSettingCacheModel
	implements CacheModel<CPDefinitionVirtualSetting>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionVirtualSettingCacheModel)) {
			return false;
		}

		CPDefinitionVirtualSettingCacheModel
			cpDefinitionVirtualSettingCacheModel =
				(CPDefinitionVirtualSettingCacheModel)object;

		if ((CPDefinitionVirtualSettingId ==
				cpDefinitionVirtualSettingCacheModel.
					CPDefinitionVirtualSettingId) &&
			(mvccVersion == cpDefinitionVirtualSettingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPDefinitionVirtualSettingId);

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
		StringBundler sb = new StringBundler(49);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionVirtualSettingId=");
		sb.append(CPDefinitionVirtualSettingId);
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
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", url=");
		sb.append(url);
		sb.append(", activationStatus=");
		sb.append(activationStatus);
		sb.append(", duration=");
		sb.append(duration);
		sb.append(", maxUsages=");
		sb.append(maxUsages);
		sb.append(", useSample=");
		sb.append(useSample);
		sb.append(", sampleFileEntryId=");
		sb.append(sampleFileEntryId);
		sb.append(", sampleURL=");
		sb.append(sampleURL);
		sb.append(", termsOfUseRequired=");
		sb.append(termsOfUseRequired);
		sb.append(", termsOfUseContent=");
		sb.append(termsOfUseContent);
		sb.append(", termsOfUseJournalArticleResourcePrimKey=");
		sb.append(termsOfUseJournalArticleResourcePrimKey);
		sb.append(", override=");
		sb.append(override);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionVirtualSetting toEntityModel() {
		CPDefinitionVirtualSettingImpl cpDefinitionVirtualSettingImpl =
			new CPDefinitionVirtualSettingImpl();

		cpDefinitionVirtualSettingImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			cpDefinitionVirtualSettingImpl.setUuid("");
		}
		else {
			cpDefinitionVirtualSettingImpl.setUuid(uuid);
		}

		cpDefinitionVirtualSettingImpl.setCPDefinitionVirtualSettingId(
			CPDefinitionVirtualSettingId);
		cpDefinitionVirtualSettingImpl.setGroupId(groupId);
		cpDefinitionVirtualSettingImpl.setCompanyId(companyId);
		cpDefinitionVirtualSettingImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionVirtualSettingImpl.setUserName("");
		}
		else {
			cpDefinitionVirtualSettingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionVirtualSettingImpl.setCreateDate(null);
		}
		else {
			cpDefinitionVirtualSettingImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionVirtualSettingImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionVirtualSettingImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		cpDefinitionVirtualSettingImpl.setClassNameId(classNameId);
		cpDefinitionVirtualSettingImpl.setClassPK(classPK);
		cpDefinitionVirtualSettingImpl.setFileEntryId(fileEntryId);

		if (url == null) {
			cpDefinitionVirtualSettingImpl.setUrl("");
		}
		else {
			cpDefinitionVirtualSettingImpl.setUrl(url);
		}

		cpDefinitionVirtualSettingImpl.setActivationStatus(activationStatus);
		cpDefinitionVirtualSettingImpl.setDuration(duration);
		cpDefinitionVirtualSettingImpl.setMaxUsages(maxUsages);
		cpDefinitionVirtualSettingImpl.setUseSample(useSample);
		cpDefinitionVirtualSettingImpl.setSampleFileEntryId(sampleFileEntryId);

		if (sampleURL == null) {
			cpDefinitionVirtualSettingImpl.setSampleURL("");
		}
		else {
			cpDefinitionVirtualSettingImpl.setSampleURL(sampleURL);
		}

		cpDefinitionVirtualSettingImpl.setTermsOfUseRequired(
			termsOfUseRequired);

		if (termsOfUseContent == null) {
			cpDefinitionVirtualSettingImpl.setTermsOfUseContent("");
		}
		else {
			cpDefinitionVirtualSettingImpl.setTermsOfUseContent(
				termsOfUseContent);
		}

		cpDefinitionVirtualSettingImpl.
			setTermsOfUseJournalArticleResourcePrimKey(
				termsOfUseJournalArticleResourcePrimKey);
		cpDefinitionVirtualSettingImpl.setOverride(override);

		if (lastPublishDate == Long.MIN_VALUE) {
			cpDefinitionVirtualSettingImpl.setLastPublishDate(null);
		}
		else {
			cpDefinitionVirtualSettingImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		cpDefinitionVirtualSettingImpl.resetOriginalValues();

		return cpDefinitionVirtualSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		CPDefinitionVirtualSettingId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		fileEntryId = objectInput.readLong();
		url = objectInput.readUTF();

		activationStatus = objectInput.readInt();

		duration = objectInput.readLong();

		maxUsages = objectInput.readInt();

		useSample = objectInput.readBoolean();

		sampleFileEntryId = objectInput.readLong();
		sampleURL = objectInput.readUTF();

		termsOfUseRequired = objectInput.readBoolean();
		termsOfUseContent = objectInput.readUTF();

		termsOfUseJournalArticleResourcePrimKey = objectInput.readLong();

		override = objectInput.readBoolean();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(CPDefinitionVirtualSettingId);

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

		objectOutput.writeLong(fileEntryId);

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}

		objectOutput.writeInt(activationStatus);

		objectOutput.writeLong(duration);

		objectOutput.writeInt(maxUsages);

		objectOutput.writeBoolean(useSample);

		objectOutput.writeLong(sampleFileEntryId);

		if (sampleURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sampleURL);
		}

		objectOutput.writeBoolean(termsOfUseRequired);

		if (termsOfUseContent == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(termsOfUseContent);
		}

		objectOutput.writeLong(termsOfUseJournalArticleResourcePrimKey);

		objectOutput.writeBoolean(override);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long CPDefinitionVirtualSettingId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long fileEntryId;
	public String url;
	public int activationStatus;
	public long duration;
	public int maxUsages;
	public boolean useSample;
	public long sampleFileEntryId;
	public String sampleURL;
	public boolean termsOfUseRequired;
	public String termsOfUseContent;
	public long termsOfUseJournalArticleResourcePrimKey;
	public boolean override;
	public long lastPublishDate;

}