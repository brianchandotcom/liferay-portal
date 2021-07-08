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

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CPDefinitionDiagramPin. This utility wraps
 * <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramPinLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramPinLocalService
 * @generated
 */
public class CPDefinitionDiagramPinLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramPinLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static CPDefinitionDiagramPin addCPDefinitionDiagramPin(
		CPDefinitionDiagramPin cpDefinitionDiagramPin) {

		return getService().addCPDefinitionDiagramPin(cpDefinitionDiagramPin);
	}

	public static CPDefinitionDiagramPin addCPDefinitionDiagramPin(
			long userId, long cpDefinitionId, int number, double positionX,
			double positionY)
		throws PortalException {

		return getService().addCPDefinitionDiagramPin(
			userId, cpDefinitionId, number, positionX, positionY);
	}

	/**
	 * Creates a new cp definition diagram pin with the primary key. Does not add the cp definition diagram pin to the database.
	 *
	 * @param CPDefinitionDiagramPinId the primary key for the new cp definition diagram pin
	 * @return the new cp definition diagram pin
	 */
	public static CPDefinitionDiagramPin createCPDefinitionDiagramPin(
		long CPDefinitionDiagramPinId) {

		return getService().createCPDefinitionDiagramPin(
			CPDefinitionDiagramPinId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static CPDefinitionDiagramPin deleteCPDefinitionDiagramPin(
		CPDefinitionDiagramPin cpDefinitionDiagramPin) {

		return getService().deleteCPDefinitionDiagramPin(
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
	public static CPDefinitionDiagramPin deleteCPDefinitionDiagramPin(
			long CPDefinitionDiagramPinId)
		throws PortalException {

		return getService().deleteCPDefinitionDiagramPin(
			CPDefinitionDiagramPinId);
	}

	public static void deleteCPDefinitionDiagramPins(long cpDefinitionId) {
		getService().deleteCPDefinitionDiagramPins(cpDefinitionId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CPDefinitionDiagramPin fetchCPDefinitionDiagramPin(
		long CPDefinitionDiagramPinId) {

		return getService().fetchCPDefinitionDiagramPin(
			CPDefinitionDiagramPinId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the cp definition diagram pin with the primary key.
	 *
	 * @param CPDefinitionDiagramPinId the primary key of the cp definition diagram pin
	 * @return the cp definition diagram pin
	 * @throws PortalException if a cp definition diagram pin with the primary key could not be found
	 */
	public static CPDefinitionDiagramPin getCPDefinitionDiagramPin(
			long CPDefinitionDiagramPinId)
		throws PortalException {

		return getService().getCPDefinitionDiagramPin(CPDefinitionDiagramPinId);
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
	public static List<CPDefinitionDiagramPin> getCPDefinitionDiagramPins(
		int start, int end) {

		return getService().getCPDefinitionDiagramPins(start, end);
	}

	public static List<CPDefinitionDiagramPin> getCPDefinitionDiagramPins(
		long cpDefinitionId, int start, int end) {

		return getService().getCPDefinitionDiagramPins(
			cpDefinitionId, start, end);
	}

	/**
	 * Returns the number of cp definition diagram pins.
	 *
	 * @return the number of cp definition diagram pins
	 */
	public static int getCPDefinitionDiagramPinsCount() {
		return getService().getCPDefinitionDiagramPinsCount();
	}

	public static int getCPDefinitionDiagramPinsCount(long cpDefinitionId) {
		return getService().getCPDefinitionDiagramPinsCount(cpDefinitionId);
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
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
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
	public static CPDefinitionDiagramPin updateCPDefinitionDiagramPin(
		CPDefinitionDiagramPin cpDefinitionDiagramPin) {

		return getService().updateCPDefinitionDiagramPin(
			cpDefinitionDiagramPin);
	}

	public static CPDefinitionDiagramPin updateCPDefinitionDiagramPin(
			long cpDefinitionDiagramPinId, int number, double positionX,
			double positionY)
		throws PortalException {

		return getService().updateCPDefinitionDiagramPin(
			cpDefinitionDiagramPinId, number, positionX, positionY);
	}

	public static CPDefinitionDiagramPinLocalService getService() {
		return _service;
	}

	private static volatile CPDefinitionDiagramPinLocalService _service;

}