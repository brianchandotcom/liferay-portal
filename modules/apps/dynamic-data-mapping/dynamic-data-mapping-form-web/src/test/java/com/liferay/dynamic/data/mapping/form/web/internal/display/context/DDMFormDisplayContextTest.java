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
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
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
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Adam Brandizzi
 */
@PrepareForTest(
	{LocaleUtil.class, PortletPermissionUtil.class, ResourceBundleUtil.class}
)
@RunWith(PowerMockRunner.class)
public class DDMFormDisplayContextTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.setProps(new PropsImpl());
	}

	@Before
	public void setUp() throws PortalException {
		_setUpJSONFactoryUtil();
		_setUpLanguageUtil();
		_setUpLocaleUtil();
		_setUpPortalUtil();
		_setUpResourceBundleUtil();
	}

	@Test
	public void testAutosaveWithDefaultUser() throws Exception {
		MockRenderRequest mockRenderRequest = _mockRenderRequest();

		ThemeDisplay themeDisplay = mock(ThemeDisplay.class);

		mockRenderRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		PortletDisplay portletDisplay = mock(PortletDisplay.class);

		when(
			portletDisplay.getPortletResource()
		).thenReturn(
			null
		);

		when(
			themeDisplay.getPortletDisplay()
		).thenReturn(
			portletDisplay
		);

		User user = mock(User.class);

		when(
			user.isDefaultUser()
		).thenReturn(
			Boolean.TRUE
		);

		when(
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

		when(
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

		when(
			ddmFormInstanceSettings.autosaveEnabled()
		).thenReturn(
			Boolean.TRUE
		);

		when(
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

		when(
			ddmFormInstanceSettings.autosaveEnabled()
		).thenReturn(
			Boolean.TRUE
		);

		when(
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
		DDMFormInstanceSettings ddmFormInstanceSettings = mock(
			DDMFormInstanceSettings.class);

		_mockDDMFormInstance(ddmFormInstanceSettings);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext();

		DDMFormRenderingContext ddmFormRenderingContext =
			ddmFormDisplayContext.createDDMFormRenderingContext(new DDMForm());

		Assert.assertFalse(
			(boolean)ddmFormRenderingContext.getProperty(
				"showPartialResultsToRespondents"));

		when(
			ddmFormInstanceSettings.showPartialResultsToRespondents()
		).thenReturn(
			true
		);

		ddmFormRenderingContext =
			ddmFormDisplayContext.createDDMFormRenderingContext(new DDMForm());

		Assert.assertTrue(
			(boolean)ddmFormRenderingContext.getProperty(
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

		_request.addParameter(
			"languageId", LocaleUtil.toLanguageId(LocaleUtil.SPAIN));

		DDMFormRenderingContext ddmFormRenderingContext =
			ddmFormDisplayContext.createDDMFormRenderingContext(ddmForm);

		Assert.assertEquals(
			LocaleUtil.SPAIN, ddmFormRenderingContext.getLocale());
	}

	@Test
	public void testGetCustomizedSubmitLabel() throws Exception {
		DDMFormInstanceSettings ddmFormInstanceSettings = mock(
			DDMFormInstanceSettings.class);

		_mockDDMFormInstance(ddmFormInstanceSettings);

		String submitLabel = "Enviar Personalizado";

		when(
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

		DDMFormInstance ddmFormInstance = spy(new DDMFormInstanceImpl());

		String expectedSettings = StringUtil.randomString();

		ddmFormInstance.setSettings(expectedSettings);

		when(
			_ddmFormInstanceService.fetchFormInstance(Matchers.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		when(
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

		when(
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
		_mockDDMFormInstance(mock(DDMFormInstanceSettings.class));

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
		_mockDDMFormInstance(mock(DDMFormInstanceSettings.class));

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

		when(
			_ddmFormInstanceLocalService.fetchFormInstance(Matchers.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		when(
			_ddmFormInstanceService.fetchFormInstance(Matchers.anyLong())
		).thenReturn(
			null
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext();

		Assert.assertFalse(ddmFormDisplayContext.isFormAvailable());
	}

	@Test
	public void testIsFormAvailableForLoggedUser() throws Exception {
		DDMFormInstance ddmFormInstance = _mockDDMFormInstance();

		when(
			_ddmFormInstanceLocalService.fetchFormInstance(Matchers.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		when(
			_ddmFormInstanceService.fetchFormInstance(Matchers.anyLong())
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
		DDMFormDisplayContext ddmFormDisplayContext = spy(
			_createDDMFormDisplayContext());

		ThemeDisplay themeDisplay = mock(ThemeDisplay.class);

		when(
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
		_mockHttpServletRequest.addParameter("p_l_mode", Constants.EDIT);

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
		DDMFormInstanceSettings ddmFormInstanceSettings = mock(
			DDMFormInstanceSettings.class);

		_mockDDMFormInstance(ddmFormInstanceSettings);

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext();

		Assert.assertFalse(
			ddmFormDisplayContext.isShowPartialResultsToRespondents());

		when(
			ddmFormInstanceSettings.showPartialResultsToRespondents()
		).thenReturn(
			true
		);

		Assert.assertTrue(
			ddmFormDisplayContext.isShowPartialResultsToRespondents());
	}

	@Test
	public void testIsShowSuccessPage() throws Exception {
		_mockDDMFormInstance(mock(DDMFormInstanceSettings.class));

		MockRenderRequest mockRenderRequest = _mockRenderRequest();

		SessionMessages.add(mockRenderRequest, "formInstanceRecordAdded");

		DDMFormDisplayContext ddmFormDisplayContext =
			_createDDMFormDisplayContext(mockRenderRequest);

		Assert.assertTrue(ddmFormDisplayContext.isShowSuccessPage());
	}

	@Test
	public void testIsShowSuccessPageWithRedirectURL() throws Exception {
		DDMFormInstanceSettings ddmFormInstanceSettings = mock(
			DDMFormInstanceSettings.class);

		when(
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
			mock(DDMFormFieldTypeServicesTracker.class),
			_ddmFormInstanceLocalService,
			mock(DDMFormInstanceRecordService.class),
			mock(DDMFormInstanceRecordVersionLocalService.class),
			_ddmFormInstanceService, _mockDDMFormInstanceVersionLocalService(),
			mock(DDMFormRenderer.class), mock(DDMFormValuesFactory.class),
			mock(DDMFormValuesMerger.class), _ddmFormWebConfiguration,
			mock(DDMStorageAdapterTracker.class), mock(GroupLocalService.class),
			new JSONFactoryImpl(), null, null, mock(Portal.class),
			renderRequest, new MockRenderResponse(),
			mock(RoleLocalService.class), mock(UserLocalService.class),
			_workflowDefinitionLinkLocalService);
	}

	private DDMFormDisplayContext _createSpy(
			boolean formShared, boolean preview, boolean sharedURL)
		throws Exception {

		DDMFormDisplayContext ddmFormDisplayContext = spy(
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
		DDMFormInstance formInstance = mock(DDMFormInstance.class);

		DDMFormInstanceSettings formInstanceSettings = mock(
			DDMFormInstanceSettings.class);

		when(
			formInstance.getSettingsModel()
		).thenReturn(
			formInstanceSettings
		);

		return formInstance;
	}

	private void _mockDDMFormInstance(
			DDMFormInstanceSettings ddmFormInstanceSettings)
		throws Exception {

		DDMFormInstance ddmFormInstance = mock(DDMFormInstance.class);

		when(
			ddmFormInstance.getSettingsModel()
		).thenReturn(
			ddmFormInstanceSettings
		);

		DDMStructure ddmStructure = _mockDDMStructure();

		when(
			ddmFormInstance.getStructure()
		).thenReturn(
			ddmStructure
		);

		when(
			_ddmFormInstanceService.fetchFormInstance(Matchers.anyLong())
		).thenReturn(
			ddmFormInstance
		);
	}

	private DDMFormInstanceSettings
			_mockDDMFormInstanceSettingsAutosaveWithNondefaultUser()
		throws Exception {

		DDMFormInstance ddmFormInstance = mock(DDMFormInstance.class);

		DDMFormInstanceSettings ddmFormInstanceSettings = mock(
			DDMFormInstanceSettings.class);

		when(
			ddmFormInstance.getSettingsModel()
		).thenReturn(
			ddmFormInstanceSettings
		);

		when(
			_ddmFormInstanceService.fetchFormInstance(Matchers.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		return ddmFormInstanceSettings;
	}

	private DDMFormInstanceVersionLocalService
			_mockDDMFormInstanceVersionLocalService()
		throws PortalException {

		when(
			_ddmFormInstanceVersionLocalService.getLatestFormInstanceVersion(
				Matchers.anyLong(), Matchers.anyInt())
		).thenReturn(
			_ddmFormInstanceVersion
		);

		return _ddmFormInstanceVersionLocalService;
	}

	private DDMStructure _mockDDMStructure() throws Exception {
		DDMStructure ddmStructure = mock(DDMStructure.class);

		Locale defaultLocale = LocaleUtil.fromLanguageId(_DEFAULT_LANGUAGE_ID);

		DDMForm ddmForm = _createDDMForm(
			new HashSet<>(Arrays.asList(defaultLocale)), defaultLocale);

		when(
			ddmStructure.getDDMForm()
		).thenReturn(
			ddmForm
		);

		return ddmStructure;
	}

	private void _mockLanguageGet(String key, String value) {
		when(
			_language.get(Matchers.any(ResourceBundle.class), Matchers.eq(key))
		).thenReturn(
			value
		);
	}

	private void _mockPortletPermissionUtil() throws Exception {
		mockStatic(PortletPermissionUtil.class);

		when(
			PortletPermissionUtil.contains(
				Matchers.any(PermissionChecker.class),
				Matchers.any(Layout.class), Matchers.anyString(),
				Matchers.anyString())
		).thenReturn(
			true
		);
	}

	private MockRenderRequest _mockRenderRequest() throws PortalException {
		MockRenderRequest mockRenderRequest = new MockRenderRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(mock(Company.class));
		themeDisplay.setLayout(mock(Layout.class));
		themeDisplay.setLocale(LocaleUtil.SPAIN);

		mockRenderRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		mockRenderRequest.setParameter("languageId", _DEFAULT_LANGUAGE_ID);

		return mockRenderRequest;
	}

	private RenderRequest _mockRenderRequestWithDefaultUser(boolean defaultUser)
		throws Exception {

		MockRenderRequest mockRenderRequest = _mockRenderRequest();

		ThemeDisplay themeDisplay = mock(ThemeDisplay.class);

		mockRenderRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		User user = mock(User.class);

		when(
			user.isDefaultUser()
		).thenReturn(
			defaultUser
		);

		when(
			themeDisplay.getUser()
		).thenReturn(
			user
		);

		PortletDisplay portletDisplay = mock(PortletDisplay.class);

		when(
			portletDisplay.getPortletResource()
		).thenReturn(
			null
		);

		when(
			themeDisplay.getPortletDisplay()
		).thenReturn(
			portletDisplay
		);

		return mockRenderRequest;
	}

	private void _mockWorkflowDefinitionLinkLocalService(
		boolean hasWorkflowDefinitionLink) {

		when(
			_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				Matchers.anyLong(), Matchers.anyLong(), Matchers.anyString(),
				Matchers.anyLong())
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

		when(
			_language.getLanguageId(Matchers.any(Locale.class))
		).thenReturn(
			_DEFAULT_LANGUAGE_ID
		);

		when(
			_language.getLanguageId(Matchers.eq(_request))
		).thenReturn(
			_DEFAULT_LANGUAGE_ID
		);

		languageUtil.setLanguage(_language);
	}

	private void _setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId(_DEFAULT_LANGUAGE_ID)
		).thenReturn(
			LocaleUtil.SPAIN
		);

		when(
			LocaleUtil.fromLanguageId("pt_BR")
		).thenReturn(
			LocaleUtil.BRAZIL
		);
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(mock(Portal.class));

		when(
			PortalUtil.getHttpServletRequest(Matchers.any(RenderRequest.class))
		).thenReturn(
			_request
		);

		when(
			PortalUtil.getLiferayPortletRequest(
				Matchers.any(RenderRequest.class))
		).thenReturn(
			Mockito.mock(LiferayPortletRequest.class)
		);

		when(
			PortalUtil.getOriginalServletRequest(
				Matchers.any(HttpServletRequest.class))
		).thenReturn(
			_mockHttpServletRequest
		);
	}

	private void _setUpResourceBundleUtil() {
		mockStatic(ResourceBundleUtil.class);

		when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private static final String _DEFAULT_LANGUAGE_ID = "es_ES";

	@Mock
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Mock
	private DDMFormInstanceService _ddmFormInstanceService;

	@Mock
	private DDMFormInstanceVersion _ddmFormInstanceVersion;

	@Mock
	private DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;

	@Mock
	private DDMFormWebConfiguration _ddmFormWebConfiguration;

	@Mock
	private Language _language;

	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();

	@Mock
	private MockHttpServletRequest _request;

	@Mock
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}