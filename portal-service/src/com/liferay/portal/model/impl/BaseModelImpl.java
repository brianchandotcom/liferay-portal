/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.expando.model.ExpandoBridge;

/**
 * The base implementation for all model classes. This class should never need
 * to be used directly.
 *
 * @author Brian Wing Shun Chan
 */
public abstract class BaseModelImpl<T> implements BaseModel<T> {

	public BaseModelImpl() {
	}

	public boolean isNew() {
		return _new;
	}

	public void setNew(boolean n) {
		_new = n;
	}

	public boolean isCachedModel() {
		return _cachedModel;
	}

	public void setCachedModel(boolean cachedModel) {
		_cachedModel = cachedModel;
	}

	public boolean isEscapedModel() {
		return false;
	}

	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public abstract Object clone();

	public void resetOriginalValues() {
	}

	public CacheModel<T> toCacheModel() {
		throw new UnsupportedOperationException();
	}

	public T toEscapedModel() {
		throw new UnsupportedOperationException();
	}

	private boolean _new;
	private boolean _cachedModel;

}