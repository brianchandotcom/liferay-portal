/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.util;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.internal.model.listener.CompanyModelListener;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.io.File;
import java.io.FileInputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gustavo Lima
 */
public class SXPElementUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws PortalException {
		_sxpElementLocalService = Mockito.mock(SXPElementLocalService.class);

		_company = Mockito.mock(Company.class);

		_setUpCompanyAndUser();
	}

	@Test
	public void testRangeQueriesDoNotUseDeprecatedParameters()
		throws Exception {

		URL url = CompanyModelListener.class.getResource("dependencies");

		File directory = new File(url.toURI());

		JSONFactoryImpl jsonFactoryImpl = new JSONFactoryImpl();

		for (File file : directory.listFiles()) {
			_assertRangeQueries(
				jsonFactoryImpl.createJSONObject(
					StringUtil.read(new FileInputStream(file))));
		}
	}

	@Test
	public void testTitleAndDescriptionNotNullWhenEnUsIsNotAvailable()
		throws Exception {

		_setUpLanguageUtil();

		_setUpSXPElements();

		SXPElementUtil.addSXPElements(_company, _sxpElementLocalService);

		Mockito.verify(
			_sxpElementLocalService, Mockito.atLeastOnce()
		).addSXPElement(
			Mockito.anyString(), Mockito.anyLong(), Mockito.eq(_localizedMap),
			Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
			Mockito.anyBoolean(), Mockito.anyString(),
			Mockito.eq(_localizedMap), Mockito.anyInt(),
			Mockito.any(ServiceContext.class)
		);
	}

	private static void _setUpCompanyAndUser() throws PortalException {
		Mockito.when(
			_company.getCompanyId()
		).thenReturn(
			1L
		);

		Mockito.when(
			_company.getGroupId()
		).thenReturn(
			1L
		);

		User user = Mockito.mock(User.class);

		Mockito.when(
			_company.getGuestUser()
		).thenReturn(
			user
		);

		Mockito.when(
			user.getUserId()
		).thenReturn(
			1L
		);
	}

	private void _assertRangeQueries(Object object) {
		if (object instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)object;

			for (Object value : jsonArray) {
				_assertRangeQueries(value);
			}
		}
		else if (object instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)object;

			for (String key : jsonObject.keySet()) {
				if (key.equals("range")) {
					JSONObject rangeJSONObject = jsonObject.getJSONObject(key);

					for (String field : rangeJSONObject.keySet()) {
						JSONObject fieldJSONObject =
							rangeJSONObject.getJSONObject(field);

						if (fieldJSONObject == null) {
							continue;
						}

						Assert.assertFalse(fieldJSONObject.has("from"));
						Assert.assertFalse(
							fieldJSONObject.has("include_lower"));
						Assert.assertFalse(
							fieldJSONObject.has("include_upper"));
						Assert.assertFalse(fieldJSONObject.has("to"));
					}
				}

				_assertRangeQueries(jsonObject.get(key));
			}
		}
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		languageUtil.setLanguage(language);

		Mockito.when(
			language.isAvailableLocale(LocaleUtil.US)
		).thenReturn(
			false
		);
	}

	private void _setUpSXPElements() {
		List<SXPElement> sxpElements = new ArrayList<>();

		Map<String, String> i18nMap = HashMapBuilder.put(
			LocaleUtil.US.toString(), RandomTestUtil.randomString()
		).build();

		_localizedMap = HashMapBuilder.put(
			LocaleUtil.US, i18nMap.get(LocaleUtil.US.toString())
		).build();

		sxpElements.add(
			new SXPElement() {
				{
					setDescription_i18n(() -> i18nMap);
					setExternalReferenceCode(RandomTestUtil.randomString());
					setTitle_i18n(() -> i18nMap);
				}
			});

		ReflectionTestUtil.setFieldValue(
			SXPElementUtil.class, "_sxpElements", sxpElements);
	}

	private static Company _company;
	private static SXPElementLocalService _sxpElementLocalService;

	private Map<Locale, String> _localizedMap;

}