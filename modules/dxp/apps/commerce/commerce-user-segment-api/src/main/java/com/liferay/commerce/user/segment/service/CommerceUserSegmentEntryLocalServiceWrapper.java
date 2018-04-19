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

package com.liferay.commerce.user.segment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceUserSegmentEntryLocalService}.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentEntryLocalService
 * @generated
 */
@ProviderType
public class CommerceUserSegmentEntryLocalServiceWrapper
	implements CommerceUserSegmentEntryLocalService,
		ServiceWrapper<CommerceUserSegmentEntryLocalService> {
	public CommerceUserSegmentEntryLocalServiceWrapper(
		CommerceUserSegmentEntryLocalService commerceUserSegmentEntryLocalService) {
		_commerceUserSegmentEntryLocalService = commerceUserSegmentEntryLocalService;
	}

	/**
	* Adds the commerce user segment entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntry the commerce user segment entry
	* @return the commerce user segment entry that was added
	*/
	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry addCommerceUserSegmentEntry(
		com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry commerceUserSegmentEntry) {
		return _commerceUserSegmentEntryLocalService.addCommerceUserSegmentEntry(commerceUserSegmentEntry);
	}

	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry addCommerceUserSegmentEntry(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryLocalService.addCommerceUserSegmentEntry(nameMap,
			priority, active, serviceContext);
	}

	/**
	* Creates a new commerce user segment entry with the primary key. Does not add the commerce user segment entry to the database.
	*
	* @param commerceUserSegmentEntryId the primary key for the new commerce user segment entry
	* @return the new commerce user segment entry
	*/
	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry createCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId) {
		return _commerceUserSegmentEntryLocalService.createCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	/**
	* Deletes the commerce user segment entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntry the commerce user segment entry
	* @return the commerce user segment entry that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
		com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry commerceUserSegmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryLocalService.deleteCommerceUserSegmentEntry(commerceUserSegmentEntry);
	}

	/**
	* Deletes the commerce user segment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry that was removed
	* @throws PortalException if a commerce user segment entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryLocalService.deleteCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceUserSegmentEntryLocalService.dynamicQuery();
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
		return _commerceUserSegmentEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceUserSegmentEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceUserSegmentEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _commerceUserSegmentEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceUserSegmentEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry fetchCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId) {
		return _commerceUserSegmentEntryLocalService.fetchCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceUserSegmentEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the commerce user segment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment entries
	* @param end the upper bound of the range of commerce user segment entries (not inclusive)
	* @return the range of commerce user segment entries
	*/
	@Override
	public java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		int start, int end) {
		return _commerceUserSegmentEntryLocalService.getCommerceUserSegmentEntries(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> orderByComparator) {
		return _commerceUserSegmentEntryLocalService.getCommerceUserSegmentEntries(groupId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce user segment entries.
	*
	* @return the number of commerce user segment entries
	*/
	@Override
	public int getCommerceUserSegmentEntriesCount() {
		return _commerceUserSegmentEntryLocalService.getCommerceUserSegmentEntriesCount();
	}

	@Override
	public int getCommerceUserSegmentEntriesCount(long groupId) {
		return _commerceUserSegmentEntryLocalService.getCommerceUserSegmentEntriesCount(groupId);
	}

	/**
	* Returns the commerce user segment entry with the primary key.
	*
	* @param commerceUserSegmentEntryId the primary key of the commerce user segment entry
	* @return the commerce user segment entry
	* @throws PortalException if a commerce user segment entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry getCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryLocalService.getCommerceUserSegmentEntry(commerceUserSegmentEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceUserSegmentEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceUserSegmentEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> searchCommerceUserSegmentEntries(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryLocalService.searchCommerceUserSegmentEntries(companyId,
			groupId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry> searchCommerceUserSegmentEntries(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryLocalService.searchCommerceUserSegmentEntries(searchContext);
	}

	/**
	* Updates the commerce user segment entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentEntry the commerce user segment entry
	* @return the commerce user segment entry that was updated
	*/
	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry updateCommerceUserSegmentEntry(
		com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry commerceUserSegmentEntry) {
		return _commerceUserSegmentEntryLocalService.updateCommerceUserSegmentEntry(commerceUserSegmentEntry);
	}

	@Override
	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry updateCommerceUserSegmentEntry(
		long commerceUserSegmentEntryId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		double priority, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceUserSegmentEntryLocalService.updateCommerceUserSegmentEntry(commerceUserSegmentEntryId,
			nameMap, priority, active, serviceContext);
	}

	@Override
	public CommerceUserSegmentEntryLocalService getWrappedService() {
		return _commerceUserSegmentEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceUserSegmentEntryLocalService commerceUserSegmentEntryLocalService) {
		_commerceUserSegmentEntryLocalService = commerceUserSegmentEntryLocalService;
	}

	private CommerceUserSegmentEntryLocalService _commerceUserSegmentEntryLocalService;
}