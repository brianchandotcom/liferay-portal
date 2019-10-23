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

package com.liferay.portlet.announcements.model.impl;

import com.liferay.announcements.kernel.model.AnnouncementsDelivery;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AnnouncementsDelivery in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnnouncementsDeliveryCacheModel
	implements CacheModel<AnnouncementsDelivery>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AnnouncementsDeliveryCacheModel)) {
			return false;
		}

		AnnouncementsDeliveryCacheModel announcementsDeliveryCacheModel =
			(AnnouncementsDeliveryCacheModel)obj;

		if ((deliveryId == announcementsDeliveryCacheModel.deliveryId) &&
			(mvccVersion == announcementsDeliveryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, deliveryId);

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", deliveryId=");
		sb.append(deliveryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
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
	public AnnouncementsDelivery toEntityModel() {
		AnnouncementsDeliveryImpl announcementsDeliveryImpl =
			new AnnouncementsDeliveryImpl();

		announcementsDeliveryImpl.setMvccVersion(mvccVersion);
		announcementsDeliveryImpl.setDeliveryId(deliveryId);
		announcementsDeliveryImpl.setCompanyId(companyId);
		announcementsDeliveryImpl.setUserId(userId);

		if (type == null) {
			announcementsDeliveryImpl.setType("");
		}
		else {
			announcementsDeliveryImpl.setType(type);
		}

		announcementsDeliveryImpl.setEmail(email);
		announcementsDeliveryImpl.setSms(sms);
		announcementsDeliveryImpl.setWebsite(website);

		announcementsDeliveryImpl.resetOriginalValues();

		return announcementsDeliveryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		deliveryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		type = objectInput.readUTF();

		email = objectInput.readBoolean();

		sms = objectInput.readBoolean();

		website = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(deliveryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeBoolean(email);

		objectOutput.writeBoolean(sms);

		objectOutput.writeBoolean(website);
	}

	public long mvccVersion;
	public long deliveryId;
	public long companyId;
	public long userId;
	public String type;
	public boolean email;
	public boolean sms;
	public boolean website;

}