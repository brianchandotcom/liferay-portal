/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.fido2.credential.service;

import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for MFAFIDO2CredentialEntry. This utility wraps
 * <code>com.liferay.multi.factor.authentication.fido2.credential.service.impl.MFAFIDO2CredentialEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Arthur Chan
 * @see MFAFIDO2CredentialEntryLocalService
 * @generated
 */
public class MFAFIDO2CredentialEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.multi.factor.authentication.fido2.credential.service.impl.MFAFIDO2CredentialEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static MFAFIDO2CredentialEntry addMFAFIDO2CredentialEntry(
			long userId, String credentialKey, int credentialType,
			String publicKeyCOSE)
		throws PortalException {

		return getService().addMFAFIDO2CredentialEntry(
			userId, credentialKey, credentialType, publicKeyCOSE);
	}

	/**
	 * Adds the mfafido2 credential entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MFAFIDO2CredentialEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mfaFIDO2CredentialEntry the mfafido2 credential entry
	 * @return the mfafido2 credential entry that was added
	 */
	public static MFAFIDO2CredentialEntry addMFAFIDO2CredentialEntry(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		return getService().addMFAFIDO2CredentialEntry(mfaFIDO2CredentialEntry);
	}

	/**
	 * Creates a new mfafido2 credential entry with the primary key. Does not add the mfafido2 credential entry to the database.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key for the new mfafido2 credential entry
	 * @return the new mfafido2 credential entry
	 */
	public static MFAFIDO2CredentialEntry createMFAFIDO2CredentialEntry(
		long mfaFIDO2CredentialEntryId) {

		return getService().createMFAFIDO2CredentialEntry(
			mfaFIDO2CredentialEntryId);
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
	 * Deletes the mfafido2 credential entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MFAFIDO2CredentialEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry that was removed
	 * @throws PortalException if a mfafido2 credential entry with the primary key could not be found
	 */
	public static MFAFIDO2CredentialEntry deleteMFAFIDO2CredentialEntry(
			long mfaFIDO2CredentialEntryId)
		throws PortalException {

		return getService().deleteMFAFIDO2CredentialEntry(
			mfaFIDO2CredentialEntryId);
	}

	/**
	 * Deletes the mfafido2 credential entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MFAFIDO2CredentialEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mfaFIDO2CredentialEntry the mfafido2 credential entry
	 * @return the mfafido2 credential entry that was removed
	 */
	public static MFAFIDO2CredentialEntry deleteMFAFIDO2CredentialEntry(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		return getService().deleteMFAFIDO2CredentialEntry(
			mfaFIDO2CredentialEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.multi.factor.authentication.fido2.credential.model.impl.MFAFIDO2CredentialEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.multi.factor.authentication.fido2.credential.model.impl.MFAFIDO2CredentialEntryModelImpl</code>.
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

	public static MFAFIDO2CredentialEntry fetchMFAFIDO2CredentialEntry(
		long mfaFIDO2CredentialEntryId) {

		return getService().fetchMFAFIDO2CredentialEntry(
			mfaFIDO2CredentialEntryId);
	}

	public static MFAFIDO2CredentialEntry
		fetchMFAFIDO2CredentialEntryByUserIdAndCredentialKey(
			long userId, String credentialKey) {

		return getService().
			fetchMFAFIDO2CredentialEntryByUserIdAndCredentialKey(
				userId, credentialKey);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the mfafido2 credential entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.multi.factor.authentication.fido2.credential.model.impl.MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @return the range of mfafido2 credential entries
	 */
	public static List<MFAFIDO2CredentialEntry> getMFAFIDO2CredentialEntries(
		int start, int end) {

		return getService().getMFAFIDO2CredentialEntries(start, end);
	}

	public static List<MFAFIDO2CredentialEntry>
		getMFAFIDO2CredentialEntriesByCredentialKey(String credentialKey) {

		return getService().getMFAFIDO2CredentialEntriesByCredentialKey(
			credentialKey);
	}

	public static List<MFAFIDO2CredentialEntry>
		getMFAFIDO2CredentialEntriesByUserId(long userId) {

		return getService().getMFAFIDO2CredentialEntriesByUserId(userId);
	}

	/**
	 * Returns the number of mfafido2 credential entries.
	 *
	 * @return the number of mfafido2 credential entries
	 */
	public static int getMFAFIDO2CredentialEntriesCount() {
		return getService().getMFAFIDO2CredentialEntriesCount();
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry
	 * @throws PortalException if a mfafido2 credential entry with the primary key could not be found
	 */
	public static MFAFIDO2CredentialEntry getMFAFIDO2CredentialEntry(
			long mfaFIDO2CredentialEntryId)
		throws PortalException {

		return getService().getMFAFIDO2CredentialEntry(
			mfaFIDO2CredentialEntryId);
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

	public static MFAFIDO2CredentialEntry updateAttempts(
			long userId, String credentialKey, long signatureCount)
		throws PortalException {

		return getService().updateAttempts(
			userId, credentialKey, signatureCount);
	}

	/**
	 * Updates the mfafido2 credential entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MFAFIDO2CredentialEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mfaFIDO2CredentialEntry the mfafido2 credential entry
	 * @return the mfafido2 credential entry that was updated
	 */
	public static MFAFIDO2CredentialEntry updateMFAFIDO2CredentialEntry(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		return getService().updateMFAFIDO2CredentialEntry(
			mfaFIDO2CredentialEntry);
	}

	public static MFAFIDO2CredentialEntryLocalService getService() {
		return _service;
	}

	public static void setService(MFAFIDO2CredentialEntryLocalService service) {
		_service = service;
	}

	private static volatile MFAFIDO2CredentialEntryLocalService _service;

}