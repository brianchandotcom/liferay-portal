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

package com.liferay.blogs.rest.internal.model;

import com.liferay.blogs.model.BlogsEntry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alejandro Hernández
 */
@XmlRootElement
public class BlogsEntryRestModel {

	public BlogsEntryRestModel() {
	}

	public BlogsEntryRestModel(BlogsEntry blogsEntry) {
		_title = blogsEntry.getTitle();
		_content = blogsEntry.getContent();
		_entryId = blogsEntry.getEntryId();
	}

	public String getContent() {
		return _content;
	}

	@XmlElement(name = "id")
	public long getEntryId() {
		return _entryId;
	}

	public String getTitle() {
		return _title;
	}

	public void setContent(String content) {
		_content = content;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _content;
	private long _entryId;
	private String _title;

}