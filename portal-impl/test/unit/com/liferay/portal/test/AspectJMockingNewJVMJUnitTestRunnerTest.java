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

package com.liferay.portal.test;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewJVMJUnitTestRunner.class)
public class AspectJMockingNewJVMJUnitTestRunnerTest {

	@Test
	public void testStaticUtil() {
		assertEquals(1, StaticUtil.getValue1());
		assertEquals(2, StaticUtil.getValue2());
	}

	@Test
	@AdviseWith(adviceClasses={AdviceClass1.class})
	public void testStaticUtilMocking1() {
		assertEquals(3, StaticUtil.getValue1());
		assertEquals(2, StaticUtil.getValue2());
	}

	@Test
	@AdviseWith(adviceClasses={AdviceClass2.class})
	public void testStaticUtilMocking2() {
		assertEquals(1, StaticUtil.getValue1());
		assertEquals(4, StaticUtil.getValue2());
	}

	@Test
	@AdviseWith(adviceClasses={AdviceClass1.class, AdviceClass2.class})
	public void testStaticUtilMocking3() {
		assertEquals(3, StaticUtil.getValue1());
		assertEquals(4, StaticUtil.getValue2());
	}

	@Test
	@AdviseWith(adviceClasses={AdviceClass3.class})
	public void testStaticUtilMocking4() {
		assertEquals(5, StaticUtil.getValue1());

		try {
			StaticUtil.getValue2();

			fail();
		}
		catch (IllegalStateException ise) {
		}
	}

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