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
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnWrapper;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.adapter.StagedExpandoColumn;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Date;

/**
 * @author Akos Thurzo
 */
public class StagedExpandoColumnImpl
	extends ExpandoColumnWrapper implements StagedExpandoColumn {

	public StagedExpandoColumnImpl(ExpandoColumn expandoColumn) {
		super(expandoColumn);

		ExpandoTable expandoTable = null;

		try {
			expandoTable = ExpandoTableLocalServiceUtil.getExpandoTable(
				getTableId());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Could not find expando table for tableId=" +
					expandoColumn.getTableId(),
				portalException);
		}

		_expandoTableClassName = expandoTable.getClassName();
		_expandoTableName = expandoTable.getName();
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
		return new StagedModelType(StagedExpandoColumn.class);
	}

	@Override
	public String getUuid() {
		return StringBundler.concat(
			_expandoTableClassName, StringPool.POUND, _expandoTableName,
			StringPool.POUND, getName());
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
	protected ExpandoColumnWrapper wrap(ExpandoColumn expandoColumn) {
		return new StagedExpandoColumnImpl(
			expandoColumn, _expandoTableClassName, _expandoTableName);
	}

	private StagedExpandoColumnImpl(
		ExpandoColumn expandoColumn, String expandoTableClassName,
		String expandoTableName) {

		super(expandoColumn);

		_expandoTableClassName = expandoTableClassName;
		_expandoTableName = expandoTableName;
	}

	private final String _expandoTableClassName;
	private final String _expandoTableName;

}