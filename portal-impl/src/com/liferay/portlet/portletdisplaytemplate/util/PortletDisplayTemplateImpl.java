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

package com.liferay.portlet.portletdisplaytemplate.util;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
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

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Garcia
 * @author Juan Fernández
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@DoPrivileged
public class PortletDisplayTemplateImpl implements PortletDisplayTemplate {

	@Override
	public DDMTemplate fetchDDMTemplate(long groupId, String displayStyle) {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				group.getCompanyId());

			String uuid = getDDMTemplateUuid(displayStyle);

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
	public long getDDMTemplateGroupId(long groupId) {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isLayout()) {
				group = group.getParentGroup();
			}

			if (group.isStagingGroup()) {
				Group liveGroup = group.getLiveGroup();

				if (!liveGroup.isStagedPortlet(
						PortletKeys.PORTLET_DISPLAY_TEMPLATES)) {

					return liveGroup.getGroupId();
				}
			}

			return group.getGroupId();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return groupId;
	}

	@Override
	public String getDDMTemplateUuid(String displayStyle) {
		if (!displayStyle.startsWith(DISPLAY_STYLE_PREFIX)) {
			return null;
		}

		return displayStyle.substring(DISPLAY_STYLE_PREFIX.length());
	}

	@Override
	public long getPortletDisplayTemplateDDMTemplateId(
		long groupId, String displayStyle) {

		long portletDisplayDDMTemplateId = 0;

		long portletDisplayDDMTemplateGroupId = getDDMTemplateGroupId(groupId);

		if (displayStyle.startsWith(DISPLAY_STYLE_PREFIX)) {
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
	public List<TemplateHandler> getPortletDisplayTemplateHandlers() {
		List<TemplateHandler> templateHandlers =
			TemplateHandlerRegistryUtil.getTemplateHandlers();

		List<TemplateHandler> portletDisplayTemplateHandlers =
			new ArrayList<>();

		for (TemplateHandler templateHandler : templateHandlers) {
			if (templateHandler instanceof BasePortletDisplayTemplateHandler) {
				portletDisplayTemplateHandlers.add(templateHandler);
			}
			else if (ProxyUtil.isProxyClass(templateHandler.getClass())) {
				InvocationHandler invocationHandler =
					ProxyUtil.getInvocationHandler(templateHandler);

				if (invocationHandler instanceof ClassLoaderBeanHandler) {
					ClassLoaderBeanHandler classLoaderBeanHandler =
						(ClassLoaderBeanHandler)invocationHandler;

					Object bean = classLoaderBeanHandler.getBean();

					if (bean instanceof BasePortletDisplayTemplateHandler) {
						portletDisplayTemplateHandlers.add(templateHandler);
					}
				}
			}
		}

		return portletDisplayTemplateHandlers;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
		String language) {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			new LinkedHashMap<>();

		TemplateVariableGroup fieldsTemplateVariableGroup =
			new TemplateVariableGroup("fields");

		fieldsTemplateVariableGroup.addCollectionVariable(
			"entries", List.class, PortletDisplayTemplateConstants.ENTRIES,
			"entries-item", null, "curEntry", null);
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
			HttpServletRequest request, HttpServletResponse response,
			long ddmTemplateId, List<?> entries)
		throws Exception {

		Map<String, Object> contextObjects = new HashMap<>();

		return renderDDMTemplate(
			request, response, ddmTemplateId, entries, contextObjects);
	}

	@Override
	public String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			long ddmTemplateId, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception {

		contextObjects.put(
			PortletDisplayTemplateConstants.TEMPLATE_ID, ddmTemplateId);
		contextObjects.put(PortletDisplayTemplateConstants.ENTRIES, entries);

		if (!entries.isEmpty()) {
			contextObjects.put(
				PortletDisplayTemplateConstants.ENTRY, entries.get(0));
		}

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

		// Custom context objects

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
			ddmTemplateId);

		contextObjects.put(
			TemplateConstants.CLASS_NAME_ID, ddmTemplate.getClassNameId());

		String language = ddmTemplate.getLanguage();

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(language);

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				ddmTemplate.getClassNameId());

		if (templateHandler instanceof BasePortletDisplayTemplateHandler) {
			BasePortletDisplayTemplateHandler portletDisplayTemplateHandler =
				(BasePortletDisplayTemplateHandler)templateHandler;

			Map<String, Object> customContextObjects =
				portletDisplayTemplateHandler.getCustomContextObjects();

			for (String variableName : customContextObjects.keySet()) {
				if (contextObjects.containsKey(variableName)) {
					continue;
				}

				Object object = customContextObjects.get(variableName);

				if (object instanceof Class) {
					templateManager.addStaticClassSupport(
						contextObjects, variableName, (Class<?>)object);
				}
				else {
					contextObjects.put(variableName, object);
				}
			}
		}

		// Taglibs

		if (language.equals(TemplateConstants.LANG_TYPE_FTL)) {
			_addTaglibSupportFTL(
				templateManager, contextObjects, request, response);
		}
		else if (language.equals(TemplateConstants.LANG_TYPE_VM)) {
			_addTaglibSupportVM(
				templateManager, contextObjects, request, response);
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		contextObjects.putAll(_getPortletPreferences(renderRequest));

		return _transformer.transform(
			themeDisplay, contextObjects, ddmTemplate.getScript(), language,
			unsyncStringWriter);
	}

	private void _addTaglibSupportFTL(
			TemplateManager templateManager, Map<String, Object> contextObjects,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// FreeMarker servlet application

		templateManager.addTaglibApplication(
			contextObjects,
			PortletDisplayTemplateConstants.FREEMARKER_SERVLET_APPLICATION,
			request.getServletContext());

		// FreeMarker servlet request

		templateManager.addTaglibRequest(
			contextObjects,
			PortletDisplayTemplateConstants.FREEMARKER_SERVLET_REQUEST, request,
			response);

		// Taglib Liferay hash

		templateManager.addTaglibFactory(
			contextObjects, PortletDisplayTemplateConstants.TAGLIB_LIFERAY_HASH,
			request.getServletContext());
	}

	private void _addTaglibSupportVM(
		TemplateManager templateManager, Map<String, Object> contextObjects,
		HttpServletRequest request, HttpServletResponse response) {

		templateManager.addTaglibRequest(
			contextObjects, PortletDisplayTemplateConstants.TAGLIB_LIFERAY,
			request, response);
	}

	private Map<String, Object> _getPortletPreferences(
		RenderRequest renderRequest) {

		Map<String, Object> contextObjects = new HashMap<>();

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		Map<String, String[]> map = portletPreferences.getMap();

		contextObjects.put(
			PortletDisplayTemplateConstants.PORTLET_PREFERENCES, map);

		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String[] values = entry.getValue();

			if (ArrayUtil.isEmpty(values)) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		PortletDisplayTemplateImpl.class);

	private final Transformer _transformer = new Transformer(
		PropsKeys.DYNAMIC_DATA_LISTS_ERROR_TEMPLATE, true);

}