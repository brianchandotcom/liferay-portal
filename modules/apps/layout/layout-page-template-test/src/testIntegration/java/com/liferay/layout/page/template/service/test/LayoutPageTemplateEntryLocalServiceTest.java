/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateEntryExternalReferenceCodeException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryDefaultTemplateException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.test.util.LayoutPageTemplateTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

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
public class LayoutPageTemplateEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layoutPageTemplateCollection =
			LayoutPageTemplateTestUtil.addLayoutPageTemplateCollection(
				_group.getGroupId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@Test
	public void testAddLayoutPageTemplateEntry() throws Exception {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.BASIC, 0,
				WorkflowConstants.STATUS_DRAFT, _serviceContext);

		Assert.assertTrue(
			Validator.isNotNull(
				layoutPageTemplateEntry.getExternalReferenceCode()));

		Assert.assertFalse(layoutPageTemplateEntry.isDefaultTemplate());

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Assert.assertFalse(layout.isPublished());

		layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				0, 0, RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0, true, 0,
				0, 0, WorkflowConstants.STATUS_APPROVED, _serviceContext);

		Assert.assertTrue(
			Validator.isNotNull(
				layoutPageTemplateEntry.getExternalReferenceCode()));

		Assert.assertTrue(layoutPageTemplateEntry.isDefaultTemplate());

		layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Assert.assertTrue(layout.isPublished());

		try {
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				0, 0, RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0, true, 0,
				0, 0, WorkflowConstants.STATUS_DRAFT, _serviceContext);

			Assert.fail();
		}
		catch (LayoutPageTemplateEntryDefaultTemplateException
					layoutPageTemplateEntryDefaultTemplateException) {

			if (_log.isDebugEnabled()) {
				_log.debug(layoutPageTemplateEntryDefaultTemplateException);
			}
		}
	}

	@Test(
		expected = DuplicateLayoutPageTemplateEntryExternalReferenceCodeException.class
	)
	public void testAddLayoutPageTemplateEntryWithExistingExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(),
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.BASIC, 0,
			WorkflowConstants.STATUS_DRAFT, _serviceContext);
		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(),
			_layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.BASIC, 0,
			WorkflowConstants.STATUS_DRAFT, _serviceContext);
	}

	@Test
	public void testDeleteLayoutPageTemplateEntryByExternalReferenceCode()
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(),
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.BASIC, 0,
				WorkflowConstants.STATUS_DRAFT, _serviceContext);

		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getExternalReferenceCode(),
			layoutPageTemplateEntry.getGroupId());

		Assert.assertNull(
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
	}

	@Test
	public void testUpdateLayoutPageTemplateEntryDefaultTemplate()
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_DRAFT, _serviceContext);

		try {
			_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), true);

			Assert.fail();
		}
		catch (LayoutPageTemplateEntryDefaultTemplateException
					layoutPageTemplateEntryDefaultTemplateException) {

			if (_log.isDebugEnabled()) {
				_log.debug(layoutPageTemplateEntryDefaultTemplateException);
			}
		}

		layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.updateStatus(
				TestPropsValues.getUserId(),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				WorkflowConstants.STATUS_APPROVED);

		layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), true);

		Assert.assertTrue(layoutPageTemplateEntry.isDefaultTemplate());

		try {
			_layoutPageTemplateEntryLocalService.updateStatus(
				TestPropsValues.getUserId(),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				WorkflowConstants.STATUS_DRAFT);

			Assert.fail();
		}
		catch (LayoutPageTemplateEntryDefaultTemplateException
					layoutPageTemplateEntryDefaultTemplateException) {

			if (_log.isDebugEnabled()) {
				_log.debug(layoutPageTemplateEntryDefaultTemplateException);
			}
		}

		try {
			_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				TestPropsValues.getUserId(),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				RandomTestUtil.randomString(), WorkflowConstants.STATUS_DRAFT);

			Assert.fail();
		}
		catch (LayoutPageTemplateEntryDefaultTemplateException
					layoutPageTemplateEntryDefaultTemplateException) {

			if (_log.isDebugEnabled()) {
				_log.debug(layoutPageTemplateEntryDefaultTemplateException);
			}
		}
	}

	@Test
	public void testUpdateLayoutPageTemplateEntryName() throws Exception {
		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.BASIC,
				masterLayoutPageTemplateEntry.getPlid(),
				WorkflowConstants.STATUS_DRAFT, _serviceContext);

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), false,
				StringPool.BLANK, RandomTestUtil.randomString(),
				StringPool.BLANK, RandomTestUtil.randomString(),
				_serviceContext);

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		_layoutLocalService.updateStyleBookEntryId(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			styleBookEntry.getStyleBookEntryId());

		Layout draftLayout = layout.fetchDraftLayout();

		_layoutLocalService.updateStyleBookEntryId(
			draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
			draftLayout.getLayoutId(), styleBookEntry.getStyleBookEntryId());

		layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				RandomTestUtil.randomString());

		layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		Assert.assertEquals(
			masterLayoutPageTemplateEntry.getPlid(),
			layout.getMasterLayoutPlid());
		Assert.assertEquals(
			styleBookEntry.getStyleBookEntryId(), layout.getStyleBookEntryId());

		draftLayout = layout.fetchDraftLayout();

		Assert.assertEquals(
			masterLayoutPageTemplateEntry.getPlid(),
			draftLayout.getMasterLayoutPlid());
		Assert.assertEquals(
			styleBookEntry.getStyleBookEntryId(),
			draftLayout.getStyleBookEntryId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryLocalServiceTest.class);

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private LayoutPageTemplateCollection _layoutPageTemplateCollection;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	private ServiceContext _serviceContext;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}