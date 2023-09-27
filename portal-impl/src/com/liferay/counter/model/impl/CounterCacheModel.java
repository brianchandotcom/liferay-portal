/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CounterCacheModel)) {
			return false;
		}

		CounterCacheModel counterCacheModel = (CounterCacheModel)object;

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