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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ClassName;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ClassName in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ClassName
 * @generated
 */
public class ClassNameCacheModel implements CacheModel<ClassName>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{classNameId=");
		sb.append(classNameId);
		sb.append(", value=");
		sb.append(value);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ClassName toEntityModel() {
		ClassNameImpl classNameImpl = new ClassNameImpl();

		classNameImpl.setClassNameId(classNameId);

		if (value == null) {
			classNameImpl.setValue(StringPool.BLANK);
		}
		else {
			classNameImpl.setValue(value);
		}

		classNameImpl.setMvccVersion(mvccVersion);

		classNameImpl.resetOriginalValues();

		return classNameImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		classNameId = objectInput.readLong();
		value = objectInput.readUTF();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(classNameId);

		if (value == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(value);
		}

		objectOutput.writeLong(mvccVersion);
	}

	public long classNameId;
	public String value;
	public long mvccVersion;
}