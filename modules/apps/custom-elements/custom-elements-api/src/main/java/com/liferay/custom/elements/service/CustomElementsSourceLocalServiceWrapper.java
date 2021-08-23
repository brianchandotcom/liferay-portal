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
 * Provides a wrapper for {@link CustomElementsSourceLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementsSourceLocalService
 * @generated
 */
public class CustomElementsSourceLocalServiceWrapper
	implements CustomElementsSourceLocalService,
			   ServiceWrapper<CustomElementsSourceLocalService> {

	public CustomElementsSourceLocalServiceWrapper(
		CustomElementsSourceLocalService customElementsSourceLocalService) {

		_customElementsSourceLocalService = customElementsSourceLocalService;
	}

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
	@Override
	public com.liferay.custom.elements.model.CustomElementsSource
		addCustomElementsSource(
			com.liferay.custom.elements.model.CustomElementsSource
				customElementsSource) {

		return _customElementsSourceLocalService.addCustomElementsSource(
			customElementsSource);
	}

	@Override
	public com.liferay.custom.elements.model.CustomElementsSource
			addCustomElementsSource(
				long userId, String htmlElementName, String name, String urls,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsSourceLocalService.addCustomElementsSource(
			userId, htmlElementName, name, urls, serviceContext);
	}

	/**
	 * Creates a new custom elements source with the primary key. Does not add the custom elements source to the database.
	 *
	 * @param customElementsSourceId the primary key for the new custom elements source
	 * @return the new custom elements source
	 */
	@Override
	public com.liferay.custom.elements.model.CustomElementsSource
		createCustomElementsSource(long customElementsSourceId) {

		return _customElementsSourceLocalService.createCustomElementsSource(
			customElementsSourceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsSourceLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.custom.elements.model.CustomElementsSource
		deleteCustomElementsSource(
			com.liferay.custom.elements.model.CustomElementsSource
				customElementsSource) {

		return _customElementsSourceLocalService.deleteCustomElementsSource(
			customElementsSource);
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
	@Override
	public com.liferay.custom.elements.model.CustomElementsSource
			deleteCustomElementsSource(long customElementsSourceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsSourceLocalService.deleteCustomElementsSource(
			customElementsSourceId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsSourceLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _customElementsSourceLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _customElementsSourceLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _customElementsSourceLocalService.dynamicQuery();
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

		return _customElementsSourceLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _customElementsSourceLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _customElementsSourceLocalService.dynamicQuery(
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

		return _customElementsSourceLocalService.dynamicQueryCount(
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

		return _customElementsSourceLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.custom.elements.model.CustomElementsSource
		fetchCustomElementsSource(long customElementsSourceId) {

		return _customElementsSourceLocalService.fetchCustomElementsSource(
			customElementsSourceId);
	}

	/**
	 * Returns the custom elements source with the matching UUID and company.
	 *
	 * @param uuid the custom elements source's UUID
	 * @param companyId the primary key of the company
	 * @return the matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	@Override
	public com.liferay.custom.elements.model.CustomElementsSource
		fetchCustomElementsSourceByUuidAndCompanyId(
			String uuid, long companyId) {

		return _customElementsSourceLocalService.
			fetchCustomElementsSourceByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _customElementsSourceLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the custom elements source with the primary key.
	 *
	 * @param customElementsSourceId the primary key of the custom elements source
	 * @return the custom elements source
	 * @throws PortalException if a custom elements source with the primary key could not be found
	 */
	@Override
	public com.liferay.custom.elements.model.CustomElementsSource
			getCustomElementsSource(long customElementsSourceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsSourceLocalService.getCustomElementsSource(
			customElementsSourceId);
	}

	/**
	 * Returns the custom elements source with the matching UUID and company.
	 *
	 * @param uuid the custom elements source's UUID
	 * @param companyId the primary key of the company
	 * @return the matching custom elements source
	 * @throws PortalException if a matching custom elements source could not be found
	 */
	@Override
	public com.liferay.custom.elements.model.CustomElementsSource
			getCustomElementsSourceByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsSourceLocalService.
			getCustomElementsSourceByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List
		<com.liferay.custom.elements.model.CustomElementsSource>
			getCustomElementsSources(int start, int end) {

		return _customElementsSourceLocalService.getCustomElementsSources(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.custom.elements.model.CustomElementsSource>
			getCustomElementsSources(long companyId) {

		return _customElementsSourceLocalService.getCustomElementsSources(
			companyId);
	}

	/**
	 * Returns the number of custom elements sources.
	 *
	 * @return the number of custom elements sources
	 */
	@Override
	public int getCustomElementsSourcesCount() {
		return _customElementsSourceLocalService.
			getCustomElementsSourcesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _customElementsSourceLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _customElementsSourceLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _customElementsSourceLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsSourceLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public java.util.List
		<com.liferay.custom.elements.model.CustomElementsSource> search(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsSourceLocalService.search(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCount(long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsSourceLocalService.searchCount(
			companyId, keywords);
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
	@Override
	public com.liferay.custom.elements.model.CustomElementsSource
		updateCustomElementsSource(
			com.liferay.custom.elements.model.CustomElementsSource
				customElementsSource) {

		return _customElementsSourceLocalService.updateCustomElementsSource(
			customElementsSource);
	}

	@Override
	public com.liferay.custom.elements.model.CustomElementsSource
			updateCustomElementsSource(
				long customElementsSourceId, String htmlElementName,
				String name, String urls,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _customElementsSourceLocalService.updateCustomElementsSource(
			customElementsSourceId, htmlElementName, name, urls,
			serviceContext);
	}

	@Override
	public CustomElementsSourceLocalService getWrappedService() {
		return _customElementsSourceLocalService;
	}

	@Override
	public void setWrappedService(
		CustomElementsSourceLocalService customElementsSourceLocalService) {

		_customElementsSourceLocalService = customElementsSourceLocalService;
	}

	private CustomElementsSourceLocalService _customElementsSourceLocalService;

}