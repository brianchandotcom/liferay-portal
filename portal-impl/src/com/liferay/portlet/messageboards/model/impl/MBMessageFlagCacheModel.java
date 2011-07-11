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
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.messageboards.model.MBMessageFlag;

import java.util.Date;

/**
 * The cache model class for representing MBMessageFlag in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see MBMessageFlag
 * @generated
 */
public class MBMessageFlagCacheModel implements CacheModel<MBMessageFlag> {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{messageFlagId=");
		sb.append(messageFlagId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", threadId=");
		sb.append(threadId);
		sb.append(", messageId=");
		sb.append(messageId);
		sb.append(", flag=");
		sb.append(flag);
		sb.append("}");

		return sb.toString();
	}

	public MBMessageFlag toEntityModel() {
		MBMessageFlagImpl mbMessageFlagImpl = new MBMessageFlagImpl();

		mbMessageFlagImpl.setMessageFlagId(messageFlagId);
		mbMessageFlagImpl.setUserId(userId);
		mbMessageFlagImpl.setModifiedDate(new Date(modifiedDate));
		mbMessageFlagImpl.setThreadId(threadId);
		mbMessageFlagImpl.setMessageId(messageId);
		mbMessageFlagImpl.setFlag(flag);

		mbMessageFlagImpl.resetOriginalValues();

		return mbMessageFlagImpl;
	}

	public long messageFlagId;
	public long userId;
	public long modifiedDate;
	public long threadId;
	public long messageId;
	public int flag;
}