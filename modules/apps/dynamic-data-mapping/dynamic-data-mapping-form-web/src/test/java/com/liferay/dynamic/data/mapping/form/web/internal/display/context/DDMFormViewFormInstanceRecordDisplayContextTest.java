/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.display.context;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.RenderRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alberto Sousa
 */
public class DDMFormViewFormInstanceRecordDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpDDMFormInstanceRecordService();
		_setUpDDMFormInstanceVersionLocalService();
		_setUpDDMFormRenderer();
		_setUpDDMFormValuesMerger();
		_setUpHttpServletRequest();
		_setUpPortalUtil();
	}

	@Test
	public void testGetDDMFormContext() throws Exception {
		DDMFormViewFormInstanceRecordDisplayContext
			ddmFormViewFormInstanceRecordDisplayContext =
				new DDMFormViewFormInstanceRecordDisplayContext(
					_httpServletRequest,
					Mockito.mock(HttpServletResponse.class),
					_ddmFormInstanceRecordService,
					_ddmFormInstanceVersionLocalService, _ddmFormRenderer,
					Mockito.mock(DDMFormValuesFactory.class),
					_ddmFormValuesMerger);

		ddmFormViewFormInstanceRecordDisplayContext.getDDMFormContext(
			_renderRequest);

		Mockito.verify(
			_ddmFormInstanceRecordService
		).getFormInstanceRecord(
			_FORM_INSTANCE_RECORD_ID
		);
	}

	private DDMFormInstance _mockDDMFormInstance() throws Exception {
		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		Mockito.when(
			ddmFormInstance.getFormInstanceVersion(Mockito.anyString())
		).thenReturn(
			_ddmFormInstanceVersion
		);

		return ddmFormInstance;
	}

	private DDMFormInstanceRecord _mockDDMFormInstanceRecord()
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord = Mockito.mock(
			DDMFormInstanceRecord.class);

		DDMFormValues ddmFormValues = _mockDDMFormValues();

		Mockito.when(
			ddmFormInstanceRecord.getDDMFormValues()
		).thenReturn(
			ddmFormValues
		);

		DDMFormInstance ddmFormInstance = _mockDDMFormInstance();

		Mockito.when(
			ddmFormInstanceRecord.getFormInstance()
		).thenReturn(
			ddmFormInstance
		);

		Mockito.when(
			ddmFormInstanceRecord.getFormInstanceVersion()
		).thenReturn(
			"1.0"
		);

		return ddmFormInstanceRecord;
	}

	private DDMFormValues _mockDDMFormValues() {
		DDMFormValues ddmFormValues = Mockito.mock(DDMFormValues.class);

		Mockito.when(
			ddmFormValues.getAvailableLocales()
		).thenReturn(
			Collections.singleton(LocaleUtil.US)
		);

		Mockito.when(
			ddmFormValues.getDefaultLocale()
		).thenReturn(
			LocaleUtil.US
		);

		return ddmFormValues;
	}

	private void _setUpDDMFormInstanceRecordService() throws Exception {
		DDMFormInstanceRecord ddmFormInstanceRecord =
			_mockDDMFormInstanceRecord();

		Mockito.when(
			_ddmFormInstanceRecordService.getFormInstanceRecord(
				_FORM_INSTANCE_RECORD_ID)
		).thenReturn(
			ddmFormInstanceRecord
		);
	}

	private void _setUpDDMFormInstanceVersionLocalService() throws Exception {
		DDMStructureVersion ddmStructureVersion = Mockito.mock(
			DDMStructureVersion.class);

		Mockito.when(
			ddmStructureVersion.getDDMForm()
		).thenReturn(
			Mockito.mock(DDMForm.class)
		);

		Mockito.when(
			ddmStructureVersion.getDDMFormLayout()
		).thenReturn(
			Mockito.mock(DDMFormLayout.class)
		);

		Mockito.when(
			_ddmFormInstanceVersion.getStructureVersion()
		).thenReturn(
			ddmStructureVersion
		);

		Mockito.when(
			_ddmFormInstanceVersionLocalService.getLatestFormInstanceVersion(
				Mockito.anyLong(), Mockito.anyInt())
		).thenReturn(
			_ddmFormInstanceVersion
		);
	}

	private void _setUpDDMFormRenderer() throws Exception {
		Mockito.when(
			_ddmFormRenderer.getDDMFormTemplateContext(
				Mockito.any(), Mockito.any(), Mockito.any())
		).thenReturn(
			Collections.emptyMap()
		);
	}

	private void _setUpDDMFormValuesMerger() {
		DDMFormValues mergedDDMFormValues = Mockito.mock(DDMFormValues.class);

		Mockito.when(
			mergedDDMFormValues.getDefaultLocale()
		).thenReturn(
			LocaleUtil.US
		);

		Mockito.when(
			_ddmFormValuesMerger.merge(Mockito.any(), Mockito.any())
		).thenReturn(
			mergedDDMFormValues
		);
	}

	private void _setUpHttpServletRequest() {
		Mockito.when(
			_httpServletRequest.getParameter("formInstanceRecordId")
		).thenReturn(
			String.valueOf(_FORM_INSTANCE_RECORD_ID)
		);
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(Mockito.mock(Portal.class));
	}

	private static final long _FORM_INSTANCE_RECORD_ID =
		RandomTestUtil.randomLong();

	private final DDMFormInstanceRecordService _ddmFormInstanceRecordService =
		Mockito.mock(DDMFormInstanceRecordService.class);
	private final DDMFormInstanceVersion _ddmFormInstanceVersion = Mockito.mock(
		DDMFormInstanceVersion.class);
	private final DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService = Mockito.mock(
			DDMFormInstanceVersionLocalService.class);
	private final DDMFormRenderer _ddmFormRenderer = Mockito.mock(
		DDMFormRenderer.class);
	private final DDMFormValuesMerger _ddmFormValuesMerger = Mockito.mock(
		DDMFormValuesMerger.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final RenderRequest _renderRequest = Mockito.mock(
		RenderRequest.class);

}