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

package com.liferay.portal.workflow.kaleo.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

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

	public KaleoTaskFormLocalServiceWrapper(
		KaleoTaskFormLocalService kaleoTaskFormLocalService) {

		_kaleoTaskFormLocalService = kaleoTaskFormLocalService;
	}

	/**
	 * Adds the kaleo task form to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskForm the kaleo task form
	 * @return the kaleo task form that was added
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskForm
		addKaleoTaskForm(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskForm
				kaleoTaskForm) {

		return _kaleoTaskFormLocalService.addKaleoTaskForm(kaleoTaskForm);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskForm
			addKaleoTaskForm(
				long kaleoDefinitionVersionId, long kaleoNodeId,
				com.liferay.portal.workflow.kaleo.model.KaleoTask kaleoTask,
				com.liferay.portal.workflow.kaleo.definition.TaskForm taskForm,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormLocalService.addKaleoTaskForm(
			kaleoDefinitionVersionId, kaleoNodeId, kaleoTask, taskForm,
			serviceContext);
	}

	/**
	 * Creates a new kaleo task form with the primary key. Does not add the kaleo task form to the database.
	 *
	 * @param kaleoTaskFormId the primary key for the new kaleo task form
	 * @return the new kaleo task form
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskForm
		createKaleoTaskForm(long kaleoTaskFormId) {

		return _kaleoTaskFormLocalService.createKaleoTaskForm(kaleoTaskFormId);
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
	 * @param kaleoTaskForm the kaleo task form
	 * @return the kaleo task form that was removed
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskForm
		deleteKaleoTaskForm(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskForm
				kaleoTaskForm) {

		return _kaleoTaskFormLocalService.deleteKaleoTaskForm(kaleoTaskForm);
	}

	/**
	 * Deletes the kaleo task form with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskFormId the primary key of the kaleo task form
	 * @return the kaleo task form that was removed
	 * @throws PortalException if a kaleo task form with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskForm
			deleteKaleoTaskForm(long kaleoTaskFormId)
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
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskForm
		fetchKaleoTaskForm(long kaleoTaskFormId) {

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
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskForm
			getKaleoTaskForm(long kaleoTaskFormId)
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
	public java.util.List<com.liferay.portal.workflow.kaleo.model.KaleoTaskForm>
		getKaleoTaskForms(int start, int end) {

		return _kaleoTaskFormLocalService.getKaleoTaskForms(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.model.KaleoTaskForm>
			getKaleoTaskForms(long kaleoTaskId)
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

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kaleoTaskFormLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the kaleo task form in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskForm the kaleo task form
	 * @return the kaleo task form that was updated
	 */
	@Override
	public com.liferay.portal.workflow.kaleo.model.KaleoTaskForm
		updateKaleoTaskForm(
			com.liferay.portal.workflow.kaleo.model.KaleoTaskForm
				kaleoTaskForm) {

		return _kaleoTaskFormLocalService.updateKaleoTaskForm(kaleoTaskForm);
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