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

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributor;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributorRegistry;
import com.liferay.frontend.data.set.filter.FDSFilterRegistry;
import com.liferay.frontend.data.set.filter.FDSFilterSerializer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
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
@Component(immediate = true, service = FDSFilterSerializer.class)
public class FDSFilterSerializerImpl implements FDSFilterSerializer {

	@Override
	public JSONArray serialize(
		String fdsDisplayName, List<FDSFilter> fdsFilters, Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		_serialize(fdsFilters, jsonArray, locale);
		_serialize(
			_fdsFilterRegistry.getFDSFilters(fdsDisplayName), jsonArray,
			locale);

		return jsonArray;
	}

	private void _serialize(
		List<FDSFilter> fdsFilters, JSONArray jsonArray, Locale locale) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		for (FDSFilter fdsFilter : fdsFilters) {
			JSONObject jsonObject = JSONUtil.put(
				"id", fdsFilter.getId()
			).put(
				"label", _language.get(resourceBundle, fdsFilter.getLabel())
			).put(
				"preloadedData", fdsFilter.getPreloadedData()
			).put(
				"type", fdsFilter.getType()
			);

			List<FDSFilterContextContributor> fdsFilterContextContributors =
				_fdsFilterContextContributorRegistry.
					getFDSFilterContextContributors(fdsFilter.getType());

			for (FDSFilterContextContributor fdsFilterContextContributor :
					fdsFilterContextContributors) {

				Map<String, Object> fdsFilterContext =
					fdsFilterContextContributor.getFDSFilterContext(
						fdsFilter, locale);

				if (fdsFilterContext == null) {
					continue;
				}

				for (Map.Entry<String, Object> entry :
						fdsFilterContext.entrySet()) {

					jsonObject.put(entry.getKey(), entry.getValue());
				}
			}

			jsonArray.put(jsonObject);
		}
	}

	@Reference
	private FDSFilterContextContributorRegistry
		_fdsFilterContextContributorRegistry;

	@Reference
	private FDSFilterRegistry _fdsFilterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}