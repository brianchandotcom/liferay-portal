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

package com.liferay.document.library.model.impl;

import com.liferay.document.library.model.DLStorageQuota;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DLStorageQuota in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLStorageQuotaCacheModel
	implements CacheModel<DLStorageQuota>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DLStorageQuotaCacheModel)) {
			return false;
		}

		DLStorageQuotaCacheModel dlStorageQuotaCacheModel =
			(DLStorageQuotaCacheModel)object;

		if ((dlStorageQuotaId == dlStorageQuotaCacheModel.dlStorageQuotaId) &&
			(mvccVersion == dlStorageQuotaCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, dlStorageQuotaId);

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
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", dlStorageQuotaId=");
		sb.append(dlStorageQuotaId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", storageSize=");
		sb.append(storageSize);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DLStorageQuota toEntityModel() {
		DLStorageQuotaImpl dlStorageQuotaImpl = new DLStorageQuotaImpl();

		dlStorageQuotaImpl.setMvccVersion(mvccVersion);
		dlStorageQuotaImpl.setCtCollectionId(ctCollectionId);
		dlStorageQuotaImpl.setDlStorageQuotaId(dlStorageQuotaId);
		dlStorageQuotaImpl.setCompanyId(companyId);
		dlStorageQuotaImpl.setStorageSize(storageSize);

		dlStorageQuotaImpl.resetOriginalValues();

		return dlStorageQuotaImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		dlStorageQuotaId = objectInput.readLong();

		companyId = objectInput.readLong();

		storageSize = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(dlStorageQuotaId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(storageSize);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long dlStorageQuotaId;
	public long companyId;
	public long storageSize;

}