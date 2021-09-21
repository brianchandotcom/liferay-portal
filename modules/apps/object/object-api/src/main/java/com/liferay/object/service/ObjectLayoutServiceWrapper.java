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

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectLayoutService}.
 *
 * @author Marco Leo
 * @see ObjectLayoutService
 * @generated
 */
public class ObjectLayoutServiceWrapper
	implements ObjectLayoutService, ServiceWrapper<ObjectLayoutService> {

	public ObjectLayoutServiceWrapper(ObjectLayoutService objectLayoutService) {
		_objectLayoutService = objectLayoutService;
	}

	@Override
	public com.liferay.object.model.ObjectLayout addObjectLayout(
			long objectDefinitionId, boolean defaultObjectLayout,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.List<com.liferay.object.model.ObjectLayoutTab>
				objectLayoutTabs)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutService.addObjectLayout(
			objectDefinitionId, defaultObjectLayout, nameMap, objectLayoutTabs);
	}

	@Override
	public com.liferay.object.model.ObjectLayout deleteObjectLayout(
			long objectLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutService.deleteObjectLayout(objectLayoutId);
	}

	@Override
	public com.liferay.object.model.ObjectLayout getObjectLayout(
			long objectLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutService.getObjectLayout(objectLayoutId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectLayoutService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectLayout updateObjectLayout(
			long objectLayoutId, boolean defaultObjectLayout,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.List<com.liferay.object.model.ObjectLayoutTab>
				objectLayoutTabs)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectLayoutService.updateObjectLayout(
			objectLayoutId, defaultObjectLayout, nameMap, objectLayoutTabs);
	}

	@Override
	public ObjectLayoutService getWrappedService() {
		return _objectLayoutService;
	}

	@Override
	public void setWrappedService(ObjectLayoutService objectLayoutService) {
		_objectLayoutService = objectLayoutService;
	}

	private ObjectLayoutService _objectLayoutService;

}