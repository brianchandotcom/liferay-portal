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

package com.liferay.headless.admin.user.internal.graphql.servlet.v1_0;

import com.liferay.headless.admin.user.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.admin.user.internal.graphql.query.v1_0.Query;
import com.liferay.headless.admin.user.resource.v1_0.EmailAddressResource;
import com.liferay.headless.admin.user.resource.v1_0.OrganizationResource;
import com.liferay.headless.admin.user.resource.v1_0.PhoneResource;
import com.liferay.headless.admin.user.resource.v1_0.PostalAddressResource;
import com.liferay.headless.admin.user.resource.v1_0.RoleResource;
import com.liferay.headless.admin.user.resource.v1_0.SegmentResource;
import com.liferay.headless.admin.user.resource.v1_0.SegmentUserResource;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.headless.admin.user.resource.v1_0.WebUrlResource;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(immediate = true, service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Query.setEmailAddressResourceComponentServiceObjects(
			_emailAddressResourceComponentServiceObjects);
		Query.setOrganizationResourceComponentServiceObjects(
			_organizationResourceComponentServiceObjects);
		Query.setPhoneResourceComponentServiceObjects(
			_phoneResourceComponentServiceObjects);
		Query.setPostalAddressResourceComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects);
		Query.setRoleResourceComponentServiceObjects(
			_roleResourceComponentServiceObjects);
		Query.setSegmentResourceComponentServiceObjects(
			_segmentResourceComponentServiceObjects);
		Query.setSegmentUserResourceComponentServiceObjects(
			_segmentUserResourceComponentServiceObjects);
		Query.setUserAccountResourceComponentServiceObjects(
			_userAccountResourceComponentServiceObjects);
		Query.setWebUrlResourceComponentServiceObjects(
			_webUrlResourceComponentServiceObjects);
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated
	public String getPath() {
		return "/headless-admin-user-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	@Reference
	private ComponentServiceObjects<EmailAddressResource>
		_emailAddressResourceComponentServiceObjects;

	@Reference
	private ComponentServiceObjects<OrganizationResource>
		_organizationResourceComponentServiceObjects;

	@Reference
	private ComponentServiceObjects<PhoneResource>
		_phoneResourceComponentServiceObjects;

	@Reference
	private ComponentServiceObjects<PostalAddressResource>
		_postalAddressResourceComponentServiceObjects;

	@Reference
	private ComponentServiceObjects<RoleResource>
		_roleResourceComponentServiceObjects;

	@Reference
	private ComponentServiceObjects<SegmentResource>
		_segmentResourceComponentServiceObjects;

	@Reference
	private ComponentServiceObjects<SegmentUserResource>
		_segmentUserResourceComponentServiceObjects;

	@Reference
	private ComponentServiceObjects<UserAccountResource>
		_userAccountResourceComponentServiceObjects;

	@Reference
	private ComponentServiceObjects<WebUrlResource>
		_webUrlResourceComponentServiceObjects;

}