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

package com.liferay.opensocial.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;

/**
 * Provides the local service utility for Gadget. This utility wraps
 * <code>com.liferay.opensocial.service.impl.GadgetLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see GadgetLocalService
 * @generated
 */
public class GadgetLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.opensocial.service.impl.GadgetLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the gadget to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GadgetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param gadget the gadget
	 * @return the gadget that was added
	 */
	public static com.liferay.opensocial.model.Gadget addGadget(
		com.liferay.opensocial.model.Gadget gadget) {

		return getService().addGadget(gadget);
	}

	public static com.liferay.opensocial.model.Gadget addGadget(
			long companyId, String url, String portletCategoryNames,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addGadget(
			companyId, url, portletCategoryNames, serviceContext);
	}

	/**
	 * Creates a new gadget with the primary key. Does not add the gadget to the database.
	 *
	 * @param gadgetId the primary key for the new gadget
	 * @return the new gadget
	 */
	public static com.liferay.opensocial.model.Gadget createGadget(
		long gadgetId) {

		return getService().createGadget(gadgetId);
	}

	/**
	 * Deletes the gadget from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GadgetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param gadget the gadget
	 * @return the gadget that was removed
	 * @throws PortalException
	 */
	public static com.liferay.opensocial.model.Gadget deleteGadget(
			com.liferay.opensocial.model.Gadget gadget)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteGadget(gadget);
	}

	/**
	 * Deletes the gadget with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GadgetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param gadgetId the primary key of the gadget
	 * @return the gadget that was removed
	 * @throws PortalException if a gadget with the primary key could not be found
	 */
	public static com.liferay.opensocial.model.Gadget deleteGadget(
			long gadgetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteGadget(gadgetId);
	}

	public static void deleteGadgets(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteGadgets(companyId);
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

	public static void destroyGadget(String uuid, long companyId) {
		getService().destroyGadget(uuid, companyId);
	}

	public static void destroyGadgets() {
		getService().destroyGadgets();
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.GadgetModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.GadgetModelImpl</code>.
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

	public static com.liferay.opensocial.model.Gadget fetchGadget(
		long gadgetId) {

		return getService().fetchGadget(gadgetId);
	}

	public static com.liferay.opensocial.model.Gadget fetchGadget(
		long companyId, String url) {

		return getService().fetchGadget(companyId, url);
	}

	/**
	 * Returns the gadget with the matching UUID and company.
	 *
	 * @param uuid the gadget's UUID
	 * @param companyId the primary key of the company
	 * @return the matching gadget, or <code>null</code> if a matching gadget could not be found
	 */
	public static com.liferay.opensocial.model.Gadget
		fetchGadgetByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchGadgetByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	 * Returns the gadget with the primary key.
	 *
	 * @param gadgetId the primary key of the gadget
	 * @return the gadget
	 * @throws PortalException if a gadget with the primary key could not be found
	 */
	public static com.liferay.opensocial.model.Gadget getGadget(long gadgetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGadget(gadgetId);
	}

	public static com.liferay.opensocial.model.Gadget getGadget(
			long companyId, String url)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGadget(companyId, url);
	}

	public static com.liferay.opensocial.model.Gadget getGadget(
			String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGadget(uuid, companyId);
	}

	/**
	 * Returns the gadget with the matching UUID and company.
	 *
	 * @param uuid the gadget's UUID
	 * @param companyId the primary key of the company
	 * @return the matching gadget
	 * @throws PortalException if a matching gadget could not be found
	 */
	public static com.liferay.opensocial.model.Gadget
			getGadgetByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGadgetByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the gadgets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.GadgetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of gadgets
	 * @param end the upper bound of the range of gadgets (not inclusive)
	 * @return the range of gadgets
	 */
	public static java.util.List<com.liferay.opensocial.model.Gadget>
		getGadgets(int start, int end) {

		return getService().getGadgets(start, end);
	}

	public static java.util.List<com.liferay.opensocial.model.Gadget>
		getGadgets(long companyId, int start, int end) {

		return getService().getGadgets(companyId, start, end);
	}

	/**
	 * Returns the number of gadgets.
	 *
	 * @return the number of gadgets
	 */
	public static int getGadgetsCount() {
		return getService().getGadgetsCount();
	}

	public static int getGadgetsCount(long companyId) {
		return getService().getGadgetsCount(companyId);
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

	public static void initGadget(
			String uuid, long companyId, long gadgetId, String name,
			String portletCategoryNames)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().initGadget(
			uuid, companyId, gadgetId, name, portletCategoryNames);
	}

	public static void initGadgets()
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().initGadgets();
	}

	/**
	 * Updates the gadget in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GadgetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param gadget the gadget
	 * @return the gadget that was updated
	 */
	public static com.liferay.opensocial.model.Gadget updateGadget(
		com.liferay.opensocial.model.Gadget gadget) {

		return getService().updateGadget(gadget);
	}

	public static com.liferay.opensocial.model.Gadget updateGadget(
			long gadgetId, String portletCategoryNames)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateGadget(gadgetId, portletCategoryNames);
	}

	public static void clearService() {
		_service = null;
	}

	public static GadgetLocalService getService() {
		if (_service == null) {
			_service = (GadgetLocalService)PortletBeanLocatorUtil.locate(
				ServletContextUtil.getServletContextName(),
				GadgetLocalService.class.getName());
		}

		return _service;
	}

	private static GadgetLocalService _service;

}