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

package com.liferay.commerce.shop.by.diagram.model.impl;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDefinitionDiagramEntry in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CPDefinitionDiagramEntryCacheModel
	implements CacheModel<CPDefinitionDiagramEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionDiagramEntryCacheModel)) {
			return false;
		}

		CPDefinitionDiagramEntryCacheModel cpDefinitionDiagramEntryCacheModel =
			(CPDefinitionDiagramEntryCacheModel)object;

		if (CPDefinitionDiagramEntryId ==
				cpDefinitionDiagramEntryCacheModel.CPDefinitionDiagramEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionDiagramEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{CPDefinitionDiagramEntryId=");
		sb.append(CPDefinitionDiagramEntryId);
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
		sb.append(", CPDefinitionDiagramSettingsId=");
		sb.append(CPDefinitionDiagramSettingsId);
		sb.append(", CPInstanceUuid=");
		sb.append(CPInstanceUuid);
		sb.append(", CProductId=");
		sb.append(CProductId);
		sb.append(", number=");
		sb.append(number);
		sb.append(", quantity=");
		sb.append(quantity);
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
	public CPDefinitionDiagramEntry toEntityModel() {
		CPDefinitionDiagramEntryImpl cpDefinitionDiagramEntryImpl =
			new CPDefinitionDiagramEntryImpl();

		cpDefinitionDiagramEntryImpl.setCPDefinitionDiagramEntryId(
			CPDefinitionDiagramEntryId);
		cpDefinitionDiagramEntryImpl.setCompanyId(companyId);
		cpDefinitionDiagramEntryImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionDiagramEntryImpl.setUserName("");
		}
		else {
			cpDefinitionDiagramEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionDiagramEntryImpl.setCreateDate(null);
		}
		else {
			cpDefinitionDiagramEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionDiagramEntryImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionDiagramEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		cpDefinitionDiagramEntryImpl.setCPDefinitionDiagramSettingsId(
			CPDefinitionDiagramSettingsId);

		if (CPInstanceUuid == null) {
			cpDefinitionDiagramEntryImpl.setCPInstanceUuid("");
		}
		else {
			cpDefinitionDiagramEntryImpl.setCPInstanceUuid(CPInstanceUuid);
		}

		cpDefinitionDiagramEntryImpl.setCProductId(CProductId);
		cpDefinitionDiagramEntryImpl.setNumber(number);
		cpDefinitionDiagramEntryImpl.setQuantity(quantity);
		cpDefinitionDiagramEntryImpl.setPositionX(positionX);
		cpDefinitionDiagramEntryImpl.setPositionY(positionY);
		cpDefinitionDiagramEntryImpl.setRadius(radius);

		cpDefinitionDiagramEntryImpl.resetOriginalValues();

		return cpDefinitionDiagramEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CPDefinitionDiagramEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionDiagramSettingsId = objectInput.readLong();
		CPInstanceUuid = objectInput.readUTF();

		CProductId = objectInput.readLong();

		number = objectInput.readInt();

		quantity = objectInput.readInt();

		positionX = objectInput.readDouble();

		positionY = objectInput.readDouble();

		radius = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(CPDefinitionDiagramEntryId);

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

		objectOutput.writeLong(CPDefinitionDiagramSettingsId);

		if (CPInstanceUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(CPInstanceUuid);
		}

		objectOutput.writeLong(CProductId);

		objectOutput.writeInt(number);

		objectOutput.writeInt(quantity);

		objectOutput.writeDouble(positionX);

		objectOutput.writeDouble(positionY);

		objectOutput.writeDouble(radius);
	}

	public long CPDefinitionDiagramEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionDiagramSettingsId;
	public String CPInstanceUuid;
	public long CProductId;
	public int number;
	public int quantity;
	public double positionX;
	public double positionY;
	public double radius;

}