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
 * Provides the local service utility for PasswordPolicyRel. This utility wraps
 * <code>com.liferay.portal.service.impl.PasswordPolicyRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyRelLocalService
 * @generated
 */
public class PasswordPolicyRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.PasswordPolicyRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.PasswordPolicyRel
		addPasswordPolicyRel(
			long passwordPolicyId, String className, long classPK) {

		return getService().addPasswordPolicyRel(
			passwordPolicyId, className, classPK);
	}

	/**
	 * Adds the password policy rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PasswordPolicyRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param passwordPolicyRel the password policy rel
	 * @return the password policy rel that was added
	 */
	public static com.liferay.portal.kernel.model.PasswordPolicyRel
		addPasswordPolicyRel(
			com.liferay.portal.kernel.model.PasswordPolicyRel
				passwordPolicyRel) {

		return getService().addPasswordPolicyRel(passwordPolicyRel);
	}

	public static void addPasswordPolicyRels(
		long passwordPolicyId, String className, long[] classPKs) {

		getService().addPasswordPolicyRels(
			passwordPolicyId, className, classPKs);
	}

	/**
	 * Creates a new password policy rel with the primary key. Does not add the password policy rel to the database.
	 *
	 * @param passwordPolicyRelId the primary key for the new password policy rel
	 * @return the new password policy rel
	 */
	public static com.liferay.portal.kernel.model.PasswordPolicyRel
		createPasswordPolicyRel(long passwordPolicyRelId) {

		return getService().createPasswordPolicyRel(passwordPolicyRelId);
	}

	/**
	 * Deletes the password policy rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PasswordPolicyRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param passwordPolicyRelId the primary key of the password policy rel
	 * @return the password policy rel that was removed
	 * @throws PortalException if a password policy rel with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.PasswordPolicyRel
			deletePasswordPolicyRel(long passwordPolicyRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePasswordPolicyRel(passwordPolicyRelId);
	}

	public static void deletePasswordPolicyRel(
		long passwordPolicyId, String className, long classPK) {

		getService().deletePasswordPolicyRel(
			passwordPolicyId, className, classPK);
	}

	/**
	 * Deletes the password policy rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PasswordPolicyRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param passwordPolicyRel the password policy rel
	 * @return the password policy rel that was removed
	 */
	public static com.liferay.portal.kernel.model.PasswordPolicyRel
		deletePasswordPolicyRel(
			com.liferay.portal.kernel.model.PasswordPolicyRel
				passwordPolicyRel) {

		return getService().deletePasswordPolicyRel(passwordPolicyRel);
	}

	public static void deletePasswordPolicyRel(String className, long classPK) {
		getService().deletePasswordPolicyRel(className, classPK);
	}

	public static void deletePasswordPolicyRels(long passwordPolicyId) {
		getService().deletePasswordPolicyRels(passwordPolicyId);
	}

	public static void deletePasswordPolicyRels(
		long passwordPolicyId, String className, long[] classPKs) {

		getService().deletePasswordPolicyRels(
			passwordPolicyId, className, classPKs);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PasswordPolicyRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PasswordPolicyRelModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.PasswordPolicyRel
		fetchPasswordPolicyRel(long passwordPolicyRelId) {

		return getService().fetchPasswordPolicyRel(passwordPolicyRelId);
	}

	public static com.liferay.portal.kernel.model.PasswordPolicyRel
		fetchPasswordPolicyRel(String className, long classPK) {

		return getService().fetchPasswordPolicyRel(className, classPK);
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
	 * Returns the password policy rel with the primary key.
	 *
	 * @param passwordPolicyRelId the primary key of the password policy rel
	 * @return the password policy rel
	 * @throws PortalException if a password policy rel with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.PasswordPolicyRel
			getPasswordPolicyRel(long passwordPolicyRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPasswordPolicyRel(passwordPolicyRelId);
	}

	public static com.liferay.portal.kernel.model.PasswordPolicyRel
			getPasswordPolicyRel(
				long passwordPolicyId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPasswordPolicyRel(
			passwordPolicyId, className, classPK);
	}

	public static com.liferay.portal.kernel.model.PasswordPolicyRel
			getPasswordPolicyRel(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPasswordPolicyRel(className, classPK);
	}

	/**
	 * Returns a range of all the password policy rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PasswordPolicyRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policy rels
	 * @param end the upper bound of the range of password policy rels (not inclusive)
	 * @return the range of password policy rels
	 */
	public static java.util.List
		<com.liferay.portal.kernel.model.PasswordPolicyRel>
			getPasswordPolicyRels(int start, int end) {

		return getService().getPasswordPolicyRels(start, end);
	}

	/**
	 * Returns the number of password policy rels.
	 *
	 * @return the number of password policy rels
	 */
	public static int getPasswordPolicyRelsCount() {
		return getService().getPasswordPolicyRelsCount();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static boolean hasPasswordPolicyRel(
		long passwordPolicyId, String className, long classPK) {

		return getService().hasPasswordPolicyRel(
			passwordPolicyId, className, classPK);
	}

	/**
	 * Updates the password policy rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect PasswordPolicyRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param passwordPolicyRel the password policy rel
	 * @return the password policy rel that was updated
	 */
	public static com.liferay.portal.kernel.model.PasswordPolicyRel
		updatePasswordPolicyRel(
			com.liferay.portal.kernel.model.PasswordPolicyRel
				passwordPolicyRel) {

		return getService().updatePasswordPolicyRel(passwordPolicyRel);
	}

	public static PasswordPolicyRelLocalService getService() {
		if (_service == null) {
			_service =
				(PasswordPolicyRelLocalService)PortalBeanLocatorUtil.locate(
					PasswordPolicyRelLocalService.class.getName());
		}

		return _service;
	}

	private static PasswordPolicyRelLocalService _service;

}