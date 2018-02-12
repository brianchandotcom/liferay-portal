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
		String expects = "http://example.com/form";

		Operation operation = new Operation("GET", expects, true);

		Assert.assertThat(operation.getExpects(), equalTo(expects));
	}

	@Test
	public void testGetMethod() {
		String method = "GET";

		Operation operation = new Operation(
			method, "http://example.com/form", true);

		Assert.assertThat(operation.getMethod(), equalTo(method));
	}

	@Test
	public void testOperation1() {
		expectedException.expect(UnsupportedOperationException.class);
		expectedException.expectMessage("Malformed URL: httpx://example.com");

		new Operation("GET", "httpx://example.com", true);
	}

	@Test
	public void testOperation2() {
		expectedException.expect(UnsupportedOperationException.class);
		expectedException.expectMessage("Unsupported operation: UPDATE");

		new Operation("UPDATE", "http://example.com", true);
	}

	@Test
	public void testOperation3() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("'Method'".concat(_MESSAGE));

		new Operation(null, "http://example.com", true);
	}

	@Test
	public void testOperation4() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("'Expects'".concat(_MESSAGE));

		new Operation("GET", null, true);
	}

	@Test
	public void testOperation5() {
		expectedException.expect(IllegalArgumentException.class);

		new Operation(null, null, true);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static final String _MESSAGE = " parameter must be non-null";

}