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

import com.liferay.object.model.ObjectRelationship;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for ObjectRelationship. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectRelationshipLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see ObjectRelationshipLocalService
 * @generated
 */
public class ObjectRelationshipLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectRelationshipLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectRelationship addObjectRelationship(
			long userId, long objectDefinitionId1, long objectDefinitionId2,
			String deletionType, Map<java.util.Locale, String> labelMap,
			String name, String type)
		throws PortalException {

		return getService().addObjectRelationship(
			userId, objectDefinitionId1, objectDefinitionId2, deletionType,
			labelMap, name, type);
	}

	/**
	 * Adds the object relationship to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectRelationshipLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectRelationship the object relationship
	 * @return the object relationship that was added
	 */
	public static ObjectRelationship addObjectRelationship(
		ObjectRelationship objectRelationship) {

		return getService().addObjectRelationship(objectRelationship);
	}

	public static void addObjectRelationshipMappingTableValues(
			long userId, long objectRelationshipId, long primaryKey1,
			long primaryKey2,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().addObjectRelationshipMappingTableValues(
			userId, objectRelationshipId, primaryKey1, primaryKey2,
			serviceContext);
	}

	/**
	 * Creates a new object relationship with the primary key. Does not add the object relationship to the database.
	 *
	 * @param objectRelationshipId the primary key for the new object relationship
	 * @return the new object relationship
	 */
	public static ObjectRelationship createObjectRelationship(
		long objectRelationshipId) {

		return getService().createObjectRelationship(objectRelationshipId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the object relationship with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectRelationshipLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectRelationshipId the primary key of the object relationship
	 * @return the object relationship that was removed
	 * @throws PortalException if a object relationship with the primary key could not be found
	 */
	public static ObjectRelationship deleteObjectRelationship(
			long objectRelationshipId)
		throws PortalException {

		return getService().deleteObjectRelationship(objectRelationshipId);
	}

	/**
	 * Deletes the object relationship from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectRelationshipLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectRelationship the object relationship
	 * @return the object relationship that was removed
	 * @throws PortalException
	 */
	public static ObjectRelationship deleteObjectRelationship(
			ObjectRelationship objectRelationship)
		throws PortalException {

		return getService().deleteObjectRelationship(objectRelationship);
	}

	public static void deleteObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1)
		throws PortalException {

		getService().deleteObjectRelationshipMappingTableValues(
			objectRelationshipId, primaryKey1);
	}

	public static void deleteObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1, long primaryKey2)
		throws PortalException {

		getService().deleteObjectRelationshipMappingTableValues(
			objectRelationshipId, primaryKey1, primaryKey2);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static ObjectRelationship fetchObjectRelationship(
		long objectRelationshipId) {

		return getService().fetchObjectRelationship(objectRelationshipId);
	}

	public static ObjectRelationship fetchObjectRelationshipByObjectFieldId2(
		long objectFieldId2) {

		return getService().fetchObjectRelationshipByObjectFieldId2(
			objectFieldId2);
	}

	/**
	 * Returns the object relationship with the matching UUID and company.
	 *
	 * @param uuid the object relationship's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object relationship, or <code>null</code> if a matching object relationship could not be found
	 */
	public static ObjectRelationship fetchObjectRelationshipByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchObjectRelationshipByUuidAndCompanyId(
			uuid, companyId);
	}

	public static ObjectRelationship fetchReverseObjectRelationship(
		ObjectRelationship objectRelationship, boolean reverse) {

		return getService().fetchReverseObjectRelationship(
			objectRelationship, reverse);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the object relationship with the primary key.
	 *
	 * @param objectRelationshipId the primary key of the object relationship
	 * @return the object relationship
	 * @throws PortalException if a object relationship with the primary key could not be found
	 */
	public static ObjectRelationship getObjectRelationship(
			long objectRelationshipId)
		throws PortalException {

		return getService().getObjectRelationship(objectRelationshipId);
	}

	/**
	 * Returns the object relationship with the matching UUID and company.
	 *
	 * @param uuid the object relationship's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object relationship
	 * @throws PortalException if a matching object relationship could not be found
	 */
	public static ObjectRelationship getObjectRelationshipByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getObjectRelationshipByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the object relationships.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @return the range of object relationships
	 */
	public static List<ObjectRelationship> getObjectRelationships(
		int start, int end) {

		return getService().getObjectRelationships(start, end);
	}

	public static List<ObjectRelationship> getObjectRelationships(
		long objectDefinitionId1) {

		return getService().getObjectRelationships(objectDefinitionId1);
	}

	public static List<ObjectRelationship> getObjectRelationships(
		long objectDefinitionId1, int start, int end) {

		return getService().getObjectRelationships(
			objectDefinitionId1, start, end);
	}

	/**
	 * Returns the number of object relationships.
	 *
	 * @return the number of object relationships
	 */
	public static int getObjectRelationshipsCount() {
		return getService().getObjectRelationshipsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static ObjectRelationship updateObjectRelationship(
			long objectRelationshipId, String deletionType,
			Map<java.util.Locale, String> labelMap)
		throws PortalException {

		return getService().updateObjectRelationship(
			objectRelationshipId, deletionType, labelMap);
	}

	/**
	 * Updates the object relationship in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectRelationshipLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectRelationship the object relationship
	 * @return the object relationship that was updated
	 */
	public static ObjectRelationship updateObjectRelationship(
		ObjectRelationship objectRelationship) {

		return getService().updateObjectRelationship(objectRelationship);
	}

	public static ObjectRelationshipLocalService getService() {
		return _service;
	}

	private static volatile ObjectRelationshipLocalService _service;

}