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

package com.liferay.data.engine.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@GraphQLName("DataDefinitionRule")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DataDefinitionRule")
public class DataDefinitionRule {

	@Schema
	public String[] getDataDefinitionFieldNames() {
		return dataDefinitionFieldNames;
	}

	public void setDataDefinitionFieldNames(String[] dataDefinitionFieldNames) {
		this.dataDefinitionFieldNames = dataDefinitionFieldNames;
	}

	@JsonIgnore
	public void setDataDefinitionFieldNames(
		UnsafeSupplier<String[], Exception>
			dataDefinitionFieldNamesUnsafeSupplier) {

		try {
			dataDefinitionFieldNames =
				dataDefinitionFieldNamesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] dataDefinitionFieldNames;

	@Schema
	public DataDefinitionRuleParameter[] getDataDefinitionRuleParameters() {
		return dataDefinitionRuleParameters;
	}

	public void setDataDefinitionRuleParameters(
		DataDefinitionRuleParameter[] dataDefinitionRuleParameters) {

		this.dataDefinitionRuleParameters = dataDefinitionRuleParameters;
	}

	@JsonIgnore
	public void setDataDefinitionRuleParameters(
		UnsafeSupplier<DataDefinitionRuleParameter[], Exception>
			dataDefinitionRuleParametersUnsafeSupplier) {

		try {
			dataDefinitionRuleParameters =
				dataDefinitionRuleParametersUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DataDefinitionRuleParameter[] dataDefinitionRuleParameters;

	@Schema
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	@Schema
	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	@JsonIgnore
	public void setRuleType(
		UnsafeSupplier<String, Exception> ruleTypeUnsafeSupplier) {

		try {
			ruleType = ruleTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
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
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (dataDefinitionFieldNames != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionFieldNames\": ");

			sb.append("[");

			for (int i = 0; i < dataDefinitionFieldNames.length; i++) {
				sb.append("\"");

				sb.append(_escape(dataDefinitionFieldNames[i]));

				sb.append("\"");

				if ((i + 1) < dataDefinitionFieldNames.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataDefinitionRuleParameters != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionRuleParameters\": ");

			sb.append("[");

			for (int i = 0; i < dataDefinitionRuleParameters.length; i++) {
				sb.append(String.valueOf(dataDefinitionRuleParameters[i]));

				if ((i + 1) < dataDefinitionRuleParameters.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		if (ruleType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ruleType\": ");

			sb.append("\"");

			sb.append(_escape(ruleType));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

}