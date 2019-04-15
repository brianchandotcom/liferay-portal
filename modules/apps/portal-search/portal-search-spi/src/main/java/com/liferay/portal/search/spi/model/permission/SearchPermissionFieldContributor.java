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