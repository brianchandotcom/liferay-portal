/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortletPreferencesCacheModel)) {
			return false;
		}

		PortletPreferencesCacheModel portletPreferencesCacheModel =
			(PortletPreferencesCacheModel)object;

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PortletPreferences toEntityModel() {
		PortletPreferencesImpl portletPreferencesImpl =
			new PortletPreferencesImpl();

		portletPreferencesImpl.setMvccVersion(mvccVersion);
		portletPreferencesImpl.setCtCollectionId(ctCollectionId);
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

		portletPreferencesImpl.resetOriginalValues();

		return portletPreferencesImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		portletPreferencesId = objectInput.readLong();

		companyId = objectInput.readLong();

		ownerId = objectInput.readLong();

		ownerType = objectInput.readInt();

		plid = objectInput.readLong();
		portletId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

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
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long portletPreferencesId;
	public long companyId;
	public long ownerId;
	public int ownerType;
	public long plid;
	public String portletId;

}