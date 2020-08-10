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

package com.liferay.style.book.internal.frontend.css.variables;

import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.frontend.css.variables.ScopedCSSVariables;
import com.liferay.frontend.css.variables.ScopedCSSVariablesProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ScopedCSSVariablesProvider.class)
public class StyleBookScopedCSSVariablesProvider
	implements ScopedCSSVariablesProvider {

	@Override
	public Collection<ScopedCSSVariables> getScopedCSSVariablesCollection(
		HttpServletRequest httpServletRequest) {

		String frontendTokensValues = _getFrontendTokensValues(
			httpServletRequest);

		if (Validator.isNull(frontendTokensValues)) {
			return Collections.emptyList();
		}

		return Collections.singletonList(
			new ScopedCSSVariables() {

				public Map<String, String> getCSSVariables() {
					Map<String, String> cssVariables = new HashMap<>();

					try {
						JSONObject frontendTokensValuesJSONObject =
							JSONFactoryUtil.createJSONObject(
								frontendTokensValues);

						Iterator<String> iterator =
							frontendTokensValuesJSONObject.keys();

						while (iterator.hasNext()) {
							String key = iterator.next();

							JSONObject frontendTokenValueJSONObject =
								frontendTokensValuesJSONObject.getJSONObject(
									key);

							cssVariables.put(
								frontendTokenValueJSONObject.getString(
									"cssVariableMapping"),
								frontendTokenValueJSONObject.getString(
									"value"));
						}
					}
					catch (JSONException jsonException) {
						if (_log.isDebugEnabled()) {
							_log.debug("Unable to parse JSON", jsonException);
						}
					}

					return cssVariables;
				}

				public String getScope() {
					return "#wrapper";
				}

			});
	}

	private String _getFrontendTokensValues(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getSiteGroup();

		if (group.isControlPanel()) {
			return StringPool.BLANK;
		}

		StyleBookEntry styleBookEntry = null;

		Layout layout = themeDisplay.getLayout();

		if (layout.getStyleBookEntryId() > 0) {
			styleBookEntry = _styleBookEntryLocalService.fetchStyleBookEntry(
				layout.getStyleBookEntryId());
		}

		if ((styleBookEntry == null) && (layout.getMasterLayoutPlid() > 0)) {
			Layout masterLayout = _layoutLocalService.fetchLayout(
				layout.getMasterLayoutPlid());

			styleBookEntry = _styleBookEntryLocalService.fetchStyleBookEntry(
				masterLayout.getStyleBookEntryId());
		}

		if (styleBookEntry == null) {
			styleBookEntry =
				_styleBookEntryLocalService.fetchDefaultStyleBookEntry(
					_staging.getLiveGroupId(layout.getGroupId()));
		}

		if (styleBookEntry == null) {
			return StringPool.BLANK;
		}

		return styleBookEntry.getFrontendTokensValues();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StyleBookScopedCSSVariablesProvider.class);

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Staging _staging;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}