/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.KeywordsEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing KeywordsEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KeywordsEntryCacheModel
	implements CacheModel<KeywordsEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KeywordsEntryCacheModel)) {
			return false;
		}

		KeywordsEntryCacheModel keywordsEntryCacheModel =
			(KeywordsEntryCacheModel)object;

		if (keywordsEntryId == keywordsEntryCacheModel.keywordsEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, keywordsEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{keywordsEntryId=");
		sb.append(keywordsEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KeywordsEntry toEntityModel() {
		KeywordsEntryImpl keywordsEntryImpl = new KeywordsEntryImpl();

		keywordsEntryImpl.setKeywordsEntryId(keywordsEntryId);
		keywordsEntryImpl.setCompanyId(companyId);

		if (name == null) {
			keywordsEntryImpl.setName("");
		}
		else {
			keywordsEntryImpl.setName(name);
		}

		keywordsEntryImpl.resetOriginalValues();

		return keywordsEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		keywordsEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(keywordsEntryId);

		objectOutput.writeLong(companyId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long keywordsEntryId;
	public long companyId;
	public String name;

}