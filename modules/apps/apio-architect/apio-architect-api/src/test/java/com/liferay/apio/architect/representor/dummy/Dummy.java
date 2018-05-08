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

package com.liferay.apio.architect.representor.dummy;

import static java.nio.charset.StandardCharsets.UTF_8;

import static java.util.Arrays.asList;

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.language.Language;

import java.io.ByteArrayInputStream;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Provides a dummy class that can be used with {@link
 * com.liferay.apio.architect.representor.Representor}.
 *
 * @author Alejandro Hernández
 */
public class Dummy {

	public Dummy(int id) {
		this.id = id;
	}

	public String getLocalizedString1(Language language) {
		Locale locale = language.getPreferredLocale();

		return locale.getLanguage() + "1";
	}

	public String getLocalizedString2(Locale locale) {
		return locale.getLanguage() + "2";
	}

	public String getString1() {
		return "String 1";
	}

	public String getString2() {
		return "String 2";
	}

	public final BinaryFile binaryFile1 = new BinaryFile(
		new ByteArrayInputStream("Input Stream 1".getBytes(UTF_8)), 0L,
		"application/octet-stream");
	public final BinaryFile binaryFile2 = new BinaryFile(
		new ByteArrayInputStream("Input Stream 2".getBytes(UTF_8)), 0L,
		"application/octet-stream");
	public final Boolean boolean1 = true;
	public final Boolean boolean2 = false;
	public final List<Boolean> booleanList1 = asList(true, false, false, true);
	public final List<Boolean> booleanList2 = asList(false, false, true, false);
	public final Date date1 = new Date(1465981200000L);
	public final Date date2 = new Date(1491244560000L);
	public final int id;
	public final Number number1 = 1L;
	public final Number number2 = 2L;
	public final List<Number> numberList1 = asList(1, 2, 3, 4, 5);
	public final List<Number> numberList2 = asList(6, 7, 8, 9, 10);
	public final Integer relatedModelId1 = 1;
	public final Integer relatedModelId2 = 2;
	public final Integer relatedModelId3 = 3;
	public final Integer relatedModelId4 = 4;
	public final List<String> stringList1 = asList("a", "b", "c", "d");
	public final List<String> stringList2 = asList("e", "f", "g", "h");

}