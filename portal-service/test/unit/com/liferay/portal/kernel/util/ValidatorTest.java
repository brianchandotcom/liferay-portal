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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Shuyang Zhou
 * @author Shinn Lok
 * @author Igor Beslic
 * @author Manuel de la Peña
 */
@RunWith(PowerMockRunner.class)
public class ValidatorTest extends PowerMockito {

	@Test
	public void testIsFileExtension() throws Exception {
		String[] validFileExtensions = {
			"abc", ".abc", "."
		};

		testValidFileExtensions(validFileExtensions, true);
	}

	@Test
	public void testIsFileName() throws Exception {
		String[] validFileNames = {
			".asdf", "abc", "abc.def", "abc.def.xyz"
		};

		testValidFileNames(validFileNames, true);
	}

	@Test
	public void testIsFilePath() throws Exception {
		String[] validFilePaths = {
			"a", "a/a", "a\\a", "a/./a", "a\\.\\a", "a//a", "a\\\\a", "a//a//",
			"a\\a\\", "a..", "a.", ".a", "..a", "a../", "a./", ".a/", "..a/",
			".", "./", "./a", "a/.", "a/./a"
		};

		testValidFilePaths(validFilePaths, false, true);
	}

	@Test
	public void testIsFilePathWithParentDirectories() throws Exception {
		String[] validFilePathsWithParentDirectories = {
			"a", "a/a", "a\\a", "a/./a", "a\\.\\a", "a//a", "a\\\\a", "a//a//",
			"a\\a\\", "a..", "a.", ".a", "..a", "a../", "a./", ".a/", "..a/",
			".", "./", "/.", "./a", "a/.", "a/./a", "c:\\a", "/", "/a", "..a/",
			"../a", "/a/../a", "c:\\a\\..\\a"
		};

		testValidFilePaths(validFilePathsWithParentDirectories, true, true);
	}

	@Test
	public void testIsInvalidEmailAddress() throws Exception {
		String[] invalidEmailAddresses = {
			"test", "liferay.com", "@liferay.com", "test(@liferay.com",
			"test)@liferay.com", "test,@liferay.com", ".test@liferay.com",
			"test.@liferay.com", "te..st@liferay.com", "test user@liferay.com",
			"test@-liferay.com", "test@liferay"
		};

		testValidEmailAddreses(invalidEmailAddresses, false);
	}

	@Test
	public void testIsInvalidFileExtension() throws Exception {
		String[] invalidFileExtensions = {
			null, "", "\u0000", ".\u0000", "abc\u0000\u0000/", "a/b", "c\\d",
		};

		testValidFileExtensions(invalidFileExtensions, false);
	}

	@Test
	public void testIsInvalidFileName() throws Exception {
		String[] invalidFileNames = {
			null, "", "a/b.txt", "/c", "/c/", "d\\e.txt", "\\f", "\\f\\",
			"/c\\f", "/", ".", "..", "./", "../", "./..", "a\u0000",
			"a\u0000/../a"
		};

		testValidFileNames(invalidFileNames, false);
	}

	@Test
	public void testIsInvalidFilePath() throws Exception {
		String[] invalidFilePaths = {
			null, "", "..", "./..", "../a", "/../a", "\u0000","a\u0000/../a"
		};

		testValidFilePaths(invalidFilePaths, false, false);
	}

	@Test
	public void testIsInvalidFilePathWithParentDirectories() throws Exception {
		String[] invalidFilePathsWithParentDirectories = {
			null, "", "\u0000", "a\u0000/../a"
		};

		testValidFilePaths(invalidFilePathsWithParentDirectories, true, false);
	}

	@Test
	public void testIsInvalidHostName() throws Exception {
		String[] invalidHostNames = {
			"(999.999.999)", "123_456_789_012", "www.$dollar$.com",
			"{abcd:1234:ef01:2345:6789:0123:4567}"
		};

		testValidHostNames(invalidHostNames, false);
	}

	@Test
	public void testIsInvalidIPv4Address() throws Exception {
		String[] invalidIPv4Addresses = {
			"392.168.1.102", "255.0.0", "256.257.258.259", "128.0000.001.002",
			"10.10.10.1000", "0192.0168.0001.0001"
		};

		testValidIPv4Addresses(invalidIPv4Addresses, false);
	}

	@Test
	public void testIsInvalidIPv6Address() throws Exception {
		String[] invalidIPv6Addresses = {
			"", "':10.0.0.1", "02001:0000:1234:0000:0000:C1C0:ABCD:0876",
			"1.2.3.4", "1.2.3.4", "1.2.3.4:1111:2222:3333:4444::5555",
			"1.2.3.4:1111:2222:3333::5555", "1.2.3.4:1111:2222::5555",
			"1.2.3.4:1111::5555", "1.2.3.4::", "1.2.3.4::5555", "1111",
			"11112222:3333:4444:5555:6666:1.2.3.4",
			"11112222:3333:4444:5555:6666:7777:8888", "1111:", "1111:1.2.3.4",
			"1111:2222", "1111:22223333:4444:5555:6666:1.2.3.4",
			"1111:22223333:4444:5555:6666:7777:8888", "1111:2222:",
			"1111:2222:1.2.3.4", "1111:2222:3333",
			"1111:2222:33334444:5555:6666:1.2.3.4",
			"1111:2222:33334444:5555:6666:7777:8888", "1111:2222:3333:",
			"1111:2222:3333:1.2.3.4", "1111:2222:3333:4444",
			"1111:2222:3333:44445555:6666:1.2.3.4",
			"1111:2222:3333:44445555:6666:7777:8888", "1111:2222:3333:4444:",
			"1111:2222:3333:4444:1.2.3.4", "1111:2222:3333:4444:5555",
			"1111:2222:3333:4444:55556666:1.2.3.4",
			"1111:2222:3333:4444:55556666:7777:8888",
			"1111:2222:3333:4444:5555:", "1111:2222:3333:4444:5555:1.2.3.4",
			"1111:2222:3333:4444:5555:6666",
			"1111:2222:3333:4444:5555:66661.2.3.4",
			"1111:2222:3333:4444:5555:66667777:8888",
			"1111:2222:3333:4444:5555:6666:",
			"1111:2222:3333:4444:5555:6666:00.00.00.00",
			"1111:2222:3333:4444:5555:6666:000.000.000.000",
			"1111:2222:3333:4444:5555:6666:1.2.3.4.5",
			"1111:2222:3333:4444:5555:6666:255.255.255255",
			"1111:2222:3333:4444:5555:6666:255.255255.255",
			"1111:2222:3333:4444:5555:6666:255255.255.255",
			"1111:2222:3333:4444:5555:6666:256.256.256.256",
			"1111:2222:3333:4444:5555:6666:7777",
			"1111:2222:3333:4444:5555:6666:77778888",
			"1111:2222:3333:4444:5555:6666:7777:",
			"1111:2222:3333:4444:5555:6666:7777:1.2.3.4",
			"1111:2222:3333:4444:5555:6666:7777:8888:",
			"1111:2222:3333:4444:5555:6666:7777:8888:1.2.3.4",
			"1111:2222:3333:4444:5555:6666:7777:8888:9999",
			"1111:2222:3333:4444:5555:6666:7777:8888::",
			"1111:2222:3333:4444:5555:6666:7777:::",
			"1111:2222:3333:4444:5555:6666:7777:::",
			"1111:2222:3333:4444:5555:6666::1.2.3.4",
			"1111:2222:3333:4444:5555:6666::8888:",
			"1111:2222:3333:4444:5555:6666:::",
			"1111:2222:3333:4444:5555:6666:::8888",
			"1111:2222:3333:4444:5555::7777:8888:",
			"1111:2222:3333:4444:5555::7777::",
			"1111:2222:3333:4444:5555::8888:", "1111:2222:3333:4444:5555:::",
			"1111:2222:3333:4444:5555:::1.2.3.4",
			"1111:2222:3333:4444:5555:::7777:8888",
			"1111:2222:3333:4444::5555:",
			"1111:2222:3333:4444::6666:7777:8888:",
			"1111:2222:3333:4444::6666:7777::",
			"1111:2222:3333:4444::6666::8888",
			"1111:2222:3333:4444::7777:8888:", "1111:2222:3333:4444::8888:",
			"1111:2222:3333:4444:::", "1111:2222:3333:4444:::6666:1.2.3.4",
			"1111:2222:3333:4444:::6666:7777:8888", "1111:2222:3333::5555:",
			"1111:2222:3333::5555:6666:7777:8888:",
			"1111:2222:3333::5555:6666:7777::",
			"1111:2222:3333::5555:6666::8888", "1111:2222:3333::5555::1.2.3.4",
			"1111:2222:3333::5555::7777:8888",
			"1111:2222:3333::6666:7777:8888:", "1111:2222:3333::7777:8888:",
			"1111:2222:3333::8888:", "1111:2222:3333:::",
			"1111:2222:3333:::5555:6666:1.2.3.4",
			"1111:2222:3333:::5555:6666:7777:8888",
			"1111:2222::4444:5555:6666:7777:8888:",
			"1111:2222::4444:5555:6666:7777::",
			"1111:2222::4444:5555:6666::8888", "1111:2222::4444:5555::1.2.3.4",
			"1111:2222::4444:5555::7777:8888", "1111:2222::4444::6666:1.2.3.4",
			"1111:2222::4444::6666:7777:8888", "1111:2222::5555:",
			"1111:2222::5555:6666:7777:8888:", "1111:2222::6666:7777:8888:",
			"1111:2222::7777:8888:", "1111:2222::8888:", "1111:2222:::",
			"1111:2222:::4444:5555:6666:1.2.3.4",
			"1111:2222:::4444:5555:6666:7777:8888",
			"1111::3333:4444:5555:6666:7777:8888:",
			"1111::3333:4444:5555:6666:7777::",
			"1111::3333:4444:5555:6666::8888", "1111::3333:4444:5555::1.2.3.4",
			"1111::3333:4444:5555::7777:8888", "1111::3333:4444::6666:1.2.3.4",
			"1111::3333:4444::6666:7777:8888", "1111::3333::5555:6666:1.2.3.4",
			"1111::3333::5555:6666:7777:8888",
			"1111::4444:5555:6666:7777:8888:", "1111::5555:",
			"1111::5555:6666:7777:8888:", "1111::6666:7777:8888:",
			"1111::7777:8888:", "1111::8888:", "1111:::",
			"1111:::3333:4444:5555:6666:1.2.3.4",
			"1111:::3333:4444:5555:6666:7777:8888", "123", "12345::6:7:8",
			"1:2:3:4:5:6:7:8:9", "1:2:3::4:5:6:7:8:9", "1:2:3::4:5::7:8",
			"1::1.2.256.4", "1::1.2.3.256", "1::1.2.3.300", "1::1.2.3.900",
			"1::1.2.300.4", "1::1.2.900.4", "1::1.256.3.4", "1::1.300.3.4",
			"1::1.900.3.4", "1::256.2.3.4", "1::260.2.3.4", "1::2::3",
			"1::300.2.3.4", "1::300.300.300.300", "1::3000.30.30.30",
			"1::400.2.3.4", "1::5:1.2.256.4", "1::5:1.2.3.256",
			"1::5:1.2.3.300", "1::5:1.2.3.900", "1::5:1.2.300.4",
			"1::5:1.2.900.4", "1::5:1.256.3.4", "1::5:1.300.3.4",
			"1::5:1.900.3.4", "1::5:256.2.3.4", "1::5:260.2.3.4",
			"1::5:300.2.3.4", "1::5:300.300.300.300", "1::5:3000.30.30.30",
			"1::5:400.2.3.4", "1::5:900.2.3.4", "1::900.2.3.4", "1:::3:4:5",
			"2001:0000:1234: 0000:0000:C1C0:ABCD:0876",
			"2001:0000:1234:0000:00001:C1C0:ABCD:0876",
			"2001:0000:1234:0000:0000:C1C0:ABCD:0876  0",
			"2001:1:1:1:1:1:255Z255X255Y255", "2001::FFD3::57ab",
			"2001:DB8:0:0:8:800:200C:417A:221",
			"2001:db8:85a3::8a2e:37023:7334", "2001:db8:85a3::8a2e:370k:7334",
			"3ffe:0b00:0000:0001:0000:0000:000a", "3ffe:b00::1::a", ":", ":",
			":", ":1.2.3.4", ":1111:2222:3333:4444:5555:6666:1.2.3.4",
			":1111:2222:3333:4444:5555:6666:1.2.3.4",
			":1111:2222:3333:4444:5555:6666:7777:8888",
			":1111:2222:3333:4444:5555:6666:7777::",
			":1111:2222:3333:4444:5555:6666::",
			":1111:2222:3333:4444:5555:6666::8888",
			":1111:2222:3333:4444:5555::", ":1111:2222:3333:4444:5555::1.2.3.4",
			":1111:2222:3333:4444:5555::7777:8888",
			":1111:2222:3333:4444:5555::8888", ":1111:2222:3333:4444::",
			":1111:2222:3333:4444::1.2.3.4", ":1111:2222:3333:4444::5555",
			":1111:2222:3333:4444::6666:1.2.3.4",
			":1111:2222:3333:4444::6666:7777:8888",
			":1111:2222:3333:4444::7777:8888", ":1111:2222:3333:4444::8888",
			":1111:2222:3333::", ":1111:2222:3333::1.2.3.4",
			":1111:2222:3333::5555", ":1111:2222:3333::5555:6666:1.2.3.4",
			":1111:2222:3333::5555:6666:7777:8888",
			":1111:2222:3333::6666:1.2.3.4", ":1111:2222:3333::6666:7777:8888",
			":1111:2222:3333::7777:8888", ":1111:2222:3333::8888",
			":1111:2222::", ":1111:2222::1.2.3.4",
			":1111:2222::4444:5555:6666:1.2.3.4",
			":1111:2222::4444:5555:6666:7777:8888", ":1111:2222::5555",
			":1111:2222::5555:6666:1.2.3.4", ":1111:2222::5555:6666:7777:8888",
			":1111:2222::6666:1.2.3.4", ":1111:2222::6666:7777:8888",
			":1111:2222::7777:8888", ":1111:2222::8888", ":1111::",
			":1111::1.2.3.4", ":1111::3333:4444:5555:6666:1.2.3.4",
			":1111::3333:4444:5555:6666:7777:8888",
			":1111::4444:5555:6666:1.2.3.4", ":1111::4444:5555:6666:7777:8888",
			":1111::5555", ":1111::5555:6666:1.2.3.4",
			":1111::5555:6666:7777:8888", ":1111::6666:1.2.3.4",
			":1111::6666:7777:8888", ":1111::7777:8888", ":1111::8888",
			":2222:3333:4444:5555:6666:1.2.3.4",
			":2222:3333:4444:5555:6666:7777:8888",
			":3333:4444:5555:6666:1.2.3.4", ":3333:4444:5555:6666:7777:8888",
			":4444:5555:6666:1.2.3.4", ":4444:5555:6666:7777:8888",
			":5555:6666:1.2.3.4", ":5555:6666:7777:8888", ":6666:1.2.3.4",
			":6666:7777:8888", ":7777:8888", ":8888", "::.", "::..", "::...",
			"::...4", "::..3.", "::..3.4", "::.2..", "::.2.3.", "::.2.3.4",
			"::1...", "::1.2..", "::1.2.256.4", "::1.2.3.", "::1.2.3.256",
			"::1.2.3.300", "::1.2.3.900", "::1.2.300.4", "::1.2.900.4",
			"::1.256.3.4", "::1.300.3.4", "::1.900.3.4",
			"::1111:2222:3333:4444:5555:6666::",
			"::2222:3333:4444:5555:6666:7777:1.2.3.4",
			"::2222:3333:4444:5555:6666:7777:8888:",
			"::2222:3333:4444:5555:6666:7777:8888:9999",
			"::2222:3333:4444:5555:7777:8888::",
			"::2222:3333:4444:5555:7777::8888",
			"::2222:3333:4444:5555::1.2.3.4",
			"::2222:3333:4444:5555::7777:8888",
			"::2222:3333:4444::6666:1.2.3.4",
			"::2222:3333:4444::6666:7777:8888",
			"::2222:3333::5555:6666:1.2.3.4",
			"::2222:3333::5555:6666:7777:8888",
			"::2222::4444:5555:6666:1.2.3.4",
			"::2222::4444:5555:6666:7777:8888", "::256.2.3.4", "::260.2.3.4",
			"::300.2.3.4", "::300.300.300.300", "::3000.30.30.30",
			"::3333:4444:5555:6666:7777:8888:", "::400.2.3.4",
			"::4444:5555:6666:7777:8888:", "::5555:", "::5555:6666:7777:8888:",
			"::6666:7777:8888:", "::7777:8888:", "::8888:", "::900.2.3.4",
			":::", ":::", ":::", ":::", ":::1.2.3.4",
			":::2222:3333:4444:5555:6666:1.2.3.4",
			":::2222:3333:4444:5555:6666:1.2.3.4",
			":::2222:3333:4444:5555:6666:7777:8888",
			":::2222:3333:4444:5555:6666:7777:8888",
			":::3333:4444:5555:6666:7777:8888", ":::4444:5555:6666:1.2.3.4",
			":::4444:5555:6666:7777:8888", ":::5555", ":::5555:6666:1.2.3.4",
			":::5555:6666:7777:8888", ":::6666:1.2.3.4", ":::6666:7777:8888",
			":::7777:8888", ":::8888", "::ffff:192x168.1.26", "::ffff:2.3.4",
			"::ffff:257.1.2.3", "fe80:0000:0000:0000:0204:61ff:254.157.241.086",
			"FF01::101::2", "FF02:0000:0000:0000:0000:0000:0000:0000:0001",
			"ldkfj", "XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:1.2.3.4",
			"XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:XXXX",
		};

		testValidIPv6Addresses(invalidIPv6Addresses, false);
	}

	@Test
	public void testIsInvalidUri() throws Exception {
		String[] invalidUris = {
			"android-app://test.lif>ray.com", "android-app:|test(@lifray.com",
			"ios@app://liferay.com/authorize"
		};

		testValidUris(invalidUris, false);
	}

	@Test
	public void testIsInvalidUrl() throws Exception {
		String[] invalidUrls = {
			"-test://liferay.com", "gopher://liferay.com", "htt<p://lifray.com",
			"http://liferay.com:valid", "my@iosapp://liferay.com/authorize"
		};

		testValidUrl(invalidUrls, false);
	}

	@Test
	public void testIsNull() throws Exception {
		String[] nullStrings = {
			null, "", "  ", "null", " null", "null ", "  null  "
		};

		testIsNull(nullStrings, true);
	}

	@Test
	public void testIsNullInvalid() throws Exception {
		String[] notNullStrings = {
			"a", "anull", "nulla", " anull", " nulla ","  null  a"
		};

		testIsNull(notNullStrings, false);
	}

	@Test
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

		testValidEmailAddreses(validEmailAddresses, true);
	}

	@Test
	public void testIsValidHostName() throws Exception {
		String[] validHostNames = {
			"localhost", "127.0.0.1", "10.10.10.1", "abc.com", "9to5.net",
			"liferay.com", "www.liferay.com", "www.liferay.co.uk", "::1",
			"[abcd:1234:ef01:2345:6789:0123:4567]"
		};

		testValidHostNames(validHostNames, true);
	}

	@Test
	public void testIsValidIPv4Address() throws Exception {
		String[] validIPv4Addresses = {
			"192.168.1.102", "255.0.0.0", "255.255.255.255", "128.000.001.002",
			"10.10.10.1"
		};

		testValidIPv4Addresses(validIPv4Addresses, true);
	}

	@Test
	public void testIsValidIPv6Address() throws Exception {
		String[] validIPv6Addresses = {
			"2001:0000:1234:0000:0000:C1C0:ABCD:0876",
			"0000:0000:0000:0000:0000:0000:0000:0000",
			"0000:0000:0000:0000:0000:0000:0000:0001",
			"0000:0000:0000:0000:0000:0000:0000:0001", "0:0:0:0:0:0:0:0",
			"0:0:0:0:0:0:0:1", "0:0:0:0:0:0:0::", "0:0:0:0:0:0:13.1.68.3",
			"0:0:0:0:0:0::", "0:0:0:0:0::", "0:0:0:0:0:FFFF:129.144.52.38",
			"0:0:0:0::", "0:0:0::", "0:0::", "0::", "0:a:b:c:d:e:f::",
			"1111:2222:3333:4444:5555:6666:123.123.123.123",
			"1111:2222:3333:4444:5555:6666:7777:8888",
			"1111:2222:3333:4444:5555:6666:7777::",
			"1111:2222:3333:4444:5555:6666::",
			"1111:2222:3333:4444:5555:6666::8888", "1111:2222:3333:4444:5555::",
			"1111:2222:3333:4444:5555::123.123.123.123",
			"1111:2222:3333:4444:5555::7777:8888",
			"1111:2222:3333:4444:5555::8888", "1111:2222:3333:4444::",
			"1111:2222:3333:4444::123.123.123.123",
			"1111:2222:3333:4444::6666:123.123.123.123",
			"1111:2222:3333:4444::6666:7777:8888",
			"1111:2222:3333:4444::7777:8888", "1111:2222:3333:4444::8888",
			"1111:2222:3333::", "1111:2222:3333::123.123.123.123",
			"1111:2222:3333::5555:6666:123.123.123.123",
			"1111:2222:3333::5555:6666:7777:8888",
			"1111:2222:3333::6666:123.123.123.123",
			"1111:2222:3333::6666:7777:8888", "1111:2222:3333::7777:8888",
			"1111:2222:3333::8888", "1111:2222::", "1111:2222::123.123.123.123",
			"1111:2222::4444:5555:6666:123.123.123.123",
			"1111:2222::4444:5555:6666:7777:8888",
			"1111:2222::5555:6666:123.123.123.123",
			"1111:2222::5555:6666:7777:8888", "1111:2222::6666:123.123.123.123",
			"1111:2222::6666:7777:8888", "1111:2222::7777:8888",
			"1111:2222::8888", "1111::", "1111::123.123.123.123",
			"1111::3333:4444:5555:6666:123.123.123.123",
			"1111::3333:4444:5555:6666:7777:8888",
			"1111::4444:5555:6666:123.123.123.123",
			"1111::4444:5555:6666:7777:8888", "1111::5555:6666:123.123.123.123",
			"1111::5555:6666:7777:8888", "1111::6666:123.123.123.123",
			"1111::6666:7777:8888", "1111::7777:8888", "1111::8888",
			"1:2:3:4:5:6:1.2.3.4", "1:2:3:4:5:6:7:8", "1:2:3:4:5:6::",
			"1:2:3:4:5:6::8", "1:2:3:4:5::", "1:2:3:4:5::1.2.3.4",
			"1:2:3:4:5::7:8", "1:2:3:4:5::8", "1:2:3:4::", "1:2:3:4::1.2.3.4",
			"1:2:3:4::5:1.2.3.4", "1:2:3:4::7:8", "1:2:3:4::8", "1:2:3::",
			"1:2:3::1.2.3.4", "1:2:3::5:1.2.3.4", "1:2:3::7:8", "1:2:3::8",
			"1:2::", "1:2::1.2.3.4", "1:2::5:1.2.3.4", "1:2::7:8", "1:2::8",
			"1::", "1::1.2.3.4", "1::2:3", "1::2:3:4", "1::2:3:4:5",
			"1::2:3:4:5:6", "1::2:3:4:5:6:7", "1::5:1.2.3.4",
			"1::5:11.22.33.44", "1::7:8", "1::8", "1::8",
			"2001:0000:1234:0000:0000:C1C0:ABCD:0876",
			"2001:0000:1234:0000:0000:C1C0:ABCD:0876",
			"2001:0db8:0000:0000:0000:0000:1428:57ab",
			"2001:0db8:0000:0000:0000::1428:57ab",
			"2001:0db8:0:0:0:0:1428:57ab", "2001:0db8:0:0::1428:57ab",
			"2001:0db8:1234:0000:0000:0000:0000:0000", "2001:0db8:1234::",
			"2001:0db8:1234:ffff:ffff:ffff:ffff:ffff",
			"2001:0db8:85a3:0000:0000:8a2e:0370:7334", "2001:0db8::1428:57ab",
			"2001:DB8:0:0:8:800:200C:417A", "2001:db8:85a3:0:0:8a2e:370:7334",
			"2001:db8:85a3::8a2e:370:7334", "2001:db8::", "2001:db8::1428:57ab",
			"2001:DB8::8:800:200C:417A", "2001:db8:a::123", "2002::", "2::10",
			"3ffe:0b00:0000:0000:0001:0000:0000:000a", "::", "::0", "::0:0",
			"::0:0:0", "::0:0:0:0", "::0:0:0:0:0", "::0:0:0:0:0:0",
			"::0:0:0:0:0:0:0", "::0:a:b:c:d:e:f", "::1", "::123.123.123.123",
			"::13.1.68.3", "::2222:3333:4444:5555:6666:123.123.123.123",
			"::2222:3333:4444:5555:6666:7777:8888", "::2:3", "::2:3:4",
			"::2:3:4:5", "::2:3:4:5:6", "::2:3:4:5:6:7", "::2:3:4:5:6:7:8",
			"::3333:4444:5555:6666:7777:8888",
			"::4444:5555:6666:123.123.123.123", "::4444:5555:6666:7777:8888",
			"::5555:6666:123.123.123.123", "::5555:6666:7777:8888",
			"::6666:123.123.123.123", "::6666:7777:8888", "::7777:8888", "::8",
			"::8888", "::ffff:0:0", "::ffff:0c22:384e", "::ffff:12.34.56.78",
			"::FFFF:129.144.52.38", "::ffff:192.0.2.128", "::ffff:192.168.1.1",
			"::ffff:192.168.1.26", "::ffff:c000:280", "a:b:c:d:e:f:0::",
			"fe80:0000:0000:0000:0204:61ff:fe9d:f156",
			"fe80:0:0:0:204:61ff:254.157.241.86",
			"fe80:0:0:0:204:61ff:fe9d:f156", "fe80::", "fe80::", "fe80::",
			"fe80::1", "fe80::204:61ff:254.157.241.86",
			"fe80::204:61ff:fe9d:f156", "fe80::217:f2ff:254.7.237.98",
			"fe80::217:f2ff:fe07:ed62", "FF01:0:0:0:0:0:0:101", "FF01::101",
			"FF02:0000:0000:0000:0000:0000:0000:0001", "ff02::1"
		};

		testValidIPv6Addresses(validIPv6Addresses, true);
	}

	@Test
	public void testIsValidUri() throws Exception {
		String[] validUris = {
			"ftp://ftp.liferay.com/file.txt",
			"gopher://gopher.liferay.com/California/Los%20Angeles",
			"http://liferay.com:valid", "http://www.ietf.org/rfc/rfc2396.txt",
			"mailto:info@liferay.com.broken",
			"my-androidapp://liferay.com/authorize",
			"myiosapp://liferay.com/application-page/.app",
			"news:comp.infosystems.www.servers.unix", "telnet://in.liferay.com/"
		};

		testValidUris(validUris, true);
	}

	@Test
	public void testIsValidUrl() throws Exception {
		String[] validUrls = {
			"ftp://ftp.liferay.com/file.txt",
			"http://liferay.com:4444/authorize",
			"HTTP://liferay.com/Los%20Angeles",
			"http://liferay.com/web/guest/home?life=345&ray=12",
			"http://www.ietf.org/rfc/rfc1738.txt",
			"mailto:info@liferay.com.broken"
		};

		testValidUrl(validUrls, true);
	}

	protected void testIsNull(String[] strings, boolean valid) {
		if (valid) {
			for (String string : strings) {
				Assert.assertTrue(Validator.isNull(string));
			}
		}
		else {
			for (String string : strings) {
				Assert.assertFalse(Validator.isNull(string));
			}
		}
	}

	protected void testValidEmailAddreses(
		String[] emailAddresses, boolean valid) {

		if (valid) {
			for (String emailAddress : emailAddresses) {
				if (!Validator.isEmailAddress(emailAddress)) {
					Assert.fail(emailAddress);
				}
			}
		}
		else {
			for (String emailAddress : emailAddresses) {
				if (Validator.isEmailAddress(emailAddress)) {
					Assert.fail(emailAddress);
				}
			}
		}
	}

	protected void testValidFileExtensions(
		String[] fileExtensions, boolean valid) {

		if (valid) {
			for (String fileExtension : fileExtensions) {
				if (!Validator.isFileExtension(fileExtension)) {
					Assert.fail(fileExtension);
				}
			}
		}
		else {
			for (String fileExtension : fileExtensions) {
				if (Validator.isFileExtension(fileExtension)) {
					Assert.fail(fileExtension);
				}
			}
		}
	}

	protected void testValidFileNames(String[] fileNames, boolean valid) {
		if (valid) {
			for (String fileName : fileNames) {
				if (!Validator.isFileName(fileName)) {
					Assert.fail(fileName);
				}
			}
		}
		else {
			for (String fileName : fileNames) {
				if (Validator.isFileName(fileName)) {
					Assert.fail(fileName);
				}
			}
		}
	}

	protected void testValidFilePaths(
		String[] filePaths, boolean isParentDirAllowed, boolean valid) {

		if (valid) {
			for (String filePath : filePaths) {
				if (!Validator.isFilePath(filePath, isParentDirAllowed)) {
					Assert.fail(filePath);
				}
			}
		}
		else {
			for (String filePath : filePaths) {
				if (Validator.isFilePath(filePath, isParentDirAllowed)) {
					Assert.fail(filePath);
				}
			}
		}
	}

	protected void testValidHostNames(String[] hostNames, boolean valid) {
		if (valid) {
			for (String hostName : hostNames) {
				if (!Validator.isHostName(hostName)) {
					Assert.fail(hostName);
				}
			}
		}
		else {
			for (String hostName : hostNames) {
				if (Validator.isHostName(hostName)) {
					Assert.fail(hostName);
				}
			}
		}
	}

	protected void testValidIPv4Addresses(
		String[] iPv4Addresses, boolean valid) {

		if (valid) {
			for (String iPv4Address : iPv4Addresses) {
				if (!Validator.isIPv4Address(iPv4Address)) {
					Assert.fail(iPv4Address);
				}
			}
		}
		else {
			for (String iPv4Address : iPv4Addresses) {
				if (Validator.isIPv4Address(iPv4Address)) {
					Assert.fail(iPv4Address);
				}
			}
		}
	}

	protected void testValidIPv6Addresses(
		String[] iPv6Addresses, boolean valid) {

		if (valid) {
			for (String iPv6Address : iPv6Addresses) {
				if (!Validator.isIPv6Address(iPv6Address)) {
					Assert.fail(iPv6Address);
				}
			}
		}
		else {
			for (String iPv6Address : iPv6Addresses) {
				if (Validator.isIPv6Address(iPv6Address)) {
					Assert.fail(iPv6Address);
				}
			}
		}
	}

	protected void testValidUris(String[] uris, boolean valid) {
		if (valid) {
			for (String uri : uris) {
				if (!Validator.isUri(uri)) {
					Assert.fail(uri);
				}
			}
		}
		else {
			for (String uri : uris) {
				if (Validator.isUri(uri)) {
					Assert.fail(uri);
				}
			}
		}
	}

	protected void testValidUrl(String[] urls, boolean valid) {
		if (valid) {
			for (String url : urls) {
				if (!Validator.isUrl(url)) {
					Assert.fail(url);
				}
			}
		}
		else {
			for (String url : urls) {
				if (Validator.isUrl(url)) {
					Assert.fail(url);
				}
			}
		}
	}

}