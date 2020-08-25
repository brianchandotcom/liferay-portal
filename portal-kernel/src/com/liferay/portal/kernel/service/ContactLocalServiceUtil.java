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
 * Provides the local service utility for Contact. This utility wraps
 * <code>com.liferay.portal.service.impl.ContactLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ContactLocalService
 * @generated
 */
public class ContactLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.ContactLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the contact to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contact the contact
	 * @return the contact that was added
	 */
	public static com.liferay.portal.kernel.model.Contact addContact(
		com.liferay.portal.kernel.model.Contact contact) {

		return getService().addContact(contact);
	}

	public static com.liferay.portal.kernel.model.Contact addContact(
			long userId, String className, long classPK, String emailAddress,
			String firstName, String middleName, String lastName, long prefixId,
			long suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String smsSn, String facebookSn, String jabberSn,
			String skypeSn, String twitterSn, String jobTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addContact(
			userId, className, classPK, emailAddress, firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, smsSn, facebookSn, jabberSn, skypeSn, twitterSn,
			jobTitle);
	}

	/**
	 * Creates a new contact with the primary key. Does not add the contact to the database.
	 *
	 * @param contactId the primary key for the new contact
	 * @return the new contact
	 */
	public static com.liferay.portal.kernel.model.Contact createContact(
		long contactId) {

		return getService().createContact(contactId);
	}

	/**
	 * Deletes the contact from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contact the contact
	 * @return the contact that was removed
	 */
	public static com.liferay.portal.kernel.model.Contact deleteContact(
		com.liferay.portal.kernel.model.Contact contact) {

		return getService().deleteContact(contact);
	}

	/**
	 * Deletes the contact with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contactId the primary key of the contact
	 * @return the contact that was removed
	 * @throws PortalException if a contact with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Contact deleteContact(
			long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContact(contactId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ContactModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ContactModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.Contact fetchContact(
		long contactId) {

		return getService().fetchContact(contactId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.portal.kernel.model.Contact>
		getCompanyContacts(long companyId, int start, int end) {

		return getService().getCompanyContacts(companyId, start, end);
	}

	public static int getCompanyContactsCount(long companyId) {
		return getService().getCompanyContactsCount(companyId);
	}

	/**
	 * Returns the contact with the primary key.
	 *
	 * @param contactId the primary key of the contact
	 * @return the contact
	 * @throws PortalException if a contact with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Contact getContact(
			long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContact(contactId);
	}

	/**
	 * Returns a range of all the contacts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ContactModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts
	 * @param end the upper bound of the range of contacts (not inclusive)
	 * @return the range of contacts
	 */
	public static java.util.List<com.liferay.portal.kernel.model.Contact>
		getContacts(int start, int end) {

		return getService().getContacts(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Contact>
		getContacts(
			long classNameId, long classPK, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Contact> orderByComparator) {

		return getService().getContacts(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns the number of contacts.
	 *
	 * @return the number of contacts
	 */
	public static int getContactsCount() {
		return getService().getContactsCount();
	}

	public static int getContactsCount(long classNameId, long classPK) {
		return getService().getContactsCount(classNameId, classPK);
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
	 * Updates the contact in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ContactLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param contact the contact
	 * @return the contact that was updated
	 */
	public static com.liferay.portal.kernel.model.Contact updateContact(
		com.liferay.portal.kernel.model.Contact contact) {

		return getService().updateContact(contact);
	}

	public static com.liferay.portal.kernel.model.Contact updateContact(
			long contactId, String emailAddress, String firstName,
			String middleName, String lastName, long prefixId, long suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String smsSn, String facebookSn, String jabberSn, String skypeSn,
			String twitterSn, String jobTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateContact(
			contactId, emailAddress, firstName, middleName, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, smsSn,
			facebookSn, jabberSn, skypeSn, twitterSn, jobTitle);
	}

	public static ContactLocalService getService() {
		if (_service == null) {
			_service = (ContactLocalService)PortalBeanLocatorUtil.locate(
				ContactLocalService.class.getName());
		}

		return _service;
	}

	private static ContactLocalService _service;

}