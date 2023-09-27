/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.extensions;

import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.util.HashMap;

/**
 * @author Raymond Augé
 */
public class BundleExtension extends HashMap<String, Object> {

	public String getInstruction(String key) {
		return GradleUtil.toString(super.get(key));
	}

	public Object instruction(String key, Object value) {
		return super.put(key, value);
	}

	/**
	 * @deprecated Replaced by {@link #instruction(String, Object)}
	 */
	@Deprecated
	@Override
	public Object put(String key, Object value) {
		return instruction(key, value);
	}

	private static final long serialVersionUID = 1L;

}