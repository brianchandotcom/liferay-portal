/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayOutputStream;

import java.util.Collections;

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
public class GetInfoItemOneToManyRelationshipsMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "myRichText",
					"myRichText", false)),
			false);
	}

	@Test
	public void testGetInfoItemOneToManyRelationships() throws Exception {
		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "myText", "myText",
					false)),
			false);

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _objectDefinition2,
				_objectDefinition1);

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());
		mockLiferayResourceRequest.setParameter(
			"classNameId",
			String.valueOf(
				_portal.getClassNameId(_objectDefinition1.getClassName())));

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		JSONArray jsonArray = _getJSONArray(mockLiferayResourceResponse);

		Assert.assertEquals(1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			_portal.getClassNameId(_objectDefinition2.getClassName()),
			jsonObject.getLong("classNameId"));
		Assert.assertEquals(
			_objectDefinition2.getLabel(LocaleUtil.getDefault()),
			jsonObject.getString("label"));
		Assert.assertEquals(
			objectRelationship.getName(), jsonObject.getString("name"));
	}

	@Test
	public void testWithEmptyClassNameId() throws Exception {
		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			new MockLiferayResourceRequest(), mockLiferayResourceResponse);

		JSONArray jsonArray = _getJSONArray(mockLiferayResourceResponse);

		Assert.assertEquals(0, jsonArray.length());
	}

	private JSONArray _getJSONArray(
			MockLiferayResourceResponse mockLiferayResourceResponse)
		throws Exception {

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONArray(
			byteArrayOutputStream.toString());
	}

	private ThemeDisplay _getThemeDisplay() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.getDefault());

		return themeDisplay;
	}

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/get_info_item_one_to_many_relationships"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private Portal _portal;

}