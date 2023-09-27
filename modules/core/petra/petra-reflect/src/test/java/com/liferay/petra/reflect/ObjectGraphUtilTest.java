/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.reflect;

import com.liferay.petra.reflect.ObjectGraphUtil.AnnotatedFieldMappingVisitor;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ObjectGraphUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Before
	public void setUp() {
		_recordVisitor = new RecordVisitor();

		_entries = _recordVisitor._entries;

		_recordAnnotatedFieldMappingVisitor =
			new RecordAnnotatedFieldMappingVisitor();

		_annotatedFieldMappingEntries =
			_recordAnnotatedFieldMappingVisitor._entries;
	}

	@Test
	public void testAnnotatedFieldMappingVisitorHasAnnoation() {
		Assert.assertFalse(
			_recordAnnotatedFieldMappingVisitor.hasAnnotation(
				new Annotation[] {Annotation1.INSTANCE}));
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.hasAnnotation(
				new Annotation[] {Annotation2.INSTANCE}));
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.hasAnnotation(
				new Annotation[] {Annotation3.INSTANCE}));
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.hasAnnotation(
				new Annotation[] {Annotation1.INSTANCE, Annotation2.INSTANCE}));
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.hasAnnotation(
				new Annotation[] {Annotation2.INSTANCE, Annotation3.INSTANCE}));
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.hasAnnotation(
				new Annotation[] {Annotation1.INSTANCE, Annotation3.INSTANCE}));
	}

	@Test
	public void testAnnotatedFieldMappingVisitorIsFieldTypeClass() {
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				LinkedType1.class));
		Assert.assertFalse(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				LinkedType2.class));
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				LinkedType3.class));
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				LinkedType1[].class));
		Assert.assertFalse(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				LinkedType2[].class));
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				LinkedType3[].class));
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				LinkedType1[][].class));
		Assert.assertFalse(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				LinkedType2[][].class));
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				LinkedType3[][].class));

		LinkedType1 linkedType1 = new LinkedType1() {
		};

		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				linkedType1.getClass()));

		LinkedType2 linkedType2 = new LinkedType2() {
		};

		Assert.assertFalse(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				linkedType2.getClass()));

		LinkedType3 linkedType3 = new LinkedType3() {
		};

		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isFieldTypeClass(
				linkedType3.getClass()));
	}

	@Test
	public void testAnnotatedFieldMappingVisitorIsLinkedClass() {
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isLinkedClass(
				LinkedType1.class));
		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isLinkedClass(
				LinkedType2.class));
		Assert.assertFalse(
			_recordAnnotatedFieldMappingVisitor.isLinkedClass(
				LinkedType3.class));

		LinkedType1 linkedType1 = new LinkedType1() {
		};

		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isLinkedClass(
				linkedType1.getClass()));

		LinkedType2 linkedType2 = new LinkedType2() {
		};

		Assert.assertTrue(
			_recordAnnotatedFieldMappingVisitor.isLinkedClass(
				linkedType2.getClass()));

		LinkedType3 linkedType3 = new LinkedType3() {
		};

		Assert.assertFalse(
			_recordAnnotatedFieldMappingVisitor.isLinkedClass(
				linkedType3.getClass()));
	}

	@Test
	public void testAnnotatedFieldMappingVisitorMapValue() {
		LinkedType1 linkedType1 = new LinkedType1();

		Assert.assertSame(
			linkedType1,
			_recordAnnotatedFieldMappingVisitor.mapValue(null, linkedType1));

		Assert.assertEquals(
			_annotatedFieldMappingEntries.toString(), 1,
			_annotatedFieldMappingEntries.size());

		Map.Entry<Field, Object> entry = _annotatedFieldMappingEntries.get(0);

		Assert.assertNull(entry.getKey());
		Assert.assertSame(linkedType1, entry.getValue());
	}

	@Test
	public void testAnnotatedFieldMappingVisitorMapValueArray() {
		LinkedType1[] linkedType1Array = {new LinkedType1(), new LinkedType1()};

		LinkedType1[] mappedLinkedType1Array =
			(LinkedType1[])_recordAnnotatedFieldMappingVisitor.mapValue(
				null, linkedType1Array);

		Assert.assertNotSame(linkedType1Array, mappedLinkedType1Array);
		Assert.assertArrayEquals(linkedType1Array, mappedLinkedType1Array);

		Assert.assertEquals(
			_annotatedFieldMappingEntries.toString(), 2,
			_annotatedFieldMappingEntries.size());

		Map.Entry<Field, Object> entry = _annotatedFieldMappingEntries.get(0);

		Assert.assertNull(entry.getKey());
		Assert.assertSame(linkedType1Array[0], entry.getValue());

		entry = _annotatedFieldMappingEntries.get(1);

		Assert.assertNull(entry.getKey());
		Assert.assertSame(linkedType1Array[1], entry.getValue());
	}

	@Test
	public void testAnnotatedFieldMappingVisitorNullReference()
		throws Exception {

		Assert.assertNull(
			_recordAnnotatedFieldMappingVisitor.visit(
				ReflectionUtil.getDeclaredField(NullReference.class, "_object"),
				new NullReference()));
		Assert.assertTrue(_annotatedFieldMappingEntries.isEmpty());
	}

	@Test
	public void testAnnotatedFieldMappingVisitorStaticField() throws Exception {
		Assert.assertNull(
			_recordAnnotatedFieldMappingVisitor.visit(
				ReflectionUtil.getDeclaredField(StaticField.class, "_INT"),
				new StaticField()));
		Assert.assertTrue(_annotatedFieldMappingEntries.isEmpty());
	}

	@Test
	public void testAnnotatedFieldMappingVisitorVisit() throws Exception {
		LinkedType1 linkedType1 = new LinkedType1();

		Assert.assertSame(
			linkedType1._object1,
			_recordAnnotatedFieldMappingVisitor.visit(
				ReflectionUtil.getDeclaredField(LinkedType1.class, "_object1"),
				linkedType1));

		Assert.assertTrue(_annotatedFieldMappingEntries.isEmpty());
		Assert.assertSame(
			linkedType1._object2,
			_recordAnnotatedFieldMappingVisitor.visit(
				ReflectionUtil.getDeclaredField(LinkedType1.class, "_object2"),
				linkedType1));
		Assert.assertTrue(_annotatedFieldMappingEntries.isEmpty());

		Field field = ReflectionUtil.getDeclaredField(
			LinkedType1.class, "_linkedType3");

		Assert.assertNull(
			_recordAnnotatedFieldMappingVisitor.visit(field, linkedType1));

		Assert.assertEquals(
			_annotatedFieldMappingEntries.toString(), 1,
			_annotatedFieldMappingEntries.size());

		Map.Entry<Field, Object> entry = _annotatedFieldMappingEntries.get(0);

		Assert.assertEquals(field, entry.getKey());
		Assert.assertSame(linkedType1._linkedType3, entry.getValue());
	}

	@Test
	public void testConstructor() {
		new ObjectGraphUtil();
	}

	@Test
	public void testWalkObjectGraphNullReference() throws Exception {
		NullReference nullReference = new NullReference();

		ObjectGraphUtil.walkObjectGraph(nullReference, _recordVisitor);

		Assert.assertEquals(_entries.toString(), 1, _entries.size());

		Map.Entry<Field, Object> entry = _entries.get(0);

		Assert.assertEquals(
			ReflectionUtil.getDeclaredField(NullReference.class, "_object"),
			entry.getKey());
		Assert.assertSame(nullReference, entry.getValue());
	}

	@Test
	public void testWalkObjectGraphNullReferenceWithException() {
		final Exception exception1 = new Exception();

		try {
			ObjectGraphUtil.walkObjectGraph(
				new NullReference(),
				new ObjectGraphUtil.Visitor() {

					@Override
					public Object visit(Field field, Object target)
						throws Exception {

						throw exception1;
					}

				});

			Assert.fail();
		}
		catch (Exception exception2) {
			Assert.assertSame(exception1, exception2);
		}
	}

	@Test
	public void testWalkObjectGraphObject() {
		ObjectGraphUtil.walkObjectGraph(new Object(), _recordVisitor);

		Assert.assertTrue(_entries.isEmpty());
	}

	@Test
	public void testWalkObjectGraphPrimitive() throws Exception {
		Primitive primitive = new Primitive();

		ObjectGraphUtil.walkObjectGraph(primitive, _recordVisitor);

		Assert.assertEquals(_entries.toString(), 1, _entries.size());

		Map.Entry<Field, Object> entry = _entries.get(0);

		Assert.assertEquals(
			ReflectionUtil.getDeclaredField(Primitive.class, "_int"),
			entry.getKey());
		Assert.assertSame(primitive, entry.getValue());
	}

	@Test
	public void testWalkObjectGraphPrimitiveArray() throws Exception {
		PrimitiveArray primitiveArray = new PrimitiveArray();

		ObjectGraphUtil.walkObjectGraph(primitiveArray, _recordVisitor);

		Assert.assertEquals(_entries.toString(), 1, _entries.size());

		Map.Entry<Field, Object> entry = _entries.get(0);

		Assert.assertEquals(
			ReflectionUtil.getDeclaredField(PrimitiveArray.class, "_ints"),
			entry.getKey());
		Assert.assertSame(primitiveArray, entry.getValue());
	}

	@Test
	public void testWalkObjectGraphSelfReference() throws Exception {
		SelfReference selfReference = new SelfReference();

		ObjectGraphUtil.walkObjectGraph(selfReference, _recordVisitor);

		Assert.assertEquals(_entries.toString(), 1, _entries.size());

		Map.Entry<Field, Object> entry = _entries.get(0);

		Assert.assertEquals(
			ReflectionUtil.getDeclaredField(
				SelfReference.class, "_selfReference"),
			entry.getKey());
		Assert.assertSame(selfReference, entry.getValue());
	}

	@Test
	public void testWalkObjectGraphSelfReferenceArray() throws Exception {
		SelfReferenceArray selfReferenceArray = new SelfReferenceArray();

		ObjectGraphUtil.walkObjectGraph(selfReferenceArray, _recordVisitor);

		Assert.assertEquals(_entries.toString(), 1, _entries.size());

		Map.Entry<Field, Object> entry = _entries.get(0);

		Assert.assertEquals(
			ReflectionUtil.getDeclaredField(
				SelfReferenceArray.class, "_selfReferenceArray"),
			entry.getKey());
		Assert.assertSame(selfReferenceArray, entry.getValue());
	}

	@Test
	public void testWalkObjectGraphStaticField() {
		ObjectGraphUtil.walkObjectGraph(new StaticField(), _recordVisitor);

		Assert.assertTrue(_entries.isEmpty());
	}

	private List<Map.Entry<Field, Object>> _annotatedFieldMappingEntries;
	private List<Map.Entry<Field, Object>> _entries;
	private RecordAnnotatedFieldMappingVisitor
		_recordAnnotatedFieldMappingVisitor;
	private RecordVisitor _recordVisitor;

	private static class LinkedType1 {

		@Annotation3
		private final LinkedType3 _linkedType3 = new LinkedType3();

		@Annotation1
		private final Object _object1 = new Object();

		@Annotation2
		private final Object _object2 = new Object();

	}

	private static class LinkedType2 {
	}

	private static class LinkedType3 {
	}

	private static class NullReference {

		@SuppressWarnings("unused")
		private final Object _object = null;

	}

	private static class Primitive {

		public Primitive() {
			_int = 1;
		}

		@SuppressWarnings("unused")
		private final int _int;

	}

	private static class PrimitiveArray {

		@SuppressWarnings("unused")
		private final int[] _ints = {1, 2, 3};

	}

	private static class RecordAnnotatedFieldMappingVisitor
		extends AnnotatedFieldMappingVisitor {

		@Override
		protected Object doMap(Field field, Object value) {
			_entries.add(new AbstractMap.SimpleImmutableEntry<>(field, value));

			return value;
		}

		private RecordAnnotatedFieldMappingVisitor() {
			super(
				new HashSet<Class<?>>(
					Arrays.asList(LinkedType1.class, LinkedType2.class)),
				new HashSet<Class<? extends Annotation>>(
					Arrays.asList(Annotation2.class, Annotation3.class)),
				new HashSet<Class<?>>(
					Arrays.asList(LinkedType1.class, LinkedType3.class)));
		}

		private List<Map.Entry<Field, Object>> _entries = new ArrayList<>();

	}

	private static class RecordVisitor implements ObjectGraphUtil.Visitor {

		@Override
		public Object visit(Field field, Object target) throws Exception {
			_entries.add(new AbstractMap.SimpleImmutableEntry<>(field, target));

			return field.get(target);
		}

		private final List<Map.Entry<Field, Object>> _entries =
			new ArrayList<>();

	}

	private static class SelfReference {

		@SuppressWarnings("unused")
		private final SelfReference _selfReference = this;

	}

	private static class SelfReferenceArray {

		@SuppressWarnings("unused")
		private final SelfReferenceArray[] _selfReferenceArray = {this, null};

	}

	private static class StaticField {

		@SuppressWarnings("unused")
		private static final int _INT = 1;

	}

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Annotation1 {

		public static final Annotation1 INSTANCE = new Annotation1() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return Annotation1.class;
			}

		};

	}

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Annotation2 {

		public static final Annotation2 INSTANCE = new Annotation2() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return Annotation2.class;
			}

		};

	}

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	private @interface Annotation3 {

		public static final Annotation3 INSTANCE = new Annotation3() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return Annotation3.class;
			}

		};

	}

}