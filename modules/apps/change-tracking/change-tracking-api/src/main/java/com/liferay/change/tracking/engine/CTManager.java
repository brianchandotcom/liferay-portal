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
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Optional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides functionality to register and retrieve model changes from the change
 * tracking framework.
 *
 * @author Daniel Kocsis
 */
@ProviderType
public interface CTManager {

	/**
	 * Executes a model addition or update using the given supplier, toggling
	 * the flag that indicates the update before and after the operation.
	 * Therefore, during the execution, {@link #isModelUpdateInProgress()} will
	 * return <code>true</code>.
	 *
	 * @param  modelUpdateSupplier the supplier that performs the add or update
	 *         and supplies the resulting model
	 * @return the created or updated model
	 */
	public <T> T executeModelUpdate(
			UnsafeSupplier<T, PortalException> modelUpdateSupplier)
		throws PortalException;

	/**
	 * Returns the model change of the current user's active change collection.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @param  modelClassNameId the primary key of the changed version model's
	 *         class
	 * @param  modelClassPK the primary key of the changed version model
	 * @return the change tracking entry representing the model change
	 */
	public Optional<CTEntry> getActiveCTCollectionCTEntryOptional(
		long companyId, long userId, long modelClassNameId, long modelClassPK);

	/**
	 * Returns the user's active change collection in the given company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @return the user's active change collection
	 */
	public Optional<CTCollection> getActiveCTCollectionOptional(
		long companyId, long userId);

	/**
	 * Returns the change entries associated with the given change collection
	 * and class name ID.
	 *
	 * @param  companyId the primary key of the company
	 * @param  ctCollectionId the primary key of the selected change collection
	 * @param  classNameId the class name's primary key
	 * @return the change entries
	 */
	public List<CTEntry> getCTCollectionCTEntries(
		long companyId, long ctCollectionId, long classNameId);

	/**
	 * Returns change tracking collections associated with the given company,
	 * optionally including production or active change lists.
	 *
	 * @param  companyId the primary key of the company
	 * @param  includeProduction whether to return the production change list
	 * @param  includeActive whether to return the active change lists
	 * @param  queryDefinition the settings regarding pagination, order and
	 *         filter
	 * @return the change tracking collections
	 */
	public List<CTCollection> getCTCollections(
		long companyId, long userId, boolean includeProduction,
		boolean includeActive, QueryDefinition<CTCollection> queryDefinition);

	/**
	 * Returns the latest model change for the current user's active change
	 * collection.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @param  modelResourcePrimKey the primary key of the changed resource
	 *         model
	 * @return the change tracking entry representing the model change
	 */
	public Optional<CTEntry> getLatestModelChangeCTEntryOptional(
		long companyId, long userId, long modelResourcePrimKey);

	/**
	 * Returns all model changes for a given resource model for the current
	 * user's active change collection.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @param  modelResourcePrimKey the primary key of the changed resource
	 *         model
	 * @return the change tracking entries representing all the registered model
	 *         changes
	 */
	public List<CTEntry> getModelChangeCTEntries(
		long companyId, long userId, long modelResourcePrimKey);

	/**
	 * Returns all model changes for a given resource model for the current
	 * user's active change collection.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @param  modelResourcePrimKey the primary key of the changed resource
	 *         model
	 * @param  queryDefinition the settings regarding pagination, order, and
	 *         filter
	 * @return the change tracking entries representing the registered model
	 *         changes
	 */
	public List<CTEntry> getModelChangeCTEntries(
		long companyId, long userId, long modelResourcePrimKey,
		QueryDefinition<CTEntry> queryDefinition);

	/**
	 * Returns a model change, first searching for it in the current user's
	 * active change collection; if it doesn't exist there, the production
	 * change collection is searched.
	 *
	 * @param  companyId the primary key of the company
	 * @param  userId the primary key of the user
	 * @param  modelClassNameId the primary key of the changed version model's
	 *         class
	 * @param  modelClassPK the primary key of the changed version model
	 * @return the change tracking entry representing the model change
	 */
	public Optional<CTEntry> getModelChangeCTEntryOptional(
		long companyId, long userId, long modelClassNameId, long modelClassPK);

	/**
	 * Returns a model change from the production change collection.
	 *
	 * @param  companyId the primary key of the company
	 * @param  modelClassNameId the primary key of the changed version model's
	 *         class
	 * @param  modelClassPK the primary key of the changed version model
	 * @return the change tracking entry representing the model change
	 */
	public Optional<CTEntry> getProductionCTCollectionCTEntryOptional(
		long companyId, long modelClassNameId, long modelClassPK);

	/**
	 * Returns <code>true</code> if a model addition or update is in progress.
	 * This only returns <code>true</code> if the addition or update is being
	 * executed with {@link #executeModelUpdate(UnsafeSupplier)} and the
	 * execution is in progress. It's useful to bypass change tracking
	 * consideration when a get or fetch is executed for a model during its own
	 * addition or update.
	 *
	 * @return <code>true</code> if a model addition or update is in progress;
	 *         <code>false</code> otherwise
	 */
	public boolean isModelUpdateInProgress();

	/**
	 * Returns <code>true</code> if there is no active change collection and the
	 * changes are made directly on production.
	 *
	 * @param  companyId the company ID
	 * @param  userId the primary key of the user
	 * @return <code>true</code> if there is no active change collection and the
	 *         changes are made directly on production; <code>false</code>
	 *         otherwise
	 */
	public boolean isProductionCheckedOut(long companyId, long userId);

	/**
	 * Returns <code>true</code> if the version entity specified by the given
	 * class name ID and class primay key is retrievable considering the current
	 * change tracking environment.
	 *
	 * @param  companyId the company ID
	 * @param  userId the primary key of the user
	 * @param  modelClassNameId the primary key of the version model's class
	 * @param  modelClassPK the primary key of the version model
	 * @return <code>true</code> if the version entity is retrievable
	 *         considering the current change tracking environment
	 */
	public boolean isRetrievableVersion(
		long companyId, long userId, long modelClassNameId, long modelClassPK);

	/**
	 * Registers the model change into the change tracking framework for the
	 * current user's active change collection. A
	 * <code>DuplicateCTEntryException</code> is thrown if the change tracking
	 * entry already exists with the same model class name ID and model class
	 * primary key.
	 *
	 * @param  companyId the company ID
	 * @param  userId the primary key of the user
	 * @param  modelClassNameId the primary key of the changed version model's
	 *         class
	 * @param  modelClassPK the primary key of the changed version model
	 * @param  modelResourcePrimKey the primary key of the changed resource
	 *         model
	 * @param  changeType the model change's type
	 * @return the change tracking entry representing the registered model
	 *         change
	 */
	public Optional<CTEntry> registerModelChange(
			long companyId, long userId, long modelClassNameId,
			long modelClassPK, long modelResourcePrimKey, int changeType)
		throws CTEngineException;

	/**
	 * Registers a model change into the change tracking framework for the
	 * current user's active change collection. A
	 * <code>DuplicateCTEntryException</code> is thrown if a change tracking
	 * entry already exists with the same model class name ID and model class
	 * primary key, unless you force the override of the existing change entry.
	 *
	 * @param  companyId the company ID
	 * @param  userId the primary key of the user
	 * @param  modelClassNameId the primary key of the changed version model's
	 *         class
	 * @param  modelClassPK the primary key of the changed version model
	 * @param  modelResourcePrimKey the primary key of the changed resource
	 *         model
	 * @param  changeType the model change's type
	 * @param  force whether to override an existing change entry
	 * @return the change tracking entry representing the registered model
	 *         change
	 */
	public Optional<CTEntry> registerModelChange(
			long companyId, long userId, long modelClassNameId,
			long modelClassPK, long modelResourcePrimKey, int changeType,
			boolean force)
		throws CTEngineException;

	/**
	 * Unregisters a model change from the change tracking framework.
	 *
	 * @param  companyId the company ID
	 * @param  userId the primary key of the user
	 * @param  modelClassNameId the primary key of the changed version model's
	 *         class
	 * @param  modelClassPK the primary key of the changed version model
	 * @return the change tracking entry that was deleted
	 */
	public Optional<CTEntry> unregisterModelChange(
		long companyId, long userId, long modelClassNameId, long modelClassPK);

}