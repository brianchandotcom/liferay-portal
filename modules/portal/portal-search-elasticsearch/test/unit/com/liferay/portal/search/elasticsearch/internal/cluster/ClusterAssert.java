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

package com.liferay.portal.search.elasticsearch.internal.cluster;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture.Index;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

import org.elasticsearch.indices.recovery.RecoveryState;
import org.elasticsearch.indices.recovery.RecoveryState.Type;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class ClusterAssert {

	public static void assertShards(Index index, Type... types)
		throws Exception {

		RecoveryState[] recoveryStates = index.getRecoveryStates(types.length);

		Type[] actualTypes = new Type[types.length];

		for (int i = 0; i < types.length; i++) {
			actualTypes[i] = recoveryStates[i].getType();
		}

		Type[] expectedTypes = ArrayUtil.clone(types);

		Arrays.sort(actualTypes);
		Arrays.sort(expectedTypes);

		Assert.assertEquals(
			ArrayUtils.toString(expectedTypes),
			ArrayUtils.toString(actualTypes));
	}

}