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