/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.qualifier.model.impl;

import com.liferay.commerce.qualifier.model.CommerceQualifierEntry;
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
 * The cache model class for representing CommerceQualifierEntry in entity cache.
 *
 * @author Riccardo Alberti
 * @generated
 */
public class CommerceQualifierEntryCacheModel
	implements CacheModel<CommerceQualifierEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceQualifierEntryCacheModel)) {
			return false;
		}

		CommerceQualifierEntryCacheModel commerceQualifierEntryCacheModel =
			(CommerceQualifierEntryCacheModel)object;

		if ((commerceQualifierEntryId ==
				commerceQualifierEntryCacheModel.commerceQualifierEntryId) &&
			(mvccVersion == commerceQualifierEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceQualifierEntryId);

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
		sb.append(", commerceQualifierEntryId=");
		sb.append(commerceQualifierEntryId);
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
		sb.append(", sourceClassNameId=");
		sb.append(sourceClassNameId);
		sb.append(", sourceClassPK=");
		sb.append(sourceClassPK);
		sb.append(", sourceCommerceQualifierMetadataKey=");
		sb.append(sourceCommerceQualifierMetadataKey);
		sb.append(", targetClassNameId=");
		sb.append(targetClassNameId);
		sb.append(", targetClassPK=");
		sb.append(targetClassPK);
		sb.append(", targetCommerceQualifierMetadataKey=");
		sb.append(targetCommerceQualifierMetadataKey);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceQualifierEntry toEntityModel() {
		CommerceQualifierEntryImpl commerceQualifierEntryImpl =
			new CommerceQualifierEntryImpl();

		commerceQualifierEntryImpl.setMvccVersion(mvccVersion);
		commerceQualifierEntryImpl.setCommerceQualifierEntryId(
			commerceQualifierEntryId);
		commerceQualifierEntryImpl.setCompanyId(companyId);
		commerceQualifierEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceQualifierEntryImpl.setUserName("");
		}
		else {
			commerceQualifierEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceQualifierEntryImpl.setCreateDate(null);
		}
		else {
			commerceQualifierEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceQualifierEntryImpl.setModifiedDate(null);
		}
		else {
			commerceQualifierEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceQualifierEntryImpl.setSourceClassNameId(sourceClassNameId);
		commerceQualifierEntryImpl.setSourceClassPK(sourceClassPK);

		if (sourceCommerceQualifierMetadataKey == null) {
			commerceQualifierEntryImpl.setSourceCommerceQualifierMetadataKey(
				"");
		}
		else {
			commerceQualifierEntryImpl.setSourceCommerceQualifierMetadataKey(
				sourceCommerceQualifierMetadataKey);
		}

		commerceQualifierEntryImpl.setTargetClassNameId(targetClassNameId);
		commerceQualifierEntryImpl.setTargetClassPK(targetClassPK);

		if (targetCommerceQualifierMetadataKey == null) {
			commerceQualifierEntryImpl.setTargetCommerceQualifierMetadataKey(
				"");
		}
		else {
			commerceQualifierEntryImpl.setTargetCommerceQualifierMetadataKey(
				targetCommerceQualifierMetadataKey);
		}

		commerceQualifierEntryImpl.resetOriginalValues();

		return commerceQualifierEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceQualifierEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		sourceClassNameId = objectInput.readLong();

		sourceClassPK = objectInput.readLong();
		sourceCommerceQualifierMetadataKey = objectInput.readUTF();

		targetClassNameId = objectInput.readLong();

		targetClassPK = objectInput.readLong();
		targetCommerceQualifierMetadataKey = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceQualifierEntryId);

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

		objectOutput.writeLong(sourceClassNameId);

		objectOutput.writeLong(sourceClassPK);

		if (sourceCommerceQualifierMetadataKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sourceCommerceQualifierMetadataKey);
		}

		objectOutput.writeLong(targetClassNameId);

		objectOutput.writeLong(targetClassPK);

		if (targetCommerceQualifierMetadataKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(targetCommerceQualifierMetadataKey);
		}
	}

	public long mvccVersion;
	public long commerceQualifierEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long sourceClassNameId;
	public long sourceClassPK;
	public String sourceCommerceQualifierMetadataKey;
	public long targetClassNameId;
	public long targetClassPK;
	public String targetCommerceQualifierMetadataKey;

}