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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class TupleTest {

	@ClassRule
	@Rule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testEquals() {
		Tuple tuple1 = new Tuple(_object1, _object2, _object3);

		Assert.assertEquals(tuple1, tuple1);

		Tuple tuple2 = new Tuple(_object1, _object2, _object3);

		Assert.assertEquals(tuple1, tuple2);

		tuple1 = new Tuple(_object1, null, _object3);

		Assert.assertNotEquals(tuple1, tuple2);

		tuple2 = new Tuple(_object1, null, _object3);

		Assert.assertEquals(tuple1, tuple2);

		Assert.assertNotEquals(tuple1, _object1);
	}

	@Test
	public void testGetObject() {
		Tuple tuple = new Tuple(_object1, _object2, _object3);

		Assert.assertEquals(tuple.toString(), 3, tuple.getSize());
		Assert.assertSame(_object1, tuple.getObject(0));
		Assert.assertSame(_object2, tuple.getObject(1));
		Assert.assertSame(_object3, tuple.getObject(2));
	}

	@Test
	public void testHashCode() {
		Tuple tuple1 = new Tuple(_object1, _object2, _object3);
		Tuple tuple2 = new Tuple(_object1, _object2, _object3);

		Assert.assertEquals(tuple1.hashCode(), tuple2.hashCode());

		tuple1 = new Tuple(_object1, null, _object3);

		Assert.assertNotEquals(tuple1.hashCode(), tuple2.hashCode());

		tuple2 = new Tuple(_object1, null, _object3);

		Assert.assertEquals(tuple1.hashCode(), tuple2.hashCode());
	}

	@Test
	public void testToString() {
		Tuple tuple1 = new Tuple(_object1);

		Assert.assertEquals("[Object 1]", tuple1.toString());
	}

	private static final Object _object1 = "Object 1";
	private static final Object _object2 = "Object 2";
	private static final Object _object3 = "Object 3";

}