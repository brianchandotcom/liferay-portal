/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.provider.search;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marco Leo
 */
@ProviderType
public interface FDSPagination {

	/**
	 * Returns the position of the requested page's last element.
	 *
	 * @return the position of the requested page's last element
	 */
	public int getEndPosition();

	/**
	 * Returns the requested page's number.
	 *
	 * @return the requested page's number
	 */
	public int getPage();

	/**
	 * Returns the selected number of items per page.
	 *
	 * @return the selected number of items per page
	 */
	public int getPageSize();

	/**
	 * Returns the position of the requested page's first element.
	 *
	 * @return the position of the requested page's first element
	 */
	public int getStartPosition();

}