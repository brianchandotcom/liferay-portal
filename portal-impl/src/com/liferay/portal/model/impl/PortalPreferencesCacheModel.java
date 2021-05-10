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
import com.liferay.portal.kernel.model.PortalPreferences;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PortalPreferences in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortalPreferencesCacheModel
	implements CacheModel<PortalPreferences>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortalPreferencesCacheModel)) {
			return false;
		}

		PortalPreferencesCacheModel portalPreferencesCacheModel =
			(PortalPreferencesCacheModel)object;

		if ((portalPreferencesId ==
				portalPreferencesCacheModel.portalPreferencesId) &&
			(mvccVersion == portalPreferencesCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, portalPreferencesId);

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
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", portalPreferencesId=");
		sb.append(portalPreferencesId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", ownerId=");
		sb.append(ownerId);
		sb.append(", ownerType=");
		sb.append(ownerType);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PortalPreferences toEntityModel() {
		PortalPreferencesImpl portalPreferencesImpl =
			new PortalPreferencesImpl();

		portalPreferencesImpl.setMvccVersion(mvccVersion);
		portalPreferencesImpl.setPortalPreferencesId(portalPreferencesId);
		portalPreferencesImpl.setCompanyId(companyId);
		portalPreferencesImpl.setOwnerId(ownerId);
		portalPreferencesImpl.setOwnerType(ownerType);

		portalPreferencesImpl.resetOriginalValues();

		return portalPreferencesImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		portalPreferencesId = objectInput.readLong();

		companyId = objectInput.readLong();

		ownerId = objectInput.readLong();

		ownerType = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(portalPreferencesId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(ownerId);

		objectOutput.writeInt(ownerType);
	}

	public long mvccVersion;
	public long portalPreferencesId;
	public long companyId;
	public long ownerId;
	public int ownerType;

}