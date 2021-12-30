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

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriter;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DDMFormInstanceRecordWriterTrackerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDeactivate() {
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTrackerImpl =
				new DDMFormInstanceRecordWriterTrackerImpl();

		_addDDMFormInstanceRecordCSVWriter(
			ddmFormInstanceRecordWriterTrackerImpl);

		ddmFormInstanceRecordWriterTrackerImpl.deactivate();

		Map<String, String> ddmFormInstanceRecordWriterExtensions =
			ddmFormInstanceRecordWriterTrackerImpl.
				getDDMFormInstanceRecordWriterExtensions();

		Assert.assertTrue(ddmFormInstanceRecordWriterExtensions.isEmpty());
	}

	@Test
	public void testGetDDMFormInstanceRecordWriter() {
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTrackerImpl =
				new DDMFormInstanceRecordWriterTrackerImpl();

		_addDDMFormInstanceRecordCSVWriter(
			ddmFormInstanceRecordWriterTrackerImpl);

		DDMFormInstanceRecordWriter ddmFormInstanceRecordWriter =
			ddmFormInstanceRecordWriterTrackerImpl.
				getDDMFormInstanceRecordWriter("csv");

		Assert.assertTrue(
			ddmFormInstanceRecordWriter instanceof
				DDMFormInstanceRecordCSVWriter);
	}

	@Test
	public void testGetDDMFormInstanceRecordWriterDefaultUpperCaseExtension() {
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTrackerImpl =
				new DDMFormInstanceRecordWriterTrackerImpl();

		_addDDMFormInstanceRecordXMLWriter(
			ddmFormInstanceRecordWriterTrackerImpl);

		Map<String, String> ddmFormInstanceRecordWriterExtensions =
			ddmFormInstanceRecordWriterTrackerImpl.
				getDDMFormInstanceRecordWriterExtensions();

		Assert.assertEquals(
			"XML", ddmFormInstanceRecordWriterExtensions.get("xml"));
	}

	@Test
	public void testGetDDMFormInstanceRecordWriterTypes() {
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTrackerImpl =
				new DDMFormInstanceRecordWriterTrackerImpl();

		_addDDMFormInstanceRecordCSVWriter(
			ddmFormInstanceRecordWriterTrackerImpl);
		_addDDMFormInstanceRecordJSONWriter(
			ddmFormInstanceRecordWriterTrackerImpl);

		Map<String, String> ddmFormInstanceRecordWriterExtensions =
			ddmFormInstanceRecordWriterTrackerImpl.
				getDDMFormInstanceRecordWriterExtensions();

		Assert.assertEquals(
			"csv", ddmFormInstanceRecordWriterExtensions.get("csv"));
		Assert.assertEquals(
			"json", ddmFormInstanceRecordWriterExtensions.get("json"));
	}

	@Test
	public void testRemoveDDMFormInstanceRecordWriter() {
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTrackerImpl =
				new DDMFormInstanceRecordWriterTrackerImpl();

		_addDDMFormInstanceRecordCSVWriter(
			ddmFormInstanceRecordWriterTrackerImpl);
		_addDDMFormInstanceRecordJSONWriter(
			ddmFormInstanceRecordWriterTrackerImpl);

		DDMFormInstanceRecordWriter ddmFormInstanceRecordWriter =
			new DDMFormInstanceRecordCSVWriter();

		ddmFormInstanceRecordWriterTrackerImpl.
			removeDDMFormInstanceRecordWriter(
				ddmFormInstanceRecordWriter,
				HashMapBuilder.<String, Object>put(
					"ddm.form.instance.record.writer.extension", "csv"
				).put(
					"ddm.form.instance.record.writer.type", "csv"
				).build());

		ddmFormInstanceRecordWriter =
			ddmFormInstanceRecordWriterTrackerImpl.
				getDDMFormInstanceRecordWriter("csv");

		Assert.assertNull(ddmFormInstanceRecordWriter);
	}

	private void _addDDMFormInstanceRecordCSVWriter(
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTrackerImpl) {

		ddmFormInstanceRecordWriterTrackerImpl.addDDMFormInstanceRecordWriter(
			new DDMFormInstanceRecordCSVWriter(),
			HashMapBuilder.<String, Object>put(
				"ddm.form.instance.record.writer.extension", "csv"
			).put(
				"ddm.form.instance.record.writer.type", "csv"
			).build());
	}

	private void _addDDMFormInstanceRecordJSONWriter(
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTrackerImpl) {

		ddmFormInstanceRecordWriterTrackerImpl.addDDMFormInstanceRecordWriter(
			new DDMFormInstanceRecordJSONWriter(),
			HashMapBuilder.<String, Object>put(
				"ddm.form.instance.record.writer.extension", "json"
			).put(
				"ddm.form.instance.record.writer.type", "json"
			).build());
	}

	private void _addDDMFormInstanceRecordXMLWriter(
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTrackerImpl) {

		ddmFormInstanceRecordWriterTrackerImpl.addDDMFormInstanceRecordWriter(
			new DDMFormInstanceRecordXMLWriter(),
			HashMapBuilder.<String, Object>put(
				"ddm.form.instance.record.writer.type", "xml"
			).build());
	}

}