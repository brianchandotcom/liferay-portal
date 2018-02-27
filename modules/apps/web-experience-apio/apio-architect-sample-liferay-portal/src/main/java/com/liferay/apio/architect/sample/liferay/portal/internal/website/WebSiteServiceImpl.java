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

package com.liferay.apio.architect.sample.liferay.portal.internal.website;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSite;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSiteService;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Victor Oliveira
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class WebSiteServiceImpl implements WebSiteService {

	@Override
	public PageItems<WebSite> getPageItems(
		Pagination pagination, long companyId) {

		List<Group> groups = _groupLocalService.getGroups(companyId, 0, true);

		List<Group> paginationGroups = ListUtil.subList(
			groups, pagination.getStartPosition(), pagination.getEndPosition());

		Stream<Group> stream = paginationGroups.stream();

		List<WebSite> webSites = stream.map(
			WebSiteImpl::new
		).collect(
			Collectors.toList()
		);

		int count = _groupLocalService.getGroupsCount(companyId, 0, true);

		return new PageItems<>(webSites, count);
	}

	@Override
	public Optional<WebSite> getWebSite(long groupId) {
		try {
			Group group = _groupLocalService.getGroup(groupId);

			return Optional.ofNullable(new WebSiteImpl(group));
		}
		catch (NoSuchGroupException nsge) {
			return Optional.empty();
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	@Reference
	private GroupLocalService _groupLocalService;

}