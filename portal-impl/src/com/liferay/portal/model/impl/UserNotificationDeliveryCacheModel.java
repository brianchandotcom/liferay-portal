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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.UserNotificationDelivery;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing UserNotificationDelivery in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationDelivery
 * @generated
 */
@ProviderType
public class UserNotificationDeliveryCacheModel implements CacheModel<UserNotificationDelivery>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserNotificationDeliveryCacheModel)) {
			return false;
		}

		UserNotificationDeliveryCacheModel userNotificationDeliveryCacheModel = (UserNotificationDeliveryCacheModel)obj;

		if ((userNotificationDeliveryId == userNotificationDeliveryCacheModel.userNotificationDeliveryId) &&
				(mvccVersion == userNotificationDeliveryCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, userNotificationDeliveryId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", userNotificationDeliveryId=");
		sb.append(userNotificationDeliveryId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", notificationType=");
		sb.append(notificationType);
		sb.append(", deliveryType=");
		sb.append(deliveryType);
		sb.append(", deliver=");
		sb.append(deliver);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserNotificationDelivery toEntityModel() {
		UserNotificationDeliveryImpl userNotificationDeliveryImpl = new UserNotificationDeliveryImpl();

		userNotificationDeliveryImpl.setMvccVersion(mvccVersion);
		userNotificationDeliveryImpl.setUserNotificationDeliveryId(userNotificationDeliveryId);
		userNotificationDeliveryImpl.setUserId(userId);

		if (portletId == null) {
			userNotificationDeliveryImpl.setPortletId(StringPool.BLANK);
		}
		else {
			userNotificationDeliveryImpl.setPortletId(portletId);
		}

		userNotificationDeliveryImpl.setClassNameId(classNameId);
		userNotificationDeliveryImpl.setNotificationType(notificationType);
		userNotificationDeliveryImpl.setDeliveryType(deliveryType);
		userNotificationDeliveryImpl.setDeliver(deliver);
		userNotificationDeliveryImpl.setCompanyId(companyId);

		userNotificationDeliveryImpl.resetOriginalValues();

		return userNotificationDeliveryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		userNotificationDeliveryId = objectInput.readLong();
		userId = objectInput.readLong();
		portletId = objectInput.readUTF();
		classNameId = objectInput.readLong();
		notificationType = objectInput.readInt();
		deliveryType = objectInput.readInt();
		deliver = objectInput.readBoolean();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);
		objectOutput.writeLong(userNotificationDeliveryId);
		objectOutput.writeLong(userId);

		if (portletId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		objectOutput.writeLong(classNameId);
		objectOutput.writeInt(notificationType);
		objectOutput.writeInt(deliveryType);
		objectOutput.writeBoolean(deliver);
		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public long userNotificationDeliveryId;
	public long userId;
	public String portletId;
	public long classNameId;
	public int notificationType;
	public int deliveryType;
	public boolean deliver;
	public long companyId;
}