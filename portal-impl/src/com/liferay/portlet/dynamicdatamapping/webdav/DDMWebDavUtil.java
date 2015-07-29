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

package com.liferay.portlet.dynamicdatamapping.webdav;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Juan Fernández
 */
public class DDMWebDavUtil {

	public static final String TYPE_STRUCTURES = "Structures";

	public static final String TYPE_TEMPLATES = "Templates";

	public static int addResource(WebDAVRequest webDavRequest, long classNameId)
		throws Exception {

		String[] pathArray = webDavRequest.getPathArray();

		if (pathArray.length != 4) {
			return HttpServletResponse.SC_FORBIDDEN;
		}

		String type = pathArray[2];
		String typeId = pathArray[3];

		if (type.equals(TYPE_STRUCTURES)) {
			HttpServletRequest request = webDavRequest.getHttpServletRequest();

			String definition = StringUtil.read(request.getInputStream());

			DDMForm ddmForm = getDDMForm(definition);

			DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(
				ddmForm);

			Map<Locale, String> nameMap = new HashMap<>();

			Locale defaultLocale = ddmForm.getDefaultLocale();

			nameMap.put(defaultLocale, typeId);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			DDMStructureLocalServiceUtil.addStructure(
				webDavRequest.getUserId(), webDavRequest.getGroupId(),
				classNameId, nameMap, null, ddmForm, ddmFormLayout,
				StorageType.JSON.toString(), serviceContext);

			return HttpServletResponse.SC_CREATED;
		}
		else if (type.equals(TYPE_TEMPLATES)) {

			// DDM templates can not be added via WebDAV because there is no way
			// to know the associated class name or class PK

			return HttpServletResponse.SC_FORBIDDEN;
		}

		return HttpServletResponse.SC_FORBIDDEN;
	}

	public static int deleteResource(
			WebDAVRequest webDAVRequest, String rootPath, String token,
			long classNameId)
		throws WebDAVException {

		try {
			Resource resource = getResource(
				webDAVRequest, rootPath, token, classNameId);

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
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public static Resource getResource(
			WebDAVRequest webDAVRequest, String rootPath, String token,
			long classNameId)
		throws WebDAVException {

		try {
			String[] pathArray = webDAVRequest.getPathArray();

			if (pathArray.length == 2) {
				String path = rootPath + webDAVRequest.getPath();

				return new BaseResourceImpl(path, StringPool.BLANK, token);
			}
			else if (pathArray.length == 3) {
				String type = pathArray[2];

				return toResource(webDAVRequest, type, rootPath, false);
			}
			else if (pathArray.length == 4) {
				String type = pathArray[2];
				String typeId = pathArray[3];

				if (type.equals(TYPE_STRUCTURES)) {
					DDMStructure structure =
						DDMStructureLocalServiceUtil.fetchStructure(
							GetterUtil.getLong(typeId));

					if (structure == null) {
						structure = DDMStructureLocalServiceUtil.fetchStructure(
							webDAVRequest.getGroupId(), classNameId, typeId);
					}

					if (structure == null) {
						return null;
					}

					return DDMWebDavUtil.toResource(
						webDAVRequest, structure, rootPath, false);
				}
				else if (type.equals(TYPE_TEMPLATES)) {
					DDMTemplate template =
						DDMTemplateLocalServiceUtil.fetchDDMTemplate(
							GetterUtil.getLong(typeId));

					if (template == null) {
						template = DDMTemplateLocalServiceUtil.fetchTemplate(
							webDAVRequest.getGroupId(), classNameId, typeId);
					}

					if (template == null) {
						return null;
					}

					return DDMWebDavUtil.toResource(
						webDAVRequest, template, rootPath, false);
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public static int putResource(
			WebDAVRequest webDAVRequest, String rootPath, String token,
			long classNameId)
		throws WebDAVException {

		try {
			Resource resource = getResource(
				webDAVRequest, rootPath, token, classNameId);

			if (resource == null) {
				return addResource(webDAVRequest, classNameId);
			}

			Object model = resource.getModel();

			if (model instanceof DDMStructure) {
				DDMStructure structure = (DDMStructure)model;

				HttpServletRequest request =
					webDAVRequest.getHttpServletRequest();

				String definition = StringUtil.read(request.getInputStream());

				DDMForm ddmForm = getDDMForm(definition);

				DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(
					ddmForm);

				DDMStructureServiceUtil.updateStructure(
					structure.getGroupId(), structure.getParentStructureId(),
					structure.getClassNameId(), structure.getStructureKey(),
					structure.getNameMap(), structure.getDescriptionMap(),
					ddmForm, ddmFormLayout, new ServiceContext());

				return HttpServletResponse.SC_CREATED;
			}
			else if (model instanceof DDMTemplate) {
				DDMTemplate template = (DDMTemplate)model;

				HttpServletRequest request =
					webDAVRequest.getHttpServletRequest();

				String script = StringUtil.read(request.getInputStream());

				DDMTemplateServiceUtil.updateTemplate(
					template.getTemplateId(), template.getClassPK(),
					template.getNameMap(), template.getDescriptionMap(),
					template.getType(), template.getMode(),
					template.getLanguage(), script, template.isCacheable(),
					template.isSmallImage(), template.getSmallImageURL(), null,
					new ServiceContext());

				return HttpServletResponse.SC_CREATED;
			}
			else {
				return HttpServletResponse.SC_FORBIDDEN;
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public static Resource toResource(
		WebDAVRequest webDAVRequest, DDMStructure structure, String rootPath,
		boolean appendPath) {

		String parentPath = rootPath + webDAVRequest.getPath();

		String name = StringPool.BLANK;

		if (appendPath) {
			name = String.valueOf(structure.getStructureId());
		}

		return new DDMStructureResourceImpl(structure, parentPath, name);
	}

	public static Resource toResource(
		WebDAVRequest webDAVRequest, DDMTemplate template, String rootPath,
		boolean appendPath) {

		String parentPath = rootPath + webDAVRequest.getPath();

		String name = StringPool.BLANK;

		if (appendPath) {
			name = String.valueOf(template.getTemplateId());
		}

		return new DDMTemplateResourceImpl(template, parentPath, name);
	}

	public static Resource toResource(
		WebDAVRequest webDAVRequest, String type, String rootPath,
		boolean appendPath) {

		String parentPath = rootPath + webDAVRequest.getPath();

		String name = StringPool.BLANK;

		if (appendPath) {
			name = type;
		}

		Resource resource = new BaseResourceImpl(parentPath, name, type);

		resource.setModel(type);

		return resource;
	}

	protected static DDMForm getDDMForm(String definition)
		throws PortalException {

		DDMXMLUtil.validateXML(definition);

		return DDMFormXSDDeserializerUtil.deserialize(definition);
	}

	private static final Log _log = LogFactoryUtil.getLog(DDMWebDavUtil.class);

}