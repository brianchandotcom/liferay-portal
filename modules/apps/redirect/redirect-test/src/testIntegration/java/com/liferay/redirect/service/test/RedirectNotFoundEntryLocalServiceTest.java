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
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;

import java.util.Dictionary;

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
		_withRedirectEnabled(
			() -> {
				_redirectNotFoundEntry =
					_redirectNotFoundEntryLocalService.
						addOrUpdateRedirectNotFoundEntry(_group, "url");

				Assert.assertEquals(1, _redirectNotFoundEntry.getHits());
			});
	}

	@Test
	public void testAddOrUpdateRedirectNotFoundEntryWithDifferentURL()
		throws Exception {

		_withRedirectEnabled(
			() -> {
				_redirectNotFoundEntry =
					_redirectNotFoundEntryLocalService.
						addOrUpdateRedirectNotFoundEntry(_group, "url1");

				RedirectNotFoundEntry redirectNotFoundEntry =
					_redirectNotFoundEntryLocalService.
						addOrUpdateRedirectNotFoundEntry(_group, "url2");

				Assert.assertNotEquals(
					_redirectNotFoundEntry, redirectNotFoundEntry);
				Assert.assertEquals(1, _redirectNotFoundEntry.getHits());
				Assert.assertEquals(1, redirectNotFoundEntry.getHits());
			});
	}

	@Test
	public void testAddOrUpdateRedirectNotFoundEntryWithExistingEntry()
		throws Exception {

		_withRedirectEnabled(
			() -> {
				_redirectNotFoundEntry =
					_redirectNotFoundEntryLocalService.
						addOrUpdateRedirectNotFoundEntry(_group, "url");

				RedirectNotFoundEntry redirectNotFoundEntry =
					_redirectNotFoundEntryLocalService.
						addOrUpdateRedirectNotFoundEntry(_group, "url");

				Assert.assertEquals(
					_redirectNotFoundEntry, redirectNotFoundEntry);
				Assert.assertEquals(2, redirectNotFoundEntry.getHits());
			});
	}

	private void _withRedirectEnabled(UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("enabled", true);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.redirect.web.internal.configuration." +
						"FFRedirectConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private RedirectNotFoundEntry _redirectNotFoundEntry;

	@Inject
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}