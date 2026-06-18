/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.util.StyleBookEntryProviderUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Lima
 */
@RunWith(Arquillian.class)
public class StyleBookEntryProviderUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);
	}

	@Test
	@TestInfo("LPD-88081")
	public void testGetStyleBookEntries() throws Exception {
		StyleBookEntry styleBookEntry = _addStyleBookEntry(_group.getGroupId());

		StyleBookEntry depotEntryStyleBookEntry =
			_addDepotEntryStyleBookEntry();

		_testGetStyleBookEntries(
			false, depotEntryStyleBookEntry, styleBookEntry);
		_testGetStyleBookEntries(
			true, depotEntryStyleBookEntry, styleBookEntry);
	}

	@Test
	@TestInfo("LPD-88081")
	public void testGetStyleBookEntry() throws Exception {
		StyleBookEntry styleBookEntry = _addStyleBookEntry(_group.getGroupId());

		_testGetStyleBookEntry(
			styleBookEntry, styleBookEntry.getExternalReferenceCode(), null);

		Group group = GroupTestUtil.addGroup();

		_testGetStyleBookEntry(
			null, RandomTestUtil.randomString(),
			group.getExternalReferenceCode());

		StyleBookEntry groupStyleBookEntry = _addStyleBookEntry(
			group.getGroupId());

		_testGetStyleBookEntry(
			groupStyleBookEntry, groupStyleBookEntry.getExternalReferenceCode(),
			group.getExternalReferenceCode());

		_testGetStyleBookEntry(
			null, RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	private StyleBookEntry _addDepotEntryStyleBookEntry() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Group depotEntryGroup = depotEntry.getGroup();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), _group.getGroupId());

		return _addStyleBookEntry(depotEntryGroup.getGroupId());
	}

	private StyleBookEntry _addStyleBookEntry(long groupId) throws Exception {
		return _styleBookEntryLocalService.addStyleBookEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), groupId,
			false, null, RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString(), null);
	}

	private void _testGetStyleBookEntries(
			boolean connectedDepotEntriesEnabled,
			StyleBookEntry depotEntryStyleBookEntry,
			StyleBookEntry styleBookEntry)
		throws Exception {

		try (FeatureFlagTemporarySwapper featureFlagTemporarySwapper1 =
				new FeatureFlagTemporarySwapper(
					connectedDepotEntriesEnabled, "LPD-17564");
			FeatureFlagTemporarySwapper featureFlagTemporarySwapper2 =
				new FeatureFlagTemporarySwapper(
					connectedDepotEntriesEnabled, "LPD-57283")) {

			List<StyleBookEntry> styleBookEntries =
				StyleBookEntryProviderUtil.getStyleBookEntries(
					TestPropsValues.getCompanyId(), _group.getGroupId());

			Assert.assertEquals(
				styleBookEntries.toString(), connectedDepotEntriesEnabled,
				styleBookEntries.contains(depotEntryStyleBookEntry));
			Assert.assertTrue(
				styleBookEntries.toString(),
				styleBookEntries.contains(styleBookEntry));
		}
	}

	private void _testGetStyleBookEntry(
			StyleBookEntry expectedStyleBookEntry, String styleBookEntryERC,
			String styleBookEntryScopeERC)
		throws Exception {

		_layout.setStyleBookEntryERC(styleBookEntryERC);
		_layout.setStyleBookEntryScopeERC(styleBookEntryScopeERC);

		_layout = _layoutLocalService.updateLayout(_layout);

		StyleBookEntry actualStyleBookEntry =
			StyleBookEntryProviderUtil.getStyleBookEntry(_layout);

		if (expectedStyleBookEntry == null) {
			Assert.assertNull(actualStyleBookEntry);

			return;
		}

		Assert.assertEquals(
			expectedStyleBookEntry.getStyleBookEntryId(),
			actualStyleBookEntry.getStyleBookEntryId());
	}

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	private static class FeatureFlagTemporarySwapper implements AutoCloseable {

		public FeatureFlagTemporarySwapper(boolean enabled, String key) {
			_key = FeatureFlagConstants.getKey(key);

			_originalValue = PropsUtil.get(_key);

			PropsUtil.set(_key, String.valueOf(enabled));
		}

		@Override
		public void close() {
			PropsUtil.set(_key, _originalValue);
		}

		private final String _key;
		private final String _originalValue;

	}

}