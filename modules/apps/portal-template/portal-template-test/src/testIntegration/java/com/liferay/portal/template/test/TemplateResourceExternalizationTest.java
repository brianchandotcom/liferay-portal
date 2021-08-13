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

package com.liferay.portal.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.template.DDMTemplateResource;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.test.ConsoleTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.template.CacheTemplateResource;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.net.URL;

import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class TemplateResourceExternalizationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCacheTemplateResourceExternalization() throws Exception {
		StringTemplateResource stringTemplateResource =
			new StringTemplateResource("testId", "testContent");

		CacheTemplateResource cacheTemplateResource = new CacheTemplateResource(
			stringTemplateResource);

		// writeExternal

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ObjectOutput objectOutput = new ObjectOutputStream(
				unsyncByteArrayOutputStream)) {

			cacheTemplateResource.writeExternal(objectOutput);
		}

		byte[] externalizedData = unsyncByteArrayOutputStream.toByteArray();

		ObjectInput objectInput = new ObjectInputStream(
			new UnsyncByteArrayInputStream(externalizedData));

		Assert.assertEquals(
			cacheTemplateResource.getLastModified(), objectInput.readLong());
		Assert.assertEquals(stringTemplateResource, objectInput.readObject());

		// readExternal

		CacheTemplateResource newCacheTemplateResource =
			new CacheTemplateResource();

		objectInput = new ObjectInputStream(
			new UnsyncByteArrayInputStream(externalizedData));

		newCacheTemplateResource.readExternal(objectInput);

		Assert.assertEquals(
			stringTemplateResource,
			ReflectionTestUtil.getFieldValue(
				newCacheTemplateResource, "_templateResource"));
		Assert.assertEquals(
			cacheTemplateResource.getLastModified(),
			newCacheTemplateResource.getLastModified());
	}

	@Test
	public void testConstructors() throws Exception {
		CacheTemplateResource.class.getConstructor();
		DDMTemplateResource.class.getConstructor();
		StringTemplateResource.class.getConstructor();
		URLTemplateResource.class.getConstructor();
	}

	@Test
	public void testDDMTemplateResourceExternalization() throws Exception {
		long templateId = 100;

		Class<?> clazz = DDMTemplate.class;

		DDMTemplate ddmTemplate = (DDMTemplate)ProxyUtil.newProxyInstance(
			clazz.getClassLoader(), new Class<?>[] {clazz},
			(proxy, method, arguments) -> {
				if (Objects.equals(method.getName(), "getTemplateId")) {
					return templateId;
				}

				throw new UnsupportedOperationException();
			});

		String ddmTemplateKey = "testKey";

		DDMTemplateResource ddmTemplateResource = new DDMTemplateResource(
			ddmTemplateKey, ddmTemplate);

		// writeExternal

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ObjectOutput objectOutput = new MockObjectOutput(
				unsyncByteArrayOutputStream)) {

			ddmTemplateResource.writeExternal(objectOutput);
		}

		byte[] externalizedData = unsyncByteArrayOutputStream.toByteArray();

		DataInputStream dataInputStream = new DataInputStream(
			new UnsyncByteArrayInputStream(externalizedData));

		Assert.assertEquals(templateId, dataInputStream.readLong());
		Assert.assertEquals(ddmTemplateKey, dataInputStream.readUTF());

		// readExternal

		DDMTemplateResource newDDMTemplateResource = new DDMTemplateResource();

		MockObjectInput mockObjectInput = new MockObjectInput(
			new DataInputStream(
				new UnsyncByteArrayInputStream(externalizedData)));

		UnsyncByteArrayOutputStream hijackedUnsyncByteArrayOutputStream =
			ConsoleTestUtil.hijackStdErr();

		try {
			newDDMTemplateResource.readExternal(mockObjectInput);

			Assert.fail();
		}
		catch (IOException ioException) {
			Assert.assertEquals(
				"Unable to retrieve ddm template with ID " + templateId,
				ioException.getMessage());
		}
		finally {
			ConsoleTestUtil.restoreStdErr(hijackedUnsyncByteArrayOutputStream);
		}

		Assert.assertEquals(null, newDDMTemplateResource.getTemplateId());
	}

	@Test
	public void testExternalizableType() {
		Assert.assertTrue(
			Externalizable.class.isAssignableFrom(TemplateResource.class));
		Assert.assertTrue(
			TemplateResource.class.isAssignableFrom(
				CacheTemplateResource.class));
		Assert.assertTrue(
			TemplateResource.class.isAssignableFrom(DDMTemplateResource.class));
		Assert.assertTrue(
			TemplateResource.class.isAssignableFrom(
				StringTemplateResource.class));
		Assert.assertTrue(
			TemplateResource.class.isAssignableFrom(URLTemplateResource.class));
	}

	@Test
	public void testStringTemplateResourceExternalization() throws Exception {
		String templateId = "testId";
		String templateContent = "testContent";

		StringTemplateResource stringTemplateResource =
			new StringTemplateResource(templateId, templateContent);

		// writeExternal

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ObjectOutput objectOutput = new MockObjectOutput(
				unsyncByteArrayOutputStream)) {

			stringTemplateResource.writeExternal(objectOutput);
		}

		byte[] externalizedData = unsyncByteArrayOutputStream.toByteArray();

		DataInputStream dataInputStream = new DataInputStream(
			new UnsyncByteArrayInputStream(externalizedData));

		Assert.assertEquals(
			stringTemplateResource.getLastModified(),
			dataInputStream.readLong());
		Assert.assertEquals(templateContent, dataInputStream.readUTF());
		Assert.assertEquals(templateId, dataInputStream.readUTF());

		// readExternal

		StringTemplateResource newStringTemplateResource =
			new StringTemplateResource();

		MockObjectInput mockObjectInput = new MockObjectInput(
			new DataInputStream(
				new UnsyncByteArrayInputStream(externalizedData)));

		newStringTemplateResource.readExternal(mockObjectInput);

		Assert.assertEquals(
			stringTemplateResource.getLastModified(),
			newStringTemplateResource.getLastModified());
		Assert.assertEquals(
			templateContent, newStringTemplateResource.getContent());
		Assert.assertEquals(
			templateId, newStringTemplateResource.getTemplateId());
	}

	@Test
	public void testURLTemplateResourceExternalization() throws IOException {
		String templateId = "testId";

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		String resourcePath = clazz.getName();

		resourcePath = StringUtil.replace(resourcePath, '.', '/') + ".class";

		URL url = classLoader.getResource(resourcePath);

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			templateId, url);

		// writeExternal

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ObjectOutput objectOutput = new MockObjectOutput(
				unsyncByteArrayOutputStream)) {

			urlTemplateResource.writeExternal(objectOutput);
		}

		byte[] externalizedData = unsyncByteArrayOutputStream.toByteArray();

		DataInputStream dataInputStream = new DataInputStream(
			new UnsyncByteArrayInputStream(externalizedData));

		Assert.assertEquals(templateId, dataInputStream.readUTF());
		Assert.assertEquals(url.toExternalForm(), dataInputStream.readUTF());

		// readExternal

		URLTemplateResource newURLTemplateResource = new URLTemplateResource();

		MockObjectInput mockObjectInput = new MockObjectInput(
			new DataInputStream(
				new UnsyncByteArrayInputStream(externalizedData)));

		newURLTemplateResource.readExternal(mockObjectInput);

		Assert.assertEquals(templateId, newURLTemplateResource.getTemplateId());
		Assert.assertEquals(
			url,
			ReflectionTestUtil.getFieldValue(
				newURLTemplateResource, "_templateURL"));
	}

	private static class MockObjectInput
		extends DataInputStream implements ObjectInput {

		public MockObjectInput(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public Object readObject() {
			throw new UnsupportedOperationException();
		}

	}

	private static class MockObjectOutput
		extends DataOutputStream implements ObjectOutput {

		public MockObjectOutput(OutputStream outputStream) {
			super(outputStream);
		}

		@Override
		public void writeObject(Object object) {
			throw new UnsupportedOperationException();
		}

	}

}