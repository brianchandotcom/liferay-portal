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

package com.liferay.change.tracking.model.impl;

import com.liferay.change.tracking.model.CTAutoResolutionInfo;
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
 * The cache model class for representing CTAutoResolutionInfo in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTAutoResolutionInfoCacheModel
	implements CacheModel<CTAutoResolutionInfo>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CTAutoResolutionInfoCacheModel)) {
			return false;
		}

		CTAutoResolutionInfoCacheModel ctAutoResolutionInfoCacheModel =
			(CTAutoResolutionInfoCacheModel)obj;

		if ((ctAutoResolutionInfoId ==
				ctAutoResolutionInfoCacheModel.ctAutoResolutionInfoId) &&
			(mvccVersion == ctAutoResolutionInfoCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ctAutoResolutionInfoId);

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
		sb.append(", ctAutoResolutionInfoId=");
		sb.append(ctAutoResolutionInfoId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", modelClassNameId=");
		sb.append(modelClassNameId);
		sb.append(", sourceModelClassPK=");
		sb.append(sourceModelClassPK);
		sb.append(", targetModelClassPK=");
		sb.append(targetModelClassPK);
		sb.append(", conflictIdentifier=");
		sb.append(conflictIdentifier);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTAutoResolutionInfo toEntityModel() {
		CTAutoResolutionInfoImpl ctAutoResolutionInfoImpl =
			new CTAutoResolutionInfoImpl();

		ctAutoResolutionInfoImpl.setMvccVersion(mvccVersion);
		ctAutoResolutionInfoImpl.setCtAutoResolutionInfoId(
			ctAutoResolutionInfoId);
		ctAutoResolutionInfoImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			ctAutoResolutionInfoImpl.setCreateDate(null);
		}
		else {
			ctAutoResolutionInfoImpl.setCreateDate(new Date(createDate));
		}

		ctAutoResolutionInfoImpl.setCtCollectionId(ctCollectionId);
		ctAutoResolutionInfoImpl.setModelClassNameId(modelClassNameId);
		ctAutoResolutionInfoImpl.setSourceModelClassPK(sourceModelClassPK);
		ctAutoResolutionInfoImpl.setTargetModelClassPK(targetModelClassPK);

		if (conflictIdentifier == null) {
			ctAutoResolutionInfoImpl.setConflictIdentifier("");
		}
		else {
			ctAutoResolutionInfoImpl.setConflictIdentifier(conflictIdentifier);
		}

		ctAutoResolutionInfoImpl.resetOriginalValues();

		return ctAutoResolutionInfoImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctAutoResolutionInfoId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		modelClassNameId = objectInput.readLong();

		sourceModelClassPK = objectInput.readLong();

		targetModelClassPK = objectInput.readLong();
		conflictIdentifier = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctAutoResolutionInfoId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(modelClassNameId);

		objectOutput.writeLong(sourceModelClassPK);

		objectOutput.writeLong(targetModelClassPK);

		if (conflictIdentifier == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(conflictIdentifier);
		}
	}

	public long mvccVersion;
	public long ctAutoResolutionInfoId;
	public long companyId;
	public long createDate;
	public long ctCollectionId;
	public long modelClassNameId;
	public long sourceModelClassPK;
	public long targetModelClassPK;
	public String conflictIdentifier;

}