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

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.content.space.apio.architect.model.ContentSpace;
import com.liferay.content.space.apio.architect.util.ContentSpaceUtil;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.comparator.GroupIdComparator;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.Arrays;
import java.util.List;

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
@Component(immediate = true, service = CollectionResource.class)
public class WebSiteCollectionResource
	implements CollectionResource<Group, Long, WebSiteIdentifier> {

	@Override
	public CollectionRoutes<Group, Long> collectionRoutes(
		CollectionRoutes.Builder<Group, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "web-site";
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
			"WebSite"
		).identifier(
			Group::getGroupId
		).addBidirectionalModel(
			"contentSpace", "webSite", ContentSpace.class, Group::getGroupId
		).addBidirectionalModel(
			"webSite", "webSites", WebSiteIdentifier.class,
			this::_getParentGroupId
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
			"privateUrl", this::_getPrivateURL
		).addString(
			"publicUrl", this::_getPublicURL
		).addStringList(
			"availableLanguages",
			group -> Arrays.asList(
				LocaleUtil.toW3cLanguageIds(group.getAvailableLanguageIds()))
		).build();
	}

	private String _getDisplayURL(Group group, boolean privateLayout) {
		return Try.fromFallible(
			() -> _getThemeDisplay(group, privateLayout)
		).map(
			themeDisplay -> group.getDisplayURL(themeDisplay, privateLayout)
		).orElse(
			null
		);
	}

	private Group _getGroup(long groupId) throws PortalException {
		return _groupLocalService.getGroup(groupId);
	}

	private PageItems<Group> _getPageItems(
		Pagination pagination, Company company) {

		List<Group> groups = _groupLocalService.getActiveGroups(
			company.getCompanyId(), true, true, pagination.getStartPosition(),
			pagination.getEndPosition(), new GroupIdComparator(true));

		int count = _groupLocalService.getActiveGroupsCount(
			company.getCompanyId(), true, true);

		return new PageItems<>(groups, count);
	}

	private Long _getParentGroupId(Group group) {
		if (group.getParentGroupId() != 0L) {
			return group.getParentGroupId();
		}

		return null;
	}

	private String _getPrivateURL(Group group) {
		return _getDisplayURL(group, true);
	}

	private String _getPublicURL(Group group) {
		return _getDisplayURL(group, false);
	}

	private ThemeDisplay _getThemeDisplay(Group group, boolean privateLayout)
		throws PortalException {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = _companyService.getCompanyById(group.getCompanyId());

		themeDisplay.setCompany(company);

		List<Layout> layouts = _layoutService.getLayouts(
			group.getGroupId(), privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		themeDisplay.setLayout(layouts.get(0));

		themeDisplay.setPortalURL(company.getPortalURL(group.getGroupId()));
		themeDisplay.setSiteGroupId(group.getGroupId());

		return themeDisplay;
	}

	@Reference
	private CompanyService _companyService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutService _layoutService;

}