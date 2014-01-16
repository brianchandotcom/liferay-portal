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
import com.liferay.portal.model.PortletPreferences;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PortletPreferences in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferences
 * @generated
 */
public class PortletPreferencesCacheModel implements CacheModel<PortletPreferences>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{portletPreferencesId=");
		sb.append(portletPreferencesId);
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
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PortletPreferences toEntityModel() {
		PortletPreferencesImpl portletPreferencesImpl = new PortletPreferencesImpl();

		portletPreferencesImpl.setPortletPreferencesId(portletPreferencesId);
		portletPreferencesImpl.setOwnerId(ownerId);
		portletPreferencesImpl.setOwnerType(ownerType);
		portletPreferencesImpl.setPlid(plid);

		if (portletId == null) {
			portletPreferencesImpl.setPortletId(StringPool.BLANK);
		}
		else {
			portletPreferencesImpl.setPortletId(portletId);
		}

		if (preferences == null) {
			portletPreferencesImpl.setPreferences(StringPool.BLANK);
		}
		else {
			portletPreferencesImpl.setPreferences(preferences);
		}

		portletPreferencesImpl.setMvccVersion(mvccVersion);

		portletPreferencesImpl.resetOriginalValues();

		return portletPreferencesImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		portletPreferencesId = objectInput.readLong();
		ownerId = objectInput.readLong();
		ownerType = objectInput.readInt();
		plid = objectInput.readLong();
		portletId = objectInput.readUTF();
		preferences = objectInput.readUTF();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(portletPreferencesId);
		objectOutput.writeLong(ownerId);
		objectOutput.writeInt(ownerType);
		objectOutput.writeLong(plid);

		if (portletId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		if (preferences == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(preferences);
		}

		objectOutput.writeLong(mvccVersion);
	}

	public long portletPreferencesId;
	public long ownerId;
	public int ownerType;
	public long plid;
	public String portletId;
	public String preferences;
	public long mvccVersion;
}