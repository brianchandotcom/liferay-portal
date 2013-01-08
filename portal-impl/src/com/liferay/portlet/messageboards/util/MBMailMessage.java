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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jorge Ferrer
 */
public class MBMailMessage {

	public void addBytes(String fileName, byte[] bytes) {
		_bytesOVPs.add(new ObjectValuePair<String, byte[]>(fileName, bytes));
	}

	public String getBody() {
		if (Validator.isNotNull(_plainBody)) {
			return GetterUtil.getString(_plainBody);
		}
		else if (Validator.isNotNull(_htmlBody)) {
			return HtmlUtil.extractText(_htmlBody);
		}
		else {
			return "-";
		}
	}

	public String getHtmlBody() {
		return _htmlBody;
	}

	public List<ObjectValuePair<String, InputStream>> getInputStreamOVPs() {
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>(
				_bytesOVPs.size());

		for (ObjectValuePair<String, byte[]> bytesOVP : _bytesOVPs) {
			String key = bytesOVP.getKey();
			byte[] bytes = bytesOVP.getValue();

			ByteArrayInputStream byteArrayInputStream =
							new ByteArrayInputStream(bytes);

			ObjectValuePair<String, InputStream> inputStreamOVP =
				new ObjectValuePair<String, InputStream>(
					key, byteArrayInputStream);

			inputStreamOVPs.add(inputStreamOVP);
		}

		return inputStreamOVPs;
	}

	public String getPlainBody() {
		return _plainBody;
	}

	public void setHtmlBody(String htmlBody) {
		_htmlBody = htmlBody;
	}

	public void setPlainBody(String plainBody) {
		if (PropsValues.MESSAGE_BOARDS_EMAIL_REMOVE_ORIGINAL_MESSAGE) {
			Matcher matcher = _QUOTE_TEXT_BEGINNING.matcher(plainBody);

			if (matcher.find()) {
				_plainBody = plainBody.substring(0, matcher.start());
				_plainBody = _plainBody.trim();
			}
			else {
				_plainBody = plainBody;
			}
		}
		else {
			_plainBody = plainBody;
		}
	}

	private static final String _BASE_SPACER_REGEX = "[\\s,/\\.\\-]";

	private static final String _DT_DAY_OF_MONTH_REGEX =
		"[0-3]?[0-9]" + _BASE_SPACER_REGEX +
			"*(?:(?:th)|(?:st)|(?:nd)|(?:rd))?";

	private static final String _DT_DAY_OF_WEEK_REGEX =
		"(?:(?:Mon(?:day)?)|(?:Tue(?:sday)?)|(?:Wed(?:nesday)?)|" +
			"(?:Thu(?:rsday)?)|(?:Fri(?:day)?)|(?:Sat(?:urday)?)|" +
				"(?:Sun(?:day)?))";

	private static final String _DT_MONTH_REGEX =
		"(?:(?:Jan(?:uary)?)|(?:Feb(?:uary)?)|(?:Mar(?:ch)?)|(?:Apr(?:il)?)|" +
			"(?:May)|(?:Jun(?:e)?)|(?:Jul(?:y)?)|(?:Aug(?:ust)?)|" +
				"(?:Sep(?:tember)?)|(?:Oct(?:ober)?)|(?:Nov(?:ember)?)|" +
					"(?:Dec(?:ember)?)|(?:[0-1]?[0-9]))";

	private static final String _DT_TIME_REGEX =
		"(?:[0-2])?[0-9]:[0-5][0-9](?::[0-5][0-9])?(?:(?:\\s)?[AP]M)?";

	private static final String _DT_YEAR_REGEX = "(?:[1-2]?[0-9])[0-9][0-9]";

	private static final String _FORMATTED_DATE_REGEX =
		"(?:" + _DT_DAY_OF_WEEK_REGEX + _BASE_SPACER_REGEX + "+)?(?:(?:" +
			_DT_DAY_OF_MONTH_REGEX + _BASE_SPACER_REGEX + "+" +
				_DT_MONTH_REGEX + ")|(?:" + _DT_MONTH_REGEX +
					_BASE_SPACER_REGEX + "+" + _DT_DAY_OF_MONTH_REGEX + "))" +
						_BASE_SPACER_REGEX + "+" + _DT_YEAR_REGEX;

	private static final String _FORMATTED_DATE_TIME_REGEX =
		"(?:" + _FORMATTED_DATE_REGEX + "[\\s,]*(?:(?:at)|(?:@))?\\s*" +
			_DT_TIME_REGEX + ")|(?:" + _DT_TIME_REGEX + "[\\s,]*(?:on)?\\s*" +
				_FORMATTED_DATE_REGEX + ")";

	private static final String _HEADER_DATE_TIME_REGEX =
		"(?:(?:date)|(?:sent)|(?:time)):\\s*("+ _FORMATTED_DATE_TIME_REGEX +
			").*\r\n";

	private static final String _HEADER_SUBJECT_ADDRESS_REGEX =
		"((?:from)|(?:subject)|(?:b?cc)|(?:to)):.*\r\n";

	private static final String _QUOTE_GMAIL_REGEX =
		"(On\\s+" + _FORMATTED_DATE_TIME_REGEX + ".*wrote:\n)";

	private static final String _QUOTE_LINE_REGEX =
		"[-,_]+\\s*(?:Original(?:\\sMessage)?)?\\s*[-,_]+\r\n\\s*";

	private static final Pattern _QUOTE_TEXT_BEGINNING =
		Pattern.compile("(?i)(?:(?:" + _QUOTE_LINE_REGEX + ")?(?:(?:" +
			_HEADER_SUBJECT_ADDRESS_REGEX + ")|(?:" + _HEADER_DATE_TIME_REGEX +
				")){2,6})|(?:" + _QUOTE_GMAIL_REGEX + ")");

	private List<ObjectValuePair<String, byte[]>> _bytesOVPs =
		new ArrayList<ObjectValuePair<String, byte[]>>();
	private String _htmlBody;
	private String _plainBody;

}