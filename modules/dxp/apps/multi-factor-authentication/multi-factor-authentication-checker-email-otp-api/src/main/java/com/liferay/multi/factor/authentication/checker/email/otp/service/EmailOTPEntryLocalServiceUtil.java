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

package com.liferay.multi.factor.authentication.checker.email.otp.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for EmailOTPEntry. This utility wraps
 * <code>com.liferay.multi.factor.authentication.checker.email.otp.service.impl.EmailOTPEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author arthurchan35
 * @see EmailOTPEntryLocalService
 * @generated
 */
public class EmailOTPEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.multi.factor.authentication.checker.email.otp.service.impl.EmailOTPEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the email otp entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param emailOTPEntry the email otp entry
	 * @return the email otp entry that was added
	 */
	public static
		com.liferay.multi.factor.authentication.checker.email.otp.model.
			EmailOTPEntry addEmailOTPEntry(
				com.liferay.multi.factor.authentication.checker.email.otp.model.
					EmailOTPEntry emailOTPEntry) {

		return getService().addEmailOTPEntry(emailOTPEntry);
	}

	public static
		com.liferay.multi.factor.authentication.checker.email.otp.model.
			EmailOTPEntry addEmailOTPEntry(long userId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEmailOTPEntry(userId);
	}

	/**
	 * Creates a new email otp entry with the primary key. Does not add the email otp entry to the database.
	 *
	 * @param entryId the primary key for the new email otp entry
	 * @return the new email otp entry
	 */
	public static
		com.liferay.multi.factor.authentication.checker.email.otp.model.
			EmailOTPEntry createEmailOTPEntry(long entryId) {

		return getService().createEmailOTPEntry(entryId);
	}

	/**
	 * Deletes the email otp entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param emailOTPEntry the email otp entry
	 * @return the email otp entry that was removed
	 */
	public static
		com.liferay.multi.factor.authentication.checker.email.otp.model.
			EmailOTPEntry deleteEmailOTPEntry(
				com.liferay.multi.factor.authentication.checker.email.otp.model.
					EmailOTPEntry emailOTPEntry) {

		return getService().deleteEmailOTPEntry(emailOTPEntry);
	}

	/**
	 * Deletes the email otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the email otp entry
	 * @return the email otp entry that was removed
	 * @throws PortalException if a email otp entry with the primary key could not be found
	 */
	public static
		com.liferay.multi.factor.authentication.checker.email.otp.model.
			EmailOTPEntry deleteEmailOTPEntry(long entryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteEmailOTPEntry(entryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.multi.factor.authentication.checker.email.otp.model.impl.EmailOTPEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.multi.factor.authentication.checker.email.otp.model.impl.EmailOTPEntryModelImpl</code>.
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
		com.liferay.multi.factor.authentication.checker.email.otp.model.
			EmailOTPEntry fetchEmailOTPEntry(long entryId) {

		return getService().fetchEmailOTPEntry(entryId);
	}

	public static
		com.liferay.multi.factor.authentication.checker.email.otp.model.
			EmailOTPEntry fetchEntryByUserId(long userId) {

		return getService().fetchEntryByUserId(userId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the email otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.multi.factor.authentication.checker.email.otp.model.impl.EmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of email otp entries
	 * @param end the upper bound of the range of email otp entries (not inclusive)
	 * @return the range of email otp entries
	 */
	public static java.util.List
		<com.liferay.multi.factor.authentication.checker.email.otp.model.
			EmailOTPEntry> getEmailOTPEntries(int start, int end) {

		return getService().getEmailOTPEntries(start, end);
	}

	/**
	 * Returns the number of email otp entries.
	 *
	 * @return the number of email otp entries
	 */
	public static int getEmailOTPEntriesCount() {
		return getService().getEmailOTPEntriesCount();
	}

	/**
	 * Returns the email otp entry with the primary key.
	 *
	 * @param entryId the primary key of the email otp entry
	 * @return the email otp entry
	 * @throws PortalException if a email otp entry with the primary key could not be found
	 */
	public static
		com.liferay.multi.factor.authentication.checker.email.otp.model.
			EmailOTPEntry getEmailOTPEntry(long entryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEmailOTPEntry(entryId);
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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void resetFailedAttempts(long userId) {
		getService().resetFailedAttempts(userId);
	}

	public static void updateAttempts(
		long userId, String userIP, boolean success) {

		getService().updateAttempts(userId, userIP, success);
	}

	/**
	 * Updates the email otp entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param emailOTPEntry the email otp entry
	 * @return the email otp entry that was updated
	 */
	public static
		com.liferay.multi.factor.authentication.checker.email.otp.model.
			EmailOTPEntry updateEmailOTPEntry(
				com.liferay.multi.factor.authentication.checker.email.otp.model.
					EmailOTPEntry emailOTPEntry) {

		return getService().updateEmailOTPEntry(emailOTPEntry);
	}

	public static EmailOTPEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<EmailOTPEntryLocalService, EmailOTPEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			EmailOTPEntryLocalService.class);

		ServiceTracker<EmailOTPEntryLocalService, EmailOTPEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<EmailOTPEntryLocalService, EmailOTPEntryLocalService>(
						bundle.getBundleContext(),
						EmailOTPEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}