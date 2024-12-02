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
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Mauricio Valdivia
 */
@FeatureFlags("LPD-29367")
@RunWith(Arquillian.class)
public class ImportTaskResourceTest extends BaseTaskResourceTestCase {

	@Test
	public void testPostImportTask() throws Exception {

		// With "restrictedFieldNames" query parameter

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, OBJECT_FIELD_NAME_TEXT, "TestObject");

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		JSONObject beforeImportJSONObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				objectDefinition.getRESTContextPath(),
				"/by-external-reference-code/",
				objectEntry.getExternalReferenceCode(),
				"?nestedFields=permissions"),
			Http.Method.GET);

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				StringBundler.concat(
					"[", _addViewPermission(beforeImportJSONObject, role), "]"),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?taskItemDelegateName=", objectDefinition.getName(),
					"&createStrategy=UPSERT&restrictedFieldNames=permissions,",
					OBJECT_FIELD_NAME_TEXT),
				Http.Method.POST));

		JSONObject afterImport1JSONObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				objectDefinition.getRESTContextPath(),
				"/by-external-reference-code/",
				objectEntry.getExternalReferenceCode(),
				"?nestedFields=permissions"),
			Http.Method.GET);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"permissions",
				JSONUtil.putAll(
					JSONUtil.put(
						"actionIds",
						JSONUtil.putAll(
							"DELETE", "PERMISSIONS", "UPDATE", "VIEW")
					).put(
						"roleName", "Owner"
					))
			).toString(),
			afterImport1JSONObject.toString(), JSONCompareMode.LENIENT);

		// Without "restrictedFieldNames" query parameter

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				StringBundler.concat(
					"[", _addViewPermission(beforeImportJSONObject, role), "]"),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?taskItemDelegateName=", objectDefinition.getName(),
					"&createStrategy=UPSERT"),
				Http.Method.POST));

		JSONObject afterImport2JSONObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				objectDefinition.getRESTContextPath(),
				"/by-external-reference-code/",
				objectEntry.getExternalReferenceCode(),
				"?nestedFields=permissions"),
			Http.Method.GET);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"permissions",
				JSONUtil.putAll(
					JSONUtil.put(
						"actionIds",
						JSONUtil.putAll(
							"DELETE", "PERMISSIONS", "UPDATE", "VIEW")
					).put(
						"roleName", "Owner"
					),
					JSONUtil.put(
						"actionIds", JSONUtil.putAll("VIEW")
					).put(
						"roleName", role.getName()
					))
			).toString(),
			afterImport2JSONObject.toString(), JSONCompareMode.LENIENT);
	}

	private JSONObject _addViewPermission(JSONObject jsonObject, Role role) {
		JSONArray permissionsJSONArray = jsonObject.getJSONArray("permissions");

		permissionsJSONArray.put(
			JSONUtil.put(
				"actionIds", JSONUtil.putAll("VIEW")
			).put(
				"roleName", role.getName()
			));

		return jsonObject;
	}

}