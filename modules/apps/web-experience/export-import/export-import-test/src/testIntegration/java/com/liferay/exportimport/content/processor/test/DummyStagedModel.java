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

package com.liferay.exportimport.content.processor.test;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.StagedModel;

import java.io.Serializable;

import java.util.Date;

/**
 * @author Michael Bowerman
 */
public class DummyStagedModel implements StagedModel {

	@Override
	public Object clone() {
		return null;
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
		return null;
	}

	@Override
	public Class<?> getModelClass() {
		return null;
	}

	@Override
	public String getModelClassName() {
		return DummyStagedModel.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return null;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return null;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(DummyStagedModel.class);
	}

	@Override
	public String getUuid() {
		return null;
	}

	@Override
	public void setCompanyId(long companyId) {
	}

	@Override
	public void setCreateDate(Date date) {
	}

	@Override
	public void setModifiedDate(Date date) {
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
	}

	@Override
	public void setUuid(String uuid) {
	}

}