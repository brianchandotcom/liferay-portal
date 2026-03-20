/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchDefinedDefaultOrderEntryException;
import com.liferay.portal.tools.service.builder.test.model.DefinedDefaultOrderEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the defined default order entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DefinedDefaultOrderEntryUtil
 * @generated
 */
@ProviderType
public interface DefinedDefaultOrderEntryPersistence
	extends BasePersistence<DefinedDefaultOrderEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DefinedDefaultOrderEntryUtil} to access the defined default order entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the defined default order entry where name = &#63; or throws a <code>NoSuchDefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a matching defined default order entry could not be found
	 */
	public DefinedDefaultOrderEntry findByName(String name)
		throws NoSuchDefinedDefaultOrderEntryException;

	/**
	 * Returns the defined default order entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching defined default order entry, or <code>null</code> if a matching defined default order entry could not be found
	 */
	public DefinedDefaultOrderEntry fetchByName(String name);

	/**
	 * Returns the defined default order entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching defined default order entry, or <code>null</code> if a matching defined default order entry could not be found
	 */
	public DefinedDefaultOrderEntry fetchByName(
		String name, boolean useFinderCache);

	/**
	 * Removes the defined default order entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the defined default order entry that was removed
	 */
	public DefinedDefaultOrderEntry removeByName(String name)
		throws NoSuchDefinedDefaultOrderEntryException;

	/**
	 * Returns the number of defined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching defined default order entries
	 */
	public int countByName(String name);

	/**
	 * Returns all the defined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching defined default order entries
	 */
	public java.util.List<DefinedDefaultOrderEntry> findByName_Collection(
		String name);

	/**
	 * Returns a range of all the defined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @return the range of matching defined default order entries
	 */
	public java.util.List<DefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end);

	/**
	 * Returns an ordered range of all the defined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching defined default order entries
	 */
	public java.util.List<DefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DefinedDefaultOrderEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the defined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching defined default order entries
	 */
	public java.util.List<DefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DefinedDefaultOrderEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a matching defined default order entry could not be found
	 */
	public DefinedDefaultOrderEntry findByName_Collection_First(
			String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<DefinedDefaultOrderEntry> orderByComparator)
		throws NoSuchDefinedDefaultOrderEntryException;

	/**
	 * Returns the first defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching defined default order entry, or <code>null</code> if a matching defined default order entry could not be found
	 */
	public DefinedDefaultOrderEntry fetchByName_Collection_First(
		String name,
		com.liferay.portal.kernel.util.OrderByComparator
			<DefinedDefaultOrderEntry> orderByComparator);

	/**
	 * Removes all the defined default order entries where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	public void removeByName_Collection(String name);

	/**
	 * Returns the number of defined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching defined default order entries
	 */
	public int countByName_Collection(String name);

	/**
	 * Caches the defined default order entry in the entity cache if it is enabled.
	 *
	 * @param definedDefaultOrderEntry the defined default order entry
	 */
	public void cacheResult(DefinedDefaultOrderEntry definedDefaultOrderEntry);

	/**
	 * Caches the defined default order entries in the entity cache if it is enabled.
	 *
	 * @param definedDefaultOrderEntries the defined default order entries
	 */
	public void cacheResult(
		java.util.List<DefinedDefaultOrderEntry> definedDefaultOrderEntries);

	/**
	 * Creates a new defined default order entry with the primary key. Does not add the defined default order entry to the database.
	 *
	 * @param definedDefaultOrderEntryId the primary key for the new defined default order entry
	 * @return the new defined default order entry
	 */
	public DefinedDefaultOrderEntry create(long definedDefaultOrderEntryId);

	/**
	 * Removes the defined default order entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry that was removed
	 * @throws NoSuchDefinedDefaultOrderEntryException if a defined default order entry with the primary key could not be found
	 */
	public DefinedDefaultOrderEntry remove(long definedDefaultOrderEntryId)
		throws NoSuchDefinedDefaultOrderEntryException;

	public DefinedDefaultOrderEntry updateImpl(
		DefinedDefaultOrderEntry definedDefaultOrderEntry);

	/**
	 * Returns the defined default order entry with the primary key or throws a <code>NoSuchDefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a defined default order entry with the primary key could not be found
	 */
	public DefinedDefaultOrderEntry findByPrimaryKey(
			long definedDefaultOrderEntryId)
		throws NoSuchDefinedDefaultOrderEntryException;

	/**
	 * Returns the defined default order entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry, or <code>null</code> if a defined default order entry with the primary key could not be found
	 */
	public DefinedDefaultOrderEntry fetchByPrimaryKey(
		long definedDefaultOrderEntryId);

	/**
	 * Returns all the defined default order entries.
	 *
	 * @return the defined default order entries
	 */
	public java.util.List<DefinedDefaultOrderEntry> findAll();

	/**
	 * Returns a range of all the defined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @return the range of defined default order entries
	 */
	public java.util.List<DefinedDefaultOrderEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the defined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of defined default order entries
	 */
	public java.util.List<DefinedDefaultOrderEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DefinedDefaultOrderEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the defined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of defined default order entries
	 */
	public java.util.List<DefinedDefaultOrderEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DefinedDefaultOrderEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the defined default order entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of defined default order entries.
	 *
	 * @return the number of defined default order entries
	 */
	public int countAll();

}
// LIFERAY-SERVICE-BUILDER-HASH:1899759483