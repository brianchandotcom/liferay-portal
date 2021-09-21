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
import com.liferay.search.experiences.rest.client.serdes.v1_0.CardinalitySerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Cardinality implements Cloneable, Serializable {

	public static Cardinality toDTO(String json) {
		return CardinalitySerDes.toDTO(json);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setField(
		UnsafeSupplier<String, Exception> fieldUnsafeSupplier) {

		try {
			field = fieldUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String field;

	public Integer getPrecision_threshold() {
		return precision_threshold;
	}

	public void setPrecision_threshold(Integer precision_threshold) {
		this.precision_threshold = precision_threshold;
	}

	public void setPrecision_threshold(
		UnsafeSupplier<Integer, Exception> precision_thresholdUnsafeSupplier) {

		try {
			precision_threshold = precision_thresholdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer precision_threshold;

	@Override
	public Cardinality clone() throws CloneNotSupportedException {
		return (Cardinality)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Cardinality)) {
			return false;
		}

		Cardinality cardinality = (Cardinality)object;

		return Objects.equals(toString(), cardinality.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CardinalitySerDes.toJSON(this);
	}

}