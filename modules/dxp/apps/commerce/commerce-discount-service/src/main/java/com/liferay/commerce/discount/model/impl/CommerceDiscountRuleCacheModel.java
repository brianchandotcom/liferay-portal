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

package com.liferay.commerce.discount.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.model.CommerceDiscountRule;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceDiscountRule in entity cache.
 *
 * @author Marco Leo
 * @see CommerceDiscountRule
 * @generated
 */
@ProviderType
public class CommerceDiscountRuleCacheModel implements CacheModel<CommerceDiscountRule>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceDiscountRuleCacheModel)) {
			return false;
		}

		CommerceDiscountRuleCacheModel commerceDiscountRuleCacheModel = (CommerceDiscountRuleCacheModel)obj;

		if (commerceDiscountRuleId == commerceDiscountRuleCacheModel.commerceDiscountRuleId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceDiscountRuleId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{commerceDiscountRuleId=");
		sb.append(commerceDiscountRuleId);
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
		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDiscountRule toEntityModel() {
		CommerceDiscountRuleImpl commerceDiscountRuleImpl = new CommerceDiscountRuleImpl();

		commerceDiscountRuleImpl.setCommerceDiscountRuleId(commerceDiscountRuleId);
		commerceDiscountRuleImpl.setGroupId(groupId);
		commerceDiscountRuleImpl.setCompanyId(companyId);
		commerceDiscountRuleImpl.setUserId(userId);

		if (userName == null) {
			commerceDiscountRuleImpl.setUserName("");
		}
		else {
			commerceDiscountRuleImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceDiscountRuleImpl.setCreateDate(null);
		}
		else {
			commerceDiscountRuleImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceDiscountRuleImpl.setModifiedDate(null);
		}
		else {
			commerceDiscountRuleImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceDiscountRuleImpl.setCommerceDiscountId(commerceDiscountId);

		if (type == null) {
			commerceDiscountRuleImpl.setType("");
		}
		else {
			commerceDiscountRuleImpl.setType(type);
		}

		if (typeSettings == null) {
			commerceDiscountRuleImpl.setTypeSettings("");
		}
		else {
			commerceDiscountRuleImpl.setTypeSettings(typeSettings);
		}

		commerceDiscountRuleImpl.resetOriginalValues();

		return commerceDiscountRuleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceDiscountRuleId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceDiscountId = objectInput.readLong();
		type = objectInput.readUTF();
		typeSettings = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceDiscountRuleId);

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

		objectOutput.writeLong(commerceDiscountId);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}
	}

	public long commerceDiscountRuleId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceDiscountId;
	public String type;
	public String typeSettings;
}