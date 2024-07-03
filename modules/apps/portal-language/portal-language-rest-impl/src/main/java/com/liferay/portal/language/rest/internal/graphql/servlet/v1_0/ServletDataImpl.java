/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.internal.graphql.servlet.v1_0;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.language.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.portal.language.rest.internal.graphql.query.v1_0.Query;
import com.liferay.portal.language.rest.internal.resource.v1_0.LanguageResourceImpl;
import com.liferay.portal.language.rest.resource.v1_0.LanguageResource;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Thiago Buarque
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setLanguageResourceComponentServiceObjects(
			_languageResourceComponentServiceObjects);

		Query.setLanguageResourceComponentServiceObjects(
			_languageResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Portal.Language.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/portal-language-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createLanguagesPageExportBatch",
						new ObjectValuePair<>(
							LanguageResourceImpl.class,
							"postLanguagesPageExportBatch"));
					put(
						"mutation#createLanguage",
						new ObjectValuePair<>(
							LanguageResourceImpl.class, "postLanguage"));
					put(
						"mutation#createLanguageBatch",
						new ObjectValuePair<>(
							LanguageResourceImpl.class, "postLanguageBatch"));
					put(
						"mutation#updateLanguage",
						new ObjectValuePair<>(
							LanguageResourceImpl.class, "putLanguage"));
					put(
						"mutation#updateLanguageBatch",
						new ObjectValuePair<>(
							LanguageResourceImpl.class, "putLanguageBatch"));
					put(
						"mutation#deleteLanguageByKey",
						new ObjectValuePair<>(
							LanguageResourceImpl.class, "deleteLanguageByKey"));

					put(
						"query#languages",
						new ObjectValuePair<>(
							LanguageResourceImpl.class, "getLanguagesPage"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<LanguageResource>
		_languageResourceComponentServiceObjects;

}