/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link DefinedDefaultOrderEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DefinedDefaultOrderEntryLocalService
 * @generated
 */
public class DefinedDefaultOrderEntryLocalServiceWrapper
	implements DefinedDefaultOrderEntryLocalService,
			   ServiceWrapper<DefinedDefaultOrderEntryLocalService> {

	public DefinedDefaultOrderEntryLocalServiceWrapper() {
		this(null);
	}

	public DefinedDefaultOrderEntryLocalServiceWrapper(
		DefinedDefaultOrderEntryLocalService
			definedDefaultOrderEntryLocalService) {

		_definedDefaultOrderEntryLocalService =
			definedDefaultOrderEntryLocalService;
	}

	/**
	 * Adds the defined default order entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param definedDefaultOrderEntry the defined default order entry
	 * @return the defined default order entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			DefinedDefaultOrderEntry addDefinedDefaultOrderEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		return _definedDefaultOrderEntryLocalService.
			addDefinedDefaultOrderEntry(definedDefaultOrderEntry);
	}

	/**
	 * Creates a new defined default order entry with the primary key. Does not add the defined default order entry to the database.
	 *
	 * @param definedDefaultOrderEntryId the primary key for the new defined default order entry
	 * @return the new defined default order entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			DefinedDefaultOrderEntry createDefinedDefaultOrderEntry(
				long definedDefaultOrderEntryId) {

		return _definedDefaultOrderEntryLocalService.
			createDefinedDefaultOrderEntry(definedDefaultOrderEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _definedDefaultOrderEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the defined default order entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param definedDefaultOrderEntry the defined default order entry
	 * @return the defined default order entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			DefinedDefaultOrderEntry deleteDefinedDefaultOrderEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		return _definedDefaultOrderEntryLocalService.
			deleteDefinedDefaultOrderEntry(definedDefaultOrderEntry);
	}

	/**
	 * Deletes the defined default order entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry that was removed
	 * @throws PortalException if a defined default order entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			DefinedDefaultOrderEntry deleteDefinedDefaultOrderEntry(
					long definedDefaultOrderEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _definedDefaultOrderEntryLocalService.
			deleteDefinedDefaultOrderEntry(definedDefaultOrderEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _definedDefaultOrderEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _definedDefaultOrderEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _definedDefaultOrderEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _definedDefaultOrderEntryLocalService.dynamicQuery();
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

		return _definedDefaultOrderEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefinedDefaultOrderEntryModelImpl</code>.
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

		return _definedDefaultOrderEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefinedDefaultOrderEntryModelImpl</code>.
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

		return _definedDefaultOrderEntryLocalService.dynamicQuery(
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

		return _definedDefaultOrderEntryLocalService.dynamicQueryCount(
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

		return _definedDefaultOrderEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			DefinedDefaultOrderEntry fetchDefinedDefaultOrderEntry(
				long definedDefaultOrderEntryId) {

		return _definedDefaultOrderEntryLocalService.
			fetchDefinedDefaultOrderEntry(definedDefaultOrderEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _definedDefaultOrderEntryLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the defined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @return the range of defined default order entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			DefinedDefaultOrderEntry> getDefinedDefaultOrderEntries(
				int start, int end) {

		return _definedDefaultOrderEntryLocalService.
			getDefinedDefaultOrderEntries(start, end);
	}

	/**
	 * Returns the number of defined default order entries.
	 *
	 * @return the number of defined default order entries
	 */
	@Override
	public int getDefinedDefaultOrderEntriesCount() {
		return _definedDefaultOrderEntryLocalService.
			getDefinedDefaultOrderEntriesCount();
	}

	/**
	 * Returns the defined default order entry with the primary key.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry
	 * @throws PortalException if a defined default order entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			DefinedDefaultOrderEntry getDefinedDefaultOrderEntry(
					long definedDefaultOrderEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _definedDefaultOrderEntryLocalService.
			getDefinedDefaultOrderEntry(definedDefaultOrderEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _definedDefaultOrderEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _definedDefaultOrderEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _definedDefaultOrderEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the defined default order entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param definedDefaultOrderEntry the defined default order entry
	 * @return the defined default order entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			DefinedDefaultOrderEntry updateDefinedDefaultOrderEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		return _definedDefaultOrderEntryLocalService.
			updateDefinedDefaultOrderEntry(definedDefaultOrderEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _definedDefaultOrderEntryLocalService.getBasePersistence();
	}

	@Override
	public DefinedDefaultOrderEntryLocalService getWrappedService() {
		return _definedDefaultOrderEntryLocalService;
	}

	@Override
	public void setWrappedService(
		DefinedDefaultOrderEntryLocalService
			definedDefaultOrderEntryLocalService) {

		_definedDefaultOrderEntryLocalService =
			definedDefaultOrderEntryLocalService;
	}

	private DefinedDefaultOrderEntryLocalService
		_definedDefaultOrderEntryLocalService;

}
// SB-Hash:2035236040