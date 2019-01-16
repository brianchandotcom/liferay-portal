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

package com.liferay.content.space.headless.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ActionRouter;
import com.liferay.content.space.headless.internal.model.ContentSpaceImpl;
import com.liferay.content.space.headless.model.ContentSpace;
import com.liferay.content.space.headless.resource.ContentSpaceActionRouter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.comparator.GroupIdComparator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose content space resources through
 * a web API. The resources are mapped from the internal model {@code Group}.
 *
 * @author Javier Gamarra
 * @author Cristina González
 */
@Component(immediate = true, service = ActionRouter.class)
public class ContentSpaceActionRouterImpl implements ContentSpaceActionRouter {

	@Override
	public ContentSpace getContentSpace(long contentSpaceId)
		throws PortalException {

		return new ContentSpaceImpl(
			_groupLocalService.getGroup(contentSpaceId));
	}

	@Override
	public PageItems<ContentSpace> getPageItems(
		Pagination pagination, Company company) {

		List<Group> groups = _groupLocalService.getActiveGroups(
			company.getCompanyId(), true, true, pagination.getStartPosition(),
			pagination.getEndPosition(), new GroupIdComparator(true));

		Stream<Group> stream = groups.stream();

		List<ContentSpace> contentSpaces = stream.map(
			ContentSpaceImpl::new
		).collect(
			Collectors.toList()
		);

		int count = _groupLocalService.getActiveGroupsCount(
			company.getCompanyId(), true, true);

		return new PageItems<>(contentSpaces, count);
	}

	@Reference
	private GroupLocalService _groupLocalService;

}