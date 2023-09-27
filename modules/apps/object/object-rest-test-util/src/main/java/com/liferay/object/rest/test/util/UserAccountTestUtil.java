/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.test.util;

import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Carlos Correa
 */
public class UserAccountTestUtil {

	public static JSONObject addUserAccountJSONObject(
			SystemObjectDefinitionManager systemObjectDefinitionManager,
			Map<String, Serializable> values)
		throws Exception {

		UserAccount userAccount = randomUserAccount();

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			systemObjectDefinitionManager.getJaxRsApplicationDescriptor();

		return HTTPTestUtil.invokeToJSONObject(
			_toBody(userAccount, values),
			jaxRsApplicationDescriptor.getRESTContextPath(), Http.Method.POST);
	}

	public static UserAccount randomUserAccount() {
		return new UserAccount() {
			{
				additionalName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				alternateName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				birthDate = RandomTestUtil.nextDate();
				currentPassword = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dashboardURL = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				familyName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				givenName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				honorificPrefix = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				honorificSuffix = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				image = StringUtil.toLowerCase(RandomTestUtil.randomString());
				jobTitle = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				lastLoginDate = RandomTestUtil.nextDate();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				password = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				profileURL = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	public static JSONObject updateUserAccountJSONObject(
			SystemObjectDefinitionManager systemObjectDefinitionManager,
			JSONObject userAccountJSONObject, Map<String, Serializable> values)
		throws Exception {

		UserAccount userAccount = randomUserAccount();

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			systemObjectDefinitionManager.getJaxRsApplicationDescriptor();

		return HTTPTestUtil.invokeToJSONObject(
			_toBody(userAccount, values),
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				StringPool.SLASH, userAccountJSONObject.get("id")),
			Http.Method.PUT);
	}

	public static JSONObject updateUserAccountJSONObjectByExternalReferenceCode(
			SystemObjectDefinitionManager systemObjectDefinitionManager,
			JSONObject userAccountJSONObject, Map<String, Serializable> values)
		throws Exception {

		UserAccount userAccount = randomUserAccount();

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			systemObjectDefinitionManager.getJaxRsApplicationDescriptor();

		return HTTPTestUtil.invokeToJSONObject(
			_toBody(userAccount, values),
			StringBundler.concat(
				jaxRsApplicationDescriptor.getRESTContextPath(),
				"/by-external-reference-code/",
				userAccountJSONObject.get("externalReferenceCode")),
			Http.Method.PUT);
	}

	private static String _toBody(
			UserAccount userAccount, Map<String, Serializable> values)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userAccount.toString());

		if (values != null) {
			for (Map.Entry<String, Serializable> entry : values.entrySet()) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

}