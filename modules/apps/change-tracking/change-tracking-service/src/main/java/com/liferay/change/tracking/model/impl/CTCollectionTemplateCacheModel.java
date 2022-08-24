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

import com.liferay.change.tracking.model.CTCollectionTemplate;
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
 * The cache model class for representing CTCollectionTemplate in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTCollectionTemplateCacheModel
	implements CacheModel<CTCollectionTemplate>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CTCollectionTemplateCacheModel)) {
			return false;
		}

		CTCollectionTemplateCacheModel ctCollectionTemplateCacheModel =
			(CTCollectionTemplateCacheModel)object;

		if ((ctCollectionTemplateId ==
				ctCollectionTemplateCacheModel.ctCollectionTemplateId) &&
			(mvccVersion == ctCollectionTemplateCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ctCollectionTemplateId);

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
		sb.append(", ctCollectionTemplateId=");
		sb.append(ctCollectionTemplateId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTCollectionTemplate toEntityModel() {
		CTCollectionTemplateImpl ctCollectionTemplateImpl =
			new CTCollectionTemplateImpl();

		ctCollectionTemplateImpl.setMvccVersion(mvccVersion);
		ctCollectionTemplateImpl.setCtCollectionTemplateId(
			ctCollectionTemplateId);
		ctCollectionTemplateImpl.setCompanyId(companyId);
		ctCollectionTemplateImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			ctCollectionTemplateImpl.setCreateDate(null);
		}
		else {
			ctCollectionTemplateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ctCollectionTemplateImpl.setModifiedDate(null);
		}
		else {
			ctCollectionTemplateImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			ctCollectionTemplateImpl.setName("");
		}
		else {
			ctCollectionTemplateImpl.setName(name);
		}

		if (description == null) {
			ctCollectionTemplateImpl.setDescription("");
		}
		else {
			ctCollectionTemplateImpl.setDescription(description);
		}

		ctCollectionTemplateImpl.resetOriginalValues();

		return ctCollectionTemplateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionTemplateId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionTemplateId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}
	}

	public long mvccVersion;
	public long ctCollectionTemplateId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;

}