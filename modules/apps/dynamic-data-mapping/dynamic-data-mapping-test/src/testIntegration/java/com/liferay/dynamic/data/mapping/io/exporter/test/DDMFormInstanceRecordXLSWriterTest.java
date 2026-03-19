/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.io.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.util.DateDDMFormFieldUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;

import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mario Gomes
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceRecordXLSWriterTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_originalThemeDisplayLocale = LocaleThreadLocal.getThemeDisplayLocale();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setThemeDisplayLocale(_originalThemeDisplayLocale);
	}

	@Test
	public void testWrite() throws Exception {
		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);

		DDMFormInstanceRecordWriterResponse
			ddmFormInstanceRecordWriterResponse =
				_ddmFormInstanceRecordWriter.write(
					DDMFormInstanceRecordWriterRequest.Builder.newBuilder(
						LinkedHashMapBuilder.put(
							"dateField", "Date"
						).put(
							"dateTimeField", "Date Time"
						).build(),
						List.of(
							LinkedHashMapBuilder.put(
								"dateField", "03/15/2024"
							).put(
								"dateTimeField", "03/20/2026, 12:12\u202FPM"
							).build())
					).withDDMFormFields(
						HashMapBuilder.<String, DDMFormField>put(
							"dateField",
							new DDMFormField(
								"dateField", DDMFormFieldTypeConstants.DATE)
						).put(
							"dateTimeField",
							new DDMFormField(
								"dateTimeField",
								DDMFormFieldTypeConstants.DATE_TIME)
						).build()
					).build());

		try (Workbook workbook = new HSSFWorkbook(
				new ByteArrayInputStream(
					ddmFormInstanceRecordWriterResponse.getContent()))) {

			Sheet sheet = workbook.getSheetAt(0);

			Row row = sheet.getRow(1);

			Cell cell = row.getCell(0);

			CellStyle cellStyle = cell.getCellStyle();

			Assert.assertEquals(
				DateDDMFormFieldUtil.getPattern(false, LocaleUtil.US),
				cellStyle.getDataFormatString());

			Assert.assertEquals(CellType.NUMERIC, cell.getCellType());

			cell = row.getCell(1);

			cellStyle = cell.getCellStyle();

			Assert.assertEquals(
				DateDDMFormFieldUtil.getPattern(true, LocaleUtil.US),
				cellStyle.getDataFormatString());

			Assert.assertEquals(CellType.NUMERIC, cell.getCellType());
		}
	}

	@Inject(filter = "ddm.form.instance.record.writer.type=xls")
	private DDMFormInstanceRecordWriter _ddmFormInstanceRecordWriter;

	private Locale _originalThemeDisplayLocale;

}