/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portlet.journal.model.JournalArticleDisplay;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalArticleDisplayImpl implements JournalArticleDisplay {

	public JournalArticleDisplayImpl(
		long companyId, long id, long resourcePrimKey, long groupId,
		long userId, String articleId, double version, String title,
		String urlTitle, String description, String[] availableLocales,
		String content, String type, String ddmStructureKey,
		String ddmTemplateKey, boolean smallImage, long smallImageId,
		String smallImageURL, int numberOfPages, int currentPage,
		boolean paginate, boolean cacheable) {

		_companyId = companyId;
		_id = id;
		_resourcePrimKey = resourcePrimKey;
		_groupId = groupId;
		_userId = userId;
		_articleId = articleId;
		_version = version;
		_title = title;
		_urlTitle = urlTitle;
		_description = description;
		_availableLocales = availableLocales;
		_content = content;
		_type = type;
		_ddmStructureKey = ddmStructureKey;
		_ddmTemplateKey = ddmTemplateKey;
		_smallImage = smallImage;
		_smallImageId = smallImageId;
		_smallImageURL = smallImageURL;
		_numberOfPages = numberOfPages;
		_currentPage = currentPage;
		_paginate = paginate;
		_cacheable = cacheable;
	}

	@Override
	public String getArticleId() {
		return _articleId;
	}

	@Override
	public String[] getAvailableLocales() {
		return _availableLocales;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public String getContent() {
		return _content;
	}

	@Override
	public int getCurrentPage() {
		return _currentPage;
	}

	@Override
	public String getDDMStructureKey() {
		return _ddmStructureKey;
	}

	@Override
	public String getDDMTemplateKey() {
		return _ddmTemplateKey;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public int getNumberOfPages() {
		return _numberOfPages;
	}

	@Override
	public long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	@Override
	public long getSmallImageId() {
		return _smallImageId;
	}

	@Override
	public String getSmallImageURL() {
		return _smallImageURL;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public String getUrlTitle() {
		return _urlTitle;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public double getVersion() {
		return _version;
	}

	@Override
	public boolean isCacheable() {
		return _cacheable;
	}

	@Override
	public boolean isPaginate() {
		return _paginate;
	}

	@Override
	public boolean isSmallImage() {
		return _smallImage;
	}

	@Override
	public void setCacheable(boolean cacheable) {
		_cacheable = cacheable;
	}

	@Override
	public void setContent(String content) {
		_content = content;
	}

	@Override
	public void setCurrentPage(int currentPage) {
		_currentPage = currentPage;
	}

	@Override
	public void setDDMStructureKey(String ddmStructureKey) {
		_ddmStructureKey = ddmStructureKey;
	}

	@Override
	public void setDDMTemplateKey(String ddmTemplateKey) {
		_ddmTemplateKey = ddmTemplateKey;
	}

	@Override
	public void setNumberOfPages(int numberOfPages) {
		_numberOfPages = numberOfPages;
	}

	@Override
	public void setPaginate(boolean paginate) {
		_paginate = paginate;
	}

	@Override
	public void setSmallImage(boolean smallImage) {
		_smallImage = smallImage;
	}

	@Override
	public void setSmallImageId(long smallImageId) {
		_smallImageId = smallImageId;
	}

	@Override
	public void setSmallImageURL(String smallImageURL) {
		_smallImageURL = smallImageURL;
	}

	private String _articleId;
	private String[] _availableLocales;
	private boolean _cacheable;
	private long _companyId;
	private String _content;
	private int _currentPage;
	private String _ddmStructureKey;
	private String _ddmTemplateKey;
	private String _description;
	private long _groupId;
	private long _id;
	private int _numberOfPages;
	private boolean _paginate;
	private long _resourcePrimKey;
	private boolean _smallImage;
	private long _smallImageId;
	private String _smallImageURL;
	private String _title;
	private String _type;
	private String _urlTitle;
	private long _userId;
	private double _version;

}