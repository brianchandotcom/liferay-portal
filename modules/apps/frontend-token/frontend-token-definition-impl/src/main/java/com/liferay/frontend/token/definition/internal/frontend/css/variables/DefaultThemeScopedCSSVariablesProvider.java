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

package com.liferay.frontend.token.definition.internal.frontend.css.variables;

import com.liferay.frontend.css.variables.ScopedCSSVariables;
import com.liferay.frontend.css.variables.ScopedCSSVariablesProvider;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 */
@Component(service = ScopedCSSVariablesProvider.class)
public class DefaultThemeScopedCSSVariablesProvider
	implements ScopedCSSVariablesProvider {

	@Override
	public Collection<ScopedCSSVariables> getScopedCSSVariablesCollection(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
			themeDisplay.getSiteGroupId(), false);

		FrontendTokenDefinition frontendTokenDefinition =
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				layoutSet.getThemeId());

		return Collections.singletonList(
			new ScopedCSSVariables() {

				public Map<String, String> getCSSVariables() {
					Map<String, String> cssVariables = new HashMap<>();

					try {
						JSONObject frontendTokenDefinitionJSONObject =
							JSONFactoryUtil.createJSONObject(
								frontendTokenDefinition.getJSON(
									themeDisplay.getLocale()));

						JSONArray frontendTokenCategoriesJSONArray =
							frontendTokenDefinitionJSONObject.getJSONArray(
								"frontendTokenCategories");

						if (frontendTokenCategoriesJSONArray != null) {
							for (Object frontendTokenCategory :
									frontendTokenCategoriesJSONArray) {

								JSONArray frontendTokenSetsJSONArray =
									((JSONObject)frontendTokenCategory).
										getJSONArray("frontendTokenSets");

								if (frontendTokenSetsJSONArray != null) {
									for (Object frontendTokenSet :
											frontendTokenSetsJSONArray) {

										JSONArray frontendTokensJSONArray =
											((JSONObject)frontendTokenSet).
												getJSONArray("frontendTokens");

										if (frontendTokensJSONArray != null) {
											for (Object frontendToken :
													frontendTokensJSONArray) {

												JSONObject
													frontendTokenJSONObject =
														(JSONObject)
															frontendTokensJSONArray;

												JSONArray
													frontendTokenMappingsJSONArray =
														frontendTokenJSONObject.
															getJSONArray(
																"mappings");

												for (Object
														frontendTokenMapping :
															frontendTokenMappingsJSONArray) {

													JSONObject
														frontendTokenMappingJSONObject =
															(JSONObject)
																frontendTokenMapping;

													if (frontendTokenMappingJSONObject.
															getString(
																"type"
															).equals(
																"cssVariable"
															)) {

														cssVariables.put(
															frontendTokenMappingJSONObject.
																getString(
																	"value"),
															frontendTokenJSONObject.
																getString(
																	"defaultValue"));
													}
												}
											}
										}
									}
								}
							}
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

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultThemeScopedCSSVariablesProvider.class);

	@Reference
	private FrontendTokenDefinitionRegistry _frontendTokenDefinitionRegistry;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}