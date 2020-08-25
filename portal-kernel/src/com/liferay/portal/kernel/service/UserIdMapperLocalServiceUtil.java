/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for UserIdMapper. This utility wraps
 * <code>com.liferay.portal.service.impl.UserIdMapperLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see UserIdMapperLocalService
 * @generated
 */
public class UserIdMapperLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.UserIdMapperLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the user ID mapper to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UserIdMapperLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param userIdMapper the user ID mapper
	 * @return the user ID mapper that was added
	 */
	public static com.liferay.portal.kernel.model.UserIdMapper addUserIdMapper(
		com.liferay.portal.kernel.model.UserIdMapper userIdMapper) {

		return getService().addUserIdMapper(userIdMapper);
	}

	/**
	 * Creates a new user ID mapper with the primary key. Does not add the user ID mapper to the database.
	 *
	 * @param userIdMapperId the primary key for the new user ID mapper
	 * @return the new user ID mapper
	 */
	public static com.liferay.portal.kernel.model.UserIdMapper
		createUserIdMapper(long userIdMapperId) {

		return getService().createUserIdMapper(userIdMapperId);
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

	/**
	 * Deletes the user ID mapper with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UserIdMapperLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param userIdMapperId the primary key of the user ID mapper
	 * @return the user ID mapper that was removed
	 * @throws PortalException if a user ID mapper with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.UserIdMapper
			deleteUserIdMapper(long userIdMapperId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteUserIdMapper(userIdMapperId);
	}

	/**
	 * Deletes the user ID mapper from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UserIdMapperLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param userIdMapper the user ID mapper
	 * @return the user ID mapper that was removed
	 */
	public static com.liferay.portal.kernel.model.UserIdMapper
		deleteUserIdMapper(
			com.liferay.portal.kernel.model.UserIdMapper userIdMapper) {

		return getService().deleteUserIdMapper(userIdMapper);
	}

	public static void deleteUserIdMappers(long userId) {
		getService().deleteUserIdMappers(userId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserIdMapperModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserIdMapperModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.UserIdMapper
		fetchUserIdMapper(long userIdMapperId) {

		return getService().fetchUserIdMapper(userIdMapperId);
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

	/**
	 * Returns the user ID mapper with the primary key.
	 *
	 * @param userIdMapperId the primary key of the user ID mapper
	 * @return the user ID mapper
	 * @throws PortalException if a user ID mapper with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.UserIdMapper getUserIdMapper(
			long userIdMapperId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserIdMapper(userIdMapperId);
	}

	public static com.liferay.portal.kernel.model.UserIdMapper getUserIdMapper(
			long userId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserIdMapper(userId, type);
	}

	public static com.liferay.portal.kernel.model.UserIdMapper
			getUserIdMapperByExternalUserId(String type, String externalUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getUserIdMapperByExternalUserId(
			type, externalUserId);
	}

	/**
	 * Returns a range of all the user ID mappers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user ID mappers
	 * @param end the upper bound of the range of user ID mappers (not inclusive)
	 * @return the range of user ID mappers
	 */
	public static java.util.List<com.liferay.portal.kernel.model.UserIdMapper>
		getUserIdMappers(int start, int end) {

		return getService().getUserIdMappers(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserIdMapper>
		getUserIdMappers(long userId) {

		return getService().getUserIdMappers(userId);
	}

	/**
	 * Returns the number of user ID mappers.
	 *
	 * @return the number of user ID mappers
	 */
	public static int getUserIdMappersCount() {
		return getService().getUserIdMappersCount();
	}

	public static com.liferay.portal.kernel.model.UserIdMapper
		updateUserIdMapper(
			long userId, String type, String description,
			String externalUserId) {

		return getService().updateUserIdMapper(
			userId, type, description, externalUserId);
	}

	/**
	 * Updates the user ID mapper in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UserIdMapperLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param userIdMapper the user ID mapper
	 * @return the user ID mapper that was updated
	 */
	public static com.liferay.portal.kernel.model.UserIdMapper
		updateUserIdMapper(
			com.liferay.portal.kernel.model.UserIdMapper userIdMapper) {

		return getService().updateUserIdMapper(userIdMapper);
	}

	public static UserIdMapperLocalService getService() {
		if (_service == null) {
			_service = (UserIdMapperLocalService)PortalBeanLocatorUtil.locate(
				UserIdMapperLocalService.class.getName());
		}

		return _service;
	}

	private static UserIdMapperLocalService _service;

}