/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.email.otp.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for MFAEmailOTPEntry. This utility wraps
 * <code>com.liferay.multi.factor.authentication.email.otp.service.impl.MFAEmailOTPEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Arthur Chan
 * @see MFAEmailOTPEntryLocalService
 * @generated
 */
public class MFAEmailOTPEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.multi.factor.authentication.email.otp.service.impl.MFAEmailOTPEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
				addMFAEmailOTPEntry(long userId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addMFAEmailOTPEntry(userId);
	}

	/**
	 * Adds the mfa email otp entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaEmailOTPEntry the mfa email otp entry
	 * @return the mfa email otp entry that was added
	 */
	public static
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			addMFAEmailOTPEntry(
				com.liferay.multi.factor.authentication.email.otp.model.
					MFAEmailOTPEntry mfaEmailOTPEntry) {

		return getService().addMFAEmailOTPEntry(mfaEmailOTPEntry);
	}

	/**
	 * Creates a new mfa email otp entry with the primary key. Does not add the mfa email otp entry to the database.
	 *
	 * @param mfaEmailOTPEntryId the primary key for the new mfa email otp entry
	 * @return the new mfa email otp entry
	 */
	public static
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			createMFAEmailOTPEntry(long mfaEmailOTPEntryId) {

		return getService().createMFAEmailOTPEntry(mfaEmailOTPEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the mfa email otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry that was removed
	 * @throws PortalException if a mfa email otp entry with the primary key could not be found
	 */
	public static
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
				deleteMFAEmailOTPEntry(long mfaEmailOTPEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteMFAEmailOTPEntry(mfaEmailOTPEntryId);
	}

	/**
	 * Deletes the mfa email otp entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaEmailOTPEntry the mfa email otp entry
	 * @return the mfa email otp entry that was removed
	 */
	public static
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			deleteMFAEmailOTPEntry(
				com.liferay.multi.factor.authentication.email.otp.model.
					MFAEmailOTPEntry mfaEmailOTPEntry) {

		return getService().deleteMFAEmailOTPEntry(mfaEmailOTPEntry);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.multi.factor.authentication.email.otp.model.impl.MFAEmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.multi.factor.authentication.email.otp.model.impl.MFAEmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

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
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			fetchMFAEmailOTPEntry(long mfaEmailOTPEntryId) {

		return getService().fetchMFAEmailOTPEntry(mfaEmailOTPEntryId);
	}

	public static
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			fetchMFAEmailOTPEntryByUserId(long userId) {

		return getService().fetchMFAEmailOTPEntryByUserId(userId);
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
	 * Returns a range of all the mfa email otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.multi.factor.authentication.email.otp.model.impl.MFAEmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa email otp entries
	 * @param end the upper bound of the range of mfa email otp entries (not inclusive)
	 * @return the range of mfa email otp entries
	 */
	public static java.util.List
		<com.liferay.multi.factor.authentication.email.otp.model.
			MFAEmailOTPEntry> getMFAEmailOTPEntries(int start, int end) {

		return getService().getMFAEmailOTPEntries(start, end);
	}

	/**
	 * Returns the number of mfa email otp entries.
	 *
	 * @return the number of mfa email otp entries
	 */
	public static int getMFAEmailOTPEntriesCount() {
		return getService().getMFAEmailOTPEntriesCount();
	}

	/**
	 * Returns the mfa email otp entry with the primary key.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry
	 * @throws PortalException if a mfa email otp entry with the primary key could not be found
	 */
	public static
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
				getMFAEmailOTPEntry(long mfaEmailOTPEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getMFAEmailOTPEntry(mfaEmailOTPEntryId);
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
				resetFailedAttempts(long userId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().resetFailedAttempts(userId);
	}

	public static
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
				updateAttempts(long userId, String ip, boolean success)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateAttempts(userId, ip, success);
	}

	/**
	 * Updates the mfa email otp entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mfaEmailOTPEntry the mfa email otp entry
	 * @return the mfa email otp entry that was updated
	 */
	public static
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			updateMFAEmailOTPEntry(
				com.liferay.multi.factor.authentication.email.otp.model.
					MFAEmailOTPEntry mfaEmailOTPEntry) {

		return getService().updateMFAEmailOTPEntry(mfaEmailOTPEntry);
	}

	public static MFAEmailOTPEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<MFAEmailOTPEntryLocalService, MFAEmailOTPEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			MFAEmailOTPEntryLocalService.class);

		ServiceTracker
			<MFAEmailOTPEntryLocalService, MFAEmailOTPEntryLocalService>
				serviceTracker =
					new ServiceTracker
						<MFAEmailOTPEntryLocalService,
						 MFAEmailOTPEntryLocalService>(
							 bundle.getBundleContext(),
							 MFAEmailOTPEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}