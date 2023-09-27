/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchDSLQueryStatusEntryException;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the dsl query status entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DSLQueryStatusEntryUtil
 * @generated
 */
@ProviderType
public interface DSLQueryStatusEntryPersistence
	extends BasePersistence<DSLQueryStatusEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DSLQueryStatusEntryUtil} to access the dsl query status entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the dsl query status entry in the entity cache if it is enabled.
	 *
	 * @param dslQueryStatusEntry the dsl query status entry
	 */
	public void cacheResult(DSLQueryStatusEntry dslQueryStatusEntry);

	/**
	 * Caches the dsl query status entries in the entity cache if it is enabled.
	 *
	 * @param dslQueryStatusEntries the dsl query status entries
	 */
	public void cacheResult(
		java.util.List<DSLQueryStatusEntry> dslQueryStatusEntries);

	/**
	 * Creates a new dsl query status entry with the primary key. Does not add the dsl query status entry to the database.
	 *
	 * @param dslQueryStatusEntryId the primary key for the new dsl query status entry
	 * @return the new dsl query status entry
	 */
	public DSLQueryStatusEntry create(long dslQueryStatusEntryId);

	/**
	 * Removes the dsl query status entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dslQueryStatusEntryId the primary key of the dsl query status entry
	 * @return the dsl query status entry that was removed
	 * @throws NoSuchDSLQueryStatusEntryException if a dsl query status entry with the primary key could not be found
	 */
	public DSLQueryStatusEntry remove(long dslQueryStatusEntryId)
		throws NoSuchDSLQueryStatusEntryException;

	public DSLQueryStatusEntry updateImpl(
		DSLQueryStatusEntry dslQueryStatusEntry);

	/**
	 * Returns the dsl query status entry with the primary key or throws a <code>NoSuchDSLQueryStatusEntryException</code> if it could not be found.
	 *
	 * @param dslQueryStatusEntryId the primary key of the dsl query status entry
	 * @return the dsl query status entry
	 * @throws NoSuchDSLQueryStatusEntryException if a dsl query status entry with the primary key could not be found
	 */
	public DSLQueryStatusEntry findByPrimaryKey(long dslQueryStatusEntryId)
		throws NoSuchDSLQueryStatusEntryException;

	/**
	 * Returns the dsl query status entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dslQueryStatusEntryId the primary key of the dsl query status entry
	 * @return the dsl query status entry, or <code>null</code> if a dsl query status entry with the primary key could not be found
	 */
	public DSLQueryStatusEntry fetchByPrimaryKey(long dslQueryStatusEntryId);

	/**
	 * Returns all the dsl query status entries.
	 *
	 * @return the dsl query status entries
	 */
	public java.util.List<DSLQueryStatusEntry> findAll();

	/**
	 * Returns a range of all the dsl query status entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryStatusEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query status entries
	 * @param end the upper bound of the range of dsl query status entries (not inclusive)
	 * @return the range of dsl query status entries
	 */
	public java.util.List<DSLQueryStatusEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the dsl query status entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryStatusEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query status entries
	 * @param end the upper bound of the range of dsl query status entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dsl query status entries
	 */
	public java.util.List<DSLQueryStatusEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DSLQueryStatusEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the dsl query status entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryStatusEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query status entries
	 * @param end the upper bound of the range of dsl query status entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dsl query status entries
	 */
	public java.util.List<DSLQueryStatusEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DSLQueryStatusEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the dsl query status entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of dsl query status entries.
	 *
	 * @return the number of dsl query status entries
	 */
	public int countAll();

}