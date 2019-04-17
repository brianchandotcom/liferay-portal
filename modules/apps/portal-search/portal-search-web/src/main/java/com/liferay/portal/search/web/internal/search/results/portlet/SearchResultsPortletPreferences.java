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

import java.util.Optional;

/**
 * @author Lino Alves
 */
public interface SearchResultsPortletPreferences {

	public static final String PREFERENCE_KEY_DISPLAY_IN_DOCUMENT_FORM =
		"displayInDocumentForm";

	public static final String PREFERENCE_KEY_FEDERATED_SEARCH_KEY =
		"federatedSearchKey";

	public static final String PREFERENCE_KEY_FIELDS_TO_DISPLAY =
		"fieldsToDisplay";

	public static final String PREFERENCE_KEY_HIGHLIGHT_ENABLED =
		"highlightEnabled";

	public static final String PREFERENCE_KEY_PAGINATION_DELTA =
		"paginationDelta";

	public static final String PREFERENCE_KEY_PAGINATION_DELTA_PARAMETER_NAME =
		"paginationDeltaParameterName";

	public static final String PREFERENCE_KEY_PAGINATION_START_PARAMETER_NAME =
		"paginationStartParameterName";

	public static final String PREFERENCE_KEY_VIEW_IN_CONTEXT = "viewInContext";

	public Optional<String> getFederatedSearchKeyOptional();

	public String getFederatedSearchKeyString();

	public Optional<String> getFieldsToDisplayOptional();

	public String getFieldsToDisplayString();

	public int getPaginationDelta();

	public String getPaginationDeltaParameterName();

	public String getPaginationStartParameterName();

	public boolean isDisplayInDocumentForm();

	public boolean isHighlightEnabled();

	public boolean isViewInContext();

}