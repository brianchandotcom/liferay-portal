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

package com.liferay.portal.kernel.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Hugo Huijser
 */
public class TextFormatterTest {

	@Test
	public void testFormatA() {
		_testFormat("Web Search", "WEB_SEARCH", TextFormatter.A);
	}

	@Test
	public void testFormatB() {
		_testFormat("Web Search", "websearch", TextFormatter.B);
	}

	@Test
	public void testFormatC() {
		_testFormat("Web Search", "web_search", TextFormatter.C);
	}

	@Test
	public void testFormatD() {
		_testFormat("Web Search", "WebSearch", TextFormatter.D);
	}

	@Test
	public void testFormatE() {
		_testFormat("Web Search", "web search", TextFormatter.E);
	}

	@Test
	public void testFormatF() {
		_testFormat("Web Search", "webSearch", TextFormatter.F);
	}

	@Test
	public void testFormatG() {
		_testFormat("formatId", "FormatId", TextFormatter.G);
		_testFormat("FriendlyURLMapper", "FriendlyURLMapper", TextFormatter.G);
	}

	@Test
	public void testFormatH() {
		_testFormat("formatId", "format id", TextFormatter.H);
		_testFormat(
			"FriendlyURLMapper", "friendly url mapper", TextFormatter.H);
	}

	@Test
	public void testFormatI() {
		_testFormat("FormatId", "formatId", TextFormatter.I);
		_testFormat("FriendlyURLMapper", "friendlyURLMapper", TextFormatter.I);
	}

	@Test
	public void testFormatJ() {
		_testFormat("format-id", "Format Id", TextFormatter.J);
		_testFormat(
			"friendly-url-mapper", "Friendly Url Mapper", TextFormatter.J);
	}

	@Test
	public void testFormatK() {
		_testFormat("formatId", "format-id", TextFormatter.K);
		_testFormat(
			"FriendlyURLMapper", "friendly-url-mapper", TextFormatter.K);
	}

	@Test
	public void testFormatL() {
		_testFormat("FormatId", "formatId", TextFormatter.L);
		_testFormat("FOrmatId", "FOrmatId", TextFormatter.L);
	}

	@Test
	public void testFormatM() {
		_testFormat("format-id", "formatId", TextFormatter.M);
		_testFormat(
			"friendly-url-mapper", "friendlyUrlMapper", TextFormatter.M);
	}

	@Test
	public void testFormatN() {
		_testFormat("format-id", "format_id", TextFormatter.N);
		_testFormat(
			"friendly-url-mapper", "friendly_url_mapper", TextFormatter.N);
	}

	@Test
	public void testFormatO() {
		_testFormat("format_id", "format-id", TextFormatter.O);
		_testFormat(
			"friendly_url_mapper", "friendly-url-mapper", TextFormatter.O);
	}

	@Test
	public void testFormatQ() {
		_testFormat("FORMATId", "format-id", TextFormatter.Q);
	}

	@Test
	public void testformatStorageSizeOneB() throws Exception {
		long bytes = 1;

		Assert.assertEquals(
			"1 B", TextFormatter.formatStorageSize(bytes, LocaleUtil.SPAIN));
		Assert.assertEquals(
			"1 B", TextFormatter.formatStorageSize(bytes, LocaleUtil.US));
	}

	@Test
	public void testformatStorageSizeOneGB() throws Exception {
		long bytes = 1024 * 1024 * 1024;

		Assert.assertEquals(
			"1 GB", TextFormatter.formatStorageSize(bytes, LocaleUtil.SPAIN));
		Assert.assertEquals(
			"1 GB", TextFormatter.formatStorageSize(bytes, LocaleUtil.US));
	}

	@Test
	public void testformatStorageSizeOneKB() throws Exception {
		long bytes = 1024;

		Assert.assertEquals(
			"1 KB", TextFormatter.formatStorageSize(bytes, LocaleUtil.SPAIN));
		Assert.assertEquals(
			"1 KB", TextFormatter.formatStorageSize(bytes, LocaleUtil.US));
	}

	@Test
	public void testformatStorageSizeOneMB() throws Exception {
		long bytes = 1024 * 1024;

		Assert.assertEquals(
			"1 MB", TextFormatter.formatStorageSize(bytes, LocaleUtil.SPAIN));
		Assert.assertEquals(
			"1 MB", TextFormatter.formatStorageSize(bytes, LocaleUtil.US));
	}

	private void _testFormat(String original, String expected, int style) {
		String actual = TextFormatter.format(original, style);

		Assert.assertEquals(expected, actual);
	}

}