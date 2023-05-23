/*******************************************************************************
 * Copyright (c) 2015 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 467859
 ******************************************************************************/

package org.eclipse.equinox.http.servlet.internal.util;

public class Params {

	public static String[] append(String[] params, String value) {
		if (params.length == 0) {
			return new String[] {value};
		}
		String[] tmp = new String[params.length + 1];
		System.arraycopy(params, 0, tmp, 0, params.length);
		tmp[params.length] = (value == null) ? Const.BLANK : value;
		return tmp;
	}

	public static String[] append(String[] params, String... values) {
		if (values == null) {
			values = new String[] {null};
		}
		String[] tmp = values;
		int length = 0;
		if (params != null) {
			length = params.length;
			tmp = new String[params.length + values.length];
			System.arraycopy(params, 0, tmp, 0, params.length);
		}
		for (int i = 0; i < values.length; i++) {
			tmp[length + i] =
				(values[i] == null) ? Const.BLANK : values[i];
		}
		return tmp;
	}

}
