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

package com.liferay.user.associated.data.entity;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringPool;

import java.io.Serializable;

import java.util.Date;

/**
 * @author William Newbury
 */
public abstract class BaseUADEntity implements UADEntity {

	public BaseUADEntity(
		long userId, String uadEntityId, String uadRegistryKey) {

		_userId = userId;

		_uadEntityId = uadEntityId;
		_uadRegistryKey = uadRegistryKey;
	}

	@Override
	public Object clone() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getCompanyId() {
		return 0;
	}

	@Override
	public Date getCreateDate() {
		return null;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<?> getModelClass() {
		return getClass();
	}

	@Override
	public String getModelClassName() {
		return getClass().getName();
	}

	@Override
	public Date getModifiedDate() {
		return null;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _uadEntityId;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(getClass());
	}

	@Override
	public String getUADEntityId() {
		return _uadEntityId;
	}

	@Override
	public String getUADRegistryKey() {
		return _uadRegistryKey;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public String getUuid() {
		return _uadRegistryKey + StringPool.POUND + _uadEntityId;
	}

	@Override
	public void setCompanyId(long companyId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCreateDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModifiedDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	private final String _uadEntityId;
	private final String _uadRegistryKey;
	private final long _userId;

}