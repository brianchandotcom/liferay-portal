/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * Provides a wrapper for {@link LayoutRevisionService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutRevisionService
 * @generated
 */
public class LayoutRevisionServiceWrapper implements LayoutRevisionService,
	ServiceWrapper<LayoutRevisionService> {
	public LayoutRevisionServiceWrapper(
		LayoutRevisionService layoutRevisionService) {
		_layoutRevisionService = layoutRevisionService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _layoutRevisionService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_layoutRevisionService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.LayoutRevision addLayoutRevision(
		long userId, long layoutSetBranchId, long layoutBranchId,
		long parentLayoutRevisionId, boolean head, long plid,
		long portletPreferencesPlid, boolean privateLayout,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String keywords,
		java.lang.String robots, java.lang.String typeSettings,
		boolean iconImage, long iconImageId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String wapThemeId,
		java.lang.String wapColorSchemeId, java.lang.String css,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutRevisionService.addLayoutRevision(userId,
			layoutSetBranchId, layoutBranchId, parentLayoutRevisionId, head,
			plid, portletPreferencesPlid, privateLayout, name, title,
			description, keywords, robots, typeSettings, iconImage,
			iconImageId, themeId, colorSchemeId, wapThemeId, wapColorSchemeId,
			css, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public LayoutRevisionService getWrappedLayoutRevisionService() {
		return _layoutRevisionService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedLayoutRevisionService(
		LayoutRevisionService layoutRevisionService) {
		_layoutRevisionService = layoutRevisionService;
	}

	public LayoutRevisionService getWrappedService() {
		return _layoutRevisionService;
	}

	public void setWrappedService(LayoutRevisionService layoutRevisionService) {
		_layoutRevisionService = layoutRevisionService;
	}

	private LayoutRevisionService _layoutRevisionService;
}