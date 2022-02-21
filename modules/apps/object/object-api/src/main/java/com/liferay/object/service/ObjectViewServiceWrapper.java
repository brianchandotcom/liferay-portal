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
 * Provides a wrapper for {@link ObjectViewService}.
 *
 * @author Marco Leo
 * @see ObjectViewService
 * @generated
 */
public class ObjectViewServiceWrapper
	implements ObjectViewService, ServiceWrapper<ObjectViewService> {

	public ObjectViewServiceWrapper() {
		this(null);
	}

	public ObjectViewServiceWrapper(ObjectViewService objectViewService) {
		_objectViewService = objectViewService;
	}

	@Override
	public com.liferay.object.model.ObjectView addObjectView(
			long objectDefinitionId, boolean defaultObjectView,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.List<com.liferay.object.model.ObjectViewColumn>
				objectViewColumns,
			java.util.List<com.liferay.object.model.ObjectViewSortColumn>
				objectViewSortColumns)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectViewService.addObjectView(
			objectDefinitionId, defaultObjectView, nameMap, objectViewColumns,
			objectViewSortColumns);
	}

	@Override
	public com.liferay.object.model.ObjectView deleteObjectView(
			long objectViewId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectViewService.deleteObjectView(objectViewId);
	}

	@Override
	public com.liferay.object.model.ObjectView getObjectView(long objectViewId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectViewService.getObjectView(objectViewId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectViewService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectView updateObjectView(
			long objectViewId, boolean defaultObjectView,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.List<com.liferay.object.model.ObjectViewColumn>
				objectViewColumns,
			java.util.List<com.liferay.object.model.ObjectViewSortColumn>
				objectViewSortColumns)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectViewService.updateObjectView(
			objectViewId, defaultObjectView, nameMap, objectViewColumns,
			objectViewSortColumns);
	}

	@Override
	public ObjectViewService getWrappedService() {
		return _objectViewService;
	}

	@Override
	public void setWrappedService(ObjectViewService objectViewService) {
		_objectViewService = objectViewService;
	}

	private ObjectViewService _objectViewService;

}