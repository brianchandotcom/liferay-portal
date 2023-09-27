/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model.impl;

import com.liferay.change.tracking.model.CTProcess;
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
 * The cache model class for representing CTProcess in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTProcessCacheModel
	implements CacheModel<CTProcess>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CTProcessCacheModel)) {
			return false;
		}

		CTProcessCacheModel ctProcessCacheModel = (CTProcessCacheModel)object;

		if ((ctProcessId == ctProcessCacheModel.ctProcessId) &&
			(mvccVersion == ctProcessCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ctProcessId);

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctProcessId=");
		sb.append(ctProcessId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", backgroundTaskId=");
		sb.append(backgroundTaskId);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTProcess toEntityModel() {
		CTProcessImpl ctProcessImpl = new CTProcessImpl();

		ctProcessImpl.setMvccVersion(mvccVersion);
		ctProcessImpl.setCtProcessId(ctProcessId);
		ctProcessImpl.setCompanyId(companyId);
		ctProcessImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			ctProcessImpl.setCreateDate(null);
		}
		else {
			ctProcessImpl.setCreateDate(new Date(createDate));
		}

		ctProcessImpl.setCtCollectionId(ctCollectionId);
		ctProcessImpl.setBackgroundTaskId(backgroundTaskId);
		ctProcessImpl.setType(type);

		ctProcessImpl.resetOriginalValues();

		return ctProcessImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctProcessId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		backgroundTaskId = objectInput.readLong();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctProcessId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(backgroundTaskId);

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public long ctProcessId;
	public long companyId;
	public long userId;
	public long createDate;
	public long ctCollectionId;
	public long backgroundTaskId;
	public int type;

}