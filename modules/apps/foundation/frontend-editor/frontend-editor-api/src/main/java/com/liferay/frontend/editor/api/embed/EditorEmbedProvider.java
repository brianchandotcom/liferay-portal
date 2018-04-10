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

package com.liferay.frontend.editor.api.embed;

/**
 * Provides an interface for supporting embed providers for the editors.
 *
 * <p>
 * Implementations of this class must be OSGi components that are registered in
 * the OSGi Registry.
 * </p>
 *
 * <p>
 * The embed providers can optionally be categorized by using the
 * <code>type</code> OSGi property with any of the values defined in
 * {@link EditorEmbedProviderTypeConstants}. By default the provider will be
 * categorized as {@link EditorEmbedProviderTypeConstants.UNKNOWN} unless
 * specified otherwise.
 * </p>
 *
 * @author Sergio González
 */
public interface EditorEmbedProvider {

	/**
	 * Returns the embed provider's ID
	 *
	 * @return the ID of the embed provider
	 */
	public String getId();

	/**
	 * Returns the template that will be used by the editor to embed the
	 * content.
	 *
	 * The template accepts the variable <code>{embedId}</code> that represents
	 * the content id that should be embedded. This value is obtained from the
	 * url scheme regular expressions.
	 *
	 * This template is tipically an iframe to the provider that will display
	 * the content.
	 *
	 * @return the template that will be used by the editor to embedded the
	 *         content
	 */
	public String getTpl();

	/**
	 * Returns an array with the the url schemes for the embed provider.
	 *
	 * The url scheme describes which urls of the provider have an embedded
	 * representation. Url schemes are defined using a regular expression that
	 * indicates whether a url matches with the provider or not.
	 *
	 * @return the url schemes for the embed provider
	 */
	public String[] getURLSchemes();

}