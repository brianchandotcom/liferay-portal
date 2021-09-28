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
import com.liferay.search.experiences.rest.client.serdes.v1_0.AggregationConfigurationSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class AggregationConfiguration implements Cloneable, Serializable {

	public static AggregationConfiguration toDTO(String json) {
		return AggregationConfigurationSerDes.toDTO(json);
	}

	public Object getAggs() {
		return aggs;
	}

	public void setAggs(Object aggs) {
		this.aggs = aggs;
	}

	public void setAggs(UnsafeSupplier<Object, Exception> aggsUnsafeSupplier) {
		try {
			aggs = aggsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object aggs;

	@Override
	public AggregationConfiguration clone() throws CloneNotSupportedException {
		return (AggregationConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AggregationConfiguration)) {
			return false;
		}

		AggregationConfiguration aggregationConfiguration =
			(AggregationConfiguration)object;

		return Objects.equals(toString(), aggregationConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AggregationConfigurationSerDes.toJSON(this);
	}

}