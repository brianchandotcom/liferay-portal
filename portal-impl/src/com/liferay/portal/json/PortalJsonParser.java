/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json;

import com.liferay.petra.string.StringBundler;

import jodd.json.JsonException;
import jodd.json.JsonParser;

/**
 * @author Igor Spasic
 */
public class PortalJsonParser extends JsonParser {

	@Override
	protected Object newObjectInstance(
		@SuppressWarnings("rawtypes") Class targetClass) {

		if (targetClass != null) {
			String targetClassName = targetClass.getName();

			if (targetClassName.contains("com.liferay") &&
				targetClassName.contains("Util")) {

				throw new JsonException(
					StringBundler.concat(
						"Not instantiating ", targetClassName, " at ", path));
			}
		}

		return super.newObjectInstance(targetClass);
	}

}