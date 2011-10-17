/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link WikiNodeService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiNodeService
 * @generated
 */
public class WikiNodeServiceWrapper implements WikiNodeService,
	ServiceWrapper<WikiNodeService> {
	public WikiNodeServiceWrapper(WikiNodeService wikiNodeService) {
		_wikiNodeService = wikiNodeService;
	}

	public com.liferay.portlet.wiki.model.WikiNode addNode(
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeService.addNode(name, description, serviceContext);
	}

	public void deleteNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeService.deleteNode(nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeService.getNode(nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiNode getNode(long groupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeService.getNode(groupId, name);
	}

	public void importPages(long nodeId, java.lang.String importer,
		java.io.InputStream[] inputStreams,
		java.util.Map<java.lang.String, java.lang.String[]> options)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeService.importPages(nodeId, importer, inputStreams, options);
	}

	public void subscribeNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeService.subscribeNode(nodeId);
	}

	public void unsubscribeNode(long nodeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_wikiNodeService.unsubscribeNode(nodeId);
	}

	public com.liferay.portlet.wiki.model.WikiNode updateNode(long nodeId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _wikiNodeService.updateNode(nodeId, name, description,
			serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public WikiNodeService getWrappedWikiNodeService() {
		return _wikiNodeService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedWikiNodeService(WikiNodeService wikiNodeService) {
		_wikiNodeService = wikiNodeService;
	}

	public WikiNodeService getWrappedService() {
		return _wikiNodeService;
	}

	public void setWrappedService(WikiNodeService wikiNodeService) {
		_wikiNodeService = wikiNodeService;
	}

	private WikiNodeService _wikiNodeService;
}