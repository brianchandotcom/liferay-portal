/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet.category;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.configuration.CategoryFacetFieldConfiguration;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.category.CategoryFacetFactory;
import com.liferay.portal.search.internal.facet.FacetImpl;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bryan Engler
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.CategoryFacetFieldConfiguration",
	service = CategoryFacetFactory.class
)
public class CategoryFacetFactoryImpl implements CategoryFacetFactory {

	@Override
	public String getFacetClassName() {
		return _categoryFacetFieldConfiguration.categoryFacetField();
	}

	@Override
	public Facet newInstance(SearchContext searchContext) {
		return new FacetImpl(
			_categoryFacetFieldConfiguration.categoryFacetField(),
			searchContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> map) {
		_categoryFacetFieldConfiguration = ConfigurableUtil.createConfigurable(
			CategoryFacetFieldConfiguration.class, map);
	}

	private volatile CategoryFacetFieldConfiguration
		_categoryFacetFieldConfiguration;

}