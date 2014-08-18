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
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{tableId=");
		sb.append(tableId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ExpandoTable toEntityModel() {
		ExpandoTableImpl expandoTableImpl = new ExpandoTableImpl();

		expandoTableImpl.setTableId(tableId);
		expandoTableImpl.setCompanyId(companyId);
		expandoTableImpl.setClassNameId(classNameId);

		if (name == null) {
			expandoTableImpl.setName(StringPool.BLANK);
		}
		else {
			expandoTableImpl.setName(name);
		}

		expandoTableImpl.resetOriginalValues();

		return expandoTableImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		tableId = objectInput.readLong();
		companyId = objectInput.readLong();
		classNameId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(tableId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(classNameId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long tableId;
	public long companyId;
	public long classNameId;
	public String name;
}