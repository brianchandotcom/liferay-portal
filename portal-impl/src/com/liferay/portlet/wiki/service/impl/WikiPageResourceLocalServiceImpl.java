/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.base.WikiPageResourceLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class WikiPageResourceLocalServiceImpl
	extends WikiPageResourceLocalServiceBaseImpl {

	@Override
	public WikiPageResource addPageResource(long nodeId, String title)
		throws SystemException {

		long pageResourcePrimKey = counterLocalService.increment();

		WikiPageResource pageResource = wikiPageResourcePersistence.create(
			pageResourcePrimKey);

		pageResource.setNodeId(nodeId);
		pageResource.setTitle(title);

		wikiPageResourcePersistence.update(pageResource);

		return pageResource;
	}

	@Override
	public void deletePageResource(long nodeId, String title)
		throws PortalException, SystemException {

		wikiPageResourcePersistence.removeByN_T(nodeId, title);
	}

	@Override
	public WikiPageResource fetchPageResource(long nodeId, String title)
		throws SystemException {

		return wikiPageResourcePersistence.fetchByN_T(nodeId, title);
	}

	@Override
	public WikiPageResource getPageResource(long pageResourcePrimKey)
		throws PortalException, SystemException {

		return wikiPageResourcePersistence.findByPrimaryKey(
			pageResourcePrimKey);
	}

	@Override
	public WikiPageResource getPageResource(long nodeId, String title)
		throws PortalException, SystemException {

		return wikiPageResourcePersistence.findByN_T(nodeId, title);
	}

	@Override
	public long getPageResourcePrimKey(long nodeId, String title)
		throws SystemException {

		WikiPageResource pageResource = wikiPageResourcePersistence.fetchByN_T(
			nodeId, title);

		if (pageResource == null) {
			long pageResourcePrimKey = counterLocalService.increment();

			pageResource = wikiPageResourcePersistence.create(
				pageResourcePrimKey);

			pageResource.setNodeId(nodeId);
			pageResource.setTitle(title);

			wikiPageResourcePersistence.update(pageResource);
		}

		return pageResource.getResourcePrimKey();
	}

}