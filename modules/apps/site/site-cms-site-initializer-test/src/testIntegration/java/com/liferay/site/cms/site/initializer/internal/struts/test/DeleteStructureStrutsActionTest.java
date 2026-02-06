/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class DeleteStructureStrutsActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@FeatureFlag("LPD-34594")
	@Test
	@TestInfo("LPD-77022")
	public void testExecute() throws Exception {
		ObjectDefinition objectDefinition1 =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();
		ObjectDefinition objectDefinition2 =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();
		ObjectDefinition objectDefinition3 =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		ObjectRelationshipTestUtil.addObjectRelationship(
			_objectRelationshipLocalService, objectDefinition1,
			objectDefinition2);

		ObjectRelationshipTestUtil.addObjectRelationship(
			_objectRelationshipLocalService, objectDefinition2,
			objectDefinition3);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			"objectDefinitionId",
			String.valueOf(objectDefinition1.getObjectDefinitionId()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_deleteStructureStrutsAction.execute(
			mockHttpServletRequest, mockHttpServletResponse);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			mockHttpServletResponse.getContentAsString());

		Assert.assertEquals(0, jsonObject.length());

		Assert.assertNull(
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectDefinition1.getObjectDefinitionId()));
		Assert.assertNull(
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectDefinition2.getObjectDefinitionId()));
		Assert.assertNull(
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectDefinition3.getObjectDefinitionId()));
	}

	@Inject(filter = "path=/cms/delete-structure")
	private StrutsAction _deleteStructureStrutsAction;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}