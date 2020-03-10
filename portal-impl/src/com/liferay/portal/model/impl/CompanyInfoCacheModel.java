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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CompanyInfoCacheModel)) {
			return false;
		}

		CompanyInfoCacheModel companyInfoCacheModel =
			(CompanyInfoCacheModel)obj;

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