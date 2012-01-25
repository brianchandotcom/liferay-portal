/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.util.Locale;

/**
 * The base implementation for all model classes. This class should never need
 * to be used directly.
 *
 * @author Brian Wing Shun Chan
 */
public abstract class BaseModelImpl<T> implements BaseModel<T> {

	public BaseModelImpl() {
	}

	@Override
	public abstract Object clone();

	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	public boolean isCachedModel() {
		return _cachedModel;
	}

	public boolean isEscapedModel() {
		return _ESCAPED_MODEL;
	}

	public boolean isNew() {
		return _new;
	}

	public void resetOriginalValues() {
	}

	public void setCachedModel(boolean cachedModel) {
		_cachedModel = cachedModel;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		throw new UnsupportedOperationException();
	}

	public void setNew(boolean n) {
		_new = n;
	}

	public CacheModel<T> toCacheModel() {
		throw new UnsupportedOperationException();
	}

	public T toEscapedModel() {
		throw new UnsupportedOperationException();
	}

	protected Locale getLocale(String languageId) {
		Locale locale = null;

		if (languageId != null) {
			locale = LocaleUtil.fromLanguageId(languageId);
		}

		if (locale == null) {
			locale = LocaleThreadLocal.getThemeDisplayLocale();
		}

		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}

		return locale;
	}

	private static final boolean _ESCAPED_MODEL = false;

	private boolean _cachedModel;
	private boolean _new;

}