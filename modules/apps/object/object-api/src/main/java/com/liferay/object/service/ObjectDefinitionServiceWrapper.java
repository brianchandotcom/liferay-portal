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

	public ObjectDefinitionServiceWrapper() {
		this(null);
	}

	public ObjectDefinitionServiceWrapper(
		ObjectDefinitionService objectDefinitionService) {

		_objectDefinitionService = objectDefinitionService;
	}

	@Override
	public com.liferay.object.model.ObjectDefinition addCustomObjectDefinition(
			boolean enableComments, boolean enableLocalization,
			java.util.Map<java.util.Locale, String> labelMap, String name,
			String panelAppOrder, String panelCategoryKey,
			java.util.Map<java.util.Locale, String> pluralLabelMap,
			Boolean portlet, String scope, String storageType,
			java.util.List<com.liferay.object.model.ObjectField> objectFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.addCustomObjectDefinition(
			enableComments, enableLocalization, labelMap, name, panelAppOrder,
			panelCategoryKey, pluralLabelMap, portlet, scope, storageType,
			objectFields);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition addObjectDefinition(
			String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.addObjectDefinition(
			externalReferenceCode);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition addSystemObjectDefinition(
			long userId, boolean enableComments,
			java.util.Map<java.util.Locale, String> labelMap, String name,
			String panelAppOrder, String panelCategoryKey,
			java.util.Map<java.util.Locale, String> pluralLabelMap,
			String scope,
			java.util.List<com.liferay.object.model.ObjectField> objectFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.addSystemObjectDefinition(
			userId, enableComments, labelMap, name, panelAppOrder,
			panelCategoryKey, pluralLabelMap, scope, objectFields);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition deleteObjectDefinition(
			long objectDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.deleteObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition
			fetchObjectDefinitionByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.
			fetchObjectDefinitionByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition getObjectDefinition(
			long objectDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.getObjectDefinition(objectDefinitionId);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition
			getObjectDefinitionByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.
			getObjectDefinitionByExternalReferenceCode(
				externalReferenceCode, companyId);
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
			publishSystemObjectDefinition(long objectDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.publishSystemObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition
			updateCustomObjectDefinition(
				String externalReferenceCode, long objectDefinitionId,
				long accountEntryRestrictedObjectFieldId,
				long descriptionObjectFieldId, long titleObjectFieldId,
				boolean accountEntryRestricted, boolean active,
				boolean enableCategorization, boolean enableComments,
				boolean enableLocalization, boolean enableObjectEntryHistory,
				java.util.Map<java.util.Locale, String> labelMap, String name,
				String panelAppOrder, String panelCategoryKey, boolean portlet,
				java.util.Map<java.util.Locale, String> pluralLabelMap,
				String scope)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.updateCustomObjectDefinition(
			externalReferenceCode, objectDefinitionId,
			accountEntryRestrictedObjectFieldId, descriptionObjectFieldId,
			titleObjectFieldId, accountEntryRestricted, active,
			enableCategorization, enableComments, enableLocalization,
			enableObjectEntryHistory, labelMap, name, panelAppOrder,
			panelCategoryKey, portlet, pluralLabelMap, scope);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition
			updateExternalReferenceCode(
				long objectDefinitionId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.updateExternalReferenceCode(
			objectDefinitionId, externalReferenceCode);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition
			updateSystemObjectDefinition(
				String externalReferenceCode, long objectDefinitionId,
				long titleObjectFieldId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.updateSystemObjectDefinition(
			externalReferenceCode, objectDefinitionId, titleObjectFieldId);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition updateTitleObjectFieldId(
			long objectDefinitionId, long titleObjectFieldId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionService.updateTitleObjectFieldId(
			objectDefinitionId, titleObjectFieldId);
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