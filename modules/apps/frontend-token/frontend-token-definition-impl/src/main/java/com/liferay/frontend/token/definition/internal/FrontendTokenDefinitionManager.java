/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
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
		Map<Long, Map<String, FrontendTokenDefinition>>
			companyFrontendTokenDefinitionsMap,
		JSONFactory jsonFactory,
		Map<String, FrontendTokenDefinitionImpl>
			themeIdFrontendTokenDefinitionsMap,
		DCLSingleton<Map<String, FrontendTokenDefinitionImpl>>
			themeIdFrontendTokenDefinitionsDCLSingleton) {

		_companyFrontendTokenDefinitionsMap =
			companyFrontendTokenDefinitionsMap;
		_jsonFactory = jsonFactory;

		_themeIdFrontendTokenDefinitionImplsMap =
			themeIdFrontendTokenDefinitionsMap;
		_themeIdFrontendTokenDefinitionImplsDCLSingleton =
			themeIdFrontendTokenDefinitionsDCLSingleton;
	}

	public void addFrontendTokenDefinition(
		long companyId, String externalReferenceCode,
		String frontendTokenDefinitionJSONString) {

		try {
			FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
				_createFrontendTokenDefinitionImpl(
					frontendTokenDefinitionJSONString,
					ResourceBundleLoaderUtil.getPortalResourceBundleLoader(),
					externalReferenceCode);

			Map<String, FrontendTokenDefinition> frontendTokenDefinitionsMap =
				_getFrontendTokenDefinitionsMap(companyId);

			frontendTokenDefinitionsMap.put(
				externalReferenceCode, frontendTokenDefinitionImpl);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	public FrontendTokenDefinitionImpl addFrontendTokenDefinition(
		String frontendTokenDefinitionJSONString,
		ResourceBundleLoader resourceBundleLoader, String themeId) {

		try {
			Objects.requireNonNull(themeId);

			FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
				_createFrontendTokenDefinitionImpl(
					frontendTokenDefinitionJSONString, resourceBundleLoader,
					themeId);

			_themeIdFrontendTokenDefinitionImplsMap.put(
				themeId, frontendTokenDefinitionImpl);

			return frontendTokenDefinitionImpl;
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	public FrontendTokenDefinition getFrontendTokenDefinition(
		long companyId, String externalReferenceCode) {

		Map<String, FrontendTokenDefinition> frontendTokenDefinitionsMap =
			_getFrontendTokenDefinitionsMap(companyId);

		return frontendTokenDefinitionsMap.get(externalReferenceCode);
	}

	public FrontendTokenDefinition getFrontendTokenDefinition(
		Runnable runnable, String themeId) {

		Map<String, FrontendTokenDefinitionImpl>
			themeIdFrontendTokenDefinitionImpls =
				_themeIdFrontendTokenDefinitionImplsDCLSingleton.getSingleton(
					() -> {
						runnable.run();

						return _themeIdFrontendTokenDefinitionImplsMap;
					});

		return themeIdFrontendTokenDefinitionImpls.get(themeId);
	}

	public void removeFrontendTokenDefinition(
		long companyId, String externalReferenceCode) {

		Map<String, FrontendTokenDefinition> frontendTokenDefinitionsMap =
			_getFrontendTokenDefinitionsMap(companyId);

		frontendTokenDefinitionsMap.remove(externalReferenceCode);
	}

	public void removeFrontendTokenDefinition(String themeId) {
		_themeIdFrontendTokenDefinitionImplsMap.remove(themeId);
	}

	private FrontendTokenDefinitionImpl _createFrontendTokenDefinitionImpl(
			String frontendTokenDefinitionJSONString,
			ResourceBundleLoader resourceBundleLoader, String themeId)
		throws JSONException {

		return new FrontendTokenDefinitionImpl(
			_jsonFactory.createJSONObject(frontendTokenDefinitionJSONString),
			_jsonFactory, resourceBundleLoader, themeId);
	}

	private Map<String, FrontendTokenDefinition>
		_getFrontendTokenDefinitionsMap(long companyId) {

		_companyFrontendTokenDefinitionsMap.putIfAbsent(
			companyId, new ConcurrentHashMap<>());

		return _companyFrontendTokenDefinitionsMap.get(companyId);
	}

	private final Map<Long, Map<String, FrontendTokenDefinition>>
		_companyFrontendTokenDefinitionsMap;
	private final JSONFactory _jsonFactory;
	private final DCLSingleton<Map<String, FrontendTokenDefinitionImpl>>
		_themeIdFrontendTokenDefinitionImplsDCLSingleton;
	private final Map<String, FrontendTokenDefinitionImpl>
		_themeIdFrontendTokenDefinitionImplsMap;

}