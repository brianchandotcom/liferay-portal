/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.federation.internal.ingest;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.test.util.AssertUtils;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class TagResolverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testResolve() {
		String equivalences = StringBundler.concat(
			"DXP 7.2 SP3=DXP 7.2", StringPool.NEW_LINE,
			"Liferay DXP 7.3=DXP 7.3");

		String transformations = StringBundler.concat(
			"DXP 7.1={product=DXP,version=7.1}", StringPool.NEW_LINE,
			"DXP 7.2={product=DXP,version=7.2,year=2019}", StringPool.NEW_LINE,
			"DXP 7.2 SP3={servicePack=SP3}");

		TagResolver tagResolver = new TagResolver(
			equivalences, "tags", transformations);

		AssertUtils.assertEquals(
			"",
			HashMapBuilder.put(
				"product", "[DXP]"
			).put(
				"tags", "[DXP 7.1]"
			).put(
				"version", "[7.1]"
			).build(),
			tagResolver.resolve(Arrays.asList("DXP 7.1")));

		AssertUtils.assertEquals(
			"",
			HashMapBuilder.put(
				"product", "[DXP]"
			).put(
				"tags", "[DXP 7.2]"
			).put(
				"version", "[7.2]"
			).put(
				"year", "[2019]"
			).build(),
			tagResolver.resolve(Arrays.asList("DXP 7.2")));

		AssertUtils.assertEquals(
			"",
			HashMapBuilder.put(
				"product", "[DXP]"
			).put(
				"servicePack", "[SP3]"
			).put(
				"tags", "[DXP 7.2]"
			).put(
				"version", "[7.2]"
			).put(
				"year", "[2019]"
			).build(),
			tagResolver.resolve(Arrays.asList("DXP 7.2 SP3")));

		AssertUtils.assertEquals(
			"",
			HashMapBuilder.put(
				"product", "[DXP]"
			).put(
				"tags", "[DXP 7.1, DXP 7.2]"
			).put(
				"version", "[7.1, 7.2]"
			).put(
				"year", "[2019]"
			).build(),
			tagResolver.resolve(Arrays.asList("DXP 7.1", "DXP 7.2")));

		AssertUtils.assertEquals(
			"",
			HashMapBuilder.put(
				"tags", "[DXP 7.3]"
			).build(),
			tagResolver.resolve(Arrays.asList("Liferay DXP 7.3")));
	}

}