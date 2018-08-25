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

package com.liferay.site.apio.internal.architect.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.content.space.apio.architect.identifier.ContentSpaceIdentifier;
import com.liferay.content.space.apio.architect.util.ContentSpaceUtil;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;
import com.liferay.site.apio.internal.model.GroupWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/WebSite">WebSite </a> resources through a web API.
 * The resources are mapped from the internal model {@link Group}.
 *
 * @author Victor Oliveira
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class WebSiteCollectionResource
	implements CollectionResource<GroupWrapper, Long, WebSiteIdentifier> {

	@Override
	public CollectionRoutes<GroupWrapper, Long> collectionRoutes(
		CollectionRoutes.Builder<GroupWrapper, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, ThemeDisplay.class
		).build();
	}

	@Override
	public String getName() {
		return "web-site";
	}

	@Override
	public ItemRoutes<GroupWrapper, Long> itemRoutes(
		ItemRoutes.Builder<GroupWrapper, Long> builder) {

		return builder.addGetter(
			this::_getGroupWrapper, ThemeDisplay.class
		).build();
	}

	@Override
	public Representor<GroupWrapper> representor(
		Representor.Builder<GroupWrapper, Long> builder) {

		return builder.types(
			"WebSite"
		).identifier(
			Group::getGroupId
		).addBidirectionalModel(
			"webSite", "webSites", WebSiteIdentifier.class,
			this::_getParentGroupId
		).addBoolean(
			"active", Group::isActive
		).addLinkedModel(
			"contentSpace", ContentSpaceIdentifier.class, Group::getGroupId
		).addLinkedModel(
			"creator", PersonIdentifier.class, Group::getCreatorUserId
		).addLocalizedStringByLocale(
			"description", Group::getDescription
		).addLocalizedStringByLocale(
			"name", ContentSpaceUtil::getName
		).addRelatedCollection(
			"members", PersonIdentifier.class
		).addString(
			"membershipType", Group::getTypeLabel
		).addString(
			"privateUrl", GroupWrapper::getPrivateURL
		).addString(
			"publicUrl", GroupWrapper::getPublicURL
		).addStringList(
			"availableLanguages",
			group -> Arrays.asList(group.getAvailableLanguageIds())
		).build();
	}

	private GroupWrapper _getGroupWrapper(
			long groupId, ThemeDisplay themeDisplay)
		throws PortalException {

		return new GroupWrapper(_groupService.getGroup(groupId), themeDisplay);
	}

	private PageItems<GroupWrapper> _getPageItems(
			Pagination pagination, ThemeDisplay themeDisplay)
		throws PortalException {

		List<GroupWrapper> groupWrappers = Stream.of(
			_groupService.getGroups(
				themeDisplay.getCompanyId(), 0, true,
				pagination.getStartPosition(), pagination.getEndPosition())
		).flatMap(
			List::stream
		).map(
			group -> new GroupWrapper(group, themeDisplay)
		).collect(
			Collectors.toList()
		);
		int count = _groupService.getGroupsCount(
			themeDisplay.getCompanyId(), 0, true);

		return new PageItems<>(groupWrappers, count);
	}

	private Long _getParentGroupId(Group group) {
		if (group.getParentGroupId() != 0L) {
			return group.getParentGroupId();
		}

		return null;
	}

	@Reference
	private GroupService _groupService;

}