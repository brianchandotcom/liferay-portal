/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link EagerBlobEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntryLocalService
 * @generated
 */
public class EagerBlobEntryLocalServiceWrapper
	implements EagerBlobEntryLocalService,
			   ServiceWrapper<EagerBlobEntryLocalService> {

	public EagerBlobEntryLocalServiceWrapper() {
		this(null);
	}

	public EagerBlobEntryLocalServiceWrapper(
		EagerBlobEntryLocalService eagerBlobEntryLocalService) {

		_eagerBlobEntryLocalService = eagerBlobEntryLocalService;
	}

	/**
	 * Adds the eager blob entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect EagerBlobEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param eagerBlobEntry the eager blob entry
	 * @return the eager blob entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
		addEagerBlobEntry(
			com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
				eagerBlobEntry) {

		return _eagerBlobEntryLocalService.addEagerBlobEntry(eagerBlobEntry);
	}

	/**
	 * Creates a new eager blob entry with the primary key. Does not add the eager blob entry to the database.
	 *
	 * @param eagerBlobEntryId the primary key for the new eager blob entry
	 * @return the new eager blob entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
		createEagerBlobEntry(long eagerBlobEntryId) {

		return _eagerBlobEntryLocalService.createEagerBlobEntry(
			eagerBlobEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _eagerBlobEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the eager blob entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect EagerBlobEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param eagerBlobEntry the eager blob entry
	 * @return the eager blob entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
		deleteEagerBlobEntry(
			com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
				eagerBlobEntry) {

		return _eagerBlobEntryLocalService.deleteEagerBlobEntry(eagerBlobEntry);
	}

	/**
	 * Deletes the eager blob entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect EagerBlobEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param eagerBlobEntryId the primary key of the eager blob entry
	 * @return the eager blob entry that was removed
	 * @throws PortalException if a eager blob entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
			deleteEagerBlobEntry(long eagerBlobEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _eagerBlobEntryLocalService.deleteEagerBlobEntry(
			eagerBlobEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _eagerBlobEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _eagerBlobEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _eagerBlobEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _eagerBlobEntryLocalService.dynamicQuery();
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

		return _eagerBlobEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.EagerBlobEntryModelImpl</code>.
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

		return _eagerBlobEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.EagerBlobEntryModelImpl</code>.
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

		return _eagerBlobEntryLocalService.dynamicQuery(
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

		return _eagerBlobEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _eagerBlobEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
		fetchEagerBlobEntry(long eagerBlobEntryId) {

		return _eagerBlobEntryLocalService.fetchEagerBlobEntry(
			eagerBlobEntryId);
	}

	/**
	 * Returns the eager blob entry matching the UUID and group.
	 *
	 * @param uuid the eager blob entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching eager blob entry, or <code>null</code> if a matching eager blob entry could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
		fetchEagerBlobEntryByUuidAndGroupId(String uuid, long groupId) {

		return _eagerBlobEntryLocalService.fetchEagerBlobEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _eagerBlobEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the eager blob entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.EagerBlobEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entries
	 * @param end the upper bound of the range of eager blob entries (not inclusive)
	 * @return the range of eager blob entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry>
			getEagerBlobEntries(int start, int end) {

		return _eagerBlobEntryLocalService.getEagerBlobEntries(start, end);
	}

	/**
	 * Returns the number of eager blob entries.
	 *
	 * @return the number of eager blob entries
	 */
	@Override
	public int getEagerBlobEntriesCount() {
		return _eagerBlobEntryLocalService.getEagerBlobEntriesCount();
	}

	/**
	 * Returns the eager blob entry with the primary key.
	 *
	 * @param eagerBlobEntryId the primary key of the eager blob entry
	 * @return the eager blob entry
	 * @throws PortalException if a eager blob entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
			getEagerBlobEntry(long eagerBlobEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _eagerBlobEntryLocalService.getEagerBlobEntry(eagerBlobEntryId);
	}

	/**
	 * Returns the eager blob entry matching the UUID and group.
	 *
	 * @param uuid the eager blob entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching eager blob entry
	 * @throws PortalException if a matching eager blob entry could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
			getEagerBlobEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _eagerBlobEntryLocalService.getEagerBlobEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _eagerBlobEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _eagerBlobEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _eagerBlobEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the eager blob entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect EagerBlobEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param eagerBlobEntry the eager blob entry
	 * @return the eager blob entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
		updateEagerBlobEntry(
			com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry
				eagerBlobEntry) {

		return _eagerBlobEntryLocalService.updateEagerBlobEntry(eagerBlobEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _eagerBlobEntryLocalService.getBasePersistence();
	}

	@Override
	public EagerBlobEntryLocalService getWrappedService() {
		return _eagerBlobEntryLocalService;
	}

	@Override
	public void setWrappedService(
		EagerBlobEntryLocalService eagerBlobEntryLocalService) {

		_eagerBlobEntryLocalService = eagerBlobEntryLocalService;
	}

	private EagerBlobEntryLocalService _eagerBlobEntryLocalService;

}