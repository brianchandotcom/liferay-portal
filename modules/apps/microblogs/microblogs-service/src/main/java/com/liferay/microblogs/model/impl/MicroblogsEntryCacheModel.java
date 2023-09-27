/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.model.impl;

import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MicroblogsEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MicroblogsEntryCacheModel
	implements CacheModel<MicroblogsEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MicroblogsEntryCacheModel)) {
			return false;
		}

		MicroblogsEntryCacheModel microblogsEntryCacheModel =
			(MicroblogsEntryCacheModel)object;

		if (microblogsEntryId == microblogsEntryCacheModel.microblogsEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, microblogsEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{microblogsEntryId=");
		sb.append(microblogsEntryId);
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
		sb.append(", creatorClassNameId=");
		sb.append(creatorClassNameId);
		sb.append(", creatorClassPK=");
		sb.append(creatorClassPK);
		sb.append(", content=");
		sb.append(content);
		sb.append(", type=");
		sb.append(type);
		sb.append(", parentMicroblogsEntryId=");
		sb.append(parentMicroblogsEntryId);
		sb.append(", socialRelationType=");
		sb.append(socialRelationType);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MicroblogsEntry toEntityModel() {
		MicroblogsEntryImpl microblogsEntryImpl = new MicroblogsEntryImpl();

		microblogsEntryImpl.setMicroblogsEntryId(microblogsEntryId);
		microblogsEntryImpl.setCompanyId(companyId);
		microblogsEntryImpl.setUserId(userId);

		if (userName == null) {
			microblogsEntryImpl.setUserName("");
		}
		else {
			microblogsEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			microblogsEntryImpl.setCreateDate(null);
		}
		else {
			microblogsEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			microblogsEntryImpl.setModifiedDate(null);
		}
		else {
			microblogsEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		microblogsEntryImpl.setCreatorClassNameId(creatorClassNameId);
		microblogsEntryImpl.setCreatorClassPK(creatorClassPK);

		if (content == null) {
			microblogsEntryImpl.setContent("");
		}
		else {
			microblogsEntryImpl.setContent(content);
		}

		microblogsEntryImpl.setType(type);
		microblogsEntryImpl.setParentMicroblogsEntryId(parentMicroblogsEntryId);
		microblogsEntryImpl.setSocialRelationType(socialRelationType);

		microblogsEntryImpl.resetOriginalValues();

		return microblogsEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		microblogsEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		creatorClassNameId = objectInput.readLong();

		creatorClassPK = objectInput.readLong();
		content = objectInput.readUTF();

		type = objectInput.readInt();

		parentMicroblogsEntryId = objectInput.readLong();

		socialRelationType = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(microblogsEntryId);

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

		objectOutput.writeLong(creatorClassNameId);

		objectOutput.writeLong(creatorClassPK);

		if (content == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(content);
		}

		objectOutput.writeInt(type);

		objectOutput.writeLong(parentMicroblogsEntryId);

		objectOutput.writeInt(socialRelationType);
	}

	public long microblogsEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long creatorClassNameId;
	public long creatorClassPK;
	public String content;
	public int type;
	public long parentMicroblogsEntryId;
	public int socialRelationType;

}