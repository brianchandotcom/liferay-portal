/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat720.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.compat720.model.BasicEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing BasicEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BasicEntryCacheModel
	implements CacheModel<BasicEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BasicEntryCacheModel)) {
			return false;
		}

		BasicEntryCacheModel basicEntryCacheModel =
			(BasicEntryCacheModel)object;

		if (basicEntryId == basicEntryCacheModel.basicEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, basicEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{basicEntryId=");
		sb.append(basicEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public BasicEntry toEntityModel() {
		BasicEntryImpl basicEntryImpl = new BasicEntryImpl();

		basicEntryImpl.setBasicEntryId(basicEntryId);
		basicEntryImpl.setGroupId(groupId);
		basicEntryImpl.setCompanyId(companyId);
		basicEntryImpl.setUserId(userId);

		if (userName == null) {
			basicEntryImpl.setUserName("");
		}
		else {
			basicEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			basicEntryImpl.setCreateDate(null);
		}
		else {
			basicEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			basicEntryImpl.setModifiedDate(null);
		}
		else {
			basicEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			basicEntryImpl.setName("");
		}
		else {
			basicEntryImpl.setName(name);
		}

		if (description == null) {
			basicEntryImpl.setDescription("");
		}
		else {
			basicEntryImpl.setDescription(description);
		}

		basicEntryImpl.resetOriginalValues();

		return basicEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		basicEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(basicEntryId);

		objectOutput.writeLong(groupId);

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

	public long basicEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;

}
// LIFERAY-SERVICE-BUILDER-HASH:-325630535