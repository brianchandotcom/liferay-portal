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

package com.liferay.portlet.expando.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.expando.model.ExpandoTable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ExpandoTable in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTable
 * @generated
 */
@ProviderType
public class ExpandoTableCacheModel implements CacheModel<ExpandoTable>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ExpandoTableCacheModel)) {
			return false;
		}

		ExpandoTableCacheModel expandoTableCacheModel = (ExpandoTableCacheModel)obj;

		if (tableId == expandoTableCacheModel.tableId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, tableId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{tableId=");
		sb.append(tableId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ExpandoTable toEntityModel() {
		ExpandoTableImpl expandoTableImpl = new ExpandoTableImpl();

		expandoTableImpl.setTableId(tableId);
		expandoTableImpl.setClassNameId(classNameId);

		if (name == null) {
			expandoTableImpl.setName(StringPool.BLANK);
		}
		else {
			expandoTableImpl.setName(name);
		}

		expandoTableImpl.setCompanyId(companyId);

		expandoTableImpl.resetOriginalValues();

		return expandoTableImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		tableId = objectInput.readLong();
		classNameId = objectInput.readLong();
		name = objectInput.readUTF();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(tableId);
		objectOutput.writeLong(classNameId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(companyId);
	}

	public long tableId;
	public long classNameId;
	public String name;
	public long companyId;
}