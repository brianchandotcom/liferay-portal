/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.object.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.launch.constants.LaunchConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dave Truong
 */
@RunWith(Arquillian.class)
public class LaunchObjectDefinitionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testLaunchDefinitionPublished() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					LaunchConstants.OBJECT_DEFINITION_ERC_LAUNCH,
					PortalUtil.getDefaultCompanyId());

		Assert.assertEquals("Launch", objectDefinition.getName());
		Assert.assertEquals("company", objectDefinition.getScope());
		Assert.assertTrue(objectDefinition.isSystem());

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		Assert.assertTrue(_hasObjectField("description", objectFields));
		Assert.assertTrue(_hasObjectField("name", objectFields));
	}

	@Test
	public void testLaunchEntryDefinitionPublished() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					LaunchConstants.OBJECT_DEFINITION_ERC_LAUNCH_ENTRY,
					PortalUtil.getDefaultCompanyId());

		Assert.assertEquals("LaunchEntry", objectDefinition.getName());
		Assert.assertEquals("company", objectDefinition.getScope());
		Assert.assertTrue(objectDefinition.isSystem());

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		Assert.assertTrue(_hasObjectField("assetClassName", objectFields));
		Assert.assertTrue(_hasObjectField("assetClassPK", objectFields));
		Assert.assertTrue(_hasObjectField("classVersion", objectFields));
	}

	@Test
	public void testLaunchToLaunchEntriesRelationshipPublished()
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					LaunchConstants.OBJECT_DEFINITION_ERC_LAUNCH,
					PortalUtil.getDefaultCompanyId());

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByExternalReferenceCode(
					LaunchConstants.
						OBJECT_RELATIONSHIP_ERC_LAUNCH_TO_LAUNCH_ENTRY,
					objectDefinition.getObjectDefinitionId());

		Assert.assertEquals("cascade", objectRelationship.getDeletionType());
		Assert.assertEquals("oneToMany", objectRelationship.getType());
	}

	private boolean _hasObjectField(
		String objectFieldName, List<ObjectField> objectFields) {

		for (ObjectField objectField : objectFields) {
			if (objectFieldName.equals(objectField.getName())) {
				return true;
			}
		}

		return false;
	}

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}