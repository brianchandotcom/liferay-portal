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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.adapter.StagedTheme;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.model.impl.ThemeImpl;

import java.io.Serializable;

import java.util.Date;

/**
 * @author Máté Thurzó
 */
public class StagedThemeImpl extends ThemeImpl implements StagedTheme {

	public StagedThemeImpl(Theme theme) {
		super(theme.getThemeId());
	}

	@Override
	public Object clone() {
		ThemeImpl themeImpl = new ThemeImpl(getThemeId());

		return new StagedThemeImpl(themeImpl);
	}

	@Override
	public long getCompanyId() {
		return CompanyThreadLocal.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return new Date();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	@Override
	public Class<?> getModelClass() {
		return StagedTheme.class;
	}

	@Override
	public String getModelClassName() {
		return StagedTheme.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return new Date();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return getThemeId();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedTheme.class);
	}

	@Override
	public String getUuid() {
		return StringPool.BLANK;
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

}