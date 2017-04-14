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

package com.liferay.portal.search.web.internal.search.results.portlet;

/**
 * @author Lino Alves
 */
public interface SearchResultsPortletPreferences {

	public static final String DISPLAY_IN_DOCUMENT_FORM_PREFERENCE_KEY =
		"displayInDocumentForm";

	public static final String HIGHLIGHT_ENABLED_PREFERENCE_KEY =
		"highlightEnabled";

	public static final String PAGINATION_DELTA_PARAMETER_NAME_PREFERENCE_KEY =
		"paginationDeltaParameterName";

	public static final String PAGINATION_DELTA_PREFERENCE_KEY =
		"paginationDelta";

	public static final String PAGINATION_START_PARAMETER_NAME_PREFERENCE_KEY =
		"paginationStartParameterName";

	public static final String VIEW_IN_CONTEXT_PREFERENCE_KEY = "viewInContext";

	public int getPaginationDelta();

	public String getPaginationDeltaParameterName();

	public String getPaginationStartParameterName();

	public boolean isDisplayInDocumentForm();

	public boolean isHighlightEnabled();

	public boolean isViewInContext();

}