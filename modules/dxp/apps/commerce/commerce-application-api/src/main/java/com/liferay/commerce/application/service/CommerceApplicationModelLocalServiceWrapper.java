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

package com.liferay.commerce.application.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceApplicationModelLocalService}.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModelLocalService
 * @generated
 */
public class CommerceApplicationModelLocalServiceWrapper
	implements CommerceApplicationModelLocalService,
			   ServiceWrapper<CommerceApplicationModelLocalService> {

	public CommerceApplicationModelLocalServiceWrapper(
		CommerceApplicationModelLocalService
			commerceApplicationModelLocalService) {

		_commerceApplicationModelLocalService =
			commerceApplicationModelLocalService;
	}

	/**
	 * Adds the commerce application model to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationModelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationModel the commerce application model
	 * @return the commerce application model that was added
	 */
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationModel
		addCommerceApplicationModel(
			com.liferay.commerce.application.model.CommerceApplicationModel
				commerceApplicationModel) {

		return _commerceApplicationModelLocalService.
			addCommerceApplicationModel(commerceApplicationModel);
	}

	@Override
	public com.liferay.commerce.application.model.CommerceApplicationModel
			addCommerceApplicationModel(
				long userId, long commerceApplicationBrandId, String name,
				String year)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationModelLocalService.
			addCommerceApplicationModel(
				userId, commerceApplicationBrandId, name, year);
	}

	/**
	 * Creates a new commerce application model with the primary key. Does not add the commerce application model to the database.
	 *
	 * @param commerceApplicationModelId the primary key for the new commerce application model
	 * @return the new commerce application model
	 */
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationModel
		createCommerceApplicationModel(long commerceApplicationModelId) {

		return _commerceApplicationModelLocalService.
			createCommerceApplicationModel(commerceApplicationModelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationModelLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the commerce application model from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationModelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationModel the commerce application model
	 * @return the commerce application model that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationModel
			deleteCommerceApplicationModel(
				com.liferay.commerce.application.model.CommerceApplicationModel
					commerceApplicationModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationModelLocalService.
			deleteCommerceApplicationModel(commerceApplicationModel);
	}

	/**
	 * Deletes the commerce application model with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationModelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationModelId the primary key of the commerce application model
	 * @return the commerce application model that was removed
	 * @throws PortalException if a commerce application model with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationModel
			deleteCommerceApplicationModel(long commerceApplicationModelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationModelLocalService.
			deleteCommerceApplicationModel(commerceApplicationModelId);
	}

	@Override
	public void deleteCommerceApplicationModels(long commerceApplicationBrandId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceApplicationModelLocalService.deleteCommerceApplicationModels(
			commerceApplicationBrandId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationModelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceApplicationModelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceApplicationModelLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceApplicationModelLocalService.dynamicQuery();
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

		return _commerceApplicationModelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.application.model.impl.CommerceApplicationModelModelImpl</code>.
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

		return _commerceApplicationModelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.application.model.impl.CommerceApplicationModelModelImpl</code>.
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

		return _commerceApplicationModelLocalService.dynamicQuery(
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

		return _commerceApplicationModelLocalService.dynamicQueryCount(
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

		return _commerceApplicationModelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.application.model.CommerceApplicationModel
		fetchCommerceApplicationModel(long commerceApplicationModelId) {

		return _commerceApplicationModelLocalService.
			fetchCommerceApplicationModel(commerceApplicationModelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceApplicationModelLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce application model with the primary key.
	 *
	 * @param commerceApplicationModelId the primary key of the commerce application model
	 * @return the commerce application model
	 * @throws PortalException if a commerce application model with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationModel
			getCommerceApplicationModel(long commerceApplicationModelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationModelLocalService.
			getCommerceApplicationModel(commerceApplicationModelId);
	}

	/**
	 * Returns a range of all the commerce application models.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.application.model.impl.CommerceApplicationModelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce application models
	 * @param end the upper bound of the range of commerce application models (not inclusive)
	 * @return the range of commerce application models
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.application.model.CommerceApplicationModel>
			getCommerceApplicationModels(int start, int end) {

		return _commerceApplicationModelLocalService.
			getCommerceApplicationModels(start, end);
	}

	/**
	 * Returns the number of commerce application models.
	 *
	 * @return the number of commerce application models
	 */
	@Override
	public int getCommerceApplicationModelsCount() {
		return _commerceApplicationModelLocalService.
			getCommerceApplicationModelsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceApplicationModelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceApplicationModelLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationModelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the commerce application model in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceApplicationModelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceApplicationModel the commerce application model
	 * @return the commerce application model that was updated
	 */
	@Override
	public com.liferay.commerce.application.model.CommerceApplicationModel
		updateCommerceApplicationModel(
			com.liferay.commerce.application.model.CommerceApplicationModel
				commerceApplicationModel) {

		return _commerceApplicationModelLocalService.
			updateCommerceApplicationModel(commerceApplicationModel);
	}

	@Override
	public com.liferay.commerce.application.model.CommerceApplicationModel
			updateCommerceApplicationModel(
				long commerceApplicationModelId, String name, String year)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceApplicationModelLocalService.
			updateCommerceApplicationModel(
				commerceApplicationModelId, name, year);
	}

	@Override
	public CommerceApplicationModelLocalService getWrappedService() {
		return _commerceApplicationModelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceApplicationModelLocalService
			commerceApplicationModelLocalService) {

		_commerceApplicationModelLocalService =
			commerceApplicationModelLocalService;
	}

	private CommerceApplicationModelLocalService
		_commerceApplicationModelLocalService;

}