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

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectActionEntryLocalService}.
 *
 * @author Marco Leo
 * @see ObjectActionEntryLocalService
 * @generated
 */
public class ObjectActionEntryLocalServiceWrapper
	implements ObjectActionEntryLocalService,
			   ServiceWrapper<ObjectActionEntryLocalService> {

	public ObjectActionEntryLocalServiceWrapper(
		ObjectActionEntryLocalService objectActionEntryLocalService) {

		_objectActionEntryLocalService = objectActionEntryLocalService;
	}

	/**
	 * Adds the object action entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectActionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectActionEntry the object action entry
	 * @return the object action entry that was added
	 */
	@Override
	public com.liferay.object.model.ObjectActionEntry addObjectActionEntry(
		com.liferay.object.model.ObjectActionEntry objectActionEntry) {

		return _objectActionEntryLocalService.addObjectActionEntry(
			objectActionEntry);
	}

	/**
	 * Creates a new object action entry with the primary key. Does not add the object action entry to the database.
	 *
	 * @param objectActionEntryId the primary key for the new object action entry
	 * @return the new object action entry
	 */
	@Override
	public com.liferay.object.model.ObjectActionEntry createObjectActionEntry(
		long objectActionEntryId) {

		return _objectActionEntryLocalService.createObjectActionEntry(
			objectActionEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectActionEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the object action entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectActionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectActionEntryId the primary key of the object action entry
	 * @return the object action entry that was removed
	 * @throws PortalException if a object action entry with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectActionEntry deleteObjectActionEntry(
			long objectActionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectActionEntryLocalService.deleteObjectActionEntry(
			objectActionEntryId);
	}

	/**
	 * Deletes the object action entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectActionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectActionEntry the object action entry
	 * @return the object action entry that was removed
	 */
	@Override
	public com.liferay.object.model.ObjectActionEntry deleteObjectActionEntry(
		com.liferay.object.model.ObjectActionEntry objectActionEntry) {

		return _objectActionEntryLocalService.deleteObjectActionEntry(
			objectActionEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectActionEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _objectActionEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _objectActionEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _objectActionEntryLocalService.dynamicQuery();
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

		return _objectActionEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectActionEntryModelImpl</code>.
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

		return _objectActionEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectActionEntryModelImpl</code>.
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

		return _objectActionEntryLocalService.dynamicQuery(
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

		return _objectActionEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _objectActionEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.object.model.ObjectActionEntry fetchObjectActionEntry(
		long objectActionEntryId) {

		return _objectActionEntryLocalService.fetchObjectActionEntry(
			objectActionEntryId);
	}

	/**
	 * Returns the object action entry with the matching UUID and company.
	 *
	 * @param uuid the object action entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectActionEntry
		fetchObjectActionEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _objectActionEntryLocalService.
			fetchObjectActionEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _objectActionEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _objectActionEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectActionEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the object action entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @return the range of object action entries
	 */
	@Override
	public java.util.List<com.liferay.object.model.ObjectActionEntry>
		getObjectActionEntries(int start, int end) {

		return _objectActionEntryLocalService.getObjectActionEntries(
			start, end);
	}

	@Override
	public java.util.List<com.liferay.object.model.ObjectActionEntry>
		getObjectActionEntries(long objectDefinitionId, String triggerName) {

		return _objectActionEntryLocalService.getObjectActionEntries(
			objectDefinitionId, triggerName);
	}

	/**
	 * Returns the number of object action entries.
	 *
	 * @return the number of object action entries
	 */
	@Override
	public int getObjectActionEntriesCount() {
		return _objectActionEntryLocalService.getObjectActionEntriesCount();
	}

	/**
	 * Returns the object action entry with the primary key.
	 *
	 * @param objectActionEntryId the primary key of the object action entry
	 * @return the object action entry
	 * @throws PortalException if a object action entry with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectActionEntry getObjectActionEntry(
			long objectActionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectActionEntryLocalService.getObjectActionEntry(
			objectActionEntryId);
	}

	/**
	 * Returns the object action entry with the matching UUID and company.
	 *
	 * @param uuid the object action entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object action entry
	 * @throws PortalException if a matching object action entry could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectActionEntry
			getObjectActionEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectActionEntryLocalService.
			getObjectActionEntryByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectActionEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectActionEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the object action entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectActionEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectActionEntry the object action entry
	 * @return the object action entry that was updated
	 */
	@Override
	public com.liferay.object.model.ObjectActionEntry updateObjectActionEntry(
		com.liferay.object.model.ObjectActionEntry objectActionEntry) {

		return _objectActionEntryLocalService.updateObjectActionEntry(
			objectActionEntry);
	}

	@Override
	public ObjectActionEntryLocalService getWrappedService() {
		return _objectActionEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ObjectActionEntryLocalService objectActionEntryLocalService) {

		_objectActionEntryLocalService = objectActionEntryLocalService;
	}

	private ObjectActionEntryLocalService _objectActionEntryLocalService;

}