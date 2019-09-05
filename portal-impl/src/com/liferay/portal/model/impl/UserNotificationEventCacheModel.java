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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.UserNotificationEvent;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing UserNotificationEvent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserNotificationEventCacheModel
	implements CacheModel<UserNotificationEvent>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserNotificationEventCacheModel)) {
			return false;
		}

		UserNotificationEventCacheModel userNotificationEventCacheModel =
			(UserNotificationEventCacheModel)obj;

		if ((userNotificationEventId ==
				userNotificationEventCacheModel.userNotificationEventId) &&
			(mvccVersion == userNotificationEventCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, userNotificationEventId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
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
		sb.append(", deliveryType=");
		sb.append(deliveryType);
		sb.append(", deliverBy=");
		sb.append(deliverBy);
		sb.append(", delivered=");
		sb.append(delivered);
		sb.append(", payload=");
		sb.append(payload);
		sb.append(", actionRequired=");
		sb.append(actionRequired);
		sb.append(", archived=");
		sb.append(archived);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserNotificationEvent toEntityModel() {
		UserNotificationEventImpl userNotificationEventImpl =
			new UserNotificationEventImpl();

		userNotificationEventImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			userNotificationEventImpl.setUuid("");
		}
		else {
			userNotificationEventImpl.setUuid(uuid);
		}

		userNotificationEventImpl.setUserNotificationEventId(
			userNotificationEventId);
		userNotificationEventImpl.setCompanyId(companyId);
		userNotificationEventImpl.setUserId(userId);

		if (type == null) {
			userNotificationEventImpl.setType("");
		}
		else {
			userNotificationEventImpl.setType(type);
		}

		userNotificationEventImpl.setTimestamp(timestamp);
		userNotificationEventImpl.setDeliveryType(deliveryType);
		userNotificationEventImpl.setDeliverBy(deliverBy);
		userNotificationEventImpl.setDelivered(delivered);

		if (payload == null) {
			userNotificationEventImpl.setPayload("");
		}
		else {
			userNotificationEventImpl.setPayload(payload);
		}

		userNotificationEventImpl.setActionRequired(actionRequired);
		userNotificationEventImpl.setArchived(archived);

		userNotificationEventImpl.resetOriginalValues();

		return userNotificationEventImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		userNotificationEventId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		type = objectInput.readUTF();

		timestamp = objectInput.readLong();

		deliveryType = objectInput.readInt();

		deliverBy = objectInput.readLong();

		delivered = objectInput.readBoolean();
		payload = objectInput.readUTF();

		actionRequired = objectInput.readBoolean();

		archived = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(userNotificationEventId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeLong(timestamp);

		objectOutput.writeInt(deliveryType);

		objectOutput.writeLong(deliverBy);

		objectOutput.writeBoolean(delivered);

		if (payload == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(payload);
		}

		objectOutput.writeBoolean(actionRequired);

		objectOutput.writeBoolean(archived);
	}

	public long mvccVersion;
	public String uuid;
	public long userNotificationEventId;
	public long companyId;
	public long userId;
	public String type;
	public long timestamp;
	public int deliveryType;
	public long deliverBy;
	public boolean delivered;
	public String payload;
	public boolean actionRequired;
	public boolean archived;

}