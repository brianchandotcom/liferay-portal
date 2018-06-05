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

package com.liferay.portal.tools.service.builder.test.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.tools.service.builder.test.model.LocalizedEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LocalizedEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LocalizedEntry
 * @generated
 */
@ProviderType
public class LocalizedEntryCacheModel implements CacheModel<LocalizedEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LocalizedEntryCacheModel)) {
			return false;
		}

		LocalizedEntryCacheModel localizedEntryCacheModel = (LocalizedEntryCacheModel)obj;

		if (entryId == localizedEntryCacheModel.entryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, entryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{entryId=");
		sb.append(entryId);
		sb.append(", defaultLanguageId=");
		sb.append(defaultLanguageId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LocalizedEntry toEntityModel() {
		LocalizedEntryImpl localizedEntryImpl = new LocalizedEntryImpl();

		localizedEntryImpl.setEntryId(entryId);

		if (defaultLanguageId == null) {
			localizedEntryImpl.setDefaultLanguageId("");
		}
		else {
			localizedEntryImpl.setDefaultLanguageId(defaultLanguageId);
		}

		localizedEntryImpl.resetOriginalValues();

		return localizedEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		entryId = objectInput.readLong();
		defaultLanguageId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(entryId);

		if (defaultLanguageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(defaultLanguageId);
		}
	}

	public long entryId;
	public String defaultLanguageId;
}