/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model.impl;

import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRelElementVariationAudienceEntryRel;
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
 * The cache model class for representing LayoutPageTemplateStructureRelElementVariationAudienceEntryRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class
	LayoutPageTemplateStructureRelElementVariationAudienceEntryRelCacheModel
		implements CacheModel
			<LayoutPageTemplateStructureRelElementVariationAudienceEntryRel>,
				   Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof
				LayoutPageTemplateStructureRelElementVariationAudienceEntryRelCacheModel)) {

			return false;
		}

		LayoutPageTemplateStructureRelElementVariationAudienceEntryRelCacheModel
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelCacheModel =
				(LayoutPageTemplateStructureRelElementVariationAudienceEntryRelCacheModel)
					object;

		if ((layoutPageTemplateStructureRelElementVariationAudienceEntryRelId ==
				layoutPageTemplateStructureRelElementVariationAudienceEntryRelCacheModel.layoutPageTemplateStructureRelElementVariationAudienceEntryRelId) &&
			(mvccVersion ==
				layoutPageTemplateStructureRelElementVariationAudienceEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(
			0,
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(
			", layoutPageTemplateStructureRelElementVariationAudienceEntryRelId=");
		sb.append(
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelId);
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
		sb.append(", audienceEntryERC=");
		sb.append(audienceEntryERC);
		sb.append(", layoutPageTemplateStructureRelElementVariationERC=");
		sb.append(layoutPageTemplateStructureRelElementVariationERC);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutPageTemplateStructureRelElementVariationAudienceEntryRel
		toEntityModel() {

		LayoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl =
				new LayoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl();

		layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
			setMvccVersion(mvccVersion);
		layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
			setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setUuid("");
		}
		else {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setExternalReferenceCode("");
		}
		else {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setExternalReferenceCode(externalReferenceCode);
		}

		layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
			setLayoutPageTemplateStructureRelElementVariationAudienceEntryRelId(
				layoutPageTemplateStructureRelElementVariationAudienceEntryRelId);
		layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
			setGroupId(groupId);
		layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
			setCompanyId(companyId);
		layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
			setUserId(userId);

		if (userName == null) {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setUserName("");
		}
		else {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setCreateDate(null);
		}
		else {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setModifiedDate(null);
		}
		else {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setModifiedDate(new Date(modifiedDate));
		}

		if (audienceEntryERC == null) {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setAudienceEntryERC("");
		}
		else {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setAudienceEntryERC(audienceEntryERC);
		}

		if (layoutPageTemplateStructureRelElementVariationERC == null) {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setLayoutPageTemplateStructureRelElementVariationERC("");
		}
		else {
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
				setLayoutPageTemplateStructureRelElementVariationERC(
					layoutPageTemplateStructureRelElementVariationERC);
		}

		layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl.
			resetOriginalValues();

		return layoutPageTemplateStructureRelElementVariationAudienceEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		layoutPageTemplateStructureRelElementVariationAudienceEntryRelId =
			objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		audienceEntryERC = objectInput.readUTF();
		layoutPageTemplateStructureRelElementVariationERC =
			objectInput.readUTF();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(
			layoutPageTemplateStructureRelElementVariationAudienceEntryRelId);

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

		if (audienceEntryERC == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(audienceEntryERC);
		}

		if (layoutPageTemplateStructureRelElementVariationERC == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(
				layoutPageTemplateStructureRelElementVariationERC);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String externalReferenceCode;
	public long
		layoutPageTemplateStructureRelElementVariationAudienceEntryRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String audienceEntryERC;
	public String layoutPageTemplateStructureRelElementVariationERC;

}
// LIFERAY-SERVICE-BUILDER-HASH:684611437