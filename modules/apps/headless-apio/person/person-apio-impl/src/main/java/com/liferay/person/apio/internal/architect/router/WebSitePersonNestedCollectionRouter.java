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

package com.liferay.person.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.person.apio.internal.model.UserWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.comparator.UserLastNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/Person">Person</a> resources contained inside a <a
 * href="http://schema.org/WebSite">WebSite</a> through a web API. The resources
 * are mapped from the internal model {@link UserWrapper}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class WebSitePersonNestedCollectionRouter
	implements NestedCollectionRouter
		<UserWrapper, Long, PersonIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<UserWrapper, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<UserWrapper, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, ThemeDisplay.class
		).build();
	}

	private PageItems<UserWrapper> _getPageItems(
			Pagination pagination, long groupId, ThemeDisplay themeDisplay)
		throws PortalException {

		List<UserWrapper> userWrappers = Stream.of(
			_userService.getGroupUsers(
				groupId, WorkflowConstants.STATUS_APPROVED,
				pagination.getStartPosition(), pagination.getEndPosition(),
				new UserLastNameComparator(true))
		).flatMap(
			List::stream
		).map(
			user -> new UserWrapper(user, themeDisplay)
		).collect(
			Collectors.toList()
		);
		int count = _userService.getGroupUsersCount(
			groupId, WorkflowConstants.STATUS_APPROVED);

		return new PageItems<>(userWrappers, count);
	}

	@Reference
	private UserService _userService;

}