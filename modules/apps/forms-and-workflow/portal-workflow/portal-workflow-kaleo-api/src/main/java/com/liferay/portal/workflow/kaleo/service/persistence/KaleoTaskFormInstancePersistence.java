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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance;

/**
 * The persistence interface for the kaleo task form instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoTaskFormInstancePersistenceImpl
 * @see KaleoTaskFormInstanceUtil
 * @generated
 */
@ProviderType
public interface KaleoTaskFormInstancePersistence extends BasePersistence<KaleoTaskFormInstance> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoTaskFormInstanceUtil} to access the kaleo task form instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the kaleo task form instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByCompanyId(long companyId);

	/**
	* Returns a range of all the kaleo task form instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the kaleo task form instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo task form instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first kaleo task form instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the first kaleo task form instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns the last kaleo task form instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the last kaleo task form instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where companyId = &#63;.
	*
	* @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public KaleoTaskFormInstance[] findByCompanyId_PrevAndNext(
		long kaleoTaskFormInstanceId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Removes all the kaleo task form instances where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of kaleo task form instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching kaleo task form instances
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the kaleo task form instances where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @return the matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId);

	/**
	* Returns a range of all the kaleo task form instances where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end);

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance findByKaleoDefinitionId_First(
		long kaleoDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByKaleoDefinitionId_First(
		long kaleoDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance findByKaleoDefinitionId_Last(
		long kaleoDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByKaleoDefinitionId_Last(
		long kaleoDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public KaleoTaskFormInstance[] findByKaleoDefinitionId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Removes all the kaleo task form instances where kaleoDefinitionId = &#63; from the database.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	*/
	public void removeByKaleoDefinitionId(long kaleoDefinitionId);

	/**
	* Returns the number of kaleo task form instances where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @return the number of matching kaleo task form instances
	*/
	public int countByKaleoDefinitionId(long kaleoDefinitionId);

	/**
	* Returns all the kaleo task form instances where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @return the matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId);

	/**
	* Returns a range of all the kaleo task form instances where kaleoInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end);

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance findByKaleoInstanceId_First(
		long kaleoInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByKaleoInstanceId_First(
		long kaleoInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance findByKaleoInstanceId_Last(
		long kaleoInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByKaleoInstanceId_Last(
		long kaleoInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	*
	* @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	* @param kaleoInstanceId the kaleo instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public KaleoTaskFormInstance[] findByKaleoInstanceId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Removes all the kaleo task form instances where kaleoInstanceId = &#63; from the database.
	*
	* @param kaleoInstanceId the kaleo instance ID
	*/
	public void removeByKaleoInstanceId(long kaleoInstanceId);

	/**
	* Returns the number of kaleo task form instances where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @return the number of matching kaleo task form instances
	*/
	public int countByKaleoInstanceId(long kaleoInstanceId);

	/**
	* Returns all the kaleo task form instances where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @return the matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoTaskId(
		long kaleoTaskId);

	/**
	* Returns a range of all the kaleo task form instances where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoTaskId(
		long kaleoTaskId, int start, int end);

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoTaskId(
		long kaleoTaskId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoTaskId(
		long kaleoTaskId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance findByKaleoTaskId_First(long kaleoTaskId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByKaleoTaskId_First(long kaleoTaskId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance findByKaleoTaskId_Last(long kaleoTaskId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByKaleoTaskId_Last(long kaleoTaskId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public KaleoTaskFormInstance[] findByKaleoTaskId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoTaskId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Removes all the kaleo task form instances where kaleoTaskId = &#63; from the database.
	*
	* @param kaleoTaskId the kaleo task ID
	*/
	public void removeByKaleoTaskId(long kaleoTaskId);

	/**
	* Returns the number of kaleo task form instances where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @return the number of matching kaleo task form instances
	*/
	public int countByKaleoTaskId(long kaleoTaskId);

	/**
	* Returns all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @return the matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId);

	/**
	* Returns a range of all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end);

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance findByKaleoTaskInstanceTokenId_First(
		long kaleoTaskInstanceTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByKaleoTaskInstanceTokenId_First(
		long kaleoTaskInstanceTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance findByKaleoTaskInstanceTokenId_Last(
		long kaleoTaskInstanceTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByKaleoTaskInstanceTokenId_Last(
		long kaleoTaskInstanceTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public KaleoTaskFormInstance[] findByKaleoTaskInstanceTokenId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoTaskInstanceTokenId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException;

	/**
	* Removes all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63; from the database.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	*/
	public void removeByKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId);

	/**
	* Returns the number of kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @return the number of matching kaleo task form instances
	*/
	public int countByKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId);

	/**
	* Returns the kaleo task form instance where kaleoTaskFormId = &#63; or throws a {@link NoSuchTaskFormInstanceException} if it could not be found.
	*
	* @param kaleoTaskFormId the kaleo task form ID
	* @return the matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance findByKaleoTaskFormId(long kaleoTaskFormId)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the kaleo task form instance where kaleoTaskFormId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param kaleoTaskFormId the kaleo task form ID
	* @return the matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByKaleoTaskFormId(long kaleoTaskFormId);

	/**
	* Returns the kaleo task form instance where kaleoTaskFormId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param kaleoTaskFormId the kaleo task form ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public KaleoTaskFormInstance fetchByKaleoTaskFormId(long kaleoTaskFormId,
		boolean retrieveFromCache);

	/**
	* Removes the kaleo task form instance where kaleoTaskFormId = &#63; from the database.
	*
	* @param kaleoTaskFormId the kaleo task form ID
	* @return the kaleo task form instance that was removed
	*/
	public KaleoTaskFormInstance removeByKaleoTaskFormId(long kaleoTaskFormId)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the number of kaleo task form instances where kaleoTaskFormId = &#63;.
	*
	* @param kaleoTaskFormId the kaleo task form ID
	* @return the number of matching kaleo task form instances
	*/
	public int countByKaleoTaskFormId(long kaleoTaskFormId);

	/**
	* Caches the kaleo task form instance in the entity cache if it is enabled.
	*
	* @param kaleoTaskFormInstance the kaleo task form instance
	*/
	public void cacheResult(KaleoTaskFormInstance kaleoTaskFormInstance);

	/**
	* Caches the kaleo task form instances in the entity cache if it is enabled.
	*
	* @param kaleoTaskFormInstances the kaleo task form instances
	*/
	public void cacheResult(
		java.util.List<KaleoTaskFormInstance> kaleoTaskFormInstances);

	/**
	* Creates a new kaleo task form instance with the primary key. Does not add the kaleo task form instance to the database.
	*
	* @param kaleoTaskFormInstanceId the primary key for the new kaleo task form instance
	* @return the new kaleo task form instance
	*/
	public KaleoTaskFormInstance create(long kaleoTaskFormInstanceId);

	/**
	* Removes the kaleo task form instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	* @return the kaleo task form instance that was removed
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public KaleoTaskFormInstance remove(long kaleoTaskFormInstanceId)
		throws NoSuchTaskFormInstanceException;

	public KaleoTaskFormInstance updateImpl(
		KaleoTaskFormInstance kaleoTaskFormInstance);

	/**
	* Returns the kaleo task form instance with the primary key or throws a {@link NoSuchTaskFormInstanceException} if it could not be found.
	*
	* @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	* @return the kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public KaleoTaskFormInstance findByPrimaryKey(long kaleoTaskFormInstanceId)
		throws NoSuchTaskFormInstanceException;

	/**
	* Returns the kaleo task form instance with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	* @return the kaleo task form instance, or <code>null</code> if a kaleo task form instance with the primary key could not be found
	*/
	public KaleoTaskFormInstance fetchByPrimaryKey(long kaleoTaskFormInstanceId);

	@Override
	public java.util.Map<java.io.Serializable, KaleoTaskFormInstance> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the kaleo task form instances.
	*
	* @return the kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findAll();

	/**
	* Returns a range of all the kaleo task form instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findAll(int start, int end);

	/**
	* Returns an ordered range of all the kaleo task form instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo task form instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of kaleo task form instances
	*/
	public java.util.List<KaleoTaskFormInstance> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the kaleo task form instances from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of kaleo task form instances.
	*
	* @return the number of kaleo task form instances
	*/
	public int countAll();
}