/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model.impl;

import com.liferay.change.tracking.model.CTComment;
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
 * The cache model class for representing CTComment in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTCommentCacheModel
	implements CacheModel<CTComment>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CTCommentCacheModel)) {
			return false;
		}

		CTCommentCacheModel ctCommentCacheModel = (CTCommentCacheModel)object;

		if ((ctCommentId == ctCommentCacheModel.ctCommentId) &&
			(mvccVersion == ctCommentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ctCommentId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCommentId=");
		sb.append(ctCommentId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", ctEntryId=");
		sb.append(ctEntryId);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTComment toEntityModel() {
		CTCommentImpl ctCommentImpl = new CTCommentImpl();

		ctCommentImpl.setMvccVersion(mvccVersion);
		ctCommentImpl.setCtCommentId(ctCommentId);
		ctCommentImpl.setCompanyId(companyId);
		ctCommentImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			ctCommentImpl.setCreateDate(null);
		}
		else {
			ctCommentImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ctCommentImpl.setModifiedDate(null);
		}
		else {
			ctCommentImpl.setModifiedDate(new Date(modifiedDate));
		}

		ctCommentImpl.setCtCollectionId(ctCollectionId);
		ctCommentImpl.setCtEntryId(ctEntryId);

		if (value == null) {
			ctCommentImpl.setValue("");
		}
		else {
			ctCommentImpl.setValue(value);
		}

		ctCommentImpl.resetOriginalValues();

		return ctCommentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCommentId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		ctEntryId = objectInput.readLong();
		value = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCommentId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(ctEntryId);

		if (value == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(value);
		}
	}

	public long mvccVersion;
	public long ctCommentId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long ctCollectionId;
	public long ctEntryId;
	public String value;

}