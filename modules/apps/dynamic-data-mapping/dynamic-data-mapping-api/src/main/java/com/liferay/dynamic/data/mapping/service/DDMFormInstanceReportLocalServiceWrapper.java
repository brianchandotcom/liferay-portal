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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMFormInstanceReportLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceReportLocalService
 * @generated
 */
public class DDMFormInstanceReportLocalServiceWrapper
	implements DDMFormInstanceReportLocalService,
			   ServiceWrapper<DDMFormInstanceReportLocalService> {

	public DDMFormInstanceReportLocalServiceWrapper(
		DDMFormInstanceReportLocalService ddmFormInstanceReportLocalService) {

		_ddmFormInstanceReportLocalService = ddmFormInstanceReportLocalService;
	}

	/**
	 * Adds the ddm form instance report to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceReport the ddm form instance report
	 * @return the ddm form instance report that was added
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
		addDDMFormInstanceReport(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
				ddmFormInstanceReport) {

		return _ddmFormInstanceReportLocalService.addDDMFormInstanceReport(
			ddmFormInstanceReport);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
			addFormInstanceReport(long formInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceReportLocalService.addFormInstanceReport(
			formInstanceId);
	}

	/**
	 * Creates a new ddm form instance report with the primary key. Does not add the ddm form instance report to the database.
	 *
	 * @param formInstanceReportId the primary key for the new ddm form instance report
	 * @return the new ddm form instance report
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
		createDDMFormInstanceReport(long formInstanceReportId) {

		return _ddmFormInstanceReportLocalService.createDDMFormInstanceReport(
			formInstanceReportId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceReportLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the ddm form instance report from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceReport the ddm form instance report
	 * @return the ddm form instance report that was removed
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
		deleteDDMFormInstanceReport(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
				ddmFormInstanceReport) {

		return _ddmFormInstanceReportLocalService.deleteDDMFormInstanceReport(
			ddmFormInstanceReport);
	}

	/**
	 * Deletes the ddm form instance report with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceReportId the primary key of the ddm form instance report
	 * @return the ddm form instance report that was removed
	 * @throws PortalException if a ddm form instance report with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
			deleteDDMFormInstanceReport(long formInstanceReportId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceReportLocalService.deleteDDMFormInstanceReport(
			formInstanceReportId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceReportLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _ddmFormInstanceReportLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmFormInstanceReportLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _ddmFormInstanceReportLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _ddmFormInstanceReportLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _ddmFormInstanceReportLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _ddmFormInstanceReportLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _ddmFormInstanceReportLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
		fetchDDMFormInstanceReport(long formInstanceReportId) {

		return _ddmFormInstanceReportLocalService.fetchDDMFormInstanceReport(
			formInstanceReportId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ddmFormInstanceReportLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the ddm form instance report with the primary key.
	 *
	 * @param formInstanceReportId the primary key of the ddm form instance report
	 * @return the ddm form instance report
	 * @throws PortalException if a ddm form instance report with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
			getDDMFormInstanceReport(long formInstanceReportId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceReportLocalService.getDDMFormInstanceReport(
			formInstanceReportId);
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
	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport>
			getDDMFormInstanceReports(int start, int end) {

		return _ddmFormInstanceReportLocalService.getDDMFormInstanceReports(
			start, end);
	}

	/**
	 * Returns the number of ddm form instance reports.
	 *
	 * @return the number of ddm form instance reports
	 */
	@Override
	public int getDDMFormInstanceReportsCount() {
		return _ddmFormInstanceReportLocalService.
			getDDMFormInstanceReportsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ddmFormInstanceReportLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddmFormInstanceReportLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceReportLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the ddm form instance report in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstanceReport the ddm form instance report
	 * @return the ddm form instance report that was updated
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
		updateDDMFormInstanceReport(
			com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
				ddmFormInstanceReport) {

		return _ddmFormInstanceReportLocalService.updateDDMFormInstanceReport(
			ddmFormInstanceReport);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport
			updateFormInstanceReport(
				long formInstanceReportId, long formInstanceRecordVersionId,
				String formInstanceReportEvent)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceReportLocalService.updateFormInstanceReport(
			formInstanceReportId, formInstanceRecordVersionId,
			formInstanceReportEvent);
	}

	@Override
	public DDMFormInstanceReportLocalService getWrappedService() {
		return _ddmFormInstanceReportLocalService;
	}

	@Override
	public void setWrappedService(
		DDMFormInstanceReportLocalService ddmFormInstanceReportLocalService) {

		_ddmFormInstanceReportLocalService = ddmFormInstanceReportLocalService;
	}

	private DDMFormInstanceReportLocalService
		_ddmFormInstanceReportLocalService;

}