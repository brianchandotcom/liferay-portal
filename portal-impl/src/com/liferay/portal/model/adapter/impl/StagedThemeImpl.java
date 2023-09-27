/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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