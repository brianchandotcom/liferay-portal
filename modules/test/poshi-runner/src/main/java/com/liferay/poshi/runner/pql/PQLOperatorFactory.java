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

	public static PQLOperator newPQLOperator(String operator) throws Exception {
		PQLOperator.validateOperator(operator);

		if (operator.equals("AND") || operator.equals("OR")) {
			return new PQLOperator(operator) {

				public Object getValue(
						PQLEntity pqlEntity1, PQLEntity pqlEntity2,
						Properties properties)
					throws Exception {

					String operator = getOperator();
					Object objectValue1 = pqlEntity1.getValue(properties);
					Object objectValue2 = pqlEntity2.getValue(properties);

					if ((objectValue1 == null) || (objectValue2 == null)) {
						throw new Exception(
							"Operators must be surrounded by 2 boolean " +
								"values: " + operator);
					}

					if (!(objectValue1 instanceof Boolean) ||
						!(objectValue2 instanceof Boolean)) {

						throw new Exception(
							"Operators must be surrounded by 2 boolean " +
								"values: " + operator);
					}

					Boolean booleanValue1 = (Boolean)objectValue1;
					Boolean booleanValue2 = (Boolean)objectValue2;

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
					Object objectValue1 = pqlEntity1.getValue(properties);
					Object objectValue2 = pqlEntity2.getValue(properties);

					if ((objectValue1 == null) || (objectValue2 == null)) {
						return false;
					}

					if (!(objectValue1 instanceof String) ||
						!(objectValue2 instanceof String)) {

						throw new Exception(
							"Operator only works for string values: " +
								operator);
					}

					String stringValue1 = (String)objectValue1;
					String stringValue2 = (String)objectValue2;

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
					Object objectValue1 = pqlEntity1.getValue(properties);
					Object objectValue2 = pqlEntity2.getValue(properties);

					if ((objectValue1 == null) || (objectValue2 == null)) {
						return false;
					}

					if (operator.equals("==")) {
						return objectValue1.equals(objectValue2);
					}
					else if (operator.equals("!=")) {
						return !objectValue1.equals(objectValue2);
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
					Object objectValue1 = pqlEntity1.getValue(properties);
					Object objectValue2 = pqlEntity2.getValue(properties);

					if ((objectValue1 == null) || (objectValue2 == null)) {
						throw new Exception(
							"Operator only works for number values: " +
								operator);
					}

					if ((objectValue1 instanceof Double ||
						 objectValue1 instanceof Integer) &&
						(objectValue2 instanceof Double ||
						 objectValue2 instanceof Integer)) {

						Double doubleValue1 = null;
						Double doubleValue2 = null;

						if (objectValue1 instanceof Integer) {
							Integer integerValue1 = (Integer)objectValue1;

							doubleValue1 = integerValue1.doubleValue();
						}
						else {
							doubleValue1 = (Double)objectValue1;
						}

						if (objectValue2 instanceof Integer) {
							Integer integerValue2 = (Integer)objectValue2;

							doubleValue2 = integerValue2.doubleValue();
						}
						else {
							doubleValue2 = (Double)objectValue2;
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