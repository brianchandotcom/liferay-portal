/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoCondition;

/**
 * Provides a wrapper for {@link KaleoConditionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoConditionLocalService
 * @generated
 */
public class KaleoConditionLocalServiceWrapper
	implements KaleoConditionLocalService,
			   ServiceWrapper<KaleoConditionLocalService> {

	public KaleoConditionLocalServiceWrapper() {
		this(null);
	}

	public KaleoConditionLocalServiceWrapper(
		KaleoConditionLocalService kaleoConditionLocalService) {

		_kaleoConditionLocalService = kaleoConditionLocalService;
	}

	/**
	 * Adds the kaleo condition to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoConditionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoCondition the kaleo condition
	 * @return the kaleo condition that was added
	 */
	@Override
	public KaleoCondition addKaleoCondition(KaleoCondition kaleoCondition) {
		return _kaleoConditionLocalService.addKaleoCondition(kaleoCondition);
	}

	@Override
	public KaleoCondition addKaleoCondition(
			long kaleoDefinitionId, long kaleoDefinitionVersionId,
			long kaleoNodeId,
			com.liferay.portal.workflow.kaleo.definition.Condition condition,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoConditionLocalService.addKaleoCondition(
			kaleoDefinitionId, kaleoDefinitionVersionId, kaleoNodeId, condition,
			serviceContext);
	}

	/**
	 * Creates a new kaleo condition with the primary key. Does not add the kaleo condition to the database.
	 *
	 * @param kaleoConditionId the primary key for the new kaleo condition
	 * @return the new kaleo condition
	 */
	@Override
	public KaleoCondition createKaleoCondition(long kaleoConditionId) {
		return _kaleoConditionLocalService.createKaleoCondition(
			kaleoConditionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoConditionLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteCompanyKaleoConditions(long companyId) {
		_kaleoConditionLocalService.deleteCompanyKaleoConditions(companyId);
	}

	/**
	 * Deletes the kaleo condition from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoConditionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoCondition the kaleo condition
	 * @return the kaleo condition that was removed
	 */
	@Override
	public KaleoCondition deleteKaleoCondition(KaleoCondition kaleoCondition) {
		return _kaleoConditionLocalService.deleteKaleoCondition(kaleoCondition);
	}

	/**
	 * Deletes the kaleo condition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoConditionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoConditionId the primary key of the kaleo condition
	 * @return the kaleo condition that was removed
	 * @throws PortalException if a kaleo condition with the primary key could not be found
	 */
	@Override
	public KaleoCondition deleteKaleoCondition(long kaleoConditionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoConditionLocalService.deleteKaleoCondition(
			kaleoConditionId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoCondition(
		long kaleoDefinitionVersionId) {

		_kaleoConditionLocalService.deleteKaleoDefinitionVersionKaleoCondition(
			kaleoDefinitionVersionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoConditionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _kaleoConditionLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _kaleoConditionLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoConditionLocalService.dynamicQuery();
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

		return _kaleoConditionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
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

		return _kaleoConditionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
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

		return _kaleoConditionLocalService.dynamicQuery(
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

		return _kaleoConditionLocalService.dynamicQueryCount(dynamicQuery);
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

		return _kaleoConditionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public KaleoCondition fetchKaleoCondition(long kaleoConditionId) {
		return _kaleoConditionLocalService.fetchKaleoCondition(
			kaleoConditionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoConditionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoConditionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo condition with the primary key.
	 *
	 * @param kaleoConditionId the primary key of the kaleo condition
	 * @return the kaleo condition
	 * @throws PortalException if a kaleo condition with the primary key could not be found
	 */
	@Override
	public KaleoCondition getKaleoCondition(long kaleoConditionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoConditionLocalService.getKaleoCondition(kaleoConditionId);
	}

	/**
	 * Returns a range of all the kaleo conditions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoConditionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo conditions
	 * @param end the upper bound of the range of kaleo conditions (not inclusive)
	 * @return the range of kaleo conditions
	 */
	@Override
	public java.util.List<KaleoCondition> getKaleoConditions(
		int start, int end) {

		return _kaleoConditionLocalService.getKaleoConditions(start, end);
	}

	/**
	 * Returns the number of kaleo conditions.
	 *
	 * @return the number of kaleo conditions
	 */
	@Override
	public int getKaleoConditionsCount() {
		return _kaleoConditionLocalService.getKaleoConditionsCount();
	}

	@Override
	public KaleoCondition getKaleoNodeKaleoCondition(long kaleoNodeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoConditionLocalService.getKaleoNodeKaleoCondition(
			kaleoNodeId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoConditionLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoConditionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the kaleo condition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoConditionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoCondition the kaleo condition
	 * @return the kaleo condition that was updated
	 */
	@Override
	public KaleoCondition updateKaleoCondition(KaleoCondition kaleoCondition) {
		return _kaleoConditionLocalService.updateKaleoCondition(kaleoCondition);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoConditionLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<KaleoCondition> getCTPersistence() {
		return _kaleoConditionLocalService.getCTPersistence();
	}

	@Override
	public Class<KaleoCondition> getModelClass() {
		return _kaleoConditionLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<KaleoCondition>, R, E>
				updateUnsafeFunction)
		throws E {

		return _kaleoConditionLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public KaleoConditionLocalService getWrappedService() {
		return _kaleoConditionLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoConditionLocalService kaleoConditionLocalService) {

		_kaleoConditionLocalService = kaleoConditionLocalService;
	}

	private KaleoConditionLocalService _kaleoConditionLocalService;

}