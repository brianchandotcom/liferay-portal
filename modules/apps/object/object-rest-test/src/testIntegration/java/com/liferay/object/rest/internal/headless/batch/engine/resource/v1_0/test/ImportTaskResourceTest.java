/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mauricio Valdivia
 */
@FeatureFlags("LPD-29367")
@RunWith(Arquillian.class)
public class ImportTaskResourceTest extends BaseTaskResourceTest {

	@Test
	public void testPostImportTask() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		ObjectEntry objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, OBJECT_FIELD_NAME_TEXT, "TestObject1");

		ObjectEntry objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, OBJECT_FIELD_NAME_TEXT, "TestObject2");

		JSONObject object1BeforeImportJSONObject =
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					objectDefinition.getRESTContextPath(),
					"/by-external-reference-code/",
					objectEntry1.getExternalReferenceCode(),
					"?nestedFields=permissions"),
				Http.Method.GET);

		JSONObject object2BeforeImportJSONObject =
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					objectDefinition.getRESTContextPath(),
					"/by-external-reference-code/",
					objectEntry2.getExternalReferenceCode(),
					"?nestedFields=permissions"),
				Http.Method.GET);

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			TestPropsValues.getCompanyId(), objectEntry2.getModelClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntry2.getPrimaryKey()), role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			TestPropsValues.getCompanyId(), objectEntry2.getModelClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntry1.getPrimaryKey()), role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				StringBundler.concat("[", object1BeforeImportJSONObject, "]"),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?taskItemDelegateName=", objectDefinition.getName(),
					"&createStrategy=UPSERT"),
				Http.Method.POST));

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				StringBundler.concat("[", object2BeforeImportJSONObject, "]"),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?taskItemDelegateName=", objectDefinition.getName(),
					"&createStrategy=UPSERT&restrictedFieldNames=permissions,",
					OBJECT_FIELD_NAME_TEXT),
				Http.Method.POST));

		JSONObject object1AfterImportJSONObject =
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					objectDefinition.getRESTContextPath(),
					"/by-external-reference-code/",
					objectEntry1.getExternalReferenceCode(),
					"?nestedFields=permissions"),
				Http.Method.GET);

		JSONObject object2AfterImportJSONObject =
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					objectDefinition.getRESTContextPath(),
					"/by-external-reference-code/",
					objectEntry2.getExternalReferenceCode(),
					"?nestedFields=permissions"),
				Http.Method.GET);

		Assert.assertNotEquals(
			object1AfterImportJSONObject.get("permissions"),
			object2AfterImportJSONObject.get("permissions"));

		Assert.assertEquals(
			object1AfterImportJSONObject.get(
				"permissions"
			).toString(),
			object1BeforeImportJSONObject.get(
				"permissions"
			).toString());

		Assert.assertNotEquals(
			object2BeforeImportJSONObject.get("permissions"),
			object2AfterImportJSONObject.get("permissions"));
		Assert.assertEquals(
			1,
			object1AfterImportJSONObject.getJSONArray(
				"permissions"
			).length());

		Assert.assertEquals(
			2,
			object2AfterImportJSONObject.getJSONArray(
				"permissions"
			).length());
		Assert.assertEquals(
			object1AfterImportJSONObject.getJSONArray(
				"permissions"
			).getJSONObject(
				0
			).get(
				"roleName"
			),
			RoleConstants.OWNER);

		_testRoleExistInPermissions(
			object2AfterImportJSONObject.getJSONArray("permissions"),
			role.getName());
	}

	private void _testRoleExistInPermissions(
		JSONArray permissionsJSONArray, String expectedRoleName) {

		boolean roleFound = false;

		for (int i = 0; i < permissionsJSONArray.length(); i++) {
			JSONObject permissionJSONObject =
				permissionsJSONArray.getJSONObject(i);

			if (permissionJSONObject.has("roleName")) {
				String roleName = permissionJSONObject.getString("roleName");

				if (expectedRoleName.equals(roleName)) {
					roleFound = true;

					break;
				}
			}
		}

		Assert.assertTrue(roleFound);
	}

}