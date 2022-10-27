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
import com.liferay.analytics.settings.rest.resource.v1_0.ChannelResource;
import com.liferay.analytics.settings.rest.resource.v1_0.CommerceChannelResource;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactAccountGroupResource;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactConfigurationResource;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactOrganizationResource;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactUserGroupResource;
import com.liferay.analytics.settings.rest.resource.v1_0.DataSourceResource;
import com.liferay.analytics.settings.rest.resource.v1_0.SiteResource;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

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
		Query.setSiteResourceComponentServiceObjects(
			_siteResourceComponentServiceObjects);
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
	private ComponentServiceObjects<SiteResource>
		_siteResourceComponentServiceObjects;

}