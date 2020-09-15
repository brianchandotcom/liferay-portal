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

package com.liferay.poshi.runner.pql;

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