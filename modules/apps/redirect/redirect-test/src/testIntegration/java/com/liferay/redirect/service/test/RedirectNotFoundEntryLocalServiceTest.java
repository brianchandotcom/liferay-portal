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

package com.liferay.redirect.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;
import com.liferay.redirect.test.util.RedirectTestUtil;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class RedirectNotFoundEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = _groupLocalService.getGroup(TestPropsValues.getGroupId());
	}

	@Test
	public void testAddOrUpdateRedirectNotFoundEntry() throws Exception {
		RedirectTestUtil.withRedirectEnabled(
			() -> {
				RedirectNotFoundEntry redirectNotFoundEntry =
					_addOrUpdateRedirectNotFoundEntry("url");

				Assert.assertEquals(1, redirectNotFoundEntry.getHits());
			});
	}

	@Test
	public void testAddOrUpdateRedirectNotFoundEntryWithDifferentURL()
		throws Exception {

		RedirectTestUtil.withRedirectEnabled(
			() -> {
				RedirectNotFoundEntry redirectNotFoundEntry1 =
					_addOrUpdateRedirectNotFoundEntry("url1");
				RedirectNotFoundEntry redirectNotFoundEntry2 =
					_addOrUpdateRedirectNotFoundEntry("url2");

				Assert.assertNotEquals(
					redirectNotFoundEntry1, redirectNotFoundEntry2);
				Assert.assertEquals(1, redirectNotFoundEntry1.getHits());
				Assert.assertEquals(1, redirectNotFoundEntry2.getHits());
			});
	}

	@Test
	public void testAddOrUpdateRedirectNotFoundEntryWithExistingEntry()
		throws Exception {

		RedirectTestUtil.withRedirectEnabled(
			() -> {
				RedirectNotFoundEntry redirectNotFoundEntry1 =
					_addOrUpdateRedirectNotFoundEntry("url");
				RedirectNotFoundEntry redirectNotFoundEntry2 =
					_addOrUpdateRedirectNotFoundEntry("url");

				Assert.assertEquals(
					redirectNotFoundEntry1, redirectNotFoundEntry2);
				Assert.assertEquals(2, redirectNotFoundEntry2.getHits());
			});
	}

	@Test
	public void testAddOrUpdateRedirectNotFoundEntryWithRedirectDisabled()
		throws Exception {

		RedirectTestUtil.withRedirectDisabled(
			() -> Assert.assertNull(_addOrUpdateRedirectNotFoundEntry("url")));
	}

	private RedirectNotFoundEntry _addOrUpdateRedirectNotFoundEntry(
		String url) {

		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				_group, url);

		if (redirectNotFoundEntry != null) {
			_redirectNotFoundEntries.add(redirectNotFoundEntry);
		}

		return redirectNotFoundEntry;
	}

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private final Set<RedirectNotFoundEntry> _redirectNotFoundEntries =
		new HashSet<>();

	@Inject
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}