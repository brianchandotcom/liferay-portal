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

package com.liferay.portal.search.web.internal.searchresults.portlet;

/**
 * @author Lino Alves
 */
public interface SearchResultsPortletPreferences {

	public static final boolean DEFAULT_DISPLAY_IN_DOCUMENT_FORM = false;

	public static final boolean DEFAULT_HIGHLIGHT_ENABLED = true;

	public static final int DEFAULT_PAGINATION_DELTA = 20;

	public static final String DEFAULT_PAGINATION_DELTA_PARAMETER_NAME =
		"delta";

	public static final String DEFAULT_PAGINATION_START_PARAMETER_NAME =
		"start";

	public static final boolean DEFAULT_VIEW_IN_CONTEXT = true;

	public static final String PREFERENCE_DISPLAY_IN_DOCUMENT_FORM =
		"displayInDocumentForm";

	public static final String PREFERENCE_HIGHLIGHT_ENABLED =
		"highlightEnabled";

	public static final String PREFERENCE_PAGINATION_DELTA = "delta";

	public static final String PREFERENCE_PAGINATION_DELTA_PARAMETER_NAME =
		"paginationDeltaParameterName";

	public static final String PREFERENCE_PAGINATION_START_PARAMETER_NAME =
		"paginationStartParameterName";

	public static final String PREFERENCE_VIEW_IN_CONTEXT = "viewInContext";

	public int getPaginationDelta();

	public String getPaginationDeltaParameterName();

	public String getPaginationStartParameterName();

	public boolean isDisplayInDocumentForm();

	public boolean isHighlightEnabled();

	public boolean isViewInContext();

}