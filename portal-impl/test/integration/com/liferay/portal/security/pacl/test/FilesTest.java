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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.security.pacl.permission.PortalFilePermission;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class FilesTest {

	@Test
	public void delete1()
		throws Exception {

		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");
			file = new File(file, "file1");

			PortalFilePermission.checkDelete(file.getPath());
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void delete2()
		throws Exception {

		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");
			file = new File(file, "file1");

			SecurityManager securityManager = System.getSecurityManager();

			securityManager.checkDelete(file.getPath());
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void delete3()
		throws Exception {

		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");
			file = new File(file, "file1");

			file.delete();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void delete4()
		throws Exception {

		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");
			file = new File(file, "file1");

			FileUtil.delete(file);
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void delete5()
		throws Exception {

		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			PortalFilePermission.checkDelete(file.getPath());

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void delete6()
		throws Exception {

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
	public void delete7() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			file.delete();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void delete8() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			FileUtil.delete(file);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void execute1() throws Exception {
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
	public void execute2() throws Exception {
		if (OSDetector.isWindows()) {
			return;
		}

		try {
			Runtime runtime = Runtime.getRuntime();

			runtime.exec("/bin/bash");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void execute3() throws Exception {
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
	public void execute4() throws Exception {
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
	public void execute5() throws Exception {
		if (!OSDetector.isWindows()) {
			return;
		}

		try {
			Runtime runtime = Runtime.getRuntime();

			runtime.exec("C:\\WINDOWS\\system32\\ping.exe");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void execute6() throws Exception {
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
	public void execute7() throws Exception {
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
	public void read1() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			PortalFilePermission.checkRead(file.getPath());
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void read2() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			SecurityManager securityManager = System.getSecurityManager();

			securityManager.checkRead(file.getPath());
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void read3() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			file.canRead();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void read4() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");

			FileUtil.read(file.getPath());
		}
		catch (FileNotFoundException fnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void read5() throws Exception {
		try {
			PortalFilePermission.checkRead(PropsValues.LIFERAY_HOME);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void read6() throws Exception {
		try {
			SecurityManager securityManager = System.getSecurityManager();

			securityManager.checkRead(PropsValues.LIFERAY_HOME);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void read7() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME);

			file.canRead();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void read8() throws Exception {
		try {
			FileUtil.read(PropsValues.LIFERAY_HOME);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void write1()
		throws Exception {

		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");
			file = new File(file, "file1");

			PortalFilePermission.checkWrite(file.getPath());
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void write2()
		throws Exception {

		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");
			file = new File(file, "file1");

			SecurityManager securityManager = System.getSecurityManager();

			securityManager.checkWrite(file.getPath());
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void write3()
		throws Exception {

		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");
			file = new File(file, "file1");

			file.canWrite();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void write4()
		throws Exception {

		try {
			File file = new File(PropsValues.LIFERAY_HOME, "pacl-test");
			file = new File(file, "file1");

			FileUtil.write(file, new byte[0]);
		}
		catch (FileNotFoundException fnfe) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void write5()
		throws Exception {

		try {
			File file = new File(PropsValues.LIFERAY_HOME, "file");

			PortalFilePermission.checkWrite(file.getPath());

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void write6()
		throws Exception {

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
	public void write7() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "file");

			file.canWrite();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void write8() throws Exception {
		try {
			File file = new File(PropsValues.LIFERAY_HOME, "file");

			FileUtil.write(file, new byte[0]);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}