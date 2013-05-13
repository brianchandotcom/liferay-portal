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
	public int deleteResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		return DDMWebDavUtil.deleteResource(
			webDAVRequest, getRootPath(), getToken(), 0);
	}

	@Override
	public Resource getResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		return DDMWebDavUtil.getResource(
			webDAVRequest, getRootPath(), getToken(), 0);
	}

	@Override
	public List<Resource> getResources(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		try {
			String[] pathArray = webDAVRequest.getPathArray();

			if (pathArray.length == 2) {
				return getFolders(webDAVRequest);
			}
			else if (pathArray.length == 3) {
				return getTemplates(webDAVRequest);
			}

			return new ArrayList<Resource>();
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public int putResource(WebDAVRequest webDAVRequest) throws WebDAVException {
		return DDMWebDavUtil.putResource(
			webDAVRequest, getRootPath(), getToken(), 0);
	}

	protected List<Resource> getFolders(WebDAVRequest webDAVRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		resources.add(
			DDMWebDavUtil.toResource(
				webDAVRequest, DDMWebDavUtil.TYPE_TEMPLATES, getRootPath(),
				true));

		return resources;
	}

	protected List<Resource> getTemplates(WebDAVRequest webDAVRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		List<DDMTemplate> ddmTemplates =
			DDMTemplateLocalServiceUtil.getTemplatesByClassPK(
				webDAVRequest.getGroupId(), 0);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			Resource resource = DDMWebDavUtil.toResource(
				webDAVRequest, ddmTemplate, getRootPath(), true);

			resources.add(resource);
		}

		return resources;
	}

}