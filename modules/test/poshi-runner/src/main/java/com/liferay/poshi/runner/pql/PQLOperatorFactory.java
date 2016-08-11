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

package com.liferay.poshi.runner.pql;

import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class PQLOperatorFactory {

	public static PQLOperator newOperator(String operator) throws Exception {
		PQLOperator.validateOperator(operator);

		if (operator.equals("AND") || operator.equals("OR")) {
			return new PQLOperator(operator) {

				public Object getValue(
						PQLEntity pqlEntity1, PQLEntity pqlEntity2,
						Properties properties)
					throws Exception {

					String operator = getOperator();
					Object value1 = pqlEntity1.getValue(properties);
					Object value2 = pqlEntity2.getValue(properties);

					if ((value1 == null) || (value2 == null)) {
						throw new Exception(
							"Operators must be surrounded by 2 boolean " +
								"values: " + operator);
					}

					if (!(value1 instanceof Boolean) ||
						!(value2 instanceof Boolean)) {

						throw new Exception(
							"Operators must be surrounded by 2 boolean " +
								"values: " + operator);
					}

					Boolean booleanValue1 = (Boolean)value1;
					Boolean booleanValue2 = (Boolean)value2;

					if (operator.equals("AND")) {
						return (booleanValue1 && booleanValue2);
					}
					else if (operator.equals("OR")) {
						return (booleanValue1 || booleanValue2);
					}

					throw new Exception("Unsupported operator: " + operator);
				}

			};
		}
		else if (operator.equals("~") || operator.equals("!~")) {
			return new PQLOperator(operator) {

				public Object getValue(
						PQLEntity pqlEntity1, PQLEntity pqlEntity2,
						Properties properties)
					throws Exception {

					String operator = getOperator();
					Object value1 = pqlEntity1.getValue(properties);
					Object value2 = pqlEntity2.getValue(properties);

					if ((value1 == null) || (value2 == null)) {
						return false;
					}

					if (!(value1 instanceof String) ||
						!(value2 instanceof String)) {

						throw new Exception(
							"Operator only works for string values: " +
								operator);
					}

					String stringValue1 = (String)value1;
					String stringValue2 = (String)value2;

					if (operator.equals("~")) {
						return stringValue1.contains(stringValue2);
					}
					else if (operator.equals("!~")) {
						return !stringValue1.contains(stringValue2);
					}

					throw new Exception("Unsupported operator: " + operator);
				}

			};
		}
		else if (operator.equals("==") || operator.equals("!=")) {
			return new PQLOperator(operator) {

				public Object getValue(
						PQLEntity pqlEntity1, PQLEntity pqlEntity2,
						Properties properties)
					throws Exception {

					String operator = getOperator();
					Object value1 = pqlEntity1.getValue(properties);
					Object value2 = pqlEntity2.getValue(properties);

					if ((value1 == null) || (value2 == null)) {
						return false;
					}

					if (operator.equals("==")) {
						return value1.equals(value2);
					}
					else if (operator.equals("!=")) {
						return !value1.equals(value2);
					}

					throw new Exception("Unsupported operator: " + operator);
				}

			};
		}
		else if (operator.equals("<") || operator.equals("<=") ||
				 operator.equals(">") || operator.equals(">=")) {

			return new PQLOperator(operator) {

				public Object getValue(
						PQLEntity pqlEntity1, PQLEntity pqlEntity2,
						Properties properties)
					throws Exception {

					String operator = getOperator();
					Object value1 = pqlEntity1.getValue(properties);
					Object value2 = pqlEntity2.getValue(properties);

					if ((value1 == null) || (value2 == null)) {
						throw new Exception(
							"Operator only works for number values: " +
								operator);
					}

					if ((value1 instanceof Double ||
						 value1 instanceof Integer) &&
						(value2 instanceof Double ||
						 value2 instanceof Integer)) {

						Double doubleValue1 = null;
						Double doubleValue2 = null;

						if (value1 instanceof Integer) {
							Integer integerValue1 = (Integer)value1;

							doubleValue1 = integerValue1.doubleValue();
						}
						else {
							doubleValue1 = (Double)value1;
						}

						if (value2 instanceof Integer) {
							Integer integerValue2 = (Integer)value2;

							doubleValue2 = integerValue2.doubleValue();
						}
						else {
							doubleValue2 = (Double)value2;
						}

						if (operator.equals("<")) {
							return (doubleValue1 < doubleValue2);
						}
						else if (operator.equals("<=")) {
							return (doubleValue1 <= doubleValue2);
						}
						else if (operator.equals(">")) {
							return (doubleValue1 > doubleValue2);
						}
						else if (operator.equals(">=")) {
							return (doubleValue1 >= doubleValue2);
						}

						throw new Exception(
							"Unsupported operator: " + operator);
					}

					throw new Exception(
						"Operator only works for number values: " + operator);
				}

			};
		}

		throw new Exception("Unsupported operator: " + operator);
	}

}