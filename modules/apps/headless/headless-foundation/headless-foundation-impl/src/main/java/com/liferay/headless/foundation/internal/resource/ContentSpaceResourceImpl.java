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

package com.liferay.headless.foundation.internal.resource;

import com.liferay.headless.foundation.dto.ContentSpace;
import com.liferay.headless.foundation.dto.ContentSpaceCollection;
import com.liferay.headless.foundation.resource.ContentSpaceResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.comparator.GroupIdComparator;
import com.liferay.portal.vulcan.context.Pagination;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-foundation-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = ContentSpaceResource.class
)
@Generated("")
public class ContentSpaceResourceImpl implements ContentSpaceResource {

	@Override
	public ContentSpaceCollection<ContentSpace> getContentSpaceCollection(
			Pagination pagination, String size)
		throws Exception {

		List<Group> groups = _groupLocalService.getActiveGroups(
			PortalUtil.getDefaultCompanyId(), true, true, pagination.getStartPosition(),
			pagination.getEndPosition(), new GroupIdComparator(true));

		Stream<Group> stream = groups.stream();

		List<ContentSpace> contentSpaces = stream.map(
			group -> {
				ContentSpace contentSpace = new ContentSpace();
				contentSpace.setId(group.getGroupId());
				return contentSpace;
			}
		).collect(
			Collectors.toList()
		);

		int totalCount = _groupLocalService.getActiveGroupsCount(
			PortalUtil.getDefaultCompanyId(), true, true);

		return new ContentSpaceCollection(contentSpaces, totalCount);
	}

	@Reference
	private GroupLocalService _groupLocalService;

}