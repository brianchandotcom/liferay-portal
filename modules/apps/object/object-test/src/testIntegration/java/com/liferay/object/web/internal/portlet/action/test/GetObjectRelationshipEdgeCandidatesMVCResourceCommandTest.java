/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.ByteArrayOutputStream;

import java.util.Collections;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class GetObjectRelationshipEdgeCandidatesMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_objectDefinitionA =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false,
				LocalizedMapUtil.getLocalizedMap("A"), "A", null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				false, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						StringUtil.randomId()
					).build()));

		_objectDefinitionA =
			_objectDefinitionLocalService.updateRootObjectDefinitionId(
				_objectDefinitionA.getObjectDefinitionId(),
				_objectDefinitionA.getObjectDefinitionId());

		_objectDefinitionAA = _createObjectDefinition(true, "AA");

		_objectRelationshipA_AA = _relateObjectDefinition(
			true, _objectDefinitionA, _objectDefinitionAA);

		_objectDefinitionAB = _createObjectDefinition(true, "AB");

		_objectRelationshipA_AB = _relateObjectDefinition(
			true, _objectDefinitionA, _objectDefinitionAB);

		_objectDefinitionAAA = _createObjectDefinition(true, "AAA");

		_objectRelationshipAA_AAA = _relateObjectDefinition(
			true, _objectDefinitionAA, _objectDefinitionAAA);

		_objectDefinitionAAB = _createObjectDefinition(true, "AAB");

		_objectRelationshipAA_AAB = _relateObjectDefinition(
			true, _objectDefinitionAA, _objectDefinitionAAB);
	}

	@Test
	public void testGetObjectRelationships() throws Exception {

		// Get object relationships where there is a parent who is inside a
		// hierarchical structure and is not the root

		ObjectDefinition objectDefinitionAAAA = _createObjectDefinition(
			false, "AAAA");

		ObjectRelationship objectRelationshipAAA_AAAA = _relateObjectDefinition(
			false, _objectDefinitionAAA, objectDefinitionAAAA);

		Assert.assertEquals(
			_jsonFactory.createJSONArray(
			).put(
				JSONUtil.put(
					"ancestors",
					_jsonFactory.createJSONArray(
					).put(
						JSONUtil.put(
							"label",
							_getEdgeLabel(
								_objectDefinitionAA, _objectRelationshipAA_AAA)
						).put(
							"objectRelationshipId",
							_objectRelationshipAA_AAA.getObjectRelationshipId()
						)
					).put(
						JSONUtil.put(
							"label",
							_getEdgeLabel(
								_objectDefinitionA, _objectRelationshipA_AA)
						).put(
							"objectRelationshipId",
							_objectRelationshipA_AA.getObjectRelationshipId()
						)
					)
				).put(
					"label",
					_getEdgeLabel(
						_objectDefinitionAAA, objectRelationshipAAA_AAAA)
				).put(
					"objectRelationshipId",
					objectRelationshipAAA_AAAA.getObjectRelationshipId()
				).put(
					"root", false
				)
			).toString(),
			_getJSONArray(
				0, objectDefinitionAAAA.getObjectDefinitionId()
			).toString());

		// Get object relationship edges candidates where there is a parent who
		// is inside a hierarchical structure but the parent depth plus child
		// depth is greater than 4

		Assert.assertEquals(
			_jsonFactory.createJSONArray(
			).toString(),
			_getJSONArray(
				3, objectDefinitionAAAA.getObjectDefinitionId()
			).toString());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationshipAAA_AAAA.getObjectRelationshipId());

		// Get object relationship edges candidates where there is a parent who
		// is not inside a hierarchical structure

		ObjectDefinition objectDefinitionBBB = _createObjectDefinition(
			false, "BBBB");

		ObjectRelationship objectRelationshipBBB_AAAA = _relateObjectDefinition(
			false, objectDefinitionBBB, objectDefinitionAAAA);

		Assert.assertEquals(
			_jsonFactory.createJSONArray(
			).put(
				JSONUtil.put(
					"label",
					_getEdgeLabel(
						objectDefinitionBBB, objectRelationshipBBB_AAAA)
				).put(
					"objectRelationshipId",
					objectRelationshipBBB_AAAA.getObjectRelationshipId()
				)
			).toString(),
			_getJSONArray(
				0, objectDefinitionAAAA.getObjectDefinitionId()
			).toString());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationshipBBB_AAAA.getObjectRelationshipId());

		// Get object relationship edges candidates where there is a parent who
		// is inside a hierarchical structure and is the root

		ObjectRelationship objectRelationshipA_AAAA = _relateObjectDefinition(
			false, _objectDefinitionA, objectDefinitionAAAA);

		Assert.assertEquals(
			_jsonFactory.createJSONArray(
			).put(
				JSONUtil.put(
					"ancestors", _jsonFactory.createJSONArray()
				).put(
					"label",
					_getEdgeLabel(_objectDefinitionA, objectRelationshipA_AAAA)
				).put(
					"objectRelationshipId",
					objectRelationshipA_AAAA.getObjectRelationshipId()
				).put(
					"root", true
				)
			).toString(),
			_getJSONArray(
				0, objectDefinitionAAAA.getObjectDefinitionId()
			).toString());
	}

	private static ObjectDefinition _createObjectDefinition(
			boolean nested, String objectDefinitionName)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false,
				LocalizedMapUtil.getLocalizedMap(objectDefinitionName),
				objectDefinitionName, null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				false, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.singletonList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						StringUtil.randomId()
					).build()));

		if (!nested) {
			return objectDefinition;
		}

		return _objectDefinitionLocalService.updateRootObjectDefinitionId(
			objectDefinition.getObjectDefinitionId(),
			_objectDefinitionA.getObjectDefinitionId());
	}

	private static ObjectRelationship _relateObjectDefinition(
			boolean edge, ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2)
		throws Exception {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				objectDefinition1.getObjectDefinitionId(),
				objectDefinition2.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(
					objectDefinition1.getShortName() + " - " +
						objectDefinition2.getShortName()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		if (!edge) {
			return objectRelationship;
		}

		return _objectRelationshipLocalService.updateObjectRelationship(
			objectRelationship.getObjectRelationshipId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE, true,
			objectRelationship.getLabelMap());
	}

	private String _getEdgeLabel(
		ObjectDefinition objectDefinition,
		ObjectRelationship objectRelationship) {

		return StringUtil.appendParentheticalSuffix(
			objectDefinition.getLabel(LocaleUtil.getSiteDefault()),
			objectRelationship.getLabel(LocaleUtil.getSiteDefault()));
	}

	private JSONArray _getJSONArray(int depth, long objectDefinitionId)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG,
			PortletConfigFactoryUtil.create(
				_portletLocalService.getPortletById(
					ObjectPortletKeys.OBJECT_DEFINITIONS),
				null));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.getSiteDefault());
		themeDisplay.setTimeZone(TimeZoneUtil.getDefault());

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayResourceRequest.addParameter("depth", String.valueOf(depth));
		mockLiferayResourceRequest.addParameter(
			"objectDefinitionId", String.valueOf(objectDefinitionId));

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONArray(
			new String(byteArrayOutputStream.toByteArray()));
	}

	private static ObjectDefinition _objectDefinitionA;
	private static ObjectDefinition _objectDefinitionAA;
	private static ObjectDefinition _objectDefinitionAAA;
	private static ObjectDefinition _objectDefinitionAAB;
	private static ObjectDefinition _objectDefinitionAB;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	private static ObjectRelationship _objectRelationshipA_AA;
	private static ObjectRelationship _objectRelationshipA_AB;
	private static ObjectRelationship _objectRelationshipAA_AAA;
	private static ObjectRelationship _objectRelationshipAA_AAB;

	@Inject
	private static ObjectRelationshipLocalService
		_objectRelationshipLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject(
		filter = "mvc.command.name=/object_definitions/get_object_relationship_edge_candidates"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private PortletLocalService _portletLocalService;

}