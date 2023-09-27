/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.NewEnv;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.JVM)
public class AspectJNewEnvJVMTestRuleTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
		catch (IllegalStateException illegalStateException) {
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