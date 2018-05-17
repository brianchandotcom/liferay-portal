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

package com.liferay.commerce.notification.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce notification template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.notification.service.persistence.impl.CommerceNotificationTemplatePersistenceImpl
 * @see CommerceNotificationTemplateUtil
 * @generated
 */
@ProviderType
public interface CommerceNotificationTemplatePersistence extends BasePersistence<CommerceNotificationTemplate> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceNotificationTemplateUtil} to access the commerce notification template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce notification templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByUuid(String uuid);

	/**
	* Returns a range of all the commerce notification templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @return the range of matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByUuid(
		String uuid, int start, int end);

	/**
	* Returns an ordered range of all the commerce notification templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the commerce notification templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce notification template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template
	* @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	* Returns the first commerce notification template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns the last commerce notification template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template
	* @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	* Returns the last commerce notification template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns the commerce notification templates before and after the current commerce notification template in the ordered set where uuid = &#63;.
	*
	* @param commerceNotificationTemplateId the primary key of the current commerce notification template
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification template
	* @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	*/
	public CommerceNotificationTemplate[] findByUuid_PrevAndNext(
		long commerceNotificationTemplateId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	* Removes all the commerce notification templates where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of commerce notification templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce notification templates
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the commerce notification template where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchNotificationTemplateException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce notification template
	* @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchNotificationTemplateException;

	/**
	* Returns the commerce notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the commerce notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the commerce notification template where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce notification template that was removed
	*/
	public CommerceNotificationTemplate removeByUUID_G(String uuid, long groupId)
		throws NoSuchNotificationTemplateException;

	/**
	* Returns the number of commerce notification templates where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce notification templates
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByUuid_C(
		String uuid, long companyId);

	/**
	* Returns a range of all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @return the range of matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template
	* @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate findByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	* Returns the first commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate fetchByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns the last commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template
	* @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate findByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	* Returns the last commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate fetchByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns the commerce notification templates before and after the current commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceNotificationTemplateId the primary key of the current commerce notification template
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification template
	* @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	*/
	public CommerceNotificationTemplate[] findByUuid_C_PrevAndNext(
		long commerceNotificationTemplateId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	* Removes all the commerce notification templates where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of commerce notification templates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce notification templates
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the commerce notification templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the commerce notification templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @return the range of matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the commerce notification templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the commerce notification templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce notification template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template
	* @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	* Returns the first commerce notification template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns the last commerce notification template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template
	* @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	* Returns the last commerce notification template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	*/
	public CommerceNotificationTemplate fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns the commerce notification templates before and after the current commerce notification template in the ordered set where groupId = &#63;.
	*
	* @param commerceNotificationTemplateId the primary key of the current commerce notification template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification template
	* @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	*/
	public CommerceNotificationTemplate[] findByGroupId_PrevAndNext(
		long commerceNotificationTemplateId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	* Returns all the commerce notification templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce notification templates that the user has permission to view
	*/
	public java.util.List<CommerceNotificationTemplate> filterFindByGroupId(
		long groupId);

	/**
	* Returns a range of all the commerce notification templates that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @return the range of matching commerce notification templates that the user has permission to view
	*/
	public java.util.List<CommerceNotificationTemplate> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the commerce notification templates that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification templates that the user has permission to view
	*/
	public java.util.List<CommerceNotificationTemplate> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns the commerce notification templates before and after the current commerce notification template in the ordered set of commerce notification templates that the user has permission to view where groupId = &#63;.
	*
	* @param commerceNotificationTemplateId the primary key of the current commerce notification template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification template
	* @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	*/
	public CommerceNotificationTemplate[] filterFindByGroupId_PrevAndNext(
		long commerceNotificationTemplateId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	* Removes all the commerce notification templates where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce notification templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce notification templates
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of commerce notification templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce notification templates that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Caches the commerce notification template in the entity cache if it is enabled.
	*
	* @param commerceNotificationTemplate the commerce notification template
	*/
	public void cacheResult(
		CommerceNotificationTemplate commerceNotificationTemplate);

	/**
	* Caches the commerce notification templates in the entity cache if it is enabled.
	*
	* @param commerceNotificationTemplates the commerce notification templates
	*/
	public void cacheResult(
		java.util.List<CommerceNotificationTemplate> commerceNotificationTemplates);

	/**
	* Creates a new commerce notification template with the primary key. Does not add the commerce notification template to the database.
	*
	* @param commerceNotificationTemplateId the primary key for the new commerce notification template
	* @return the new commerce notification template
	*/
	public CommerceNotificationTemplate create(
		long commerceNotificationTemplateId);

	/**
	* Removes the commerce notification template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateId the primary key of the commerce notification template
	* @return the commerce notification template that was removed
	* @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	*/
	public CommerceNotificationTemplate remove(
		long commerceNotificationTemplateId)
		throws NoSuchNotificationTemplateException;

	public CommerceNotificationTemplate updateImpl(
		CommerceNotificationTemplate commerceNotificationTemplate);

	/**
	* Returns the commerce notification template with the primary key or throws a {@link NoSuchNotificationTemplateException} if it could not be found.
	*
	* @param commerceNotificationTemplateId the primary key of the commerce notification template
	* @return the commerce notification template
	* @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	*/
	public CommerceNotificationTemplate findByPrimaryKey(
		long commerceNotificationTemplateId)
		throws NoSuchNotificationTemplateException;

	/**
	* Returns the commerce notification template with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceNotificationTemplateId the primary key of the commerce notification template
	* @return the commerce notification template, or <code>null</code> if a commerce notification template with the primary key could not be found
	*/
	public CommerceNotificationTemplate fetchByPrimaryKey(
		long commerceNotificationTemplateId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceNotificationTemplate> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce notification templates.
	*
	* @return the commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findAll();

	/**
	* Returns a range of all the commerce notification templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @return the range of commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the commerce notification templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the commerce notification templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification templates
	* @param end the upper bound of the range of commerce notification templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce notification templates
	*/
	public java.util.List<CommerceNotificationTemplate> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce notification templates from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce notification templates.
	*
	* @return the number of commerce notification templates
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}