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

package com.liferay.multi.factor.authentication.fido2.credential.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link MFAFIDO2CredentialEntryLocalService}.
 *
 * @author Arthur Chan
 * @see MFAFIDO2CredentialEntryLocalService
 * @generated
 */
public class MFAFIDO2CredentialEntryLocalServiceWrapper
	implements MFAFIDO2CredentialEntryLocalService,
			   ServiceWrapper<MFAFIDO2CredentialEntryLocalService> {

	public MFAFIDO2CredentialEntryLocalServiceWrapper() {
		this(null);
	}

	public MFAFIDO2CredentialEntryLocalServiceWrapper(
		MFAFIDO2CredentialEntryLocalService
			mfaFIDO2CredentialEntryLocalService) {

		_mfaFIDO2CredentialEntryLocalService =
			mfaFIDO2CredentialEntryLocalService;
	}

	@Override
	public com.liferay.multi.factor.authentication.fido2.credential.model.
		MFAFIDO2CredentialEntry addMFAFIDO2CredentialEntry(
				long userId, String credentialKey, int credentialType,
				String publicKeyCOSE)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaFIDO2CredentialEntryLocalService.addMFAFIDO2CredentialEntry(
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
	@Override
	public com.liferay.multi.factor.authentication.fido2.credential.model.
		MFAFIDO2CredentialEntry addMFAFIDO2CredentialEntry(
			com.liferay.multi.factor.authentication.fido2.credential.model.
				MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		return _mfaFIDO2CredentialEntryLocalService.addMFAFIDO2CredentialEntry(
			mfaFIDO2CredentialEntry);
	}

	/**
	 * Creates a new mfafido2 credential entry with the primary key. Does not add the mfafido2 credential entry to the database.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key for the new mfafido2 credential entry
	 * @return the new mfafido2 credential entry
	 */
	@Override
	public com.liferay.multi.factor.authentication.fido2.credential.model.
		MFAFIDO2CredentialEntry createMFAFIDO2CredentialEntry(
			long mfaFIDO2CredentialEntryId) {

		return _mfaFIDO2CredentialEntryLocalService.
			createMFAFIDO2CredentialEntry(mfaFIDO2CredentialEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaFIDO2CredentialEntryLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.multi.factor.authentication.fido2.credential.model.
		MFAFIDO2CredentialEntry deleteMFAFIDO2CredentialEntry(
				long mfaFIDO2CredentialEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaFIDO2CredentialEntryLocalService.
			deleteMFAFIDO2CredentialEntry(mfaFIDO2CredentialEntryId);
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
	@Override
	public com.liferay.multi.factor.authentication.fido2.credential.model.
		MFAFIDO2CredentialEntry deleteMFAFIDO2CredentialEntry(
			com.liferay.multi.factor.authentication.fido2.credential.model.
				MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		return _mfaFIDO2CredentialEntryLocalService.
			deleteMFAFIDO2CredentialEntry(mfaFIDO2CredentialEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaFIDO2CredentialEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _mfaFIDO2CredentialEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _mfaFIDO2CredentialEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mfaFIDO2CredentialEntryLocalService.dynamicQuery();
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

		return _mfaFIDO2CredentialEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _mfaFIDO2CredentialEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _mfaFIDO2CredentialEntryLocalService.dynamicQuery(
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

		return _mfaFIDO2CredentialEntryLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _mfaFIDO2CredentialEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.multi.factor.authentication.fido2.credential.model.
		MFAFIDO2CredentialEntry fetchMFAFIDO2CredentialEntry(
			long mfaFIDO2CredentialEntryId) {

		return _mfaFIDO2CredentialEntryLocalService.
			fetchMFAFIDO2CredentialEntry(mfaFIDO2CredentialEntryId);
	}

	@Override
	public com.liferay.multi.factor.authentication.fido2.credential.model.
		MFAFIDO2CredentialEntry
			fetchMFAFIDO2CredentialEntryByUserIdAndCredentialKey(
				long userId, String credentialKey) {

		return _mfaFIDO2CredentialEntryLocalService.
			fetchMFAFIDO2CredentialEntryByUserIdAndCredentialKey(
				userId, credentialKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _mfaFIDO2CredentialEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mfaFIDO2CredentialEntryLocalService.
			getIndexableActionableDynamicQuery();
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
	@Override
	public java.util.List
		<com.liferay.multi.factor.authentication.fido2.credential.model.
			MFAFIDO2CredentialEntry> getMFAFIDO2CredentialEntries(
				int start, int end) {

		return _mfaFIDO2CredentialEntryLocalService.
			getMFAFIDO2CredentialEntries(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.multi.factor.authentication.fido2.credential.model.
			MFAFIDO2CredentialEntry>
				getMFAFIDO2CredentialEntriesByCredentialKey(
					String credentialKey) {

		return _mfaFIDO2CredentialEntryLocalService.
			getMFAFIDO2CredentialEntriesByCredentialKey(credentialKey);
	}

	@Override
	public java.util.List
		<com.liferay.multi.factor.authentication.fido2.credential.model.
			MFAFIDO2CredentialEntry> getMFAFIDO2CredentialEntriesByUserId(
				long userId) {

		return _mfaFIDO2CredentialEntryLocalService.
			getMFAFIDO2CredentialEntriesByUserId(userId);
	}

	/**
	 * Returns the number of mfafido2 credential entries.
	 *
	 * @return the number of mfafido2 credential entries
	 */
	@Override
	public int getMFAFIDO2CredentialEntriesCount() {
		return _mfaFIDO2CredentialEntryLocalService.
			getMFAFIDO2CredentialEntriesCount();
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry
	 * @throws PortalException if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public com.liferay.multi.factor.authentication.fido2.credential.model.
		MFAFIDO2CredentialEntry getMFAFIDO2CredentialEntry(
				long mfaFIDO2CredentialEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaFIDO2CredentialEntryLocalService.getMFAFIDO2CredentialEntry(
			mfaFIDO2CredentialEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mfaFIDO2CredentialEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaFIDO2CredentialEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.multi.factor.authentication.fido2.credential.model.
		MFAFIDO2CredentialEntry updateAttempts(
				long userId, String credentialKey, long signatureCount)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _mfaFIDO2CredentialEntryLocalService.updateAttempts(
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
	@Override
	public com.liferay.multi.factor.authentication.fido2.credential.model.
		MFAFIDO2CredentialEntry updateMFAFIDO2CredentialEntry(
			com.liferay.multi.factor.authentication.fido2.credential.model.
				MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		return _mfaFIDO2CredentialEntryLocalService.
			updateMFAFIDO2CredentialEntry(mfaFIDO2CredentialEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mfaFIDO2CredentialEntryLocalService.getBasePersistence();
	}

	@Override
	public MFAFIDO2CredentialEntryLocalService getWrappedService() {
		return _mfaFIDO2CredentialEntryLocalService;
	}

	@Override
	public void setWrappedService(
		MFAFIDO2CredentialEntryLocalService
			mfaFIDO2CredentialEntryLocalService) {

		_mfaFIDO2CredentialEntryLocalService =
			mfaFIDO2CredentialEntryLocalService;
	}

	private MFAFIDO2CredentialEntryLocalService
		_mfaFIDO2CredentialEntryLocalService;

}