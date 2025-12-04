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

	public Blogs getBlogs() {
		return blogs;
	}

	public String getBlogsAsString() {
		if (blogs == null) {
			return null;
		}

		return blogs.toString();
	}

	public void setBlogs(Blogs blogs) {
		this.blogs = blogs;
	}

	public void setBlogs(UnsafeSupplier<Blogs, Exception> blogsUnsafeSupplier) {
		try {
			blogs = blogsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Blogs blogs;

	public Bookmarks getBookmarks() {
		return bookmarks;
	}

	public String getBookmarksAsString() {
		if (bookmarks == null) {
			return null;
		}

		return bookmarks.toString();
	}

	public void setBookmarks(Bookmarks bookmarks) {
		this.bookmarks = bookmarks;
	}

	public void setBookmarks(
		UnsafeSupplier<Bookmarks, Exception> bookmarksUnsafeSupplier) {

		try {
			bookmarks = bookmarksUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Bookmarks bookmarks;

	public Comments getComments() {
		return comments;
	}

	public String getCommentsAsString() {
		if (comments == null) {
			return null;
		}

		return comments.toString();
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}

	public void setComments(
		UnsafeSupplier<Comments, Exception> commentsUnsafeSupplier) {

		try {
			comments = commentsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Comments comments;

	public DocumentsAndMedia getDocumentsAndMedia() {
		return documentsAndMedia;
	}

	public String getDocumentsAndMediaAsString() {
		if (documentsAndMedia == null) {
			return null;
		}

		return documentsAndMedia.toString();
	}

	public void setDocumentsAndMedia(DocumentsAndMedia documentsAndMedia) {
		this.documentsAndMedia = documentsAndMedia;
	}

	public void setDocumentsAndMedia(
		UnsafeSupplier<DocumentsAndMedia, Exception>
			documentsAndMediaUnsafeSupplier) {

		try {
			documentsAndMedia = documentsAndMediaUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DocumentsAndMedia documentsAndMedia;

	public KnowledgeBase getKnowledgeBase() {
		return knowledgeBase;
	}

	public String getKnowledgeBaseAsString() {
		if (knowledgeBase == null) {
			return null;
		}

		return knowledgeBase.toString();
	}

	public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}

	public void setKnowledgeBase(
		UnsafeSupplier<KnowledgeBase, Exception> knowledgeBaseUnsafeSupplier) {

		try {
			knowledgeBase = knowledgeBaseUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected KnowledgeBase knowledgeBase;

	public MessageBoards getMessageBoards() {
		return messageBoards;
	}

	public String getMessageBoardsAsString() {
		if (messageBoards == null) {
			return null;
		}

		return messageBoards.toString();
	}

	public void setMessageBoards(MessageBoards messageBoards) {
		this.messageBoards = messageBoards;
	}

	public void setMessageBoards(
		UnsafeSupplier<MessageBoards, Exception> messageBoardsUnsafeSupplier) {

		try {
			messageBoards = messageBoardsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected MessageBoards messageBoards;

	public PageRatings getPageRatings() {
		return pageRatings;
	}

	public String getPageRatingsAsString() {
		if (pageRatings == null) {
			return null;
		}

		return pageRatings.toString();
	}

	public void setPageRatings(PageRatings pageRatings) {
		this.pageRatings = pageRatings;
	}

	public void setPageRatings(
		UnsafeSupplier<PageRatings, Exception> pageRatingsUnsafeSupplier) {

		try {
			pageRatings = pageRatingsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PageRatings pageRatings;

	public WebContent getWebContent() {
		return webContent;
	}

	public String getWebContentAsString() {
		if (webContent == null) {
			return null;
		}

		return webContent.toString();
	}

	public void setWebContent(WebContent webContent) {
		this.webContent = webContent;
	}

	public void setWebContent(
		UnsafeSupplier<WebContent, Exception> webContentUnsafeSupplier) {

		try {
			webContent = webContentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected WebContent webContent;

	public Wiki getWiki() {
		return wiki;
	}

	public String getWikiAsString() {
		if (wiki == null) {
			return null;
		}

		return wiki.toString();
	}

	public void setWiki(Wiki wiki) {
		this.wiki = wiki;
	}

	public void setWiki(UnsafeSupplier<Wiki, Exception> wikiUnsafeSupplier) {
		try {
			wiki = wikiUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Wiki wiki;

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

	public static enum Blogs {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static Blogs create(String value) {
			for (Blogs blogs : values()) {
				if (Objects.equals(blogs.getValue(), value) ||
					Objects.equals(blogs.name(), value)) {

					return blogs;
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

		private Blogs(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Bookmarks {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static Bookmarks create(String value) {
			for (Bookmarks bookmarks : values()) {
				if (Objects.equals(bookmarks.getValue(), value) ||
					Objects.equals(bookmarks.name(), value)) {

					return bookmarks;
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

		private Bookmarks(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Comments {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static Comments create(String value) {
			for (Comments comments : values()) {
				if (Objects.equals(comments.getValue(), value) ||
					Objects.equals(comments.name(), value)) {

					return comments;
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

		private Comments(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum DocumentsAndMedia {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static DocumentsAndMedia create(String value) {
			for (DocumentsAndMedia documentsAndMedia : values()) {
				if (Objects.equals(documentsAndMedia.getValue(), value) ||
					Objects.equals(documentsAndMedia.name(), value)) {

					return documentsAndMedia;
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

		private DocumentsAndMedia(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum KnowledgeBase {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static KnowledgeBase create(String value) {
			for (KnowledgeBase knowledgeBase : values()) {
				if (Objects.equals(knowledgeBase.getValue(), value) ||
					Objects.equals(knowledgeBase.name(), value)) {

					return knowledgeBase;
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

		private KnowledgeBase(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum MessageBoards {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static MessageBoards create(String value) {
			for (MessageBoards messageBoards : values()) {
				if (Objects.equals(messageBoards.getValue(), value) ||
					Objects.equals(messageBoards.name(), value)) {

					return messageBoards;
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

		private MessageBoards(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum PageRatings {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static PageRatings create(String value) {
			for (PageRatings pageRatings : values()) {
				if (Objects.equals(pageRatings.getValue(), value) ||
					Objects.equals(pageRatings.name(), value)) {

					return pageRatings;
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

		private PageRatings(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum WebContent {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static WebContent create(String value) {
			for (WebContent webContent : values()) {
				if (Objects.equals(webContent.getValue(), value) ||
					Objects.equals(webContent.name(), value)) {

					return webContent;
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

		private WebContent(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Wiki {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		public static Wiki create(String value) {
			for (Wiki wiki : values()) {
				if (Objects.equals(wiki.getValue(), value) ||
					Objects.equals(wiki.name(), value)) {

					return wiki;
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

		private Wiki(String value) {
			_value = value;
		}

		private final String _value;

	}

}