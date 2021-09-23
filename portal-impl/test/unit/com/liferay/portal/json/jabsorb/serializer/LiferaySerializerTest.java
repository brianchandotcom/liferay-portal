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

package com.liferay.portal.json.jabsorb.serializer;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.logging.Level;

import org.jabsorb.serializer.SerializerState;

import org.json.JSONObject;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael Bowerman
 */
public class LiferaySerializerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testUnmarshall() throws Exception {
		ClassLoaderPool.register(
			"TestClassLoader",
			new ClassLoader() {

				@Override
				public Class<?> loadClass(String name)
					throws ClassNotFoundException {

					if (name.equals(ServiceContext.class.getName())) {
						throw new ClassNotFoundException();
					}

					return super.loadClass(name);
				}

			});

		LiferaySerializer liferaySerializer = new LiferaySerializer();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("contextName", "TestClassLoader");
		jsonObject.put("javaClass", ServiceContext.class.getName());
		jsonObject.put("serializable", new JSONObject());

		SerializerState serializerState = new SerializerState();

		serializerState.store(jsonObject);

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				LiferaySerializer.class.getName(), Level.WARNING)) {

			Object object = liferaySerializer.unmarshall(
				serializerState, ServiceContext.class, jsonObject);

			Assert.assertNotNull(object);

			Assert.assertEquals(ServiceContext.class, object.getClass());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to load class " + ServiceContext.class.getName() +
					" in context TestClassLoader",
				logEntry.getMessage());
		}

		ClassLoaderPool.unregister("TestClassLoader");
	}

}