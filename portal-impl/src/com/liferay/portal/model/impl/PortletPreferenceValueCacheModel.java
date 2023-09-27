/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.PortletPreferenceValue;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PortletPreferenceValue in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortletPreferenceValueCacheModel
	implements CacheModel<PortletPreferenceValue>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortletPreferenceValueCacheModel)) {
			return false;
		}

		PortletPreferenceValueCacheModel portletPreferenceValueCacheModel =
			(PortletPreferenceValueCacheModel)object;

		if ((portletPreferenceValueId ==
				portletPreferenceValueCacheModel.portletPreferenceValueId) &&
			(mvccVersion == portletPreferenceValueCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, portletPreferenceValueId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", portletPreferenceValueId=");
		sb.append(portletPreferenceValueId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", portletPreferencesId=");
		sb.append(portletPreferencesId);
		sb.append(", index=");
		sb.append(index);
		sb.append(", largeValue=");
		sb.append(largeValue);
		sb.append(", name=");
		sb.append(name);
		sb.append(", readOnly=");
		sb.append(readOnly);
		sb.append(", smallValue=");
		sb.append(smallValue);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PortletPreferenceValue toEntityModel() {
		PortletPreferenceValueImpl portletPreferenceValueImpl =
			new PortletPreferenceValueImpl();

		portletPreferenceValueImpl.setMvccVersion(mvccVersion);
		portletPreferenceValueImpl.setCtCollectionId(ctCollectionId);
		portletPreferenceValueImpl.setPortletPreferenceValueId(
			portletPreferenceValueId);
		portletPreferenceValueImpl.setCompanyId(companyId);
		portletPreferenceValueImpl.setPortletPreferencesId(
			portletPreferencesId);
		portletPreferenceValueImpl.setIndex(index);

		if (largeValue == null) {
			portletPreferenceValueImpl.setLargeValue("");
		}
		else {
			portletPreferenceValueImpl.setLargeValue(largeValue);
		}

		if (name == null) {
			portletPreferenceValueImpl.setName("");
		}
		else {
			portletPreferenceValueImpl.setName(name);
		}

		portletPreferenceValueImpl.setReadOnly(readOnly);

		if (smallValue == null) {
			portletPreferenceValueImpl.setSmallValue("");
		}
		else {
			portletPreferenceValueImpl.setSmallValue(smallValue);
		}

		portletPreferenceValueImpl.resetOriginalValues();

		return portletPreferenceValueImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		portletPreferenceValueId = objectInput.readLong();

		companyId = objectInput.readLong();

		portletPreferencesId = objectInput.readLong();

		index = objectInput.readInt();
		largeValue = (String)objectInput.readObject();
		name = objectInput.readUTF();

		readOnly = objectInput.readBoolean();
		smallValue = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(portletPreferenceValueId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(portletPreferencesId);

		objectOutput.writeInt(index);

		if (largeValue == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(largeValue);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(readOnly);

		if (smallValue == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(smallValue);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long portletPreferenceValueId;
	public long companyId;
	public long portletPreferencesId;
	public int index;
	public String largeValue;
	public String name;
	public boolean readOnly;
	public String smallValue;

}