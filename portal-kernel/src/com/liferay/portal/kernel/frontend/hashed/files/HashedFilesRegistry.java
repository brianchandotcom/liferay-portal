/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.frontend.hashed.files;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URL;

import java.util.function.BiConsumer;

/**
 * @author Iván Zaera Avellón
 */
public interface HashedFilesRegistry {

	/**
	 * Iterates registered hashed file URIs passing the unhashed and hashed file
	 * URI as arguments to the consumer in each iteration.
	 *
	 * The URIs are absolute to DXP server, i.e., they contain a prefix when
	 * DXP is not running in the application server's root context but not the
	 * proxy path even if it is configured.
	 *
	 * @review
	 */
	public void forEachHashedFileURI(BiConsumer<String, String> biConsumer);

	/**
	 * Iterates registered servlet contexts passing the servlet context name
	 * (eg: "frontend-js-web") and the associated hash to the consumer in each
	 * iteration.
	 *
	 * @review
	 */
	public void forEachServletContextHash(
		BiConsumer<String, String> biConsumer);

	public CachingStrategy getCachingStrategy(
		HttpServletRequest httpServletRequest);

	/**
	 * Get the URI of the hashed file associated to an unhashed file URI.
	 *
	 * The URI is absolute to DXP server, i.e., it must contain a prefix when
	 * DXP is not running in the application server's root context but must not
	 * contain the proxy path if it is configured.
	 *
	 * @return a valid URI or null if hashed file does not exist
	 * @review
	 */
	public String getHashedFileURI(String unhashedFileURI);

	/**
	 * Get the URL of the file associated to a given file URI.
	 *
	 * The URI is absolute to DXP server, i.e., it must contain a prefix when
	 * DXP is not running in the application server's root context but must not
	 * contain the proxy path if it is configured.
	 *
	 * @param fileURI a URI like '/o/frontend-js-web/__liferay__/index.js' or
	 *                '/o/frontend-js-web/__liferay__/index.(zXjA8D).js'
	 * @return a valid URL or null if the file does not exist
	 * @review
	 */
	public URL getResource(String fileURI);

	/**
	 * Get the hash of hashes of all hashed files associated to a servlet
	 * context name (eg: "frontend-js-web").
	 *
	 * @review
	 */
	public String getServletContextHash(String servletContextName);

}