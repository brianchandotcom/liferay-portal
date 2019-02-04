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

package com.liferay.folder.apio.internal.architect.resource;

import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.ItemResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.folder.apio.architect.identifier.RootFolderIdentifier;
import com.liferay.media.object.apio.architect.identifier.MediaObjectIdentifier;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the root folder resource through
 * a web API. This resource is mapped from the internal model {@code Group}.
 *
 * @author Eduardo Pérez
 */
@Component(immediate = true, service = ItemResource.class)
public class RootFolderItemResource
	implements ItemResource<Group, Long, RootFolderIdentifier> {

	@Override
	public String getName() {
		return "documents-repository";
	}

	@Override
	public ItemRoutes<Group, Long> itemRoutes(
		ItemRoutes.Builder<Group, Long> builder) {

		return builder.addGetter(
			_groupService::getGroup
		).build();
	}

	@Override
	public Representor<Group> representor(
		Representor.Builder<Group, Long> builder) {

		return builder.types(
			"RootFolder"
		).identifier(
			Group::getGroupId
		).addRelatedCollection(
			"documents", MediaObjectIdentifier.class
		).build();
	}

	@Reference
	private GroupService _groupService;

}