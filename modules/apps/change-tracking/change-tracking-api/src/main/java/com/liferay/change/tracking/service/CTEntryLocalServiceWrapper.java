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
 * Provides a wrapper for {@link CTEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryLocalService
 * @generated
 */
@ProviderType
public class CTEntryLocalServiceWrapper implements CTEntryLocalService,
	ServiceWrapper<CTEntryLocalService> {
	public CTEntryLocalServiceWrapper(CTEntryLocalService ctEntryLocalService) {
		_ctEntryLocalService = ctEntryLocalService;
	}

	@Override
	public void addCTCollectionCTEntries(long ctCollectionId,
		java.util.List<com.liferay.change.tracking.model.CTEntry> ctEntries) {
		_ctEntryLocalService.addCTCollectionCTEntries(ctCollectionId, ctEntries);
	}

	@Override
	public void addCTCollectionCTEntries(long ctCollectionId, long[] ctEntryIds) {
		_ctEntryLocalService.addCTCollectionCTEntries(ctCollectionId, ctEntryIds);
	}

	@Override
	public void addCTCollectionCTEntry(long ctCollectionId,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		_ctEntryLocalService.addCTCollectionCTEntry(ctCollectionId, ctEntry);
	}

	@Override
	public void addCTCollectionCTEntry(long ctCollectionId, long ctEntryId) {
		_ctEntryLocalService.addCTCollectionCTEntry(ctCollectionId, ctEntryId);
	}

	/**
	* Adds the ct entry to the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntry the ct entry
	* @return the ct entry that was added
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntry addCTEntry(
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		return _ctEntryLocalService.addCTEntry(ctEntry);
	}

	@Override
	public com.liferay.change.tracking.model.CTEntry addCTEntry(long userId,
		long classNameId, long classPK, long resourcePrimKey, int changeType,
		long ctCollectionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctEntryLocalService.addCTEntry(userId, classNameId, classPK,
			resourcePrimKey, changeType, ctCollectionId, serviceContext);
	}

	@Override
	public void addCTEntryBagCTEntries(long ctEntryBagId,
		java.util.List<com.liferay.change.tracking.model.CTEntry> ctEntries) {
		_ctEntryLocalService.addCTEntryBagCTEntries(ctEntryBagId, ctEntries);
	}

	@Override
	public void addCTEntryBagCTEntries(long ctEntryBagId, long[] ctEntryIds) {
		_ctEntryLocalService.addCTEntryBagCTEntries(ctEntryBagId, ctEntryIds);
	}

	@Override
	public void addCTEntryBagCTEntry(long ctEntryBagId,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		_ctEntryLocalService.addCTEntryBagCTEntry(ctEntryBagId, ctEntry);
	}

	@Override
	public void addCTEntryBagCTEntry(long ctEntryBagId, long ctEntryId) {
		_ctEntryLocalService.addCTEntryBagCTEntry(ctEntryBagId, ctEntryId);
	}

	@Override
	public void clearCTCollectionCTEntries(long ctCollectionId) {
		_ctEntryLocalService.clearCTCollectionCTEntries(ctCollectionId);
	}

	@Override
	public void clearCTEntryBagCTEntries(long ctEntryBagId) {
		_ctEntryLocalService.clearCTEntryBagCTEntries(ctEntryBagId);
	}

	/**
	* Creates a new ct entry with the primary key. Does not add the ct entry to the database.
	*
	* @param ctEntryId the primary key for the new ct entry
	* @return the new ct entry
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntry createCTEntry(
		long ctEntryId) {
		return _ctEntryLocalService.createCTEntry(ctEntryId);
	}

	@Override
	public void deleteCTCollectionCTEntries(long ctCollectionId,
		java.util.List<com.liferay.change.tracking.model.CTEntry> ctEntries) {
		_ctEntryLocalService.deleteCTCollectionCTEntries(ctCollectionId,
			ctEntries);
	}

	@Override
	public void deleteCTCollectionCTEntries(long ctCollectionId,
		long[] ctEntryIds) {
		_ctEntryLocalService.deleteCTCollectionCTEntries(ctCollectionId,
			ctEntryIds);
	}

	@Override
	public void deleteCTCollectionCTEntry(long ctCollectionId,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		_ctEntryLocalService.deleteCTCollectionCTEntry(ctCollectionId, ctEntry);
	}

	@Override
	public void deleteCTCollectionCTEntry(long ctCollectionId, long ctEntryId) {
		_ctEntryLocalService.deleteCTCollectionCTEntry(ctCollectionId, ctEntryId);
	}

	/**
	* Deletes the ct entry from the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntry the ct entry
	* @return the ct entry that was removed
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntry deleteCTEntry(
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		return _ctEntryLocalService.deleteCTEntry(ctEntry);
	}

	/**
	* Deletes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ctEntryId the primary key of the ct entry
	* @return the ct entry that was removed
	* @throws PortalException if a ct entry with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntry deleteCTEntry(
		long ctEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctEntryLocalService.deleteCTEntry(ctEntryId);
	}

	@Override
	public void deleteCTEntryBagCTEntries(long ctEntryBagId,
		java.util.List<com.liferay.change.tracking.model.CTEntry> ctEntries) {
		_ctEntryLocalService.deleteCTEntryBagCTEntries(ctEntryBagId, ctEntries);
	}

	@Override
	public void deleteCTEntryBagCTEntries(long ctEntryBagId, long[] ctEntryIds) {
		_ctEntryLocalService.deleteCTEntryBagCTEntries(ctEntryBagId, ctEntryIds);
	}

	@Override
	public void deleteCTEntryBagCTEntry(long ctEntryBagId,
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		_ctEntryLocalService.deleteCTEntryBagCTEntry(ctEntryBagId, ctEntry);
	}

	@Override
	public void deleteCTEntryBagCTEntry(long ctEntryBagId, long ctEntryId) {
		_ctEntryLocalService.deleteCTEntryBagCTEntry(ctEntryBagId, ctEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ctEntryLocalService.dynamicQuery();
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
		return _ctEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ctEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ctEntryLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _ctEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _ctEntryLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntry> fetchCTEntries(
		long ctCollectionId, long resourcePrimKey,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.change.tracking.model.CTEntry> queryDefinition) {
		return _ctEntryLocalService.fetchCTEntries(ctCollectionId,
			resourcePrimKey, queryDefinition);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntry> fetchCTEntries(
		long ctCollectionId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.change.tracking.model.CTEntry> queryDefinition) {
		return _ctEntryLocalService.fetchCTEntries(ctCollectionId,
			queryDefinition);
	}

	@Override
	public com.liferay.change.tracking.model.CTEntry fetchCTEntry(
		long ctEntryId) {
		return _ctEntryLocalService.fetchCTEntry(ctEntryId);
	}

	@Override
	public com.liferay.change.tracking.model.CTEntry fetchCTEntry(
		long classNameId, long classPK) {
		return _ctEntryLocalService.fetchCTEntry(classNameId, classPK);
	}

	@Override
	public com.liferay.change.tracking.model.CTEntry fetchCTEntry(
		long ctCollectionId, long classNameId, long classPK) {
		return _ctEntryLocalService.fetchCTEntry(ctCollectionId, classNameId,
			classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ctEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntry> getCTCollectionCTEntries(
		long ctCollectionId) {
		return _ctEntryLocalService.getCTCollectionCTEntries(ctCollectionId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntry> getCTCollectionCTEntries(
		long ctCollectionId, int start, int end) {
		return _ctEntryLocalService.getCTCollectionCTEntries(ctCollectionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntry> getCTCollectionCTEntries(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.CTEntry> orderByComparator) {
		return _ctEntryLocalService.getCTCollectionCTEntries(ctCollectionId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntry> getCTCollectionCTEntriesByStatus(
		long ctCollectionId, int status) {
		return _ctEntryLocalService.getCTCollectionCTEntriesByStatus(ctCollectionId,
			status);
	}

	@Override
	public int getCTCollectionCTEntriesCount(long ctCollectionId) {
		return _ctEntryLocalService.getCTCollectionCTEntriesCount(ctCollectionId);
	}

	/**
	* Returns the ctCollectionIds of the ct collections associated with the ct entry.
	*
	* @param ctEntryId the ctEntryId of the ct entry
	* @return long[] the ctCollectionIds of ct collections associated with the ct entry
	*/
	@Override
	public long[] getCTCollectionPrimaryKeys(long ctEntryId) {
		return _ctEntryLocalService.getCTCollectionPrimaryKeys(ctEntryId);
	}

	/**
	* Returns a range of all the ct entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ct entries
	* @param end the upper bound of the range of ct entries (not inclusive)
	* @return the range of ct entries
	*/
	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntry> getCTEntries(
		int start, int end) {
		return _ctEntryLocalService.getCTEntries(start, end);
	}

	/**
	* Returns the number of ct entries.
	*
	* @return the number of ct entries
	*/
	@Override
	public int getCTEntriesCount() {
		return _ctEntryLocalService.getCTEntriesCount();
	}

	/**
	* Returns the ct entry with the primary key.
	*
	* @param ctEntryId the primary key of the ct entry
	* @return the ct entry
	* @throws PortalException if a ct entry with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntry getCTEntry(long ctEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctEntryLocalService.getCTEntry(ctEntryId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntry> getCTEntryBagCTEntries(
		long ctEntryBagId) {
		return _ctEntryLocalService.getCTEntryBagCTEntries(ctEntryBagId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntry> getCTEntryBagCTEntries(
		long ctEntryBagId, int start, int end) {
		return _ctEntryLocalService.getCTEntryBagCTEntries(ctEntryBagId, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.model.CTEntry> getCTEntryBagCTEntries(
		long ctEntryBagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.model.CTEntry> orderByComparator) {
		return _ctEntryLocalService.getCTEntryBagCTEntries(ctEntryBagId, start,
			end, orderByComparator);
	}

	@Override
	public int getCTEntryBagCTEntriesCount(long ctEntryBagId) {
		return _ctEntryLocalService.getCTEntryBagCTEntriesCount(ctEntryBagId);
	}

	/**
	* Returns the ctEntryBagIds of the ct entry bags associated with the ct entry.
	*
	* @param ctEntryId the ctEntryId of the ct entry
	* @return long[] the ctEntryBagIds of ct entry bags associated with the ct entry
	*/
	@Override
	public long[] getCTEntryBagPrimaryKeys(long ctEntryId) {
		return _ctEntryLocalService.getCTEntryBagPrimaryKeys(ctEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _ctEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _ctEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<?extends com.liferay.portal.kernel.model.PersistedModel> getPersistedModel(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctEntryLocalService.getPersistedModel(resourcePrimKey);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ctEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasCTCollectionCTEntries(long ctCollectionId) {
		return _ctEntryLocalService.hasCTCollectionCTEntries(ctCollectionId);
	}

	@Override
	public boolean hasCTCollectionCTEntry(long ctCollectionId, long ctEntryId) {
		return _ctEntryLocalService.hasCTCollectionCTEntry(ctCollectionId,
			ctEntryId);
	}

	@Override
	public boolean hasCTEntryBagCTEntries(long ctEntryBagId) {
		return _ctEntryLocalService.hasCTEntryBagCTEntries(ctEntryBagId);
	}

	@Override
	public boolean hasCTEntryBagCTEntry(long ctEntryBagId, long ctEntryId) {
		return _ctEntryLocalService.hasCTEntryBagCTEntry(ctEntryBagId, ctEntryId);
	}

	@Override
	public void setCTCollectionCTEntries(long ctCollectionId, long[] ctEntryIds) {
		_ctEntryLocalService.setCTCollectionCTEntries(ctCollectionId, ctEntryIds);
	}

	@Override
	public void setCTEntryBagCTEntries(long ctEntryBagId, long[] ctEntryIds) {
		_ctEntryLocalService.setCTEntryBagCTEntries(ctEntryBagId, ctEntryIds);
	}

	/**
	* Updates the ct entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ctEntry the ct entry
	* @return the ct entry that was updated
	*/
	@Override
	public com.liferay.change.tracking.model.CTEntry updateCTEntry(
		com.liferay.change.tracking.model.CTEntry ctEntry) {
		return _ctEntryLocalService.updateCTEntry(ctEntry);
	}

	@Override
	public com.liferay.change.tracking.model.CTEntry updateStatus(
		long ctEntryId, int status) {
		return _ctEntryLocalService.updateStatus(ctEntryId, status);
	}

	@Override
	public CTEntryLocalService getWrappedService() {
		return _ctEntryLocalService;
	}

	@Override
	public void setWrappedService(CTEntryLocalService ctEntryLocalService) {
		_ctEntryLocalService = ctEntryLocalService;
	}

	private CTEntryLocalService _ctEntryLocalService;
}