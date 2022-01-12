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

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link CSDiagramPinLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramPinLocalService
 * @generated
 */
public class CSDiagramPinLocalServiceWrapper
	implements CSDiagramPinLocalService,
			   ServiceWrapper<CSDiagramPinLocalService> {

	public CSDiagramPinLocalServiceWrapper() {
		this(null);
	}

	public CSDiagramPinLocalServiceWrapper(
		CSDiagramPinLocalService csDiagramPinLocalService) {

		_csDiagramPinLocalService = csDiagramPinLocalService;
	}

	/**
	 * Adds the cs diagram pin to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CSDiagramPinLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param csDiagramPin the cs diagram pin
	 * @return the cs diagram pin that was added
	 */
	@Override
	public CSDiagramPin addCSDiagramPin(CSDiagramPin csDiagramPin) {
		return _csDiagramPinLocalService.addCSDiagramPin(csDiagramPin);
	}

	@Override
	public CSDiagramPin addCSDiagramPin(
			long userId, long cpDefinitionId, double positionX,
			double positionY, String sequence)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinLocalService.addCSDiagramPin(
			userId, cpDefinitionId, positionX, positionY, sequence);
	}

	/**
	 * Creates a new cs diagram pin with the primary key. Does not add the cs diagram pin to the database.
	 *
	 * @param CSDiagramPinId the primary key for the new cs diagram pin
	 * @return the new cs diagram pin
	 */
	@Override
	public CSDiagramPin createCSDiagramPin(long CSDiagramPinId) {
		return _csDiagramPinLocalService.createCSDiagramPin(CSDiagramPinId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the cs diagram pin from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CSDiagramPinLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param csDiagramPin the cs diagram pin
	 * @return the cs diagram pin that was removed
	 */
	@Override
	public CSDiagramPin deleteCSDiagramPin(CSDiagramPin csDiagramPin) {
		return _csDiagramPinLocalService.deleteCSDiagramPin(csDiagramPin);
	}

	/**
	 * Deletes the cs diagram pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CSDiagramPinLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CSDiagramPinId the primary key of the cs diagram pin
	 * @return the cs diagram pin that was removed
	 * @throws PortalException if a cs diagram pin with the primary key could not be found
	 */
	@Override
	public CSDiagramPin deleteCSDiagramPin(long CSDiagramPinId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinLocalService.deleteCSDiagramPin(CSDiagramPinId);
	}

	@Override
	public void deleteCSDiagramPins(long cpDefinitionId) {
		_csDiagramPinLocalService.deleteCSDiagramPins(cpDefinitionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _csDiagramPinLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _csDiagramPinLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _csDiagramPinLocalService.dynamicQuery();
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

		return _csDiagramPinLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramPinModelImpl</code>.
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

		return _csDiagramPinLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramPinModelImpl</code>.
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

		return _csDiagramPinLocalService.dynamicQuery(
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

		return _csDiagramPinLocalService.dynamicQueryCount(dynamicQuery);
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

		return _csDiagramPinLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public CSDiagramPin fetchCSDiagramPin(long CSDiagramPinId) {
		return _csDiagramPinLocalService.fetchCSDiagramPin(CSDiagramPinId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _csDiagramPinLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the cs diagram pin with the primary key.
	 *
	 * @param CSDiagramPinId the primary key of the cs diagram pin
	 * @return the cs diagram pin
	 * @throws PortalException if a cs diagram pin with the primary key could not be found
	 */
	@Override
	public CSDiagramPin getCSDiagramPin(long CSDiagramPinId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinLocalService.getCSDiagramPin(CSDiagramPinId);
	}

	/**
	 * Returns a range of all the cs diagram pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram pins
	 * @param end the upper bound of the range of cs diagram pins (not inclusive)
	 * @return the range of cs diagram pins
	 */
	@Override
	public java.util.List<CSDiagramPin> getCSDiagramPins(int start, int end) {
		return _csDiagramPinLocalService.getCSDiagramPins(start, end);
	}

	@Override
	public java.util.List<CSDiagramPin> getCSDiagramPins(
		long cpDefinitionId, int start, int end) {

		return _csDiagramPinLocalService.getCSDiagramPins(
			cpDefinitionId, start, end);
	}

	/**
	 * Returns the number of cs diagram pins.
	 *
	 * @return the number of cs diagram pins
	 */
	@Override
	public int getCSDiagramPinsCount() {
		return _csDiagramPinLocalService.getCSDiagramPinsCount();
	}

	@Override
	public int getCSDiagramPinsCount(long cpDefinitionId) {
		return _csDiagramPinLocalService.getCSDiagramPinsCount(cpDefinitionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _csDiagramPinLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _csDiagramPinLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the cs diagram pin in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CSDiagramPinLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param csDiagramPin the cs diagram pin
	 * @return the cs diagram pin that was updated
	 */
	@Override
	public CSDiagramPin updateCSDiagramPin(CSDiagramPin csDiagramPin) {
		return _csDiagramPinLocalService.updateCSDiagramPin(csDiagramPin);
	}

	@Override
	public CSDiagramPin updateCSDiagramPin(
			long csDiagramPinId, double positionX, double positionY,
			String sequence)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramPinLocalService.updateCSDiagramPin(
			csDiagramPinId, positionX, positionY, sequence);
	}

	@Override
	public CTPersistence<CSDiagramPin> getCTPersistence() {
		return _csDiagramPinLocalService.getCTPersistence();
	}

	@Override
	public Class<CSDiagramPin> getModelClass() {
		return _csDiagramPinLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CSDiagramPin>, R, E>
				updateUnsafeFunction)
		throws E {

		return _csDiagramPinLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public CSDiagramPinLocalService getWrappedService() {
		return _csDiagramPinLocalService;
	}

	@Override
	public void setWrappedService(
		CSDiagramPinLocalService csDiagramPinLocalService) {

		_csDiagramPinLocalService = csDiagramPinLocalService;
	}

	private CSDiagramPinLocalService _csDiagramPinLocalService;

}