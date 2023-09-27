/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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