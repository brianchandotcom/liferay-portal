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
import com.liferay.portal.kernel.model.Portlet;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Portlet in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortletCacheModel
	implements CacheModel<Portlet>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortletCacheModel)) {
			return false;
		}

		PortletCacheModel portletCacheModel = (PortletCacheModel)obj;

		if ((id == portletCacheModel.id) &&
			(mvccVersion == portletCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, id);

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
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", id=");
		sb.append(id);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", roles=");
		sb.append(roles);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Portlet toEntityModel() {
		PortletImpl portletImpl = new PortletImpl();

		portletImpl.setMvccVersion(mvccVersion);
		portletImpl.setId(id);
		portletImpl.setCompanyId(companyId);

		if (portletId == null) {
			portletImpl.setPortletId("");
		}
		else {
			portletImpl.setPortletId(portletId);
		}

		if (roles == null) {
			portletImpl.setRoles("");
		}
		else {
			portletImpl.setRoles(roles);
		}

		portletImpl.setActive(active);

		portletImpl.resetOriginalValues();

		return portletImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		id = objectInput.readLong();

		companyId = objectInput.readLong();
		portletId = objectInput.readUTF();
		roles = objectInput.readUTF();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(id);

		objectOutput.writeLong(companyId);

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		if (roles == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(roles);
		}

		objectOutput.writeBoolean(active);
	}

	public long mvccVersion;
	public long id;
	public long companyId;
	public String portletId;
	public String roles;
	public boolean active;

}