/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.guardrail;

import com.google.cloud.modelarmor.v1.DetectionConfidenceLevel;
import com.google.cloud.modelarmor.v1.FilterConfig;
import com.google.cloud.modelarmor.v1.MaliciousUriFilterSettings;
import com.google.cloud.modelarmor.v1.PiAndJailbreakFilterSettings;
import com.google.cloud.modelarmor.v1.RaiFilterSettings;
import com.google.cloud.modelarmor.v1.RaiFilterType;
import com.google.cloud.modelarmor.v1.SdpBasicConfig;
import com.google.cloud.modelarmor.v1.SdpFilterSettings;
import com.google.cloud.modelarmor.v1.Template;

import java.util.Objects;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
public class ModelArmorTemplate {

	public static Builder builder(String templateId) {
		return new Builder(templateId);
	}

	public String getGuardrailType() {
		return _guardrailType;
	}

	public String getLocation() {
		return _location;
	}

	public String getName() {
		return _name;
	}

	public Template getTemplate() {
		return _template;
	}

	public String getTemplateId() {
		return _templateId;
	}

	public static class Builder {

		public ModelArmorTemplate build() {
			return new ModelArmorTemplate(this);
		}

		public Builder guardrailType(String guardrailType) {
			_guardrailType = guardrailType;

			return this;
		}

		public Builder location(String location) {
			_location = location;

			return this;
		}

		public Builder maliciousUriFilterEnabled(
			boolean maliciousUriFilterEnabled) {

			if (maliciousUriFilterEnabled) {
				_filterConfigBuilder.setMaliciousUriFilterSettings(
					MaliciousUriFilterSettings.newBuilder(
					).setFilterEnforcement(
						MaliciousUriFilterSettings.
							MaliciousUriFilterEnforcement.ENABLED
					));
			}

			return this;
		}

		public Builder multilanguageDetectionEnabled(
			boolean multilanguageDetectionEnabled) {

			_multilanguageDetectionEnabled = multilanguageDetectionEnabled;

			return this;
		}

		public Builder name(String name) {
			_name = name;

			return this;
		}

		public Builder piAndJailbreakConfidenceLevel(
			DetectionConfidenceLevel piAndJailbreakConfidenceLevel) {

			_piAndJailbreakConfidenceLevel = piAndJailbreakConfidenceLevel;

			return this;
		}

		public Builder piAndJailbreakFilterEnabled(
			boolean piAndJailbreakFilterEnabled) {

			if (piAndJailbreakFilterEnabled) {
				_filterConfigBuilder.setPiAndJailbreakFilterSettings(
					PiAndJailbreakFilterSettings.newBuilder(
					).setConfidenceLevel(
						_piAndJailbreakConfidenceLevel
					).setFilterEnforcement(
						PiAndJailbreakFilterSettings.
							PiAndJailbreakFilterEnforcement.ENABLED
					));
			}

			return this;
		}

		public Builder raiDangerousDetectionConfidenceLevel(
			DetectionConfidenceLevel raiDangerousDetectionConfidenceLevel) {

			_addRaiFilter(
				raiDangerousDetectionConfidenceLevel, RaiFilterType.DANGEROUS);

			return this;
		}

		public Builder raiHarassmentDetectionConfidenceLevel(
			DetectionConfidenceLevel raiHarassmentDetectionConfidenceLevel) {

			_addRaiFilter(
				raiHarassmentDetectionConfidenceLevel,
				RaiFilterType.HARASSMENT);

			return this;
		}

		public Builder raiHateSpeechDetectionConfidenceLevel(
			DetectionConfidenceLevel raiHateSpeechDetectionConfidenceLevel) {

			_addRaiFilter(
				raiHateSpeechDetectionConfidenceLevel,
				RaiFilterType.HATE_SPEECH);

			return this;
		}

		public Builder raiSexuallyExplicitDetectionConfidenceLevel(
			DetectionConfidenceLevel
				raiSexuallyExplicitDetectionConfidenceLevel) {

			_addRaiFilter(
				raiSexuallyExplicitDetectionConfidenceLevel,
				RaiFilterType.SEXUALLY_EXPLICIT);

			return this;
		}

		public Builder sdpFilterEnabled(boolean sdpFilterEnabled) {
			if (sdpFilterEnabled) {
				_filterConfigBuilder.setSdpSettings(
					SdpFilterSettings.newBuilder(
					).setBasicConfig(
						SdpBasicConfig.newBuilder(
						).setFilterEnforcement(
							SdpBasicConfig.SdpBasicConfigEnforcement.ENABLED
						)
					));
			}

			return this;
		}

		private Builder(String templateId) {
			_templateId = templateId;
		}

		private void _addRaiFilter(
			DetectionConfidenceLevel detectionConfidenceLevel,
			RaiFilterType raiFilterType) {

			if (Objects.equals(
					detectionConfidenceLevel,
					DetectionConfidenceLevel.
						DETECTION_CONFIDENCE_LEVEL_UNSPECIFIED)) {

				return;
			}

			_raiFilterSettingsBuilder.addRaiFilters(
				RaiFilterSettings.RaiFilter.newBuilder(
				).setConfidenceLevel(
					detectionConfidenceLevel
				).setFilterType(
					raiFilterType
				));
		}

		private FilterConfig _toFilterConfig() {
			if (_raiFilterSettingsBuilder.getRaiFiltersCount() > 0) {
				_filterConfigBuilder.setRaiSettings(_raiFilterSettingsBuilder);
			}

			return _filterConfigBuilder.build();
		}

		private final FilterConfig.Builder _filterConfigBuilder =
			FilterConfig.newBuilder();
		private String _guardrailType;
		private String _location;
		private boolean _multilanguageDetectionEnabled;
		private String _name;
		private DetectionConfidenceLevel _piAndJailbreakConfidenceLevel;
		private final RaiFilterSettings.Builder _raiFilterSettingsBuilder =
			RaiFilterSettings.newBuilder();
		private final String _templateId;

	}

	private ModelArmorTemplate(Builder builder) {
		_guardrailType = builder._guardrailType;
		_location = builder._location;
		_name = builder._name;

		_template = Template.newBuilder(
		).setFilterConfig(
			builder._toFilterConfig()
		).setTemplateMetadata(
			Template.TemplateMetadata.newBuilder(
			).setMultiLanguageDetection(
				Template.TemplateMetadata.MultiLanguageDetection.newBuilder(
				).setEnableMultiLanguageDetection(
					builder._multilanguageDetectionEnabled
				)
			).build()
		).build();

		_templateId = builder._templateId;
	}

	private final String _guardrailType;
	private final String _location;
	private final String _name;
	private final Template _template;
	private final String _templateId;

}