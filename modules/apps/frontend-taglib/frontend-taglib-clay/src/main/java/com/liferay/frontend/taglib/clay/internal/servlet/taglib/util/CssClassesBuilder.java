/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.servlet.taglib.util;

import java.util.Set;

/**
 * @author Gabriel Lima
 */
public class CssClassesBuilder {

	public CssClassesBuilder(Set<String> cssClasses) {
		_cssClasses = cssClasses;
	}

	public CssClassesBuilder add(String cssClass) {
		_cssClasses.add(cssClass);

		return this;
	}

	public CssClassesBuilder add(String cssClass, boolean condition) {
		if (condition) {
			_cssClasses.add(cssClass);
		}

		return this;
	}

	public Set<String> build() {
		return _cssClasses;
	}

	private final Set<String> _cssClasses;

}