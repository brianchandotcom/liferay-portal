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

import com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceBOMFolderApplicationRel in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceBOMFolderApplicationRelCacheModel
	implements CacheModel<CommerceBOMFolderApplicationRel>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceBOMFolderApplicationRelCacheModel)) {
			return false;
		}

		CommerceBOMFolderApplicationRelCacheModel
			commerceBOMFolderApplicationRelCacheModel =
				(CommerceBOMFolderApplicationRelCacheModel)object;

		if (commerceBOMFolderApplicationRelId ==
				commerceBOMFolderApplicationRelCacheModel.
					commerceBOMFolderApplicationRelId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceBOMFolderApplicationRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{commerceBOMFolderApplicationRelId=");
		sb.append(commerceBOMFolderApplicationRelId);
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
		sb.append(", commerceApplicationModelId=");
		sb.append(commerceApplicationModelId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceBOMFolderApplicationRel toEntityModel() {
		CommerceBOMFolderApplicationRelImpl
			commerceBOMFolderApplicationRelImpl =
				new CommerceBOMFolderApplicationRelImpl();

		commerceBOMFolderApplicationRelImpl.
			setCommerceBOMFolderApplicationRelId(
				commerceBOMFolderApplicationRelId);
		commerceBOMFolderApplicationRelImpl.setCompanyId(companyId);
		commerceBOMFolderApplicationRelImpl.setUserId(userId);

		if (userName == null) {
			commerceBOMFolderApplicationRelImpl.setUserName("");
		}
		else {
			commerceBOMFolderApplicationRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceBOMFolderApplicationRelImpl.setCreateDate(null);
		}
		else {
			commerceBOMFolderApplicationRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceBOMFolderApplicationRelImpl.setModifiedDate(null);
		}
		else {
			commerceBOMFolderApplicationRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceBOMFolderApplicationRelImpl.setCommerceBOMFolderId(
			commerceBOMFolderId);
		commerceBOMFolderApplicationRelImpl.setCommerceApplicationModelId(
			commerceApplicationModelId);

		commerceBOMFolderApplicationRelImpl.resetOriginalValues();

		return commerceBOMFolderApplicationRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceBOMFolderApplicationRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceBOMFolderId = objectInput.readLong();

		commerceApplicationModelId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(commerceBOMFolderApplicationRelId);

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

		objectOutput.writeLong(commerceApplicationModelId);
	}

	public long commerceBOMFolderApplicationRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceBOMFolderId;
	public long commerceApplicationModelId;

}