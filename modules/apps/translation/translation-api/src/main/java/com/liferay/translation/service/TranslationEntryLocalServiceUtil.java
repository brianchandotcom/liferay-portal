/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.translation.model.TranslationEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for TranslationEntry. This utility wraps
 * <code>com.liferay.translation.service.impl.TranslationEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see TranslationEntryLocalService
 * @generated
 */
public class TranslationEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.translation.service.impl.TranslationEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static TranslationEntry addOrUpdateTranslationEntry(
			long groupId, String languageId,
			com.liferay.info.item.InfoItemReference infoItemReference,
			com.liferay.info.item.InfoItemFieldValues infoItemFieldValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateTranslationEntry(
			groupId, languageId, infoItemReference, infoItemFieldValues,
			serviceContext);
	}

	public static TranslationEntry addOrUpdateTranslationEntry(
			long groupId, String className, long classPK, String content,
			String contentType, String languageId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateTranslationEntry(
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
	public static TranslationEntry addTranslationEntry(
		TranslationEntry translationEntry) {

		return getService().addTranslationEntry(translationEntry);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new translation entry with the primary key. Does not add the translation entry to the database.
	 *
	 * @param translationEntryId the primary key for the new translation entry
	 * @return the new translation entry
	 */
	public static TranslationEntry createTranslationEntry(
		long translationEntryId) {

		return getService().createTranslationEntry(translationEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteTranslationEntries(long classNameId, long classPK)
		throws PortalException {

		getService().deleteTranslationEntries(classNameId, classPK);
	}

	public static void deleteTranslationEntries(String className, long classPK)
		throws PortalException {

		getService().deleteTranslationEntries(className, classPK);
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
	public static TranslationEntry deleteTranslationEntry(
			long translationEntryId)
		throws PortalException {

		return getService().deleteTranslationEntry(translationEntryId);
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
	public static TranslationEntry deleteTranslationEntry(
		TranslationEntry translationEntry) {

		return getService().deleteTranslationEntry(translationEntry);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static TranslationEntry fetchTranslationEntry(
		long translationEntryId) {

		return getService().fetchTranslationEntry(translationEntryId);
	}

	public static TranslationEntry fetchTranslationEntry(
		String className, long classPK, String languageId) {

		return getService().fetchTranslationEntry(
			className, classPK, languageId);
	}

	/**
	 * Returns the translation entry matching the UUID and group.
	 *
	 * @param uuid the translation entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching translation entry, or <code>null</code> if a matching translation entry could not be found
	 */
	public static TranslationEntry fetchTranslationEntryByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchTranslationEntryByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static com.liferay.info.item.InfoItemFieldValues
			getInfoItemFieldValues(
				long groupId, String className, long classPK, String content)
		throws PortalException {

		return getService().getInfoItemFieldValues(
			groupId, className, classPK, content);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
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
	public static List<TranslationEntry> getTranslationEntries(
		int start, int end) {

		return getService().getTranslationEntries(start, end);
	}

	/**
	 * Returns all the translation entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the translation entries
	 * @param companyId the primary key of the company
	 * @return the matching translation entries, or an empty list if no matches were found
	 */
	public static List<TranslationEntry>
		getTranslationEntriesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getTranslationEntriesByUuidAndCompanyId(
			uuid, companyId);
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
	public static List<TranslationEntry>
		getTranslationEntriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<TranslationEntry> orderByComparator) {

		return getService().getTranslationEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of translation entries.
	 *
	 * @return the number of translation entries
	 */
	public static int getTranslationEntriesCount() {
		return getService().getTranslationEntriesCount();
	}

	public static int getTranslationEntriesCount(
		String className, long classPK, int[] statuses, boolean exclude) {

		return getService().getTranslationEntriesCount(
			className, classPK, statuses, exclude);
	}

	/**
	 * Returns the translation entry with the primary key.
	 *
	 * @param translationEntryId the primary key of the translation entry
	 * @return the translation entry
	 * @throws PortalException if a translation entry with the primary key could not be found
	 */
	public static TranslationEntry getTranslationEntry(long translationEntryId)
		throws PortalException {

		return getService().getTranslationEntry(translationEntryId);
	}

	/**
	 * Returns the translation entry matching the UUID and group.
	 *
	 * @param uuid the translation entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching translation entry
	 * @throws PortalException if a matching translation entry could not be found
	 */
	public static TranslationEntry getTranslationEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getTranslationEntryByUuidAndGroupId(uuid, groupId);
	}

	public static TranslationEntry updateStatus(
			long userId, long translationEntryId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		return getService().updateStatus(
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
	public static TranslationEntry updateTranslationEntry(
		TranslationEntry translationEntry) {

		return getService().updateTranslationEntry(translationEntry);
	}

	public static TranslationEntryLocalService getService() {
		return _service;
	}

	public static void setService(TranslationEntryLocalService service) {
		_service = service;
	}

	private static volatile TranslationEntryLocalService _service;

}