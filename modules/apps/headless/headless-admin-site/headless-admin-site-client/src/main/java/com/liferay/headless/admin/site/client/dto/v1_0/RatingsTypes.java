/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.RatingsTypesSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class RatingsTypes implements Cloneable, Serializable {

	public static RatingsTypes toDTO(String json) {
		return RatingsTypesSerDes.toDTO(json);
	}

	public BlogPosting getBlogPosting() {
		return blogPosting;
	}

	public String getBlogPostingAsString() {
		if (blogPosting == null) {
			return null;
		}

		return blogPosting.toString();
	}

	public void setBlogPosting(BlogPosting blogPosting) {
		this.blogPosting = blogPosting;
	}

	public void setBlogPosting(
		UnsafeSupplier<BlogPosting, Exception> blogPostingUnsafeSupplier) {

		try {
			blogPosting = blogPostingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BlogPosting blogPosting;

	public BookmarksEntry getBookmarksEntry() {
		return bookmarksEntry;
	}

	public String getBookmarksEntryAsString() {
		if (bookmarksEntry == null) {
			return null;
		}

		return bookmarksEntry.toString();
	}

	public void setBookmarksEntry(BookmarksEntry bookmarksEntry) {
		this.bookmarksEntry = bookmarksEntry;
	}

	public void setBookmarksEntry(
		UnsafeSupplier<BookmarksEntry, Exception>
			bookmarksEntryUnsafeSupplier) {

		try {
			bookmarksEntry = bookmarksEntryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BookmarksEntry bookmarksEntry;

	public Comment getComment() {
		return comment;
	}

	public String getCommentAsString() {
		if (comment == null) {
			return null;
		}

		return comment.toString();
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public void setComment(
		UnsafeSupplier<Comment, Exception> commentUnsafeSupplier) {

		try {
			comment = commentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Comment comment;

	public Document getDocument() {
		return document;
	}

	public String getDocumentAsString() {
		if (document == null) {
			return null;
		}

		return document.toString();
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void setDocument(
		UnsafeSupplier<Document, Exception> documentUnsafeSupplier) {

		try {
			document = documentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Document document;

	public KnowledgeBaseArticle getKnowledgeBaseArticle() {
		return knowledgeBaseArticle;
	}

	public String getKnowledgeBaseArticleAsString() {
		if (knowledgeBaseArticle == null) {
			return null;
		}

		return knowledgeBaseArticle.toString();
	}

	public void setKnowledgeBaseArticle(
		KnowledgeBaseArticle knowledgeBaseArticle) {

		this.knowledgeBaseArticle = knowledgeBaseArticle;
	}

	public void setKnowledgeBaseArticle(
		UnsafeSupplier<KnowledgeBaseArticle, Exception>
			knowledgeBaseArticleUnsafeSupplier) {

		try {
			knowledgeBaseArticle = knowledgeBaseArticleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected KnowledgeBaseArticle knowledgeBaseArticle;

	public MessageBoardMessage getMessageBoardMessage() {
		return messageBoardMessage;
	}

	public String getMessageBoardMessageAsString() {
		if (messageBoardMessage == null) {
			return null;
		}

		return messageBoardMessage.toString();
	}

	public void setMessageBoardMessage(
		MessageBoardMessage messageBoardMessage) {

		this.messageBoardMessage = messageBoardMessage;
	}

	public void setMessageBoardMessage(
		UnsafeSupplier<MessageBoardMessage, Exception>
			messageBoardMessageUnsafeSupplier) {

		try {
			messageBoardMessage = messageBoardMessageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected MessageBoardMessage messageBoardMessage;

	public SitePage getSitePage() {
		return sitePage;
	}

	public String getSitePageAsString() {
		if (sitePage == null) {
			return null;
		}

		return sitePage.toString();
	}

	public void setSitePage(SitePage sitePage) {
		this.sitePage = sitePage;
	}

	public void setSitePage(
		UnsafeSupplier<SitePage, Exception> sitePageUnsafeSupplier) {

		try {
			sitePage = sitePageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SitePage sitePage;

	public StructuredContent getStructuredContent() {
		return structuredContent;
	}

	public String getStructuredContentAsString() {
		if (structuredContent == null) {
			return null;
		}

		return structuredContent.toString();
	}

	public void setStructuredContent(StructuredContent structuredContent) {
		this.structuredContent = structuredContent;
	}

	public void setStructuredContent(
		UnsafeSupplier<StructuredContent, Exception>
			structuredContentUnsafeSupplier) {

		try {
			structuredContent = structuredContentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected StructuredContent structuredContent;

	public WikiPage getWikiPage() {
		return wikiPage;
	}

	public String getWikiPageAsString() {
		if (wikiPage == null) {
			return null;
		}

		return wikiPage.toString();
	}

	public void setWikiPage(WikiPage wikiPage) {
		this.wikiPage = wikiPage;
	}

	public void setWikiPage(
		UnsafeSupplier<WikiPage, Exception> wikiPageUnsafeSupplier) {

		try {
			wikiPage = wikiPageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected WikiPage wikiPage;

	@Override
	public RatingsTypes clone() throws CloneNotSupportedException {
		return (RatingsTypes)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RatingsTypes)) {
			return false;
		}

		RatingsTypes ratingsTypes = (RatingsTypes)object;

		return Objects.equals(toString(), ratingsTypes.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RatingsTypesSerDes.toJSON(this);
	}

	public static enum BlogPosting {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static BlogPosting create(String value) {
			for (BlogPosting blogPosting : values()) {
				if (Objects.equals(blogPosting.getValue(), value) ||
					Objects.equals(blogPosting.name(), value)) {

					return blogPosting;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private BlogPosting(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum BookmarksEntry {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static BookmarksEntry create(String value) {
			for (BookmarksEntry bookmarksEntry : values()) {
				if (Objects.equals(bookmarksEntry.getValue(), value) ||
					Objects.equals(bookmarksEntry.name(), value)) {

					return bookmarksEntry;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private BookmarksEntry(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Comment {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static Comment create(String value) {
			for (Comment comment : values()) {
				if (Objects.equals(comment.getValue(), value) ||
					Objects.equals(comment.name(), value)) {

					return comment;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Comment(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Document {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static Document create(String value) {
			for (Document document : values()) {
				if (Objects.equals(document.getValue(), value) ||
					Objects.equals(document.name(), value)) {

					return document;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Document(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum KnowledgeBaseArticle {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static KnowledgeBaseArticle create(String value) {
			for (KnowledgeBaseArticle knowledgeBaseArticle : values()) {
				if (Objects.equals(knowledgeBaseArticle.getValue(), value) ||
					Objects.equals(knowledgeBaseArticle.name(), value)) {

					return knowledgeBaseArticle;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private KnowledgeBaseArticle(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum MessageBoardMessage {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static MessageBoardMessage create(String value) {
			for (MessageBoardMessage messageBoardMessage : values()) {
				if (Objects.equals(messageBoardMessage.getValue(), value) ||
					Objects.equals(messageBoardMessage.name(), value)) {

					return messageBoardMessage;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private MessageBoardMessage(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum SitePage {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static SitePage create(String value) {
			for (SitePage sitePage : values()) {
				if (Objects.equals(sitePage.getValue(), value) ||
					Objects.equals(sitePage.name(), value)) {

					return sitePage;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private SitePage(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum StructuredContent {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static StructuredContent create(String value) {
			for (StructuredContent structuredContent : values()) {
				if (Objects.equals(structuredContent.getValue(), value) ||
					Objects.equals(structuredContent.name(), value)) {

					return structuredContent;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private StructuredContent(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum WikiPage {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static WikiPage create(String value) {
			for (WikiPage wikiPage : values()) {
				if (Objects.equals(wikiPage.getValue(), value) ||
					Objects.equals(wikiPage.name(), value)) {

					return wikiPage;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private WikiPage(String value) {
			_value = value;
		}

		private final String _value;

	}

}