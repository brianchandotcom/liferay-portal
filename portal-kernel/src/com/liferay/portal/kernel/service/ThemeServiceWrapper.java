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

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link ThemeService}.
 *
 * @author Brian Wing Shun Chan
 * @see ThemeService
 * @generated
 */
public class ThemeServiceWrapper
	implements ServiceWrapper<ThemeService>, ThemeService {

	public ThemeServiceWrapper(ThemeService themeService) {
		_themeService = themeService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _themeService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Theme> getThemes(
		long companyId) {

		return _themeService.getThemes(companyId);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getWARThemes() {
		return _themeService.getWARThemes();
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