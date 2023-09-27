/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.service.persistence;

import com.liferay.commerce.notification.exception.NoSuchNotificationAttachmentException;
import com.liferay.commerce.notification.model.CommerceNotificationAttachment;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce notification attachment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationAttachmentUtil
 * @generated
 */
@ProviderType
public interface CommerceNotificationAttachmentPersistence
	extends BasePersistence<CommerceNotificationAttachment> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceNotificationAttachmentUtil} to access the commerce notification attachment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce notification attachments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findByUuid(
		String uuid);

	/**
	 * Returns a range of all the commerce notification attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationAttachment> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationAttachment> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationAttachment> orderByComparator);

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationAttachment> orderByComparator);

	/**
	 * Returns the commerce notification attachments before and after the current commerce notification attachment in the ordered set where uuid = &#63;.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the current commerce notification attachment
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	public CommerceNotificationAttachment[] findByUuid_PrevAndNext(
			long commerceNotificationAttachmentId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Removes all the commerce notification attachments where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce notification attachments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce notification attachments
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the commerce notification attachment where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchNotificationAttachmentException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment findByUUID_G(
			String uuid, long groupId)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Returns the commerce notification attachment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment fetchByUUID_G(
		String uuid, long groupId);

	/**
	 * Returns the commerce notification attachment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the commerce notification attachment where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce notification attachment that was removed
	 */
	public CommerceNotificationAttachment removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Returns the number of commerce notification attachments where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce notification attachments
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationAttachment> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationAttachment> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Returns the first commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationAttachment> orderByComparator);

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Returns the last commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationAttachment> orderByComparator);

	/**
	 * Returns the commerce notification attachments before and after the current commerce notification attachment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the current commerce notification attachment
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	public CommerceNotificationAttachment[] findByUuid_C_PrevAndNext(
			long commerceNotificationAttachmentId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Removes all the commerce notification attachments where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce notification attachments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce notification attachments
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @return the matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId);

	/**
	 * Returns a range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationAttachment> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment>
		findByCommerceNotificationQueueEntryId(
			long commerceNotificationQueueEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationAttachment> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment
			findByCommerceNotificationQueueEntryId_First(
				long commerceNotificationQueueEntryId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Returns the first commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment
		fetchByCommerceNotificationQueueEntryId_First(
			long commerceNotificationQueueEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationAttachment> orderByComparator);

	/**
	 * Returns the last commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment
			findByCommerceNotificationQueueEntryId_Last(
				long commerceNotificationQueueEntryId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Returns the last commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification attachment, or <code>null</code> if a matching commerce notification attachment could not be found
	 */
	public CommerceNotificationAttachment
		fetchByCommerceNotificationQueueEntryId_Last(
			long commerceNotificationQueueEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceNotificationAttachment> orderByComparator);

	/**
	 * Returns the commerce notification attachments before and after the current commerce notification attachment in the ordered set where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the current commerce notification attachment
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	public CommerceNotificationAttachment[]
			findByCommerceNotificationQueueEntryId_PrevAndNext(
				long commerceNotificationAttachmentId,
				long commerceNotificationQueueEntryId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceNotificationAttachment> orderByComparator)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Removes all the commerce notification attachments where commerceNotificationQueueEntryId = &#63; from the database.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 */
	public void removeByCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId);

	/**
	 * Returns the number of commerce notification attachments where commerceNotificationQueueEntryId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID
	 * @return the number of matching commerce notification attachments
	 */
	public int countByCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId);

	/**
	 * Caches the commerce notification attachment in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationAttachment the commerce notification attachment
	 */
	public void cacheResult(
		CommerceNotificationAttachment commerceNotificationAttachment);

	/**
	 * Caches the commerce notification attachments in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationAttachments the commerce notification attachments
	 */
	public void cacheResult(
		java.util.List<CommerceNotificationAttachment>
			commerceNotificationAttachments);

	/**
	 * Creates a new commerce notification attachment with the primary key. Does not add the commerce notification attachment to the database.
	 *
	 * @param commerceNotificationAttachmentId the primary key for the new commerce notification attachment
	 * @return the new commerce notification attachment
	 */
	public CommerceNotificationAttachment create(
		long commerceNotificationAttachmentId);

	/**
	 * Removes the commerce notification attachment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the commerce notification attachment
	 * @return the commerce notification attachment that was removed
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	public CommerceNotificationAttachment remove(
			long commerceNotificationAttachmentId)
		throws NoSuchNotificationAttachmentException;

	public CommerceNotificationAttachment updateImpl(
		CommerceNotificationAttachment commerceNotificationAttachment);

	/**
	 * Returns the commerce notification attachment with the primary key or throws a <code>NoSuchNotificationAttachmentException</code> if it could not be found.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the commerce notification attachment
	 * @return the commerce notification attachment
	 * @throws NoSuchNotificationAttachmentException if a commerce notification attachment with the primary key could not be found
	 */
	public CommerceNotificationAttachment findByPrimaryKey(
			long commerceNotificationAttachmentId)
		throws NoSuchNotificationAttachmentException;

	/**
	 * Returns the commerce notification attachment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationAttachmentId the primary key of the commerce notification attachment
	 * @return the commerce notification attachment, or <code>null</code> if a commerce notification attachment with the primary key could not be found
	 */
	public CommerceNotificationAttachment fetchByPrimaryKey(
		long commerceNotificationAttachmentId);

	/**
	 * Returns all the commerce notification attachments.
	 *
	 * @return the commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findAll();

	/**
	 * Returns a range of all the commerce notification attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @return the range of commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce notification attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationAttachment> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce notification attachments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationAttachmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification attachments
	 * @param end the upper bound of the range of commerce notification attachments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce notification attachments
	 */
	public java.util.List<CommerceNotificationAttachment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceNotificationAttachment> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce notification attachments from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce notification attachments.
	 *
	 * @return the number of commerce notification attachments
	 */
	public int countAll();

}