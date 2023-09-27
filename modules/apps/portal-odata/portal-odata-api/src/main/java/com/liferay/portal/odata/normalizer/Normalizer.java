/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.normalizer;

/**
 * Utility class for normalizing OData values.
 *
 * @author Eduardo García
 */
public class Normalizer {

	/**
	 * Returns a valid Simple Identifier, according to the OData standard. See
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