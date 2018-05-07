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
public interface SyndEntry {

	public String getAuthor();

	public SyndContent getDescription();

	public List<SyndEnclosure> getEnclosures();

	public String getLink();

	public List<SyndLink> getLinks();

	public Date getPublishedDate();

	public String getTitle();

	public Date getUpdatedDate();

	public String getUri();

	public void setAuthor(String author);

	public void setDescription(SyndContent description);

	public void setEnclosures(List<SyndEnclosure> syndEnclosures);

	public void setLink(String link);

	public void setLinks(List<SyndLink> syndLinks);

	public void setPublishedDate(Date publishedDate);

	public void setTitle(String title);

	public void setUpdatedDate(Date updatedDate);

	public void setUri(String uri);

}