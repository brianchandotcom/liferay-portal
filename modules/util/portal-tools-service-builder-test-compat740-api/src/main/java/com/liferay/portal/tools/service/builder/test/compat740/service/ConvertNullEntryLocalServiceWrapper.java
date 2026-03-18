/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link ConvertNullEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ConvertNullEntryLocalService
 * @generated
 */
public class ConvertNullEntryLocalServiceWrapper
	implements ConvertNullEntryLocalService,
			   ServiceWrapper<ConvertNullEntryLocalService> {

	public ConvertNullEntryLocalServiceWrapper() {
		this(null);
	}

	public ConvertNullEntryLocalServiceWrapper(
		ConvertNullEntryLocalService convertNullEntryLocalService) {

		_convertNullEntryLocalService = convertNullEntryLocalService;
	}

	/**
	 * Adds the convert null entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ConvertNullEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param convertNullEntry the convert null entry
	 * @return the convert null entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat740.model.
		ConvertNullEntry addConvertNullEntry(
			com.liferay.portal.tools.service.builder.test.compat740.model.
				ConvertNullEntry convertNullEntry) {

		return _convertNullEntryLocalService.addConvertNullEntry(
			convertNullEntry);
	}

	/**
	 * Creates a new convert null entry with the primary key. Does not add the convert null entry to the database.
	 *
	 * @param convertNullEntryId the primary key for the new convert null entry
	 * @return the new convert null entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat740.model.
		ConvertNullEntry createConvertNullEntry(long convertNullEntryId) {

		return _convertNullEntryLocalService.createConvertNullEntry(
			convertNullEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _convertNullEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the convert null entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ConvertNullEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param convertNullEntry the convert null entry
	 * @return the convert null entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat740.model.
		ConvertNullEntry deleteConvertNullEntry(
			com.liferay.portal.tools.service.builder.test.compat740.model.
				ConvertNullEntry convertNullEntry) {

		return _convertNullEntryLocalService.deleteConvertNullEntry(
			convertNullEntry);
	}

	/**
	 * Deletes the convert null entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ConvertNullEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param convertNullEntryId the primary key of the convert null entry
	 * @return the convert null entry that was removed
	 * @throws PortalException if a convert null entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat740.model.
		ConvertNullEntry deleteConvertNullEntry(long convertNullEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _convertNullEntryLocalService.deleteConvertNullEntry(
			convertNullEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _convertNullEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _convertNullEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _convertNullEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _convertNullEntryLocalService.dynamicQuery();
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

		return _convertNullEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ConvertNullEntryModelImpl</code>.
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

		return _convertNullEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ConvertNullEntryModelImpl</code>.
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

		return _convertNullEntryLocalService.dynamicQuery(
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

		return _convertNullEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _convertNullEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.compat740.model.
		ConvertNullEntry fetchConvertNullEntry(long convertNullEntryId) {

		return _convertNullEntryLocalService.fetchConvertNullEntry(
			convertNullEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _convertNullEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the convert null entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.ConvertNullEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of convert null entries
	 * @param end the upper bound of the range of convert null entries (not inclusive)
	 * @return the range of convert null entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat740.model.
			ConvertNullEntry> getConvertNullEntries(int start, int end) {

		return _convertNullEntryLocalService.getConvertNullEntries(start, end);
	}

	/**
	 * Returns the number of convert null entries.
	 *
	 * @return the number of convert null entries
	 */
	@Override
	public int getConvertNullEntriesCount() {
		return _convertNullEntryLocalService.getConvertNullEntriesCount();
	}

	/**
	 * Returns the convert null entry with the primary key.
	 *
	 * @param convertNullEntryId the primary key of the convert null entry
	 * @return the convert null entry
	 * @throws PortalException if a convert null entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat740.model.
		ConvertNullEntry getConvertNullEntry(long convertNullEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _convertNullEntryLocalService.getConvertNullEntry(
			convertNullEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _convertNullEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _convertNullEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _convertNullEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the convert null entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ConvertNullEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param convertNullEntry the convert null entry
	 * @return the convert null entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat740.model.
		ConvertNullEntry updateConvertNullEntry(
			com.liferay.portal.tools.service.builder.test.compat740.model.
				ConvertNullEntry convertNullEntry) {

		return _convertNullEntryLocalService.updateConvertNullEntry(
			convertNullEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _convertNullEntryLocalService.getBasePersistence();
	}

	@Override
	public ConvertNullEntryLocalService getWrappedService() {
		return _convertNullEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ConvertNullEntryLocalService convertNullEntryLocalService) {

		_convertNullEntryLocalService = convertNullEntryLocalService;
	}

	private ConvertNullEntryLocalService _convertNullEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-881243104