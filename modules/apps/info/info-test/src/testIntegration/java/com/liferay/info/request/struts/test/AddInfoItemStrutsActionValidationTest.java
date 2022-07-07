/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.info.request.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.exception.InfoFormException;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.test.util.MockInfoServiceRegistrationHolder;
import com.liferay.info.test.util.model.MockObject;
import com.liferay.layout.page.template.info.item.capability.EditPageInfoItemCapability;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class AddInfoItemStrutsActionValidationTest {

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
	public void testAddInfoItemStrutsAction() throws Exception {
		InfoField<TextInfoFieldType> infoField = _getInfoField();

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_editPageInfoItemCapability)) {

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				layout, false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", infoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout, formItemId);

			_addInfoItemStrutsAction.execute(
				mockHttpServletRequest, new MockHttpServletResponse());

			Assert.assertFalse(
				SessionErrors.contains(mockHttpServletRequest, formItemId));
			Assert.assertFalse(
				SessionErrors.contains(
					mockHttpServletRequest, infoField.getUniqueId()));
			Assert.assertTrue(
				SessionMessages.contains(mockHttpServletRequest, formItemId));
		}
	}

	@Test
	public void testAddInfoItemStrutsActionCaptchaException() throws Exception {
		InfoField<TextInfoFieldType> infoField = _getInfoField();

		try (MockInfoServiceRegistrationHolder
				mockInfoServiceRegistrationHolder =
					new MockInfoServiceRegistrationHolder(
						InfoFieldSet.builder(
						).infoFieldSetEntries(
							ListUtil.fromArray(infoField)
						).build(),
						_editPageInfoItemCapability)) {

			Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				layout, true,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", infoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout, formItemId);

			_addInfoItemStrutsAction.execute(
				mockHttpServletRequest, new MockHttpServletResponse());

			Assert.assertTrue(
				SessionErrors.contains(mockHttpServletRequest, formItemId));

			InfoFormException infoFormException =
				(InfoFormException)SessionErrors.get(
					mockHttpServletRequest, formItemId);

			Assert.assertTrue(
				infoFormException instanceof
					InfoFormValidationException.InvalidCaptcha);

			Assert.assertFalse(
				SessionMessages.contains(mockHttpServletRequest, formItemId));
		}
	}

	private InfoField<TextInfoFieldType> _getInfoField() {
		return InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			RandomTestUtil.randomString()
		).name(
			RandomTestUtil.randomString()
		).labelInfoLocalizedValue(
			InfoLocalizedValue.singleValue(RandomTestUtil.randomString())
		).localizable(
			true
		).build();
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
			Layout layout, String formItemId)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			ContentLayoutTestUtil.getMockHttpServletRequest(
				_companyLocalService.getCompany(layout.getCompanyId()), _group,
				layout);

		String layoutActualURL = _portal.getLayoutActualURL(layout);

		mockHttpServletRequest.addHeader(HttpHeaders.REFERER, layoutActualURL);
		mockHttpServletRequest.addParameter("backURL", layoutActualURL);

		mockHttpServletRequest.addParameter(
			"classNameId",
			String.valueOf(_portal.getClassNameId(MockObject.class.getName())));
		mockHttpServletRequest.addParameter("classTypeId", "0");
		mockHttpServletRequest.addParameter("formItemId", formItemId);
		mockHttpServletRequest.addParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		mockHttpServletRequest.addParameter(
			"plid", String.valueOf(layout.getPlid()));
		mockHttpServletRequest.addParameter(
			"segmentsExperienceId",
			String.valueOf(
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid())));

		return mockHttpServletRequest;
	}

	@Inject(filter = "path=/portal/add_info_item")
	private StrutsAction _addInfoItemStrutsAction;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private EditPageInfoItemCapability _editPageInfoItemCapability;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}