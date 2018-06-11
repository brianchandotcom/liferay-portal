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

package com.liferay.talend.runtime.apio.operation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Zoltán Takács
 */
public class OperationTest {

	@Test
	public void testGetExpects() {
		Operation operation = new Operation(
			"GET", _DEFAULT_ID, _DEFAULT_EXPECTS, true);

		Assert.assertThat(operation.getExpects(), equalTo(_DEFAULT_EXPECTS));
	}

	@Test
	public void testGetId() {
		Operation operation = new Operation(
			"GET", _DEFAULT_ID, _DEFAULT_EXPECTS, true);

		Assert.assertThat(operation.getId(), equalTo(_DEFAULT_ID));
	}

	@Test
	public void testGetMethod() {
		String method = "GET";

		Operation operation = new Operation(
			method, _DEFAULT_ID, _DEFAULT_EXPECTS, true);

		Assert.assertThat(operation.getMethod(), equalTo(method));
	}

	@Test
	public void testIsSingleModel() {
		String method = "GET";

		Operation operation = new Operation(
			method, _DEFAULT_ID, _DEFAULT_EXPECTS, true);

		Assert.assertThat(operation.isSingleModel(), is(true));
	}

	@Test
	public void testOperation1() {
		expectedException.expect(UnsupportedOperationException.class);
		expectedException.expectMessage("Malformed URL: httpx://example.com");

		new Operation("GET", _DEFAULT_ID, "httpx://example.com", true);
	}

	@Test
	public void testOperation2() {
		expectedException.expect(UnsupportedOperationException.class);
		expectedException.expectMessage("Unsupported operation: Update");

		new Operation("Update", _DEFAULT_ID, "http://example.com", true);
	}

	@Test
	public void testOperation3() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Method".concat(_MESSAGE));

		new Operation(null, _DEFAULT_ID, "http://example.com", true);
	}

	@Test
	public void testOperation4() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Expects".concat(_MESSAGE));

		new Operation("GET", _DEFAULT_ID, null, true);
	}

	@Test
	public void testOperation5() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("ID".concat(_MESSAGE));

		new Operation("GET", null, "http://example.com", true);
	}

	@Test
	public void testOperation6() {
		expectedException.expect(IllegalArgumentException.class);

		new Operation(null, null, null, true);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static final String _DEFAULT_EXPECTS = "http://example.com/form";

	private static final String _DEFAULT_ID = "_:people/create";

	private static final String _MESSAGE = " is NULL";

}