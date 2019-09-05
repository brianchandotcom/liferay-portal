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
import com.liferay.portal.kernel.model.PasswordPolicyRel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PasswordPolicyRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PasswordPolicyRelCacheModel
	implements CacheModel<PasswordPolicyRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PasswordPolicyRelCacheModel)) {
			return false;
		}

		PasswordPolicyRelCacheModel passwordPolicyRelCacheModel =
			(PasswordPolicyRelCacheModel)obj;

		if ((passwordPolicyRelId ==
				passwordPolicyRelCacheModel.passwordPolicyRelId) &&
			(mvccVersion == passwordPolicyRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, passwordPolicyRelId);

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
		sb.append(", passwordPolicyRelId=");
		sb.append(passwordPolicyRelId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", passwordPolicyId=");
		sb.append(passwordPolicyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PasswordPolicyRel toEntityModel() {
		PasswordPolicyRelImpl passwordPolicyRelImpl =
			new PasswordPolicyRelImpl();

		passwordPolicyRelImpl.setMvccVersion(mvccVersion);
		passwordPolicyRelImpl.setPasswordPolicyRelId(passwordPolicyRelId);
		passwordPolicyRelImpl.setCompanyId(companyId);
		passwordPolicyRelImpl.setPasswordPolicyId(passwordPolicyId);
		passwordPolicyRelImpl.setClassNameId(classNameId);
		passwordPolicyRelImpl.setClassPK(classPK);

		passwordPolicyRelImpl.resetOriginalValues();

		return passwordPolicyRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		passwordPolicyRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		passwordPolicyId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(passwordPolicyRelId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(passwordPolicyId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);
	}

	public long mvccVersion;
	public long passwordPolicyRelId;
	public long companyId;
	public long passwordPolicyId;
	public long classNameId;
	public long classPK;

}