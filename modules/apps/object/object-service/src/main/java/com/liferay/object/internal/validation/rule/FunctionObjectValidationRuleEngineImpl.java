/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.validation.rule;

import com.liferay.object.internal.configuration.FunctionObjectValidationRuleEngineImplConfiguration;
import com.liferay.object.scope.CompanyScoped;
import com.liferay.object.scope.ObjectDefinitionScoped;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.osgi.util.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mateus Santana
 */
@Component(
	configurationPid = "com.liferay.object.internal.configuration.FunctionObjectValidationRuleEngineImplConfiguration",
	factory = "com.liferay.object.internal.validation.rule.FunctionObjectValidationRuleEngineImpl",
	service = ObjectValidationRuleEngine.class
)
public class FunctionObjectValidationRuleEngineImpl
	implements CompanyScoped, ObjectDefinitionScoped,
			   ObjectValidationRuleEngine {

	@Override
	public Map<String, Object> execute(
		Map<String, Object> inputObjects, String script) {

		Map<String, Object> entryDTO = (Map<String, Object>)inputObjects.get(
			"entryDTO");

		if (entryDTO == null) {
			Map<String, Object> baseModel =
				(Map<String, Object>)inputObjects.get("baseModel");

			return getResults(
				baseModel, GetterUtil.getLong(baseModel.get("creator")));
		}

		Map<String, Object> creatorMap = (Map<String, Object>)entryDTO.get(
			"creator");

		return getResults(
			HashMapBuilder.<String, Object>put(
				"creator", creatorMap
			).put(
				"dateCreated", entryDTO.get("dateCreated")
			).put(
				"dateModified", entryDTO.get("dateModified")
			).put(
				"externalReferenceCode", entryDTO.get("externalReferenceCode")
			).put(
				"status", entryDTO.get("status")
			).putAll(
				(Map<String, Object>)entryDTO.get("properties")
			).build(),
			GetterUtil.getLong(creatorMap.get("id")));
	}

	@Override
	public long getAllowedCompanyId() {
		return _companyId;
	}

	@Override
	public List<String> getAllowedObjectDefinitionNames() {
		return _allowedObjectDefinitionNames;
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public String getLabel(Locale locale) {
		return _name;
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		_allowedObjectDefinitionNames = StringUtil.asList(
			properties.get("allowedObjectDefinitionNames"));
		_companyId = ConfigurationFactoryUtil.getCompanyId(
			_companyLocalService, properties);
		_functionObjectValidationRuleEngineImplConfiguration =
			ConfigurableUtil.createConfigurable(
				FunctionObjectValidationRuleEngineImplConfiguration.class,
				properties);
		_key = GetterUtil.getString(properties.get("object.validation.rule"));
		_name = GetterUtil.getString(properties.get("name"));
	}

	protected Map<String, Object> getResults(
		Map<String, Object> inputObjects, long userId) {

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				new String(
					_portalCatapult.launch(
						_companyId, Http.Method.POST,
						_functionObjectValidationRuleEngineImplConfiguration.
							oAuth2ApplicationExternalReferenceCode(),
						_jsonFactory.createJSONObject(inputObjects),
						_functionObjectValidationRuleEngineImplConfiguration.
							resourcePath(),
						userId
					).get()));

			return HashMapBuilder.<String, Object>put(
				"validationCriteriaMet", jsonObject.get("validationCriteriaMet")
			).build();
		}
		catch (Exception exception) {
			_log.error(exception);

			return HashMapBuilder.<String, Object>put(
				"validationCriteriaMet", false
			).build();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FunctionObjectValidationRuleEngineImpl.class);

	private List<String> _allowedObjectDefinitionNames;
	private long _companyId;

	@Reference
	private CompanyLocalService _companyLocalService;

	private FunctionObjectValidationRuleEngineImplConfiguration
		_functionObjectValidationRuleEngineImplConfiguration;

	@Reference
	private JSONFactory _jsonFactory;

	private String _key;
	private String _name;

	@Reference
	private PortalCatapult _portalCatapult;

}