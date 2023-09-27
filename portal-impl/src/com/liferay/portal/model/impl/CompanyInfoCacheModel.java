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
		StringBundler sb = new StringBundler(9);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", companyInfoId=");
		sb.append(companyInfoId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", key=");
		sb.append(key);
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
	}

	public long mvccVersion;
	public long companyInfoId;
	public long companyId;
	public String key;

}