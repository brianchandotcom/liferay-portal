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

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DefaultScopedEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DefaultScopedEntryLocalService
 * @generated
 */
public class DefaultScopedEntryLocalServiceWrapper
	implements DefaultScopedEntryLocalService,
			   ServiceWrapper<DefaultScopedEntryLocalService> {

	public DefaultScopedEntryLocalServiceWrapper(
		DefaultScopedEntryLocalService defaultScopedEntryLocalService) {

		_defaultScopedEntryLocalService = defaultScopedEntryLocalService;
	}

	/**
	 * Adds the default scoped entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefaultScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param defaultScopedEntry the default scoped entry
	 * @return the default scoped entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			addDefaultScopedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DefaultScopedEntry defaultScopedEntry) {

		return _defaultScopedEntryLocalService.addDefaultScopedEntry(
			defaultScopedEntry);
	}

	/**
	 * Creates a new default scoped entry with the primary key. Does not add the default scoped entry to the database.
	 *
	 * @param DefaultScopedEntryId the primary key for the new default scoped entry
	 * @return the new default scoped entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			createDefaultScopedEntry(long DefaultScopedEntryId) {

		return _defaultScopedEntryLocalService.createDefaultScopedEntry(
			DefaultScopedEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _defaultScopedEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the default scoped entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefaultScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param defaultScopedEntry the default scoped entry
	 * @return the default scoped entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			deleteDefaultScopedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DefaultScopedEntry defaultScopedEntry) {

		return _defaultScopedEntryLocalService.deleteDefaultScopedEntry(
			defaultScopedEntry);
	}

	/**
	 * Deletes the default scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefaultScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry that was removed
	 * @throws PortalException if a default scoped entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
				deleteDefaultScopedEntry(long DefaultScopedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _defaultScopedEntryLocalService.deleteDefaultScopedEntry(
			DefaultScopedEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _defaultScopedEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _defaultScopedEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _defaultScopedEntryLocalService.dynamicQuery();
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

		return _defaultScopedEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefaultScopedEntryModelImpl</code>.
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

		return _defaultScopedEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefaultScopedEntryModelImpl</code>.
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

		return _defaultScopedEntryLocalService.dynamicQuery(
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

		return _defaultScopedEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _defaultScopedEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			fetchDefaultScopedEntry(long DefaultScopedEntryId) {

		return _defaultScopedEntryLocalService.fetchDefaultScopedEntry(
			DefaultScopedEntryId);
	}

	/**
	 * Returns the default scoped entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the default scoped entry's external reference code
	 * @return the matching default scoped entry, or <code>null</code> if a matching default scoped entry could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			fetchDefaultScopedEntryByReferenceCode(
				long companyId, String externalReferenceCode) {

		return _defaultScopedEntryLocalService.
			fetchDefaultScopedEntryByReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _defaultScopedEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the default scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefaultScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of default scoped entries
	 * @param end the upper bound of the range of default scoped entries (not inclusive)
	 * @return the range of default scoped entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry>
			getDefaultScopedEntries(int start, int end) {

		return _defaultScopedEntryLocalService.getDefaultScopedEntries(
			start, end);
	}

	/**
	 * Returns the number of default scoped entries.
	 *
	 * @return the number of default scoped entries
	 */
	@Override
	public int getDefaultScopedEntriesCount() {
		return _defaultScopedEntryLocalService.getDefaultScopedEntriesCount();
	}

	/**
	 * Returns the default scoped entry with the primary key.
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry
	 * @throws PortalException if a default scoped entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
				getDefaultScopedEntry(long DefaultScopedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _defaultScopedEntryLocalService.getDefaultScopedEntry(
			DefaultScopedEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _defaultScopedEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _defaultScopedEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _defaultScopedEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the default scoped entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefaultScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param defaultScopedEntry the default scoped entry
	 * @return the default scoped entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			updateDefaultScopedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DefaultScopedEntry defaultScopedEntry) {

		return _defaultScopedEntryLocalService.updateDefaultScopedEntry(
			defaultScopedEntry);
	}

	@Override
	public DefaultScopedEntryLocalService getWrappedService() {
		return _defaultScopedEntryLocalService;
	}

	@Override
	public void setWrappedService(
		DefaultScopedEntryLocalService defaultScopedEntryLocalService) {

		_defaultScopedEntryLocalService = defaultScopedEntryLocalService;
	}

	private DefaultScopedEntryLocalService _defaultScopedEntryLocalService;

}