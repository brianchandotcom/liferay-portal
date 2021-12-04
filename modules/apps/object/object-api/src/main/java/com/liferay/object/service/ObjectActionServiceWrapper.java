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
 * Provides a wrapper for {@link ObjectActionService}.
 *
 * @author Marco Leo
 * @see ObjectActionService
 * @generated
 */
public class ObjectActionServiceWrapper
	implements ObjectActionService, ServiceWrapper<ObjectActionService> {

	public ObjectActionServiceWrapper() {
		this(null);
	}

	public ObjectActionServiceWrapper(ObjectActionService objectActionService) {
		_objectActionService = objectActionService;
	}

	@Override
	public com.liferay.object.model.ObjectAction addObjectAction(
			long objectDefinitionId, boolean active, String name,
			String objectActionExecutorKey, String objectActionTriggerKey,
			com.liferay.portal.kernel.util.UnicodeProperties
				parametersUnicodeProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectActionService.addObjectAction(
			objectDefinitionId, active, name, objectActionExecutorKey,
			objectActionTriggerKey, parametersUnicodeProperties);
	}

	@Override
	public com.liferay.object.model.ObjectAction deleteObjectAction(
			long objectActionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectActionService.deleteObjectAction(objectActionId);
	}

	@Override
	public com.liferay.object.model.ObjectAction getObjectAction(
			long objectActionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectActionService.getObjectAction(objectActionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectActionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectAction updateObjectAction(
			long objectActionId, boolean active, String name,
			com.liferay.portal.kernel.util.UnicodeProperties
				parametersUnicodeProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectActionService.updateObjectAction(
			objectActionId, active, name, parametersUnicodeProperties);
	}

	@Override
	public ObjectActionService getWrappedService() {
		return _objectActionService;
	}

	@Override
	public void setWrappedService(ObjectActionService objectActionService) {
		_objectActionService = objectActionService;
	}

	private ObjectActionService _objectActionService;

}