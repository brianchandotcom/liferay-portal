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

package com.liferay.portal.kernel.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides utility methods related to data validation and format checking.
 *
 * @author Brian Wing Shun Chan
 * @author Alysa Carver
 */
public class Validator {

	/**
	 * Returns <code>true</code> if the booleans are equal.
	 *
	 * @param  boolean1 the first boolean
	 * @param  boolean2 the second boolean
	 * @return <code>true</code> if the booleans are equal; <code>false</code>
	 *         otherwise
	 */
	public static boolean equals(boolean boolean1, boolean boolean2) {
		if (boolean1 == boolean2) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the bytes are equal.
	 *
	 * @param  byte1 the first byte
	 * @param  byte2 the second byte
	 * @return <code>true</code> if the bytes are equal; <code>false</code>
	 *         otherwise
	 */
	public static boolean equals(byte byte1, byte byte2) {
		if (byte1 == byte2) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the characters are equal.
	 *
	 * @param  char1 the first character
	 * @param  char2 the second character
	 * @return <code>true</code> if the characters are equal; <code>false</code>
	 *         otherwise
	 */
	public static boolean equals(char char1, char char2) {
		if (char1 == char2) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the doubles are equal.
	 *
	 * @param  double1 the first double
	 * @param  double2 the second double
	 * @return <code>true</code> if the doubles are equal; <code>false</code>
	 *         otherwise
	 */
	public static boolean equals(double double1, double double2) {
		if (Double.compare(double1, double2) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the floats are equal.
	 *
	 * @param  float1 the first float
	 * @param  float2 the second float
	 * @return <code>true</code> if the floats are equal; <code>false</code>
	 *         otherwise
	 */
	public static boolean equals(float float1, float float2) {
		if (Float.compare(float1, float2) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the integers are equal.
	 *
	 * @param  int1 the first integer
	 * @param  int2 the second integer
	 * @return <code>true</code> if the integers are equal; <code>false</code>
	 *         otherwise
	 */
	public static boolean equals(int int1, int int2) {
		if (int1 == int2) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the long integers are equal.
	 *
	 * @param  long1 the first long integer
	 * @param  long2 the second long integer
	 * @return <code>true</code> if the long integers are equal;
	 *         <code>false</code> otherwise
	 */
	public static boolean equals(long long1, long long2) {
		if (long1 == long2) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the objects are either equal, the same
	 * instance, or both <code>null</code>.
	 *
	 * @param  obj1 the first object
	 * @param  obj2 the second object
	 * @return <code>true</code> if the objects are either equal, the same
	 *         instance, or both <code>null</code>; <code>false</code> otherwise
	 */
	public static boolean equals(Object obj1, Object obj2) {
		if ((obj1 == null) && (obj2 == null)) {
			return true;
		}
		else if ((obj1 == null) || (obj2 == null)) {
			return false;
		}
		else {
			return obj1.equals(obj2);
		}
	}

	/**
	 * Returns <code>true</code> if the short integers are equal.
	 *
	 * @param  short1 the first short integer
	 * @param  short2 the second short integer
	 * @return <code>true</code> if the short integers are equal;
	 *         <code>false</code> otherwise
	 */
	public static boolean equals(short short1, short short2) {
		if (short1 == short2) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the boolean arrays are equal.
	 *
	 * @param  booleanArray1 the first boolean array
	 * @param  booleanArray2 the second boolean array
	 * @return <code>true</code> if the booleans arrays are equal; <code>false
	 *         </code>otherwise
	 */
	public static boolean equalsSorted(
		boolean[] booleanArray1, boolean[] booleanArray2) {

		Boolean[] booleanObjArray1 = ArrayUtil.toArray(booleanArray1);

		Arrays.sort(booleanObjArray1);

		Boolean[] booleanObjArray2 = ArrayUtil.toArray(booleanArray2);

		Arrays.sort(booleanObjArray2);

		return Arrays.equals(booleanObjArray1, booleanObjArray2);
	}

	/**
	 * Returns <code>true</code> if the byte arrays are equal.
	 *
	 * @param  byteArray1 the first byte array
	 * @param  byteArray2 the second byte array
	 * @return <code>true</code> if the byte arrays are equal; <code>false
	 *         </code>otherwise
	 */
	public static boolean equalsSorted(byte[] byteArray1, byte[] byteArray2) {
		byteArray1 = ArrayUtil.clone(byteArray1);

		Arrays.sort(byteArray1);

		byteArray2 = ArrayUtil.clone(byteArray2);

		Arrays.sort(byteArray2);

		return Arrays.equals(byteArray1, byteArray2);
	}

	/**
	 * Returns <code>true</code> if the char arrays are equal.
	 *
	 * @param  charArray1 the first char array
	 * @param  charArray2 the second char array
	 * @return <code>true</code> if the char arrays are equal; <code>false
	 *         </code>otherwise
	 */
	public static boolean equalsSorted(char[] charArray1, char[] charArray2) {
		charArray1 = ArrayUtil.clone(charArray1);

		Arrays.sort(charArray1);

		charArray2 = ArrayUtil.clone(charArray2);

		Arrays.sort(charArray2);

		return Arrays.equals(charArray1, charArray2);
	}

	/**
	 * Returns <code>true</code> if the double arrays are equal.
	 *
	 * @param  doubleArray1 the first double array
	 * @param  doubleArray2 the second double array
	 * @return <code>true</code> if the double arrays are equal; <code>false
	 *         </code>otherwise
	 */
	public static boolean equalsSorted(
		double[] doubleArray1, double[] doubleArray2) {

		doubleArray1 = ArrayUtil.clone(doubleArray1);

		Arrays.sort(doubleArray1);

		doubleArray2 = ArrayUtil.clone(doubleArray2);

		Arrays.sort(doubleArray2);

		return Arrays.equals(doubleArray1, doubleArray2);
	}

	/**
	 * Returns <code>true</code> if the float arrays are equal.
	 *
	 * @param  floatArray1 the first float array
	 * @param  floatArray2 the second char array
	 * @return <code>true</code> if the float arrays are equal; <code>false
	 *         </code>otherwise
	 */
	public static boolean equalsSorted(
		float[] floatArray1, float[] floatArray2) {

		floatArray1 = ArrayUtil.clone(floatArray1);

		Arrays.sort(floatArray1);

		floatArray2 = ArrayUtil.clone(floatArray2);

		Arrays.sort(floatArray2);

		return Arrays.equals(floatArray1, floatArray2);
	}

	/**
	 * Returns <code>true</code> if the int arrays are equal.
	 *
	 * @param  intArray1 the first int array
	 * @param  intArray2 the second int array
	 * @return <code>true</code> if the int arrays are equal; <code>false</code>
	 *         otherwise
	 */
	public static boolean equalsSorted(int[] intArray1, int[] intArray2) {
		intArray1 = ArrayUtil.clone(intArray1);

		Arrays.sort(intArray1);

		intArray2 = ArrayUtil.clone(intArray2);

		Arrays.sort(intArray2);

		return Arrays.equals(intArray1, intArray2);
	}

	/**
	 * Returns <code>true</code> if the long arrays are equal.
	 *
	 * @param  longArray1 the first long array
	 * @param  longArray2 the second long array
	 * @return <code>true</code> if the long arrays are equal; <code>false
	 *         </code>otherwise
	 */
	public static boolean equalsSorted(long[] longArray1, long[] longArray2) {
		longArray1 = ArrayUtil.clone(longArray1);

		Arrays.sort(longArray1);

		longArray2 = ArrayUtil.clone(longArray2);

		Arrays.sort(longArray2);

		return Arrays.equals(longArray1, longArray2);
	}

	/**
	 * Returns <code>true</code> if the object arrays are equal.
	 *
	 * @param  objArray1 the first object array
	 * @param  objArray2 the second object array
	 * @return <code>true</code> if the object arrays are equal; <code>false
	 *         </code>otherwise
	 */
	public static boolean equalsSorted(Object[] objArray1, Object[] objArray2) {
		objArray1 = ArrayUtil.clone(objArray1);

		Arrays.sort(objArray1);

		objArray2 = ArrayUtil.clone(objArray2);

		Arrays.sort(objArray2);

		return Arrays.equals(objArray1, objArray2);
	}

	/**
	 * Returns <code>true</code> if the short arrays are equal.
	 *
	 * @param  shortArray1 the first short array
	 * @param  shortArray2 the second short array
	 * @return <code>true</code> if the short arrays are equal; <code>false
	 *         </code>otherwise
	 */
	public static boolean equalsSorted(
		short[] shortArray1, short[] shortArray2) {

		shortArray1 = ArrayUtil.clone(shortArray1);

		Arrays.sort(shortArray1);

		shortArray2 = ArrayUtil.clone(shortArray2);

		Arrays.sort(shortArray2);

		return Arrays.equals(shortArray1, shortArray2);
	}

	/**
	 * Returns <code>true</code> if the string is an email address. The only
	 * requirements are that the string consist of two parts separated by an @
	 * symbol, and that it contain no whitespace.
	 *
	 * @param  address the string to check
	 * @return <code>true</code> if the string is an email address;
	 *         <code>false</code> otherwise
	 */
	public static boolean isAddress(String address) {
		if (isNull(address)) {
			return false;
		}

		String[] tokens = address.split(StringPool.AT);

		if (tokens.length != 2) {
			return false;
		}

		for (String token : tokens) {
			for (char c : token.toCharArray()) {
				if (Character.isWhitespace(c)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the string is an alphanumeric name, meaning
	 * it contains nothing but English letters, numbers, and spaces.
	 *
	 * @param  name the string to check
	 * @return <code>true</code> if the string is an Alphanumeric name;
	 *         <code>false</code> otherwise
	 */
	public static boolean isAlphanumericName(String name) {
		if (isNull(name)) {
			return false;
		}

		for (char c : name.trim().toCharArray()) {
			if (!isChar(c) && !isDigit(c) && !Character.isWhitespace(c)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the character is in the ASCII character set.
	 * This includes characters with integer values between 32 and 126
	 * (inclusive).
	 *
	 * @param  c the character to check
	 * @return <code>true</code> if the character is in the ASCII character set;
	 *         <code>false</code> otherwise
	 */
	public static boolean isAscii(char c) {
		int i = c;

		if ((i >= 32) && (i <= 126)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isBlank(String s) {
		if (s == null) {
			return true;
		}

		if (s.length() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the character is an upper or lower case
	 * English letter.
	 *
	 * @param  c the character to check
	 * @return <code>true</code> if the character is an upper or lower case
	 *         English letter; <code>false</code> otherwise
	 */
	public static boolean isChar(char c) {
		int x = c;

		if (((x >= _CHAR_LOWER_CASE_BEGIN) && (x <= _CHAR_LOWER_CASE_END)) ||
			((x >= _CHAR_UPPER_CASE_BEGIN) && (x <= _CHAR_UPPER_CASE_END))) {

			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if string consists only of upper and lower case
	 * English letters.
	 *
	 * @param  s the string to check
	 * @return <code>true</code> if the string consists only of upper and lower
	 *         case English letters
	 */
	public static boolean isChar(String s) {
		if (isNull(s)) {
			return false;
		}

		for (char c : s.toCharArray()) {
			if (!isChar(c)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the date is valid in the Gregorian calendar.
	 *
	 * @param  month the month to check
	 * @param  day the day to check
	 * @param  year the year to check
	 * @return <code>true</code> if the date is valid in the Gregorian calendar;
	 *         <code>false</code> otherwise
	 */
	public static boolean isDate(int month, int day, int year) {
		return isGregorianDate(month, day, year);
	}

	/**
	 * Returns <code>true</code> if the character is a digit between 0 and 9
	 * (inclusive).
	 *
	 * @param  c the character to check
	 * @return <code>true</code> if the character is a digit between 0 and 9
	 *         (inclusive); <code>false</code> otherwise
	 */
	public static boolean isDigit(char c) {
		int x = c;

		if ((x >= _DIGIT_BEGIN) && (x <= _DIGIT_END)) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the string consists of only digits between 0
	 * and 9 (inclusive).
	 *
	 * @param  s the string to check
	 * @return <code>true</code> if the string consists of only digits between 0
	 *         and 9 (inclusive); <code>false</code> otherwise
	 */
	public static boolean isDigit(String s) {
		if (isNull(s)) {
			return false;
		}

		for (char c : s.toCharArray()) {
			if (!isDigit(c)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the string is a valid domain name. See
	 * RFC-1034 (section 3), RFC-1123 (section 2.1), and RFC-952 (section B.
	 * Lexical grammar).
	 *
	 * @param  domainName the string to check
	 * @return <code>true</code> if the string is a valid domain name;
	 *         <code>false</code> otherwise
	 */
	public static boolean isDomain(String domainName) {

		// See RFC-1034 (section 3), RFC-1123 (section 2.1), and RFC-952
		// (section B. Lexical grammar)

		if (isNull(domainName)) {
			return false;
		}

		if (domainName.length() > 255) {
			return false;
		}

		if (domainName.startsWith(StringPool.PERIOD)) {
			return false;
		}

		String[] domainNameArray = StringUtil.split(
			domainName, CharPool.PERIOD);

		for (String domainNamePart : domainNameArray) {
			char[] domainNamePartCharArray = domainNamePart.toCharArray();

			for (int i = 0; i < domainNamePartCharArray.length; i++) {
				char c = domainNamePartCharArray[i];

				if ((i == 0) && (c == CharPool.DASH)) {
					return false;
				}

				if ((i == (domainNamePartCharArray.length - 1)) &&
					(c == CharPool.DASH)) {

					return false;
				}

				if (!isChar(c) && !isDigit(c) && (c != CharPool.DASH)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the string is a valid email address.
	 *
	 * @param  emailAddress the string to check
	 * @return <code>true</code> if the string is a valid email address;
	 *         <code>false</code> otherwise
	 */
	public static boolean isEmailAddress(String emailAddress) {
		Matcher matcher = _emailAddressPattern.matcher(emailAddress);

		return matcher.matches();
	}

	/**
	 * Returns <code>true</code> if the character is a special character in an
	 * email address.
	 *
	 * @param  c the character to check
	 * @return <code>true</code> if the character is a special character in an
	 *         email address; <code>false</code> otherwise
	 */
	public static boolean isEmailAddressSpecialChar(char c) {

		// LEP-1445

		for (char specialChar : _EMAIL_ADDRESS_SPECIAL_CHAR) {
			if (c == specialChar) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the file extension is valid.
	 *
	 * @param  fileExtension string to check
	 * @return <code>true</code> if the extension is valid; <code>false</code>
	 *         otherwise
	 */
	public static boolean isFileExtension(String fileExtension) {
		if (isNull(fileExtension) ||
			fileExtension.contains(StringPool.BACK_SLASH) ||
			fileExtension.contains(StringPool.NULL_CHAR) ||
			fileExtension.contains(StringPool.SLASH)) {

			return false;
		}

		return true;
	}

	public static boolean isFileName(String name) {
		if (isNull(name) || name.equals(StringPool.PERIOD) ||
			name.equals(StringPool.DOUBLE_PERIOD) ||
			name.contains(StringPool.BACK_SLASH) ||
			name.contains(StringPool.NULL_CHAR) ||
			name.contains(StringPool.SLASH)) {

			return false;
		}

		return true;
	}

	public static boolean isFilePath(String path, boolean isParentDirAllowed) {
		if (isNull(path)) {
			return false;
		}

		if (path.contains(StringPool.NULL_CHAR)) {
			return false;
		}

		if (isParentDirAllowed) {
			return true;
		}

		if (path.equals(StringPool.DOUBLE_PERIOD)) {
			return false;
		}

		String normalizedPath = path.replace(
			CharPool.BACK_SLASH, CharPool.SLASH);

		if (normalizedPath.startsWith(
				StringPool.DOUBLE_PERIOD.concat(StringPool.SLASH))) {

			return false;
		}

		if (normalizedPath.endsWith(
				StringPool.SLASH.concat(StringPool.DOUBLE_PERIOD))) {

			return false;
		}

		if (normalizedPath.contains(
				StringPool.SLASH.concat(
					StringPool.DOUBLE_PERIOD).concat(StringPool.SLASH))) {

			return false;
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the date is valid in the Gregorian calendar.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @return <code>true</code> if the date is valid; <code>false</code>
	 *         otherwise
	 */
	public static boolean isGregorianDate(int month, int day, int year) {
		if ((month < 0) || (month > 11)) {
			return false;
		}

		int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

		if (month == 1) {
			int febMax = 28;

			if (((year % 4) == 0) && ((year % 100) != 0) ||
				((year % 400) == 0)) {

				febMax = 29;
			}

			if ((day < 1) || (day > febMax)) {
				return false;
			}
		}
		else if ((day < 1) || (day > months[month])) {
			return false;
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the string is a hexidecimal number. At
	 * present the only requirement is that the string is not <code>null</code>;
	 * it does not actually check the format of the string.
	 *
	 * @param  s the string to check
	 * @return <code>true</code> if the string is a hexidecimal number;
	 *         <code>false</code> otherwise
	 * @see    #isNull(String)
	 */
	public static boolean isHex(String s) {
		if (isNull(s)) {
			return false;
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the string is a valid host name.
	 *
	 * @param  name the string to check
	 * @return <code>true</code> if the string is a valid host name;
	 *         <code>false</code> otherwise
	 */
	public static boolean isHostName(String name) {
		if (isNull(name)) {
			return false;
		}

		char[] nameCharArray = name.toCharArray();

		if ((nameCharArray[0] == CharPool.DASH) ||
			(nameCharArray[0] == CharPool.PERIOD) ||
			(nameCharArray[nameCharArray.length - 1] == CharPool.DASH)) {

			return false;
		}

		for (char c : nameCharArray) {
			if (!isChar(c) && !isDigit(c) && (c != CharPool.CLOSE_BRACKET) &&
				(c != CharPool.COLON) && (c != CharPool.DASH) &&
				(c != CharPool.OPEN_BRACKET) && (c != CharPool.PERIOD)) {

				return false;
			}
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the string is an HTML document. The only
	 * requirement is that it contain the opening and closing html tags.
	 *
	 * @param  s the string to check
	 * @return <code>true</code> if the string is an HTML document;
	 *         <code>false</code> otherwise
	 */
	public static boolean isHTML(String s) {
		if (isNull(s)) {
			return false;
		}

		if ((s.contains("<html>") || s.contains("<HTML>")) &&
			(s.contains("</html>") || s.contains("</HTML>"))) {

			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the string is a valid IPv4 or IPv6 IP
	 * address.
	 *
	 * @param  ipAddress the string to check
	 * @return <code>true</code> if the string is a valid IPv4 or IPv6 IP
	 *         address; <code>false</code> otherwise
	 */
	public static boolean isIPAddress(String ipAddress) {
		if (isIPv4Address(ipAddress) || isIPv6Address(ipAddress)) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the string is a valid IPv4 IP address.
	 *
	 * @param  ipAddress the string to check
	 * @return <code>true</code> if the string is a valid IPv4 IP address;
	 *         <code>false</code> otherwise
	 */
	public static boolean isIPv4Address(String ipAddress) {
		Matcher matcher = _ipv4AddressPattern.matcher(ipAddress);

		return matcher.matches();
	}

	/**
	 * Returns <code>true</code> if the string is a valid IPv6 IP address.
	 *
	 * @param  ipAddress the string to check
	 * @return <code>true</code> if the string is a valid IPv6 IP address;
	 *         <code>false</code> otherwise
	 */
	public static boolean isIPv6Address(String ipAddress) {
		if (isNull(ipAddress)) {
			return false;
		}

		if (StringUtil.startsWith(ipAddress, CharPool.OPEN_BRACKET) &&
			StringUtil.endsWith(ipAddress, CharPool.CLOSE_BRACKET)) {

			ipAddress = ipAddress.substring(1, ipAddress.length() - 1);
		}

		Matcher matcher = _ipv6AddressPattern.matcher(ipAddress);

		return matcher.matches();
	}

	/**
	 * Returns <code>true</code> if the date is valid in the Julian calendar.
	 *
	 * @param  month the month (0-based, meaning 0 for January)
	 * @param  day the day of the month
	 * @param  year the year
	 * @return <code>true</code> if the date is valid in the Julian calendar;
	 *         <code>false</code> otherwise
	 */
	public static boolean isJulianDate(int month, int day, int year) {
		if ((month < 0) || (month > 11)) {
			return false;
		}

		int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

		if (month == 1) {
			int febMax = 28;

			if ((year % 4) == 0) {
				febMax = 29;
			}

			if ((day < 1) || (day > febMax)) {
				return false;
			}
		}
		else if ((day < 1) || (day > months[month])) {
			return false;
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the string contains a valid number according
	 * to the Luhn algorithm, commonly used to validate credit card numbers.
	 *
	 * @param  number the string to check
	 * @return <code>true</code> if the string contains a valid number according
	 *         to the Luhn algorithm; <code>false</code> otherwise
	 */
	public static boolean isLUHN(String number) {
		if (number == null) {
			return false;
		}

		number = StringUtil.reverse(number);

		int total = 0;

		for (int i = 0; i < number.length(); i++) {
			int x = 0;

			if (((i + 1) % 2) == 0) {
				x = GetterUtil.getInteger(number.substring(i, i + 1)) * 2;

				if (x >= 10) {
					String s = String.valueOf(x);

					x =
						GetterUtil.getInteger(s.substring(0, 1)) +
							GetterUtil.getInteger(s.substring(1, 2));
				}
			}
			else {
				x = GetterUtil.getInteger(number.substring(i, i + 1));
			}

			total = total + x;
		}

		if ((total % 10) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the string is a name, meaning it contains
	 * nothing but English letters and spaces.
	 *
	 * @param  name the string to check
	 * @return <code>true</code> if the string is a name; <code>false</code>
	 *         otherwise
	 */
	public static boolean isName(String name) {
		if (isNull(name)) {
			return false;
		}

		for (char c : name.trim().toCharArray()) {
			if (!isChar(c) && !Character.isWhitespace(c)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the long number object is not
	 * <code>null</code>, meaning it is neither a <code>null</code> reference or
	 * zero.
	 *
	 * @param  l the long number object to check
	 * @return <code>true</code> if the long number object is not
	 *         <code>null</code>; <code>false</code> otherwise
	 */
	public static boolean isNotNull(Long l) {
		return !isNull(l);
	}

	/**
	 * Returns <code>true</code> if the object is not <code>null</code>, using
	 * the rules from {@link #isNotNull(Long)} or {@link #isNotNull(String)} if
	 * the object is one of these types.
	 *
	 * @param  obj the object to check
	 * @return <code>true</code> if the object is not <code>null</code>;
	 *         <code>false</code> otherwise
	 */
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	/**
	 * Returns <code>true</code> if the array is not <code>null</code>, meaning
	 * it is neither a <code>null</code> reference or empty.
	 *
	 * @param  array the array to check
	 * @return <code>true</code> if the array is not <code>null</code>;
	 *         <code>false</code> otherwise
	 */
	public static boolean isNotNull(Object[] array) {
		return !isNull(array);
	}

	/**
	 * Returns <code>true</code> if the string is not <code>null</code>, meaning
	 * it is not a <code>null</code> reference, nothing but spaces, or the
	 * string "<code>null</code>".
	 *
	 * @param  s the string to check
	 * @return <code>true</code> if the string is not <code>null</code>;
	 *         <code>false</code> otherwise
	 */
	public static boolean isNotNull(String s) {
		return !isNull(s);
	}

	/**
	 * Returns <code>true</code> if the long number object is <code>null</code>,
	 * meaning it is either a <code>null</code> reference or zero.
	 *
	 * @param  l the long number object to check
	 * @return <code>true</code> if the long number object is <code>null</code>;
	 *         <code>false</code> otherwise
	 */
	public static boolean isNull(Long l) {
		if ((l == null) || (l.longValue() == 0)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the object is <code>null</code>, using the
	 * rules from {@link #isNull(Long)} or {@link #isNull(String)} if the object
	 * is one of these types.
	 *
	 * @param  obj the object to check
	 * @return <code>true</code> if the object is <code>null</code>;
	 *         <code>false</code> otherwise
	 */
	public static boolean isNull(Object obj) {
		if (obj instanceof Long) {
			return isNull((Long)obj);
		}
		else if (obj instanceof String) {
			return isNull((String)obj);
		}
		else if (obj == null) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the array is <code>null</code>, meaning it
	 * is either a <code>null</code> reference or empty.
	 *
	 * @param  array the array to check
	 * @return <code>true</code> if the array is <code>null</code>;
	 *         <code>false</code> otherwise
	 */
	public static boolean isNull(Object[] array) {
		if ((array == null) || (array.length == 0)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the string is <code>null</code>, meaning it
	 * is a <code>null</code> reference, nothing but spaces, or the string
	 * "<code>null</code>".
	 *
	 * @param  s the string to check
	 * @return <code>true</code> if the string is <code>null</code>;
	 *         <code>false</code> otherwise
	 */
	public static boolean isNull(String s) {
		if (s == null) {
			return true;
		}

		int counter = 0;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == CharPool.SPACE) {
				continue;
			}
			else if (counter > 3) {
				return false;
			}

			if (counter == 0) {
				if (c != CharPool.LOWER_CASE_N) {
					return false;
				}
			}
			else if (counter == 1) {
				if (c != CharPool.LOWER_CASE_U) {
					return false;
				}
			}
			else if ((counter == 2) || (counter == 3)) {
				if (c != CharPool.LOWER_CASE_L) {
					return false;
				}
			}

			counter++;
		}

		if ((counter == 0) || (counter == 4)) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the string is a decimal integer number,
	 * meaning it contains nothing but decimal digits.
	 *
	 * @param  number the string to check
	 * @return <code>true</code> if the string is a decimal integer number;
	 *         <code>false</code> otherwise
	 */
	public static boolean isNumber(String number) {
		if (isNull(number)) {
			return false;
		}

		for (char c : number.toCharArray()) {
			if (!isDigit(c)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the string is a valid password, meaning it
	 * is at least four characters long and contains only letters and decimal
	 * digits.
	 *
	 * @param  password the string to check
	 * @return <code>true</code> if the string is a valid password;
	 *         <code>false</code> otherwise
	 */
	public static boolean isPassword(String password) {
		if (isNull(password)) {
			return false;
		}

		if (password.length() < 4) {
			return false;
		}

		for (char c : password.toCharArray()) {
			if (!isChar(c) && !isDigit(c)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns <code>true</code> if the string is a valid phone number. The only
	 * requirement is that there are decimal digits in the string; length and
	 * format are not checked.
	 *
	 * @param  phoneNumber the string to check
	 * @return <code>true</code> if the string is a valid phone number;
	 *         <code>false</code> otherwise
	 */
	public static boolean isPhoneNumber(String phoneNumber) {
		return isNumber(StringUtil.extractDigits(phoneNumber));
	}

	public static boolean isUri(String uri) {
		if (isNotNull(uri)) {
			try {
				new URI(uri);

				return true;
			}
			catch (URISyntaxException urise) {
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the string is a valid URL based on the rules
	 * in {@link java.net.URL}.
	 *
	 * @param  url the string to check
	 * @return <code>true</code> if the string is a valid URL;
	 *         <code>false</code> otherwise
	 */
	public static boolean isUrl(String url) {
		if (isNotNull(url)) {
			if (url.indexOf(CharPool.COLON) == -1) {
				return false;
			}

			try {
				new URL(url);

				return true;
			}
			catch (MalformedURLException murle) {
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the string is a valid variable name in Java.
	 *
	 * @param  variableName the string to check
	 * @return <code>true</code> if the string is a valid variable name in Java;
	 *         <code>false</code> otherwise
	 */
	public static boolean isVariableName(String variableName) {
		if (isNull(variableName)) {
			return false;
		}

		Matcher matcher = _variableNamePattern.matcher(variableName);

		if (matcher.matches()) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the string is a valid variable term, meaning
	 * it begins with "[$" and ends with "$]".
	 *
	 * @param  s the string to check
	 * @return <code>true</code> if the string is a valid variable term;
	 *         <code>false</code> otherwise
	 */
	public static boolean isVariableTerm(String s) {
		if (s.startsWith(_VARIABLE_TERM_BEGIN) &&
			s.endsWith(_VARIABLE_TERM_END)) {

			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the character is whitespace, meaning it is
	 * either the <code>null</code> character '0' or whitespace according to
	 * {@link java.lang.Character#isWhitespace(char)}.
	 *
	 * @param  c the character to check
	 * @return <code>true</code> if the character is whitespace;
	 *         <code>false</code> otherwise
	 */
	public static boolean isWhitespace(char c) {
		int i = c;

		if ((i == 0) || Character.isWhitespace(c)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if the string is an XML document. The only
	 * requirement is that it contain either the xml start tag "<?xml" or the
	 * empty document tag "<root />".
	 *
	 * @param  s the string to check
	 * @return <code>true</code> if the string is an XML document;
	 *         <code>false</code> otherwise
	 */
	public static boolean isXml(String s) {
		if (s.startsWith(_XML_BEGIN) || s.startsWith(_XML_EMPTY)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static final int _CHAR_LOWER_CASE_BEGIN = 97;

	private static final int _CHAR_LOWER_CASE_END = 122;

	private static final int _CHAR_UPPER_CASE_BEGIN = 65;

	private static final int _CHAR_UPPER_CASE_END = 90;

	private static final int _DIGIT_BEGIN = 48;

	private static final int _DIGIT_END = 57;

	private static final char[] _EMAIL_ADDRESS_SPECIAL_CHAR = new char[] {
		'.', '!', '#', '$', '%', '&', '\'', '*', '+', '-', '/', '=', '?', '^',
		'_', '`', '{', '|', '}', '~'
	};

	private static final String _LOCALHOST = "localhost";

	private static final String _VARIABLE_TERM_BEGIN = "[$";

	private static final String _VARIABLE_TERM_END = "$]";

	private static final String _XML_BEGIN = "<?xml";

	private static final String _XML_EMPTY = "<root />";

	private static Pattern _emailAddressPattern = Pattern.compile(
		"[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@" +
		"(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
	private static Pattern _ipv4AddressPattern = Pattern.compile(
		"^" +
		"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
		"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
		"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
		"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
		"$");
	private static Pattern _ipv6AddressPattern = Pattern.compile(
		"^" +
		"\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|" +
		"(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|" +
		"((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
		"(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|" +
		"(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:" +
		"((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
		"(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|" +
		"(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|" +
		"((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
		"(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|" +
		"(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|" +
		"((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
		"(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|" +
		"(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|" +
		"((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
		"(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|" +
		"(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|" +
		"((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
		"(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|" +
		"(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:" +
		"((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\." +
		"(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*" +
		"$");
	private static Pattern _variableNamePattern = Pattern.compile(
		"[_a-zA-Z]+[_a-zA-Z0-9]*");

}