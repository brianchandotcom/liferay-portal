/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
public class ExperimentVariant {

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