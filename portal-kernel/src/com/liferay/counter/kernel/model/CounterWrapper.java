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

package com.liferay.counter.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link Counter}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Counter
 * @generated
 */
@ProviderType
public class CounterWrapper implements Counter, ModelWrapper<Counter> {
	public CounterWrapper(Counter counter) {
		_counter = counter;
	}

	@Override
	public Class<?> getModelClass() {
		return Counter.class;
	}

	@Override
	public String getModelClassName() {
		return Counter.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("name", getName());
		attributes.put("currentId", getCurrentId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long currentId = (Long)attributes.get("currentId");

		if (currentId != null) {
			setCurrentId(currentId);
		}
	}

	@Override
	public Counter toEscapedModel() {
		return new CounterWrapper(_counter.toEscapedModel());
	}

	@Override
	public Counter toUnescapedModel() {
		return new CounterWrapper(_counter.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _counter.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _counter.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _counter.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _counter.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<Counter> toCacheModel() {
		return _counter.toCacheModel();
	}

	@Override
	public int compareTo(Counter counter) {
		return _counter.compareTo(counter);
	}

	@Override
	public int hashCode() {
		return _counter.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _counter.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CounterWrapper((Counter)_counter.clone());
	}

	/**
	* Returns the name of this counter.
	*
	* @return the name of this counter
	*/
	@Override
	public java.lang.String getName() {
		return _counter.getName();
	}

	/**
	* Returns the primary key of this counter.
	*
	* @return the primary key of this counter
	*/
	@Override
	public java.lang.String getPrimaryKey() {
		return _counter.getPrimaryKey();
	}

	@Override
	public java.lang.String toString() {
		return _counter.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _counter.toXmlString();
	}

	/**
	* Returns the current ID of this counter.
	*
	* @return the current ID of this counter
	*/
	@Override
	public long getCurrentId() {
		return _counter.getCurrentId();
	}

	@Override
	public void persist() {
		_counter.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_counter.setCachedModel(cachedModel);
	}

	/**
	* Sets the current ID of this counter.
	*
	* @param currentId the current ID of this counter
	*/
	@Override
	public void setCurrentId(long currentId) {
		_counter.setCurrentId(currentId);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_counter.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_counter.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_counter.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the name of this counter.
	*
	* @param name the name of this counter
	*/
	@Override
	public void setName(java.lang.String name) {
		_counter.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_counter.setNew(n);
	}

	/**
	* Sets the primary key of this counter.
	*
	* @param primaryKey the primary key of this counter
	*/
	@Override
	public void setPrimaryKey(java.lang.String primaryKey) {
		_counter.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_counter.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CounterWrapper)) {
			return false;
		}

		CounterWrapper counterWrapper = (CounterWrapper)obj;

		if (Objects.equals(_counter, counterWrapper._counter)) {
			return true;
		}

		return false;
	}

	@Override
	public Counter getWrappedModel() {
		return _counter;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _counter.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _counter.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_counter.resetOriginalValues();
	}

	private final Counter _counter;
}