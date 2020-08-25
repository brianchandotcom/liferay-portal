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
 * Provides the local service utility for EmailAddress. This utility wraps
 * <code>com.liferay.portal.service.impl.EmailAddressLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see EmailAddressLocalService
 * @generated
 */
public class EmailAddressLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.EmailAddressLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the email address to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect EmailAddressLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @return the email address that was added
	 */
	public static com.liferay.portal.kernel.model.EmailAddress addEmailAddress(
		com.liferay.portal.kernel.model.EmailAddress emailAddress) {

		return getService().addEmailAddress(emailAddress);
	}

	public static com.liferay.portal.kernel.model.EmailAddress addEmailAddress(
			long userId, String className, long classPK, String address,
			long typeId, boolean primary, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addEmailAddress(
			userId, className, classPK, address, typeId, primary,
			serviceContext);
	}

	/**
	 * Creates a new email address with the primary key. Does not add the email address to the database.
	 *
	 * @param emailAddressId the primary key for the new email address
	 * @return the new email address
	 */
	public static com.liferay.portal.kernel.model.EmailAddress
		createEmailAddress(long emailAddressId) {

		return getService().createEmailAddress(emailAddressId);
	}

	/**
	 * Deletes the email address from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect EmailAddressLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @return the email address that was removed
	 */
	public static com.liferay.portal.kernel.model.EmailAddress
		deleteEmailAddress(
			com.liferay.portal.kernel.model.EmailAddress emailAddress) {

		return getService().deleteEmailAddress(emailAddress);
	}

	/**
	 * Deletes the email address with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect EmailAddressLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param emailAddressId the primary key of the email address
	 * @return the email address that was removed
	 * @throws PortalException if a email address with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.EmailAddress
			deleteEmailAddress(long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteEmailAddress(emailAddressId);
	}

	public static void deleteEmailAddresses(
		long companyId, String className, long classPK) {

		getService().deleteEmailAddresses(companyId, className, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.EmailAddressModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.EmailAddressModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.EmailAddress
		fetchEmailAddress(long emailAddressId) {

		return getService().fetchEmailAddress(emailAddressId);
	}

	/**
	 * Returns the email address with the matching UUID and company.
	 *
	 * @param uuid the email address's UUID
	 * @param companyId the primary key of the company
	 * @return the matching email address, or <code>null</code> if a matching email address could not be found
	 */
	public static com.liferay.portal.kernel.model.EmailAddress
		fetchEmailAddressByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchEmailAddressByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the email address with the primary key.
	 *
	 * @param emailAddressId the primary key of the email address
	 * @return the email address
	 * @throws PortalException if a email address with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.EmailAddress getEmailAddress(
			long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEmailAddress(emailAddressId);
	}

	/**
	 * Returns the email address with the matching UUID and company.
	 *
	 * @param uuid the email address's UUID
	 * @param companyId the primary key of the company
	 * @return the matching email address
	 * @throws PortalException if a matching email address could not be found
	 */
	public static com.liferay.portal.kernel.model.EmailAddress
			getEmailAddressByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEmailAddressByUuidAndCompanyId(uuid, companyId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.EmailAddress>
		getEmailAddresses() {

		return getService().getEmailAddresses();
	}

	/**
	 * Returns a range of all the email addresses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.EmailAddressModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of email addresses
	 * @param end the upper bound of the range of email addresses (not inclusive)
	 * @return the range of email addresses
	 */
	public static java.util.List<com.liferay.portal.kernel.model.EmailAddress>
		getEmailAddresses(int start, int end) {

		return getService().getEmailAddresses(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.EmailAddress>
		getEmailAddresses(long companyId, String className, long classPK) {

		return getService().getEmailAddresses(companyId, className, classPK);
	}

	/**
	 * Returns the number of email addresses.
	 *
	 * @return the number of email addresses
	 */
	public static int getEmailAddressesCount() {
		return getService().getEmailAddressesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
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
	 * Updates the email address in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect EmailAddressLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @return the email address that was updated
	 */
	public static com.liferay.portal.kernel.model.EmailAddress
		updateEmailAddress(
			com.liferay.portal.kernel.model.EmailAddress emailAddress) {

		return getService().updateEmailAddress(emailAddress);
	}

	public static com.liferay.portal.kernel.model.EmailAddress
			updateEmailAddress(
				long emailAddressId, String address, long typeId,
				boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateEmailAddress(
			emailAddressId, address, typeId, primary);
	}

	public static EmailAddressLocalService getService() {
		if (_service == null) {
			_service = (EmailAddressLocalService)PortalBeanLocatorUtil.locate(
				EmailAddressLocalService.class.getName());
		}

		return _service;
	}

	private static EmailAddressLocalService _service;

}