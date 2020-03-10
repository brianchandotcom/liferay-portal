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

package com.liferay.revert.schema.version.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SchemaEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SchemaEntryLocalService
 * @generated
 */
public class SchemaEntryLocalServiceWrapper
	implements SchemaEntryLocalService,
			   ServiceWrapper<SchemaEntryLocalService> {

	public SchemaEntryLocalServiceWrapper(
		SchemaEntryLocalService schemaEntryLocalService) {

		_schemaEntryLocalService = schemaEntryLocalService;
	}

	/**
	 * Adds the schema entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param schemaEntry the schema entry
	 * @return the schema entry that was added
	 */
	@Override
	public com.liferay.revert.schema.version.model.SchemaEntry addSchemaEntry(
		com.liferay.revert.schema.version.model.SchemaEntry schemaEntry) {

		return _schemaEntryLocalService.addSchemaEntry(schemaEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schemaEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new schema entry with the primary key. Does not add the schema entry to the database.
	 *
	 * @param entryId the primary key for the new schema entry
	 * @return the new schema entry
	 */
	@Override
	public com.liferay.revert.schema.version.model.SchemaEntry
		createSchemaEntry(long entryId) {

		return _schemaEntryLocalService.createSchemaEntry(entryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schemaEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the schema entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the schema entry
	 * @return the schema entry that was removed
	 * @throws PortalException if a schema entry with the primary key could not be found
	 */
	@Override
	public com.liferay.revert.schema.version.model.SchemaEntry
			deleteSchemaEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schemaEntryLocalService.deleteSchemaEntry(entryId);
	}

	/**
	 * Deletes the schema entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schemaEntry the schema entry
	 * @return the schema entry that was removed
	 */
	@Override
	public com.liferay.revert.schema.version.model.SchemaEntry
		deleteSchemaEntry(
			com.liferay.revert.schema.version.model.SchemaEntry schemaEntry) {

		return _schemaEntryLocalService.deleteSchemaEntry(schemaEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _schemaEntryLocalService.dynamicQuery();
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

		return _schemaEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.revert.schema.version.model.impl.SchemaEntryModelImpl</code>.
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

		return _schemaEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.revert.schema.version.model.impl.SchemaEntryModelImpl</code>.
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

		return _schemaEntryLocalService.dynamicQuery(
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

		return _schemaEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _schemaEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.revert.schema.version.model.SchemaEntry fetchSchemaEntry(
		long entryId) {

		return _schemaEntryLocalService.fetchSchemaEntry(entryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _schemaEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _schemaEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _schemaEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schemaEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the schema entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.revert.schema.version.model.impl.SchemaEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schema entries
	 * @param end the upper bound of the range of schema entries (not inclusive)
	 * @return the range of schema entries
	 */
	@Override
	public java.util.List<com.liferay.revert.schema.version.model.SchemaEntry>
		getSchemaEntries(int start, int end) {

		return _schemaEntryLocalService.getSchemaEntries(start, end);
	}

	/**
	 * Returns the number of schema entries.
	 *
	 * @return the number of schema entries
	 */
	@Override
	public int getSchemaEntriesCount() {
		return _schemaEntryLocalService.getSchemaEntriesCount();
	}

	/**
	 * Returns the schema entry with the primary key.
	 *
	 * @param entryId the primary key of the schema entry
	 * @return the schema entry
	 * @throws PortalException if a schema entry with the primary key could not be found
	 */
	@Override
	public com.liferay.revert.schema.version.model.SchemaEntry getSchemaEntry(
			long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _schemaEntryLocalService.getSchemaEntry(entryId);
	}

	/**
	 * Updates the schema entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param schemaEntry the schema entry
	 * @return the schema entry that was updated
	 */
	@Override
	public com.liferay.revert.schema.version.model.SchemaEntry
		updateSchemaEntry(
			com.liferay.revert.schema.version.model.SchemaEntry schemaEntry) {

		return _schemaEntryLocalService.updateSchemaEntry(schemaEntry);
	}

	@Override
	public SchemaEntryLocalService getWrappedService() {
		return _schemaEntryLocalService;
	}

	@Override
	public void setWrappedService(
		SchemaEntryLocalService schemaEntryLocalService) {

		_schemaEntryLocalService = schemaEntryLocalService;
	}

	private SchemaEntryLocalService _schemaEntryLocalService;

}