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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CustomElementsPortletDescriptorLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementsPortletDescriptorLocalService
 * @generated
 */
public class CustomElementsPortletDescriptorLocalServiceWrapper
	implements CustomElementsPortletDescriptorLocalService,
			   ServiceWrapper<CustomElementsPortletDescriptorLocalService> {

	public CustomElementsPortletDescriptorLocalServiceWrapper(
		CustomElementsPortletDescriptorLocalService
			customElementsPortletDescriptorLocalService) {

		_customElementsPortletDescriptorLocalService =
			customElementsPortletDescriptorLocalService;
	}

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
	@Override
	public com.liferay.custom.elements.model.CustomElementsPortletDescriptor
		addCustomElementsPortletDescriptor(
			com.liferay.custom.elements.model.CustomElementsPortletDescriptor
				customElementsPortletDescriptor) {

		return _customElementsPortletDescriptorLocalService.
			addCustomElementsPortletDescriptor(customElementsPortletDescriptor);
	}

	@Override
	public com.liferay.custom.elements.model.CustomElementsPortletDescriptor
			addCustomElementsPortletDescriptor(
				String cssURLs, String htmlElementName, boolean instanceable,
				String name, String properties,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsPortletDescriptorLocalService.
			addCustomElementsPortletDescriptor(
				cssURLs, htmlElementName, instanceable, name, properties,
				serviceContext);
	}

	/**
	 * Creates a new custom elements portlet descriptor with the primary key. Does not add the custom elements portlet descriptor to the database.
	 *
	 * @param customElementsPortletDescriptorId the primary key for the new custom elements portlet descriptor
	 * @return the new custom elements portlet descriptor
	 */
	@Override
	public com.liferay.custom.elements.model.CustomElementsPortletDescriptor
		createCustomElementsPortletDescriptor(
			long customElementsPortletDescriptorId) {

		return _customElementsPortletDescriptorLocalService.
			createCustomElementsPortletDescriptor(
				customElementsPortletDescriptorId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsPortletDescriptorLocalService.
			createPersistedModel(primaryKeyObj);
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
	@Override
	public com.liferay.custom.elements.model.CustomElementsPortletDescriptor
		deleteCustomElementsPortletDescriptor(
			com.liferay.custom.elements.model.CustomElementsPortletDescriptor
				customElementsPortletDescriptor) {

		return _customElementsPortletDescriptorLocalService.
			deleteCustomElementsPortletDescriptor(
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
	@Override
	public com.liferay.custom.elements.model.CustomElementsPortletDescriptor
			deleteCustomElementsPortletDescriptor(
				long customElementsPortletDescriptorId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsPortletDescriptorLocalService.
			deleteCustomElementsPortletDescriptor(
				customElementsPortletDescriptorId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsPortletDescriptorLocalService.
			deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _customElementsPortletDescriptorLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _customElementsPortletDescriptorLocalService.dslQueryCount(
			dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _customElementsPortletDescriptorLocalService.dynamicQuery();
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

		return _customElementsPortletDescriptorLocalService.dynamicQuery(
			dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _customElementsPortletDescriptorLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _customElementsPortletDescriptorLocalService.dynamicQuery(
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

		return _customElementsPortletDescriptorLocalService.dynamicQueryCount(
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

		return _customElementsPortletDescriptorLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.custom.elements.model.CustomElementsPortletDescriptor
		fetchCustomElementsPortletDescriptor(
			long customElementsPortletDescriptorId) {

		return _customElementsPortletDescriptorLocalService.
			fetchCustomElementsPortletDescriptor(
				customElementsPortletDescriptorId);
	}

	/**
	 * Returns the custom elements portlet descriptor with the matching UUID and company.
	 *
	 * @param uuid the custom elements portlet descriptor's UUID
	 * @param companyId the primary key of the company
	 * @return the matching custom elements portlet descriptor, or <code>null</code> if a matching custom elements portlet descriptor could not be found
	 */
	@Override
	public com.liferay.custom.elements.model.CustomElementsPortletDescriptor
		fetchCustomElementsPortletDescriptorByUuidAndCompanyId(
			String uuid, long companyId) {

		return _customElementsPortletDescriptorLocalService.
			fetchCustomElementsPortletDescriptorByUuidAndCompanyId(
				uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _customElementsPortletDescriptorLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the custom elements portlet descriptor with the primary key.
	 *
	 * @param customElementsPortletDescriptorId the primary key of the custom elements portlet descriptor
	 * @return the custom elements portlet descriptor
	 * @throws PortalException if a custom elements portlet descriptor with the primary key could not be found
	 */
	@Override
	public com.liferay.custom.elements.model.CustomElementsPortletDescriptor
			getCustomElementsPortletDescriptor(
				long customElementsPortletDescriptorId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsPortletDescriptorLocalService.
			getCustomElementsPortletDescriptor(
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
	@Override
	public com.liferay.custom.elements.model.CustomElementsPortletDescriptor
			getCustomElementsPortletDescriptorByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsPortletDescriptorLocalService.
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
	@Override
	public java.util.List
		<com.liferay.custom.elements.model.CustomElementsPortletDescriptor>
			getCustomElementsPortletDescriptors(int start, int end) {

		return _customElementsPortletDescriptorLocalService.
			getCustomElementsPortletDescriptors(start, end);
	}

	/**
	 * Returns the number of custom elements portlet descriptors.
	 *
	 * @return the number of custom elements portlet descriptors
	 */
	@Override
	public int getCustomElementsPortletDescriptorsCount() {
		return _customElementsPortletDescriptorLocalService.
			getCustomElementsPortletDescriptorsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _customElementsPortletDescriptorLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _customElementsPortletDescriptorLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _customElementsPortletDescriptorLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsPortletDescriptorLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public java.util.List
		<com.liferay.custom.elements.model.CustomElementsPortletDescriptor>
				search(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsPortletDescriptorLocalService.search(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCount(long companyId, String keywords)
		throws com.liferay.portal.kernel.search.SearchException {

		return _customElementsPortletDescriptorLocalService.searchCount(
			companyId, keywords);
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
	@Override
	public com.liferay.custom.elements.model.CustomElementsPortletDescriptor
		updateCustomElementsPortletDescriptor(
			com.liferay.custom.elements.model.CustomElementsPortletDescriptor
				customElementsPortletDescriptor) {

		return _customElementsPortletDescriptorLocalService.
			updateCustomElementsPortletDescriptor(
				customElementsPortletDescriptor);
	}

	@Override
	public com.liferay.custom.elements.model.CustomElementsPortletDescriptor
			updateCustomElementsPortletDescriptor(
				long customElementsSourceId, String cssURLs,
				String htmlElementName, boolean instanceable, String name,
				String properties,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsPortletDescriptorLocalService.
			updateCustomElementsPortletDescriptor(
				customElementsSourceId, cssURLs, htmlElementName, instanceable,
				name, properties, serviceContext);
	}

	@Override
	public CustomElementsPortletDescriptorLocalService getWrappedService() {
		return _customElementsPortletDescriptorLocalService;
	}

	@Override
	public void setWrappedService(
		CustomElementsPortletDescriptorLocalService
			customElementsPortletDescriptorLocalService) {

		_customElementsPortletDescriptorLocalService =
			customElementsPortletDescriptorLocalService;
	}

	private CustomElementsPortletDescriptorLocalService
		_customElementsPortletDescriptorLocalService;

}