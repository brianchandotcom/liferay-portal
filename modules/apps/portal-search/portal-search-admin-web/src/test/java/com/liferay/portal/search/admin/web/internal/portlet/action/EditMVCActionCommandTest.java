/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Gustavo Lima
 */
public class EditMVCActionCommandTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_portalUtilMockedStatic = Mockito.mockStatic(PortalUtil.class);
		_sessionErrorsMockedStatic = Mockito.mockStatic(SessionErrors.class);

		_setUpRedirectValue();
	}

	@AfterClass
	public static void tearDownClass() {
		_portalUtilMockedStatic.close();
		_sessionErrorsMockedStatic.close();
	}

	@Before
	public void setUp() {
		_actionResponse = Mockito.mock(ActionResponse.class);
		_setUpBackgroudTaskManager();
		_setUpIndexerWriterHelper();
		_setUpThemeDisplay();
	}

	@Test
	public void testReindexAll() throws Exception {
		_setUpPermissionCheckerOmniadmin(true);
		_setUpActionRequest(StringPool.BLANK, "reindex");
		_setUpHttpServletRequest(new String[] {RandomTestUtil.randomString()});

		_editMVCActionCommand.doProcessAction(_actionRequest, _actionResponse);

		_verifyIndexWriterHelperReindex(1);
		_verifyReindexIndexReindexer(1);
		_verifySendRedirect(1);
	}

	@Test
	public void testReindexDictionaries() throws Exception {
		_setUpPermissionCheckerOmniadmin(true);
		_setUpActionRequest("reindexDictionaries");
		_setUpHttpServletRequest(new String[] {RandomTestUtil.randomString()});

		_editMVCActionCommand.doProcessAction(_actionRequest, _actionResponse);

		Mockito.verify(
			_indexWriterHelper, Mockito.times(1)
		).indexQuerySuggestionDictionaries(
			0
		);

		Mockito.verify(
			_indexWriterHelper, Mockito.times(1)
		).indexSpellCheckerDictionaries(
			0
		);

		_verifySendRedirect(1);
	}

	@Test
	public void testReindexIndexReindexer() throws Exception {
		_setUpPermissionCheckerOmniadmin(true);
		_setUpActionRequest("reindexIndexReindexer");
		_setUpHttpServletRequest(new String[] {RandomTestUtil.randomString()});

		_editMVCActionCommand.doProcessAction(_actionRequest, _actionResponse);

		_verifyReindexIndexReindexer(1);
		_verifySendRedirect(1);
	}

	@Test
	public void testReindexMultipleCompanies() throws Exception {
		_setUpPermissionCheckerOmniadmin(true);
		_setUpActionRequest("reindex");
		_setUpHttpServletRequest(
			ArrayUtil.toStringArray(
				new long[] {
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
				}));

		_editMVCActionCommand.doProcessAction(_actionRequest, _actionResponse);

		_verifyIndexWriterHelperReindex(1);
		_verifySendRedirect(1);
	}

	@Test
	public void testReindexMultipleCompaniesWithoutPermission()
		throws Exception {

		_setUpActionRequest("reindex");
		_setUpPermissionCheckerOmniadmin(false);

		long[] companyIds = {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
		};

		_setUpHttpServletRequest(ArrayUtil.toStringArray(companyIds));
		_setUpPermissionCheckerCompanyAdmin(true, companyIds[0]);
		_setUpPermissionCheckerCompanyAdmin(false, companyIds[1]);

		_editMVCActionCommand.doProcessAction(_actionRequest, _actionResponse);

		_verifyPermissionCheckerIsCompanyAdmin(2);
		_verifyRenderParameterError();
		_verifySendRedirect(0);
	}

	@Test
	public void testReindexOneClassName() throws Exception {
		_setUpActionRequest("reindex");
		_setUpHttpServletRequest(new String[] {RandomTestUtil.randomString()});
		_setUpPermissionCheckerOmniadmin(true);

		_editMVCActionCommand.doProcessAction(_actionRequest, _actionResponse);

		_verifyIndexWriterHelperReindex(1);
		_verifyReindexIndexReindexer(0);
		_verifySendRedirect(1);
	}

	@Test
	public void testReindexWithoutPermission() throws Exception {
		long[] companyIds = {RandomTestUtil.randomLong()};

		_setUpHttpServletRequest(ArrayUtil.toStringArray(companyIds));
		_setUpPermissionCheckerCompanyAdmin(false, companyIds[0]);

		_setUpPermissionCheckerOmniadmin(false);

		_editMVCActionCommand.doProcessAction(_actionRequest, _actionResponse);

		_verifyPermissionCheckerIsCompanyAdmin(1);
		_verifyRenderParameterError();
		_verifySendRedirect(0);
	}

	private static void _setUpRedirectValue() {
		Mockito.doReturn(
			"redirect"
		).when(
			_actionRequest
		).getAttribute(
			WebKeys.REDIRECT
		);

		_portalUtilMockedStatic.when(
			() -> PortalUtil.escapeRedirect("redirect")
		).thenReturn(
			"redirect"
		);
	}

	private void _setUpActionRequest(String cmd) {
		_setUpActionRequest(RandomTestUtil.randomString(), cmd);
	}

	private void _setUpActionRequest(String className, String cdm) {
		Mockito.doReturn(
			cdm
		).when(
			_actionRequest
		).getParameter(
			Constants.CMD
		);

		Mockito.doReturn(
			className
		).when(
			_actionRequest
		).getParameter(
			"className"
		);
	}

	private void _setUpBackgroudTaskManager() {
		_backgroundTaskManager = Mockito.mock(BackgroundTaskManager.class);

		ReflectionTestUtil.setFieldValue(
			_editMVCActionCommand, "_backgroundTaskManager",
			_backgroundTaskManager);
	}

	private void _setUpHttpServletRequest(String[] companyIds) {
		Mockito.doReturn(
			companyIds
		).when(
			_httpServletRequest
		).getParameterValues(
			"companyIds"
		);

		_portalUtilMockedStatic.when(
			() -> PortalUtil.getHttpServletRequest(_actionRequest)
		).thenReturn(
			_httpServletRequest
		);
	}

	private void _setUpIndexerWriterHelper() {
		_indexWriterHelper = Mockito.mock(IndexWriterHelper.class);

		ReflectionTestUtil.setFieldValue(
			_editMVCActionCommand, "_indexWriterHelper", _indexWriterHelper);
	}

	private void _setUpPermissionCheckerCompanyAdmin(
		boolean companyAdmin, long companyId) {

		Mockito.doReturn(
			companyAdmin
		).when(
			_permissionChecker
		).isCompanyAdmin(
			companyId
		);
	}

	private void _setUpPermissionCheckerOmniadmin(boolean omniadmin) {
		Mockito.doReturn(
			omniadmin
		).when(
			_permissionChecker
		).isOmniadmin();

		if (omniadmin) {
			_verifyPermissionCheckerIsCompanyAdmin(0);
		}
	}

	private void _setUpThemeDisplay() {
		Mockito.doReturn(
			_themeDisplay
		).when(
			_actionRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);

		_permissionChecker = Mockito.mock(PermissionChecker.class);

		Mockito.doReturn(
			_permissionChecker
		).when(
			_themeDisplay
		).getPermissionChecker();
	}

	private void _verifyIndexWriterHelperReindex(int wantedNumberOfInvocations)
		throws Exception {

		Mockito.verify(
			_indexWriterHelper, Mockito.times(wantedNumberOfInvocations)
		).reindex(
			Mockito.anyLong(), Mockito.anyString(), Mockito.any(),
			Mockito.anyString(), Mockito.any()
		);
	}

	private void _verifyPermissionCheckerIsCompanyAdmin(
		int wantedNumberOfInvocations) {

		Mockito.verify(
			_permissionChecker, Mockito.times(wantedNumberOfInvocations)
		).isCompanyAdmin(
			Mockito.anyLong()
		);
	}

	private void _verifyReindexIndexReindexer(int wantedNumberOfInvocations)
		throws Exception {

		Mockito.verify(
			_backgroundTaskManager, Mockito.times(wantedNumberOfInvocations)
		).addBackgroundTask(
			Mockito.anyLong(), Mockito.anyLong(),
			ArgumentMatchers.eq("reindexIndexReindexer"), Mockito.anyString(),
			Mockito.any(), Mockito.any()
		);
	}

	private void _verifyRenderParameterError() {
		Mockito.verify(
			_actionResponse, Mockito.times(1)
		).setRenderParameter(
			"mvcPath", "/error.jsp"
		);
	}

	private void _verifySendRedirect(int wantedNumberOfInvocations)
		throws Exception {

		Mockito.verify(
			_actionResponse, Mockito.times(wantedNumberOfInvocations)
		).sendRedirect(
			Mockito.anyString()
		);
	}

	private static final ActionRequest _actionRequest = Mockito.mock(
		ActionRequest.class);
	private static final EditMVCActionCommand _editMVCActionCommand =
		new EditMVCActionCommand();
	private static MockedStatic<PortalUtil> _portalUtilMockedStatic;
	private static MockedStatic<SessionErrors> _sessionErrorsMockedStatic;

	private ActionResponse _actionResponse;
	private BackgroundTaskManager _backgroundTaskManager;
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private IndexWriterHelper _indexWriterHelper;
	private PermissionChecker _permissionChecker;
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}