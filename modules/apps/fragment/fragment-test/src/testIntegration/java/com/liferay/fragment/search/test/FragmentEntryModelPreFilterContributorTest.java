/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentEntryField;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
 * @author Javier Moral
 */
@RunWith(Arquillian.class)
public class FragmentEntryModelPreFilterContributorTest {

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
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		BooleanFilter booleanFilter = new BooleanFilter();

		_fragmentEntryModelPreFilterContributor.contribute(
			booleanFilter, null, searchContext);

		String booleanFilterString = booleanFilter.toString();

		Assert.assertFalse(
			booleanFilterString,
			booleanFilterString.contains(FragmentEntryField.HEAD_LISTABLE));
		Assert.assertTrue(
			booleanFilterString,
			booleanFilterString.contains(FragmentEntryField.HEAD));

		searchContext.setAttribute(
			FragmentEntryField.HEAD_LISTABLE, Boolean.TRUE);

		booleanFilter = new BooleanFilter();

		_fragmentEntryModelPreFilterContributor.contribute(
			booleanFilter, null, searchContext);

		booleanFilterString = booleanFilter.toString();

		Assert.assertTrue(
			booleanFilterString,
			booleanFilterString.contains(FragmentEntryField.HEAD_LISTABLE));

		searchContext.setAttribute(FragmentEntryField.HEAD, Boolean.FALSE);
		searchContext.setAttribute(
			FragmentEntryField.HEAD_LISTABLE, Boolean.FALSE);

		booleanFilter = new BooleanFilter();

		_fragmentEntryModelPreFilterContributor.contribute(
			booleanFilter, null, searchContext);

		booleanFilterString = booleanFilter.toString();

		Assert.assertFalse(
			booleanFilterString,
			booleanFilterString.contains(FragmentEntryField.HEAD));

		searchContext.setAttribute(
			FragmentEntryField.FRAGMENT_COLLECTION_ID,
			RandomTestUtil.randomLong());

		booleanFilter = new BooleanFilter();

		_fragmentEntryModelPreFilterContributor.contribute(
			booleanFilter, null, searchContext);

		booleanFilterString = booleanFilter.toString();

		Assert.assertTrue(
			booleanFilterString,
			booleanFilterString.contains(
				FragmentEntryField.FRAGMENT_COLLECTION_ID));
	}

	@Inject(
		filter = "indexer.class.name=com.liferay.fragment.model.FragmentEntry"
	)
	private ModelPreFilterContributor _fragmentEntryModelPreFilterContributor;

	@DeleteAfterTestRun
	private Group _group;

}