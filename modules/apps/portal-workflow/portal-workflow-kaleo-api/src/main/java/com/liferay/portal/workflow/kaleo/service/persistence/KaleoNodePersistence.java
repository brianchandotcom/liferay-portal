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
import com.liferay.portal.workflow.kaleo.exception.NoSuchNodeException;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the kaleo node service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNodeUtil
 * @generated
 */
@ProviderType
public interface KaleoNodePersistence extends BasePersistence<KaleoNode> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoNodeUtil} to access the kaleo node persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the kaleo nodes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the kaleo nodes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	public KaleoNode findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
				orderByComparator)
		throws NoSuchNodeException;

	/**
	 * Returns the first kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	public KaleoNode fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator);

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	public KaleoNode findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
				orderByComparator)
		throws NoSuchNodeException;

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	public KaleoNode fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator);

	/**
	 * Returns the kaleo nodes before and after the current kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoNodeId the primary key of the current kaleo node
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	public KaleoNode[] findByCompanyId_PrevAndNext(
			long kaleoNodeId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
				orderByComparator)
		throws NoSuchNodeException;

	/**
	 * Removes all the kaleo nodes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of kaleo nodes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo nodes
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId);

	/**
	 * Returns a range of all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	public KaleoNode findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
				orderByComparator)
		throws NoSuchNodeException;

	/**
	 * Returns the first kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	public KaleoNode fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator);

	/**
	 * Returns the last kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	public KaleoNode findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
				orderByComparator)
		throws NoSuchNodeException;

	/**
	 * Returns the last kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	public KaleoNode fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator);

	/**
	 * Returns the kaleo nodes before and after the current kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoNodeId the primary key of the current kaleo node
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	public KaleoNode[] findByKaleoDefinitionVersionId_PrevAndNext(
			long kaleoNodeId, long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
				orderByComparator)
		throws NoSuchNodeException;

	/**
	 * Removes all the kaleo nodes where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	public void removeByKaleoDefinitionVersionId(long kaleoDefinitionVersionId);

	/**
	 * Returns the number of kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo nodes
	 */
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId);

	/**
	 * Returns all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByC_KDVI(
		long companyId, long kaleoDefinitionVersionId);

	/**
	 * Returns a range of all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByC_KDVI(
		long companyId, long kaleoDefinitionVersionId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByC_KDVI(
		long companyId, long kaleoDefinitionVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo nodes
	 */
	public java.util.List<KaleoNode> findByC_KDVI(
		long companyId, long kaleoDefinitionVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	public KaleoNode findByC_KDVI_First(
			long companyId, long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
				orderByComparator)
		throws NoSuchNodeException;

	/**
	 * Returns the first kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	public KaleoNode fetchByC_KDVI_First(
		long companyId, long kaleoDefinitionVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator);

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	public KaleoNode findByC_KDVI_Last(
			long companyId, long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
				orderByComparator)
		throws NoSuchNodeException;

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	public KaleoNode fetchByC_KDVI_Last(
		long companyId, long kaleoDefinitionVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator);

	/**
	 * Returns the kaleo nodes before and after the current kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoNodeId the primary key of the current kaleo node
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	public KaleoNode[] findByC_KDVI_PrevAndNext(
			long kaleoNodeId, long companyId, long kaleoDefinitionVersionId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
				orderByComparator)
		throws NoSuchNodeException;

	/**
	 * Removes all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	public void removeByC_KDVI(long companyId, long kaleoDefinitionVersionId);

	/**
	 * Returns the number of kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo nodes
	 */
	public int countByC_KDVI(long companyId, long kaleoDefinitionVersionId);

	/**
	 * Caches the kaleo node in the entity cache if it is enabled.
	 *
	 * @param kaleoNode the kaleo node
	 */
	public void cacheResult(KaleoNode kaleoNode);

	/**
	 * Caches the kaleo nodes in the entity cache if it is enabled.
	 *
	 * @param kaleoNodes the kaleo nodes
	 */
	public void cacheResult(java.util.List<KaleoNode> kaleoNodes);

	/**
	 * Creates a new kaleo node with the primary key. Does not add the kaleo node to the database.
	 *
	 * @param kaleoNodeId the primary key for the new kaleo node
	 * @return the new kaleo node
	 */
	public KaleoNode create(long kaleoNodeId);

	/**
	 * Removes the kaleo node with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNodeId the primary key of the kaleo node
	 * @return the kaleo node that was removed
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	public KaleoNode remove(long kaleoNodeId) throws NoSuchNodeException;

	public KaleoNode updateImpl(KaleoNode kaleoNode);

	/**
	 * Returns the kaleo node with the primary key or throws a <code>NoSuchNodeException</code> if it could not be found.
	 *
	 * @param kaleoNodeId the primary key of the kaleo node
	 * @return the kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	public KaleoNode findByPrimaryKey(long kaleoNodeId)
		throws NoSuchNodeException;

	/**
	 * Returns the kaleo node with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoNodeId the primary key of the kaleo node
	 * @return the kaleo node, or <code>null</code> if a kaleo node with the primary key could not be found
	 */
	public KaleoNode fetchByPrimaryKey(long kaleoNodeId);

	/**
	 * Returns all the kaleo nodes.
	 *
	 * @return the kaleo nodes
	 */
	public java.util.List<KaleoNode> findAll();

	/**
	 * Returns a range of all the kaleo nodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of kaleo nodes
	 */
	public java.util.List<KaleoNode> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the kaleo nodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo nodes
	 */
	public java.util.List<KaleoNode> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo nodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo nodes
	 */
	public java.util.List<KaleoNode> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNode>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the kaleo nodes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of kaleo nodes.
	 *
	 * @return the number of kaleo nodes
	 */
	public int countAll();

}