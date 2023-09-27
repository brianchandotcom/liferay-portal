/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.model.impl;

import com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ContactsLayoutTemplate in entity cache.
 *
 * @author Shinn Lok
 * @generated
 */
public class ContactsLayoutTemplateCacheModel
	implements CacheModel<ContactsLayoutTemplate>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ContactsLayoutTemplateCacheModel)) {
			return false;
		}

		ContactsLayoutTemplateCacheModel contactsLayoutTemplateCacheModel =
			(ContactsLayoutTemplateCacheModel)object;

		if ((contactsLayoutTemplateId ==
				contactsLayoutTemplateCacheModel.contactsLayoutTemplateId) &&
			(mvccVersion == contactsLayoutTemplateCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, contactsLayoutTemplateId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", contactsLayoutTemplateId=");
		sb.append(contactsLayoutTemplateId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createTime=");
		sb.append(createTime);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", modifiedTime=");
		sb.append(modifiedTime);
		sb.append(", headerContactsCardTemplateIds=");
		sb.append(headerContactsCardTemplateIds);
		sb.append(", name=");
		sb.append(name);
		sb.append(", settings=");
		sb.append(settings);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ContactsLayoutTemplate toEntityModel() {
		ContactsLayoutTemplateImpl contactsLayoutTemplateImpl =
			new ContactsLayoutTemplateImpl();

		contactsLayoutTemplateImpl.setMvccVersion(mvccVersion);
		contactsLayoutTemplateImpl.setContactsLayoutTemplateId(
			contactsLayoutTemplateId);
		contactsLayoutTemplateImpl.setGroupId(groupId);
		contactsLayoutTemplateImpl.setCompanyId(companyId);
		contactsLayoutTemplateImpl.setCreateTime(createTime);
		contactsLayoutTemplateImpl.setUserId(userId);

		if (userName == null) {
			contactsLayoutTemplateImpl.setUserName("");
		}
		else {
			contactsLayoutTemplateImpl.setUserName(userName);
		}

		contactsLayoutTemplateImpl.setModifiedTime(modifiedTime);

		if (headerContactsCardTemplateIds == null) {
			contactsLayoutTemplateImpl.setHeaderContactsCardTemplateIds("");
		}
		else {
			contactsLayoutTemplateImpl.setHeaderContactsCardTemplateIds(
				headerContactsCardTemplateIds);
		}

		if (name == null) {
			contactsLayoutTemplateImpl.setName("");
		}
		else {
			contactsLayoutTemplateImpl.setName(name);
		}

		if (settings == null) {
			contactsLayoutTemplateImpl.setSettings("");
		}
		else {
			contactsLayoutTemplateImpl.setSettings(settings);
		}

		contactsLayoutTemplateImpl.setType(type);

		contactsLayoutTemplateImpl.resetOriginalValues();

		return contactsLayoutTemplateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		contactsLayoutTemplateId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		createTime = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		modifiedTime = objectInput.readLong();
		headerContactsCardTemplateIds = objectInput.readUTF();
		name = objectInput.readUTF();
		settings = objectInput.readUTF();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(contactsLayoutTemplateId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(createTime);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(modifiedTime);

		if (headerContactsCardTemplateIds == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(headerContactsCardTemplateIds);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (settings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(settings);
		}

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public long contactsLayoutTemplateId;
	public long groupId;
	public long companyId;
	public long createTime;
	public long userId;
	public String userName;
	public long modifiedTime;
	public String headerContactsCardTemplateIds;
	public String name;
	public String settings;
	public int type;

}