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

package com.liferay.search.experiences.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
@GraphQLName("Field")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Field")
public class Field implements Serializable {

	public static Field toDTO(String json) {
		return ObjectMapperUtil.readValue(Field.class, json);
	}

	public static Field unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Field.class, json);
	}

	@Schema
	public Boolean getBoost() {
		return boost;
	}

	public void setBoost(Boolean boost) {
		this.boost = boost;
	}

	@JsonIgnore
	public void setBoost(
		UnsafeSupplier<Boolean, Exception> boostUnsafeSupplier) {

		try {
			boost = boostUnsafeSupplier.get();
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
	protected Boolean boost;

	@Schema
	@Valid
	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	@JsonIgnore
	public void setDefaultValue(
		UnsafeSupplier<Object, Exception> defaultValueUnsafeSupplier) {

		try {
			defaultValue = defaultValueUnsafeSupplier.get();
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
	protected Object defaultValue;

	@Schema
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@JsonIgnore
	public void setFormat(
		UnsafeSupplier<String, Exception> formatUnsafeSupplier) {

		try {
			format = formatUnsafeSupplier.get();
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
	protected String format;

	@Schema
	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	@JsonIgnore
	public void setHelpText(
		UnsafeSupplier<String, Exception> helpTextUnsafeSupplier) {

		try {
			helpText = helpTextUnsafeSupplier.get();
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
	protected String helpText;

	@Schema
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@JsonIgnore
	public void setLabel(
		UnsafeSupplier<String, Exception> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
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
	protected String label;

	@Schema
	@Valid
	public Object getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Object maxValue) {
		this.maxValue = maxValue;
	}

	@JsonIgnore
	public void setMaxValue(
		UnsafeSupplier<Object, Exception> maxValueUnsafeSupplier) {

		try {
			maxValue = maxValueUnsafeSupplier.get();
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
	protected Object maxValue;

	@Schema
	@Valid
	public Object getMinValue() {
		return minValue;
	}

	public void setMinValue(Object minValue) {
		this.minValue = minValue;
	}

	@JsonIgnore
	public void setMinValue(
		UnsafeSupplier<Object, Exception> minValueUnsafeSupplier) {

		try {
			minValue = minValueUnsafeSupplier.get();
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
	protected Object minValue;

	@Schema
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
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
	protected String name;

	@Schema
	public Boolean getNullable() {
		return nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	@JsonIgnore
	public void setNullable(
		UnsafeSupplier<Boolean, Exception> nullableUnsafeSupplier) {

		try {
			nullable = nullableUnsafeSupplier.get();
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
	protected Boolean nullable;

	@Schema
	@Valid
	public Option[] getOptions() {
		return options;
	}

	public void setOptions(Option[] options) {
		this.options = options;
	}

	@JsonIgnore
	public void setOptions(
		UnsafeSupplier<Option[], Exception> optionsUnsafeSupplier) {

		try {
			options = optionsUnsafeSupplier.get();
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
	protected Option[] options;

	@Schema
	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	@JsonIgnore
	public void setRequired(
		UnsafeSupplier<Boolean, Exception> requiredUnsafeSupplier) {

		try {
			required = requiredUnsafeSupplier.get();
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
	protected Boolean required;

	@Schema
	@Valid
	public Object getStepValue() {
		return stepValue;
	}

	public void setStepValue(Object stepValue) {
		this.stepValue = stepValue;
	}

	@JsonIgnore
	public void setStepValue(
		UnsafeSupplier<Object, Exception> stepValueUnsafeSupplier) {

		try {
			stepValue = stepValueUnsafeSupplier.get();
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
	protected Object stepValue;

	@Schema
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<String, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
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
	protected String type;

	@Schema
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@JsonIgnore
	public void setUnit(UnsafeSupplier<String, Exception> unitUnsafeSupplier) {
		try {
			unit = unitUnsafeSupplier.get();
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
	protected String unit;

	@Schema
	public String getUnitSuffix() {
		return unitSuffix;
	}

	public void setUnitSuffix(String unitSuffix) {
		this.unitSuffix = unitSuffix;
	}

	@JsonIgnore
	public void setUnitSuffix(
		UnsafeSupplier<String, Exception> unitSuffixUnsafeSupplier) {

		try {
			unitSuffix = unitSuffixUnsafeSupplier.get();
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
	protected String unitSuffix;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Field)) {
			return false;
		}

		Field field = (Field)object;

		return Objects.equals(toString(), field.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (boost != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"boost\": ");

			sb.append(boost);
		}

		if (defaultValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValue\": ");

			sb.append(String.valueOf(defaultValue));
		}

		if (format != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"format\": ");

			sb.append("\"");

			sb.append(_escape(format));

			sb.append("\"");
		}

		if (helpText != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"helpText\": ");

			sb.append("\"");

			sb.append(_escape(helpText));

			sb.append("\"");
		}

		if (label != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(label));

			sb.append("\"");
		}

		if (maxValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValue\": ");

			sb.append(String.valueOf(maxValue));
		}

		if (minValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValue\": ");

			sb.append(String.valueOf(minValue));
		}

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		if (nullable != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nullable\": ");

			sb.append(nullable);
		}

		if (options != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"options\": ");

			sb.append("[");

			for (int i = 0; i < options.length; i++) {
				sb.append(String.valueOf(options[i]));

				if ((i + 1) < options.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (required != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\": ");

			sb.append(required);
		}

		if (stepValue != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"stepValue\": ");

			sb.append(String.valueOf(stepValue));
		}

		if (type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(type));

			sb.append("\"");
		}

		if (unit != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"unit\": ");

			sb.append("\"");

			sb.append(_escape(unit));

			sb.append("\"");
		}

		if (unitSuffix != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"unitSuffix\": ");

			sb.append("\"");

			sb.append(_escape(unitSuffix));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.search.experiences.rest.dto.v1_0.Field",
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
			sb.append("\": ");

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
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}