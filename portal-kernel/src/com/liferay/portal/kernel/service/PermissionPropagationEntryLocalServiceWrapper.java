/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.PermissionPropagationEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link PermissionPropagationEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagationEntryLocalService
 * @generated
 */
public class PermissionPropagationEntryLocalServiceWrapper
	implements PermissionPropagationEntryLocalService,
			   ServiceWrapper<PermissionPropagationEntryLocalService> {

	public PermissionPropagationEntryLocalServiceWrapper() {
		this(null);
	}

	public PermissionPropagationEntryLocalServiceWrapper(
		PermissionPropagationEntryLocalService
			permissionPropagationEntryLocalService) {

		_permissionPropagationEntryLocalService =
			permissionPropagationEntryLocalService;
	}

	/**
	 * Adds the permission propagation entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagationEntry the permission propagation entry
	 * @return the permission propagation entry that was added
	 */
	@Override
	public PermissionPropagationEntry addPermissionPropagationEntry(
		PermissionPropagationEntry permissionPropagationEntry) {

		return _permissionPropagationEntryLocalService.
			addPermissionPropagationEntry(permissionPropagationEntry);
	}

	/**
	 * Creates a new permission propagation entry with the primary key. Does not add the permission propagation entry to the database.
	 *
	 * @param permissionPropagationEntryId the primary key for the new permission propagation entry
	 * @return the new permission propagation entry
	 */
	@Override
	public PermissionPropagationEntry createPermissionPropagationEntry(
		long permissionPropagationEntryId) {

		return _permissionPropagationEntryLocalService.
			createPermissionPropagationEntry(permissionPropagationEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _permissionPropagationEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the permission propagation entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry that was removed
	 * @throws PortalException if a permission propagation entry with the primary key could not be found
	 */
	@Override
	public PermissionPropagationEntry deletePermissionPropagationEntry(
			long permissionPropagationEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _permissionPropagationEntryLocalService.
			deletePermissionPropagationEntry(permissionPropagationEntryId);
	}

	/**
	 * Deletes the permission propagation entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagationEntry the permission propagation entry
	 * @return the permission propagation entry that was removed
	 */
	@Override
	public PermissionPropagationEntry deletePermissionPropagationEntry(
		PermissionPropagationEntry permissionPropagationEntry) {

		return _permissionPropagationEntryLocalService.
			deletePermissionPropagationEntry(permissionPropagationEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _permissionPropagationEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _permissionPropagationEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _permissionPropagationEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _permissionPropagationEntryLocalService.dynamicQuery();
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

		return _permissionPropagationEntryLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PermissionPropagationEntryModelImpl</code>.
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

		return _permissionPropagationEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PermissionPropagationEntryModelImpl</code>.
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

		return _permissionPropagationEntryLocalService.dynamicQuery(
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

		return _permissionPropagationEntryLocalService.dynamicQueryCount(
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

		return _permissionPropagationEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public PermissionPropagationEntry fetchPermissionPropagationEntry(
		long permissionPropagationEntryId) {

		return _permissionPropagationEntryLocalService.
			fetchPermissionPropagationEntry(permissionPropagationEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _permissionPropagationEntryLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _permissionPropagationEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _permissionPropagationEntryLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * Returns a range of all the permission propagation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PermissionPropagationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagation entries
	 * @param end the upper bound of the range of permission propagation entries (not inclusive)
	 * @return the range of permission propagation entries
	 */
	@Override
	public java.util.List<PermissionPropagationEntry>
		getPermissionPropagationEntries(int start, int end) {

		return _permissionPropagationEntryLocalService.
			getPermissionPropagationEntries(start, end);
	}

	/**
	 * Returns the number of permission propagation entries.
	 *
	 * @return the number of permission propagation entries
	 */
	@Override
	public int getPermissionPropagationEntriesCount() {
		return _permissionPropagationEntryLocalService.
			getPermissionPropagationEntriesCount();
	}

	/**
	 * Returns the permission propagation entry with the primary key.
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry
	 * @throws PortalException if a permission propagation entry with the primary key could not be found
	 */
	@Override
	public PermissionPropagationEntry getPermissionPropagationEntry(
			long permissionPropagationEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _permissionPropagationEntryLocalService.
			getPermissionPropagationEntry(permissionPropagationEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _permissionPropagationEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the permission propagation entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PermissionPropagationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param permissionPropagationEntry the permission propagation entry
	 * @return the permission propagation entry that was updated
	 */
	@Override
	public PermissionPropagationEntry updatePermissionPropagationEntry(
		PermissionPropagationEntry permissionPropagationEntry) {

		return _permissionPropagationEntryLocalService.
			updatePermissionPropagationEntry(permissionPropagationEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _permissionPropagationEntryLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<PermissionPropagationEntry> getCTPersistence() {
		return _permissionPropagationEntryLocalService.getCTPersistence();
	}

	@Override
	public Class<PermissionPropagationEntry> getModelClass() {
		return _permissionPropagationEntryLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<PermissionPropagationEntry>, R, E>
				updateUnsafeFunction)
		throws E {

		return _permissionPropagationEntryLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public PermissionPropagationEntryLocalService getWrappedService() {
		return _permissionPropagationEntryLocalService;
	}

	@Override
	public void setWrappedService(
		PermissionPropagationEntryLocalService
			permissionPropagationEntryLocalService) {

		_permissionPropagationEntryLocalService =
			permissionPropagationEntryLocalService;
	}

	private PermissionPropagationEntryLocalService
		_permissionPropagationEntryLocalService;

}