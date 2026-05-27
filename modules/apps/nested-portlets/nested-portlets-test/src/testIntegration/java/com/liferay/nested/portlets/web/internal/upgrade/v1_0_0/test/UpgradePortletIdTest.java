/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.nested.portlets.web.internal.upgrade.v1_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutRevisionConstants;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetBranchConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class UpgradePortletIdTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgradeProcessUpdateNestedPortletLayoutRevisionTypeSettings()
		throws Exception {

		_enableLocalStaging();

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.addLayoutSetBranch(
				TestPropsValues.getUserId(), _group.getGroupId(), false,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				false, LayoutSetBranchConstants.ALL_BRANCHES,
				ServiceContextTestUtil.getServiceContext());

		String instanceId = RandomTestUtil.randomString();

		String oldNestedPortletId = _getPorlteId(
			_OLD_NESTED_PORTLET_ID, instanceId);

		LayoutRevision layoutRevision =
			_layoutRevisionLocalService.addLayoutRevision(
				TestPropsValues.getUserId(),
				layoutSetBranch.getLayoutSetBranchId(), 0,
				LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID,
				false, layout.getPlid(), LayoutConstants.DEFAULT_PLID, false,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null, null,
				_getTypeSettings(oldNestedPortletId), false, 0,
				layoutSetBranch.getThemeId(),
				layoutSetBranch.getColorSchemeId(), layoutSetBranch.getCss(),
				ServiceContextTestUtil.getServiceContext());

		_assertTypeSettings(
			oldNestedPortletId, layoutRevision.getTypeSettingsProperties());

		_runUpgrade();

		layoutRevision = _layoutRevisionLocalService.fetchLayoutRevision(
			layoutRevision.getLayoutRevisionId());

		_assertTypeSettings(
			_getPorlteId(_NEW_NESTED_PORTLET_ID, instanceId),
			layoutRevision.getTypeSettingsProperties());
	}

	@Test
	public void testUpgradeProcessUpdateNestedPortletLayoutTypeSettings()
		throws Exception {

		String instanceId = RandomTestUtil.randomString();

		String oldNestedPortletId = _getPorlteId(
			_OLD_NESTED_PORTLET_ID, instanceId);

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), _getTypeSettings(oldNestedPortletId));

		_assertTypeSettings(
			oldNestedPortletId, layout.getTypeSettingsProperties());

		_runUpgrade();

		layout = _layoutLocalService.fetchLayout(layout.getPlid());

		_assertTypeSettings(
			_getPorlteId(_NEW_NESTED_PORTLET_ID, instanceId),
			layout.getTypeSettingsProperties());
	}

	private void _assertTypeSettings(
		String portletId, UnicodeProperties typeSettingsUnicodeProperties) {

		Assert.assertEquals(
			5,
			StringUtil.count(
				typeSettingsUnicodeProperties.toString(), portletId));
		Assert.assertNotNull(
			typeSettingsUnicodeProperties.getProperty(portletId + "_column-1"));
		Assert.assertNotNull(
			typeSettingsUnicodeProperties.getProperty(portletId + "_column-2"));
		Assert.assertEquals(
			typeSettingsUnicodeProperties.getProperty("column-2"), portletId);
		Assert.assertNotNull(
			typeSettingsUnicodeProperties.getProperty("nested-column-ids"),
			StringBundler.concat(
				portletId, "_column-1,", portletId, "_column-2"));
	}

	private void _enableLocalStaging() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(_group.getGroupId());

		Map<String, Serializable> attributes = serviceContext.getAttributes();

		attributes.putAll(
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap());

		attributes.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.FALSE.toString()});
		attributes.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.FALSE.toString()});

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), _group, true, false, serviceContext);
	}

	private String _getPorlteId(String portletId, String instanceId) {
		return StringPool.UNDERLINE +
			PortletIdCodec.encode(portletId, instanceId) + StringPool.UNDERLINE;
	}

	private String _getTypeSettings(String portletId) {
		UnicodeProperties typeSettingsUnicodeProperties =
			_group.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.put(
			portletId + "_column-1",
			PortletIdCodec.encode(
				AssetPublisherPortletKeys.ASSET_PUBLISHER,
				RandomTestUtil.randomString()));
		typeSettingsUnicodeProperties.put(
			portletId + "_column-2",
			PortletIdCodec.encode(
				AssetPublisherPortletKeys.ASSET_PUBLISHER,
				RandomTestUtil.randomString()));
		typeSettingsUnicodeProperties.put("column-2", portletId);
		typeSettingsUnicodeProperties.put("layout-template-id", "2_columns_ii");
		typeSettingsUnicodeProperties.put(
			"nested-column-ids",
			StringBundler.concat(
				portletId, "_column-1,", portletId, "_column-2"));

		return typeSettingsUnicodeProperties.toString();
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(1, 0, 0));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}

		_multiVMPool.clear();
	}

	private static final String _NEW_NESTED_PORTLET_ID =
		"com_liferay_nested_portlets_web_portlet_NestedPortletsPortlet";

	private static final String _OLD_NESTED_PORTLET_ID = "118";

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Inject
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject(
		filter = "(&(component.name=com.liferay.nested.portlets.web.internal.upgrade.registry.NestedPortletWebUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}