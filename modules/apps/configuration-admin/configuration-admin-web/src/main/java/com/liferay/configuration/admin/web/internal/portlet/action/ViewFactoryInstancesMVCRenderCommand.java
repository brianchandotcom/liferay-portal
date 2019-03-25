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

package com.liferay.configuration.admin.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.internal.constants.ConfigurationAdminWebKeys;
import com.liferay.configuration.admin.web.internal.display.ConfigurationCategoryMenuDisplay;
import com.liferay.configuration.admin.web.internal.display.ConfigurationEntry;
import com.liferay.configuration.admin.web.internal.display.ConfigurationModelConfigurationEntry;
import com.liferay.configuration.admin.web.internal.display.context.ConfigurationScopeDisplayContext;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.configuration.admin.web.internal.util.ConfigurationEntryRetriever;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelIterator;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelRetriever;
import com.liferay.configuration.admin.web.internal.util.ResourceBundleLoaderProvider;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=/view_factory_instances",
		"service.ranking:Integer=" + (Integer.MAX_VALUE - 1000)
	},
	service = MVCRenderCommand.class
)
public class ViewFactoryInstancesMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		String factoryPid = ParamUtil.getString(renderRequest, "factoryPid");

		MVCRenderCommand customRenderCommand = _renderCommands.get(factoryPid);

		if (customRenderCommand != null) {
			return customRenderCommand.render(renderRequest, renderResponse);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				themeDisplay.getLanguageId(),
				ExtendedObjectClassDefinition.Scope.SYSTEM, null);

		try {
			ConfigurationModel factoryConfigurationModel =
				configurationModels.get(factoryPid);

			ConfigurationScopeDisplayContext configurationScopeDisplayContext =
				new ConfigurationScopeDisplayContext(renderRequest);

			ConfigurationCategoryMenuDisplay configurationCategoryMenuDisplay =
				_configurationEntryRetriever.
					getConfigurationCategoryMenuDisplay(
						factoryConfigurationModel.getCategory(),
						themeDisplay.getLanguageId(),
						configurationScopeDisplayContext.getScope(),
						configurationScopeDisplayContext.getScopePK());

			renderRequest.setAttribute(
				ConfigurationAdminWebKeys.CONFIGURATION_CATEGORY_MENU_DISPLAY,
				configurationCategoryMenuDisplay);

			List<ConfigurationModel> factoryInstances =
				_configurationModelRetriever.getFactoryInstances(
					factoryConfigurationModel,
					ExtendedObjectClassDefinition.Scope.SYSTEM, null);

			ConfigurationEntry configurationEntry =
				new ConfigurationModelConfigurationEntry(
					factoryConfigurationModel, _portal.getLocale(renderRequest),
					_resourceBundleLoaderProvider);

			renderRequest.setAttribute(
				ConfigurationAdminWebKeys.CONFIGURATION_ENTRY,
				configurationEntry);

			renderRequest.setAttribute(
				ConfigurationAdminWebKeys.CONFIGURATION_MODEL_ITERATOR,
				new ConfigurationModelIterator(factoryInstances));

			renderRequest.setAttribute(
				ConfigurationAdminWebKeys.FACTORY_CONFIGURATION_MODEL,
				factoryConfigurationModel);

			renderRequest.setAttribute(
				ConfigurationAdminWebKeys.RESOURCE_BUNDLE_LOADER_PROVIDER,
				_resourceBundleLoaderProvider);

			return "/view_factory_instances.jsp";
		}
		catch (IOException ioe) {
			throw new PortletException(ioe);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS + ")(mvc.command.name=/view_factory_instances)(configurationPid=*))"
	)
	protected void addRenderCommand(
		MVCRenderCommand mvcRenderCommand, Map<String, Object> properties) {

		_renderCommands.put(
			(String)properties.get("configurationPid"), mvcRenderCommand);
	}

	protected void removeRenderCommand(
		MVCRenderCommand mvcRenderCommand, Map<String, Object> properties) {

		_renderCommands.remove(properties.get("configurationPid"));
	}

	@Reference
	private ConfigurationEntryRetriever _configurationEntryRetriever;

	@Reference
	private ConfigurationModelRetriever _configurationModelRetriever;

	@Reference
	private Portal _portal;

	private final Map<String, MVCRenderCommand> _renderCommands =
		new HashMap<>();

	@Reference
	private ResourceBundleLoaderProvider _resourceBundleLoaderProvider;

}