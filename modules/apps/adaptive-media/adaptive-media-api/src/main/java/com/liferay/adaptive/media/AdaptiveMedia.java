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

package com.liferay.adaptive.media;

import java.io.InputStream;

import java.net.URI;

import java.util.Optional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Represents content (images, audio, video, and other types of content) along
 * with a set of attributes which characterize the content.
 *
 * @author Adolfo Pérez
 */
@ProviderType
public interface AdaptiveMedia<T> {

	/**
	 * Returns an {@link InputStream} with the raw contents of this {@link
	 * AdaptiveMedia} instance.
	 *
	 * @return An {@link InputStream} with the raw contents of this {@link
	 *         AdaptiveMedia} instance
	 */
	public InputStream getInputStream();

	/**
	 * Returns the URI of this {@link AdaptiveMedia} instance. The URI can be
	 * used by other parts of the system to uniquely identify each {@link
	 * AdaptiveMedia} instance. This URI should be treated as an opaque value.
	 *
	 * @return a URI for this {@link AdaptiveMedia} instance
	 */
	public URI getURI();

	/**
	 * Returns the value of this {@link AdaptiveMedia} instance's attribute.
	 * This method always returns a non-<code>null</code> optional.
	 *
	 * @param  amAttribute the adaptive media attribute
	 * @return the attribute value wrapped in an {@link Optional}. If no value
	 *         exists, an {@link Optional#EMPTY} is returned.
	 */
	public <V> Optional<V> getValueOptional(AMAttribute<T, V> amAttribute);

}