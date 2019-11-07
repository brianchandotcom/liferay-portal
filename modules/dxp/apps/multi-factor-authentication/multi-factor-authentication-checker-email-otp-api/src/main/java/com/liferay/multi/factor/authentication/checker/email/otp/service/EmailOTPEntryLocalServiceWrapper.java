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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link EmailOTPEntryLocalService}.
 *
 * @author arthurchan35
 * @see EmailOTPEntryLocalService
 * @generated
 */
public class EmailOTPEntryLocalServiceWrapper
	implements EmailOTPEntryLocalService,
			   ServiceWrapper<EmailOTPEntryLocalService> {

	public EmailOTPEntryLocalServiceWrapper(
		EmailOTPEntryLocalService emailOTPEntryLocalService) {

		_emailOTPEntryLocalService = emailOTPEntryLocalService;
	}

	/**
	 * Adds the email otp entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param emailOTPEntry the email otp entry
	 * @return the email otp entry that was added
	 */
	@Override
	public com.liferay.multi.factor.authentication.checker.email.otp.model.
		EmailOTPEntry addEmailOTPEntry(
			com.liferay.multi.factor.authentication.checker.email.otp.model.
				EmailOTPEntry emailOTPEntry) {

		return _emailOTPEntryLocalService.addEmailOTPEntry(emailOTPEntry);
	}

	@Override
	public com.liferay.multi.factor.authentication.checker.email.otp.model.
		EmailOTPEntry addEmailOTPEntry(long userId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _emailOTPEntryLocalService.addEmailOTPEntry(userId);
	}

	/**
	 * Creates a new email otp entry with the primary key. Does not add the email otp entry to the database.
	 *
	 * @param entryId the primary key for the new email otp entry
	 * @return the new email otp entry
	 */
	@Override
	public com.liferay.multi.factor.authentication.checker.email.otp.model.
		EmailOTPEntry createEmailOTPEntry(long entryId) {

		return _emailOTPEntryLocalService.createEmailOTPEntry(entryId);
	}

	/**
	 * Deletes the email otp entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param emailOTPEntry the email otp entry
	 * @return the email otp entry that was removed
	 */
	@Override
	public com.liferay.multi.factor.authentication.checker.email.otp.model.
		EmailOTPEntry deleteEmailOTPEntry(
			com.liferay.multi.factor.authentication.checker.email.otp.model.
				EmailOTPEntry emailOTPEntry) {

		return _emailOTPEntryLocalService.deleteEmailOTPEntry(emailOTPEntry);
	}

	/**
	 * Deletes the email otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the email otp entry
	 * @return the email otp entry that was removed
	 * @throws PortalException if a email otp entry with the primary key could not be found
	 */
	@Override
	public com.liferay.multi.factor.authentication.checker.email.otp.model.
		EmailOTPEntry deleteEmailOTPEntry(long entryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _emailOTPEntryLocalService.deleteEmailOTPEntry(entryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _emailOTPEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _emailOTPEntryLocalService.dynamicQuery();
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

		return _emailOTPEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _emailOTPEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _emailOTPEntryLocalService.dynamicQuery(
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

		return _emailOTPEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _emailOTPEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.multi.factor.authentication.checker.email.otp.model.
		EmailOTPEntry fetchEmailOTPEntry(long entryId) {

		return _emailOTPEntryLocalService.fetchEmailOTPEntry(entryId);
	}

	@Override
	public com.liferay.multi.factor.authentication.checker.email.otp.model.
		EmailOTPEntry fetchEntryByUserId(long userId) {

		return _emailOTPEntryLocalService.fetchEntryByUserId(userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _emailOTPEntryLocalService.getActionableDynamicQuery();
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
	@Override
	public java.util.List
		<com.liferay.multi.factor.authentication.checker.email.otp.model.
			EmailOTPEntry> getEmailOTPEntries(int start, int end) {

		return _emailOTPEntryLocalService.getEmailOTPEntries(start, end);
	}

	/**
	 * Returns the number of email otp entries.
	 *
	 * @return the number of email otp entries
	 */
	@Override
	public int getEmailOTPEntriesCount() {
		return _emailOTPEntryLocalService.getEmailOTPEntriesCount();
	}

	/**
	 * Returns the email otp entry with the primary key.
	 *
	 * @param entryId the primary key of the email otp entry
	 * @return the email otp entry
	 * @throws PortalException if a email otp entry with the primary key could not be found
	 */
	@Override
	public com.liferay.multi.factor.authentication.checker.email.otp.model.
		EmailOTPEntry getEmailOTPEntry(long entryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _emailOTPEntryLocalService.getEmailOTPEntry(entryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _emailOTPEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _emailOTPEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _emailOTPEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void resetFailedAttempts(long userId) {
		_emailOTPEntryLocalService.resetFailedAttempts(userId);
	}

	@Override
	public void updateAttempts(long userId, String userIP, boolean success) {
		_emailOTPEntryLocalService.updateAttempts(userId, userIP, success);
	}

	/**
	 * Updates the email otp entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param emailOTPEntry the email otp entry
	 * @return the email otp entry that was updated
	 */
	@Override
	public com.liferay.multi.factor.authentication.checker.email.otp.model.
		EmailOTPEntry updateEmailOTPEntry(
			com.liferay.multi.factor.authentication.checker.email.otp.model.
				EmailOTPEntry emailOTPEntry) {

		return _emailOTPEntryLocalService.updateEmailOTPEntry(emailOTPEntry);
	}

	@Override
	public EmailOTPEntryLocalService getWrappedService() {
		return _emailOTPEntryLocalService;
	}

	@Override
	public void setWrappedService(
		EmailOTPEntryLocalService emailOTPEntryLocalService) {

		_emailOTPEntryLocalService = emailOTPEntryLocalService;
	}

	private EmailOTPEntryLocalService _emailOTPEntryLocalService;

}