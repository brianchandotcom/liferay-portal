/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.ERCGroupEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ERCGroupEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.ERCGroupEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntryLocalService
 * @generated
 */
public class ERCGroupEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.ERCGroupEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the erc group entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntry the erc group entry
	 * @return the erc group entry that was added
	 */
	public static ERCGroupEntry addERCGroupEntry(ERCGroupEntry ercGroupEntry) {
		return getService().addERCGroupEntry(ercGroupEntry);
	}

	/**
	 * Creates a new erc group entry with the primary key. Does not add the erc group entry to the database.
	 *
	 * @param ercGroupEntryId the primary key for the new erc group entry
	 * @return the new erc group entry
	 */
	public static ERCGroupEntry createERCGroupEntry(long ercGroupEntryId) {
		return getService().createERCGroupEntry(ercGroupEntryId);
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
	 * Deletes the erc group entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntry the erc group entry
	 * @return the erc group entry that was removed
	 */
	public static ERCGroupEntry deleteERCGroupEntry(
		ERCGroupEntry ercGroupEntry) {

		return getService().deleteERCGroupEntry(ercGroupEntry);
	}

	/**
	 * Deletes the erc group entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry that was removed
	 * @throws PortalException if a erc group entry with the primary key could not be found
	 */
	public static ERCGroupEntry deleteERCGroupEntry(long ercGroupEntryId)
		throws PortalException {

		return getService().deleteERCGroupEntry(ercGroupEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntryModelImpl</code>.
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

	public static ERCGroupEntry fetchERCGroupEntry(long ercGroupEntryId) {
		return getService().fetchERCGroupEntry(ercGroupEntryId);
	}

	public static ERCGroupEntry fetchERCGroupEntryByExternalReferenceCode(
		String externalReferenceCode, long groupId) {

		return getService().fetchERCGroupEntryByExternalReferenceCode(
			externalReferenceCode, groupId);
	}

	/**
	 * Returns the erc group entry matching the UUID and group.
	 *
	 * @param uuid the erc group entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erc group entry, or <code>null</code> if a matching erc group entry could not be found
	 */
	public static ERCGroupEntry fetchERCGroupEntryByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchERCGroupEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the erc group entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @return the range of erc group entries
	 */
	public static List<ERCGroupEntry> getERCGroupEntries(int start, int end) {
		return getService().getERCGroupEntries(start, end);
	}

	/**
	 * Returns all the erc group entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the erc group entries
	 * @param companyId the primary key of the company
	 * @return the matching erc group entries, or an empty list if no matches were found
	 */
	public static List<ERCGroupEntry> getERCGroupEntriesByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getERCGroupEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of erc group entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the erc group entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of erc group entries
	 * @param end the upper bound of the range of erc group entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching erc group entries, or an empty list if no matches were found
	 */
	public static List<ERCGroupEntry> getERCGroupEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCGroupEntry> orderByComparator) {

		return getService().getERCGroupEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of erc group entries.
	 *
	 * @return the number of erc group entries
	 */
	public static int getERCGroupEntriesCount() {
		return getService().getERCGroupEntriesCount();
	}

	/**
	 * Returns the erc group entry with the primary key.
	 *
	 * @param ercGroupEntryId the primary key of the erc group entry
	 * @return the erc group entry
	 * @throws PortalException if a erc group entry with the primary key could not be found
	 */
	public static ERCGroupEntry getERCGroupEntry(long ercGroupEntryId)
		throws PortalException {

		return getService().getERCGroupEntry(ercGroupEntryId);
	}

	public static ERCGroupEntry getERCGroupEntryByExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws PortalException {

		return getService().getERCGroupEntryByExternalReferenceCode(
			externalReferenceCode, groupId);
	}

	/**
	 * Returns the erc group entry matching the UUID and group.
	 *
	 * @param uuid the erc group entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erc group entry
	 * @throws PortalException if a matching erc group entry could not be found
	 */
	public static ERCGroupEntry getERCGroupEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getERCGroupEntryByUuidAndGroupId(uuid, groupId);
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
	 * Updates the erc group entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntry the erc group entry
	 * @return the erc group entry that was updated
	 */
	public static ERCGroupEntry updateERCGroupEntry(
		ERCGroupEntry ercGroupEntry) {

		return getService().updateERCGroupEntry(ercGroupEntry);
	}

	public static ERCGroupEntryLocalService getService() {
		return _service;
	}

	public static void setService(ERCGroupEntryLocalService service) {
		_service = service;
	}

	private static volatile ERCGroupEntryLocalService _service;

}