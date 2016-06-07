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

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Preston Crary
 */
public abstract class BaseModelAdaptor<T> implements BaseModel<T> {

	public abstract Object clone();

	@Override
	public int compareTo(T o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<?> getModelClass() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getModelClassName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCachedModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEscapedModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNew() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNew(boolean n) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T toEscapedModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public T toUnescapedModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toXmlString() {
		throw new UnsupportedOperationException();
	}

}