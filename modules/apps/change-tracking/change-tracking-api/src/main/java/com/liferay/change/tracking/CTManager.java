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

import com.liferay.change.tracking.exception.CTException;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryBag;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Optional;

/**
 * Provides functionality to register and retrieve model changes from the change
 * tracking framework.
 *
 * @author Daniel Kocsis
 * @review
 */
@ProviderType
public interface CTManager {

	/**
	 * Puts a model change to a change bag associated with the owner model
	 * change. If there is no change bag associated with the owner it creates a
	 * new one.
	 *
	 * @param  userId the primary key of the user
	 * @param  ownerCTEntry the owner of the change bag
	 * @param  relatedCTEntry the change to add to the bag
	 * @return the created or updated change bag
	 */
	public Optional<CTEntryBag> addRelatedCTEntry(
		long userId, CTEntry ownerCTEntry, CTEntry relatedCTEntry);

	/**
	 * Executes a model addition or update using the given supplier, with
	 * setting and un-setting the flag that indicates the update before and
	 * after the operation. Therefore during the execution {@link
	 * #isModelUpdateInProgress()} will return <code>true</code>.
	 *
	 * @param  modelUpdateSupplier The supplier that performs the add or update
	 *         and supplies the resulting model
	 * @return The created or updated model of type T
	 */
	public <T> T executeModelUpdate(
			UnsafeSupplier<T, PortalException> modelUpdateSupplier)
		throws PortalException;

	/**
	 * Retrieves a model change in the context of the current user's active
	 * change collection.
	 *
	 * @param  userId the primary key of the user
	 * @param  classNameId the primary key of the changed version model's class
	 * @param  classPK the primary key of the changed version model
	 * @return the change tracking entry representing the model change
	 */
	public Optional<CTEntry> getActiveCTCollectionCTEntryOptional(
		long userId, long classNameId, long classPK);

	/**
	 * Retrieves the latest model change in the context of the current user's
	 * active change collection.
	 *
	 * @param  userId the primary key of the user
	 * @param  resourcePrimKey the primary key of the changed resource model
	 * @return the change tracking entry representing the model change
	 */
	public Optional<CTEntry> getLatestModelChangeCTEntryOptional(
		long userId, long resourcePrimKey);

	/**
	 * Retrieves all model changes in the context of the current user's active
	 * change collection.
	 *
	 * @param  userId the primary key of the user
	 * @param  resourcePrimKey the primary key of the changed resource model
	 * @return a list of change tracking entries representing all the registered
	 *         model changes
	 */
	public List<CTEntry> getModelChangeCTEntries(
		long userId, long resourcePrimKey);

	/**
	 * Retrieves a paginated and ordered list of all model changes in the
	 * context of the current user's active change collection.
	 *
	 * @param  userId the primary key of the user
	 * @param  resourcePrimKey the primary key of the changed resource model
	 * @param  queryDefinition the object contains settings regarding
	 *         pagination, order and filter
	 * @return a list of change tracking entries representing the registered
	 *         model changes
	 */
	public List<CTEntry> getModelChangeCTEntries(
		long userId, long resourcePrimKey,
		QueryDefinition<CTEntry> queryDefinition);

	/**
	 * Retrieves a model change's bag, first looking for it in the current
	 * user's active change collection, and if it doesn't exist, looking for it
	 * in the production change collection
	 *
	 * @param  userId the primary key of the user
	 * @param  classNameId the primary key of the changed version model's class
	 * @param  classPK the primary key of the changed version model
	 * @return the change tracking entry representing the model change
	 */
	public Optional<CTEntryBag> getModelChangeCTEntryBagOptional(
		long userId, long classNameId, long classPK);

	/**
	 * Retrieves a model change, first looking for it in the current user's
	 * active change collection, and if it doesn't exist, looking for it in the
	 * production change collection
	 *
	 * @param  userId the primary key of the user
	 * @param  classNameId the primary key of the changed version model's class
	 * @param  classPK the primary key of the changed version model
	 * @return the change tracking entry representing the model change
	 */
	public Optional<CTEntry> getModelChangeCTEntryOptional(
		long userId, long classNameId, long classPK);

	/**
	 * Retrieves a model change from the production change collection.
	 *
	 * @param  userId the primary key of the user
	 * @param  classNameId the primary key of the changed version model's class
	 * @param  classPK the primary key of the changed version model
	 * @return the change tracking entry representing the model change
	 */
	public Optional<CTEntry> getProductionCTCollectionCTEntryOptional(
		long userId, long classNameId, long classPK);

	/**
	 * Indicates whether an add or update is in progress for a model. This will
	 * only return <code>true</code> if the add or update is being executed with
	 * {@link #executeModelUpdate(UnsafeSupplier)} and the execution is in
	 * progress. Useful to be able to bypass change tracking consideration when
	 * a get or fetch is executed for a model during it's own addition or
	 * update.
	 *
	 * @return <code>true</code> if an add or update is in progress for a model
	 *         using {@link #executeModelUpdate(UnsafeSupplier)}
	 */
	public boolean isModelUpdateInProgress();

	/**
	 * Registers a model change into the change tracking framework in the
	 * context of the current user's active change collection. Throws
	 * <code>DuplicateCTEntryException</code> if a change tracking entry already
	 * exists with the same <code>classNameId</code> and <code> classPK</code>.
	 *
	 * @param  userId the primary key of the user
	 * @param  classNameId the primary key of the changed version model's class
	 * @param  classPK the primary key of the changed version model
	 * @param  resourcePrimKey the primary key of the changed resource model
	 * @param  changeType the type of the model change
	 * @return the change tracking entry representing the registered model
	 *         change
	 */
	public Optional<CTEntry> registerModelChange(
			long userId, long classNameId, long classPK, long resourcePrimKey,
			int changeType)
		throws CTException;

	/**
	 * Registers a model change into the change tracking framework in the
	 * context of the current user's active change collection. Throws
	 * <code>DuplicateCTEntryException</code> if a change tracking entry already
	 * exists with the same <code>classNameId</code> and <code> classPK</code>,
	 * except when the <code>force</code> attribute is <code>true</code>.
	 *
	 * @param  userId the primary key of the user
	 * @param  classNameId the primary key of the changed version model's class
	 * @param  classPK the primary key of the changed version model
	 * @param  resourcePrimKey the primary key of the changed resource model
	 * @param  changeType the type of the model change
	 * @param  force forces to override an existing change entry
	 * @return the change tracking entry representing the registered model
	 *         change
	 */
	public Optional<CTEntry> registerModelChange(
			long userId, long classNameId, long classPK, long resourcePrimKey,
			int changeType, boolean force)
		throws CTException;

	/**
	 * Unregisters a model change from the change tracking framework.
	 *
	 * @param  userId the primary key of the user
	 * @param  classNameId the primary key of the changed version model's class
	 * @param  classPK the primary key of the changed version model
	 * @return the change tracking entry that was deleted
	 */
	public Optional<CTEntry> unregisterModelChange(
		long userId, long classNameId, long classPK);

}