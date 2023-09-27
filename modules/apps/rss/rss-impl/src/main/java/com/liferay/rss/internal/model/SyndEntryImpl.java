/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.rss.internal.model;

import com.liferay.rss.model.SyndContent;
import com.liferay.rss.model.SyndEnclosure;
import com.liferay.rss.model.SyndEntry;
import com.liferay.rss.model.SyndLink;

import java.util.Date;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class SyndEntryImpl implements SyndEntry {

	@Override
	public String getAuthor() {
		return _author;
	}

	@Override
	public SyndContent getDescription() {
		return _description;
	}

	@Override
	public List<SyndEnclosure> getEnclosures() {
		return _syndEnclosures;
	}

	@Override
	public String getLink() {
		return _link;
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
	public Date getUpdatedDate() {
		return _updatedDate;
	}

	@Override
	public String getUri() {
		return _uri;
	}

	@Override
	public void setAuthor(String author) {
		_author = author;
	}

	@Override
	public void setDescription(SyndContent description) {
		_description = description;
	}

	@Override
	public void setEnclosures(List<SyndEnclosure> syndEnclosures) {
		_syndEnclosures = syndEnclosures;
	}

	@Override
	public void setLink(String link) {
		_link = link;
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
	public void setUpdatedDate(Date updatedDate) {
		_updatedDate = updatedDate;
	}

	@Override
	public void setUri(String uri) {
		_uri = uri;
	}

	private String _author;
	private SyndContent _description;
	private String _link;
	private Date _publishedDate;
	private List<SyndEnclosure> _syndEnclosures;
	private List<SyndLink> _syndLinks;
	private String _title;
	private Date _updatedDate;
	private String _uri;

}