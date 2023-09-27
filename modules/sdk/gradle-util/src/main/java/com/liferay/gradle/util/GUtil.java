/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.util;

import java.util.Collection;

/**
 * @author Peter Shin
 */
public class GUtil extends org.gradle.util.GUtil {

	public static <V, T extends Collection<? super V>> T addToCollection(
		T master, Iterable<? extends V> copy) {

		for (V element : copy) {
			master.add(element);
		}

		return master;
	}

}