/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.pql;

import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class PQLOperatorFactory {

	public static PQLOperator newPQLOperator(String operator) throws Exception {
		PQLOperator.validateOperator(operator);

		if (operator.equals("AND") || operator.equals("OR")) {
			return new PQLOperator(operator) {

				public Object getPQLResult(
						PQLEntity pqlEntity1, PQLEntity pqlEntity2,
						Properties properties)
					throws Exception {

					String operator = getOperator();
					Object pqlResultObject1 = pqlEntity1.getPQLResult(
						properties);
					Object pqlResultObject2 = pqlEntity2.getPQLResult(
						properties);

					if ((pqlResultObject1 == null) ||
						(pqlResultObject2 == null) ||
						!(pqlResultObject1 instanceof Boolean) ||
						!(pqlResultObject2 instanceof Boolean)) {

						throw new Exception(
							"Operators must be surrounded by 2 boolean " +
								"values: " + operator);
					}

					Boolean pqlResultBoolean1 = (Boolean)pqlResultObject1;
					Boolean pqlResultBoolean2 = (Boolean)pqlResultObject2;

					if (operator.equals("AND")) {
						return pqlResultBoolean1 && pqlResultBoolean2;
					}
					else if (operator.equals("OR")) {
						return pqlResultBoolean1 || pqlResultBoolean2;
					}

					throw new Exception("Unsupported operator: " + operator);
				}

			};
		}
		else if (operator.equals("~") || operator.equals("!~")) {
			return new PQLOperator(operator) {

				public Object getPQLResult(
						PQLEntity pqlEntity1, PQLEntity pqlEntity2,
						Properties properties)
					throws Exception {

					Object pqlResultObject1 = pqlEntity1.getPQLResult(
						properties);
					Object pqlResultObject2 = pqlEntity2.getPQLResult(
						properties);

					if ((pqlResultObject1 == null) ||
						(pqlResultObject2 == null)) {

						return false;
					}

					String operator = getOperator();

					String pqlResultString1 = String.valueOf(pqlResultObject1);
					String pqlResultString2 = String.valueOf(pqlResultObject2);

					if (operator.equals("~")) {
						return pqlResultString1.contains(pqlResultString2);
					}
					else if (operator.equals("!~")) {
						return !pqlResultString1.contains(pqlResultString2);
					}

					throw new Exception("Unsupported operator: " + operator);
				}

			};
		}
		else if (operator.equals("==") || operator.equals("!=")) {
			return new PQLOperator(operator) {

				public Object getPQLResult(
						PQLEntity pqlEntity1, PQLEntity pqlEntity2,
						Properties properties)
					throws Exception {

					String operator = getOperator();
					Object pqlResultObject1 = pqlEntity1.getPQLResult(
						properties);
					Object pqlResultObject2 = pqlEntity2.getPQLResult(
						properties);

					if ((pqlResultObject1 == null) ||
						(pqlResultObject2 == null)) {

						if (operator.equals("==")) {
							return pqlResultObject1 == pqlResultObject2;
						}
						else if (operator.equals("!=")) {
							return pqlResultObject1 != pqlResultObject2;
						}
					}
					else {
						if (operator.equals("==")) {
							return pqlResultObject1.equals(pqlResultObject2);
						}
						else if (operator.equals("!=")) {
							return !pqlResultObject1.equals(pqlResultObject2);
						}
					}

					throw new Exception("Unsupported operator: " + operator);
				}

			};
		}
		else if (operator.equals("<") || operator.equals("<=") ||
				 operator.equals(">") || operator.equals(">=")) {

			return new PQLOperator(operator) {

				public Object getPQLResult(
						PQLEntity pqlEntity1, PQLEntity pqlEntity2,
						Properties properties)
					throws Exception {

					String operator = getOperator();
					Object pqlResultObject1 = pqlEntity1.getPQLResult(
						properties);
					Object pqlResultObject2 = pqlEntity2.getPQLResult(
						properties);

					if ((pqlResultObject1 == null) ||
						(pqlResultObject2 == null)) {

						throw new Exception(
							"Operator only works for number values: " +
								operator);
					}

					if ((pqlResultObject1 instanceof Double ||
						 pqlResultObject1 instanceof Integer) &&
						(pqlResultObject2 instanceof Double ||
						 pqlResultObject2 instanceof Integer)) {

						Double pqlResultDouble1 = null;
						Double pqlResultDouble2 = null;

						if (pqlResultObject1 instanceof Integer) {
							Integer pqlResultInteger1 =
								(Integer)pqlResultObject1;

							pqlResultDouble1 = pqlResultInteger1.doubleValue();
						}
						else {
							pqlResultDouble1 = (Double)pqlResultObject1;
						}

						if (pqlResultObject2 instanceof Integer) {
							Integer pqlResultInteger2 =
								(Integer)pqlResultObject2;

							pqlResultDouble2 = pqlResultInteger2.doubleValue();
						}
						else {
							pqlResultDouble2 = (Double)pqlResultObject2;
						}

						if (operator.equals("<")) {
							return pqlResultDouble1 < pqlResultDouble2;
						}
						else if (operator.equals("<=")) {
							return pqlResultDouble1 <= pqlResultDouble2;
						}
						else if (operator.equals(">")) {
							return pqlResultDouble1 > pqlResultDouble2;
						}
						else if (operator.equals(">=")) {
							return pqlResultDouble1 >= pqlResultDouble2;
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