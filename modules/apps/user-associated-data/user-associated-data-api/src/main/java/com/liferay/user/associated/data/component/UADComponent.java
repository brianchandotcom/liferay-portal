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

package com.liferay.user.associated.data.component;

/**
 * Provides the base interface for the UAD framework. Do not implement this
 * interface directly.
 *
 * @author Drew Brokke
 * @param  <T> the entity type to be anonymized, deleted, displayed, edited, or
 *         exported. This is also used as an identifier for grouping the various
 *         components.
 * @see    com.liferay.user.associated.data.anonymizer.UADAnonymizer
 * @see    com.liferay.user.associated.data.display.UADDisplay
 * @see    com.liferay.user.associated.data.exporter.UADExporter
 */
public interface UADComponent<T> {

	/**
	 * Returns the class representing the extending components' data types.
	 *
	 * @return the identifying class of type {@code T}
	 */
	public Class<T> getTypeClass();

	public default String getTypeKey() {
		Class<T> typeClass = getTypeClass();

		return typeClass.getName();
	}

}