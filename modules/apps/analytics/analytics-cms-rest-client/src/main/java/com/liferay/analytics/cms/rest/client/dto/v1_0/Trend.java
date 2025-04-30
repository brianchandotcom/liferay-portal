/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.dto.v1_0;

import com.liferay.analytics.cms.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.cms.rest.client.serdes.v1_0.TrendSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class Trend implements Cloneable, Serializable {

	public static Trend toDTO(String json) {
		return TrendSerDes.toDTO(json);
	}

	public Classification getClassification() {
		return classification;
	}

	public String getClassificationAsString() {
		if (classification == null) {
			return null;
		}

		return classification.toString();
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	public void setClassification(
		UnsafeSupplier<Classification, Exception>
			classificationUnsafeSupplier) {

		try {
			classification = classificationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Classification classification;

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public void setPercentage(
		UnsafeSupplier<Double, Exception> percentageUnsafeSupplier) {

		try {
			percentage = percentageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double percentage;

	@Override
	public Trend clone() throws CloneNotSupportedException {
		return (Trend)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Trend)) {
			return false;
		}

		Trend trend = (Trend)object;

		return Objects.equals(toString(), trend.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return TrendSerDes.toJSON(this);
	}

	public static enum Classification {

		NEGATIVE("NEGATIVE"), NEUTRAL("NEUTRAL"), POSITIVE("POSITIVE");

		public static Classification create(String value) {
			for (Classification classification : values()) {
				if (Objects.equals(classification.getValue(), value) ||
					Objects.equals(classification.name(), value)) {

					return classification;
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

		private Classification(String value) {
			_value = value;
		}

		private final String _value;

	}

}