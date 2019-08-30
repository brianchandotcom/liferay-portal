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

import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsRetriever;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.dynamic.data.mapping.form.web.internal.instance.lifecycle.AddDefaultSharedFormLayoutPortalInstanceLifecycleListener;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializer;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterTracker;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

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

/**
 * @author Adam Brandizzi
 */
@PrepareForTest(
	{
		LocaleUtil.class, ResourceBundleUtil.class,
		ResourceBundleLoaderUtil.class, ServiceTrackerCollections.class
	}
)
@RunWith(PowerMockRunner.class)
public class DDMFormAdminDisplayContextTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.setProps(new PropsImpl());
	}

	@Before
	public void setUp() throws PortalException {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		setUpPortalUtil();
		setUpServiceTrackerCollections();

		setUpLanguageUtil();
		setUpResourceBundleUtil();
		setUpResourceBundleLoaderUtil();

		setUpDDMFormDisplayContext();
	}

	@Test
	public void testGetFormURLForRestrictedFormInstance() throws Exception {
		setRenderRequestParamenter(
			"formInstanceId", String.valueOf(_RESTRICTED_FORM_INSTANCE_ID));

		String formURL = _ddmFormAdminDisplayContext.getFormURL();

		Assert.assertEquals(getRestrictedFormURL(), formURL);
	}

	@Test
	public void testGetFormURLForSharedFormInstance() throws Exception {
		setRenderRequestParamenter(
			"formInstanceId", String.valueOf(_SHARED_FORM_INSTANCE_ID));

		String formURL = _ddmFormAdminDisplayContext.getFormURL();

		Assert.assertEquals(getSharedFormURL(), formURL);
	}

	@Test
	public void testGetRestrictedFormURL() throws Exception {
		String restrictedFormURL =
			_ddmFormAdminDisplayContext.getRestrictedFormURL();

		Assert.assertEquals(getRestrictedFormURL(), restrictedFormURL);
	}

	@Test
	public void testGetSharedFormURL() throws Exception {
		String sharedFormURL = _ddmFormAdminDisplayContext.getSharedFormURL();

		Assert.assertEquals(getSharedFormURL(), sharedFormURL);
	}

	protected String getFormURL(
		String friendlyURLPath, String pageFriendlyURLPath) {

		return StringBundler.concat(
			_PORTAL_URL, friendlyURLPath, _GROUP_FRIENDLY_URL_PATH,
			pageFriendlyURLPath, _FORM_APPLICATION_PATH);
	}

	protected String getRestrictedFormURL() {
		return getFormURL(
			_PRIVATE_FRIENDLY_URL_PATH, _PRIVATE_PAGE_FRIENDLY_URL_PATH);
	}

	protected String getSharedFormURL() {
		return getFormURL(
			_PUBLIC_FRIENDLY_URL_PATH, _PUBLIC_PAGE_FRIENDLY_URL_PATH);
	}

	protected DDMFormInstance mockDDMFormInstance(
			long formInstanceId, boolean requireAuthentication)
		throws PortalException {

		DDMFormInstance formInstance = mock(DDMFormInstance.class);

		when(
			formInstance.getFormInstanceId()
		).thenReturn(
			formInstanceId
		);

		DDMFormInstanceSettings settings = mockDDMFormInstanceSettings(
			requireAuthentication);

		when(
			formInstance.getSettingsModel()
		).thenReturn(
			settings
		);

		return formInstance;
	}

	protected DDMFormInstanceService mockDDMFormInstanceService()
		throws PortalException {

		DDMFormInstanceService formInstanceService = mock(
			DDMFormInstanceService.class);

		DDMFormInstance sharedFormInstance = mockDDMFormInstance(
			_SHARED_FORM_INSTANCE_ID, false);

		when(
			formInstanceService.fetchFormInstance(
				Matchers.eq(_SHARED_FORM_INSTANCE_ID))
		).thenReturn(
			sharedFormInstance
		);

		DDMFormInstance restrictedFormInstance = mockDDMFormInstance(
			_RESTRICTED_FORM_INSTANCE_ID, true);

		when(
			formInstanceService.fetchFormInstance(
				Matchers.eq(_RESTRICTED_FORM_INSTANCE_ID))
		).thenReturn(
			restrictedFormInstance
		);

		return formInstanceService;
	}

	protected DDMFormInstanceSettings mockDDMFormInstanceSettings(
		boolean requireAuthentication) {

		DDMFormInstanceSettings settings = mock(DDMFormInstanceSettings.class);

		when(
			settings.requireAuthentication()
		).thenReturn(
			requireAuthentication
		);

		return settings;
	}

	protected Group mockGroup() {
		Group group = mock(Group.class);

		when(
			group.getPathFriendlyURL(
				Matchers.eq(false), Matchers.any(ThemeDisplay.class))
		).thenReturn(
			_PUBLIC_FRIENDLY_URL_PATH
		);

		when(
			group.getPathFriendlyURL(
				Matchers.eq(true), Matchers.any(ThemeDisplay.class))
		).thenReturn(
			_PRIVATE_FRIENDLY_URL_PATH
		);

		return group;
	}

	protected HttpServletRequest mockHttpServletRequest() {
		ThemeDisplay themeDisplay = mockThemeDisplay();

		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

		when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		return httpServletRequest;
	}

	protected ThemeDisplay mockThemeDisplay() {
		ThemeDisplay themeDisplay = mock(ThemeDisplay.class);

		Group group = mockGroup();

		when(
			themeDisplay.getPortalURL()
		).thenReturn(
			_PORTAL_URL
		);

		when(
			themeDisplay.getSiteGroup()
		).thenReturn(
			group
		);

		return themeDisplay;
	}

	protected void setRenderRequestParamenter(String parameter, String value) {
		when(
			_renderRequest.getParameter(Matchers.eq(parameter))
		).thenReturn(
			value
		);
	}

	protected void setUpDDMFormDisplayContext() throws PortalException {
		_renderRequest = mock(RenderRequest.class);

		_ddmFormAdminDisplayContext = new DDMFormAdminDisplayContext(
			_renderRequest, mock(RenderResponse.class),
			new AddDefaultSharedFormLayoutPortalInstanceLifecycleListener(),
			mock(DDMFormBuilderContextFactory.class),
			mock(DDMFormBuilderSettingsRetriever.class),
			mock(DDMFormFieldTypeServicesTracker.class),
			mock(DDMFormFieldTypesSerializer.class),
			mock(DDMFormInstanceLocalService.class),
			mock(DDMFormInstanceRecordLocalService.class),
			mock(DDMFormInstanceRecordWriterTracker.class),
			mockDDMFormInstanceService(),
			mock(DDMFormInstanceVersionLocalService.class),
			mock(DDMFormRenderer.class),
			mock(DDMFormTemplateContextFactory.class),
			mock(DDMFormValuesFactory.class), mock(DDMFormValuesMerger.class),
			mock(DDMFormWebConfiguration.class),
			mock(DDMStructureLocalService.class),
			mock(DDMStructureService.class), mock(JSONFactory.class),
			mock(NPMResolver.class), mock(Portal.class));
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = mock(Language.class);

		languageUtil.setLanguage(language);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		HttpServletRequest httpServletRequest = mockHttpServletRequest();

		when(
			portal.getHttpServletRequest(Matchers.any(PortletRequest.class))
		).thenReturn(
			httpServletRequest
		);

		portalUtil.setPortal(portal);
	}

	protected void setUpResourceBundleLoaderUtil() {
		ResourceBundleLoader resourceBundleLoader = mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		when(
			resourceBundleLoader.loadResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	protected void setUpResourceBundleUtil() {
		mockStatic(ResourceBundleUtil.class);

		when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	protected void setUpServiceTrackerCollections() {
		mockStatic(ServiceTrackerCollections.class, Mockito.RETURNS_MOCKS);

		stub(
			method(
				ServiceTrackerCollections.class, "openSingleValueMap",
				Class.class, String.class)
		).toReturn(
			_serviceTrackerMap
		);
	}

	private static final String _FORM_APPLICATION_PATH =
		Portal.FRIENDLY_URL_SEPARATOR + "form/";

	private static final String _GROUP_FRIENDLY_URL_PATH = "/forms";

	private static final String _PORTAL_URL = "http://localhost:9999";

	private static final String _PRIVATE_FRIENDLY_URL_PATH = "/group";

	private static final String _PRIVATE_PAGE_FRIENDLY_URL_PATH = "/shared";

	private static final String _PUBLIC_FRIENDLY_URL_PATH = "/web";

	private static final String _PUBLIC_PAGE_FRIENDLY_URL_PATH = "/shared";

	private static final long _RESTRICTED_FORM_INSTANCE_ID =
		RandomTestUtil.randomLong();

	private static final long _SHARED_FORM_INSTANCE_ID =
		RandomTestUtil.randomLong();

	private DDMFormAdminDisplayContext _ddmFormAdminDisplayContext;
	private RenderRequest _renderRequest;

	@Mock
	private ServiceTrackerMap<String, ResourceBundleLoader> _serviceTrackerMap;

}