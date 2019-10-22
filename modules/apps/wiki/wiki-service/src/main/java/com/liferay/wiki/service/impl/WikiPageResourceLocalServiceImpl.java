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

package com.liferay.wiki.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.base.WikiPageResourceLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@Component(
	property = "model.class.name=com.liferay.wiki.model.WikiPageResource",
	service = AopService.class
)
public class WikiPageResourceLocalServiceImpl
	extends WikiPageResourceLocalServiceBaseImpl {

	@Override
	public WikiPageResource addPageResource(
		long groupId, long nodeId, String title) {

		long pageResourcePrimKey = counterLocalService.increment();

		WikiPageResource pageResource = wikiPageResourcePersistence.create(
			pageResourcePrimKey);

		pageResource.setGroupId(groupId);
		pageResource.setNodeId(nodeId);
		pageResource.setTitle(title);

		wikiPageResourcePersistence.update(pageResource);

		return pageResource;
	}

	@Override
	public void deletePageResource(long nodeId, String title)
		throws PortalException {

		wikiPageResourcePersistence.removeByN_T(nodeId, title);
	}

	@Override
	public WikiPageResource fetchPageResource(long nodeId, String title) {
		return wikiPageResourcePersistence.fetchByN_T(nodeId, title);
	}

	@Override
	public WikiPageResource fetchPageResource(String uuid) {
		return wikiPageResourcePersistence.fetchByUuid_First(uuid, null);
	}

	@Override
	public WikiPageResource getPageResource(long pageResourcePrimKey)
		throws PortalException {

		return wikiPageResourcePersistence.findByPrimaryKey(
			pageResourcePrimKey);
	}

	@Override
	public WikiPageResource getPageResource(long nodeId, String title)
		throws PortalException {

		return wikiPageResourcePersistence.findByN_T(nodeId, title);
	}

	@Override
	public long getPageResourcePrimKey(
		long groupId, long nodeId, String title) {

		WikiPageResource pageResource = wikiPageResourcePersistence.fetchByN_T(
			nodeId, title);

		if (pageResource == null) {
			pageResource = addPageResource(groupId, nodeId, title);
		}

		return pageResource.getResourcePrimKey();
	}

}