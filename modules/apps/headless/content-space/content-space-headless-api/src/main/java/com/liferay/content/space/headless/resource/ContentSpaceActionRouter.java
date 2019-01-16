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

package com.liferay.content.space.headless.resource;

import com.liferay.apio.architect.annotation.Actions;
import com.liferay.apio.architect.annotation.EntryPoint;
import com.liferay.apio.architect.annotation.Id;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ActionRouter;
import com.liferay.content.space.headless.model.ContentSpace;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;

/**
 * Provides the information necessary to expose content space resources through
 * a web API. The resources are mapped from the internal model {@code Group}.
 *
 * @author Javier Gamarra
 * @author Cristina González
 */
public interface ContentSpaceActionRouter extends ActionRouter<ContentSpace> {

	@Actions.Retrieve
	public ContentSpace getContentSpace(@Id long contentSpaceId)
		throws PortalException;

	@Actions.Retrieve
	@EntryPoint
	public PageItems<ContentSpace> getPageItems(
		Pagination pagination, Company company);

}