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

package com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.taglib.internal.security.permission.DDMFormInstancePermission;
import com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib.util.DDMFormTaglibUtil;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordVersionImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionImpl;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.lang.reflect.Field;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.portlet.MockRenderResponse;

/**
 * @author Pedro Queiroz
 */
@PrepareForTest(
	{
		DDMFormInstancePermission.class, DDMFormTaglibUtil.class,
		LocaleUtil.class
	}
)
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor(
	{
		"com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceModelImpl",
		"com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordModelImpl",
		"com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordVersionModelImpl",
		"com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionModelImpl",
		"com.liferay.taglib.util.IncludeTag"
	}
)
public class DDMFormRendererTagTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormInstanceLocalService();
		setUpDDMFormInstancePermission();
		setUpDDMFormInstanceRecordLocalService();
		setUpDDMFormInstanceRecordVersionLocalService();
		setUpDDMFormInstanceVersionLocalService();
		setUpDDMFormValuesFactory();
		setUpHttpServletRequest();
		setUpLanguageUtil();
		setUpLocaleUtil();
		setUpPortalUtil();
	}

	@Test
	public void testCreateDDMFormRenderingContext() {
		setDDMFormRendererTagInputs(1L, null, null, null);

		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormRenderingContext ddmFormRenderingContext =
			_ddmFormRendererTag.createDDMFormRenderingContext(ddmForm);

		Assert.assertNotNull(ddmFormRenderingContext.getContainerId());
		Assert.assertEquals(
			_ddmFormInstance.getGroupId(),
			ddmFormRenderingContext.getGroupId());
		Assert.assertNotNull(ddmFormRenderingContext.getHttpServletRequest());
		Assert.assertNotNull(ddmFormRenderingContext.getHttpServletResponse());
		Assert.assertEquals(LocaleUtil.US, ddmFormRenderingContext.getLocale());
		Assert.assertTrue(ddmFormRenderingContext.isViewMode());
		Assert.assertNotNull(ddmFormRenderingContext.getDDMFormValues());
		Assert.assertNotNull(ddmFormRenderingContext.getPortletNamespace());
		Assert.assertFalse(ddmFormRenderingContext.isReadOnly());
		Assert.assertFalse(ddmFormRenderingContext.isShowSubmitButton());
	}

	@Test
	public void testGetDDMFormDefaultLocaleWhenLocaleIsNotAvailable() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.BRAZIL);

		Locale locale = _ddmFormRendererTag.getLocale(_request, ddmForm);

		Assert.assertEquals(LocaleUtil.BRAZIL, locale);
	}

	@Test
	public void testGetFormInstanceWhenFormInstanceRecordIdHasHigherPriority() {
		setDDMFormRendererTagInputs(1L, 2L, null, 4L);

		DDMFormInstance ddmFormInstance =
			_ddmFormRendererTag.getDDMFormInstance();

		Assert.assertEquals(2L, ddmFormInstance.getFormInstanceId());
	}

	@Test
	public void testGetFormInstanceWhenFormInstanceRecordVersionIdHasHigherPriority() {
		setDDMFormRendererTagInputs(1L, 2L, 3L, 4L);

		DDMFormInstance ddmFormInstance =
			_ddmFormRendererTag.getDDMFormInstance();

		Assert.assertEquals(3L, ddmFormInstance.getFormInstanceId());
	}

	@Test
	public void testGetFormInstanceWhenFormInstanceVersionIdHasHigherPriority() {
		setDDMFormRendererTagInputs(1L, null, null, 4L);

		DDMFormInstance ddmFormInstance =
			_ddmFormRendererTag.getDDMFormInstance();

		Assert.assertEquals(4L, ddmFormInstance.getFormInstanceId());
	}

	@Test
	public void testGetFormInstanceWithFormInstanceId() {
		setDDMFormRendererTagInputs(1L, null, null, null);

		DDMFormInstance ddmFormInstance =
			_ddmFormRendererTag.getDDMFormInstance();

		Assert.assertEquals(1L, ddmFormInstance.getFormInstanceId());
	}

	@Test
	public void testGetFormInstanceWithFormInstanceRecordId() {
		setDDMFormRendererTagInputs(null, 2L, null, null);

		DDMFormInstance ddmFormInstance =
			_ddmFormRendererTag.getDDMFormInstance();

		Assert.assertEquals(2L, ddmFormInstance.getFormInstanceId());
	}

	@Test
	public void testGetFormInstanceWithFormInstanceRecordVersionId() {
		setDDMFormRendererTagInputs(null, null, 3L, null);

		DDMFormInstance ddmFormInstance =
			_ddmFormRendererTag.getDDMFormInstance();

		Assert.assertEquals(3L, ddmFormInstance.getFormInstanceId());
	}

	@Test
	public void testGetFormInstanceWithFormInstanceVersionId() {
		setDDMFormRendererTagInputs(null, null, null, 4L);

		DDMFormInstance ddmFormInstance =
			_ddmFormRendererTag.getDDMFormInstance();

		Assert.assertEquals(4L, ddmFormInstance.getFormInstanceId());
	}

	@Test
	public void testGetLocaleFromRequestWhenDDMFormIsNull() {
		Locale locale = _ddmFormRendererTag.getLocale(_request, null);

		Assert.assertEquals(LocaleUtil.US, locale);
	}

	@Test
	public void testGetLocaleWhenLocaleIsAvailable() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.BRAZIL);
		ddmForm.setAvailableLocales(
			createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US));

		Locale locale = _ddmFormRendererTag.getLocale(_request, ddmForm);

		Assert.assertEquals(LocaleUtil.US, locale);
	}

	@Test
	public void testGetRedirectURLWhenFormInstanceIsNull() {
		setDDMFormRendererTagInputs(null, null, null, null);

		String redirectURL = _ddmFormRendererTag.getRedirectURL();

		Assert.assertEquals(StringPool.BLANK, redirectURL);
	}

	protected Set<Locale> createAvailableLocales(Locale... locales) {
		Set<Locale> availableLocales = new LinkedHashSet<>();

		for (Locale locale : locales) {
			availableLocales.add(locale);
		}

		return availableLocales;
	}

	protected void mockDDMFormInstance(long ddmFormInstanceId) {
		DDMFormInstanceImpl ddmFormInstance = new DDMFormInstanceImpl();

		ddmFormInstance.setFormInstanceId(ddmFormInstanceId);

		Mockito.when(
			_ddmFormInstanceLocalService.fetchFormInstance(
				Matchers.eq(ddmFormInstanceId))
		).thenReturn(
			ddmFormInstance
		);
	}

	protected void setDDMFormRendererTagInputs(
		Long ddmFormInstanceId, Long ddmFormInstanceRecordId,
		Long ddmFormInstanceRecordVersionId, Long ddmFormInstanceVersionId) {

		_ddmFormRendererTag.setDdmFormInstanceId(ddmFormInstanceId);
		_ddmFormRendererTag.setDdmFormInstanceRecordId(ddmFormInstanceRecordId);
		_ddmFormRendererTag.setDdmFormInstanceRecordVersionId(
			ddmFormInstanceRecordVersionId);
		_ddmFormRendererTag.setDdmFormInstanceVersionId(
			ddmFormInstanceVersionId);
	}

	protected void setUpDDMFormInstanceLocalService() throws Exception {
		_ddmFormInstance = new DDMFormInstanceImpl();

		Field field = ReflectionUtil.getDeclaredField(
			DDMFormTaglibUtil.class, "_ddmFormInstanceLocalService");

		mockDDMFormInstance(1L);
		mockDDMFormInstance(2L);
		mockDDMFormInstance(3L);
		mockDDMFormInstance(4L);

		field.set(_ddmFormTaglibUtil, _ddmFormInstanceLocalService);
	}

	protected void setUpDDMFormInstancePermission() throws PortalException {
		mockStatic(DDMFormInstancePermission.class);

		when(
			DDMFormInstancePermission.contains(
				Mockito.any(PermissionChecker.class),
				Mockito.any(DDMFormInstance.class), Mockito.anyString())
		).thenReturn(
			true
		);
	}

	protected void setUpDDMFormInstanceRecordLocalService() throws Exception {
		_ddmFormInstanceRecord = new DDMFormInstanceRecordImpl();

		_ddmFormInstanceRecord.setFormInstanceId(2L);

		Field field = ReflectionUtil.getDeclaredField(
			DDMFormTaglibUtil.class, "_ddmFormInstanceRecordLocalService");

		Mockito.when(
			_ddmFormInstanceRecordLocalService.fetchDDMFormInstanceRecord(
				Mockito.anyLong())
		).thenReturn(
			_ddmFormInstanceRecord
		);

		field.set(_ddmFormTaglibUtil, _ddmFormInstanceRecordLocalService);
	}

	protected void setUpDDMFormInstanceRecordVersionLocalService()
		throws Exception {

		_ddmFormInstanceRecordVersion = new DDMFormInstanceRecordVersionImpl();

		_ddmFormInstanceRecordVersion.setFormInstanceId(3L);

		Field field = ReflectionUtil.getDeclaredField(
			DDMFormTaglibUtil.class,
			"_ddmFormInstanceRecordVersionLocalService");

		Mockito.when(
			_ddmFormInstanceRecordVersionLocalService.
				fetchDDMFormInstanceRecordVersion(Mockito.anyLong())
		).thenReturn(
			_ddmFormInstanceRecordVersion
		);

		field.set(
			_ddmFormTaglibUtil, _ddmFormInstanceRecordVersionLocalService);
	}

	protected void setUpDDMFormInstanceVersionLocalService() throws Exception {
		_ddmFormInstanceVersion = new DDMFormInstanceVersionImpl();

		_ddmFormInstanceVersion.setFormInstanceId(4L);

		Field field = ReflectionUtil.getDeclaredField(
			DDMFormTaglibUtil.class, "_ddmFormInstanceVersionLocalService");

		Mockito.when(
			_ddmFormInstanceVersionLocalService.fetchDDMFormInstanceVersion(
				Mockito.anyLong())
		).thenReturn(
			_ddmFormInstanceVersion
		);

		field.set(_ddmFormTaglibUtil, _ddmFormInstanceVersionLocalService);
	}

	protected void setUpDDMFormValuesFactory() throws Exception {
		_ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			new DDMForm());

		Field field = ReflectionUtil.getDeclaredField(
			DDMFormTaglibUtil.class, "_ddmFormValuesFactory");

		Mockito.when(
			_ddmFormValuesFactory.create(
				Mockito.any(HttpServletRequest.class),
				Mockito.any(DDMForm.class))
		).thenReturn(
			_ddmFormValues
		);

		field.set(_ddmFormTaglibUtil, _ddmFormValuesFactory);
	}

	protected void setUpHttpServletRequest() throws IllegalAccessException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		_request.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());
		_request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		MemberMatcher.field(
			DDMFormRendererTag.class, "request"
		).set(
			_ddmFormRendererTag, _request
		);
	}

	protected void setUpLanguageUtil() {
		when(
			_language.getLanguageId(Matchers.eq(_request))
		).thenReturn(
			"en_US"
		);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	protected void setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("en_US")
		).thenReturn(
			LocaleUtil.US
		);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(mock(Portal.class));

		when(
			PortalUtil.getHttpServletRequest(Matchers.any(RenderRequest.class))
		).thenReturn(
			_request
		);

		when(
			PortalUtil.getHttpServletResponse(
				Matchers.any(RenderResponse.class))
		).thenReturn(
			new MockHttpServletResponse()
		);
	}

	private DDMFormInstance _ddmFormInstance;

	@Mock
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	private DDMFormInstanceRecord _ddmFormInstanceRecord;

	@Mock
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	private DDMFormInstanceRecordVersion _ddmFormInstanceRecordVersion;

	@Mock
	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

	private DDMFormInstanceVersion _ddmFormInstanceVersion;

	@Mock
	private DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;

	private final DDMFormRendererTag _ddmFormRendererTag =
		new DDMFormRendererTag();

	@Mock
	private DDMFormTaglibUtil _ddmFormTaglibUtil;

	private DDMFormValues _ddmFormValues;

	@Mock
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Mock
	private Language _language;

	private final HttpServletRequest _request = new MockHttpServletRequest();

}