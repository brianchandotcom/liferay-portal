/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Anderson Luiz
 * @author Thiago Buarque
 */
public class FrontendTokenDefinitionManager {

	public FrontendTokenDefinitionManager(
		JSONFactory jsonFactory,
		DCLSingleton<Map<String, FrontendTokenDefinitionImpl>>
			themeIdFrontendTokenDefinitionImplsDCLSingleton,
		Map<Long, Map<String, FrontendTokenDefinition>>
			allCompaniesFrontendTokenDefinitions,
		Map<String, FrontendTokenDefinitionImpl>
			themeIdFrontendTokenDefinitions) {

		_jsonFactory = jsonFactory;
		_themeIdFrontendTokenDefinitionImplsDCLSingleton =
			themeIdFrontendTokenDefinitionImplsDCLSingleton;
		_allCompaniesFrontendTokenDefinitions =
			allCompaniesFrontendTokenDefinitions;

		_themeIdFrontendTokenDefinitionImpls = themeIdFrontendTokenDefinitions;
	}

	public void addFrontendTokenDefinition(
		long companyId, String externalReferenceCode,
		String frontendTokenDefinitionAsJsonString) {

		_addFrontendTokenDefinitionFromClientExtension(
			companyId, externalReferenceCode,
			frontendTokenDefinitionAsJsonString,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	public FrontendTokenDefinitionImpl addFrontendTokenDefinition(
		String themeId, ResourceBundleLoader resourceBundleLoader,
		String frontendTokenDefinitionAsJsonString) {

		return _addFrontendTokenDefinitionFromWorkspace(
			frontendTokenDefinitionAsJsonString, resourceBundleLoader, themeId);
	}

	public FrontendTokenDefinition getFrontendTokenDefinition(
		long companyId, String externalReferenceCode) {

		return _getCompanyFrontendTokenDefinitions(
			companyId
		).get(
			externalReferenceCode
		);
	}

	public Map<String, FrontendTokenDefinitionImpl>
		getThemeIdFrontendTokenDefinitionImpls() {

		return _themeIdFrontendTokenDefinitionImpls;
	}

	public DCLSingleton<Map<String, FrontendTokenDefinitionImpl>>
		getThemeIdFrontendTokenDefinitionImplsDCLSingleton() {

		return _themeIdFrontendTokenDefinitionImplsDCLSingleton;
	}

	public void removeFrontendTokenDefinition(Long companyId, String key) {
		if (Objects.nonNull(companyId)) {
			_getCompanyFrontendTokenDefinitions(
				companyId
			).remove(
				key
			);
		}
		else {
			_themeIdFrontendTokenDefinitionImpls.remove(key);
		}
	}

	public void removeFrontendTokenDefinition(String key) {
		removeFrontendTokenDefinition(null, key);
	}

	private void _addFrontendTokenDefinitionFromClientExtension(
		Long companyId, String externalReferenceCode,
		String frontendTokenDefinitionAsJsonString,
		ResourceBundleLoader resourceBundleLoader) {

		try {
			Objects.requireNonNull(frontendTokenDefinitionAsJsonString);
			Objects.requireNonNull(companyId);
			Objects.requireNonNull(externalReferenceCode);

			FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
				_newFrontendTokenDefinitionImpl(
					frontendTokenDefinitionAsJsonString, resourceBundleLoader);

			if (frontendTokenDefinitionImpl != null) {
				_getCompanyFrontendTokenDefinitions(
					companyId
				).put(
					externalReferenceCode, frontendTokenDefinitionImpl
				);
			}
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	private FrontendTokenDefinitionImpl
		_addFrontendTokenDefinitionFromWorkspace(
			String frontendTokenDefinitionAsJsonString,
			ResourceBundleLoader resourceBundleLoader, String themeId) {

		try {
			Objects.requireNonNull(frontendTokenDefinitionAsJsonString);
			Objects.requireNonNull(themeId);

			FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
				_newFrontendTokenDefinitionImpl(
					frontendTokenDefinitionAsJsonString, resourceBundleLoader,
					themeId);

			if (frontendTokenDefinitionImpl != null) {
				_themeIdFrontendTokenDefinitionImpls.put(
					themeId, frontendTokenDefinitionImpl);
			}

			return frontendTokenDefinitionImpl;
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	private Map<String, FrontendTokenDefinition>
		_getCompanyFrontendTokenDefinitions(long companyId) {

		_allCompaniesFrontendTokenDefinitions.putIfAbsent(
			companyId, new ConcurrentHashMap<>());

		return _allCompaniesFrontendTokenDefinitions.get(companyId);
	}

	private FrontendTokenDefinitionImpl _newFrontendTokenDefinitionImpl(
			String frontendTokenDefinitionAsJSON,
			ResourceBundleLoader resourceBundleLoader)
		throws JSONException {

		return _newFrontendTokenDefinitionImpl(
			frontendTokenDefinitionAsJSON, resourceBundleLoader, null);
	}

	private FrontendTokenDefinitionImpl _newFrontendTokenDefinitionImpl(
			String frontendTokenDefinitionAsJSON,
			ResourceBundleLoader resourceBundleLoader, String themeId)
		throws JSONException {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			frontendTokenDefinitionAsJSON);

		return new FrontendTokenDefinitionImpl(
			jsonObject, _jsonFactory, resourceBundleLoader, themeId);
	}

	private final Map<Long, Map<String, FrontendTokenDefinition>>
		_allCompaniesFrontendTokenDefinitions;
	private final JSONFactory _jsonFactory;
	private final Map<String, FrontendTokenDefinitionImpl>
		_themeIdFrontendTokenDefinitionImpls;
	private final DCLSingleton<Map<String, FrontendTokenDefinitionImpl>>
		_themeIdFrontendTokenDefinitionImplsDCLSingleton;

}