/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.render.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Nícolas Moura
 */
@RunWith(Arquillian.class)
public class DDMFormFieldFreeMarkerRendererTest {

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
	public void testRenderEscapesFieldName() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			_SCRIPT, false, false, false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			_createDDMFormFieldRenderingContext();

		String renderedHTML = _ddmFormFieldRenderer.render(
			ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertFalse(renderedHTML.contains("<script>"));
	}

	private DDMFormFieldRenderingContext _createDDMFormFieldRenderingContext()
		throws Exception {

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _createThemeDisplay());

		ddmFormFieldRenderingContext.setHttpServletRequest(
			mockHttpServletRequest);

		ddmFormFieldRenderingContext.setHttpServletResponse(
			new MockHttpServletResponse());
		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);
		ddmFormFieldRenderingContext.setMode("");
		ddmFormFieldRenderingContext.setNamespace("_TEST_NAMESPACE_");
		ddmFormFieldRenderingContext.setPortletNamespace(
			"_TEST_PORTLET_NAMESPACE_");
		ddmFormFieldRenderingContext.setReadOnly(false);

		return ddmFormFieldRenderingContext;
	}

	private ThemeDisplay _createThemeDisplay() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId());

		return ContentLayoutTestUtil.getThemeDisplay(company, _group, layout);
	}

	private static final String _SCRIPT =
		"contentu248f\"><script>alert(1)</script>x1ytosh593m";

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(filter = "ddm.form.field.renderer.type=freemarker")
	private DDMFormFieldRenderer _ddmFormFieldRenderer;

	@DeleteAfterTestRun
	private Group _group;

}