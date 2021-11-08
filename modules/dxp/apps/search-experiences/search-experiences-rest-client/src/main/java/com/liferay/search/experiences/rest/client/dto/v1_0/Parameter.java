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
import com.liferay.search.experiences.rest.client.serdes.v1_0.ParameterSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Parameter implements Cloneable, Serializable {

	public static Parameter toDTO(String json) {
		return ParameterSerDes.toDTO(json);
	}

	public ValueDefinition getValueDefinition() {
		return valueDefinition;
	}

	public void setValueDefinition(ValueDefinition valueDefinition) {
		this.valueDefinition = valueDefinition;
	}

	public void setValueDefinition(
		UnsafeSupplier<ValueDefinition, Exception>
			valueDefinitionUnsafeSupplier) {

		try {
			valueDefinition = valueDefinitionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ValueDefinition valueDefinition;

	@Override
	public Parameter clone() throws CloneNotSupportedException {
		return (Parameter)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Parameter)) {
			return false;
		}

		Parameter parameter = (Parameter)object;

		return Objects.equals(toString(), parameter.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ParameterSerDes.toJSON(this);
	}

}