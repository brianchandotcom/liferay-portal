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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Guilherme Camacho
 */
public class BeanTestUtilTest {

	@Test(expected = NoSuchMethodException.class)
	public void testCopyPropertiesShouldFailIfPropertiesDoesNotExist()
		throws Exception {

		TestClass sourceTestClass = new TestClass();

		sourceTestClass.setIntegerProperty(1);

		Object targetTestClass = new Object();

		BeanTestUtil.copyProperties(sourceTestClass, targetTestClass);

		Assert.assertEquals(sourceTestClass, targetTestClass);
	}

	@Test
	public void testCopyPropertiesShouldSucceedIfClassUsesDoubleBraceInitialization()
		throws Exception {

		TestClass sourceTestClass = new TestClass() {
			{
				booleanProperty = true;
				dateProperty = new Date();
				doubleProperty = 0.1;
				integerProperty = 1;
				longProperty = 1L;
				mapProperty = Collections.singletonMap("Aaa", "Aaa");
				stringProperty = "aaa";
			}
		};

		TestClass targetTestClass = new TestClass();

		BeanTestUtil.copyProperties(sourceTestClass, targetTestClass);

		Assert.assertEquals(sourceTestClass, targetTestClass);
	}

	@Test
	public void testCopyPropertiesShouldSucceedIfPropertiesExist()
		throws Exception {

		TestClass sourceTestClass = new TestClass();

		sourceTestClass.setBooleanProperty(true);
		sourceTestClass.setDateProperty(new Date());
		sourceTestClass.setDoubleProperty(0.1);
		sourceTestClass.setIntegerProperty(1);
		sourceTestClass.setLongProperty(1L);
		sourceTestClass.setMapProperty(Collections.singletonMap("Aaa", "Aaa"));
		sourceTestClass.setStringProperty("aaa");

		TestClass targetTestClass = new TestClass();

		BeanTestUtil.copyProperties(sourceTestClass, targetTestClass);

		Assert.assertEquals(sourceTestClass, targetTestClass);
	}

	@Test
	public void testHasPropertyShouldReturnFalseIfPropertyDoesNotExist() {
		Assert.assertFalse(
			BeanTestUtil.hasProperty(
				new TestClass(), RandomTestUtil.randomString()));
	}

	@Test
	public void testHasPropertyShouldReturnTrueIfPropertyExists() {
		Assert.assertTrue(
			BeanTestUtil.hasProperty(new TestClass(), "stringProperty"));
	}

	@Test(expected = NoSuchMethodException.class)
	public void testSetPropertyShouldFailIfPropertyDoesNotExist()
		throws Exception {

		BeanTestUtil.setProperty(
			new TestClass(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Test
	public void testSetPropertyShouldSucceedIfPropertyExists()
		throws Exception {

		TestClass testClass = new TestClass();

		BeanTestUtil.setProperty(testClass, "booleanProperty", true);
		Assert.assertTrue(testClass.getBooleanProperty());

		Date date = new Date();

		BeanTestUtil.setProperty(testClass, "dateProperty", date);
		Assert.assertEquals(date, testClass.getDateProperty());

		BeanTestUtil.setProperty(testClass, "doubleProperty", 0.1);
		Assert.assertEquals(0.1D, testClass.getDoubleProperty(), 0.01);

		BeanTestUtil.setProperty(testClass, "integerProperty", 1);
		Assert.assertEquals(1, testClass.getIntegerProperty(), 0);

		BeanTestUtil.setProperty(testClass, "longProperty", 1);
		Assert.assertEquals(1, testClass.getLongProperty(), 0);

		BeanTestUtil.setProperty(
			testClass, "mapProperty", Collections.singletonMap("Aaa", "Aaa"));
		Assert.assertEquals(
			Collections.singletonMap("Aaa", "Aaa"), testClass.getMapProperty());

		BeanTestUtil.setProperty(testClass, "stringProperty", "aaa");
		Assert.assertEquals("aaa", testClass.getStringProperty());
	}

	public static class TestClass {

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof TestClass)) {
				return false;
			}

			TestClass testClass = (TestClass)object;

			if (Objects.equals(booleanProperty, testClass.booleanProperty) &&
				Objects.equals(dateProperty, testClass.dateProperty) &&
				Objects.equals(doubleProperty, testClass.doubleProperty) &&
				Objects.equals(integerProperty, testClass.integerProperty) &&
				Objects.equals(longProperty, testClass.longProperty) &&
				Objects.equals(mapProperty, testClass.mapProperty) &&
				Objects.equals(stringProperty, testClass.stringProperty)) {

				return true;
			}

			return false;
		}

		public Boolean getBooleanProperty() {
			return booleanProperty;
		}

		public Date getDateProperty() {
			return dateProperty;
		}

		public Double getDoubleProperty() {
			return doubleProperty;
		}

		public Integer getIntegerProperty() {
			return integerProperty;
		}

		public Long getLongProperty() {
			return longProperty;
		}

		public Map<String, Object> getMapProperty() {
			return mapProperty;
		}

		public String getStringProperty() {
			return stringProperty;
		}

		@Override
		public int hashCode() {
			return Objects.hash(
				booleanProperty, dateProperty, doubleProperty, integerProperty,
				longProperty, mapProperty, stringProperty);
		}

		public void setBooleanProperty(Boolean booleanProperty) {
			this.booleanProperty = booleanProperty;
		}

		public void setDateProperty(Date dateProperty) {
			this.dateProperty = dateProperty;
		}

		public void setDoubleProperty(Double doubleProperty) {
			this.doubleProperty = doubleProperty;
		}

		public void setIntegerProperty(Integer integerProperty) {
			this.integerProperty = integerProperty;
		}

		public void setLongProperty(Long longProperty) {
			this.longProperty = longProperty;
		}

		public void setMapProperty(Map<String, Object> mapProperty) {
			this.mapProperty = mapProperty;
		}

		public void setStringProperty(String stringProperty) {
			this.stringProperty = stringProperty;
		}

		protected Boolean booleanProperty;
		protected Date dateProperty;
		protected Double doubleProperty;
		protected Integer integerProperty;
		protected Long longProperty;
		protected Map<String, Object> mapProperty;
		protected String stringProperty;

	}

}