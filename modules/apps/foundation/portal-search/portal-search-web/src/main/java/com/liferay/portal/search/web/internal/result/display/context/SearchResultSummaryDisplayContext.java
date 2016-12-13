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

package com.liferay.portal.search.web.internal.result.display.context;

import java.io.Serializable;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author André de Oliveira
 */
public class SearchResultSummaryDisplayContext implements Serializable {

	public long assetEntryUserId;
	public String assetRendererURLDownload;
	public String className;
	public long classPK;
	public String content;
	public List<SearchResultFieldDisplayContext>
		documentFormFieldDisplayContexts;
	public String fieldAssetCategoryIds;
	public String fieldAssetTagNames;
	public boolean hasAssetCategoriesOrTags;
	public boolean hasAssetRendererURLDownload;
	public boolean hasContent;
	public boolean hasDocumentForm;
	public boolean hasLocaleReminder;
	public boolean hasUserPortrait;
	public String highlightedTitle;
	public String localeLanguageId;
	public String localeReminder;
	public String modelResource;
	public PortletURL portletURL;
	public String title;
	public String viewURL;

}