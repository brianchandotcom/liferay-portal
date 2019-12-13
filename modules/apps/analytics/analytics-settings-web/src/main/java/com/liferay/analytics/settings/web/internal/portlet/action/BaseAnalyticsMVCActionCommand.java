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

package com.liferay.analytics.settings.web.internal.portlet.action;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MutableRenderParameters;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseAnalyticsMVCActionCommand
	extends BaseMVCActionCommand {

	protected void checkPermissions(ThemeDisplay themeDisplay)
		throws PrincipalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(themeDisplay.getCompanyId())) {
			throw new PrincipalException();
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			checkPermissions(themeDisplay);

			saveCompanyConfiguration(actionRequest, themeDisplay);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(actionRequest, pe.getClass());

			MutableRenderParameters mutableRenderParameters =
				actionResponse.getRenderParameters();

			mutableRenderParameters.setValue("mvcPath", "/error.jsp");
		}
	}

	protected String getConfigurationPid() {
		Class<?> clazz = AnalyticsConfiguration.class;

		Meta.OCD ocd = clazz.getAnnotation(Meta.OCD.class);

		return ocd.id();
	}

	protected Dictionary<String, Object> getConfigurationProperties(
			String pid, long scopePK)
		throws Exception {

		Dictionary<String, Object> configurationProperties = new Hashtable<>();

		Settings settings = settingsFactory.getSettings(
			new CompanyServiceSettingsLocator(scopePK, pid));

		SettingsDescriptor settingsDescriptor =
			settingsFactory.getSettingsDescriptor(pid);

		Set<String> multiValuedKeys = settingsDescriptor.getMultiValuedKeys();

		for (String multiValuedKey : multiValuedKeys) {
			configurationProperties.put(
				multiValuedKey,
				settings.getValues(multiValuedKey, new String[0]));
		}

		Set<String> keys = settingsDescriptor.getAllKeys();

		keys.removeAll(multiValuedKeys);

		for (String key : keys) {
			configurationProperties.put(
				key, settings.getValue(key, StringPool.BLANK));
		}

		return configurationProperties;
	}

	protected void saveCompanyConfiguration(
			ActionRequest actionRequest, ThemeDisplay themeDisplay)
		throws Exception {

		Dictionary<String, Object> configurationProperties =
			getConfigurationProperties(
				getConfigurationPid(), themeDisplay.getCompanyId());

		updateConfigurationProperties(actionRequest, configurationProperties);

		configurationProvider.saveCompanyConfiguration(
			AnalyticsConfiguration.class, themeDisplay.getCompanyId(),
			configurationProperties);
	}

	protected abstract void updateConfigurationProperties(
			ActionRequest actionRequest,
			Dictionary<String, Object> configurationProperties)
		throws Exception;

	@Reference
	protected ConfigurationProvider configurationProvider;

	@Reference
	protected SettingsFactory settingsFactory;

}