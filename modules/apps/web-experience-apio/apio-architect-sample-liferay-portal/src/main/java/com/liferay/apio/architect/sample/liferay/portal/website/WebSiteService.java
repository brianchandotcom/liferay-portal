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

package com.liferay.apio.architect.sample.liferay.portal.website;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;

import java.util.Optional;

/**
 * Provides the service for getting a {@link WebSite} or a page of a {@code
 * WebSite}.
 *
 * @author Victor Oliveira
 * @author Alejandro Hernández
 */
public interface WebSiteService {

	/**
	 * Returns the website's {@link PageItems} that correspond to the {@link
	 * Pagination} and the ID of the {@code
	 * com.liferay.portal.kernel.model.Company}.
	 *
	 * @param  pagination the {@code Pagination} object
	 * @param  companyId the company ID
	 * @return the website's page items
	 */
	public PageItems<WebSite> getPageItems(
		Pagination pagination, long companyId);

	/**
	 * Returns the website that matches the specified ID, if it exists. Returns
	 * {@code Optional#empty()} otherwise.
	 *
	 * @param  siteId the website's ID
	 * @return the website, if present; {@code Optional#empty()} otherwise
	 */
	public Optional<WebSite> getWebSite(long siteId);

}