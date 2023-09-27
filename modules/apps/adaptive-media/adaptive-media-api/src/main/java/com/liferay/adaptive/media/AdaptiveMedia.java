/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media;

import java.io.InputStream;

import java.net.URI;

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

	public <V> V getValue(AMAttribute<T, V> amAttribute);

}