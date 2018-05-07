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

package com.liferay.rss.model;

import java.util.Date;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public interface SyndFeed {

	public String getDescription();

	public List<SyndEntry> getEntries();

	public String getFeedType();

	public List<SyndLink> getLinks();

	public Date getPublishedDate();

	public String getTitle();

	public String getUri();

	public void setDescription(String description);

	public void setEntries(List<SyndEntry> syndEntries);

	public void setFeedType(String feedType);

	public void setLinks(List<SyndLink> syndLinks);

	public void setPublishedDate(Date publishedDate);

	public void setTitle(String title);

	public void setUri(String uri);

}