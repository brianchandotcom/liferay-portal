/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.validation.Valid;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Site applications where the default rating system can be modified to a desired ratings type.",
	value = "RatingsTypes"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "RatingsTypes")
public class RatingsTypes implements Serializable {

	public static RatingsTypes toDTO(String json) {
		return ObjectMapperUtil.readValue(RatingsTypes.class, json);
	}

	public static RatingsTypes unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(RatingsTypes.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The type of rating system to use"
	)
	@JsonGetter("blogs")
	@Valid
	public Blogs getBlogs() {
		if (_blogsSupplier != null) {
			blogs = _blogsSupplier.get();

			_blogsSupplier = null;
		}

		return blogs;
	}

	@JsonIgnore
	public String getBlogsAsString() {
		Blogs blogs = getBlogs();

		if (blogs == null) {
			return null;
		}

		return blogs.toString();
	}

	public void setBlogs(Blogs blogs) {
		this.blogs = blogs;

		_blogsSupplier = null;
	}

	@JsonIgnore
	public void setBlogs(UnsafeSupplier<Blogs, Exception> blogsUnsafeSupplier) {
		_blogsSupplier = () -> {
			try {
				return blogsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The type of rating system to use")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Blogs blogs;

	@JsonIgnore
	private Supplier<Blogs> _blogsSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The type of rating system to use"
	)
	@JsonGetter("bookmarks")
	@Valid
	public Bookmarks getBookmarks() {
		if (_bookmarksSupplier != null) {
			bookmarks = _bookmarksSupplier.get();

			_bookmarksSupplier = null;
		}

		return bookmarks;
	}

	@JsonIgnore
	public String getBookmarksAsString() {
		Bookmarks bookmarks = getBookmarks();

		if (bookmarks == null) {
			return null;
		}

		return bookmarks.toString();
	}

	public void setBookmarks(Bookmarks bookmarks) {
		this.bookmarks = bookmarks;

		_bookmarksSupplier = null;
	}

	@JsonIgnore
	public void setBookmarks(
		UnsafeSupplier<Bookmarks, Exception> bookmarksUnsafeSupplier) {

		_bookmarksSupplier = () -> {
			try {
				return bookmarksUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The type of rating system to use")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Bookmarks bookmarks;

	@JsonIgnore
	private Supplier<Bookmarks> _bookmarksSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The type of rating system to use"
	)
	@JsonGetter("comments")
	@Valid
	public Comments getComments() {
		if (_commentsSupplier != null) {
			comments = _commentsSupplier.get();

			_commentsSupplier = null;
		}

		return comments;
	}

	@JsonIgnore
	public String getCommentsAsString() {
		Comments comments = getComments();

		if (comments == null) {
			return null;
		}

		return comments.toString();
	}

	public void setComments(Comments comments) {
		this.comments = comments;

		_commentsSupplier = null;
	}

	@JsonIgnore
	public void setComments(
		UnsafeSupplier<Comments, Exception> commentsUnsafeSupplier) {

		_commentsSupplier = () -> {
			try {
				return commentsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The type of rating system to use")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Comments comments;

	@JsonIgnore
	private Supplier<Comments> _commentsSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The type of rating system to use"
	)
	@JsonGetter("documentsAndMedia")
	@Valid
	public DocumentsAndMedia getDocumentsAndMedia() {
		if (_documentsAndMediaSupplier != null) {
			documentsAndMedia = _documentsAndMediaSupplier.get();

			_documentsAndMediaSupplier = null;
		}

		return documentsAndMedia;
	}

	@JsonIgnore
	public String getDocumentsAndMediaAsString() {
		DocumentsAndMedia documentsAndMedia = getDocumentsAndMedia();

		if (documentsAndMedia == null) {
			return null;
		}

		return documentsAndMedia.toString();
	}

	public void setDocumentsAndMedia(DocumentsAndMedia documentsAndMedia) {
		this.documentsAndMedia = documentsAndMedia;

		_documentsAndMediaSupplier = null;
	}

	@JsonIgnore
	public void setDocumentsAndMedia(
		UnsafeSupplier<DocumentsAndMedia, Exception>
			documentsAndMediaUnsafeSupplier) {

		_documentsAndMediaSupplier = () -> {
			try {
				return documentsAndMediaUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The type of rating system to use")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DocumentsAndMedia documentsAndMedia;

	@JsonIgnore
	private Supplier<DocumentsAndMedia> _documentsAndMediaSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The type of rating system to use"
	)
	@JsonGetter("knowledgeBase")
	@Valid
	public KnowledgeBase getKnowledgeBase() {
		if (_knowledgeBaseSupplier != null) {
			knowledgeBase = _knowledgeBaseSupplier.get();

			_knowledgeBaseSupplier = null;
		}

		return knowledgeBase;
	}

	@JsonIgnore
	public String getKnowledgeBaseAsString() {
		KnowledgeBase knowledgeBase = getKnowledgeBase();

		if (knowledgeBase == null) {
			return null;
		}

		return knowledgeBase.toString();
	}

	public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;

		_knowledgeBaseSupplier = null;
	}

	@JsonIgnore
	public void setKnowledgeBase(
		UnsafeSupplier<KnowledgeBase, Exception> knowledgeBaseUnsafeSupplier) {

		_knowledgeBaseSupplier = () -> {
			try {
				return knowledgeBaseUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The type of rating system to use")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected KnowledgeBase knowledgeBase;

	@JsonIgnore
	private Supplier<KnowledgeBase> _knowledgeBaseSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The type of rating system to use"
	)
	@JsonGetter("messageBoards")
	@Valid
	public MessageBoards getMessageBoards() {
		if (_messageBoardsSupplier != null) {
			messageBoards = _messageBoardsSupplier.get();

			_messageBoardsSupplier = null;
		}

		return messageBoards;
	}

	@JsonIgnore
	public String getMessageBoardsAsString() {
		MessageBoards messageBoards = getMessageBoards();

		if (messageBoards == null) {
			return null;
		}

		return messageBoards.toString();
	}

	public void setMessageBoards(MessageBoards messageBoards) {
		this.messageBoards = messageBoards;

		_messageBoardsSupplier = null;
	}

	@JsonIgnore
	public void setMessageBoards(
		UnsafeSupplier<MessageBoards, Exception> messageBoardsUnsafeSupplier) {

		_messageBoardsSupplier = () -> {
			try {
				return messageBoardsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The type of rating system to use")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MessageBoards messageBoards;

	@JsonIgnore
	private Supplier<MessageBoards> _messageBoardsSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The type of rating system to use"
	)
	@JsonGetter("pageRatings")
	@Valid
	public PageRatings getPageRatings() {
		if (_pageRatingsSupplier != null) {
			pageRatings = _pageRatingsSupplier.get();

			_pageRatingsSupplier = null;
		}

		return pageRatings;
	}

	@JsonIgnore
	public String getPageRatingsAsString() {
		PageRatings pageRatings = getPageRatings();

		if (pageRatings == null) {
			return null;
		}

		return pageRatings.toString();
	}

	public void setPageRatings(PageRatings pageRatings) {
		this.pageRatings = pageRatings;

		_pageRatingsSupplier = null;
	}

	@JsonIgnore
	public void setPageRatings(
		UnsafeSupplier<PageRatings, Exception> pageRatingsUnsafeSupplier) {

		_pageRatingsSupplier = () -> {
			try {
				return pageRatingsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The type of rating system to use")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected PageRatings pageRatings;

	@JsonIgnore
	private Supplier<PageRatings> _pageRatingsSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The type of rating system to use"
	)
	@JsonGetter("webContent")
	@Valid
	public WebContent getWebContent() {
		if (_webContentSupplier != null) {
			webContent = _webContentSupplier.get();

			_webContentSupplier = null;
		}

		return webContent;
	}

	@JsonIgnore
	public String getWebContentAsString() {
		WebContent webContent = getWebContent();

		if (webContent == null) {
			return null;
		}

		return webContent.toString();
	}

	public void setWebContent(WebContent webContent) {
		this.webContent = webContent;

		_webContentSupplier = null;
	}

	@JsonIgnore
	public void setWebContent(
		UnsafeSupplier<WebContent, Exception> webContentUnsafeSupplier) {

		_webContentSupplier = () -> {
			try {
				return webContentUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The type of rating system to use")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected WebContent webContent;

	@JsonIgnore
	private Supplier<WebContent> _webContentSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The type of rating system to use"
	)
	@JsonGetter("wiki")
	@Valid
	public Wiki getWiki() {
		if (_wikiSupplier != null) {
			wiki = _wikiSupplier.get();

			_wikiSupplier = null;
		}

		return wiki;
	}

	@JsonIgnore
	public String getWikiAsString() {
		Wiki wiki = getWiki();

		if (wiki == null) {
			return null;
		}

		return wiki.toString();
	}

	public void setWiki(Wiki wiki) {
		this.wiki = wiki;

		_wikiSupplier = null;
	}

	@JsonIgnore
	public void setWiki(UnsafeSupplier<Wiki, Exception> wikiUnsafeSupplier) {
		_wikiSupplier = () -> {
			try {
				return wikiUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The type of rating system to use")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Wiki wiki;

	@JsonIgnore
	private Supplier<Wiki> _wikiSupplier;

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
		StringBundler sb = new StringBundler();

		sb.append("{");

		Blogs blogs = getBlogs();

		if (blogs != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"blogs\": ");

			sb.append("\"");
			sb.append(blogs);
			sb.append("\"");
		}

		Bookmarks bookmarks = getBookmarks();

		if (bookmarks != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bookmarks\": ");

			sb.append("\"");
			sb.append(bookmarks);
			sb.append("\"");
		}

		Comments comments = getComments();

		if (comments != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"comments\": ");

			sb.append("\"");
			sb.append(comments);
			sb.append("\"");
		}

		DocumentsAndMedia documentsAndMedia = getDocumentsAndMedia();

		if (documentsAndMedia != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentsAndMedia\": ");

			sb.append("\"");
			sb.append(documentsAndMedia);
			sb.append("\"");
		}

		KnowledgeBase knowledgeBase = getKnowledgeBase();

		if (knowledgeBase != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"knowledgeBase\": ");

			sb.append("\"");
			sb.append(knowledgeBase);
			sb.append("\"");
		}

		MessageBoards messageBoards = getMessageBoards();

		if (messageBoards != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"messageBoards\": ");

			sb.append("\"");
			sb.append(messageBoards);
			sb.append("\"");
		}

		PageRatings pageRatings = getPageRatings();

		if (pageRatings != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageRatings\": ");

			sb.append("\"");
			sb.append(pageRatings);
			sb.append("\"");
		}

		WebContent webContent = getWebContent();

		if (webContent != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"webContent\": ");

			sb.append("\"");
			sb.append(webContent);
			sb.append("\"");
		}

		Wiki wiki = getWiki();

		if (wiki != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"wiki\": ");

			sb.append("\"");
			sb.append(wiki);
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.admin.site.dto.v1_0.RatingsTypes",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Blogs")
	public static enum Blogs {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		@JsonCreator
		public static Blogs create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Blogs blogs : values()) {
				if (Objects.equals(blogs.getValue(), value)) {
					return blogs;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
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

	@GraphQLName("Bookmarks")
	public static enum Bookmarks {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		@JsonCreator
		public static Bookmarks create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Bookmarks bookmarks : values()) {
				if (Objects.equals(bookmarks.getValue(), value)) {
					return bookmarks;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
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

	@GraphQLName("Comments")
	public static enum Comments {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		@JsonCreator
		public static Comments create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Comments comments : values()) {
				if (Objects.equals(comments.getValue(), value)) {
					return comments;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
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

	@GraphQLName("DocumentsAndMedia")
	public static enum DocumentsAndMedia {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		@JsonCreator
		public static DocumentsAndMedia create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (DocumentsAndMedia documentsAndMedia : values()) {
				if (Objects.equals(documentsAndMedia.getValue(), value)) {
					return documentsAndMedia;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
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

	@GraphQLName("KnowledgeBase")
	public static enum KnowledgeBase {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		@JsonCreator
		public static KnowledgeBase create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (KnowledgeBase knowledgeBase : values()) {
				if (Objects.equals(knowledgeBase.getValue(), value)) {
					return knowledgeBase;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
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

	@GraphQLName("MessageBoards")
	public static enum MessageBoards {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		@JsonCreator
		public static MessageBoards create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (MessageBoards messageBoards : values()) {
				if (Objects.equals(messageBoards.getValue(), value)) {
					return messageBoards;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
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

	@GraphQLName("PageRatings")
	public static enum PageRatings {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		@JsonCreator
		public static PageRatings create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (PageRatings pageRatings : values()) {
				if (Objects.equals(pageRatings.getValue(), value)) {
					return pageRatings;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
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

	@GraphQLName("WebContent")
	public static enum WebContent {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		@JsonCreator
		public static WebContent create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (WebContent webContent : values()) {
				if (Objects.equals(webContent.getValue(), value)) {
					return webContent;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
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

	@GraphQLName("Wiki")
	public static enum Wiki {

		LIKE("like"), STACKED_STARS("stacked-stars"), STARS("stars"),
		THUMBS("thumbs");

		@JsonCreator
		public static Wiki create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Wiki wiki : values()) {
				if (Objects.equals(wiki.getValue(), value)) {
					return wiki;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
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

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}