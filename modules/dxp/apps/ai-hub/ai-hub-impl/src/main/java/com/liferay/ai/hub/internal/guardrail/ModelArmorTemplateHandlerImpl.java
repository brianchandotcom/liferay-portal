/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.guardrail;

import com.google.cloud.modelarmor.v1.DetectionConfidenceLevel;
import com.google.cloud.modelarmor.v1.LocationName;
import com.google.cloud.modelarmor.v1.ModelArmorClient;
import com.google.cloud.modelarmor.v1.ModelArmorSettings;
import com.google.cloud.modelarmor.v1.RaiFilterType;
import com.google.cloud.modelarmor.v1.Template;
import com.google.cloud.modelarmor.v1.TemplateName;
import com.google.protobuf.FieldMask;

import com.liferay.ai.hub.guardrail.ModelArmorTemplateHandler;
import com.liferay.ai.hub.internal.configuration.VertexAIConfiguration;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
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
				StringBundler.concat(
					"projects/", vertexAIConfiguration.projectId(),
					"/locations/", modelArmorTemplate.getLocation(),
					"/templates/", modelArmorTemplate.getTemplateId())
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

		return ModelArmorTemplate.builder(
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
			_toDetectionConfidenceLevel(
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

		if (!Objects.equals(GetterUtil.getString(levelProperty), "none")) {
			raiFilters.put(
				raiFilterType,
				_toDetectionConfidenceLevel(
					GetterUtil.getString(levelProperty)));
		}
	}

	private DetectionConfidenceLevel _toDetectionConfidenceLevel(String key) {
		try {
			return DetectionConfidenceLevel.valueOf(
				StringUtil.toUpperCase(
					CamelCaseUtil.fromCamelCase(key, CharPool.UNDERLINE)));
		}
		catch (IllegalArgumentException illegalArgumentException) {
			return DetectionConfidenceLevel.MEDIUM_AND_ABOVE;
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	private final Map<String, ModelArmorClient> _modelArmorClients =
		new ConcurrentHashMap<>();

}