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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.DummyWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTemplateConstants;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.impl.LayoutTemplateImpl;
import com.liferay.portal.service.base.LayoutTemplateLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.layoutconfiguration.util.velocity.InitColumnProcessor;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * @author Ivica Cardic
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class LayoutTemplateLocalServiceImpl
	extends LayoutTemplateLocalServiceBaseImpl {

	public String getContent(
			String layoutTemplateId, boolean standard, String themeId)
		throws SystemException {

		LayoutTemplate layoutTemplate = getLayoutTemplate(
			layoutTemplateId, standard, themeId);

		if (layoutTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Layout template " + layoutTemplateId + " does not exist");
			}

			layoutTemplate = getLayoutTemplate(
				PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID, standard, themeId);

			if (layoutTemplate == null) {
				_log.error(
					"Layout template " + layoutTemplateId +
						" and default layout template " +
							PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID +
								" do not exist");

				return StringPool.BLANK;
			}
		}

		if (PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED) {
			return layoutTemplate.getContent();
		}
		else {
			try {
				return layoutTemplate.getUncachedContent();
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}
	}

	public LayoutTemplate getLayoutTemplate(
		String layoutTemplateId, boolean standard, String themeId) {

		if (Validator.isNull(layoutTemplateId)) {
			return null;
		}

		LayoutTemplate layoutTemplate = null;

		if (themeId != null) {
			if (standard) {
				layoutTemplate = _getThemesStandard(themeId).get(
					layoutTemplateId);
			}
			else {
				layoutTemplate = _getThemesCustom(themeId).get(
					layoutTemplateId);
			}

			if (layoutTemplate != null) {
				return layoutTemplate;
			}
		}

		if (standard) {
			layoutTemplate = _warStandard.get(layoutTemplateId);

			if (layoutTemplate == null) {
				layoutTemplate = _portalStandard.get(layoutTemplateId);
			}
		}
		else {
			layoutTemplate = _warCustom.get(layoutTemplateId);

			if (layoutTemplate == null) {
				layoutTemplate = _portalCustom.get(layoutTemplateId);
			}
		}

		return layoutTemplate;
	}

	public List<LayoutTemplate> getLayoutTemplates() {
		List<LayoutTemplate> customLayoutTemplates =
			new ArrayList<LayoutTemplate>(
							_portalCustom.size() + _warCustom.size());

		customLayoutTemplates.addAll(ListUtil.fromMapValues(_portalCustom));
		customLayoutTemplates.addAll(ListUtil.fromMapValues(_warCustom));

		return customLayoutTemplates;
	}

	public List<LayoutTemplate> getLayoutTemplates(String themeId) {
		Map<String, LayoutTemplate> _themesCustom = _getThemesCustom(themeId);

		List<LayoutTemplate> customLayoutTemplates =
			new ArrayList<LayoutTemplate>(
				_portalCustom.size() + _warCustom.size() +
					_themesCustom.size());

		Iterator<Map.Entry<String, LayoutTemplate>> itr =
			_portalCustom.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, LayoutTemplate> entry = itr.next();

			String layoutTemplateId = entry.getKey();
			LayoutTemplate layoutTemplate = entry.getValue();

			LayoutTemplate themeCustomLayoutTemplate = _themesCustom.get(
				layoutTemplateId);

			if (themeCustomLayoutTemplate != null) {
				customLayoutTemplates.add(themeCustomLayoutTemplate);
			}
			else {
				LayoutTemplate warCustomLayoutTemplate = _warCustom.get(
					layoutTemplateId);

				if (warCustomLayoutTemplate != null) {
					customLayoutTemplates.add(warCustomLayoutTemplate);
				}
				else {
					customLayoutTemplates.add(layoutTemplate);
				}
			}
		}

		itr = _warCustom.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, LayoutTemplate> entry = itr.next();

			String layoutTemplateId = entry.getKey();

			if (!_portalCustom.containsKey(layoutTemplateId) &&
				!_themesCustom.containsKey(layoutTemplateId)) {

				customLayoutTemplates.add(_warCustom.get(layoutTemplateId));
			}
		}

		itr = _themesCustom.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, LayoutTemplate> entry = itr.next();

			String layoutTemplateId = entry.getKey();

			if (!_portalCustom.containsKey(layoutTemplateId) &&
				!_warCustom.containsKey(layoutTemplateId)) {

				customLayoutTemplates.add(_themesCustom.get(layoutTemplateId));
			}
		}

		return customLayoutTemplates;
	}

	public String getWapContent(
			String layoutTemplateId, boolean standard, String themeId)
		throws SystemException {

		LayoutTemplate layoutTemplate = getLayoutTemplate(
			layoutTemplateId, standard, themeId);

		if (layoutTemplate == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Layout template " + layoutTemplateId + " does not exist");
			}

			layoutTemplate = getLayoutTemplate(
				PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID, standard, themeId);

			if (layoutTemplate == null) {
				_log.error(
					"Layout template " + layoutTemplateId +
						" and default layout template " +
							PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID +
								" do not exist");

				return StringPool.BLANK;
			}
		}

		if (PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED) {
			return layoutTemplate.getWapContent();
		}
		else {
			try {
				return layoutTemplate.getUncachedWapContent();
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}
	}

	public List<ObjectValuePair<String, Boolean>> init(
		ServletContext servletContext, String[] xmls,
		PluginPackage pluginPackage) {

		return init(null, servletContext, xmls, pluginPackage);
	}

	public List<ObjectValuePair<String, Boolean>> init(
		String servletContextName, ServletContext servletContext, String[] xmls,
		PluginPackage pluginPackage) {

		List<ObjectValuePair<String, Boolean>> layoutTemplateIds =
			new ArrayList<ObjectValuePair<String, Boolean>>();

		try {
			for (int i = 0; i < xmls.length; i++) {
				Set<ObjectValuePair<String, Boolean>> curLayoutTemplateIds =
					_readLayoutTemplates(
						servletContextName, servletContext, xmls[i],
						pluginPackage);

				Iterator<ObjectValuePair<String, Boolean>> itr =
					curLayoutTemplateIds.iterator();

				while (itr.hasNext()) {
					ObjectValuePair<String, Boolean> ovp = itr.next();

					if (!layoutTemplateIds.contains(ovp)) {
						layoutTemplateIds.add(ovp);
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return layoutTemplateIds;
	}

	public void readLayoutTemplate(
		String servletContextName, ServletContext servletContext,
		Set<ObjectValuePair<String, Boolean>> layoutTemplateIds,
		com.liferay.portal.kernel.xml.Element el, boolean standard,
		String themeId, PluginPackage pluginPackage) {

		Map<String, LayoutTemplate> layoutTemplates = null;

		if (themeId != null) {
			if (standard) {
				layoutTemplates = _getThemesStandard(themeId);
			}
			else {
				layoutTemplates = _getThemesCustom(themeId);
			}
		}
		else if (servletContextName != null) {
			if (standard) {
				layoutTemplates = _warStandard;
			}
			else {
				layoutTemplates = _warCustom;
			}
		}
		else {
			if (standard) {
				layoutTemplates = _portalStandard;
			}
			else {
				layoutTemplates = _portalCustom;
			}
		}

		Iterator<com.liferay.portal.kernel.xml.Element> itr = el.elements(
			"layout-template").iterator();

		while (itr.hasNext()) {
			com.liferay.portal.kernel.xml.Element layoutTemplate = itr.next();

			String layoutTemplateId = layoutTemplate.attributeValue("id");

			if (layoutTemplateIds != null) {
				ObjectValuePair<String, Boolean> ovp =
					new ObjectValuePair<String, Boolean>(
						layoutTemplateId, standard);

				layoutTemplateIds.add(ovp);
			}

			LayoutTemplate layoutTemplateModel = layoutTemplates.get(
				layoutTemplateId);

			if (layoutTemplateModel == null) {
				layoutTemplateModel = new LayoutTemplateImpl(layoutTemplateId);

				layoutTemplates.put(layoutTemplateId, layoutTemplateModel);
			}

			PluginSetting pluginSetting =
				pluginSettingLocalService.getDefaultPluginSetting();

			layoutTemplateModel.setPluginPackage(pluginPackage);
			layoutTemplateModel.setServletContext(servletContext);

			if (servletContextName != null) {
				layoutTemplateModel.setServletContextName(servletContextName);
			}

			layoutTemplateModel.setStandard(standard);
			layoutTemplateModel.setThemeId(themeId);
			layoutTemplateModel.setName(GetterUtil.getString(
				layoutTemplate.attributeValue("name"),
				layoutTemplateModel.getName()));
			layoutTemplateModel.setTemplatePath(GetterUtil.getString(
				layoutTemplate.elementText("template-path"),
				layoutTemplateModel.getTemplatePath()));
			layoutTemplateModel.setWapTemplatePath(GetterUtil.getString(
				layoutTemplate.elementText("wap-template-path"),
				layoutTemplateModel.getWapTemplatePath()));
			layoutTemplateModel.setThumbnailPath(GetterUtil.getString(
				layoutTemplate.elementText("thumbnail-path"),
				layoutTemplateModel.getThumbnailPath()));

			String content = null;

			try {
				content = HttpUtil.URLtoString(servletContext.getResource(
					layoutTemplateModel.getTemplatePath()));
			}
			catch (Exception e) {
				_log.error(
					"Unable to get content at template path " +
						layoutTemplateModel.getTemplatePath() + ": " +
							e.getMessage());
			}

			if (Validator.isNull(content)) {
				_log.error(
					"No content found at template path " +
						layoutTemplateModel.getTemplatePath());
			}
			else {
				StringBundler sb = new StringBundler(3);

				sb.append(themeId);

				if (standard) {
					sb.append(LayoutTemplateConstants.STANDARD_SEPARATOR);
				}
				else {
					sb.append(LayoutTemplateConstants.CUSTOM_SEPARATOR);
				}

				sb.append(layoutTemplateId);

				String velocityTemplateId = sb.toString();

				layoutTemplateModel.setContent(content);
				layoutTemplateModel.setColumns(
					_getColumns(velocityTemplateId, content));
			}

			if (Validator.isNull(layoutTemplateModel.getWapTemplatePath())) {
				_log.error(
					"The element wap-template-path is not defined for " +
						layoutTemplateId);
			}
			else {
				String wapContent = null;

				try {
					wapContent = HttpUtil.URLtoString(
						servletContext.getResource(
							layoutTemplateModel.getWapTemplatePath()));
				}
				catch (Exception e) {
					_log.error(
						"Unable to get content at WAP template path " +
							layoutTemplateModel.getWapTemplatePath() + ": " +
								e.getMessage());
				}

				if (Validator.isNull(wapContent)) {
					_log.error(
						"No content found at WAP template path " +
							layoutTemplateModel.getWapTemplatePath());
				}
				else {
					layoutTemplateModel.setWapContent(wapContent);
				}
			}

			com.liferay.portal.kernel.xml.Element rolesEl =
				layoutTemplate.element("roles");

			if (rolesEl != null) {
				Iterator<com.liferay.portal.kernel.xml.Element> itr2 =
					rolesEl.elements("role-name").iterator();

				while (itr2.hasNext()) {
					com.liferay.portal.kernel.xml.Element roleNameEl =
						itr2.next();

					pluginSetting.addRole(roleNameEl.getText());
				}
			}

			layoutTemplateModel.setDefaultPluginSetting(pluginSetting);
		}
	}

	public void uninstallLayoutTemplate(
		String layoutTemplateId, boolean standard) {

		if (standard) {
			VelocityEngineUtil.flushTemplate(
				"null" + LayoutTemplateConstants.STANDARD_SEPARATOR +
					layoutTemplateId);

			_warStandard.remove(layoutTemplateId);
		}
		else {
			VelocityEngineUtil.flushTemplate(
				"null" + LayoutTemplateConstants.CUSTOM_SEPARATOR +
					layoutTemplateId);

			_warCustom.remove(layoutTemplateId);
		}
	}

	public void uninstallLayoutTemplates(String themeId) {
		Map<String, LayoutTemplate> _themesStandard = _getThemesStandard(
			themeId);

		for (Map.Entry<String, LayoutTemplate> entry :
				_themesStandard.entrySet()) {

			LayoutTemplate layoutTemplate = entry.getValue();

			VelocityEngineUtil.flushTemplate(
				themeId + LayoutTemplateConstants.STANDARD_SEPARATOR +
					layoutTemplate.getLayoutTemplateId());
		}

		_themesStandard.clear();

		Map<String, LayoutTemplate> _themesCustom = _getThemesCustom(themeId);

		for (Map.Entry<String, LayoutTemplate> entry :
				_themesCustom.entrySet()) {

			LayoutTemplate layoutTemplate = entry.getValue();

			VelocityEngineUtil.flushTemplate(
				themeId + LayoutTemplateConstants.CUSTOM_SEPARATOR +
					layoutTemplate.getLayoutTemplateId());
		}

		_themesCustom.clear();
	}

	private List<String> _getColumns(
		String velocityTemplateId, String velocityTemplateContent) {

		try {
			InitColumnProcessor processor = new InitColumnProcessor();

			VelocityContext velocityContext =
				VelocityEngineUtil.getStandardToolsContext();

			velocityContext.put("processor", processor);

			VelocityEngineUtil.mergeTemplate(
				velocityTemplateId, velocityTemplateContent, velocityContext,
				new DummyWriter());

			return ListUtil.sort(processor.getColumns());
		}
		catch (Exception e) {
			_log.error(e);

			return new ArrayList<String>();
		}
	}

	private Map<String, LayoutTemplate> _getThemesCustom(String themeId) {
		String key = themeId.concat(LayoutTemplateConstants.CUSTOM_SEPARATOR);

		Map<String, LayoutTemplate> layoutTemplates = _themes.get(key);

		if (layoutTemplates == null) {
			layoutTemplates = new LinkedHashMap<String, LayoutTemplate>();

			_themes.put(key, layoutTemplates);
		}

		return layoutTemplates;
	}

	private Map<String, LayoutTemplate> _getThemesStandard(String themeId) {
		String key = themeId + LayoutTemplateConstants.STANDARD_SEPARATOR;

		Map<String, LayoutTemplate> layoutTemplates = _themes.get(key);

		if (layoutTemplates == null) {
			layoutTemplates = new LinkedHashMap<String, LayoutTemplate>();

			_themes.put(key, layoutTemplates);
		}

		return layoutTemplates;
	}

	private Set<ObjectValuePair<String, Boolean>> _readLayoutTemplates(
			String servletContextName, ServletContext servletContext,
			String xml, PluginPackage pluginPackage)
		throws Exception {

		Set<ObjectValuePair<String, Boolean>> layoutTemplateIds =
			new HashSet<ObjectValuePair<String, Boolean>>();

		if (xml == null) {
			return layoutTemplateIds;
		}

		Document doc = SAXReaderUtil.read(xml, true);

		Element root = doc.getRootElement();

		Element standardEl = root.element("standard");

		if (standardEl != null) {
			readLayoutTemplate(
				servletContextName, servletContext, layoutTemplateIds,
				standardEl, true, null, pluginPackage);
		}

		Element customEl = root.element("custom");

		if (customEl != null) {
			readLayoutTemplate(
				servletContextName, servletContext, layoutTemplateIds, customEl,
				false, null, pluginPackage);
		}

		return layoutTemplateIds;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LayoutTemplateLocalServiceImpl.class);

	private static Map<String, LayoutTemplate> _portalCustom =
		new LinkedHashMap<String, LayoutTemplate>();
	private static Map<String, LayoutTemplate> _portalStandard =
		new LinkedHashMap<String, LayoutTemplate>();

	private static Map<String, Map<String, LayoutTemplate>> _themes =
		new LinkedHashMap<String, Map<String, LayoutTemplate>>();

	private static Map<String, LayoutTemplate> _warCustom =
		new LinkedHashMap<String, LayoutTemplate>();
	private static Map<String, LayoutTemplate> _warStandard =
		new LinkedHashMap<String, LayoutTemplate>();

}