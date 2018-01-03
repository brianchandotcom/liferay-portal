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

package com.liferay.consumer.talend.runtime.reader;

import static org.hamcrest.Matchers.is;

import com.liferay.consumer.talend.tliferayinput.TLiferayInputProperties;

import java.io.File;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.generic.IndexedRecord;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import org.talend.components.api.service.ComponentService;
import org.talend.components.api.service.common.ComponentServiceImpl;
import org.talend.components.api.service.common.DefinitionRegistry;
import org.talend.components.liferay.LiferayInputFamilyDefinition;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.SchemaConstants;

/**
 * @author Zoltán Takács
 */
@SuppressWarnings("nls")
public class LiferayInputTest {

	public ComponentService getComponentService() {
		if (_componentService == null) {
			DefinitionRegistry testComponentRegistry = new DefinitionRegistry();

			testComponentRegistry.registerComponentFamilyDefinition(
				new LiferayInputFamilyDefinition());
			_componentService = new ComponentServiceImpl(testComponentRegistry);
		}

		return _componentService;
	}

	@Before
	public void setUpComponentRegistryAndService() {

		// reset the component service

		_componentService = null;
	}

	@Ignore
	@Test
	public void testtLiferayInputRuntime() throws Exception {
		TLiferayInputProperties props =
			(TLiferayInputProperties)
				getComponentService().getComponentProperties("tLiferayInput");

		File tempFile = File.createTempFile("tLiferayInputTestFile", ".txt");

		try {
			PrintWriter writer = new PrintWriter(
				tempFile.getAbsolutePath(), "UTF-8");

			writer.println("string value 1;true;100;2017-01-01;1.23");
			writer.println("string value 2;false;200;2017-01-22;4.56");
			writer.close();

			//props.endpoint.setValue(tempFile.getAbsolutePath());

			Schema.Field col0 = new Schema.Field(
				"stringCol", AvroUtils._string(), null, (Object)null);

			Schema.Field col1 = new Schema.Field(
				"booleanCol", AvroUtils._boolean(), null, (Object)null);

			Schema.Field col2 = new Schema.Field(
				"intCol", AvroUtils._int(), null, (Object)null);

			Schema.Field col3 = new Schema.Field(
				"timestampCol", AvroUtils._logicalTimestamp(), null,
				(Object)null);

			col3.addProp(SchemaConstants.TALEND_COLUMN_PATTERN, "yyyy-MM-dd");

			Schema.Field col4 = new Schema.Field(
				"doubleCol", AvroUtils._double(), null, (Object)null);

			List<Field> fields = Arrays.asList(col0, col1, col2, col3, col4);

			Schema schema = Schema.createRecord(
				"file", null, null, false, fields);

			//props.schema.schema.setValue(schema);

			LiferayInputSource source = new LiferayInputSource();

			source.initialize(null, props);

			LiferayInputReader reader = source.createReader(null);

			Assert.assertThat(reader.start(), is(true));

			IndexedRecord current = reader.getCurrent();

			IndexedRecord currentDataRecord = (IndexedRecord)current.get(0);
			IndexedRecord currentOutOfBandRecord = (IndexedRecord)current.get(
				1);

			Assert.assertThat(
				currentDataRecord.get(0), is((Object)"string value 1"));
			Assert.assertThat(currentDataRecord.get(1), is((Object)true));
			Assert.assertThat(currentDataRecord.get(2), is((Object)100));
			Assert.assertThat(
				currentDataRecord.get(3), is((Object)1483228800000L));
			Assert.assertThat(currentDataRecord.get(4), is((Object)1.23));
			Assert.assertThat(currentOutOfBandRecord.get(0), is((Object)0));

			// No auto advance when calling getCurrent more than once.

			current = reader.getCurrent();

			currentDataRecord = (IndexedRecord)current.get(0);
			currentOutOfBandRecord = (IndexedRecord)current.get(1);

			Assert.assertThat(
				currentDataRecord.get(0), is((Object)"string value 1"));
			Assert.assertThat(currentDataRecord.get(1), is((Object)true));
			Assert.assertThat(currentDataRecord.get(2), is((Object)100));
			Assert.assertThat(
				currentDataRecord.get(3), is((Object)1483228800000L));
			Assert.assertThat(currentDataRecord.get(4), is((Object)1.23));
			Assert.assertThat(currentOutOfBandRecord.get(0), is((Object)0));

			Assert.assertThat(reader.advance(), is(true));
			current = reader.getCurrent();

			currentDataRecord = (IndexedRecord)current.get(0);
			currentOutOfBandRecord = (IndexedRecord)current.get(1);

			Assert.assertThat(
				currentDataRecord.get(0), is((Object)"string value 2"));
			Assert.assertThat(currentDataRecord.get(1), is((Object)false));
			Assert.assertThat(currentDataRecord.get(2), is((Object)200));
			Assert.assertThat(
				currentDataRecord.get(3), is((Object)1485043200000L));
			Assert.assertThat(currentDataRecord.get(4), is((Object)4.56));
			Assert.assertThat(currentOutOfBandRecord.get(0), is((Object)1));

			// no more records

			Assert.assertThat(reader.advance(), is(false));
		}
		finally {
			tempFile.delete();
		}
	}

	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	private ComponentService _componentService;

}