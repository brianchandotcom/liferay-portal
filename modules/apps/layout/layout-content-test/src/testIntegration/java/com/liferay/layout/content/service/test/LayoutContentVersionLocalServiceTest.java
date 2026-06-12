/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.content.exception.LayoutContentVersionExternalReferenceCodeException;
import com.liferay.layout.content.exception.LayoutContentVersionNameException;
import com.liferay.layout.content.exception.RequiredLayoutContentVersionException;
import com.liferay.layout.content.exception.UnsupportedLayoutLayoutContentVersionException;
import com.liferay.layout.content.model.LayoutContentVersion;
import com.liferay.layout.content.service.LayoutContentVersionLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.test.util.DisplayPageTemplateTestUtil;
import com.liferay.layout.page.template.test.util.LayoutPageTemplateTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@FeatureFlag("LPD-10622")
@RunWith(Arquillian.class)
public class LayoutContentVersionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_draftLayout = layout.fetchDraftLayout();
	}

	@Test
	public void testAddLayoutContentVersion() throws Exception {
		String data = RandomTestUtil.randomString();

		LayoutContentVersion draftLayoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				data, RandomTestUtil.randomLocaleStringMap(),
				_draftLayout.getPlid(), WorkflowConstants.STATUS_DRAFT);

		Assert.assertNotNull(draftLayoutContentVersion.getDataHash());
		Assert.assertEquals(
			_draftLayout.getPlid(), draftLayoutContentVersion.getPlid());
		Assert.assertEquals(1, draftLayoutContentVersion.getVersion());
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			draftLayoutContentVersion.getStatus());

		LayoutContentVersion approvedLayoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				data, RandomTestUtil.randomLocaleStringMap(),
				_draftLayout.getPlid(), WorkflowConstants.STATUS_APPROVED);

		Assert.assertNotEquals(
			draftLayoutContentVersion.getLayoutContentVersionId(),
			approvedLayoutContentVersion.getLayoutContentVersionId());
		Assert.assertEquals(2, approvedLayoutContentVersion.getVersion());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			approvedLayoutContentVersion.getStatus());

		_testAddLayoutContentVersionWithExternalReferenceCodeTooLong();
		_testAddLayoutContentVersionWithNullExternalReferenceCode();
		_testAddLayoutContentVersionWithNullNameMap();
		_testAddLayoutContentVersionWithUnsupportedLayout();
	}

	@Test
	public void testAddOrUpdateLayoutContentVersion() throws Exception {
		String data = RandomTestUtil.randomString();

		LayoutContentVersion layoutContentVersion1 =
			_layoutContentVersionLocalService.addOrUpdateLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				data, RandomTestUtil.randomLocaleStringMap(),
				_draftLayout.getPlid(), WorkflowConstants.STATUS_DRAFT);
		LayoutContentVersion layoutContentVersion2 =
			_layoutContentVersionLocalService.addOrUpdateLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				data, RandomTestUtil.randomLocaleStringMap(),
				_draftLayout.getPlid(), WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			layoutContentVersion1.getLayoutContentVersionId(),
			layoutContentVersion2.getLayoutContentVersionId());

		data = RandomTestUtil.randomString();

		LayoutContentVersion layoutContentVersion3 =
			_layoutContentVersionLocalService.addOrUpdateLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				data, RandomTestUtil.randomLocaleStringMap(),
				_draftLayout.getPlid(), WorkflowConstants.STATUS_APPROVED);

		Assert.assertNotEquals(
			layoutContentVersion2.getLayoutContentVersionId(),
			layoutContentVersion3.getLayoutContentVersionId());
	}

	@Test
	public void testDeleteLayoutContentVersion() throws Exception {
		LayoutContentVersion approvedLayoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(), _draftLayout.getPlid(),
				WorkflowConstants.STATUS_APPROVED);

		LayoutContentVersion latestApprovedLayoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(), _draftLayout.getPlid(),
				WorkflowConstants.STATUS_APPROVED);

		LayoutContentVersion draftLayoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(), _draftLayout.getPlid(),
				WorkflowConstants.STATUS_DRAFT);

		_layoutContentVersionLocalService.deleteLayoutContentVersion(
			approvedLayoutContentVersion.getLayoutContentVersionId());

		Assert.assertNull(
			_layoutContentVersionLocalService.fetchLayoutContentVersion(
				approvedLayoutContentVersion.getLayoutContentVersionId()));

		try {
			_layoutContentVersionLocalService.deleteLayoutContentVersion(
				latestApprovedLayoutContentVersion.getLayoutContentVersionId());

			Assert.fail();
		}
		catch (RequiredLayoutContentVersionException
					requiredLayoutContentVersionException) {

			Assert.assertNotNull(requiredLayoutContentVersionException);
		}

		Assert.assertNotNull(
			_layoutContentVersionLocalService.fetchLayoutContentVersion(
				latestApprovedLayoutContentVersion.
					getLayoutContentVersionId()));

		_layoutContentVersionLocalService.deleteLayoutContentVersion(
			draftLayoutContentVersion.getLayoutContentVersionId());

		Assert.assertNull(
			_layoutContentVersionLocalService.fetchLayoutContentVersion(
				draftLayoutContentVersion.getLayoutContentVersionId()));
	}

	@Test
	public void testGetLayoutContentVersions() throws Exception {
		_layoutContentVersionLocalService.addLayoutContentVersion(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(), _draftLayout.getPlid(),
			WorkflowConstants.STATUS_DRAFT);
		_layoutContentVersionLocalService.addLayoutContentVersion(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(),
			RandomTestUtil.randomLocaleStringMap(), _draftLayout.getPlid(),
			WorkflowConstants.STATUS_DRAFT);

		List<LayoutContentVersion> layoutContentVersions =
			_layoutContentVersionLocalService.getLayoutContentVersions(
				_draftLayout.getPlid());

		Assert.assertEquals(
			layoutContentVersions.toString(), 2, layoutContentVersions.size());
	}

	@Test
	public void testUpdateLayoutContentVersion() throws Exception {
		_testUpdateLayoutContentVersionWithEmptyNameMap();
		_testUpdateLayoutContentVersionWithNullNameMap();
	}

	private void _testAddLayoutContentVersionWithExternalReferenceCodeTooLong()
		throws Exception {

		int maxLength = ModelHintsUtil.getMaxLength(
			LayoutContentVersion.class.getName(), "externalReferenceCode");

		try {
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(maxLength + 1),
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(), _draftLayout.getPlid(),
				WorkflowConstants.STATUS_DRAFT);

			Assert.fail();
		}
		catch (LayoutContentVersionExternalReferenceCodeException
					layoutContentVersionExternalReferenceCodeException) {

			Assert.assertNotNull(
				layoutContentVersionExternalReferenceCodeException);
		}
	}

	private void _testAddLayoutContentVersionWithNullExternalReferenceCode()
		throws Exception {

		LayoutContentVersion layoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				null, TestPropsValues.getUserId(),
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(), _draftLayout.getPlid(),
				WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			_draftLayout.getExternalReferenceCode() + "_v_" +
				layoutContentVersion.getVersion(),
			layoutContentVersion.getExternalReferenceCode());
	}

	private void _testAddLayoutContentVersionWithNullNameMap()
		throws Exception {

		LayoutContentVersion layoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(), null, _draftLayout.getPlid(),
				WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			_draftLayout.getNameMap(), layoutContentVersion.getNameMap());
	}

	private void _testAddLayoutContentVersionWithUnsupportedLayout()
		throws Exception {

		_testAddLayoutContentVersionWithUnsupportedLayout(
			_draftLayout.getClassPK());

		Layout portletLayout = LayoutTestUtil.addTypePortletLayout(_group);

		_testAddLayoutContentVersionWithUnsupportedLayout(
			portletLayout.getPlid());

		LayoutPageTemplateEntry displayPageLayoutPageTemplateEntry =
			DisplayPageTemplateTestUtil.addDisplayPageTemplate(
				_group.getGroupId());

		_testAddLayoutContentVersionWithUnsupportedLayout(
			displayPageLayoutPageTemplateEntry.getPlid());

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateEntry(
				_group.getGroupId(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
				WorkflowConstants.STATUS_APPROVED);

		_testAddLayoutContentVersionWithUnsupportedLayout(
			masterLayoutPageTemplateEntry.getPlid());

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
				false, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_testAddLayoutContentVersionWithUnsupportedLayout(
			layoutUtilityPageEntry.getPlid());
	}

	private void _testAddLayoutContentVersionWithUnsupportedLayout(long plid)
		throws Exception {

		try {
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(), plid,
				WorkflowConstants.STATUS_DRAFT);

			Assert.fail();
		}
		catch (UnsupportedLayoutLayoutContentVersionException
					unsupportedLayoutLayoutContentVersionException) {

			Assert.assertNotNull(
				unsupportedLayoutLayoutContentVersionException);
		}
	}

	private void _testUpdateLayoutContentVersionWithEmptyNameMap()
		throws Exception {

		LayoutContentVersion layoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(), _draftLayout.getPlid(),
				WorkflowConstants.STATUS_DRAFT);

		try {
			_layoutContentVersionLocalService.updateLayoutContentVersion(
				layoutContentVersion.getLayoutContentVersionId(),
				new HashMap<>());

			Assert.fail();
		}
		catch (LayoutContentVersionNameException
					layoutContentVersionNameException) {

			Assert.assertNotNull(layoutContentVersionNameException);
		}
	}

	private void _testUpdateLayoutContentVersionWithNullNameMap()
		throws Exception {

		LayoutContentVersion layoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(), _draftLayout.getPlid(),
				WorkflowConstants.STATUS_DRAFT);

		try {
			_layoutContentVersionLocalService.updateLayoutContentVersion(
				layoutContentVersion.getLayoutContentVersionId(), null);

			Assert.fail();
		}
		catch (LayoutContentVersionNameException
					layoutContentVersionNameException) {

			Assert.assertNotNull(layoutContentVersionNameException);
		}
	}

	private Layout _draftLayout;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutContentVersionLocalService _layoutContentVersionLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

}