/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.object.model.ObjectRelationship;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for ObjectRelationship. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectRelationshipServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see ObjectRelationshipService
 * @generated
 */
public class ObjectRelationshipServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectRelationshipServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectRelationship addObjectRelationship(
			long objectDefinitionId1, long objectDefinitionId2,
			long parameterObjectFieldId, String deletionType,
			Map<java.util.Locale, String> labelMap, String name, String type)
		throws PortalException {

		return getService().addObjectRelationship(
			objectDefinitionId1, objectDefinitionId2, parameterObjectFieldId,
			deletionType, labelMap, name, type);
	}

	public static void addObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1, long primaryKey2,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().addObjectRelationshipMappingTableValues(
			objectRelationshipId, primaryKey1, primaryKey2, serviceContext);
	}

	public static ObjectRelationship deleteObjectRelationship(
			long objectRelationshipId)
		throws PortalException {

		return getService().deleteObjectRelationship(objectRelationshipId);
	}

	public static ObjectRelationship getObjectRelationship(
			long objectRelationshipId)
		throws PortalException {

		return getService().getObjectRelationship(objectRelationshipId);
	}

	public static ObjectRelationship getObjectRelationship(
			long objectDefinitionId1, String name)
		throws PortalException {

		return getService().getObjectRelationship(objectDefinitionId1, name);
	}

	public static List<ObjectRelationship> getObjectRelationships(
			long objectDefinitionId1, int start, int end)
		throws PortalException {

		return getService().getObjectRelationships(
			objectDefinitionId1, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ObjectRelationship updateObjectRelationship(
			long objectRelationshipId, long parameterObjectFieldId,
			String deletionType, boolean edge,
			Map<java.util.Locale, String> labelMap)
		throws PortalException {

		return getService().updateObjectRelationship(
			objectRelationshipId, parameterObjectFieldId, deletionType, edge,
			labelMap);
	}

	public static ObjectRelationshipService getService() {
		return _service;
	}

	public static void setService(ObjectRelationshipService service) {
		_service = service;
	}

	private static volatile ObjectRelationshipService _service;

}