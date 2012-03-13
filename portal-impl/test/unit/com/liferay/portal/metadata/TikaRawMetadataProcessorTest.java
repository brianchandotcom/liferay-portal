/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.PowerMockitoStubber;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.xml.sax.ContentHandler;

/**
 * @author Miguel Pastor
 */
@PrepareForTest(PrefsPropsUtil.class)
@RunWith(PowerMockRunner.class)
public class TikaRawMetadataProcessorTest extends PowerMockito {

	@Test
	public void testExtractMetadataFromInputStream() {
		mockStatic(PrefsPropsUtil.class);

		try {
			OngoingStubbing<Boolean> ongoingStubbing = when(
				PrefsPropsUtil.getBoolean(
					PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED));

			ongoingStubbing.thenReturn(Boolean.FALSE);

			ContentHandler contentHandler = Mockito.any(ContentHandler.class);

			Metadata metadata = Mockito.any(Metadata.class);

			InputStream inputStream = Mockito.any(InputStream.class);
			ParseContext parseContext = Mockito.any(ParseContext.class);

			_parser.parse(inputStream, contentHandler, metadata, parseContext);

			PowerMockitoStubber powerMockitoStubber = doAnswer(
				new Answer<Object>() {

					public Object answer(InvocationOnMock invocationOnMock)
						throws Throwable {

						return new Metadata();
					}

				});

			Parser parser = powerMockitoStubber.when(_parser);

			parser.parse(inputStream, contentHandler, metadata, parseContext);

			metadata = _tikaRawMetadataProcessor.extractMetadata(
				"pdf", "application/pdf", inputStream);

			Assert.assertNotNull(metadata);
			Assert.assertEquals(0, metadata.size());

		}
		catch (Exception e) {
			Assert.fail("Unexpected error");
		}
	}

	@Mock
	private Parser _parser;

	@InjectMocks
	private TikaRawMetadataProcessor _tikaRawMetadataProcessor =
		new TikaRawMetadataProcessor();

}