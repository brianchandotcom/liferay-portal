/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.util;

/**
 * @author Adolfo Pérez
 */
public class Tuple<S, T> {

	public static <U, V> Tuple<U, V> of(U first, V second) {
		return new Tuple<>(first, second);
	}

	public final S first;
	public final T second;

	private Tuple(S first, T second) {
		this.first = first;
		this.second = second;
	}

}