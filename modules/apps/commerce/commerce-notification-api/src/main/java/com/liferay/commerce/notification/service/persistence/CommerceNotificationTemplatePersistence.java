/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.service.persistence;

import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce notification template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateUtil
 * @generated
 */
@ProviderType
public interface CommerceNotificationTemplatePersistence
	extends BasePersistence<CommerceNotificationTemplate> {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
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
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the first commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
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
	 * Returns the commerce notification template where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchNotificationTemplateException</code> if it could not be found.
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
	public CommerceNotificationTemplate fetchByUUID_G(
		String uuid, long groupId);

	/**
	 * Returns the commerce notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the commerce notification template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce notification template that was removed
	 */
	public CommerceNotificationTemplate removeByUUID_G(
			String uuid, long groupId)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
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
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the first commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the last commerce notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
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
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
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
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
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
	 * Returns all the commerce notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the matching commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findByG_E(
		long groupId, boolean enabled);

	/**
	 * Returns a range of all the commerce notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findByG_E(
		long groupId, boolean enabled, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate findByG_E_First(
			long groupId, boolean enabled,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate fetchByG_E_First(
		long groupId, boolean enabled,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate findByG_E_Last(
			long groupId, boolean enabled,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate fetchByG_E_Last(
		long groupId, boolean enabled,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set where groupId = &#63; and enabled = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public CommerceNotificationTemplate[] findByG_E_PrevAndNext(
			long commerceNotificationTemplateId, long groupId, boolean enabled,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns all the commerce notification templates that the user has permission to view where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the matching commerce notification templates that the user has permission to view
	 */
	public java.util.List<CommerceNotificationTemplate> filterFindByG_E(
		long groupId, boolean enabled);

	/**
	 * Returns a range of all the commerce notification templates that the user has permission to view where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates that the user has permission to view
	 */
	public java.util.List<CommerceNotificationTemplate> filterFindByG_E(
		long groupId, boolean enabled, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification templates that the user has permissions to view where groupId = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates that the user has permission to view
	 */
	public java.util.List<CommerceNotificationTemplate> filterFindByG_E(
		long groupId, boolean enabled, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set of commerce notification templates that the user has permission to view where groupId = &#63; and enabled = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public CommerceNotificationTemplate[] filterFindByG_E_PrevAndNext(
			long commerceNotificationTemplateId, long groupId, boolean enabled,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Removes all the commerce notification templates where groupId = &#63; and enabled = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 */
	public void removeByG_E(long groupId, boolean enabled);

	/**
	 * Returns the number of commerce notification templates where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the number of matching commerce notification templates
	 */
	public int countByG_E(long groupId, boolean enabled);

	/**
	 * Returns the number of commerce notification templates that the user has permission to view where groupId = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param enabled the enabled
	 * @return the number of matching commerce notification templates that the user has permission to view
	 */
	public int filterCountByG_E(long groupId, boolean enabled);

	/**
	 * Returns all the commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @return the matching commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findByG_T_E(
		long groupId, String type, boolean enabled);

	/**
	 * Returns a range of all the commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findByG_T_E(
		long groupId, String type, boolean enabled, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findByG_T_E(
		long groupId, String type, boolean enabled, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findByG_T_E(
		long groupId, String type, boolean enabled, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate findByG_T_E_First(
			long groupId, String type, boolean enabled,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the first commerce notification template in the ordered set where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate fetchByG_T_E_First(
		long groupId, String type, boolean enabled,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template
	 * @throws NoSuchNotificationTemplateException if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate findByG_T_E_Last(
			long groupId, String type, boolean enabled,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns the last commerce notification template in the ordered set where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template, or <code>null</code> if a matching commerce notification template could not be found
	 */
	public CommerceNotificationTemplate fetchByG_T_E_Last(
		long groupId, String type, boolean enabled,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public CommerceNotificationTemplate[] findByG_T_E_PrevAndNext(
			long commerceNotificationTemplateId, long groupId, String type,
			boolean enabled,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Returns all the commerce notification templates that the user has permission to view where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @return the matching commerce notification templates that the user has permission to view
	 */
	public java.util.List<CommerceNotificationTemplate> filterFindByG_T_E(
		long groupId, String type, boolean enabled);

	/**
	 * Returns a range of all the commerce notification templates that the user has permission to view where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of matching commerce notification templates that the user has permission to view
	 */
	public java.util.List<CommerceNotificationTemplate> filterFindByG_T_E(
		long groupId, String type, boolean enabled, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification templates that the user has permissions to view where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification templates that the user has permission to view
	 */
	public java.util.List<CommerceNotificationTemplate> filterFindByG_T_E(
		long groupId, String type, boolean enabled, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns the commerce notification templates before and after the current commerce notification template in the ordered set of commerce notification templates that the user has permission to view where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param commerceNotificationTemplateId the primary key of the current commerce notification template
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template
	 * @throws NoSuchNotificationTemplateException if a commerce notification template with the primary key could not be found
	 */
	public CommerceNotificationTemplate[] filterFindByG_T_E_PrevAndNext(
			long commerceNotificationTemplateId, long groupId, String type,
			boolean enabled,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException;

	/**
	 * Removes all the commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 */
	public void removeByG_T_E(long groupId, String type, boolean enabled);

	/**
	 * Returns the number of commerce notification templates where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @return the number of matching commerce notification templates
	 */
	public int countByG_T_E(long groupId, String type, boolean enabled);

	/**
	 * Returns the number of commerce notification templates that the user has permission to view where groupId = &#63; and type = &#63; and enabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param enabled the enabled
	 * @return the number of matching commerce notification templates that the user has permission to view
	 */
	public int filterCountByG_T_E(long groupId, String type, boolean enabled);

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
		java.util.List<CommerceNotificationTemplate>
			commerceNotificationTemplates);

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
	 * Returns the commerce notification template with the primary key or throws a <code>NoSuchNotificationTemplateException</code> if it could not be found.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @return the range of commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification templates
	 * @param end the upper bound of the range of commerce notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce notification templates
	 */
	public java.util.List<CommerceNotificationTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationTemplate> orderByComparator,
		boolean useFinderCache);

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

}