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
 * Provides a wrapper for {@link CTCollectionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionLocalService
 * @generated
 */
@ProviderType
public class CTCollectionLocalServiceWrapper implements CTCollectionLocalService,
	ServiceWrapper<CTCollectionLocalService> {
	public CTCollectionLocalServiceWrapper(
		CTCollectionLocalService ctCollectionLocalService) {
		_ctCollectionLocalService = ctCollectionLocalService;
	}

	/**
	* Adds the ct collection to the database. Also notifies the appropriate model listeners.
	*
	* @param ctCollection the ct collection
	* @return the ct collection that was added
	*/
	@Override
	public com.liferay.change.tracking.model.CTCollection addCTCollection(
		com.liferay.change.tracking.model.CTCollection ctCollection) {
		return _ctCollectionLocalService.addCTCollection(ctCollection);
	}

	@Override
	public com.liferay.change.tracking.model.CTCollection addCTCollection(
		long userId, String name, String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctCollectionLocalService.addCTCollection(userId, name,
			description, serviceContext);
	}

	@Override
	public void addCTEntryCTCollection(long ctEntryId,
		com.liferay.change.tracking.model.CTCollection ctCollection) {
		_ctCollectionLocalService.addCTEntryCTCollection(ctEntryId, ctCollection);
	}

	@Override
	public void addCTEntryCTCollection(long ctEntryId, long ctCollectionId) {
		_ctCollectionLocalService.addCTEntryCTCollection(ctEntryId,
			ctCollectionId);
	}

	@Override
	public void addCTEntryCTCollections(long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTCollection> ctCollections) {
		_ctCollectionLocalService.addCTEntryCTCollections(ctEntryId,
			ctCollections);
	}

	@Override
	public void addCTEntryCTCollections(long ctEntryId, long[] ctCollectionIds) {
		_ctCollectionLocalService.addCTEntryCTCollections(ctEntryId,
			ctCollectionIds);
	}

	@Override
	public void clearCTEntryCTCollections(long ctEntryId) {
		_ctCollectionLocalService.clearCTEntryCTCollections(ctEntryId);
	}

	/**
	* Creates a new ct collection with the primary key. Does not add the ct collection to the database.
	*
	* @param ctCollectionId the primary key for the new ct collection
	* @return the new ct collection
	*/
	@Override
	public com.liferay.change.tracking.model.CTCollection createCTCollection(
		long ctCollectionId) {
		return _ctCollectionLocalService.createCTCollection(ctCollectionId);
	}

	@Override
	public void deleteCompanyCTCollections(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ctCollectionLocalService.deleteCompanyCTCollections(companyId);
	}

	/**
	* Deletes the ct collection from the database. Also notifies the appropriate model listeners.
	*
	* @param ctCollection the ct collection
	* @return the ct collection that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.change.tracking.model.CTCollection deleteCTCollection(
		com.liferay.change.tracking.model.CTCollection ctCollection)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctCollectionLocalService.deleteCTCollection(ctCollection);
	}

	/**
	* Deletes the ct collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ctCollectionId the primary key of the ct collection
	* @return the ct collection that was removed
	* @throws PortalException if a ct collection with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.model.CTCollection deleteCTCollection(
		long ctCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctCollectionLocalService.deleteCTCollection(ctCollectionId);
	}

	@Override
	public void deleteCTEntryCTCollection(long ctEntryId,
		com.liferay.change.tracking.model.CTCollection ctCollection) {
		_ctCollectionLocalService.deleteCTEntryCTCollection(ctEntryId,
			ctCollection);
	}

	@Override
	public void deleteCTEntryCTCollection(long ctEntryId, long ctCollectionId) {
		_ctCollectionLocalService.deleteCTEntryCTCollection(ctEntryId,
			ctCollectionId);
	}

	@Override
	public void deleteCTEntryCTCollections(long ctEntryId,
		java.util.List<com.liferay.change.tracking.model.CTCollection> ctCollections) {
		_ctCollectionLocalService.deleteCTEntryCTCollections(ctEntryId,
			ctCollections);
	}

	@Override
	public void deleteCTEntryCTCollections(long ctEntryId,
		long[] ctCollectionIds) {
		_ctCollectionLocalService.deleteCTEntryCTCollections(ctEntryId,
			ctCollectionIds);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctCollectionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ctCollectionLocalService.dynamicQuery();
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
		return _ctCollectionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.CTCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ctCollectionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.CTCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ctCollectionLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _ctCollectionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _ctCollectionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.change.tracking.model.CTCollection fetchCTCollection(
		long ctCollectionId) {
		return _ctCollectionLocalService.fetchCTCollection(ctCollectionId);
	}

	@Override
	public com.liferay.change.tracking.model.CTCollection fetchCTCollection(
		long companyId, String name) {
		return _ctCollectionLocalService.fetchCTCollection(companyId, name);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ctCollectionLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the ct collection with the primary key.
	*
	* @param ctCollectionId the primary key of the ct collection
	* @return the ct collection
	* @throws PortalException if a ct collection with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.model.CTCollection getCTCollection(
		long ctCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctCollectionLocalService.getCTCollection(ctCollectionId);
	}

	/**
	* Returns a range of all the ct collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.model.impl.CTCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ct collections
	* @param end the upper bound of the range of ct collections (not inclusive)
	* @return the range of ct collections
	*/
	@Override
	public java.util.List<com.liferay.change.tracking.model.CTCollection> getCTCollections(
		int start, int end) {
		return _ctCollectionLocalService.getCTCollections(start, end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTCollection> getCTCollections(
		long companyId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.change.tracking.model.CTCollection> queryDefinition) {
		return _ctCollectionLocalService.getCTCollections(companyId,
			queryDefinition);
	}

	/**
	* Returns the number of ct collections.
	*
	* @return the number of ct collections
	*/
	@Override
	public int getCTCollectionsCount() {
		return _ctCollectionLocalService.getCTCollectionsCount();
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTCollection> getCTEntryCTCollections(
		long ctEntryId) {
		return _ctCollectionLocalService.getCTEntryCTCollections(ctEntryId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTCollection> getCTEntryCTCollections(
		long ctEntryId, int start, int end) {
		return _ctCollectionLocalService.getCTEntryCTCollections(ctEntryId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTCollection> getCTEntryCTCollections(
		long ctEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.CTCollection> orderByComparator) {
		return _ctCollectionLocalService.getCTEntryCTCollections(ctEntryId,
			start, end, orderByComparator);
	}

	@Override
	public int getCTEntryCTCollectionsCount(long ctEntryId) {
		return _ctCollectionLocalService.getCTEntryCTCollectionsCount(ctEntryId);
	}

	/**
	* Returns the ctEntryIds of the ct entries associated with the ct collection.
	*
	* @param ctCollectionId the ctCollectionId of the ct collection
	* @return long[] the ctEntryIds of ct entries associated with the ct collection
	*/
	@Override
	public long[] getCTEntryPrimaryKeys(long ctCollectionId) {
		return _ctCollectionLocalService.getCTEntryPrimaryKeys(ctCollectionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _ctCollectionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctCollectionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctCollectionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasCTEntryCTCollection(long ctEntryId, long ctCollectionId) {
		return _ctCollectionLocalService.hasCTEntryCTCollection(ctEntryId,
			ctCollectionId);
	}

	@Override
	public boolean hasCTEntryCTCollections(long ctEntryId) {
		return _ctCollectionLocalService.hasCTEntryCTCollections(ctEntryId);
	}

	@Override
	public void setCTEntryCTCollections(long ctEntryId, long[] ctCollectionIds) {
		_ctCollectionLocalService.setCTEntryCTCollections(ctEntryId,
			ctCollectionIds);
	}

	/**
	* Updates the ct collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ctCollection the ct collection
	* @return the ct collection that was updated
	*/
	@Override
	public com.liferay.change.tracking.model.CTCollection updateCTCollection(
		com.liferay.change.tracking.model.CTCollection ctCollection) {
		return _ctCollectionLocalService.updateCTCollection(ctCollection);
	}

	@Override
	public com.liferay.change.tracking.model.CTCollection updateStatus(
		long userId,
		com.liferay.change.tracking.model.CTCollection ctCollection,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctCollectionLocalService.updateStatus(userId, ctCollection,
			status, serviceContext);
	}

	@Override
	public CTCollectionLocalService getWrappedService() {
		return _ctCollectionLocalService;
	}

	@Override
	public void setWrappedService(
		CTCollectionLocalService ctCollectionLocalService) {
		_ctCollectionLocalService = ctCollectionLocalService;
	}

	private CTCollectionLocalService _ctCollectionLocalService;
}