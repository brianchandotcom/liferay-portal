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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.nio.charset.Charset;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MutableRenderParameters;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

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

	protected void checkResponse(long companyId, HttpResponse httpResponse)
		throws Exception {

		HttpEntity httpEntity = httpResponse.getEntity();

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
			EntityUtils.toString(httpEntity, Charset.defaultCharset()));

		String message = responseJSONObject.getString("message");

		if (message.equals("INVALID_TOKEN")) {
			clearConfiguration(companyId);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Deleted analytics configuration for company " + companyId +
						" due to invalid token");
			}
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to access Analytics Cloud");
			}

			throw new PortalException("Invalid token");
		}
	}

	protected void clearConfiguration(long companyId) throws Exception {
		removeChannelId(
			PrefsPropsUtil.getStringArray(
				companyId, "liferayAnalyticsGroupIds", StringPool.COMMA));

		removeCompanyPreferences(companyId);

		configurationProvider.deleteCompanyConfiguration(
			AnalyticsConfiguration.class, companyId);
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

			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (Objects.equals(cmd, "update_synced_contacts_fields")) {
				boolean exit = ParamUtil.getBoolean(actionRequest, "exit");

				if (exit) {
					SessionErrors.add(actionRequest, "unsavedContactsFields");

					hideDefaultErrorMessage(actionRequest);
				}

				sendRedirect(
					actionRequest, actionResponse,
					ParamUtil.getString(actionRequest, "redirect"));
			}

			if (SessionErrors.contains(
					actionRequest, "unableToNotifyAnalyticsCloud")) {

				hideDefaultErrorMessage(actionRequest);

				sendRedirect(
					actionRequest, actionResponse,
					ParamUtil.getString(actionRequest, "redirect"));
			}
		}
		catch (PrincipalException principalException) {
			_log.error(principalException, principalException);

			SessionErrors.add(actionRequest, principalException.getClass());

			MutableRenderParameters mutableRenderParameters =
				actionResponse.getRenderParameters();

			mutableRenderParameters.setValue("mvcPath", "/error.jsp");
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw exception;
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

		if (settingsDescriptor == null) {
			return configurationProperties;
		}

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

	protected void removeChannelId(String[] groupIds) {
		for (String groupId : groupIds) {
			Group group = groupLocalService.fetchGroup(
				GetterUtil.getLong(groupId));

			if (group == null) {
				continue;
			}

			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			typeSettingsUnicodeProperties.remove("analyticsChannelId");

			group.setTypeSettingsProperties(typeSettingsUnicodeProperties);

			groupLocalService.updateGroup(group);
		}
	}

	protected void removeCompanyPreferences(long companyId) throws Exception {
		companyService.removePreferences(
			companyId,
			new String[] {
				"liferayAnalyticsConnectionType",
				"liferayAnalyticsDataSourceId", "liferayAnalyticsEndpointURL",
				"liferayAnalyticsFaroBackendSecuritySignature",
				"liferayAnalyticsFaroBackendURL", "liferayAnalyticsGroupIds",
				"liferayAnalyticsURL"
			});
	}

	protected void saveCompanyConfiguration(
			ActionRequest actionRequest, ThemeDisplay themeDisplay)
		throws Exception {

		Dictionary<String, Object> configurationProperties =
			getConfigurationProperties(
				getConfigurationPid(), themeDisplay.getCompanyId());

		updateConfigurationProperties(actionRequest, configurationProperties);

		try {
			AnalyticsConfiguration analyticsConfiguration =
				configurationProvider.getCompanyConfiguration(
					AnalyticsConfiguration.class, themeDisplay.getCompanyId());

			if (Validator.isBlank(
					analyticsConfiguration.liferayAnalyticsDataSourceId()) &&
				Validator.isBlank(
					analyticsConfiguration.liferayAnalyticsEndpointURL()) &&
				Validator.isBlank(analyticsConfiguration.token())) {

				return;
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Analytics configuration not found", exception);
			}

			return;
		}

		String token = (String)configurationProperties.get("token");

		if ((token != null) && !token.isEmpty()) {
			configurationProvider.saveCompanyConfiguration(
				AnalyticsConfiguration.class, themeDisplay.getCompanyId(),
				configurationProperties);
		}
	}

	protected abstract void updateConfigurationProperties(
			ActionRequest actionRequest,
			Dictionary<String, Object> configurationProperties)
		throws Exception;

	@Reference
	protected CompanyService companyService;

	@Reference
	protected ConfigurationProvider configurationProvider;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected SettingsFactory settingsFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAnalyticsMVCActionCommand.class);

}