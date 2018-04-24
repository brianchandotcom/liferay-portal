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
 * @author Preston Crary
 * @see VersionServiceListener
 */
public interface VersionService
	<E extends VersionedModel<V>, V extends VersionModel<E>> {

	/**
	 * Checks out the previous version for the versionedModel and saves it as a
	 * draft. Notifies any {@link VersionServiceListener} that have been
	 * registered.
	 *
	 * @param publishedVersionedModel the published versioned model
	 * @param version the version
	 * @return the draft {@link VersionModel} being checkout out
	 * @throws IllegalArgumentException if the versioned model already has a
	 * draft
	 * @throws PortalException if the version does not exist
	 */
	public E checkout(E publishedVersionedModel, int version)
		throws PortalException;

	/**
	 * Creates a new draft {@link VersionedModel}. Does not add the versioned
	 * model to the database.
	 *
	 * @return the new draft versioned model
	 */
	public E create();

	/**
	 * Deletes the {@link VersionedModel}. Related draft if it exists and
	 * versions are also deleted. Notifies any {@link VersionServiceListener}
	 * that have been registered.
	 *
	 * @param publishedVersionedModel the published versioned model
	 * @throws IllegalArgumentException if the versioned model is a draft,
	 *         delete drafts by calling {@link #deleteDraft(VersionedModel)}
	 * @throws PortalException if a portal exception occurred
	 * @return the versioned model that was deleted
	 */
	public E delete(E publishedVersionedModel) throws PortalException;

	/**
	 * Deletes the {@link VersionedModel} if it is a draft. Notifies any
	 * {@link VersionServiceListener} that have been registered.
	 *
	 * @param draftVersionedModel the draft versioned model
	 * @throws IllegalArgumentException if the versioned model is not a draft,
	 *         delete published versioned models by calling {@link #delete(
	 *         VersionedModel)}
	 * @throws PortalException if a portal exception occurred
	 * @return the versioned model that was deleted
	 */
	public E deleteDraft(E draftVersionedModel) throws PortalException;

	/**
	 * Deletes the {@link VersionModel} if it is not the latest version.
	 * Notifies any {@link VersionServiceListener} that have been registered.
	 *
	 * @param versionModel the version model
	 * @throws IllegalArgumentException if the version model is the latest
	 *         version
	 * @throws PortalException if a portal exception occurred
	 * @return the version model that was deleted
	 */
	public V deleteVersion(V versionModel) throws PortalException;

	/**
	 * Fetches the draft if it exists, otherwise returns <code>null</code>.
	 *
	 * @param versionedModel the draft or published versioned model
	 * @return <code>null</code> or the draft versioned model if it exists
	 */
	public E fetchDraft(E versionedModel);

	/**
	 * Fetches the draft if it exists, otherwise returns <code>null</code>.
	 *
	 * @param primaryKey of the draft or published versioned model
	 * @return <code>null</code> or the draft versioned model if it exists
	 */
	public E fetchDraft(long primaryKey);

	/**
	 * Fetches the latest version model if it exists, otherwise returns
	 * <code>null</code> if the versioned model has never been published.
	 *
	 * @param versionedModel the versioned model
	 * @return <code>null</code> or the latest version model if it exists
	 */
	public V fetchLatestVersion(E versionedModel);

	/**
	 * Fetches the published versioned model if it exists, otherwise returns
	 * <code>null</code> if the versioned model has never been published.
	 *
	 * @param versionedModel the versioned model
	 * @return <code>null</code> or the published versioned model if it exists
	 */
	public E fetchPublished(E versionedModel);

	/**
	 * Fetches the published versioned model if it exists, otherwise returns
	 * <code>null</code>.
	 *
	 * @param versionedModel the versioned model
	 * @return <code>null</code> or the published versioned model if it exists
	 */
	public E fetchPublished(long primaryKey);

	/**
	 * If the versioned model is a draft it is returned, otherwise fetches the
	 * draft and lazily creates a new draft if it does not exist adding it to
	 * the database. If a draft is lazily created any
	 * {@link VersionServiceListener} that have been registered are notified.
	 *
	 * @param versionedModel the versioned model
	 * @return the versioned model
	 * @throws PortalException if a portal exception occurred
	 */
	public E getDraft(E versionedModel) throws PortalException;

	/**
	 * Lazily creates a new draft adding it to the database if it does not exist
	 * otherwise the existing versioned model already exists it is returned.
	 * If a draft is lazily created any {@link VersionServiceListener} that
	 * have been registered are notified.
	 *
	 * @param primaryKey of the draft or published versioned model
	 * @return the versioned model
	 * @throws PortalException no draft or published versioned model exists with
	 *         the primary key
	 */
	public E getDraft(long primaryKey) throws PortalException;

	/**
	 * Gets the {@link VersionModel} for the {@link VersionedModel} with the
	 * given version.
	 *
	 * @param versionedModel the versioned model
	 * @param version the version
	 * @return the version model
	 * @throws PortalException if the version does not exist
	 */
	public V getVersion(E versionedModel, int version) throws PortalException;

	/**
	 * Gets the list of {@link VersionModel} for the {@link VersionedModel}.
	 * Sorted by highest to lowest version.
	 *
	 * @param versionedModel the versioned model
	 * @return the version models
	 */
	public List<V> getVersions(E versionedModel);

	/**
	 * Publishes the draft and creates a new version model for the new version.
	 * Notifies any {@link VersionServiceListener} that have been registered.
	 *
	 * @param draftVersionedModel the draft version model
	 * @return the published versioned model
	 * @throws IllegalArgumentException if the versionedModel is not a draft
	 * @throws PortalException if a portal exception occurred
	 */
	public E publishDraft(E draftVersionedModel) throws PortalException;

	/**
	 * Registers the {@link VersionServiceListener}.
	 *
	 * @param versionServiceListener to be registered
	 */
	public void registerListener(
		VersionServiceListener<E, V> versionServiceListener);

	/**
	 * Unregisters the {@link VersionServiceListener}.
	 *
	 * @param versionServiceListener to be unregistered
	 */
	public void unregisterListener(
		VersionServiceListener<E, V> versionServiceListener);

	/**
	 * Updates the draft. Notifies any {@link VersionServiceListener} that have
	 * been registered.
	 *
	 * @param draftVersionedModel the draft version model
	 * @return the draft versioned model
	 * @throws IllegalArgumentException if the versioned model is not a draft
	 * @throws PortalException if a portal exception occurred
	 */
	public E updateDraft(E draftVersionedModel) throws PortalException;

}