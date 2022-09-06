/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.admin.rest.internal.graphql.servlet.v1_0;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.saml.admin.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.saml.admin.rest.internal.graphql.query.v1_0.Query;
import com.liferay.saml.admin.rest.internal.resource.v1_0.SamlProviderResourceImpl;
import com.liferay.saml.admin.rest.resource.v1_0.SamlProviderResource;

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
 * @author Stian Sigvartsen
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setSamlProviderResourceComponentServiceObjects(
			_samlProviderResourceComponentServiceObjects);

		Query.setSamlProviderResourceComponentServiceObjects(
			_samlProviderResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Saml.Admin.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/saml-admin-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#patchSamlProvider",
			new ObjectValuePair<>(
				SamlProviderResourceImpl.class, "patchSamlProvider"));
		_resourceMethodPairs.put(
			"mutation#createSamlProvider",
			new ObjectValuePair<>(
				SamlProviderResourceImpl.class, "postSamlProvider"));
		_resourceMethodPairs.put(
			"mutation#createSamlProviderBatch",
			new ObjectValuePair<>(
				SamlProviderResourceImpl.class, "postSamlProviderBatch"));
		_resourceMethodPairs.put(
			"query#samlProvider",
			new ObjectValuePair<>(
				SamlProviderResourceImpl.class, "getSamlProvider"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SamlProviderResource>
		_samlProviderResourceComponentServiceObjects;

}