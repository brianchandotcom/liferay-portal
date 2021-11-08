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

package com.liferay.search.experiences.rest.client.dto.v1_0;

import com.liferay.search.experiences.rest.client.function.UnsafeSupplier;
import com.liferay.search.experiences.rest.client.serdes.v1_0.ValueDefinitionSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class ValueDefinition implements Cloneable, Serializable {

	public static ValueDefinition toDTO(String json) {
		return ValueDefinitionSerDes.toDTO(json);
	}

	public Double getDefaultValueDouble() {
		return defaultValueDouble;
	}

	public void setDefaultValueDouble(Double defaultValueDouble) {
		this.defaultValueDouble = defaultValueDouble;
	}

	public void setDefaultValueDouble(
		UnsafeSupplier<Double, Exception> defaultValueDoubleUnsafeSupplier) {

		try {
			defaultValueDouble = defaultValueDoubleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double defaultValueDouble;

	public Float getDefaultValueFloat() {
		return defaultValueFloat;
	}

	public void setDefaultValueFloat(Float defaultValueFloat) {
		this.defaultValueFloat = defaultValueFloat;
	}

	public void setDefaultValueFloat(
		UnsafeSupplier<Float, Exception> defaultValueFloatUnsafeSupplier) {

		try {
			defaultValueFloat = defaultValueFloatUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Float defaultValueFloat;

	public Integer getDefaultValueInteger() {
		return defaultValueInteger;
	}

	public void setDefaultValueInteger(Integer defaultValueInteger) {
		this.defaultValueInteger = defaultValueInteger;
	}

	public void setDefaultValueInteger(
		UnsafeSupplier<Integer, Exception> defaultValueIntegerUnsafeSupplier) {

		try {
			defaultValueInteger = defaultValueIntegerUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer defaultValueInteger;

	public Long getDefaultValueLong() {
		return defaultValueLong;
	}

	public void setDefaultValueLong(Long defaultValueLong) {
		this.defaultValueLong = defaultValueLong;
	}

	public void setDefaultValueLong(
		UnsafeSupplier<Long, Exception> defaultValueLongUnsafeSupplier) {

		try {
			defaultValueLong = defaultValueLongUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long defaultValueLong;

	public String getDefaultValueString() {
		return defaultValueString;
	}

	public void setDefaultValueString(String defaultValueString) {
		this.defaultValueString = defaultValueString;
	}

	public void setDefaultValueString(
		UnsafeSupplier<String, Exception> defaultValueStringUnsafeSupplier) {

		try {
			defaultValueString = defaultValueStringUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String defaultValueString;

	public Integer[] getDefaultValuesIntegerArray() {
		return defaultValuesIntegerArray;
	}

	public void setDefaultValuesIntegerArray(
		Integer[] defaultValuesIntegerArray) {

		this.defaultValuesIntegerArray = defaultValuesIntegerArray;
	}

	public void setDefaultValuesIntegerArray(
		UnsafeSupplier<Integer[], Exception>
			defaultValuesIntegerArrayUnsafeSupplier) {

		try {
			defaultValuesIntegerArray =
				defaultValuesIntegerArrayUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer[] defaultValuesIntegerArray;

	public Long[] getDefaultValuesLongArray() {
		return defaultValuesLongArray;
	}

	public void setDefaultValuesLongArray(Long[] defaultValuesLongArray) {
		this.defaultValuesLongArray = defaultValuesLongArray;
	}

	public void setDefaultValuesLongArray(
		UnsafeSupplier<Long[], Exception>
			defaultValuesLongArrayUnsafeSupplier) {

		try {
			defaultValuesLongArray = defaultValuesLongArrayUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] defaultValuesLongArray;

	public String[] getDefaultValuesStringArray() {
		return defaultValuesStringArray;
	}

	public void setDefaultValuesStringArray(String[] defaultValuesStringArray) {
		this.defaultValuesStringArray = defaultValuesStringArray;
	}

	public void setDefaultValuesStringArray(
		UnsafeSupplier<String[], Exception>
			defaultValuesStringArrayUnsafeSupplier) {

		try {
			defaultValuesStringArray =
				defaultValuesStringArrayUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] defaultValuesStringArray;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setFormat(
		UnsafeSupplier<String, Exception> formatUnsafeSupplier) {

		try {
			format = formatUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String format;

	public Double getMaxValueDouble() {
		return maxValueDouble;
	}

	public void setMaxValueDouble(Double maxValueDouble) {
		this.maxValueDouble = maxValueDouble;
	}

	public void setMaxValueDouble(
		UnsafeSupplier<Double, Exception> maxValueDoubleUnsafeSupplier) {

		try {
			maxValueDouble = maxValueDoubleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double maxValueDouble;

	public Float getMaxValueFloat() {
		return maxValueFloat;
	}

	public void setMaxValueFloat(Float maxValueFloat) {
		this.maxValueFloat = maxValueFloat;
	}

	public void setMaxValueFloat(
		UnsafeSupplier<Float, Exception> maxValueFloatUnsafeSupplier) {

		try {
			maxValueFloat = maxValueFloatUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Float maxValueFloat;

	public Integer getMaxValueInteger() {
		return maxValueInteger;
	}

	public void setMaxValueInteger(Integer maxValueInteger) {
		this.maxValueInteger = maxValueInteger;
	}

	public void setMaxValueInteger(
		UnsafeSupplier<Integer, Exception> maxValueIntegerUnsafeSupplier) {

		try {
			maxValueInteger = maxValueIntegerUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer maxValueInteger;

	public Long getMaxValueLong() {
		return maxValueLong;
	}

	public void setMaxValueLong(Long maxValueLong) {
		this.maxValueLong = maxValueLong;
	}

	public void setMaxValueLong(
		UnsafeSupplier<Long, Exception> maxValueLongUnsafeSupplier) {

		try {
			maxValueLong = maxValueLongUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long maxValueLong;

	public Double getMinValueDouble() {
		return minValueDouble;
	}

	public void setMinValueDouble(Double minValueDouble) {
		this.minValueDouble = minValueDouble;
	}

	public void setMinValueDouble(
		UnsafeSupplier<Double, Exception> minValueDoubleUnsafeSupplier) {

		try {
			minValueDouble = minValueDoubleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double minValueDouble;

	public Float getMinValueFloat() {
		return minValueFloat;
	}

	public void setMinValueFloat(Float minValueFloat) {
		this.minValueFloat = minValueFloat;
	}

	public void setMinValueFloat(
		UnsafeSupplier<Float, Exception> minValueFloatUnsafeSupplier) {

		try {
			minValueFloat = minValueFloatUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Float minValueFloat;

	public Integer getMinValueInteger() {
		return minValueInteger;
	}

	public void setMinValueInteger(Integer minValueInteger) {
		this.minValueInteger = minValueInteger;
	}

	public void setMinValueInteger(
		UnsafeSupplier<Integer, Exception> minValueIntegerUnsafeSupplier) {

		try {
			minValueInteger = minValueIntegerUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer minValueInteger;

	public Long getMinValueLong() {
		return minValueLong;
	}

	public void setMinValueLong(Long minValueLong) {
		this.minValueLong = minValueLong;
	}

	public void setMinValueLong(
		UnsafeSupplier<Long, Exception> minValueLongUnsafeSupplier) {

		try {
			minValueLong = minValueLongUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long minValueLong;

	public Double getStepValueDouble() {
		return stepValueDouble;
	}

	public void setStepValueDouble(Double stepValueDouble) {
		this.stepValueDouble = stepValueDouble;
	}

	public void setStepValueDouble(
		UnsafeSupplier<Double, Exception> stepValueDoubleUnsafeSupplier) {

		try {
			stepValueDouble = stepValueDoubleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double stepValueDouble;

	public Float getStepValueFloat() {
		return stepValueFloat;
	}

	public void setStepValueFloat(Float stepValueFloat) {
		this.stepValueFloat = stepValueFloat;
	}

	public void setStepValueFloat(
		UnsafeSupplier<Float, Exception> stepValueFloatUnsafeSupplier) {

		try {
			stepValueFloat = stepValueFloatUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Float stepValueFloat;

	public Integer getStepValueInteger() {
		return stepValueInteger;
	}

	public void setStepValueInteger(Integer stepValueInteger) {
		this.stepValueInteger = stepValueInteger;
	}

	public void setStepValueInteger(
		UnsafeSupplier<Integer, Exception> stepValueIntegerUnsafeSupplier) {

		try {
			stepValueInteger = stepValueIntegerUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer stepValueInteger;

	public Long getStepValueLong() {
		return stepValueLong;
	}

	public void setStepValueLong(Long stepValueLong) {
		this.stepValueLong = stepValueLong;
	}

	public void setStepValueLong(
		UnsafeSupplier<Long, Exception> stepValueLongUnsafeSupplier) {

		try {
			stepValueLong = stepValueLongUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long stepValueLong;

	public Type getType() {
		return type;
	}

	public String getTypeAsString() {
		if (type == null) {
			return null;
		}

		return type.toString();
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setType(UnsafeSupplier<Type, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Type type;

	@Override
	public ValueDefinition clone() throws CloneNotSupportedException {
		return (ValueDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ValueDefinition)) {
			return false;
		}

		ValueDefinition valueDefinition = (ValueDefinition)object;

		return Objects.equals(toString(), valueDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ValueDefinitionSerDes.toJSON(this);
	}

	public static enum Type {

		DATE("Date"), DOUBLE("Double"), FLOAT("Float"), INTEGER("Integer"),
		INTEGER_ARRAY("IntegerArray"), LONG("Long"), LONG_ARRAY("LongArray"),
		STRING("String"), STRING_ARRAY("StringArray"), TIME_RANGE("TimeRange");

		public static Type create(String value) {
			for (Type type : values()) {
				if (Objects.equals(type.getValue(), value) ||
					Objects.equals(type.name(), value)) {

					return type;
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

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

}