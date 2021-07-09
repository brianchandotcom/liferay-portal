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

import java.util.Date;

/**
 * The cache model class for representing Company in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CompanyCacheModel
	implements CacheModel<Company>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CompanyCacheModel)) {
			return false;
		}

		CompanyCacheModel companyCacheModel = (CompanyCacheModel)object;

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
		StringBundler sb = new StringBundler(45);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", webId=");
		sb.append(webId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", legalName=");
		sb.append(legalName);
		sb.append(", legalId=");
		sb.append(legalId);
		sb.append(", legalType=");
		sb.append(legalType);
		sb.append(", sicCode=");
		sb.append(sicCode);
		sb.append(", tickerSymbol=");
		sb.append(tickerSymbol);
		sb.append(", industry=");
		sb.append(industry);
		sb.append(", type=");
		sb.append(type);
		sb.append(", size=");
		sb.append(size);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Company toEntityModel() {
		CompanyImpl companyImpl = new CompanyImpl();

		companyImpl.setMvccVersion(mvccVersion);
		companyImpl.setCompanyId(companyId);
		companyImpl.setUserId(userId);

		if (userName == null) {
			companyImpl.setUserName("");
		}
		else {
			companyImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			companyImpl.setCreateDate(null);
		}
		else {
			companyImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			companyImpl.setModifiedDate(null);
		}
		else {
			companyImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (webId == null) {
			companyImpl.setWebId("");
		}
		else {
			companyImpl.setWebId(webId);
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

		if (name == null) {
			companyImpl.setName("");
		}
		else {
			companyImpl.setName(name);
		}

		if (legalName == null) {
			companyImpl.setLegalName("");
		}
		else {
			companyImpl.setLegalName(legalName);
		}

		if (legalId == null) {
			companyImpl.setLegalId("");
		}
		else {
			companyImpl.setLegalId(legalId);
		}

		if (legalType == null) {
			companyImpl.setLegalType("");
		}
		else {
			companyImpl.setLegalType(legalType);
		}

		if (sicCode == null) {
			companyImpl.setSicCode("");
		}
		else {
			companyImpl.setSicCode(sicCode);
		}

		if (tickerSymbol == null) {
			companyImpl.setTickerSymbol("");
		}
		else {
			companyImpl.setTickerSymbol(tickerSymbol);
		}

		if (industry == null) {
			companyImpl.setIndustry("");
		}
		else {
			companyImpl.setIndustry(industry);
		}

		if (type == null) {
			companyImpl.setType("");
		}
		else {
			companyImpl.setType(type);
		}

		if (size == null) {
			companyImpl.setSize("");
		}
		else {
			companyImpl.setSize(size);
		}

		companyImpl.resetOriginalValues();

		companyImpl.setCompanySecurityBag(_companySecurityBag);

		companyImpl.setVirtualHostname(_virtualHostname);

		return companyImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		webId = objectInput.readUTF();
		mx = objectInput.readUTF();
		homeURL = objectInput.readUTF();

		logoId = objectInput.readLong();

		system = objectInput.readBoolean();

		maxUsers = objectInput.readInt();

		active = objectInput.readBoolean();
		name = objectInput.readUTF();
		legalName = objectInput.readUTF();
		legalId = objectInput.readUTF();
		legalType = objectInput.readUTF();
		sicCode = objectInput.readUTF();
		tickerSymbol = objectInput.readUTF();
		industry = objectInput.readUTF();
		type = objectInput.readUTF();
		size = objectInput.readUTF();

		_companySecurityBag =
			(CompanyImpl.CompanySecurityBag)objectInput.readObject();
		_virtualHostname = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (webId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(webId);
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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (legalName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(legalName);
		}

		if (legalId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(legalId);
		}

		if (legalType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(legalType);
		}

		if (sicCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sicCode);
		}

		if (tickerSymbol == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(tickerSymbol);
		}

		if (industry == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(industry);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (size == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(size);
		}

		objectOutput.writeObject(_companySecurityBag);
		objectOutput.writeObject(_virtualHostname);
	}

	public long mvccVersion;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String webId;
	public String mx;
	public String homeURL;
	public long logoId;
	public boolean system;
	public int maxUsers;
	public boolean active;
	public String name;
	public String legalName;
	public String legalId;
	public String legalType;
	public String sicCode;
	public String tickerSymbol;
	public String industry;
	public String type;
	public String size;
	public CompanyImpl.CompanySecurityBag _companySecurityBag;
	public String _virtualHostname;

}