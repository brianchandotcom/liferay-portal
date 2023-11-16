/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.saved.content.model.SavedContentEntry;

/**
 * Provides a wrapper for {@link SavedContentEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SavedContentEntryLocalService
 * @generated
 */
public class SavedContentEntryLocalServiceWrapper
	implements SavedContentEntryLocalService,
			   ServiceWrapper<SavedContentEntryLocalService> {

	public SavedContentEntryLocalServiceWrapper() {
		this(null);
	}

	public SavedContentEntryLocalServiceWrapper(
		SavedContentEntryLocalService savedContentEntryLocalService) {

		_savedContentEntryLocalService = savedContentEntryLocalService;
	}

	@Override
	public SavedContentEntry addSavedContentEntry(
			long userId, long groupId, String className, long classPK,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryLocalService.addSavedContentEntry(
			userId, groupId, className, classPK, serviceContext);
	}

	/**
	 * Adds the saved content entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntry the saved content entry
	 * @return the saved content entry that was added
	 */
	@Override
	public SavedContentEntry addSavedContentEntry(
		SavedContentEntry savedContentEntry) {

		return _savedContentEntryLocalService.addSavedContentEntry(
			savedContentEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new saved content entry with the primary key. Does not add the saved content entry to the database.
	 *
	 * @param savedContentEntryId the primary key for the new saved content entry
	 * @return the new saved content entry
	 */
	@Override
	public SavedContentEntry createSavedContentEntry(long savedContentEntryId) {
		return _savedContentEntryLocalService.createSavedContentEntry(
			savedContentEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public void deleteSavedContentEntries(
		long groupId, long classNameId, long classPK) {

		_savedContentEntryLocalService.deleteSavedContentEntries(
			groupId, classNameId, classPK);
	}

	@Override
	public void deleteSavedContentEntriesByUserId(long userId) {
		_savedContentEntryLocalService.deleteSavedContentEntriesByUserId(
			userId);
	}

	/**
	 * Deletes the saved content entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntryId the primary key of the saved content entry
	 * @return the saved content entry that was removed
	 * @throws PortalException if a saved content entry with the primary key could not be found
	 */
	@Override
	public SavedContentEntry deleteSavedContentEntry(long savedContentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryLocalService.deleteSavedContentEntry(
			savedContentEntryId);
	}

	/**
	 * Deletes the saved content entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntry the saved content entry
	 * @return the saved content entry that was removed
	 */
	@Override
	public SavedContentEntry deleteSavedContentEntry(
		SavedContentEntry savedContentEntry) {

		return _savedContentEntryLocalService.deleteSavedContentEntry(
			savedContentEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _savedContentEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _savedContentEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _savedContentEntryLocalService.dynamicQuery();
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

		return _savedContentEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saved.content.model.impl.SavedContentEntryModelImpl</code>.
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

		return _savedContentEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saved.content.model.impl.SavedContentEntryModelImpl</code>.
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

		return _savedContentEntryLocalService.dynamicQuery(
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

		return _savedContentEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _savedContentEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public SavedContentEntry fetchSavedContentEntry(long savedContentEntryId) {
		return _savedContentEntryLocalService.fetchSavedContentEntry(
			savedContentEntryId);
	}

	@Override
	public SavedContentEntry fetchSavedContentEntry(
		long userId, long groupId, String className, long classPK) {

		return _savedContentEntryLocalService.fetchSavedContentEntry(
			userId, groupId, className, classPK);
	}

	/**
	 * Returns the saved content entry matching the UUID and group.
	 *
	 * @param uuid the saved content entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching saved content entry, or <code>null</code> if a matching saved content entry could not be found
	 */
	@Override
	public SavedContentEntry fetchSavedContentEntryByUuidAndGroupId(
		String uuid, long groupId) {

		return _savedContentEntryLocalService.
			fetchSavedContentEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _savedContentEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _savedContentEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _savedContentEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _savedContentEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the saved content entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saved.content.model.impl.SavedContentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @return the range of saved content entries
	 */
	@Override
	public java.util.List<SavedContentEntry> getSavedContentEntries(
		int start, int end) {

		return _savedContentEntryLocalService.getSavedContentEntries(
			start, end);
	}

	/**
	 * Returns all the saved content entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the saved content entries
	 * @param companyId the primary key of the company
	 * @return the matching saved content entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<SavedContentEntry>
		getSavedContentEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return _savedContentEntryLocalService.
			getSavedContentEntriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of saved content entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the saved content entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of saved content entries
	 * @param end the upper bound of the range of saved content entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching saved content entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<SavedContentEntry>
		getSavedContentEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator) {

		return _savedContentEntryLocalService.
			getSavedContentEntriesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of saved content entries.
	 *
	 * @return the number of saved content entries
	 */
	@Override
	public int getSavedContentEntriesCount() {
		return _savedContentEntryLocalService.getSavedContentEntriesCount();
	}

	/**
	 * Returns the saved content entry with the primary key.
	 *
	 * @param savedContentEntryId the primary key of the saved content entry
	 * @return the saved content entry
	 * @throws PortalException if a saved content entry with the primary key could not be found
	 */
	@Override
	public SavedContentEntry getSavedContentEntry(long savedContentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryLocalService.getSavedContentEntry(
			savedContentEntryId);
	}

	@Override
	public SavedContentEntry getSavedContentEntry(
			long userId, long groupId, String className, long classPK)
		throws com.liferay.saved.content.exception.
			NoSuchSavedContentEntryException {

		return _savedContentEntryLocalService.getSavedContentEntry(
			userId, groupId, className, classPK);
	}

	/**
	 * Returns the saved content entry matching the UUID and group.
	 *
	 * @param uuid the saved content entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching saved content entry
	 * @throws PortalException if a matching saved content entry could not be found
	 */
	@Override
	public SavedContentEntry getSavedContentEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryLocalService.
			getSavedContentEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Updates the saved content entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SavedContentEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param savedContentEntry the saved content entry
	 * @return the saved content entry that was updated
	 */
	@Override
	public SavedContentEntry updateSavedContentEntry(
		SavedContentEntry savedContentEntry) {

		return _savedContentEntryLocalService.updateSavedContentEntry(
			savedContentEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _savedContentEntryLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<SavedContentEntry> getCTPersistence() {
		return _savedContentEntryLocalService.getCTPersistence();
	}

	@Override
	public Class<SavedContentEntry> getModelClass() {
		return _savedContentEntryLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<SavedContentEntry>, R, E>
				updateUnsafeFunction)
		throws E {

		return _savedContentEntryLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public SavedContentEntryLocalService getWrappedService() {
		return _savedContentEntryLocalService;
	}

	@Override
	public void setWrappedService(
		SavedContentEntryLocalService savedContentEntryLocalService) {

		_savedContentEntryLocalService = savedContentEntryLocalService;
	}

	private SavedContentEntryLocalService _savedContentEntryLocalService;

}