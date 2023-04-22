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

package com.liferay.osb.testray.rest.client.dto.v1_0;

import com.liferay.osb.testray.rest.client.function.UnsafeSupplier;
import com.liferay.osb.testray.rest.client.serdes.v1_0.CompareRunsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author José Abelenda
 * @generated
 */
@Generated("")
public class CompareRuns implements Cloneable, Serializable {

	public static CompareRuns toDTO(String json) {
		return CompareRunsSerDes.toDTO(json);
	}

	public String[] getDueStatuses() {
		return dueStatuses;
	}

	public void setDueStatuses(String[] dueStatuses) {
		this.dueStatuses = dueStatuses;
	}

	public void setDueStatuses(
		UnsafeSupplier<String[], Exception> dueStatusesUnsafeSupplier) {

		try {
			dueStatuses = dueStatusesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] dueStatuses;

	public Object getValues() {
		return values;
	}

	public void setValues(Object values) {
		this.values = values;
	}

	public void setValues(
		UnsafeSupplier<Object, Exception> valuesUnsafeSupplier) {

		try {
			values = valuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object values;

	@Override
	public CompareRuns clone() throws CloneNotSupportedException {
		return (CompareRuns)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CompareRuns)) {
			return false;
		}

		CompareRuns compareRuns = (CompareRuns)object;

		return Objects.equals(toString(), compareRuns.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CompareRunsSerDes.toJSON(this);
	}

}