/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchDSLQueryEntryException;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the dsl query entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DSLQueryEntryUtil
 * @generated
 */
@ProviderType
public interface DSLQueryEntryPersistence
	extends BasePersistence<DSLQueryEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DSLQueryEntryUtil} to access the dsl query entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the dsl query entry in the entity cache if it is enabled.
	 *
	 * @param dslQueryEntry the dsl query entry
	 */
	public void cacheResult(DSLQueryEntry dslQueryEntry);

	/**
	 * Caches the dsl query entries in the entity cache if it is enabled.
	 *
	 * @param dslQueryEntries the dsl query entries
	 */
	public void cacheResult(java.util.List<DSLQueryEntry> dslQueryEntries);

	/**
	 * Creates a new dsl query entry with the primary key. Does not add the dsl query entry to the database.
	 *
	 * @param dslQueryEntryId the primary key for the new dsl query entry
	 * @return the new dsl query entry
	 */
	public DSLQueryEntry create(long dslQueryEntryId);

	/**
	 * Removes the dsl query entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry that was removed
	 * @throws NoSuchDSLQueryEntryException if a dsl query entry with the primary key could not be found
	 */
	public DSLQueryEntry remove(long dslQueryEntryId)
		throws NoSuchDSLQueryEntryException;

	public DSLQueryEntry updateImpl(DSLQueryEntry dslQueryEntry);

	/**
	 * Returns the dsl query entry with the primary key or throws a <code>NoSuchDSLQueryEntryException</code> if it could not be found.
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry
	 * @throws NoSuchDSLQueryEntryException if a dsl query entry with the primary key could not be found
	 */
	public DSLQueryEntry findByPrimaryKey(long dslQueryEntryId)
		throws NoSuchDSLQueryEntryException;

	/**
	 * Returns the dsl query entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry, or <code>null</code> if a dsl query entry with the primary key could not be found
	 */
	public DSLQueryEntry fetchByPrimaryKey(long dslQueryEntryId);

	/**
	 * Returns all the dsl query entries.
	 *
	 * @return the dsl query entries
	 */
	public java.util.List<DSLQueryEntry> findAll();

	/**
	 * Returns a range of all the dsl query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query entries
	 * @param end the upper bound of the range of dsl query entries (not inclusive)
	 * @return the range of dsl query entries
	 */
	public java.util.List<DSLQueryEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the dsl query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query entries
	 * @param end the upper bound of the range of dsl query entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dsl query entries
	 */
	public java.util.List<DSLQueryEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DSLQueryEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the dsl query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query entries
	 * @param end the upper bound of the range of dsl query entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dsl query entries
	 */
	public java.util.List<DSLQueryEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DSLQueryEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the dsl query entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of dsl query entries.
	 *
	 * @return the number of dsl query entries
	 */
	public int countAll();

}