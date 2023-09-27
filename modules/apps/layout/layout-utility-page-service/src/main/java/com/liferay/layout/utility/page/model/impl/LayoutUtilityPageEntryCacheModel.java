/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.model.impl;

import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
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
 * The cache model class for representing LayoutUtilityPageEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutUtilityPageEntryCacheModel
	implements CacheModel<LayoutUtilityPageEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutUtilityPageEntryCacheModel)) {
			return false;
		}

		LayoutUtilityPageEntryCacheModel layoutUtilityPageEntryCacheModel =
			(LayoutUtilityPageEntryCacheModel)object;

		if ((LayoutUtilityPageEntryId ==
				layoutUtilityPageEntryCacheModel.LayoutUtilityPageEntryId) &&
			(mvccVersion == layoutUtilityPageEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, LayoutUtilityPageEntryId);

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
		StringBundler sb = new StringBundler(35);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", LayoutUtilityPageEntryId=");
		sb.append(LayoutUtilityPageEntryId);
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
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", previewFileEntryId=");
		sb.append(previewFileEntryId);
		sb.append(", defaultLayoutUtilityPageEntry=");
		sb.append(defaultLayoutUtilityPageEntry);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutUtilityPageEntry toEntityModel() {
		LayoutUtilityPageEntryImpl layoutUtilityPageEntryImpl =
			new LayoutUtilityPageEntryImpl();

		layoutUtilityPageEntryImpl.setMvccVersion(mvccVersion);
		layoutUtilityPageEntryImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			layoutUtilityPageEntryImpl.setUuid("");
		}
		else {
			layoutUtilityPageEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			layoutUtilityPageEntryImpl.setExternalReferenceCode("");
		}
		else {
			layoutUtilityPageEntryImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		layoutUtilityPageEntryImpl.setLayoutUtilityPageEntryId(
			LayoutUtilityPageEntryId);
		layoutUtilityPageEntryImpl.setGroupId(groupId);
		layoutUtilityPageEntryImpl.setCompanyId(companyId);
		layoutUtilityPageEntryImpl.setUserId(userId);

		if (userName == null) {
			layoutUtilityPageEntryImpl.setUserName("");
		}
		else {
			layoutUtilityPageEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutUtilityPageEntryImpl.setCreateDate(null);
		}
		else {
			layoutUtilityPageEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutUtilityPageEntryImpl.setModifiedDate(null);
		}
		else {
			layoutUtilityPageEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutUtilityPageEntryImpl.setPlid(plid);
		layoutUtilityPageEntryImpl.setPreviewFileEntryId(previewFileEntryId);
		layoutUtilityPageEntryImpl.setDefaultLayoutUtilityPageEntry(
			defaultLayoutUtilityPageEntry);

		if (name == null) {
			layoutUtilityPageEntryImpl.setName("");
		}
		else {
			layoutUtilityPageEntryImpl.setName(name);
		}

		if (type == null) {
			layoutUtilityPageEntryImpl.setType("");
		}
		else {
			layoutUtilityPageEntryImpl.setType(type);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			layoutUtilityPageEntryImpl.setLastPublishDate(null);
		}
		else {
			layoutUtilityPageEntryImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		layoutUtilityPageEntryImpl.resetOriginalValues();

		return layoutUtilityPageEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		LayoutUtilityPageEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		plid = objectInput.readLong();

		previewFileEntryId = objectInput.readLong();

		defaultLayoutUtilityPageEntry = objectInput.readBoolean();
		name = objectInput.readUTF();
		type = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(LayoutUtilityPageEntryId);

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

		objectOutput.writeLong(plid);

		objectOutput.writeLong(previewFileEntryId);

		objectOutput.writeBoolean(defaultLayoutUtilityPageEntry);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String externalReferenceCode;
	public long LayoutUtilityPageEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long plid;
	public long previewFileEntryId;
	public boolean defaultLayoutUtilityPageEntry;
	public String name;
	public String type;
	public long lastPublishDate;

}