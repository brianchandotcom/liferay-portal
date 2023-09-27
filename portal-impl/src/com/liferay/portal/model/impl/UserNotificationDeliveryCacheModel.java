/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.UserNotificationDelivery;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing UserNotificationDelivery in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserNotificationDeliveryCacheModel
	implements CacheModel<UserNotificationDelivery>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UserNotificationDeliveryCacheModel)) {
			return false;
		}

		UserNotificationDeliveryCacheModel userNotificationDeliveryCacheModel =
			(UserNotificationDeliveryCacheModel)object;

		if ((userNotificationDeliveryId ==
				userNotificationDeliveryCacheModel.
					userNotificationDeliveryId) &&
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
		sb.append(", companyId=");
		sb.append(companyId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UserNotificationDelivery toEntityModel() {
		UserNotificationDeliveryImpl userNotificationDeliveryImpl =
			new UserNotificationDeliveryImpl();

		userNotificationDeliveryImpl.setMvccVersion(mvccVersion);
		userNotificationDeliveryImpl.setUserNotificationDeliveryId(
			userNotificationDeliveryId);
		userNotificationDeliveryImpl.setCompanyId(companyId);
		userNotificationDeliveryImpl.setUserId(userId);

		if (portletId == null) {
			userNotificationDeliveryImpl.setPortletId("");
		}
		else {
			userNotificationDeliveryImpl.setPortletId(portletId);
		}

		userNotificationDeliveryImpl.setClassNameId(classNameId);
		userNotificationDeliveryImpl.setNotificationType(notificationType);
		userNotificationDeliveryImpl.setDeliveryType(deliveryType);
		userNotificationDeliveryImpl.setDeliver(deliver);

		userNotificationDeliveryImpl.resetOriginalValues();

		return userNotificationDeliveryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		userNotificationDeliveryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		portletId = objectInput.readUTF();

		classNameId = objectInput.readLong();

		notificationType = objectInput.readInt();

		deliveryType = objectInput.readInt();

		deliver = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(userNotificationDeliveryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		objectOutput.writeLong(classNameId);

		objectOutput.writeInt(notificationType);

		objectOutput.writeInt(deliveryType);

		objectOutput.writeBoolean(deliver);
	}

	public long mvccVersion;
	public long userNotificationDeliveryId;
	public long companyId;
	public long userId;
	public String portletId;
	public long classNameId;
	public int notificationType;
	public int deliveryType;
	public boolean deliver;

}