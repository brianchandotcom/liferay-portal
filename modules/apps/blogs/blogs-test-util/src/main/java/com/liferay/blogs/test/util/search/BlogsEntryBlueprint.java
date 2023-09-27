/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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