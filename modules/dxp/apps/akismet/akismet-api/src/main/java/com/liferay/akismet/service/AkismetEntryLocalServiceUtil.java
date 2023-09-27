/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.akismet.service;

import com.liferay.akismet.model.AkismetEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for AkismetEntry. This utility wraps
 * <code>com.liferay.akismet.service.impl.AkismetEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Jamie Sammons
 * @see AkismetEntryLocalService
 * @generated
 */
public class AkismetEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.akismet.service.impl.AkismetEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the akismet entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AkismetEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param akismetEntry the akismet entry
	 * @return the akismet entry that was added
	 */
	public static AkismetEntry addAkismetEntry(AkismetEntry akismetEntry) {
		return getService().addAkismetEntry(akismetEntry);
	}

	/**
	 * Creates a new akismet entry with the primary key. Does not add the akismet entry to the database.
	 *
	 * @param akismetEntryId the primary key for the new akismet entry
	 * @return the new akismet entry
	 */
	public static AkismetEntry createAkismetEntry(long akismetEntryId) {
		return getService().createAkismetEntry(akismetEntryId);
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
	 * Deletes the akismet entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AkismetEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param akismetEntry the akismet entry
	 * @return the akismet entry that was removed
	 */
	public static AkismetEntry deleteAkismetEntry(AkismetEntry akismetEntry) {
		return getService().deleteAkismetEntry(akismetEntry);
	}

	public static void deleteAkismetEntry(java.util.Date modifiedDate) {
		getService().deleteAkismetEntry(modifiedDate);
	}

	/**
	 * Deletes the akismet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AkismetEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry that was removed
	 * @throws PortalException if a akismet entry with the primary key could not be found
	 */
	public static AkismetEntry deleteAkismetEntry(long akismetEntryId)
		throws PortalException {

		return getService().deleteAkismetEntry(akismetEntryId);
	}

	public static void deleteAkismetEntry(String className, long classPK)
		throws PortalException {

		getService().deleteAkismetEntry(className, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.akismet.model.impl.AkismetEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.akismet.model.impl.AkismetEntryModelImpl</code>.
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

	public static AkismetEntry fetchAkismetEntry(long akismetEntryId) {
		return getService().fetchAkismetEntry(akismetEntryId);
	}

	public static AkismetEntry fetchAkismetEntry(
		String className, long classPK) {

		return getService().fetchAkismetEntry(className, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the akismet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.akismet.model.impl.AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @return the range of akismet entries
	 */
	public static List<AkismetEntry> getAkismetEntries(int start, int end) {
		return getService().getAkismetEntries(start, end);
	}

	/**
	 * Returns the number of akismet entries.
	 *
	 * @return the number of akismet entries
	 */
	public static int getAkismetEntriesCount() {
		return getService().getAkismetEntriesCount();
	}

	/**
	 * Returns the akismet entry with the primary key.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry
	 * @throws PortalException if a akismet entry with the primary key could not be found
	 */
	public static AkismetEntry getAkismetEntry(long akismetEntryId)
		throws PortalException {

		return getService().getAkismetEntry(akismetEntryId);
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
	 * Updates the akismet entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AkismetEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param akismetEntry the akismet entry
	 * @return the akismet entry that was updated
	 */
	public static AkismetEntry updateAkismetEntry(AkismetEntry akismetEntry) {
		return getService().updateAkismetEntry(akismetEntry);
	}

	public static AkismetEntry updateAkismetEntry(
		String className, long classPK, String type, String permalink,
		String referrer, String userAgent, String userIP, String userURL) {

		return getService().updateAkismetEntry(
			className, classPK, type, permalink, referrer, userAgent, userIP,
			userURL);
	}

	public static AkismetEntryLocalService getService() {
		return _service;
	}

	public static void setService(AkismetEntryLocalService service) {
		_service = service;
	}

	private static volatile AkismetEntryLocalService _service;

}