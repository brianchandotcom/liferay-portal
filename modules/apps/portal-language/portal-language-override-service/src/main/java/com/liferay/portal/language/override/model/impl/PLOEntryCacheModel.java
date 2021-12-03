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

package com.liferay.portal.language.override.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.language.override.model.PLOEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PLOEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PLOEntryCacheModel
	implements CacheModel<PLOEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PLOEntryCacheModel)) {
			return false;
		}

		PLOEntryCacheModel ploEntryCacheModel = (PLOEntryCacheModel)object;

		if ((ploEntryId == ploEntryCacheModel.ploEntryId) &&
			(mvccVersion == ploEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ploEntryId);

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
		sb.append(", ploEntryId=");
		sb.append(ploEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", key=");
		sb.append(key);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PLOEntry toEntityModel() {
		PLOEntryImpl ploEntryImpl = new PLOEntryImpl();

		ploEntryImpl.setMvccVersion(mvccVersion);
		ploEntryImpl.setPloEntryId(ploEntryId);
		ploEntryImpl.setCompanyId(companyId);
		ploEntryImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			ploEntryImpl.setCreateDate(null);
		}
		else {
			ploEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ploEntryImpl.setModifiedDate(null);
		}
		else {
			ploEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (key == null) {
			ploEntryImpl.setKey("");
		}
		else {
			ploEntryImpl.setKey(key);
		}

		if (languageId == null) {
			ploEntryImpl.setLanguageId("");
		}
		else {
			ploEntryImpl.setLanguageId(languageId);
		}

		if (value == null) {
			ploEntryImpl.setValue("");
		}
		else {
			ploEntryImpl.setValue(value);
		}

		ploEntryImpl.resetOriginalValues();

		return ploEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ploEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		key = objectInput.readUTF();
		languageId = objectInput.readUTF();
		value = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ploEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (value == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(value);
		}
	}

	public long mvccVersion;
	public long ploEntryId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String key;
	public String languageId;
	public String value;

}