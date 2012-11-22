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

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.ConsoleTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.template.CacheTemplateResource;
import com.liferay.portlet.journal.model.JournalTemplate;

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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class TemplateResourceExternalizationTest {

	@Test
	public void testCacheTemplateResourceExternalization() throws Exception {
		StringTemplateResource stringTemplateResource =
			new StringTemplateResource("testId", "testContent");

		CacheTemplateResource cacheTemplateResource =
			new CacheTemplateResource(stringTemplateResource);

		// writeExternal

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ObjectOutput objectOutput = new ObjectOutputStream(
			unsyncByteArrayOutputStream);

		cacheTemplateResource.writeExternal(objectOutput);

		objectOutput.close();

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

		Field templateResourceField = ReflectionUtil.getDeclaredField(
			CacheTemplateResource.class, "_templateResource");

		Assert.assertEquals(
			stringTemplateResource,
			templateResourceField.get(newCacheTemplateResource));

		Assert.assertEquals(
			cacheTemplateResource.getLastModified(),
			newCacheTemplateResource.getLastModified());
	}

	@Test
	public void testConstructors() throws Exception {
		CacheTemplateResource.class.getConstructor();
		JournalTemplateResource.class.getConstructor();
		StringTemplateResource.class.getConstructor();
		URLTemplateResource.class.getConstructor();
	}

	@Test
	public void testExternalizableType() {
		Assert.assertTrue(
			Externalizable.class.isAssignableFrom(TemplateResource.class));
		Assert.assertTrue(
			TemplateResource.class.isAssignableFrom(
				CacheTemplateResource.class));
		Assert.assertTrue(
			TemplateResource.class.isAssignableFrom(
				JournalTemplateResource.class));
		Assert.assertTrue(
			TemplateResource.class.isAssignableFrom(
				StringTemplateResource.class));
		Assert.assertTrue(
			TemplateResource.class.isAssignableFrom(URLTemplateResource.class));
	}

	@Test
	public void testJournalTemplateResourceExternalization() throws Exception {
		String templateId = "testId";
		final long journalTemplateId = 100;

		JournalTemplate journalTemplate =
			(JournalTemplate)ProxyUtil.newProxyInstance(
				getClass().getClassLoader(),
				new Class<?>[] {JournalTemplate.class},
				new InvocationHandler() {

					public Object invoke(
							Object proxy, Method method, Object[] arguments)
						throws Throwable {

						String methodName = method.getName();

						if (methodName.equals("getId")) {
							return journalTemplateId;
						}

						throw new UnsupportedOperationException();
					}

				});

		JournalTemplateResource journalTemplateResource =
			new JournalTemplateResource(templateId, journalTemplate);

		// writeExternal

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ObjectOutput objectOutput = new MockObjectOutput(
			unsyncByteArrayOutputStream);

		journalTemplateResource.writeExternal(objectOutput);

		objectOutput.close();

		byte[] externalizedData = unsyncByteArrayOutputStream.toByteArray();

		DataInputStream dataInputStream = new DataInputStream(
			new UnsyncByteArrayInputStream(externalizedData));

		Assert.assertEquals(journalTemplateId, dataInputStream.readLong());
		Assert.assertEquals(templateId, dataInputStream.readUTF());

		// readExternal

		JournalTemplateResource newJournalTemplateResource =
			new JournalTemplateResource();

		MockObjectInput mockObjectInput = new MockObjectInput(
			new DataInputStream(
				new UnsyncByteArrayInputStream(externalizedData)));

		UnsyncByteArrayOutputStream hijackedUnsyncByteArrayOutputStream =
			ConsoleTestUtil.hijackStdErr();

		try {
			newJournalTemplateResource.readExternal(mockObjectInput);

			Assert.fail();
		}
		catch (IOException ioe) {
			Assert.assertEquals(
				"Unable to retrieve journal template with ID " +
					journalTemplateId,
				ioe.getMessage());
		}
		finally {
			ConsoleTestUtil.restoreStdErr(hijackedUnsyncByteArrayOutputStream);
		}

		Assert.assertEquals(null, newJournalTemplateResource.getTemplateId());
	}

	@Test
	public void testStringTemplateResourceExternalization() throws Exception {
		String templateId= "testId";
		String templateContent = "testContent";

		StringTemplateResource stringTemplateResource =
			new StringTemplateResource(templateId, templateContent);

		// writeExternal

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ObjectOutput objectOutput = new MockObjectOutput(
			unsyncByteArrayOutputStream);

		stringTemplateResource.writeExternal(objectOutput);

		objectOutput.close();

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
	public void testURLTemplateResourceExternalization() throws Exception {
		String templateId = "testId";

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		String resourcePath = clazz.getName();

		resourcePath = resourcePath.replace('.', '/') + ".class";

		URL url = classLoader.getResource(resourcePath);

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			templateId, url);

		// writeExternal

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		ObjectOutput objectOutput = new MockObjectOutput(
			unsyncByteArrayOutputStream);

		urlTemplateResource.writeExternal(objectOutput);

		objectOutput.close();

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

		Field templateURLField = ReflectionUtil.getDeclaredField(
			URLTemplateResource.class, "_templateURL");

		Assert.assertEquals(url, templateURLField.get(newURLTemplateResource));
	}

	private static class MockObjectInput
		extends DataInputStream implements ObjectInput {

		public MockObjectInput(InputStream inputStream) {
			super(inputStream);
		}

		public Object readObject() {
			throw new UnsupportedOperationException();
		}

	}

	private static class MockObjectOutput
		extends DataOutputStream implements ObjectOutput {

		public MockObjectOutput(OutputStream outputStream) {
			super(outputStream);
		}

		public void writeObject(Object obj) {
			throw new UnsupportedOperationException();
		}

	}

}