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

package com.liferay.change.tracking.engine;

import com.liferay.change.tracking.engine.exception.CTEngineException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the main internal manager interface to interact with the Change
 * Tracking framework.
 *
 * @author Daniel Kocsis
 */
@ProviderType
public interface CTEngineManager {

	/**
	 * Changes the selected change tracking collection for the given user.
	 *
	 * @param userId the primary key of the user
	 * @param ctCollectionId the primary key of the selected change collection
	 */
	public void checkoutCTCollection(long userId, long ctCollectionId);

	/**
	 * Returns the number of change tracking collections with the given company
	 * and keywords.
	 *
	 * @param  companyId the primary key of the company
	 * @param  queryDefinition the settings regarding pagination, order, and
	 *         filter (keywords)
	 * @return the number of change tracking collections
	 */
	public long countByKeywords(
		long companyId, QueryDefinition<CTCollection> queryDefinition);

	/**
	 * Creates a new change tracking collection.
	 *
	 * @param  userId the primary key of the user who initiated the action
	 * @param  name the change tracking collection's name
	 * @param  description the change tracking collection's description
	 * @return the new change tracking collection
	 */
	public Optional<CTCollection> createCTCollection(
			long userId, String name, String description)
		throws CTEngineException;

	/**
	 * Deletes the change tracking collection.
	 *
	 * @param ctCollectionId the primary key of the change collection
	 */
	public void deleteCTCollection(long ctCollectionId);

	/**
	 * Disables the change tracking functionality for the given company. As a
	 * side effect, it deletes all the related change tracking collections and
	 * entries.
	 *
	 * @param companyId the primary key of the company
	 */
	public void disableChangeTracking(long companyId);

	/**
	 * Enables the change tracking feature for the given company.
	 *
	 * @param userId the primary key of the user who initiated the action
	 */
	public void enableChangeTracking(long companyId, long userId);

	/**
	 * Returns the number of changes per change type within a given change
	 * tracking collection.
	 *
	 * @param  ctCollectionId the primary key of the change collection
	 * @return the change counts per change type
	 */
	public Map<Integer, Long> getCTCollectionChangeTypeCounts(
		long ctCollectionId);

	/**
	 * Returns the change tracking collection with the primary key.
	 *
	 * @param  ctCollectionId the primary key of the change collection
	 * @return the change tracking collection
	 */
	public Optional<CTCollection> getCTCollectionOptional(long ctCollectionId);

	/**
	 * Returns all the change tracking collections associated with the given
	 * company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the change tracking collections
	 */
	public List<CTCollection> getCTCollections(long companyId);

	/**
	 * Returns the change entries associated with the given change collection.
	 *
	 * @param  ctCollection the primary key of the change collection
	 * @param  groupIds the group primary keys
	 * @param  userIds the user primary keys
	 * @param  classNameIds the class name primary keys
	 * @param  changeTypes the change types
	 * @param  collision whether the change entries collide with the production
	 *         change collection
	 * @param  queryDefinition the settings regarding pagination, order, and
	 *         status filtering
	 * @return the change entries associated with the given change collection
	 */
	public List<CTEntry> getCTEntries(
		CTCollection ctCollection, long[] groupIds, long[] userIds,
		long[] classNameIds, int[] changeTypes, Boolean collision,
		QueryDefinition<CTEntry> queryDefinition);

	/**
	 * Returns all the change entries associated with the given change
	 * collection.
	 *
	 * @param  ctCollectionId the primary key of the change collection
	 * @return the change entries
	 */
	public List<CTEntry> getCTEntries(long ctCollectionId);

	/**
	 * Returns all the change entries associated with the given change
	 * collection and query definition.
	 *
	 * @param  ctCollectionId the primary key of the change collection
	 * @param  queryDefinition the settings regarding pagination, order, and
	 *         status filtering
	 * @return the change entries
	 */
	public List<CTEntry> getCTEntries(
		long ctCollectionId, QueryDefinition<CTEntry> queryDefinition);

	/**
	 * Returns the number of change entries associated with the given change
	 * collection and filters.
	 *
	 * @param  ctCollection the primary key of the change collection
	 * @param  groupIds the group primary keys
	 * @param  userIds the user primary keys
	 * @param  classNameIds the class name primary keys
	 * @param  changeTypes the change types
	 * @param  collision whether the change entries collide with the production
	 *         change collection
	 * @param  queryDefinition the settings regarding the status filtering
	 * @return the number of change tracking entries with the given change
	 *         collection and filters
	 */
	public int getCTEntriesCount(
		CTCollection ctCollection, long[] groupIds, long[] userIds,
		long[] classNameIds, int[] changeTypes, Boolean collision,
		QueryDefinition<CTEntry> queryDefinition);

	/**
	 * Returns the number of the change entries associated with the given change
	 * collection.
	 *
	 * @param  ctCollectionId the primary key of the change collection
	 * @param  queryDefinition the settings regarding pagination, order and
	 *         status filtering
	 * @return the number of change entries
	 */
	public int getCTEntriesCount(
		long ctCollectionId, QueryDefinition<CTEntry> queryDefinition);

	/**
	 * Returns all the change entry aggregates associated with the given change
	 * collection.
	 *
	 * @param  ctCollectionId the primary key of the change collection
	 * @return the change entry aggregates
	 */
	public List<CTEntryAggregate> getCTEntryAggregates(long ctCollectionId);

	/**
	 * Returns the change tracking processes.
	 *
	 * @param  companyId the company ID of the desired processes
	 * @param  userId the user ID of the user to filter the processes. If it's
	 *         not a valid user it's omitted from the filter
	 * @param  keywords the keywords to filter processes. If empty or
	 *         <code>null</code> it's omitted from the filter
	 * @param  queryDefinition the settings regarding pagination, order and
	 *         status filtering
	 * @return the change tracking processes filtered based on the parameters
	 */
	public List<CTProcess> getCTProcesses(
		long companyId, long userId, String[] keywords,
		QueryDefinition<?> queryDefinition);

	/**
	 * Returns the production change tracking collection that contains all the
	 * changes published before.
	 *
	 * @param  companyId the primary key of the company
	 * @return the production change tracking collection
	 */
	public Optional<CTCollection> getProductionCTCollectionOptional(
		long companyId);

	/**
	 * Returns the recent change tracking collection ID for the user.
	 *
	 * @param  userId the user's ID
	 * @return the recent change tracking collection ID
	 */
	public long getRecentCTCollectionId(long userId);

	/**
	 * Returns <code>true</code> if change tracking is allowed for the given
	 * company.
	 *
	 * @param  companyId the primary key of the company
	 * @return <code>true</code> if change tracking is allowed for the company;
	 *         <code>false</code> otherwise
	 */
	public boolean isChangeTrackingAllowed(long companyId);

	/**
	 * Returns <code>true</code> if change tracking is enabled for the company.
	 *
	 * @param  companyId the primary key of the company
	 * @return <code>true</code> if change tracking is enabled for the company;
	 *         <code>false</code> otherwise
	 */
	public boolean isChangeTrackingEnabled(long companyId);

	/**
	 * Returns <code>true</code> if the given base model supports change
	 * tracking.
	 *
	 * @param  companyId the primary key of the company
	 * @param  clazz the class object
	 * @return <code>true</code> if the given base model supports change
	 *         tracking; <code>false</code> otherwise
	 */
	public boolean isChangeTrackingSupported(
		long companyId, Class<? extends BaseModel> clazz);

	/**
	 * Returns <code>true</code> if the given base model supports change
	 * tracking.
	 *
	 * @param  companyId the primary key of the company
	 * @param  modelClassNameId the class name ID of the model class
	 * @return <code>true</code> if the given base model supports change
	 *         tracking; <code>false</code> otherwise
	 */
	public boolean isChangeTrackingSupported(
		long companyId, long modelClassNameId);

	/**
	 * Publishes all the change entries from the given change tracking
	 * collection to the production change tracking collection.
	 *
	 * @param userId the primary key of the user
	 * @param ctCollectionId the primary key of the change collection
	 * @param ignoreCollision whether to publish the change tracking collection
	 *        if a collision is detected
	 */
	public void publishCTCollection(
		long userId, long ctCollectionId, boolean ignoreCollision);

	/**
	 * Returns all the change tracking collections associated with the given
	 * company and keywords.
	 *
	 * @param  companyId the primary key of the company
	 * @param  queryDefinition the settings regarding pagination, order, and
	 *         filter (keywords)
	 * @return the change tracking collections
	 */
	public List<CTCollection> searchByKeywords(
		long companyId, QueryDefinition<CTCollection> queryDefinition);

}