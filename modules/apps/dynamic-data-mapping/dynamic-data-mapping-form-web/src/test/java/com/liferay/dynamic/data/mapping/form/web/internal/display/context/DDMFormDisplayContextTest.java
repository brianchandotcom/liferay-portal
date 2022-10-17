/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.mapping.form.web.internal.display.context;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceImpl;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterTracker;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockRenderRequest;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockRenderResponse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Adam Brandizzi
 */
public class DDMFormDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.setProps(new PropsImpl());
	}

	@Before
	public void setUp() {
		_setUpJSONFactoryUtil();
		_setUpLanguageUtil();
		_setUpPortalUtil();
		_setUpResourceBundleUtil();
	}

	@Test
	public void testAutosaveWithDefaultUser() throws Exception {
		MockRenderRequest mockRenderRequest = _mockRenderRequest();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		mockRenderRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		PortletDisplay portletDisplay = Mockito.mock(PortletDisplay.class);

		Mockito.when(
			portletDisplay.getPortletResource()
		).thenReturn(
			null
		);

		Mockito.when(
			themeDisplay.getPortletDisplay()
		).thenReturn(
			portletDisplay
		);

		User user = Mockito.mock(User.class);

		Mockito.when(
			user.isDefaultUser()
		).thenReturn(
			Boolean.TRUE
		);

		Mockito.when(
			themeDisplay.getUser()
		).thenReturn(
			user
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(mockRenderRequest);

		Assert.assertFalse(ddmFormDisplayContext.isAutosaveEnabled());
	}

	@Test
	public void testAutosaveWithNondefaultUser1() throws Exception {
		RenderRequest renderRequest = _mockRenderRequestWithDefaultUser(false);

		DDMFormInstanceSettings ddmFormInstanceSettings =
			_mockDDMFormInstanceSettingsAutosaveWithNondefaultUser();

		Mockito.when(
			ddmFormInstanceSettings.autosaveEnabled()
		).thenReturn(
			Boolean.FALSE
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(renderRequest);

		Assert.assertFalse(ddmFormDisplayContext.isAutosaveEnabled());
	}

	@Test
	public void testAutosaveWithNondefaultUser2() throws Exception {
		RenderRequest renderRequest = _mockRenderRequestWithDefaultUser(false);

		DDMFormInstanceSettings ddmFormInstanceSettings =
			_mockDDMFormInstanceSettingsAutosaveWithNondefaultUser();

		Mockito.when(
			ddmFormInstanceSettings.autosaveEnabled()
		).thenReturn(
			Boolean.TRUE
		);

		Mockito.when(
			_ddmFormWebConfiguration.autosaveInterval()
		).thenReturn(
			1
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(renderRequest);

		Assert.assertTrue(ddmFormDisplayContext.isAutosaveEnabled());
	}

	@Test
	public void testAutosaveWithNondefaultUser3() throws Exception {
		RenderRequest renderRequest = _mockRenderRequestWithDefaultUser(false);

		DDMFormInstanceSettings ddmFormInstanceSettings =
			_mockDDMFormInstanceSettingsAutosaveWithNondefaultUser();

		Mockito.when(
			ddmFormInstanceSettings.autosaveEnabled()
		).thenReturn(
			Boolean.TRUE
		);

		Mockito.when(
			_ddmFormWebConfiguration.autosaveInterval()
		).thenReturn(
			0
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(renderRequest);

		Assert.assertFalse(ddmFormDisplayContext.isAutosaveEnabled());
	}

	@Test
	public void testCreateDDMFormRenderingContext() throws Exception {
		DDMFormInstanceSettings ddmFormInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		_mockDDMFormInstance(ddmFormInstanceSettings);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext();

		DDMFormRenderingContext ddmFormRenderingContext =
			ddmFormDisplayContext.createDDMFormRenderingContext(new DDMForm());

		Assert.assertFalse(
			ddmFormRenderingContext.getProperty(
				"showPartialResultsToRespondents"));

		Mockito.when(
			ddmFormInstanceSettings.showPartialResultsToRespondents()
		).thenReturn(
			true
		);

		ddmFormRenderingContext =
			ddmFormDisplayContext.createDDMFormRenderingContext(new DDMForm());

		Assert.assertTrue(
			ddmFormRenderingContext.getProperty(
				"showPartialResultsToRespondents"));
	}

	@Test
	public void testDDMFormRenderingContextLocaleIsThemeDisplayLocale()
		throws Exception {

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext();

		Locale defaultLocale = LocaleUtil.BRAZIL;

		Set<Locale> availableLocales = new HashSet<>();

		availableLocales.add(defaultLocale);
		availableLocales.add(LocaleUtil.SPAIN);

		DDMForm ddmForm = _createDDMForm(availableLocales, defaultLocale);

		_mockHttpServletRequest2.addParameter(
			"languageId", LocaleUtil.toLanguageId(LocaleUtil.SPAIN));

		DDMFormRenderingContext ddmFormRenderingContext =
			ddmFormDisplayContext.createDDMFormRenderingContext(ddmForm);

		Assert.assertEquals(
			LocaleUtil.SPAIN, ddmFormRenderingContext.getLocale());
	}

	@Test
	public void testGetCustomizedSubmitLabel() throws Exception {
		DDMFormInstanceSettings ddmFormInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		_mockDDMFormInstance(ddmFormInstanceSettings);

		String submitLabel = "Enviar Personalizado";

		Mockito.when(
			ddmFormInstanceSettings.submitLabel()
		).thenReturn(
			JSONUtil.put(
				_DEFAULT_LANGUAGE_ID, submitLabel
			).toString()
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(_mockRenderRequest());

		Assert.assertEquals(
			submitLabel, ddmFormDisplayContext.getSubmitLabel());
	}

	@Test
	public void testGetFormInstanceWithMissingSettings() throws Exception {
		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext();

		DDMFormInstance ddmFormInstance = Mockito.spy(
			new DDMFormInstanceImpl());

		String expectedSettings = StringUtil.randomString();

		ddmFormInstance.setSettings(expectedSettings);

		Mockito.when(
			_ddmFormInstanceService.fetchFormInstance(Mockito.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		Mockito.when(
			_ddmFormInstanceVersion.getSettings()
		).thenReturn(
			StringPool.BLANK
		);

		ddmFormInstance = ddmFormDisplayContext.getFormInstance();

		Assert.assertThat(
			ddmFormInstance.getSettings(), CoreMatchers.is(expectedSettings));
	}

	@Test
	public void testGetLocale() throws PortalException {
		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext();

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameter(Mockito.eq("defaultLanguageId"))
		).thenReturn(
			"pt_BR"
		);

		Locale defaultLocale = LocaleUtil.US;
		Locale expectedLocale = LocaleUtil.BRAZIL;

		DDMForm ddmForm = _createDDMForm(
			new HashSet<>(Arrays.asList(defaultLocale, expectedLocale)),
			defaultLocale);

		Assert.assertEquals(
			expectedLocale,
			ddmFormDisplayContext.getLocale(httpServletRequest, ddmForm));
	}

	@Test
	public void testGetSubmitLabel() throws Exception {
		_mockDDMFormInstance(Mockito.mock(DDMFormInstanceSettings.class));

		String submitLabel = "Submit";

		_mockLanguageGet("submit-form", submitLabel);

		_mockWorkflowDefinitionLinkLocalService(false);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(_mockRenderRequest());

		Assert.assertEquals(
			submitLabel, ddmFormDisplayContext.getSubmitLabel());
	}

	@Test
	public void testGetSubmitLabelWithWorkflow() throws Exception {
		_mockDDMFormInstance(Mockito.mock(DDMFormInstanceSettings.class));

		String submitLabel = "Submit For Publication";

		_mockLanguageGet("submit-for-publication", submitLabel);

		_mockWorkflowDefinitionLinkLocalService(true);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(_mockRenderRequest());

		Assert.assertEquals(
			submitLabel, ddmFormDisplayContext.getSubmitLabel());
	}

	@Test
	public void testIsFormAvailableForGuest() throws Exception {
		DDMFormInstance ddmFormInstance = _mockDDMFormInstance();

		Mockito.when(
			_ddmFormInstanceLocalService.fetchFormInstance(Mockito.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		Mockito.when(
			_ddmFormInstanceService.fetchFormInstance(Mockito.anyLong())
		).thenReturn(
			null
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext();

		Assert.assertFalse(ddmFormDisplayContext.isFormAvailable());
	}

	@Test
	public void testIsFormAvailableForLoggedUser() throws Exception {
		DDMFormInstanceSettings ddmFormInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		Mockito.when(
			ddmFormInstanceSettings.published()
		).thenReturn(
			true
		);

		DDMFormInstance ddmFormInstance = _mockDDMFormInstance(
			ddmFormInstanceSettings);

		Mockito.when(
			_ddmFormInstanceLocalService.fetchFormInstance(Mockito.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		Mockito.when(
			_ddmFormInstanceService.fetchFormInstance(Mockito.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext();

		Assert.assertTrue(ddmFormDisplayContext.isFormAvailable());
	}

	@Test
	public void testIsSharedFormWithoutPortletSession() throws Exception {
		MockRenderRequest mockRenderRequest = _mockRenderRequest();

		Assert.assertNull(mockRenderRequest.getPortletSession(false));

		mockRenderRequest.setParameter("shared", Boolean.TRUE.toString());

		DDMFormDisplayContext createDDMFormDisplayContext =
			_createDDMFormDisplayContext(mockRenderRequest);

		Assert.assertTrue(createDDMFormDisplayContext.isFormShared());
	}

	@Test
	public void testIsSharedFormWithPortletSession() throws Exception {
		MockRenderRequest mockRenderRequest = _mockRenderRequest();

		PortletSession portletSession = mockRenderRequest.getPortletSession(
			true);

		Assert.assertNotNull(portletSession);

		portletSession.setAttribute("shared", Boolean.TRUE);

		DDMFormDisplayContext createDDMFormDisplayContext =
			_createDDMFormDisplayContext(mockRenderRequest);

		Assert.assertTrue(createDDMFormDisplayContext.isFormShared());
	}

	@Test
	public void testIsSharedURL() throws Exception {
		DDMFormDisplayContext ddmFormDisplayContext = Mockito.spy(
			_createDDMFormDisplayContext());

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getURLCurrent()
		).thenReturn(
			"http://localhost:8080/web/forms/shared?form=123"
		);

		Mockito.doReturn(
			themeDisplay
		).when(
			ddmFormDisplayContext
		).getThemeDisplay();

		Assert.assertTrue(ddmFormDisplayContext.isSharedURL());
	}

	@Test
	public void testIsShowIconInEditMode() throws Exception {
		_mockHttpServletRequest1.addParameter("p_l_mode", Constants.EDIT);

		DDMFormDisplayContext ddmFormDisplayContext = _createSpy(
			false, false, false);

		Assert.assertFalse(ddmFormDisplayContext.isShowConfigurationIcon());
	}

	@Test
	public void testIsShowIconInPreview() throws Exception {
		DDMFormDisplayContext ddmFormDisplayContext = _createSpy(
			false, true, false);

		Assert.assertFalse(ddmFormDisplayContext.isShowConfigurationIcon());
	}

	@Test
	public void testIsShowIconWithPermission() throws Exception {
		_mockPortletPermissionUtil();

		DDMFormDisplayContext ddmFormDisplayContext = _createSpy(
			false, false, true);

		Assert.assertTrue(ddmFormDisplayContext.isShowConfigurationIcon());
	}

	@Test
	public void testIsShowIconWithSharedForm() throws Exception {
		DDMFormDisplayContext ddmFormDisplayContext = _createSpy(
			true, false, true);

		Assert.assertFalse(ddmFormDisplayContext.isShowConfigurationIcon());
	}

	@Test
	public void testIsShowPartialResultsToRespondents() throws Exception {
		DDMFormInstanceSettings ddmFormInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		_mockDDMFormInstance(ddmFormInstanceSettings);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext();

		Assert.assertFalse(
			ddmFormDisplayContext.isShowPartialResultsToRespondents());

		Mockito.when(
			ddmFormInstanceSettings.showPartialResultsToRespondents()
		).thenReturn(
			true
		);

		Assert.assertTrue(
			ddmFormDisplayContext.isShowPartialResultsToRespondents());
	}

	@Test
	public void testIsShowSuccessPage() throws Exception {
		_mockDDMFormInstance(Mockito.mock(DDMFormInstanceSettings.class));

		MockRenderRequest mockRenderRequest = _mockRenderRequest();

		SessionMessages.add(mockRenderRequest, "formInstanceRecordAdded");

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(mockRenderRequest);

		Assert.assertTrue(ddmFormDisplayContext.isShowSuccessPage());
	}

	@Test
	public void testIsShowSuccessPageWithRedirectURL() throws Exception {
		DDMFormInstanceSettings ddmFormInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		Mockito.when(
			ddmFormInstanceSettings.redirectURL()
		).thenReturn(
			"http://localhost:8080/web/forms/shared/-/form/123"
		);

		_mockDDMFormInstance(ddmFormInstanceSettings);

		MockRenderRequest mockRenderRequest = _mockRenderRequest();

		SessionMessages.add(mockRenderRequest, "formInstanceRecordAdded");

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(mockRenderRequest);

		Assert.assertFalse(ddmFormDisplayContext.isShowSuccessPage());
	}

	@Test
	public void testSubmissionLimitReachedCustomMessage() throws Exception {
		String limitToOneSubmissionPerUserBody =
			"You cannot submit more than once.";
		String limitToOneSubmissionPerUserHeader = "Limit reached";

		DDMFormInstanceSettings ddmFormInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		_mockDDMFormInstance(ddmFormInstanceSettings);

		Mockito.when(
			ddmFormInstanceSettings.limitToOneSubmissionPerUserBody()
		).thenReturn(
			JSONUtil.put(
				_DEFAULT_LANGUAGE_ID, limitToOneSubmissionPerUserBody
			).toString()
		);

		Mockito.when(
			ddmFormInstanceSettings.limitToOneSubmissionPerUserHeader()
		).thenReturn(
			JSONUtil.put(
				_DEFAULT_LANGUAGE_ID, limitToOneSubmissionPerUserHeader
			).toString()
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(_mockRenderRequest());

		Assert.assertEquals(
			limitToOneSubmissionPerUserBody,
			ddmFormDisplayContext.getLimitToOneSubmissionPerUserBody());
		Assert.assertEquals(
			limitToOneSubmissionPerUserHeader,
			ddmFormDisplayContext.getLimitToOneSubmissionPerUserHeader());
	}

	@Test
	public void testSubmissionLimitReachedDefaultMessage() throws Exception {
		String limitToOneSubmissionPerUserBody =
			"You can fill out this form only once. Contact the owner of the " +
				"form if you think this is a mistake.";
		String limitToOneSubmissionPerUserHeader =
			"You have already responded.";

		Mockito.when(
			_language.get(
				Mockito.any(HttpServletRequest.class),
				Mockito.eq(
					"you-can-fill-out-this-form-only-once.-contact-the-owner-" +
						"of-the-form-if-you-think-this-is-a-mistake"))
		).thenReturn(
			limitToOneSubmissionPerUserBody
		);

		Mockito.when(
			_language.get(
				Mockito.any(HttpServletRequest.class),
				Mockito.eq("you-have-already-responded"))
		).thenReturn(
			limitToOneSubmissionPerUserHeader
		);

		DDMFormInstanceSettings ddmFormInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		_mockDDMFormInstance(ddmFormInstanceSettings);

		Mockito.when(
			ddmFormInstanceSettings.limitToOneSubmissionPerUserBody()
		).thenReturn(
			JSONUtil.put(
				_DEFAULT_LANGUAGE_ID, StringPool.BLANK
			).toString()
		);

		Mockito.when(
			ddmFormInstanceSettings.limitToOneSubmissionPerUserHeader()
		).thenReturn(
			JSONUtil.put(
				_DEFAULT_LANGUAGE_ID, StringPool.BLANK
			).toString()
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(_mockRenderRequest());

		Assert.assertEquals(
			limitToOneSubmissionPerUserBody,
			ddmFormDisplayContext.getLimitToOneSubmissionPerUserBody());
		Assert.assertEquals(
			limitToOneSubmissionPerUserHeader,
			ddmFormDisplayContext.getLimitToOneSubmissionPerUserHeader());
	}

	private DDMForm _createDDMForm(
		Set<Locale> availableLocales, Locale locale) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(availableLocales);

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			new DDMFormSuccessPageSettings();

		ddmFormSuccessPageSettings.setEnabled(true);

		ddmForm.setDDMFormSuccessPageSettings(ddmFormSuccessPageSettings);

		ddmForm.setDefaultLocale(locale);

		return ddmForm;
	}

	private DDMFormDisplayContext _createDDMFormDisplayContext()
		throws PortalException {

		return _createDDMFormDisplayContext(_mockRenderRequest());
	}

	private DDMFormDisplayContext _createDDMFormDisplayContext(
			RenderRequest renderRequest)
		throws PortalException {

		return new DDMFormDisplayContext(
			Mockito.mock(DDMFormFieldTypeServicesTracker.class),
			_ddmFormInstanceLocalService,
			Mockito.mock(DDMFormInstanceRecordService.class),
			Mockito.mock(DDMFormInstanceRecordVersionLocalService.class),
			_ddmFormInstanceService, _mockDDMFormInstanceVersionLocalService(),
			Mockito.mock(DDMFormRenderer.class),
			Mockito.mock(DDMFormValuesFactory.class),
			Mockito.mock(DDMFormValuesMerger.class), _ddmFormWebConfiguration,
			Mockito.mock(DDMStorageAdapterTracker.class),
			Mockito.mock(GroupLocalService.class), new JSONFactoryImpl(), null,
			null, null, null, Mockito.mock(Portal.class), renderRequest,
			new MockRenderResponse(), Mockito.mock(RoleLocalService.class),
			Mockito.mock(UserLocalService.class),
			_workflowDefinitionLinkLocalService);
	}

	private DDMFormDisplayContext _createSpy(
			boolean formShared, boolean preview, boolean sharedURL)
		throws Exception {

		DDMFormDisplayContext ddmFormDisplayContext = Mockito.spy(
			_createDDMFormDisplayContext());

		Mockito.doReturn(
			formShared
		).when(
			ddmFormDisplayContext
		).isFormShared();

		Mockito.doReturn(
			preview
		).when(
			ddmFormDisplayContext
		).isPreview();

		Mockito.doReturn(
			sharedURL
		).when(
			ddmFormDisplayContext
		).isSharedURL();

		return ddmFormDisplayContext;
	}

	private DDMFormInstance _mockDDMFormInstance() throws Exception {
		DDMFormInstance formInstance = Mockito.mock(DDMFormInstance.class);

		DDMFormInstanceSettings formInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		Mockito.when(
			formInstance.getSettingsModel()
		).thenReturn(
			formInstanceSettings
		);

		return formInstance;
	}

	private DDMFormInstance _mockDDMFormInstance(
			DDMFormInstanceSettings ddmFormInstanceSettings)
		throws Exception {

		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		Mockito.when(
			ddmFormInstance.getSettingsModel()
		).thenReturn(
			ddmFormInstanceSettings
		);

		DDMStructure ddmStructure = _mockDDMStructure();

		Mockito.when(
			ddmFormInstance.getStructure()
		).thenReturn(
			ddmStructure
		);

		Mockito.when(
			_ddmFormInstanceService.fetchFormInstance(Mockito.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		return ddmFormInstance;
	}

	private DDMFormInstanceSettings
			_mockDDMFormInstanceSettingsAutosaveWithNondefaultUser()
		throws Exception {

		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		DDMFormInstanceSettings ddmFormInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		Mockito.when(
			ddmFormInstance.getSettingsModel()
		).thenReturn(
			ddmFormInstanceSettings
		);

		Mockito.when(
			_ddmFormInstanceService.fetchFormInstance(Mockito.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		return ddmFormInstanceSettings;
	}

	private DDMFormInstanceVersionLocalService
			_mockDDMFormInstanceVersionLocalService()
		throws PortalException {

		Mockito.when(
			_ddmFormInstanceVersionLocalService.getLatestFormInstanceVersion(
				Mockito.anyLong(), Mockito.anyInt())
		).thenReturn(
			_ddmFormInstanceVersion
		);

		return _ddmFormInstanceVersionLocalService;
	}

	private DDMStructure _mockDDMStructure() throws Exception {
		DDMStructure ddmStructure = Mockito.mock(DDMStructure.class);

		Locale defaultLocale = LocaleUtil.fromLanguageId(_DEFAULT_LANGUAGE_ID);

		DDMForm ddmForm = _createDDMForm(
			new HashSet<>(Arrays.asList(defaultLocale)), defaultLocale);

		Mockito.when(
			ddmStructure.getDDMForm()
		).thenReturn(
			ddmForm
		);

		return ddmStructure;
	}

	private void _mockLanguageGet(String key, String value) {
		Mockito.when(
			_language.get(Mockito.any(ResourceBundle.class), Mockito.eq(key))
		).thenReturn(
			value
		);
	}

	private void _mockPortletPermissionUtil() throws Exception {
		PortletPermissionUtil portletPermissionUtil =
			new PortletPermissionUtil();

		PortletPermission portletPermission = Mockito.mock(
			PortletPermission.class);

		Mockito.when(
			portletPermission.contains(
				Mockito.nullable(PermissionChecker.class),
				Mockito.nullable(Layout.class), Mockito.anyString(),
				Mockito.anyString())
		).thenReturn(
			true
		);

		portletPermissionUtil.setPortletPermission(portletPermission);
	}

	private MockRenderRequest _mockRenderRequest() throws PortalException {
		MockRenderRequest mockRenderRequest = new MockRenderRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(Mockito.mock(Company.class));
		themeDisplay.setLayout(Mockito.mock(Layout.class));
		themeDisplay.setLocale(LocaleUtil.SPAIN);

		mockRenderRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		mockRenderRequest.setParameter("languageId", _DEFAULT_LANGUAGE_ID);

		return mockRenderRequest;
	}

	private RenderRequest _mockRenderRequestWithDefaultUser(boolean defaultUser)
		throws Exception {

		MockRenderRequest mockRenderRequest = _mockRenderRequest();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		mockRenderRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		User user = Mockito.mock(User.class);

		Mockito.when(
			user.isDefaultUser()
		).thenReturn(
			defaultUser
		);

		Mockito.when(
			themeDisplay.getUser()
		).thenReturn(
			user
		);

		PortletDisplay portletDisplay = Mockito.mock(PortletDisplay.class);

		Mockito.when(
			portletDisplay.getPortletResource()
		).thenReturn(
			null
		);

		Mockito.when(
			themeDisplay.getPortletDisplay()
		).thenReturn(
			portletDisplay
		);

		return mockRenderRequest;
	}

	private void _mockWorkflowDefinitionLinkLocalService(
		boolean hasWorkflowDefinitionLink) {

		Mockito.when(
			_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong())
		).thenReturn(
			hasWorkflowDefinitionLink
		);
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Mockito.when(
			_language.getLanguageId(Mockito.any(Locale.class))
		).thenReturn(
			_DEFAULT_LANGUAGE_ID
		);

		Mockito.when(
			_language.getLanguageId(Mockito.eq(_mockHttpServletRequest2))
		).thenReturn(
			_DEFAULT_LANGUAGE_ID
		);

		_whenLanguageIsAvailableLocale(LocaleUtil.BRAZIL);
		_whenLanguageIsAvailableLocale(LocaleUtil.SPAIN);

		languageUtil.setLanguage(_language);
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		portalUtil.setPortal(portal);

		Mockito.when(
			portal.getHttpServletRequest(Mockito.any(RenderRequest.class))
		).thenReturn(
			_mockHttpServletRequest2
		);

		Mockito.when(
			portal.getLiferayPortletRequest(Mockito.any(RenderRequest.class))
		).thenReturn(
			Mockito.mock(LiferayPortletRequest.class)
		);

		Mockito.when(
			portal.getOriginalServletRequest(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			_mockHttpServletRequest1
		);
	}

	private void _setUpResourceBundleUtil() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Mockito.any(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private void _whenLanguageIsAvailableLocale(Locale locale) {
		Mockito.when(
			_language.isAvailableLocale(Mockito.eq(locale))
		).thenReturn(
			true
		);

		Mockito.when(
			_language.isAvailableLocale(
				Mockito.eq(LocaleUtil.toLanguageId(locale)))
		).thenReturn(
			true
		);
	}

	private static final String _DEFAULT_LANGUAGE_ID = "es_ES";

	private final DDMFormInstanceLocalService _ddmFormInstanceLocalService =
		Mockito.mock(DDMFormInstanceLocalService.class);
	private final DDMFormInstanceService _ddmFormInstanceService = Mockito.mock(
		DDMFormInstanceService.class);
	private final DDMFormInstanceVersion _ddmFormInstanceVersion = Mockito.mock(
		DDMFormInstanceVersion.class);
	private final DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService = Mockito.mock(
			DDMFormInstanceVersionLocalService.class);
	private final DDMFormWebConfiguration _ddmFormWebConfiguration =
		Mockito.mock(DDMFormWebConfiguration.class);
	private final Language _language = Mockito.mock(Language.class);
	private final MockHttpServletRequest _mockHttpServletRequest1 =
		new MockHttpServletRequest();
	private final MockHttpServletRequest _mockHttpServletRequest2 =
		Mockito.mock(MockHttpServletRequest.class);
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService = Mockito.mock(
			WorkflowDefinitionLinkLocalService.class);

}