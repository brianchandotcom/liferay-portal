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
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ClassName in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ClassNameCacheModel
	implements CacheModel<ClassName>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ClassNameCacheModel)) {
			return false;
		}

		ClassNameCacheModel classNameCacheModel = (ClassNameCacheModel)obj;

		if ((classNameId == classNameCacheModel.classNameId) &&
			(mvccVersion == classNameCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, classNameId);

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
		StringBundler sb = new StringBundler(7);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ClassName toEntityModel() {
		ClassNameImpl classNameImpl = new ClassNameImpl();

		classNameImpl.setMvccVersion(mvccVersion);
		classNameImpl.setClassNameId(classNameId);

		if (value == null) {
			classNameImpl.setValue("");
		}
		else {
			classNameImpl.setValue(value);
		}

		classNameImpl.resetOriginalValues();

		return classNameImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		classNameId = objectInput.readLong();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(classNameId);

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}
	}

	public long mvccVersion;
	public long classNameId;
	public String value;

}