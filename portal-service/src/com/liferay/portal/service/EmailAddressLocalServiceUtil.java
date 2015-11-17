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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for EmailAddress. This utility wraps
 * {@link com.liferay.portal.service.impl.EmailAddressLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see EmailAddressLocalService
 * @see com.liferay.portal.service.base.EmailAddressLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.EmailAddressLocalServiceImpl
 * @generated
 */
@ProviderType
public class EmailAddressLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.EmailAddressLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the email address to the database. Also notifies the appropriate model listeners.
	*
	* @param emailAddress the email address
	* @return the email address that was added
	*/
	public static com.liferay.portal.model.EmailAddress addEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress) {
		return getService().addEmailAddress(emailAddress);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addEmailAddress(long,
	String, long, String, int, boolean, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.EmailAddress addEmailAddress(
		long userId, java.lang.String className, long classPK,
		java.lang.String address, long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEmailAddress(userId, className, classPK, address,
			typeId, primary);
	}

	public static com.liferay.portal.model.EmailAddress addEmailAddress(
		long userId, java.lang.String className, long classPK,
		java.lang.String address, long typeId, boolean primary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEmailAddress(userId, className, classPK, address,
			typeId, primary, serviceContext);
	}

	/**
	* Creates a new email address with the primary key. Does not add the email address to the database.
	*
	* @param emailAddressId the primary key for the new email address
	* @return the new email address
	*/
	public static com.liferay.portal.model.EmailAddress createEmailAddress(
		long emailAddressId) {
		return getService().createEmailAddress(emailAddressId);
	}

	/**
	* Deletes the email address from the database. Also notifies the appropriate model listeners.
	*
	* @param emailAddress the email address
	* @return the email address that was removed
	*/
	public static com.liferay.portal.model.EmailAddress deleteEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress) {
		return getService().deleteEmailAddress(emailAddress);
	}

	/**
	* Deletes the email address with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param emailAddressId the primary key of the email address
	* @return the email address that was removed
	* @throws PortalException if a email address with the primary key could not be found
	*/
	public static com.liferay.portal.model.EmailAddress deleteEmailAddress(
		long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteEmailAddress(emailAddressId);
	}

	public static void deleteEmailAddresses(long companyId,
		java.lang.String className, long classPK) {
		getService().deleteEmailAddresses(companyId, className, classPK);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.portal.model.EmailAddress fetchEmailAddress(
		long emailAddressId) {
		return getService().fetchEmailAddress(emailAddressId);
	}

	/**
	* Returns the email address with the matching UUID and company.
	*
	* @param uuid the email address's UUID
	* @param companyId the primary key of the company
	* @return the matching email address, or <code>null</code> if a matching email address could not be found
	*/
	public static com.liferay.portal.model.EmailAddress fetchEmailAddressByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().fetchEmailAddressByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the email address with the primary key.
	*
	* @param emailAddressId the primary key of the email address
	* @return the email address
	* @throws PortalException if a email address with the primary key could not be found
	*/
	public static com.liferay.portal.model.EmailAddress getEmailAddress(
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
	public static com.liferay.portal.model.EmailAddress getEmailAddressByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEmailAddressByUuidAndCompanyId(uuid, companyId);
	}

	public static java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses() {
		return getService().getEmailAddresses();
	}

	public static java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses(
		long companyId, java.lang.String className, long classPK) {
		return getService().getEmailAddresses(companyId, className, classPK);
	}

	/**
	* Returns a range of all the email addresses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.EmailAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of email addresses
	* @param end the upper bound of the range of email addresses (not inclusive)
	* @return the range of email addresses
	*/
	public static java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses(
		int start, int end) {
		return getService().getEmailAddresses(start, end);
	}

	/**
	* Returns the number of email addresses.
	*
	* @return the number of email addresses
	*/
	public static int getEmailAddressesCount() {
		return getService().getEmailAddressesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the email address in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param emailAddress the email address
	* @return the email address that was updated
	*/
	public static com.liferay.portal.model.EmailAddress updateEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress) {
		return getService().updateEmailAddress(emailAddress);
	}

	public static com.liferay.portal.model.EmailAddress updateEmailAddress(
		long emailAddressId, java.lang.String address, long typeId,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateEmailAddress(emailAddressId, address, typeId, primary);
	}

	public static EmailAddressLocalService getService() {
		if (_service == null) {
			_service = (EmailAddressLocalService)PortalBeanLocatorUtil.locate(EmailAddressLocalService.class.getName());

			ReferenceRegistry.registerReference(EmailAddressLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(EmailAddressLocalService service) {
	}

	private static EmailAddressLocalService _service;
}