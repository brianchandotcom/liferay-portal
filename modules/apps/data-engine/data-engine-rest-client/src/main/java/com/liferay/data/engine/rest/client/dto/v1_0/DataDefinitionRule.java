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

package com.liferay.data.engine.rest.client.dto.v1_0;

import com.liferay.data.engine.rest.client.function.UnsafeSupplier;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataDefinitionRuleSerDes;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataDefinitionRule {

	public String[] getDataDefinitionFieldNames() {
		return dataDefinitionFieldNames;
	}

	public void setDataDefinitionFieldNames(String[] dataDefinitionFieldNames) {
		this.dataDefinitionFieldNames = dataDefinitionFieldNames;
	}

	public void setDataDefinitionFieldNames(
		UnsafeSupplier<String[], Exception>
			dataDefinitionFieldNamesUnsafeSupplier) {

		try {
			dataDefinitionFieldNames =
				dataDefinitionFieldNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] dataDefinitionFieldNames;

	public Map<String, Object> getDataDefinitionRuleParameters() {
		return dataDefinitionRuleParameters;
	}

	public void setDataDefinitionRuleParameters(
		Map<String, Object> dataDefinitionRuleParameters) {

		this.dataDefinitionRuleParameters = dataDefinitionRuleParameters;
	}

	public void setDataDefinitionRuleParameters(
		UnsafeSupplier<Map<String, Object>, Exception>
			dataDefinitionRuleParametersUnsafeSupplier) {

		try {
			dataDefinitionRuleParameters =
				dataDefinitionRuleParametersUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> dataDefinitionRuleParameters;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public void setRuleType(
		UnsafeSupplier<String, Exception> ruleTypeUnsafeSupplier) {

		try {
			ruleType = ruleTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String ruleType;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataDefinitionRule)) {
			return false;
		}

		DataDefinitionRule dataDefinitionRule = (DataDefinitionRule)object;

		return Objects.equals(toString(), dataDefinitionRule.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DataDefinitionRuleSerDes.toJSON(this);
	}

}