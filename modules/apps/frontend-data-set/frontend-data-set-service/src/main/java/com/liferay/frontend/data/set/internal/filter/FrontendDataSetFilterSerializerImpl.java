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

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.FrontendDataSetFilter;
import com.liferay.frontend.data.set.filter.FrontendDataSetFilterContextContributor;
import com.liferay.frontend.data.set.filter.FrontendDataSetFilterContextContributorRegistry;
import com.liferay.frontend.data.set.filter.FrontendDataSetFilterRegistry;
import com.liferay.frontend.data.set.filter.FrontendDataSetFilterSerializer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = FrontendDataSetFilterSerializer.class)
public class FrontendDataSetFilterSerializerImpl
	implements FrontendDataSetFilterSerializer {

	@Override
	public JSONArray serialize(String frontendDataSetName, Locale locale) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		List<FrontendDataSetFilter> frontendDataSetFilters =
			_frontendDataSetFilterRegistry.getFrontendDataSetFilters(
				frontendDataSetName);

		for (FrontendDataSetFilter frontendDataSetFilter :
				frontendDataSetFilters) {

			String label = LanguageUtil.get(
				resourceBundle, frontendDataSetFilter.getLabel());

			JSONObject jsonObject = JSONUtil.put(
				"id", frontendDataSetFilter.getId()
			).put(
				"label", label
			).put(
				"type", frontendDataSetFilter.getType()
			);

			List<FrontendDataSetFilterContextContributor>
				frontendDataSetFilterContextContributors =
					_frontendDataSetFilterContextContributorRegistry.
						getFrontendDataSetFilterContextContributors(
							frontendDataSetFilter.getType());

			for (FrontendDataSetFilterContextContributor
					frontendDataSetFilterContextContributor :
						frontendDataSetFilterContextContributors) {

				Map<String, Object> filterContext =
					frontendDataSetFilterContextContributor.
						getFrontendDataSetFilterContext(
							frontendDataSetFilter, locale);

				if (filterContext == null) {
					continue;
				}

				for (Map.Entry<String, Object> filterContextEntry :
						filterContext.entrySet()) {

					jsonObject.put(
						filterContextEntry.getKey(),
						filterContextEntry.getValue());
				}
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Reference
	private FrontendDataSetFilterContextContributorRegistry
		_frontendDataSetFilterContextContributorRegistry;

	@Reference
	private FrontendDataSetFilterRegistry _frontendDataSetFilterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

}