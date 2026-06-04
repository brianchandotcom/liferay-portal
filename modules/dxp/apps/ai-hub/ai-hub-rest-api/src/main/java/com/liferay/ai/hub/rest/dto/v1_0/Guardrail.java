/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.validation.Valid;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
@GraphQLName("Guardrail")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Guardrail")
public class Guardrail implements Serializable {

	public static Guardrail toDTO(String json) {
		return ObjectMapperUtil.readValue(Guardrail.class, json);
	}

	public static Guardrail unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Guardrail.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getActive() {
		if (_activeSupplier != null) {
			active = _activeSupplier.get();

			_activeSupplier = null;
		}

		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;

		_activeSupplier = null;
	}

	@JsonIgnore
	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		_activeSupplier = () -> {
			try {
				return activeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean active;

	@JsonIgnore
	private Supplier<Boolean> _activeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getDescription() {
		if (_descriptionSupplier != null) {
			description = _descriptionSupplier.get();

			_descriptionSupplier = null;
		}

		return description;
	}

	public void setDescription(String description) {
		this.description = description;

		_descriptionSupplier = null;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		_descriptionSupplier = () -> {
			try {
				return descriptionUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String description;

	@JsonIgnore
	private Supplier<String> _descriptionSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getExternalReferenceCode() {
		if (_externalReferenceCodeSupplier != null) {
			externalReferenceCode = _externalReferenceCodeSupplier.get();

			_externalReferenceCodeSupplier = null;
		}

		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;

		_externalReferenceCodeSupplier = null;
	}

	@JsonIgnore
	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		_externalReferenceCodeSupplier = () -> {
			try {
				return externalReferenceCodeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String externalReferenceCode;

	@JsonIgnore
	private Supplier<String> _externalReferenceCodeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("guardrailType")
	@Valid
	public GuardrailType getGuardrailType() {
		if (_guardrailTypeSupplier != null) {
			guardrailType = _guardrailTypeSupplier.get();

			_guardrailTypeSupplier = null;
		}

		return guardrailType;
	}

	@JsonIgnore
	public String getGuardrailTypeAsString() {
		GuardrailType guardrailType = getGuardrailType();

		if (guardrailType == null) {
			return null;
		}

		return guardrailType.toString();
	}

	public void setGuardrailType(GuardrailType guardrailType) {
		this.guardrailType = guardrailType;

		_guardrailTypeSupplier = null;
	}

	@JsonIgnore
	public void setGuardrailType(
		UnsafeSupplier<GuardrailType, Exception> guardrailTypeUnsafeSupplier) {

		_guardrailTypeSupplier = () -> {
			try {
				return guardrailTypeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected GuardrailType guardrailType;

	@JsonIgnore
	private Supplier<GuardrailType> _guardrailTypeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getLocation() {
		if (_locationSupplier != null) {
			location = _locationSupplier.get();

			_locationSupplier = null;
		}

		return location;
	}

	public void setLocation(String location) {
		this.location = location;

		_locationSupplier = null;
	}

	@JsonIgnore
	public void setLocation(
		UnsafeSupplier<String, Exception> locationUnsafeSupplier) {

		_locationSupplier = () -> {
			try {
				return locationUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String location;

	@JsonIgnore
	private Supplier<String> _locationSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getMaliciousUriFilterEnabled() {
		if (_maliciousUriFilterEnabledSupplier != null) {
			maliciousUriFilterEnabled =
				_maliciousUriFilterEnabledSupplier.get();

			_maliciousUriFilterEnabledSupplier = null;
		}

		return maliciousUriFilterEnabled;
	}

	public void setMaliciousUriFilterEnabled(
		Boolean maliciousUriFilterEnabled) {

		this.maliciousUriFilterEnabled = maliciousUriFilterEnabled;

		_maliciousUriFilterEnabledSupplier = null;
	}

	@JsonIgnore
	public void setMaliciousUriFilterEnabled(
		UnsafeSupplier<Boolean, Exception>
			maliciousUriFilterEnabledUnsafeSupplier) {

		_maliciousUriFilterEnabledSupplier = () -> {
			try {
				return maliciousUriFilterEnabledUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean maliciousUriFilterEnabled;

	@JsonIgnore
	private Supplier<Boolean> _maliciousUriFilterEnabledSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getMultilanguageDetectionEnabled() {
		if (_multilanguageDetectionEnabledSupplier != null) {
			multilanguageDetectionEnabled =
				_multilanguageDetectionEnabledSupplier.get();

			_multilanguageDetectionEnabledSupplier = null;
		}

		return multilanguageDetectionEnabled;
	}

	public void setMultilanguageDetectionEnabled(
		Boolean multilanguageDetectionEnabled) {

		this.multilanguageDetectionEnabled = multilanguageDetectionEnabled;

		_multilanguageDetectionEnabledSupplier = null;
	}

	@JsonIgnore
	public void setMultilanguageDetectionEnabled(
		UnsafeSupplier<Boolean, Exception>
			multilanguageDetectionEnabledUnsafeSupplier) {

		_multilanguageDetectionEnabledSupplier = () -> {
			try {
				return multilanguageDetectionEnabledUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean multilanguageDetectionEnabled;

	@JsonIgnore
	private Supplier<Boolean> _multilanguageDetectionEnabledSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("piAndJailbreakConfidenceLevel")
	@Valid
	public PiAndJailbreakConfidenceLevel getPiAndJailbreakConfidenceLevel() {
		if (_piAndJailbreakConfidenceLevelSupplier != null) {
			piAndJailbreakConfidenceLevel =
				_piAndJailbreakConfidenceLevelSupplier.get();

			_piAndJailbreakConfidenceLevelSupplier = null;
		}

		return piAndJailbreakConfidenceLevel;
	}

	@JsonIgnore
	public String getPiAndJailbreakConfidenceLevelAsString() {
		PiAndJailbreakConfidenceLevel piAndJailbreakConfidenceLevel =
			getPiAndJailbreakConfidenceLevel();

		if (piAndJailbreakConfidenceLevel == null) {
			return null;
		}

		return piAndJailbreakConfidenceLevel.toString();
	}

	public void setPiAndJailbreakConfidenceLevel(
		PiAndJailbreakConfidenceLevel piAndJailbreakConfidenceLevel) {

		this.piAndJailbreakConfidenceLevel = piAndJailbreakConfidenceLevel;

		_piAndJailbreakConfidenceLevelSupplier = null;
	}

	@JsonIgnore
	public void setPiAndJailbreakConfidenceLevel(
		UnsafeSupplier<PiAndJailbreakConfidenceLevel, Exception>
			piAndJailbreakConfidenceLevelUnsafeSupplier) {

		_piAndJailbreakConfidenceLevelSupplier = () -> {
			try {
				return piAndJailbreakConfidenceLevelUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected PiAndJailbreakConfidenceLevel piAndJailbreakConfidenceLevel;

	@JsonIgnore
	private Supplier<PiAndJailbreakConfidenceLevel>
		_piAndJailbreakConfidenceLevelSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getPiAndJailbreakFilterEnabled() {
		if (_piAndJailbreakFilterEnabledSupplier != null) {
			piAndJailbreakFilterEnabled =
				_piAndJailbreakFilterEnabledSupplier.get();

			_piAndJailbreakFilterEnabledSupplier = null;
		}

		return piAndJailbreakFilterEnabled;
	}

	public void setPiAndJailbreakFilterEnabled(
		Boolean piAndJailbreakFilterEnabled) {

		this.piAndJailbreakFilterEnabled = piAndJailbreakFilterEnabled;

		_piAndJailbreakFilterEnabledSupplier = null;
	}

	@JsonIgnore
	public void setPiAndJailbreakFilterEnabled(
		UnsafeSupplier<Boolean, Exception>
			piAndJailbreakFilterEnabledUnsafeSupplier) {

		_piAndJailbreakFilterEnabledSupplier = () -> {
			try {
				return piAndJailbreakFilterEnabledUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean piAndJailbreakFilterEnabled;

	@JsonIgnore
	private Supplier<Boolean> _piAndJailbreakFilterEnabledSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("raiDangerousLevel")
	@Valid
	public RaiDangerousLevel getRaiDangerousLevel() {
		if (_raiDangerousLevelSupplier != null) {
			raiDangerousLevel = _raiDangerousLevelSupplier.get();

			_raiDangerousLevelSupplier = null;
		}

		return raiDangerousLevel;
	}

	@JsonIgnore
	public String getRaiDangerousLevelAsString() {
		RaiDangerousLevel raiDangerousLevel = getRaiDangerousLevel();

		if (raiDangerousLevel == null) {
			return null;
		}

		return raiDangerousLevel.toString();
	}

	public void setRaiDangerousLevel(RaiDangerousLevel raiDangerousLevel) {
		this.raiDangerousLevel = raiDangerousLevel;

		_raiDangerousLevelSupplier = null;
	}

	@JsonIgnore
	public void setRaiDangerousLevel(
		UnsafeSupplier<RaiDangerousLevel, Exception>
			raiDangerousLevelUnsafeSupplier) {

		_raiDangerousLevelSupplier = () -> {
			try {
				return raiDangerousLevelUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected RaiDangerousLevel raiDangerousLevel;

	@JsonIgnore
	private Supplier<RaiDangerousLevel> _raiDangerousLevelSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("raiHarassmentLevel")
	@Valid
	public RaiHarassmentLevel getRaiHarassmentLevel() {
		if (_raiHarassmentLevelSupplier != null) {
			raiHarassmentLevel = _raiHarassmentLevelSupplier.get();

			_raiHarassmentLevelSupplier = null;
		}

		return raiHarassmentLevel;
	}

	@JsonIgnore
	public String getRaiHarassmentLevelAsString() {
		RaiHarassmentLevel raiHarassmentLevel = getRaiHarassmentLevel();

		if (raiHarassmentLevel == null) {
			return null;
		}

		return raiHarassmentLevel.toString();
	}

	public void setRaiHarassmentLevel(RaiHarassmentLevel raiHarassmentLevel) {
		this.raiHarassmentLevel = raiHarassmentLevel;

		_raiHarassmentLevelSupplier = null;
	}

	@JsonIgnore
	public void setRaiHarassmentLevel(
		UnsafeSupplier<RaiHarassmentLevel, Exception>
			raiHarassmentLevelUnsafeSupplier) {

		_raiHarassmentLevelSupplier = () -> {
			try {
				return raiHarassmentLevelUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected RaiHarassmentLevel raiHarassmentLevel;

	@JsonIgnore
	private Supplier<RaiHarassmentLevel> _raiHarassmentLevelSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("raiHateSpeechLevel")
	@Valid
	public RaiHateSpeechLevel getRaiHateSpeechLevel() {
		if (_raiHateSpeechLevelSupplier != null) {
			raiHateSpeechLevel = _raiHateSpeechLevelSupplier.get();

			_raiHateSpeechLevelSupplier = null;
		}

		return raiHateSpeechLevel;
	}

	@JsonIgnore
	public String getRaiHateSpeechLevelAsString() {
		RaiHateSpeechLevel raiHateSpeechLevel = getRaiHateSpeechLevel();

		if (raiHateSpeechLevel == null) {
			return null;
		}

		return raiHateSpeechLevel.toString();
	}

	public void setRaiHateSpeechLevel(RaiHateSpeechLevel raiHateSpeechLevel) {
		this.raiHateSpeechLevel = raiHateSpeechLevel;

		_raiHateSpeechLevelSupplier = null;
	}

	@JsonIgnore
	public void setRaiHateSpeechLevel(
		UnsafeSupplier<RaiHateSpeechLevel, Exception>
			raiHateSpeechLevelUnsafeSupplier) {

		_raiHateSpeechLevelSupplier = () -> {
			try {
				return raiHateSpeechLevelUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected RaiHateSpeechLevel raiHateSpeechLevel;

	@JsonIgnore
	private Supplier<RaiHateSpeechLevel> _raiHateSpeechLevelSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("raiSexuallyExplicitLevel")
	@Valid
	public RaiSexuallyExplicitLevel getRaiSexuallyExplicitLevel() {
		if (_raiSexuallyExplicitLevelSupplier != null) {
			raiSexuallyExplicitLevel = _raiSexuallyExplicitLevelSupplier.get();

			_raiSexuallyExplicitLevelSupplier = null;
		}

		return raiSexuallyExplicitLevel;
	}

	@JsonIgnore
	public String getRaiSexuallyExplicitLevelAsString() {
		RaiSexuallyExplicitLevel raiSexuallyExplicitLevel =
			getRaiSexuallyExplicitLevel();

		if (raiSexuallyExplicitLevel == null) {
			return null;
		}

		return raiSexuallyExplicitLevel.toString();
	}

	public void setRaiSexuallyExplicitLevel(
		RaiSexuallyExplicitLevel raiSexuallyExplicitLevel) {

		this.raiSexuallyExplicitLevel = raiSexuallyExplicitLevel;

		_raiSexuallyExplicitLevelSupplier = null;
	}

	@JsonIgnore
	public void setRaiSexuallyExplicitLevel(
		UnsafeSupplier<RaiSexuallyExplicitLevel, Exception>
			raiSexuallyExplicitLevelUnsafeSupplier) {

		_raiSexuallyExplicitLevelSupplier = () -> {
			try {
				return raiSexuallyExplicitLevelUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected RaiSexuallyExplicitLevel raiSexuallyExplicitLevel;

	@JsonIgnore
	private Supplier<RaiSexuallyExplicitLevel>
		_raiSexuallyExplicitLevelSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getSdpFilterEnabled() {
		if (_sdpFilterEnabledSupplier != null) {
			sdpFilterEnabled = _sdpFilterEnabledSupplier.get();

			_sdpFilterEnabledSupplier = null;
		}

		return sdpFilterEnabled;
	}

	public void setSdpFilterEnabled(Boolean sdpFilterEnabled) {
		this.sdpFilterEnabled = sdpFilterEnabled;

		_sdpFilterEnabledSupplier = null;
	}

	@JsonIgnore
	public void setSdpFilterEnabled(
		UnsafeSupplier<Boolean, Exception> sdpFilterEnabledUnsafeSupplier) {

		_sdpFilterEnabledSupplier = () -> {
			try {
				return sdpFilterEnabledUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean sdpFilterEnabled;

	@JsonIgnore
	private Supplier<Boolean> _sdpFilterEnabledSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getTitle() {
		if (_titleSupplier != null) {
			title = _titleSupplier.get();

			_titleSupplier = null;
		}

		return title;
	}

	public void setTitle(String title) {
		this.title = title;

		_titleSupplier = null;
	}

	@JsonIgnore
	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		_titleSupplier = () -> {
			try {
				return titleUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String title;

	@JsonIgnore
	private Supplier<String> _titleSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Map<String, String> getTitle_i18n() {
		if (_title_i18nSupplier != null) {
			title_i18n = _title_i18nSupplier.get();

			_title_i18nSupplier = null;
		}

		return title_i18n;
	}

	public void setTitle_i18n(Map<String, String> title_i18n) {
		this.title_i18n = title_i18n;

		_title_i18nSupplier = null;
	}

	@JsonIgnore
	public void setTitle_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			title_i18nUnsafeSupplier) {

		_title_i18nSupplier = () -> {
			try {
				return title_i18nUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, String> title_i18n;

	@JsonIgnore
	private Supplier<Map<String, String>> _title_i18nSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Guardrail)) {
			return false;
		}

		Guardrail guardrail = (Guardrail)object;

		return Objects.equals(toString(), guardrail.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Boolean active = getActive();

		if (active != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(active);
		}

		String description = getDescription();

		if (description != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(description));

			sb.append("\"");
		}

		String externalReferenceCode = getExternalReferenceCode();

		if (externalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(externalReferenceCode));

			sb.append("\"");
		}

		GuardrailType guardrailType = getGuardrailType();

		if (guardrailType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"guardrailType\": ");

			sb.append("\"");
			sb.append(guardrailType);
			sb.append("\"");
		}

		String location = getLocation();

		if (location != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"location\": ");

			sb.append("\"");

			sb.append(_escape(location));

			sb.append("\"");
		}

		Boolean maliciousUriFilterEnabled = getMaliciousUriFilterEnabled();

		if (maliciousUriFilterEnabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maliciousUriFilterEnabled\": ");

			sb.append(maliciousUriFilterEnabled);
		}

		Boolean multilanguageDetectionEnabled =
			getMultilanguageDetectionEnabled();

		if (multilanguageDetectionEnabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"multilanguageDetectionEnabled\": ");

			sb.append(multilanguageDetectionEnabled);
		}

		PiAndJailbreakConfidenceLevel piAndJailbreakConfidenceLevel =
			getPiAndJailbreakConfidenceLevel();

		if (piAndJailbreakConfidenceLevel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"piAndJailbreakConfidenceLevel\": ");

			sb.append("\"");
			sb.append(piAndJailbreakConfidenceLevel);
			sb.append("\"");
		}

		Boolean piAndJailbreakFilterEnabled = getPiAndJailbreakFilterEnabled();

		if (piAndJailbreakFilterEnabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"piAndJailbreakFilterEnabled\": ");

			sb.append(piAndJailbreakFilterEnabled);
		}

		RaiDangerousLevel raiDangerousLevel = getRaiDangerousLevel();

		if (raiDangerousLevel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiDangerousLevel\": ");

			sb.append("\"");
			sb.append(raiDangerousLevel);
			sb.append("\"");
		}

		RaiHarassmentLevel raiHarassmentLevel = getRaiHarassmentLevel();

		if (raiHarassmentLevel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiHarassmentLevel\": ");

			sb.append("\"");
			sb.append(raiHarassmentLevel);
			sb.append("\"");
		}

		RaiHateSpeechLevel raiHateSpeechLevel = getRaiHateSpeechLevel();

		if (raiHateSpeechLevel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiHateSpeechLevel\": ");

			sb.append("\"");
			sb.append(raiHateSpeechLevel);
			sb.append("\"");
		}

		RaiSexuallyExplicitLevel raiSexuallyExplicitLevel =
			getRaiSexuallyExplicitLevel();

		if (raiSexuallyExplicitLevel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"raiSexuallyExplicitLevel\": ");

			sb.append("\"");
			sb.append(raiSexuallyExplicitLevel);
			sb.append("\"");
		}

		Boolean sdpFilterEnabled = getSdpFilterEnabled();

		if (sdpFilterEnabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sdpFilterEnabled\": ");

			sb.append(sdpFilterEnabled);
		}

		String title = getTitle();

		if (title != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(title));

			sb.append("\"");
		}

		Map<String, String> title_i18n = getTitle_i18n();

		if (title_i18n != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title_i18n\": ");

			sb.append(_toJSON(title_i18n));
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.ai.hub.rest.dto.v1_0.Guardrail",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("GuardrailType")
	public static enum GuardrailType {

		INPUT("input"), OUTPUT("output");

		@JsonCreator
		public static GuardrailType create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (GuardrailType guardrailType : values()) {
				if (Objects.equals(guardrailType.getValue(), value)) {
					return guardrailType;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private GuardrailType(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("PiAndJailbreakConfidenceLevel")
	public static enum PiAndJailbreakConfidenceLevel {

		HIGH("high"), LOW_AND_ABOVE("lowAndAbove"),
		MEDIUM_AND_ABOVE("mediumAndAbove");

		@JsonCreator
		public static PiAndJailbreakConfidenceLevel create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (PiAndJailbreakConfidenceLevel piAndJailbreakConfidenceLevel :
					values()) {

				if (Objects.equals(
						piAndJailbreakConfidenceLevel.getValue(), value)) {

					return piAndJailbreakConfidenceLevel;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private PiAndJailbreakConfidenceLevel(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("RaiDangerousLevel")
	public static enum RaiDangerousLevel {

		HIGH("high"), LOW_AND_ABOVE("lowAndAbove"),
		MEDIUM_AND_ABOVE("mediumAndAbove"), NONE("none");

		@JsonCreator
		public static RaiDangerousLevel create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (RaiDangerousLevel raiDangerousLevel : values()) {
				if (Objects.equals(raiDangerousLevel.getValue(), value)) {
					return raiDangerousLevel;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private RaiDangerousLevel(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("RaiHarassmentLevel")
	public static enum RaiHarassmentLevel {

		HIGH("high"), LOW_AND_ABOVE("lowAndAbove"),
		MEDIUM_AND_ABOVE("mediumAndAbove"), NONE("none");

		@JsonCreator
		public static RaiHarassmentLevel create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (RaiHarassmentLevel raiHarassmentLevel : values()) {
				if (Objects.equals(raiHarassmentLevel.getValue(), value)) {
					return raiHarassmentLevel;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private RaiHarassmentLevel(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("RaiHateSpeechLevel")
	public static enum RaiHateSpeechLevel {

		HIGH("high"), LOW_AND_ABOVE("lowAndAbove"),
		MEDIUM_AND_ABOVE("mediumAndAbove"), NONE("none");

		@JsonCreator
		public static RaiHateSpeechLevel create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (RaiHateSpeechLevel raiHateSpeechLevel : values()) {
				if (Objects.equals(raiHateSpeechLevel.getValue(), value)) {
					return raiHateSpeechLevel;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private RaiHateSpeechLevel(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("RaiSexuallyExplicitLevel")
	public static enum RaiSexuallyExplicitLevel {

		HIGH("high"), LOW_AND_ABOVE("lowAndAbove"),
		MEDIUM_AND_ABOVE("mediumAndAbove"), NONE("none");

		@JsonCreator
		public static RaiSexuallyExplicitLevel create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (RaiSexuallyExplicitLevel raiSexuallyExplicitLevel : values()) {
				if (Objects.equals(
						raiSexuallyExplicitLevel.getValue(), value)) {

					return raiSexuallyExplicitLevel;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private RaiSexuallyExplicitLevel(String value) {
			_value = value;
		}

		private final String _value;

	}

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}
// LIFERAY-REST-BUILDER-HASH:-570620727