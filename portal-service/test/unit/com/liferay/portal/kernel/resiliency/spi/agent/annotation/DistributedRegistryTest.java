/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Field;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class DistributedRegistryTest {

	@Before
	public void setUp() throws Exception {
		_exactMatchMap = _getExactMatchMap();
		_postfixMatchMap = _getPostfixMatchMap();
		_prefixMatchMap = _getPrefixMatchMap();
	}

	@Test
	public void testClassRegister() {
		DistributedRegistry.registerDistributed(ChildClass.class);

		Assert.assertEquals(3, _exactMatchMap.size());
		Assert.assertEquals(
			Direction.Request, _exactMatchMap.get(ChildClass.name1));
		Assert.assertEquals(
			Direction.Request, _exactMatchMap.get(ChildClass.name6));
		Assert.assertEquals(
			Direction.Request, _exactMatchMap.get(ChildClass.name11));

		Assert.assertEquals(3, _postfixMatchMap.size());
		Assert.assertEquals(
			Direction.Response, _postfixMatchMap.get(ChildClass.name2));
		Assert.assertEquals(
			Direction.Response, _postfixMatchMap.get(ChildClass.name7));
		Assert.assertEquals(
			Direction.Response, _postfixMatchMap.get(ChildClass.name12));

		Assert.assertEquals(3, _prefixMatchMap.size());
		Assert.assertEquals(
			Direction.Duplex, _prefixMatchMap.get(ChildClass.name3));
		Assert.assertEquals(
			Direction.Duplex, _prefixMatchMap.get(ChildClass.name8));
		Assert.assertEquals(
			Direction.Duplex, _prefixMatchMap.get(ChildClass.name13));

		try {
			DistributedRegistry.registerDistributed(BadInitialization.class);

			Assert.fail();
		}

		catch(RuntimeException re) {
			Throwable throwable = re.getCause();

			Assert.assertEquals(
				ExceptionInInitializerError.class, throwable.getClass());

			throwable = throwable.getCause();

			Assert.assertEquals(
				NullPointerException.class, throwable.getClass());
		}
	}

	@Test
	public void testHasDistributed() {

		// 1) No such name

		String name = "name";

		Assert.assertFalse(
			DistributedRegistry.hasDistributed(name, Direction.Request));

		// 2) Exact match name, but not match Direction

		DistributedRegistry.registerDistributed(
			name, Direction.Request, MatchType.Exact);

		Assert.assertFalse(
			DistributedRegistry.hasDistributed(name, Direction.Response));

		// 3) Exact match name, direct match Direction

		Assert.assertTrue(
			DistributedRegistry.hasDistributed(name, Direction.Request));

		// 4) Exact match name, indirect match Direction

		DistributedRegistry.registerDistributed(
			name, Direction.Duplex, MatchType.Exact);

		Assert.assertTrue(
			DistributedRegistry.hasDistributed(name, Direction.Request));

		String prefix = "prefix";

		DistributedRegistry.registerDistributed(
			prefix, Direction.Request, MatchType.Prefix);

		// 5) Prefix dismatch name

		Assert.assertFalse(
			DistributedRegistry.hasDistributed(
				"PrefixName", Direction.Request));

		// 6) Prefix match name, but not match Direction

		Assert.assertFalse(
			DistributedRegistry.hasDistributed(
				prefix + "Name", Direction.Response));

		// 7) Prefix match name, direct match Direction

		Assert.assertTrue(
			DistributedRegistry.hasDistributed(
				prefix + "Name", Direction.Request));

		// 8) Prefix match name, indirect match Direction

		DistributedRegistry.registerDistributed(
			prefix, Direction.Duplex, MatchType.Prefix);

		Assert.assertTrue(
			DistributedRegistry.hasDistributed(
				prefix + "Name", Direction.Request));

		String postfix = "postfix";

		DistributedRegistry.registerDistributed(
			postfix, Direction.Request, MatchType.Postfix);

		// 9) Postfix dismatch name

		Assert.assertFalse(
			DistributedRegistry.hasDistributed(
				"NamePostfix", Direction.Request));

		// 10) Postfix match name, but not match Direction

		Assert.assertFalse(
			DistributedRegistry.hasDistributed(
				"name" + postfix, Direction.Response));

		// 11) Postfix match name, direct match Direction

		Assert.assertTrue(
			DistributedRegistry.hasDistributed(
				"name" + postfix, Direction.Request));

		// 12) Postfix match name, indirect match Direction

		DistributedRegistry.registerDistributed(
			postfix, Direction.Duplex, MatchType.Postfix);

		Assert.assertTrue(
			DistributedRegistry.hasDistributed(
				"name" + postfix, Direction.Request));
	}

	@Test
	public void testIndividualRegister() {
		DistributedRegistry.registerDistributed(
			"name1", Direction.Request, MatchType.Exact);

		Assert.assertEquals(1, _exactMatchMap.size());
		Assert.assertTrue(_postfixMatchMap.isEmpty());
		Assert.assertTrue(_prefixMatchMap.isEmpty());
		Assert.assertEquals(Direction.Request, _exactMatchMap.remove("name1"));

		DistributedRegistry.registerDistributed(
			"name2", Direction.Response, MatchType.Postfix);

		Assert.assertEquals(1, _postfixMatchMap.size());
		Assert.assertTrue(_exactMatchMap.isEmpty());
		Assert.assertTrue(_prefixMatchMap.isEmpty());
		Assert.assertEquals(
			Direction.Response, _postfixMatchMap.remove("name2"));

		DistributedRegistry.registerDistributed(
			"name3", Direction.Duplex, MatchType.Prefix);

		Assert.assertEquals(1, _prefixMatchMap.size());
		Assert.assertTrue(_exactMatchMap.isEmpty());
		Assert.assertTrue(_postfixMatchMap.isEmpty());
		Assert.assertEquals(Direction.Duplex, _prefixMatchMap.remove("name3"));
	}

	private static Map<String, Direction> _getExactMatchMap() throws Exception {
		Field exactMatchMapField = ReflectionUtil.getDeclaredField(
			DistributedRegistry.class, "_exactMatchMap");

		return (Map<String, Direction>)exactMatchMapField.get(null);
	}

	private static Map<String, Direction> _getPostfixMatchMap()
		throws Exception {

		Field postfixMatchMapField = ReflectionUtil.getDeclaredField(
			DistributedRegistry.class, "_postfixMatchMap");

		return (Map<String, Direction>)postfixMatchMapField.get(null);
	}

	private static Map<String, Direction> _getPrefixMatchMap()
		throws Exception {

		Field prefixMatchMapField = ReflectionUtil.getDeclaredField(
			DistributedRegistry.class, "_prefixMatchMap");

		return (Map<String, Direction>)prefixMatchMapField.get(null);
	}

	private Map<String, Direction> _exactMatchMap;
	private Map<String, Direction> _postfixMatchMap;
	private Map<String, Direction> _prefixMatchMap;

	private static class BadInitialization {

		@Distributed
		public static final String name = new String((String)null);

	}

	private static class ChildClass
		extends ParentClass implements ParentInterface {

		@Distributed(direction = Direction.Request, matchType = MatchType.Exact)
		public static final String name11 = "nam11";

		@Distributed(
			direction = Direction.Response, matchType = MatchType.Postfix)
		public static final String name12 = "name12";

		@Distributed(direction = Direction.Duplex, matchType = MatchType.Prefix)
		public static final String name13 = "name13";

		public static final String name14 = "name14";

		@Distributed
		public final String name15 = "name15";

		@Distributed
		static final String name16 = "name16";
	}

	private static class ParentClass implements ParentInterface {

		@Distributed(direction = Direction.Request, matchType = MatchType.Exact)
		public static final String name6 = "name6";

		@Distributed(
			direction = Direction.Response, matchType = MatchType.Postfix)
		public static final String name7 = "name7";

		@Distributed(direction = Direction.Duplex, matchType = MatchType.Prefix)
		public static final String name8 = "name8";

		public static final String name9 = "name9";

		@Distributed
		public static String name10 = "name10";
	}

	private static interface ParentInterface {

		@Distributed(direction = Direction.Request, matchType = MatchType.Exact)
		public static final String name1 = "name1";

		@Distributed(
			direction = Direction.Response, matchType = MatchType.Postfix)
		public static final String name2 = "name2";

		@Distributed(direction = Direction.Duplex, matchType = MatchType.Prefix)
		public static final String name3 = "name3";

		public static final String name4 = "name4";

		@Distributed
		public static final Object name5 = "name5";

	}

}