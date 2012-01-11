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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link LayoutSetService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetService
 * @generated
 */
public class LayoutSetServiceWrapper implements LayoutSetService,
	ServiceWrapper<LayoutSetService> {
	public LayoutSetServiceWrapper(LayoutSetService layoutSetService) {
		_layoutSetService = layoutSetService;
	}

	public void updateLayoutSetPrototypeLinkEnabled(long groupId,
		boolean privateLayout, boolean layoutSetPrototypeLinkEnabled,
		java.lang.String layoutSetPrototypeUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutSetService.updateLayoutSetPrototypeLinkEnabled(groupId,
			privateLayout, layoutSetPrototypeLinkEnabled, layoutSetPrototypeUuid);
	}

	public void updateLogo(long groupId, boolean privateLayout, boolean logo,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutSetService.updateLogo(groupId, privateLayout, logo, inputStream);
	}

	public void updateLogo(long groupId, boolean privateLayout, boolean logo,
		java.io.InputStream inputStream, boolean cleanUpStream)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutSetService.updateLogo(groupId, privateLayout, logo, inputStream,
			cleanUpStream);
	}

	public com.liferay.portal.model.LayoutSet updateLookAndFeel(long groupId,
		boolean privateLayout, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetService.updateLookAndFeel(groupId, privateLayout,
			themeId, colorSchemeId, css, wapTheme);
	}

	public com.liferay.portal.model.LayoutSet updateSettings(long groupId,
		boolean privateLayout, java.lang.String settings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetService.updateSettings(groupId, privateLayout, settings);
	}

	public com.liferay.portal.model.LayoutSet updateVirtualHost(long groupId,
		boolean privateLayout, java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetService.updateVirtualHost(groupId, privateLayout,
			virtualHost);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public LayoutSetService getWrappedLayoutSetService() {
		return _layoutSetService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedLayoutSetService(LayoutSetService layoutSetService) {
		_layoutSetService = layoutSetService;
	}

	public LayoutSetService getWrappedService() {
		return _layoutSetService;
	}

	public void setWrappedService(LayoutSetService layoutSetService) {
		_layoutSetService = layoutSetService;
	}

	private LayoutSetService _layoutSetService;
}