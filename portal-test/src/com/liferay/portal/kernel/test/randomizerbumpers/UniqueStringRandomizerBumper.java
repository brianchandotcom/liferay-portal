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

package com.liferay.portal.kernel.test.randomizerbumpers;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class UniqueStringRandomizerBumper implements RandomizerBumper<String> {

	public static final UniqueStringRandomizerBumper INSTANCE =
		new UniqueStringRandomizerBumper();

	public static void reset() {
		_randomValues.clear();
	}

	@Override
	public boolean accept(String randomValue) {
		return _randomValues.add(randomValue);
	}

	private static final Set<String> _randomValues = Collections.newSetFromMap(
		new ConcurrentHashMap<>());

}