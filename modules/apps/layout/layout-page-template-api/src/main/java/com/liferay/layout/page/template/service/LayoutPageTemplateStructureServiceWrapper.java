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

package com.liferay.layout.page.template.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutPageTemplateStructureService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureService
 * @generated
 */
public class LayoutPageTemplateStructureServiceWrapper
	implements LayoutPageTemplateStructureService,
			   ServiceWrapper<LayoutPageTemplateStructureService> {

	public LayoutPageTemplateStructureServiceWrapper(
		LayoutPageTemplateStructureService layoutPageTemplateStructureService) {

		_layoutPageTemplateStructureService =
			layoutPageTemplateStructureService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutPageTemplateStructureService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateStructure
			updateLayoutPageTemplateStructure(
				long groupId, long classNameId, long classPK,
				long segmentsExperienceId, String data)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateStructureService.
			updateLayoutPageTemplateStructure(
				groupId, classNameId, classPK, segmentsExperienceId, data);
	}

	@Override
	public LayoutPageTemplateStructureService getWrappedService() {
		return _layoutPageTemplateStructureService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateStructureService layoutPageTemplateStructureService) {

		_layoutPageTemplateStructureService =
			layoutPageTemplateStructureService;
	}

	private LayoutPageTemplateStructureService
		_layoutPageTemplateStructureService;

}