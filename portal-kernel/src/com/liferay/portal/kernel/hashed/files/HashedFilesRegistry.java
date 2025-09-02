/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.hashed.files;

import java.net.URL;

import java.util.function.BiConsumer;

/**
 * @author Iván Zaera Avellón
 */
public interface HashedFilesRegistry {

	public void forEach(BiConsumer<String, String> biConsumer);

	/**
	 * Get the URI of the hashed file associated to an unhashed file URI.
	 *
	 * @return a valid URI or null if hashed file does not exist
	 * @review
	 */
	public String get(String unhashedFileURI);

	/**
	 * Get the URL of the file associated to a given URI.
	 *
	 * @param fileURI an URI like '/o/frontend-js-web/__liferay__/index.js' or
	 *                '/o/frontend-js-web/__liferay__/index.(zXjA8D).js'
	 * @return a valid URL or null if the file does not exist
	 * @review
	 */
	public URL getResourceURL(String fileURI);

}