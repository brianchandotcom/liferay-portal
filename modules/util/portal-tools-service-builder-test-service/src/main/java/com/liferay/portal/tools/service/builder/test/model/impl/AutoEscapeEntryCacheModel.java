/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.AutoEscapeEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AutoEscapeEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AutoEscapeEntryCacheModel
	implements CacheModel<AutoEscapeEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AutoEscapeEntryCacheModel)) {
			return false;
		}

		AutoEscapeEntryCacheModel autoEscapeEntryCacheModel =
			(AutoEscapeEntryCacheModel)object;

		if (autoEscapeEntryId == autoEscapeEntryCacheModel.autoEscapeEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, autoEscapeEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{autoEscapeEntryId=");
		sb.append(autoEscapeEntryId);
		sb.append(", autoEscapeEnabledColumn=");
		sb.append(autoEscapeEnabledColumn);
		sb.append(", autoEscapeDisabledColumn=");
		sb.append(autoEscapeDisabledColumn);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AutoEscapeEntry toEntityModel() {
		AutoEscapeEntryImpl autoEscapeEntryImpl = new AutoEscapeEntryImpl();

		autoEscapeEntryImpl.setAutoEscapeEntryId(autoEscapeEntryId);

		if (autoEscapeEnabledColumn == null) {
			autoEscapeEntryImpl.setAutoEscapeEnabledColumn("");
		}
		else {
			autoEscapeEntryImpl.setAutoEscapeEnabledColumn(
				autoEscapeEnabledColumn);
		}

		if (autoEscapeDisabledColumn == null) {
			autoEscapeEntryImpl.setAutoEscapeDisabledColumn("");
		}
		else {
			autoEscapeEntryImpl.setAutoEscapeDisabledColumn(
				autoEscapeDisabledColumn);
		}

		autoEscapeEntryImpl.resetOriginalValues();

		return autoEscapeEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		autoEscapeEntryId = objectInput.readLong();
		autoEscapeEnabledColumn = objectInput.readUTF();
		autoEscapeDisabledColumn = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(autoEscapeEntryId);

		if (autoEscapeEnabledColumn == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(autoEscapeEnabledColumn);
		}

		if (autoEscapeDisabledColumn == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(autoEscapeDisabledColumn);
		}
	}

	public long autoEscapeEntryId;
	public String autoEscapeEnabledColumn;
	public String autoEscapeDisabledColumn;

}