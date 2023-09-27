/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class SynonymSetDisplayContextTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_synonymSetDisplayContext = new SynonymSetDisplayContext();
	}

	@Test
	public void testGetterSetter() {
		_synonymSetDisplayContext.setDisplayedSynonymSet("displayedSynonymSet");
		_synonymSetDisplayContext.setDropDownItems(
			new ArrayList<DropdownItem>());
		_synonymSetDisplayContext.setEditRenderURL("editRenderURL");
		_synonymSetDisplayContext.setSynonyms("synonyms");
		_synonymSetDisplayContext.setSynonymSetId("synonymSetId");

		Assert.assertEquals(
			"displayedSynonymSet",
			_synonymSetDisplayContext.getDisplayedSynonymSet());
		Assert.assertNotNull(_synonymSetDisplayContext.getDropdownItems());
		Assert.assertEquals(
			"editRenderURL", _synonymSetDisplayContext.getEditRenderURL());
		Assert.assertEquals(
			"synonyms", _synonymSetDisplayContext.getSynonymSet());
		Assert.assertEquals(
			"synonymSetId", _synonymSetDisplayContext.getSynonymSetId());
	}

	private SynonymSetDisplayContext _synonymSetDisplayContext;

}