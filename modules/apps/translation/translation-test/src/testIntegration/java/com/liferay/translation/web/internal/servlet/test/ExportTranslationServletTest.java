/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.web.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesService;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.translation.test.util.TranslationTestUtil;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Manuel Rives
 */
@RunWith(Arquillian.class)
public class ExportTranslationServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		if (_ctCollection != null) {
			_ctPreferencesService.checkoutCTCollection(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				CTConstants.CT_COLLECTION_ID_PRODUCTION);

			_ctCollectionLocalService.deleteCTCollection(_ctCollection);
		}
	}

	@Test
	@TestInfo("LPD-88077")
	public void testDoGetExportsArticleCreatedInsidePublication()
		throws Exception {

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		JournalArticle journalArticle;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			journalArticle = TranslationTestUtil.getJournalArticle(
				_group, _ddmFormDeserializer);
		}

		_ctPreferencesService.checkoutCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_ctCollection.getCtCollectionId());

		User user = _userLocalService.getUser(TestPropsValues.getUserId());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.USER, user);
		mockHttpServletRequest.setMethod("GET");
		mockHttpServletRequest.setParameter(
			"classNameId",
			String.valueOf(_portal.getClassNameId(JournalArticle.class)));
		mockHttpServletRequest.setParameter(
			"classPK", String.valueOf(journalArticle.getResourcePrimKey()));
		mockHttpServletRequest.setParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		mockHttpServletRequest.setParameter(
			"sourceLanguageId", LocaleUtil.toLanguageId(LocaleUtil.US));
		mockHttpServletRequest.setParameter(
			"targetLanguageIds", LocaleUtil.toLanguageId(LocaleUtil.SPAIN));
		mockHttpServletRequest.setParameter(
			"xliffMimeType", _MIMETYPE_XLIFF_1_2);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		ReflectionTestUtil.invoke(
			_servlet, "doGet",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class
			},
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		byte[] bytes = mockHttpServletResponse.getContentAsByteArray();

		Assert.assertTrue(bytes.length > 0);
	}

	private static final String _MIMETYPE_XLIFF_1_2 = "application/x-xliff+xml";

	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTPreferencesService _ctPreferencesService;

	@Inject(filter = "ddm.form.deserializer.type=json")
	private DDMFormDeserializer _ddmFormDeserializer;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "osgi.http.whiteboard.servlet.pattern=/translation/export_translation"
	)
	private Servlet _servlet;

	@Inject
	private UserLocalService _userLocalService;

}