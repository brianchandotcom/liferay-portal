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

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class RankingTest {

	@Test
	public void testDefaults() {
		Ranking ranking = new Ranking.RankingBuilder().build();

		Assert.assertEquals("[]", String.valueOf(ranking.getAliases()));
		Assert.assertEquals("[]", String.valueOf(ranking.getBlockIds()));
		Assert.assertEquals("[]", String.valueOf(ranking.getPins()));
		Assert.assertEquals("[]", String.valueOf(ranking.getQueryStrings()));
	}

}