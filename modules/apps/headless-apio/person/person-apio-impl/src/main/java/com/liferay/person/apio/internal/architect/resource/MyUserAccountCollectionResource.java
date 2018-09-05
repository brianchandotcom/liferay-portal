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

package com.liferay.person.apio.internal.architect.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.organization.apio.architect.identifier.OrganizationIdentifier;
import com.liferay.person.apio.architect.identifier.MyUserAccountIdentifier;
import com.liferay.person.apio.internal.model.UserWrapper;
import com.liferay.person.apio.internal.util.UserAccountRepresentorBuilderHelper;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.Arrays;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose MyUserAccount resources through
 * a web API. The resources are mapped from the internal model {@link
 * UserWrapper}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class MyUserAccountCollectionResource
	implements CollectionResource<UserWrapper, Long, MyUserAccountIdentifier> {

	@Override
	public CollectionRoutes<UserWrapper, Long> collectionRoutes(
		CollectionRoutes.Builder<UserWrapper, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, ThemeDisplay.class, CurrentUser.class
		).build();
	}

	@Override
	public String getName() {
		return "my-user-account";
	}

	@Override
	public ItemRoutes<UserWrapper, Long> itemRoutes(
		ItemRoutes.Builder<UserWrapper, Long> builder) {

		return builder.addGetter(
			this::_getUserWrapper, ThemeDisplay.class
		).build();
	}

	@Override
	public Representor<UserWrapper> representor(
		Representor.Builder<UserWrapper, Long> builder) {

		Representor.FirstStep<UserWrapper> userWrapperFirstStep =
			_userAccountRepresentorBuilderHelper.buildUserWrapperFirstStep(
				builder);

		userWrapperFirstStep.addRelatedCollection(
			"myOrganizations", OrganizationIdentifier.class);
		userWrapperFirstStep.addRelatedCollection(
			"myWebSites", WebSiteIdentifier.class);

		return userWrapperFirstStep.build();
	}

	private PageItems<UserWrapper> _getPageItems(
			Pagination pagination, ThemeDisplay themeDisplay,
			CurrentUser currentUser)
		throws PortalException {

		if (currentUser != null) {
			UserWrapper userWrapper = new UserWrapper(
				currentUser, themeDisplay);

			return new PageItems<>(Arrays.asList(userWrapper), 1);
		}

		return null;
	}

	private UserWrapper _getUserWrapper(long userId, ThemeDisplay themeDisplay)
		throws PortalException {

		if (themeDisplay.getDefaultUserId() == userId) {
			throw new NotFoundException();
		}

		User user = _userService.getUserById(userId);

		return new UserWrapper(user, themeDisplay);
	}

	@Reference
	private UserAccountRepresentorBuilderHelper
		_userAccountRepresentorBuilderHelper;

	@Reference
	private UserService _userService;

}