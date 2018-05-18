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

package com.liferay.commerce.notification.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceNotificationTemplateUserSegmentRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateUserSegmentRel
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateUserSegmentRelCacheModel
	implements CacheModel<CommerceNotificationTemplateUserSegmentRel>,
		Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceNotificationTemplateUserSegmentRelCacheModel)) {
			return false;
		}

		CommerceNotificationTemplateUserSegmentRelCacheModel commerceNotificationTemplateUserSegmentRelCacheModel =
			(CommerceNotificationTemplateUserSegmentRelCacheModel)obj;

		if (commerceNotificationTemplateUserSegmentRelId == commerceNotificationTemplateUserSegmentRelCacheModel.commerceNotificationTemplateUserSegmentRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceNotificationTemplateUserSegmentRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{commerceNotificationTemplateUserSegmentRelId=");
		sb.append(commerceNotificationTemplateUserSegmentRelId);
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
		sb.append(", commerceNotificationTemplateId=");
		sb.append(commerceNotificationTemplateId);
		sb.append(", commerceUserSegmentEntryId=");
		sb.append(commerceUserSegmentEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceNotificationTemplateUserSegmentRel toEntityModel() {
		CommerceNotificationTemplateUserSegmentRelImpl commerceNotificationTemplateUserSegmentRelImpl =
			new CommerceNotificationTemplateUserSegmentRelImpl();

		commerceNotificationTemplateUserSegmentRelImpl.setCommerceNotificationTemplateUserSegmentRelId(commerceNotificationTemplateUserSegmentRelId);
		commerceNotificationTemplateUserSegmentRelImpl.setGroupId(groupId);
		commerceNotificationTemplateUserSegmentRelImpl.setCompanyId(companyId);
		commerceNotificationTemplateUserSegmentRelImpl.setUserId(userId);

		if (userName == null) {
			commerceNotificationTemplateUserSegmentRelImpl.setUserName("");
		}
		else {
			commerceNotificationTemplateUserSegmentRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceNotificationTemplateUserSegmentRelImpl.setCreateDate(null);
		}
		else {
			commerceNotificationTemplateUserSegmentRelImpl.setCreateDate(new Date(
					createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceNotificationTemplateUserSegmentRelImpl.setModifiedDate(null);
		}
		else {
			commerceNotificationTemplateUserSegmentRelImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceNotificationTemplateUserSegmentRelImpl.setCommerceNotificationTemplateId(commerceNotificationTemplateId);
		commerceNotificationTemplateUserSegmentRelImpl.setCommerceUserSegmentEntryId(commerceUserSegmentEntryId);

		commerceNotificationTemplateUserSegmentRelImpl.resetOriginalValues();

		return commerceNotificationTemplateUserSegmentRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceNotificationTemplateUserSegmentRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceNotificationTemplateId = objectInput.readLong();

		commerceUserSegmentEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceNotificationTemplateUserSegmentRelId);

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

		objectOutput.writeLong(commerceNotificationTemplateId);

		objectOutput.writeLong(commerceUserSegmentEntryId);
	}

	public long commerceNotificationTemplateUserSegmentRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceNotificationTemplateId;
	public long commerceUserSegmentEntryId;
}