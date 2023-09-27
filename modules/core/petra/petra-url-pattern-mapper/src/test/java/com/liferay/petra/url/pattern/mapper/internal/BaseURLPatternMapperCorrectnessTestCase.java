/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.url.pattern.mapper.internal;

import com.liferay.petra.url.pattern.mapper.URLPatternMapper;

import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public abstract class BaseURLPatternMapperCorrectnessTestCase
	extends BaseURLPatternMapperTestCase {

	@Test
	public void testGetValue() {
		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			createValues());

		for (Map.Entry<String, Integer[]> entry :
				expectedURLPatternIndexesMap.entrySet()) {

			Integer[] expectedURLPatternIndexes = entry.getValue();
			String urlPath = entry.getKey();

			if (expectedURLPatternIndexes.length == 0) {
				Assert.assertNull(urlPatternMapper.getValue(urlPath));

				continue;
			}

			Assert.assertEquals(
				expectedURLPatternIndexes[0],
				urlPatternMapper.getValue(urlPath));
		}
	}

	@Test
	public void testGetValues() {
		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			createValues());

		for (Map.Entry<String, Integer[]> entry :
				expectedURLPatternIndexesMap.entrySet()) {

			Integer[] expectedURLPatternIndexes = entry.getValue();

			String urlPath = entry.getKey();

			Set<Integer> actualURLPatternIndexes = urlPatternMapper.getValues(
				urlPath);

			if (expectedURLPatternIndexes.length == 0) {
				Assert.assertTrue(actualURLPatternIndexes.isEmpty());

				continue;
			}

			for (int expectedURLPatternIndex : expectedURLPatternIndexes) {
				Assert.assertTrue(
					actualURLPatternIndexes.remove(expectedURLPatternIndex));
			}

			Assert.assertTrue(actualURLPatternIndexes.isEmpty());
		}
	}

}