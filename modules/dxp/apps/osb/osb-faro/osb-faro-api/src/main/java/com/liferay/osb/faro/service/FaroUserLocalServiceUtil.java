/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service;

import com.liferay.osb.faro.model.FaroUser;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for FaroUser. This utility wraps
 * <code>com.liferay.osb.faro.service.impl.FaroUserLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Matthew Kong
 * @see FaroUserLocalService
 * @generated
 */
public class FaroUserLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.faro.service.impl.FaroUserLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static List<FaroUser> acceptInvitations(long userId, String key) {
		return getService().acceptInvitations(userId, key);
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
	public static FaroUser addFaroUser(FaroUser faroUser) {
		return getService().addFaroUser(faroUser);
	}

	public static FaroUser addFaroUser(
			long userId, long groupId, long liveUserId, long roleId,
			String emailAddress, int status, boolean sendEmail)
		throws PortalException {

		return getService().addFaroUser(
			userId, groupId, liveUserId, roleId, emailAddress, status,
			sendEmail);
	}

	/**
	 * Creates a new faro user with the primary key. Does not add the faro user to the database.
	 *
	 * @param faroUserId the primary key for the new faro user
	 * @return the new faro user
	 */
	public static FaroUser createFaroUser(long faroUserId) {
		return getService().createFaroUser(faroUserId);
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
	 * Deletes the faro user from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroUserLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroUser the faro user
	 * @return the faro user that was removed
	 */
	public static FaroUser deleteFaroUser(FaroUser faroUser) {
		return getService().deleteFaroUser(faroUser);
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
	public static FaroUser deleteFaroUser(long faroUserId)
		throws PortalException {

		return getService().deleteFaroUser(faroUserId);
	}

	public static void deleteFaroUsers(long groupId) {
		getService().deleteFaroUsers(groupId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroUserModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroUserModelImpl</code>.
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

	public static FaroUser fetchFaroUser(long faroUserId) {
		return getService().fetchFaroUser(faroUserId);
	}

	public static FaroUser fetchFaroUser(long groupId, long liveUserId) {
		return getService().fetchFaroUser(groupId, liveUserId);
	}

	public static FaroUser fetchFaroUser(long groupId, String emailAddress) {
		return getService().fetchFaroUser(groupId, emailAddress);
	}

	public static FaroUser fetchOwnerFaroUser(long groupId) {
		return getService().fetchOwnerFaroUser(groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the faro user with the primary key.
	 *
	 * @param faroUserId the primary key of the faro user
	 * @return the faro user
	 * @throws PortalException if a faro user with the primary key could not be found
	 */
	public static FaroUser getFaroUser(long faroUserId) throws PortalException {
		return getService().getFaroUser(faroUserId);
	}

	public static FaroUser getFaroUser(long groupId, long liveUserId)
		throws PortalException {

		return getService().getFaroUser(groupId, liveUserId);
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
	public static List<FaroUser> getFaroUsers(int start, int end) {
		return getService().getFaroUsers(start, end);
	}

	public static List<FaroUser> getFaroUsers(
			long groupId, boolean available, String query,
			List<Integer> statuses, long workspaceGroupId, int start, int end,
			OrderByComparator<FaroUser> orderByComparator)
		throws PortalException {

		return getService().getFaroUsers(
			groupId, available, query, statuses, workspaceGroupId, start, end,
			orderByComparator);
	}

	public static List<FaroUser> getFaroUsersByLiveUserId(
		long liveUserId, int status) {

		return getService().getFaroUsersByLiveUserId(liveUserId, status);
	}

	public static List<FaroUser> getFaroUsersByRoleId(
		long groupId, long roleId) {

		return getService().getFaroUsersByRoleId(groupId, roleId);
	}

	public static List<FaroUser> getFaroUsersByStatus(
		long groupId, int status) {

		return getService().getFaroUsersByStatus(groupId, status);
	}

	/**
	 * Returns the number of faro users.
	 *
	 * @return the number of faro users
	 */
	public static int getFaroUsersCount() {
		return getService().getFaroUsersCount();
	}

	public static int getFaroUsersCount(
			long groupId, boolean available, String query,
			List<Integer> statuses, long workspaceGroupId)
		throws PortalException {

		return getService().getFaroUsersCount(
			groupId, available, query, statuses, workspaceGroupId);
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

	public static FaroUser getOwnerFaroUser(long groupId)
		throws PortalException {

		return getService().getOwnerFaroUser(groupId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static List<FaroUser> search(
		long groupId, String query, List<Integer> statuses, int start, int end,
		OrderByComparator<FaroUser> orderByComparator) {

		return getService().search(
			groupId, query, statuses, start, end, orderByComparator);
	}

	public static int searchCount(
		long groupId, String query, List<Integer> statuses) {

		return getService().searchCount(groupId, query, statuses);
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
	public static FaroUser updateFaroUser(FaroUser faroUser) {
		return getService().updateFaroUser(faroUser);
	}

	public static FaroUserLocalService getService() {
		return _service;
	}

	public static void setService(FaroUserLocalService service) {
		_service = service;
	}

	private static volatile FaroUserLocalService _service;

}