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

package com.liferay.site.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/WebSite">WebSite</a> resources of a <a
 * href="http://schema.org/WebSite">WebSite</a> through a web API. The resources
 * are mapped from the internal model {@link Group}.
 *
 * @author Eduardo Pérez
 */
@Component(immediate = true, service = NestedCollectionRouter.class)
public class WebSiteNestedCollectionRouter
	implements NestedCollectionRouter
		<Group, Long, WebSiteIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<Group, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Group, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	private PageItems<Group> _getPageItems(
			Pagination pagination, long parentGroupId)
		throws PortalException {

		Group group = _groupService.getGroup(parentGroupId);

		List<Group> groups = _groupService.getGroups(
			group.getCompanyId(), parentGroupId, true,
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = _groupService.getGroupsCount(
			group.getCompanyId(), parentGroupId, true);

		return new PageItems<>(groups, count);
	}

	@Reference
	private GroupService _groupService;

}