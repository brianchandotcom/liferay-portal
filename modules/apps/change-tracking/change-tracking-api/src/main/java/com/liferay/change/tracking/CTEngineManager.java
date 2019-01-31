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

package com.liferay.change.tracking;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;
import java.util.Optional;

/**
 * Provides the main internal manager interface to interact with the Change
 * Tracking framework.
 *
 * @author Daniel Kocsis
 * @review
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
	 * Creates a new change tracking collection.
	 *
	 * @param  userId the primary key of the user who initiated the action
	 * @param  name the name of the change tracking collection
	 * @param  description the description of the change tracking collection
	 * @return the newly created change tracking collection
	 */
	public Optional<CTCollection> createCTCollection(
		long userId, String name, String description);

	/**
	 * Deletes a change tracking collection.
	 *
	 * @param ctCollectionId the primary key of the change collection
	 */
	public void deleteCTCollection(long ctCollectionId);

	/**
	 * Disables the change tracking functionality in the scope of the given
	 * company. As a side effect it deletes all the related change tracking
	 * collections and entries.
	 *
	 * @param companyId the primary key of the company
	 */
	public void disableChangeTracking(long companyId);

	/**
	 * Enables the change tracking feature in the scope of the given company.
	 *
	 * @param userId the primary key of the user who initiated the action
	 */
	public void enableChangeTracking(long companyId, long userId);

	/**
	 * Returns the active change tracking collection associated with the given
	 * user in the scope of the given company.
	 *
	 * @param  userId the primary key of the user
	 * @return the selected change tracking collection
	 */
	public Optional<CTCollection> getActiveCTCollectionOptional(long userId);

	/**
	 * Returns the change tracking collection identified by the primary key.
	 *
	 * @param  ctCollectionId the primary key of the change collection
	 * @return the change tracking collection
	 */
	public Optional<CTCollection> getCTCollectionOptional(long ctCollectionId);

	/**
	 * Returns all the change tracking collection associated with the given
	 * company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the list of change tracking collections
	 */
	public List<CTCollection> getCTCollections(long companyId);

	/**
	 * Returns all the change tracking collection associated with the given
	 * company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  queryDefinition the object contains settings regarding
	 *         pagination, order and filter
	 * @return the list of change tracking collections
	 */
	public List<CTCollection> getCTCollections(
		long companyId, QueryDefinition<CTCollection> queryDefinition);

	/**
	 * Returns all the change entries associated with the given change
	 * collection.
	 *
	 * @param  ctCollectionId the primary key of the change collection
	 * @return the list of change entries
	 */
	public List<CTEntry> getCTEntries(long ctCollectionId);

	/**
	 * Returns the special change tracking collection which is called production
	 * and contains all the changes published before.
	 *
	 * @param  companyId the primary key of the company
	 * @return the production change tracking collection
	 */
	public Optional<CTCollection> getProductionCTCollectionOptional(
		long companyId);

	/**
	 * Returns <code>true</code> if the change tracking is enabled in the scope
	 * of the given company or <code>false</code> if not.
	 *
	 * @param  companyId the primary key of the company
	 * @return <code>true</code> if change tracking is enabled in the scope of
	 *         the given company; <code>false</code> otherwise.
	 */
	public boolean isChangeTrackingEnabled(long companyId);

	/**
	 * Returns <code>true</code> if the given base model supports change
	 * tracking or <code>false</code> if not.
	 *
	 * @param  companyId the primary key of the company
	 * @param  clazz the class object
	 * @return <code>true</code> if the given base model supports change
	 *         tracking; <code>false</code> otherwise.
	 */
	public boolean isChangeTrackingSupported(
		long companyId, Class<? extends BaseModel> clazz);

	/**
	 * Returns <code>true</code> if the given base model supports change
	 * tracking or <code>false</code> if not.
	 *
	 * @param  companyId the primary key of the company
	 * @param  classNameId the class name ID of the model class
	 * @return <code>true</code> if the given base model supports change
	 *         tracking; <code>false</code> otherwise.
	 */
	public boolean isChangeTrackingSupported(long companyId, long classNameId);

	/**
	 * Publishes all the change entries from the given change tracking
	 * collection to the production change tracking collection.
	 *
	 * @param userId the primary key of the user
	 * @param ctCollectionId the primary key of the change collection
	 */
	public void publishCTCollection(long userId, long ctCollectionId);

}