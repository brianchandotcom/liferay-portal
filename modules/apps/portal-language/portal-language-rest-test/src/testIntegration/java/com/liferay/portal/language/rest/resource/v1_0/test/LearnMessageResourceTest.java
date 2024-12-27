/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.language.rest.client.dto.v1_0.LearnMessage;
import com.liferay.portal.language.rest.client.dto.v1_0.LearnMessageDetail;
import com.liferay.portal.language.rest.client.pagination.Page;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Thiago Buarque
 */
@RunWith(Arquillian.class)
public class LearnMessageResourceTest extends BaseLearnMessageResourceTestCase {

	@Override
	protected String testGetLearnMessagesResourcePage_getResource() {
		return "click-to-chat-web";
	}

	@Override
	@Test
	public void testGetLearnMessagesResourcePage() throws Exception {
		String resource = testGetLearnMessagesResourcePage_getResource();

		// Test with no filters
		Page<LearnMessage> page = learnMessageResource.getLearnMessagesResourcePage(
			resource, null, null);

		assertValid(page);

		// Test with language filter
		String languageId = "en_US";
		page = learnMessageResource.getLearnMessagesResourcePage(
			resource, languageId, null);

		assertValid(page);
		_validateLanguageFilter(page, languageId);

		// Test with specific key
		String key = "chat-provider-account-id-hubspot";
		page = learnMessageResource.getLearnMessagesResourcePage(
			resource, null, key);

		assertValid(page);
		_validateKeyFilter(page, key);

		// Test with both language and key filters
		page = learnMessageResource.getLearnMessagesResourcePage(
			resource, languageId, key);

		assertValid(page);
		_validateLanguageFilter(page, languageId);
		_validateKeyFilter(page, key);
	}

	@Test
	public void testGetLearnMessagesResourcePage_InvalidResource() throws Exception {
		String invalidResource = RandomTestUtil.randomString();

		Page<LearnMessage> page = learnMessageResource.getLearnMessagesResourcePage(
			invalidResource, null, null);

		_assertValidEmptyPage(page);
	}

	@Test
	public void testGetLearnMessagesResourcePage_InvalidLanguage() throws Exception {
		String resource = testGetLearnMessagesResourcePage_getResource();
		String invalidLanguage = RandomTestUtil.randomString();

		Page<LearnMessage> page = learnMessageResource.getLearnMessagesResourcePage(
			resource, invalidLanguage, null);

		_assertValidEmptyPage(page);
	}

	@Test
	public void testGetLearnMessagesResourcePage_InvalidKey() throws Exception {
		String resource = testGetLearnMessagesResourcePage_getResource();
		String invalidKey = RandomTestUtil.randomString();

		Page<LearnMessage> page = learnMessageResource.getLearnMessagesResourcePage(
			resource, null, invalidKey);

		_assertValidEmptyPage(page);
	}

	protected void _assertValidEmptyPage(Page<LearnMessage> page) {
		Assert.assertNotNull("Page should not be null", page);
		Assert.assertNotNull("Page items should not be null", page.getItems());
		Assert.assertTrue("Page should be empty", page.getItems().isEmpty());
	}

	protected void _validateLanguageFilter(Page<LearnMessage> page, String languageId) {
		for (LearnMessage message : page.getItems()) {
			LearnMessageDetail[] details = message.getLearnMessageDetails();

			Assert.assertNotNull("Message details should not be null", details);
			Assert.assertTrue("Message should have details", details.length > 0);

			boolean hasLanguage = false;
			for (LearnMessageDetail detail : details) {
				if (languageId.equals(detail.getLanguageId())) {
					hasLanguage = true;
					break;
				}
			}

			Assert.assertTrue(
				"Message should have requested language: " + languageId,
				hasLanguage);
		}
	}

	protected void _validateKeyFilter(Page<LearnMessage> page, String key) {
		Assert.assertFalse("Page should have items", page.getItems().isEmpty());

		for (LearnMessage message : page.getItems()) {
			Assert.assertEquals(
				"Message key should match filter",
				key,
				message.getKey());
		}
	}
}