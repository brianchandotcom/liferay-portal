/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class RankingEntryDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSetterGetter() {
		_rankingEntryDisplayContext = new RankingEntryDisplayContext();

		_rankingEntryDisplayContext.setAliases("aliases");
		_rankingEntryDisplayContext.setGroupExternalReferenceCode(
			"groupExternalReferenceCode");
		_rankingEntryDisplayContext.setHiddenResultsCount("hiddenResultsCount");
		_rankingEntryDisplayContext.setInactive(false);
		_rankingEntryDisplayContext.setIndex("index");
		_rankingEntryDisplayContext.setKeywords("keywords");
		_rankingEntryDisplayContext.setPinnedResultsCount("pinnedResultsCount");
		_rankingEntryDisplayContext.setSXPBlueprintExternalReferenceCode(
			"sxpBlueprintExternalReferenceCode");
		_rankingEntryDisplayContext.setUid("uid");

		Assert.assertEquals(
			"aliases", _rankingEntryDisplayContext.getAliases());
		Assert.assertEquals(
			"groupExternalReferenceCode",
			_rankingEntryDisplayContext.getGroupExternalReferenceCode());
		Assert.assertEquals(
			"hiddenResultsCount",
			_rankingEntryDisplayContext.getHiddenResultsCount());
		Assert.assertFalse(_rankingEntryDisplayContext.getInactive());
		Assert.assertEquals("index", _rankingEntryDisplayContext.getIndex());
		Assert.assertEquals(
			"keywords", _rankingEntryDisplayContext.getKeywords());
		Assert.assertEquals(
			"pinnedResultsCount",
			_rankingEntryDisplayContext.getPinnedResultsCount());
		Assert.assertEquals(
			"sxpBlueprintExternalReferenceCode",
			_rankingEntryDisplayContext.getSXPBlueprintExternalReferenceCode());
		Assert.assertEquals("uid", _rankingEntryDisplayContext.getUid());
	}

	private RankingEntryDisplayContext _rankingEntryDisplayContext;

}