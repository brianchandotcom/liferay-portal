/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;

/**
 * Provides a wrapper for {@link KaleoTaskFormLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskFormLocalService
 * @generated
 */
public class KaleoTaskFormLocalServiceWrapper
	implements KaleoTaskFormLocalService,
			   ServiceWrapper<KaleoTaskFormLocalService> {

	public KaleoTaskFormLocalServiceWrapper() {
		this(null);
	}

	public KaleoTaskFormLocalServiceWrapper(
		KaleoTaskFormLocalService kaleoTaskFormLocalService) {

		_kaleoTaskFormLocalService = kaleoTaskFormLocalService;
	}

	/**
	 * Adds the kaleo task form to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoTaskFormLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoTaskForm the kaleo task form
	 * @return the kaleo task form that was added
	 */
	@Override
	public KaleoTaskForm addKaleoTaskForm(KaleoTaskForm kaleoTaskForm) {
		return _kaleoTaskFormLocalService.addKaleoTaskForm(kaleoTaskForm);
	}

	@Override
	public KaleoTaskForm addKaleoTaskForm(
			long kaleoDefinitionId, long kaleoDefinitionVersionId,
			long kaleoNodeId,
			com.liferay.portal.workflow.kaleo.model.KaleoTask kaleoTask,
			com.liferay.portal.workflow.kaleo.definition.TaskForm taskForm,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormLocalService.addKaleoTaskForm(
			kaleoDefinitionId, kaleoDefinitionVersionId, kaleoNodeId, kaleoTask,
			taskForm, serviceContext);
	}

	/**
	 * Creates a new kaleo task form with the primary key. Does not add the kaleo task form to the database.
	 *
	 * @param kaleoTaskFormId the primary key for the new kaleo task form
	 * @return the new kaleo task form
	 */
	@Override
	public KaleoTaskForm createKaleoTaskForm(long kaleoTaskFormId) {
		return _kaleoTaskFormLocalService.createKaleoTaskForm(kaleoTaskFormId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteCompanyKaleoTaskForms(long companyId) {
		_kaleoTaskFormLocalService.deleteCompanyKaleoTaskForms(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoTaskForms(
		long kaleoDefinitionVersionId) {

		_kaleoTaskFormLocalService.deleteKaleoDefinitionVersionKaleoTaskForms(
			kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo task form from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoTaskFormLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoTaskForm the kaleo task form
	 * @return the kaleo task form that was removed
	 */
	@Override
	public KaleoTaskForm deleteKaleoTaskForm(KaleoTaskForm kaleoTaskForm) {
		return _kaleoTaskFormLocalService.deleteKaleoTaskForm(kaleoTaskForm);
	}

	/**
	 * Deletes the kaleo task form with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoTaskFormLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoTaskFormId the primary key of the kaleo task form
	 * @return the kaleo task form that was removed
	 * @throws PortalException if a kaleo task form with the primary key could not be found
	 */
	@Override
	public KaleoTaskForm deleteKaleoTaskForm(long kaleoTaskFormId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormLocalService.deleteKaleoTaskForm(kaleoTaskFormId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _kaleoTaskFormLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _kaleoTaskFormLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoTaskFormLocalService.dynamicQuery();
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

		return _kaleoTaskFormLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormModelImpl</code>.
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

		return _kaleoTaskFormLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormModelImpl</code>.
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

		return _kaleoTaskFormLocalService.dynamicQuery(
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

		return _kaleoTaskFormLocalService.dynamicQueryCount(dynamicQuery);
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

		return _kaleoTaskFormLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public KaleoTaskForm fetchKaleoTaskForm(long kaleoTaskFormId) {
		return _kaleoTaskFormLocalService.fetchKaleoTaskForm(kaleoTaskFormId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kaleoTaskFormLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kaleoTaskFormLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo task form with the primary key.
	 *
	 * @param kaleoTaskFormId the primary key of the kaleo task form
	 * @return the kaleo task form
	 * @throws PortalException if a kaleo task form with the primary key could not be found
	 */
	@Override
	public KaleoTaskForm getKaleoTaskForm(long kaleoTaskFormId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormLocalService.getKaleoTaskForm(kaleoTaskFormId);
	}

	/**
	 * Returns a range of all the kaleo task forms.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task forms
	 * @param end the upper bound of the range of kaleo task forms (not inclusive)
	 * @return the range of kaleo task forms
	 */
	@Override
	public java.util.List<KaleoTaskForm> getKaleoTaskForms(int start, int end) {
		return _kaleoTaskFormLocalService.getKaleoTaskForms(start, end);
	}

	@Override
	public java.util.List<KaleoTaskForm> getKaleoTaskForms(long kaleoTaskId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormLocalService.getKaleoTaskForms(kaleoTaskId);
	}

	/**
	 * Returns the number of kaleo task forms.
	 *
	 * @return the number of kaleo task forms
	 */
	@Override
	public int getKaleoTaskFormsCount() {
		return _kaleoTaskFormLocalService.getKaleoTaskFormsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kaleoTaskFormLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the kaleo task form in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KaleoTaskFormLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param kaleoTaskForm the kaleo task form
	 * @return the kaleo task form that was updated
	 */
	@Override
	public KaleoTaskForm updateKaleoTaskForm(KaleoTaskForm kaleoTaskForm) {
		return _kaleoTaskFormLocalService.updateKaleoTaskForm(kaleoTaskForm);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoTaskFormLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<KaleoTaskForm> getCTPersistence() {
		return _kaleoTaskFormLocalService.getCTPersistence();
	}

	@Override
	public Class<KaleoTaskForm> getModelClass() {
		return _kaleoTaskFormLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<KaleoTaskForm>, R, E>
				updateUnsafeFunction)
		throws E {

		return _kaleoTaskFormLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public KaleoTaskFormLocalService getWrappedService() {
		return _kaleoTaskFormLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoTaskFormLocalService kaleoTaskFormLocalService) {

		_kaleoTaskFormLocalService = kaleoTaskFormLocalService;
	}

	private KaleoTaskFormLocalService _kaleoTaskFormLocalService;

}