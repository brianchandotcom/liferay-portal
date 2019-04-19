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

package com.liferay.batch.engine.model.impl;

import com.liferay.batch.engine.model.BatchFileImport;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing BatchFileImport in entity cache.
 *
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class BatchFileImportCacheModel
	implements CacheModel<BatchFileImport>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BatchFileImportCacheModel)) {
			return false;
		}

		BatchFileImportCacheModel batchFileImportCacheModel =
			(BatchFileImportCacheModel)obj;

		if (batchFileImportId == batchFileImportCacheModel.batchFileImportId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, batchFileImportId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", batchFileImportId=");
		sb.append(batchFileImportId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", batchJobExecutionId=");
		sb.append(batchJobExecutionId);
		sb.append(", domainName=");
		sb.append(domainName);
		sb.append(", version=");
		sb.append(version);
		sb.append(", operation=");
		sb.append(operation);
		sb.append(", callbackURL=");
		sb.append(callbackURL);
		sb.append(", columnNames=");
		sb.append(columnNames);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchFileImport toEntityModel() {
		BatchFileImportImpl batchFileImportImpl = new BatchFileImportImpl();

		if (uuid == null) {
			batchFileImportImpl.setUuid("");
		}
		else {
			batchFileImportImpl.setUuid(uuid);
		}

		batchFileImportImpl.setBatchFileImportId(batchFileImportId);
		batchFileImportImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			batchFileImportImpl.setCreateDate(null);
		}
		else {
			batchFileImportImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchFileImportImpl.setModifiedDate(null);
		}
		else {
			batchFileImportImpl.setModifiedDate(new Date(modifiedDate));
		}

		batchFileImportImpl.setFileEntryId(fileEntryId);
		batchFileImportImpl.setBatchJobExecutionId(batchJobExecutionId);

		if (domainName == null) {
			batchFileImportImpl.setDomainName("");
		}
		else {
			batchFileImportImpl.setDomainName(domainName);
		}

		if (version == null) {
			batchFileImportImpl.setVersion("");
		}
		else {
			batchFileImportImpl.setVersion(version);
		}

		if (operation == null) {
			batchFileImportImpl.setOperation("");
		}
		else {
			batchFileImportImpl.setOperation(operation);
		}

		if (callbackURL == null) {
			batchFileImportImpl.setCallbackURL("");
		}
		else {
			batchFileImportImpl.setCallbackURL(callbackURL);
		}

		if (columnNames == null) {
			batchFileImportImpl.setColumnNames("");
		}
		else {
			batchFileImportImpl.setColumnNames(columnNames);
		}

		if (status == null) {
			batchFileImportImpl.setStatus("");
		}
		else {
			batchFileImportImpl.setStatus(status);
		}

		batchFileImportImpl.resetOriginalValues();

		return batchFileImportImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		batchFileImportId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		fileEntryId = objectInput.readLong();

		batchJobExecutionId = objectInput.readLong();
		domainName = objectInput.readUTF();
		version = objectInput.readUTF();
		operation = objectInput.readUTF();
		callbackURL = objectInput.readUTF();
		columnNames = objectInput.readUTF();
		status = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(batchFileImportId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(fileEntryId);

		objectOutput.writeLong(batchJobExecutionId);

		if (domainName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(domainName);
		}

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		if (operation == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(operation);
		}

		if (callbackURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(callbackURL);
		}

		if (columnNames == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(columnNames);
		}

		if (status == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(status);
		}
	}

	public String uuid;
	public long batchFileImportId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long fileEntryId;
	public long batchJobExecutionId;
	public String domainName;
	public String version;
	public String operation;
	public String callbackURL;
	public String columnNames;
	public String status;

}