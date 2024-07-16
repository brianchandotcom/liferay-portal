/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.language.rest.client.dto.v1_0.Message;
import com.liferay.portal.test.rule.Inject;

import java.io.File;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Thiago Buarque
 */
@RunWith(Arquillian.class)
public class MessageResourceTest extends BaseMessageResourceTestCase {

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		for (Message message : _messages) {
			_ploEntryLocalService.deletePLOEntry(
				testCompany.getCompanyId(), message.getKey(),
				message.getLanguageId());
		}
	}

	@Override
	@Test
	public void testDeleteMessage() throws Exception {
		Message message = _createMessage();

		_postMessage(message);

		messageResource.deleteMessage(
			message.getKey(), message.getLanguageId());

		Assert.assertNull(
			_ploEntryLocalService.fetchPLOEntry(
				testCompany.getCompanyId(), message.getKey(),
				message.getLanguageId()));
	}

	@Override
	@Test
	public void testGetMessage() throws Exception {
		Message message1 = _createMessage();

		_postMessage(message1);

		Message message2 = messageResource.getMessage(
			message1.getKey(), message1.getLanguageId());

		Assert.assertEquals(message1, message2);
	}

	@Override
	@Test
	public void testPostMessage() throws Exception {
		testGetMessage();
	}

	@Override
	@Test
	public void testPostMessageImport() throws Exception {
		File file = FileUtil.createTempFile("properties");

		try {
			FileUtil.write(
				file, "property-1=Property One\nproperty-2=Property Two");

			messageResource.postMessageImport(
				"en_US", null,
				HashMapBuilder.<String, File>put(
					"file", file
				).build());

			Properties properties = new Properties();

			properties.load(
				Files.newBufferedReader(
					Paths.get(file.getPath()), Charset.defaultCharset()));

			Enumeration<String> enumeration =
				(Enumeration<String>)properties.propertyNames();

			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();

				Message message = new Message();

				message.setKey(enumeration.nextElement());
				message.setLanguageId("en_US");
				message.setValue(properties.getProperty(key));

				_messages.add(message);
			}

			Message message1 = messageResource.getMessage(
				"property-1", "en_US");

			Assert.assertEquals("Property One", message1.getValue());

			Message message2 = messageResource.getMessage(
				"property-2", "en_US");

			Assert.assertEquals("Property Two", message2.getValue());
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	@Test
	public void testPutMessage() throws Exception {
		Message message1 = _createMessage();

		_postMessage(message1);

		message1.setValue(RandomTestUtil.randomString());

		messageResource.putMessage(message1);

		Message message2 = messageResource.getMessage(
			message1.getKey(), message1.getLanguageId());

		Assert.assertEquals(message1, message2);
	}

	private Message _createMessage() throws Exception {
		Message message = randomMessage();

		message.setLanguageId("en_US");

		return message;
	}

	private void _postMessage(Message message) throws Exception {
		messageResource.postMessage(message);

		_messages.add(message);
	}

	private final List<Message> _messages = new ArrayList<>();

	@Inject
	private PLOEntryLocalService _ploEntryLocalService;

}