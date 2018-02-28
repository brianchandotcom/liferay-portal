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
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

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
	implements CollectionResource<Group, Long, WebSiteIdentifier> {

	@Override
	public CollectionRoutes<Group> collectionRoutes(
		CollectionRoutes.Builder<Group> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "web-sites";
	}

	@Override
	public ItemRoutes<Group, Long> itemRoutes(
		ItemRoutes.Builder<Group, Long> builder) {

		return builder.addGetter(
			this::_getGroup
		).build();
	}

	@Override
	public Representor<Group, Long> representor(
		Representor.Builder<Group, Long> builder) {

		return builder.types(
			"WebSite"
		).identifier(
			Group::getGroupId
		).addLocalizedString(
			"description",
			(group, language) -> group.getDescription(
				language.getPreferredLocale())
		).addLocalizedString(
			"name",
			(group, language) -> group.getName(language.getPreferredLocale())
		).build();
	}

	private Group _getGroup(Long groupId) {
		try {
			return _groupLocalService.getGroup(groupId);
		}
		catch (NoSuchGroupException nsge) {
			throw new NotFoundException("Unable to get group " + groupId, nsge);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<Group> _getPageItems(
		Pagination pagination, Company company) {

		List<Group> groups = _groupLocalService.getGroups(
			company.getCompanyId(), 0, true, pagination.getStartPosition(),
			pagination.getEndPosition());
		int count = _groupLocalService.getGroupsCount(
			company.getCompanyId(), 0, true);

		return new PageItems<>(groups, count);
	}

	@Reference
	private GroupLocalService _groupLocalService;

}