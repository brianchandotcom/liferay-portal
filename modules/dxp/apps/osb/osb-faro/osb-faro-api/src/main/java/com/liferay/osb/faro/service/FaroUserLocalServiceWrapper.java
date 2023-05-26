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

package com.liferay.osb.faro.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link FaroUserLocalService}.
 *
 * @author Matthew Kong
 * @see FaroUserLocalService
 * @generated
 */
public class FaroUserLocalServiceWrapper
	implements FaroUserLocalService, ServiceWrapper<FaroUserLocalService> {

	public FaroUserLocalServiceWrapper() {
		this(null);
	}

	public FaroUserLocalServiceWrapper(
		FaroUserLocalService faroUserLocalService) {

		_faroUserLocalService = faroUserLocalService;
	}

	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroUser>
		acceptInvitations(long userId, String key) {

		return _faroUserLocalService.acceptInvitations(userId, key);
	}

	/**
	 * Adds the faro user to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroUser the faro user
	 * @return the faro user that was added
	 */
	@Override
	public com.liferay.osb.faro.model.FaroUser addFaroUser(
		com.liferay.osb.faro.model.FaroUser faroUser) {

		return _faroUserLocalService.addFaroUser(faroUser);
	}

	@Override
	public com.liferay.osb.faro.model.FaroUser addFaroUser(
			long userId, long groupId, long liveUserId, long roleId,
			String emailAddress, int status, boolean sendEmail)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroUserLocalService.addFaroUser(
			userId, groupId, liveUserId, roleId, emailAddress, status,
			sendEmail);
	}

	/**
	 * Creates a new faro user with the primary key. Does not add the faro user to the database.
	 *
	 * @param faroUserId the primary key for the new faro user
	 * @return the new faro user
	 */
	@Override
	public com.liferay.osb.faro.model.FaroUser createFaroUser(long faroUserId) {
		return _faroUserLocalService.createFaroUser(faroUserId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroUserLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the faro user from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroUser the faro user
	 * @return the faro user that was removed
	 */
	@Override
	public com.liferay.osb.faro.model.FaroUser deleteFaroUser(
		com.liferay.osb.faro.model.FaroUser faroUser) {

		return _faroUserLocalService.deleteFaroUser(faroUser);
	}

	/**
	 * Deletes the faro user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroUserId the primary key of the faro user
	 * @return the faro user that was removed
	 * @throws PortalException if a faro user with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.model.FaroUser deleteFaroUser(long faroUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroUserLocalService.deleteFaroUser(faroUserId);
	}

	@Override
	public void deleteFaroUsers(long groupId) {
		_faroUserLocalService.deleteFaroUsers(groupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroUserLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _faroUserLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _faroUserLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _faroUserLocalService.dynamicQuery();
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

		return _faroUserLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroUserModelImpl</code>.
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

		return _faroUserLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroUserModelImpl</code>.
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

		return _faroUserLocalService.dynamicQuery(
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

		return _faroUserLocalService.dynamicQueryCount(dynamicQuery);
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

		return _faroUserLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.osb.faro.model.FaroUser fetchFaroUser(long faroUserId) {
		return _faroUserLocalService.fetchFaroUser(faroUserId);
	}

	@Override
	public com.liferay.osb.faro.model.FaroUser fetchFaroUser(
		long groupId, long liveUserId) {

		return _faroUserLocalService.fetchFaroUser(groupId, liveUserId);
	}

	@Override
	public com.liferay.osb.faro.model.FaroUser fetchFaroUser(
		long groupId, String emailAddress) {

		return _faroUserLocalService.fetchFaroUser(groupId, emailAddress);
	}

	@Override
	public com.liferay.osb.faro.model.FaroUser fetchOwnerFaroUser(
		long groupId) {

		return _faroUserLocalService.fetchOwnerFaroUser(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _faroUserLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the faro user with the primary key.
	 *
	 * @param faroUserId the primary key of the faro user
	 * @return the faro user
	 * @throws PortalException if a faro user with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.model.FaroUser getFaroUser(long faroUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroUserLocalService.getFaroUser(faroUserId);
	}

	@Override
	public com.liferay.osb.faro.model.FaroUser getFaroUser(
			long groupId, long liveUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroUserLocalService.getFaroUser(groupId, liveUserId);
	}

	/**
	 * Returns a range of all the faro users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro users
	 * @param end the upper bound of the range of faro users (not inclusive)
	 * @return the range of faro users
	 */
	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroUser> getFaroUsers(
		int start, int end) {

		return _faroUserLocalService.getFaroUsers(start, end);
	}

	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroUser>
		getFaroUsersByLiveUserId(long liveUserId, int status) {

		return _faroUserLocalService.getFaroUsersByLiveUserId(
			liveUserId, status);
	}

	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroUser>
		getFaroUsersByRoleId(long groupId, long roleId) {

		return _faroUserLocalService.getFaroUsersByRoleId(groupId, roleId);
	}

	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroUser>
		getFaroUsersByStatus(long groupId, int status) {

		return _faroUserLocalService.getFaroUsersByStatus(groupId, status);
	}

	/**
	 * Returns the number of faro users.
	 *
	 * @return the number of faro users
	 */
	@Override
	public int getFaroUsersCount() {
		return _faroUserLocalService.getFaroUsersCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _faroUserLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _faroUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.osb.faro.model.FaroUser getOwnerFaroUser(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroUserLocalService.getOwnerFaroUser(groupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroUserLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroUser> search(
		long groupId, String query, java.util.List<Integer> statuses, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.osb.faro.model.FaroUser> orderByComparator) {

		return _faroUserLocalService.search(
			groupId, query, statuses, start, end, orderByComparator);
	}

	@Override
	public int searchCount(
		long groupId, String query, java.util.List<Integer> statuses) {

		return _faroUserLocalService.searchCount(groupId, query, statuses);
	}

	/**
	 * Updates the faro user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroUser the faro user
	 * @return the faro user that was updated
	 */
	@Override
	public com.liferay.osb.faro.model.FaroUser updateFaroUser(
		com.liferay.osb.faro.model.FaroUser faroUser) {

		return _faroUserLocalService.updateFaroUser(faroUser);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _faroUserLocalService.getBasePersistence();
	}

	@Override
	public FaroUserLocalService getWrappedService() {
		return _faroUserLocalService;
	}

	@Override
	public void setWrappedService(FaroUserLocalService faroUserLocalService) {
		_faroUserLocalService = faroUserLocalService;
	}

	private FaroUserLocalService _faroUserLocalService;

}