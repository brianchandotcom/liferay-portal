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

package com.liferay.adaptive.media.image.configuration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.exception.AdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.exception.AdaptiveMediaImageConfigurationException.InvalidStateAdaptiveMediaImageConfigurationException;

import java.io.IOException;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A helper class to manage and fetch {@link
 * AdaptiveMediaImageConfigurationEntry}. Using this interface is the preferred
 * mechanism to interact with image configuration entries.
 *
 * @author Alejandro Hernández
 *
 * @review
 */
@ProviderType
public interface AdaptiveMediaImageConfigurationHelper {

	/**
	 * Adds a new image configuration entry.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the not blank and unique name of the image configuration
	 *         entry.
	 * @param  description the description of the image configuration entry
	 * @param  uuid the image configuration entry's UUID
	 * @param  properties a set of properties with additional information about
	 *         how the adaptive media image will be generated
	 * @return the image configuration entry
	 * @throws AdaptiveMediaImageConfigurationException if there is an issue
	 *         with the values of the new configuration entry.
	 * @throws IOException if there is an issue when persisting the new image
	 *         configuration entry in the store.
	 *
	 * @review
	 */
	public AdaptiveMediaImageConfigurationEntry
			addAdaptiveMediaImageConfigurationEntry(
				long companyId, String name, String description, String uuid,
				Map<String, String> properties)
		throws AdaptiveMediaImageConfigurationException, IOException;

	/**
	 * Deletes an already existing and disabled image configuration entry.
	 *
	 * <p>
	 * If there is no image configuration entry with the specified UUID no
	 * operation is done.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  uuid the image configuration entry's UUID
	 * @throws InvalidStateAdaptiveMediaImageConfigurationException if the image
	 *         configuration entry to be deleted is not disabled.
	 * @throws IOException if there is an issue when deleting the image
	 *         configuration entry from the store.
	 *
	 * @review
	 */
	public void deleteAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws InvalidStateAdaptiveMediaImageConfigurationException,
			IOException;

	/**
	 * Disables an already existing and enabled image configuration entry.
	 *
	 * <p>
	 * If there is no image configuration entry with the specified UUID or it is
	 * already disabled no operation is done.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  uuid the image configuration entry's UUID
	 * @throws IOException if there is an issue when updating the image
	 *         configuration entry from the store.
	 *
	 * @review
	 */
	public void disableAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws IOException;

	/**
	 * Enables an already existing and disabled image configuration entry.
	 *
	 * <p>
	 * If there is no image configuration entry with the specified UUID or it is
	 * already enabled no operation is done.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  uuid the image configuration entry's UUID
	 * @throws IOException if there is an issue when updating the image
	 *         configuration entry from the store.
	 *
	 * @review
	 */
	public void enableAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws IOException;

	/**
	 * Deletes an already existing image configuration entry even if it is
	 * enabled.
	 *
	 * <p>
	 * If there is no image configuration entry with the specified UUID no
	 * operation is done.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  uuid the image configuration entry's UUID
	 * @throws IOException if there is an issue when deleting the image
	 *         configuration entry from the store.
	 *
	 * @review
	 */
	public void forceDeleteAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws IOException;

	/**
	 * Returns a collection of the enabled image configuration entries in a
	 * particular company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the collection of enabled image configuration entries
	 *
	 * @review
	 */
	public Collection<AdaptiveMediaImageConfigurationEntry>
		getAdaptiveMediaImageConfigurationEntries(long companyId);

	/**
	 * Returns a collection of the image configuration entries filtered by a
	 * particular predicate.
	 *
	 * @param  companyId the primary key of the company
	 * @param  predicate the predicate used to filter the collection
	 * @return the collection filtered by predicate of image configuration
	 *         entries
	 *
	 * @review
	 */
	public Collection<AdaptiveMediaImageConfigurationEntry>
		getAdaptiveMediaImageConfigurationEntries(
			long companyId,
			Predicate<? super AdaptiveMediaImageConfigurationEntry> predicate);

	/**
	 * Returns an optional image configuration entry for a particular company
	 * and image configuration entry's UUID.
	 *
	 * @param  companyId the primary key of the company
	 * @param  configurationEntryUUID the image configuration entry's UUID
	 * @return an optional image configuration entry
	 *
	 * @review
	 */
	public Optional<AdaptiveMediaImageConfigurationEntry>
		getAdaptiveMediaImageConfigurationEntry(
			long companyId, String configurationEntryUUID);

	/**
	 * Updates an already existing image configuration entry.
	 *
	 * @param  companyId the primary key of the company
	 * @param  oldUuid the image configuration entry's UUID to be updated
	 * @param  name the name of the new image configuration entry
	 * @param  description the description of the new image configuration entry
	 * @param  newUuid the new image configuration entry'ps UUID
	 * @param  properties the new set of properties with additional information
	 *         about how the adaptive media image will be generated
	 * @return the updated image configuration entry
	 * @throws AdaptiveMediaImageConfigurationException if there is an issue
	 *         with the values of the new configuration entry.
	 * @throws IOException if there is an issue when persisting the new image
	 *         configuration entry in the store.
	 *
	 * @review
	 */
	public AdaptiveMediaImageConfigurationEntry
			updateAdaptiveMediaImageConfigurationEntry(
				long companyId, String oldUuid, String name, String description,
				String newUuid, Map<String, String> properties)
		throws AdaptiveMediaImageConfigurationException, IOException;

}