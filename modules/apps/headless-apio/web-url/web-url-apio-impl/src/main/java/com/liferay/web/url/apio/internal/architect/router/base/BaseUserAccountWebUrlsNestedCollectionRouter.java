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

package com.liferay.web.url.apio.internal.architect.router.base;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.WebsiteService;
import com.liferay.web.url.apio.architect.identifier.WebUrlIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Perez
 */
public abstract class BaseUserAccountWebUrlsNestedCollectionRouter
	<T extends Identifier<Long>> implements
		NestedCollectionRouter<Website, Long, WebUrlIdentifier, Long, T> {

	@Override
	public NestedCollectionRoutes<Website, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Website, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Reference
	protected UserService userService;

	@Reference
	protected WebsiteService websiteService;

	private PageItems<Website> _getPageItems(
			Pagination pagination, long personId)
		throws PortalException {

		User user = userService.getUserById(personId);

		List<Website> websites = websiteService.getWebsites(
			Contact.class.getName(), user.getContactId());

		int endPosition = Math.min(
			websites.size(), pagination.getEndPosition());

		return new PageItems<>(
			websites.subList(pagination.getStartPosition(), endPosition),
			websites.size());
	}

}