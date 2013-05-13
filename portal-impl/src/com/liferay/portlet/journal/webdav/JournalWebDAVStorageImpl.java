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

package com.liferay.portlet.journal.webdav;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.webdav.DDMWebDavUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Juan Fernández
 */
public class JournalWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	@Override
	public int deleteResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		return DDMWebDavUtil.deleteResource(
			webDAVRequest, getRootPath(), getToken(),
			PortalUtil.getClassNameId(JournalArticle.class));
	}

	@Override
	public Resource getResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		return DDMWebDavUtil.getResource(
			webDAVRequest, getRootPath(), getToken(),
			PortalUtil.getClassNameId(JournalArticle.class));
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
				String type = pathArray[2];

				if (type.equals(DDMWebDavUtil.TYPE_STRUCTURES)) {
					return getStructures(webDAVRequest);
				}
				else if (type.equals(DDMWebDavUtil.TYPE_TEMPLATES)) {
					return getTemplates(webDAVRequest);
				}
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
			webDAVRequest, getRootPath(), getToken(),
			PortalUtil.getClassNameId(JournalArticle.class));
	}

	protected List<Resource> getFolders(WebDAVRequest webDAVRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		resources.add(
			DDMWebDavUtil.toResource(
				webDAVRequest, DDMWebDavUtil.TYPE_STRUCTURES, getRootPath(),
				true));
		resources.add(
			DDMWebDavUtil.toResource(
				webDAVRequest, DDMWebDavUtil.TYPE_TEMPLATES, getRootPath(),
				true));

		return resources;
	}

	protected List<Resource> getStructures(WebDAVRequest webDAVRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		List<DDMStructure> ddmStructures =
			DDMStructureLocalServiceUtil.getStructures(
				webDAVRequest.getGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class));

		for (DDMStructure ddmStructure : ddmStructures) {
			Resource resource = DDMWebDavUtil.toResource(
				webDAVRequest, ddmStructure, getRootPath(), true);

			resources.add(resource);
		}

		return resources;
	}

	protected List<Resource> getTemplates(WebDAVRequest webDAVRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		List<DDMTemplate> ddmTemplates =
			DDMTemplateLocalServiceUtil.getTemplatesByStructureClassNameId(
				webDAVRequest.getGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			Resource resource = DDMWebDavUtil.toResource(
				webDAVRequest, ddmTemplate, getRootPath(), true);

			resources.add(resource);
		}

		return resources;
	}

}