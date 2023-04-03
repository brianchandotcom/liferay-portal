/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.contacts.model.impl;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ContactsCardTemplate in entity cache.
 *
 * @author Shinn Lok
 * @generated
 */
public class ContactsCardTemplateCacheModel
	implements CacheModel<ContactsCardTemplate>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ContactsCardTemplateCacheModel)) {
			return false;
		}

		ContactsCardTemplateCacheModel contactsCardTemplateCacheModel =
			(ContactsCardTemplateCacheModel)object;

		if (contactsCardTemplateId ==
				contactsCardTemplateCacheModel.contactsCardTemplateId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, contactsCardTemplateId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{contactsCardTemplateId=");
		sb.append(contactsCardTemplateId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createTime=");
		sb.append(createTime);
		sb.append(", modifiedTime=");
		sb.append(modifiedTime);
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
	public ContactsCardTemplate toEntityModel() {
		ContactsCardTemplateImpl contactsCardTemplateImpl =
			new ContactsCardTemplateImpl();

		contactsCardTemplateImpl.setContactsCardTemplateId(
			contactsCardTemplateId);
		contactsCardTemplateImpl.setGroupId(groupId);
		contactsCardTemplateImpl.setUserId(userId);

		if (userName == null) {
			contactsCardTemplateImpl.setUserName("");
		}
		else {
			contactsCardTemplateImpl.setUserName(userName);
		}

		contactsCardTemplateImpl.setCreateTime(createTime);
		contactsCardTemplateImpl.setModifiedTime(modifiedTime);

		if (name == null) {
			contactsCardTemplateImpl.setName("");
		}
		else {
			contactsCardTemplateImpl.setName(name);
		}

		if (settings == null) {
			contactsCardTemplateImpl.setSettings("");
		}
		else {
			contactsCardTemplateImpl.setSettings(settings);
		}

		contactsCardTemplateImpl.setType(type);

		contactsCardTemplateImpl.resetOriginalValues();

		return contactsCardTemplateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		contactsCardTemplateId = objectInput.readLong();

		groupId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		createTime = objectInput.readLong();

		modifiedTime = objectInput.readLong();
		name = objectInput.readUTF();
		settings = objectInput.readUTF();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(contactsCardTemplateId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createTime);

		objectOutput.writeLong(modifiedTime);

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

	public long contactsCardTemplateId;
	public long groupId;
	public long userId;
	public String userName;
	public long createTime;
	public long modifiedTime;
	public String name;
	public String settings;
	public int type;

}