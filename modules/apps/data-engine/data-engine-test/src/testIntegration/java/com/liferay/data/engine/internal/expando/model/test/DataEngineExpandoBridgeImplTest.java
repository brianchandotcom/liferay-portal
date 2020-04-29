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

package com.liferay.data.engine.internal.expando.model.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.nativeobject.DataEngineNativeObject;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class DataEngineExpandoBridgeImplTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			DataEngineExpandoBridgeImplTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<DataEngineNativeObject> serviceReference =
			bundleContext.getServiceReference(DataEngineNativeObject.class);

		if (serviceReference != null) {
			bundleContext.ungetService(serviceReference);
		}

		bundleContext.registerService(
			DataEngineNativeObject.class,
			new DataEngineNativeObject() {

				@Override
				public String getClassName() {
					return DataDefinition.class.getName();
				}

				@Override
				public String getName() {
					return DataDefinition.class.getSimpleName();
				}

			},
			new HashMapDictionary<>());

		Group group = GroupTestUtil.addGroup();

		_company = CompanyLocalServiceUtil.getCompany(group.getCompanyId());

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.data.engine.internal.configuration." +
						"DataEngineConfiguration",
					new HashMapDictionary<String, Object>() {
						{
							put(
								"dataEngineNativeObjectClassNames",
								new String[] {DataDefinition.class.getName()});
						}
					})) {

			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
				_company.getCompanyId(), DataDefinition.class.getName(),
				RandomTestUtil.randomLong());
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).checkPermissions(
				false
			).user(
				_company.getDefaultUser()
			).build();

		DataDefinition dataDefinition =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeByDataDefinitionKey(
					_company.getGroupId(), "native-object",
					DataDefinition.class.getName());

		dataDefinitionResource.deleteDataDefinition(dataDefinition.getId());
	}

	@Test
	public void testAddAttribute() throws Exception {
		long classPK = _expandoBridge.getClassPK();

		_expandoBridge.setClassPK(1);

		Assert.assertEquals(
			StringPool.BLANK, _expandoBridge.getAttribute(_addAttribute()));

		_expandoBridge.setClassPK(classPK);
	}

	@Test
	public void testAddAttributeWithDefaultValue() throws Exception {
		long classPK = _expandoBridge.getClassPK();

		_expandoBridge.setClassPK(1);

		String defaultValue = RandomTestUtil.randomString();

		Assert.assertEquals(
			defaultValue,
			_expandoBridge.getAttribute(_addAttribute(defaultValue)));

		_expandoBridge.setClassPK(classPK);
	}

	@Test
	public void testGetAttributeDefault() throws Exception {
		String defaultValue = RandomTestUtil.randomString();

		Assert.assertEquals(
			defaultValue,
			_expandoBridge.getAttributeDefault(_addAttribute(defaultValue)));
	}

	@Test
	public void testGetAttributeNames() {
		List<String> attributeNames = ListUtil.fromEnumeration(
			_expandoBridge.getAttributeNames());

		Assert.assertEquals(
			_attributes.toString(), attributeNames.size(), _attributes.size());
	}

	@Test
	public void testGetAttributeProperties() throws Exception {
		UnicodeProperties unicodeProperties =
			_expandoBridge.getAttributeProperties(_addAttribute());

		Assert.assertEquals("text", unicodeProperties.getProperty("fieldType"));
	}

	@Test
	public void testGetAttributes() {
		Map<String, Serializable> attributes = _expandoBridge.getAttributes(
			_attributes.keySet());

		Assert.assertEquals(
			attributes.toString(), _attributes.size(), attributes.size());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetAttributeType() {
		_expandoBridge.getAttributeType(null);
	}

	@Test
	public void testHasAttribute() throws Exception {
		Assert.assertTrue(_expandoBridge.hasAttribute(_addAttribute()));
	}

	@Test
	public void testSetAttributeAndGetAttribute() throws Exception {
		String attributeValue = RandomTestUtil.randomString();

		String attributeName = _addAttribute(attributeValue);

		_expandoBridge.setAttribute(attributeName, attributeValue);

		Serializable value = _expandoBridge.getAttribute(attributeName);

		Assert.assertEquals(attributeValue, value);
	}

	@Test
	public void testSetAttributeDefault() throws Exception {
		String defaultValue = RandomTestUtil.randomString();

		String attributeName = _addAttribute(defaultValue);

		Assert.assertEquals(
			defaultValue, _expandoBridge.getAttributeDefault(attributeName));

		defaultValue = RandomTestUtil.randomString();

		_expandoBridge.setAttributeDefault(attributeName, defaultValue);

		Assert.assertEquals(
			defaultValue, _expandoBridge.getAttributeDefault(attributeName));
	}

	@Test
	public void testSetAttributeProperties() throws Exception {
		String attributeName = _addAttribute();

		UnicodeProperties unicodeProperties =
			_expandoBridge.getAttributeProperties(attributeName);

		Assert.assertEquals("text", unicodeProperties.getProperty("fieldType"));

		unicodeProperties.put("fieldType", "numeric");

		_expandoBridge.setAttributeProperties(attributeName, unicodeProperties);

		unicodeProperties = _expandoBridge.getAttributeProperties(
			attributeName);

		Assert.assertEquals(
			"numeric", unicodeProperties.getProperty("fieldType"));
	}

	@Test
	public void testSetAttributes() throws Exception {
		String attributeName1 = _addAttribute();

		String attributeName2 = _addAttribute();

		Assert.assertNull(_expandoBridge.getAttribute(attributeName1));

		Assert.assertNull(_expandoBridge.getAttribute(attributeName2));

		String defaultValue1 = RandomTestUtil.randomString();

		String defaultValue2 = RandomTestUtil.randomString();

		_expandoBridge.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				attributeName1, defaultValue1
			).put(
				attributeName2, defaultValue2
			).build());

		Assert.assertEquals(
			defaultValue1, _expandoBridge.getAttribute(attributeName1));
		Assert.assertEquals(
			defaultValue2, _expandoBridge.getAttribute(attributeName2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSetAttributesWithServiceContext() {
		_expandoBridge.setAttributes(new ServiceContext());
	}

	private String _addAttribute() throws Exception {
		String attributeName = RandomTestUtil.randomString();

		_expandoBridge.addAttribute(attributeName);

		_attributes.put(attributeName, StringPool.BLANK);

		return attributeName;
	}

	private String _addAttribute(Serializable value) throws Exception {
		String attributeName = RandomTestUtil.randomString();

		_expandoBridge.addAttribute(attributeName, "text", value);

		_attributes.put(attributeName, value);

		return attributeName;
	}

	private static final Map<String, Serializable> _attributes =
		new HashMap<>();
	private static Company _company;
	private static ExpandoBridge _expandoBridge;

}