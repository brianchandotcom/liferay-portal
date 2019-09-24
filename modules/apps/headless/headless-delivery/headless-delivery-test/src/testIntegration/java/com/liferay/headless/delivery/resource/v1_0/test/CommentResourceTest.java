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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.headless.delivery.client.dto.v1_0.Comment;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Objects;

import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class CommentResourceTest extends BaseCommentResourceTestCase {

	@Override
	protected boolean equals(Comment comment1, Comment comment2) {
		if (Objects.equals(_formatHTML(comment1), _formatHTML(comment2))) {
			return true;
		}

		return false;
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"text"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId"};
	}

	@Override
	protected Comment testDeleteComment_addComment() throws Exception {
		BlogsEntry blogsEntry = _addBlogsEntry();

		return commentResource.postBlogPostingComment(
			blogsEntry.getEntryId(), randomComment());
	}

	@Override
	protected Long testGetBlogPostingCommentsPage_getBlogPostingId()
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry();

		return blogsEntry.getEntryId();
	}

	@Override
	protected Comment testGetComment_addComment() throws Exception {
		BlogsEntry blogsEntry = _addBlogsEntry();

		return commentResource.postBlogPostingComment(
			blogsEntry.getEntryId(), randomComment());
	}

	@Override
	protected Long testGetCommentCommentsPage_getParentCommentId()
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry();

		Comment comment = commentResource.postBlogPostingComment(
			blogsEntry.getEntryId(), randomComment());

		return comment.getId();
	}

	@Override
	protected Long testGetDocumentCommentsPage_getDocumentId()
		throws Exception {

		FileEntry fileEntry = _addFileEntry();

		return fileEntry.getFileEntryId();
	}

	@Override
	protected Long testGetStructuredContentCommentsPage_getStructuredContentId()
		throws Exception {

		JournalArticle journalArticle = _addJournalArticle();

		return journalArticle.getResourcePrimKey();
	}

	@Override
	protected Comment testGraphQLComment_addComment() throws Exception {
		return testGetComment_addComment();
	}

	@Override
	protected Comment testPutComment_addComment() throws Exception {
		BlogsEntry blogsEntry = _addBlogsEntry();

		Comment comment = commentResource.postBlogPostingComment(
			blogsEntry.getEntryId(), randomComment());

		return commentResource.postCommentComment(
			comment.getId(), randomComment());
	}

	private BlogsEntry _addBlogsEntry() throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		return BlogsEntryLocalServiceUtil.addEntry(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);
	}

	private FileEntry _addFileEntry() throws Exception {
		return DLAppTestUtil.addFileEntryWithWorkflow(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), true, new ServiceContext());
	}

	private JournalArticle _addJournalArticle() throws Exception {
		return JournalTestUtil.addArticle(testGroup.getGroupId(), 0);
	}

	private String _formatHTML(Comment comment) {
		String text = comment.getText();

		if (!text.startsWith("<p>")) {
			return StringBundler.concat("<p>", text, "</p>");
		}

		return text;
	}

}