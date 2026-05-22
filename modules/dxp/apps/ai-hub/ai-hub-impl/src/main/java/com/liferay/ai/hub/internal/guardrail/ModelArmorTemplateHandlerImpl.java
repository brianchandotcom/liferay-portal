/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.guardrail;

import com.google.cloud.modelarmor.v1.DetectionConfidenceLevel;
import com.google.cloud.modelarmor.v1.RaiFilterType;

import com.liferay.ai.hub.configuration.VertexAIConfiguration;
import com.liferay.ai.hub.guardrail.ModelArmorTemplateHandler;
import com.liferay.ai.hub.internal.guardrail.util.ModelArmorClientUtil;
import com.liferay.ai.hub.internal.guardrail.util.ModelArmorTemplateUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author João Victor Alves
 */
@Component(service = ModelArmorTemplateHandler.class)
public class ModelArmorTemplateHandlerImpl
	implements ModelArmorTemplateHandler {

	@Override
	public void createModelArmorTemplate(
			long companyId, String externalReferenceCode,
			Map<String, Object> properties)
		throws Exception {

		VertexAIConfiguration vertexAIConfiguration =
			_configurationProvider.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		ModelArmorTemplateUtil.createModelArmorTemplate(
			vertexAIConfiguration.projectId(),
			ModelArmorClientUtil.getModelArmorClient(
				companyId, GetterUtil.getString(properties.get("location"))),
			_getModelArmorTemplateConfig(externalReferenceCode, properties));
	}

	@Override
	public void deleteModelArmorTemplate(
			long companyId, String externalReferenceCode, String location)
		throws Exception {

		VertexAIConfiguration vertexAIConfiguration =
			_configurationProvider.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		ModelArmorTemplateUtil.deleteModelArmorTemplate(
			vertexAIConfiguration.projectId(),
			ModelArmorClientUtil.getModelArmorClient(companyId, location),
			location, externalReferenceCode);
	}

	@Override
	public void updateModelArmorTemplate(
			long companyId, String externalReferenceCode,
			Map<String, Object> properties)
		throws Exception {

		VertexAIConfiguration vertexAIConfiguration =
			_configurationProvider.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		ModelArmorTemplateUtil.updateModelArmorTemplate(
			vertexAIConfiguration.projectId(),
			ModelArmorClientUtil.getModelArmorClient(
				companyId, GetterUtil.getString(properties.get("location"))),
			_getModelArmorTemplateConfig(externalReferenceCode, properties));
	}

	private ModelArmorTemplateConfig _getModelArmorTemplateConfig(
		String externalReferenceCode, Map<String, Object> properties) {

		Map<RaiFilterType, DetectionConfidenceLevel> raiFilters = new EnumMap<>(
			RaiFilterType.class);

		_putRaiFilter(
			raiFilters, RaiFilterType.DANGEROUS,
			Objects.toString(properties.get("raiDangerousLevel"), null));
		_putRaiFilter(
			raiFilters, RaiFilterType.HARASSMENT,
			Objects.toString(properties.get("raiHarassmentLevel"), null));
		_putRaiFilter(
			raiFilters, RaiFilterType.HATE_SPEECH,
			Objects.toString(properties.get("raiHateSpeechLevel"), null));
		_putRaiFilter(
			raiFilters, RaiFilterType.SEXUALLY_EXPLICIT,
			Objects.toString(properties.get("raiSexuallyExplicitLevel"), null));

		return ModelArmorTemplateConfig.builder(
			externalReferenceCode
		).guardrailType(
			Objects.toString(properties.get("guardrailType"), null)
		).location(
			GetterUtil.getString(properties.get("location"))
		).maliciousUriFilterEnabled(
			GetterUtil.getBoolean(properties.get("maliciousUriFilterEnabled"))
		).multiLanguageDetectionEnabled(
			GetterUtil.getBoolean(
				properties.get("multiLanguageDetectionEnabled"))
		).name(
			GetterUtil.getString(properties.get("name"))
		).piAndJailbreakFilterEnabled(
			GetterUtil.getBoolean(properties.get("piAndJailbreakFilterEnabled"))
		).piAndJailbreakConfidenceLevel(
			_toConfidenceLevel(
				Objects.toString(
					properties.get("piAndJailbreakConfidenceLevel"), null))
		).raiFilters(
			raiFilters
		).sdpFilterEnabled(
			GetterUtil.getBoolean(properties.get("sdpFilterEnabled"))
		).build();
	}

	private void _putRaiFilter(
		Map<RaiFilterType, DetectionConfidenceLevel> raiFilters,
		RaiFilterType raiFilterType, Object levelProperty) {

		if (Objects.equals(
				GetterUtil.getString(levelProperty, "none"), "none")) {

			return;
		}

		raiFilters.put(
			raiFilterType,
			_toConfidenceLevel(GetterUtil.getString(levelProperty, "none")));
	}

	private DetectionConfidenceLevel _toConfidenceLevel(String key) {
		try {
			return DetectionConfidenceLevel.valueOf(_toEnumKey(key));
		}
		catch (IllegalArgumentException illegalArgumentException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unknown confidence level " + key +
						", falling back to MEDIUM_AND_ABOVE",
					illegalArgumentException);
			}

			return DetectionConfidenceLevel.MEDIUM_AND_ABOVE;
		}
	}

	private String _toEnumKey(String key) {
		if (Validator.isNull(key)) {
			return StringPool.BLANK;
		}

		return StringUtil.toUpperCase(
			CamelCaseUtil.fromCamelCase(key, CharPool.UNDERLINE));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModelArmorTemplateHandlerImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}