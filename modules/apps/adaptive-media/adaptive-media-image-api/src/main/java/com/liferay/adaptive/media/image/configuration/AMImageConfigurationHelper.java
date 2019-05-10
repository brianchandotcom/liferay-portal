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

import com.liferay.adaptive.media.exception.AMImageConfigurationException;
import com.liferay.adaptive.media.exception.AMImageConfigurationException.InvalidStateAMImageConfigurationException;

import java.io.IOException;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Manages and fetches an {@link AMImageConfigurationEntry}. This interface is
 * the preferred mechanism to interact with image configuration entries.
 *
 * @author Alejandro Hernández
 */
@ProviderType
public interface AMImageConfigurationHelper {

	/**
	 * Adds a new image configuration entry.
	 *
	 * @param  companyId the primary key of the company
	 * @param  name the image configuration entry's unique name
	 * @param  description the image configuration entry's description
	 * @param  uuid the image configuration entry's UUID
	 * @param  properties a set of properties with additional information about
	 *         how the adaptive media image will be generated
	 * @return the image configuration entry
	 * @throws AMImageConfigurationException if there was an issue with the
	 *         values of the new configuration entry
	 * @throws IOException if there was an issue when persisting the new image
	 *         configuration entry in the store
	 */
	public AMImageConfigurationEntry addAMImageConfigurationEntry(
			long companyId, String name, String description, String uuid,
			Map<String, String> properties)
		throws AMImageConfigurationException, IOException;

	/**
	 * Deletes an existing and disabled image configuration entry. If no image
	 * configuration entry matches the specified UUID, no operation is
	 * performed.
	 *
	 * @param  companyId the primary key of the company
	 * @param  uuid the image configuration entry's UUID
	 * @throws InvalidStateAMImageConfigurationException if the image
	 *         configuration entry to delete was not disabled
	 * @throws IOException if there was an issue when deleting the image
	 *         configuration entry from the store
	 */
	public void deleteAMImageConfigurationEntry(long companyId, String uuid)
		throws InvalidStateAMImageConfigurationException, IOException;

	/**
	 * Disables an existing and enabled image configuration entry. If there is
	 * no image configuration entry with the specified UUID or it is already
	 * disabled, no operation is performed.
	 *
	 * @param  companyId the primary key of the company
	 * @param  uuid the image configuration entry's UUID
	 * @throws IOException if there was an issue when updating the image
	 *         configuration entry from the store
	 */
	public void disableAMImageConfigurationEntry(long companyId, String uuid)
		throws IOException;

	/**
	 * Enables an existing and disabled image configuration entry. If there is
	 * no image configuration entry with the specified UUID or it is already
	 * enabled, no operation is performed.
	 *
	 * @param  companyId the primary key of the company
	 * @param  uuid the image configuration entry's UUID
	 * @throws IOException if there was an issue when updating the image
	 *         configuration entry from the store
	 */
	public void enableAMImageConfigurationEntry(long companyId, String uuid)
		throws IOException;

	/**
	 * Deletes an existing image configuration entry, even if it is enabled.
	 * This should be a last resort. If possible, an image configuration should
	 * be disabled before deleting. If there is no image configuration entry
	 * with the specified UUID, no operation is performed.
	 *
	 * @param  companyId the primary key of the company
	 * @param  uuid the image configuration entry's UUID
	 * @throws IOException if there was an issue when deleting the image
	 *         configuration entry from the store
	 */
	public void forceDeleteAMImageConfigurationEntry(
			long companyId, String uuid)
		throws IOException;

	/**
	 * Returns a collection of the enabled image configuration entries for a
	 * company.
	 *
	 * @param  companyId the primary key of the company
	 * @return the collection of enabled image configuration entries
	 */
	public Collection<AMImageConfigurationEntry> getAMImageConfigurationEntries(
		long companyId);

	/**
	 * Returns a collection of image configuration entries filtered by the given
	 * predicate for a company.
	 *
	 * @param  companyId the primary key of the company
	 * @param  predicate the predicate used to filter the collection
	 * @return the filtered collection of image configuration entries
	 */
	public Collection<AMImageConfigurationEntry> getAMImageConfigurationEntries(
		long companyId, Predicate<? super AMImageConfigurationEntry> predicate);

	/**
	 * Returns an optional image configuration entry for the given company and
	 * image configuration entry UUID.
	 *
	 * @param  companyId the primary key of the company
	 * @param  configurationEntryUUID the image configuration entry's UUID
	 * @return an optional image configuration entry
	 */
	public Optional<AMImageConfigurationEntry> getAMImageConfigurationEntry(
		long companyId, String configurationEntryUUID);

	/**
	 * Updates an existing image configuration entry.
	 *
	 * @param  companyId the primary key of the company
	 * @param  oldUuid the image configuration entry's UUID to update
	 * @param  name the new image configuration entry's name
	 * @param  description the new image configuration entry's description
	 * @param  newUuid the new image configuration entry's UUID
	 * @param  properties the new set of properties with additional information
	 *         about how the adaptive media image will be generated
	 * @return the updated image configuration entry
	 * @throws AMImageConfigurationException if there was an issue with the
	 *         values of the new configuration entry
	 * @throws IOException if there was an issue when persisting the new image
	 *         configuration entry in the store
	 */
	public AMImageConfigurationEntry updateAMImageConfigurationEntry(
			long companyId, String oldUuid, String name, String description,
			String newUuid, Map<String, String> properties)
		throws AMImageConfigurationException, IOException;

}