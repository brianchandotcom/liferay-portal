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
import com.liferay.portal.kernel.model.PortalPreferenceValue;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PortalPreferenceValue in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortalPreferenceValueCacheModel
	implements CacheModel<PortalPreferenceValue>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortalPreferenceValueCacheModel)) {
			return false;
		}

		PortalPreferenceValueCacheModel portalPreferenceValueCacheModel =
			(PortalPreferenceValueCacheModel)object;

		if ((portalPreferenceValueId ==
				portalPreferenceValueCacheModel.portalPreferenceValueId) &&
			(mvccVersion == portalPreferenceValueCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, portalPreferenceValueId);

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
		sb.append(", portalPreferenceValueId=");
		sb.append(portalPreferenceValueId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", portalPreferencesId=");
		sb.append(portalPreferencesId);
		sb.append(", index=");
		sb.append(index);
		sb.append(", key=");
		sb.append(key);
		sb.append(", largeValue=");
		sb.append(largeValue);
		sb.append(", namespace=");
		sb.append(namespace);
		sb.append(", smallValue=");
		sb.append(smallValue);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PortalPreferenceValue toEntityModel() {
		PortalPreferenceValueImpl portalPreferenceValueImpl =
			new PortalPreferenceValueImpl();

		portalPreferenceValueImpl.setMvccVersion(mvccVersion);
		portalPreferenceValueImpl.setPortalPreferenceValueId(
			portalPreferenceValueId);
		portalPreferenceValueImpl.setCompanyId(companyId);
		portalPreferenceValueImpl.setPortalPreferencesId(portalPreferencesId);
		portalPreferenceValueImpl.setIndex(index);

		if (key == null) {
			portalPreferenceValueImpl.setKey("");
		}
		else {
			portalPreferenceValueImpl.setKey(key);
		}

		if (largeValue == null) {
			portalPreferenceValueImpl.setLargeValue("");
		}
		else {
			portalPreferenceValueImpl.setLargeValue(largeValue);
		}

		if (namespace == null) {
			portalPreferenceValueImpl.setNamespace("");
		}
		else {
			portalPreferenceValueImpl.setNamespace(namespace);
		}

		if (smallValue == null) {
			portalPreferenceValueImpl.setSmallValue("");
		}
		else {
			portalPreferenceValueImpl.setSmallValue(smallValue);
		}

		portalPreferenceValueImpl.resetOriginalValues();

		return portalPreferenceValueImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		portalPreferenceValueId = objectInput.readLong();

		companyId = objectInput.readLong();

		portalPreferencesId = objectInput.readLong();

		index = objectInput.readInt();
		key = objectInput.readUTF();
		largeValue = (String)objectInput.readObject();
		namespace = objectInput.readUTF();
		smallValue = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(portalPreferenceValueId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(portalPreferencesId);

		objectOutput.writeInt(index);

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		if (largeValue == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(largeValue);
		}

		if (namespace == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(namespace);
		}

		if (smallValue == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(smallValue);
		}
	}

	public long mvccVersion;
	public long portalPreferenceValueId;
	public long companyId;
	public long portalPreferencesId;
	public int index;
	public String key;
	public String largeValue;
	public String namespace;
	public String smallValue;

}