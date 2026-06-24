/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.ReassociateEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ReassociateEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ReassociateEntryCacheModel
	implements CacheModel<ReassociateEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ReassociateEntryCacheModel)) {
			return false;
		}

		ReassociateEntryCacheModel reassociateEntryCacheModel =
			(ReassociateEntryCacheModel)object;

		if (reassociateEntryId ==
				reassociateEntryCacheModel.reassociateEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, reassociateEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{reassociateEntryId=");
		sb.append(reassociateEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ReassociateEntry toEntityModel() {
		ReassociateEntryImpl reassociateEntryImpl = new ReassociateEntryImpl();

		reassociateEntryImpl.setReassociateEntryId(reassociateEntryId);

		if (name == null) {
			reassociateEntryImpl.setName("");
		}
		else {
			reassociateEntryImpl.setName(name);
		}

		reassociateEntryImpl.resetOriginalValues();

		return reassociateEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		reassociateEntryId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(reassociateEntryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long reassociateEntryId;
	public String name;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1824368709