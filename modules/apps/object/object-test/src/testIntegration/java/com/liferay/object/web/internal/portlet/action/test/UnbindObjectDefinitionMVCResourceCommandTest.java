/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.exception.ObjectDefinitionRootObjectDefinitionIdException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.test.util.TreeTestUtil;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class UnbindObjectDefinitionMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Test
	public void testUnbindObjectDefinition() throws Exception {

		// Unbind object definition internal node

		TreeTestUtil.assertTree(
			LinkedHashMapBuilder.put(
				"A", new String[] {"AA", "AB"}
			).put(
				"AA", new String[] {"AAA", "AAB"}
			).put(
				"AB", new String[0]
			).put(
				"AAA", new String[0]
			).put(
				"AAB", new String[0]
			).build(),
			TreeTestUtil.createTree(
				_bindObjectDefinitionsMVCResourceCommand,
				_objectDefinitionLocalService, _objectRelationshipLocalService,
				_portletLocalService, _treeFactory),
			_objectDefinitionLocalService);

		_unbind("C_AA");

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), "C_A");

		TreeTestUtil.assertTree(
			LinkedHashMapBuilder.put(
				"A", new String[] {"AB"}
			).put(
				"AB", new String[0]
			).build(),
			_treeFactory.create(objectDefinition.getObjectDefinitionId()),
			_objectDefinitionLocalService);

		// Unbind object definition leaf node

		_unbind("C_AB");

		TreeTestUtil.assertTree(
			LinkedHashMapBuilder.put(
				"A", new String[0]
			).build(),
			_treeFactory.create(objectDefinition.getObjectDefinitionId()),
			_objectDefinitionLocalService);

		// Unbind object definition root node

		_unbind("C_A");

		AssertUtils.assertFailure(
			ObjectDefinitionRootObjectDefinitionIdException.class,
			"The object definition id " +
				objectDefinition.getObjectDefinitionId() +
					" is not inside a hierarchical structure.",
			() -> _treeFactory.create(
				objectDefinition.getObjectDefinitionId()));
	}

	private void _unbind(String objectDefinitionName) throws Exception {
		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), objectDefinitionName);

		mockLiferayResourceRequest.addParameter(
			"objectDefinitionId",
			String.valueOf(objectDefinition.getObjectDefinitionId()));

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

		_unbindObjectDefinitionMVCResourceCommand.serveResource(
			mockLiferayResourceRequest, null);
	}

	@Inject(
		filter = "mvc.command.name=/object_definitions/bind_object_definitions"
	)
	private MVCResourceCommand _bindObjectDefinitionsMVCResourceCommand;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private TreeFactory _treeFactory;

	@Inject(
		filter = "mvc.command.name=/object_definitions/unbind_object_definition"
	)
	private MVCResourceCommand _unbindObjectDefinitionMVCResourceCommand;

}