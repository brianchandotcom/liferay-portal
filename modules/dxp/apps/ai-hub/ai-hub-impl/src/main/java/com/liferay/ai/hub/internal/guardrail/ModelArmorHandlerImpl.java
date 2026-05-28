/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.guardrail;

import com.google.cloud.modelarmor.v1.DataItem;
import com.google.cloud.modelarmor.v1.DetectionConfidenceLevel;
import com.google.cloud.modelarmor.v1.FilterMatchState;
import com.google.cloud.modelarmor.v1.LocationName;
import com.google.cloud.modelarmor.v1.ModelArmorClient;
import com.google.cloud.modelarmor.v1.ModelArmorSettings;
import com.google.cloud.modelarmor.v1.SanitizationResult;
import com.google.cloud.modelarmor.v1.SanitizeModelResponseRequest;
import com.google.cloud.modelarmor.v1.SanitizeModelResponseResponse;
import com.google.cloud.modelarmor.v1.SanitizeUserPromptRequest;
import com.google.cloud.modelarmor.v1.SanitizeUserPromptResponse;
import com.google.cloud.modelarmor.v1.Template;
import com.google.cloud.modelarmor.v1.TemplateName;
import com.google.protobuf.FieldMask;

import com.liferay.ai.hub.configuration.VertexAIConfiguration;
import com.liferay.ai.hub.guardrail.ModelArmorHandler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
@Component(service = ModelArmorHandler.class)
public class ModelArmorHandlerImpl implements ModelArmorHandler {

	@Override
	public void createModelArmorTemplate(
			long companyId, String externalReferenceCode,
			Map<String, Object> properties)
		throws Exception {

		VertexAIConfiguration vertexAIConfiguration =
			_configurationProvider.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		ModelArmorTemplate modelArmorTemplate = _getModelArmorTemplate(
			externalReferenceCode, properties);

		ModelArmorClient modelArmorClient = _getModelArmorClient(
			GetterUtil.getString(properties.get("location")));

		modelArmorClient.createTemplate(
			LocationName.of(
				vertexAIConfiguration.projectId(),
				modelArmorTemplate.getLocation()),
			modelArmorTemplate.getTemplate(),
			modelArmorTemplate.getTemplateId());
	}

	@Override
	public void deleteModelArmorTemplate(
			long companyId, String externalReferenceCode, String location)
		throws Exception {

		VertexAIConfiguration vertexAIConfiguration =
			_configurationProvider.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		ModelArmorClient modelArmorClient = _getModelArmorClient(location);

		modelArmorClient.deleteTemplate(
			TemplateName.of(
				vertexAIConfiguration.projectId(), location,
				externalReferenceCode));
	}

	@Override
	public boolean hasModelResponseViolation(
			long companyId, String externalReferenceCode, String location,
			String text)
		throws Exception {

		return _hasViolation(
			(modelArmorClient, templateName) -> {
				SanitizeModelResponseResponse sanitizeModelResponseResponse =
					modelArmorClient.sanitizeModelResponse(
						SanitizeModelResponseRequest.newBuilder(
						).setModelResponseData(
							DataItem.newBuilder(
							).setText(
								text
							).build()
						).setName(
							templateName
						).build());

				return sanitizeModelResponseResponse.getSanitizationResult();
			},
			companyId, externalReferenceCode, location);
	}

	@Override
	public boolean hasUserPromptViolation(
			long companyId, String externalReferenceCode, String location,
			String text)
		throws Exception {

		return _hasViolation(
			(modelArmorClient, templateName) -> {
				SanitizeUserPromptResponse sanitizeUserPromptResponse =
					modelArmorClient.sanitizeUserPrompt(
						SanitizeUserPromptRequest.newBuilder(
						).setName(
							templateName
						).setUserPromptData(
							DataItem.newBuilder(
							).setText(
								text
							).build()
						).build());

				return sanitizeUserPromptResponse.getSanitizationResult();
			},
			companyId, externalReferenceCode, location);
	}

	@Override
	public void updateModelArmorTemplate(
			long companyId, String externalReferenceCode,
			Map<String, Object> properties)
		throws Exception {

		ModelArmorTemplate modelArmorTemplate = _getModelArmorTemplate(
			externalReferenceCode, properties);

		if ((modelArmorTemplate == null) ||
			Validator.isNull(modelArmorTemplate.getTemplateId())) {

			return;
		}

		VertexAIConfiguration vertexAIConfiguration =
			_configurationProvider.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		ModelArmorClient modelArmorClient = _getModelArmorClient(
			GetterUtil.getString(properties.get("location")));

		modelArmorClient.updateTemplate(
			Template.newBuilder(
				modelArmorTemplate.getTemplate()
			).setName(
				TemplateName.of(
					vertexAIConfiguration.projectId(),
					modelArmorTemplate.getLocation(),
					modelArmorTemplate.getTemplateId()
				).toString()
			).build(),
			FieldMask.newBuilder(
			).addPaths(
				"filter_config"
			).addPaths(
				"labels"
			).addPaths(
				"template_metadata"
			).build());
	}

	private ModelArmorClient _getModelArmorClient(String location) {
		return _modelArmorClients.computeIfAbsent(
			location,
			__ -> {
				try {
					String endpoint = "modelarmor";

					if (!Objects.equals(location, "global")) {
						endpoint += "." + location + ".rep";
					}

					return ModelArmorClient.create(
						ModelArmorSettings.newBuilder(
						).setEndpoint(
							endpoint + ".googleapis.com:443"
						).build());
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			});
	}

	private ModelArmorTemplate _getModelArmorTemplate(
		String externalReferenceCode, Map<String, Object> properties) {

		return ModelArmorTemplate.builder(
			externalReferenceCode
		).guardrailType(
			Objects.toString(properties.get("guardrailType"), "input")
		).location(
			GetterUtil.getString(properties.get("location"))
		).maliciousUriFilterEnabled(
			GetterUtil.getBoolean(properties.get("maliciousUriFilterEnabled"))
		).multiLanguageDetectionEnabled(
			GetterUtil.getBoolean(
				properties.get("multiLanguageDetectionEnabled"))
		).name(
			GetterUtil.getString(properties.get("name"))
		).piAndJailbreakConfidenceLevel(
			_toDetectionConfidenceLevel(
				properties.get("piAndJailbreakConfidenceLevel"))
		).piAndJailbreakFilterEnabled(
			GetterUtil.getBoolean(properties.get("piAndJailbreakFilterEnabled"))
		).raiDangerousDetectionConfidenceLevel(
			_toDetectionConfidenceLevel(properties.get("raiDangerousLevel"))
		).raiHarassmentDetectionConfidenceLevel(
			_toDetectionConfidenceLevel(properties.get("raiHarassmentLevel"))
		).raiHateSpeechDetectionConfidenceLevel(
			_toDetectionConfidenceLevel(properties.get("raiHateSpeechLevel"))
		).raiSexuallyExplicitDetectionConfidenceLevel(
			_toDetectionConfidenceLevel(
				properties.get("raiSexuallyExplicitLevel"))
		).sdpFilterEnabled(
			GetterUtil.getBoolean(properties.get("sdpFilterEnabled"))
		).build();
	}

	private boolean _hasViolation(
			BiFunction<ModelArmorClient, String, SanitizationResult> biFunction,
			long companyId, String externalReferenceCode, String location)
		throws Exception {

		VertexAIConfiguration vertexAIConfiguration =
			_configurationProvider.getCompanyConfiguration(
				VertexAIConfiguration.class, companyId);

		SanitizationResult sanitizationResult = biFunction.apply(
			_getModelArmorClient(location),
			TemplateName.of(
				vertexAIConfiguration.projectId(), location,
				externalReferenceCode
			).toString());

		return Objects.equals(
			sanitizationResult.getFilterMatchState(),
			FilterMatchState.MATCH_FOUND);
	}

	private DetectionConfidenceLevel _toDetectionConfidenceLevel(
		Object property) {

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				String.valueOf(property));

			String key = jsonObject.getString("key");

			if (Objects.equals(key, "high")) {
				return DetectionConfidenceLevel.HIGH;
			}
			else if (Objects.equals(key, "lowAndAbove")) {
				return DetectionConfidenceLevel.LOW_AND_ABOVE;
			}
			else if (Objects.equals(key, "mediumAndAbove")) {
				return DetectionConfidenceLevel.MEDIUM_AND_ABOVE;
			}

			return DetectionConfidenceLevel.
				DETECTION_CONFIDENCE_LEVEL_UNSPECIFIED;
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

	private final Map<String, ModelArmorClient> _modelArmorClients =
		new ConcurrentHashMap<>();

}