/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link ArrayableEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ArrayableEntryLocalService
 * @generated
 */
public class ArrayableEntryLocalServiceWrapper
	implements ArrayableEntryLocalService,
			   ServiceWrapper<ArrayableEntryLocalService> {

	public ArrayableEntryLocalServiceWrapper(
		ArrayableEntryLocalService arrayableEntryLocalService) {

		_arrayableEntryLocalService = arrayableEntryLocalService;
	}

	/**
	 * Adds the arrayable entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ArrayableEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param arrayableEntry the arrayable entry
	 * @return the arrayable entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat730.model.
			ArrayableEntry addArrayableEntry(
				com.liferay.portal.tools.service.builder.test.compat730.model.
					ArrayableEntry arrayableEntry) {

		return _arrayableEntryLocalService.addArrayableEntry(arrayableEntry);
	}

	/**
	 * Creates a new arrayable entry with the primary key. Does not add the arrayable entry to the database.
	 *
	 * @param arrayableEntryId the primary key for the new arrayable entry
	 * @return the new arrayable entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat730.model.
			ArrayableEntry createArrayableEntry(long arrayableEntryId) {

		return _arrayableEntryLocalService.createArrayableEntry(
			arrayableEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _arrayableEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the arrayable entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ArrayableEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param arrayableEntry the arrayable entry
	 * @return the arrayable entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat730.model.
			ArrayableEntry deleteArrayableEntry(
				com.liferay.portal.tools.service.builder.test.compat730.model.
					ArrayableEntry arrayableEntry) {

		return _arrayableEntryLocalService.deleteArrayableEntry(arrayableEntry);
	}

	/**
	 * Deletes the arrayable entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ArrayableEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param arrayableEntryId the primary key of the arrayable entry
	 * @return the arrayable entry that was removed
	 * @throws PortalException if a arrayable entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat730.model.
			ArrayableEntry deleteArrayableEntry(long arrayableEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _arrayableEntryLocalService.deleteArrayableEntry(
			arrayableEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _arrayableEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _arrayableEntryLocalService.dynamicQuery();
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

		return _arrayableEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat730.model.impl.ArrayableEntryModelImpl</code>.
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

		return _arrayableEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat730.model.impl.ArrayableEntryModelImpl</code>.
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

		return _arrayableEntryLocalService.dynamicQuery(
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

		return _arrayableEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _arrayableEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat730.model.
			ArrayableEntry fetchArrayableEntry(long arrayableEntryId) {

		return _arrayableEntryLocalService.fetchArrayableEntry(
			arrayableEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _arrayableEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the arrayable entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat730.model.impl.ArrayableEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of arrayable entries
	 * @param end the upper bound of the range of arrayable entries (not inclusive)
	 * @return the range of arrayable entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat730.model.
			ArrayableEntry> getArrayableEntries(int start, int end) {

		return _arrayableEntryLocalService.getArrayableEntries(start, end);
	}

	/**
	 * Returns the number of arrayable entries.
	 *
	 * @return the number of arrayable entries
	 */
	@Override
	public int getArrayableEntriesCount() {
		return _arrayableEntryLocalService.getArrayableEntriesCount();
	}

	/**
	 * Returns the arrayable entry with the primary key.
	 *
	 * @param arrayableEntryId the primary key of the arrayable entry
	 * @return the arrayable entry
	 * @throws PortalException if a arrayable entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat730.model.
			ArrayableEntry getArrayableEntry(long arrayableEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _arrayableEntryLocalService.getArrayableEntry(arrayableEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _arrayableEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _arrayableEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _arrayableEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the arrayable entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ArrayableEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param arrayableEntry the arrayable entry
	 * @return the arrayable entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat730.model.
			ArrayableEntry updateArrayableEntry(
				com.liferay.portal.tools.service.builder.test.compat730.model.
					ArrayableEntry arrayableEntry) {

		return _arrayableEntryLocalService.updateArrayableEntry(arrayableEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _arrayableEntryLocalService.getBasePersistence();
	}

	@Override
	public ArrayableEntryLocalService getWrappedService() {
		return _arrayableEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ArrayableEntryLocalService arrayableEntryLocalService) {

		_arrayableEntryLocalService = arrayableEntryLocalService;
	}

	private ArrayableEntryLocalService _arrayableEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1652554386