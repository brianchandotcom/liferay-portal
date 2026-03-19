/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.DefinedDefaultOrderEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DefinedDefaultOrderEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DefinedDefaultOrderEntryCacheModel
	implements CacheModel<DefinedDefaultOrderEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DefinedDefaultOrderEntryCacheModel)) {
			return false;
		}

		DefinedDefaultOrderEntryCacheModel definedDefaultOrderEntryCacheModel =
			(DefinedDefaultOrderEntryCacheModel)object;

		if (definedDefaultOrderEntryId ==
				definedDefaultOrderEntryCacheModel.definedDefaultOrderEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, definedDefaultOrderEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{definedDefaultOrderEntryId=");
		sb.append(definedDefaultOrderEntryId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DefinedDefaultOrderEntry toEntityModel() {
		DefinedDefaultOrderEntryImpl definedDefaultOrderEntryImpl =
			new DefinedDefaultOrderEntryImpl();

		definedDefaultOrderEntryImpl.setDefinedDefaultOrderEntryId(
			definedDefaultOrderEntryId);

		if (modifiedDate == Long.MIN_VALUE) {
			definedDefaultOrderEntryImpl.setModifiedDate(null);
		}
		else {
			definedDefaultOrderEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (name == null) {
			definedDefaultOrderEntryImpl.setName("");
		}
		else {
			definedDefaultOrderEntryImpl.setName(name);
		}

		definedDefaultOrderEntryImpl.resetOriginalValues();

		return definedDefaultOrderEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		definedDefaultOrderEntryId = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(definedDefaultOrderEntryId);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long definedDefaultOrderEntryId;
	public long modifiedDate;
	public String name;

}
// LIFERAY-SERVICE-BUILDER-HASH:1241076284