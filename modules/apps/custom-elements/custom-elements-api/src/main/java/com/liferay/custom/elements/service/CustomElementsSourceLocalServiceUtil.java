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

package com.liferay.custom.elements.service;

import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CustomElementsSource. This utility wraps
 * <code>com.liferay.custom.elements.service.impl.CustomElementsSourceLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementsSourceLocalService
 * @generated
 */
public class CustomElementsSourceLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.custom.elements.service.impl.CustomElementsSourceLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the custom elements source to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementsSourceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementsSource the custom elements source
	 * @return the custom elements source that was added
	 */
	public static CustomElementsSource addCustomElementsSource(
		CustomElementsSource customElementsSource) {

		return getService().addCustomElementsSource(customElementsSource);
	}

	public static CustomElementsSource addCustomElementsSource(
			long userId, String htmlElementName, String name, String urls,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCustomElementsSource(
			userId, htmlElementName, name, urls, serviceContext);
	}

	/**
	 * Creates a new custom elements source with the primary key. Does not add the custom elements source to the database.
	 *
	 * @param customElementsSourceId the primary key for the new custom elements source
	 * @return the new custom elements source
	 */
	public static CustomElementsSource createCustomElementsSource(
		long customElementsSourceId) {

		return getService().createCustomElementsSource(customElementsSourceId);
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
	 * Deletes the custom elements source from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementsSourceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementsSource the custom elements source
	 * @return the custom elements source that was removed
	 */
	public static CustomElementsSource deleteCustomElementsSource(
		CustomElementsSource customElementsSource) {

		return getService().deleteCustomElementsSource(customElementsSource);
	}

	/**
	 * Deletes the custom elements source with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementsSourceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementsSourceId the primary key of the custom elements source
	 * @return the custom elements source that was removed
	 * @throws PortalException if a custom elements source with the primary key could not be found
	 */
	public static CustomElementsSource deleteCustomElementsSource(
			long customElementsSourceId)
		throws PortalException {

		return getService().deleteCustomElementsSource(customElementsSourceId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.custom.elements.model.impl.CustomElementsSourceModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.custom.elements.model.impl.CustomElementsSourceModelImpl</code>.
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

	public static CustomElementsSource fetchCustomElementsSource(
		long customElementsSourceId) {

		return getService().fetchCustomElementsSource(customElementsSourceId);
	}

	/**
	 * Returns the custom elements source with the matching UUID and company.
	 *
	 * @param uuid the custom elements source's UUID
	 * @param companyId the primary key of the company
	 * @return the matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public static CustomElementsSource
		fetchCustomElementsSourceByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchCustomElementsSourceByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the custom elements source with the primary key.
	 *
	 * @param customElementsSourceId the primary key of the custom elements source
	 * @return the custom elements source
	 * @throws PortalException if a custom elements source with the primary key could not be found
	 */
	public static CustomElementsSource getCustomElementsSource(
			long customElementsSourceId)
		throws PortalException {

		return getService().getCustomElementsSource(customElementsSourceId);
	}

	/**
	 * Returns the custom elements source with the matching UUID and company.
	 *
	 * @param uuid the custom elements source's UUID
	 * @param companyId the primary key of the company
	 * @return the matching custom elements source
	 * @throws PortalException if a matching custom elements source could not be found
	 */
	public static CustomElementsSource
			getCustomElementsSourceByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return getService().getCustomElementsSourceByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the custom elements sources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.custom.elements.model.impl.CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @return the range of custom elements sources
	 */
	public static List<CustomElementsSource> getCustomElementsSources(
		int start, int end) {

		return getService().getCustomElementsSources(start, end);
	}

	public static List<CustomElementsSource> getCustomElementsSources(
		long companyId) {

		return getService().getCustomElementsSources(companyId);
	}

	/**
	 * Returns the number of custom elements sources.
	 *
	 * @return the number of custom elements sources
	 */
	public static int getCustomElementsSourcesCount() {
		return getService().getCustomElementsSourcesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
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

	public static List<CustomElementsSource> search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().search(companyId, keywords, start, end, sort);
	}

	public static int searchCount(long companyId, String keywords)
		throws PortalException {

		return getService().searchCount(companyId, keywords);
	}

	/**
	 * Updates the custom elements source in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementsSourceLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementsSource the custom elements source
	 * @return the custom elements source that was updated
	 */
	public static CustomElementsSource updateCustomElementsSource(
		CustomElementsSource customElementsSource) {

		return getService().updateCustomElementsSource(customElementsSource);
	}

	public static CustomElementsSource updateCustomElementsSource(
			long customElementsSourceId, String htmlElementName, String name,
			String urls,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCustomElementsSource(
			customElementsSourceId, htmlElementName, name, urls,
			serviceContext);
	}

	public static CustomElementsSourceLocalService getService() {
		return _service;
	}

	private static volatile CustomElementsSourceLocalService _service;

}