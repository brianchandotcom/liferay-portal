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

package com.liferay.revert.schema.version.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.revert.schema.version.exception.NoSuchSchemaEntryException;
import com.liferay.revert.schema.version.model.SchemaEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the schema entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SchemaEntryUtil
 * @generated
 */
@ProviderType
public interface SchemaEntryPersistence extends BasePersistence<SchemaEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SchemaEntryUtil} to access the schema entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the schema entry in the entity cache if it is enabled.
	 *
	 * @param schemaEntry the schema entry
	 */
	public void cacheResult(SchemaEntry schemaEntry);

	/**
	 * Caches the schema entries in the entity cache if it is enabled.
	 *
	 * @param schemaEntries the schema entries
	 */
	public void cacheResult(java.util.List<SchemaEntry> schemaEntries);

	/**
	 * Creates a new schema entry with the primary key. Does not add the schema entry to the database.
	 *
	 * @param entryId the primary key for the new schema entry
	 * @return the new schema entry
	 */
	public SchemaEntry create(long entryId);

	/**
	 * Removes the schema entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the schema entry
	 * @return the schema entry that was removed
	 * @throws NoSuchSchemaEntryException if a schema entry with the primary key could not be found
	 */
	public SchemaEntry remove(long entryId) throws NoSuchSchemaEntryException;

	public SchemaEntry updateImpl(SchemaEntry schemaEntry);

	/**
	 * Returns the schema entry with the primary key or throws a <code>NoSuchSchemaEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the schema entry
	 * @return the schema entry
	 * @throws NoSuchSchemaEntryException if a schema entry with the primary key could not be found
	 */
	public SchemaEntry findByPrimaryKey(long entryId)
		throws NoSuchSchemaEntryException;

	/**
	 * Returns the schema entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the schema entry
	 * @return the schema entry, or <code>null</code> if a schema entry with the primary key could not be found
	 */
	public SchemaEntry fetchByPrimaryKey(long entryId);

	/**
	 * Returns all the schema entries.
	 *
	 * @return the schema entries
	 */
	public java.util.List<SchemaEntry> findAll();

	/**
	 * Returns a range of all the schema entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchemaEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schema entries
	 * @param end the upper bound of the range of schema entries (not inclusive)
	 * @return the range of schema entries
	 */
	public java.util.List<SchemaEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the schema entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchemaEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schema entries
	 * @param end the upper bound of the range of schema entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of schema entries
	 */
	public java.util.List<SchemaEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchemaEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the schema entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchemaEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schema entries
	 * @param end the upper bound of the range of schema entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of schema entries
	 */
	public java.util.List<SchemaEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SchemaEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the schema entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of schema entries.
	 *
	 * @return the number of schema entries
	 */
	public int countAll();

}