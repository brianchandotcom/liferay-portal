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

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.exception.FormInstanceRecordExporterException;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordExporterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordExporterResponse;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class DDMFormInstanceRecordExporterImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpHtmlUtil();
		setUpLanguageUtil();
	}

	@Test
	public void testExport() throws Exception {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			mock(DDMFormInstanceRecordExporterImpl.class);

		ddmFormInstanceRecordExporterImpl.ddmFormInstanceRecordLocalService =
			_ddmFormInstanceRecordLocalService;

		DDMFormInstanceRecordExporterRequest.Builder builder =
			DDMFormInstanceRecordExporterRequest.Builder.newBuilder(1, "csv");

		OrderByComparator<DDMFormInstanceRecord> orderByComparator = mock(
			OrderByComparator.class);

		Locale locale = new Locale("pt", "BR");

		DDMFormInstanceRecordExporterRequest
			ddmFormInstanceRecordExporterRequest = builder.withStatus(
				WorkflowConstants.STATUS_APPROVED
			).withLocale(
				locale
			).withStart(
				1
			).withEnd(
				5
			).withOrderByComparator(
				orderByComparator
			).build();

		List<DDMFormInstanceRecord> ddmFormInstanceRecords =
			Collections.emptyList();

		when(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecords(
				1, WorkflowConstants.STATUS_APPROVED, 1, 5, orderByComparator)
		).thenReturn(
			ddmFormInstanceRecords
		);

		Map<String, DDMFormField> ddmFormFields = Collections.emptyMap();

		when(
			ddmFormInstanceRecordExporterImpl.getDistinctFields(1)
		).thenReturn(
			ddmFormFields
		);

		Map<String, String> ddmFormFieldsLabel = Collections.emptyMap();

		when(
			ddmFormInstanceRecordExporterImpl.getDDMFormFieldsLabel(
				ddmFormFields, locale)
		).thenReturn(
			ddmFormFieldsLabel
		);

		List<Map<String, String>> ddmFormFieldsValues = Collections.emptyList();

		when(
			ddmFormInstanceRecordExporterImpl.getDDMFormFieldValues(
				ddmFormFields, ddmFormInstanceRecords, locale)
		).thenReturn(
			ddmFormFieldsValues
		);

		when(
			ddmFormInstanceRecordExporterImpl.write(
				"csv", ddmFormFieldsLabel, ddmFormFieldsValues)
		).thenReturn(
			new byte[] {1, 2, 3}
		);

		when(
			ddmFormInstanceRecordExporterImpl.export(
				ddmFormInstanceRecordExporterRequest)
		).thenCallRealMethod();

		DDMFormInstanceRecordExporterResponse
			ddmFormInstanceRecordExporterResponse =
				ddmFormInstanceRecordExporterImpl.export(
					ddmFormInstanceRecordExporterRequest);

		Assert.assertArrayEquals(
			new byte[] {1, 2, 3},
			ddmFormInstanceRecordExporterResponse.getContent());

		InOrder inOrder = Mockito.inOrder(
			_ddmFormInstanceRecordLocalService,
			ddmFormInstanceRecordExporterImpl);

		inOrder.verify(
			_ddmFormInstanceRecordLocalService, Mockito.times(1)
		).getFormInstanceRecords(
			1, WorkflowConstants.STATUS_APPROVED, 1, 5, orderByComparator
		);

		inOrder.verify(
			ddmFormInstanceRecordExporterImpl, Mockito.times(1)
		).getDistinctFields(
			1
		);

		inOrder.verify(
			ddmFormInstanceRecordExporterImpl, Mockito.times(1)
		).getDDMFormFieldsLabel(
			ddmFormFields, locale
		);

		inOrder.verify(
			ddmFormInstanceRecordExporterImpl, Mockito.times(1)
		).getDDMFormFieldValues(
			ddmFormFields, ddmFormInstanceRecords, locale
		);

		inOrder.verify(
			ddmFormInstanceRecordExporterImpl, Mockito.times(1)
		).write(
			"csv", ddmFormFieldsLabel, ddmFormFieldsValues
		);
	}

	@Test(expected = FormInstanceRecordExporterException.class)
	public void testExportCatchException() throws Exception {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			new DDMFormInstanceRecordExporterImpl();

		ddmFormInstanceRecordExporterImpl.ddmFormInstanceRecordLocalService =
			_ddmFormInstanceRecordLocalService;

		when(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecords(
				Matchers.anyLong(), Matchers.anyInt(), Matchers.anyInt(),
				Matchers.anyInt(), Matchers.any(OrderByComparator.class))
		).thenThrow(
			Exception.class
		);

		DDMFormInstanceRecordExporterRequest.Builder builder =
			DDMFormInstanceRecordExporterRequest.Builder.newBuilder(1, "csv");

		ddmFormInstanceRecordExporterImpl.export(builder.build());
	}

	@Test
	public void testFormatDate() {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			new DDMFormInstanceRecordExporterImpl();

		DateTimeFormatter dateTimeFormatter =
			ddmFormInstanceRecordExporterImpl.getDateTimeFormatter(
				new Locale("pt", "BR"));

		LocalDate localDate = LocalDate.of(2018, 2, 1);

		Instant instant = Instant.from(
			localDate.atStartOfDay(ZoneId.systemDefault()));

		Date date = Date.from(instant);

		String actual = ddmFormInstanceRecordExporterImpl.formatDate(
			date, dateTimeFormatter);

		Assert.assertEquals("01/02/18 00:00", actual);
	}

	@Test
	public void testGetDateTimeFormatter() {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			new DDMFormInstanceRecordExporterImpl();

		DateTimeFormatter dateTimeFormatter =
			ddmFormInstanceRecordExporterImpl.getDateTimeFormatter(
				new Locale("pt", "BR"));

		Assert.assertEquals(
			"Localized(SHORT,SHORT)", dateTimeFormatter.toString());
	}

	@Test
	public void testGetDDMFormFieldsLabel() {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			new DDMFormInstanceRecordExporterImpl();

		Locale locale = new Locale("pt", "BR");

		when(
			_language.get(locale, "status")
		).thenReturn(
			"Estado"
		);

		when(
			_language.get(locale, "modified-date")
		).thenReturn(
			"Data de Modificação"
		);

		when(
			_language.get(locale, "author")
		).thenReturn(
			"Autor"
		);

		DDMFormField ddmFormField1 = new DDMFormField("field1", "text");

		LocalizedValue localizedValue1 = new LocalizedValue();

		localizedValue1.addString(locale, "Campo 1");

		ddmFormField1.setLabel(localizedValue1);

		DDMFormField ddmFormField2 = new DDMFormField("field2", "text");

		LocalizedValue localizedValue2 = new LocalizedValue();

		localizedValue2.addString(locale, "Campo 2");

		ddmFormField2.setLabel(localizedValue2);

		Map<String, DDMFormField> ddmFormFieldMap =
			HashMapBuilder.<String, DDMFormField>put(
				"field1", ddmFormField1
			).put(
				"field2", ddmFormField2
			).build();

		Map<String, String> ddmFormFieldsLabel =
			ddmFormInstanceRecordExporterImpl.getDDMFormFieldsLabel(
				ddmFormFieldMap, locale);

		Assert.assertEquals("Campo 1", ddmFormFieldsLabel.get("field1"));
		Assert.assertEquals("Campo 2", ddmFormFieldsLabel.get("field2"));
		Assert.assertEquals("Estado", ddmFormFieldsLabel.get("status"));
		Assert.assertEquals(
			"Data de Modificação", ddmFormFieldsLabel.get("modifiedDate"));
		Assert.assertEquals("Autor", ddmFormFieldsLabel.get("author"));
	}

	@Test
	public void testGetDDMFormFieldValue() throws Exception {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			new DDMFormInstanceRecordExporterImpl();

		ddmFormInstanceRecordExporterImpl.ddmFormFieldTypeServicesTracker =
			_ddmFormFieldTypeServicesTracker;

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer = mock(
			DDMFormFieldValueRenderer.class);

		DDMFormField ddmFormField = new DDMFormField("field1", "text");

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1", new UnlocalizedValue("value1"));

		ddmFormFieldValues.add(ddmFormFieldValue);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap =
			new HashMap<>();

		ddmFormFieldValueMap.put("field1", ddmFormFieldValues);

		Locale locale = new Locale("pt", "BR");

		when(
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueRenderer(
				"text")
		).thenReturn(
			ddmFormFieldValueRenderer
		);

		when(
			ddmFormFieldValueRenderer.render(ddmFormFieldValue, locale)
		).thenReturn(
			"value1"
		);

		when(
			_html.render("value1")
		).thenReturn(
			"value1"
		);

		String actualValue =
			ddmFormInstanceRecordExporterImpl.getDDMFormFieldValue(
				ddmFormField, ddmFormFieldValueMap, locale);

		Assert.assertEquals("value1", actualValue);

		Mockito.verify(
			_ddmFormFieldTypeServicesTracker, Mockito.times(1)
		).getDDMFormFieldValueRenderer(
			"text"
		);

		Mockito.verify(
			ddmFormFieldValueRenderer, Mockito.times(1)
		).render(
			ddmFormFieldValue, locale
		);

		Mockito.verify(
			_html, Mockito.times(1)
		).render(
			"value1"
		);
	}

	@Test
	public void testGetDDMFormFieldValues() throws Exception {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			mock(DDMFormInstanceRecordExporterImpl.class);

		Locale locale = new Locale("pt", "BR");

		List<DDMFormInstanceRecord> ddmFormInstanceRecords = new ArrayList<>();

		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		ddmFormInstanceRecords.add(ddmFormInstanceRecord);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField1 = new DDMFormField("field1", "text");

		ddmForm.addDDMFormField(ddmFormField1);

		DDMFormField ddmFormField2 = new DDMFormField("field2", "text");

		ddmForm.addDDMFormField(ddmFormField2);

		Map<String, DDMFormField> ddmFormFields = new HashMap<>();

		ddmFormFields.put("field1", ddmFormField1);
		ddmFormFields.put("field2", ddmFormField2);

		DDMFormValues ddmFormValues1 =
			DDMFormValuesTestUtil.createDDMFormValues(ddmForm);

		DDMFormFieldValue ddmFormFieldValue2 =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2", new UnlocalizedValue("value2"));

		ddmFormValues1.addDDMFormFieldValue(ddmFormFieldValue2);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion = mock(
			DDMFormInstanceRecordVersion.class);

		DateTimeFormatter dateTimeFormatter = mock(DateTimeFormatter.class);

		when(
			ddmFormInstanceRecordExporterImpl.getDateTimeFormatter(locale)
		).thenReturn(
			dateTimeFormatter
		);

		when(
			ddmFormInstanceRecord.getDDMFormValues()
		).thenReturn(
			ddmFormValues1
		);

		when(
			ddmFormInstanceRecordExporterImpl.getDDMFormFieldValue(
				Matchers.any(DDMFormField.class), Matchers.anyMap(),
				Matchers.any(Locale.class))
		).thenReturn(
			"value"
		);

		when(
			ddmFormInstanceRecord.getFormInstanceRecordVersion()
		).thenReturn(
			ddmFormInstanceRecordVersion
		);

		when(
			ddmFormInstanceRecordVersion.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		when(
			ddmFormInstanceRecordVersion.getStatusDate()
		).thenReturn(
			new Date()
		);

		when(
			ddmFormInstanceRecordVersion.getUserName()
		).thenReturn(
			"User Name"
		);

		when(
			ddmFormInstanceRecordExporterImpl.getStatusMessage(
				Matchers.anyInt(), Matchers.any(Locale.class))
		).thenReturn(
			"aprovado"
		);

		when(
			ddmFormInstanceRecordExporterImpl.formatDate(
				Matchers.any(Date.class), Matchers.any(DateTimeFormatter.class))
		).thenReturn(
			"01/02/2018 00:00"
		);

		when(
			ddmFormInstanceRecordExporterImpl.getDDMFormFieldValues(
				ddmFormFields, ddmFormInstanceRecords, locale)
		).thenCallRealMethod();

		List<Map<String, String>> ddmFormFieldValues =
			ddmFormInstanceRecordExporterImpl.getDDMFormFieldValues(
				ddmFormFields, ddmFormInstanceRecords, locale);

		Map<String, String> valuesMap = ddmFormFieldValues.get(0);

		Assert.assertEquals(StringPool.BLANK, valuesMap.get("field1"));
		Assert.assertEquals("value", valuesMap.get("field2"));
		Assert.assertEquals("aprovado", valuesMap.get("status"));
		Assert.assertEquals("01/02/2018 00:00", valuesMap.get("modifiedDate"));
		Assert.assertEquals("User Name", valuesMap.get("author"));

		InOrder inOrder = Mockito.inOrder(
			ddmFormInstanceRecordExporterImpl, ddmFormInstanceRecord,
			ddmFormInstanceRecordVersion);

		inOrder.verify(
			ddmFormInstanceRecordExporterImpl, Mockito.times(1)
		).getDateTimeFormatter(
			locale
		);

		inOrder.verify(
			ddmFormInstanceRecord, Mockito.times(1)
		).getDDMFormValues();

		inOrder.verify(
			ddmFormInstanceRecordExporterImpl, Mockito.times(1)
		).getDDMFormFieldValue(
			Matchers.any(DDMFormField.class), Matchers.anyMap(),
			Matchers.any(Locale.class)
		);

		inOrder.verify(
			ddmFormInstanceRecord, Mockito.times(1)
		).getFormInstanceRecordVersion();

		inOrder.verify(
			ddmFormInstanceRecordVersion, Mockito.times(1)
		).getStatus();

		inOrder.verify(
			ddmFormInstanceRecordExporterImpl, Mockito.times(1)
		).getStatusMessage(
			Matchers.anyInt(), Matchers.any(Locale.class)
		);

		inOrder.verify(
			ddmFormInstanceRecordVersion, Mockito.times(1)
		).getStatusDate();

		inOrder.verify(
			ddmFormInstanceRecordExporterImpl, Mockito.times(1)
		).formatDate(
			Matchers.any(Date.class), Matchers.any(DateTimeFormatter.class)
		);
	}

	@Test
	public void testGetDistinctFields() throws Exception {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			mock(DDMFormInstanceRecordExporterImpl.class);

		DDMStructureVersion ddmStructureVersion = mock(
			DDMStructureVersion.class);

		when(
			ddmFormInstanceRecordExporterImpl.getStructureVersions(1L)
		).thenReturn(
			ListUtil.fromArray(ddmStructureVersion)
		);

		Map<String, DDMFormField> ddmFormFields = new LinkedHashMap<>();

		DDMFormField ddmFormField1 = new DDMFormField("field1", "text");

		DDMFormField ddmFormField2 = new DDMFormField("field2", "text");

		ddmFormFields.put("field1", ddmFormField1);
		ddmFormFields.put("field2", ddmFormField2);

		when(
			ddmFormInstanceRecordExporterImpl.getNontransientDDMFormFieldsMap(
				ddmStructureVersion)
		).thenReturn(
			ddmFormFields
		);

		when(
			ddmFormInstanceRecordExporterImpl.getDistinctFields(1L)
		).thenCallRealMethod();

		Map<String, DDMFormField> distinctFields =
			ddmFormInstanceRecordExporterImpl.getDistinctFields(1);

		Assert.assertEquals(ddmFormField1, distinctFields.get("field1"));
		Assert.assertEquals(ddmFormField2, distinctFields.get("field2"));

		InOrder inOrder = Mockito.inOrder(ddmFormInstanceRecordExporterImpl);

		inOrder.verify(
			ddmFormInstanceRecordExporterImpl, Mockito.times(1)
		).getStructureVersions(
			1
		);

		inOrder.verify(
			ddmFormInstanceRecordExporterImpl, Mockito.times(1)
		).getNontransientDDMFormFieldsMap(
			ddmStructureVersion
		);
	}

	@Test
	public void testGetNontransientDDMFormFieldsMap() {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			new DDMFormInstanceRecordExporterImpl();

		DDMStructureVersion ddmStructureVersion = mock(
			DDMStructureVersion.class);

		DDMForm ddmForm = mock(DDMForm.class);

		when(
			ddmStructureVersion.getDDMForm()
		).thenReturn(
			ddmForm
		);

		ddmFormInstanceRecordExporterImpl.getNontransientDDMFormFieldsMap(
			ddmStructureVersion);

		InOrder inOrder = Mockito.inOrder(ddmStructureVersion, ddmForm);

		inOrder.verify(
			ddmStructureVersion, Mockito.times(1)
		).getDDMForm();

		inOrder.verify(
			ddmForm, Mockito.times(1)
		).getNontransientDDMFormFieldsMap(
			true
		);
	}

	@Test
	public void testGetStatusMessage() {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			new DDMFormInstanceRecordExporterImpl();

		Locale locale = new Locale("pt", "BR");

		when(
			_language.get(locale, "approved")
		).thenReturn(
			"approvado"
		);

		String statusMessage =
			ddmFormInstanceRecordExporterImpl.getStatusMessage(
				WorkflowConstants.STATUS_APPROVED, locale);

		Assert.assertEquals("approvado", statusMessage);

		Mockito.verify(
			_language, Mockito.times(1)
		).get(
			locale, "approved"
		);
	}

	@Test
	public void testGetStructureVersions() throws Exception {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			new DDMFormInstanceRecordExporterImpl();

		ddmFormInstanceRecordExporterImpl.ddmFormInstanceVersionLocalService =
			_ddmFormInstanceVersionLocalService;

		List<DDMFormInstanceVersion> ddmFormInstanceVersions =
			new ArrayList<>();

		DDMFormInstanceVersion ddmFormInstanceVersion = mock(
			DDMFormInstanceVersion.class);

		ddmFormInstanceVersions.add(ddmFormInstanceVersion);

		when(
			_ddmFormInstanceVersionLocalService.getFormInstanceVersions(
				1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)
		).thenReturn(
			ddmFormInstanceVersions
		);

		DDMStructureVersion ddmStructureVersion = mock(
			DDMStructureVersion.class);

		when(
			ddmFormInstanceVersion.getStructureVersion()
		).thenReturn(
			ddmStructureVersion
		);

		List<DDMStructureVersion> structureVersions =
			ddmFormInstanceRecordExporterImpl.getStructureVersions(1);

		Assert.assertEquals(ddmStructureVersion, structureVersions.get(0));

		InOrder inOrder = Mockito.inOrder(
			_ddmFormInstanceVersionLocalService, ddmFormInstanceVersion);

		inOrder.verify(
			_ddmFormInstanceVersionLocalService, Mockito.times(1)
		).getFormInstanceVersions(
			1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null
		);

		inOrder.verify(
			ddmFormInstanceVersion, Mockito.times(1)
		).getStructureVersion();
	}

	@Test
	public void testWrite() throws Exception {
		DDMFormInstanceRecordExporterImpl ddmFormInstanceRecordExporterImpl =
			new DDMFormInstanceRecordExporterImpl();

		ddmFormInstanceRecordExporterImpl.ddmFormInstanceRecordWriterTracker =
			_ddmFormInstanceRecordWriterTracker;

		DDMFormInstanceRecordWriter ddmFormInstanceRecordWriter = mock(
			DDMFormInstanceRecordWriter.class);

		when(
			_ddmFormInstanceRecordWriterTracker.getDDMFormInstanceRecordWriter(
				"txt")
		).thenReturn(
			ddmFormInstanceRecordWriter
		);

		DDMFormInstanceRecordWriterResponse.Builder builder =
			DDMFormInstanceRecordWriterResponse.Builder.newBuilder(
				new byte[] {1, 2, 3});

		when(
			ddmFormInstanceRecordWriter.write(
				Matchers.any(DDMFormInstanceRecordWriterRequest.class))
		).thenReturn(
			builder.build()
		);

		byte[] content = ddmFormInstanceRecordExporterImpl.write(
			"txt", Collections.emptyMap(), Collections.emptyList());

		Assert.assertArrayEquals(new byte[] {1, 2, 3}, content);

		InOrder inOrder = Mockito.inOrder(
			_ddmFormInstanceRecordWriterTracker, ddmFormInstanceRecordWriter);

		inOrder.verify(
			_ddmFormInstanceRecordWriterTracker, Mockito.times(1)
		).getDDMFormInstanceRecordWriter(
			"txt"
		);

		inOrder.verify(
			ddmFormInstanceRecordWriter, Mockito.times(1)
		).write(
			Matchers.any(DDMFormInstanceRecordWriterRequest.class)
		);
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(_html);
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	@Mock
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Mock
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	@Mock
	private DDMFormInstanceRecordWriterTracker
		_ddmFormInstanceRecordWriterTracker;

	@Mock
	private DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;

	@Mock
	private Html _html;

	@Mock
	private Language _language;

}