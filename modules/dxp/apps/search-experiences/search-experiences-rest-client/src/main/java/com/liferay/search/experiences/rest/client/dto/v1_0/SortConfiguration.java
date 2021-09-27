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
import com.liferay.search.experiences.rest.client.serdes.v1_0.SortConfigurationSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class SortConfiguration implements Cloneable, Serializable {

	public static SortConfiguration toDTO(String json) {
		return SortConfigurationSerDes.toDTO(json);
	}

	public Object getSortsJSONArray() {
		return sortsJSONArray;
	}

	public void setSortsJSONArray(Object sortsJSONArray) {
		this.sortsJSONArray = sortsJSONArray;
	}

	public void setSortsJSONArray(
		UnsafeSupplier<Object, Exception> sortsJSONArrayUnsafeSupplier) {

		try {
			sortsJSONArray = sortsJSONArrayUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object sortsJSONArray;

	@Override
	public SortConfiguration clone() throws CloneNotSupportedException {
		return (SortConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SortConfiguration)) {
			return false;
		}

		SortConfiguration sortConfiguration = (SortConfiguration)object;

		return Objects.equals(toString(), sortConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SortConfigurationSerDes.toJSON(this);
	}

}