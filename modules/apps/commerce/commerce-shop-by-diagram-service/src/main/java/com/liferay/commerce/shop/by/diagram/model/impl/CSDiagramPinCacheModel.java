/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.shop.by.diagram.model.impl;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
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
 * The cache model class for representing CSDiagramPin in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CSDiagramPinCacheModel
	implements CacheModel<CSDiagramPin>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CSDiagramPinCacheModel)) {
			return false;
		}

		CSDiagramPinCacheModel csDiagramPinCacheModel =
			(CSDiagramPinCacheModel)object;

		if ((CSDiagramPinId == csDiagramPinCacheModel.CSDiagramPinId) &&
			(mvccVersion == csDiagramPinCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CSDiagramPinId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", CSDiagramPinId=");
		sb.append(CSDiagramPinId);
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
		sb.append(", positionX=");
		sb.append(positionX);
		sb.append(", positionY=");
		sb.append(positionY);
		sb.append(", sequence=");
		sb.append(sequence);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CSDiagramPin toEntityModel() {
		CSDiagramPinImpl csDiagramPinImpl = new CSDiagramPinImpl();

		csDiagramPinImpl.setMvccVersion(mvccVersion);
		csDiagramPinImpl.setCtCollectionId(ctCollectionId);
		csDiagramPinImpl.setCSDiagramPinId(CSDiagramPinId);
		csDiagramPinImpl.setCompanyId(companyId);
		csDiagramPinImpl.setUserId(userId);

		if (userName == null) {
			csDiagramPinImpl.setUserName("");
		}
		else {
			csDiagramPinImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			csDiagramPinImpl.setCreateDate(null);
		}
		else {
			csDiagramPinImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			csDiagramPinImpl.setModifiedDate(null);
		}
		else {
			csDiagramPinImpl.setModifiedDate(new Date(modifiedDate));
		}

		csDiagramPinImpl.setCPDefinitionId(CPDefinitionId);
		csDiagramPinImpl.setPositionX(positionX);
		csDiagramPinImpl.setPositionY(positionY);

		if (sequence == null) {
			csDiagramPinImpl.setSequence("");
		}
		else {
			csDiagramPinImpl.setSequence(sequence);
		}

		csDiagramPinImpl.resetOriginalValues();

		return csDiagramPinImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		CSDiagramPinId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		positionX = objectInput.readDouble();

		positionY = objectInput.readDouble();
		sequence = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(CSDiagramPinId);

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

		objectOutput.writeDouble(positionX);

		objectOutput.writeDouble(positionY);

		if (sequence == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sequence);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long CSDiagramPinId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public double positionX;
	public double positionY;
	public String sequence;

}