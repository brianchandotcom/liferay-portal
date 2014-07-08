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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link ThemeService}.
 *
 * @author Brian Wing Shun Chan
 * @see ThemeService
 * @generated
 */
@ProviderType
public class ThemeServiceWrapper implements ThemeService,
	ServiceWrapper<ThemeService> {
	public ThemeServiceWrapper(ThemeService themeService) {
		_themeService = themeService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _themeService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_themeService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Theme> getThemes(
		long companyId) {
		return _themeService.getThemes(companyId);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getWARThemes() {
		return _themeService.getWARThemes();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public ThemeService getWrappedThemeService() {
		return _themeService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedThemeService(ThemeService themeService) {
		_themeService = themeService;
	}

	@Override
	public ThemeService getWrappedService() {
		return _themeService;
	}

	@Override
	public void setWrappedService(ThemeService themeService) {
		_themeService = themeService;
	}

	private ThemeService _themeService;
}