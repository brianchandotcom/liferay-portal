/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Yuri Monteiro
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class UpdateStructureStrutsActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	@TestInfo("LPD-92696")
	public void testExecute() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition();
		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition();

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectRelationshipLocalService, _objectDefinition1,
				_objectDefinition2);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_updateStructureStrutsAction.execute(
			_getMockHttpServletRequest(_objectDefinition1),
			mockHttpServletResponse);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			mockHttpServletResponse.getContentAsString());

		Assert.assertEquals(jsonObject.toString(), 0, jsonObject.length());

		Assert.assertNotNull(
			_objectFieldLocalService.fetchObjectField(
				objectRelationship.getObjectFieldId2()));
		Assert.assertNotNull(
			_objectRelationshipLocalService.fetchObjectRelationship(
				objectRelationship.getObjectRelationshipId()));
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
			ObjectDefinition objectDefinition)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setUser(TestPropsValues.getUser());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setParameter(
			"objectDefinition",
			_getObjectDefinitionJSON(objectDefinition.getObjectDefinitionId()));

		return mockHttpServletRequest;
	}

	private String _getObjectDefinitionJSON(long objectDefinitionId)
		throws Exception {

		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		builder.user(TestPropsValues.getUser());

		ObjectDefinitionResource objectDefinitionResource = builder.build();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			String.valueOf(
				objectDefinitionResource.getObjectDefinition(
					objectDefinitionId)));

		jsonObject.put("objectRelationships", _jsonFactory.createJSONArray());

		return jsonObject.toString();
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionResource.Factory _objectDefinitionResourceFactory;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject(filter = "path=/cms/update-structure")
	private StrutsAction _updateStructureStrutsAction;

}