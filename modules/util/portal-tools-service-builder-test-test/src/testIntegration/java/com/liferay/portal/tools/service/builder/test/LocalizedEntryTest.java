/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TransactionTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.model.LocalizedEntry;
import com.liferay.portal.tools.service.builder.test.model.LocalizedEntryLocalization;
import com.liferay.portal.tools.service.builder.test.service.LocalizedEntryLocalService;
import com.liferay.portal.tools.service.builder.test.service.persistence.LocalizedEntryLocalizationPersistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class LocalizedEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddLocalizedEntryLocalization() throws Exception {
		_localizedEntry = _localizedEntryLocalService.addLocalizedEntry(
			_localizedEntryLocalService.createLocalizedEntry(
				RandomTestUtil.nextLong()));

		LocalizedEntryLocalization localizedEntryLocalization =
			_localizedEntryLocalService.addLocalizedEntryLocalization(
				_localizedEntry, "en_US", "Title", "Content");

		Assert.assertEquals(
			localizedEntryLocalization,
			_localizedEntryLocalService.fetchLocalizedEntryLocalization(
				_localizedEntry.getLocalizedEntryId(), "en_US"));
		Assert.assertEquals("Title", localizedEntryLocalization.getTitle());
		Assert.assertEquals("Content", localizedEntryLocalization.getContent());
	}

	@Test
	public void testFindByLocalizedEntryId() throws Exception {
		LocalizedEntry localizedEntry1 = _addLocalizedEntry();
		LocalizedEntry localizedEntry2 = _addLocalizedEntry();
		LocalizedEntry localizedEntry3 = _addLocalizedEntry();

		LocalizedEntryLocalization localizedEntryLocalization1 =
			_localizedEntryLocalService.addLocalizedEntryLocalization(
				localizedEntry1, "en_US", RandomTestUtil.randomString(),
				RandomTestUtil.randomString());
		LocalizedEntryLocalization localizedEntryLocalization2 =
			_localizedEntryLocalService.addLocalizedEntryLocalization(
				localizedEntry2, "en_US", RandomTestUtil.randomString(),
				RandomTestUtil.randomString());
		LocalizedEntryLocalization localizedEntryLocalization3 =
			_localizedEntryLocalService.addLocalizedEntryLocalization(
				localizedEntry1, "es_ES", RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		_localizedEntryLocalService.addLocalizedEntryLocalization(
			localizedEntry3, "en_US", RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		Assert.assertEquals(
			Arrays.asList(
				localizedEntryLocalization1, localizedEntryLocalization2,
				localizedEntryLocalization3),
			TransactionTestUtil.invoke(
				_localizedEntryLocalizationPersistence,
				localizedEntryLocalizationPersistence ->
					localizedEntryLocalizationPersistence.
						findByLocalizedEntryId(
							new long[] {
								localizedEntry1.getLocalizedEntryId(),
								localizedEntry2.getLocalizedEntryId()
							})));
	}

	private LocalizedEntry _addLocalizedEntry() {
		LocalizedEntry localizedEntry =
			_localizedEntryLocalService.addLocalizedEntry(
				_localizedEntryLocalService.createLocalizedEntry(
					RandomTestUtil.nextLong()));

		_localizedEntries.add(localizedEntry);

		return localizedEntry;
	}

	@DeleteAfterTestRun
	private final List<LocalizedEntry> _localizedEntries = new ArrayList<>();

	@DeleteAfterTestRun
	private LocalizedEntry _localizedEntry;

	@Inject
	private LocalizedEntryLocalService _localizedEntryLocalService;

	@Inject
	private LocalizedEntryLocalizationPersistence
		_localizedEntryLocalizationPersistence;

}