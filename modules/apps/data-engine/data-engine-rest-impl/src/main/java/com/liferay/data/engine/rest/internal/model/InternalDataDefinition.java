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

package com.liferay.data.engine.rest.internal.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ClassedModel;

import java.io.Serializable;

/**
 * @author Jeyvison Nascimento
 */
public class InternalDataDefinition implements ClassedModel, Serializable {

	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<?> getModelClass() {
		return InternalDataDefinition.class;
	}

	@Override
	public String getModelClassName() {
		return InternalDataDefinition.class.getName();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dataDefinitionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dataDefinitionId = (long)primaryKeyObj;
	}

	private long _dataDefinitionId;

}