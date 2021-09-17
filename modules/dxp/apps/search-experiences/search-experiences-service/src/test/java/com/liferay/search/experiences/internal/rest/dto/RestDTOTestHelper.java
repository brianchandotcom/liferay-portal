/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.rest.dto;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.search.experiences.internal.util.ResourceUtil;

import org.junit.Assert;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class RestDTOTestHelper {

	public RestDTOTestHelper(Object object, TestName testName) {
		_class = object.getClass();
		_methodName = testName.getMethodName();
	}

	public void assertJSONString(Object object) throws Exception {
		Assert.assertEquals(
			formatJSON(getExpectedJSONString()), formatJSON(object.toString()));
	}

	public String getExpectedJSONString() {
		return ResourceUtil.getResourceAsString(
			_class,
			StringBundler.concat(
				_class.getSimpleName(), StringPool.PERIOD, _methodName,
				".json"));
	}

	protected static String formatJSON(String jsonString) throws Exception {
		return String.valueOf(JSONFactoryUtil.createJSONObject(jsonString));
	}

	private final Class<?> _class;
	private final String _methodName;

}