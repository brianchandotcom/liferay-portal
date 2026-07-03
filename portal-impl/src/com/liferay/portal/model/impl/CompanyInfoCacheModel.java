/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.CompanyInfo;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CompanyInfo in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CompanyInfoCacheModel
	implements CacheModel<CompanyInfo>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CompanyInfoCacheModel)) {
			return false;
		}

		CompanyInfoCacheModel companyInfoCacheModel =
			(CompanyInfoCacheModel)object;

		if ((companyInfoId == companyInfoCacheModel.companyInfoId) &&
			(mvccVersion == companyInfoCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, companyInfoId);

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
		StringBundler sb = new StringBundler(35);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", companyInfoId=");
		sb.append(companyInfoId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", key=");
		sb.append(key);
		sb.append(", homeURL=");
		sb.append(homeURL);
		sb.append(", logoId=");
		sb.append(logoId);
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
		sb.append(", indexNameCurrent=");
		sb.append(indexNameCurrent);
		sb.append(", indexNameNext=");
		sb.append(indexNameNext);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CompanyInfo toEntityModel() {
		CompanyInfoImpl companyInfoImpl = new CompanyInfoImpl();

		companyInfoImpl.setMvccVersion(mvccVersion);
		companyInfoImpl.setCompanyInfoId(companyInfoId);
		companyInfoImpl.setCompanyId(companyId);

		if (key == null) {
			companyInfoImpl.setKey("");
		}
		else {
			companyInfoImpl.setKey(key);
		}

		if (homeURL == null) {
			companyInfoImpl.setHomeURL("");
		}
		else {
			companyInfoImpl.setHomeURL(homeURL);
		}

		companyInfoImpl.setLogoId(logoId);

		if (name == null) {
			companyInfoImpl.setName("");
		}
		else {
			companyInfoImpl.setName(name);
		}

		if (legalName == null) {
			companyInfoImpl.setLegalName("");
		}
		else {
			companyInfoImpl.setLegalName(legalName);
		}

		if (legalId == null) {
			companyInfoImpl.setLegalId("");
		}
		else {
			companyInfoImpl.setLegalId(legalId);
		}

		if (legalType == null) {
			companyInfoImpl.setLegalType("");
		}
		else {
			companyInfoImpl.setLegalType(legalType);
		}

		if (sicCode == null) {
			companyInfoImpl.setSicCode("");
		}
		else {
			companyInfoImpl.setSicCode(sicCode);
		}

		if (tickerSymbol == null) {
			companyInfoImpl.setTickerSymbol("");
		}
		else {
			companyInfoImpl.setTickerSymbol(tickerSymbol);
		}

		if (industry == null) {
			companyInfoImpl.setIndustry("");
		}
		else {
			companyInfoImpl.setIndustry(industry);
		}

		if (type == null) {
			companyInfoImpl.setType("");
		}
		else {
			companyInfoImpl.setType(type);
		}

		if (size == null) {
			companyInfoImpl.setSize("");
		}
		else {
			companyInfoImpl.setSize(size);
		}

		if (indexNameCurrent == null) {
			companyInfoImpl.setIndexNameCurrent("");
		}
		else {
			companyInfoImpl.setIndexNameCurrent(indexNameCurrent);
		}

		if (indexNameNext == null) {
			companyInfoImpl.setIndexNameNext("");
		}
		else {
			companyInfoImpl.setIndexNameNext(indexNameNext);
		}

		companyInfoImpl.resetOriginalValues();

		return companyInfoImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		companyInfoId = objectInput.readLong();

		companyId = objectInput.readLong();
		key = (String)objectInput.readObject();
		homeURL = objectInput.readUTF();

		logoId = objectInput.readLong();
		name = objectInput.readUTF();
		legalName = objectInput.readUTF();
		legalId = objectInput.readUTF();
		legalType = objectInput.readUTF();
		sicCode = objectInput.readUTF();
		tickerSymbol = objectInput.readUTF();
		industry = objectInput.readUTF();
		type = objectInput.readUTF();
		size = objectInput.readUTF();
		indexNameCurrent = objectInput.readUTF();
		indexNameNext = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(companyInfoId);

		objectOutput.writeLong(companyId);

		if (key == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(key);
		}

		if (homeURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(homeURL);
		}

		objectOutput.writeLong(logoId);

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

		if (indexNameCurrent == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(indexNameCurrent);
		}

		if (indexNameNext == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(indexNameNext);
		}
	}

	public long mvccVersion;
	public long companyInfoId;
	public long companyId;
	public String key;
	public String homeURL;
	public long logoId;
	public String name;
	public String legalName;
	public String legalId;
	public String legalType;
	public String sicCode;
	public String tickerSymbol;
	public String industry;
	public String type;
	public String size;
	public String indexNameCurrent;
	public String indexNameNext;

}
// LIFERAY-SERVICE-BUILDER-HASH:781592771