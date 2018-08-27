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
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;
import com.liferay.site.apio.internal.model.GroupWrapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/WebSite">WebSite</a> resources contained inside a <a
 * href="http://schema.org/WebSite">WebSite</a> through a web API. The resources
 * are mapped from the internal model {@link GroupWrapper}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class WebSiteNestedCollectionRouter
	implements NestedCollectionRouter
		<GroupWrapper, Long, WebSiteIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<GroupWrapper, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<GroupWrapper, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, ThemeDisplay.class
		).build();
	}

	private PageItems<GroupWrapper> _getPageItems(
			Pagination pagination, long parentGroupId,
			ThemeDisplay themeDisplay)
		throws PortalException {

		List<GroupWrapper> groupWrappers = Stream.of(
			_groupService.getGroups(
				themeDisplay.getCompanyId(), parentGroupId, true,
				pagination.getStartPosition(), pagination.getEndPosition())
		).flatMap(
			List::stream
		).map(
			group ->
				new GroupWrapper(group, themeDisplay)
		).collect(
			Collectors.toList()
		);
		int count = _groupService.getGroupsCount(
			themeDisplay.getCompanyId(), parentGroupId, true);

		return new PageItems<>(groupWrappers, count);
	}

	@Reference
	private GroupService _groupService;

}