/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search.spi.model.query.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class DLFolderModelPreFilterContributorTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testContribute() throws Exception {
		BooleanFilter booleanFilter = new BooleanFilter();

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		_dlFolderModelPreFilterContributor.contribute(
			booleanFilter, null, searchContext);

		String booleanFilterString = booleanFilter.toString();

		Assert.assertTrue(
			booleanFilterString, booleanFilterString.contains("hidden"));

		booleanFilter = new BooleanFilter();

		searchContext.setAttribute("showHidden", Boolean.TRUE);

		_dlFolderModelPreFilterContributor.contribute(
			booleanFilter, null, searchContext);

		booleanFilterString = booleanFilter.toString();

		Assert.assertFalse(
			booleanFilterString, booleanFilterString.contains("hidden"));
	}

	@Inject(
		filter = "indexer.class.name=com.liferay.document.library.kernel.model.DLFolder"
	)
	private ModelPreFilterContributor _dlFolderModelPreFilterContributor;

	@DeleteAfterTestRun
	private Group _group;

}