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
import com.liferay.portal.workflow.kaleo.exception.NoSuchInstanceException;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the kaleo instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoInstanceUtil
 * @generated
 */
@ProviderType
public interface KaleoInstancePersistence
	extends BasePersistence<KaleoInstance> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoInstanceUtil} to access the kaleo instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the kaleo instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the kaleo instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the first kaleo instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	public KaleoInstance[] findByCompanyId_PrevAndNext(
			long kaleoInstanceId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Removes all the kaleo instances where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of kaleo instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo instances
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the kaleo instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId);

	/**
	 * Returns a range of all the kaleo instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the first kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the last kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the last kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	public KaleoInstance[] findByKaleoDefinitionVersionId_PrevAndNext(
			long kaleoInstanceId, long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Removes all the kaleo instances where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	public void removeByKaleoDefinitionVersionId(long kaleoDefinitionVersionId);

	/**
	 * Returns the number of kaleo instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo instances
	 */
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId);

	/**
	 * Returns all the kaleo instances where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByC_U(long companyId, long userId);

	/**
	 * Returns a range of all the kaleo instances where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByC_U(
		long companyId, long userId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByC_U(
		long companyId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByC_U(
		long companyId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo instance in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByC_U_First(
			long companyId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the first kaleo instance in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByC_U_First(
		long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByC_U_Last(
			long companyId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByC_U_Last(
		long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	public KaleoInstance[] findByC_U_PrevAndNext(
			long kaleoInstanceId, long companyId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Removes all the kaleo instances where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	public void removeByC_U(long companyId, long userId);

	/**
	 * Returns the number of kaleo instances where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching kaleo instances
	 */
	public int countByC_U(long companyId, long userId);

	/**
	 * Returns all the kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @return the matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByKDVI_C(
		long kaleoDefinitionVersionId, boolean completed);

	/**
	 * Returns a range of all the kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByKDVI_C(
		long kaleoDefinitionVersionId, boolean completed, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByKDVI_C(
		long kaleoDefinitionVersionId, boolean completed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByKDVI_C(
		long kaleoDefinitionVersionId, boolean completed, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByKDVI_C_First(
			long kaleoDefinitionVersionId, boolean completed,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the first kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByKDVI_C_First(
		long kaleoDefinitionVersionId, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the last kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByKDVI_C_Last(
			long kaleoDefinitionVersionId, boolean completed,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the last kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByKDVI_C_Last(
		long kaleoDefinitionVersionId, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	public KaleoInstance[] findByKDVI_C_PrevAndNext(
			long kaleoInstanceId, long kaleoDefinitionVersionId,
			boolean completed,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Removes all the kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 */
	public void removeByKDVI_C(
		long kaleoDefinitionVersionId, boolean completed);

	/**
	 * Returns the number of kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @return the number of matching kaleo instances
	 */
	public int countByKDVI_C(long kaleoDefinitionVersionId, boolean completed);

	/**
	 * Returns all the kaleo instances where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByCN_CPK(
		String className, long classPK);

	/**
	 * Returns a range of all the kaleo instances where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByCN_CPK(
		String className, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo instances where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByCN_CPK(
		String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo instances where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByCN_CPK(
		String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo instance in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByCN_CPK_First(
			String className, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the first kaleo instance in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByCN_CPK_First(
		String className, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the last kaleo instance in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByCN_CPK_Last(
			String className, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the last kaleo instance in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByCN_CPK_Last(
		String className, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	public KaleoInstance[] findByCN_CPK_PrevAndNext(
			long kaleoInstanceId, String className, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Removes all the kaleo instances where className = &#63; and classPK = &#63; from the database.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 */
	public void removeByCN_CPK(String className, long classPK);

	/**
	 * Returns the number of kaleo instances where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the number of matching kaleo instances
	 */
	public int countByCN_CPK(String className, long classPK);

	/**
	 * Returns all the kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @return the matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate);

	/**
	 * Returns a range of all the kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	public java.util.List<KaleoInstance> findByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo instance in the ordered set where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByC_KDN_KDV_CD_First(
			long companyId, String kaleoDefinitionName,
			int kaleoDefinitionVersion, Date completionDate,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the first kaleo instance in the ordered set where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByC_KDN_KDV_CD_First(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	public KaleoInstance findByC_KDN_KDV_CD_Last(
			long companyId, String kaleoDefinitionName,
			int kaleoDefinitionVersion, Date completionDate,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	public KaleoInstance fetchByC_KDN_KDV_CD_Last(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	public KaleoInstance[] findByC_KDN_KDV_CD_PrevAndNext(
			long kaleoInstanceId, long companyId, String kaleoDefinitionName,
			int kaleoDefinitionVersion, Date completionDate,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
				orderByComparator)
		throws NoSuchInstanceException;

	/**
	 * Removes all the kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 */
	public void removeByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate);

	/**
	 * Returns the number of kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @return the number of matching kaleo instances
	 */
	public int countByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate);

	/**
	 * Caches the kaleo instance in the entity cache if it is enabled.
	 *
	 * @param kaleoInstance the kaleo instance
	 */
	public void cacheResult(KaleoInstance kaleoInstance);

	/**
	 * Caches the kaleo instances in the entity cache if it is enabled.
	 *
	 * @param kaleoInstances the kaleo instances
	 */
	public void cacheResult(java.util.List<KaleoInstance> kaleoInstances);

	/**
	 * Creates a new kaleo instance with the primary key. Does not add the kaleo instance to the database.
	 *
	 * @param kaleoInstanceId the primary key for the new kaleo instance
	 * @return the new kaleo instance
	 */
	public KaleoInstance create(long kaleoInstanceId);

	/**
	 * Removes the kaleo instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstanceId the primary key of the kaleo instance
	 * @return the kaleo instance that was removed
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	public KaleoInstance remove(long kaleoInstanceId)
		throws NoSuchInstanceException;

	public KaleoInstance updateImpl(KaleoInstance kaleoInstance);

	/**
	 * Returns the kaleo instance with the primary key or throws a <code>NoSuchInstanceException</code> if it could not be found.
	 *
	 * @param kaleoInstanceId the primary key of the kaleo instance
	 * @return the kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	public KaleoInstance findByPrimaryKey(long kaleoInstanceId)
		throws NoSuchInstanceException;

	/**
	 * Returns the kaleo instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoInstanceId the primary key of the kaleo instance
	 * @return the kaleo instance, or <code>null</code> if a kaleo instance with the primary key could not be found
	 */
	public KaleoInstance fetchByPrimaryKey(long kaleoInstanceId);

	/**
	 * Returns all the kaleo instances.
	 *
	 * @return the kaleo instances
	 */
	public java.util.List<KaleoInstance> findAll();

	/**
	 * Returns a range of all the kaleo instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of kaleo instances
	 */
	public java.util.List<KaleoInstance> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the kaleo instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo instances
	 */
	public java.util.List<KaleoInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo instances
	 */
	public java.util.List<KaleoInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoInstance>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the kaleo instances from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of kaleo instances.
	 *
	 * @return the number of kaleo instances
	 */
	public int countAll();

}