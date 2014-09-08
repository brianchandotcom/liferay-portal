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

package com.liferay.portal.json;

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
					"Not instantiating " + targetClassName + " at " + path);
			}
		}

		return super.newObjectInstance(targetClass);
	}

}