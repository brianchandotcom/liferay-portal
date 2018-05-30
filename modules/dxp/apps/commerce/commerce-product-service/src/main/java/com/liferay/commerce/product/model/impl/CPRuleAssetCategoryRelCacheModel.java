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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPRuleAssetCategoryRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPRuleAssetCategoryRel in entity cache.
 *
 * @author Marco Leo
 * @see CPRuleAssetCategoryRel
 * @generated
 */
@ProviderType
public class CPRuleAssetCategoryRelCacheModel implements CacheModel<CPRuleAssetCategoryRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPRuleAssetCategoryRelCacheModel)) {
			return false;
		}

		CPRuleAssetCategoryRelCacheModel cpRuleAssetCategoryRelCacheModel = (CPRuleAssetCategoryRelCacheModel)obj;

		if (CPRuleAssetCategoryRelId == cpRuleAssetCategoryRelCacheModel.CPRuleAssetCategoryRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPRuleAssetCategoryRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{CPRuleAssetCategoryRelId=");
		sb.append(CPRuleAssetCategoryRelId);
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
		sb.append(", CPRuleId=");
		sb.append(CPRuleId);
		sb.append(", assetCategoryId=");
		sb.append(assetCategoryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPRuleAssetCategoryRel toEntityModel() {
		CPRuleAssetCategoryRelImpl cpRuleAssetCategoryRelImpl = new CPRuleAssetCategoryRelImpl();

		cpRuleAssetCategoryRelImpl.setCPRuleAssetCategoryRelId(CPRuleAssetCategoryRelId);
		cpRuleAssetCategoryRelImpl.setGroupId(groupId);
		cpRuleAssetCategoryRelImpl.setCompanyId(companyId);
		cpRuleAssetCategoryRelImpl.setUserId(userId);

		if (userName == null) {
			cpRuleAssetCategoryRelImpl.setUserName("");
		}
		else {
			cpRuleAssetCategoryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpRuleAssetCategoryRelImpl.setCreateDate(null);
		}
		else {
			cpRuleAssetCategoryRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpRuleAssetCategoryRelImpl.setModifiedDate(null);
		}
		else {
			cpRuleAssetCategoryRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpRuleAssetCategoryRelImpl.setCPRuleId(CPRuleId);
		cpRuleAssetCategoryRelImpl.setAssetCategoryId(assetCategoryId);

		cpRuleAssetCategoryRelImpl.resetOriginalValues();

		return cpRuleAssetCategoryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CPRuleAssetCategoryRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPRuleId = objectInput.readLong();

		assetCategoryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(CPRuleAssetCategoryRelId);

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

		objectOutput.writeLong(CPRuleId);

		objectOutput.writeLong(assetCategoryId);
	}

	public long CPRuleAssetCategoryRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPRuleId;
	public long assetCategoryId;
}