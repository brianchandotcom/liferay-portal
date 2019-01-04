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

package com.liferay.portal.kernel.model.wrapper;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public abstract class BaseModelWrapper<T extends BaseModel<T>>
	implements BaseModel<T>, ModelWrapper<T> {

	public BaseModelWrapper(T model) {
		this.model = model;
	}

	@Override
	public Object clone() {
		return wrap((T)model.clone());
	}

	@Override
	public int compareTo(T o) {
		return model.compareTo(o);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BaseModelWrapper)) {
			return false;
		}

		BaseModelWrapper<?> baseModelWrapper = (BaseModelWrapper<?>)object;

		if (Objects.equals(model, baseModelWrapper.model)) {
			return true;
		}

		return false;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return model.getExpandoBridge();
	}

	@Override
	public Class<?> getModelClass() {
		return model.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return model.getModelClassName();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return model.getPrimaryKeyObj();
	}

	@Override
	public T getWrappedModel() {
		return model;
	}

	@Override
	public int hashCode() {
		return model.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return model.isCachedModel();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return model.isEntityCacheEnabled();
	}

	@Override
	public boolean isEscapedModel() {
		return model.isEscapedModel();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return model.isFinderCacheEnabled();
	}

	@Override
	public boolean isNew() {
		return model.isNew();
	}

	@Override
	public void resetOriginalValues() {
		model.resetOriginalValues();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		model.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		model.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		model.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		model.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		model.setNew(n);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		model.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public CacheModel<T> toCacheModel() {
		return model.toCacheModel();
	}

	@Override
	public T toEscapedModel() {
		return wrap(model.toEscapedModel());
	}

	@Override
	public String toString() {
		return model.toString();
	}

	@Override
	public T toUnescapedModel() {
		return wrap(model.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	protected abstract T wrap(T model);

	protected final T model;

}