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

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionDiagramPinLocalService}.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramPinLocalService
 * @generated
 */
public class CPDefinitionDiagramPinLocalServiceWrapper
	implements CPDefinitionDiagramPinLocalService,
			   ServiceWrapper<CPDefinitionDiagramPinLocalService> {

	public CPDefinitionDiagramPinLocalServiceWrapper(
		CPDefinitionDiagramPinLocalService cpDefinitionDiagramPinLocalService) {

		_cpDefinitionDiagramPinLocalService =
			cpDefinitionDiagramPinLocalService;
	}

	/**
	 * Adds the cp definition diagram pin to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramPinLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramPin the cp definition diagram pin
	 * @return the cp definition diagram pin that was added
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
		addCPDefinitionDiagramPin(
			com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
				cpDefinitionDiagramPin) {

		return _cpDefinitionDiagramPinLocalService.addCPDefinitionDiagramPin(
			cpDefinitionDiagramPin);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
			addCPDefinitionDiagramPin(
				long userId, long cpDefinitionId, int number, double positionX,
				double positionY)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramPinLocalService.addCPDefinitionDiagramPin(
			userId, cpDefinitionId, number, positionX, positionY);
	}

	/**
	 * Creates a new cp definition diagram pin with the primary key. Does not add the cp definition diagram pin to the database.
	 *
	 * @param CPDefinitionDiagramPinId the primary key for the new cp definition diagram pin
	 * @return the new cp definition diagram pin
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
		createCPDefinitionDiagramPin(long CPDefinitionDiagramPinId) {

		return _cpDefinitionDiagramPinLocalService.createCPDefinitionDiagramPin(
			CPDefinitionDiagramPinId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramPinLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the cp definition diagram pin from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramPinLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramPin the cp definition diagram pin
	 * @return the cp definition diagram pin that was removed
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
		deleteCPDefinitionDiagramPin(
			com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
				cpDefinitionDiagramPin) {

		return _cpDefinitionDiagramPinLocalService.deleteCPDefinitionDiagramPin(
			cpDefinitionDiagramPin);
	}

	/**
	 * Deletes the cp definition diagram pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramPinLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin that was removed
	 * @throws PortalException if a cp definition diagram pin with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
			deleteCPDefinitionDiagramPin(long CPDefinitionDiagramPinId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramPinLocalService.deleteCPDefinitionDiagramPin(
			CPDefinitionDiagramPinId);
	}

	@Override
	public void deleteCPDefinitionDiagramPins(long cpDefinitionId) {
		_cpDefinitionDiagramPinLocalService.deleteCPDefinitionDiagramPins(
			cpDefinitionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramPinLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cpDefinitionDiagramPinLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _cpDefinitionDiagramPinLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpDefinitionDiagramPinLocalService.dynamicQuery();
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

		return _cpDefinitionDiagramPinLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramPinModelImpl</code>.
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

		return _cpDefinitionDiagramPinLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramPinModelImpl</code>.
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

		return _cpDefinitionDiagramPinLocalService.dynamicQuery(
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

		return _cpDefinitionDiagramPinLocalService.dynamicQueryCount(
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

		return _cpDefinitionDiagramPinLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
		fetchCPDefinitionDiagramPin(long CPDefinitionDiagramPinId) {

		return _cpDefinitionDiagramPinLocalService.fetchCPDefinitionDiagramPin(
			CPDefinitionDiagramPinId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cpDefinitionDiagramPinLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the cp definition diagram pin with the primary key.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin
	 * @throws PortalException if a cp definition diagram pin with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
			getCPDefinitionDiagramPin(long CPDefinitionDiagramPinId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramPinLocalService.getCPDefinitionDiagramPin(
			CPDefinitionDiagramPinId);
	}

	/**
	 * Returns a range of all the cp definition diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram pins
	 * @param end the upper bound of the range of cp definition diagram pins (not inclusive)
	 * @return the range of cp definition diagram pins
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin>
			getCPDefinitionDiagramPins(int start, int end) {

		return _cpDefinitionDiagramPinLocalService.getCPDefinitionDiagramPins(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin>
			getCPDefinitionDiagramPins(
				long cpDefinitionId, int start, int end) {

		return _cpDefinitionDiagramPinLocalService.getCPDefinitionDiagramPins(
			cpDefinitionId, start, end);
	}

	/**
	 * Returns the number of cp definition diagram pins.
	 *
	 * @return the number of cp definition diagram pins
	 */
	@Override
	public int getCPDefinitionDiagramPinsCount() {
		return _cpDefinitionDiagramPinLocalService.
			getCPDefinitionDiagramPinsCount();
	}

	@Override
	public int getCPDefinitionDiagramPinsCount(long cpDefinitionId) {
		return _cpDefinitionDiagramPinLocalService.
			getCPDefinitionDiagramPinsCount(cpDefinitionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpDefinitionDiagramPinLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionDiagramPinLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramPinLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the cp definition diagram pin in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramPinLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramPin the cp definition diagram pin
	 * @return the cp definition diagram pin that was updated
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
		updateCPDefinitionDiagramPin(
			com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
				cpDefinitionDiagramPin) {

		return _cpDefinitionDiagramPinLocalService.updateCPDefinitionDiagramPin(
			cpDefinitionDiagramPin);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin
			updateCPDefinitionDiagramPin(
				long cpDefinitionDiagramPinId, int number, double positionX,
				double positionY)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionDiagramPinLocalService.updateCPDefinitionDiagramPin(
			cpDefinitionDiagramPinId, number, positionX, positionY);
	}

	@Override
	public CPDefinitionDiagramPinLocalService getWrappedService() {
		return _cpDefinitionDiagramPinLocalService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionDiagramPinLocalService cpDefinitionDiagramPinLocalService) {

		_cpDefinitionDiagramPinLocalService =
			cpDefinitionDiagramPinLocalService;
	}

	private CPDefinitionDiagramPinLocalService
		_cpDefinitionDiagramPinLocalService;

}