/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.internal.upgrade.v1_4_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.test.util.DisplayPageTemplateTestUtil;
import com.liferay.layout.page.template.test.util.LayoutPageTemplateTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class LayoutUtilityPageEntryLayoutFriendlyURLUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	@TestInfo("LPD-71983")
	public void testUpgradeProcess() throws Exception {
		String name = String.valueOf(RandomTestUtil.randomInt());

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				"LFR-" + RandomTestUtil.randomString(),
				TestPropsValues.getUserId(), _group.getGroupId(), 0, 0, false,
				name, LayoutUtilityPageEntryConstants.TYPE_STATUS, null,
				_serviceContext);

		Layout[] layouts = _getLayouts();

		_updateFriendlyURL(
			ArrayUtil.append(
				layouts,
				_layoutLocalService.fetchLayout(
					layoutUtilityPageEntry.getPlid())));

		_runUpgrade();

		Layout layout = _layoutLocalService.fetchLayout(
			layoutUtilityPageEntry.getPlid());

		Assert.assertEquals(
			StringBundler.concat(StringPool.SLASH, name, StringPool.DASH, 1),
			layout.getFriendlyURL());

		for (Layout curLayout : layouts) {
			Layout updatedLayout = _layoutLocalService.getLayout(
				curLayout.getPlid());

			Assert.assertEquals(
				StringPool.SLASH + curLayout.getLayoutId(),
				updatedLayout.getFriendlyURL());
		}
	}

	private Layout[] _getLayouts() throws Exception {
		LayoutPageTemplateEntry basicLayoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_group.getGroupId(), LayoutPageTemplateEntryTypeConstants.BASIC,
				WorkflowConstants.STATUS_APPROVED);
		LayoutPageTemplateEntry displayPageLayoutPageTemplateEntry =
			DisplayPageTemplateTestUtil.addDisplayPageTemplate(
				_group.getGroupId());
		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_group.getGroupId(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
				WorkflowConstants.STATUS_APPROVED);
		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
				false, RandomTestUtil.randomString(),
				LayoutUtilityPageEntryConstants.TYPE_STATUS, null,
				_serviceContext);

		return new Layout[] {
			_layoutLocalService.getLayout(
				basicLayoutPageTemplateEntry.getPlid()),
			_layoutLocalService.getLayout(
				displayPageLayoutPageTemplateEntry.getPlid()),
			_layoutLocalService.getLayout(
				masterLayoutPageTemplateEntry.getPlid()),
			_layoutLocalService.getLayout(layoutUtilityPageEntry.getPlid()),
			LayoutTestUtil.addTypeContentLayout(_group),
			LayoutTestUtil.addTypePortletLayout(_group)
		};
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(1, 4, 1));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}

		_multiVMPool.clear();
	}

	private void _updateFriendlyURL(Layout... layouts) throws Exception {
		for (Layout layout : layouts) {
			String friendlyURL = StringPool.SLASH + layout.getLayoutId();

			Layout updatedLayout = _layoutLocalService.updateFriendlyURL(
				layout.getUserId(), layout.getPlid(), friendlyURL,
				layout.getDefaultLanguageId());

			Assert.assertEquals(friendlyURL, updatedLayout.getFriendlyURL());
		}
	}

	@Inject(
		filter = "(&(component.name=com.liferay.layout.utility.page.internal.upgrade.registry.LayoutUtilityPageEntryUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	private ServiceContext _serviceContext;

}