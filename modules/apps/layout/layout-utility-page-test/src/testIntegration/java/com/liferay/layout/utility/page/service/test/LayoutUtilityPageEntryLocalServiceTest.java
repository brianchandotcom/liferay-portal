/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.utility.page.exception.DuplicateLayoutUtilityPageEntryExternalReferenceCodeException;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class LayoutUtilityPageEntryLocalServiceTest {

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
	public void testAddLayoutUtilityPageEntry() throws Exception {
		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
				true, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 0, _serviceContext);

		Assert.assertTrue(
			Validator.isNotNull(
				layoutUtilityPageEntry.getExternalReferenceCode()));

		Layout layout = _layoutLocalService.getLayout(
			layoutUtilityPageEntry.getPlid());

		Assert.assertTrue(layout.isPublished());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, layout.getStatus());

		layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
				false, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 0, _serviceContext);

		Assert.assertTrue(
			Validator.isNotNull(
				layoutUtilityPageEntry.getExternalReferenceCode()));

		layout = _layoutLocalService.getLayout(
			layoutUtilityPageEntry.getPlid());

		Assert.assertFalse(layout.isPublished());
		Assert.assertEquals(WorkflowConstants.STATUS_DRAFT, layout.getStatus());

		try {
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getExternalReferenceCode(),
				TestPropsValues.getUserId(), _group.getGroupId(), 0, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				_serviceContext);

			Assert.fail();
		}
		catch (DuplicateLayoutUtilityPageEntryExternalReferenceCodeException
					duplicateLayoutUtilityPageEntryExternalReferenceCodeException) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					duplicateLayoutUtilityPageEntryExternalReferenceCodeException);
			}
		}
	}

	@Test
	public void testDeleteLayoutUtilityPageEntryByExternalReferenceCode()
		throws Exception {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, 0,
				true, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 0, _serviceContext);

		_layoutUtilityPageEntryLocalService.deleteLayoutUtilityPageEntry(
			layoutUtilityPageEntry.getExternalReferenceCode(),
			layoutUtilityPageEntry.getGroupId());

		Assert.assertNull(
			_layoutUtilityPageEntryLocalService.fetchLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId()));
	}

	@Test
	public void testGetExternalReferenceCode() throws Exception {
		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				null, _group.getGroupId(), 0, 0, true,
				"Test Layout Utility Page", RandomTestUtil.randomString(), 0,
				_serviceContext);

		Assert.assertEquals(
			"test-layout-utility-page",
			layoutUtilityPageEntry.getExternalReferenceCode());

		layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				"ERC", _group.getGroupId(), 0, 0, true,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0,
				_serviceContext);

		Assert.assertEquals(
			"ERC", layoutUtilityPageEntry.getExternalReferenceCode());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutUtilityPageEntryLocalServiceTest.class);

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private LayoutUtilityPageEntryService _layoutUtilityPageEntryService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}