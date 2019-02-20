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
 * Provides a wrapper for {@link CTEntryBagLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryBagLocalService
 * @generated
 */
@ProviderType
public class CTEntryBagLocalServiceWrapper implements CTEntryBagLocalService,
	ServiceWrapper<CTEntryBagLocalService> {
	public CTEntryBagLocalServiceWrapper(
		CTEntryBagLocalService ctEntryBagLocalService) {
		_ctEntryBagLocalService = ctEntryBagLocalService;
	}

	@Override
	public void addCTEntry(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		_ctEntryBagLocalService.addCTEntry(ctEntryBag, ctEntry);
	}

	/**
	* Adds the ct entry bag to the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBag the ct entry bag
	* @return the ct entry bag that was added
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntryBag addCTEntryBag(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag) {
		return _ctEntryBagLocalService.addCTEntryBag(ctEntryBag);
	}

	@Override
	public void addCTEntryCTEntryBag(long ctEntryId,
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag) {
		_ctEntryBagLocalService.addCTEntryCTEntryBag(ctEntryId, ctEntryBag);
	}

	@Override
	public void addCTEntryCTEntryBag(long ctEntryId, long ctEntryBagId) {
		_ctEntryBagLocalService.addCTEntryCTEntryBag(ctEntryId, ctEntryBagId);
	}

	@Override
	public void addCTEntryCTEntryBags(long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTEntryBag> ctEntryBags) {
		_ctEntryBagLocalService.addCTEntryCTEntryBags(ctEntryId, ctEntryBags);
	}

	@Override
	public void addCTEntryCTEntryBags(long ctEntryId, long[] ctEntryBagIds) {
		_ctEntryBagLocalService.addCTEntryCTEntryBags(ctEntryId, ctEntryBagIds);
	}

	@Override
	public void clearCTEntryCTEntryBags(long ctEntryId) {
		_ctEntryBagLocalService.clearCTEntryCTEntryBags(ctEntryId);
	}

	/**
	* Creates a new ct entry bag with the primary key. Does not add the ct entry bag to the database.
	*
	* @param ctEntryBagId the primary key for the new ct entry bag
	* @return the new ct entry bag
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntryBag createCTEntryBag(
		long ctEntryBagId) {
		return _ctEntryBagLocalService.createCTEntryBag(ctEntryBagId);
	}

	@Override
	public com.liferay.change.tracking.model.CTEntryBag createCTEntryBag(
		long userId, long ownerCTEntryId, long ctCollectionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctEntryBagLocalService.createCTEntryBag(userId, ownerCTEntryId,
			ctCollectionId, serviceContext);
	}

	/**
	* Deletes the ct entry bag from the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBag the ct entry bag
	* @return the ct entry bag that was removed
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntryBag deleteCTEntryBag(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag) {
		return _ctEntryBagLocalService.deleteCTEntryBag(ctEntryBag);
	}

	/**
	* Deletes the ct entry bag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag that was removed
	* @throws PortalException if a ct entry bag with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntryBag deleteCTEntryBag(
		long ctEntryBagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctEntryBagLocalService.deleteCTEntryBag(ctEntryBagId);
	}

	@Override
	public void deleteCTEntryCTEntryBag(long ctEntryId,
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag) {
		_ctEntryBagLocalService.deleteCTEntryCTEntryBag(ctEntryId, ctEntryBag);
	}

	@Override
	public void deleteCTEntryCTEntryBag(long ctEntryId, long ctEntryBagId) {
		_ctEntryBagLocalService.deleteCTEntryCTEntryBag(ctEntryId, ctEntryBagId);
	}

	@Override
	public void deleteCTEntryCTEntryBags(long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTEntryBag> ctEntryBags) {
		_ctEntryBagLocalService.deleteCTEntryCTEntryBags(ctEntryId, ctEntryBags);
	}

	@Override
	public void deleteCTEntryCTEntryBags(long ctEntryId, long[] ctEntryBagIds) {
		_ctEntryBagLocalService.deleteCTEntryCTEntryBags(ctEntryId,
			ctEntryBagIds);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctEntryBagLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ctEntryBagLocalService.dynamicQuery();
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
		return _ctEntryBagLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ctEntryBagLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ctEntryBagLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _ctEntryBagLocalService.dynamicQueryCount(dynamicQuery);
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
		return _ctEntryBagLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.change.tracking.model.CTEntryBag fetchCTEntryBag(
		long ctEntryBagId) {
		return _ctEntryBagLocalService.fetchCTEntryBag(ctEntryBagId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryBag> fetchCTEntryBags(
		long ownerCTEntryId, long ctCollectionId) {
		return _ctEntryBagLocalService.fetchCTEntryBags(ownerCTEntryId,
			ctCollectionId);
	}

	@Override
	public com.liferay.change.tracking.model.CTEntryBag fetchLatestCTEntryBag(
		long ownerCTEntryId, long ctCollectionId) {
		return _ctEntryBagLocalService.fetchLatestCTEntryBag(ownerCTEntryId,
			ctCollectionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ctEntryBagLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the ct entry bag with the primary key.
	*
	* @param ctEntryBagId the primary key of the ct entry bag
	* @return the ct entry bag
	* @throws PortalException if a ct entry bag with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntryBag getCTEntryBag(
		long ctEntryBagId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctEntryBagLocalService.getCTEntryBag(ctEntryBagId);
	}

	/**
	* Returns a range of all the ct entry bags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryBagModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ct entry bags
	* @param end the upper bound of the range of ct entry bags (not inclusive)
	* @return the range of ct entry bags
	*/
	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryBag> getCTEntryBags(
		int start, int end) {
		return _ctEntryBagLocalService.getCTEntryBags(start, end);
	}

	/**
	* Returns the number of ct entry bags.
	*
	* @return the number of ct entry bags
	*/
	@Override
	public int getCTEntryBagsCount() {
		return _ctEntryBagLocalService.getCTEntryBagsCount();
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryBag> getCTEntryCTEntryBags(
		long ctEntryId) {
		return _ctEntryBagLocalService.getCTEntryCTEntryBags(ctEntryId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryBag> getCTEntryCTEntryBags(
		long ctEntryId, int start, int end) {
		return _ctEntryBagLocalService.getCTEntryCTEntryBags(ctEntryId, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntryBag> getCTEntryCTEntryBags(
		long ctEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.CTEntryBag> orderByComparator) {
		return _ctEntryBagLocalService.getCTEntryCTEntryBags(ctEntryId, start,
			end, orderByComparator);
	}

	@Override
	public int getCTEntryCTEntryBagsCount(long ctEntryId) {
		return _ctEntryBagLocalService.getCTEntryCTEntryBagsCount(ctEntryId);
	}

	/**
	* Returns the ctEntryIds of the ct entries associated with the ct entry bag.
	*
	* @param ctEntryBagId the ctEntryBagId of the ct entry bag
	* @return long[] the ctEntryIds of ct entries associated with the ct entry bag
	*/
	@Override
	public long[] getCTEntryPrimaryKeys(long ctEntryBagId) {
		return _ctEntryBagLocalService.getCTEntryPrimaryKeys(ctEntryBagId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _ctEntryBagLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctEntryBagLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctEntryBagLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasCTEntry(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		return _ctEntryBagLocalService.hasCTEntry(ctEntryBag, ctEntry);
	}

	@Override
	public boolean hasCTEntryCTEntryBag(long ctEntryId, long ctEntryBagId) {
		return _ctEntryBagLocalService.hasCTEntryCTEntryBag(ctEntryId,
			ctEntryBagId);
	}

	@Override
	public boolean hasCTEntryCTEntryBags(long ctEntryId) {
		return _ctEntryBagLocalService.hasCTEntryCTEntryBags(ctEntryId);
	}

	@Override
	public void removeCTEntry(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		_ctEntryBagLocalService.removeCTEntry(ctEntryBag, ctEntry);
	}

	@Override
	public void setCTEntryCTEntryBags(long ctEntryId, long[] ctEntryBagIds) {
		_ctEntryBagLocalService.setCTEntryCTEntryBags(ctEntryId, ctEntryBagIds);
	}

	/**
	* Updates the ct entry bag in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ctEntryBag the ct entry bag
	* @return the ct entry bag that was updated
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntryBag updateCTEntryBag(
		com.liferay.change.tracking.model.CTEntryBag ctEntryBag) {
		return _ctEntryBagLocalService.updateCTEntryBag(ctEntryBag);
	}

	@Override
	public CTEntryBagLocalService getWrappedService() {
		return _ctEntryBagLocalService;
	}

	@Override
	public void setWrappedService(CTEntryBagLocalService ctEntryBagLocalService) {
		_ctEntryBagLocalService = ctEntryBagLocalService;
	}

	private CTEntryBagLocalService _ctEntryBagLocalService;
}