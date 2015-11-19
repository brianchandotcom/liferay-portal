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

package com.liferay.marketplace.app.manager.web.portlet;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.marketplace.app.manager.web.constants.MarketplaceAppManagerPortletKeys;
import com.liferay.marketplace.bundle.BundleManagerUtil;
import com.liferay.marketplace.exception.FileExtensionException;
import com.liferay.marketplace.service.AppService;
import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.PluginSettingLocalService;
import com.liferay.portal.service.PluginSettingService;
import com.liferay.portal.service.PortletService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 * @author Joan Kim
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=marketplace-portlet",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/icon.png",
		"com.liferay.portlet.preferences-owned-by-group=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.description=", "javax.portlet.display-name=App Manager",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + MarketplaceAppManagerPortletKeys.MARKETPLACE_APP_MANAGER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {javax.portlet.Portlet.class}
)
public class MarketplaceAppManagerPortlet extends MVCPortlet {

	public void activateBundles(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] bundleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "bundleIds"), 0L);

		List<Bundle> bundles = BundleManagerUtil.getInstalledBundles();

		for (Bundle bundle : bundles) {
			if (ArrayUtil.contains(bundleIds, bundle.getBundleId())) {
				bundle.start();
			}
		}
	}

	public void deactivateBundles(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] bundleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "bundleIds"), 0L);

		List<Bundle> bundles = BundleManagerUtil.getInstalledBundles();

		for (Bundle bundle : bundles) {
			if (ArrayUtil.contains(bundleIds, bundle.getBundleId())) {
				bundle.stop();
			}
		}
	}

	public void installApp(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		String installMethod = ParamUtil.getString(
			uploadPortletRequest, "installMethod");

		if (installMethod.equals("local")) {
			String fileName = GetterUtil.getString(
				uploadPortletRequest.getFileName("file"));

			File file = uploadPortletRequest.getFile("file");

			byte[] bytes = FileUtil.getBytes(file);

			if (ArrayUtil.isEmpty(bytes)) {
				SessionErrors.add(
					actionRequest, UploadException.class.getName());
			}
			else if (!fileName.endsWith(".jar") &&
					 !fileName.endsWith(".lpkg") &&
					 !fileName.endsWith(".war")) {

				throw new FileExtensionException();
			}
			else {
				String deployDir = PrefsPropsUtil.getString(
					PropsKeys.AUTO_DEPLOY_DEPLOY_DIR);

				FileUtil.copyFile(
					file.toString(), deployDir + StringPool.SLASH + fileName);

				SessionMessages.add(actionRequest, "pluginUploaded");
			}
		}
		else {
			try {
				String url = ParamUtil.getString(uploadPortletRequest, "url");

				URL urlObj = new URL(url);

				String host = urlObj.getHost();

				if (host.endsWith("sf.net") ||
					host.endsWith("sourceforge.net")) {

					doInstallSourceForgeApp(
						urlObj.getPath(), uploadPortletRequest, actionRequest);
				}
				else {
					doInstallRemoteApp(
						url, uploadPortletRequest, actionRequest, true);
				}
			}
			catch (MalformedURLException murle) {
				SessionErrors.add(actionRequest, "invalidUrl", murle);
			}
		}

		String redirect = ParamUtil.getString(uploadPortletRequest, "redirect");

		actionResponse.sendRedirect(redirect);
	}

	public void uninstallApp(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long remoteAppId = ParamUtil.getLong(actionRequest, "remoteAppId");

		if (remoteAppId > 0) {
			_appService.uninstallApp(remoteAppId);
		}
		else {
			String[] contextNames = StringUtil.split(
				ParamUtil.getString(actionRequest, "contextNames"));

			for (String contextName : contextNames) {
				DeployManagerUtil.undeploy(contextName);
			}
		}

		SessionMessages.add(actionRequest, "triggeredPortletUndeploy");
	}

	public void uninstallBundles(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] bundleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "bundleIds"), 0L);

		List<Bundle> bundles = BundleManagerUtil.getInstalledBundles();

		for (Bundle bundle : bundles) {
			if (ArrayUtil.contains(bundleIds, bundle.getBundleId())) {
				bundle.uninstall();
			}
		}
	}

	public void updatePluginSetting(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String pluginId = ParamUtil.getString(actionRequest, "pluginId");
		String pluginType = ParamUtil.getString(actionRequest, "pluginType");

		String[] roles = StringUtil.split(
			ParamUtil.getString(actionRequest, "roles"), CharPool.NEW_LINE);

		Arrays.sort(roles);

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		if (pluginType.equals(Plugin.TYPE_PORTLET)) {
			_portletService.updatePortlet(
				themeDisplay.getCompanyId(), pluginId, StringPool.BLANK,
				active);
		}
		else {
			if (roles.length == 0) {
				PluginSetting pluginSetting =
					_pluginSettingLocalService.getPluginSetting(
						themeDisplay.getCompanyId(), pluginId, pluginType);

				roles = StringUtil.split(pluginSetting.getRoles());
			}

			_pluginSettingService.updatePluginSetting(
				themeDisplay.getCompanyId(), pluginId, pluginType,
				StringUtil.merge(roles), active);
		}
	}

	public void updatePluginSettings(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] contextNames = StringUtil.split(
			ParamUtil.getString(actionRequest, "contextNames"));

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		for (String contextName : contextNames) {
			ServletContext servletContext = ServletContextPool.get(contextName);

			List<LayoutTemplate> layoutTemplates =
				(List<LayoutTemplate>)servletContext.getAttribute(
					WebKeys.PLUGIN_LAYOUT_TEMPLATES);

			if (layoutTemplates != null) {
				for (LayoutTemplate layoutTemplate : layoutTemplates) {
					PluginSetting pluginSetting =
						_pluginSettingLocalService.getPluginSetting(
							themeDisplay.getCompanyId(),
							layoutTemplate.getLayoutTemplateId(),
							Plugin.TYPE_LAYOUT_TEMPLATE);

					_pluginSettingService.updatePluginSetting(
						themeDisplay.getCompanyId(),
						layoutTemplate.getLayoutTemplateId(),
						Plugin.TYPE_LAYOUT_TEMPLATE, pluginSetting.getRoles(),
						active);
				}
			}

			List<Portlet> portlets = (List<Portlet>)servletContext.getAttribute(
				WebKeys.PLUGIN_PORTLETS);

			if (portlets != null) {
				for (Portlet portlet : portlets) {
					_portletService.updatePortlet(
						themeDisplay.getCompanyId(), portlet.getPortletId(),
						StringPool.BLANK, active);
				}
			}

			List<Theme> themes = (List<Theme>)servletContext.getAttribute(
				WebKeys.PLUGIN_THEMES);

			if (themes != null) {
				for (Theme theme : themes) {
					PluginSetting pluginSetting =
						_pluginSettingLocalService.getPluginSetting(
							themeDisplay.getCompanyId(), theme.getThemeId(),
							Plugin.TYPE_THEME);

					_pluginSettingService.updatePluginSetting(
						themeDisplay.getCompanyId(), theme.getThemeId(),
						Plugin.TYPE_THEME, pluginSetting.getRoles(), active);
				}
			}
		}
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			ApplicationListWebKeys.PANEL_APP_REGISTRY, _panelAppRegistry);

		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		renderRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_HELPER, panelCategoryHelper);

		renderRequest.setAttribute(
			ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY,
			_panelCategoryRegistry);

		super.doDispatch(renderRequest, renderResponse);
	}

	protected int doInstallRemoteApp(
			String url, UploadPortletRequest uploadPortletRequest,
			ActionRequest actionRequest, boolean failOnError)
		throws Exception {

		int responseCode = HttpServletResponse.SC_OK;

		String deploymentContext = ParamUtil.getString(
			uploadPortletRequest, "deploymentContext");

		try {
			String fileName = null;

			if (Validator.isNotNull(deploymentContext)) {
				fileName = _DEPLOY_TO_PREFIX + deploymentContext + ".war";
			}
			else {
				fileName = url.substring(url.lastIndexOf(CharPool.SLASH) + 1);

				int pos = fileName.lastIndexOf(CharPool.PERIOD);

				if (pos != -1) {
					deploymentContext = fileName.substring(0, pos);
				}
			}

			Http.Options options = new Http.Options();

			options.setFollowRedirects(false);
			options.setLocation(url);
			options.setPost(false);

			byte[] bytes = HttpUtil.URLtoByteArray(options);

			Http.Response response = options.getResponse();

			responseCode = response.getResponseCode();

			if ((responseCode == HttpServletResponse.SC_OK) &&
				(bytes.length > 0)) {

				String deployDir = PrefsPropsUtil.getString(
					PropsKeys.AUTO_DEPLOY_DEPLOY_DIR);

				String destination = deployDir + StringPool.SLASH + fileName;

				File destinationFile = new File(destination);

				FileUtil.write(destinationFile, bytes);

				SessionMessages.add(actionRequest, "pluginDownloaded");
			}
			else {
				if (failOnError) {
					SessionErrors.add(
						actionRequest, UploadException.class.getName());
				}

				responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			}
		}
		catch (MalformedURLException murle) {
			SessionErrors.add(actionRequest, "invalidUrl", murle);
		}
		catch (IOException ioe) {
			SessionErrors.add(actionRequest, "errorConnectingToUrl", ioe);
		}

		return responseCode;
	}

	protected void doInstallSourceForgeApp(
			String path, UploadPortletRequest uploadPortletRequest,
			ActionRequest actionRequest)
		throws Exception {

		String[] sourceForgeMirrors = PropsUtil.getArray(
			PropsKeys.SOURCE_FORGE_MIRRORS);

		for (int i = 0; i < sourceForgeMirrors.length; i++) {
			try {
				String url = sourceForgeMirrors[i] + path;

				boolean failOnError = false;

				if ((i + 1) == sourceForgeMirrors.length) {
					failOnError = true;
				}

				int responseCode = doInstallRemoteApp(
					url, uploadPortletRequest, actionRequest, failOnError);

				if (responseCode == HttpServletResponse.SC_OK) {
					return;
				}
			}
			catch (MalformedURLException murle) {
				SessionErrors.add(actionRequest, "invalidUrl", murle);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setAppService(AppService appService) {
		_appService = appService;
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	@Reference(unbind = "-")
	protected void setPanelCategoryRegistry(
		PanelCategoryRegistry panelCategoryRegistry) {

		_panelCategoryRegistry = panelCategoryRegistry;
	}

	@Reference(unbind = "-")
	protected void setPluginSettingLocalService(
		PluginSettingLocalService pluginSettingLocalService) {

		_pluginSettingLocalService = pluginSettingLocalService;
	}

	@Reference(unbind = "-")
	protected void setPluginSettingService(
		PluginSettingService pluginSettingService) {

		_pluginSettingService = pluginSettingService;
	}

	@Reference(unbind = "-")
	protected void setPortletService(PortletService portletService) {
		_portletService = portletService;
	}

	private static final String _DEPLOY_TO_PREFIX = "DEPLOY_TO__";

	private AppService _appService;
	private PanelAppRegistry _panelAppRegistry;
	private PanelCategoryRegistry _panelCategoryRegistry;
	private PluginSettingLocalService _pluginSettingLocalService;
	private PluginSettingService _pluginSettingService;
	private PortletService _portletService;

}