/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.view;

import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.FDSViewContextContributor;
import com.liferay.frontend.data.set.view.FDSViewContextContributorRegistry;
import com.liferay.frontend.data.set.view.FDSViewRegistry;
import com.liferay.frontend.data.set.view.FDSViewSerializer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = FDSViewSerializer.class)
public class FDSViewSerializerImpl implements FDSViewSerializer {

	@Override
	public JSONArray serialize(String fdsName, Locale locale) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		List<FDSView> fdsViews = _fdsViewRegistry.getFDSViews(fdsName);

		for (FDSView fdsView : fdsViews) {
			JSONObject jsonObject = JSONUtil.put(
				"contentRenderer", fdsView.getContentRenderer()
			).put(
				"contentRendererModuleURL",
				fdsView.getContentRendererModuleURL()
			).put(
				"default", fdsView.isDefault()
			).put(
				"label",
				_language.get(
					ResourceBundleUtil.getBundle(
						"content.Language", locale, getClass()),
					fdsView.getLabel())
			).put(
				"name", fdsView.getName()
			).put(
				"thumbnail", fdsView.getThumbnail()
			);

			List<FDSViewContextContributor> fdsViewContextContributors =
				_fdsViewContextContributorRegistry.
					getFDSViewContextContributors(fdsView.getContentRenderer());

			for (FDSViewContextContributor fdsViewContextContributor :
					fdsViewContextContributors) {

				Map<String, Object> fdsViewContext =
					fdsViewContextContributor.getFDSViewContext(
						fdsView, locale);

				if (fdsViewContext == null) {
					continue;
				}

				for (Map.Entry<String, Object> fdsViewContextEntry :
						fdsViewContext.entrySet()) {

					jsonObject.put(
						fdsViewContextEntry.getKey(),
						fdsViewContextEntry.getValue());
				}
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Reference
	private FDSViewContextContributorRegistry
		_fdsViewContextContributorRegistry;

	@Reference
	private FDSViewRegistry _fdsViewRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}