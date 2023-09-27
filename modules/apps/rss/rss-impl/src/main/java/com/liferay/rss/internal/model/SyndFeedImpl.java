/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.rss.internal.model;

import com.liferay.rss.model.SyndEntry;
import com.liferay.rss.model.SyndFeed;
import com.liferay.rss.model.SyndLink;

import java.util.Date;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class SyndFeedImpl implements SyndFeed {

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public List<SyndEntry> getEntries() {
		return _syndEntries;
	}

	@Override
	public String getFeedType() {
		return _feedType;
	}

	@Override
	public List<SyndLink> getLinks() {
		return _syndLinks;
	}

	@Override
	public Date getPublishedDate() {
		return _publishedDate;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public String getUri() {
		return _uri;
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public void setEntries(List<SyndEntry> syndEntries) {
		_syndEntries = syndEntries;
	}

	@Override
	public void setFeedType(String feedType) {
		_feedType = feedType;
	}

	@Override
	public void setLinks(List<SyndLink> syndLinks) {
		_syndLinks = syndLinks;
	}

	@Override
	public void setPublishedDate(Date publishedDate) {
		_publishedDate = publishedDate;
	}

	@Override
	public void setTitle(String title) {
		_title = title;
	}

	@Override
	public void setUri(String uri) {
		_uri = uri;
	}

	private String _description;
	private String _feedType;
	private Date _publishedDate;
	private List<SyndEntry> _syndEntries;
	private List<SyndLink> _syndLinks;
	private String _title;
	private String _uri;

}