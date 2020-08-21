/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.bom.model.impl;

import com.liferay.commerce.bom.model.CommerceBOMDefinition;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceBOMDefinition in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceBOMDefinitionCacheModel
	implements CacheModel<CommerceBOMDefinition>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceBOMDefinitionCacheModel)) {
			return false;
		}

		CommerceBOMDefinitionCacheModel commerceBOMDefinitionCacheModel =
			(CommerceBOMDefinitionCacheModel)object;

		if (commerceBOMDefinitionId ==
				commerceBOMDefinitionCacheModel.commerceBOMDefinitionId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceBOMDefinitionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceBOMDefinitionId=");
		sb.append(commerceBOMDefinitionId);
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
		sb.append(", commerceBOMFolderId=");
		sb.append(commerceBOMFolderId);
		sb.append(", CPAttachmentFileEntryId=");
		sb.append(CPAttachmentFileEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", friendlyUrl=");
		sb.append(friendlyUrl);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceBOMDefinition toEntityModel() {
		CommerceBOMDefinitionImpl commerceBOMDefinitionImpl =
			new CommerceBOMDefinitionImpl();

		commerceBOMDefinitionImpl.setCommerceBOMDefinitionId(
			commerceBOMDefinitionId);
		commerceBOMDefinitionImpl.setCompanyId(companyId);
		commerceBOMDefinitionImpl.setUserId(userId);

		if (userName == null) {
			commerceBOMDefinitionImpl.setUserName("");
		}
		else {
			commerceBOMDefinitionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceBOMDefinitionImpl.setCreateDate(null);
		}
		else {
			commerceBOMDefinitionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceBOMDefinitionImpl.setModifiedDate(null);
		}
		else {
			commerceBOMDefinitionImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceBOMDefinitionImpl.setCommerceBOMFolderId(commerceBOMFolderId);
		commerceBOMDefinitionImpl.setCPAttachmentFileEntryId(
			CPAttachmentFileEntryId);

		if (name == null) {
			commerceBOMDefinitionImpl.setName("");
		}
		else {
			commerceBOMDefinitionImpl.setName(name);
		}

		if (friendlyUrl == null) {
			commerceBOMDefinitionImpl.setFriendlyUrl("");
		}
		else {
			commerceBOMDefinitionImpl.setFriendlyUrl(friendlyUrl);
		}

		commerceBOMDefinitionImpl.resetOriginalValues();

		return commerceBOMDefinitionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceBOMDefinitionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceBOMFolderId = objectInput.readLong();

		CPAttachmentFileEntryId = objectInput.readLong();
		name = objectInput.readUTF();
		friendlyUrl = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(commerceBOMDefinitionId);

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

		objectOutput.writeLong(commerceBOMFolderId);

		objectOutput.writeLong(CPAttachmentFileEntryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (friendlyUrl == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(friendlyUrl);
		}
	}

	public long commerceBOMDefinitionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceBOMFolderId;
	public long CPAttachmentFileEntryId;
	public String name;
	public String friendlyUrl;

}