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

package com.liferay.site.apio.internal.resource;

import static com.liferay.portal.kernel.workflow.WorkflowConstants.STATUS_APPROVED;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.person.apio.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.comparator.UserLastNameComparator;
import com.liferay.site.apio.identifier.WebSiteIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Perez
 */
@Component(immediate = true)
public class PersonNestedCollectionRouter implements
	NestedCollectionRouter<User, PersonIdentifier, Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<User, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<User, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	private PageItems<User> _getPageItems(Pagination pagination, Long groupId)
		throws PortalException {

		List<User> users = _userService.getGroupUsers(
			groupId, STATUS_APPROVED, pagination.getStartPosition(),
			pagination.getEndPosition(), new UserLastNameComparator(true));

		int userCount = _userService.getGroupUsersCount(
			groupId, STATUS_APPROVED);

		return new PageItems<>(users, userCount);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserService _userService;

}