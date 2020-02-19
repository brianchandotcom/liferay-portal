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

package com.liferay.portal.workflow.metrics.rest.client.dto.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.function.UnsafeSupplier;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.MetricSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Metric implements Cloneable {

	public static enum Unit {

		DAYS("Days"), HOURS("Hours"), MONTHS("Months"), WEEKS("Weeks"),
		YEARS("Years");

		public static Unit create(String value) {
			for (Unit unit : values()) {
				if (Objects.equals(unit.getValue(), value)) {
					return unit;
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

		private Unit(String value) {
			_value = value;
		}

		private final String _value;

	}

	public Histogram[] getHistograms() {
		return histograms;
	}

	public void setHistograms(Histogram[] histograms) {
		this.histograms = histograms;
	}

	public void setHistograms(
		UnsafeSupplier<Histogram[], Exception> histogramsUnsafeSupplier) {

		try {
			histograms = histogramsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Histogram[] histograms;

	public Unit getUnit() {
		return unit;
	}

	public String getUnitAsString() {
		if (unit == null) {
			return null;
		}

		return unit.toString();
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public void setUnit(UnsafeSupplier<Unit, Exception> unitUnsafeSupplier) {
		try {
			unit = unitUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Unit unit;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public void setValue(
		UnsafeSupplier<Double, Exception> valueUnsafeSupplier) {

		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double value;

	@Override
	public Metric clone() throws CloneNotSupportedException {
		return (Metric)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Metric)) {
			return false;
		}

		Metric metric = (Metric)object;

		return Objects.equals(toString(), metric.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return MetricSerDes.toJSON(this);
	}

}