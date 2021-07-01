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

package com.liferay.remote.web.component.admin.web.internal.layouttpl;

import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.service.LayoutTemplateLocalService;
import com.liferay.portal.plugin.PluginPackageUtil;

import java.net.URL;

import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(service = {})
public class LayoutTemplateComponent {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Bundle bundle = bundleContext.getBundle();

		URL url = bundle.getEntry("/META-INF/liferay-layout-templates.xml");

		try {
			String layoutTemplateXml = StreamUtil.toString(url.openStream());

			Properties props = new Properties();

			props.put("module-group-id", "remote-web-components");

			PluginPackage pluginPackage =
				PluginPackageUtil.readPluginPackageProperties(
					"remote-web-component-layouttpl", props);

			pluginPackage.setContext(_servletContext.getServletContextName());

			_layoutTemplates = _layoutTemplateLocalService.init(
				_servletContext, new String[] {layoutTemplateXml},
				pluginPackage);

			if (_log.isInfoEnabled()) {
				if (_layoutTemplates.size() == 1) {
					_log.info(
						String.format(
							"1 layout template for %s is available for use",
							_servletContext.getServletContextName()));
				}
				else {
					_log.info(
						String.format(
							"%s layout templates for %s are available for use",
							_layoutTemplates.size(),
							_servletContext.getServletContextName()));
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				String.format(
					"Error initializing layout templates for %s",
					_servletContext.getServletContextName()),
				exception);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_layoutTemplates == null) {
			return;
		}

		for (LayoutTemplate layoutTemplate : _layoutTemplates) {
			try {
				_layoutTemplateLocalService.uninstallLayoutTemplate(
					layoutTemplate.getLayoutTemplateId(),
					layoutTemplate.isStandard());
			}
			catch (Exception exception) {
				_log.error(
					String.format(
						"Could not process layoutTemplate %s", layoutTemplate),
					exception);
			}
		}

		if (_log.isInfoEnabled()) {
			if (_layoutTemplates.size() == 1) {
				_log.info(
					String.format(
						"1 layout template for {} was unregistered",
						_servletContext.getServletContextName()));
			}
			else {
				_log.info(
					String.format(
						"%s layout templates for %s were unregistered",
						_layoutTemplates.size(),
						_servletContext.getServletContextName()));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutTemplateComponent.class);

	@Reference
	private LayoutTemplateLocalService _layoutTemplateLocalService;

	private volatile List<LayoutTemplate> _layoutTemplates;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.remote.web.component.admin.web)"
	)
	private ServletContext _servletContext;

}