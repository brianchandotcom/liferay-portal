/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.pql;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public abstract class PQLModifier {

	public static Set<String> getAvailableModifiers() {
		return _availableModifiers;
	}

	public static void validateModifier(String modifier) throws Exception {
		if ((modifier == null) || !_availableModifiers.contains(modifier)) {
			throw new Exception("Invalid modifier: " + modifier);
		}
	}

	public PQLModifier(String modifier) throws Exception {
		validateModifier(modifier);

		_modifier = modifier;
	}

	public String getModifier() {
		return _modifier;
	}

	public abstract Object getPQLResult(Object pqlResultObject)
		throws Exception;

	private static final Set<String> _availableModifiers = new HashSet<>(
		Arrays.asList("NOT"));

	private final String _modifier;

}