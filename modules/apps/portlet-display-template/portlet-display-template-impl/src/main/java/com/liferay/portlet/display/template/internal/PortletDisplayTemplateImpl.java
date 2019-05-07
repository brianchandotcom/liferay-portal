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

package com.liferay.portlet.display.template.internal;

import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.templateparser.Transformer;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.taglib.util.VelocityTaglib;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 * @author Juan Fernández
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Leonardo Barros
 */
@Component(immediate = true, service = PortletDisplayTemplate.class)
public class PortletDisplayTemplateImpl implements PortletDisplayTemplate {

	@Override
	public DDMTemplate fetchDDMTemplate(long groupId, String displayStyle) {
		try {
			String uuid = getDDMTemplateKey(displayStyle);

			if (Validator.isNull(uuid)) {
				return null;
			}

			try {
				return _ddmTemplateLocalService.getDDMTemplateByUuidAndGroupId(
					uuid, groupId);
			}
			catch (PortalException pe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}

			Group group = _groupLocalService.getGroup(groupId);

			Group companyGroup = _groupLocalService.getCompanyGroup(
				group.getCompanyId());

			try {
				return _ddmTemplateLocalService.getDDMTemplateByUuidAndGroupId(
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
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return groupId;
		}

		try {
			if (group.isLayout()) {
				group = group.getParentGroup();
			}

			if (group.isStagingGroup()) {
				Group liveGroup = group.getLiveGroup();

				if (!liveGroup.isStagedPortlet(
						PortletKeys.PORTLET_DISPLAY_TEMPLATE)) {

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
	public String getDDMTemplateKey(String displayStyle) {
		if (!displayStyle.startsWith(DISPLAY_STYLE_PREFIX)) {
			return null;
		}

		return displayStyle.substring(DISPLAY_STYLE_PREFIX.length());
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public String getDDMTemplateUuid(String displayStyle) {
		return getDDMTemplateKey(displayStyle);
	}

	@Override
	public DDMTemplate getDefaultPortletDisplayTemplateDDMTemplate(
		long groupId, long classNameId) {

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

		if ((templateHandler == null) ||
			(templateHandler.getDefaultTemplateKey() == null)) {

			return null;
		}

		return getPortletDisplayTemplateDDMTemplate(
			groupId, classNameId,
			getDisplayStyle(templateHandler.getDefaultTemplateKey()));
	}

	@Override
	public String getDisplayStyle(String ddmTemplateKey) {
		return DISPLAY_STYLE_PREFIX + ddmTemplateKey;
	}

	@Override
	public DDMTemplate getPortletDisplayTemplateDDMTemplate(
		long groupId, long classNameId, String displayStyle) {

		return getPortletDisplayTemplateDDMTemplate(
			groupId, classNameId, displayStyle, false);
	}

	@Override
	public DDMTemplate getPortletDisplayTemplateDDMTemplate(
		long groupId, long classNameId, String displayStyle,
		boolean useDefault) {

		DDMTemplate portletDisplayDDMTemplate = null;

		if (displayStyle.startsWith(DISPLAY_STYLE_PREFIX)) {
			String ddmTemplateKey = getDDMTemplateKey(displayStyle);

			if (Validator.isNotNull(ddmTemplateKey)) {
				portletDisplayDDMTemplate =
					_ddmTemplateLocalService.fetchTemplate(
						getDDMTemplateGroupId(groupId), classNameId,
						ddmTemplateKey, true);
			}
		}

		if ((portletDisplayDDMTemplate == null) && useDefault) {
			portletDisplayDDMTemplate =
				getDefaultPortletDisplayTemplateDDMTemplate(
					groupId, classNameId);
		}

		return portletDisplayDDMTemplate;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public long getPortletDisplayTemplateDDMTemplateId(
		long groupId, String displayStyle) {

		long portletDisplayDDMTemplateId = 0;

		if (displayStyle.startsWith(DISPLAY_STYLE_PREFIX)) {
			DDMTemplate portletDisplayDDMTemplate = fetchDDMTemplate(
				getDDMTemplateGroupId(groupId), displayStyle);

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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, DDMTemplate ddmTemplate,
			List<?> entries)
		throws Exception {

		Map<String, Object> contextObjects = new HashMap<>();

		return renderDDMTemplate(
			httpServletRequest, httpServletResponse, ddmTemplate, entries,
			contextObjects);
	}

	/**
	 * Returns the DDM template's content.
	 *
	 * @param  httpServletRequest the request corresponding to a portlet render.
	 *         In some cases, such as an {@link HttpServletRequest}
	 *         corresponding to a portlet action or resource request, or for a
	 *         regular servlet, the <code>renderRequest</code> is not accessible
	 *         to the template.
	 * @param  httpServletResponse the response corresponding to a portlet
	 *         render. In some cases, such as an {@link HttpServletResponse}
	 *         corresponding to a portlet action or resource response, or for a
	 *         regular servlet, the <code>renderResponse</code> is not
	 *         accessible to the template.
	 * @param  ddmTemplate the template to be rendered
	 * @param  entries the template's entries
	 * @param  contextObjects the stored parameters used to get the template's
	 *         content
	 * @return the DDM template's content
	 * @throws Exception if an exception occurred
	 */
	@Override
	public String renderDDMTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, DDMTemplate ddmTemplate,
			List<?> entries, Map<String, Object> contextObjects)
		throws Exception {

		Transformer transformer = TransformerHolder.getTransformer();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletRequest != null) && (portletResponse != null)) {
			PortletURL currentURL = PortletURLUtil.getCurrent(
				_portal.getLiferayPortletRequest(portletRequest),
				_portal.getLiferayPortletResponse(portletResponse));

			contextObjects.put(
				PortletDisplayTemplateConstants.CURRENT_URL,
				currentURL.toString());
		}

		contextObjects.put(PortletDisplayTemplateConstants.ENTRIES, entries);

		if (!entries.isEmpty()) {
			contextObjects.put(
				PortletDisplayTemplateConstants.ENTRY, entries.get(0));
		}

		contextObjects.put(
			PortletDisplayTemplateConstants.LOCALE,
			httpServletRequest.getLocale());

		if (portletRequest instanceof RenderRequest) {
			RenderRequest renderRequest = (RenderRequest)portletRequest;

			contextObjects.put(
				PortletDisplayTemplateConstants.RENDER_REQUEST, renderRequest);
		}
		else if (portletRequest instanceof ResourceRequest) {
			ResourceRequest resourceRequest = (ResourceRequest)portletRequest;

			contextObjects.put(
				PortletDisplayTemplateConstants.RESOURCE_REQUEST,
				resourceRequest);
		}

		if (portletResponse instanceof RenderResponse) {
			RenderResponse renderResponse = (RenderResponse)portletResponse;

			contextObjects.put(
				PortletDisplayTemplateConstants.RENDER_RESPONSE,
				renderResponse);
		}
		else if (portletResponse instanceof ResourceResponse) {
			ResourceResponse resourceResponse =
				(ResourceResponse)portletResponse;

			contextObjects.put(
				PortletDisplayTemplateConstants.RESOURCE_RESPONSE,
				resourceResponse);
		}

		contextObjects.put(
			PortletDisplayTemplateConstants.TEMPLATE_ID,
			ddmTemplate.getTemplateId());

		contextObjects.put(
			PortletDisplayTemplateConstants.THEME_DISPLAY, themeDisplay);

		// Custom context objects

		contextObjects.put(
			TemplateConstants.CLASS_NAME_ID, ddmTemplate.getClassNameId());

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(ddmTemplate.getLanguage());

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				ddmTemplate.getClassNameId());

		templateManager.addContextObjects(
			contextObjects, templateHandler.getCustomContextObjects());

		// Taglibs

		templateManager.addTaglibSupport(
			contextObjects, httpServletRequest, httpServletResponse);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		templateManager.addTaglibTheme(
			contextObjects, "taglibLiferay", httpServletRequest,
			new PipingServletResponse(httpServletResponse, unsyncStringWriter));

		contextObjects.put(TemplateConstants.WRITER, unsyncStringWriter);

		if (portletRequest != null) {
			_mergePortletPreferences(portletRequest, contextObjects);
		}

		return transformer.transform(
			themeDisplay, contextObjects, ddmTemplate.getScript(),
			ddmTemplate.getLanguage(), unsyncStringWriter);
	}

	@Override
	public String renderDDMTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long ddmTemplateId,
			List<?> entries)
		throws Exception {

		Map<String, Object> contextObjects = new HashMap<>();

		return renderDDMTemplate(
			httpServletRequest, httpServletResponse, ddmTemplateId, entries,
			contextObjects);
	}

	@Override
	public String renderDDMTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long ddmTemplateId,
			List<?> entries, Map<String, Object> contextObjects)
		throws Exception {

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.getTemplate(
			ddmTemplateId);

		return renderDDMTemplate(
			httpServletRequest, httpServletResponse, ddmTemplate, entries,
			contextObjects);
	}

	private Map<String, Object> _mergePortletPreferences(
		PortletRequest portletRequest, Map<String, Object> contextObjects) {

		PortletPreferences portletPreferences = portletRequest.getPreferences();

		Map<String, String[]> map = portletPreferences.getMap();

		contextObjects.put(
			PortletDisplayTemplateConstants.PORTLET_PREFERENCES, map);

		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			if (contextObjects.containsKey(entry.getKey())) {
				continue;
			}

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

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	private static class TransformerHolder {

		public static Transformer getTransformer() {
			return _transformer;
		}

		private static final Transformer _transformer = new Transformer(
			PropsKeys.PORTLET_DISPLAY_TEMPLATES_ERROR, true);

	}

}