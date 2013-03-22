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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.net.URL;
import java.net.URLClassLoader;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.AllPermission;
import java.security.DomainCombiner;
import java.security.Permissions;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class JavaSecurityTest {

	@Test
	public void accessController1() throws Exception {
		try {
			Permissions permissions = new Permissions();

			permissions.add(new AllPermission());

			ProtectionDomain[] protectionDomains = new ProtectionDomain[] {
				new ProtectionDomain(null, permissions)};

			AccessControlContext accessControlContext =
				new AccessControlContext(protectionDomains);

			AccessController.doPrivileged(
				new PrivilegedAction<Void>() {

					public Void run() {
						new URLClassLoader(new URL[0]);

						return null;
					}

				},
				accessControlContext
			);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void accessController2() throws Exception {
		try {
			Permissions permissions = new Permissions();

			permissions.add(new AllPermission());

			ProtectionDomain[] protectionDomains = new ProtectionDomain[] {
				new ProtectionDomain(null, permissions)};

			AccessControlContext accessControlContext =
				new AccessControlContext(protectionDomains);

			AccessController.doPrivileged(
				new PrivilegedAction<Void>() {

					public Void run() {
						Permissions permissions = new Permissions();

						permissions.add(new AllPermission());

						ProtectionDomain[] protectionDomains =
							new ProtectionDomain[] {
								new ProtectionDomain(null, permissions)};

						AccessControlContext accessControlContext =
							new AccessControlContext(protectionDomains);

						AccessController.doPrivileged(
							new PrivilegedAction<Void>() {

								public Void run() {
									new URLClassLoader(new URL[0]);

									return null;
								}

							},
							accessControlContext
						);

						return null;
					}

				},
				accessControlContext
			);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void accessController3() throws Exception {
		try {
			Permissions permissions = new Permissions();

			permissions.add(new AllPermission());

			ProtectionDomain[] protectionDomains = new ProtectionDomain[] {
				new ProtectionDomain(null, permissions)};

			AccessControlContext accessControlContext =
				new AccessControlContext(protectionDomains);

			accessControlContext = new AccessControlContext(
				accessControlContext,
				new DomainCombiner() {

					public ProtectionDomain[] combine(
						ProtectionDomain[] currentDomains,
						ProtectionDomain[] assignedDomains) {

						return assignedDomains;
					}
				}
			);

			AccessController.doPrivileged(
				new PrivilegedAction<Void>() {

					public Void run() {
						new URLClassLoader(new URL[0]);

						return null;
					}

				},
				accessControlContext
			);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void crypto1() throws Exception {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

			keyGenerator.init(128);

			SecretKey secretKey = keyGenerator.generateKey();

			Cipher cipher = Cipher.getInstance("AES");

			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			String text = "Hello World";

			cipher.doFinal(text.getBytes());
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void crypto2() throws Exception {
		try {
			Mac mac = Mac.getInstance("HmacMD5");

			String key = "123456789";

			SecretKeySpec secretKeySpec = new SecretKeySpec(
				key.getBytes(), "HmacMD5");

			mac.init(secretKeySpec);

			String text = "Hello World";

			mac.doFinal(text.getBytes());
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void fileDescriptor1() throws Exception {
		try {
			new FileInputStream(FileDescriptor.in);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void fileDescriptor2() throws Exception {
		try {
			new FileOutputStream(FileDescriptor.out);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void loadLibrary1() throws Exception {
		try {
			System.loadLibrary("test_a");

			Assert.fail();
		}
		catch (UnsatisfiedLinkError usle) {
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void loadLibrary2() throws Exception {
		try {
			System.loadLibrary("test_b");
		}
		catch (UnsatisfiedLinkError usle) {
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void policy1() throws Exception {
		try {
			Policy.getPolicy();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void policy2() throws Exception {
		try {
			Policy.setPolicy(null);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void protectionDomain1() throws Exception {
		try {
			PortalUtil.class.getProtectionDomain();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void protectionDomain2() throws Exception {
		try {
			getClass().getProtectionDomain();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void securityManager1() throws Exception {
		try {
			new SecurityManager();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}