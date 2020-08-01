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

package com.liferay.translation.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link TranslationEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TranslationEntryLocalService
 * @generated
 */
public class TranslationEntryLocalServiceWrapper
	implements ServiceWrapper<TranslationEntryLocalService>,
			   TranslationEntryLocalService {

	public TranslationEntryLocalServiceWrapper(
		TranslationEntryLocalService translationEntryLocalService) {

		_translationEntryLocalService = translationEntryLocalService;
	}

	@Override
	public com.liferay.translation.model.TranslationEntry
			addOrUpdateTranslationEntry(
				long groupId, String languageId,
				com.liferay.info.item.InfoItemReference
					infoItemReference,
				com.liferay.info.item.InfoItemFieldValues infoItemFieldValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryLocalService.addOrUpdateTranslationEntry(
			groupId, languageId, infoItemReference, infoItemFieldValues,
			serviceContext);
	}

	@Override
	public com.liferay.translation.model.TranslationEntry
			addOrUpdateTranslationEntry(
				long groupId, String className, long classPK, String content,
				String contentType, String languageId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryLocalService.addOrUpdateTranslationEntry(
			groupId, className, classPK, content, contentType, languageId,
			serviceContext);
	}

	/**
	 * Adds the translation entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TranslationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param translationEntry the translation entry
	 * @return the translation entry that was added
	 */
	@Override
	public com.liferay.translation.model.TranslationEntry addTranslationEntry(
		com.liferay.translation.model.TranslationEntry translationEntry) {

		return _translationEntryLocalService.addTranslationEntry(
			translationEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new translation entry with the primary key. Does not add the translation entry to the database.
	 *
	 * @param translationEntryId the primary key for the new translation entry
	 * @return the new translation entry
	 */
	@Override
	public com.liferay.translation.model.TranslationEntry
		createTranslationEntry(long translationEntryId) {

		return _translationEntryLocalService.createTranslationEntry(
			translationEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the translation entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TranslationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry that was removed
	 * @throws PortalException if a translation entry with the primary key could not be found
	 */
	@Override
	public com.liferay.translation.model.TranslationEntry
			deleteTranslationEntry(long translationEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryLocalService.deleteTranslationEntry(
			translationEntryId);
	}

	/**
	 * Deletes the translation entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TranslationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param translationEntry the translation entry
	 * @return the translation entry that was removed
	 */
	@Override
	public com.liferay.translation.model.TranslationEntry
		deleteTranslationEntry(
			com.liferay.translation.model.TranslationEntry translationEntry) {

		return _translationEntryLocalService.deleteTranslationEntry(
			translationEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _translationEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _translationEntryLocalService.dynamicQuery();
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

		return _translationEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.translation.model.impl.TranslationEntryModelImpl</code>.
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

		return _translationEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.translation.model.impl.TranslationEntryModelImpl</code>.
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

		return _translationEntryLocalService.dynamicQuery(
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

		return _translationEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _translationEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.translation.model.TranslationEntry fetchTranslationEntry(
		long translationEntryId) {

		return _translationEntryLocalService.fetchTranslationEntry(
			translationEntryId);
	}

	@Override
	public com.liferay.translation.model.TranslationEntry fetchTranslationEntry(
		String className, long classPK, String languageId) {

		return _translationEntryLocalService.fetchTranslationEntry(
			className, classPK, languageId);
	}

	/**
	 * Returns the translation entry matching the UUID and group.
	 *
	 * @param uuid the translation entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	@Override
	public com.liferay.translation.model.TranslationEntry
		fetchTranslationEntryByUuidAndGroupId(String uuid, long groupId) {

		return _translationEntryLocalService.
			fetchTranslationEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _translationEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _translationEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _translationEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.info.item.InfoItemFieldValues getInfoItemFieldValues(
			long groupId, String className, long classPK, String content)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryLocalService.getInfoItemFieldValues(
			groupId, className, classPK, content);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _translationEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the translation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.translation.model.impl.TranslationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @return the range of translation entries
	 */
	@Override
	public java.util.List<com.liferay.translation.model.TranslationEntry>
		getTranslationEntries(int start, int end) {

		return _translationEntryLocalService.getTranslationEntries(start, end);
	}

	/**
	 * Returns all the translation entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the translation entries
	 * @param companyId the primary key of the company
	 * @return the matching translation entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.translation.model.TranslationEntry>
		getTranslationEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return _translationEntryLocalService.
			getTranslationEntriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of translation entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the translation entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of translation entries
	 * @param end the upper bound of the range of translation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching translation entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.translation.model.TranslationEntry>
		getTranslationEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.translation.model.TranslationEntry>
					orderByComparator) {

		return _translationEntryLocalService.
			getTranslationEntriesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of translation entries.
	 *
	 * @return the number of translation entries
	 */
	@Override
	public int getTranslationEntriesCount() {
		return _translationEntryLocalService.getTranslationEntriesCount();
	}

	/**
	 * Returns the translation entry with the primary key.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry
	 * @throws PortalException if a translation entry with the primary key could not be found
	 */
	@Override
	public com.liferay.translation.model.TranslationEntry getTranslationEntry(
			long translationEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryLocalService.getTranslationEntry(
			translationEntryId);
	}

	/**
	 * Returns the translation entry matching the UUID and group.
	 *
	 * @param uuid the translation entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching translation entry
	 * @throws PortalException if a matching translation entry could not be found
	 */
	@Override
	public com.liferay.translation.model.TranslationEntry
			getTranslationEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryLocalService.
			getTranslationEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.translation.model.TranslationEntry updateStatus(
			long userId, long translationEntryId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _translationEntryLocalService.updateStatus(
			userId, translationEntryId, status, serviceContext,
			workflowContext);
	}

	/**
	 * Updates the translation entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TranslationEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param translationEntry the translation entry
	 * @return the translation entry that was updated
	 */
	@Override
	public com.liferay.translation.model.TranslationEntry
		updateTranslationEntry(
			com.liferay.translation.model.TranslationEntry translationEntry) {

		return _translationEntryLocalService.updateTranslationEntry(
			translationEntry);
	}

	@Override
	public TranslationEntryLocalService getWrappedService() {
		return _translationEntryLocalService;
	}

	@Override
	public void setWrappedService(
		TranslationEntryLocalService translationEntryLocalService) {

		_translationEntryLocalService = translationEntryLocalService;
	}

	private TranslationEntryLocalService _translationEntryLocalService;

}