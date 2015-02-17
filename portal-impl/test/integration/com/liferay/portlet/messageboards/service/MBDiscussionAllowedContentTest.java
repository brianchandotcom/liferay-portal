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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.model.MBDiscussionAllowedContent;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class MBDiscussionAllowedContentTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		MBDiscussionAllowedContent mbDiscussionAllowedContent =
			new MBDiscussionAllowedContent(
				PropsValues.DISCUSSION_COMMENTS_ALLOWED_CONTENT);

		MBMessageLocalServiceUtil.setMBDiscussionAllowedContent(
			mbDiscussionAllowedContent);
	}

	@Test
	public void testAddCommentWithLinkWhenLinkIsAllowed() throws Exception {
		String allowedContent = "a[href];em;p;strong;u";
		String body = "Test with valid <a href=\"www.google.com\">link</a>";

		testAllowedContent(allowedContent, body, body);
	}

	@Test
	public void testAddCommentWithLinkWhenLinkIsNotAllowed() throws Exception {
		String allowedContent = "em;p;strong;u";
		String body = "Test with valid <a href=\"www.google.com\">link</a>";
		String expectedBody = "Test with valid link";

		testAllowedContent(allowedContent, body, expectedBody);
	}

	@Test
	public void testAddCommentWithParagraphAndLinkWhenBothAreAllowed()
		throws Exception {

		String allowedContent = "a[href];p";
		String body =
			"<p>Test with valid <a href=\"www.google.com\">link</a></p>";

		testAllowedContent(allowedContent, body, body);
	}

	@Test
	public void testAddCommentWithParagraphAndLinkWhenOnlyLinkIsAllowed()
		throws Exception {

		String allowedContent = "a[href]";
		String body =
			"<p>Test with valid <a href=\"www.google.com\">link</a></p>";
		String expectedBody =
			"Test with valid <a href=\"www.google.com\">link</a>";

		testAllowedContent(allowedContent, body, expectedBody);
	}

	@Test
	public void testAddCommentWithParagraphAndLinkWhenOnlyParagraphIsAllowed()
		throws Exception {

		String allowedContent = "p";
		String body =
			"<p>Test with valid <a href=\"www.google.com\">link</a></p>";
		String expectedBody = "<p>Test with valid link</p>";

		testAllowedContent(allowedContent, body, expectedBody);
	}

	@Test
	public void testIncludeAllowedAttributes() throws Exception {
		String allowedContent = "a[href,title];p[title]";
		String body =
			"<p title=\"paragraph\"><a href=\"www.google.com\" " +
				"title=\"link\">link</a></p>";

		testAllowedContent(allowedContent, body, body);
	}

	@Test
	public void testRemoveNotAllowedAttributes() throws Exception {
		String allowedContent = "a[href];p";
		String body =
			"<p title=\"paragraph\"><a href=\"www.google.com\" " +
				"title=\"link\">link</a></p>";
		String expectedBody = "<p><a href=\"www.google.com\">link</a></p>";

		testAllowedContent(allowedContent, body, expectedBody);
	}

	protected void testAllowedContent(
			String allowedContent, String body, String expectedBody)
		throws Exception {

		MBDiscussionAllowedContent mbDiscussionAllowedContent =
			new MBDiscussionAllowedContent(allowedContent);

		MBMessageLocalServiceUtil.setMBDiscussionAllowedContent(
			mbDiscussionAllowedContent);

		ServiceContext serviceContext = new ServiceContext();

		User user = TestPropsValues.getUser();
		String className = RandomTestUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		MBMessage message = MBMessageLocalServiceUtil.addDiscussionMessage(
			user.getUserId(), user.getFullName(), _group.getGroupId(),
			className, classPK, 0, MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID,
			RandomTestUtil.randomString(), body, serviceContext);

		Assert.assertEquals(expectedBody, message.getBody());
	}

	@DeleteAfterTestRun
	private Group _group;

}