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

package com.liferay.frontend.data.set.internal.view;

import com.liferay.frontend.data.set.view.FrontendDataSetView;
import com.liferay.frontend.data.set.view.FrontendDataSetViewContextContributor;
import com.liferay.frontend.data.set.view.FrontendDataSetViewContextContributorRegistry;
import com.liferay.frontend.data.set.view.FrontendDataSetViewRegistry;
import com.liferay.frontend.data.set.view.FrontendDataSetViewSerializer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = FrontendDataSetViewSerializer.class)
public class FrontendDataSetViewSerializerImpl
	implements FrontendDataSetViewSerializer {

	@Override
	public JSONArray serialize(String frontendDataSetName, Locale locale) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		List<FrontendDataSetView> frontendDataSetViews =
			_frontendDataSetViewRegistry.getFrontendDataSetViews(
				frontendDataSetName);

		for (FrontendDataSetView frontendDataSetView : frontendDataSetViews) {
			JSONObject jsonObject = JSONUtil.put(
				"contentRenderer", frontendDataSetView.getContentRenderer()
			).put(
				"contentRendererModuleURL",
				frontendDataSetView.getContentRendererModuleURL()
			).put(
				"label",
				LanguageUtil.get(
					ResourceBundleUtil.getBundle(
						"content.Language", locale, getClass()),
					frontendDataSetView.getLabel())
			).put(
				"name", frontendDataSetView.getName()
			).put(
				"thumbnail", frontendDataSetView.getThumbnail()
			);

			List<FrontendDataSetViewContextContributor>
				frontendDataSetViewContextContributors =
					_frontendDataSetViewContextContributorRegistry.
						getFrontendDataSetViewContextContributors(
							frontendDataSetView.getContentRenderer());

			for (FrontendDataSetViewContextContributor
					frontendDataSetViewContextContributor :
						frontendDataSetViewContextContributors) {

				Map<String, Object> frontendDataSetViewContext =
					frontendDataSetViewContextContributor.
						getFrontendDataSetViewContext(
							frontendDataSetView, locale);

				if (frontendDataSetViewContext == null) {
					continue;
				}

				for (Map.Entry<String, Object> frontendDataSetViewContextEntry :
						frontendDataSetViewContext.entrySet()) {

					jsonObject.put(
						frontendDataSetViewContextEntry.getKey(),
						frontendDataSetViewContextEntry.getValue());
				}
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Reference
	private FrontendDataSetViewContextContributorRegistry
		_frontendDataSetViewContextContributorRegistry;

	@Reference
	private FrontendDataSetViewRegistry _frontendDataSetViewRegistry;

	@Reference
	private JSONFactory _jsonFactory;

}