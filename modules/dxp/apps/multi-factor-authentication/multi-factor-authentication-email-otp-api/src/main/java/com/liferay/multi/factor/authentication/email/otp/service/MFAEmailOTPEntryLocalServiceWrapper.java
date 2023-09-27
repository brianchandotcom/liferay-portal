/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.email.otp.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link MFAEmailOTPEntryLocalService}.
 *
 * @author Arthur Chan
 * @see MFAEmailOTPEntryLocalService
 * @generated
 */
public class MFAEmailOTPEntryLocalServiceWrapper
	implements MFAEmailOTPEntryLocalService,
			   ServiceWrapper<MFAEmailOTPEntryLocalService> {

	public MFAEmailOTPEntryLocalServiceWrapper() {
		this(null);
	}

	public MFAEmailOTPEntryLocalServiceWrapper(
		MFAEmailOTPEntryLocalService mfaEmailOTPEntryLocalService) {

		_mfaEmailOTPEntryLocalService = mfaEmailOTPEntryLocalService;
	}

	@Override
	public
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
				addMFAEmailOTPEntry(long userId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaEmailOTPEntryLocalService.addMFAEmailOTPEntry(userId);
	}

	/**
	 * Adds the mfa email otp entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MFAEmailOTPEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mfaEmailOTPEntry the mfa email otp entry
	 * @return the mfa email otp entry that was added
	 */
	@Override
	public
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			addMFAEmailOTPEntry(
				com.liferay.multi.factor.authentication.email.otp.model.
					MFAEmailOTPEntry mfaEmailOTPEntry) {

		return _mfaEmailOTPEntryLocalService.addMFAEmailOTPEntry(
			mfaEmailOTPEntry);
	}

	/**
	 * Creates a new mfa email otp entry with the primary key. Does not add the mfa email otp entry to the database.
	 *
	 * @param mfaEmailOTPEntryId the primary key for the new mfa email otp entry
	 * @return the new mfa email otp entry
	 */
	@Override
	public
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			createMFAEmailOTPEntry(long mfaEmailOTPEntryId) {

		return _mfaEmailOTPEntryLocalService.createMFAEmailOTPEntry(
			mfaEmailOTPEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaEmailOTPEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the mfa email otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MFAEmailOTPEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry that was removed
	 * @throws PortalException if a mfa email otp entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
				deleteMFAEmailOTPEntry(long mfaEmailOTPEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaEmailOTPEntryLocalService.deleteMFAEmailOTPEntry(
			mfaEmailOTPEntryId);
	}

	/**
	 * Deletes the mfa email otp entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MFAEmailOTPEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mfaEmailOTPEntry the mfa email otp entry
	 * @return the mfa email otp entry that was removed
	 */
	@Override
	public
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			deleteMFAEmailOTPEntry(
				com.liferay.multi.factor.authentication.email.otp.model.
					MFAEmailOTPEntry mfaEmailOTPEntry) {

		return _mfaEmailOTPEntryLocalService.deleteMFAEmailOTPEntry(
			mfaEmailOTPEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaEmailOTPEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _mfaEmailOTPEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _mfaEmailOTPEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mfaEmailOTPEntryLocalService.dynamicQuery();
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

		return _mfaEmailOTPEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _mfaEmailOTPEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _mfaEmailOTPEntryLocalService.dynamicQuery(
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

		return _mfaEmailOTPEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _mfaEmailOTPEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			fetchMFAEmailOTPEntry(long mfaEmailOTPEntryId) {

		return _mfaEmailOTPEntryLocalService.fetchMFAEmailOTPEntry(
			mfaEmailOTPEntryId);
	}

	@Override
	public
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			fetchMFAEmailOTPEntryByUserId(long userId) {

		return _mfaEmailOTPEntryLocalService.fetchMFAEmailOTPEntryByUserId(
			userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _mfaEmailOTPEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mfaEmailOTPEntryLocalService.
			getIndexableActionableDynamicQuery();
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
	@Override
	public java.util.List
		<com.liferay.multi.factor.authentication.email.otp.model.
			MFAEmailOTPEntry> getMFAEmailOTPEntries(int start, int end) {

		return _mfaEmailOTPEntryLocalService.getMFAEmailOTPEntries(start, end);
	}

	/**
	 * Returns the number of mfa email otp entries.
	 *
	 * @return the number of mfa email otp entries
	 */
	@Override
	public int getMFAEmailOTPEntriesCount() {
		return _mfaEmailOTPEntryLocalService.getMFAEmailOTPEntriesCount();
	}

	/**
	 * Returns the mfa email otp entry with the primary key.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry
	 * @throws PortalException if a mfa email otp entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
				getMFAEmailOTPEntry(long mfaEmailOTPEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaEmailOTPEntryLocalService.getMFAEmailOTPEntry(
			mfaEmailOTPEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mfaEmailOTPEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaEmailOTPEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
				resetFailedAttempts(long userId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaEmailOTPEntryLocalService.resetFailedAttempts(userId);
	}

	@Override
	public
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
				updateAttempts(long userId, String ip, boolean success)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaEmailOTPEntryLocalService.updateAttempts(
			userId, ip, success);
	}

	/**
	 * Updates the mfa email otp entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MFAEmailOTPEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mfaEmailOTPEntry the mfa email otp entry
	 * @return the mfa email otp entry that was updated
	 */
	@Override
	public
		com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry
			updateMFAEmailOTPEntry(
				com.liferay.multi.factor.authentication.email.otp.model.
					MFAEmailOTPEntry mfaEmailOTPEntry) {

		return _mfaEmailOTPEntryLocalService.updateMFAEmailOTPEntry(
			mfaEmailOTPEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mfaEmailOTPEntryLocalService.getBasePersistence();
	}

	@Override
	public MFAEmailOTPEntryLocalService getWrappedService() {
		return _mfaEmailOTPEntryLocalService;
	}

	@Override
	public void setWrappedService(
		MFAEmailOTPEntryLocalService mfaEmailOTPEntryLocalService) {

		_mfaEmailOTPEntryLocalService = mfaEmailOTPEntryLocalService;
	}

	private MFAEmailOTPEntryLocalService _mfaEmailOTPEntryLocalService;

}