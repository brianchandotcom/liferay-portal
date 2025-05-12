/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.test.util.TreeTestUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkService;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;

import java.io.ByteArrayOutputStream;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Nathaly Gomes
 */
@FeatureFlag("LPD-34594")
@RunWith(Arquillian.class)
public class GetObjectDefinitionInfoMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_objectDefinitionA = ObjectDefinitionTestUtil.publishObjectDefinition();
		_objectDefinitionAA =
			ObjectDefinitionTestUtil.publishObjectDefinition();
	}

	@Test
	public void testDoServeResource() throws Exception {
		KaleoDefinition kaleoDefinition = _addKaleoDefinition(
			_objectDefinitionA);

		_assertJSONObject(kaleoDefinition, _objectDefinitionA, true);

		_assertJSONObject(
			_addKaleoDefinition(_objectDefinitionAA), _objectDefinitionAA,
			true);

		TreeTestUtil.bind(
			_objectRelationshipLocalService,
			Collections.singletonList(
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectRelationshipLocalService, _objectDefinitionA,
					_objectDefinitionAA,
					ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
					StringUtil.randomId())));

		_assertJSONObject(kaleoDefinition, _objectDefinitionA, true);
		_assertJSONObject(kaleoDefinition, _objectDefinitionAA, false);

		TreeTestUtil.deleteObjectDefinitionHierarchy(
			_objectDefinitionLocalService,
			new String[] {
				_objectDefinitionA.getName(), _objectDefinitionAA.getName()
			},
			_objectEntryLocalService, _objectRelationshipLocalService);

		ObjectDefinition systemObjectDefinition =
			_objectDefinitionLocalService.fetchSystemObjectDefinition(
				TestPropsValues.getCompanyId(), "Organization");

		_assertJSONObject(null, systemObjectDefinition, false);
	}

	private KaleoDefinition _addKaleoDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.addKaleoDefinition(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null, null,
				WorkflowDefinitionConstants.SCOPE_ALL, 1,
				ServiceContextTestUtil.getServiceContext());

		_workflowDefinitionLinkService.addWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			TestPropsValues.getGroupId(), objectDefinition.getClassName(), 0, 0,
			kaleoDefinition.getName(), 1);

		return kaleoDefinition;
	}

	private void _assertJSONObject(
			KaleoDefinition kaleoDefinition, ObjectDefinition objectDefinition,
			boolean workflowSupported)
		throws Exception {

		JSONObject jsonObject = JSONUtil.put(
			"isWorkflowSupported", workflowSupported
		).put(
			"tableName", objectDefinition.getDBTableName()
		);

		if (workflowSupported) {
			jsonObject.put(
				"workflowDefinitionTitle", kaleoDefinition.getTitle());
		}

		Assert.assertEquals(
			jsonObject.toString(),
			String.valueOf(
				_getJSONObject(objectDefinition.getObjectDefinitionId())));
	}

	private JSONObject _getJSONObject(long objectDefinitionId)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.addParameter(
			"objectDefinitionId", String.valueOf(objectDefinitionId));
		mockLiferayResourceRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG,
			PortletConfigFactoryUtil.create(
				_portletLocalService.getPortletById(
					ObjectPortletKeys.OBJECT_DEFINITIONS),
				null));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setSiteGroupId(TestPropsValues.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONObject(
			byteArrayOutputStream.toString());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Inject(
		filter = "mvc.command.name=/object_definitions/get_object_definition_info"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinitionA;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinitionAA;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private WorkflowDefinitionLinkService _workflowDefinitionLinkService;

}