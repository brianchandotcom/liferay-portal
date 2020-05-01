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
 * Provides the local service utility for DDMFormInstanceReport. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceReportLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceReportLocalService
 * @generated
 */
public class DDMFormInstanceReportLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceReportLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the ddm form instance report to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceReport the ddm form instance report
	 * @return the ddm form instance report that was added
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
		addDDMFormInstanceReport(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
				ddmFormInstanceReport) {

		return getService().addDDMFormInstanceReport(ddmFormInstanceReport);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
			addFormInstanceReport(long formInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFormInstanceReport(formInstanceId);
	}

	/**
	 * Creates a new ddm form instance report with the primary key. Does not add the ddm form instance report to the database.
	 *
	 * @param formInstanceReportId the primary key for the new ddm form instance report
	 * @return the new ddm form instance report
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
		createDDMFormInstanceReport(long formInstanceReportId) {

		return getService().createDDMFormInstanceReport(formInstanceReportId);
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
	 * Deletes the ddm form instance report from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceReport the ddm form instance report
	 * @return the ddm form instance report that was removed
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
		deleteDDMFormInstanceReport(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
				ddmFormInstanceReport) {

		return getService().deleteDDMFormInstanceReport(ddmFormInstanceReport);
	}

	/**
	 * Deletes the ddm form instance report with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceReportId the primary key of the ddm form instance report
	 * @return the ddm form instance report that was removed
	 * @throws PortalException if a ddm form instance report with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
			deleteDDMFormInstanceReport(long formInstanceReportId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDDMFormInstanceReport(formInstanceReportId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceReportModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceReportModelImpl</code>.
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

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
		fetchDDMFormInstanceReport(long formInstanceReportId) {

		return getService().fetchDDMFormInstanceReport(formInstanceReportId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the ddm form instance report with the primary key.
	 *
	 * @param formInstanceReportId the primary key of the ddm form instance report
	 * @return the ddm form instance report
	 * @throws PortalException if a ddm form instance report with the primary key could not be found
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
			getDDMFormInstanceReport(long formInstanceReportId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDDMFormInstanceReport(formInstanceReportId);
	}

	/**
	 * Returns a range of all the ddm form instance reports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceReportModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance reports
	 * @param end the upper bound of the range of ddm form instance reports (not inclusive)
	 * @return the range of ddm form instance reports
	 */
	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport>
			getDDMFormInstanceReports(int start, int end) {

		return getService().getDDMFormInstanceReports(start, end);
	}

	/**
	 * Returns the number of ddm form instance reports.
	 *
	 * @return the number of ddm form instance reports
	 */
	public static int getDDMFormInstanceReportsCount() {
		return getService().getDDMFormInstanceReportsCount();
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
	 * Updates the ddm form instance report in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceReport the ddm form instance report
	 * @return the ddm form instance report that was updated
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
		updateDDMFormInstanceReport(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
				ddmFormInstanceReport) {

		return getService().updateDDMFormInstanceReport(ddmFormInstanceReport);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
			updateFormInstanceReport(
				long formInstanceReportId, long formInstanceRecordVersionId,
				String formInstanceReportEvent)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFormInstanceReport(
			formInstanceReportId, formInstanceRecordVersionId,
			formInstanceReportEvent);
	}

	public static DDMFormInstanceReportLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMFormInstanceReportLocalService, DDMFormInstanceReportLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMFormInstanceReportLocalService.class);

		ServiceTracker
			<DDMFormInstanceReportLocalService,
			 DDMFormInstanceReportLocalService> serviceTracker =
				new ServiceTracker
					<DDMFormInstanceReportLocalService,
					 DDMFormInstanceReportLocalService>(
						 bundle.getBundleContext(),
						 DDMFormInstanceReportLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}