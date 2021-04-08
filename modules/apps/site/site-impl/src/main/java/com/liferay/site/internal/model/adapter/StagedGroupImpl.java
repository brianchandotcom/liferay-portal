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

package com.liferay.site.internal.model.adapter;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupWrapper;
import com.liferay.site.model.adapter.StagedGroup;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

/**
 * @author Daniel Kocsis
 */
public class StagedGroupImpl extends GroupWrapper implements StagedGroup {

	public StagedGroupImpl(Group group) {
		super(
			Objects.requireNonNull(
				group, "Unable to create a new staged group for a null group"));
	}

	@Override
	public Date getCreateDate() {
		return new Date();
	}

	@Override
	public Group getGroup() {
		return getWrappedModel();
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

	@Override
	protected GroupWrapper wrap(Group group) {
		return new StagedGroupImpl(group);
	}

}