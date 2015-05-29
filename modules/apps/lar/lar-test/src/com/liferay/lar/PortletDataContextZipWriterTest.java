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

package com.liferay.lar;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Mate Thurzo
 */
@RunWith(Arquillian.class)
public class PortletDataContextZipWriterTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_zipWriter = mock(ZipWriter.class);

		_portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				null, null, null, _zipWriter);
	}

	@Test
	public void testMultipleByteArraysAddition() throws Exception {
		byte[] bytes = {Byte.MIN_VALUE, Byte.MAX_VALUE};

		_portletDataContext.addZipEntry(_PATH, bytes);

		Mockito.verify(
			_zipWriter, Mockito.times(1)
		).addEntry(
			_PATH, bytes
		);

		_portletDataContext.addZipEntry(_PATH, bytes);

		Mockito.verify(
			_zipWriter,
			Mockito.times(1)).addEntry(
				_PATH, bytes
		);
	}

	@Test
	public void testMultipleInputStreamsAddition() throws Exception {
		byte[] bytes = {Byte.MIN_VALUE, Byte.MAX_VALUE};

		InputStream is = new ByteArrayInputStream(bytes);

		_portletDataContext.addZipEntry(_PATH, is);

		Mockito.verify(
			_zipWriter, Mockito.times(1)
		).addEntry(
			_PATH, is
		);

		_portletDataContext.addZipEntry(_PATH, is);

		Mockito.verify(
			_zipWriter, Mockito.times(1)
		).addEntry(
			_PATH, is
		);
	}

	@Test
	public void testMultipleStringsAddition() throws Exception {
		String string = RandomTestUtil.randomString();

		_portletDataContext.addZipEntry(_PATH, string);

		Mockito.verify(
			_zipWriter, Mockito.times(1)
		).addEntry(
			_PATH, string
		);

		_portletDataContext.addZipEntry(_PATH, string);

		Mockito.verify(
			_zipWriter, Mockito.times(1)
		).addEntry(
			_PATH, string
		);
	}

	private static final String _PATH = "/test.xml";

	private PortletDataContext _portletDataContext;
	private ZipWriter _zipWriter;

}