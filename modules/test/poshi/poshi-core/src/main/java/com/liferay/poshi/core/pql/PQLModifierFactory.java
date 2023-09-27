/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.pql;

/**
 * @author Michael Hashimoto
 */
public class PQLModifierFactory {

	public static PQLModifier newPQLModifier(String modifier) throws Exception {
		PQLModifier.validateModifier(modifier);

		if (modifier.equals("NOT")) {
			return new PQLModifier(modifier) {

				public Object getPQLResult(Object pqlResultObject)
					throws Exception {

					if ((pqlResultObject == null) ||
						!(pqlResultObject instanceof Boolean)) {

						throw new Exception(
							"Modifier must be used with a boolean value: " +
								getModifier());
					}

					Boolean pqlResultBoolean = (Boolean)pqlResultObject;

					return !pqlResultBoolean;
				}

			};
		}

		throw new Exception("Unsupported modifier: " + modifier);
	}

}