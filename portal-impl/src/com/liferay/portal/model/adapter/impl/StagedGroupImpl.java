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

package com.liferay.portal.model.adapter.impl;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.adapter.StagedGroup;
import com.liferay.portal.model.impl.GroupImpl;

import java.io.Serializable;

import java.util.Date;

/**
 * @author     Daniel Kocsis
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
public class StagedGroupImpl extends GroupImpl implements StagedGroup {

	public StagedGroupImpl(Group group) {
		_group = group;
	}

	@Override
	public Object clone() {
		return new StagedGroupImpl(_group);
	}

	@Override
	public Date getCreateDate() {
		return new Date();
	}

	@Override
	public Class<?> getModelClass() {
		return StagedGroup.class;
	}

	@Override
	public String getModelClassName() {
		return StagedGroup.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return new Date();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedGroup.class);
	}

	@Override
	public void setCompanyId(long companyId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCreateDate(Date createDate) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
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

	private final Group _group;

}