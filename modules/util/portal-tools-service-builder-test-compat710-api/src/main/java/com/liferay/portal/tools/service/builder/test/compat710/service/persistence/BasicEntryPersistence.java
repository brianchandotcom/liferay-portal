/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.compat710.exception.NoSuchBasicEntryException;
import com.liferay.portal.tools.service.builder.test.compat710.model.BasicEntry;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

/**
 * The persistence interface for the basic entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BasicEntryUtil
 * @generated
 */
@ProviderType
public interface BasicEntryPersistence extends BasePersistence<BasicEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BasicEntryUtil} to access the basic entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */
	@Override
	public Map<Serializable, BasicEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys);

	/**
	 * Returns all the basic entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching basic entries
	 */
	public java.util.List<BasicEntry> findByGroupId(long groupId);

	/**
	 * Returns a range of all the basic entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @return the range of matching basic entries
	 */
	public java.util.List<BasicEntry> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the basic entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching basic entries
	 */
	public java.util.List<BasicEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BasicEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the basic entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching basic entries
	 */
	public java.util.List<BasicEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BasicEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching basic entry
	 * @throws NoSuchBasicEntryException if a matching basic entry could not be found
	 */
	public BasicEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<BasicEntry>
				orderByComparator)
		throws NoSuchBasicEntryException;

	/**
	 * Returns the first basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	public BasicEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BasicEntry>
			orderByComparator);

	/**
	 * Returns the last basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching basic entry
	 * @throws NoSuchBasicEntryException if a matching basic entry could not be found
	 */
	public BasicEntry findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<BasicEntry>
				orderByComparator)
		throws NoSuchBasicEntryException;

	/**
	 * Returns the last basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	public BasicEntry fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<BasicEntry>
			orderByComparator);

	/**
	 * Returns the basic entries before and after the current basic entry in the ordered set where groupId = &#63;.
	 *
	 * @param basicEntryId the primary key of the current basic entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next basic entry
	 * @throws NoSuchBasicEntryException if a basic entry with the primary key could not be found
	 */
	public BasicEntry[] findByGroupId_PrevAndNext(
			long basicEntryId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<BasicEntry>
				orderByComparator)
		throws NoSuchBasicEntryException;

	/**
	 * Removes all the basic entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of basic entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching basic entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the basic entry where companyId = &#63; and name = &#63; or throws a <code>NoSuchBasicEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching basic entry
	 * @throws NoSuchBasicEntryException if a matching basic entry could not be found
	 */
	public BasicEntry findByC_N(long companyId, String name)
		throws NoSuchBasicEntryException;

	/**
	 * Returns the basic entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	public BasicEntry fetchByC_N(long companyId, String name);

	/**
	 * Returns the basic entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching basic entry, or <code>null</code> if a matching basic entry could not be found
	 */
	public BasicEntry fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the basic entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the basic entry that was removed
	 */
	public BasicEntry removeByC_N(long companyId, String name)
		throws NoSuchBasicEntryException;

	/**
	 * Returns the number of basic entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching basic entries
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Caches the basic entry in the entity cache if it is enabled.
	 *
	 * @param basicEntry the basic entry
	 */
	public void cacheResult(BasicEntry basicEntry);

	/**
	 * Caches the basic entries in the entity cache if it is enabled.
	 *
	 * @param basicEntries the basic entries
	 */
	public void cacheResult(java.util.List<BasicEntry> basicEntries);

	/**
	 * Creates a new basic entry with the primary key. Does not add the basic entry to the database.
	 *
	 * @param basicEntryId the primary key for the new basic entry
	 * @return the new basic entry
	 */
	public BasicEntry create(long basicEntryId);

	/**
	 * Removes the basic entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry that was removed
	 * @throws NoSuchBasicEntryException if a basic entry with the primary key could not be found
	 */
	public BasicEntry remove(long basicEntryId)
		throws NoSuchBasicEntryException;

	public BasicEntry updateImpl(BasicEntry basicEntry);

	/**
	 * Returns the basic entry with the primary key or throws a <code>NoSuchBasicEntryException</code> if it could not be found.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry
	 * @throws NoSuchBasicEntryException if a basic entry with the primary key could not be found
	 */
	public BasicEntry findByPrimaryKey(long basicEntryId)
		throws NoSuchBasicEntryException;

	/**
	 * Returns the basic entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry, or <code>null</code> if a basic entry with the primary key could not be found
	 */
	public BasicEntry fetchByPrimaryKey(long basicEntryId);

	/**
	 * Returns all the basic entries.
	 *
	 * @return the basic entries
	 */
	public java.util.List<BasicEntry> findAll();

	/**
	 * Returns a range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @return the range of basic entries
	 */
	public java.util.List<BasicEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of basic entries
	 */
	public java.util.List<BasicEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BasicEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of basic entries
	 */
	public java.util.List<BasicEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BasicEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the basic entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of basic entries.
	 *
	 * @return the number of basic entries
	 */
	public int countAll();

	/**
	 * Returns the primaryKeys of mapping entries associated with the basic entry.
	 *
	 * @param pk the primary key of the basic entry
	 * @return long[] of the primaryKeys of mapping entries associated with the basic entry
	 */
	public long[] getMappingEntryPrimaryKeys(long pk);

	/**
	 * Returns all the mapping entries associated with the basic entry.
	 *
	 * @param pk the primary key of the basic entry
	 * @return the mapping entries associated with the basic entry
	 */
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat710.model.
			MappingEntry> getMappingEntries(long pk);

	/**
	 * Returns a range of all the mapping entries associated with the basic entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the basic entry
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @return the range of mapping entries associated with the basic entry
	 */
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat710.model.
			MappingEntry> getMappingEntries(long pk, int start, int end);

	/**
	 * Returns an ordered range of all the mapping entries associated with the basic entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the basic entry
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mapping entries associated with the basic entry
	 */
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat710.model.
			MappingEntry> getMappingEntries(
				long pk, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.tools.service.builder.test.compat710.
						model.MappingEntry> orderByComparator);

	/**
	 * Returns the number of mapping entries associated with the basic entry.
	 *
	 * @param pk the primary key of the basic entry
	 * @return the number of mapping entries associated with the basic entry
	 */
	public int getMappingEntriesSize(long pk);

	/**
	 * Returns <code>true</code> if the mapping entry is associated with the basic entry.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPK the primary key of the mapping entry
	 * @return <code>true</code> if the mapping entry is associated with the basic entry; <code>false</code> otherwise
	 */
	public boolean containsMappingEntry(long pk, long mappingEntryPK);

	/**
	 * Returns <code>true</code> if the basic entry has any mapping entries associated with it.
	 *
	 * @param pk the primary key of the basic entry to check for associations with mapping entries
	 * @return <code>true</code> if the basic entry has any mapping entries associated with it; <code>false</code> otherwise
	 */
	public boolean containsMappingEntries(long pk);

	/**
	 * Adds an association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPK the primary key of the mapping entry
	 */
	public void addMappingEntry(long pk, long mappingEntryPK);

	/**
	 * Adds an association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntry the mapping entry
	 */
	public void addMappingEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.compat710.model.
			MappingEntry mappingEntry);

	/**
	 * Adds an association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPKs the primary keys of the mapping entries
	 */
	public void addMappingEntries(long pk, long[] mappingEntryPKs);

	/**
	 * Adds an association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntries the mapping entries
	 */
	public void addMappingEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.compat710.model.
				MappingEntry> mappingEntries);

	/**
	 * Clears all associations between the basic entry and its mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry to clear the associated mapping entries from
	 */
	public void clearMappingEntries(long pk);

	/**
	 * Removes the association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPK the primary key of the mapping entry
	 */
	public void removeMappingEntry(long pk, long mappingEntryPK);

	/**
	 * Removes the association between the basic entry and the mapping entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntry the mapping entry
	 */
	public void removeMappingEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.compat710.model.
			MappingEntry mappingEntry);

	/**
	 * Removes the association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPKs the primary keys of the mapping entries
	 */
	public void removeMappingEntries(long pk, long[] mappingEntryPKs);

	/**
	 * Removes the association between the basic entry and the mapping entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntries the mapping entries
	 */
	public void removeMappingEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.compat710.model.
				MappingEntry> mappingEntries);

	/**
	 * Sets the mapping entries associated with the basic entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntryPKs the primary keys of the mapping entries to be associated with the basic entry
	 */
	public void setMappingEntries(long pk, long[] mappingEntryPKs);

	/**
	 * Sets the mapping entries associated with the basic entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the basic entry
	 * @param mappingEntries the mapping entries to be associated with the basic entry
	 */
	public void setMappingEntries(
		long pk,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.compat710.model.
				MappingEntry> mappingEntries);

}
// SB-Hash:-512109146