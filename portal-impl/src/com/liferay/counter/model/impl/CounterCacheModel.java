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

package com.liferay.counter.model.impl;

import com.liferay.counter.kernel.model.Counter;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Counter in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CounterCacheModel implements CacheModel<Counter>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CounterCacheModel)) {
			return false;
		}

		CounterCacheModel counterCacheModel = (CounterCacheModel)obj;

		if (name.equals(counterCacheModel.name)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, name);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{name=");
		sb.append(name);
		sb.append(", currentId=");
		sb.append(currentId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Counter toEntityModel() {
		CounterImpl counterImpl = new CounterImpl();

		if (name == null) {
			counterImpl.setName("");
		}
		else {
			counterImpl.setName(name);
		}

		counterImpl.setCurrentId(currentId);

		counterImpl.resetOriginalValues();

		return counterImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		name = objectInput.readUTF();

		currentId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(currentId);
	}

	public String name;
	public long currentId;

}