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
import com.liferay.portal.kernel.model.PortletPreferences;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PortletPreferences in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortletPreferencesCacheModel
	implements CacheModel<PortletPreferences>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortletPreferencesCacheModel)) {
			return false;
		}

		PortletPreferencesCacheModel portletPreferencesCacheModel =
			(PortletPreferencesCacheModel)obj;

		if ((portletPreferencesId ==
				portletPreferencesCacheModel.portletPreferencesId) &&
			(mvccVersion == portletPreferencesCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, portletPreferencesId);

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
		sb.append(", portletPreferencesId=");
		sb.append(portletPreferencesId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", ownerId=");
		sb.append(ownerId);
		sb.append(", ownerType=");
		sb.append(ownerType);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", preferences=");
		sb.append(preferences);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PortletPreferences toEntityModel() {
		PortletPreferencesImpl portletPreferencesImpl =
			new PortletPreferencesImpl();

		portletPreferencesImpl.setMvccVersion(mvccVersion);
		portletPreferencesImpl.setPortletPreferencesId(portletPreferencesId);
		portletPreferencesImpl.setCompanyId(companyId);
		portletPreferencesImpl.setOwnerId(ownerId);
		portletPreferencesImpl.setOwnerType(ownerType);
		portletPreferencesImpl.setPlid(plid);

		if (portletId == null) {
			portletPreferencesImpl.setPortletId("");
		}
		else {
			portletPreferencesImpl.setPortletId(portletId);
		}

		if (preferences == null) {
			portletPreferencesImpl.setPreferences("");
		}
		else {
			portletPreferencesImpl.setPreferences(preferences);
		}

		portletPreferencesImpl.resetOriginalValues();

		return portletPreferencesImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		portletPreferencesId = objectInput.readLong();

		companyId = objectInput.readLong();

		ownerId = objectInput.readLong();

		ownerType = objectInput.readInt();

		plid = objectInput.readLong();
		portletId = objectInput.readUTF();
		preferences = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(portletPreferencesId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(ownerId);

		objectOutput.writeInt(ownerType);

		objectOutput.writeLong(plid);

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		if (preferences == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(preferences);
		}
	}

	public long mvccVersion;
	public long portletPreferencesId;
	public long companyId;
	public long ownerId;
	public int ownerType;
	public long plid;
	public String portletId;
	public String preferences;

}