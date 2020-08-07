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

import com.liferay.commerce.bom.exception.NoSuchBOMEntryException;
import com.liferay.commerce.bom.model.CommerceBOMEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce bom entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceBOMEntryUtil
 * @generated
 */
@ProviderType
public interface CommerceBOMEntryPersistence
	extends BasePersistence<CommerceBOMEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceBOMEntryUtil} to access the commerce bom entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @return the matching commerce bom entries
	 */
	public java.util.List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId);

	/**
	 * Returns a range of all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @return the range of matching commerce bom entries
	 */
	public java.util.List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom entries
	 */
	public java.util.List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce bom entries
	 */
	public java.util.List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom entry
	 * @throws NoSuchBOMEntryException if a matching commerce bom entry could not be found
	 */
	public CommerceBOMEntry findByCommerceBOMDefinitionId_First(
			long commerceBOMDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMEntry>
				orderByComparator)
		throws NoSuchBOMEntryException;

	/**
	 * Returns the first commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom entry, or <code>null</code> if a matching commerce bom entry could not be found
	 */
	public CommerceBOMEntry fetchByCommerceBOMDefinitionId_First(
		long commerceBOMDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMEntry>
			orderByComparator);

	/**
	 * Returns the last commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom entry
	 * @throws NoSuchBOMEntryException if a matching commerce bom entry could not be found
	 */
	public CommerceBOMEntry findByCommerceBOMDefinitionId_Last(
			long commerceBOMDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMEntry>
				orderByComparator)
		throws NoSuchBOMEntryException;

	/**
	 * Returns the last commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom entry, or <code>null</code> if a matching commerce bom entry could not be found
	 */
	public CommerceBOMEntry fetchByCommerceBOMDefinitionId_Last(
		long commerceBOMDefinitionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMEntry>
			orderByComparator);

	/**
	 * Returns the commerce bom entries before and after the current commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMEntryId the primary key of the current commerce bom entry
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom entry
	 * @throws NoSuchBOMEntryException if a commerce bom entry with the primary key could not be found
	 */
	public CommerceBOMEntry[] findByCommerceBOMDefinitionId_PrevAndNext(
			long commerceBOMEntryId, long commerceBOMDefinitionId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMEntry>
				orderByComparator)
		throws NoSuchBOMEntryException;

	/**
	 * Removes all the commerce bom entries where commerceBOMDefinitionId = &#63; from the database.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 */
	public void removeByCommerceBOMDefinitionId(long commerceBOMDefinitionId);

	/**
	 * Returns the number of commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @return the number of matching commerce bom entries
	 */
	public int countByCommerceBOMDefinitionId(long commerceBOMDefinitionId);

	/**
	 * Caches the commerce bom entry in the entity cache if it is enabled.
	 *
	 * @param commerceBOMEntry the commerce bom entry
	 */
	public void cacheResult(CommerceBOMEntry commerceBOMEntry);

	/**
	 * Caches the commerce bom entries in the entity cache if it is enabled.
	 *
	 * @param commerceBOMEntries the commerce bom entries
	 */
	public void cacheResult(
		java.util.List<CommerceBOMEntry> commerceBOMEntries);

	/**
	 * Creates a new commerce bom entry with the primary key. Does not add the commerce bom entry to the database.
	 *
	 * @param commerceBOMEntryId the primary key for the new commerce bom entry
	 * @return the new commerce bom entry
	 */
	public CommerceBOMEntry create(long commerceBOMEntryId);

	/**
	 * Removes the commerce bom entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceBOMEntryId the primary key of the commerce bom entry
	 * @return the commerce bom entry that was removed
	 * @throws NoSuchBOMEntryException if a commerce bom entry with the primary key could not be found
	 */
	public CommerceBOMEntry remove(long commerceBOMEntryId)
		throws NoSuchBOMEntryException;

	public CommerceBOMEntry updateImpl(CommerceBOMEntry commerceBOMEntry);

	/**
	 * Returns the commerce bom entry with the primary key or throws a <code>NoSuchBOMEntryException</code> if it could not be found.
	 *
	 * @param commerceBOMEntryId the primary key of the commerce bom entry
	 * @return the commerce bom entry
	 * @throws NoSuchBOMEntryException if a commerce bom entry with the primary key could not be found
	 */
	public CommerceBOMEntry findByPrimaryKey(long commerceBOMEntryId)
		throws NoSuchBOMEntryException;

	/**
	 * Returns the commerce bom entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceBOMEntryId the primary key of the commerce bom entry
	 * @return the commerce bom entry, or <code>null</code> if a commerce bom entry with the primary key could not be found
	 */
	public CommerceBOMEntry fetchByPrimaryKey(long commerceBOMEntryId);

	/**
	 * Returns all the commerce bom entries.
	 *
	 * @return the commerce bom entries
	 */
	public java.util.List<CommerceBOMEntry> findAll();

	/**
	 * Returns a range of all the commerce bom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @return the range of commerce bom entries
	 */
	public java.util.List<CommerceBOMEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce bom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce bom entries
	 */
	public java.util.List<CommerceBOMEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce bom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce bom entries
	 */
	public java.util.List<CommerceBOMEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceBOMEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce bom entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce bom entries.
	 *
	 * @return the number of commerce bom entries
	 */
	public int countAll();

}