/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.spi.contributor.helper;

/**
 * @author André de Oliveira
 */
public interface DestinationBuilder {

	/**
	 * Replaces all occurrences of a substring in the destination URL with a
	 * new substring.
	 *
	 * @param  oldSub The substring to be replaced.
	 * @param  newSub The replacement substring.
	 * @return This {@link DestinationBuilder} instance.
	 */
	public DestinationBuilder replace(String oldSub, String newSub);

	/**
	 * Replaces the value of a specific parameter in the destination URL.
	 *
	 * @param  parameter The name of the parameter to replace.
	 * @param  newValue The new value for the parameter.
	 * @return This {@link DestinationBuilder} instance.
	 */
	public DestinationBuilder replaceParameter(
		String parameter, String newValue);

	/**
	 * Replaces the entire destination URL string with a new URL string.
	 *
	 * @param  urlString The new URL string to replace the existing destination
	 *         URL.
	 * @return This {@link DestinationBuilder} instance.
	 */
	public DestinationBuilder replaceURLString(String urlString);

}