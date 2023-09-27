/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.spi.model.permission;

import com.liferay.portal.kernel.search.Document;

/**
 * Indexes new permission checking fields in a search document. These fields can
 * be matched when returning search results via a corresponding {@link
 * SearchPermissionFilterContributor}.
 *
 * <p>
 * Register implementations of this interface as OSGi components using the
 * service {@code SearchPermissionFieldContributor}.
 * </p>
 *
 * @author Bryan Engler
 */
public interface SearchPermissionFieldContributor {

	/**
	 * Contributes permission checking fields to the search document.
	 *
	 * @param document the document being indexed
	 * @param className the class name of the entity being indexed
	 * @param classPK the primary key of the entity being indexed
	 */
	public void contribute(Document document, String className, long classPK);

}