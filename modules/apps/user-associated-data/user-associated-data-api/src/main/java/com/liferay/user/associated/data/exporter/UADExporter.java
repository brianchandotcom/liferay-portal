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

package com.liferay.user.associated.data.exporter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.user.associated.data.component.UADComponent;

import java.io.File;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Handles converting the user-related type {@code T} entities into a format
 * that can be written to a file and downloaded.
 *
 * @author William Newbury
 */
@ProviderType
public interface UADExporter<T> extends UADComponent<T> {

	/**
	 * Returns the number of type {@code T} entities associated with the user.
	 *
	 * @param  userId the primary key of the user whose data to count
	 * @return the number of entities associated with the user
	 */
	public long count(long userId) throws PortalException;

	/**
	 * Returns a byte array representing the entity, ready to be written to a
	 * file.
	 *
	 * @param  t the type {@code T} entity to convert into a byte array
	 * @return a byte array representing the given entity
	 * @throws PortalException if a portal exception occurred
	 */
	public byte[] export(T t) throws PortalException;

	/**
	 * Returns a file object containing the data from all type {@code T}
	 * entities related to the user.
	 *
	 * @param  userId the primary key of the user whose data to export
	 * @return a file containing the exported data
	 * @throws PortalException if a portal exception occurred
	 */
	public File exportAll(long userId) throws PortalException;

	/**
	 * Returns the number of export data items of type {@code T} entities
	 * associated with the user.
	 *
	 * @param  userId the primary key of the user whose data to count
	 * @return the number of export data items
	 */
	public default long getExportDataCount(long userId) throws PortalException {
		return count(userId);
	}

}