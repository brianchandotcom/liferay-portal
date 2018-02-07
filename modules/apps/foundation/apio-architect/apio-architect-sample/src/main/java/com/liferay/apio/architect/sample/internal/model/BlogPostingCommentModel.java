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

package com.liferay.apio.architect.sample.internal.model;

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
 * Represents a comment on a {@link BlogPostingModel}. This is a mock class for
 * sample purposes only. It contains methods for creating, retrieving, updating,
 * and deleting blog post comments in an in-memory database with fake data.
 *
 * @author Alejandro Hernández
 */
public class BlogPostingCommentModel {

	/**
	 * Adds a new blog posting comment.
	 *
	 * @param  authorId the ID of the blog posting comment's author
	 * @param  blogPostingId the blog posting comment's ID
	 * @param  content the the blog posting comment's content
	 * @return the new blog posting comment
	 */
	public static BlogPostingCommentModel addBlogPostingComment(
		long authorId, long blogPostingId, String content) {

		long blogPostingCommentId = _count.incrementAndGet();

		BlogPostingCommentModel blogPostingCommentModel =
			new BlogPostingCommentModel(
				authorId, blogPostingCommentId, blogPostingId, content,
				new Date(), new Date());

		Map<Long, BlogPostingCommentModel> blogPostingComments =
			_blogPostingComments.computeIfAbsent(
				blogPostingId, __ -> new HashMap<>());

		blogPostingComments.put(blogPostingCommentId, blogPostingCommentModel);

		return blogPostingCommentModel;
	}

	/**
	 * Deletes the blog posting comment that matches the specified ID.
	 *
	 * @param blogPostingCommentId the blog posting comment's ID
	 */
	public static void deleteBlogPostingComment(long blogPostingCommentId) {
		_blogPostingComments.remove(blogPostingCommentId);
	}

	/**
	 * Returns the blog posting comment that matches the specified ID, if that
	 * comment exists. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  blogPostingCommentId the blog posting comment's ID
	 * @return the blog posting comment, if present; {@code Optional#empty()}
	 *         otherwise
	 */
	public static Optional<BlogPostingCommentModel>
		getBlogPostingCommentOptional(long blogPostingCommentId) {

		Collection<Map<Long, BlogPostingCommentModel>> blogPostingComments =
			_blogPostingComments.values();

		Stream<Map<Long, BlogPostingCommentModel>> blogPostingCommentsStream =
			blogPostingComments.stream();

		return blogPostingCommentsStream.map(
			Map::values
		).map(
			Collection::stream
		).flatMap(
			stream -> stream
		).filter(
			blogPostingCommentModel ->
				blogPostingCommentModel.getBlogPostingCommentId() ==
					blogPostingCommentId
		).findFirst();
	}

	/**
	 * Returns the page of blog posting comments for a blog posting, as
	 * specified by the page's start and end positions.
	 *
	 * @param  blogPostingId the blog posting's ID
	 * @param  start the page's start position
	 * @param  end the page's end position
	 * @return the page of blog posting comments
	 */
	public static List<BlogPostingCommentModel> getBlogPostingComments(
		long blogPostingId, int start, int end) {

		Map<Long, BlogPostingCommentModel> blogPostingComments =
			_blogPostingComments.get(blogPostingId);

		Collection<BlogPostingCommentModel> blogPostingCommentsValueModels =
			blogPostingComments.values();

		Stream<BlogPostingCommentModel> stream =
			blogPostingCommentsValueModels.stream();

		return stream.skip(
			start
		).limit(
			end
		).collect(
			Collectors.toList()
		);
	}

	/**
	 * Returns the total number of blog posting comments for a blog posting.
	 *
	 * @param  blogPostingId the blog posting's ID
	 * @return the total number of blog posting comments for a blog posting
	 */
	public static int getBlogPostingCommentsCount(long blogPostingId) {
		if (_blogPostingComments.containsKey(blogPostingId)) {
			Map<Long, BlogPostingCommentModel> blogPostingComments =
				_blogPostingComments.get(blogPostingId);

			return blogPostingComments.size();
		}

		return 0;
	}

	/**
	 * Updates the blog posting comment that matches the specified ID, if that
	 * blog posting comment exists. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  blogPostingCommentId the blog posting comment's ID
	 * @param  content the blog posting comment's new content
	 * @return the updated blog posting comment, if present; {@code
	 *         Optional#empty()} otherwise
	 */
	public static Optional<BlogPostingCommentModel> updateBlogPostingComment(
		long blogPostingCommentId, String content) {

		Optional<BlogPostingCommentModel> oldBlogPostingCommentOptional =
			getBlogPostingCommentOptional(blogPostingCommentId);

		Optional<BlogPostingCommentModel> newBlogPostingCommentOptional =
			oldBlogPostingCommentOptional.map(
				blogPostingCommentModel -> new BlogPostingCommentModel(
					blogPostingCommentModel.getAuthorId(), blogPostingCommentId,
					blogPostingCommentModel.getBlogPostingId(), content,
					blogPostingCommentModel.getCreateDate(), new Date()));

		newBlogPostingCommentOptional.ifPresent(
			blogPostingCommentModel -> {
				long blogPostingId = blogPostingCommentModel.getBlogPostingId();

				Map<Long, BlogPostingCommentModel> blogPostingComments =
					_blogPostingComments.computeIfAbsent(
						blogPostingId, __ -> new HashMap<>());

				blogPostingComments.put(
					blogPostingCommentId, blogPostingCommentModel);
			});

		return newBlogPostingCommentOptional;
	}

	/**
	 * Returns the ID of the current blog posting comment's author.
	 *
	 * @return the ID of the current blog posting comment's author
	 */
	public long getAuthorId() {
		return _authorId;
	}

	/**
	 * Returns the current blog posting comment's ID.
	 *
	 * @return the current blog posting comment's ID
	 */
	public long getBlogPostingCommentId() {
		return _blogPostingCommentId;
	}

	/**
	 * Returns the ID of the current blog posting comment's blog posting.
	 *
	 * @return the ID of the current blog posting comment's blog posting
	 */
	public long getBlogPostingId() {
		return _blogPostingId;
	}

	/**
	 * Returns the current blog posting comment's content.
	 *
	 * @return the current blog posting comment's content
	 */
	public String getContent() {
		return _content;
	}

	/**
	 * Returns the current blog posting comment's creation date.
	 *
	 * @return the current blog posting comment's creation date
	 */
	public Date getCreateDate() {
		return new Date(_createDate.getTime());
	}

	/**
	 * Returns the current blog posting comment's modification date.
	 *
	 * @return the current blog posting comment's modification date
	 */
	public Date getModifiedDate() {
		return new Date(_modifiedDate.getTime());
	}

	private BlogPostingCommentModel(
		long authorId, long blogPostingCommentId, long blogPostingId,
		String content, Date createDate, Date modifiedDate) {

		_authorId = authorId;
		_blogPostingCommentId = blogPostingCommentId;
		_blogPostingId = blogPostingId;
		_content = content;
		_createDate = createDate;
		_modifiedDate = modifiedDate;
	}

	private static final Map<Long, Map<Long, BlogPostingCommentModel>>
		_blogPostingComments = new HashMap<>();
	private static final AtomicLong _count = new AtomicLong(0);

	static {
		for (long blogPostingId = 0;
			 blogPostingId < BlogPostingModel.getBlogPostingCount();
			 blogPostingId++) {

			Map<Long, BlogPostingCommentModel> blogPostingComments =
				new HashMap<>();
			Random random = new Random();

			for (int i = 0; i < random.nextInt(70); i++) {
				long authorId = random.nextInt(PersonModel.getPeopleCount());

				long blogPostingCommentId = _count.getAndIncrement();

				Faker faker = new Faker();

				Shakespeare shakespeare = faker.shakespeare();

				DateAndTime dateAndTime = faker.date();

				Date date = dateAndTime.past(400, TimeUnit.DAYS);

				BlogPostingCommentModel blogPostingCommentModel =
					new BlogPostingCommentModel(
						authorId, blogPostingCommentId, blogPostingId,
						shakespeare.hamletQuote(), date, date);

				blogPostingComments.put(
					blogPostingCommentId, blogPostingCommentModel);
			}

			_blogPostingComments.put(blogPostingId, blogPostingComments);
		}
	}

	private final long _authorId;
	private final long _blogPostingCommentId;
	private final long _blogPostingId;
	private final String _content;
	private final Date _createDate;
	private final Date _modifiedDate;

}