/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.filter;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface Filter extends com.liferay.portal.kernel.search.filter.Filter {

	@Override
	public default <T> T accept(
		com.liferay.portal.kernel.search.filter.FilterVisitor<T>
			filterVisitor) {

		return (T)accept((FilterVisitor)filterVisitor);
	}

	public <T> T accept(FilterVisitor<T> filterVisitor);

}