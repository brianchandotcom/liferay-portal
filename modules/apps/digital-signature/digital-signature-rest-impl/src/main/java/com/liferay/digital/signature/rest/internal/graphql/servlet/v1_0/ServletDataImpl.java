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

package com.liferay.digital.signature.rest.internal.graphql.servlet.v1_0;

import com.liferay.digital.signature.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.digital.signature.rest.internal.graphql.query.v1_0.Query;
import com.liferay.digital.signature.rest.internal.resource.v1_0.DSEnvelopeResourceImpl;
import com.liferay.digital.signature.rest.resource.v1_0.DSEnvelopeResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
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
 * @author José Abelenda
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setDSEnvelopeResourceComponentServiceObjects(
			_dsEnvelopeResourceComponentServiceObjects);

		Query.setDSEnvelopeResourceComponentServiceObjects(
			_dsEnvelopeResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Digital.Signature.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/digital-signature-rest-graphql/v1_0";
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
			"mutation#createSiteDSEnvelope",
			new ObjectValuePair<>(
				DSEnvelopeResourceImpl.class, "postSiteDSEnvelope"));
		_resourceMethodPairs.put(
			"mutation#createSiteDSEnvelopeBatch",
			new ObjectValuePair<>(
				DSEnvelopeResourceImpl.class, "postSiteDSEnvelopeBatch"));
		_resourceMethodPairs.put(
			"query#dSEnvelopes",
			new ObjectValuePair<>(
				DSEnvelopeResourceImpl.class, "getSiteDSEnvelopesPage"));
		_resourceMethodPairs.put(
			"query#dSEnvelope",
			new ObjectValuePair<>(
				DSEnvelopeResourceImpl.class, "getSiteDSEnvelope"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DSEnvelopeResource>
		_dsEnvelopeResourceComponentServiceObjects;

}