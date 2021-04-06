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

package com.liferay.blogs.test.util.search;

import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author André de Oliveira
 */
public class BlogsEntryBlueprint {

	public BlogsEntryBlueprint() {
	}

	public BlogsEntryBlueprint(BlogsEntryBlueprint blogsEntryBlueprint) {
		_content = blogsEntryBlueprint._content;
		_title = blogsEntryBlueprint._title;
		_groupId = blogsEntryBlueprint._groupId;
		_serviceContext = blogsEntryBlueprint._serviceContext;
		_userId = blogsEntryBlueprint._userId;
	}

	public String getContent() {
		return _content;
	}

	public long getGroupId() {
		return _groupId;
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public String getTitle() {
		return _title;
	}

	public long getUserId() {
		return _userId;
	}

	public static class BlogsEntryBlueprintBuilder {

		public static BlogsEntryBlueprintBuilder builder() {
			return new BlogsEntryBlueprintBuilder();
		}

		public BlogsEntryBlueprint build() {
			return new BlogsEntryBlueprint(_blogsEntryBlueprint);
		}

		public BlogsEntryBlueprintBuilder content(String content) {
			_blogsEntryBlueprint._content = content;

			return this;
		}

		public BlogsEntryBlueprintBuilder groupId(long groupId) {
			_blogsEntryBlueprint._groupId = groupId;

			return this;
		}

		public BlogsEntryBlueprintBuilder serviceContext(
			ServiceContext serviceContext) {

			_blogsEntryBlueprint._serviceContext = serviceContext;

			return this;
		}

		public BlogsEntryBlueprintBuilder title(String title) {
			_blogsEntryBlueprint._title = title;

			return this;
		}

		public BlogsEntryBlueprintBuilder userId(long userId) {
			_blogsEntryBlueprint._userId = userId;

			return this;
		}

		private final BlogsEntryBlueprint _blogsEntryBlueprint =
			new BlogsEntryBlueprint();

	}

	private String _content;
	private long _groupId;
	private ServiceContext _serviceContext;
	private String _title;
	private long _userId;

}