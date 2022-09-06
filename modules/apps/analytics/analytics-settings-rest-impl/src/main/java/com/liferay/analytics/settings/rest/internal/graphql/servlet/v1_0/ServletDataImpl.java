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

package com.liferay.analytics.settings.rest.internal.graphql.servlet.v1_0;

import com.liferay.analytics.settings.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.analytics.settings.rest.internal.graphql.query.v1_0.Query;
import com.liferay.analytics.settings.rest.internal.resource.v1_0.ChannelResourceImpl;
import com.liferay.analytics.settings.rest.internal.resource.v1_0.CommerceChannelResourceImpl;
import com.liferay.analytics.settings.rest.internal.resource.v1_0.ContactAccountGroupResourceImpl;
import com.liferay.analytics.settings.rest.internal.resource.v1_0.ContactConfigurationResourceImpl;
import com.liferay.analytics.settings.rest.internal.resource.v1_0.ContactOrganizationResourceImpl;
import com.liferay.analytics.settings.rest.internal.resource.v1_0.ContactUserGroupResourceImpl;
import com.liferay.analytics.settings.rest.internal.resource.v1_0.DataSourceResourceImpl;
import com.liferay.analytics.settings.rest.internal.resource.v1_0.FieldResourceImpl;
import com.liferay.analytics.settings.rest.internal.resource.v1_0.FieldSummaryResourceImpl;
import com.liferay.analytics.settings.rest.internal.resource.v1_0.SiteResourceImpl;
import com.liferay.analytics.settings.rest.resource.v1_0.ChannelResource;
import com.liferay.analytics.settings.rest.resource.v1_0.CommerceChannelResource;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactAccountGroupResource;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactConfigurationResource;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactOrganizationResource;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactUserGroupResource;
import com.liferay.analytics.settings.rest.resource.v1_0.DataSourceResource;
import com.liferay.analytics.settings.rest.resource.v1_0.FieldResource;
import com.liferay.analytics.settings.rest.resource.v1_0.FieldSummaryResource;
import com.liferay.analytics.settings.rest.resource.v1_0.SiteResource;
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
 * @author Riccardo Ferrari
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setChannelResourceComponentServiceObjects(
			_channelResourceComponentServiceObjects);
		Mutation.setContactConfigurationResourceComponentServiceObjects(
			_contactConfigurationResourceComponentServiceObjects);
		Mutation.setDataSourceResourceComponentServiceObjects(
			_dataSourceResourceComponentServiceObjects);
		Mutation.setFieldResourceComponentServiceObjects(
			_fieldResourceComponentServiceObjects);

		Query.setChannelResourceComponentServiceObjects(
			_channelResourceComponentServiceObjects);
		Query.setCommerceChannelResourceComponentServiceObjects(
			_commerceChannelResourceComponentServiceObjects);
		Query.setContactAccountGroupResourceComponentServiceObjects(
			_contactAccountGroupResourceComponentServiceObjects);
		Query.setContactConfigurationResourceComponentServiceObjects(
			_contactConfigurationResourceComponentServiceObjects);
		Query.setContactOrganizationResourceComponentServiceObjects(
			_contactOrganizationResourceComponentServiceObjects);
		Query.setContactUserGroupResourceComponentServiceObjects(
			_contactUserGroupResourceComponentServiceObjects);
		Query.setFieldResourceComponentServiceObjects(
			_fieldResourceComponentServiceObjects);
		Query.setFieldSummaryResourceComponentServiceObjects(
			_fieldSummaryResourceComponentServiceObjects);
		Query.setSiteResourceComponentServiceObjects(
			_siteResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Analytyics.Settings.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/analytics-settings-rest-graphql/v1_0";
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
			"mutation#patchChannel",
			new ObjectValuePair<>(ChannelResourceImpl.class, "patchChannel"));
		_resourceMethodPairs.put(
			"mutation#createChannel",
			new ObjectValuePair<>(ChannelResourceImpl.class, "postChannel"));
		_resourceMethodPairs.put(
			"mutation#updateContactConfiguration",
			new ObjectValuePair<>(
				ContactConfigurationResourceImpl.class,
				"putContactConfiguration"));
		_resourceMethodPairs.put(
			"mutation#deleteDataSource",
			new ObjectValuePair<>(
				DataSourceResourceImpl.class, "deleteDataSource"));
		_resourceMethodPairs.put(
			"mutation#createDataSource",
			new ObjectValuePair<>(
				DataSourceResourceImpl.class, "postDataSource"));
		_resourceMethodPairs.put(
			"mutation#patchFieldPeople",
			new ObjectValuePair<>(FieldResourceImpl.class, "patchFieldPeople"));
		_resourceMethodPairs.put(
			"query#channels",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "getChannelsPage"));
		_resourceMethodPairs.put(
			"query#commerceChannels",
			new ObjectValuePair<>(
				CommerceChannelResourceImpl.class, "getCommerceChannelsPage"));
		_resourceMethodPairs.put(
			"query#contactAccountGroups",
			new ObjectValuePair<>(
				ContactAccountGroupResourceImpl.class,
				"getContactAccountGroupsPage"));
		_resourceMethodPairs.put(
			"query#contactConfiguration",
			new ObjectValuePair<>(
				ContactConfigurationResourceImpl.class,
				"getContactConfiguration"));
		_resourceMethodPairs.put(
			"query#contactOrganizations",
			new ObjectValuePair<>(
				ContactOrganizationResourceImpl.class,
				"getContactOrganizationsPage"));
		_resourceMethodPairs.put(
			"query#contactUserGroups",
			new ObjectValuePair<>(
				ContactUserGroupResourceImpl.class,
				"getContactUserGroupsPage"));
		_resourceMethodPairs.put(
			"query#fieldsPeople",
			new ObjectValuePair<>(
				FieldResourceImpl.class, "getFieldsPeoplePage"));
		_resourceMethodPairs.put(
			"query#field",
			new ObjectValuePair<>(FieldSummaryResourceImpl.class, "getField"));
		_resourceMethodPairs.put(
			"query#sites",
			new ObjectValuePair<>(SiteResourceImpl.class, "getSitesPage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ContactConfigurationResource>
		_contactConfigurationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DataSourceResource>
		_dataSourceResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FieldResource>
		_fieldResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CommerceChannelResource>
		_commerceChannelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ContactAccountGroupResource>
		_contactAccountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ContactOrganizationResource>
		_contactOrganizationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ContactUserGroupResource>
		_contactUserGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FieldSummaryResource>
		_fieldSummaryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SiteResource>
		_siteResourceComponentServiceObjects;

}