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

package com.liferay.segments.asah.rest.client.dto.v1_0;

import com.liferay.segments.asah.rest.client.function.UnsafeSupplier;
import com.liferay.segments.asah.rest.client.serdes.v1_0.ExperimentVariantSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ExperimentVariant implements Cloneable {

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String id;

	public Double getTrafficSplit() {
		return trafficSplit;
	}

	public void setTrafficSplit(Double trafficSplit) {
		this.trafficSplit = trafficSplit;
	}

	public void setTrafficSplit(
		UnsafeSupplier<Double, Exception> trafficSplitUnsafeSupplier) {

		try {
			trafficSplit = trafficSplitUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double trafficSplit;

	@Override
	public ExperimentVariant clone() throws CloneNotSupportedException {
		return (ExperimentVariant)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ExperimentVariant)) {
			return false;
		}

		ExperimentVariant experimentVariant = (ExperimentVariant)object;

		return Objects.equals(toString(), experimentVariant.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ExperimentVariantSerDes.toJSON(this);
	}

}