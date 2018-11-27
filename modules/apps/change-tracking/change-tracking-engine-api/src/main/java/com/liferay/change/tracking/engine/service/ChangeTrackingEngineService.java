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

package com.liferay.change.tracking.engine.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.model.CTECollection;
import com.liferay.change.tracking.engine.model.CTEEntry;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;

/**
 * Provides the main internal service interface to interact with the Change
 * Tracking framework.
 *
 * @author Daniel Kocsis
 */
@ProviderType
public interface ChangeTrackingEngineService {

	/**
	 * Changes the selected change collection for the given user.
	 *
	 * @param userId the primary key of the user
	 * @param collectionId the primary key of the selected change collection
	 */
	public void checkoutCollection(long userId, long collectionId);

	/**
	 * Creates a new change collection.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user who initiated the action
	 * @param  name the name of the change collection
	 * @param  description the description of the change collection
	 * @return the newly created change collection
	 */
	public CTECollection createCollection(
		long companyId, long userId, String name, String description);

	/**
	 * Deletes a change collection.
	 *
	 * @param userId the primary key of the user who initiated the action
	 * @param collectionId the primary key of the change collection
	 */
	public void deleteCollection(long userId, long collectionId);

	/**
	 * Disables the change tracking functionality in the scope of the given
	 * company. As a side effect it deletes all the related change collections
	 * and entries.
	 *
	 * @param companyId the primary key of the company
	 * @param userId the primary key of the user who initiated the action
	 */
	public void disableChangeTracking(long companyId, long userId);

	/**
	 * Enables the change tracking feature in the scope of the given company.
	 *
	 * @param companyId the primary key of the company
	 * @param userId the primary key of the user who initiated the action
	 */
	public void enableChangeTracking(long companyId, long userId);

	/**
	 * Returns the active change collection associated with the given user in
	 * the scope of the given company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @return the selected change collection
	 */
	public CTECollection getActiveCollection(long companyId, long userId);

	/**
	 * Returns the change collection identified by the primary key.
	 *
	 * @param  collectionId the primary key of the change collection
	 * @return the change collection
	 */
	public CTECollection getCollection(long collectionId);

	/**
	 * Returns all the change collection associated with the given company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the list of change collections
	 */
	public List<CTECollection> getCollections(long companyId);

	/**
	 * Returns all the change entries associated with the given change
	 * collection.
	 *
	 * @param  collectionId the primary key of the change collection
	 * @return the list of change entries
	 */
	public List<CTEEntry> getEntries(long collectionId);

	/**
	 * Returns the special change collection which is called production and
	 * contains all the changes published before.
	 *
	 * @param  companyId the primary key of the company
	 * @return the production change collection
	 */
	public CTECollection getProductionCollection(long companyId);

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
	 * @param  clazz the class object
	 * @return <code>true</code> if the given base model supports change
	 *         tracking; <code>false</code> otherwise.
	 */
	public boolean isChangeTrackingSupported(Class<BaseModel> clazz);

	/**
	 * Publishes all the change entries from the given change collection to the
	 * production change collection.
	 *
	 * @param companyId the primary key of the company
	 * @param userId the primary key of the user
	 * @param collectionId the primary key of the change collection
	 */
	public void publishCollection(
		long companyId, long userId, long collectionId);

}