/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model.impl;

import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
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
 * The cache model class for representing CommerceNotificationQueueEntry in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceNotificationQueueEntryCacheModel
	implements CacheModel<CommerceNotificationQueueEntry>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceNotificationQueueEntryCacheModel)) {
			return false;
		}

		CommerceNotificationQueueEntryCacheModel
			commerceNotificationQueueEntryCacheModel =
				(CommerceNotificationQueueEntryCacheModel)object;

		if ((commerceNotificationQueueEntryId ==
				commerceNotificationQueueEntryCacheModel.
					commerceNotificationQueueEntryId) &&
			(mvccVersion ==
				commerceNotificationQueueEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceNotificationQueueEntryId);

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
		StringBundler sb = new StringBundler(45);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceNotificationQueueEntryId=");
		sb.append(commerceNotificationQueueEntryId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", commerceNotificationTemplateId=");
		sb.append(commerceNotificationTemplateId);
		sb.append(", from=");
		sb.append(from);
		sb.append(", fromName=");
		sb.append(fromName);
		sb.append(", to=");
		sb.append(to);
		sb.append(", toName=");
		sb.append(toName);
		sb.append(", cc=");
		sb.append(cc);
		sb.append(", bcc=");
		sb.append(bcc);
		sb.append(", subject=");
		sb.append(subject);
		sb.append(", body=");
		sb.append(body);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", sent=");
		sb.append(sent);
		sb.append(", sentDate=");
		sb.append(sentDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceNotificationQueueEntry toEntityModel() {
		CommerceNotificationQueueEntryImpl commerceNotificationQueueEntryImpl =
			new CommerceNotificationQueueEntryImpl();

		commerceNotificationQueueEntryImpl.setMvccVersion(mvccVersion);
		commerceNotificationQueueEntryImpl.setCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId);
		commerceNotificationQueueEntryImpl.setGroupId(groupId);
		commerceNotificationQueueEntryImpl.setCompanyId(companyId);
		commerceNotificationQueueEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceNotificationQueueEntryImpl.setUserName("");
		}
		else {
			commerceNotificationQueueEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceNotificationQueueEntryImpl.setCreateDate(null);
		}
		else {
			commerceNotificationQueueEntryImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceNotificationQueueEntryImpl.setModifiedDate(null);
		}
		else {
			commerceNotificationQueueEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceNotificationQueueEntryImpl.setClassNameId(classNameId);
		commerceNotificationQueueEntryImpl.setClassPK(classPK);
		commerceNotificationQueueEntryImpl.setCommerceNotificationTemplateId(
			commerceNotificationTemplateId);

		if (from == null) {
			commerceNotificationQueueEntryImpl.setFrom("");
		}
		else {
			commerceNotificationQueueEntryImpl.setFrom(from);
		}

		if (fromName == null) {
			commerceNotificationQueueEntryImpl.setFromName("");
		}
		else {
			commerceNotificationQueueEntryImpl.setFromName(fromName);
		}

		if (to == null) {
			commerceNotificationQueueEntryImpl.setTo("");
		}
		else {
			commerceNotificationQueueEntryImpl.setTo(to);
		}

		if (toName == null) {
			commerceNotificationQueueEntryImpl.setToName("");
		}
		else {
			commerceNotificationQueueEntryImpl.setToName(toName);
		}

		if (cc == null) {
			commerceNotificationQueueEntryImpl.setCc("");
		}
		else {
			commerceNotificationQueueEntryImpl.setCc(cc);
		}

		if (bcc == null) {
			commerceNotificationQueueEntryImpl.setBcc("");
		}
		else {
			commerceNotificationQueueEntryImpl.setBcc(bcc);
		}

		if (subject == null) {
			commerceNotificationQueueEntryImpl.setSubject("");
		}
		else {
			commerceNotificationQueueEntryImpl.setSubject(subject);
		}

		if (body == null) {
			commerceNotificationQueueEntryImpl.setBody("");
		}
		else {
			commerceNotificationQueueEntryImpl.setBody(body);
		}

		commerceNotificationQueueEntryImpl.setPriority(priority);
		commerceNotificationQueueEntryImpl.setSent(sent);

		if (sentDate == Long.MIN_VALUE) {
			commerceNotificationQueueEntryImpl.setSentDate(null);
		}
		else {
			commerceNotificationQueueEntryImpl.setSentDate(new Date(sentDate));
		}

		commerceNotificationQueueEntryImpl.resetOriginalValues();

		return commerceNotificationQueueEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		commerceNotificationQueueEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		commerceNotificationTemplateId = objectInput.readLong();
		from = objectInput.readUTF();
		fromName = objectInput.readUTF();
		to = objectInput.readUTF();
		toName = objectInput.readUTF();
		cc = objectInput.readUTF();
		bcc = objectInput.readUTF();
		subject = objectInput.readUTF();
		body = (String)objectInput.readObject();

		priority = objectInput.readDouble();

		sent = objectInput.readBoolean();
		sentDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceNotificationQueueEntryId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(commerceNotificationTemplateId);

		if (from == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(from);
		}

		if (fromName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fromName);
		}

		if (to == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(to);
		}

		if (toName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(toName);
		}

		if (cc == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(cc);
		}

		if (bcc == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(bcc);
		}

		if (subject == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(subject);
		}

		if (body == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(body);
		}

		objectOutput.writeDouble(priority);

		objectOutput.writeBoolean(sent);
		objectOutput.writeLong(sentDate);
	}

	public long mvccVersion;
	public long commerceNotificationQueueEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long commerceNotificationTemplateId;
	public String from;
	public String fromName;
	public String to;
	public String toName;
	public String cc;
	public String bcc;
	public String subject;
	public String body;
	public double priority;
	public boolean sent;
	public long sentDate;

}