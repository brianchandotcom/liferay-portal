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

import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
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
 * The cache model class for representing CSDiagramEntry in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CSDiagramEntryCacheModel
	implements CacheModel<CSDiagramEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CSDiagramEntryCacheModel)) {
			return false;
		}

		CSDiagramEntryCacheModel csDiagramEntryCacheModel =
			(CSDiagramEntryCacheModel)object;

		if ((CSDiagramEntryId == csDiagramEntryCacheModel.CSDiagramEntryId) &&
			(mvccVersion == csDiagramEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CSDiagramEntryId);

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
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", CSDiagramEntryId=");
		sb.append(CSDiagramEntryId);
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
		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);
		sb.append(", CProductId=");
		sb.append(CProductId);
		sb.append(", diagram=");
		sb.append(diagram);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append(", sequence=");
		sb.append(sequence);
		sb.append(", sku=");
		sb.append(sku);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CSDiagramEntry toEntityModel() {
		CSDiagramEntryImpl csDiagramEntryImpl = new CSDiagramEntryImpl();

		csDiagramEntryImpl.setMvccVersion(mvccVersion);
		csDiagramEntryImpl.setCtCollectionId(ctCollectionId);
		csDiagramEntryImpl.setCSDiagramEntryId(CSDiagramEntryId);
		csDiagramEntryImpl.setCompanyId(companyId);
		csDiagramEntryImpl.setUserId(userId);

		if (userName == null) {
			csDiagramEntryImpl.setUserName("");
		}
		else {
			csDiagramEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			csDiagramEntryImpl.setCreateDate(null);
		}
		else {
			csDiagramEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			csDiagramEntryImpl.setModifiedDate(null);
		}
		else {
			csDiagramEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		csDiagramEntryImpl.setCPDefinitionId(CPDefinitionId);
		csDiagramEntryImpl.setCPInstanceId(CPInstanceId);
		csDiagramEntryImpl.setCProductId(CProductId);
		csDiagramEntryImpl.setDiagram(diagram);
		csDiagramEntryImpl.setQuantity(quantity);

		if (sequence == null) {
			csDiagramEntryImpl.setSequence("");
		}
		else {
			csDiagramEntryImpl.setSequence(sequence);
		}

		if (sku == null) {
			csDiagramEntryImpl.setSku("");
		}
		else {
			csDiagramEntryImpl.setSku(sku);
		}

		csDiagramEntryImpl.resetOriginalValues();

		return csDiagramEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		CSDiagramEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		CPInstanceId = objectInput.readLong();

		CProductId = objectInput.readLong();

		diagram = objectInput.readBoolean();

		quantity = objectInput.readInt();
		sequence = objectInput.readUTF();
		sku = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(CSDiagramEntryId);

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

		objectOutput.writeLong(CPInstanceId);

		objectOutput.writeLong(CProductId);

		objectOutput.writeBoolean(diagram);

		objectOutput.writeInt(quantity);

		if (sequence == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sequence);
		}

		if (sku == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sku);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long CSDiagramEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public long CPInstanceId;
	public long CProductId;
	public boolean diagram;
	public int quantity;
	public String sequence;
	public String sku;

}