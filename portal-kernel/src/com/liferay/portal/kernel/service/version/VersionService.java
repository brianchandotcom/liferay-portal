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

package com.liferay.portal.kernel.service.version;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.version.VersionModel;
import com.liferay.portal.kernel.model.version.VersionedModel;

import java.util.List;

/**
 * Provides a way to view versions, create new drafts, publish drafts, and roll
 * back versions of versioned models.
 *
 * @author Preston Crary
 * @see    VersionServiceListener
 */
public interface VersionService
	<E extends VersionedModel<V>, V extends VersionModel<E>> {

	/**
	 * Checks out the previous version of the versioned model and saves it as a
	 * draft. Any {@link VersionServiceListener} that has been registered is
	 * also notified. An {@link IllegalArgumentException} is thrown if the
	 * versioned model already has a draft.
	 *
	 * @param  publishedVersionedModel the published versioned model
	 * @param  version the version
	 * @return the draft {@link VersionModel} being checked out
	 * @throws PortalException if the version does not exist
	 */
	public E checkout(E publishedVersionedModel, int version)
		throws PortalException;

	/**
	 * Creates a new draft {@link VersionedModel}. The versioned model is not
	 * added to the database.
	 *
	 * @return the new draft versioned model
	 */
	public E create();

	/**
	 * Deletes the {@link VersionedModel}. Related drafts and versions are also
	 * deleted. Any {@link VersionServiceListener} that has been registered is
	 * notified. An {@link IllegalArgumentException} is thrown if the versioned
	 * model is a draft. Drafts can be deleted by calling {@link
	 * #deleteDraft(VersionedModel)}.
	 *
	 * @param  publishedVersionedModel the published versioned model
	 * @return the versioned model that is deleted
	 * @throws PortalException if a portal exception occurred
	 */
	public E delete(E publishedVersionedModel) throws PortalException;

	/**
	 * Deletes the {@link VersionedModel} if it is a draft. Any {@link
	 * VersionServiceListener} that has been registered is notified. An {@link
	 * IllegalArgumentException} is thrown if the versioned model is not a
	 * draft. Published versioned models can be deleted by calling {@link
	 * #delete(VersionedModel)}.
	 *
	 * @param  draftVersionedModel the draft versioned model
	 * @return the versioned model that is deleted
	 * @throws PortalException if a portal exception occurred
	 */
	public E deleteDraft(E draftVersionedModel) throws PortalException;

	/**
	 * Deletes the {@link VersionModel} if it is not the latest version. Any
	 * {@link VersionServiceListener} that has been registered is notified. An
	 * {@link IllegalArgumentException} is thrown if the versioned model is the
	 * latest version.
	 *
	 * @param  versionModel the version model
	 * @return the version model that is deleted
	 * @throws PortalException if a portal exception occurred
	 */
	public V deleteVersion(V versionModel) throws PortalException;

	/**
	 * Fetches the draft if it exists; otherwise, returns <code>null</code>.
	 *
	 * @param  versionedModel the draft or published versioned model
	 * @return the draft versioned model if it exists; otherwise
	 *         <code>null</code>
	 */
	public E fetchDraft(E versionedModel);

	/**
	 * Fetches the draft if it exists; otherwise, returns <code>null</code>.
	 *
	 * @param  primaryKey the draft or published versioned model's primary key
	 * @return the draft versioned model if it exists; otherwise
	 *         <code>null</code>
	 */
	public E fetchDraft(long primaryKey);

	/**
	 * Fetches the latest version model if it exists. If the versioned model has
	 * never been published, <code>null</code> is returned.
	 *
	 * @param  versionedModel the versioned model
	 * @return the latest version model if it exists; otherwise
	 *         <code>null</code>
	 */
	public V fetchLatestVersion(E versionedModel);

	/**
	 * Fetches the published versioned model if it exists. If the versioned
	 * model has never been published, <code>null</code> is returned.
	 *
	 * @param  versionedModel the versioned model
	 * @return the published versioned model if it exists; otherwise
	 *         <code>null</code>
	 */
	public E fetchPublished(E versionedModel);

	/**
	 * Fetches the published versioned model if it exists; otherwise, returns
	 * <code>null</code>.
	 *
	 * @param  primaryKey the published versioned model's primary key
	 * @return the published versioned model if it exists; otherwise
	 *         <code>null</code>
	 */
	public E fetchPublished(long primaryKey);

	/**
	 * Returns the versioned model draft. If the draft does not exist, it is
	 * lazily created, adding it to the database. If a draft is lazily created,
	 * any {@link VersionServiceListener} that has been registered is notified.
	 *
	 * @param  versionedModel the versioned model
	 * @return the versioned model
	 * @throws PortalException if a portal exception occurred
	 */
	public E getDraft(E versionedModel) throws PortalException;

	/**
	 * Returns the versioned model draft with the primary key. If it does not
	 * exist, a new draft is lazily created and added to the database. If a
	 * draft is lazily created, any {@link VersionServiceListener} that has been
	 * registered is notified.
	 *
	 * @param  primaryKey of the draft or published versioned model
	 * @return the versioned model
	 * @throws PortalException if no draft or published versioned model exists
	 *         with the primary key
	 */
	public E getDraft(long primaryKey) throws PortalException;

	/**
	 * Returns the {@link VersionModel} for the {@link VersionedModel} matching
	 * the version.
	 *
	 * @param  versionedModel the versioned model
	 * @param  version the version
	 * @return the version model
	 * @throws PortalException if the version does not exist
	 */
	public V getVersion(E versionedModel, int version) throws PortalException;

	/**
	 * Returns the {@link VersionModel}s for the {@link VersionedModel}. They
	 * are sorted in descending order.
	 *
	 * @param  versionedModel the versioned model
	 * @return the version models
	 */
	public List<V> getVersions(E versionedModel);

	/**
	 * Publishes the draft and creates a new version model for the new version.
	 * Any {@link VersionServiceListener} that has been registered is notified.
	 * An {@link IllegalArgumentException} is thrown if the versioned model is
	 * not a draft.
	 *
	 * @param  draftVersionedModel the draft version model
	 * @return the published versioned model
	 * @throws PortalException if a portal exception occurred
	 */
	public E publishDraft(E draftVersionedModel) throws PortalException;

	/**
	 * Registers the {@link VersionServiceListener}.
	 *
	 * @param versionServiceListener the version service listener to register
	 */
	public void registerListener(
		VersionServiceListener<E, V> versionServiceListener);

	/**
	 * Unregisters the {@link VersionServiceListener}.
	 *
	 * @param versionServiceListener the version service listener to unregister
	 */
	public void unregisterListener(
		VersionServiceListener<E, V> versionServiceListener);

	/**
	 * Updates the draft. Any {@link VersionServiceListener} that has been
	 * registered is notified. An {@link IllegalArgumentException} is thrown if
	 * the versioned model is not a draft.
	 *
	 * @param  draftVersionedModel the draft version model
	 * @return the draft versioned model
	 * @throws PortalException if a portal exception occurred
	 */
	public E updateDraft(E draftVersionedModel) throws PortalException;

}