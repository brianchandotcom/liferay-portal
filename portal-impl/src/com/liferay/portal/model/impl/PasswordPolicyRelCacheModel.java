/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.PasswordPolicyRel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PasswordPolicyRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyRel
 * @generated
 */
public class PasswordPolicyRelCacheModel implements CacheModel<PasswordPolicyRel>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{passwordPolicyRelId=");
		sb.append(passwordPolicyRelId);
		sb.append(", passwordPolicyId=");
		sb.append(passwordPolicyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PasswordPolicyRel toEntityModel() {
		PasswordPolicyRelImpl passwordPolicyRelImpl = new PasswordPolicyRelImpl();

		passwordPolicyRelImpl.setPasswordPolicyRelId(passwordPolicyRelId);
		passwordPolicyRelImpl.setPasswordPolicyId(passwordPolicyId);
		passwordPolicyRelImpl.setClassNameId(classNameId);
		passwordPolicyRelImpl.setClassPK(classPK);
		passwordPolicyRelImpl.setMvccVersion(mvccVersion);

		passwordPolicyRelImpl.resetOriginalValues();

		return passwordPolicyRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		passwordPolicyRelId = objectInput.readLong();
		passwordPolicyId = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(passwordPolicyRelId);
		objectOutput.writeLong(passwordPolicyId);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);
		objectOutput.writeLong(mvccVersion);
	}

	public long passwordPolicyRelId;
	public long passwordPolicyId;
	public long classNameId;
	public long classPK;
	public long mvccVersion;
}