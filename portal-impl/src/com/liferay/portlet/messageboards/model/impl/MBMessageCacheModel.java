/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.messageboards.model.MBMessage;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing MBMessage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessage
 * @generated
 */
public class MBMessageCacheModel implements CacheModel<MBMessage>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(53);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", messageId=");
		sb.append(messageId);
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
		sb.append(", categoryId=");
		sb.append(categoryId);
		sb.append(", threadId=");
		sb.append(threadId);
		sb.append(", rootMessageId=");
		sb.append(rootMessageId);
		sb.append(", parentMessageId=");
		sb.append(parentMessageId);
		sb.append(", subject=");
		sb.append(subject);
		sb.append(", body=");
		sb.append(body);
		sb.append(", format=");
		sb.append(format);
		sb.append(", attachments=");
		sb.append(attachments);
		sb.append(", anonymous=");
		sb.append(anonymous);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", allowPingbacks=");
		sb.append(allowPingbacks);
		sb.append(", answer=");
		sb.append(answer);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	public MBMessage toEntityModel() {
		MBMessageImpl mbMessageImpl = new MBMessageImpl();

		if (uuid == null) {
			mbMessageImpl.setUuid(StringPool.BLANK);
		}
		else {
			mbMessageImpl.setUuid(uuid);
		}

		mbMessageImpl.setMessageId(messageId);
		mbMessageImpl.setGroupId(groupId);
		mbMessageImpl.setCompanyId(companyId);
		mbMessageImpl.setUserId(userId);

		if (userName == null) {
			mbMessageImpl.setUserName(StringPool.BLANK);
		}
		else {
			mbMessageImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mbMessageImpl.setCreateDate(null);
		}
		else {
			mbMessageImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mbMessageImpl.setModifiedDate(null);
		}
		else {
			mbMessageImpl.setModifiedDate(new Date(modifiedDate));
		}

		mbMessageImpl.setClassNameId(classNameId);
		mbMessageImpl.setClassPK(classPK);
		mbMessageImpl.setCategoryId(categoryId);
		mbMessageImpl.setThreadId(threadId);
		mbMessageImpl.setRootMessageId(rootMessageId);
		mbMessageImpl.setParentMessageId(parentMessageId);

		if (subject == null) {
			mbMessageImpl.setSubject(StringPool.BLANK);
		}
		else {
			mbMessageImpl.setSubject(subject);
		}

		if (body == null) {
			mbMessageImpl.setBody(StringPool.BLANK);
		}
		else {
			mbMessageImpl.setBody(body);
		}

		if (format == null) {
			mbMessageImpl.setFormat(StringPool.BLANK);
		}
		else {
			mbMessageImpl.setFormat(format);
		}

		mbMessageImpl.setAttachments(attachments);
		mbMessageImpl.setAnonymous(anonymous);
		mbMessageImpl.setPriority(priority);
		mbMessageImpl.setAllowPingbacks(allowPingbacks);
		mbMessageImpl.setAnswer(answer);
		mbMessageImpl.setStatus(status);
		mbMessageImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			mbMessageImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			mbMessageImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			mbMessageImpl.setStatusDate(null);
		}
		else {
			mbMessageImpl.setStatusDate(new Date(statusDate));
		}

		mbMessageImpl.resetOriginalValues();

		return mbMessageImpl;
	}

	public String uuid;
	public long messageId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long categoryId;
	public long threadId;
	public long rootMessageId;
	public long parentMessageId;
	public String subject;
	public String body;
	public String format;
	public boolean attachments;
	public boolean anonymous;
	public double priority;
	public boolean allowPingbacks;
	public boolean answer;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}