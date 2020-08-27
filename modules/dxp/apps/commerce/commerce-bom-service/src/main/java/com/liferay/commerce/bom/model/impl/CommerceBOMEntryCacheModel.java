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

import com.liferay.commerce.bom.model.CommerceBOMEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceBOMEntry in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceBOMEntryCacheModel
	implements CacheModel<CommerceBOMEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceBOMEntryCacheModel)) {
			return false;
		}

		CommerceBOMEntryCacheModel commerceBOMEntryCacheModel =
			(CommerceBOMEntryCacheModel)object;

		if (commerceBOMEntryId ==
				commerceBOMEntryCacheModel.commerceBOMEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceBOMEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{commerceBOMEntryId=");
		sb.append(commerceBOMEntryId);
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
		sb.append(", number=");
		sb.append(number);
		sb.append(", CPInstanceUuid=");
		sb.append(CPInstanceUuid);
		sb.append(", CProductId=");
		sb.append(CProductId);
		sb.append(", commerceBOMDefinitionId=");
		sb.append(commerceBOMDefinitionId);
		sb.append(", positionX=");
		sb.append(positionX);
		sb.append(", positionY=");
		sb.append(positionY);
		sb.append(", radius=");
		sb.append(radius);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceBOMEntry toEntityModel() {
		CommerceBOMEntryImpl commerceBOMEntryImpl = new CommerceBOMEntryImpl();

		commerceBOMEntryImpl.setCommerceBOMEntryId(commerceBOMEntryId);
		commerceBOMEntryImpl.setCompanyId(companyId);
		commerceBOMEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceBOMEntryImpl.setUserName("");
		}
		else {
			commerceBOMEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceBOMEntryImpl.setCreateDate(null);
		}
		else {
			commerceBOMEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceBOMEntryImpl.setModifiedDate(null);
		}
		else {
			commerceBOMEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceBOMEntryImpl.setNumber(number);

		if (CPInstanceUuid == null) {
			commerceBOMEntryImpl.setCPInstanceUuid("");
		}
		else {
			commerceBOMEntryImpl.setCPInstanceUuid(CPInstanceUuid);
		}

		commerceBOMEntryImpl.setCProductId(CProductId);
		commerceBOMEntryImpl.setCommerceBOMDefinitionId(
			commerceBOMDefinitionId);
		commerceBOMEntryImpl.setPositionX(positionX);
		commerceBOMEntryImpl.setPositionY(positionY);
		commerceBOMEntryImpl.setRadius(radius);

		commerceBOMEntryImpl.resetOriginalValues();

		return commerceBOMEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceBOMEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		number = objectInput.readInt();
		CPInstanceUuid = objectInput.readUTF();

		CProductId = objectInput.readLong();

		commerceBOMDefinitionId = objectInput.readLong();

		positionX = objectInput.readDouble();

		positionY = objectInput.readDouble();

		radius = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(commerceBOMEntryId);

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

		objectOutput.writeInt(number);

		if (CPInstanceUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(CPInstanceUuid);
		}

		objectOutput.writeLong(CProductId);

		objectOutput.writeLong(commerceBOMDefinitionId);

		objectOutput.writeDouble(positionX);

		objectOutput.writeDouble(positionY);

		objectOutput.writeDouble(radius);
	}

	public long commerceBOMEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public int number;
	public String CPInstanceUuid;
	public long CProductId;
	public long commerceBOMDefinitionId;
	public double positionX;
	public double positionY;
	public double radius;

}