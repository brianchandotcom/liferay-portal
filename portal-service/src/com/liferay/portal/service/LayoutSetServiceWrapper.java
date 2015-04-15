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
 * Provides a wrapper for {@link LayoutSetService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetService
 * @generated
 */
@ProviderType
public class LayoutSetServiceWrapper implements LayoutSetService,
	ServiceWrapper<LayoutSetService> {
	public LayoutSetServiceWrapper(LayoutSetService layoutSetService) {
		_layoutSetService = layoutSetService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _layoutSetService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_layoutSetService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the state of the layout set prototype link.
	*
	* <p>
	* <strong>Important:</strong> Setting
	* <code>layoutSetPrototypeLinkEnabled</code> to <code>true</code> and
	* <code>layoutSetPrototypeUuid</code> to <code>null</code> when the layout
	* set prototype's current uuid is <code>null</code> will result in an
	* <code>IllegalStateException</code>.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param layoutSetPrototypeLinkEnabled whether the layout set prototype is
	link enabled
	* @param layoutSetPrototypeUuid the uuid of the layout set prototype to
	link with
	* @throws PortalException if a portal exception occurred
	*/
	@Override
	public void updateLayoutSetPrototypeLinkEnabled(long groupId,
		boolean layoutSetPrototypeLinkEnabled,
		java.lang.String layoutSetPrototypeUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetService.updateLayoutSetPrototypeLinkEnabled(groupId,
			layoutSetPrototypeLinkEnabled, layoutSetPrototypeUuid);
	}

	@Override
	public void updateLogo(long groupId, boolean logo, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetService.updateLogo(groupId, logo, bytes);
	}

	@Override
	public void updateLogo(long groupId, boolean logo, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetService.updateLogo(groupId, logo, file);
	}

	@Override
	public void updateLogo(long groupId, boolean logo,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetService.updateLogo(groupId, logo, inputStream);
	}

	@Override
	public void updateLogo(long groupId, boolean logo,
		java.io.InputStream inputStream, boolean cleanUpStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetService.updateLogo(groupId, logo, inputStream, cleanUpStream);
	}

	@Override
	public com.liferay.portal.model.LayoutSet updateLookAndFeel(long groupId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetService.updateLookAndFeel(groupId, themeId,
			colorSchemeId, css, wapTheme);
	}

	@Override
	public com.liferay.portal.model.LayoutSet updateSettings(long groupId,
		java.lang.String settings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetService.updateSettings(groupId, settings);
	}

	@Override
	public com.liferay.portal.model.LayoutSet updateVirtualHost(long groupId,
		java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetService.updateVirtualHost(groupId, virtualHost);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public LayoutSetService getWrappedLayoutSetService() {
		return _layoutSetService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedLayoutSetService(LayoutSetService layoutSetService) {
		_layoutSetService = layoutSetService;
	}

	@Override
	public LayoutSetService getWrappedService() {
		return _layoutSetService;
	}

	@Override
	public void setWrappedService(LayoutSetService layoutSetService) {
		_layoutSetService = layoutSetService;
	}

	private LayoutSetService _layoutSetService;
}