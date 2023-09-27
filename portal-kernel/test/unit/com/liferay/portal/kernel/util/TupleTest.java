/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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