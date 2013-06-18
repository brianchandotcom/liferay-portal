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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.DummyWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.layoutconfiguration.util.velocity.InitColumnProcessor;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTemplateConstants;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.impl.LayoutTemplateImpl;
import com.liferay.portal.service.base.LayoutTemplateLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
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

	@Override
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

	@Override
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

	@Override
	public List<LayoutTemplate> getLayoutTemplates() {
		List<LayoutTemplate> customLayoutTemplates =
			new ArrayList<LayoutTemplate>(
				_portalCustom.size() + _warCustom.size());

		customLayoutTemplates.addAll(ListUtil.fromMapValues(_portalCustom));
		customLayoutTemplates.addAll(ListUtil.fromMapValues(_warCustom));

		return customLayoutTemplates;
	}

	@Override
	public List<LayoutTemplate> getLayoutTemplates(String themeId) {
		Map<String, LayoutTemplate> _themesCustom = _getThemesCustom(themeId);

		List<LayoutTemplate> customLayoutTemplates =
			new ArrayList<LayoutTemplate>(
				_portalCustom.size() + _warCustom.size() +
					_themesCustom.size());

		for (Map.Entry<String, LayoutTemplate> entry :
				_portalCustom.entrySet()) {

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

		for (Map.Entry<String, LayoutTemplate> entry : _warCustom.entrySet()) {
			String layoutTemplateId = entry.getKey();

			if (!_portalCustom.containsKey(layoutTemplateId) &&
				!_themesCustom.containsKey(layoutTemplateId)) {

				customLayoutTemplates.add(_warCustom.get(layoutTemplateId));
			}
		}

		for (Map.Entry<String, LayoutTemplate> entry :
				_themesCustom.entrySet()) {

			String layoutTemplateId = entry.getKey();

			if (!_portalCustom.containsKey(layoutTemplateId) &&
				!_warCustom.containsKey(layoutTemplateId)) {

				customLayoutTemplates.add(_themesCustom.get(layoutTemplateId));
			}
		}

		return customLayoutTemplates;
	}

	@Override
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

	@Override
	public List<LayoutTemplate> init(
		ServletContext servletContext, String[] xmls,
		PluginPackage pluginPackage) {

		return init(null, servletContext, xmls, pluginPackage);
	}

	@Override
	public List<LayoutTemplate> init(
		String servletContextName, ServletContext servletContext, String[] xmls,
		PluginPackage pluginPackage) {

		List<LayoutTemplate> layoutTemplates = new ArrayList<LayoutTemplate>();

		try {
			for (int i = 0; i < xmls.length; i++) {
				Set<LayoutTemplate> curLayoutTemplates = _readLayoutTemplates(
					servletContextName, servletContext, xmls[i], pluginPackage);

				for (LayoutTemplate layoutTemplate : curLayoutTemplates) {
					if (!layoutTemplates.contains(layoutTemplate)) {
						layoutTemplates.add(layoutTemplate);
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return layoutTemplates;
	}

	@Override
	public void readLayoutTemplate(
		String servletContextName, ServletContext servletContext,
		Set<LayoutTemplate> layoutTemplates, Element element, boolean standard,
		String themeId, PluginPackage pluginPackage) {

		Map<String, LayoutTemplate> installedLayoutTemplates = null;

		if (themeId != null) {
			if (standard) {
				installedLayoutTemplates = _getThemesStandard(themeId);
			}
			else {
				installedLayoutTemplates = _getThemesCustom(themeId);
			}
		}
		else if (servletContextName != null) {
			if (standard) {
				installedLayoutTemplates = _warStandard;
			}
			else {
				installedLayoutTemplates = _warCustom;
			}
		}
		else {
			if (standard) {
				installedLayoutTemplates = _portalStandard;
			}
			else {
				installedLayoutTemplates = _portalCustom;
			}
		}

		List<Element> layoutTemplateElements = element.elements(
			"layout-template");

		for (Element layoutTemplateElement : layoutTemplateElements) {
			String layoutTemplateId = layoutTemplateElement.attributeValue(
				"id");

			LayoutTemplate layoutTemplateModel = installedLayoutTemplates.get(
				layoutTemplateId);

			if (layoutTemplateModel == null) {
				layoutTemplateModel = new LayoutTemplateImpl(layoutTemplateId);

				installedLayoutTemplates.put(
					layoutTemplateId, layoutTemplateModel);
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
			layoutTemplateModel.setName(
				GetterUtil.getString(
					layoutTemplateElement.attributeValue("name"),
					layoutTemplateModel.getName()));
			layoutTemplateModel.setTemplatePath(
				GetterUtil.getString(
					layoutTemplateElement.elementText("template-path"),
					layoutTemplateModel.getTemplatePath()));
			layoutTemplateModel.setWapTemplatePath(
				GetterUtil.getString(
					layoutTemplateElement.elementText("wap-template-path"),
					layoutTemplateModel.getWapTemplatePath()));
			layoutTemplateModel.setThumbnailPath(
				GetterUtil.getString(
					layoutTemplateElement.elementText("thumbnail-path"),
					layoutTemplateModel.getThumbnailPath()));

			String content = null;

			try {
				content = HttpUtil.URLtoString(
					servletContext.getResource(
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

			Element rolesElement = layoutTemplateElement.element("roles");

			if (rolesElement != null) {
				List<Element> roleNameElements = rolesElement.elements(
					"role-name");

				for (Element roleNameElement : roleNameElements) {
					pluginSetting.addRole(roleNameElement.getText());
				}
			}

			layoutTemplateModel.setDefaultPluginSetting(pluginSetting);

			if (layoutTemplates != null) {
				layoutTemplates.add(layoutTemplateModel);
			}
		}
	}

	@Override
	public void uninstallLayoutTemplate(
		String layoutTemplateId, boolean standard) {

		String templateId = null;

		try {
			if (standard) {
				templateId =
					"null" + LayoutTemplateConstants.STANDARD_SEPARATOR +
						layoutTemplateId;

				TemplateResourceLoaderUtil.clearCache(
					TemplateConstants.LANG_TYPE_VM, templateId);

				_warStandard.remove(layoutTemplateId);
			}
			else {
				templateId =
					"null" + LayoutTemplateConstants.CUSTOM_SEPARATOR +
						layoutTemplateId;

				TemplateResourceLoaderUtil.clearCache(
					TemplateConstants.LANG_TYPE_VM, templateId);

				_warCustom.remove(layoutTemplateId);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to uninstall layout template " + layoutTemplateId, e);
		}
	}

	@Override
	public void uninstallLayoutTemplates(String themeId) {
		Map<String, LayoutTemplate> _themesStandard = _getThemesStandard(
			themeId);

		for (Map.Entry<String, LayoutTemplate> entry :
				_themesStandard.entrySet()) {

			LayoutTemplate layoutTemplate = entry.getValue();

			String templateId =
				themeId + LayoutTemplateConstants.STANDARD_SEPARATOR +
					layoutTemplate.getLayoutTemplateId();

			try {
				TemplateResourceLoaderUtil.clearCache(
					TemplateConstants.LANG_TYPE_VM, templateId);
			}
			catch (Exception e) {
				_log.error(
					"Unable to uninstall layout template " +
						layoutTemplate.getLayoutTemplateId(),
					e);
			}
		}

		_themesStandard.clear();

		Map<String, LayoutTemplate> _themesCustom = _getThemesCustom(themeId);

		for (Map.Entry<String, LayoutTemplate> entry :
				_themesCustom.entrySet()) {

			LayoutTemplate layoutTemplate = entry.getValue();

			String templateId =
				themeId + LayoutTemplateConstants.CUSTOM_SEPARATOR +
					layoutTemplate.getLayoutTemplateId();

			try {
				TemplateResourceLoaderUtil.clearCache(
					TemplateConstants.LANG_TYPE_VM, templateId);
			}
			catch (Exception e) {
				_log.error(
					"Unable to uninstall layout template " +
						layoutTemplate.getLayoutTemplateId(),
					e);
			}
		}

		_themesCustom.clear();
	}

	private List<String> _getColumns(
		String velocityTemplateId, String velocityTemplateContent) {

		try {
			InitColumnProcessor processor = new InitColumnProcessor();

			Template template = TemplateManagerUtil.getTemplate(
				TemplateConstants.LANG_TYPE_VM,
				new StringTemplateResource(
					velocityTemplateId, velocityTemplateContent),
				false);

			template.put("processor", processor);

			template.processTemplate(new DummyWriter());

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

	private Set<LayoutTemplate> _readLayoutTemplates(
			String servletContextName, ServletContext servletContext,
			String xml, PluginPackage pluginPackage)
		throws Exception {

		Set<LayoutTemplate> layoutTemplates = new HashSet<LayoutTemplate>();

		if (xml == null) {
			return layoutTemplates;
		}

		Document document = SAXReaderUtil.read(xml, true);

		Element rootElement = document.getRootElement();

		Element standardElement = rootElement.element("standard");

		if (standardElement != null) {
			readLayoutTemplate(
				servletContextName, servletContext, layoutTemplates,
				standardElement, true, null, pluginPackage);
		}

		Element customElement = rootElement.element("custom");

		if (customElement != null) {
			readLayoutTemplate(
				servletContextName, servletContext, layoutTemplates,
				customElement, false, null, pluginPackage);
		}

		return layoutTemplates;
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