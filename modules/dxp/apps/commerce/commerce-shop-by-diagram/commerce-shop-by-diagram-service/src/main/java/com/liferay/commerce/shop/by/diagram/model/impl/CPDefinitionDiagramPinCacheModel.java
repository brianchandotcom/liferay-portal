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

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDefinitionDiagramPin in entity cache.
 *
 * @author Andrea Sbarra
 * @generated
 */
public class CPDefinitionDiagramPinCacheModel
	implements CacheModel<CPDefinitionDiagramPin>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionDiagramPinCacheModel)) {
			return false;
		}

		CPDefinitionDiagramPinCacheModel cpDefinitionDiagramPinCacheModel =
			(CPDefinitionDiagramPinCacheModel)object;

		if (CPDefinitionDiagramPinId ==
				cpDefinitionDiagramPinCacheModel.CPDefinitionDiagramPinId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionDiagramPinId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{CPDefinitionDiagramPinId=");
		sb.append(CPDefinitionDiagramPinId);
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
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", number=");
		sb.append(number);
		sb.append(", positionX=");
		sb.append(positionX);
		sb.append(", positionY=");
		sb.append(positionY);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionDiagramPin toEntityModel() {
		CPDefinitionDiagramPinImpl cpDefinitionDiagramPinImpl =
			new CPDefinitionDiagramPinImpl();

		cpDefinitionDiagramPinImpl.setCPDefinitionDiagramPinId(
			CPDefinitionDiagramPinId);
		cpDefinitionDiagramPinImpl.setCompanyId(companyId);
		cpDefinitionDiagramPinImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionDiagramPinImpl.setUserName("");
		}
		else {
			cpDefinitionDiagramPinImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionDiagramPinImpl.setCreateDate(null);
		}
		else {
			cpDefinitionDiagramPinImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionDiagramPinImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionDiagramPinImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpDefinitionDiagramPinImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionDiagramPinImpl.setNumber(number);
		cpDefinitionDiagramPinImpl.setPositionX(positionX);
		cpDefinitionDiagramPinImpl.setPositionY(positionY);

		cpDefinitionDiagramPinImpl.resetOriginalValues();

		return cpDefinitionDiagramPinImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CPDefinitionDiagramPinId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		number = objectInput.readInt();

		positionX = objectInput.readDouble();

		positionY = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(CPDefinitionDiagramPinId);

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

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeInt(number);

		objectOutput.writeDouble(positionX);

		objectOutput.writeDouble(positionY);
	}

	public long CPDefinitionDiagramPinId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public int number;
	public double positionX;
	public double positionY;

}