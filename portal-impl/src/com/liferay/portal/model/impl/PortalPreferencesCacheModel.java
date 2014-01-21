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
import com.liferay.portal.model.PortalPreferences;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PortalPreferences in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PortalPreferences
 * @generated
 */
public class PortalPreferencesCacheModel implements CacheModel<PortalPreferences>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{portalPreferencesId=");
		sb.append(portalPreferencesId);
		sb.append(", ownerId=");
		sb.append(ownerId);
		sb.append(", ownerType=");
		sb.append(ownerType);
		sb.append(", preferences=");
		sb.append(preferences);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PortalPreferences toEntityModel() {
		PortalPreferencesImpl portalPreferencesImpl = new PortalPreferencesImpl();

		portalPreferencesImpl.setPortalPreferencesId(portalPreferencesId);
		portalPreferencesImpl.setOwnerId(ownerId);
		portalPreferencesImpl.setOwnerType(ownerType);

		if (preferences == null) {
			portalPreferencesImpl.setPreferences(StringPool.BLANK);
		}
		else {
			portalPreferencesImpl.setPreferences(preferences);
		}

		portalPreferencesImpl.setMvccVersion(mvccVersion);

		portalPreferencesImpl.resetOriginalValues();

		return portalPreferencesImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		portalPreferencesId = objectInput.readLong();
		ownerId = objectInput.readLong();
		ownerType = objectInput.readInt();
		preferences = objectInput.readUTF();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(portalPreferencesId);
		objectOutput.writeLong(ownerId);
		objectOutput.writeInt(ownerType);

		if (preferences == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(preferences);
		}

		objectOutput.writeLong(mvccVersion);
	}

	public long portalPreferencesId;
	public long ownerId;
	public int ownerType;
	public String preferences;
	public long mvccVersion;
}