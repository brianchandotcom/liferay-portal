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
		sb.append(", name=");
		sb.append(name);
		sb.append(", index=");
		sb.append(index);
		sb.append(", smallValue=");
		sb.append(smallValue);
		sb.append(", largeValue=");
		sb.append(largeValue);
		sb.append(", readOnly=");
		sb.append(readOnly);
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

		if (name == null) {
			portletPreferenceValueImpl.setName("");
		}
		else {
			portletPreferenceValueImpl.setName(name);
		}

		portletPreferenceValueImpl.setIndex(index);

		if (smallValue == null) {
			portletPreferenceValueImpl.setSmallValue("");
		}
		else {
			portletPreferenceValueImpl.setSmallValue(smallValue);
		}

		if (largeValue == null) {
			portletPreferenceValueImpl.setLargeValue("");
		}
		else {
			portletPreferenceValueImpl.setLargeValue(largeValue);
		}

		portletPreferenceValueImpl.setReadOnly(readOnly);

		portletPreferenceValueImpl.resetOriginalValues();

		return portletPreferenceValueImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		portletPreferenceValueId = objectInput.readLong();

		companyId = objectInput.readLong();

		portletPreferencesId = objectInput.readLong();
		name = objectInput.readUTF();

		index = objectInput.readInt();
		smallValue = objectInput.readUTF();
		largeValue = objectInput.readUTF();

		readOnly = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(portletPreferenceValueId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(portletPreferencesId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(index);

		if (smallValue == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(smallValue);
		}

		if (largeValue == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(largeValue);
		}

		objectOutput.writeBoolean(readOnly);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long portletPreferenceValueId;
	public long companyId;
	public long portletPreferencesId;
	public String name;
	public int index;
	public String smallValue;
	public String largeValue;
	public boolean readOnly;

}