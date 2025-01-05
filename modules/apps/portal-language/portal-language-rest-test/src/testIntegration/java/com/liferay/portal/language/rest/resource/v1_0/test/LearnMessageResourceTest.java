/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
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
	@Test
	public void testGetLearnMessagesResourcePage() throws Exception {
		String resource = testGetLearnMessagesResourcePage_getResource();

		Page<LearnMessage> page =
			learnMessageResource.getLearnMessagesResourcePage(
				resource, null, null);

		assertValid(page);

		String languageId = "en_US";

		page = learnMessageResource.getLearnMessagesResourcePage(
			resource, languageId, null);

		assertValid(page);
		validateLanguageFilter(page, languageId);

		String key = "chat-provider-account-id-hubspot";

		page = learnMessageResource.getLearnMessagesResourcePage(
			resource, null, key);

		assertValid(page);
		validateKeyFilter(page, key);

		page = learnMessageResource.getLearnMessagesResourcePage(
			resource, languageId, key);

		assertValid(page);
		validateLanguageFilter(page, languageId);
		validateKeyFilter(page, key);
	}

	@Test
	public void testGetLearnMessagesResourcePage_InvalidKey() throws Exception {
		String resource = testGetLearnMessagesResourcePage_getResource();
		String invalidKey = RandomTestUtil.randomString();

		Page<LearnMessage> page =
			learnMessageResource.getLearnMessagesResourcePage(
				resource, null, invalidKey);

		assertValidEmptyPage(page);
	}

	@Test
	public void testGetLearnMessagesResourcePage_InvalidLanguage()
		throws Exception {

		String resource = testGetLearnMessagesResourcePage_getResource();
		String invalidLanguage = RandomTestUtil.randomString();

		Page<LearnMessage> page =
			learnMessageResource.getLearnMessagesResourcePage(
				resource, invalidLanguage, null);

		assertValidEmptyPage(page);
	}

	@Test
	public void testGetLearnMessagesResourcePage_InvalidResource()
		throws Exception {

		String invalidResource = RandomTestUtil.randomString();

		Page<LearnMessage> page =
			learnMessageResource.getLearnMessagesResourcePage(
				invalidResource, null, null);

		assertValidEmptyPage(page);
	}

	protected void assertValidEmptyPage(Page<LearnMessage> page) {
		Assert.assertNotNull("Page should not be null", page);
		Assert.assertNotNull("Page items should not be null", page.getItems());
		Assert.assertTrue(
			"Page should be empty",
			page.getItems(
			).isEmpty());
	}

	@Override
	protected String testGetLearnMessagesResourcePage_getResource() {
		return "click-to-chat-web";
	}

	protected void validateKeyFilter(Page<LearnMessage> page, String key) {
		Assert.assertFalse(
			"Page should have items",
			page.getItems(
			).isEmpty());

		for (LearnMessage message : page.getItems()) {
			Assert.assertEquals(
				"Message key should match filter", key, message.getKey());
		}
	}

	protected void validateLanguageFilter(
		Page<LearnMessage> page, String languageId) {

		for (LearnMessage message : page.getItems()) {
			LearnMessageDetail[] details = message.getLearnMessageDetails();

			Assert.assertNotNull("Message details should not be null", details);
			Assert.assertTrue(
				"Message should have details", details.length > 0);

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

}