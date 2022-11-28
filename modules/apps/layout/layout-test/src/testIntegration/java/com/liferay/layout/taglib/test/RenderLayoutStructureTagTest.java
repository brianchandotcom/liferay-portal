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

package com.liferay.layout.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.exception.InfoFormException;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.test.util.MockInfoServiceRegistrationHolder;
import com.liferay.info.test.util.model.MockObject;
import com.liferay.layout.page.template.info.item.capability.EditPageInfoItemCapability;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.taglib.servlet.taglib.RenderLayoutStructureTag;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class RenderLayoutStructureTagTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		_layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testRemovedLayoutTemplateId() throws Exception {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)_layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			_layout.getUserId(), "removed-template-id");

		_layout = LayoutLocalServiceUtil.updateLayout(
			_layout.getGroupId(), _layout.isPrivateLayout(),
			_layout.getLayoutId(), _layout.getTypeSettings());

		RenderLayoutStructureTag renderLayoutStructureTag =
			new RenderLayoutStructureTag();

		renderLayoutStructureTag.setLayoutStructure(
			_getDefaultMasterLayoutStructure());

		renderLayoutStructureTag.doTag(
			_getMockHttpServletRequest(), new MockHttpServletResponse());

		_layout = _layoutLocalService.fetchLayout(_layout.getPlid());

		layoutTypePortlet = (LayoutTypePortlet)_layout.getLayoutType();

		Assert.assertEquals(
			layoutTypePortlet.getLayoutTemplateId(),
			PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);
	}

	@Test
	public void testRenderFormWithInfoFormException() throws Exception {
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

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			InfoFormException infoFormException = new InfoFormException();

			SessionErrors.add(
				mockHttpServletRequest, formItemId, infoFormException);

			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			RenderLayoutStructureTag renderLayoutStructureTag =
				_getRenderLayoutStructureTag(
					layout, mockHttpServletRequest, mockHttpServletResponse);

			renderLayoutStructureTag.doTag(
				mockHttpServletRequest, mockHttpServletResponse);

			Assert.assertFalse(
				SessionErrors.contains(mockHttpServletRequest, formItemId));

			String content = mockHttpServletResponse.getContentAsString();

			_assertErrorMessage(
				content,
				infoFormException.getLocalizedMessage(
					_portal.getSiteDefaultLocale(_group)));

			_assertInfoFieldInput(infoField, content);
		}
	}

	@Test
	public void testRenderFormWithInfoFormValidationException()
		throws Exception {

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

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout);

			String formItemId = ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			InfoFormValidationException infoFormValidationException =
				new InfoFormValidationException(infoField.getUniqueId());

			SessionErrors.add(
				mockHttpServletRequest, formItemId,
				infoFormValidationException);
			SessionErrors.add(
				mockHttpServletRequest, infoField.getUniqueId(),
				infoFormValidationException);

			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			RenderLayoutStructureTag renderLayoutStructureTag =
				_getRenderLayoutStructureTag(
					layout, mockHttpServletRequest, mockHttpServletResponse);

			renderLayoutStructureTag.doTag(
				mockHttpServletRequest, mockHttpServletResponse);

			Assert.assertFalse(
				SessionErrors.contains(mockHttpServletRequest, formItemId));
			Assert.assertFalse(
				SessionErrors.contains(
					mockHttpServletRequest, infoField.getUniqueId()));

			String content = mockHttpServletResponse.getContentAsString();

			Locale locale = _portal.getSiteDefaultLocale(_group);

			_assertErrorMessage(
				content,
				infoFormValidationException.getLocalizedMessage(
					infoField.getLabel(locale), locale));

			_assertInfoFieldInput(infoField, content);
		}
	}

	@Test
	public void testRenderFormWithoutErrors() throws Exception {
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

			ContentLayoutTestUtil.addFormToPublishedLayout(
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout);

			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			RenderLayoutStructureTag renderLayoutStructureTag =
				_getRenderLayoutStructureTag(
					layout, mockHttpServletRequest, mockHttpServletResponse);

			renderLayoutStructureTag.doTag(
				mockHttpServletRequest, mockHttpServletResponse);

			String content = mockHttpServletResponse.getContentAsString();

			String errorHTML = "<div class=\"alert alert-danger\">";

			Assert.assertFalse(content.contains(errorHTML));

			_assertInfoFieldInput(infoField, content);
		}
	}

	@Test
	public void testRenderFormWithSuccessMessage() throws Exception {
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
				false,
				String.valueOf(
					_portal.getClassNameId(MockObject.class.getName())),
				"0", layout, _layoutStructureProvider, infoField);

			MockHttpServletRequest mockHttpServletRequest =
				_getMockHttpServletRequest(layout);

			SessionMessages.add(mockHttpServletRequest, formItemId);

			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			RenderLayoutStructureTag renderLayoutStructureTag =
				_getRenderLayoutStructureTag(
					layout, mockHttpServletRequest, mockHttpServletResponse);

			renderLayoutStructureTag.doTag(
				mockHttpServletRequest, mockHttpServletResponse);

			String content = mockHttpServletResponse.getContentAsString();

			String formStartHTML = "<form action=\"";

			Assert.assertFalse(content.contains(formStartHTML));

			Locale locale = _portal.getSiteDefaultLocale(_group);

			String expectedSuccessMessage = LanguageUtil.get(
				locale,
				"thank-you.-your-information-was-successfully-received");

			String expectedSuccessHTML = StringBundler.concat(
				"<div class=\"font-weight-semi-bold bg-white",
				"text-secondary text-center text-3 p-5\">",
				expectedSuccessMessage, "</div>");

			Assert.assertTrue(content.contains(expectedSuccessHTML));

			String expectedInfoFieldInput =
				"<p>InputName:" + infoField.getName() + "</p>";

			Assert.assertFalse(content.contains(expectedInfoFieldInput));
		}
	}

	private void _assertErrorMessage(
		String content, String expectedErrorMessage) {

		String expectedErrorHTML =
			"<div class=\"alert alert-danger\">" + expectedErrorMessage +
				"</div>";

		Assert.assertTrue(content.contains(expectedErrorHTML));
	}

	private void _assertInfoFieldInput(
		InfoField<TextInfoFieldType> infoField, String content) {

		String expectedInfoFieldInput =
			"<p>InputName:" + infoField.getName() + "</p>";

		Assert.assertTrue(content.contains(expectedInfoFieldInput));
	}

	private LayoutStructure _getDefaultMasterLayoutStructure() {
		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		layoutStructure.addDropZoneLayoutStructureItem(
			rootLayoutStructureItem.getItemId(), 0);

		return layoutStructure;
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

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setLayout(_layout);

		LayoutSet layoutSet = _layout.getLayoutSet();

		themeDisplay.setLayoutSet(layoutSet);

		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());
		themeDisplay.setLocale(LocaleUtil.getSiteDefault());
		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());
		themeDisplay.setRequest(mockHttpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setAttribute(
			"ORIGINAL_HTTP_SERVLET_REQUEST",
			_getOriginalMockHttpServletRequest());

		mockHttpServletRequest.setMethod(HttpMethods.GET);

		return mockHttpServletRequest;
	}

	private MockHttpServletRequest _getMockHttpServletRequest(Layout layout)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			ContentLayoutTestUtil.getMockHttpServletRequest(
				_companyLocalService.getCompany(layout.getCompanyId()), _group,
				layout);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setRequest(mockHttpServletRequest);

		return mockHttpServletRequest;
	}

	private MockHttpServletRequest _getOriginalMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(_layout);

		LayoutSet layoutSet = _layout.getLayoutSet();

		themeDisplay.setLayoutSet(layoutSet);

		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());
		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(mockHttpServletRequest);
		themeDisplay.setUser(TestPropsValues.getUser());

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setMethod(HttpMethods.GET);

		return mockHttpServletRequest;
	}

	private RenderLayoutStructureTag _getRenderLayoutStructureTag(
		Layout layout, MockHttpServletRequest mockHttpServletRequest,
		MockHttpServletResponse mockHttpServletResponse) {

		RenderLayoutStructureTag renderLayoutStructureTag =
			new RenderLayoutStructureTag();

		renderLayoutStructureTag.setLayoutStructure(
			_layoutStructureProvider.getLayoutStructure(
				layout.getPlid(),
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid())));
		renderLayoutStructureTag.setPageContext(
			new MockPageContext(
				null, mockHttpServletRequest, mockHttpServletResponse));

		return renderLayoutStructureTag;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private EditPageInfoItemCapability _editPageInfoItemCapability;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}