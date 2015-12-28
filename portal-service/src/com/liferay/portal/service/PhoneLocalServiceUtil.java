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
 * Provides the local service utility for Phone. This utility wraps
 * {@link com.liferay.portal.service.impl.PhoneLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see PhoneLocalService
 * @see com.liferay.portal.service.base.PhoneLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.PhoneLocalServiceImpl
 * @generated
 */
@ProviderType
public class PhoneLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.PhoneLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the phone to the database. Also notifies the appropriate model listeners.
	*
	* @param phone the phone
	* @return the phone that was added
	*/
	public static com.liferay.portal.model.Phone addPhone(
		com.liferay.portal.model.Phone phone) {
		return getService().addPhone(phone);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addPhone(long, String, long,
	String, String, int, boolean, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.Phone addPhone(long userId,
		java.lang.String className, long classPK, java.lang.String number,
		java.lang.String extension, long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addPhone(userId, className, classPK, number, extension,
			typeId, primary);
	}

	public static com.liferay.portal.model.Phone addPhone(long userId,
		java.lang.String className, long classPK, java.lang.String number,
		java.lang.String extension, long typeId, boolean primary,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addPhone(userId, className, classPK, number, extension,
			typeId, primary, serviceContext);
	}

	/**
	* Creates a new phone with the primary key. Does not add the phone to the database.
	*
	* @param phoneId the primary key for the new phone
	* @return the new phone
	*/
	public static com.liferay.portal.model.Phone createPhone(long phoneId) {
		return getService().createPhone(phoneId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the phone from the database. Also notifies the appropriate model listeners.
	*
	* @param phone the phone
	* @return the phone that was removed
	*/
	public static com.liferay.portal.model.Phone deletePhone(
		com.liferay.portal.model.Phone phone) {
		return getService().deletePhone(phone);
	}

	/**
	* Deletes the phone with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param phoneId the primary key of the phone
	* @return the phone that was removed
	* @throws PortalException if a phone with the primary key could not be found
	*/
	public static com.liferay.portal.model.Phone deletePhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePhone(phoneId);
	}

	public static void deletePhones(long companyId, java.lang.String className,
		long classPK) {
		getService().deletePhones(companyId, className, classPK);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portal.model.Phone fetchPhone(long phoneId) {
		return getService().fetchPhone(phoneId);
	}

	/**
	* Returns the phone with the matching UUID and company.
	*
	* @param uuid the phone's UUID
	* @param companyId the primary key of the company
	* @return the matching phone, or <code>null</code> if a matching phone could not be found
	*/
	public static com.liferay.portal.model.Phone fetchPhoneByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().fetchPhoneByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
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
	* Returns the phone with the primary key.
	*
	* @param phoneId the primary key of the phone
	* @return the phone
	* @throws PortalException if a phone with the primary key could not be found
	*/
	public static com.liferay.portal.model.Phone getPhone(long phoneId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPhone(phoneId);
	}

	/**
	* Returns the phone with the matching UUID and company.
	*
	* @param uuid the phone's UUID
	* @param companyId the primary key of the company
	* @return the matching phone
	* @throws PortalException if a matching phone could not be found
	*/
	public static com.liferay.portal.model.Phone getPhoneByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPhoneByUuidAndCompanyId(uuid, companyId);
	}

	public static java.util.List<com.liferay.portal.model.Phone> getPhones() {
		return getService().getPhones();
	}

	public static java.util.List<com.liferay.portal.model.Phone> getPhones(
		long companyId, java.lang.String className, long classPK) {
		return getService().getPhones(companyId, className, classPK);
	}

	/**
	* Returns a range of all the phones.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PhoneModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of phones
	* @param end the upper bound of the range of phones (not inclusive)
	* @return the range of phones
	*/
	public static java.util.List<com.liferay.portal.model.Phone> getPhones(
		int start, int end) {
		return getService().getPhones(start, end);
	}

	/**
	* Returns the number of phones.
	*
	* @return the number of phones
	*/
	public static int getPhonesCount() {
		return getService().getPhonesCount();
	}

	/**
	* Updates the phone in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param phone the phone
	* @return the phone that was updated
	*/
	public static com.liferay.portal.model.Phone updatePhone(
		com.liferay.portal.model.Phone phone) {
		return getService().updatePhone(phone);
	}

	public static com.liferay.portal.model.Phone updatePhone(long phoneId,
		java.lang.String number, java.lang.String extension, long typeId,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePhone(phoneId, number, extension, typeId, primary);
	}

	public static PhoneLocalService getService() {
		if (_service == null) {
			_service = (PhoneLocalService)PortalBeanLocatorUtil.locate(PhoneLocalService.class.getName());

			ReferenceRegistry.registerReference(PhoneLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static PhoneLocalService _service;
}