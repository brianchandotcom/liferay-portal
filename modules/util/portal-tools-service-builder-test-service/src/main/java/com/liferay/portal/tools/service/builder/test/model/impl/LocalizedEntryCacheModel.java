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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LocalizedEntryCacheModel)) {
			return false;
		}

		LocalizedEntryCacheModel localizedEntryCacheModel =
			(LocalizedEntryCacheModel)obj;

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