/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.bom.service.persistence;

import com.liferay.commerce.bom.exception.NoSuchBOMDefinitionException;
import com.liferay.commerce.bom.model.CommerceBOMDefinition;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce bom definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceBOMDefinitionUtil
 * @generated
 */
@ProviderType
public interface CommerceBOMDefinitionPersistence
	extends BasePersistence<CommerceBOMDefinition> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceBOMDefinitionUtil} to access the commerce bom definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce bom definitions where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @return the matching commerce bom definitions
	 */
	public java.util.List<CommerceBOMDefinition> findByCommerceBOMFolderId(
		long commerceBOMFolderId);

	/**
	 * Returns a range of all the commerce bom definitions where commerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom definitions
	 * @param end the upper bound of the range of commerce bom definitions (not inclusive)
	 * @return the range of matching commerce bom definitions
	 */
	public java.util.List<CommerceBOMDefinition> findByCommerceBOMFolderId(
		long commerceBOMFolderId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce bom definitions where commerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom definitions
	 * @param end the upper bound of the range of commerce bom definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom definitions
	 */
	public java.util.List<CommerceBOMDefinition> findByCommerceBOMFolderId(
		long commerceBOMFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMDefinition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce bom definitions where commerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom definitions
	 * @param end the upper bound of the range of commerce bom definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce bom definitions
	 */
	public java.util.List<CommerceBOMDefinition> findByCommerceBOMFolderId(
		long commerceBOMFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMDefinition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce bom definition in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom definition
	 * @throws NoSuchBOMDefinitionException if a matching commerce bom definition could not be found
	 */
	public CommerceBOMDefinition findByCommerceBOMFolderId_First(
			long commerceBOMFolderId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMDefinition> orderByComparator)
		throws NoSuchBOMDefinitionException;

	/**
	 * Returns the first commerce bom definition in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom definition, or <code>null</code> if a matching commerce bom definition could not be found
	 */
	public CommerceBOMDefinition fetchByCommerceBOMFolderId_First(
		long commerceBOMFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMDefinition>
			orderByComparator);

	/**
	 * Returns the last commerce bom definition in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom definition
	 * @throws NoSuchBOMDefinitionException if a matching commerce bom definition could not be found
	 */
	public CommerceBOMDefinition findByCommerceBOMFolderId_Last(
			long commerceBOMFolderId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMDefinition> orderByComparator)
		throws NoSuchBOMDefinitionException;

	/**
	 * Returns the last commerce bom definition in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom definition, or <code>null</code> if a matching commerce bom definition could not be found
	 */
	public CommerceBOMDefinition fetchByCommerceBOMFolderId_Last(
		long commerceBOMFolderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMDefinition>
			orderByComparator);

	/**
	 * Returns the commerce bom definitions before and after the current commerce bom definition in the ordered set where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the primary key of the current commerce bom definition
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom definition
	 * @throws NoSuchBOMDefinitionException if a commerce bom definition with the primary key could not be found
	 */
	public CommerceBOMDefinition[] findByCommerceBOMFolderId_PrevAndNext(
			long commerceBOMDefinitionId, long commerceBOMFolderId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMDefinition> orderByComparator)
		throws NoSuchBOMDefinitionException;

	/**
	 * Returns all the commerce bom definitions that the user has permission to view where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @return the matching commerce bom definitions that the user has permission to view
	 */
	public java.util.List<CommerceBOMDefinition>
		filterFindByCommerceBOMFolderId(long commerceBOMFolderId);

	/**
	 * Returns a range of all the commerce bom definitions that the user has permission to view where commerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom definitions
	 * @param end the upper bound of the range of commerce bom definitions (not inclusive)
	 * @return the range of matching commerce bom definitions that the user has permission to view
	 */
	public java.util.List<CommerceBOMDefinition>
		filterFindByCommerceBOMFolderId(
			long commerceBOMFolderId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce bom definitions that the user has permissions to view where commerceBOMFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param start the lower bound of the range of commerce bom definitions
	 * @param end the upper bound of the range of commerce bom definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom definitions that the user has permission to view
	 */
	public java.util.List<CommerceBOMDefinition>
		filterFindByCommerceBOMFolderId(
			long commerceBOMFolderId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMDefinition> orderByComparator);

	/**
	 * Returns the commerce bom definitions before and after the current commerce bom definition in the ordered set of commerce bom definitions that the user has permission to view where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the primary key of the current commerce bom definition
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom definition
	 * @throws NoSuchBOMDefinitionException if a commerce bom definition with the primary key could not be found
	 */
	public CommerceBOMDefinition[] filterFindByCommerceBOMFolderId_PrevAndNext(
			long commerceBOMDefinitionId, long commerceBOMFolderId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceBOMDefinition> orderByComparator)
		throws NoSuchBOMDefinitionException;

	/**
	 * Removes all the commerce bom definitions where commerceBOMFolderId = &#63; from the database.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 */
	public void removeByCommerceBOMFolderId(long commerceBOMFolderId);

	/**
	 * Returns the number of commerce bom definitions where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @return the number of matching commerce bom definitions
	 */
	public int countByCommerceBOMFolderId(long commerceBOMFolderId);

	/**
	 * Returns the number of commerce bom definitions that the user has permission to view where commerceBOMFolderId = &#63;.
	 *
	 * @param commerceBOMFolderId the commerce bom folder ID
	 * @return the number of matching commerce bom definitions that the user has permission to view
	 */
	public int filterCountByCommerceBOMFolderId(long commerceBOMFolderId);

	/**
	 * Caches the commerce bom definition in the entity cache if it is enabled.
	 *
	 * @param commerceBOMDefinition the commerce bom definition
	 */
	public void cacheResult(CommerceBOMDefinition commerceBOMDefinition);

	/**
	 * Caches the commerce bom definitions in the entity cache if it is enabled.
	 *
	 * @param commerceBOMDefinitions the commerce bom definitions
	 */
	public void cacheResult(
		java.util.List<CommerceBOMDefinition> commerceBOMDefinitions);

	/**
	 * Creates a new commerce bom definition with the primary key. Does not add the commerce bom definition to the database.
	 *
	 * @param commerceBOMDefinitionId the primary key for the new commerce bom definition
	 * @return the new commerce bom definition
	 */
	public CommerceBOMDefinition create(long commerceBOMDefinitionId);

	/**
	 * Removes the commerce bom definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceBOMDefinitionId the primary key of the commerce bom definition
	 * @return the commerce bom definition that was removed
	 * @throws NoSuchBOMDefinitionException if a commerce bom definition with the primary key could not be found
	 */
	public CommerceBOMDefinition remove(long commerceBOMDefinitionId)
		throws NoSuchBOMDefinitionException;

	public CommerceBOMDefinition updateImpl(
		CommerceBOMDefinition commerceBOMDefinition);

	/**
	 * Returns the commerce bom definition with the primary key or throws a <code>NoSuchBOMDefinitionException</code> if it could not be found.
	 *
	 * @param commerceBOMDefinitionId the primary key of the commerce bom definition
	 * @return the commerce bom definition
	 * @throws NoSuchBOMDefinitionException if a commerce bom definition with the primary key could not be found
	 */
	public CommerceBOMDefinition findByPrimaryKey(long commerceBOMDefinitionId)
		throws NoSuchBOMDefinitionException;

	/**
	 * Returns the commerce bom definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceBOMDefinitionId the primary key of the commerce bom definition
	 * @return the commerce bom definition, or <code>null</code> if a commerce bom definition with the primary key could not be found
	 */
	public CommerceBOMDefinition fetchByPrimaryKey(
		long commerceBOMDefinitionId);

	/**
	 * Returns all the commerce bom definitions.
	 *
	 * @return the commerce bom definitions
	 */
	public java.util.List<CommerceBOMDefinition> findAll();

	/**
	 * Returns a range of all the commerce bom definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom definitions
	 * @param end the upper bound of the range of commerce bom definitions (not inclusive)
	 * @return the range of commerce bom definitions
	 */
	public java.util.List<CommerceBOMDefinition> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce bom definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom definitions
	 * @param end the upper bound of the range of commerce bom definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce bom definitions
	 */
	public java.util.List<CommerceBOMDefinition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMDefinition>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce bom definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom definitions
	 * @param end the upper bound of the range of commerce bom definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce bom definitions
	 */
	public java.util.List<CommerceBOMDefinition> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMDefinition>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce bom definitions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce bom definitions.
	 *
	 * @return the number of commerce bom definitions
	 */
	public int countAll();

}