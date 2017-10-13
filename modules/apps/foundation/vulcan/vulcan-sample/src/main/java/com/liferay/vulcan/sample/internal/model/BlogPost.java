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

import com.github.javafaker.Book;
import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import com.github.javafaker.Lorem;
import com.github.javafaker.service.RandomService;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Instances of this class represents a blog post. This is a mock class for
 * sample purposes. It contains methods for retrieving/updating/deleting blog
 * posts and a in-memory database with fake data.
 *
 * @author Alejandro Hernández
 * @review
 */
public class BlogPost {

	/**
	 * Adds a new {@code BlogPost} to the database.
	 *
	 * @param  title the title of the blog post.
	 * @param  subtitle the subtitle of the blog post.
	 * @param  content the content of the blog post.
	 * @param  creatorId the ID of the creator of this blog post.
	 * @return the added {@code BlogPost}.
	 * @review
	 */
	public static BlogPost addBlogPost(
		String title, String subtitle, String content, long creatorId) {

		long id = _count.incrementAndGet();

		BlogPost blogPost = new BlogPost(
			id, title, subtitle, content, creatorId, new Date(), new Date());

		_blogPosts.put(id, blogPost);

		return blogPost;
	}

	/**
	 * Deletes a {@code BlogPost} with a certain {@code ID} from the database.
	 *
	 * @param  id the ID of the blog post to delete.
	 * @review
	 */
	public static void deleteBlogPost(long id) {
		_blogPosts.remove(id);
	}

	/**
	 * Returns a {@code BlogPost} with a certain {@code ID} from the database if
	 * present. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  id the ID of the blog post to retrieve.
	 * @return the {@code BlogPost} for the requested ID if present; {@code
	 *         Optional#empty()} otherwise.
	 * @review
	 */
	public static Optional<BlogPost> getBlogPost(long id) {
		BlogPost blogPost = _blogPosts.get(id);

		return Optional.ofNullable(blogPost);
	}

	/**
	 * Returns a page of {@code BlogPost} from the database.
	 *
	 * @param  start the start position.
	 * @param  end the end position.
	 * @return the list of blog posts between {@code start} and {@code end}.
	 * @review
	 */
	public static List<BlogPost> getBlogPosts(int start, int end) {
		Collection<BlogPost> blogPosts = _blogPosts.values();

		Stream<BlogPost> stream = blogPosts.stream();

		return stream.skip(
			start
		).limit(
			end
		).collect(
			Collectors.toList()
		);
	}

	/**
	 * Return the total number of blog posts in the database.
	 *
	 * @return the total number of blog posts in the database.
	 * @review
	 */
	public static int getBlogPostsCount() {
		return _blogPosts.size();
	}

	/**
	 * Updates a {@code BlogPost} with a certain {@code ID} in the database, if
	 * present.
	 *
	 * @param  id the ID of the blog post to update.
	 * @param  title the new title for the blog post.
	 * @param  subtitle the new subtitle for the blog post.
	 * @param  content the new content for the blog post.
	 * @param  creatorId the ID of the new creator for the blog post.
	 * @return the updated {@code BlogPost} if present; {@code Optional#empty()}
	 *         otherwise.
	 * @review
	 */
	public static Optional<BlogPost> updateBlogPost(
		long id, String title, String subtitle, String content,
		long creatorId) {

		BlogPost blogPost = _blogPosts.get(id);

		if (blogPost == null) {
			return Optional.empty();
		}

		Date createDate = blogPost.getCreateDate();

		blogPost = new BlogPost(
			id, title, subtitle, content, creatorId, createDate, new Date());

		_blogPosts.put(id, blogPost);

		return Optional.of(blogPost);
	}

	/**
	 * Returns the content of this {@code BlogPost}.
	 *
	 * @return the content of the blog post.
	 * @review
	 */
	public String getContent() {
		return _content;
	}

	/**
	 * Returns the create date of this {@code BlogPost}.
	 *
	 * @return the create date of the blog post.
	 * @review
	 */
	public Date getCreateDate() {
		return _createDate;
	}

	/**
	 * Returns the ID of the creator of this {@code BlogPost}.
	 *
	 * @return the ID of the creator the blog post.
	 * @review
	 */
	public long getCreatorId() {
		return _creatorId;
	}

	/**
	 * Returns the ID of this {@code BlogPost}.
	 *
	 * @return the ID of the blog post.
	 * @review
	 */
	public long getId() {
		return _id;
	}

	/**
	 * Returns the modified date of this {@code BlogPost}.
	 *
	 * @return the modified date of the blog post.
	 * @review
	 */
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	/**
	 * Returns the subtitle of this {@code BlogPost}.
	 *
	 * @return the subtitle of the blog post.
	 * @review
	 */
	public String getSubtitle() {
		return _subtitle;
	}

	/**
	 * Returns the title of this {@code BlogPost}.
	 *
	 * @return the title of the blog post.
	 * @review
	 */
	public String getTitle() {
		return _title;
	}

	private BlogPost(
		long id, String title, String subtitle, String content, long creatorId,
		Date createDate, Date modifiedDate) {

		_id = id;
		_title = title;
		_subtitle = subtitle;
		_content = content;
		_creatorId = creatorId;
		_createDate = createDate;
		_modifiedDate = modifiedDate;
	}

	private static Map<Long, BlogPost> _blogPosts;
	private static final AtomicLong _count = new AtomicLong(30);

	static {
		_blogPosts = new HashMap<>();

		for (long i = 0; i < 42; i++) {
			Faker faker = new Faker();

			DateAndTime dateAndTime = faker.date();

			Book book = faker.book();

			String title = book.title();

			Lorem lorem = faker.lorem();

			Date date = dateAndTime.past(400, TimeUnit.DAYS);
			String subtitle = lorem.sentence();
			List<String> paragraphs = lorem.paragraphs(5);

			Stream<String> stream = paragraphs.stream();

			String content = stream.map(
				paragraph -> "<p>" + paragraph + "</p>"
			).collect(
				Collectors.joining()
			);

			RandomService random = faker.random();

			int creatorId = random.nextInt(User.getUsersCount());

			BlogPost blogPost = new BlogPost(
				i, title, subtitle, content, creatorId, date, date);

			_blogPosts.put(i, blogPost);
		}
	}

	private final String _content;
	private final Date _createDate;
	private final long _creatorId;
	private final long _id;
	private final Date _modifiedDate;
	private final String _subtitle;
	private final String _title;

}