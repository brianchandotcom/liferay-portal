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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.security.pacl.permission.PortalFilePermission;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.test.rule.PACLTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class FilesTest {

	@ClassRule
	@Rule
	public static final PACLTestRule paclTestRule = new PACLTestRule();

	@Test
	public void testDelete1() throws Exception {
		File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

		file = new File(file, "file1");

		PortalFilePermission.checkDelete(file.getPath());
	}

	@Test
	public void testDelete2() throws Exception {
		File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

		file = new File(file, "file1");

		SecurityManager securityManager = System.getSecurityManager();

		securityManager.checkDelete(file.getPath());
	}

	@Test
	public void testDelete3() throws Exception {
		File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

		file = new File(file, "file1");

		file.delete();
	}

	@Test
	public void testDelete4() throws Exception {
		File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

		file = new File(file, "file1");

		FileUtil.delete(file);
	}

	@Test
	public void testDelete5() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			PortalFilePermission.checkDelete(file.getPath());

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testDelete6() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			SecurityManager securityManager = System.getSecurityManager();

			securityManager.checkDelete(file.getPath());

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testDelete7() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			file.delete();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testDelete8() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			FileUtil.delete(file);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testExecute1() throws Exception {
		if (OSDetector.isWindows()) {
			return;
		}

		try {
			Runtime runtime = Runtime.getRuntime();

			runtime.exec("bash");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testExecute2() throws Exception {
		if (OSDetector.isWindows()) {
			return;
		}

		Runtime runtime = Runtime.getRuntime();

		runtime.exec("/bin/bash");
	}

	@Test
	public void testExecute3() throws Exception {
		if (OSDetector.isWindows()) {
			return;
		}

		try {
			Runtime runtime = Runtime.getRuntime();

			runtime.exec("/bin/cat");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testExecute4() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			file = new File(file, "file1");

			Runtime runtime = Runtime.getRuntime();

			runtime.exec(file.getPath());

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testExecute5() throws Exception {
		if (!OSDetector.isWindows()) {
			return;
		}

		Runtime runtime = Runtime.getRuntime();

		runtime.exec("C:\\WINDOWS\\system32\\ping.exe");
	}

	@Test
	public void testExecute6() throws Exception {
		if (!OSDetector.isWindows()) {
			return;
		}

		try {
			Runtime runtime = Runtime.getRuntime();

			runtime.exec("C:\\WINDOWS\\system32\\whoami.exe");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testExecute7() throws Exception {
		if (!OSDetector.isWindows()) {
			return;
		}

		try {
			Runtime runtime = Runtime.getRuntime();

			runtime.exec("ping.exe");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testRead1() throws Exception {
		File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

		PortalFilePermission.checkRead(file.getPath());
	}

	@Test
	public void testRead2() throws Exception {
		File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

		SecurityManager securityManager = System.getSecurityManager();

		securityManager.checkRead(file.getPath());
	}

	@Test
	public void testRead3() throws Exception {
		File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

		file.canRead();
	}

	@Test
	public void testRead4() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			FileUtil.read(file.getPath());
		}
		catch (FileNotFoundException fnfe) {
		}
	}

	@Test
	public void testRead5() throws Exception {
		try {
			PortalFilePermission.checkRead(PropsValues.LIFERAY_HOME);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testRead6() throws Exception {
		try {
			SecurityManager securityManager = System.getSecurityManager();

			securityManager.checkRead(PropsValues.LIFERAY_HOME);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testRead7() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME);

			file.canRead();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testRead8() throws Exception {
		try {
			FileUtil.read(PropsValues.LIFERAY_HOME);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testRead9() throws Exception {
		File file = new File(System.getProperty("java.home"));

		file.canRead();
	}

	@Test
	public void testRead10() throws Exception {
		try {
			File file = new File(System.getProperty("java.home"), "bin");

			file.canRead();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testRead11() throws Exception {
		String javaCommand = "java";

		if (OSDetector.isWindows()) {
			javaCommand = "java.exe";
		}

		File file = new File(
			System.getProperty("java.home"),
			"bin" + File.separator + javaCommand);

		file.canRead();
	}

	@Test
	public void testRead12() throws Exception {
		try {
			String javaCommand = "javac";

			if (OSDetector.isWindows()) {
				javaCommand = "javac.exe";
			}

			File file = new File(
				System.getProperty("java.home"),
				"bin".concat(File.separator).concat(javaCommand));

			file.canRead();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testWrite1() throws Exception {
		File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

		file = new File(file, "file1");

		PortalFilePermission.checkWrite(file.getPath());
	}

	@Test
	public void testWrite2() throws Exception {
		File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

		file = new File(file, "file1");

		SecurityManager securityManager = System.getSecurityManager();

		securityManager.checkWrite(file.getPath());
	}

	@Test
	public void testWrite3() throws Exception {
		File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

		file = new File(file, "file1");

		file.canWrite();
	}

	@Test
	public void testWrite4() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			file = new File(file, "file1");

			FileUtil.write(file, new byte[0]);
		}
		catch (FileNotFoundException fnfe) {
		}
	}

	@Test
	public void testWrite5() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "file");

			PortalFilePermission.checkWrite(file.getPath());

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testWrite6() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "file");

			SecurityManager securityManager = System.getSecurityManager();

			securityManager.checkWrite(file.getPath());

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testWrite7() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "file");

			file.canWrite();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testWrite8() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "file");

			FileUtil.write(file, new byte[0]);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}