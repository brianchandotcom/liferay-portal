/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.versioning.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.content.versioning.exception.LayoutContentVersionExternalReferenceCodeException;
import com.liferay.layout.content.versioning.exception.LayoutContentVersionNameException;
import com.liferay.layout.content.versioning.exception.RequiredLayoutContentVersionException;
import com.liferay.layout.content.versioning.model.LayoutContentVersion;
import com.liferay.layout.content.versioning.service.LayoutContentVersionLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
		LayoutContentVersion draftLayoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), WorkflowConstants.STATUS_DRAFT,
				false);

		Assert.assertEquals(
			_draftLayout.getPlid(), draftLayoutContentVersion.getPlid());
		Assert.assertEquals(1, draftLayoutContentVersion.getVersion());
		Assert.assertNotNull(draftLayoutContentVersion.getDataHash());
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			draftLayoutContentVersion.getStatus());

		LayoutContentVersion approvedLayoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(),
				WorkflowConstants.STATUS_APPROVED, false);

		Assert.assertEquals(2, approvedLayoutContentVersion.getVersion());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			approvedLayoutContentVersion.getStatus());

		_testAddLayoutContentVersionWithExternalReferenceCodeTooLong();
		_testAddLayoutContentVersionWithNullExternalReferenceCode();
		_testAddLayoutContentVersionWithNullNameMap();
		_testAddLayoutContentVersionWithSkipIfUnchanged();
	}

	@Test
	public void testDeleteLayoutContentVersion() throws Exception {
		LayoutContentVersion approvedLayoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(),
				WorkflowConstants.STATUS_APPROVED, false);

		LayoutContentVersion latestApprovedLayoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(),
				WorkflowConstants.STATUS_APPROVED, false);

		LayoutContentVersion draftLayoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), WorkflowConstants.STATUS_DRAFT,
				false);

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

			if (_log.isDebugEnabled()) {
				_log.debug(requiredLayoutContentVersionException);
			}
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
			_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_DRAFT,
			false);
		_layoutContentVersionLocalService.addLayoutContentVersion(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), WorkflowConstants.STATUS_DRAFT,
			false);

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
				TestPropsValues.getUserId(), _draftLayout.getPlid(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), WorkflowConstants.STATUS_DRAFT,
				false);

			Assert.fail();
		}
		catch (LayoutContentVersionExternalReferenceCodeException
					layoutContentVersionExternalReferenceCodeException) {

			if (_log.isDebugEnabled()) {
				_log.debug(layoutContentVersionExternalReferenceCodeException);
			}
		}
	}

	private void _testAddLayoutContentVersionWithNullExternalReferenceCode()
		throws Exception {

		LayoutContentVersion layoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				null, TestPropsValues.getUserId(), _draftLayout.getPlid(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), WorkflowConstants.STATUS_DRAFT,
				false);

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
				_draftLayout.getPlid(), null, RandomTestUtil.randomString(),
				WorkflowConstants.STATUS_DRAFT, false);

		Assert.assertEquals(
			_draftLayout.getNameMap(), layoutContentVersion.getNameMap());
	}

	private void _testAddLayoutContentVersionWithSkipIfUnchanged()
		throws Exception {

		String data = RandomTestUtil.randomString();

		LayoutContentVersion layoutContentVersion1 =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				data, WorkflowConstants.STATUS_DRAFT, false);
		LayoutContentVersion layoutContentVersion2 =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				data, WorkflowConstants.STATUS_DRAFT, false);

		Assert.assertNotEquals(
			layoutContentVersion1.getLayoutContentVersionId(),
			layoutContentVersion2.getLayoutContentVersionId());

		String otherData = RandomTestUtil.randomString();

		LayoutContentVersion layoutContentVersion3 =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				otherData, WorkflowConstants.STATUS_APPROVED, true);
		LayoutContentVersion layoutContentVersion4 =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				otherData, WorkflowConstants.STATUS_APPROVED, true);

		Assert.assertEquals(
			layoutContentVersion3.getLayoutContentVersionId(),
			layoutContentVersion4.getLayoutContentVersionId());
	}

	private void _testUpdateLayoutContentVersionWithEmptyNameMap()
		throws Exception {

		LayoutContentVersion layoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), WorkflowConstants.STATUS_DRAFT,
				false);

		try {
			_layoutContentVersionLocalService.updateLayoutContentVersion(
				layoutContentVersion.getLayoutContentVersionId(),
				new HashMap<>());

			Assert.fail();
		}
		catch (LayoutContentVersionNameException
					layoutContentVersionNameException) {

			if (_log.isDebugEnabled()) {
				_log.debug(layoutContentVersionNameException);
			}
		}
	}

	private void _testUpdateLayoutContentVersionWithNullNameMap()
		throws Exception {

		LayoutContentVersion layoutContentVersion =
			_layoutContentVersionLocalService.addLayoutContentVersion(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), WorkflowConstants.STATUS_DRAFT,
				false);

		try {
			_layoutContentVersionLocalService.updateLayoutContentVersion(
				layoutContentVersion.getLayoutContentVersionId(), null);

			Assert.fail();
		}
		catch (LayoutContentVersionNameException
					layoutContentVersionNameException) {

			if (_log.isDebugEnabled()) {
				_log.debug(layoutContentVersionNameException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutContentVersionLocalServiceTest.class);

	private Layout _draftLayout;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutContentVersionLocalService _layoutContentVersionLocalService;

}