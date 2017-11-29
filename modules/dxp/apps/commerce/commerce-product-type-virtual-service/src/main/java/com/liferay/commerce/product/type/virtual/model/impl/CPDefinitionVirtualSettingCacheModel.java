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

package com.liferay.commerce.product.type.virtual.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDefinitionVirtualSetting in entity cache.
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSetting
 * @generated
 */
@ProviderType
public class CPDefinitionVirtualSettingCacheModel implements CacheModel<CPDefinitionVirtualSetting>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionVirtualSettingCacheModel)) {
			return false;
		}

		CPDefinitionVirtualSettingCacheModel cpDefinitionVirtualSettingCacheModel =
			(CPDefinitionVirtualSettingCacheModel)obj;

		if (CPDefinitionVirtualSettingId == cpDefinitionVirtualSettingCacheModel.CPDefinitionVirtualSettingId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionVirtualSettingId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(43);

		sb.append("{uuid=");
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
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
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
		sb.append(", sampleUrl=");
		sb.append(sampleUrl);
		sb.append(", termsOfUseRequired=");
		sb.append(termsOfUseRequired);
		sb.append(", termsOfUseContent=");
		sb.append(termsOfUseContent);
		sb.append(", termsOfUseJournalArticleResourcePrimKey=");
		sb.append(termsOfUseJournalArticleResourcePrimKey);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionVirtualSetting toEntityModel() {
		CPDefinitionVirtualSettingImpl cpDefinitionVirtualSettingImpl = new CPDefinitionVirtualSettingImpl();

		if (uuid == null) {
			cpDefinitionVirtualSettingImpl.setUuid("");
		}
		else {
			cpDefinitionVirtualSettingImpl.setUuid(uuid);
		}

		cpDefinitionVirtualSettingImpl.setCPDefinitionVirtualSettingId(CPDefinitionVirtualSettingId);
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
			cpDefinitionVirtualSettingImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		cpDefinitionVirtualSettingImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionVirtualSettingImpl.setFileEntryId(fileEntryId);

		if (url == null) {
			cpDefinitionVirtualSettingImpl.setUrl("");
		}
		else {
			cpDefinitionVirtualSettingImpl.setUrl(url);
		}

		if (activationStatus == null) {
			cpDefinitionVirtualSettingImpl.setActivationStatus("");
		}
		else {
			cpDefinitionVirtualSettingImpl.setActivationStatus(activationStatus);
		}

		cpDefinitionVirtualSettingImpl.setDuration(duration);
		cpDefinitionVirtualSettingImpl.setMaxUsages(maxUsages);
		cpDefinitionVirtualSettingImpl.setUseSample(useSample);
		cpDefinitionVirtualSettingImpl.setSampleFileEntryId(sampleFileEntryId);

		if (sampleUrl == null) {
			cpDefinitionVirtualSettingImpl.setSampleUrl("");
		}
		else {
			cpDefinitionVirtualSettingImpl.setSampleUrl(sampleUrl);
		}

		cpDefinitionVirtualSettingImpl.setTermsOfUseRequired(termsOfUseRequired);

		if (termsOfUseContent == null) {
			cpDefinitionVirtualSettingImpl.setTermsOfUseContent("");
		}
		else {
			cpDefinitionVirtualSettingImpl.setTermsOfUseContent(termsOfUseContent);
		}

		cpDefinitionVirtualSettingImpl.setTermsOfUseJournalArticleResourcePrimKey(termsOfUseJournalArticleResourcePrimKey);

		if (lastPublishDate == Long.MIN_VALUE) {
			cpDefinitionVirtualSettingImpl.setLastPublishDate(null);
		}
		else {
			cpDefinitionVirtualSettingImpl.setLastPublishDate(new Date(
					lastPublishDate));
		}

		cpDefinitionVirtualSettingImpl.resetOriginalValues();

		return cpDefinitionVirtualSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPDefinitionVirtualSettingId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		fileEntryId = objectInput.readLong();
		url = objectInput.readUTF();
		activationStatus = objectInput.readUTF();

		duration = objectInput.readLong();

		maxUsages = objectInput.readInt();

		useSample = objectInput.readBoolean();

		sampleFileEntryId = objectInput.readLong();
		sampleUrl = objectInput.readUTF();

		termsOfUseRequired = objectInput.readBoolean();
		termsOfUseContent = objectInput.readUTF();

		termsOfUseJournalArticleResourcePrimKey = objectInput.readLong();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeLong(fileEntryId);

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}

		if (activationStatus == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(activationStatus);
		}

		objectOutput.writeLong(duration);

		objectOutput.writeInt(maxUsages);

		objectOutput.writeBoolean(useSample);

		objectOutput.writeLong(sampleFileEntryId);

		if (sampleUrl == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sampleUrl);
		}

		objectOutput.writeBoolean(termsOfUseRequired);

		if (termsOfUseContent == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(termsOfUseContent);
		}

		objectOutput.writeLong(termsOfUseJournalArticleResourcePrimKey);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long CPDefinitionVirtualSettingId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public long fileEntryId;
	public String url;
	public String activationStatus;
	public long duration;
	public int maxUsages;
	public boolean useSample;
	public long sampleFileEntryId;
	public String sampleUrl;
	public boolean termsOfUseRequired;
	public String termsOfUseContent;
	public long termsOfUseJournalArticleResourcePrimKey;
	public long lastPublishDate;
}