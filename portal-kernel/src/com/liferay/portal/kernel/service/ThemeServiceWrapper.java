/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public ThemeServiceWrapper() {
		this(null);
	}

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