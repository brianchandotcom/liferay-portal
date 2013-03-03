/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.portletdisplaytemplate.webdav;

import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.webdav.DDMWebDavUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Fernández
 */
public class ApplicationDisplayTemplateWebDAVStorageImpl
	extends BaseWebDAVStorageImpl {

	@Override
	public int deleteResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		return DDMWebDavUtil.deleteResource(
			webDavRequest, getRootPath(), getToken(), 0);
	}

	public Resource getResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		return DDMWebDavUtil.getResource(
			webDavRequest, getRootPath(), getToken(), 0);
	}

	public List<Resource> getResources(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			String[] pathArray = webDavRequest.getPathArray();

			if (pathArray.length == 2) {
				return getFolders(webDavRequest);
			}
			else if (pathArray.length == 3) {
				return getTemplates(webDavRequest);
			}

			return new ArrayList<Resource>();
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public int putResource(WebDAVRequest webDavRequest) throws WebDAVException {
		return DDMWebDavUtil.putResource(
			webDavRequest, getRootPath(), getToken(), 0);
	}

	protected List<Resource> getFolders(WebDAVRequest webDavRequest)
		throws Exception {

		List<Resource> folders = new ArrayList<Resource>();

		folders.add(
			DDMWebDavUtil.toResource(
				webDavRequest, DDMWebDavUtil.TYPE_TEMPLATES, getRootPath(),
				true));

		return folders;
	}

	protected List<Resource> getTemplates(WebDAVRequest webDavRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		List<DDMTemplate> ddmTemplates =
			DDMTemplateLocalServiceUtil.getTemplatesByClassPK(
				webDavRequest.getGroupId(), 0);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			Resource resource = DDMWebDavUtil.toResource(
				webDavRequest, ddmTemplate, getRootPath(), true);

			resources.add(resource);
		}

		return resources;
	}

}