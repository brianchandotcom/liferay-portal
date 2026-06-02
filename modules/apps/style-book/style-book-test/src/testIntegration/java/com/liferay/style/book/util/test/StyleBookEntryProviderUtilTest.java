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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
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
	public void testGetStyleBookEntriesExcludesConnectedDepotEntryStyleBookEntries()
		throws Exception {

		StyleBookEntry styleBookEntry = _addStyleBookEntry(_group.getGroupId());

		StyleBookEntry depotEntryStyleBookEntry =
			_getDepotEntryStyleBookEntry();

		List<StyleBookEntry> styleBookEntries =
			StyleBookEntryProviderUtil.getStyleBookEntries(
				TestPropsValues.getCompanyId(), _group.getGroupId());

		Assert.assertFalse(
			styleBookEntries.toString(),
			styleBookEntries.contains(depotEntryStyleBookEntry));
		Assert.assertTrue(
			styleBookEntries.toString(),
			styleBookEntries.contains(styleBookEntry));
	}

	@FeatureFlags(
		featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-57283")}
	)
	@Test
	@TestInfo("LPD-88081")
	public void testGetStyleBookEntriesIncludesConnectedDepotEntryStyleBookEntries()
		throws Exception {

		StyleBookEntry styleBookEntry = _addStyleBookEntry(_group.getGroupId());

		StyleBookEntry depotEntryStyleBookEntry =
			_getDepotEntryStyleBookEntry();

		List<StyleBookEntry> styleBookEntries =
			StyleBookEntryProviderUtil.getStyleBookEntries(
				TestPropsValues.getCompanyId(), _group.getGroupId());

		Assert.assertTrue(
			styleBookEntries.toString(),
			styleBookEntries.contains(depotEntryStyleBookEntry));
		Assert.assertTrue(
			styleBookEntries.toString(),
			styleBookEntries.contains(styleBookEntry));
	}

	@Test
	@TestInfo("LPD-88081")
	public void testGetStyleBookEntriesReturnsGroupStyleBookEntries()
		throws Exception {

		StyleBookEntry styleBookEntry = _addStyleBookEntry(_group.getGroupId());

		List<StyleBookEntry> styleBookEntries =
			StyleBookEntryProviderUtil.getStyleBookEntries(
				TestPropsValues.getCompanyId(), _group.getGroupId());

		Assert.assertTrue(
			styleBookEntries.toString(),
			styleBookEntries.contains(styleBookEntry));
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

	private StyleBookEntry _addStyleBookEntry(long groupId) throws Exception {
		return _styleBookEntryLocalService.addStyleBookEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), groupId,
			false, null, RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString(), null);
	}

	private StyleBookEntry _getDepotEntryStyleBookEntry() throws Exception {
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

	private Group _group;
	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}