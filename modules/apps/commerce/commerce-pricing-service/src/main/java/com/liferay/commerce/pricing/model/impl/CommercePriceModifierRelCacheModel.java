/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.model.impl;

import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
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
 * The cache model class for representing CommercePriceModifierRel in entity cache.
 *
 * @author Riccardo Alberti
 * @generated
 */
public class CommercePriceModifierRelCacheModel
	implements CacheModel<CommercePriceModifierRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceModifierRelCacheModel)) {
			return false;
		}

		CommercePriceModifierRelCacheModel commercePriceModifierRelCacheModel =
			(CommercePriceModifierRelCacheModel)object;

		if ((commercePriceModifierRelId ==
				commercePriceModifierRelCacheModel.
					commercePriceModifierRelId) &&
			(mvccVersion == commercePriceModifierRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commercePriceModifierRelId);

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
		sb.append(", commercePriceModifierRelId=");
		sb.append(commercePriceModifierRelId);
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
		sb.append(", commercePriceModifierId=");
		sb.append(commercePriceModifierId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePriceModifierRel toEntityModel() {
		CommercePriceModifierRelImpl commercePriceModifierRelImpl =
			new CommercePriceModifierRelImpl();

		commercePriceModifierRelImpl.setMvccVersion(mvccVersion);
		commercePriceModifierRelImpl.setCtCollectionId(ctCollectionId);
		commercePriceModifierRelImpl.setCommercePriceModifierRelId(
			commercePriceModifierRelId);
		commercePriceModifierRelImpl.setCompanyId(companyId);
		commercePriceModifierRelImpl.setUserId(userId);

		if (userName == null) {
			commercePriceModifierRelImpl.setUserName("");
		}
		else {
			commercePriceModifierRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceModifierRelImpl.setCreateDate(null);
		}
		else {
			commercePriceModifierRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceModifierRelImpl.setModifiedDate(null);
		}
		else {
			commercePriceModifierRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commercePriceModifierRelImpl.setCommercePriceModifierId(
			commercePriceModifierId);
		commercePriceModifierRelImpl.setClassNameId(classNameId);
		commercePriceModifierRelImpl.setClassPK(classPK);

		commercePriceModifierRelImpl.resetOriginalValues();

		return commercePriceModifierRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		commercePriceModifierRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commercePriceModifierId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(commercePriceModifierRelId);

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

		objectOutput.writeLong(commercePriceModifierId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long commercePriceModifierRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commercePriceModifierId;
	public long classNameId;
	public long classPK;

}