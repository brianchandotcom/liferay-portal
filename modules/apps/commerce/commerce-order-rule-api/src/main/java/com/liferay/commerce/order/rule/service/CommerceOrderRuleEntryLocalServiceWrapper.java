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

package com.liferay.commerce.order.rule.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrderRuleEntryLocalService}.
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntryLocalService
 * @generated
 */
public class CommerceOrderRuleEntryLocalServiceWrapper
	implements CommerceOrderRuleEntryLocalService,
			   ServiceWrapper<CommerceOrderRuleEntryLocalService> {

	public CommerceOrderRuleEntryLocalServiceWrapper(
		CommerceOrderRuleEntryLocalService commerceOrderRuleEntryLocalService) {

		_commerceOrderRuleEntryLocalService =
			commerceOrderRuleEntryLocalService;
	}

	/**
	 * Adds the commerce order rule entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was added
	 */
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
		addCommerceOrderRuleEntry(
			com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
				commerceOrderRuleEntry) {

		return _commerceOrderRuleEntryLocalService.addCommerceOrderRuleEntry(
			commerceOrderRuleEntry);
	}

	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
			addCommerceOrderRuleEntry(
				String externalReferenceCode, long userId, boolean active,
				String description, String name, int priority, String type,
				String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryLocalService.addCommerceOrderRuleEntry(
			externalReferenceCode, userId, active, description, name, priority,
			type, typeSettings);
	}

	/**
	 * Creates a new commerce order rule entry with the primary key. Does not add the commerce order rule entry to the database.
	 *
	 * @param commerceOrderRuleEntryId the primary key for the new commerce order rule entry
	 * @return the new commerce order rule entry
	 */
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
		createCommerceOrderRuleEntry(long commerceOrderRuleEntryId) {

		return _commerceOrderRuleEntryLocalService.createCommerceOrderRuleEntry(
			commerceOrderRuleEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the commerce order rule entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was removed
	 */
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
		deleteCommerceOrderRuleEntry(
			com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
				commerceOrderRuleEntry) {

		return _commerceOrderRuleEntryLocalService.deleteCommerceOrderRuleEntry(
			commerceOrderRuleEntry);
	}

	/**
	 * Deletes the commerce order rule entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry that was removed
	 * @throws PortalException if a commerce order rule entry with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
			deleteCommerceOrderRuleEntry(long commerceOrderRuleEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryLocalService.deleteCommerceOrderRuleEntry(
			commerceOrderRuleEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceOrderRuleEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceOrderRuleEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceOrderRuleEntryLocalService.dynamicQuery();
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

		return _commerceOrderRuleEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
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

		return _commerceOrderRuleEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
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

		return _commerceOrderRuleEntryLocalService.dynamicQuery(
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

		return _commerceOrderRuleEntryLocalService.dynamicQueryCount(
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

		return _commerceOrderRuleEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
		fetchCommerceOrderRuleEntry(long commerceOrderRuleEntryId) {

		return _commerceOrderRuleEntryLocalService.fetchCommerceOrderRuleEntry(
			commerceOrderRuleEntryId);
	}

	/**
	 * Returns the commerce order rule entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order rule entry's external reference code
	 * @return the matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
		fetchCommerceOrderRuleEntryByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceOrderRuleEntryLocalService.
			fetchCommerceOrderRuleEntryByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceOrderRuleEntryByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
		fetchCommerceOrderRuleEntryByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceOrderRuleEntryLocalService.
			fetchCommerceOrderRuleEntryByReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceOrderRuleEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the commerce order rule entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of commerce order rule entries
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry>
			getCommerceOrderRuleEntries(int start, int end) {

		return _commerceOrderRuleEntryLocalService.getCommerceOrderRuleEntries(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry>
			getCommerceOrderRuleEntries(
				long companyId, boolean active, int start, int end) {

		return _commerceOrderRuleEntryLocalService.getCommerceOrderRuleEntries(
			companyId, active, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry>
			getCommerceOrderRuleEntries(
				long companyId, boolean active, String type, int start,
				int end) {

		return _commerceOrderRuleEntryLocalService.getCommerceOrderRuleEntries(
			companyId, active, type, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry>
			getCommerceOrderRuleEntries(
				long companyId, String type, int start, int end) {

		return _commerceOrderRuleEntryLocalService.getCommerceOrderRuleEntries(
			companyId, type, start, end);
	}

	/**
	 * Returns the number of commerce order rule entries.
	 *
	 * @return the number of commerce order rule entries
	 */
	@Override
	public int getCommerceOrderRuleEntriesCount() {
		return _commerceOrderRuleEntryLocalService.
			getCommerceOrderRuleEntriesCount();
	}

	/**
	 * Returns the commerce order rule entry with the primary key.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry
	 * @throws PortalException if a commerce order rule entry with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
			getCommerceOrderRuleEntry(long commerceOrderRuleEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryLocalService.getCommerceOrderRuleEntry(
			commerceOrderRuleEntryId);
	}

	/**
	 * Returns the commerce order rule entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order rule entry's external reference code
	 * @return the matching commerce order rule entry
	 * @throws PortalException if a matching commerce order rule entry could not be found
	 */
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
			getCommerceOrderRuleEntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryLocalService.
			getCommerceOrderRuleEntryByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceOrderRuleEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrderRuleEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the commerce order rule entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was updated
	 */
	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
		updateCommerceOrderRuleEntry(
			com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
				commerceOrderRuleEntry) {

		return _commerceOrderRuleEntryLocalService.updateCommerceOrderRuleEntry(
			commerceOrderRuleEntry);
	}

	@Override
	public com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry
			updateCommerceOrderRuleEntry(
				long commerceOrderRuleEntryId, boolean active,
				String description, String name, int priority,
				String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceOrderRuleEntryLocalService.updateCommerceOrderRuleEntry(
			commerceOrderRuleEntryId, active, description, name, priority,
			typeSettings);
	}

	@Override
	public CommerceOrderRuleEntryLocalService getWrappedService() {
		return _commerceOrderRuleEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderRuleEntryLocalService commerceOrderRuleEntryLocalService) {

		_commerceOrderRuleEntryLocalService =
			commerceOrderRuleEntryLocalService;
	}

	private CommerceOrderRuleEntryLocalService
		_commerceOrderRuleEntryLocalService;

}