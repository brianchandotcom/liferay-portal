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

package com.liferay.portlet.dynamicdatalists.webdav;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.webdav.DDMWebDavUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Fernández
 */
public class DDLWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	@Override
	public int deleteResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		return DDMWebDavUtil.deleteResource(
			webDavRequest, getRootPath(), getToken(),
			PortalUtil.getClassNameId(DDLRecordSet.class));
	}

	public Resource getResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		return DDMWebDavUtil.getResource(
			webDavRequest, getRootPath(), getToken(),
			PortalUtil.getClassNameId(DDLRecordSet.class));
	}

	public List<Resource> getResources(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			String[] pathArray = webDavRequest.getPathArray();

			if (pathArray.length == 2) {
				return getFolders(webDavRequest);
			}
			else if (pathArray.length == 3) {
				String type = pathArray[2];

				if (type.equals(DDMWebDavUtil.TYPE_STRUCTURES)) {
					return getStructures(webDavRequest);
				}
				else if (type.equals(DDMWebDavUtil.TYPE_TEMPLATES)) {
					return getTemplates(webDavRequest);
				}
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
			webDavRequest, getRootPath(), getToken(),
			PortalUtil.getClassNameId(DDLRecordSet.class));
	}

	protected List<Resource> getFolders(WebDAVRequest webDavRequest)
		throws Exception {

		List<Resource> folders = new ArrayList<Resource>();

		folders.add(
			DDMWebDavUtil.toResource(
				webDavRequest, DDMWebDavUtil.TYPE_STRUCTURES, getRootPath(),
				true));
		folders.add(
			DDMWebDavUtil.toResource(
				webDavRequest, DDMWebDavUtil.TYPE_TEMPLATES, getRootPath(),
				true));

		return folders;
	}

	protected List<Resource> getStructures(WebDAVRequest webDavRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		List<DDMStructure> ddmStructures =
			DDMStructureLocalServiceUtil.getStructures(
				webDavRequest.getGroupId(),
				PortalUtil.getClassNameId(DDLRecordSet.class));

		for (DDMStructure ddmStructure : ddmStructures) {
			Resource resource = DDMWebDavUtil.toResource(
				webDavRequest, ddmStructure, getRootPath(), true);

			resources.add(resource);
		}

		return resources;
	}

	protected List<Resource> getTemplates(WebDAVRequest webDavRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		List<DDMTemplate> ddmTemplates =
			DDMTemplateLocalServiceUtil.getTemplatesByStructureClassNameId(
				webDavRequest.getGroupId(),
				PortalUtil.getClassNameId(DDLRecordSet.class),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			Resource resource = DDMWebDavUtil.toResource(
				webDavRequest, ddmTemplate, getRootPath(), true);

			resources.add(resource);
		}

		return resources;
	}

}