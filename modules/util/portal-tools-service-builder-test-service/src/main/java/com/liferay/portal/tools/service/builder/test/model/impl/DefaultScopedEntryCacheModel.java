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
import com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DefaultScopedEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DefaultScopedEntryCacheModel
	implements CacheModel<DefaultScopedEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DefaultScopedEntryCacheModel)) {
			return false;
		}

		DefaultScopedEntryCacheModel defaultScopedEntryCacheModel =
			(DefaultScopedEntryCacheModel)object;

		if (DefaultScopedEntryId ==
				defaultScopedEntryCacheModel.DefaultScopedEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, DefaultScopedEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", DefaultScopedEntryId=");
		sb.append(DefaultScopedEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DefaultScopedEntry toEntityModel() {
		DefaultScopedEntryImpl defaultScopedEntryImpl =
			new DefaultScopedEntryImpl();

		if (externalReferenceCode == null) {
			defaultScopedEntryImpl.setExternalReferenceCode("");
		}
		else {
			defaultScopedEntryImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		defaultScopedEntryImpl.setDefaultScopedEntryId(DefaultScopedEntryId);
		defaultScopedEntryImpl.setCompanyId(companyId);

		defaultScopedEntryImpl.resetOriginalValues();

		return defaultScopedEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		externalReferenceCode = objectInput.readUTF();

		DefaultScopedEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(DefaultScopedEntryId);

		objectOutput.writeLong(companyId);
	}

	public String externalReferenceCode;
	public long DefaultScopedEntryId;
	public long companyId;

}