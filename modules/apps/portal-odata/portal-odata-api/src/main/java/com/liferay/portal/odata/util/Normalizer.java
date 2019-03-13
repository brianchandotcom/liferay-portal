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

package com.liferay.portal.odata.util;

/**
 * Utility class for normalizing OData values.
 *
 * @author Eduardo García
 */
public class Normalizer {

	/**
	 * Returns a valid Simple Identifier, according to the Odata standard. See
	 * <a href="SimpleIdentifier">http://docs.oasis-open.org/odata/odata/
	 * v4.0/errata03/os/complete/part3-csdl/odata-v4.0-errata03-os-part3-csdl-
	 * complete.html#_SimpleIdentifier</a>
	 *
	 * @param  identifier the original identifier
	 * @return the valid identifier
	 * @review
	 */
	public static String normalizeIdentifier(String identifier) {
		if (identifier == null) {
			return null;
		}

		identifier = identifier.replaceAll("[ ]", "_");

		return identifier.replaceAll(
			"[^\\p{L}\\p{Nl}\\p{Nd}\\p{Mn}\\p{Mc}\\p{Pc}\\p{Cf}]", "");
	}

}