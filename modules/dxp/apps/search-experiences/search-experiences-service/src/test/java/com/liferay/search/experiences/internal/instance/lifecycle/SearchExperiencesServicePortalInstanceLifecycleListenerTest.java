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

package com.liferay.search.experiences.internal.instance.lifecycle;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.internal.instance.lifecycle.SearchExperiencesServicePortalInstanceLifecycleListener.SXPElementJSONStringsLookup;
import com.liferay.search.experiences.internal.instance.lifecycle.SearchExperiencesServicePortalInstanceLifecycleListener.SXPElementLookup;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;

/**
 * @author André de Oliveira
 */
public class SearchExperiencesServicePortalInstanceLifecycleListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		Mockito.doReturn(
			_user
		).when(
			_company
		).getDefaultUser();

		ReflectionTestUtil.setFieldValue(
			_searchExperiencesServicePortalInstanceLifecycleListener,
			"_sxpElementLocalService", _sxpElementLocalService);

		_setUpLanguageUtil();
	}

	@Test
	public void testPortalInstanceRegistered() throws Exception {
		String title1 = "Charlie";
		String title2 = "Delta";

		SXPElement sxpElement1 = new SXPElement() {
			{
				title_i18n = _toLocalizationMap(title1);
			}
		};

		SXPElement sxpElement2 = new SXPElement() {
			{
				title_i18n = _toLocalizationMap(title2);
			}
		};

		_flagAsExisting(sxpElement1);

		try {
			_portalInstanceRegistered(
				"{INVALID}", sxpElement1.toString(), sxpElement2.toString());

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			MatcherAssert.assertThat(
				_getSuppressedMessage(runtimeException),
				CoreMatchers.containsString("{INVALID}"));
		}

		_verifyAddSXPElement(title1, Mockito.never());
		_verifyAddSXPElement(title2, Mockito.times(1));
	}

	private void _flagAsExisting(SXPElement sxpElement1) {
		ReflectionTestUtil.setFieldValue(
			_searchExperiencesServicePortalInstanceLifecycleListener,
			"_sxpElementLookup",
			(SXPElementLookup)sxpElement2 -> Objects.equals(
				_getTitle(sxpElement1), _getTitle(sxpElement2)));
	}

	private String _getSuppressedMessage(RuntimeException runtimeException) {
		Throwable[] throwables = runtimeException.getSuppressed();

		if (throwables.length == 1) {
			return throwables[0].getMessage();
		}

		throw runtimeException;
	}

	private String _getTitle(SXPElement sxpElement) {
		return MapUtil.getString(sxpElement.getTitle_i18n(), "en_US");
	}

	private void _portalInstanceRegistered(String... jsons) throws Exception {
		ReflectionTestUtil.setFieldValue(
			_searchExperiencesServicePortalInstanceLifecycleListener,
			"_sxpElementJSONStringsLookup",
			(SXPElementJSONStringsLookup)() -> Stream.of(jsons));

		_searchExperiencesServicePortalInstanceLifecycleListener.
			portalInstanceRegistered(_company);
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());
	}

	private Map<String, String> _toLocalizationMap(String title) {
		return Collections.singletonMap("en_US", title);
	}

	private com.liferay.search.experiences.model.SXPElement
			_verifyAddSXPElement(
				String title, VerificationMode verificationMode)
		throws Exception {

		return Mockito.verify(
			_sxpElementLocalService, verificationMode
		).addSXPElement(
			Mockito.anyInt(), Mockito.any(), Mockito.any(), Mockito.eq(true),
			Mockito.eq(Collections.singletonMap(LocaleUtil.US, title)),
			Mockito.anyInt(), Mockito.any()
		);
	}

	@Mock
	private Company _company;

	private final SearchExperiencesServicePortalInstanceLifecycleListener
		_searchExperiencesServicePortalInstanceLifecycleListener =
			new SearchExperiencesServicePortalInstanceLifecycleListener();

	@Mock
	private SXPElementLocalService _sxpElementLocalService;

	@Mock
	private User _user;

}