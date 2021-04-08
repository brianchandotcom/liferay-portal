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

package com.liferay.expando.exportimport.internal.model.adapter;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableWrapper;
import com.liferay.expando.kernel.model.adapter.StagedExpandoTable;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;

/**
 * @author Akos Thurzo
 */
public class StagedExpandoTableImpl
	extends ExpandoTableWrapper implements StagedExpandoTable {

	public StagedExpandoTableImpl(ExpandoTable expandoTable) {
		super(expandoTable);

		_className = expandoTable.getClassName();
	}

	@Override
	public String getClassName() {
		if (!Validator.isBlank(_className)) {
			return _className;
		}

		_className = super.getClassName();

		return _className;
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(getClassName());
	}

	@Override
	public Date getCreateDate() {
		return new Date();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	@Override
	public Date getModifiedDate() {
		return new Date();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedExpandoTable.class);
	}

	@Override
	public String getUuid() {
		return getClassName() + StringPool.POUND + getName();
	}

	@Override
	public void setCreateDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModifiedDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected ExpandoTableWrapper wrap(ExpandoTable expandoTable) {
		return new StagedExpandoTableImpl(expandoTable);
	}

	private String _className;

}