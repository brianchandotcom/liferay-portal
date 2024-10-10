/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.indicator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noor Najjar
 */
@RunWith(Arquillian.class)
public class GetConflictInfoMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_ctCollection1 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, "P1", null);

		_ctCollection2 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, "P2", null);

		_group = GroupTestUtil.addGroup();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			_journalArticle = JournalTestUtil.addArticle(
				_group.getGroupId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());
		}
	}

	@Test
	public void testGetConflictIconWithNoConflicts() throws Exception {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			_assertGetConflictInfo("change-tracking-conflict-icon", "check");

			_journalArticle = JournalTestUtil.updateArticle(
				_journalArticle, "testModifyJournalArticle");

			_assertGetConflictInfo("change-tracking-conflict-icon", "check");
		}
	}

	@Test
	public void testGetConflictIconWithPossibleConflict() throws Exception {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection2.getCtCollectionId())) {

			JournalTestUtil.updateArticle(
				_journalArticle, "testModifyJournalArticle");
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			_journalArticle = JournalTestUtil.updateArticle(
				_journalArticle, "testModifyJournalArticle");

			_assertGetConflictInfo(
				"change-tracking-conflict-icon-warning", "warning-full");
		}
	}

	@Test
	public void testGetConflictInfoWithConflict() throws Exception {
		JournalArticle productionArticle = _journalArticle;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			_journalArticle = JournalTestUtil.updateArticle(
				_journalArticle, "testModifyJournalArticle");
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			JournalTestUtil.updateArticle(
				productionArticle, "testModifyJournalArticle");
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			_assertGetConflictInfo(
				"change-tracking-conflict-icon-danger", "warning-full");
		}
	}

	private void _assertGetConflictInfo(
			String expectedConflictIconClass, String expectedConflictIconName)
		throws Exception {

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			_getMockLiferayResourceRequest(
				_portal.getClassNameId(JournalArticle.class),
				_journalArticle.getPrimaryKey(),
				_ctCollection1.getCtCollectionId()),
			mockLiferayResourceResponse);

		JSONObject jsonObject = _getConflictInfoJSONObject(
			mockLiferayResourceResponse);

		Assert.assertEquals(
			expectedConflictIconClass,
			String.valueOf(jsonObject.get("conflictIconClass")));
		Assert.assertEquals(
			expectedConflictIconName,
			String.valueOf(jsonObject.get("conflictIconName")));
	}

	private JSONObject _getConflictInfoJSONObject(
			MockLiferayResourceResponse mockLiferayResourceResponse)
		throws Exception {

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
			long classNameId, long classPK, long ctCollectionId)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()));

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayResourceRequest.setParameter(
			"classNameId", String.valueOf(classNameId));
		mockLiferayResourceRequest.setParameter(
			"classPK", String.valueOf(classPK));
		mockLiferayResourceRequest.setParameter(
			"currentCTCollectionId", String.valueOf(ctCollectionId));

		return mockLiferayResourceRequest;
	}

	@DeleteAfterTestRun
	private static CTCollection _ctCollection1;

	@DeleteAfterTestRun
	private static CTCollection _ctCollection2;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@DeleteAfterTestRun
	private static Group _group;

	@DeleteAfterTestRun
	private static JournalArticle _journalArticle;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private static Portal _portal;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(filter = "mvc.command.name=/change_tracking/get_conflict_info")
	private MVCResourceCommand _mvcResourceCommand;

}