/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model;

import java.util.HashMap;
import java.util.Map;

// simple composite as the fastest solution
public class JournalArticleComposite {

	public void addContent(String name, String value) {
		this._content.put(name, value);
	}

	public JournalArticle getArticle() {
		return _article;
	}

	public Map<String, String> getContent() {
		return _content;
	}

	public long getId() {
		return _id;
	}

	public void setArticle(JournalArticle article) {
		_article = article;
		_id = article.getId();
	}

	public void setContent(Map<String, String> content) {
		this._content = content;
	}

	public void setId(long id) {
		_id = id;
	}

	private JournalArticle _article;
	private Map<String, String> _content = new HashMap<String, String>();
	private long _id;

}