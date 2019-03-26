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

package com.liferay.petra.reflect;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * <p>
 * <table>
 * <tr>
 * <th colspan = 3>
 * Test Classes Inherited Hierarchy
 * </th>
 * </tr>
 * <tr valign="top">
 * <td>
 * <pre>
 * &#64;Type(value = 5)
 * OriginClass {    -------->
 *   &#64;Method(value = 5)
 *   &#64;Mix(value = 5)
 *   originMethod1()
 *   originMethod2()
 * }
 * </pre></td>
 * <td>
 * <pre>
 * &#64;Mix(value = 8)
 * OriginInterface2 {  -------->
 *   &#64;Method(value = 8)
 *   originMethod2()
 * }
 * </pre></td>
 * <td>
 * <pre>
 * &#64;Type(value = 9)
 * OriginInterface1 {
 *   &#64;Method(value = 9)
 *   &#64;Mix(value = 9)
 *   originMethod1()
 * }
 * </pre></td>
 * </tr>
 * <tr valign="top">
 * <td>
 * <pre>
 *   ^
 *   |
 * </pre></td>
 * <td>
 * <pre>
 * </pre></td>
 * <td>
 * <pre>
 *   ^
 *   |
 * </pre></td>
 * </tr>
 * <tr valign="top">
 * <td>
 * <pre>
 * &#64;Mix(value = 2)
 * SuperClass {    -------->
 *   &#64;Method(value = 2)
 *   originMethod2()
 *   &#64;Method(value = 2)
 *   superMethod1()
 *   superMethod2()
 * }
 * </pre></td>
 * <td>
 * <pre>
 * &#64;Type(value = 6)
 * SuperInterface2 {  -------->
 *   &#64;Method(value = 6)
 *   &#64;Mix(value = 6)
 *   originMethod1()
 *   &#64;Method(value = 6)
 *   &#64;Mix(value = 6)
 *   superMethod2()
 * }
 * </pre></td>
 * <td>
 * <pre>
 * &#64;Mix(value = 7)
 * SuperInterface1 {
 * &#64;Method(value = 7)
 * superMethod1()
 * }
 * </pre></td>
 * </tr>
 * <tr valign="top">
 * <td>
 * <pre>
 *   ^
 *   |
 * </pre></td>
 * <td>
 * <pre>
 *   ^
 *   |
 * </pre></td>
 * <td>
 * <pre>
 * </pre></td>
 * </tr>
 * <tr valign="top">
 * <td>
 * <pre>
 * &#64;Type(value = 1)
 * TestClass {    -------->
 * &#64;Method(value = 1)
 *   &#64;Method(value = 1)
 *   &#64;Mix(value = 1)
 *   originMethod1()
 *   &#64;Method(value = 1)
 *   &#64;Mix(value = 1)
 *   superMethod2()
 *   &#64;Method(value = 1)
 *   &#64;Mix(value = 1)
 *   testMethod1()
 *   testMethod2()
 * }
 * </pre></td>
 * <td>
 * <pre>
 * &#64;Mix(value = 3)
 * TestInterface2 {  -------->
 *   &#64;Method(value = 3)
 *   superMethod1()
 *   &#64;Method(value = 3)
 *   testMethod2()
 * }
 * </pre></td>
 * <td>
 * <pre>
 * &#64;Type(value = 4)
 * TestInterface1 {
 *   &#64;Method(value = 4)
 *   &#64;Mix(value = 4)
 *   testMethod1()
 * }
 * </pre></td>
 * </tr>
 * </table>
 * </p>
 *
 * @author Shuyang Zhou
 */
public class AnnotationLocatorTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testClassListLocate() {
		_classListLocate(TestClass.class, Arrays.asList(_type(1), _mix(2)));

		_classListLocate(SuperClass.class, Arrays.asList(_mix(2), _type(5)));

		_classListLocate(
			TestInterface2.class, Arrays.asList(_mix(3), _type(4)));

		_classListLocate(TestInterface1.class, Arrays.asList(_type(4)));

		_classListLocate(OriginClass.class, Arrays.asList(_type(5), _mix(8)));

		_classListLocate(
			SuperInterface2.class, Arrays.asList(_type(6), _mix(7)));

		_classListLocate(
			SuperInterface1.class, Arrays.asList(_mix(7), _type(9)));

		_classListLocate(
			OriginInterface2.class, Arrays.asList(_mix(8), _type(9)));

		_classListLocate(OriginInterface1.class, Arrays.asList(_type(9)));
	}

	@Test
	public void testClassSingleLocate() {
		_classSingleLocate(TestClass.class, 2, 1);

		_classSingleLocate(SuperClass.class, 2, 5);

		_classSingleLocate(TestInterface2.class, 3, 4);

		_classSingleLocate(TestInterface1.class, -1, 4);

		_classSingleLocate(OriginClass.class, 8, 5);

		_classSingleLocate(SuperInterface2.class, 7, 6);

		_classSingleLocate(SuperInterface1.class, 7, 9);

		_classSingleLocate(OriginInterface2.class, 8, 9);

		_classSingleLocate(OriginInterface1.class, -1, 9);
	}

	@Test
	public void testConstructor() {
		new AnnotationLocator();
	}

	@Test
	public void testInheritedHierarchyWalking() throws Exception {
		List<Class<?>> expectedClassHierarchy = Arrays.asList(
			TestClass.class, SuperClass.class, TestInterface2.class,
			TestInterface1.class, OriginClass.class, SuperInterface2.class,
			SuperInterface1.class, OriginInterface2.class,
			OriginInterface1.class);

		List<Class<?>> actualClassHierarchy = new ArrayList<>();

		Queue<Class<?>> queue = new LinkedList<>();

		queue.offer(TestClass.class);

		Class<?> clazz = null;

		while ((clazz = queue.poll()) != null) {
			actualClassHierarchy.add(clazz);

			_QUEUE_SUPER_TYPES_METHOD.invoke(null, queue, clazz);
		}

		Assert.assertEquals(expectedClassHierarchy, actualClassHierarchy);
	}

	@Test
	public void testMethodListLocate() {
		_methodListLocate(
			TestClass.class,
			Arrays.asList(
				new Annotation[] {_method(1), _mix(1), _type(1)},
				new Annotation[] {_type(1), _method(2), _mix(2)},
				new Annotation[] {_type(1), _method(2), _mix(2)},
				new Annotation[] {_method(1), _mix(1), _type(1)},
				new Annotation[] {_method(1), _mix(1), _type(1)},
				new Annotation[] {_type(1), _method(3), _mix(3)}));

		_methodListLocate(
			SuperClass.class,
			Arrays.asList(
				new Annotation[] {_mix(2), _method(5), _type(5)},
				new Annotation[] {_method(2), _mix(2), _type(5)},
				new Annotation[] {_method(2), _mix(2), _type(6)},
				new Annotation[] {_mix(2), _method(6), _type(6)},
				new Annotation[0], new Annotation[0]));

		_methodListLocate(
			TestInterface2.class,
			Arrays.asList(
				new Annotation[] {_mix(3), _method(6), _type(6)},
				new Annotation[0],
				new Annotation[] {_method(3), _mix(3), _type(6)},
				new Annotation[] {_mix(3), _method(6), _type(6)},
				new Annotation[] {_mix(3), _method(4), _type(4)},
				new Annotation[] {_method(3), _mix(3)}));

		_methodListLocate(
			TestInterface1.class,
			Arrays.asList(
				new Annotation[0], new Annotation[0], new Annotation[0],
				new Annotation[0],
				new Annotation[] {_method(4), _mix(4), _type(4)},
				new Annotation[0]));

		_methodListLocate(
			OriginClass.class,
			Arrays.asList(
				new Annotation[] {_method(5), _mix(5), _type(5)},
				new Annotation[] {_type(5), _method(8), _mix(8)},
				new Annotation[0], new Annotation[0], new Annotation[0],
				new Annotation[0]));

		_methodListLocate(
			SuperInterface2.class,
			Arrays.asList(
				new Annotation[] {_method(6), _mix(6), _type(6)},
				new Annotation[0],
				new Annotation[] {_type(6), _method(7), _mix(7)},
				new Annotation[] {_method(6), _mix(6), _type(6)},
				new Annotation[0], new Annotation[0]));

		_methodListLocate(
			SuperInterface1.class,
			Arrays.asList(
				new Annotation[] {_mix(7), _method(9), _type(9)},
				new Annotation[0], new Annotation[] {_method(7), _mix(7)},
				new Annotation[0], new Annotation[0], new Annotation[0]));

		_methodListLocate(
			OriginInterface2.class,
			Arrays.asList(
				new Annotation[] {_mix(8), _method(9), _type(9)},
				new Annotation[] {_method(8), _mix(8)}, new Annotation[0],
				new Annotation[0], new Annotation[0], new Annotation[0]));

		_methodListLocate(
			OriginInterface1.class,
			Arrays.asList(
				new Annotation[] {_method(9), _mix(9), _type(9)},
				new Annotation[0], new Annotation[0], new Annotation[0],
				new Annotation[0], new Annotation[0]));
	}

	@Test
	public void testMethodSingleLocate() {
		_methodSingleLocate(
			TestClass.class, new int[] {1, 2, 2, 1, 1, 3},
			new int[] {1, 2, 2, 1, 1, 3}, new int[] {1, 1, 1, 1, 1, 1});

		_methodSingleLocate(
			SuperClass.class, new int[] {5, 2, 2, 6, -1, -1},
			new int[] {2, 2, 2, 2, -1, -1}, new int[] {5, 5, 6, 6, -1, -1});

		_methodSingleLocate(
			TestInterface2.class, new int[] {6, -1, 3, 6, 4, 3},
			new int[] {3, -1, 3, 3, 3, 3}, new int[] {6, -1, 6, 6, 4, -1});

		_methodSingleLocate(
			TestInterface1.class, new int[] {-1, -1, -1, -1, 4, -1},
			new int[] {-1, -1, -1, -1, 4, -1},
			new int[] {-1, -1, -1, -1, 4, -1});

		_methodSingleLocate(
			OriginClass.class, new int[] {5, 8, -1, -1, -1, -1},
			new int[] {5, 8, -1, -1, -1, -1}, new int[] {5, 5, -1, -1, -1, -1});

		_methodSingleLocate(
			SuperInterface2.class, new int[] {6, -1, 7, 6, -1, -1},
			new int[] {6, -1, 7, 6, -1, -1}, new int[] {6, -1, 6, 6, -1, -1});

		_methodSingleLocate(
			SuperInterface1.class, new int[] {9, -1, 7, -1, -1, -1},
			new int[] {7, -1, 7, -1, -1, -1},
			new int[] {9, -1, -1, -1, -1, -1});

		_methodSingleLocate(
			OriginInterface2.class, new int[] {9, 8, -1, -1, -1, -1},
			new int[] {8, 8, -1, -1, -1, -1},
			new int[] {9, -1, -1, -1, -1, -1});

		_methodSingleLocate(
			OriginInterface1.class, new int[] {9, -1, -1, -1, -1, -1},
			new int[] {9, -1, -1, -1, -1, -1},
			new int[] {9, -1, -1, -1, -1, -1});
	}

	private void _classListLocate(
		Class<?> clazz, List<? extends Annotation> expectedAnnotations) {

		List<Annotation> actualAnnotations = AnnotationLocator.locate(clazz);

		Assert.assertEquals(
			clazz.getName(), expectedAnnotations.size(),
			actualAnnotations.size());

		for (int i = 0; i < expectedAnnotations.size(); i++) {
			Annotation expectedAnnotation = expectedAnnotations.get(i);
			Annotation actualAnnotation = actualAnnotations.get(i);

			Assert.assertEquals(
				clazz.getName(), expectedAnnotation.annotationType(),
				actualAnnotation.annotationType());

			if (expectedAnnotation.annotationType() == Mix.class) {
				Mix expectedMix = (Mix)expectedAnnotation;
				Mix actualMix = (Mix)actualAnnotation;

				Assert.assertEquals(
					"@Mix : " + clazz.getName(), expectedMix.value(),
					actualMix.value());
			}
			else {
				Type expectedType = (Type)expectedAnnotation;
				Type actualType = (Type)actualAnnotation;

				Assert.assertEquals(
					"@Type : ", expectedType.value(), actualType.value());
			}
		}
	}

	private void _classSingleLocate(
		Class<?> clazz, int expectedMixValue, int expectedTypeValue) {

		Mix actualMix = AnnotationLocator.locate(clazz, Mix.class);

		if (expectedMixValue == -1) {
			Assert.assertNull("@Mix : " + clazz.getName(), actualMix);
		}
		else {
			Assert.assertEquals(
				"@Mix : " + clazz.getName(), expectedMixValue,
				actualMix.value());
		}

		Type actualType = AnnotationLocator.locate(clazz, Type.class);

		if (expectedTypeValue == -1) {
			Assert.assertNull("@Type : " + clazz.getName(), actualType);
		}
		else {
			Assert.assertEquals(
				"@Type : " + clazz.getName(), expectedTypeValue,
				actualType.value());
		}
	}

	private Method _method(final int value) {
		return new Method() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return Method.class;
			}

			@Override
			public int value() {
				return value;
			}

		};
	}

	private void _methodListLocate(
		Class<?> clazz, List<Annotation[]> expectedAnnotationsList) {

		for (int i = 0; i < _interfaceMethods.length; i++) {
			Annotation[] expectedAnnotations = expectedAnnotationsList.get(i);

			java.lang.reflect.Method method = _interfaceMethods[i];

			List<Annotation> actualAnnotations = AnnotationLocator.locate(
				method, clazz);

			Assert.assertEquals(
				method.getName() + "()@" + clazz.getName(),
				expectedAnnotations.length, actualAnnotations.size());

			for (int j = 0; j < expectedAnnotations.length; j++) {
				Annotation expectedAnnotation = expectedAnnotations[j];
				Annotation actualAnnotation = actualAnnotations.get(j);

				Assert.assertEquals(
					method.getName() + "()@" + clazz.getName(),
					expectedAnnotation.annotationType(),
					actualAnnotation.annotationType());

				if (expectedAnnotation.annotationType() == Mix.class) {
					Mix expectedMix = (Mix)expectedAnnotation;
					Mix actualMix = (Mix)actualAnnotation;

					Assert.assertEquals(
						StringBundler.concat(
							"@Mix : ", method.getName(), "()@",
							clazz.getName()),
						expectedMix.value(), actualMix.value());
				}
				else if (expectedAnnotation.annotationType() == Method.class) {
					Method expectedType = (Method)expectedAnnotation;
					Method actualMethod = (Method)actualAnnotation;

					Assert.assertEquals(
						StringBundler.concat(
							"@Method : ", method.getName(), "()@",
							clazz.getName()),
						expectedType.value(), actualMethod.value());
				}
				else {
					Type expectedType = (Type)expectedAnnotation;
					Type actualType = (Type)actualAnnotation;

					Assert.assertEquals(
						StringBundler.concat(
							"@Type : ", method.getName(), "()@",
							clazz.getName()),
						expectedType.value(), actualType.value());
				}
			}

			try {
				method = clazz.getDeclaredMethod(
					method.getName(), method.getParameterTypes());

				actualAnnotations = AnnotationLocator.locate(method, null);

				Assert.assertEquals(
					method.getName() + "()@" + clazz.getName(),
					expectedAnnotations.length, actualAnnotations.size());

				for (int j = 0; j < expectedAnnotations.length; j++) {
					Annotation expectedAnnotation = expectedAnnotations[j];
					Annotation actualAnnotation = actualAnnotations.get(j);

					Assert.assertEquals(
						method.getName() + "()@" + clazz.getName(),
						expectedAnnotation.annotationType(),
						actualAnnotation.annotationType());

					if (expectedAnnotation.annotationType() == Mix.class) {
						Mix expectedMix = (Mix)expectedAnnotation;
						Mix actualMix = (Mix)actualAnnotation;

						Assert.assertEquals(
							StringBundler.concat(
								"@Mix : ", method.getName(), "()@",
								clazz.getName()),
							expectedMix.value(), actualMix.value());
					}
					else if (expectedAnnotation.annotationType() ==
								Method.class) {

						Method expectedType = (Method)expectedAnnotation;
						Method actualMethod = (Method)actualAnnotation;

						Assert.assertEquals(
							StringBundler.concat(
								"@Method : ", method.getName(), "()@",
								clazz.getName()),
							expectedType.value(), actualMethod.value());
					}
					else {
						Type expectedType = (Type)expectedAnnotation;
						Type actualType = (Type)actualAnnotation;

						Assert.assertEquals(
							StringBundler.concat(
								"@Type : ", method.getName(), "()@",
								clazz.getName()),
							expectedType.value(), actualType.value());
					}
				}
			}
			catch (Exception e) {
			}
		}
	}

	private void _methodSingleLocate(
		Class<?> clazz, int[] expectedMethodValues, int[] expectedMixValues,
		int[] expectedTypeValues) {

		for (int i = 0; i < 6; i++) {
			java.lang.reflect.Method method = _interfaceMethods[i];
			int expectedMethodValue = expectedMethodValues[i];

			Method methodAnnotation = AnnotationLocator.locate(
				method, clazz, Method.class);

			if (methodAnnotation == null) {
				Assert.assertEquals(
					"@Method : " + clazz.getName(), -1, expectedMethodValue);

				continue;
			}

			Assert.assertEquals(
				StringBundler.concat(
					"@Method : ", method.getName(), "()@", clazz.getName()),
				expectedMethodValue, methodAnnotation.value());

			try {
				method = clazz.getDeclaredMethod(
					method.getName(), method.getParameterTypes());

				methodAnnotation = AnnotationLocator.locate(
					method, null, Method.class);

				Assert.assertEquals(
					method.getName() + "()@" + clazz.getName(),
					expectedMethodValue, methodAnnotation.value());
			}
			catch (Exception e) {
			}
		}

		for (int i = 0; i < 6; i++) {
			java.lang.reflect.Method method = _interfaceMethods[i];
			int expectedMixValue = expectedMixValues[i];

			Mix mixAnnotation = AnnotationLocator.locate(
				method, clazz, Mix.class);

			if (mixAnnotation == null) {
				Assert.assertEquals(
					"@Mix : " + clazz.getName(), -1, expectedMixValue);

				continue;
			}

			Assert.assertEquals(
				StringBundler.concat(
					"@Mix : ", method.getName(), "()@", clazz.getName()),
				expectedMixValue, mixAnnotation.value());

			try {
				method = clazz.getDeclaredMethod(
					method.getName(), method.getParameterTypes());

				mixAnnotation = AnnotationLocator.locate(
					method, null, Mix.class);

				Assert.assertEquals(
					method.getName() + "()@" + clazz.getName(),
					expectedMixValue, mixAnnotation.value());
			}
			catch (Exception e) {
			}
		}

		for (int i = 0; i < 6; i++) {
			java.lang.reflect.Method method = _interfaceMethods[i];
			int expectedTypeValue = expectedTypeValues[i];

			Type typeAnnotation = AnnotationLocator.locate(
				method, clazz, Type.class);

			if (typeAnnotation == null) {
				Assert.assertEquals(
					"@Type : " + clazz.getName(), -1, expectedTypeValue);

				continue;
			}

			Assert.assertEquals(
				StringBundler.concat(
					"@Type : ", method.getName(), "()@", clazz.getName()),
				expectedTypeValue, typeAnnotation.value());

			try {
				method = clazz.getDeclaredMethod(
					method.getName(), method.getParameterTypes());

				typeAnnotation = AnnotationLocator.locate(
					method, null, Type.class);

				Assert.assertEquals(
					method.getName() + "()@" + clazz.getName(),
					expectedTypeValue, typeAnnotation.value());
			}
			catch (Exception e) {
			}
		}
	}

	private Mix _mix(final int value) {
		return new Mix() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return Mix.class;
			}

			@Override
			public int value() {
				return value;
			}

		};
	}

	private Type _type(final int value) {
		return new Type() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return Type.class;
			}

			@Override
			public int value() {
				return value;
			}

		};
	}

	private static final java.lang.reflect.Method _QUEUE_SUPER_TYPES_METHOD;

	private static final java.lang.reflect.Method[] _interfaceMethods;

	static {
		try {
			_interfaceMethods = new java.lang.reflect.Method[6];

			_interfaceMethods[0] = OriginInterface1.class.getDeclaredMethod(
				"originMethod1");
			_interfaceMethods[1] = OriginInterface2.class.getDeclaredMethod(
				"originMethod2");
			_interfaceMethods[2] = SuperInterface1.class.getDeclaredMethod(
				"superMethod1");
			_interfaceMethods[3] = SuperInterface2.class.getDeclaredMethod(
				"superMethod2");
			_interfaceMethods[4] = TestInterface1.class.getDeclaredMethod(
				"testMethod1");
			_interfaceMethods[5] = TestInterface2.class.getDeclaredMethod(
				"testMethod2");

			java.lang.reflect.Method queueSuperTypesMethod =
				AnnotationLocator.class.getDeclaredMethod(
					"_queueSuperTypes", Queue.class, Class.class);

			queueSuperTypesMethod.setAccessible(true);

			_QUEUE_SUPER_TYPES_METHOD = queueSuperTypesMethod;
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	@Type(value = 5)
	private static class OriginClass
		implements OriginInterface2, OriginInterface1 {

		@Method(value = 5)
		@Mix(value = 5)
		@Override
		public void originMethod1() {
		}

		@Override
		public void originMethod2() {
		}

	}

	@Mix(value = 2)
	private static class SuperClass
		extends OriginClass implements SuperInterface2, SuperInterface1 {

		@Method(value = 2)
		@Override
		public void originMethod2() {
		}

		@Method(value = 2)
		@Override
		public void superMethod1() {
		}

		@Override
		public void superMethod2() {
		}

	}

	@Type(value = 1)
	private static class TestClass
		extends SuperClass implements TestInterface2, TestInterface1 {

		@Method(value = 1)
		@Mix(value = 1)
		@Override
		public void originMethod1() {
		}

		@Method(value = 1)
		@Mix(value = 1)
		@Override
		public void superMethod2() {
		}

		@Method(value = 1)
		@Mix(value = 1)
		@Override
		public void testMethod1() {
		}

		@Override
		public void testMethod2() {
		}

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	private @interface Method {

		public int value();

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.TYPE})
	private @interface Mix {

		public int value();

	}

	@Type(value = 9)
	private interface OriginInterface1 {

		@Method(value = 9)
		@Mix(value = 9)
		public void originMethod1();

	}

	@Mix(value = 8)
	private interface OriginInterface2 extends OriginInterface1 {

		@Method(value = 8)
		public void originMethod2();

	}

	@Mix(value = 7)
	private interface SuperInterface1 extends OriginInterface1 {

		@Method(value = 7)
		void superMethod1();

	}

	@Type(value = 6)
	private interface SuperInterface2 extends SuperInterface1 {

		@Method(value = 6)
		@Mix(value = 6)
		@Override
		void originMethod1();

		@Method(value = 6)
		@Mix(value = 6)
		void superMethod2();

	}

	@Type(value = 4)
	private interface TestInterface1 {

		@Method(value = 4)
		@Mix(value = 4)
		public void testMethod1();

	}

	@Mix(value = 3)
	private interface TestInterface2 extends TestInterface1, SuperInterface2 {

		@Method(value = 3)
		@Override
		public void superMethod1();

		@Method(value = 3)
		public void testMethod2();

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	private @interface Type {

		public int value();

	}

}