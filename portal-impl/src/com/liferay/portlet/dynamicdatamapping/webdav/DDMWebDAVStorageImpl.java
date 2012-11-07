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

package com.liferay.portlet.dynamicdatamapping.webdav;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Juan Fernández
 */
public class DDMWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	@Override
	public int deleteResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			Resource resource = getResource(webDavRequest);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			Object model = resource.getModel();

			if (model instanceof DDMStructure) {
				DDMStructure structure = (DDMStructure)model;

				DDMStructureServiceUtil.deleteStructure(
					structure.getStructureId());

				return HttpServletResponse.SC_NO_CONTENT;
			}
			else if (model instanceof DDMTemplate) {
				DDMTemplate template = (DDMTemplate)model;

				DDMTemplateServiceUtil.deleteTemplate(template.getTemplateId());

				return HttpServletResponse.SC_NO_CONTENT;
			}
			else {
				return HttpServletResponse.SC_FORBIDDEN;
			}
		}
		catch (PortalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public Resource getResource(WebDAVRequest webDavRequest) {
		String[] pathArray = webDavRequest.getPathArray();

		if (pathArray.length == 4) {
			String type = pathArray[2];
			String typeId = pathArray[3];

			if (type.equals(_TYPE_STRUCTURES)) {
				try {
					DDMStructure structure =
						DDMStructureLocalServiceUtil.getStructure(
							Long.valueOf(typeId));

					return toResource(webDavRequest, structure, false);
				}
				catch (Exception e) {
					return null;
				}
			}
			else if (type.equals(_TYPE_TEMPLATES)) {
				try {
					DDMTemplate template =
						DDMTemplateLocalServiceUtil.getTemplate(
							Long.valueOf(typeId));

					return toResource(webDavRequest, template, false);
				}
				catch (Exception e) {
					return null;
				}
			}
		}

		return null;
	}

	public List<Resource> getResources(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			String[] pathArray = webDavRequest.getPathArray();

			if (pathArray.length == 3) {
				String type = pathArray[2];

				if (type.equals(_TYPE_STRUCTURES)) {
					return getStructures(webDavRequest);
				}
				else if (type.equals(_TYPE_TEMPLATES)) {
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
		try {
			Resource resource = getResource(webDavRequest);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			Object model = resource.getModel();

			if (model instanceof DDMStructure) {
				DDMStructure structure = (DDMStructure)model;

				HttpServletRequest request =
					webDavRequest.getHttpServletRequest();

				String xsd = StringUtil.read(request.getInputStream());

				DDMStructureServiceUtil.updateStructure(
					structure.getStructureId(),
					structure.getParentStructureId(), structure.getNameMap(),
					structure.getDescriptionMap(), xsd, new ServiceContext());

				return HttpServletResponse.SC_CREATED;
			}
			else if (model instanceof DDMTemplate) {
				DDMTemplate template = (DDMTemplate)model;

				HttpServletRequest request =
					webDavRequest.getHttpServletRequest();

				String script = StringUtil.read(request.getInputStream());

				DDMTemplateServiceUtil.updateTemplate(
					template.getTemplateId(), template.getNameMap(),
					template.getDescriptionMap(), template.getType(),
					template.getMode(), template.getLanguage(), script,
					template.isCacheable(), template.isSmallImage(),
					template.getSmallImageURL(), null, new ServiceContext());

				return HttpServletResponse.SC_CREATED;
			}
			else {
				return HttpServletResponse.SC_FORBIDDEN;
			}
		}
		catch (PortalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected List<Resource> getStructures(WebDAVRequest webDavRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		long groupId = webDavRequest.getGroupId();

		List<DDMStructure> structures =
			DDMStructureLocalServiceUtil.getStructures(groupId);

		for (DDMStructure structure : structures) {
			Resource resource = toResource(webDavRequest, structure, true);

			resources.add(resource);
		}

		return resources;
	}

	protected List<Resource> getTemplates(WebDAVRequest webDavRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		long groupId = webDavRequest.getGroupId();
		long classNameId = PortalUtil.getClassNameId(
			DDMStructure.class.getName());

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.getTemplates(
			groupId, classNameId);

		for (DDMTemplate template : templates) {
			Resource resource = toResource(webDavRequest, template, true);

			resources.add(resource);
		}

		return resources;
	}

	protected Resource toResource(
		WebDAVRequest webDavRequest, DDMStructure structure,
		boolean appendPath) {

		String parentPath = getRootPath() + webDavRequest.getPath();

		String name = StringPool.BLANK;

		if (appendPath) {
			name = structure.getName();
		}

		return new DDMStructureResourceImpl(structure, parentPath, name);
	}

	protected Resource toResource(
		WebDAVRequest webDavRequest, DDMTemplate template, boolean appendPath) {

		String parentPath = getRootPath() + webDavRequest.getPath();

		String name = StringPool.BLANK;

		if (appendPath) {
			name = template.getName();
		}

		return new DDMTemplateResourceImpl(template, parentPath, name);
	}

	private static final String _TYPE_STRUCTURES = "ddmStructures";

	private static final String _TYPE_TEMPLATES = "ddmTemplates";

}