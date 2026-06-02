/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.object.action.executor;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.object.action.executor.BaseObjectActionExecutor;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Leite
 */
@Component(service = ObjectActionExecutor.class)
public class SyncEnvironmentUrlsObjectActionExecutorImpl
	extends BaseObjectActionExecutor implements ObjectDefinitionScoped {

	@Override
	public List<String> getAllowedObjectDefinitionNames() {
		return List.of("AIHubConfiguration");
	}

	@Override
	public String getKey() {
		return "sync-environment-urls";
	}

	@Override
	protected void doExecute(
			long companyId, long objectActionId,
			UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			payloadJSONObject.getLong("classPK"));

		String environmentUrls = MapUtil.getString(values, "environmentUrls");

		if (Validator.isNull(environmentUrls)) {
			return;
		}

		_updateOAuth2Application(
			MapUtil.getLong(
				values, "r_accountToAIHubConfigurations_accountEntryId"),
			companyId, environmentUrls);
		_updatePortalCORSConfiguration(
			companyId, payloadJSONObject.getLong("objectDefinitionId"));
	}

	private void _updateOAuth2Application(
		long accountEntryId, long companyId, String environmentUrls) {

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					accountEntryId + "-ai-hub-oauth2-application", companyId);

		if (oAuth2Application == null) {
			return;
		}

		String[] environmentUrlsArray = StringUtil.split(
			environmentUrls, CharPool.SPACE);

		oAuth2Application.setHomePageURL(environmentUrlsArray[0]);

		_oAuth2ApplicationLocalService.updateOAuth2Application(
			oAuth2Application);
	}

	private void _updatePortalCORSConfiguration(
			long companyId, long objectDefinitionId)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(&(service.factoryPid=", _PORTAL_CORS_CONFIGURATION_PID,
				".scoped)(companyId=", companyId, "))"));

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		List<String> environmentUrlsList = TransformUtil.transform(
			_objectEntryLocalService.getObjectEntries(
				0, objectDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			objectEntry -> {
				String environmentUrls = MapUtil.getString(
					objectEntry.getValues(), "environmentUrls");

				if (Validator.isNull(environmentUrls)) {
					return null;
				}

				return environmentUrls;
			});

		Configuration configuration = configurations[0];

		Dictionary<String, Object> properties = configuration.getProperties();

		properties.put(
			"headers",
			TransformUtil.transform(
				GetterUtil.getStringValues(properties.get("headers")),
				header -> {
					if (!StringUtil.startsWith(
							header, "Access-Control-Allow-Origin:")) {

						return header;
					}

					return "Access-Control-Allow-Origin: " +
						StringUtil.merge(environmentUrlsList, StringPool.SPACE);
				},
				String.class));

		configuration.update(properties);
	}

	private static final String _PORTAL_CORS_CONFIGURATION_PID =
		"com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration";

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}