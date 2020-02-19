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
import com.liferay.segments.asah.rest.client.serdes.v1_0.ExperimentRunSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ExperimentRun implements Cloneable {

	public Double getConfidenceLevel() {
		return confidenceLevel;
	}

	public void setConfidenceLevel(Double confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}

	public void setConfidenceLevel(
		UnsafeSupplier<Double, Exception> confidenceLevelUnsafeSupplier) {

		try {
			confidenceLevel = confidenceLevelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double confidenceLevel;

	public ExperimentVariant[] getExperimentVariants() {
		return experimentVariants;
	}

	public void setExperimentVariants(ExperimentVariant[] experimentVariants) {
		this.experimentVariants = experimentVariants;
	}

	public void setExperimentVariants(
		UnsafeSupplier<ExperimentVariant[], Exception>
			experimentVariantsUnsafeSupplier) {

		try {
			experimentVariants = experimentVariantsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ExperimentVariant[] experimentVariants;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<String, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String status;

	@Override
	public ExperimentRun clone() throws CloneNotSupportedException {
		return (ExperimentRun)super.clone();
	}

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
		return ExperimentRunSerDes.toJSON(this);
	}

}