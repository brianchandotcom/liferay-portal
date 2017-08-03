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

package com.liferay.vulcan.sample.liferay.portal.internal.resource;

import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.resource.Resource;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.resource.builder.RoutesBuilder;

import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides all the necessary information to expose Group resources through a
 * web API.
 *
 * The resources are mapped from the internal {@link Group} model.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class GroupResource implements Resource<Group> {

	@Override
	public void buildRepresentor(RepresentorBuilder<Group> representorBuilder) {
		representorBuilder.identifier(
			group -> String.valueOf(group.getGroupId())
		).addField(
			"groupType", Group::getTypeLabel
		).addType(
			"Group"
		);
	}

	@Override
	public String getPath() {
		return "groups";
	}

	@Override
	public Routes<Group> routes(RoutesBuilder<Group> routesBuilder) {
		return routesBuilder.collectionPage(
			this::_getPageItems
		).collectionItem(
			this::_getGroup, Long.class
		).build();
	}

	private Group _getGroup(Long id) {
		try {
			return _groupLocalService.getGroup(id);
		}
		catch (NoSuchGroupException nsge) {
			throw new NotFoundException(nsge);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<Group> _getPageItems(Pagination pagination) {
		List<Group> groups = _groupLocalService.getGroups(
			pagination.getStartPosition(), pagination.getEndPosition());

		int groupsCount = _groupLocalService.getGroupsCount();

		return new PageItems<>(groups, groupsCount);
	}

	@Reference
	private GroupLocalService _groupLocalService;

}