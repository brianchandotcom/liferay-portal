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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;

/**
 * @author Shuyang Zhou
 * @author Shinn Lok
 */
public class ValidatorTest extends TestCase {

	public void testIsFileExtension() throws Exception {
		String[] validFileExtensions = {
			"abc", ".abc", "."
		};

		for (String validFileExtension : validFileExtensions) {
			if (!Validator.isFileExtension(validFileExtension)) {
				fail(validFileExtension);
			}
		}

		String[] invalidFileExtensions = {
			null, "", "\u0000", ".\u0000", "abc\u0000\u0000/", "a/b", "c\\d",
		};

		for (String invalidFileExtension : invalidFileExtensions) {
			if (Validator.isFileExtension(invalidFileExtension)) {
				fail(invalidFileExtension);
			}
		}
	}

	public void testIsFileName() throws Exception {
		String[] validFileNames = {
			".asdf", "abc", "abc.def", "abc.def.xyz"
		};

		for (String validFileName : validFileNames) {
			if (!Validator.isFileName(validFileName)) {
				fail(validFileName);
			}
		}

		String[] invalidFileNames = {
			null, "", "a/b.txt", "/c", "/c/", "d\\e.txt", "\\f", "\\f\\",
			"/c\\f", "/", ".", "..", "./", "../", "./..", "a\u0000",
			"a\u0000/../a"
		};

		for (String invalidFileName : invalidFileNames) {
			if (Validator.isFileName(invalidFileName)) {
				fail(invalidFileName);
			}
		}
	}

	public void testIsFilePath() throws Exception {
		String[] validFilePaths = {
			"a", "a/a", "a\\a", "a/./a", "a\\.\\a", "a//a", "a\\\\a", "a//a//",
			"a\\a\\", "a..", "a.", ".a", "..a", "a../", "a./", ".a/", "..a/",
			".", "./", "./a", "a/.", "a/./a"
		};

		for (String validFilePath : validFilePaths) {
			if (!Validator.isFilePath(validFilePath, false)) {
				fail(validFilePath);
			}
		}

		String[] validFilePathsWithParentDirectories = {
			"a", "a/a", "a\\a", "a/./a", "a\\.\\a", "a//a", "a\\\\a", "a//a//",
			"a\\a\\", "a..", "a.", ".a", "..a", "a../", "a./", ".a/", "..a/",
			".", "./", "/.", "./a", "a/.", "a/./a", "c:\\a", "/", "/a", "..a/",
			"../a", "/a/../a", "c:\\a\\..\\a"
		};

		for (String validFilePath : validFilePathsWithParentDirectories) {
			if (!Validator.isFilePath(validFilePath, true)) {
				fail(validFilePath);
			}
		}

		String[] invalidFilePaths = {
			null, "", "..", "./..", "../a", "/../a", "\u0000","a\u0000/../a"
		};

		for (String invalidFilePath : invalidFilePaths) {
			if (Validator.isFilePath(invalidFilePath, false)) {
				fail(invalidFilePath);
			}
		}

		String[] invalidFilePathsWithParentDirectories = {
			null, "", "\u0000", "a\u0000/../a"
		};

		for (String invalidFilePath : invalidFilePathsWithParentDirectories) {
			if (Validator.isFilePath(invalidFilePath, true)) {
				fail(invalidFilePath);
			}
		}

	}

	public void testIsNull() throws Exception {
		String[] nullStrings = {
			null, "", "  ", "null", " null", "null ", "  null  "
		};

		for (String nullString : nullStrings) {
			assertTrue(Validator.isNull(nullString));
		}

		String[] notNullStrings = {
			"a", "anull", "nulla", " anull", " nulla ","  null  a"
		};

		for (String notNullString : notNullStrings) {
			assertFalse(Validator.isNull(notNullString));
		}
	}

	public void testIsValidEmailAddress() throws Exception {
		String[] validEmailAddresses = {
			"test@liferay.com", "test123@liferay.com", "test.user@liferay.com",
			"test@liferay.com.ch", "test!@liferay.com", "test#@liferay.com",
			"test$@liferay.com", "test%@liferay.com", "test&@liferay.com",
			"test'@liferay.com", "test*@liferay.com", "test+@liferay.com",
			"test-@liferay.com", "test/@liferay.com", "test=@liferay.com",
			"test?@liferay.com", "test^@liferay.com", "test_@liferay.com",
			"test`@liferay.com", "test{@liferay.com", "test|@liferay.com",
			"test{@liferay.com", "test~@liferay.com"
		};

		for (String validEmailAddress : validEmailAddresses) {
			if (!Validator.isEmailAddress(validEmailAddress)) {
				fail(validEmailAddress);
			}
		}

		String[] invalidEmailAddresses = {
			"test", "liferay.com", "@liferay.com", "test(@liferay.com",
			"test)@liferay.com", "test,@liferay.com", ".test@liferay.com",
			"test.@liferay.com", "te..st@liferay.com", "test user@liferay.com",
			"test@-liferay.com", "test@liferay"
		};

		for (String invalidEmailAddress : invalidEmailAddresses) {
			if (Validator.isEmailAddress(invalidEmailAddress)) {
				fail(invalidEmailAddress);
			}
		}
	}

	public void testIsValidHostName() throws Exception {
		String[] validHostNames = {
			"localhost", "127.0.0.1", "10.10.10.1", "abc.com", "9to5.net",
			"liferay.com", "www.liferay.com", "www.liferay.co.uk", "::1",
			"[abcd:1234:ef01:2345:6789:0123:4567]"
		};

		for (String validHostName : validHostNames) {
			if (!Validator.isHostName(validHostName)) {
				fail(validHostName);
			}
		}

		String[] invalidHostNames = {
			"(999.999.999)", "123_456_789_012", "www.$dollar$.com",
			"{abcd:1234:ef01:2345:6789:0123:4567}"
		};

		for (String invalidHostName : invalidHostNames) {
			if (Validator.isHostName(invalidHostName)) {
				fail(invalidHostName);
			}
		}
	}

}