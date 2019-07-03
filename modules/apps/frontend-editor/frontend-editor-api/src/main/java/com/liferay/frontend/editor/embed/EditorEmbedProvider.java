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

package com.liferay.frontend.editor.embed;

/**
 * Provides an interface for supporting embed providers for editors.
 * Implementations of this class must be OSGi components that are registered in
 * the OSGi Registry.
 *
 * <p>
 * The embed providers can optionally be categorized by using the
 * <code>type</code> OSGi property with any of the values defined in {@link
 * EditorEmbedProviderTypeConstants}. By default, the provider is categorized as
 * {@link EditorEmbedProviderTypeConstants#UNKNOWN}, unless specified otherwise.
 * </p>
 *
 * @author Sergio González
 */
public interface EditorEmbedProvider {

	/**
	 * Returns the embed provider's ID.
	 *
	 * @return the embed provider's ID
	 */
	public String getId();

	/**
	 * Returns the template that is used by the editor to embed the content.
	 * This template is usually an iFrame to the provider that displays the
	 * content.
	 *
	 * <p>
	 * The template accepts the <code>embedId</code> that represents the ID of
	 * the content to embed. This value is obtained from URL scheme regular
	 * expressions.
	 * </p>
	 *
	 * @return the template that is used by the editor to embed the content
	 */
	public String getTpl();

	/**
	 * Returns an array with the URL schemes for the embed provider. The URL
	 * scheme describes which URLs of the provider have an embedded
	 * representation. URL schemes are defined using a JavaScript regular
	 * expression that indicates whether a URL matches the provider.
	 *
	 * <p>
	 * Every URL scheme should contain a single matching group. This match is
	 * used to replace the <code>embedId</code> placeholder from the provided
	 * template.
	 * </p>
	 *
	 * @return the URL schemes for the embed provider
	 */
	public String[] getURLSchemes();

}