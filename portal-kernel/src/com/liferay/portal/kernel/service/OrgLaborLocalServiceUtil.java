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
 * Provides the local service utility for OrgLabor. This utility wraps
 * <code>com.liferay.portal.service.impl.OrgLaborLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see OrgLaborLocalService
 * @generated
 */
public class OrgLaborLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.OrgLaborLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.OrgLabor addOrgLabor(
			long organizationId, long typeId, int sunOpen, int sunClose,
			int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen,
			int wedClose, int thuOpen, int thuClose, int friOpen, int friClose,
			int satOpen, int satClose)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addOrgLabor(
			organizationId, typeId, sunOpen, sunClose, monOpen, monClose,
			tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose, friOpen,
			friClose, satOpen, satClose);
	}

	/**
	 * Adds the org labor to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OrgLaborLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param orgLabor the org labor
	 * @return the org labor that was added
	 */
	public static com.liferay.portal.kernel.model.OrgLabor addOrgLabor(
		com.liferay.portal.kernel.model.OrgLabor orgLabor) {

		return getService().addOrgLabor(orgLabor);
	}

	/**
	 * Creates a new org labor with the primary key. Does not add the org labor to the database.
	 *
	 * @param orgLaborId the primary key for the new org labor
	 * @return the new org labor
	 */
	public static com.liferay.portal.kernel.model.OrgLabor createOrgLabor(
		long orgLaborId) {

		return getService().createOrgLabor(orgLaborId);
	}

	/**
	 * Deletes the org labor with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OrgLaborLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param orgLaborId the primary key of the org labor
	 * @return the org labor that was removed
	 * @throws PortalException if a org labor with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.OrgLabor deleteOrgLabor(
			long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteOrgLabor(orgLaborId);
	}

	/**
	 * Deletes the org labor from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OrgLaborLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param orgLabor the org labor
	 * @return the org labor that was removed
	 */
	public static com.liferay.portal.kernel.model.OrgLabor deleteOrgLabor(
		com.liferay.portal.kernel.model.OrgLabor orgLabor) {

		return getService().deleteOrgLabor(orgLabor);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.OrgLaborModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.OrgLaborModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.OrgLabor fetchOrgLabor(
		long orgLaborId) {

		return getService().fetchOrgLabor(orgLaborId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the org labor with the primary key.
	 *
	 * @param orgLaborId the primary key of the org labor
	 * @return the org labor
	 * @throws PortalException if a org labor with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.OrgLabor getOrgLabor(
			long orgLaborId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getOrgLabor(orgLaborId);
	}

	/**
	 * Returns a range of all the org labors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.OrgLaborModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of org labors
	 * @param end the upper bound of the range of org labors (not inclusive)
	 * @return the range of org labors
	 */
	public static java.util.List<com.liferay.portal.kernel.model.OrgLabor>
		getOrgLabors(int start, int end) {

		return getService().getOrgLabors(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.OrgLabor>
		getOrgLabors(long organizationId) {

		return getService().getOrgLabors(organizationId);
	}

	/**
	 * Returns the number of org labors.
	 *
	 * @return the number of org labors
	 */
	public static int getOrgLaborsCount() {
		return getService().getOrgLaborsCount();
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

	public static com.liferay.portal.kernel.model.OrgLabor updateOrgLabor(
			long orgLaborId, long typeId, int sunOpen, int sunClose,
			int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen,
			int wedClose, int thuOpen, int thuClose, int friOpen, int friClose,
			int satOpen, int satClose)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateOrgLabor(
			orgLaborId, typeId, sunOpen, sunClose, monOpen, monClose, tueOpen,
			tueClose, wedOpen, wedClose, thuOpen, thuClose, friOpen, friClose,
			satOpen, satClose);
	}

	/**
	 * Updates the org labor in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OrgLaborLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param orgLabor the org labor
	 * @return the org labor that was updated
	 */
	public static com.liferay.portal.kernel.model.OrgLabor updateOrgLabor(
		com.liferay.portal.kernel.model.OrgLabor orgLabor) {

		return getService().updateOrgLabor(orgLabor);
	}

	public static OrgLaborLocalService getService() {
		if (_service == null) {
			_service = (OrgLaborLocalService)PortalBeanLocatorUtil.locate(
				OrgLaborLocalService.class.getName());
		}

		return _service;
	}

	private static OrgLaborLocalService _service;

}