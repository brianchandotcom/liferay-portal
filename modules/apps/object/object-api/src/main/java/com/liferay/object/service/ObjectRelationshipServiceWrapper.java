/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectRelationshipService}.
 *
 * @author Marco Leo
 * @see ObjectRelationshipService
 * @generated
 */
public class ObjectRelationshipServiceWrapper
	implements ObjectRelationshipService,
			   ServiceWrapper<ObjectRelationshipService> {

	public ObjectRelationshipServiceWrapper() {
		this(null);
	}

	public ObjectRelationshipServiceWrapper(
		ObjectRelationshipService objectRelationshipService) {

		_objectRelationshipService = objectRelationshipService;
	}

	@Override
	public com.liferay.object.model.ObjectRelationship addObjectRelationship(
			long objectDefinitionId1, long objectDefinitionId2,
			long parameterObjectFieldId, String deletionType,
			java.util.Map<java.util.Locale, String> labelMap, String name,
			String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipService.addObjectRelationship(
			objectDefinitionId1, objectDefinitionId2, parameterObjectFieldId,
			deletionType, labelMap, name, type);
	}

	@Override
	public void addObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1, long primaryKey2,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_objectRelationshipService.addObjectRelationshipMappingTableValues(
			objectRelationshipId, primaryKey1, primaryKey2, serviceContext);
	}

	@Override
	public com.liferay.object.model.ObjectRelationship deleteObjectRelationship(
			long objectRelationshipId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipService.deleteObjectRelationship(
			objectRelationshipId);
	}

	@Override
	public com.liferay.object.model.ObjectRelationship getObjectRelationship(
			long objectRelationshipId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipService.getObjectRelationship(
			objectRelationshipId);
	}

	@Override
	public com.liferay.object.model.ObjectRelationship getObjectRelationship(
			long objectDefinitionId1, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipService.getObjectRelationship(
			objectDefinitionId1, name);
	}

	@Override
	public java.util.List<com.liferay.object.model.ObjectRelationship>
			getObjectRelationships(long objectDefinitionId1, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipService.getObjectRelationships(
			objectDefinitionId1, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectRelationshipService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectRelationship updateObjectRelationship(
			long objectRelationshipId, long parameterObjectFieldId,
			String deletionType, boolean edge,
			java.util.Map<java.util.Locale, String> labelMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectRelationshipService.updateObjectRelationship(
			objectRelationshipId, parameterObjectFieldId, deletionType, edge,
			labelMap);
	}

	@Override
	public ObjectRelationshipService getWrappedService() {
		return _objectRelationshipService;
	}

	@Override
	public void setWrappedService(
		ObjectRelationshipService objectRelationshipService) {

		_objectRelationshipService = objectRelationshipService;
	}

	private ObjectRelationshipService _objectRelationshipService;

}