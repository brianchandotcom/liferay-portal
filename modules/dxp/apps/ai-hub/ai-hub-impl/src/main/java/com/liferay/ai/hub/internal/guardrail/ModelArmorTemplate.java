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

import java.util.Collections;
import java.util.Map;

/**
 * @author João Victor Alves
 */
public class ModelArmorTemplate {

	public static Builder builder(String templateId) {
		return new Builder(templateId);
	}

	public FilterConfig getFilterConfig() {
		return _filterConfig;
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

			_maliciousUriFilterEnabled = maliciousUriFilterEnabled;

			return this;
		}

		public Builder multiLanguageDetectionEnabled(
			boolean multiLanguageDetectionEnabled) {

			_multiLanguageDetectionEnabled = multiLanguageDetectionEnabled;

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

			_piAndJailbreakFilterEnabled = piAndJailbreakFilterEnabled;

			return this;
		}

		public Builder raiFilters(
			Map<RaiFilterType, DetectionConfidenceLevel> raiFilters) {

			_raiFilters = raiFilters;

			return this;
		}

		public Builder sdpFilterEnabled(boolean sdpFilterEnabled) {
			_sdpFilterEnabled = sdpFilterEnabled;

			return this;
		}

		private Builder(String templateId) {
			_templateId = templateId;
		}

		private FilterConfig _toFilterConfig() {
			FilterConfig.Builder builder = FilterConfig.newBuilder();

			if (_maliciousUriFilterEnabled) {
				builder.setMaliciousUriFilterSettings(
					MaliciousUriFilterSettings.newBuilder(
					).setFilterEnforcement(
						MaliciousUriFilterSettings.
							MaliciousUriFilterEnforcement.ENABLED
					));
			}

			if (_piAndJailbreakFilterEnabled) {
				builder.setPiAndJailbreakFilterSettings(
					PiAndJailbreakFilterSettings.newBuilder(
					).setFilterEnforcement(
						PiAndJailbreakFilterSettings.
							PiAndJailbreakFilterEnforcement.ENABLED
					).setConfidenceLevel(
						_piAndJailbreakConfidenceLevel
					));
			}

			if (_sdpFilterEnabled) {
				builder.setSdpSettings(
					SdpFilterSettings.newBuilder(
					).setBasicConfig(
						SdpBasicConfig.newBuilder(
						).setFilterEnforcement(
							SdpBasicConfig.SdpBasicConfigEnforcement.ENABLED
						)
					));
			}

			if (_raiFilters.isEmpty()) {
				return builder.build();
			}

			RaiFilterSettings.Builder raiFilterSettingsBuilder =
				RaiFilterSettings.newBuilder();

			for (Map.Entry<RaiFilterType, DetectionConfidenceLevel> entry :
					_raiFilters.entrySet()) {

				raiFilterSettingsBuilder.addRaiFilters(
					RaiFilterSettings.RaiFilter.newBuilder(
					).setConfidenceLevel(
						entry.getValue()
					).setFilterType(
						entry.getKey()
					));
			}

			builder.setRaiSettings(raiFilterSettingsBuilder);

			return builder.build();
		}

		private Template _toTemplate(FilterConfig filterConfig) {
			return Template.newBuilder(
			).setFilterConfig(
				filterConfig
			).setTemplateMetadata(
				Template.TemplateMetadata.newBuilder(
				).setMultiLanguageDetection(
					Template.TemplateMetadata.MultiLanguageDetection.newBuilder(
					).setEnableMultiLanguageDetection(
						_multiLanguageDetectionEnabled
					)
				).build()
			).build();
		}

		private String _guardrailType = "input";
		private String _location;
		private boolean _maliciousUriFilterEnabled;
		private boolean _multiLanguageDetectionEnabled;
		private String _name;
		private DetectionConfidenceLevel _piAndJailbreakConfidenceLevel =
			DetectionConfidenceLevel.MEDIUM_AND_ABOVE;
		private boolean _piAndJailbreakFilterEnabled;
		private Map<RaiFilterType, DetectionConfidenceLevel> _raiFilters =
			Collections.emptyMap();
		private boolean _sdpFilterEnabled;
		private final String _templateId;

	}

	private ModelArmorTemplate(Builder builder) {
		_filterConfig = builder._toFilterConfig();
		_guardrailType = builder._guardrailType;
		_location = builder._location;
		_name = builder._name;
		_template = builder._toTemplate(_filterConfig);
		_templateId = builder._templateId;
	}

	private final FilterConfig _filterConfig;
	private final String _guardrailType;
	private final String _location;
	private final String _name;
	private final Template _template;
	private final String _templateId;

}