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
import com.liferay.search.experiences.rest.client.serdes.v1_0.SearchParameterSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class SearchParameter implements Cloneable, Serializable {

	public static SearchParameter toDTO(String json) {
		return SearchParameterSerDes.toDTO(json);
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setDateFormat(
		UnsafeSupplier<String, Exception> dateFormatUnsafeSupplier) {

		try {
			dateFormat = dateFormatUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String dateFormat;

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

	public ParameterType getParameterType() {
		return parameterType;
	}

	public String getParameterTypeAsString() {
		if (parameterType == null) {
			return null;
		}

		return parameterType.toString();
	}

	public void setParameterType(ParameterType parameterType) {
		this.parameterType = parameterType;
	}

	public void setParameterType(
		UnsafeSupplier<ParameterType, Exception> parameterTypeUnsafeSupplier) {

		try {
			parameterType = parameterTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ParameterType parameterType;

	@Override
	public SearchParameter clone() throws CloneNotSupportedException {
		return (SearchParameter)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SearchParameter)) {
			return false;
		}

		SearchParameter searchParameter = (SearchParameter)object;

		return Objects.equals(toString(), searchParameter.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SearchParameterSerDes.toJSON(this);
	}

	public static enum ParameterType {

		DATE("Date"), DOUBLE("Double"), FLOAT("Float"), INTEGER("Integer"),
		INTEGER_ARRAY("IntegerArray"), LONG("Long"), LONG_ARRAY("LongArray"),
		STRING("String"), STRING_ARRAY("StringArray"), TIME_RANGE("TimeRange");

		public static ParameterType create(String value) {
			for (ParameterType parameterType : values()) {
				if (Objects.equals(parameterType.getValue(), value) ||
					Objects.equals(parameterType.name(), value)) {

					return parameterType;
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

		private ParameterType(String value) {
			_value = value;
		}

		private final String _value;

	}

}