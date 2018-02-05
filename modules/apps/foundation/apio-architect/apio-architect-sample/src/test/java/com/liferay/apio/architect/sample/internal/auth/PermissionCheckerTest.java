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

package com.liferay.apio.architect.sample.internal.auth;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

/**
 * @author Alejandro Hernández
 */
public class PermissionCheckerTest {

	@Test
	public void testPermissionCheckerInvalidatesIfEnvVarNotPresent() {
		PermissionChecker.setAuthorization("Apio");

		assertThat(PermissionChecker.hasPermission(), is(false));
	}

	@Test
	public void testPermissionCheckerInvalidatesIfNotValidRequest() {
		PermissionChecker.setAuthorization("Hypermedia");
		environmentVariables.set("APIO_AUTH", "Apio");

		assertThat(PermissionChecker.hasPermission(), is(false));
	}

	@Test
	public void testPermissionCheckerValidatesIfValidRequest() {
		PermissionChecker.setAuthorization("Apio");
		environmentVariables.set("APIO_AUTH", "Apio");

		assertThat(PermissionChecker.hasPermission(), is(true));
	}

	@Rule
	public final EnvironmentVariables environmentVariables =
		new EnvironmentVariables();

}