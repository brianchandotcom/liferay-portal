/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.headless.batch.engine.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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

		// With "permissions" and "createStrategy" INSERT

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		JSONObject beforeImportJSONObject = JSONUtil.put(
			OBJECT_FIELD_NAME_TEXT, RandomTestUtil.randomString()
		).put(
			"externalReferenceCode", RandomTestUtil.randomString()
		).put(
			"permissions",
			JSONUtil.putAll(
				JSONUtil.put(
					"actionIds", JSONUtil.putAll("VIEW")
				).put(
					"roleName", role.getName()
				))
		);

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.putAll(
					beforeImportJSONObject
				).toString(),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?createStrategy=INSERT&taskItemDelegateName=",
					objectDefinition.getName()),
				Http.Method.POST));

		JSONAssert.assertEquals(
			JSONUtil.merge(
				beforeImportJSONObject,
				JSONUtil.put(
					"permissions",
					JSONUtil.putAll(
						JSONUtil.put(
							"actionIds", JSONUtil.putAll("VIEW")
						).put(
							"roleName", role.getName()
						)))
			).toString(),
			_getJSONObject(
				beforeImportJSONObject.getString("externalReferenceCode")
			).toString(),
			JSONCompareMode.LENIENT);

		// With "permissions" and "createStrategy" UPSERT

		beforeImportJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				OBJECT_FIELD_NAME_TEXT, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", RandomTestUtil.randomString()
			).toString(),
			StringBundler.concat(
				objectDefinition.getRESTContextPath(),
				"?nestedFields=permissions",
				"&restrictFields=dateCreated,dateModified"),
			Http.Method.POST);

		beforeImportJSONObject = beforeImportJSONObject.put(
			"permissions",
			beforeImportJSONObject.getJSONArray(
				"permissions"
			).put(
				JSONUtil.put(
					"actionIds", JSONUtil.putAll("VIEW")
				).put(
					"roleName", role.getName()
				)
			));

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.putAll(
					beforeImportJSONObject
				).toString(),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?createStrategy=UPSERT&taskItemDelegateName=",
					objectDefinition.getName()),
				Http.Method.POST));

		JSONAssert.assertEquals(
			JSONUtil.merge(
				beforeImportJSONObject,
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
						)))
			).toString(),
			_getJSONObject(
				beforeImportJSONObject.getString("externalReferenceCode")
			).toString(),
			JSONCompareMode.LENIENT);

		// With "batchRestrictFields" query parameter

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, OBJECT_FIELD_NAME_TEXT, "TestObject");

		beforeImportJSONObject = _getJSONObject(
			objectEntry.getExternalReferenceCode());

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.putAll(
					beforeImportJSONObject.put(
						"permissions",
						beforeImportJSONObject.getJSONArray(
							"permissions"
						).put(
							JSONUtil.put(
								"actionIds", JSONUtil.putAll("VIEW")
							).put(
								"roleName", role.getName()
							)
						))
				).toString(),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?taskItemDelegateName=", objectDefinition.getName(),
					"&createStrategy=UPSERT&batchRestrictFields=permissions,",
					OBJECT_FIELD_NAME_TEXT),
				Http.Method.POST));

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
			_getJSONObject(
				objectEntry.getExternalReferenceCode()
			).toString(),
			JSONCompareMode.LENIENT);

		// With empty "permissions" and "createStrategy" INSERT

		beforeImportJSONObject = JSONUtil.put(
			OBJECT_FIELD_NAME_TEXT, RandomTestUtil.randomString()
		).put(
			"externalReferenceCode", RandomTestUtil.randomString()
		).put(
			"permissions", JSONFactoryUtil.createJSONArray()
		);

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.putAll(
					beforeImportJSONObject
				).toString(),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?createStrategy=INSERT&taskItemDelegateName=",
					objectDefinition.getName()),
				Http.Method.POST));

		JSONAssert.assertEquals(
			JSONUtil.merge(
				beforeImportJSONObject,
				JSONUtil.put("permissions", JSONFactoryUtil.createJSONArray())
			).toString(),
			_getJSONObject(
				beforeImportJSONObject.getString("externalReferenceCode")
			).toString(),
			JSONCompareMode.LENIENT);

		// With empty "permissions" and "createStrategy" UPSERT

		beforeImportJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				OBJECT_FIELD_NAME_TEXT, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", RandomTestUtil.randomString()
			).toString(),
			StringBundler.concat(
				objectDefinition.getRESTContextPath(),
				"?nestedFields=permissions",
				"&restrictFields=dateCreated,dateModified"),
			Http.Method.POST
		).put(
			"permissions", JSONFactoryUtil.createJSONArray()
		);

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.putAll(
					beforeImportJSONObject
				).toString(),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?createStrategy=UPSERT&taskItemDelegateName=",
					objectDefinition.getName()),
				Http.Method.POST));

		JSONAssert.assertEquals(
			JSONUtil.merge(
				beforeImportJSONObject,
				JSONUtil.put("permissions", JSONFactoryUtil.createJSONArray())
			).toString(),
			_getJSONObject(
				beforeImportJSONObject.getString("externalReferenceCode")
			).toString(),
			JSONCompareMode.LENIENT);

		// With no "permissions" and "createStrategy" INSERT

		beforeImportJSONObject = JSONUtil.put(
			OBJECT_FIELD_NAME_TEXT, RandomTestUtil.randomString()
		).put(
			"externalReferenceCode", RandomTestUtil.randomString()
		);

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.putAll(
					beforeImportJSONObject
				).toString(),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?createStrategy=INSERT&taskItemDelegateName=",
					objectDefinition.getName()),
				Http.Method.POST));

		JSONAssert.assertEquals(
			JSONUtil.merge(
				beforeImportJSONObject,
				JSONUtil.put(
					"permissions",
					JSONUtil.putAll(
						JSONUtil.put(
							"actionIds",
							JSONUtil.putAll(
								"DELETE", "PERMISSIONS", "UPDATE", "VIEW")
						).put(
							"roleName", "Owner"
						)))
			).toString(),
			_getJSONObject(
				beforeImportJSONObject.getString("externalReferenceCode")
			).toString(),
			JSONCompareMode.LENIENT);

		// With no "permissions" and "createStrategy" UPSERT

		beforeImportJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				OBJECT_FIELD_NAME_TEXT, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", RandomTestUtil.randomString()
			).toString(),
			StringBundler.concat(
				objectDefinition.getRESTContextPath(),
				"?nestedFields=permissions",
				"&restrictFields=dateCreated,dateModified"),
			Http.Method.POST
		).put(
			"permissions", (JSONObject)null
		);

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.putAll(
					beforeImportJSONObject
				).toString(),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?createStrategy=UPSERT&taskItemDelegateName=",
					objectDefinition.getName()),
				Http.Method.POST));

		JSONAssert.assertEquals(
			JSONUtil.merge(
				beforeImportJSONObject,
				JSONUtil.put(
					"permissions",
					JSONUtil.putAll(
						JSONUtil.put(
							"actionIds",
							JSONUtil.putAll(
								"DELETE", "PERMISSIONS", "UPDATE", "VIEW")
						).put(
							"roleName", "Owner"
						)))
			).toString(),
			_getJSONObject(
				beforeImportJSONObject.getString("externalReferenceCode")
			).toString(),
			JSONCompareMode.LENIENT);

		// Without "batchRestrictFields" query parameter

		objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, OBJECT_FIELD_NAME_TEXT, "TestObject");

		beforeImportJSONObject = _getJSONObject(
			objectEntry.getExternalReferenceCode());

		waitForFinish(
			"COMPLETED", true,
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.putAll(
					beforeImportJSONObject.put(
						"permissions",
						beforeImportJSONObject.getJSONArray(
							"permissions"
						).put(
							JSONUtil.put(
								"actionIds", JSONUtil.putAll("VIEW")
							).put(
								"roleName", role.getName()
							)
						))
				).toString(),
				StringBundler.concat(
					"headless-batch-engine/v1.0/import-task",
					"/com.liferay.object.rest.dto.v1_0.ObjectEntry",
					"?taskItemDelegateName=", objectDefinition.getName(),
					"&createStrategy=UPSERT"),
				Http.Method.POST));

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
			_getJSONObject(
				objectEntry.getExternalReferenceCode()
			).toString(),
			JSONCompareMode.LENIENT);
	}

	private JSONObject _getJSONObject(String externalReferenceCode)
		throws Exception {

		return HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				objectDefinition.getRESTContextPath(),
				"/by-external-reference-code/", externalReferenceCode,
				"?nestedFields=permissions"),
			Http.Method.GET);
	}

}