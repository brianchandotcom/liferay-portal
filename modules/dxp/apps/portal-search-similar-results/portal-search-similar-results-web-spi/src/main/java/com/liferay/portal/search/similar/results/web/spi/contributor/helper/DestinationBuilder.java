/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.spi.contributor.helper;

/**
 * @author André de Oliveira
 */
public interface DestinationBuilder {

	public DestinationBuilder replace(String oldSub, String newSub);

	public DestinationBuilder replaceParameter(
		String parameter, String newValue);

	public DestinationBuilder replaceURLString(String urlString);

}