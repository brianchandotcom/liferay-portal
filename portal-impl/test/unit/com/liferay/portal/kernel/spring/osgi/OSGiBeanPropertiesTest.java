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

package com.liferay.portal.kernel.spring.osgi;

import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.PropsImpl;

import java.io.Serializable;

import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class OSGiBeanPropertiesTest {

	@Before
	public void setUp() {
		PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void testAnnotatedClass() {
		@OSGiBeanProperties
		class C {
		}

		Assert.assertNotNull(OSGiBeanProperties.Convert.fromObject(new C()));
	}

	@Test
	public void testNotAnnotatedClass() {
		class C {
		}

		Assert.assertNull(OSGiBeanProperties.Convert.fromObject(new C()));
	}

	@Test
	public void testServicesAnnotated() {
		@OSGiBeanProperties(service = Serializable.class)
		class C implements Serializable {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(1, interfaceClasses.size());
	}

	@Test
	public void testServicesAnnotatedButExtends() {
		class B implements Serializable {
		}

		@OSGiBeanProperties(service = Serializable.class)
		class C extends B {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(1, interfaceClasses.size());
	}

	@Test
	public void testServicesAnnotatedButExtendsMutliple() {
		class B implements EventListener, Serializable {
		}

		@OSGiBeanProperties(service = Serializable.class)
		class C extends B {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(1, interfaceClasses.size());
	}

	@Test
	public void testServicesAnnotatedButImplements() {
		@OSGiBeanProperties(service = Serializable.class)
		class C implements Serializable {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(1, interfaceClasses.size());
	}

	@Test
	public void testServicesAnnotatedButImplementsMultiple() {
		@OSGiBeanProperties(service = Serializable.class)
		class C implements EventListener, Serializable {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(1, interfaceClasses.size());
	}

	@Test(expected = ClassCastException.class)
	public void testServicesAnnotatedButNotImplements() {
		@OSGiBeanProperties(service = Serializable.class)
		class C {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(1, interfaceClasses.size());
	}

	@Test(expected = ClassCastException.class)
	public void testServicesAnnotatedButNotImplementsMultiple() {
		@OSGiBeanProperties(service = {EventListener.class, Serializable.class})
		class C implements Serializable {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(1, interfaceClasses.size());
	}

	@Test
	public void testServicesAnnotatedNoneSet() {
		@OSGiBeanProperties
		class C {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(0, interfaceClasses.size());
	}

	@Test
	public void testServicesAnnotatedNoneSetButExtends() {
		class B implements Serializable {
		}

		@OSGiBeanProperties
		class C extends B {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(1, interfaceClasses.size());
	}

	@Test
	public void testServicesAnnotatedNoneSetButExtendsMultiple() {
		class B implements EventListener, Serializable {
		}

		@OSGiBeanProperties
		class C extends B {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(2, interfaceClasses.size());
	}

	@Test
	public void testServicesAnnotatedNoneSetButImplements() {
		@OSGiBeanProperties
		class C implements Serializable {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(1, interfaceClasses.size());
	}

	@Test
	public void testServicesAnnotatedNoneSetButImplementsMultiple() {
		@OSGiBeanProperties
		class C implements EventListener, Serializable {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(2, interfaceClasses.size());
	}

	@Test
	public void testServicesNotAnnotated() {
		class C {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(0, interfaceClasses.size());
	}

	@Test
	public void testServicesNotAnnotatedButExtends() {
		class B implements Serializable {
		}

		class C extends B {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(1, interfaceClasses.size());
	}

	@Test
	public void testServicesNotAnnotatedButExtendsMultiple() {
		class B implements EventListener, Serializable {
		}

		class C extends B {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(2, interfaceClasses.size());
	}

	@Test
	public void testServicesNotAnnotatedButImplements() {
		class C implements Serializable {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(1, interfaceClasses.size());
	}

	@Test
	public void testServicesNotAnnotatedButImplementsMultiple() {
		class C implements EventListener, Serializable {
		}

		Set<Class<?>> interfaceClasses = OSGiBeanProperties.Service.interfaces(
			new C());

		Assert.assertEquals(2, interfaceClasses.size());
	}

	@Test
	public void testWithArrayOfBooleanProperties() {
		@OSGiBeanProperties(
			property = {
				"key:Boolean=true", "key:Boolean=false", "key:Boolean=true"
			}
		)
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);

		Object value = properties.get("key");

		Class<? extends Object> clazz = value.getClass();

		Assert.assertTrue(clazz.isArray());
		Assert.assertEquals(Boolean[].class, clazz);
		Boolean[] values = (Boolean[])value;
		Assert.assertEquals(true, values[0]);
		Assert.assertEquals(false, values[1]);
		Assert.assertEquals(true, values[2]);
		Assert.assertEquals(3, values.length);
	}

	@Test
	public void testWithArrayOfByteProperties() {
		@OSGiBeanProperties(
			property = {"key:Byte=127", "key:Byte=95", "key:Byte=13"}
		)
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);

		Object value = properties.get("key");

		Class<? extends Object> clazz = value.getClass();

		Assert.assertTrue(clazz.isArray());
		Assert.assertEquals(Byte[].class, clazz);
		Byte[] values = (Byte[])value;
		Assert.assertEquals((byte)127, (byte)values[0]);
		Assert.assertEquals((byte)95, (byte)values[1]);
		Assert.assertEquals((byte)13, (byte)values[2]);
		Assert.assertEquals(3, values.length);
	}

	@Test
	public void testWithArrayOfCharacterProperties() {
		@OSGiBeanProperties(
			property = {
				"key:Character=@", "key:Character=#", "key:Character=\u0069"
			}
		)
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);

		Object value = properties.get("key");

		Class<? extends Object> clazz = value.getClass();

		Assert.assertTrue(clazz.isArray());
		Assert.assertEquals(Character[].class, clazz);
		Character[] values = (Character[])value;
		Assert.assertEquals('@', (char)values[0]);
		Assert.assertEquals('#', (char)values[1]);
		Assert.assertEquals('\u0069', (char)values[2]);
		Assert.assertEquals(3, values.length);
	}

	@Test
	public void testWithArrayOfDoubleProperties() {
		@OSGiBeanProperties(
			property = {
				"key:Double=1.7976931348623157E308", "key:Double=4.9E-324",
				"key:Double=2.2250738585072014E-308"
			}
		)
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);

		Object value = properties.get("key");

		Class<? extends Object> clazz = value.getClass();

		Assert.assertTrue(clazz.isArray());
		Assert.assertEquals(Double[].class, clazz);
		Double[] values = (Double[])value;
		Assert.assertEquals(Double.MAX_VALUE, values[0], 0);
		Assert.assertEquals(Double.MIN_VALUE, values[1], 0);
		Assert.assertEquals(Double.MIN_NORMAL, values[2], 0);
		Assert.assertEquals(3, values.length);
	}

	@Test
	public void testWithArrayOfFloatProperties() {
		@OSGiBeanProperties(
			property = {
				"key:Float=3.4028234663852886E38",
				"key:Float=1.401298464324817E-45",
				"key:Float=1.1754943508222875E-38"
			}
		)
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);

		Object value = properties.get("key");

		Class<? extends Object> clazz = value.getClass();

		Assert.assertTrue(clazz.isArray());
		Assert.assertEquals(Float[].class, clazz);
		Float[] values = (Float[])value;
		Assert.assertEquals(Float.MAX_VALUE, values[0], 0);
		Assert.assertEquals(Float.MIN_VALUE, values[1], 0);
		Assert.assertEquals(Float.MIN_NORMAL, values[2], 0);
		Assert.assertEquals(3, values.length);
	}

	@Test
	public void testWithArrayOfIntegerProperties() {
		@OSGiBeanProperties(
			property = {
				"key:Integer=2147483647", "key:Integer=-2147483648",
				"key:Integer=12"
			}
		)
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);

		Object value = properties.get("key");

		Class<? extends Object> clazz = value.getClass();

		Assert.assertTrue(clazz.isArray());
		Assert.assertEquals(Integer[].class, clazz);
		Integer[] values = (Integer[])value;
		Assert.assertEquals(Integer.MAX_VALUE, (int)values[0]);
		Assert.assertEquals(Integer.MIN_VALUE, (int)values[1]);
		Assert.assertEquals(12, (int)values[2]);
		Assert.assertEquals(3, values.length);
	}

	@Test
	public void testWithArrayOfLongProperties() {
		@OSGiBeanProperties(
			property = {
				"key:Long=9223372036854775807", "key:Long=-9223372036854775808",
				"key:Long=12"
			}
		)
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);

		Object value = properties.get("key");

		Class<? extends Object> clazz = value.getClass();

		Assert.assertTrue(clazz.isArray());
		Assert.assertEquals(Long[].class, clazz);
		Long[] values = (Long[])value;
		Assert.assertEquals(Long.MAX_VALUE, (long)values[0]);
		Assert.assertEquals(Long.MIN_VALUE, (long)values[1]);
		Assert.assertEquals(12L, (long)values[2]);
		Assert.assertEquals(3, values.length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithArrayOfMixedProperties() {
		@OSGiBeanProperties(
			property = {
				"key:String=32767", "key:Integer=-32768", "key:Short=12"
			}
		)
		class C {
		}

		OSGiBeanProperties.Convert.fromObject(new C());
	}

	@Test
	public void testWithArrayOfProperties() {
		@OSGiBeanProperties(
			property = {"key=valueA", "key=valueB", "key=valueC"}
		)
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);

		Object value = properties.get("key");

		Class<? extends Object> clazz = value.getClass();

		Assert.assertTrue(clazz.isArray());
		Assert.assertEquals(String[].class, clazz);
		String[] values = (String[])value;
		Assert.assertEquals("valueA", values[0]);
		Assert.assertEquals("valueB", values[1]);
		Assert.assertEquals("valueC", values[2]);
		Assert.assertEquals(3, values.length);
	}

	@Test
	public void testWithArrayOfShortProperties() {
		@OSGiBeanProperties(
			property = {"key:Short=32767", "key:Short=-32768", "key:Short=12"}
		)
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);

		Object value = properties.get("key");

		Class<? extends Object> clazz = value.getClass();

		Assert.assertTrue(clazz.isArray());
		Assert.assertEquals(Short[].class, clazz);
		Short[] values = (Short[])value;
		Assert.assertEquals(Short.MAX_VALUE, (short)values[0]);
		Assert.assertEquals(Short.MIN_VALUE, (short)values[1]);
		Assert.assertEquals(12, (short)values[2]);
		Assert.assertEquals(3, values.length);
	}

	@Test
	public void testWithMultipleProperties() {
		@OSGiBeanProperties(property = {"key1=value1", "key2=value2"})
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);
		Assert.assertFalse(properties.isEmpty());
		Assert.assertEquals(2, properties.size());
		Assert.assertEquals("value1", properties.get("key1"));
	}

	@Test
	public void testWithPortalProperties() {
		@OSGiBeanProperties(portalPropertyPrefix = "portlet.")
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);
		Assert.assertTrue(
			properties.containsKey("add.default.resource.check.whitelist"));
		Assert.assertEquals(
			PropsUtil.get("portlet.add.default.resource.check.whitelist"),
			properties.get("add.default.resource.check.whitelist"));
	}

	@Test
	public void testWithSingleProperty() {
		@OSGiBeanProperties(property = "key=value")
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);
		Assert.assertFalse(properties.isEmpty());
		Assert.assertEquals(1, properties.size());
		Assert.assertEquals("value", properties.get("key"));
	}

	@Test
	public void testWithTypedProperties() {
		@OSGiBeanProperties(
			property = {
				"key1=value1", "key2:Boolean=true", "key3:Byte=127",
				"key4:Character=@", "key5:Double=1.7976931348623157E308",
				"key6:Float=3.4028234663852886E38f", "key7:Integer=2147483647",
				"key8:Long=9223372036854775807", "key9:Short=32767",
				"key10:String=value10"
			}
		)
		class C {
		}

		Map<String, Object> properties = OSGiBeanProperties.Convert.fromObject(
			new C());

		Assert.assertNotNull(properties);
		Assert.assertEquals("value1", properties.get("key1"));
		Assert.assertEquals(Boolean.TRUE, properties.get("key2"));
		Assert.assertEquals(Byte.valueOf("127"), properties.get("key3"));
		Assert.assertEquals(Character.valueOf('@'), properties.get("key4"));
		Assert.assertEquals(Double.MAX_VALUE, properties.get("key5"));
		Assert.assertEquals(Float.MAX_VALUE, properties.get("key6"));
		Assert.assertEquals(Integer.MAX_VALUE, properties.get("key7"));
		Assert.assertEquals(Long.MAX_VALUE, properties.get("key8"));
		Assert.assertEquals(Short.MAX_VALUE, properties.get("key9"));
		Assert.assertEquals("value10", properties.get("key10"));
	}

}