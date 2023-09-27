/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.SearchMapUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrew Betts
 */
@RunWith(Arquillian.class)
public class FacetedSearcherTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSearchByKeywords() throws Exception {
		Group group = userSearchFixture.addGroup();

		String tag = randomString();

		User user = addUser(group, tag);

		SearchContext searchContext = getSearchContext(tag);

		Map<String, String> expected = toMap(user, tag);

		assertTags(tag, expected, searchContext);
	}

	@Test
	public void testSearchByKeywordsIgnoresInactiveSites() throws Exception {
		Group group1 = userSearchFixture.addGroup();

		String prefix = randomString();

		String tag1 = prefix + " " + randomString();

		User user1 = addUser(group1, tag1);

		Group group2 = userSearchFixture.addGroup();

		String tag2 = prefix + " " + randomString();

		User user2 = addUser(group2, tag2);

		assertSearchGroupIdsUnset(
			prefix, SearchMapUtil.join(toMap(user1, tag1), toMap(user2, tag2)));

		deactivate(group1);

		assertSearchGroupIdsUnset(prefix, toMap(user2, tag2));

		deactivate(group2);

		assertSearchGroupIdsUnset(
			prefix, Collections.<String, String>emptyMap());
	}

	@Test
	public void testSearchByQuotedRegexKeywords() throws Exception {
		Group group = userSearchFixture.addGroup();

		String tag = randomString();

		User user = addUser(group, tag);

		String[] regexSymbols = {"(", ")", "*", "[", "]", "{", "}"};

		for (String regexSymbol : regexSymbols) {
			assertTags(
				tag, toMap(user, tag),
				getSearchContext(
					StringBundler.concat("\"", tag, regexSymbol, "\"")));
		}
	}

	protected static String randomString() {
		return RandomTestUtil.randomString(
			NumericStringRandomizerBumper.INSTANCE);
	}

	protected void assertSearchGroupIdsUnset(
			String keywords, Map<String, String> expected)
		throws Exception {

		SearchContext searchContext = getSearchContextWithGroupIdsUnset(
			keywords);

		assertTags(keywords, expected, searchContext);
	}

	protected void assertTags(
			String keywords, Map<String, String> expected,
			SearchContext searchContext)
		throws Exception {

		String[] userClassName = {User.class.getName()};

		searchContext.setEntryClassNames(userClassName);

		Hits hits = search(searchContext);

		assertTags(keywords, hits, expected, searchContext);
	}

	protected void deactivate(Group group) {
		group.setActive(false);

		GroupLocalServiceUtil.updateGroup(group);
	}

}