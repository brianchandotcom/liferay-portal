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

package com.liferay.commerce.product.taglib.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.ManagementToolbarTag;

/**
 * @author Carlos Lancha
 */
public class ManagementToolbarWithExtraFiltersTag extends ManagementToolbarTag {

	public ManagementToolbarWithExtraFiltersTag() {
		super(
	"management-toolbar-with-extra-filters",
	"com.liferay.commerce.product.taglib.ManagementToolbarWithExtraFilters",
	true);
	}

	@Override
	public String getModule() {
		return "commerce-product-taglib/management_toolbar_with_extra_filters/ManagementToolbarWithExtraFilters.es";
	}

	public void setCategorySelectorURL(String categorySelectorURL) {
		putValue("categorySelectorURL", categorySelectorURL);
	}

	public void setCpDefinitionsFacetsURL(String cpDefinitionsFacetsURL) {
		putValue("cpDefinitionsFacetsURL", cpDefinitionsFacetsURL);
	}

	public void setGroupIds(String groupIds) {
		putValue("groupIds", groupIds);
	}

	@Override
	public void setNamespace(String namespace) {
		super.setNamespace(namespace);

		putValue("namespace", namespace);
	}

	public void setPortletURL(String portletURL) {
		putValue("portletURL", portletURL);
	}

	public void setVocabularyIds(String vocabularyIds) {
		putValue("vocabularyIds", vocabularyIds);
	}

}