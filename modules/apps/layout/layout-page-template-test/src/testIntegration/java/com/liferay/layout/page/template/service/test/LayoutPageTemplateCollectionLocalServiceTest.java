/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateCollectionExternalReferenceCodeException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateCollectionGroupIdException;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class LayoutPageTemplateCollectionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddLayoutPageTemplateCollection() throws Exception {
		String externalReferenceCode = StringUtil.randomString();

		_layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				externalReferenceCode, TestPropsValues.getUserId(),
				_group.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				RandomTestUtil.randomString(), null,
				LayoutPageTemplateCollectionTypeConstants.BASIC,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		try {
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					externalReferenceCode, TestPropsValues.getUserId(),
					_group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(), null,
					LayoutPageTemplateCollectionTypeConstants.BASIC,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

			Assert.fail();
		}
		catch (DuplicateLayoutPageTemplateCollectionExternalReferenceCodeException
					duplicateLayoutPageTemplateCollectionExternalReferenceCodeException) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					duplicateLayoutPageTemplateCollectionExternalReferenceCodeException);
			}
		}

		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		try {
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(),
					companyGroup.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(), null,
					LayoutPageTemplateCollectionTypeConstants.BASIC,
					ServiceContextTestUtil.getServiceContext(
						companyGroup.getGroupId()));

			Assert.fail();
		}
		catch (LayoutPageTemplateCollectionGroupIdException
					layoutPageTemplateCollectionGroupIdException) {

			if (_log.isDebugEnabled()) {
				_log.debug(layoutPageTemplateCollectionGroupIdException);
			}
		}
	}

	@Test
	public void testDeleteLayoutPageTemplateCollectionByExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = StringUtil.randomString();

		_layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				externalReferenceCode, TestPropsValues.getUserId(),
				_group.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				RandomTestUtil.randomString(), null,
				LayoutPageTemplateCollectionTypeConstants.BASIC,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_layoutPageTemplateCollectionLocalService.
			deleteLayoutPageTemplateCollection(
				externalReferenceCode, _group.getGroupId());

		Assert.assertNull(
			_layoutPageTemplateCollectionLocalService.
				fetchLayoutPageTemplateCollectionByExternalReferenceCode(
					externalReferenceCode, _group.getGroupId()));
	}

	@Test
	public void testFetchLayoutPageTemplateCollectionByExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = StringUtil.randomString();

		_layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				externalReferenceCode, TestPropsValues.getUserId(),
				_group.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				RandomTestUtil.randomString(), null,
				LayoutPageTemplateCollectionTypeConstants.BASIC,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertNotNull(
			_layoutPageTemplateCollectionLocalService.
				fetchLayoutPageTemplateCollectionByExternalReferenceCode(
					externalReferenceCode, _group.getGroupId()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateCollectionLocalServiceTest.class);

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

}