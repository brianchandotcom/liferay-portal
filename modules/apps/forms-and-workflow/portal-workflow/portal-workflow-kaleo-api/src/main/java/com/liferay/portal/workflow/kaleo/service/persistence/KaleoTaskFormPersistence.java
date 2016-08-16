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
import com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;

/**
 * The persistence interface for the kaleo task form service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoTaskFormPersistenceImpl
 * @see KaleoTaskFormUtil
 * @generated
 */
@ProviderType
public interface KaleoTaskFormPersistence extends BasePersistence<KaleoTaskForm> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoTaskFormUtil} to access the kaleo task form persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the kaleo task forms where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByCompanyId(long companyId);

	/**
	* Returns a range of all the kaleo task forms where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @return the range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the kaleo task forms where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo task forms where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first kaleo task form in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Returns the first kaleo task form in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns the last kaleo task form in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Returns the last kaleo task form in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns the kaleo task forms before and after the current kaleo task form in the ordered set where companyId = &#63;.
	*
	* @param kaleoTaskFormId the primary key of the current kaleo task form
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public KaleoTaskForm[] findByCompanyId_PrevAndNext(long kaleoTaskFormId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Removes all the kaleo task forms where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of kaleo task forms where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching kaleo task forms
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the kaleo task forms where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @return the matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoDefinitionId(
		long kaleoDefinitionId);

	/**
	* Returns a range of all the kaleo task forms where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @return the range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end);

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first kaleo task form in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm findByKaleoDefinitionId_First(long kaleoDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Returns the first kaleo task form in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm fetchByKaleoDefinitionId_First(
		long kaleoDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns the last kaleo task form in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm findByKaleoDefinitionId_Last(long kaleoDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Returns the last kaleo task form in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm fetchByKaleoDefinitionId_Last(long kaleoDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns the kaleo task forms before and after the current kaleo task form in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoTaskFormId the primary key of the current kaleo task form
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public KaleoTaskForm[] findByKaleoDefinitionId_PrevAndNext(
		long kaleoTaskFormId, long kaleoDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Removes all the kaleo task forms where kaleoDefinitionId = &#63; from the database.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	*/
	public void removeByKaleoDefinitionId(long kaleoDefinitionId);

	/**
	* Returns the number of kaleo task forms where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @return the number of matching kaleo task forms
	*/
	public int countByKaleoDefinitionId(long kaleoDefinitionId);

	/**
	* Returns all the kaleo task forms where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @return the matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoNodeId(long kaleoNodeId);

	/**
	* Returns a range of all the kaleo task forms where kaleoNodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoNodeId the kaleo node ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @return the range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoNodeId(long kaleoNodeId,
		int start, int end);

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoNodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoNodeId the kaleo node ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoNodeId(long kaleoNodeId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoNodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoNodeId the kaleo node ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoNodeId(long kaleoNodeId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first kaleo task form in the ordered set where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm findByKaleoNodeId_First(long kaleoNodeId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Returns the first kaleo task form in the ordered set where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm fetchByKaleoNodeId_First(long kaleoNodeId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns the last kaleo task form in the ordered set where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm findByKaleoNodeId_Last(long kaleoNodeId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Returns the last kaleo task form in the ordered set where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm fetchByKaleoNodeId_Last(long kaleoNodeId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns the kaleo task forms before and after the current kaleo task form in the ordered set where kaleoNodeId = &#63;.
	*
	* @param kaleoTaskFormId the primary key of the current kaleo task form
	* @param kaleoNodeId the kaleo node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public KaleoTaskForm[] findByKaleoNodeId_PrevAndNext(long kaleoTaskFormId,
		long kaleoNodeId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Removes all the kaleo task forms where kaleoNodeId = &#63; from the database.
	*
	* @param kaleoNodeId the kaleo node ID
	*/
	public void removeByKaleoNodeId(long kaleoNodeId);

	/**
	* Returns the number of kaleo task forms where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @return the number of matching kaleo task forms
	*/
	public int countByKaleoNodeId(long kaleoNodeId);

	/**
	* Returns all the kaleo task forms where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @return the matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoTaskId(long kaleoTaskId);

	/**
	* Returns a range of all the kaleo task forms where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @return the range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoTaskId(long kaleoTaskId,
		int start, int end);

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoTaskId(long kaleoTaskId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findByKaleoTaskId(long kaleoTaskId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first kaleo task form in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm findByKaleoTaskId_First(long kaleoTaskId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Returns the first kaleo task form in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm fetchByKaleoTaskId_First(long kaleoTaskId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns the last kaleo task form in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm findByKaleoTaskId_Last(long kaleoTaskId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Returns the last kaleo task form in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm fetchByKaleoTaskId_Last(long kaleoTaskId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns the kaleo task forms before and after the current kaleo task form in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskFormId the primary key of the current kaleo task form
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public KaleoTaskForm[] findByKaleoTaskId_PrevAndNext(long kaleoTaskFormId,
		long kaleoTaskId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator)
		throws NoSuchTaskFormException;

	/**
	* Removes all the kaleo task forms where kaleoTaskId = &#63; from the database.
	*
	* @param kaleoTaskId the kaleo task ID
	*/
	public void removeByKaleoTaskId(long kaleoTaskId);

	/**
	* Returns the number of kaleo task forms where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @return the number of matching kaleo task forms
	*/
	public int countByKaleoTaskId(long kaleoTaskId);

	/**
	* Returns the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; or throws a {@link NoSuchTaskFormException} if it could not be found.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param formUuid the form uuid
	* @return the matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm findByFormUuid_KTI(long kaleoTaskId,
		java.lang.String formUuid) throws NoSuchTaskFormException;

	/**
	* Returns the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param formUuid the form uuid
	* @return the matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm fetchByFormUuid_KTI(long kaleoTaskId,
		java.lang.String formUuid);

	/**
	* Returns the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param formUuid the form uuid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public KaleoTaskForm fetchByFormUuid_KTI(long kaleoTaskId,
		java.lang.String formUuid, boolean retrieveFromCache);

	/**
	* Removes the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; from the database.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param formUuid the form uuid
	* @return the kaleo task form that was removed
	*/
	public KaleoTaskForm removeByFormUuid_KTI(long kaleoTaskId,
		java.lang.String formUuid) throws NoSuchTaskFormException;

	/**
	* Returns the number of kaleo task forms where kaleoTaskId = &#63; and formUuid = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param formUuid the form uuid
	* @return the number of matching kaleo task forms
	*/
	public int countByFormUuid_KTI(long kaleoTaskId, java.lang.String formUuid);

	/**
	* Caches the kaleo task form in the entity cache if it is enabled.
	*
	* @param kaleoTaskForm the kaleo task form
	*/
	public void cacheResult(KaleoTaskForm kaleoTaskForm);

	/**
	* Caches the kaleo task forms in the entity cache if it is enabled.
	*
	* @param kaleoTaskForms the kaleo task forms
	*/
	public void cacheResult(java.util.List<KaleoTaskForm> kaleoTaskForms);

	/**
	* Creates a new kaleo task form with the primary key. Does not add the kaleo task form to the database.
	*
	* @param kaleoTaskFormId the primary key for the new kaleo task form
	* @return the new kaleo task form
	*/
	public KaleoTaskForm create(long kaleoTaskFormId);

	/**
	* Removes the kaleo task form with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoTaskFormId the primary key of the kaleo task form
	* @return the kaleo task form that was removed
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public KaleoTaskForm remove(long kaleoTaskFormId)
		throws NoSuchTaskFormException;

	public KaleoTaskForm updateImpl(KaleoTaskForm kaleoTaskForm);

	/**
	* Returns the kaleo task form with the primary key or throws a {@link NoSuchTaskFormException} if it could not be found.
	*
	* @param kaleoTaskFormId the primary key of the kaleo task form
	* @return the kaleo task form
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public KaleoTaskForm findByPrimaryKey(long kaleoTaskFormId)
		throws NoSuchTaskFormException;

	/**
	* Returns the kaleo task form with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param kaleoTaskFormId the primary key of the kaleo task form
	* @return the kaleo task form, or <code>null</code> if a kaleo task form with the primary key could not be found
	*/
	public KaleoTaskForm fetchByPrimaryKey(long kaleoTaskFormId);

	@Override
	public java.util.Map<java.io.Serializable, KaleoTaskForm> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the kaleo task forms.
	*
	* @return the kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findAll();

	/**
	* Returns a range of all the kaleo task forms.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @return the range of kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findAll(int start, int end);

	/**
	* Returns an ordered range of all the kaleo task forms.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator);

	/**
	* Returns an ordered range of all the kaleo task forms.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of kaleo task forms
	*/
	public java.util.List<KaleoTaskForm> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the kaleo task forms from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of kaleo task forms.
	*
	* @return the number of kaleo task forms
	*/
	public int countAll();
}