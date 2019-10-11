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

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.exception.NoSuchNotificationException;
import com.liferay.portal.workflow.kaleo.model.KaleoNotification;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the kaleo notification service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNotificationUtil
 * @generated
 */
@ProviderType
public interface KaleoNotificationPersistence
	extends BasePersistence<KaleoNotification> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoNotificationUtil} to access the kaleo notification persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the kaleo notifications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the kaleo notifications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @return the range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo notifications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo notifications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo notification in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification
	 * @throws NoSuchNotificationException if a matching kaleo notification could not be found
	 */
	public KaleoNotification findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the first kaleo notification in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification, or <code>null</code> if a matching kaleo notification could not be found
	 */
	public KaleoNotification fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns the last kaleo notification in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification
	 * @throws NoSuchNotificationException if a matching kaleo notification could not be found
	 */
	public KaleoNotification findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the last kaleo notification in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification, or <code>null</code> if a matching kaleo notification could not be found
	 */
	public KaleoNotification fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns the kaleo notifications before and after the current kaleo notification in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoNotificationId the primary key of the current kaleo notification
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo notification
	 * @throws NoSuchNotificationException if a kaleo notification with the primary key could not be found
	 */
	public KaleoNotification[] findByCompanyId_PrevAndNext(
			long kaleoNotificationId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Removes all the kaleo notifications where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of kaleo notifications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo notifications
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the kaleo notifications where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId);

	/**
	 * Returns a range of all the kaleo notifications where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @return the range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo notifications where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo notifications where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo notification in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification
	 * @throws NoSuchNotificationException if a matching kaleo notification could not be found
	 */
	public KaleoNotification findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the first kaleo notification in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification, or <code>null</code> if a matching kaleo notification could not be found
	 */
	public KaleoNotification fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns the last kaleo notification in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification
	 * @throws NoSuchNotificationException if a matching kaleo notification could not be found
	 */
	public KaleoNotification findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the last kaleo notification in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification, or <code>null</code> if a matching kaleo notification could not be found
	 */
	public KaleoNotification fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns the kaleo notifications before and after the current kaleo notification in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoNotificationId the primary key of the current kaleo notification
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo notification
	 * @throws NoSuchNotificationException if a kaleo notification with the primary key could not be found
	 */
	public KaleoNotification[] findByKaleoDefinitionVersionId_PrevAndNext(
			long kaleoNotificationId, long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Removes all the kaleo notifications where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	public void removeByKaleoDefinitionVersionId(long kaleoDefinitionVersionId);

	/**
	 * Returns the number of kaleo notifications where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo notifications
	 */
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId);

	/**
	 * Returns all the kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @return the matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK);

	/**
	 * Returns a range of all the kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @return the range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKCN_KCPK(
		String kaleoClassName, long kaleoClassPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo notification in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification
	 * @throws NoSuchNotificationException if a matching kaleo notification could not be found
	 */
	public KaleoNotification findByKCN_KCPK_First(
			String kaleoClassName, long kaleoClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the first kaleo notification in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification, or <code>null</code> if a matching kaleo notification could not be found
	 */
	public KaleoNotification fetchByKCN_KCPK_First(
		String kaleoClassName, long kaleoClassPK,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns the last kaleo notification in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification
	 * @throws NoSuchNotificationException if a matching kaleo notification could not be found
	 */
	public KaleoNotification findByKCN_KCPK_Last(
			String kaleoClassName, long kaleoClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the last kaleo notification in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification, or <code>null</code> if a matching kaleo notification could not be found
	 */
	public KaleoNotification fetchByKCN_KCPK_Last(
		String kaleoClassName, long kaleoClassPK,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns the kaleo notifications before and after the current kaleo notification in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoNotificationId the primary key of the current kaleo notification
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo notification
	 * @throws NoSuchNotificationException if a kaleo notification with the primary key could not be found
	 */
	public KaleoNotification[] findByKCN_KCPK_PrevAndNext(
			long kaleoNotificationId, String kaleoClassName, long kaleoClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Removes all the kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63; from the database.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 */
	public void removeByKCN_KCPK(String kaleoClassName, long kaleoClassPK);

	/**
	 * Returns the number of kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @return the number of matching kaleo notifications
	 */
	public int countByKCN_KCPK(String kaleoClassName, long kaleoClassPK);

	/**
	 * Returns all the kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63; and executionType = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param executionType the execution type
	 * @return the matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKCN_KCPK_ET(
		String kaleoClassName, long kaleoClassPK, String executionType);

	/**
	 * Returns a range of all the kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63; and executionType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param executionType the execution type
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @return the range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKCN_KCPK_ET(
		String kaleoClassName, long kaleoClassPK, String executionType,
		int start, int end);

	/**
	 * Returns an ordered range of all the kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63; and executionType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param executionType the execution type
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKCN_KCPK_ET(
		String kaleoClassName, long kaleoClassPK, String executionType,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63; and executionType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param executionType the execution type
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo notifications
	 */
	public java.util.List<KaleoNotification> findByKCN_KCPK_ET(
		String kaleoClassName, long kaleoClassPK, String executionType,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo notification in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and executionType = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param executionType the execution type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification
	 * @throws NoSuchNotificationException if a matching kaleo notification could not be found
	 */
	public KaleoNotification findByKCN_KCPK_ET_First(
			String kaleoClassName, long kaleoClassPK, String executionType,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the first kaleo notification in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and executionType = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param executionType the execution type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo notification, or <code>null</code> if a matching kaleo notification could not be found
	 */
	public KaleoNotification fetchByKCN_KCPK_ET_First(
		String kaleoClassName, long kaleoClassPK, String executionType,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns the last kaleo notification in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and executionType = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param executionType the execution type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification
	 * @throws NoSuchNotificationException if a matching kaleo notification could not be found
	 */
	public KaleoNotification findByKCN_KCPK_ET_Last(
			String kaleoClassName, long kaleoClassPK, String executionType,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the last kaleo notification in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and executionType = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param executionType the execution type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo notification, or <code>null</code> if a matching kaleo notification could not be found
	 */
	public KaleoNotification fetchByKCN_KCPK_ET_Last(
		String kaleoClassName, long kaleoClassPK, String executionType,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns the kaleo notifications before and after the current kaleo notification in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and executionType = &#63;.
	 *
	 * @param kaleoNotificationId the primary key of the current kaleo notification
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param executionType the execution type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo notification
	 * @throws NoSuchNotificationException if a kaleo notification with the primary key could not be found
	 */
	public KaleoNotification[] findByKCN_KCPK_ET_PrevAndNext(
			long kaleoNotificationId, String kaleoClassName, long kaleoClassPK,
			String executionType,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Removes all the kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63; and executionType = &#63; from the database.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param executionType the execution type
	 */
	public void removeByKCN_KCPK_ET(
		String kaleoClassName, long kaleoClassPK, String executionType);

	/**
	 * Returns the number of kaleo notifications where kaleoClassName = &#63; and kaleoClassPK = &#63; and executionType = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param executionType the execution type
	 * @return the number of matching kaleo notifications
	 */
	public int countByKCN_KCPK_ET(
		String kaleoClassName, long kaleoClassPK, String executionType);

	/**
	 * Caches the kaleo notification in the entity cache if it is enabled.
	 *
	 * @param kaleoNotification the kaleo notification
	 */
	public void cacheResult(KaleoNotification kaleoNotification);

	/**
	 * Caches the kaleo notifications in the entity cache if it is enabled.
	 *
	 * @param kaleoNotifications the kaleo notifications
	 */
	public void cacheResult(
		java.util.List<KaleoNotification> kaleoNotifications);

	/**
	 * Creates a new kaleo notification with the primary key. Does not add the kaleo notification to the database.
	 *
	 * @param kaleoNotificationId the primary key for the new kaleo notification
	 * @return the new kaleo notification
	 */
	public KaleoNotification create(long kaleoNotificationId);

	/**
	 * Removes the kaleo notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNotificationId the primary key of the kaleo notification
	 * @return the kaleo notification that was removed
	 * @throws NoSuchNotificationException if a kaleo notification with the primary key could not be found
	 */
	public KaleoNotification remove(long kaleoNotificationId)
		throws NoSuchNotificationException;

	public KaleoNotification updateImpl(KaleoNotification kaleoNotification);

	/**
	 * Returns the kaleo notification with the primary key or throws a <code>NoSuchNotificationException</code> if it could not be found.
	 *
	 * @param kaleoNotificationId the primary key of the kaleo notification
	 * @return the kaleo notification
	 * @throws NoSuchNotificationException if a kaleo notification with the primary key could not be found
	 */
	public KaleoNotification findByPrimaryKey(long kaleoNotificationId)
		throws NoSuchNotificationException;

	/**
	 * Returns the kaleo notification with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoNotificationId the primary key of the kaleo notification
	 * @return the kaleo notification, or <code>null</code> if a kaleo notification with the primary key could not be found
	 */
	public KaleoNotification fetchByPrimaryKey(long kaleoNotificationId);

	/**
	 * Returns all the kaleo notifications.
	 *
	 * @return the kaleo notifications
	 */
	public java.util.List<KaleoNotification> findAll();

	/**
	 * Returns a range of all the kaleo notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @return the range of kaleo notifications
	 */
	public java.util.List<KaleoNotification> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the kaleo notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo notifications
	 */
	public java.util.List<KaleoNotification> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo notifications
	 * @param end the upper bound of the range of kaleo notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo notifications
	 */
	public java.util.List<KaleoNotification> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the kaleo notifications from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of kaleo notifications.
	 *
	 * @return the number of kaleo notifications
	 */
	public int countAll();

}