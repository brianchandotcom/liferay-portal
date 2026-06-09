/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service;

import com.liferay.audience.model.AudienceEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for AudienceEntry. This utility wraps
 * <code>com.liferay.audience.service.impl.AudienceEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AudienceEntryLocalService
 * @generated
 */
public class AudienceEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.audience.service.impl.AudienceEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the audience entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AudienceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param audienceEntry the audience entry
	 * @return the audience entry that was added
	 */
	public static AudienceEntry addAudienceEntry(AudienceEntry audienceEntry) {
		return getService().addAudienceEntry(audienceEntry);
	}

	public static AudienceEntry addAudienceEntry(
			String externalReferenceCode, String json,
			Map<java.util.Locale, String> nameMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addAudienceEntry(
			externalReferenceCode, json, nameMap, serviceContext);
	}

	/**
	 * Creates a new audience entry with the primary key. Does not add the audience entry to the database.
	 *
	 * @param audienceEntryId the primary key for the new audience entry
	 * @return the new audience entry
	 */
	public static AudienceEntry createAudienceEntry(long audienceEntryId) {
		return getService().createAudienceEntry(audienceEntryId);
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
	 * Deletes the audience entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AudienceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param audienceEntry the audience entry
	 * @return the audience entry that was removed
	 * @throws PortalException
	 */
	public static AudienceEntry deleteAudienceEntry(AudienceEntry audienceEntry)
		throws PortalException {

		return getService().deleteAudienceEntry(audienceEntry);
	}

	/**
	 * Deletes the audience entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AudienceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry that was removed
	 * @throws PortalException if a audience entry with the primary key could not be found
	 */
	public static AudienceEntry deleteAudienceEntry(long audienceEntryId)
		throws PortalException {

		return getService().deleteAudienceEntry(audienceEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
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

	public static AudienceEntry fetchAudienceEntry(long audienceEntryId) {
		return getService().fetchAudienceEntry(audienceEntryId);
	}

	public static AudienceEntry fetchAudienceEntryByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return getService().fetchAudienceEntryByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the audience entry with the matching UUID and company.
	 *
	 * @param uuid the audience entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching audience entry, or <code>null</code> if a matching audience entry could not be found
	 */
	public static AudienceEntry fetchAudienceEntryByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchAudienceEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the audience entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.audience.model.impl.AudienceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of audience entries
	 * @param end the upper bound of the range of audience entries (not inclusive)
	 * @return the range of audience entries
	 */
	public static List<AudienceEntry> getAudienceEntries(int start, int end) {
		return getService().getAudienceEntries(start, end);
	}

	public static List<AudienceEntry> getAudienceEntries(
		long companyId, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return getService().getAudienceEntries(
			companyId, start, end, orderByComparator);
	}

	public static List<AudienceEntry> getAudienceEntries(
			long companyId, String name, int start, int end,
			OrderByComparator<AudienceEntry> orderByComparator)
		throws PortalException {

		return getService().getAudienceEntries(
			companyId, name, start, end, orderByComparator);
	}

	/**
	 * Returns the number of audience entries.
	 *
	 * @return the number of audience entries
	 */
	public static int getAudienceEntriesCount() {
		return getService().getAudienceEntriesCount();
	}

	public static int getAudienceEntriesCount(long companyId) {
		return getService().getAudienceEntriesCount(companyId);
	}

	public static int getAudienceEntriesCount(long companyId, String name) {
		return getService().getAudienceEntriesCount(companyId, name);
	}

	/**
	 * Returns the audience entry with the primary key.
	 *
	 * @param audienceEntryId the primary key of the audience entry
	 * @return the audience entry
	 * @throws PortalException if a audience entry with the primary key could not be found
	 */
	public static AudienceEntry getAudienceEntry(long audienceEntryId)
		throws PortalException {

		return getService().getAudienceEntry(audienceEntryId);
	}

	public static AudienceEntry getAudienceEntryByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().getAudienceEntryByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the audience entry with the matching UUID and company.
	 *
	 * @param uuid the audience entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching audience entry
	 * @throws PortalException if a matching audience entry could not be found
	 */
	public static AudienceEntry getAudienceEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getAudienceEntryByUuidAndCompanyId(uuid, companyId);
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
	 * Updates the audience entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AudienceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param audienceEntry the audience entry
	 * @return the audience entry that was updated
	 */
	public static AudienceEntry updateAudienceEntry(
		AudienceEntry audienceEntry) {

		return getService().updateAudienceEntry(audienceEntry);
	}

	public static AudienceEntry updateAudienceEntry(
			long audienceEntryId, String json,
			Map<java.util.Locale, String> nameMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateAudienceEntry(
			audienceEntryId, json, nameMap, serviceContext);
	}

	public static AudienceEntryLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<AudienceEntryLocalService> _serviceSnapshot =
		new Snapshot<>(
			AudienceEntryLocalServiceUtil.class,
			AudienceEntryLocalService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:-1848783454