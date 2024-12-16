/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.CharArrayWriter;
import java.io.IOException;

import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Iván Zaera Avellón
 */
public class JSImportMapsCacheTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_jsImportMapsCache = new JSImportMapsCache();
	}

	@Test
	public void testCompanyCleanUp() throws JSONException {
		JSONObject jsonObject = _jsonFactoryImpl.createJSONObject(
			"{\"react\":\"https://unpkg.com/react@19.0.0/index.js\"}");

		JSImportMapsRegistration jsImportMapsRegistration =
			_jsImportMapsCache.register(1, jsonObject, null);

		ConcurrentMap<Long, ConcurrentMap<Long, String>>
			globalImportMapsValuesMap = ReflectionTestUtil.getFieldValue(
				_jsImportMapsCache, "_globalImportMapsValuesMap");

		ConcurrentMap<Long, String> globalImportMapsValues =
			globalImportMapsValuesMap.get(1L);

		Assert.assertNotNull(globalImportMapsValues);

		jsImportMapsRegistration.unregister();

		globalImportMapsValues = globalImportMapsValuesMap.get(1L);

		Assert.assertNull(globalImportMapsValues);
	}

	@Test
	public void testWriteImportMapsForAllCompanies()
		throws IOException, JSONException {

		JSONObject jsonObject = _jsonFactoryImpl.createJSONObject(
			"{\"react\":\"https://unpkg.com/react@19.0.0/index.js\"}");

		JSImportMapsRegistration jsImportMapsRegistration1 =
			_jsImportMapsCache.register(
				JSImportMapsCache.COMPANY_ID_ALL, jsonObject, null);

		JSImportMapsRegistration jsImportMapsRegistration2 =
			_jsImportMapsCache.register(
				JSImportMapsCache.COMPANY_ID_ALL, jsonObject, "a-scope");

		Assert.assertEquals(
			StringBundler.concat(
				"{\"imports\":{\"react\":",
				"\"https://unpkg.com/react@19.0.0/index.js\"},\"scopes\":{",
				"\"a-scope\":{\"react\":",
				"\"https://unpkg.com/react@19.0.0/index.js\"}}}"),
			_getImportMaps(1));

		jsImportMapsRegistration1.unregister();

		Assert.assertEquals(
			"{\"imports\":{},\"scopes\":{\"a-scope\":{\"react\":" +
				"\"https://unpkg.com/react@19.0.0/index.js\"}}}",
			_getImportMaps(1));

		jsImportMapsRegistration2.unregister();

		Assert.assertEquals(
			"{\"imports\":{},\"scopes\":{}}", _getImportMaps(1));
	}

	@Test
	public void testWriteImportMapsForOneCompany()
		throws IOException, JSONException {

		JSONObject jsonObject = _jsonFactoryImpl.createJSONObject(
			"{\"react\":\"https://unpkg.com/react@19.0.0/index.js\"}");

		JSImportMapsRegistration jsImportMapsRegistration1 =
			_jsImportMapsCache.register(1, jsonObject, null);

		JSImportMapsRegistration jsImportMapsRegistration2 =
			_jsImportMapsCache.register(1, jsonObject, "a-scope");

		Assert.assertEquals(
			StringBundler.concat(
				"{\"imports\":{\"react\":",
				"\"https://unpkg.com/react@19.0.0/index.js\"},\"scopes\":{",
				"\"a-scope\":{\"react\":",
				"\"https://unpkg.com/react@19.0.0/index.js\"}}}"),
			_getImportMaps(1));

		jsImportMapsRegistration1.unregister();

		Assert.assertEquals(
			"{\"imports\":{},\"scopes\":{\"a-scope\":{\"react\":" +
				"\"https://unpkg.com/react@19.0.0/index.js\"}}}",
			_getImportMaps(1));

		jsImportMapsRegistration2.unregister();

		Assert.assertEquals(
			"{\"imports\":{},\"scopes\":{}}", _getImportMaps(1));
	}

	@Test
	public void testWriteImportMapsMixed() throws IOException, JSONException {
		JSONObject jsonObject = _jsonFactoryImpl.createJSONObject(
			"{\"jquery\":\"https://unpkg.com/jquery@3.7.1/dist/jquery.js\"}");

		JSImportMapsRegistration companyAllJSImportMapsRegistration =
			_jsImportMapsCache.register(
				JSImportMapsCache.COMPANY_ID_ALL, jsonObject, null);

		jsonObject = _jsonFactoryImpl.createJSONObject(
			"{\"react\":\"https://unpkg.com/react@19.0.0/index.js\"}");

		_jsImportMapsCache.register(1, jsonObject, null);

		jsonObject = _jsonFactoryImpl.createJSONObject(
			"{\"lodash\":\"https://unpkg.com/lodash@4.17.21/lodash.js\"}");

		JSImportMapsRegistration company2JSImportMapsRegistration =
			_jsImportMapsCache.register(2, jsonObject, null);

		Assert.assertEquals(
			StringBundler.concat(
				"{\"imports\":{\"jquery\":",
				"\"https://unpkg.com/jquery@3.7.1/dist/jquery.js\",\"react\":",
				"\"https://unpkg.com/react@19.0.0/index.js\"}",
				",\"scopes\":{}}"),
			_getImportMaps(1));

		Assert.assertEquals(
			StringBundler.concat(
				"{\"imports\":{\"jquery\":",
				"\"https://unpkg.com/jquery@3.7.1/dist/jquery.js\",\"lodash\":",
				"\"https://unpkg.com/lodash@4.17.21/lodash.js\"},",
				"\"scopes\":{}}"),
			_getImportMaps(2));

		company2JSImportMapsRegistration.unregister();

		Assert.assertEquals(
			StringBundler.concat(
				"{\"imports\":{\"jquery\":",
				"\"https://unpkg.com/jquery@3.7.1/dist/jquery.js\",\"react\":",
				"\"https://unpkg.com/react@19.0.0/index.js\"}",
				",\"scopes\":{}}"),
			_getImportMaps(1));
		Assert.assertEquals(
			StringBundler.concat(
				"{\"imports\":{\"jquery\":",
				"\"https://unpkg.com/jquery@3.7.1/dist/jquery.js\"},",
				"\"scopes\":{}}"),
			_getImportMaps(2));

		companyAllJSImportMapsRegistration.unregister();

		Assert.assertEquals(
			"{\"imports\":{\"react\":" +
				"\"https://unpkg.com/react@19.0.0/index.js\"},\"scopes\":{}}",
			_getImportMaps(1));
		Assert.assertEquals(
			"{\"imports\":{},\"scopes\":{}}", _getImportMaps(2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWriteImportMapsThrowsForCompanyIdAll() throws IOException {
		_getImportMaps(JSImportMapsCache.COMPANY_ID_ALL);
	}

	private String _getImportMaps(long companyId) throws IOException {
		CharArrayWriter charArrayWriter = new CharArrayWriter();

		_jsImportMapsCache.writeImportMaps(companyId, charArrayWriter);

		return charArrayWriter.toString();
	}

	private JSImportMapsCache _jsImportMapsCache;
	private final JSONFactoryImpl _jsonFactoryImpl = new JSONFactoryImpl();

}