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

package com.liferay.wiki.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Iván Zaera
 * @author Roberto Díaz
 */
public class WikiAttachmentItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public WikiAttachmentItemSelectorCriterion() {
	}

	public WikiAttachmentItemSelectorCriterion(long wikiPageResourceId) {
		this(wikiPageResourceId, new String[0]);
	}

	public WikiAttachmentItemSelectorCriterion(
		long wikiPageResourceId, String[] mimeTypes) {

		_wikiPageResourceId = wikiPageResourceId;
		_mimeTypes = mimeTypes;
	}

	public String[] getMimeTypes() {
		return _mimeTypes;
	}

	public long getWikiPageResourceId() {
		return _wikiPageResourceId;
	}

	public void setMimeTypes(String[] mimeTypes) {
		_mimeTypes = mimeTypes;
	}

	public void setWikiPageResourceId(long wikiPageResourceId) {
		_wikiPageResourceId = wikiPageResourceId;
	}

	private String[] _mimeTypes;
	private long _wikiPageResourceId;

}