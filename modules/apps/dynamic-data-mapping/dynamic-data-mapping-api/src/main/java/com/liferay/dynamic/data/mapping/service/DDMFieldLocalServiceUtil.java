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

package com.liferay.dynamic.data.mapping.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDMField. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMFieldLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFieldLocalService
 * @generated
 */
public class DDMFieldLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMFieldLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the ddm field to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DDMFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ddmField the ddm field
	 * @return the ddm field that was added
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMField addDDMField(
		com.liferay.dynamic.data.mapping.model.DDMField ddmField) {

		return getService().addDDMField(ddmField);
	}

	/**
	 * Creates a new ddm field with the primary key. Does not add the ddm field to the database.
	 *
	 * @param fieldId the primary key for the new ddm field
	 * @return the new ddm field
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMField
		createDDMField(long fieldId) {

		return getService().createDDMField(fieldId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the ddm field from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DDMFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ddmField the ddm field
	 * @return the ddm field that was removed
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMField
		deleteDDMField(
			com.liferay.dynamic.data.mapping.model.DDMField ddmField) {

		return getService().deleteDDMField(ddmField);
	}

	/**
	 * Deletes the ddm field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DDMFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fieldId the primary key of the ddm field
	 * @return the ddm field that was removed
	 * @throws PortalException if a ddm field with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMField
			deleteDDMField(long fieldId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDDMField(fieldId);
	}

	public static void deleteDDMFields(long structureId) {
		getService().deleteDDMFields(structureId);
	}

	public static void deleteDDMFormValues(long storageId) {
		getService().deleteDDMFormValues(storageId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

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
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMField fetchDDMField(
		long fieldId) {

		return getService().fetchDDMField(fieldId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the ddm field with the primary key.
	 *
	 * @param fieldId the primary key of the ddm field
	 * @return the ddm field
	 * @throws PortalException if a ddm field with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMField getDDMField(
			long fieldId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDDMField(fieldId);
	}

	/**
	 * Returns a range of all the ddm fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFieldModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm fields
	 * @param end the upper bound of the range of ddm fields (not inclusive)
	 * @return the range of ddm fields
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMField> getDDMFields(
			int start, int end) {

		return getService().getDDMFields(start, end);
	}

	/**
	 * Returns the number of ddm fields.
	 *
	 * @return the number of ddm fields
	 */
	public static int getDDMFieldsCount() {
		return getService().getDDMFieldsCount();
	}

	public static com.liferay.dynamic.data.mapping.storage.DDMFormValues
		getDDMFormValues(
			com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
			long storageId) {

		return getService().getDDMFormValues(ddmForm, storageId);
	}

	public static int getDDMFormValuesCount(long structureId) {
		return getService().getDDMFormValuesCount(structureId);
	}

	public static int getDDMFormValuesCount(
		long companyId, String fieldType,
		java.util.Map<String, Object> attributes) {

		return getService().getDDMFormValuesCount(
			companyId, fieldType, attributes);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the ddm field in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DDMFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ddmField the ddm field
	 * @return the ddm field that was updated
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMField
		updateDDMField(
			com.liferay.dynamic.data.mapping.model.DDMField ddmField) {

		return getService().updateDDMField(ddmField);
	}

	public static void updateDDMFormValues(
			long structureId, long storageId,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateDDMFormValues(structureId, storageId, ddmFormValues);
	}

	public static DDMFieldLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDMFieldLocalService, DDMFieldLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMFieldLocalService.class);

		ServiceTracker<DDMFieldLocalService, DDMFieldLocalService>
			serviceTracker =
				new ServiceTracker<DDMFieldLocalService, DDMFieldLocalService>(
					bundle.getBundleContext(), DDMFieldLocalService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}