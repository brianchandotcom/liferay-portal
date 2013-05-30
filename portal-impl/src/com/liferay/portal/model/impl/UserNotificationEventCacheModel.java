/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.UserNotificationEvent;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing UserNotificationEvent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEvent
 * @generated
 */
public class UserNotificationEventCacheModel implements CacheModel<UserNotificationEvent>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", userNotificationEventId=");
		sb.append(userNotificationEventId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", timestamp=");
		sb.append(timestamp);
		sb.append(", deliverBy=");
		sb.append(deliverBy);
		sb.append(", delivered=");
		sb.append(delivered);
		sb.append(", payload=");
		sb.append(payload);
		sb.append(", archived=");
		sb.append(archived);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserNotificationEvent toEntityModel() {
		UserNotificationEventImpl userNotificationEventImpl = new UserNotificationEventImpl();

		if (uuid == null) {
			userNotificationEventImpl.setUuid(StringPool.BLANK);
		}
		else {
			userNotificationEventImpl.setUuid(uuid);
		}

		userNotificationEventImpl.setUserNotificationEventId(userNotificationEventId);
		userNotificationEventImpl.setCompanyId(companyId);
		userNotificationEventImpl.setUserId(userId);

		if (type == null) {
			userNotificationEventImpl.setType(StringPool.BLANK);
		}
		else {
			userNotificationEventImpl.setType(type);
		}

		userNotificationEventImpl.setTimestamp(timestamp);
		userNotificationEventImpl.setDeliverBy(deliverBy);
		userNotificationEventImpl.setDelivered(delivered);

		if (payload == null) {
			userNotificationEventImpl.setPayload(StringPool.BLANK);
		}
		else {
			userNotificationEventImpl.setPayload(payload);
		}

		userNotificationEventImpl.setArchived(archived);

		userNotificationEventImpl.resetOriginalValues();

		return userNotificationEventImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		userNotificationEventId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		type = objectInput.readUTF();
		timestamp = objectInput.readLong();
		deliverBy = objectInput.readLong();
		delivered = objectInput.readBoolean();
		payload = objectInput.readUTF();
		archived = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(userNotificationEventId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (type == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeLong(timestamp);
		objectOutput.writeLong(deliverBy);
		objectOutput.writeBoolean(delivered);

		if (payload == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(payload);
		}

		objectOutput.writeBoolean(archived);
	}

	public String uuid;
	public long userNotificationEventId;
	public long companyId;
	public long userId;
	public String type;
	public long timestamp;
	public long deliverBy;
	public boolean delivered;
	public String payload;
	public boolean archived;
}