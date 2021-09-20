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
import com.liferay.search.experiences.rest.client.serdes.v1_0.AggregationSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Aggregation implements Cloneable, Serializable {

	public static Aggregation toDTO(String json) {
		return AggregationSerDes.toDTO(json);
	}

	public DistanceType getDistanceType() {
		return distanceType;
	}

	public String getDistanceTypeAsString() {
		if (distanceType == null) {
			return null;
		}

		return distanceType.toString();
	}

	public void setDistanceType(DistanceType distanceType) {
		this.distanceType = distanceType;
	}

	public void setDistanceType(
		UnsafeSupplier<DistanceType, Exception> distanceTypeUnsafeSupplier) {

		try {
			distanceType = distanceTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DistanceType distanceType;

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setEnabled(
		UnsafeSupplier<Boolean, Exception> enabledUnsafeSupplier) {

		try {
			enabled = enabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean enabled;

	@Override
	public Aggregation clone() throws CloneNotSupportedException {
		return (Aggregation)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Aggregation)) {
			return false;
		}

		Aggregation aggregation = (Aggregation)object;

		return Objects.equals(toString(), aggregation.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AggregationSerDes.toJSON(this);
	}

	public static enum DistanceType {

		ARC("arc"), PLANE("plane");

		public static DistanceType create(String value) {
			for (DistanceType distanceType : values()) {
				if (Objects.equals(distanceType.getValue(), value) ||
					Objects.equals(distanceType.name(), value)) {

					return distanceType;
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

		private DistanceType(String value) {
			_value = value;
		}

		private final String _value;

	}

}