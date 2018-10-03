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

package com.liferay.content.space.apio.internal.architect.resource;

import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.content.space.apio.architect.identifier.ContentSpaceIdentifier;
import com.liferay.content.space.apio.architect.util.ContentSpaceUtil;
import com.liferay.folder.apio.architect.identifier.RootFolderIdentifier;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose content space resources through
 * a web API. The resources are mapped from the internal model {@code Group}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true, service = CollectionResource.class)
public class ContentSpaceCollectionResource
	implements CollectionResource<Group, Long, ContentSpaceIdentifier> {

	@Override
	public CollectionRoutes<Group, Long> collectionRoutes(
		CollectionRoutes.Builder<Group, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class, AcceptLanguage.class
		).build();
	}

	@Override
	public String getName() {
		return "content-space";
	}

	@Override
	public ItemRoutes<Group, Long> itemRoutes(
		ItemRoutes.Builder<Group, Long> builder) {

		return builder.addGetter(
			this::_getGroup
		).build();
	}

	@Override
	public Representor<Group> representor(
		Representor.Builder<Group, Long> builder) {

		return builder.types(
			"ContentSpace"
		).identifier(
			Group::getGroupId
		).addLinkedModel(
			"creator", PersonIdentifier.class, Group::getCreatorUserId
		).addLinkedModel(
			"documentsRepository", RootFolderIdentifier.class, Group::getGroupId
		).addLinkedModel(
			"webSite", WebSiteIdentifier.class, Group::getGroupId
		).addLocalizedStringByLocale(
			"description", Group::getDescription
		).addLocalizedStringByLocale(
			"name", ContentSpaceUtil::getName
		).addStringList(
			"availableLanguages",
			group -> Arrays.asList(
				LocaleUtil.toW3cLanguageIds(group.getAvailableLanguageIds()))
		).build();
	}

	private Group _getGroup(long groupId) throws PortalException {
		return _groupLocalService.getGroup(groupId);
	}

	private PageItems<Group> _getPageItems(
		Pagination pagination, Company company, AcceptLanguage acceptLanguage) {

		List<Group> groups = _groupLocalService.getActiveGroups(
			company.getCompanyId(), true, pagination.getStartPosition(),
			pagination.getEndPosition(),
			new GroupNameComparator(true, acceptLanguage.getPreferredLocale()));
		int count = _groupLocalService.getActiveGroupsCount(
			company.getCompanyId(), true);

		return new PageItems<>(groups, count);
	}

	@Reference
	private GroupLocalService _groupLocalService;

}