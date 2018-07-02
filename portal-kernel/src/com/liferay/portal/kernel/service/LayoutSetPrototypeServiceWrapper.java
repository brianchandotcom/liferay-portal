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

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link LayoutSetPrototypeService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototypeService
 * @generated
 */
@ProviderType
public class LayoutSetPrototypeServiceWrapper
	implements LayoutSetPrototypeService,
		ServiceWrapper<LayoutSetPrototypeService> {
	public LayoutSetPrototypeServiceWrapper(
		LayoutSetPrototypeService layoutSetPrototypeService) {
		_layoutSetPrototypeService = layoutSetPrototypeService;
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSetPrototype addLayoutSetPrototype(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, boolean active,
		boolean layoutsUpdateable, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetPrototypeService.addLayoutSetPrototype(nameMap,
			descriptionMap, active, layoutsUpdateable, serviceContext);
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#addLayoutSetPrototype(Map, Map, boolean, boolean,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.LayoutSetPrototype addLayoutSetPrototype(
		java.util.Map<java.util.Locale, String> nameMap, String description,
		boolean active, boolean layoutsUpdateable, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetPrototypeService.addLayoutSetPrototype(nameMap,
			description, active, layoutsUpdateable, serviceContext);
	}

	@Override
	public void deleteLayoutSetPrototype(long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetPrototypeService.deleteLayoutSetPrototype(layoutSetPrototypeId);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSetPrototype fetchLayoutSetPrototype(
		long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetPrototypeService.fetchLayoutSetPrototype(layoutSetPrototypeId);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSetPrototype getLayoutSetPrototype(
		long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetPrototypeService.getLayoutSetPrototype(layoutSetPrototypeId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutSetPrototypeService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.LayoutSetPrototype> search(
		long companyId, Boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.LayoutSetPrototype> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetPrototypeService.search(companyId, active, obc);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, boolean active,
		boolean layoutsUpdateable, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetPrototypeService.updateLayoutSetPrototype(layoutSetPrototypeId,
			nameMap, descriptionMap, active, layoutsUpdateable, serviceContext);
	}

	/**
	* @deprecated As of Wilberforce (7.0.x), replaced by {@link
	#updateLayoutSetPrototype(long, Map, Map, boolean, boolean,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId,
		java.util.Map<java.util.Locale, String> nameMap, String description,
		boolean active, boolean layoutsUpdateable, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetPrototypeService.updateLayoutSetPrototype(layoutSetPrototypeId,
			nameMap, description, active, layoutsUpdateable, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId, String settings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetPrototypeService.updateLayoutSetPrototype(layoutSetPrototypeId,
			settings);
	}

	@Override
	public LayoutSetPrototypeService getWrappedService() {
		return _layoutSetPrototypeService;
	}

	@Override
	public void setWrappedService(
		LayoutSetPrototypeService layoutSetPrototypeService) {
		_layoutSetPrototypeService = layoutSetPrototypeService;
	}

	private LayoutSetPrototypeService _layoutSetPrototypeService;
}