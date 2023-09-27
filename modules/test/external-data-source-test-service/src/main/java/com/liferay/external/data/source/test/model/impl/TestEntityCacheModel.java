/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.data.source.test.model.impl;

import com.liferay.external.data.source.test.model.TestEntity;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing TestEntity in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TestEntityCacheModel
	implements CacheModel<TestEntity>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestEntityCacheModel)) {
			return false;
		}

		TestEntityCacheModel testEntityCacheModel =
			(TestEntityCacheModel)object;

		if (id == testEntityCacheModel.id) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, id);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{id=");
		sb.append(id);
		sb.append(", data=");
		sb.append(data);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public TestEntity toEntityModel() {
		TestEntityImpl testEntityImpl = new TestEntityImpl();

		testEntityImpl.setId(id);

		if (data == null) {
			testEntityImpl.setData("");
		}
		else {
			testEntityImpl.setData(data);
		}

		testEntityImpl.resetOriginalValues();

		return testEntityImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		id = objectInput.readLong();
		data = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(id);

		if (data == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(data);
		}
	}

	public long id;
	public String data;

}