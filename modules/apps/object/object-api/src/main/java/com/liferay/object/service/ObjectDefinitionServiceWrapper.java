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
 * Provides a wrapper for {@link ObjectDefinitionService}.
 *
 * @author Marco Leo
 * @see ObjectDefinitionService
 * @generated
 */
public class ObjectDefinitionServiceWrapper
	implements ObjectDefinitionService,
			   ServiceWrapper<ObjectDefinitionService> {

	public ObjectDefinitionServiceWrapper(
		ObjectDefinitionService objectDefinitionService) {

		_objectDefinitionService = objectDefinitionService;
	}

	@Override
	public com.liferay.object.model.ObjectDefinition addCustomObjectDefinition(
			java.util.Map<java.util.Locale, String> labelMap, String name,
			String panelAppOrder, String panelCategoryKey,
			java.util.Map<java.util.Locale, String> pluralLabelMap,
			String scope,
			java.util.List<com.liferay.object.model.ObjectField> objectFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.addCustomObjectDefinition(
			labelMap, name, panelAppOrder, panelCategoryKey, pluralLabelMap,
			scope, objectFields);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition deleteObjectDefinition(
			long objectDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.deleteObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition getObjectDefinition(
			long objectDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.getObjectDefinition(objectDefinitionId);
	}

	@Override
	public java.util.List<com.liferay.object.model.ObjectDefinition>
		getObjectDefinitions(int start, int end) {

		return _objectDefinitionService.getObjectDefinitions(start, end);
	}

	@Override
	public java.util.List<com.liferay.object.model.ObjectDefinition>
		getObjectDefinitions(long companyId, int start, int end) {

		return _objectDefinitionService.getObjectDefinitions(
			companyId, start, end);
	}

	@Override
	public int getObjectDefinitionsCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.getObjectDefinitionsCount();
	}

	@Override
	public int getObjectDefinitionsCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.getObjectDefinitionsCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectDefinitionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectDefinition
			publishCustomObjectDefinition(long objectDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.publishCustomObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition
			updateCustomObjectDefinition(
				Long objectDefinitionId, long descriptionObjectFieldId,
				long titleObjectFieldId, boolean active,
				java.util.Map<java.util.Locale, String> labelMap, String name,
				String panelAppOrder, String panelCategoryKey,
				java.util.Map<java.util.Locale, String> pluralLabelMap,
				String scope)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.updateCustomObjectDefinition(
			objectDefinitionId, descriptionObjectFieldId, titleObjectFieldId,
			active, labelMap, name, panelAppOrder, panelCategoryKey,
			pluralLabelMap, scope);
	}

	@Override
	public ObjectDefinitionService getWrappedService() {
		return _objectDefinitionService;
	}

	@Override
	public void setWrappedService(
		ObjectDefinitionService objectDefinitionService) {

		_objectDefinitionService = objectDefinitionService;
	}

	private ObjectDefinitionService _objectDefinitionService;

}