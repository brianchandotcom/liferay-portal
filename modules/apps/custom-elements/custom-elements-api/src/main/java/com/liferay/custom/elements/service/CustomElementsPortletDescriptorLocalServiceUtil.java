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

import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CustomElementsPortletDescriptor. This utility wraps
 * <code>com.liferay.custom.elements.service.impl.CustomElementsPortletDescriptorLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementsPortletDescriptorLocalService
 * @generated
 */
public class CustomElementsPortletDescriptorLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.custom.elements.service.impl.CustomElementsPortletDescriptorLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the custom elements portlet descriptor to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementsPortletDescriptorLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementsPortletDescriptor the custom elements portlet descriptor
	 * @return the custom elements portlet descriptor that was added
	 */
	public static CustomElementsPortletDescriptor
		addCustomElementsPortletDescriptor(
			CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		return getService().addCustomElementsPortletDescriptor(
			customElementsPortletDescriptor);
	}

	public static CustomElementsPortletDescriptor
			addCustomElementsPortletDescriptor(
				String cssURLs, String htmlElementName, boolean instanceable,
				String name, String properties,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCustomElementsPortletDescriptor(
			cssURLs, htmlElementName, instanceable, name, properties,
			serviceContext);
	}

	/**
	 * Creates a new custom elements portlet descriptor with the primary key. Does not add the custom elements portlet descriptor to the database.
	 *
	 * @param customElementsPortletDescriptorId the primary key for the new custom elements portlet descriptor
	 * @return the new custom elements portlet descriptor
	 */
	public static CustomElementsPortletDescriptor
		createCustomElementsPortletDescriptor(
			long customElementsPortletDescriptorId) {

		return getService().createCustomElementsPortletDescriptor(
			customElementsPortletDescriptorId);
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
	 * Deletes the custom elements portlet descriptor from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementsPortletDescriptorLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementsPortletDescriptor the custom elements portlet descriptor
	 * @return the custom elements portlet descriptor that was removed
	 */
	public static CustomElementsPortletDescriptor
		deleteCustomElementsPortletDescriptor(
			CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		return getService().deleteCustomElementsPortletDescriptor(
			customElementsPortletDescriptor);
	}

	/**
	 * Deletes the custom elements portlet descriptor with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementsPortletDescriptorLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementsPortletDescriptorId the primary key of the custom elements portlet descriptor
	 * @return the custom elements portlet descriptor that was removed
	 * @throws PortalException if a custom elements portlet descriptor with the primary key could not be found
	 */
	public static CustomElementsPortletDescriptor
			deleteCustomElementsPortletDescriptor(
				long customElementsPortletDescriptorId)
		throws PortalException {

		return getService().deleteCustomElementsPortletDescriptor(
			customElementsPortletDescriptorId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.custom.elements.model.impl.CustomElementsPortletDescriptorModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.custom.elements.model.impl.CustomElementsPortletDescriptorModelImpl</code>.
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

	public static CustomElementsPortletDescriptor
		fetchCustomElementsPortletDescriptor(
			long customElementsPortletDescriptorId) {

		return getService().fetchCustomElementsPortletDescriptor(
			customElementsPortletDescriptorId);
	}

	/**
	 * Returns the custom elements portlet descriptor with the matching UUID and company.
	 *
	 * @param uuid the custom elements portlet descriptor's UUID
	 * @param companyId the primary key of the company
	 * @return the matching custom elements portlet descriptor, or <code>null</code> if a matching custom elements portlet descriptor could not be found
	 */
	public static CustomElementsPortletDescriptor
		fetchCustomElementsPortletDescriptorByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().
			fetchCustomElementsPortletDescriptorByUuidAndCompanyId(
				uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the custom elements portlet descriptor with the primary key.
	 *
	 * @param customElementsPortletDescriptorId the primary key of the custom elements portlet descriptor
	 * @return the custom elements portlet descriptor
	 * @throws PortalException if a custom elements portlet descriptor with the primary key could not be found
	 */
	public static CustomElementsPortletDescriptor
			getCustomElementsPortletDescriptor(
				long customElementsPortletDescriptorId)
		throws PortalException {

		return getService().getCustomElementsPortletDescriptor(
			customElementsPortletDescriptorId);
	}

	/**
	 * Returns the custom elements portlet descriptor with the matching UUID and company.
	 *
	 * @param uuid the custom elements portlet descriptor's UUID
	 * @param companyId the primary key of the company
	 * @return the matching custom elements portlet descriptor
	 * @throws PortalException if a matching custom elements portlet descriptor could not be found
	 */
	public static CustomElementsPortletDescriptor
			getCustomElementsPortletDescriptorByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return getService().
			getCustomElementsPortletDescriptorByUuidAndCompanyId(
				uuid, companyId);
	}

	/**
	 * Returns a range of all the custom elements portlet descriptors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.custom.elements.model.impl.CustomElementsPortletDescriptorModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom elements portlet descriptors
	 * @param end the upper bound of the range of custom elements portlet descriptors (not inclusive)
	 * @return the range of custom elements portlet descriptors
	 */
	public static List<CustomElementsPortletDescriptor>
		getCustomElementsPortletDescriptors(int start, int end) {

		return getService().getCustomElementsPortletDescriptors(start, end);
	}

	/**
	 * Returns the number of custom elements portlet descriptors.
	 *
	 * @return the number of custom elements portlet descriptors
	 */
	public static int getCustomElementsPortletDescriptorsCount() {
		return getService().getCustomElementsPortletDescriptorsCount();
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

	public static List<CustomElementsPortletDescriptor> search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().search(companyId, keywords, start, end, sort);
	}

	public static int searchCount(long companyId, String keywords)
		throws com.liferay.portal.kernel.search.SearchException {

		return getService().searchCount(companyId, keywords);
	}

	/**
	 * Updates the custom elements portlet descriptor in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CustomElementsPortletDescriptorLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param customElementsPortletDescriptor the custom elements portlet descriptor
	 * @return the custom elements portlet descriptor that was updated
	 */
	public static CustomElementsPortletDescriptor
		updateCustomElementsPortletDescriptor(
			CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		return getService().updateCustomElementsPortletDescriptor(
			customElementsPortletDescriptor);
	}

	public static CustomElementsPortletDescriptor
			updateCustomElementsPortletDescriptor(
				long customElementsSourceId, String cssURLs,
				String htmlElementName, boolean instanceable, String name,
				String properties,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCustomElementsPortletDescriptor(
			customElementsSourceId, cssURLs, htmlElementName, instanceable,
			name, properties, serviceContext);
	}

	public static CustomElementsPortletDescriptorLocalService getService() {
		return _service;
	}

	private static volatile CustomElementsPortletDescriptorLocalService
		_service;

}