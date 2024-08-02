/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v1_1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;

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
public class PortletPreferencesUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

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

		_segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());
	}

	@Test
	public void testUpgrade() throws Exception {
		PortletPreferences portletPreferences = _getPortletPreferences(
			_addPortletFragmentEntryLink());

		Layout controlPanelLayout = LayoutTestUtil.addTypeContentLayout(_group);

		_layoutLocalService.updateType(
			controlPanelLayout.getPlid(), LayoutConstants.TYPE_CONTROL_PANEL);

		portletPreferences.setPlid(controlPanelLayout.getPlid());

		portletPreferences =
			_portletPreferencesLocalService.updatePortletPreferences(
				portletPreferences);

		Assert.assertEquals(
			controlPanelLayout.getPlid(), portletPreferences.getPlid());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.fragment.internal.upgrade.v1_1_0." +
					"PortletPreferencesUpgradeProcess",
				LoggerTestUtil.ALL)) {

			runUpgrade();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertTrue(logEntries.isEmpty());
		}

		Assert.assertNull(
			_layoutLocalService.fetchLayout(controlPanelLayout.getPlid()));

		portletPreferences =
			_portletPreferencesLocalService.fetchPortletPreferences(
				portletPreferences.getPortletPreferencesId());

		Assert.assertEquals(_layout.getPlid(), portletPreferences.getPlid());
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			"{}", _layout.fetchDraftLayout(), _segmentsExperienceId);
	}

	@Override
	protected CTService<?> getCTService() {
		return _fragmentEntryLinkLocalService;
	}

	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		FragmentEntryLink fragmentEntryLink = (FragmentEntryLink)ctModel;

		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(
			TestPropsValues.getUserId(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			JSONUtil.put(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString())
			).toString());
	}

	private String _addPortletFragmentEntryLink() throws Exception {
		JSONObject processAddPortletJSONObject =
			ContentLayoutTestUtil.addPortletToLayout(
				_layout, JournalContentPortletKeys.JOURNAL_CONTENT);

		JSONObject fragmentEntryLinkJSONObject =
			processAddPortletJSONObject.getJSONObject("fragmentEntryLink");

		JSONObject editableValuesJSONObject =
			fragmentEntryLinkJSONObject.getJSONObject("editableValues");

		return PortletIdCodec.encode(
			editableValuesJSONObject.getString("portletId"),
			editableValuesJSONObject.getString("instanceId"));
	}

	private PortletPreferences _getPortletPreferences(String portletId)
		throws Exception {

		List<PortletPreferences> portletPreferences =
			_portletPreferencesLocalService.getPortletPreferences(
				_layout.getPlid(), portletId);

		Assert.assertEquals(
			portletPreferences.toString(), 1, portletPreferences.size());

		return portletPreferences.get(0);
	}

	private static final String _CLASS_NAME =
		"com.liferay.fragment.internal.upgrade.v1_1_0." +
			"PortletPreferencesUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.fragment.internal.upgrade.registry.FragmentServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private long _segmentsExperienceId;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}