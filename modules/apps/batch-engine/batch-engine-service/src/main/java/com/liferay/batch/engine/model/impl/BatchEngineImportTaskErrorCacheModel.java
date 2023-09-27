/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.model.impl;

import com.liferay.batch.engine.model.BatchEngineImportTaskError;
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
 * The cache model class for representing BatchEngineImportTaskError in entity cache.
 *
 * @author Shuyang Zhou
 * @generated
 */
public class BatchEngineImportTaskErrorCacheModel
	implements CacheModel<BatchEngineImportTaskError>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BatchEngineImportTaskErrorCacheModel)) {
			return false;
		}

		BatchEngineImportTaskErrorCacheModel
			batchEngineImportTaskErrorCacheModel =
				(BatchEngineImportTaskErrorCacheModel)object;

		if ((batchEngineImportTaskErrorId ==
				batchEngineImportTaskErrorCacheModel.
					batchEngineImportTaskErrorId) &&
			(mvccVersion == batchEngineImportTaskErrorCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, batchEngineImportTaskErrorId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", batchEngineImportTaskErrorId=");
		sb.append(batchEngineImportTaskErrorId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", batchEngineImportTaskId=");
		sb.append(batchEngineImportTaskId);
		sb.append(", item=");
		sb.append(item);
		sb.append(", itemIndex=");
		sb.append(itemIndex);
		sb.append(", message=");
		sb.append(message);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BatchEngineImportTaskError toEntityModel() {
		BatchEngineImportTaskErrorImpl batchEngineImportTaskErrorImpl =
			new BatchEngineImportTaskErrorImpl();

		batchEngineImportTaskErrorImpl.setMvccVersion(mvccVersion);
		batchEngineImportTaskErrorImpl.setBatchEngineImportTaskErrorId(
			batchEngineImportTaskErrorId);
		batchEngineImportTaskErrorImpl.setCompanyId(companyId);
		batchEngineImportTaskErrorImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			batchEngineImportTaskErrorImpl.setCreateDate(null);
		}
		else {
			batchEngineImportTaskErrorImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			batchEngineImportTaskErrorImpl.setModifiedDate(null);
		}
		else {
			batchEngineImportTaskErrorImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		batchEngineImportTaskErrorImpl.setBatchEngineImportTaskId(
			batchEngineImportTaskId);

		if (item == null) {
			batchEngineImportTaskErrorImpl.setItem("");
		}
		else {
			batchEngineImportTaskErrorImpl.setItem(item);
		}

		batchEngineImportTaskErrorImpl.setItemIndex(itemIndex);

		if (message == null) {
			batchEngineImportTaskErrorImpl.setMessage("");
		}
		else {
			batchEngineImportTaskErrorImpl.setMessage(message);
		}

		batchEngineImportTaskErrorImpl.resetOriginalValues();

		return batchEngineImportTaskErrorImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		batchEngineImportTaskErrorId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		batchEngineImportTaskId = objectInput.readLong();
		item = (String)objectInput.readObject();

		itemIndex = objectInput.readInt();
		message = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(batchEngineImportTaskErrorId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(batchEngineImportTaskId);

		if (item == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(item);
		}

		objectOutput.writeInt(itemIndex);

		if (message == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(message);
		}
	}

	public long mvccVersion;
	public long batchEngineImportTaskErrorId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long batchEngineImportTaskId;
	public String item;
	public int itemIndex;
	public String message;

}