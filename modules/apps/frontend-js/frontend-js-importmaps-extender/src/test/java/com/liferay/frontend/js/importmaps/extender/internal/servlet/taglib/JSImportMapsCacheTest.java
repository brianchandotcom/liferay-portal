/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.servlet.taglib;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.CharArrayWriter;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

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
	public void testWriteImportMapsForAllCompanies() throws Exception {
		JSONObject jsonObject = JSONUtil.put(
			"react", "https://unpkg.com/react@19.0.0/index.js");

		JSImportMapsRegistration jsImportMapsRegistration1 =
			_jsImportMapsCache.register(
				JSImportMapsCache.COMPANY_ID_ALL, jsonObject, null);

		JSImportMapsRegistration jsImportMapsRegistration2 =
			_jsImportMapsCache.register(
				JSImportMapsCache.COMPANY_ID_ALL, jsonObject, "a-scope");

		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports",
				JSONUtil.put("react", "https://unpkg.com/react@19.0.0/index.js")
			).put(
				"scopes",
				JSONUtil.put(
					"a-scope",
					JSONUtil.put(
						"react", "https://unpkg.com/react@19.0.0/index.js"))
			).toString(),
			_getImportMaps(1), JSONCompareMode.LENIENT);

		jsImportMapsRegistration1.unregister();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports", JSONFactoryUtil.createJSONObject()
			).put(
				"scopes",
				JSONUtil.put(
					"a-scope",
					JSONUtil.put(
						"react", "https://unpkg.com/react@19.0.0/index.js"))
			).toString(),
			_getImportMaps(1), JSONCompareMode.LENIENT);

		jsImportMapsRegistration2.unregister();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports", JSONFactoryUtil.createJSONObject()
			).put(
				"scopes", JSONFactoryUtil.createJSONObject()
			).toString(),
			_getImportMaps(1), JSONCompareMode.LENIENT);
	}

	@Test
	public void testWriteImportMapsForOneCompany() throws Exception {
		JSONObject jsonObject = JSONUtil.put(
			"react", "https://unpkg.com/react@19.0.0/index.js");

		JSImportMapsRegistration jsImportMapsRegistration1 =
			_jsImportMapsCache.register(1, jsonObject, null);

		JSImportMapsRegistration jsImportMapsRegistration2 =
			_jsImportMapsCache.register(1, jsonObject, "a-scope");

		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports",
				JSONUtil.put("react", "https://unpkg.com/react@19.0.0/index.js")
			).put(
				"scopes",
				JSONUtil.put(
					"a-scope",
					JSONUtil.put(
						"react", "https://unpkg.com/react@19.0.0/index.js"))
			).toString(),
			_getImportMaps(1), JSONCompareMode.LENIENT);

		jsImportMapsRegistration1.unregister();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports", JSONFactoryUtil.createJSONObject()
			).put(
				"scopes",
				JSONUtil.put(
					"a-scope",
					JSONUtil.put(
						"react", "https://unpkg.com/react@19.0.0/index.js"))
			).toString(),
			_getImportMaps(1), JSONCompareMode.LENIENT);

		jsImportMapsRegistration2.unregister();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports", JSONFactoryUtil.createJSONObject()
			).put(
				"scopes", JSONFactoryUtil.createJSONObject()
			).toString(),
			_getImportMaps(1), JSONCompareMode.LENIENT);
	}

	@Test
	public void testWriteImportMapsMixed() throws Exception {
		JSONObject jsonObject = JSONUtil.put(
			"jquery", "https://unpkg.com/jquery@3.7.1/dist/jquery.js");

		JSImportMapsRegistration companyAllJSImportMapsRegistration =
			_jsImportMapsCache.register(
				JSImportMapsCache.COMPANY_ID_ALL, jsonObject, null);

		jsonObject = JSONUtil.put(
			"react", "https://unpkg.com/react@19.0.0/index.js");

		_jsImportMapsCache.register(1, jsonObject, null);

		jsonObject = JSONUtil.put(
			"lodash", "https://unpkg.com/lodash@4.17.21/lodash.js");

		JSImportMapsRegistration company2JSImportMapsRegistration =
			_jsImportMapsCache.register(2, jsonObject, null);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports",
				JSONUtil.put(
					"jquery", "https://unpkg.com/jquery@3.7.1/dist/jquery.js"
				).put(
					"react", "https://unpkg.com/react@19.0.0/index.js"
				)
			).put(
				"scopes", JSONFactoryUtil.createJSONObject()
			).toString(),
			_getImportMaps(1), JSONCompareMode.LENIENT);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports",
				JSONUtil.put(
					"jquery", "https://unpkg.com/jquery@3.7.1/dist/jquery.js"
				).put(
					"lodash", "https://unpkg.com/lodash@4.17.21/lodash.js"
				)
			).put(
				"scopes", JSONFactoryUtil.createJSONObject()
			).toString(),
			_getImportMaps(2), JSONCompareMode.LENIENT);

		company2JSImportMapsRegistration.unregister();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports",
				JSONUtil.put(
					"jquery", "https://unpkg.com/jquery@3.7.1/dist/jquery.js"
				).put(
					"react", "https://unpkg.com/react@19.0.0/index.js"
				)
			).put(
				"scopes", JSONFactoryUtil.createJSONObject()
			).toString(),
			_getImportMaps(1), JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports",
				JSONUtil.put(
					"jquery", "https://unpkg.com/jquery@3.7.1/dist/jquery.js")
			).put(
				"scopes", JSONFactoryUtil.createJSONObject()
			).toString(),
			_getImportMaps(2), JSONCompareMode.LENIENT);

		companyAllJSImportMapsRegistration.unregister();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports",
				JSONUtil.put("react", "https://unpkg.com/react@19.0.0/index.js")
			).put(
				"scopes", JSONFactoryUtil.createJSONObject()
			).toString(),
			_getImportMaps(1), JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"imports", JSONFactoryUtil.createJSONObject()
			).put(
				"scopes", JSONFactoryUtil.createJSONObject()
			).toString(),
			_getImportMaps(2), JSONCompareMode.LENIENT);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWriteImportMapsThrowsForCompanyIdAll() throws Exception {
		_getImportMaps(JSImportMapsCache.COMPANY_ID_ALL);
	}

	private String _getImportMaps(long companyId) throws Exception {
		CharArrayWriter charArrayWriter = new CharArrayWriter();

		_jsImportMapsCache.writeImportMaps(companyId, charArrayWriter);

		return charArrayWriter.toString();
	}

	private JSImportMapsCache _jsImportMapsCache;

}