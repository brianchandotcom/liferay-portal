/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class EditRankingDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSetterGetter() {
		EditRankingDisplayContext editRankingDisplayContext =
			new EditRankingDisplayContext();

		Map<String, Object> data = new HashMap<>();

		editRankingDisplayContext.setCompanyId(111L);
		editRankingDisplayContext.setBackURL("backURL");
		editRankingDisplayContext.setData(data);
		editRankingDisplayContext.setFormName("formName");
		editRankingDisplayContext.setInactive(false);
		editRankingDisplayContext.setKeywords("keywords");
		editRankingDisplayContext.setRedirect("redirect");
		editRankingDisplayContext.setResultsRankingUid("resultsRankingUid");

		Assert.assertEquals(111L, editRankingDisplayContext.getCompanyId());
		Assert.assertEquals("backURL", editRankingDisplayContext.getBackURL());
		Assert.assertEquals(data, editRankingDisplayContext.getData());
		Assert.assertFalse(editRankingDisplayContext.getInactive());
		Assert.assertEquals(
			"formName", editRankingDisplayContext.getFormName());
		Assert.assertEquals(
			"keywords", editRankingDisplayContext.getKeywords());
		Assert.assertEquals(
			"redirect", editRankingDisplayContext.getRedirect());
		Assert.assertEquals(
			"resultsRankingUid",
			editRankingDisplayContext.getResultsRankingUid());
	}

}