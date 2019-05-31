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

package com.liferay.talend.runtime.apio.form;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Zoltán Takács
 */
public class PropertyTest {

	@Test
	public void testGetName() {
		String name = "Test";

		Property property = new Property(name, true, true, true);

		Assert.assertThat(property.getName(), equalTo(name));
	}

	@Test
	public void testIsReadable1() {
		String name = "Test";

		Property property = new Property(name, true, true, true);

		Assert.assertThat(property.isReadable(), is(true));
	}

	@Test
	public void testIsReadable2() {
		String name = "Test";

		Property property = new Property(name, true, false, true);

		Assert.assertThat(property.isReadable(), is(false));
	}

	@Test
	public void testIsRequired1() {
		String name = "Test";

		Property property = new Property(name, true, true, true);

		Assert.assertThat(property.isRequired(), is(true));
	}

	@Test
	public void testIsRequired2() {
		String name = "Test";

		Property property = new Property(name, false, true, true);

		Assert.assertThat(property.isRequired(), is(false));
	}

	@Test
	public void testIsWriteable1() {
		String name = "Test";

		Property property = new Property(name, true, true, true);

		Assert.assertThat(property.isWriteable(), is(true));
	}

	@Test
	public void testIsWriteable2() {
		String name = "Test";

		Property property = new Property(name, true, true, false);

		Assert.assertThat(property.isWriteable(), is(false));
	}

	@Test
	public void testProperty() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Name is NULL");

		new Property(null, true, true, true);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

}