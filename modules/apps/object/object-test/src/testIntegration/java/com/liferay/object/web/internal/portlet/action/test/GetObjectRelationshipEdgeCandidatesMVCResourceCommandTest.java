/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.service.test.util.TreeTestUtil;
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
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
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

	@Test
	public void testGetObjectRelationships() throws Exception {

		// Object definition, hierarchical structure, maximum height

		Tree tree = TreeTestUtil.createTree(
			_objectDefinitionLocalService, _objectRelationshipLocalService,
			_treeFactory);

		ObjectDefinition objectDefinitionAAA =
			_objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), "C_AAA");

		ObjectDefinition objectDefinitionAAAA =
			ObjectDefinitionTestUtil.addObjectDefinition(
				"AAAA", _objectDefinitionLocalService);

		ObjectRelationship objectRelationshipAAA_AAAA =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				objectDefinitionAAA.getObjectDefinitionId(),
				objectDefinitionAAAA.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertEquals(
			_jsonFactory.createJSONArray(
			).toString(),
			_getObjectRelationshipEdgeCandidatesJSONArray(
				2, objectDefinitionAAAA.getObjectDefinitionId()
			).toString());

		// Object definition, hierarchical structure, not root

		ObjectDefinition objectDefinitionAA =
			_objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), "C_AA");

		ObjectDefinition objectDefinitionA =
			_objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), "C_A");

		ObjectRelationship objectRelationshipA_AA =
			TreeTestUtil.getEdgeObjectRelationship(
				objectDefinitionAA, _objectRelationshipLocalService, tree);

		ObjectRelationship objectRelationshipAA_AAA =
			TreeTestUtil.getEdgeObjectRelationship(
				objectDefinitionAAA, _objectRelationshipLocalService, tree);

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
								objectDefinitionAA, objectRelationshipAA_AAA)
						).put(
							"objectRelationshipId",
							objectRelationshipAA_AAA.getObjectRelationshipId()
						)
					).put(
						JSONUtil.put(
							"label",
							_getEdgeLabel(
								objectDefinitionA, objectRelationshipA_AA)
						).put(
							"objectRelationshipId",
							objectRelationshipA_AA.getObjectRelationshipId()
						)
					)
				).put(
					"label",
					_getEdgeLabel(
						objectDefinitionAAA, objectRelationshipAAA_AAAA)
				).put(
					"objectRelationshipId",
					objectRelationshipAAA_AAAA.getObjectRelationshipId()
				).put(
					"root", false
				)
			).toString(),
			_getObjectRelationshipEdgeCandidatesJSONArray(
				0, objectDefinitionAAAA.getObjectDefinitionId()
			).toString());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationshipAAA_AAAA.getObjectRelationshipId());

		// Object definition, hierarchical structure, root

		ObjectRelationship objectRelationshipA_AAAA =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				objectDefinitionA.getObjectDefinitionId(),
				objectDefinitionAAAA.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertEquals(
			_jsonFactory.createJSONArray(
			).put(
				JSONUtil.put(
					"ancestors", _jsonFactory.createJSONArray()
				).put(
					"label",
					_getEdgeLabel(objectDefinitionA, objectRelationshipA_AAAA)
				).put(
					"objectRelationshipId",
					objectRelationshipA_AAAA.getObjectRelationshipId()
				).put(
					"root", true
				)
			).toString(),
			_getObjectRelationshipEdgeCandidatesJSONArray(
				0, objectDefinitionAAAA.getObjectDefinitionId()
			).toString());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationshipA_AAAA.getObjectRelationshipId());

		// Object definition, not hierarchical structure

		ObjectDefinition objectDefinitionBBB =
			ObjectDefinitionTestUtil.addObjectDefinition(
				"BBB", _objectDefinitionLocalService);

		ObjectRelationship objectRelationshipBBB_AAAA =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				objectDefinitionBBB.getObjectDefinitionId(),
				objectDefinitionAAAA.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				StringUtil.randomId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

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
			_getObjectRelationshipEdgeCandidatesJSONArray(
				0, objectDefinitionAAAA.getObjectDefinitionId()
			).toString());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationshipBBB_AAAA.getObjectRelationshipId());
	}

	private String _getEdgeLabel(
		ObjectDefinition objectDefinition,
		ObjectRelationship objectRelationship) {

		return StringUtil.appendParentheticalSuffix(
			objectDefinition.getLabel(LocaleUtil.getSiteDefault()),
			objectRelationship.getLabel(LocaleUtil.getSiteDefault()));
	}

	private JSONArray _getObjectRelationshipEdgeCandidatesJSONArray(
			int depth, long objectDefinitionId)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.addParameter("depth", String.valueOf(depth));
		mockLiferayResourceRequest.addParameter(
			"objectDefinitionId", String.valueOf(objectDefinitionId));
		mockLiferayResourceRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG,
			PortletConfigFactoryUtil.create(
				_portletLocalService.getPortletById(
					ObjectPortletKeys.OBJECT_DEFINITIONS),
				null));

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONArray(
			byteArrayOutputStream.toString());
	}

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

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

	@Inject
	private TreeFactory _treeFactory;

}