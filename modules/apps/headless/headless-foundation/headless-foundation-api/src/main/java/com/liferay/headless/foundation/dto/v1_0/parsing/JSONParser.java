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

package com.liferay.headless.foundation.dto.v1_0.parsing;

import com.liferay.headless.foundation.dto.v1_0.AnotherDTO;
import com.liferay.headless.foundation.dto.v1_0.MyDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * @author Rubén Pulido
 */
public abstract class JSONParser<T> {

	public T parse(String string) {
		if (string == null) {
			throw new IllegalArgumentException("Expected non null");
		}

		initFields(string);

		_assertStartsAndEndsWith("{", "}");

		T dto = createDto();

		if (_isEmpty()) {
			return dto;
		}

		readNextChar();

		_readWhileLastCharIsWhiteSpace();

		readNextChar();

		if (_isLastChar('}')) {
			_readWhileLastCharIsWhiteSpace();

			if (!_isEndOfText()) {
				readNextChar();

				throw new IllegalArgumentException(
					"Expected end of JSON; got '" + lastChar + "'");
			}

			return dto;
		}

		do {
			_readWhileLastCharIsWhiteSpace();

			String fieldName = _readString();

			_readWhileLastCharIsWhiteSpace();

			_assertLastChar(':');

			readNextChar();

			_readWhileLastCharIsWhiteSpace();

			setField(dto, fieldName, readValue());

			_readWhileLastCharIsWhiteSpace();
		}
		while (_ifLastCharMatchesThenRead(','));

		return dto;
	}

	public T[] parseArray(String string) {
		if (string == null) {
			throw new IllegalArgumentException("Expected non null");
		}

		initFields(string);

		_assertStartsAndEndsWith("[", "]");

		if (_isEmpty()) {
			return createDtos();
		}

		readNextChar();

		_readWhileLastCharIsWhiteSpace();

		if (_isLastChar(']')) {
			readNextChar();

			return createDtos();
		}

		_readWhileLastCharIsWhiteSpace();

		return arrayOfObjectsToArrayOfDtos((Object[])readValue());
	}

	protected static AnotherDTO[] arrayOfObjectsToArrayOfAnotherDtos(
		Object[] objects) {

		return Stream.of(
			objects
		).map(
			object -> AnotherDTO.toAnotherDto((String)object)
		).toArray(
			size -> new AnotherDTO[size]
		);
	}

	protected static Boolean[] arrayOfObjectsToArrayOfBooleans(
		Object[] objects) {

		return Stream.of(
			objects
		).map(
			Boolean.class::cast
		).toArray(
			size -> new Boolean[size]
		);
	}

	protected static Integer[] arrayOfObjectsToArrayOfIntegers(
		Object[] objects) {

		return Stream.of(
			objects
		).map(
			object -> {
				try {
					return Integer.parseInt(object.toString());
				}
				catch (NumberFormatException nfe) {
					throw new RuntimeException(nfe);
				}
			}
		).toArray(
			size -> new Integer[size]
		);
	}

	protected static Long[] arrayOfObjectsToArrayOfLongs(Object[] objects) {
		return Stream.of(
			objects
		).map(
			object -> {
				try {
					return Long.parseLong(object.toString());
				}
				catch (NumberFormatException nfe) {
					throw new RuntimeException(nfe);
				}
			}
		).toArray(
			size -> new Long[size]
		);
	}

	protected static MyDTO[] arrayOfObjectsToArrayOfMyDtos(Object[] objects) {
		return Stream.of(
			objects
		).map(
			object -> MyDTO.toMyDto((String)object)
		).toArray(
			size -> new MyDTO[size]
		);
	}

	protected static String[] arrayOfObjectsToArrayOfStrings(Object[] objects) {
		return Stream.of(
			objects
		).map(
			String.class::cast
		).toArray(
			size -> new String[size]
		);
	}

	protected Date[] arrayOfObjectsToArrayOfDates(Object[] objects) {
		return Stream.of(
			objects
		).map(
			object -> {
				try {
					return dateFormat.parse((String)object);
				}
				catch (ParseException pe) {
					throw new IllegalArgumentException(
						"Could not parse date from value " + object, pe);
				}
			}
		).toArray(
			size -> new Date[size]
		);
	}

	protected abstract T[] arrayOfObjectsToArrayOfDtos(Object[] objects);

	protected abstract T createDto();

	protected abstract T[] createDtos();

	protected void initFields(String string) {
		captureStartStack = new Stack<>();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		index = 0;
		jsonString = string.trim();
		lastChar = 0;
	}

	protected void readNextChar() {
		if (!_isEndOfText()) {
			lastChar = jsonString.charAt(index++);
		}
	}

	protected Object readValue() {
		if (lastChar == 'f') {
			return _readFalseAsBoolean();
		}
		else if (lastChar == 'n') {
			return _readNullAsObject();
		}
		else if ((lastChar == '-') || (lastChar == '0') || (lastChar == '1') ||
				 (lastChar == '2') || (lastChar == '3') || (lastChar == '4') ||
				 (lastChar == '5') || (lastChar == '6') || (lastChar == '7') ||
				 (lastChar == '8') || (lastChar == '9')) {

			return _readNumberAsString();
		}
		else if (lastChar == '"') {
			return _readString();
		}
		else if (lastChar == 't') {
			return _readTrueAsBoolean();
		}
		else if (lastChar == '[') {
			return _readArrayAsArrayOfObjects();
		}
		else if (lastChar == '{') {
			return _readObjectAsJsonString();
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	protected abstract void setField(T dto, String fieldName, Object object);

	protected static DateFormat dateFormat;

	protected Stack<Integer> captureStartStack;
	protected int index;
	protected String jsonString;
	protected char lastChar;

	private void _assertLastChar(char ch) {
		if (lastChar != ch) {
			throw new IllegalArgumentException(
				String.format("Expected '%s'; got '%s'", ch, lastChar));
		}
	}

	private void _assertStartsAndEndsWith(String prefix, String sufix) {
		if (!jsonString.startsWith(prefix)) {
			throw new IllegalArgumentException(
				String.format(
					"Expected '%s'; got '%s'", prefix, jsonString.charAt(0)));
		}

		if (!jsonString.endsWith(sufix)) {
			throw new IllegalArgumentException(
				String.format(
					"Expected '%s'; got '%s'", sufix,
					jsonString.charAt(jsonString.length() - 1)));
		}
	}

	private String _getCapturedSubstring() {
		return jsonString.substring(captureStartStack.pop(), index - 1);
	}

	private boolean _ifLastCharMatchesThenRead(char ch) {
		if (lastChar != ch) {
			return false;
		}

		readNextChar();

		return true;
	}

	private boolean _isEmpty() {
		String substring = jsonString.substring(1, jsonString.length() - 1);

		String substringTrimmed = substring.trim();

		return substringTrimmed.isEmpty();
	}

	private boolean _isEndOfText() {
		if (index == jsonString.length()) {
			return true;
		}

		return false;
	}

	private boolean _isLastChar(char ch) {
		if (lastChar == ch) {
			return true;
		}

		return false;
	}

	private boolean _isLastCharDigit() {
		if ((lastChar >= '0') && (lastChar <= '9')) {
			return true;
		}

		return false;
	}

	private Object[] _readArrayAsArrayOfObjects() {
		List<Object> objects = new ArrayList<>();

		readNextChar();

		_readWhileLastCharIsWhiteSpace();

		if (_isLastChar(']')) {
			readNextChar();

			return objects.toArray();
		}

		do {
			_readWhileLastCharIsWhiteSpace();
			objects.add(readValue());
			_readWhileLastCharIsWhiteSpace();
		}
		while (_ifLastCharMatchesThenRead(','));

		if (!_isLastChar(']')) {
			throw new IllegalArgumentException(
				"Expected ']'; got '" + lastChar + "'");
		}

		readNextChar();

		return objects.toArray();
	}

	private boolean _readFalseAsBoolean() {
		readNextChar();
		_assertLastChar('a');
		readNextChar();
		_assertLastChar('l');
		readNextChar();
		_assertLastChar('s');
		readNextChar();
		_assertLastChar('e');
		readNextChar();

		return false;
	}

	private Object _readNullAsObject() {
		readNextChar();
		_assertLastChar('u');
		readNextChar();
		_assertLastChar('l');
		readNextChar();
		_assertLastChar('l');
		readNextChar();

		return null;
	}

	private String _readNumberAsString() {
		_setCaptureStart();

		do {
			readNextChar();
		}
		while (_isLastCharDigit());

		return _getCapturedSubstring();
	}

	private String _readObjectAsJsonString() {
		_setCaptureStart();
		readNextChar();
		_readWhileLastCharIsWhiteSpace();

		if (_isLastChar('}')) {
			readNextChar();

			return "{}";
		}

		do {
			_readWhileLastCharIsWhiteSpace();
			_readString();
			_readWhileLastCharIsWhiteSpace();

			if (!_ifLastCharMatchesThenRead(':')) {
				throw new IllegalArgumentException("Expected ':'");
			}

			_readWhileLastCharIsWhiteSpace();
			readValue();
			_readWhileLastCharIsWhiteSpace();
		}
		while (_ifLastCharMatchesThenRead(','));

		_readWhileLastCharIsWhiteSpace();

		if (!_ifLastCharMatchesThenRead('}')) {
			throw new IllegalArgumentException(
				"Expected either ',' or '}'; found '" + lastChar + "'");
		}

		return _getCapturedSubstring();
	}

	private String _readString() {
		readNextChar();
		_setCaptureStart();

		while (lastChar != '"') {
			readNextChar();
		}

		String string = _getCapturedSubstring();

		readNextChar();

		return string;
	}

	private boolean _readTrueAsBoolean() {
		readNextChar();
		_assertLastChar('r');
		readNextChar();
		_assertLastChar('u');
		readNextChar();
		_assertLastChar('e');
		readNextChar();

		return true;
	}

	private void _readWhileLastCharIsWhiteSpace() {
		while ((lastChar == ' ') || (lastChar == '\t') || (lastChar == '\n') ||
			   (lastChar == '\r')) {

			readNextChar();
		}
	}

	private void _setCaptureStart() {
		captureStartStack.push(index - 1);
	}

}