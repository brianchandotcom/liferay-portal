/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cms.site.initializer.test.util.CMSTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class GroupModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_cmsGroup = CMSTestUtil.getOrAddGroup(GroupModelListenerTest.class);
	}

	@Test
	public void testRemoveDepotEntryHidesLayout() throws Exception {
		Layout layout = _getRecycleBinLayout();

		layout = _setLayoutHidden(layout, false);

		Assert.assertFalse(layout.isHidden());

		DepotEntry depotEntry = _addDepotEntry();

		_setTrashEnabled(depotEntry.getGroup(), Boolean.TRUE.toString());

		Assert.assertFalse(layout.isHidden());

		_depotEntryLocalService.deleteDepotEntry(depotEntry);

		layout = _getRecycleBinLayout();

		Assert.assertTrue(layout.isHidden());
	}

	@Test
	public void testUpdateDepotEntry() throws Exception {
		Layout layout = _getRecycleBinLayout();

		layout = _setLayoutHidden(layout, false);

		Assert.assertFalse(_getRecycleBinLayout().isHidden());

		DepotEntry depotEntry = _addDepotEntry();

		Group depotGroup = depotEntry.getGroup();

		_setTrashEnabled(depotGroup, Boolean.TRUE.toString());

		Assert.assertTrue(
			GetterUtil.getBoolean(
				depotGroup.getTypeSettingsProperty("trashEnabled")));

		Assert.assertFalse(layout.isHidden());

		_setTrashEnabled(depotGroup, Boolean.FALSE.toString());

		layout = _getRecycleBinLayout();

		Assert.assertFalse(
			GetterUtil.getBoolean(
				depotGroup.getTypeSettingsProperty("trashEnabled")));

		Assert.assertTrue(layout.isHidden());
	}

	private DepotEntry _addDepotEntry() throws Exception {
		return _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());
	}

	private Layout _getRecycleBinLayout() throws Exception {
		return _layoutLocalService.getLayoutByFriendlyURL(
			_cmsGroup.getGroupId(), false, "/recycle-bin");
	}

	private Layout _setLayoutHidden(Layout layout, boolean hidden)
		throws Exception {

		layout.setHidden(hidden);

		return _layoutLocalService.updateLayout(layout);
	}

	private void _setTrashEnabled(Group group, String value) throws Exception {
		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		if (value == null) {
			unicodeProperties.remove("trashEnabled");
		}
		else {
			unicodeProperties.setProperty("trashEnabled", value);
		}

		_groupLocalService.updateGroup(
			group.getGroupId(), unicodeProperties.toString());
	}

	private Group _cmsGroup;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

}