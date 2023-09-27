/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PasswordPolicyRelCacheModel)) {
			return false;
		}

		PasswordPolicyRelCacheModel passwordPolicyRelCacheModel =
			(PasswordPolicyRelCacheModel)object;

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