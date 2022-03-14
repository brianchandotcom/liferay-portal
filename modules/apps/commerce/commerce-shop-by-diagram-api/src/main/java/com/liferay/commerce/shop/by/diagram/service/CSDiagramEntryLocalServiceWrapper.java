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

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CSDiagramEntryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramEntryLocalService
 * @generated
 */
public class CSDiagramEntryLocalServiceWrapper
	implements CSDiagramEntryLocalService,
			   ServiceWrapper<CSDiagramEntryLocalService> {

	public CSDiagramEntryLocalServiceWrapper() {
		this(null);
	}

	public CSDiagramEntryLocalServiceWrapper(
		CSDiagramEntryLocalService csDiagramEntryLocalService) {

		_csDiagramEntryLocalService = csDiagramEntryLocalService;
	}

	/**
	 * Adds the cs diagram entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CSDiagramEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param csDiagramEntry the cs diagram entry
	 * @return the cs diagram entry that was added
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
		addCSDiagramEntry(
			com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
				csDiagramEntry) {

		return _csDiagramEntryLocalService.addCSDiagramEntry(csDiagramEntry);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
			addCSDiagramEntry(
				long userId, long cpDefinitionId, long cpInstanceId,
				long cProductId, boolean diagram, int quantity, String sequence,
				String sku,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryLocalService.addCSDiagramEntry(
			userId, cpDefinitionId, cpInstanceId, cProductId, diagram, quantity,
			sequence, sku, serviceContext);
	}

	/**
	 * Creates a new cs diagram entry with the primary key. Does not add the cs diagram entry to the database.
	 *
	 * @param CSDiagramEntryId the primary key for the new cs diagram entry
	 * @return the new cs diagram entry
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
		createCSDiagramEntry(long CSDiagramEntryId) {

		return _csDiagramEntryLocalService.createCSDiagramEntry(
			CSDiagramEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteCSDiagramEntries(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_csDiagramEntryLocalService.deleteCSDiagramEntries(cpDefinitionId);
	}

	/**
	 * Deletes the cs diagram entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CSDiagramEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param csDiagramEntry the cs diagram entry
	 * @return the cs diagram entry that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
			deleteCSDiagramEntry(
				com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
					csDiagramEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryLocalService.deleteCSDiagramEntry(csDiagramEntry);
	}

	/**
	 * Deletes the cs diagram entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CSDiagramEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CSDiagramEntryId the primary key of the cs diagram entry
	 * @return the cs diagram entry that was removed
	 * @throws PortalException if a cs diagram entry with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
			deleteCSDiagramEntry(long CSDiagramEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryLocalService.deleteCSDiagramEntry(
			CSDiagramEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _csDiagramEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _csDiagramEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _csDiagramEntryLocalService.dynamicQuery();
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

		return _csDiagramEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramEntryModelImpl</code>.
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

		return _csDiagramEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramEntryModelImpl</code>.
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

		return _csDiagramEntryLocalService.dynamicQuery(
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

		return _csDiagramEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _csDiagramEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
		fetchCSDiagramEntry(long CSDiagramEntryId) {

		return _csDiagramEntryLocalService.fetchCSDiagramEntry(
			CSDiagramEntryId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
		fetchCSDiagramEntry(long cpDefinitionId, String sequence) {

		return _csDiagramEntryLocalService.fetchCSDiagramEntry(
			cpDefinitionId, sequence);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _csDiagramEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry>
			getCPDefinitionRelatedCSDiagramEntries(long cpDefinitionId) {

		return _csDiagramEntryLocalService.
			getCPDefinitionRelatedCSDiagramEntries(cpDefinitionId);
	}

	/**
	 * Returns a range of all the cs diagram entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram entries
	 * @param end the upper bound of the range of cs diagram entries (not inclusive)
	 * @return the range of cs diagram entries
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry>
			getCSDiagramEntries(int start, int end) {

		return _csDiagramEntryLocalService.getCSDiagramEntries(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry>
			getCSDiagramEntries(long cpDefinitionId, int start, int end) {

		return _csDiagramEntryLocalService.getCSDiagramEntries(
			cpDefinitionId, start, end);
	}

	/**
	 * Returns the number of cs diagram entries.
	 *
	 * @return the number of cs diagram entries
	 */
	@Override
	public int getCSDiagramEntriesCount() {
		return _csDiagramEntryLocalService.getCSDiagramEntriesCount();
	}

	@Override
	public int getCSDiagramEntriesCount(long cpDefinitionId) {
		return _csDiagramEntryLocalService.getCSDiagramEntriesCount(
			cpDefinitionId);
	}

	/**
	 * Returns the cs diagram entry with the primary key.
	 *
	 * @param CSDiagramEntryId the primary key of the cs diagram entry
	 * @return the cs diagram entry
	 * @throws PortalException if a cs diagram entry with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
			getCSDiagramEntry(long CSDiagramEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryLocalService.getCSDiagramEntry(CSDiagramEntryId);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
			getCSDiagramEntry(long cpDefinitionId, String sequence)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryLocalService.getCSDiagramEntry(
			cpDefinitionId, sequence);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _csDiagramEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _csDiagramEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the cs diagram entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CSDiagramEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param csDiagramEntry the cs diagram entry
	 * @return the cs diagram entry that was updated
	 */
	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
		updateCSDiagramEntry(
			com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
				csDiagramEntry) {

		return _csDiagramEntryLocalService.updateCSDiagramEntry(csDiagramEntry);
	}

	@Override
	public com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry
			updateCSDiagramEntry(
				long csDiagramEntryId, long cpInstanceId, long cProductId,
				boolean diagram, int quantity, String sequence, String sku,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _csDiagramEntryLocalService.updateCSDiagramEntry(
			csDiagramEntryId, cpInstanceId, cProductId, diagram, quantity,
			sequence, sku, serviceContext);
	}

	@Override
	public CSDiagramEntryLocalService getWrappedService() {
		return _csDiagramEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CSDiagramEntryLocalService csDiagramEntryLocalService) {

		_csDiagramEntryLocalService = csDiagramEntryLocalService;
	}

	private CSDiagramEntryLocalService _csDiagramEntryLocalService;

}