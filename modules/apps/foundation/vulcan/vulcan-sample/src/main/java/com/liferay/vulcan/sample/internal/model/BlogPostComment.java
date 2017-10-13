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

package com.liferay.vulcan.sample.internal.model;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import com.github.javafaker.Shakespeare;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Instances of this class represents a {@link BlogPost} comment. This is a mock
 * class for sample purposes. It contains methods for
 * retrieving/updating/deleting blog post comments and a in-memory database with
 * fake data.
 *
 * @author Alejandro Hernández
 * @review
 */
public class BlogPostComment {

	/**
	 * Adds a new {@code BlogPostComment} to the database.
	 *
	 * @param  blogPostId the ID of the {@link BlogPost} comment.
	 * @param  authorId the ID of the author of the {@link BlogPost} comment.
	 * @param  content the content of the {@link BlogPost} comment.
	 * @return the added {@code BlogPostComment}.
	 * @review
	 */
	public static BlogPostComment addBlogPostComment(
		Long blogPostId, Long authorId, String content) {

		long id = _count.incrementAndGet();

		BlogPostComment blogPostComment = new BlogPostComment(
			id, blogPostId, authorId, content, new Date(), new Date());

		Map<Long, BlogPostComment> blogPostComments =
			_blogPostCommentsMap.computeIfAbsent(
				blogPostId, __ -> new HashMap<>());

		blogPostComments.put(id, blogPostComment);

		return blogPostComment;
	}

	/**
	 * Deletes a {@code BlogPostComment} with a certain {@code ID} from the
	 * database.
	 *
	 * @param  id the ID of the {@link BlogPost} comment to delete.
	 * @review
	 */
	public static void deleteBlogPostComment(long id) {
		_blogPostCommentsMap.remove(id);
	}

	/**
	 * Returns a {@code BlogPostComment} with a certain {@code ID} from the
	 * database if present. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  id the ID of the blog post comment to retrieve.
	 * @return the {@code BlogPostComment} for the requested ID if present;
	 *         {@code Optional#empty()} otherwise.
	 * @review
	 */
	public static Optional<BlogPostComment> getBlogPostComment(long id) {
		Collection<Map<Long, BlogPostComment>> blogPostComments =
			_blogPostCommentsMap.values();

		Stream<Map<Long, BlogPostComment>> blogPostCommentsStream =
			blogPostComments.stream();

		return blogPostCommentsStream.map(
			Map::values
		).map(
			Collection::stream
		).flatMap(
			stream -> stream
		).filter(
			blogPostComment -> blogPostComment.getId() == id
		).findFirst();
	}

	/**
	 * Returns a page of {@code BlogPostComment} of {@link BlogPost} from the
	 * database.
	 *
	 * @param  blogPostId the ID of the {@link BlogPost}.
	 * @param  start the start position.
	 * @param  end the end position.
	 * @return the list of blog post comments between {@code start} and {@code
	 *         end} of a {@link BlogPost}.
	 * @review
	 */
	public static List<BlogPostComment> getBlogPostComments(
		long blogPostId, int start, int end) {

		Map<Long, BlogPostComment> blogPostComments = _blogPostCommentsMap.get(
			blogPostId);

		Collection<BlogPostComment> blogPostCommentsValues =
			blogPostComments.values();

		Stream<BlogPostComment> stream = blogPostCommentsValues.stream();

		return stream.skip(
			start
		).limit(
			end
		).collect(
			Collectors.toList()
		);
	}

	/**
	 * Return the total number of comments for a {@link BlogPost} in the
	 * database.
	 *
	 * @param  blogPostId the ID of the {@link BlogPost}.
	 * @return the total number of comments for a {@link BlogPost} in the
	 *         database.
	 * @review
	 */
	public static int getBlogPostCommentsCount(long blogPostId) {
		if (_blogPostCommentsMap.containsKey(blogPostId)) {
			Map<Long, BlogPostComment> blogPostComments =
				_blogPostCommentsMap.get(blogPostId);

			return blogPostComments.size();
		}

		return 0;
	}

	/**
	 * Updates a {@code BlogPostComment} with a certain {@code ID} in the
	 * database if present.
	 *
	 * @param  id the ID of the {@link BlogPost} comment to update.
	 * @param  authorId the ID of the author of the {@link BlogPost} comment.
	 * @param  content the content of the {@link BlogPost} comment.
	 * @return the updated {@code BlogPostComment} if present; {@code
	 *         Optional#empty()} otherwise.
	 * @review
	 */
	public static Optional<BlogPostComment> updateBlogPostComment(
		long id, Long authorId, String content) {

		Optional<BlogPostComment> oldBlogPostComment = getBlogPostComment(id);

		Optional<BlogPostComment> newBlogPostComment = oldBlogPostComment.map(
			blogPostComment -> {
				Date createDate = blogPostComment.getCreateDate();

				Long blogPostId = blogPostComment.getBlogPostId();

				return new BlogPostComment(
					id, blogPostId, authorId, content, createDate, new Date());
			});

		newBlogPostComment.ifPresent(
			blogPostComment -> {
				Long blogPostId = blogPostComment.getBlogPostId();

				Map<Long, BlogPostComment> blogPostComments =
					_blogPostCommentsMap.computeIfAbsent(
						blogPostId, __ -> new HashMap<>());

				blogPostComments.put(id, blogPostComment);
			});

		return newBlogPostComment;
	}

	/**
	 * The ID of the author of this {@code BlogPostComment}.
	 *
	 * @return the ID of the author of the {@link BlogPost} comment.
	 * @review
	 */
	public Long getAuthorId() {
		return _authorId;
	}

	/**
	 * Returns the ID of the {@link BlogPost} of this {@code BlogPostComment}.
	 *
	 * @return the ID of the {@link BlogPost} of the {@link BlogPost} comment.
	 * @review
	 */
	public Long getBlogPostId() {
		return _blogPostId;
	}

	/**
	 * Returns the content of this {@code BlogPostComment}.
	 *
	 * @return the content of the {@link BlogPost} comment.
	 * @review
	 */
	public String getContent() {
		return _content;
	}

	/**
	 * The create date of this {@code BlogPostComment}.
	 *
	 * @return the create date of the {@link BlogPost} comment.
	 * @review
	 */
	public Date getCreateDate() {
		return _createDate;
	}

	/**
	 * The ID of this {@code BlogPostComment}.
	 *
	 * @return the ID of the {@link BlogPost} comment.
	 * @review
	 */
	public long getId() {
		return _id;
	}

	/**
	 * Returns the modified date of this {@code BlogPostComment}.
	 *
	 * @return the modified date of the {@link BlogPost} comment.
	 * @review
	 */
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	private BlogPostComment(
		long id, Long blogPostId, Long authorId, String content,
		Date createDate, Date modifiedDate) {

		_id = id;
		_blogPostId = blogPostId;
		_authorId = authorId;
		_content = content;
		_createDate = createDate;
		_modifiedDate = modifiedDate;
	}

	private static Map<Long, Map<Long, BlogPostComment>> _blogPostCommentsMap;
	private static final AtomicLong _count = new AtomicLong(0);

	static {
		_blogPostCommentsMap = new HashMap<>();

		Random random = new Random();

		for (long i = 0; i < BlogPost.getBlogPostsCount(); i++) {
			HashMap<Long, BlogPostComment> blogPostComments = new HashMap<>();

			for (int j = 0; j < random.nextInt(70); j++) {
				long id = _count.getAndIncrement();

				Faker faker = new Faker();

				long creatorId = random.nextInt(User.getUsersCount());

				Shakespeare shakespeare = faker.shakespeare();

				String content = shakespeare.hamletQuote();

				DateAndTime dateAndTime = faker.date();

				Date createDate = dateAndTime.past(400, TimeUnit.DAYS);

				BlogPostComment blogPostComment = new BlogPostComment(
					id, i, creatorId, content, createDate, createDate);

				blogPostComments.put(id, blogPostComment);
			}

			_blogPostCommentsMap.put(i, blogPostComments);
		}
	}

	private final Long _authorId;
	private final Long _blogPostId;
	private final String _content;
	private final Date _createDate;
	private final long _id;
	private final Date _modifiedDate;

}