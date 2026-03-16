/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.collection.provider.RepeatableFieldInfoItemCollectionProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import java.io.ByteArrayOutputStream;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akhash Ramprakash
 */
@RunWith(Arquillian.class)
public class GetAvailableListRenderersMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	@TestInfo("LPD-82156")
	public void testDoServeResource() throws Exception {
		_testDoServeResource(
			JournalArticle.class.getName(),
			jsonArray -> Assert.assertFalse(JSONUtil.isEmpty(jsonArray)),
			JournalArticle.class.getName());
		_testDoServeResource(
			JournalArticle.class.getName(),
			jsonArray -> Assert.assertEquals(0, jsonArray.length()),
			RepeatableFieldInfoItemCollectionProvider.class.getName());
	}

	private void _testDoServeResource(
			String className, Consumer<JSONArray> consumer, String key)
		throws Exception {

		MockLiferayResourceRequest resourceRequest =
			new MockLiferayResourceRequest();

		resourceRequest.addParameter("className", className);
		resourceRequest.addParameter("key", key);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		resourceRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		MockLiferayResourceResponse resourceResponse =
			new MockLiferayResourceResponse();

		ReflectionTestUtil.invoke(
			_mvcResourceCommand, "doServeResource",
			new Class<?>[] {ResourceRequest.class, ResourceResponse.class},
			resourceRequest, resourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)resourceResponse.getPortletOutputStream();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			byteArrayOutputStream.toString());

		consumer.accept(jsonArray);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/get_available_list_renderers"
	)
	private MVCResourceCommand _mvcResourceCommand;

}