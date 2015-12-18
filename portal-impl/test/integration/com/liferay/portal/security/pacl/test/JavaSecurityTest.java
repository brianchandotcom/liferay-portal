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

import com.liferay.portal.test.rule.PACLTestRule;
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
import java.security.SecurityPermission;

import java.util.concurrent.Callable;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class JavaSecurityTest {

	@ClassRule
	@Rule
	public static final PACLTestRule paclTestRule = new PACLTestRule();

	@Test
	public void testAccessController1() throws Exception {
		try {
			Permissions permissions = new Permissions();

			permissions.add(new AllPermission());

			ProtectionDomain[] protectionDomains = new ProtectionDomain[] {
				new ProtectionDomain(null, permissions)
			};

			AccessControlContext accessControlContext =
				new AccessControlContext(protectionDomains);

			AccessController.doPrivileged(
				new PrivilegedAction<Void>() {

					@Override
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
	public void testAccessController2() throws Exception {
		try {
			Permissions permissions = new Permissions();

			permissions.add(new AllPermission());

			ProtectionDomain[] protectionDomains = new ProtectionDomain[] {
				new ProtectionDomain(null, permissions)
			};

			AccessControlContext accessControlContext =
				new AccessControlContext(protectionDomains);

			AccessController.doPrivileged(
				new PrivilegedAction<Void>() {

					@Override
					public Void run() {
						Permissions permissions = new Permissions();

						permissions.add(new AllPermission());

						ProtectionDomain[] protectionDomains =
							new ProtectionDomain[] {
								new ProtectionDomain(null, permissions)
							};

						AccessControlContext accessControlContext =
							new AccessControlContext(protectionDomains);

						AccessController.doPrivileged(
							new PrivilegedAction<Void>() {

								@Override
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
	public void testAccessController3() throws Exception {
		try {
			Permissions permissions = new Permissions();

			permissions.add(new AllPermission());

			ProtectionDomain[] protectionDomains = new ProtectionDomain[] {
				new ProtectionDomain(null, permissions)
			};

			AccessControlContext accessControlContext =
				new AccessControlContext(protectionDomains);

			accessControlContext = new AccessControlContext(
				accessControlContext,
				new DomainCombiner() {

					@Override
					public ProtectionDomain[] combine(
						ProtectionDomain[] currentDomains,
						ProtectionDomain[] assignedDomains) {

						return assignedDomains;
					}

				}
			);

			AccessController.doPrivileged(
				new PrivilegedAction<Void>() {

					@Override
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
	public void testCrypto1() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

		keyGenerator.init(128);

		SecretKey secretKey = keyGenerator.generateKey();

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		String text = "Hello World";

		cipher.doFinal(text.getBytes());
	}

	@Test
	public void testCrypto2() throws Exception {
		Mac mac = Mac.getInstance("HmacMD5");

		String key = "123456789";

		SecretKeySpec secretKeySpec = new SecretKeySpec(
			key.getBytes(), "HmacMD5");

		mac.init(secretKeySpec);

		String text = "Hello World";

		mac.doFinal(text.getBytes());
	}

	@Test
	public void testFileDescriptor1() throws Exception {
		try {
			new FileInputStream(FileDescriptor.in);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testFileDescriptor2() throws Exception {
		try {
			new FileOutputStream(FileDescriptor.out);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testLoadLibrary1() throws Exception {
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
	public void testLoadLibrary2() throws Exception {
		try {
			System.loadLibrary("test_b");
		}
		catch (UnsatisfiedLinkError usle) {
		}
	}

	@Test
	public void testPolicy1() throws Exception {
		try {
			Policy.getPolicy();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testPolicy2() throws Exception {
		try {

			// Simulate the stack length required to set the policy without
			// actually setting it (in case we fail)

			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					SecurityManager sm = System.getSecurityManager();

					sm.checkPermission(new SecurityPermission("setPolicy"));

					return null;
				}

			};

			callable.call();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testProtectionDomain1() throws Exception {
		try {
			PortalUtil.class.getProtectionDomain();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testProtectionDomain2() throws Exception {
		try {
			getClass().getProtectionDomain();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testSecurityManager1() throws Exception {
		try {
			new SecurityManager();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}