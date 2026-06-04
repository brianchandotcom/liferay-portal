/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.dto.v1_0;

import com.liferay.ai.hub.rest.client.function.UnsafeSupplier;
import com.liferay.ai.hub.rest.client.serdes.v1_0.GuardrailSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class Guardrail implements Cloneable, Serializable {

	public static Guardrail toDTO(String json) {
		return GuardrailSerDes.toDTO(json);
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean active;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String description;

	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String externalReferenceCode;

	public GuardrailType getGuardrailType() {
		return guardrailType;
	}

	public String getGuardrailTypeAsString() {
		if (guardrailType == null) {
			return null;
		}

		return guardrailType.toString();
	}

	public void setGuardrailType(GuardrailType guardrailType) {
		this.guardrailType = guardrailType;
	}

	public void setGuardrailType(
		UnsafeSupplier<GuardrailType, Exception> guardrailTypeUnsafeSupplier) {

		try {
			guardrailType = guardrailTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected GuardrailType guardrailType;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setLocation(
		UnsafeSupplier<String, Exception> locationUnsafeSupplier) {

		try {
			location = locationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String location;

	public Boolean getMaliciousUriFilterEnabled() {
		return maliciousUriFilterEnabled;
	}

	public void setMaliciousUriFilterEnabled(
		Boolean maliciousUriFilterEnabled) {

		this.maliciousUriFilterEnabled = maliciousUriFilterEnabled;
	}

	public void setMaliciousUriFilterEnabled(
		UnsafeSupplier<Boolean, Exception>
			maliciousUriFilterEnabledUnsafeSupplier) {

		try {
			maliciousUriFilterEnabled =
				maliciousUriFilterEnabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean maliciousUriFilterEnabled;

	public Boolean getMultilanguageDetectionEnabled() {
		return multilanguageDetectionEnabled;
	}

	public void setMultilanguageDetectionEnabled(
		Boolean multilanguageDetectionEnabled) {

		this.multilanguageDetectionEnabled = multilanguageDetectionEnabled;
	}

	public void setMultilanguageDetectionEnabled(
		UnsafeSupplier<Boolean, Exception>
			multilanguageDetectionEnabledUnsafeSupplier) {

		try {
			multilanguageDetectionEnabled =
				multilanguageDetectionEnabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean multilanguageDetectionEnabled;

	public PiAndJailbreakConfidenceLevel getPiAndJailbreakConfidenceLevel() {
		return piAndJailbreakConfidenceLevel;
	}

	public String getPiAndJailbreakConfidenceLevelAsString() {
		if (piAndJailbreakConfidenceLevel == null) {
			return null;
		}

		return piAndJailbreakConfidenceLevel.toString();
	}

	public void setPiAndJailbreakConfidenceLevel(
		PiAndJailbreakConfidenceLevel piAndJailbreakConfidenceLevel) {

		this.piAndJailbreakConfidenceLevel = piAndJailbreakConfidenceLevel;
	}

	public void setPiAndJailbreakConfidenceLevel(
		UnsafeSupplier<PiAndJailbreakConfidenceLevel, Exception>
			piAndJailbreakConfidenceLevelUnsafeSupplier) {

		try {
			piAndJailbreakConfidenceLevel =
				piAndJailbreakConfidenceLevelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PiAndJailbreakConfidenceLevel piAndJailbreakConfidenceLevel;

	public Boolean getPiAndJailbreakFilterEnabled() {
		return piAndJailbreakFilterEnabled;
	}

	public void setPiAndJailbreakFilterEnabled(
		Boolean piAndJailbreakFilterEnabled) {

		this.piAndJailbreakFilterEnabled = piAndJailbreakFilterEnabled;
	}

	public void setPiAndJailbreakFilterEnabled(
		UnsafeSupplier<Boolean, Exception>
			piAndJailbreakFilterEnabledUnsafeSupplier) {

		try {
			piAndJailbreakFilterEnabled =
				piAndJailbreakFilterEnabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean piAndJailbreakFilterEnabled;

	public RaiDangerousLevel getRaiDangerousLevel() {
		return raiDangerousLevel;
	}

	public String getRaiDangerousLevelAsString() {
		if (raiDangerousLevel == null) {
			return null;
		}

		return raiDangerousLevel.toString();
	}

	public void setRaiDangerousLevel(RaiDangerousLevel raiDangerousLevel) {
		this.raiDangerousLevel = raiDangerousLevel;
	}

	public void setRaiDangerousLevel(
		UnsafeSupplier<RaiDangerousLevel, Exception>
			raiDangerousLevelUnsafeSupplier) {

		try {
			raiDangerousLevel = raiDangerousLevelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RaiDangerousLevel raiDangerousLevel;

	public RaiHarassmentLevel getRaiHarassmentLevel() {
		return raiHarassmentLevel;
	}

	public String getRaiHarassmentLevelAsString() {
		if (raiHarassmentLevel == null) {
			return null;
		}

		return raiHarassmentLevel.toString();
	}

	public void setRaiHarassmentLevel(RaiHarassmentLevel raiHarassmentLevel) {
		this.raiHarassmentLevel = raiHarassmentLevel;
	}

	public void setRaiHarassmentLevel(
		UnsafeSupplier<RaiHarassmentLevel, Exception>
			raiHarassmentLevelUnsafeSupplier) {

		try {
			raiHarassmentLevel = raiHarassmentLevelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RaiHarassmentLevel raiHarassmentLevel;

	public RaiHateSpeechLevel getRaiHateSpeechLevel() {
		return raiHateSpeechLevel;
	}

	public String getRaiHateSpeechLevelAsString() {
		if (raiHateSpeechLevel == null) {
			return null;
		}

		return raiHateSpeechLevel.toString();
	}

	public void setRaiHateSpeechLevel(RaiHateSpeechLevel raiHateSpeechLevel) {
		this.raiHateSpeechLevel = raiHateSpeechLevel;
	}

	public void setRaiHateSpeechLevel(
		UnsafeSupplier<RaiHateSpeechLevel, Exception>
			raiHateSpeechLevelUnsafeSupplier) {

		try {
			raiHateSpeechLevel = raiHateSpeechLevelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RaiHateSpeechLevel raiHateSpeechLevel;

	public RaiSexuallyExplicitLevel getRaiSexuallyExplicitLevel() {
		return raiSexuallyExplicitLevel;
	}

	public String getRaiSexuallyExplicitLevelAsString() {
		if (raiSexuallyExplicitLevel == null) {
			return null;
		}

		return raiSexuallyExplicitLevel.toString();
	}

	public void setRaiSexuallyExplicitLevel(
		RaiSexuallyExplicitLevel raiSexuallyExplicitLevel) {

		this.raiSexuallyExplicitLevel = raiSexuallyExplicitLevel;
	}

	public void setRaiSexuallyExplicitLevel(
		UnsafeSupplier<RaiSexuallyExplicitLevel, Exception>
			raiSexuallyExplicitLevelUnsafeSupplier) {

		try {
			raiSexuallyExplicitLevel =
				raiSexuallyExplicitLevelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RaiSexuallyExplicitLevel raiSexuallyExplicitLevel;

	public Boolean getSdpFilterEnabled() {
		return sdpFilterEnabled;
	}

	public void setSdpFilterEnabled(Boolean sdpFilterEnabled) {
		this.sdpFilterEnabled = sdpFilterEnabled;
	}

	public void setSdpFilterEnabled(
		UnsafeSupplier<Boolean, Exception> sdpFilterEnabledUnsafeSupplier) {

		try {
			sdpFilterEnabled = sdpFilterEnabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean sdpFilterEnabled;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String title;

	public Map<String, String> getTitle_i18n() {
		return title_i18n;
	}

	public void setTitle_i18n(Map<String, String> title_i18n) {
		this.title_i18n = title_i18n;
	}

	public void setTitle_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			title_i18nUnsafeSupplier) {

		try {
			title_i18n = title_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> title_i18n;

	@Override
	public Guardrail clone() throws CloneNotSupportedException {
		return (Guardrail)super.clone();
	}

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
		return GuardrailSerDes.toJSON(this);
	}

	public static enum GuardrailType {

		INPUT("input"), OUTPUT("output");

		public static GuardrailType create(String value) {
			for (GuardrailType guardrailType : values()) {
				if (Objects.equals(guardrailType.getValue(), value) ||
					Objects.equals(guardrailType.name(), value)) {

					return guardrailType;
				}
			}

			return null;
		}

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

	public static enum PiAndJailbreakConfidenceLevel {

		HIGH("high"), LOW_AND_ABOVE("lowAndAbove"),
		MEDIUM_AND_ABOVE("mediumAndAbove");

		public static PiAndJailbreakConfidenceLevel create(String value) {
			for (PiAndJailbreakConfidenceLevel piAndJailbreakConfidenceLevel :
					values()) {

				if (Objects.equals(
						piAndJailbreakConfidenceLevel.getValue(), value) ||
					Objects.equals(
						piAndJailbreakConfidenceLevel.name(), value)) {

					return piAndJailbreakConfidenceLevel;
				}
			}

			return null;
		}

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

	public static enum RaiDangerousLevel {

		HIGH("high"), LOW_AND_ABOVE("lowAndAbove"),
		MEDIUM_AND_ABOVE("mediumAndAbove"), NONE("none");

		public static RaiDangerousLevel create(String value) {
			for (RaiDangerousLevel raiDangerousLevel : values()) {
				if (Objects.equals(raiDangerousLevel.getValue(), value) ||
					Objects.equals(raiDangerousLevel.name(), value)) {

					return raiDangerousLevel;
				}
			}

			return null;
		}

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

	public static enum RaiHarassmentLevel {

		HIGH("high"), LOW_AND_ABOVE("lowAndAbove"),
		MEDIUM_AND_ABOVE("mediumAndAbove"), NONE("none");

		public static RaiHarassmentLevel create(String value) {
			for (RaiHarassmentLevel raiHarassmentLevel : values()) {
				if (Objects.equals(raiHarassmentLevel.getValue(), value) ||
					Objects.equals(raiHarassmentLevel.name(), value)) {

					return raiHarassmentLevel;
				}
			}

			return null;
		}

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

	public static enum RaiHateSpeechLevel {

		HIGH("high"), LOW_AND_ABOVE("lowAndAbove"),
		MEDIUM_AND_ABOVE("mediumAndAbove"), NONE("none");

		public static RaiHateSpeechLevel create(String value) {
			for (RaiHateSpeechLevel raiHateSpeechLevel : values()) {
				if (Objects.equals(raiHateSpeechLevel.getValue(), value) ||
					Objects.equals(raiHateSpeechLevel.name(), value)) {

					return raiHateSpeechLevel;
				}
			}

			return null;
		}

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

	public static enum RaiSexuallyExplicitLevel {

		HIGH("high"), LOW_AND_ABOVE("lowAndAbove"),
		MEDIUM_AND_ABOVE("mediumAndAbove"), NONE("none");

		public static RaiSexuallyExplicitLevel create(String value) {
			for (RaiSexuallyExplicitLevel raiSexuallyExplicitLevel : values()) {
				if (Objects.equals(
						raiSexuallyExplicitLevel.getValue(), value) ||
					Objects.equals(raiSexuallyExplicitLevel.name(), value)) {

					return raiSexuallyExplicitLevel;
				}
			}

			return null;
		}

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

}
// LIFERAY-REST-BUILDER-HASH:854641146