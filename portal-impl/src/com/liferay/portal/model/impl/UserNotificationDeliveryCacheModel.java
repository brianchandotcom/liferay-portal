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
import com.liferay.portal.model.CacheModel;
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
public class UserNotificationDeliveryCacheModel implements CacheModel<UserNotificationDelivery>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{userNotificationDeliveryId=");
		sb.append(userNotificationDeliveryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", email=");
		sb.append(email);
		sb.append(", sms=");
		sb.append(sms);
		sb.append(", website=");
		sb.append(website);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserNotificationDelivery toEntityModel() {
		UserNotificationDeliveryImpl userNotificationDeliveryImpl = new UserNotificationDeliveryImpl();

		userNotificationDeliveryImpl.setUserNotificationDeliveryId(userNotificationDeliveryId);
		userNotificationDeliveryImpl.setCompanyId(companyId);
		userNotificationDeliveryImpl.setUserId(userId);
		userNotificationDeliveryImpl.setClassNameId(classNameId);
		userNotificationDeliveryImpl.setType(type);
		userNotificationDeliveryImpl.setEmail(email);
		userNotificationDeliveryImpl.setSms(sms);
		userNotificationDeliveryImpl.setWebsite(website);

		userNotificationDeliveryImpl.resetOriginalValues();

		return userNotificationDeliveryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		userNotificationDeliveryId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		classNameId = objectInput.readLong();
		type = objectInput.readInt();
		email = objectInput.readBoolean();
		sms = objectInput.readBoolean();
		website = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(userNotificationDeliveryId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);
		objectOutput.writeLong(classNameId);
		objectOutput.writeInt(type);
		objectOutput.writeBoolean(email);
		objectOutput.writeBoolean(sms);
		objectOutput.writeBoolean(website);
	}

	public long userNotificationDeliveryId;
	public long companyId;
	public long userId;
	public long classNameId;
	public int type;
	public boolean email;
	public boolean sms;
	public boolean website;
}