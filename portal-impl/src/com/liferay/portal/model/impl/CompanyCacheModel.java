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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Company in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CompanyCacheModel
	implements CacheModel<Company>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CompanyCacheModel)) {
			return false;
		}

		CompanyCacheModel companyCacheModel = (CompanyCacheModel)obj;

		if ((companyId == companyCacheModel.companyId) &&
			(mvccVersion == companyCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, companyId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", accountId=");
		sb.append(accountId);
		sb.append(", webId=");
		sb.append(webId);
		sb.append(", key=");
		sb.append(key);
		sb.append(", mx=");
		sb.append(mx);
		sb.append(", homeURL=");
		sb.append(homeURL);
		sb.append(", logoId=");
		sb.append(logoId);
		sb.append(", system=");
		sb.append(system);
		sb.append(", maxUsers=");
		sb.append(maxUsers);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Company toEntityModel() {
		CompanyImpl companyImpl = new CompanyImpl();

		companyImpl.setMvccVersion(mvccVersion);
		companyImpl.setCompanyId(companyId);
		companyImpl.setAccountId(accountId);

		if (webId == null) {
			companyImpl.setWebId("");
		}
		else {
			companyImpl.setWebId(webId);
		}

		if (key == null) {
			companyImpl.setKey("");
		}
		else {
			companyImpl.setKey(key);
		}

		if (mx == null) {
			companyImpl.setMx("");
		}
		else {
			companyImpl.setMx(mx);
		}

		if (homeURL == null) {
			companyImpl.setHomeURL("");
		}
		else {
			companyImpl.setHomeURL(homeURL);
		}

		companyImpl.setLogoId(logoId);
		companyImpl.setSystem(system);
		companyImpl.setMaxUsers(maxUsers);
		companyImpl.setActive(active);

		companyImpl.resetOriginalValues();

		companyImpl.setCompanySecurityBag(_companySecurityBag);

		companyImpl.setKeyObj(_keyObj);

		companyImpl.setVirtualHostname(_virtualHostname);

		return companyImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		companyId = objectInput.readLong();

		accountId = objectInput.readLong();
		webId = objectInput.readUTF();
		key = objectInput.readUTF();
		mx = objectInput.readUTF();
		homeURL = objectInput.readUTF();

		logoId = objectInput.readLong();

		system = objectInput.readBoolean();

		maxUsers = objectInput.readInt();

		active = objectInput.readBoolean();

		_companySecurityBag =
			(CompanyImpl.CompanySecurityBag)objectInput.readObject();
		_keyObj = (java.security.Key)objectInput.readObject();
		_virtualHostname = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(accountId);

		if (webId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(webId);
		}

		if (key == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(key);
		}

		if (mx == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(mx);
		}

		if (homeURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(homeURL);
		}

		objectOutput.writeLong(logoId);

		objectOutput.writeBoolean(system);

		objectOutput.writeInt(maxUsers);

		objectOutput.writeBoolean(active);

		objectOutput.writeObject(_companySecurityBag);
		objectOutput.writeObject(_keyObj);
		objectOutput.writeObject(_virtualHostname);
	}

	public long mvccVersion;
	public long companyId;
	public long accountId;
	public String webId;
	public String key;
	public String mx;
	public String homeURL;
	public long logoId;
	public boolean system;
	public int maxUsers;
	public boolean active;
	public CompanyImpl.CompanySecurityBag _companySecurityBag;
	public java.security.Key _keyObj;
	public String _virtualHostname;

}