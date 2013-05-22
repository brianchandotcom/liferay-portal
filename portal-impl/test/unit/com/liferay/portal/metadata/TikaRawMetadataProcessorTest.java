/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.metadata;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.xuggler.XugglerUtil;
import com.liferay.portal.util.PrefsPropsUtil;

import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.xml.sax.ContentHandler;

/**
 * @author Miguel Pastor
 */
@PrepareForTest({PrefsPropsUtil.class, XugglerUtil.class})
@RunWith(PowerMockRunner.class)
public class TikaRawMetadataProcessorTest extends PowerMockito {

	@Test
	public void testExtractMetadataFromInputStream() throws Exception {
		mockStatic(PrefsPropsUtil.class);
		mockStatic(XugglerUtil.class);

		try {
			when(
				XugglerUtil.isEnabled()
			).thenReturn(
				Boolean.FALSE
			);

			InputStream inputStream = Mockito.any(InputStream.class);
			ContentHandler contentHandler = Mockito.any(ContentHandler.class);
			Metadata metadata = Mockito.any(Metadata.class);
			ParseContext parseContext = Mockito.any(ParseContext.class);

			_parser.parse(inputStream, contentHandler, metadata, parseContext);

			metadata = _tikaRawMetadataProcessor.extractMetadata(
				"pdf", "application/pdf", inputStream);

			Assert.assertNotNull(metadata);
			Assert.assertEquals(0, metadata.size());

		}
		catch (SystemException se) {
			Assert.fail("Unexpected error");
		}
	}

	@Mock
	private Parser _parser;

	@InjectMocks
	private TikaRawMetadataProcessor _tikaRawMetadataProcessor =
		new TikaRawMetadataProcessor();

}