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

package com.liferay.portlet.portletdisplaytemplate.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.GenericServletWrapper;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.templateparser.Transformer;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.taglib.util.VelocityTaglib;
import com.liferay.taglib.util.VelocityTaglibImpl;
import com.liferay.util.freemarker.FreeMarkerTaglibFactoryUtil;

import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.ServletContextHashModel;

import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

/**
 * @author Eduardo Garcia
 * @author Juan Fernández
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class PortletDisplayTemplateImpl implements PortletDisplayTemplate {

	@Override
	public DDMTemplate fetchDDMTemplate(long groupId, String displayStyle) {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				group.getCompanyId());

			if (!displayStyle.startsWith("ddmTemplate_")) {
				return null;
			}

			String uuid = displayStyle.substring(12);

			if (Validator.isNull(uuid)) {
				return null;
			}

			try {
				return
					DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
						uuid, groupId);
			}
			catch (NoSuchTemplateException nste) {
			}

			try {
				return
					DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
						uuid, companyGroup.getGroupId());
			}
			catch (NoSuchTemplateException nste) {
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	@Override
	public long getDDMTemplateGroupId(ThemeDisplay themeDisplay) {
		try {
			Group scopeGroup = themeDisplay.getScopeGroup();

			if (scopeGroup.isLayout()) {
				scopeGroup = scopeGroup.getParentGroup();
			}

			if (scopeGroup.isStagingGroup()) {
				Group liveGroup = scopeGroup.getLiveGroup();

				if (!liveGroup.isStagedPortlet(
						PortletKeys.PORTLET_DISPLAY_TEMPLATES)) {

					return liveGroup.getGroupId();
				}
			}

			return scopeGroup.getGroupId();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return themeDisplay.getScopeGroupId();
	}

	@Override
	public long getPortletDisplayTemplateDDMTemplateId(
		ThemeDisplay themeDisplay, String displayStyle) {

		long portletDisplayDDMTemplateId = 0;

		long portletDisplayDDMTemplateGroupId = getDDMTemplateGroupId(
			themeDisplay);

		if (displayStyle.startsWith("ddmTemplate_")) {
			DDMTemplate portletDisplayDDMTemplate = fetchDDMTemplate(
				portletDisplayDDMTemplateGroupId, displayStyle);

			if (portletDisplayDDMTemplate != null) {
				portletDisplayDDMTemplateId =
					portletDisplayDDMTemplate.getTemplateId();
			}
		}

		return portletDisplayDDMTemplateId;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
		String language) {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			new LinkedHashMap<String, TemplateVariableGroup>();

		TemplateVariableGroup fieldsTemplateVariableGroup =
			new TemplateVariableGroup("fields");

		fieldsTemplateVariableGroup.addCollectionVariable(
			"entries", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"entries-item", null, "curEntry");
		fieldsTemplateVariableGroup.addVariable(
			"entry", null, PortletDisplayTemplateConstants.ENTRY);

		templateVariableGroups.put("fields", fieldsTemplateVariableGroup);

		TemplateVariableGroup generalVariablesTemplateVariableGroup =
			new TemplateVariableGroup("general-variables");

		generalVariablesTemplateVariableGroup.addVariable(
			"current-url", String.class,
			PortletDisplayTemplateConstants.CURRENT_URL);
		generalVariablesTemplateVariableGroup.addVariable(
			"locale", Locale.class, PortletDisplayTemplateConstants.LOCALE);
		generalVariablesTemplateVariableGroup.addVariable(
			"portlet-preferences", Map.class,
			PortletDisplayTemplateConstants.PORTLET_PREFERENCES);
		generalVariablesTemplateVariableGroup.addVariable(
			"template-id", null, PortletDisplayTemplateConstants.TEMPLATE_ID);
		generalVariablesTemplateVariableGroup.addVariable(
			"theme-display", ThemeDisplay.class,
			PortletDisplayTemplateConstants.THEME_DISPLAY);

		templateVariableGroups.put(
			"general-variables", generalVariablesTemplateVariableGroup);

		TemplateVariableGroup utilTemplateVariableGroup =
			new TemplateVariableGroup("util");

		utilTemplateVariableGroup.addVariable(
			"http-request", HttpServletRequest.class,
			PortletDisplayTemplateConstants.REQUEST);

		if (language.equals(TemplateConstants.LANG_TYPE_VM)) {
			utilTemplateVariableGroup.addVariable(
				"liferay-taglib", VelocityTaglib.class,
				PortletDisplayTemplateConstants.TAGLIB_LIFERAY);
		}

		utilTemplateVariableGroup.addVariable(
			"render-request", RenderRequest.class,
			PortletDisplayTemplateConstants.RENDER_REQUEST);
		utilTemplateVariableGroup.addVariable(
			"render-response", RenderResponse.class,
			PortletDisplayTemplateConstants.RENDER_RESPONSE);

		templateVariableGroups.put("util", utilTemplateVariableGroup);

		return templateVariableGroups;
	}

	@Override
	public String renderDDMTemplate(
			PageContext pageContext, long ddmTemplateId, List<?> entries)
		throws Exception {

		Map<String, Object> contextObjects = new HashMap<String, Object>();

		return renderDDMTemplate(
			pageContext, ddmTemplateId, entries, contextObjects);
	}

	@Override
	public String renderDDMTemplate(
			PageContext pageContext, long ddmTemplateId, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception {

		contextObjects.put(
			PortletDisplayTemplateConstants.TEMPLATE_ID, ddmTemplateId);
		contextObjects.put(PortletDisplayTemplateConstants.ENTRIES, entries);

		if (!entries.isEmpty()) {
			contextObjects.put(
				PortletDisplayTemplateConstants.ENTRY, entries.get(0));
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		contextObjects.put(
			PortletDisplayTemplateConstants.LOCALE, request.getLocale());

		contextObjects.put(PortletDisplayTemplateConstants.REQUEST, request);

		RenderRequest renderRequest = (RenderRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		contextObjects.put(
			PortletDisplayTemplateConstants.RENDER_REQUEST, renderRequest);

		RenderResponse renderResponse = (RenderResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		contextObjects.put(
			PortletDisplayTemplateConstants.RENDER_RESPONSE, renderResponse);

		PortletURL currentURL = PortletURLUtil.getCurrent(
			renderRequest, renderResponse);

		contextObjects.put(
			PortletDisplayTemplateConstants.CURRENT_URL, currentURL.toString());

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		contextObjects.put(
			PortletDisplayTemplateConstants.THEME_DISPLAY, themeDisplay);

		// Taglibs

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
			ddmTemplateId);

		String language = ddmTemplate.getLanguage();

		if (language.equals(TemplateConstants.LANG_TYPE_FTL)) {
			_addTaglibSupportFTL(contextObjects, pageContext);
		}
		else if (language.equals(TemplateConstants.LANG_TYPE_VM)) {
			_addTaglibSupportVM(contextObjects, pageContext);
		}

		contextObjects.putAll(_getPortletPreferences(renderRequest));

		return _transformer.transform(
			themeDisplay, contextObjects, ddmTemplate.getScript(), language);
	}

	private void _addTaglibSupportFTL(
			Map<String, Object> contextObjects, PageContext pageContext)
		throws Exception {

		// FreeMarker servlet application

		final Servlet servlet = (Servlet)pageContext.getPage();

		GenericServlet genericServlet = null;

		if (servlet instanceof GenericServlet) {
			genericServlet = (GenericServlet)servlet;
		}
		else {
			genericServlet = new GenericServletWrapper(servlet);

			genericServlet.init(pageContext.getServletConfig());
		}

		ServletContextHashModel servletContextHashModel =
			new ServletContextHashModel(
				genericServlet, ObjectWrapper.DEFAULT_WRAPPER);

		contextObjects.put(
			PortletDisplayTemplateConstants.FREEMARKER_SERVLET_APPLICATION,
			servletContextHashModel);

		// FreeMarker servlet request

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		HttpRequestHashModel requestHashModel = new HttpRequestHashModel(
			request, response, ObjectWrapper.DEFAULT_WRAPPER);

		contextObjects.put(
			PortletDisplayTemplateConstants.FREEMARKER_SERVLET_REQUEST,
			requestHashModel);

		// Taglib Liferay hash

		TemplateHashModel taglibLiferayHash =
			FreeMarkerTaglibFactoryUtil.createTaglibFactory(
				pageContext.getServletContext());

		contextObjects.put(
			PortletDisplayTemplateConstants.TAGLIB_LIFERAY_HASH,
			taglibLiferayHash);
	}

	private void _addTaglibSupportVM(
		Map<String, Object> contextObjects, PageContext pageContext) {

		contextObjects.put(
			PortletDisplayTemplateConstants.TAGLIB_LIFERAY,
			_getVelocityTaglib(pageContext));
	}

	private Map<String, Object> _getPortletPreferences(
		RenderRequest renderRequest) {

		Map<String, Object> contextObjects = new HashMap<String, Object>();

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		Map<String, String[]> map = portletPreferences.getMap();

		contextObjects.put(
			PortletDisplayTemplateConstants.PORTLET_PREFERENCES, map);

		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String[] values = entry.getValue();

			if ((values == null) || (values.length == 0)) {
				continue;
			}

			String value = values[0];

			if (value == null) {
				continue;
			}

			contextObjects.put(entry.getKey(), value);
		}

		return contextObjects;
	}

	private VelocityTaglib _getVelocityTaglib(PageContext pageContext) {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		VelocityTaglib velocityTaglib = new VelocityTaglibImpl(
			servletContext, request,
			new PipingServletResponse(response, pageContext.getOut()),
			pageContext, null);

		return velocityTaglib;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletDisplayTemplateImpl.class);

	private Transformer _transformer = new Transformer(
		PropsKeys.DYNAMIC_DATA_LISTS_ERROR_TEMPLATE, false);

}