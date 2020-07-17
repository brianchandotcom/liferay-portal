/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("ExperimentRun")
@JsonFilter("Liferay.Vulcan")
@Schema(requiredProperties = {"confidenceLevel", "experimentVariants"})
@XmlRootElement(name = "ExperimentRun")
public class ExperimentRun {

	public static ExperimentRun toDTO(String json) {
		return ObjectMapperUtil.readValue(ExperimentRun.class, json);
	}

	@DecimalMax("99")
	@DecimalMin("80")
	@Schema
	public Double getConfidenceLevel() {
		return confidenceLevel;
	}

	public void setConfidenceLevel(Double confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}

	@JsonIgnore
	public void setConfidenceLevel(
		UnsafeSupplier<Double, Exception> confidenceLevelUnsafeSupplier) {

		try {
			confidenceLevel = confidenceLevelUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotNull
	protected Double confidenceLevel;

	@Schema
	@Valid
	public ExperimentVariant[] getExperimentVariants() {
		return experimentVariants;
	}

	public void setExperimentVariants(ExperimentVariant[] experimentVariants) {
		this.experimentVariants = experimentVariants;
	}

	@JsonIgnore
	public void setExperimentVariants(
		UnsafeSupplier<ExperimentVariant[], Exception>
			experimentVariantsUnsafeSupplier) {

		try {
			experimentVariants = experimentVariantsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotNull
	protected ExperimentVariant[] experimentVariants;

	@Schema
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonIgnore
	public void setStatus(
		UnsafeSupplier<String, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String status;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ExperimentRun)) {
			return false;
		}

		ExperimentRun experimentRun = (ExperimentRun)object;

		return Objects.equals(toString(), experimentRun.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (confidenceLevel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"confidenceLevel\": ");

			sb.append(confidenceLevel);
		}

		if (experimentVariants != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"experimentVariants\": ");

			sb.append("[");

			for (int i = 0; i < experimentVariants.length; i++) {
				sb.append(String.valueOf(experimentVariants[i]));

				if ((i + 1) < experimentVariants.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (status != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(_escape(status));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.segments.asah.rest.dto.v1_0.ExperimentRun",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
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
				sb.append(value);
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}