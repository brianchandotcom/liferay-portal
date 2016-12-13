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

package com.liferay.portal.search.web.internal.result.display.builder;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.search.web.internal.display.context.SearchResultPreferences;

import java.util.Locale;

/**
 * @author André de Oliveira
 */
public class SearchResultSummary {

	public AssetRenderer<?> assetRenderer;
	public String className;
	public long classPK;
	public String currentURL;
	public Document document;
	public Locale locale;
	public SearchResultPreferences searchResultPreferences;
	public Summary summary;
	public String viewURL;

}