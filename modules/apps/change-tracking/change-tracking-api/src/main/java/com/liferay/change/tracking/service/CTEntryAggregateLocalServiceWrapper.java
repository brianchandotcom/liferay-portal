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

package com.liferay.change.tracking.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CTEntryAggregateLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryAggregateLocalService
 * @generated
 */
@ProviderType
public class CTEntryAggregateLocalServiceWrapper
	implements CTEntryAggregateLocalService,
			   ServiceWrapper<CTEntryAggregateLocalService> {

	public CTEntryAggregateLocalServiceWrapper(
		CTEntryAggregateLocalService ctEntryAggregateLocalService) {

		_ctEntryAggregateLocalService = ctEntryAggregateLocalService;
	}

	@Override
	public void addCTCollectionCTEntryAggregate(
		long ctCollectionId,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		_ctEntryAggregateLocalService.addCTCollectionCTEntryAggregate(
			ctCollectionId, ctEntryAggregate);
	}

	@Override
	public void addCTCollectionCTEntryAggregate(
		long ctCollectionId, long ctEntryAggregateId) {

		_ctEntryAggregateLocalService.addCTCollectionCTEntryAggregate(
			ctCollectionId, ctEntryAggregateId);
	}

	@Override
	public void addCTCollectionCTEntryAggregates(
		long ctCollectionId,
		java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		_ctEntryAggregateLocalService.addCTCollectionCTEntryAggregates(
			ctCollectionId, ctEntryAggregates);
	}

	@Override
	public void addCTCollectionCTEntryAggregates(
		long ctCollectionId, long[] ctEntryAggregateIds) {

		_ctEntryAggregateLocalService.addCTCollectionCTEntryAggregates(
			ctCollectionId, ctEntryAggregateIds);
	}

	@Override
	public void addCTEntry(
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate,
		com.liferay.change.tracking.model.CTEntry ctEntry) {

		_ctEntryAggregateLocalService.addCTEntry(ctEntryAggregate, ctEntry);
	}

	/**
	 * Adds the ct entry aggregate to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 * @return the ct entry aggregate that was added
	 */
	@Override
	public com.liferay.change.tracking.model.CTEntryAggregate
		addCTEntryAggregate(
			com.liferay.change.tracking.model.CTEntryAggregate
				ctEntryAggregate) {

		return _ctEntryAggregateLocalService.addCTEntryAggregate(
			ctEntryAggregate);
	}

	@Override
	public com.liferay.change.tracking.model.CTEntryAggregate
			addCTEntryAggregate(
				long userId, long ctCollectionId, long ownerCTEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctEntryAggregateLocalService.addCTEntryAggregate(
			userId, ctCollectionId, ownerCTEntryId, serviceContext);
	}

	@Override
	public void addCTEntryCTEntryAggregate(
		long ctEntryId,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		_ctEntryAggregateLocalService.addCTEntryCTEntryAggregate(
			ctEntryId, ctEntryAggregate);
	}

	@Override
	public void addCTEntryCTEntryAggregate(
		long ctEntryId, long ctEntryAggregateId) {

		_ctEntryAggregateLocalService.addCTEntryCTEntryAggregate(
			ctEntryId, ctEntryAggregateId);
	}

	@Override
	public void addCTEntryCTEntryAggregates(
		long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		_ctEntryAggregateLocalService.addCTEntryCTEntryAggregates(
			ctEntryId, ctEntryAggregates);
	}

	@Override
	public void addCTEntryCTEntryAggregates(
		long ctEntryId, long[] ctEntryAggregateIds) {

		_ctEntryAggregateLocalService.addCTEntryCTEntryAggregates(
			ctEntryId, ctEntryAggregateIds);
	}

	@Override
	public void clearCTCollectionCTEntryAggregates(long ctCollectionId) {
		_ctEntryAggregateLocalService.clearCTCollectionCTEntryAggregates(
			ctCollectionId);
	}

	@Override
	public void clearCTEntryCTEntryAggregates(long ctEntryId) {
		_ctEntryAggregateLocalService.clearCTEntryCTEntryAggregates(ctEntryId);
	}

	/**
	 * Creates a new ct entry aggregate with the primary key. Does not add the ct entry aggregate to the database.
	 *
	 * @param ctEntryAggregateId the primary key for the new ct entry aggregate
	 * @return the new ct entry aggregate
	 */
	@Override
	public com.liferay.change.tracking.model.CTEntryAggregate
		createCTEntryAggregate(long ctEntryAggregateId) {

		return _ctEntryAggregateLocalService.createCTEntryAggregate(
			ctEntryAggregateId);
	}

	@Override
	public void deleteCTCollectionCTEntryAggregate(
		long ctCollectionId,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		_ctEntryAggregateLocalService.deleteCTCollectionCTEntryAggregate(
			ctCollectionId, ctEntryAggregate);
	}

	@Override
	public void deleteCTCollectionCTEntryAggregate(
		long ctCollectionId, long ctEntryAggregateId) {

		_ctEntryAggregateLocalService.deleteCTCollectionCTEntryAggregate(
			ctCollectionId, ctEntryAggregateId);
	}

	@Override
	public void deleteCTCollectionCTEntryAggregates(
		long ctCollectionId,
		java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		_ctEntryAggregateLocalService.deleteCTCollectionCTEntryAggregates(
			ctCollectionId, ctEntryAggregates);
	}

	@Override
	public void deleteCTCollectionCTEntryAggregates(
		long ctCollectionId, long[] ctEntryAggregateIds) {

		_ctEntryAggregateLocalService.deleteCTCollectionCTEntryAggregates(
			ctCollectionId, ctEntryAggregateIds);
	}

	/**
	 * Deletes the ct entry aggregate from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 * @return the ct entry aggregate that was removed
	 */
	@Override
	public com.liferay.change.tracking.model.CTEntryAggregate
		deleteCTEntryAggregate(
			com.liferay.change.tracking.model.CTEntryAggregate
				ctEntryAggregate) {

		return _ctEntryAggregateLocalService.deleteCTEntryAggregate(
			ctEntryAggregate);
	}

	/**
	 * Deletes the ct entry aggregate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate that was removed
	 * @throws PortalException if a ct entry aggregate with the primary key could not be found
	 */
	@Override
	public com.liferay.change.tracking.model.CTEntryAggregate
			deleteCTEntryAggregate(long ctEntryAggregateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctEntryAggregateLocalService.deleteCTEntryAggregate(
			ctEntryAggregateId);
	}

	@Override
	public void deleteCTEntryCTEntryAggregate(
		long ctEntryId,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		_ctEntryAggregateLocalService.deleteCTEntryCTEntryAggregate(
			ctEntryId, ctEntryAggregate);
	}

	@Override
	public void deleteCTEntryCTEntryAggregate(
		long ctEntryId, long ctEntryAggregateId) {

		_ctEntryAggregateLocalService.deleteCTEntryCTEntryAggregate(
			ctEntryId, ctEntryAggregateId);
	}

	@Override
	public void deleteCTEntryCTEntryAggregates(
		long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		_ctEntryAggregateLocalService.deleteCTEntryCTEntryAggregates(
			ctEntryId, ctEntryAggregates);
	}

	@Override
	public void deleteCTEntryCTEntryAggregates(
		long ctEntryId, long[] ctEntryAggregateIds) {

		_ctEntryAggregateLocalService.deleteCTEntryCTEntryAggregates(
			ctEntryId, ctEntryAggregateIds);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctEntryAggregateLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ctEntryAggregateLocalService.dynamicQuery();
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

		return _ctEntryAggregateLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _ctEntryAggregateLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _ctEntryAggregateLocalService.dynamicQuery(
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

		return _ctEntryAggregateLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ctEntryAggregateLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.change.tracking.model.CTEntryAggregate
		fetchCTEntryAggregate(long ctEntryAggregateId) {

		return _ctEntryAggregateLocalService.fetchCTEntryAggregate(
			ctEntryAggregateId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
		fetchCTEntryAggregates(long ctCollectionId, long ownerCTEntryId) {

		return _ctEntryAggregateLocalService.fetchCTEntryAggregates(
			ctCollectionId, ownerCTEntryId);
	}

	@Override
	public com.liferay.change.tracking.model.CTEntryAggregate
		fetchLatestCTEntryAggregate(long ctCollectionId, long ownerCTEntryId) {

		return _ctEntryAggregateLocalService.fetchLatestCTEntryAggregate(
			ctCollectionId, ownerCTEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ctEntryAggregateLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTCollectionCTEntryAggregates(long ctCollectionId) {

		return _ctEntryAggregateLocalService.getCTCollectionCTEntryAggregates(
			ctCollectionId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTCollectionCTEntryAggregates(
			long ctCollectionId, int start, int end) {

		return _ctEntryAggregateLocalService.getCTCollectionCTEntryAggregates(
			ctCollectionId, start, end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTCollectionCTEntryAggregates(
			long ctCollectionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTEntryAggregate>
					orderByComparator) {

		return _ctEntryAggregateLocalService.getCTCollectionCTEntryAggregates(
			ctCollectionId, start, end, orderByComparator);
	}

	@Override
	public int getCTCollectionCTEntryAggregatesCount(long ctCollectionId) {
		return _ctEntryAggregateLocalService.
			getCTCollectionCTEntryAggregatesCount(ctCollectionId);
	}

	/**
	 * Returns the ctCollectionIds of the ct collections associated with the ct entry aggregate.
	 *
	 * @param ctEntryAggregateId the ctEntryAggregateId of the ct entry aggregate
	 * @return long[] the ctCollectionIds of ct collections associated with the ct entry aggregate
	 */
	@Override
	public long[] getCTCollectionPrimaryKeys(long ctEntryAggregateId) {
		return _ctEntryAggregateLocalService.getCTCollectionPrimaryKeys(
			ctEntryAggregateId);
	}

	/**
	 * Returns the ct entry aggregate with the primary key.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate
	 * @throws PortalException if a ct entry aggregate with the primary key could not be found
	 */
	@Override
	public com.liferay.change.tracking.model.CTEntryAggregate
			getCTEntryAggregate(long ctEntryAggregateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctEntryAggregateLocalService.getCTEntryAggregate(
			ctEntryAggregateId);
	}

	/**
	 * Returns a range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entry aggregates
	 */
	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTEntryAggregates(int start, int end) {

		return _ctEntryAggregateLocalService.getCTEntryAggregates(start, end);
	}

	/**
	 * Returns the number of ct entry aggregates.
	 *
	 * @return the number of ct entry aggregates
	 */
	@Override
	public int getCTEntryAggregatesCount() {
		return _ctEntryAggregateLocalService.getCTEntryAggregatesCount();
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTEntryCTEntryAggregates(long ctEntryId) {

		return _ctEntryAggregateLocalService.getCTEntryCTEntryAggregates(
			ctEntryId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTEntryCTEntryAggregates(long ctEntryId, int start, int end) {

		return _ctEntryAggregateLocalService.getCTEntryCTEntryAggregates(
			ctEntryId, start, end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryAggregate>
		getCTEntryCTEntryAggregates(
			long ctEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTEntryAggregate>
					orderByComparator) {

		return _ctEntryAggregateLocalService.getCTEntryCTEntryAggregates(
			ctEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCTEntryCTEntryAggregatesCount(long ctEntryId) {
		return _ctEntryAggregateLocalService.getCTEntryCTEntryAggregatesCount(
			ctEntryId);
	}

	/**
	 * Returns the ctEntryIds of the ct entries associated with the ct entry aggregate.
	 *
	 * @param ctEntryAggregateId the ctEntryAggregateId of the ct entry aggregate
	 * @return long[] the ctEntryIds of ct entries associated with the ct entry aggregate
	 */
	@Override
	public long[] getCTEntryPrimaryKeys(long ctEntryAggregateId) {
		return _ctEntryAggregateLocalService.getCTEntryPrimaryKeys(
			ctEntryAggregateId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ctEntryAggregateLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctEntryAggregateLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ctEntryAggregateLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasCTCollectionCTEntryAggregate(
		long ctCollectionId, long ctEntryAggregateId) {

		return _ctEntryAggregateLocalService.hasCTCollectionCTEntryAggregate(
			ctCollectionId, ctEntryAggregateId);
	}

	@Override
	public boolean hasCTCollectionCTEntryAggregates(long ctCollectionId) {
		return _ctEntryAggregateLocalService.hasCTCollectionCTEntryAggregates(
			ctCollectionId);
	}

	@Override
	public boolean hasCTEntry(
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate,
		com.liferay.change.tracking.model.CTEntry ctEntry) {

		return _ctEntryAggregateLocalService.hasCTEntry(
			ctEntryAggregate, ctEntry);
	}

	@Override
	public boolean hasCTEntryCTEntryAggregate(
		long ctEntryId, long ctEntryAggregateId) {

		return _ctEntryAggregateLocalService.hasCTEntryCTEntryAggregate(
			ctEntryId, ctEntryAggregateId);
	}

	@Override
	public boolean hasCTEntryCTEntryAggregates(long ctEntryId) {
		return _ctEntryAggregateLocalService.hasCTEntryCTEntryAggregates(
			ctEntryId);
	}

	@Override
	public void removeCTEntry(
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate,
		com.liferay.change.tracking.model.CTEntry ctEntry) {

		_ctEntryAggregateLocalService.removeCTEntry(ctEntryAggregate, ctEntry);
	}

	@Override
	public void setCTCollectionCTEntryAggregates(
		long ctCollectionId, long[] ctEntryAggregateIds) {

		_ctEntryAggregateLocalService.setCTCollectionCTEntryAggregates(
			ctCollectionId, ctEntryAggregateIds);
	}

	@Override
	public void setCTEntryCTEntryAggregates(
		long ctEntryId, long[] ctEntryAggregateIds) {

		_ctEntryAggregateLocalService.setCTEntryCTEntryAggregates(
			ctEntryId, ctEntryAggregateIds);
	}

	/**
	 * Updates the ct entry aggregate in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 * @return the ct entry aggregate that was updated
	 */
	@Override
	public com.liferay.change.tracking.model.CTEntryAggregate
		updateCTEntryAggregate(
			com.liferay.change.tracking.model.CTEntryAggregate
				ctEntryAggregate) {

		return _ctEntryAggregateLocalService.updateCTEntryAggregate(
			ctEntryAggregate);
	}

	@Override
	public CTEntryAggregateLocalService getWrappedService() {
		return _ctEntryAggregateLocalService;
	}

	@Override
	public void setWrappedService(
		CTEntryAggregateLocalService ctEntryAggregateLocalService) {

		_ctEntryAggregateLocalService = ctEntryAggregateLocalService;
	}

	private CTEntryAggregateLocalService _ctEntryAggregateLocalService;

}