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
import com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ERCCompanyEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.ERCCompanyEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntryLocalService
 * @generated
 */
public class ERCCompanyEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.ERCCompanyEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the erc company entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntry the erc company entry
	 * @return the erc company entry that was added
	 */
	public static ERCCompanyEntry addERCCompanyEntry(
		ERCCompanyEntry ercCompanyEntry) {

		return getService().addERCCompanyEntry(ercCompanyEntry);
	}

	/**
	 * Creates a new erc company entry with the primary key. Does not add the erc company entry to the database.
	 *
	 * @param ercCompanyEntryId the primary key for the new erc company entry
	 * @return the new erc company entry
	 */
	public static ERCCompanyEntry createERCCompanyEntry(
		long ercCompanyEntryId) {

		return getService().createERCCompanyEntry(ercCompanyEntryId);
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
	 * Deletes the erc company entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntry the erc company entry
	 * @return the erc company entry that was removed
	 */
	public static ERCCompanyEntry deleteERCCompanyEntry(
		ERCCompanyEntry ercCompanyEntry) {

		return getService().deleteERCCompanyEntry(ercCompanyEntry);
	}

	/**
	 * Deletes the erc company entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntryId the primary key of the erc company entry
	 * @return the erc company entry that was removed
	 * @throws PortalException if a erc company entry with the primary key could not be found
	 */
	public static ERCCompanyEntry deleteERCCompanyEntry(long ercCompanyEntryId)
		throws PortalException {

		return getService().deleteERCCompanyEntry(ercCompanyEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntryModelImpl</code>.
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

	public static ERCCompanyEntry fetchERCCompanyEntry(long ercCompanyEntryId) {
		return getService().fetchERCCompanyEntry(ercCompanyEntryId);
	}

	public static ERCCompanyEntry fetchERCCompanyEntryByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return getService().fetchERCCompanyEntryByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the erc company entry with the matching UUID and company.
	 *
	 * @param uuid the erc company entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching erc company entry, or <code>null</code> if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry fetchERCCompanyEntryByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchERCCompanyEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the erc company entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entries
	 * @param end the upper bound of the range of erc company entries (not inclusive)
	 * @return the range of erc company entries
	 */
	public static List<ERCCompanyEntry> getERCCompanyEntries(
		int start, int end) {

		return getService().getERCCompanyEntries(start, end);
	}

	/**
	 * Returns the number of erc company entries.
	 *
	 * @return the number of erc company entries
	 */
	public static int getERCCompanyEntriesCount() {
		return getService().getERCCompanyEntriesCount();
	}

	/**
	 * Returns the erc company entry with the primary key.
	 *
	 * @param ercCompanyEntryId the primary key of the erc company entry
	 * @return the erc company entry
	 * @throws PortalException if a erc company entry with the primary key could not be found
	 */
	public static ERCCompanyEntry getERCCompanyEntry(long ercCompanyEntryId)
		throws PortalException {

		return getService().getERCCompanyEntry(ercCompanyEntryId);
	}

	public static ERCCompanyEntry getERCCompanyEntryByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().getERCCompanyEntryByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the erc company entry with the matching UUID and company.
	 *
	 * @param uuid the erc company entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching erc company entry
	 * @throws PortalException if a matching erc company entry could not be found
	 */
	public static ERCCompanyEntry getERCCompanyEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getERCCompanyEntryByUuidAndCompanyId(
			uuid, companyId);
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
	 * Updates the erc company entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntry the erc company entry
	 * @return the erc company entry that was updated
	 */
	public static ERCCompanyEntry updateERCCompanyEntry(
		ERCCompanyEntry ercCompanyEntry) {

		return getService().updateERCCompanyEntry(ercCompanyEntry);
	}

	public static ERCCompanyEntryLocalService getService() {
		return _service;
	}

	public static void setService(ERCCompanyEntryLocalService service) {
		_service = service;
	}

	private static volatile ERCCompanyEntryLocalService _service;

}