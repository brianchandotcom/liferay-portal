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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.NewEnv;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.JVM)
public class AspectJNewEnvJVMTestRuleTest {

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testStaticUtil() {
		Assert.assertEquals(1, StaticUtil.getValue1());
		Assert.assertEquals(2, StaticUtil.getValue2());
	}

	@AdviseWith(adviceClasses = AdviceClass1.class)
	@Test
	public void testStaticUtilMocking1() {
		Assert.assertEquals(3, StaticUtil.getValue1());
		Assert.assertEquals(2, StaticUtil.getValue2());
	}

	@AdviseWith(adviceClasses = AdviceClass2.class)
	@Test
	public void testStaticUtilMocking2() {
		Assert.assertEquals(1, StaticUtil.getValue1());
		Assert.assertEquals(4, StaticUtil.getValue2());
	}

	@AdviseWith(adviceClasses = {AdviceClass1.class, AdviceClass2.class})
	@Test
	public void testStaticUtilMocking3() {
		Assert.assertEquals(3, StaticUtil.getValue1());
		Assert.assertEquals(4, StaticUtil.getValue2());
	}

	@AdviseWith(adviceClasses = AdviceClass3.class)
	@Test
	public void testStaticUtilMocking4() {
		Assert.assertEquals(5, StaticUtil.getValue1());

		try {
			StaticUtil.getValue2();

			Assert.fail();
		}
		catch (IllegalStateException ise) {
		}
	}

	@Rule
	public final AspectJNewEnvTestRule aspectJNewEnvTestRule =
		AspectJNewEnvTestRule.INSTANCE;

	@Aspect
	private static class AdviceClass1 {

		@Around("execution(* *.getValue1())")
		public Object mockGetValue() {
			return 3;
		}

	}

	@Aspect
	private static class AdviceClass2 {

		@Around("execution(* *.getValue2())")
		public Object mockGetValue() {
			return 4;
		}

	}

	@Aspect
	private static class AdviceClass3 {

		@Around("execution(* *.getValue1())")
		public Object mockGetValue1() {
			return 5;
		}

		@Around("execution(* *.getValue2())")
		public Object mockGetValue2() {
			throw new IllegalStateException();
		}

	}

	private static class StaticUtil {

		public static int getValue1() {
			return 1;
		}

		public static int getValue2() {
			return 2;
		}

	}

}