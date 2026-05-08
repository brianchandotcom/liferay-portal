/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.graphql.servlet.v1_0;

import com.liferay.osb.faro.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.osb.faro.rest.internal.graphql.query.v1_0.Query;
import com.liferay.osb.faro.rest.internal.resource.v1_0.AssetSummaryMetricResourceImpl;
import com.liferay.osb.faro.rest.internal.resource.v1_0.EventResourceImpl;
import com.liferay.osb.faro.rest.internal.resource.v1_0.PageMetricResourceImpl;
import com.liferay.osb.faro.rest.resource.v1_0.AssetSummaryMetricResource;
import com.liferay.osb.faro.rest.resource.v1_0.EventResource;
import com.liferay.osb.faro.rest.resource.v1_0.PageMetricResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import jakarta.annotation.Generated;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Leslie Wong
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Query.setAssetSummaryMetricResourceComponentServiceObjects(
			_assetSummaryMetricResourceComponentServiceObjects);
		Query.setEventResourceComponentServiceObjects(
			_eventResourceComponentServiceObjects);
		Query.setPageMetricResourceComponentServiceObjects(
			_pageMetricResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Faro.Rest";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/faro-rest-graphql/v1_0";
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
						"query#workspaceGroupAssetSummaries",
						new ObjectValuePair<>(
							AssetSummaryMetricResourceImpl.class,
							"getWorkspaceGroupAssetSummariesPage"));
					put(
						"query#workspaceGroupChannelEvents",
						new ObjectValuePair<>(
							EventResourceImpl.class,
							"getWorkspaceGroupChannelEventsPage"));
					put(
						"query#workspaceGroupPages",
						new ObjectValuePair<>(
							PageMetricResourceImpl.class,
							"getWorkspaceGroupPagesPage"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AssetSummaryMetricResource>
		_assetSummaryMetricResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<EventResource>
		_eventResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageMetricResource>
		_pageMetricResourceComponentServiceObjects;

}
// LIFERAY-REST-BUILDER-HASH:-2109415230