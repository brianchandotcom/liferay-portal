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

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.io.impl.DDMFormXSDDeserializerImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeServicesTrackerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Before;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Pablo Carvalho
 */
@PrepareForTest({DDMFormFieldTypeServicesTrackerUtil.class, LocaleUtil.class})
public class DDMFormXSDDeserializerTest
	extends BaseDDMFormDeserializerTestCase {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormFieldTypeServicesTrackerUtil();
		setUpDDMFormXSDDeserializerUtil();
		setUpHtmlUtil();
		setUpLocaleUtil();
		setUpPropsUtil();
		setUpSAXReaderUtil();
	}

	@Override
	protected DDMForm deserialize(String serializedDDMForm)
		throws PortalException {

		return DDMFormXSDDeserializerUtil.deserialize(serializedDDMForm);
	}

	@Override
	protected String getDeserializerType() {
		return "xsd";
	}

	@Override
	protected String getTestFileExtension() {
		return ".xml";
	}

	protected void setUpDDMFormXSDDeserializerUtil() {
		DDMFormXSDDeserializerUtil ddmFormXSDDeserializerUtil =
			new DDMFormXSDDeserializerUtil();

		ddmFormXSDDeserializerUtil.setDDMFormXSDDeserializer(
			new DDMFormXSDDeserializerImpl());
	}

}