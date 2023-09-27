/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.LocalizedEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LocalizedEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LocalizedEntryCacheModel
	implements CacheModel<LocalizedEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LocalizedEntryCacheModel)) {
			return false;
		}

		LocalizedEntryCacheModel localizedEntryCacheModel =
			(LocalizedEntryCacheModel)object;

		if (localizedEntryId == localizedEntryCacheModel.localizedEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, localizedEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{defaultLanguageId=");
		sb.append(defaultLanguageId);
		sb.append(", localizedEntryId=");
		sb.append(localizedEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LocalizedEntry toEntityModel() {
		LocalizedEntryImpl localizedEntryImpl = new LocalizedEntryImpl();

		if (defaultLanguageId == null) {
			localizedEntryImpl.setDefaultLanguageId("");
		}
		else {
			localizedEntryImpl.setDefaultLanguageId(defaultLanguageId);
		}

		localizedEntryImpl.setLocalizedEntryId(localizedEntryId);

		localizedEntryImpl.resetOriginalValues();

		return localizedEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		defaultLanguageId = objectInput.readUTF();

		localizedEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (defaultLanguageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(defaultLanguageId);
		}

		objectOutput.writeLong(localizedEntryId);
	}

	public String defaultLanguageId;
	public long localizedEntryId;

}