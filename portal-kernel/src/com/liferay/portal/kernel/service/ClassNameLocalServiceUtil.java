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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for ClassName. This utility wraps
 * <code>com.liferay.portal.service.impl.ClassNameLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ClassNameLocalService
 * @generated
 */
public class ClassNameLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.ClassNameLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the class name to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClassNameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param className the class name
	 * @return the class name that was added
	 */
	public static com.liferay.portal.kernel.model.ClassName addClassName(
		com.liferay.portal.kernel.model.ClassName className) {

		return getService().addClassName(className);
	}

	public static com.liferay.portal.kernel.model.ClassName addClassName(
		String value) {

		return getService().addClassName(value);
	}

	public static void checkClassNames() {
		getService().checkClassNames();
	}

	/**
	 * Creates a new class name with the primary key. Does not add the class name to the database.
	 *
	 * @param classNameId the primary key for the new class name
	 * @return the new class name
	 */
	public static com.liferay.portal.kernel.model.ClassName createClassName(
		long classNameId) {

		return getService().createClassName(classNameId);
	}

	/**
	 * Deletes the class name from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClassNameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param className the class name
	 * @return the class name that was removed
	 */
	public static com.liferay.portal.kernel.model.ClassName deleteClassName(
		com.liferay.portal.kernel.model.ClassName className) {

		return getService().deleteClassName(className);
	}

	/**
	 * Deletes the class name with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClassNameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name that was removed
	 * @throws PortalException if a class name with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.ClassName deleteClassName(
			long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteClassName(classNameId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ClassNameModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ClassNameModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.ClassName fetchByClassNameId(
		long classNameId) {

		return getService().fetchByClassNameId(classNameId);
	}

	public static com.liferay.portal.kernel.model.ClassName fetchClassName(
		long classNameId) {

		return getService().fetchClassName(classNameId);
	}

	public static com.liferay.portal.kernel.model.ClassName fetchClassName(
		String value) {

		return getService().fetchClassName(value);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the class name with the primary key.
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name
	 * @throws PortalException if a class name with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.ClassName getClassName(
			long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getClassName(classNameId);
	}

	public static com.liferay.portal.kernel.model.ClassName getClassName(
		String value) {

		return getService().getClassName(value);
	}

	public static long getClassNameId(Class<?> clazz) {
		return getService().getClassNameId(clazz);
	}

	public static long getClassNameId(String value) {
		return getService().getClassNameId(value);
	}

	/**
	 * Returns a range of all the class names.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ClassNameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of class names
	 * @param end the upper bound of the range of class names (not inclusive)
	 * @return the range of class names
	 */
	public static java.util.List<com.liferay.portal.kernel.model.ClassName>
		getClassNames(int start, int end) {

		return getService().getClassNames(start, end);
	}

	/**
	 * Returns the number of class names.
	 *
	 * @return the number of class names
	 */
	public static int getClassNamesCount() {
		return getService().getClassNamesCount();
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

	public static String getRegistryName() {
		return getService().getRegistryName();
	}

	public static void invalidate() {
		getService().invalidate();
	}

	/**
	 * Updates the class name in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClassNameLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param className the class name
	 * @return the class name that was updated
	 */
	public static com.liferay.portal.kernel.model.ClassName updateClassName(
		com.liferay.portal.kernel.model.ClassName className) {

		return getService().updateClassName(className);
	}

	public static ClassNameLocalService getService() {
		if (_service == null) {
			_service = (ClassNameLocalService)PortalBeanLocatorUtil.locate(
				ClassNameLocalService.class.getName());
		}

		return _service;
	}

	private static ClassNameLocalService _service;

}