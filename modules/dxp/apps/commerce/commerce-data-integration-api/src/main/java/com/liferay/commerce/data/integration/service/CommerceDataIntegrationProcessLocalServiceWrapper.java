/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceDataIntegrationProcessLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceDataIntegrationProcessLocalService
 * @generated
 */
public class CommerceDataIntegrationProcessLocalServiceWrapper
	implements CommerceDataIntegrationProcessLocalService,
			   ServiceWrapper<CommerceDataIntegrationProcessLocalService> {

	public CommerceDataIntegrationProcessLocalServiceWrapper(
		CommerceDataIntegrationProcessLocalService
			commerceDataIntegrationProcessLocalService) {

		_commerceDataIntegrationProcessLocalService =
			commerceDataIntegrationProcessLocalService;
	}

	/**
	 * Adds the commerce data integration process to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDataIntegrationProcessLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDataIntegrationProcess the commerce data integration process
	 * @return the commerce data integration process that was added
	 */
	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess addCommerceDataIntegrationProcess(
				com.liferay.commerce.data.integration.model.
					CommerceDataIntegrationProcess
						commerceDataIntegrationProcess) {

		return _commerceDataIntegrationProcessLocalService.
			addCommerceDataIntegrationProcess(commerceDataIntegrationProcess);
	}

	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess addCommerceDataIntegrationProcess(
					long userId, String name, String type,
					com.liferay.portal.kernel.util.UnicodeProperties
						typeSettingsUnicodeProperties,
					boolean system)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLocalService.
			addCommerceDataIntegrationProcess(
				userId, name, type, typeSettingsUnicodeProperties, system);
	}

	/**
	 * Creates a new commerce data integration process with the primary key. Does not add the commerce data integration process to the database.
	 *
	 * @param commerceDataIntegrationProcessId the primary key for the new commerce data integration process
	 * @return the new commerce data integration process
	 */
	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess createCommerceDataIntegrationProcess(
				long commerceDataIntegrationProcessId) {

		return _commerceDataIntegrationProcessLocalService.
			createCommerceDataIntegrationProcess(
				commerceDataIntegrationProcessId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the commerce data integration process from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDataIntegrationProcessLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDataIntegrationProcess the commerce data integration process
	 * @return the commerce data integration process that was removed
	 * @throws PortalException
	 */
	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess deleteCommerceDataIntegrationProcess(
					com.liferay.commerce.data.integration.model.
						CommerceDataIntegrationProcess
							commerceDataIntegrationProcess)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLocalService.
			deleteCommerceDataIntegrationProcess(
				commerceDataIntegrationProcess);
	}

	/**
	 * Deletes the commerce data integration process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDataIntegrationProcessLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the commerce data integration process
	 * @return the commerce data integration process that was removed
	 * @throws PortalException if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess deleteCommerceDataIntegrationProcess(
					long commerceDataIntegrationProcessId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLocalService.
			deleteCommerceDataIntegrationProcess(
				commerceDataIntegrationProcessId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceDataIntegrationProcessLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceDataIntegrationProcessLocalService.dynamicQuery();
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

		return _commerceDataIntegrationProcessLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessModelImpl</code>.
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

		return _commerceDataIntegrationProcessLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessModelImpl</code>.
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

		return _commerceDataIntegrationProcessLocalService.dynamicQuery(
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

		return _commerceDataIntegrationProcessLocalService.dynamicQueryCount(
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

		return _commerceDataIntegrationProcessLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess fetchCommerceDataIntegrationProcess(
				long commerceDataIntegrationProcessId) {

		return _commerceDataIntegrationProcessLocalService.
			fetchCommerceDataIntegrationProcess(
				commerceDataIntegrationProcessId);
	}

	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess fetchCommerceDataIntegrationProcess(
				long companyId, String name) {

		return _commerceDataIntegrationProcessLocalService.
			fetchCommerceDataIntegrationProcess(companyId, name);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceDataIntegrationProcessLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce data integration process with the primary key.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the commerce data integration process
	 * @return the commerce data integration process
	 * @throws PortalException if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess getCommerceDataIntegrationProcess(
					long commerceDataIntegrationProcessId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLocalService.
			getCommerceDataIntegrationProcess(commerceDataIntegrationProcessId);
	}

	/**
	 * Returns a range of all the commerce data integration processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of commerce data integration processes
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess> getCommerceDataIntegrationProcesses(
				int start, int end) {

		return _commerceDataIntegrationProcessLocalService.
			getCommerceDataIntegrationProcesses(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess> getCommerceDataIntegrationProcesses(
				long companyId, int start, int end) {

		return _commerceDataIntegrationProcessLocalService.
			getCommerceDataIntegrationProcesses(companyId, start, end);
	}

	/**
	 * Returns the number of commerce data integration processes.
	 *
	 * @return the number of commerce data integration processes
	 */
	@Override
	public int getCommerceDataIntegrationProcessesCount() {
		return _commerceDataIntegrationProcessLocalService.
			getCommerceDataIntegrationProcessesCount();
	}

	@Override
	public int getCommerceDataIntegrationProcessesCount(long companyId) {
		return _commerceDataIntegrationProcessLocalService.
			getCommerceDataIntegrationProcessesCount(companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceDataIntegrationProcessLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDataIntegrationProcessLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the commerce data integration process in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDataIntegrationProcessLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDataIntegrationProcess the commerce data integration process
	 * @return the commerce data integration process that was updated
	 */
	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess updateCommerceDataIntegrationProcess(
				com.liferay.commerce.data.integration.model.
					CommerceDataIntegrationProcess
						commerceDataIntegrationProcess) {

		return _commerceDataIntegrationProcessLocalService.
			updateCommerceDataIntegrationProcess(
				commerceDataIntegrationProcess);
	}

	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess updateCommerceDataIntegrationProcess(
					long commerceDataIntegrationProcessId, String name,
					com.liferay.portal.kernel.util.UnicodeProperties
						typeSettingsUnicodeProperties)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLocalService.
			updateCommerceDataIntegrationProcess(
				commerceDataIntegrationProcessId, name,
				typeSettingsUnicodeProperties);
	}

	@Override
	public
		com.liferay.commerce.data.integration.model.
			CommerceDataIntegrationProcess
					updateCommerceDataIntegrationProcessTrigger(
						long commerceDataIntegrationProcessId, boolean active,
						String cronExpression, int startDateMonth,
						int startDateDay, int startDateYear, int startDateHour,
						int startDateMinute, int endDateMonth, int endDateDay,
						int endDateYear, int endDateHour, int endDateMinute,
						boolean neverEnd)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDataIntegrationProcessLocalService.
			updateCommerceDataIntegrationProcessTrigger(
				commerceDataIntegrationProcessId, active, cronExpression,
				startDateMonth, startDateDay, startDateYear, startDateHour,
				startDateMinute, endDateMonth, endDateDay, endDateYear,
				endDateHour, endDateMinute, neverEnd);
	}

	@Override
	public CommerceDataIntegrationProcessLocalService getWrappedService() {
		return _commerceDataIntegrationProcessLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceDataIntegrationProcessLocalService
			commerceDataIntegrationProcessLocalService) {

		_commerceDataIntegrationProcessLocalService =
			commerceDataIntegrationProcessLocalService;
	}

	private CommerceDataIntegrationProcessLocalService
		_commerceDataIntegrationProcessLocalService;

}