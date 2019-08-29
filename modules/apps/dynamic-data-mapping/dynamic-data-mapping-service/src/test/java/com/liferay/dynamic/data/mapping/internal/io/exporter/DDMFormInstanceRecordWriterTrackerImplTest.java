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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class DDMFormInstanceRecordWriterTrackerImplTest {

	@Test
	public void testDeactivate() {
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTracker =
				new DDMFormInstanceRecordWriterTrackerImpl();

		addDDMFormInstanceRecordCSVWriter(ddmFormInstanceRecordWriterTracker);

		ddmFormInstanceRecordWriterTracker.deactivate();

		Map<String, String> ddmFormInstanceRecordWriterExtensions =
			ddmFormInstanceRecordWriterTracker.
				getDDMFormInstanceRecordWriterExtensions();

		Assert.assertTrue(ddmFormInstanceRecordWriterExtensions.isEmpty());
	}

	@Test
	public void testGetDDMFormInstanceRecordWriter() {
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTracker =
				new DDMFormInstanceRecordWriterTrackerImpl();

		addDDMFormInstanceRecordCSVWriter(ddmFormInstanceRecordWriterTracker);

		DDMFormInstanceRecordWriter ddmFormInstanceRecordWriter =
			ddmFormInstanceRecordWriterTracker.getDDMFormInstanceRecordWriter(
				"csv");

		Assert.assertTrue(
			ddmFormInstanceRecordWriter instanceof
				DDMFormInstanceRecordCSVWriter);
	}

	@Test
	public void testGetDDMFormInstanceRecordWriterDefaultUpperCaseExtension() {
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTracker =
				new DDMFormInstanceRecordWriterTrackerImpl();

		addDDMFormInstanceRecordXMLWriter(ddmFormInstanceRecordWriterTracker);

		Map<String, String> ddmFormInstanceRecordWriterExtensions =
			ddmFormInstanceRecordWriterTracker.
				getDDMFormInstanceRecordWriterExtensions();

		Assert.assertEquals(
			"XML", ddmFormInstanceRecordWriterExtensions.get("xml"));
	}

	@Test
	public void testGetDDMFormInstanceRecordWriterTypes() {
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTracker =
				new DDMFormInstanceRecordWriterTrackerImpl();

		addDDMFormInstanceRecordCSVWriter(ddmFormInstanceRecordWriterTracker);
		addDDMFormInstanceRecordJSONWriter(ddmFormInstanceRecordWriterTracker);

		Map<String, String> ddmFormInstanceRecordWriterExtensions =
			ddmFormInstanceRecordWriterTracker.
				getDDMFormInstanceRecordWriterExtensions();

		Assert.assertEquals(
			"csv", ddmFormInstanceRecordWriterExtensions.get("csv"));
		Assert.assertEquals(
			"json", ddmFormInstanceRecordWriterExtensions.get("json"));
	}

	@Test
	public void testRemoveDDMFormInstanceRecordWriter() {
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTracker =
				new DDMFormInstanceRecordWriterTrackerImpl();

		addDDMFormInstanceRecordCSVWriter(ddmFormInstanceRecordWriterTracker);
		addDDMFormInstanceRecordJSONWriter(ddmFormInstanceRecordWriterTracker);

		DDMFormInstanceRecordWriter ddmFormInstanceRecordWriter =
			new DDMFormInstanceRecordCSVWriter();

		Map<String, Object> properties = new HashMap<String, Object>() {
			{
				put("ddm.form.instance.record.writer.type", "csv");
				put("ddm.form.instance.record.writer.extension", "csv");
			}
		};

		ddmFormInstanceRecordWriterTracker.removeDDMFormInstanceRecordWriter(
			ddmFormInstanceRecordWriter, properties);

		ddmFormInstanceRecordWriter =
			ddmFormInstanceRecordWriterTracker.getDDMFormInstanceRecordWriter(
				"csv");

		Assert.assertNull(ddmFormInstanceRecordWriter);
	}

	protected void addDDMFormInstanceRecordCSVWriter(
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTracker) {

		DDMFormInstanceRecordWriter ddmFormInstanceRecordWriter =
			new DDMFormInstanceRecordCSVWriter();

		Map<String, Object> properties = new HashMap<String, Object>() {
			{
				put("ddm.form.instance.record.writer.type", "csv");
				put("ddm.form.instance.record.writer.extension", "csv");
			}
		};

		ddmFormInstanceRecordWriterTracker.addDDMFormInstanceRecordWriter(
			ddmFormInstanceRecordWriter, properties);
	}

	protected void addDDMFormInstanceRecordJSONWriter(
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTracker) {

		DDMFormInstanceRecordWriter ddmFormInstanceRecordWriter =
			new DDMFormInstanceRecordJSONWriter();

		Map<String, Object> properties = new HashMap<String, Object>() {
			{
				put("ddm.form.instance.record.writer.type", "json");
				put("ddm.form.instance.record.writer.extension", "json");
			}
		};

		ddmFormInstanceRecordWriterTracker.addDDMFormInstanceRecordWriter(
			ddmFormInstanceRecordWriter, properties);
	}

	protected void addDDMFormInstanceRecordXMLWriter(
		DDMFormInstanceRecordWriterTrackerImpl
			ddmFormInstanceRecordWriterTracker) {

		DDMFormInstanceRecordWriter ddmFormInstanceRecordWriter =
			new DDMFormInstanceRecordXMLWriter();

		Map<String, Object> properties = new HashMap<String, Object>() {
			{
				put("ddm.form.instance.record.writer.type", "xml");
			}
		};

		ddmFormInstanceRecordWriterTracker.addDDMFormInstanceRecordWriter(
			ddmFormInstanceRecordWriter, properties);
	}

}