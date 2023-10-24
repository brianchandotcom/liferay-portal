/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutBranchLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrew Betts
 */
@RunWith(Arquillian.class)
public class EditServerMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group, false);
	}

	@Test
	public void testCleanUpLayoutRevisionPortletPreferencesWithOrphanedPortletPreferences()
		throws Exception {

		LayoutRevision layoutRevision = _getLayoutRevision();

		_portletPreferences = _addPortletPreferences(
			TestPropsValues.getUserId(), 0,
			layoutRevision.getLayoutRevisionId(),
			RandomTestUtil.randomString());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "_cleanUpLayoutRevisionPortletPreferences",
			new Class<?>[0]);

		Assert.assertNull(
			_portletPreferencesLocalService.fetchPortletPreferences(
				_portletPreferences.getPortletPreferencesId()));
	}

	@Test
	public void testCleanUpLayoutRevisionPortletPreferencesWithProperPortletPreferences()
		throws Exception {

		LayoutRevision layoutRevision = _getLayoutRevision();

		String portletId = PortletIdCodec.encode(
			"com_liferay_test_portlet_TestPortlet");

		UnicodeProperties typeSettingsUnicodeProperties =
			layoutRevision.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.setProperty("column-1", portletId);

		layoutRevision = _layoutRevisionLocalService.updateLayoutRevision(
			layoutRevision);

		_portletPreferences = _addPortletPreferences(
			TestPropsValues.getUserId(), 0,
			layoutRevision.getLayoutRevisionId(), portletId);

		Layout layout = _layoutLocalService.getLayout(layoutRevision.getPlid());

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		List<String> portletIds = layoutTypePortlet.getPortletIds();

		Assert.assertTrue(portletIds.isEmpty());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "_cleanUpLayoutRevisionPortletPreferences",
			new Class<?>[0]);

		Assert.assertNotNull(
			_portletPreferencesLocalService.fetchPortletPreferences(
				_portletPreferences.getPortletPreferencesId()));
	}

	@Test
	public void testCleanUpOrphanedPortletPreferencesWithLayoutRevision()
		throws Exception {

		LayoutRevision layoutRevision = _getLayoutRevision();

		_portletPreferences = _addPortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			layoutRevision.getLayoutRevisionId(),
			RandomTestUtil.randomString());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "_cleanUpOrphanedPortletPreferences",
			new Class<?>[0]);

		Assert.assertNotNull(
			_portletPreferencesLocalService.fetchPortletPreferences(
				_portletPreferences.getPortletPreferencesId()));
	}

	@Test
	public void testCleanUpOrphanedPortletPreferencesWithoutLayoutRevision()
		throws Exception {

		_portletPreferences = _addPortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
			RandomTestUtil.randomString());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "_cleanUpOrphanedPortletPreferences",
			new Class<?>[0]);

		Assert.assertNull(
			_portletPreferencesLocalService.fetchPortletPreferences(
				_portletPreferences.getPortletPreferencesId()));
	}

	@Test
	public void testCleanUpOrphanedPortletPreferencesWithProperPortletPreferences()
		throws Exception {

		String portletId = PortletIdCodec.encode(
			"com_liferay_test_portlet_TestPortlet");

		UnicodeProperties typeSettingsUnicodeProperties =
			_layout.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.setProperty("column-1", portletId);

		_layout = _layoutLocalService.updateLayout(_layout);

		_portletPreferences = _addPortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(), portletId);

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "_cleanUpOrphanedPortletPreferences",
			new Class<?>[0]);

		Assert.assertNotNull(
			_portletPreferencesLocalService.fetchPortletPreferences(
				_portletPreferences.getPortletPreferencesId()));
	}

	private PortletPreferences _addPortletPreferences(
			long ownerId, int ownerType, long plid, String portletId)
		throws Exception {

		return _portletPreferencesLocalService.addPortletPreferences(
			TestPropsValues.getCompanyId(), ownerId, ownerType, plid, portletId,
			null, StringPool.BLANK);
	}

	private LayoutRevision _getLayoutRevision() throws Exception {
		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.addLayoutSetBranch(
				TestPropsValues.getUserId(), _group.getGroupId(), false,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				true, 0, ServiceContextTestUtil.getServiceContext());

		LayoutBranch layoutBranch =
			_layoutBranchLocalService.getMasterLayoutBranch(
				layoutSetBranch.getLayoutSetBranchId(), _layout.getPlid());

		return _layoutRevisionLocalService.getLayoutRevision(
			layoutSetBranch.getLayoutSetBranchId(),
			layoutBranch.getLayoutBranchId(), _layout.getPlid());
	}

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutBranchLocalService _layoutBranchLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Inject
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Inject(filter = "mvc.command.name=/server_admin/edit_server")
	private MVCActionCommand _mvcActionCommand;

	private PortletPreferences _portletPreferences;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}