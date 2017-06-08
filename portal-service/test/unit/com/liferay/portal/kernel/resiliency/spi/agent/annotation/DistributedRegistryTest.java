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

package com.liferay.portal.kernel.resiliency.spi.agent.annotation;

import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class DistributedRegistryTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() throws Exception {
		_exactDirections = _getExactDirections();
		_postfixDirections = _getPostfixDirections();
		_prefixDirections = _getPrefixDirections();
	}

	@Test
	public void testClassRegister() {
		DistributedRegistry.registerDistributed(ChildClass.class);

		Assert.assertEquals(3, _exactDirections.size());
		Assert.assertEquals(
			Direction.REQUEST, _exactDirections.get(ChildClass.name1));
		Assert.assertEquals(
			Direction.REQUEST, _exactDirections.get(ChildClass.name6));
		Assert.assertEquals(
			Direction.REQUEST, _exactDirections.get(ChildClass.name10));

		Assert.assertEquals(3, _postfixDirections.size());
		Assert.assertEquals(
			Direction.RESPONSE, _postfixDirections.get(ChildClass.name2));
		Assert.assertEquals(
			Direction.RESPONSE, _postfixDirections.get(ChildClass.name7));
		Assert.assertEquals(
			Direction.RESPONSE, _postfixDirections.get(ChildClass.name11));

		Assert.assertEquals(3, _prefixDirections.size());
		Assert.assertEquals(
			Direction.DUPLEX, _prefixDirections.get(ChildClass.name3));
		Assert.assertEquals(
			Direction.DUPLEX, _prefixDirections.get(ChildClass.name8));
		Assert.assertEquals(
			Direction.DUPLEX, _prefixDirections.get(ChildClass.name12));

		try {
			DistributedRegistry.registerDistributed(BadInitialization.class);

			Assert.fail();
		}

		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			Assert.assertSame(
				ExceptionInInitializerError.class, throwable.getClass());

			throwable = throwable.getCause();

			Assert.assertSame(NullPointerException.class, throwable.getClass());
		}
	}

	@Test
	public void testHasDistributed() {

		// No such name

		String name = "name";

		Assert.assertFalse(
			DistributedRegistry.isDistributed(name, Direction.REQUEST));

		// Exact match name, but not match Direction

		DistributedRegistry.registerDistributed(
			name, Direction.REQUEST, MatchType.EXACT);

		Assert.assertFalse(
			DistributedRegistry.isDistributed(name, Direction.RESPONSE));

		// Exact match name, direct match Direction

		Assert.assertTrue(
			DistributedRegistry.isDistributed(name, Direction.REQUEST));

		// Exact match name, indirect match Direction

		DistributedRegistry.registerDistributed(
			name, Direction.DUPLEX, MatchType.EXACT);

		Assert.assertTrue(
			DistributedRegistry.isDistributed(name, Direction.REQUEST));

		String prefix = "prefix";

		DistributedRegistry.registerDistributed(
			prefix, Direction.REQUEST, MatchType.PREFIX);

		// Prefix dismatch name

		Assert.assertFalse(
			DistributedRegistry.isDistributed("PrefixName", Direction.REQUEST));

		// Prefix match name, but not match Direction

		Assert.assertFalse(
			DistributedRegistry.isDistributed(
				prefix + "Name", Direction.RESPONSE));

		// Prefix match name, direct match Direction

		Assert.assertTrue(
			DistributedRegistry.isDistributed(
				prefix + "Name", Direction.REQUEST));

		// Prefix match name, indirect match Direction

		DistributedRegistry.registerDistributed(
			prefix, Direction.DUPLEX, MatchType.PREFIX);

		Assert.assertTrue(
			DistributedRegistry.isDistributed(
				prefix + "Name", Direction.REQUEST));

		String postfix = "postfix";

		DistributedRegistry.registerDistributed(
			postfix, Direction.REQUEST, MatchType.POSTFIX);

		// Postfix dismatch name

		Assert.assertFalse(
			DistributedRegistry.isDistributed(
				"NamePostfix", Direction.REQUEST));

		// Postfix match name, but not match Direction

		Assert.assertFalse(
			DistributedRegistry.isDistributed(
				"name" + postfix, Direction.RESPONSE));

		// Postfix match name, direct match Direction

		Assert.assertTrue(
			DistributedRegistry.isDistributed(
				"name" + postfix, Direction.REQUEST));

		// Postfix match name, indirect match Direction

		DistributedRegistry.registerDistributed(
			postfix, Direction.DUPLEX, MatchType.POSTFIX);

		Assert.assertTrue(
			DistributedRegistry.isDistributed(
				"name" + postfix, Direction.REQUEST));
	}

	@Test
	public void testIndividualRegister() {
		DistributedRegistry.registerDistributed(
			"name1", Direction.REQUEST, MatchType.EXACT);

		Assert.assertEquals(1, _exactDirections.size());
		Assert.assertTrue(_postfixDirections.isEmpty());
		Assert.assertTrue(_prefixDirections.isEmpty());
		Assert.assertEquals(
			Direction.REQUEST, _exactDirections.remove("name1"));

		DistributedRegistry.registerDistributed(
			"name2", Direction.RESPONSE, MatchType.POSTFIX);

		Assert.assertEquals(1, _postfixDirections.size());
		Assert.assertTrue(_exactDirections.isEmpty());
		Assert.assertTrue(_prefixDirections.isEmpty());
		Assert.assertEquals(
			Direction.RESPONSE, _postfixDirections.remove("name2"));

		DistributedRegistry.registerDistributed(
			"name3", Direction.DUPLEX, MatchType.PREFIX);

		Assert.assertEquals(1, _prefixDirections.size());
		Assert.assertTrue(_exactDirections.isEmpty());
		Assert.assertTrue(_postfixDirections.isEmpty());
		Assert.assertEquals(
			Direction.DUPLEX, _prefixDirections.remove("name3"));
	}

	private static Map<String, Direction> _getExactDirections()
		throws Exception {

		Field exactDirectionsField = ReflectionUtil.getDeclaredField(
			DistributedRegistry.class, "_exactDirections");

		return (Map<String, Direction>)exactDirectionsField.get(null);
	}

	private static Map<String, Direction> _getPostfixDirections()
		throws Exception {

		Field postfixDirectionsField = ReflectionUtil.getDeclaredField(
			DistributedRegistry.class, "_postfixDirections");

		return (Map<String, Direction>)postfixDirectionsField.get(null);
	}

	private static Map<String, Direction> _getPrefixDirections()
		throws Exception {

		Field prefixDirectionsField = ReflectionUtil.getDeclaredField(
			DistributedRegistry.class, "_prefixDirections");

		return (Map<String, Direction>)prefixDirectionsField.get(null);
	}

	private Map<String, Direction> _exactDirections;
	private Map<String, Direction> _postfixDirections;
	private Map<String, Direction> _prefixDirections;

	private static class BadInitialization {

		@Distributed
		public static final String name = new String((String)null);

	}

	private static class ChildClass
		extends ParentClass implements ParentInterface {

		@Distributed(direction = Direction.REQUEST, matchType = MatchType.EXACT)
		public static final String name10 = "nam10";

		@Distributed(
			direction = Direction.RESPONSE, matchType = MatchType.POSTFIX)
		public static final String name11 = "name11";

		@Distributed(direction = Direction.DUPLEX, matchType = MatchType.PREFIX)
		public static final String name12 = "name12";

		@Distributed
		public final String name13 = "name13";

		@Distributed
		static final String name14 = "name14";
	}

	private static class ParentClass implements ParentInterface {

		@Distributed(direction = Direction.REQUEST, matchType = MatchType.EXACT)
		public static final String name6 = "name6";

		@Distributed(
			direction = Direction.RESPONSE, matchType = MatchType.POSTFIX)
		public static final String name7 = "name7";

		@Distributed(direction = Direction.DUPLEX, matchType = MatchType.PREFIX)
		public static final String name8 = "name8";

		@Distributed
		public static String name9 = "name9";
	}

	private static interface ParentInterface {

		@Distributed(direction = Direction.REQUEST, matchType = MatchType.EXACT)
		public static final String name1 = "name1";

		@Distributed(
			direction = Direction.RESPONSE, matchType = MatchType.POSTFIX)
		public static final String name2 = "name2";

		@Distributed(direction = Direction.DUPLEX, matchType = MatchType.PREFIX)
		public static final String name3 = "name3";

		public static final String name4 = "name4";

		@Distributed
		public static final Object name5 = "name5";

	}

}