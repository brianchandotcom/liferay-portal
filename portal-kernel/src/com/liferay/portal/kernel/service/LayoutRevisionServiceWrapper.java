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
 * Provides a wrapper for {@link LayoutRevisionService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevisionService
 * @generated
 */
public class LayoutRevisionServiceWrapper
	implements LayoutRevisionService, ServiceWrapper<LayoutRevisionService> {

	public LayoutRevisionServiceWrapper(
		LayoutRevisionService layoutRevisionService) {

		_layoutRevisionService = layoutRevisionService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutRevisionServiceUtil} to access the layout revision remote service. Add custom service methods to <code>com.liferay.portal.service.impl.LayoutRevisionServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.kernel.model.LayoutRevision addLayoutRevision(
			long userId, long layoutSetBranchId, long layoutBranchId,
			long parentLayoutRevisionId, boolean head, long plid,
			long portletPreferencesPlid, boolean privateLayout,
			java.lang.String name, java.lang.String title,
			java.lang.String description, java.lang.String keywords,
			java.lang.String robots, java.lang.String typeSettings,
			boolean iconImage, long iconImageId, java.lang.String themeId,
			java.lang.String colorSchemeId, java.lang.String css,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutRevisionService.addLayoutRevision(
			userId, layoutSetBranchId, layoutBranchId, parentLayoutRevisionId,
			head, plid, portletPreferencesPlid, privateLayout, name, title,
			description, keywords, robots, typeSettings, iconImage, iconImageId,
			themeId, colorSchemeId, css, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutRevisionService.getOSGiServiceIdentifier();
	}

	@Override
	public LayoutRevisionService getWrappedService() {
		return _layoutRevisionService;
	}

	@Override
	public void setWrappedService(LayoutRevisionService layoutRevisionService) {
		_layoutRevisionService = layoutRevisionService;
	}

	private LayoutRevisionService _layoutRevisionService;

}