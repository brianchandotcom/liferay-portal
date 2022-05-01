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
import com.liferay.dynamic.data.mapping.form.taglib.internal.servlet.taglib.util.DDMFormTaglibUtil;
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
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockRenderResponse;

import java.lang.reflect.Field;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Pedro Queiroz
 */
public class DDMFormRendererTagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
		setUpPortalUtil();
	}

	@Test
	public void testCreateDDMFormRenderingContext() {
		setDDMFormRendererTagInputs(1L, null, null, null, false, false);

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

		Assert.assertEquals(
			LocaleUtil.BRAZIL,
			_ddmFormRendererTag.getLocale(_httpServletRequest, ddmForm));
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
		Assert.assertEquals(
			LocaleUtil.US,
			_ddmFormRendererTag.getLocale(_httpServletRequest, null));
	}

	@Test
	public void testGetLocaleWhenLocaleIsAvailable() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.BRAZIL);
		ddmForm.setAvailableLocales(
			createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US));

		Assert.assertEquals(
			LocaleUtil.US,
			_ddmFormRendererTag.getLocale(_httpServletRequest, ddmForm));
	}

	@Test
	public void testGetRedirectURLWhenFormInstanceIsNull() {
		setDDMFormRendererTagInputs(null, null, null, null);

		Assert.assertEquals(
			StringPool.BLANK, _ddmFormRendererTag.getRedirectURL());
	}

	protected Set<Locale> createAvailableLocales(Locale... locales) {
		Set<Locale> availableLocales = new LinkedHashSet<>();

		for (Locale locale : locales) {
			availableLocales.add(locale);
		}

		return availableLocales;
	}

	protected void mockDDMFormInstance(long ddmFormInstanceId) {
		DDMFormInstanceImpl ddmFormInstanceImpl = new DDMFormInstanceImpl();

		ddmFormInstanceImpl.setFormInstanceId(ddmFormInstanceId);

		Mockito.when(
			_ddmFormInstanceLocalService.fetchFormInstance(
				Mockito.eq(ddmFormInstanceId))
		).thenReturn(
			ddmFormInstanceImpl
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

	protected void setDDMFormRendererTagInputs(
		Long ddmFormInstanceId, Long ddmFormInstanceRecordId,
		Long ddmFormInstanceRecordVersionId, Long ddmFormInstanceVersionId,
		Boolean showFormBasicInfo, Boolean showSubmitButton) {

		setDDMFormRendererTagInputs(
			ddmFormInstanceId, ddmFormInstanceRecordId,
			ddmFormInstanceRecordVersionId, ddmFormInstanceVersionId);

		_ddmFormRendererTag.setShowFormBasicInfo(showFormBasicInfo);
		_ddmFormRendererTag.setShowSubmitButton(showSubmitButton);
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
		ModelResourcePermission<DDMFormInstance>
			ddmFormInstanceModelResourcePermission = Mockito.mock(
				ModelResourcePermission.class);

		Mockito.when(
			ddmFormInstanceModelResourcePermission.contains(
				Mockito.nullable(PermissionChecker.class),
				Mockito.nullable(DDMFormInstance.class),
				Mockito.nullable(String.class))
		).thenReturn(
			true
		);

		ReflectionTestUtil.setFieldValue(
			DDMFormInstancePermission.class,
			"_ddmFormInstanceModelResourcePermission",
			ddmFormInstanceModelResourcePermission);
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
		_httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());
		_httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, new ThemeDisplay());

		ReflectionTestUtil.setFieldValue(
			_ddmFormRendererTag, "_httpServletRequest", _httpServletRequest);
	}

	protected void setUpLanguageUtil() {
		Mockito.when(
			_language.getLanguageId(Mockito.eq(_httpServletRequest))
		).thenReturn(
			"en_US"
		);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		portalUtil.setPortal(portal);

		Mockito.when(
			portal.getHttpServletRequest(Mockito.any(RenderRequest.class))
		).thenReturn(
			_httpServletRequest
		);

		Mockito.when(
			portal.getHttpServletResponse(Mockito.any(RenderResponse.class))
		).thenReturn(
			new MockHttpServletResponse()
		);
	}

	private DDMFormInstance _ddmFormInstance;
	private final DDMFormInstanceLocalService _ddmFormInstanceLocalService =
		Mockito.mock(DDMFormInstanceLocalService.class);
	private DDMFormInstanceRecord _ddmFormInstanceRecord;
	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService = Mockito.mock(
			DDMFormInstanceRecordLocalService.class);
	private DDMFormInstanceRecordVersion _ddmFormInstanceRecordVersion =
		Mockito.mock(DDMFormInstanceRecordVersion.class);
	private final DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService = Mockito.mock(
			DDMFormInstanceRecordVersionLocalService.class);
	private DDMFormInstanceVersion _ddmFormInstanceVersion;
	private final DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService = Mockito.mock(
			DDMFormInstanceVersionLocalService.class);
	private final DDMFormRendererTag _ddmFormRendererTag =
		new DDMFormRendererTag();
	private final DDMFormTaglibUtil _ddmFormTaglibUtil = Mockito.mock(
		DDMFormTaglibUtil.class);
	private DDMFormValues _ddmFormValues;
	private final DDMFormValuesFactory _ddmFormValuesFactory = Mockito.mock(
		DDMFormValuesFactory.class);
	private final HttpServletRequest _httpServletRequest =
		new MockHttpServletRequest();
	private final Language _language = Mockito.mock(Language.class);

}